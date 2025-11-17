package stockTrading.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stockTrading.domain.model.*;
import stockTrading.domain.repository.AccountRepository;
import stockTrading.domain.repository.OrderBookRepository;
import stockTrading.domain.repository.OrderRepository;
import stockTrading.domain.repository.SymbolRegistry;
import stockTrading.global.util.OrderParser;
import stockTrading.infra.InMemoryAccountRepository;
import stockTrading.infra.InMemoryOrderBookRepository;
import stockTrading.infra.InMemoryOrderRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class OrderServiceTest {

    private OrderService orderService;
    private SymbolRegistry symbolRegistry;
    private AccountRepository accountRepository;
    private OrderRepository orderRepository;
    private OrderBookRepository orderBookRepository;

    @BeforeEach
    void setUp() {
        // given
        accountRepository = new InMemoryAccountRepository();
        Symbols symbols = new Symbols();
        symbols.add(new Symbol("APPL"));
        symbols.add(new Symbol("GOOG"));
        symbolRegistry = new SymbolRegistry(symbols);
        orderRepository = new InMemoryOrderRepository();
        orderBookRepository = new InMemoryOrderBookRepository();
        orderBookRepository.add(new OrderBook(new Symbol("APPL")));
        orderBookRepository.add(new OrderBook(new Symbol("GOOG")));

        // 계좌 초기화
        Account accountA = new Account("3333-11-1234567", 4_000_000);
        Account accountB = new Account("3333-22-1234567", 4_000_000);
        accountRepository.add(accountA);
        accountRepository.add(accountB);

        // 계좌 종목 초기화
        AccountSymbol accountSymbol1 = AccountSymbol.of(new Symbol("APPL"), 10);
        AccountSymbols accountSymbols1 = new AccountSymbols();
        accountSymbols1.add(accountSymbol1);
        accountA.initializeSymbolQuantities(accountSymbols1);

        AccountSymbols accountSymbols2 = new AccountSymbols();
        AccountSymbol accountSymbol2 = AccountSymbol.of(new Symbol("APPL"), 10);
        accountSymbols2.add(accountSymbol2);
        accountB.initializeSymbolQuantities(accountSymbols2);

        OrderValidator orderValidator = new OrderValidator();

        orderService = new OrderService(
                accountRepository,
                symbolRegistry,
                orderRepository,
                orderValidator,
                new MatchingService(orderBookRepository),
                new SettlementService(accountRepository),
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
        Order order = Order.create(
                "3333-11-1234567",
                new Symbol("APPL"),
                "BUY",
                10_000,
                10
        );
        orderRepository.add(order);
        // order 상태 CANCELED
        orderService.startOrder("CANCEL 1");
        // OrderBook에서 Order 제거
        OrderBook orderBook = orderBookRepository.findBySymbol(new Symbol("APPL")).get();
        orderBook.removeOrder(order.getOrderId());

        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCELLED);
        assertThatThrownBy(() ->
            orderBook.findOrderById(order.getOrderId())
        ).isInstanceOf(IllegalArgumentException.class);

    }

    // 완료된 주문 취소 할때 예외 처리
    @Test
    @DisplayName("COMPLETE 된 주문을 취소할 시에 예외 처리")
    void orderCancelFailTest() {
        // given
        Order order1 = Order.create(
                "3333-11-1234567",
                new Symbol("APPL"),
                "BUY",
                10_000,
                10
        );
        Order order2 = Order.create(
                "3333-22-1234567",
                new Symbol("APPL"),
                "SELL",
                10_000,
                10
        );
        orderRepository.add(order1);
        orderRepository.add(order2);

        assertThatThrownBy(() ->
            orderService.startOrder("CANCEL 1")
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
