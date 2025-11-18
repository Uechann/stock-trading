package stockTrading.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stockTrading.domain.model.*;
import stockTrading.domain.repository.*;
import stockTrading.dto.OrderRequest;
import stockTrading.global.util.OrderParser;
import stockTrading.infra.*;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class OrderServiceTest {

    private OrderService orderService;
    private SymbolRegistry symbolRegistry;
    private SymbolPriceProvider symbolPriceProvider;
    private AccountRepository accountRepository;
    private OrderRepository orderRepository;
    private OrderBookRepository orderBookRepository;
    private TradeRepository tradeRepository;

    @BeforeEach
    void setUp() {
        // given
        accountRepository = new InMemoryAccountRepository();
        orderRepository = new InMemoryOrderRepository();
        Symbols symbols = new Symbols();
        symbols.add(new Symbol("APPL"));
        symbols.add(new Symbol("GOOG"));
        symbolRegistry = new SymbolRegistry(symbols);
        orderBookRepository = new InMemoryOrderBookRepository();
        orderBookRepository.add(OrderBook.create(new Symbol("APPL")));
        orderBookRepository.add(OrderBook.create(new Symbol("GOOG")));
        tradeRepository = new InMemoryTradeRepository();
        symbolPriceProvider = new InMemorySymbolPrice();

        // 계좌 초기화
        Account accountA = Account.create("3333-11-1234567", 4_000_000);
        Account accountB = Account.create("3333-22-1234567", 4_000_000);
        accountRepository.add(accountA);
        accountRepository.add(accountB);

        // 계좌 종목 초기화
        Position position1 = Position.create(new Symbol("APPL"), 10000, 10);
        Positions positions1 = new Positions();
        positions1.add(position1);
        accountA.initializeSymbolQuantities(positions1);

        Positions positions2 = new Positions();
        Position position2 = Position.create(new Symbol("APPL"), 10000, 10);
        positions2.add(position2);
        accountB.initializeSymbolQuantities(positions2);

        OrderValidator orderValidator = new OrderValidator();

        orderService = new OrderService(
                accountRepository,
                symbolRegistry,
                orderRepository,
                orderValidator,
                new TradeService(orderRepository, orderBookRepository, tradeRepository),
                new SettlementService(accountRepository, symbolPriceProvider),
                new OrderParser()
        );
    }

    @Test
    @DisplayName("존재하지 않는 계좌 입력 예외")
    void notExistAccountTest() {
        String orderInput = "3333-12-1234567 APPL BUY 150 50";
        assertThatThrownBy(() ->
                orderService.startOrder(orderInput)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("존재하지 않는 종목일 경우 예외 처리")
    void notExistSymbolTest() {
        String orderInput = "3333-12-1234567 TSLA BUY 150 50";
        assertThatThrownBy(() ->
                orderService.startOrder(orderInput)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    // 주문 취소 기능 테스트
    @Test
    @DisplayName("주문 id로 주문을 취소가 성공되는 지 테스트 코드 작성")
    void orderCancelTest() {
        // given
        List<String> orderInput = Arrays.stream("ORDER 3333-11-1234567 APPL BUY 10000 10".split(" "))
                .toList();
        OrderRequest orderRequest = OrderRequest.create(orderInput);
        Order order = Order.create(orderRequest);

        orderRepository.add(order);

        orderService.startOrder("CANCEL 1");
        OrderBook orderBook = orderBookRepository.findBySymbol(new Symbol("APPL")).get();

        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCELLED);
        assertThatThrownBy(() ->
                orderBook.findOrderById(order.getOrderId())
        ).isInstanceOf(IllegalArgumentException.class);
    }

    // 완료된 주문 취소 할때 예외 처리
    @Test
    @DisplayName("PENDING이 아닌 주문을 취소할 시에 예외 처리")
    void orderCancelFailTest() {
        // given
        List<String> orderInput = Arrays.stream("ORDER 3333-11-1234567 APPL BUY 1000 10".split(" "))
                .toList();
        OrderRequest orderRequest = OrderRequest.create(orderInput);
        Order order = Order.create(orderRequest);
        orderRepository.add(order);
        order.cancel();

        assertThatThrownBy(() ->
                orderService.startOrder("CANCEL 1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 해당 주문을 취소할 수 없습니다.");
    }
}
