package stockTrading.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stockTrading.domain.model.*;
import stockTrading.domain.repository.AccountRepository;
import stockTrading.domain.repository.OrderRepository;
import stockTrading.domain.repository.SymbolRegistry;
import stockTrading.domain.repository.TradeRepository;
import stockTrading.dto.*;
import stockTrading.infra.InMemoryAccountRepository;
import stockTrading.infra.InMemoryOrderRepository;
import stockTrading.infra.InMemorySymbolPrice;
import stockTrading.infra.InMemoryTradeRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class SummaryServiceTest {

    private AccountRepository accountRepository;
    private SymbolRegistry symbolRegistry;
    private SymbolPriceProvider symbolPriceProvider;
    private OrderRepository orderRepository;
    private TradeRepository tradeRepository;
    private SummaryService summaryService;

    private Symbol APPL;
    private Symbol GOOG;

    @BeforeEach
    void setUp() {
        accountRepository = new InMemoryAccountRepository();
        orderRepository = new InMemoryOrderRepository();
        tradeRepository = new InMemoryTradeRepository();
        symbolPriceProvider = new InMemorySymbolPrice();

        APPL = new Symbol("APPL");
        GOOG = new Symbol("GOOG");
        symbolRegistry = new SymbolRegistry(Symbols.create(List.of(APPL, GOOG)));

        summaryService = new SummaryService(
                accountRepository,
                symbolRegistry,
                symbolPriceProvider,
                orderRepository,
                tradeRepository
        );
    }

/*
    - 체결 결과 출력
      - [체결] <종목> <체결가> x <수량> (BUY:<계좌>, SELL:<계좌>)
            - `[체결] AAPL 115 x30 (BUY:A1, SELL:A2)`
    - 각 계좌 별 잔액 및 포지션 요약 평가 손익, 수익률 출력
      - `계좌 <계좌ID> : 현금 <현금잔액>`
            - `/종목 | 보유수량 | 평균 매입가 | 최종 체결가 | 평가손익 | 수익률`
            - ```
            계좌 A1 : 현금 9,996,550
            종목  | 보유수량 | 평균 매입가 | 최종 체결가 | 평가손익 | 수익률
            AAPL | 30     | 115      | 120      | 150    | 0.52%
            GOOG | 10     | 200      | 250      | 500    | 2.00%
            ```
    - 종목별 통계 (체결 건수, 평균 체결가, 최종 체결가) 출력
      - <종목>: 체결 <체결건수>건, 평균체결가 <평균체결가>, 최종체결가 <최종체결가>
      - `AAPL: 체결 10건, 평균체결가 118, 최종체결가 120`
    - 전체 처리 통계 출력 (총 주문수, 체결수, 부분체결수, 취소수)
      - 통계: 총주문 <총주문수>, 체결 <체결수>, 부분체결 <부분체결수>, 취소성공 <취소성공수>
      - `통계: 총주문 6, 체결 4, 부분체결 1, 취소 1`
*/

    // 각 체결에 대한 (종목, 채결가격, 수량, 매수자, 매도자) 검증
    @Test
    @DisplayName("각 체결에 대한 종목, 체결가격, 수량, 매수자, 매도자 검증")
    void tradeTest() {
        // 계좌 초기화
        // given
        Account accountA = Account.create("3333-11-1234567", 1_000_000);
        Account accountB = Account.create("3333-22-1234567", 1_000_000);
        Account accountC = Account.create("3333-33-1234567", 1_000_000);

        Position positionA1 = Position.create(APPL, 10000, 100);
        Position positionA2 = Position.create(GOOG, 10000, 100);
        Positions positionsA = Positions.create(List.of(positionA1, positionA2));
        accountA.initializeSymbolQuantities(positionsA);

        Position positionB1 = Position.create(APPL, 10000, 100);
        Position positionB2 = Position.create(GOOG, 10000, 100);
        Positions positionsB = Positions.create(List.of(positionB1, positionB2));
        accountB.initializeSymbolQuantities(positionsB);

        Position positionC1 = Position.create(APPL, 10000, 100);
        Position positionC2 = Position.create(GOOG, 10000, 100);
        Positions positionsC = Positions.create(List.of(positionC1, positionC2));
        accountC.initializeSymbolQuantities(positionsC);

        accountRepository.add(accountA);
        accountRepository.add(accountB);
        accountRepository.add(accountC);

        Trade trade1 = Trade.create("3333-11-1234567", "3333-22-1234567", APPL, 10000, 10);
        Trade trade2 = Trade.create("3333-11-1234567", "3333-33-1234567", APPL, 10000, 10);
        tradeRepository.add(trade1);
        tradeRepository.add(trade2);

        // when
        List<TradeSummary> summaries = summaryService.summarizeTrades();

        // then 매수자 매도자 종목 가격 수량 검증
        assertThat(summaries).hasSize(2);
        TradeSummary summary1 = summaries.getFirst();
        assertThat(summary1.buyerAccountId()).isEqualTo("3333-11-1234567");
        assertThat(summary1.sellerAccountId()).isEqualTo("3333-22-1234567");
        assertThat(summary1.symbol()).isEqualTo("APPL");
        assertThat(summary1.price()).isEqualTo(10000);
        assertThat(summary1.quantity()).isEqualTo(10);

    }


    // 각 계좌별 잔액 및 포지션 요약 (계좌, 잔액, 종목별 포지션(보유 수량, 평균 매입가, 최종 체결가, 평가 손인, 수익률))
    @Test
    @DisplayName("각 계좌별 잔액 및 포지션 요약 테스트")
    void accountSummaryTest() {
        // 계좌 초기화
        // given
        Account accountA = Account.create("3333-11-1234567", 1_000_000);
        Account accountB = Account.create("3333-22-1234567", 1_000_000);
        Account accountC = Account.create("3333-33-1234567", 1_000_000);

        Position positionA1 = Position.create(APPL, 10000, 100);
        Position positionA2 = Position.create(GOOG, 10000, 100);
        Positions positionsA = Positions.create(List.of(positionA1, positionA2));
        accountA.initializeSymbolQuantities(positionsA);

        Position positionB1 = Position.create(APPL, 10000, 100);
        Position positionB2 = Position.create(GOOG, 10000, 100);
        Positions positionsB = Positions.create(List.of(positionB1, positionB2));
        accountB.initializeSymbolQuantities(positionsB);

        Position positionC1 = Position.create(APPL, 10000, 100);
        Position positionC2 = Position.create(GOOG, 10000, 100);
        Positions positionsC = Positions.create(List.of(positionC1, positionC2));
        accountC.initializeSymbolQuantities(positionsC);

        accountRepository.add(accountA);
        accountRepository.add(accountB);
        accountRepository.add(accountC);

        Trade trade1 = Trade.create("3333-11-1234567", "3333-22-1234567", APPL, 10000, 10);
        Trade trade2 = Trade.create("3333-11-1234567", "3333-33-1234567", APPL, 8000, 10);
        tradeRepository.add(trade1);
        tradeRepository.add(trade2);

        List<AccountSummary> summaries = summaryService.summarizeAccounts();
        assertThat(summaries).hasSize(3);

        AccountSummary summary = summaries.stream()
                .filter(accountSummary -> accountSummary.accountId().equals("3333-11-1234567"))
                .findFirst()
                .orElseThrow();

        assertThat(summary.accountId()).isEqualTo("3333-11-1234567");
        assertThat(summary.funds()).isEqualTo(10000);

        List<PositionSummary> positions = summary.positions();
        PositionSummary appleSummary = positions.stream()
                .filter(positionSummary -> positionSummary.symbol().equals("APPL"))
                .findFirst()
                .orElseThrow();

        assertThat(appleSummary.symbol()).isEqualTo("APPL");
        assertThat(appleSummary.quantity()).isEqualTo(120);
        // 평균 매입가 기존 100개 매입가를 9000원이라고 가정하면
        // 평균 매입가 : 총원가((9000 * 100) + (10000 * 10) + (8000 * 10)) / (보유 수량)120 = 9000원
        assertThat(appleSummary.averageCost()).isEqualTo(9000); // 9000원
        // 마지막 체결가 8000원
        assertThat(appleSummary.lastCost()).isEqualTo(8000);
        // 평가 손익 : (마지막 채결가 - 평균 매입가) * 보유 수량
        // (8000 - 9000) * 120 = -120,000원
        assertThat(appleSummary.profit()).isEqualTo(-120_000);
        // 수익률 : 평가 손익 / (평균매입가 * 보유 수량) * 100
        // -120,000 / (9000 * 120) * 100
        // -120,000 / 10,80,000 = -11,11%
        assertThat(appleSummary.profitRate()).isEqualTo(-11.11);
    }

    // 각 종목 별 통계 검증 (종목, 체결 건수, 평균 체결가, 최종 체결가)
    @Test
    @DisplayName("각 종목별 통계 검증 테스트 ")
    void symbolSummaryTest() {
        Trade trade1 = Trade.create("3333-11-1234567", "3333-22-1234567", APPL, 10000, 10);
        Trade trade2 = Trade.create("3333-11-1234567", "3333-33-1234567", APPL, 8000, 10);
        Trade trade3 = Trade.create("3333-11-1234567", "3333-33-1234567", APPL, 9000, 10);
        Trade trade4 = Trade.create("3333-11-1234567", "3333-33-1234567", APPL, 7000, 10);
        tradeRepository.add(trade1);
        tradeRepository.add(trade2);
        tradeRepository.add(trade3);
        tradeRepository.add(trade4);

        List<SymbolSummary> symbolSummaries = summaryService.summarizeSymbols();
        assertThat(symbolSummaries).hasSize(1);

        SymbolSummary appl = symbolSummaries.stream()
                .filter(symbolSummary -> symbolSummary.symbol().equals("APPL"))
                .findFirst()
                .orElseThrow();

        // 이름, 체결 건수, 평균 체결가, 최종 체결가
        assertThat(appl.symbol()).isEqualTo("APPL");
        assertThat(appl.tradeCount()).isEqualTo(4);
        assertThat(appl.avgPrice()).isEqualTo(8500);
        assertThat(appl.lastPrice()).isEqualTo(7000);
    }


    // 전체 통계 출력 (총 주문 수, 체결 수, 부분 체결 수, 취소 수) 검증
    @Test
    @DisplayName("전체 통계 출력")
    void orderSummaryTest() {
        String orderInput1 = "ORDER 3333-11-1234567 APPL BUY 1000 10";
        String orderInput2 = "ORDER 3333-11-1234567 APPL BUY 1500 10";
        String orderInput3 = "ORDER 3333-22-1234567 APPL SELL 1400 10";
        String orderInput4 = "ORDER 3333-33-1234567 APPL SELL 1300 10";
        OrderRequest orderRequest1 = OrderRequest.create(Arrays.stream(orderInput1.split(" ")).toList());
        OrderRequest orderRequest2 = OrderRequest.create(Arrays.stream(orderInput2.split(" ")).toList());
        OrderRequest orderRequest3 = OrderRequest.create(Arrays.stream(orderInput3.split(" ")).toList());
        OrderRequest orderRequest4 = OrderRequest.create(Arrays.stream(orderInput4.split(" ")).toList());
        Order order1 = Order.create(orderRequest1);
        Order order2 = Order.create(orderRequest2);
        Order order3 = Order.create(orderRequest3);
        Order order4 = Order.create(orderRequest4);
        orderRepository.add(order1);
        orderRepository.add(order2);
        orderRepository.add(order3);
        orderRepository.add(order4);

        order1.cancel();
        order2.decrementRemainingQuantity(10);
        order3.decrementRemainingQuantity(10);

        // 총 주문 수, 체결 수, 부분 체결 수, 대기 수, 취소 수
        OrderSummary orderSummary = summaryService.summarizeOrders();
        assertThat(orderSummary.orderCount()).isEqualTo(4);
        assertThat(orderSummary.completeCount()).isEqualTo(2);
        assertThat(orderSummary.partialTradeCount()).isEqualTo(0);
        assertThat(orderSummary.pendingCount()).isEqualTo(1);
        assertThat(orderSummary.cancelCount()).isEqualTo(1);
    }
}
