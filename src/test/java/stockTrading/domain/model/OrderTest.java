package stockTrading.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stockTrading.domain.repository.AccountRepository;
import stockTrading.domain.repository.SymbolRegistry;
import stockTrading.infra.InMemoryAccountRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class OrderTest {

    private AccountRepository accountRepository;
    private SymbolRegistry symbolRegistry;


    @BeforeEach
    void setUp() {
        // given
        accountRepository = new InMemoryAccountRepository();
        Account accountA = new Account("3333-11-1234567", 4_000_000);
        Account accountB = new Account("3333-22-1234567", 4_000_000);
        accountRepository.add(accountA);
        accountRepository.add(accountB);

        Symbols symbols = new Symbols();
        symbols.add(new Symbol("AAPL"));
        symbols.add(new Symbol("GOOG"));
        symbolRegistry = new SymbolRegistry(symbols);

        AccountSymbols accountSymbolsA = new AccountSymbols();
        AccountSymbol accountSymbolA1 = AccountSymbol.of(new Symbol("APPL"), 100);
        AccountSymbol accountSymbolA2 = AccountSymbol.of(new Symbol("GOOG"), 100);
        accountSymbolsA.add(accountSymbolA1);
        accountSymbolsA.add(accountSymbolA2);
        accountA.initializeSymbolQuantities(accountSymbolsA);

        AccountSymbols accountSymbolsB = new AccountSymbols();
        AccountSymbol accountSymbol1B1 = AccountSymbol.of(new Symbol("APPL"), 100);
        AccountSymbol accountSymbol1B2 = AccountSymbol.of(new Symbol("GOOG"), 100);
        accountSymbolsB.add(accountSymbol1B1);
        accountSymbolsB.add(accountSymbol1B2);
        accountB.initializeSymbolQuantities(accountSymbolsB);

        OrderValidator orderValidator = new OrderValidator();
    }

    @Test
    @DisplayName("주문이 해당 입력 대로 생성되는지 검증하는 성공 테스트")
    void orderSuccessTest() {
        OrderRequset orderRequset = new OrderRequest(
                "3333-12-1234567",
                "APPL",
                "BUY",
                150,
                50);

        Order order = Order.of(orderRequset);
        assertThat(order).isNotNull();
        assertThat(order.getAccountId()).isEqualTo("3333-12-1234567");
        assertThat(order.getSymolName()).isEqualTo("APPL");
        assertThat(order.getSide()).isEqualTo("SELL");
        assertThat(order.getPrice()).isEqualTo(150);
        assertThat(order.getQuantity()).isEqualTo(50);
    }

    //============= 비즈니스 규칙 ================

    // 존재하지 않는 계좌 입력 예외
    @Test
    @DisplayName("존재하지 않는 계좌 입력 예외")
    void notExistAccountTest() {
        OrderRequset orderRequset = new OrderRequest(
                "3333-12-1234567",
                "APPL",
                "BUY",
                150,
                50);

        assertThatThrownBy(() ->
                orderValidator(orderRequset)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    // 존재하지 않는 종목일 경우 예외 처리
    @Test
    @DisplayName("존재하지 않는 종목일 경우 예외 처리")
    void notExistSymbolTest() {
        OrderRequset orderRequset = new OrderRequest(
                "3333-11-1234567",
                "TSLA",
                "BUY",
                150,
                50);

        assertThatThrownBy(() ->
                orderValidator(orderRequset)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    // BUY, SELL 이외의 주문일 경우 예외 처리
    @Test
    @DisplayName("BUY, SELL 이외의 주문일 경우 예외 처리")
    void notAllowedOrderSideTest() {
        OrderRequset orderRequset = new OrderRequest(
                "3333-11-1234567",
                "TSLA",
                "RES",
                150,
                50);

        assertThatThrownBy(() ->
                orderValidator(orderRequset)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    // 가격이 범위에 맞지 않을 경우 예외 처리
    @Test
    @DisplayName("가격이 음수나 0일 경우, 한도 1,000,000 초과 시 예외처리")
    void PriceRangeTest() {
        OrderRequset orderRequset = new OrderRequest(
                "3333-11-1234567",
                "TSLA",
                "BUY",
                0,
                50);

        assertThatThrownBy(() ->
                orderValidator(orderRequset)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("가격이 음수나 0일 경우, 한도 1,000,000 초과 시 예외처리")
    void PriceRangeTest() {
        OrderRequset orderRequset = new OrderRequest(
                "3333-11-1234567",
                "TSLA",
                "BUY",
                -100,
                50);

        assertThatThrownBy(() ->
                orderValidator(orderRequset)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("가격이 음수나 0일 경우, 한도 1,000,000 초과 시 예외처리")
    void PriceRangeTest() {
        OrderRequset orderRequset = new OrderRequest(
                "3333-11-1234567",
                "TSLA",
                "BUY",
                2_000_000,
                50);

        assertThatThrownBy(() ->
                orderValidator(orderRequset)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    // 수량이 0이나 음수일 경우 예외처리
    @Test
    @DisplayName("수량이 0이나 음수, 한도 10,000 초과시 예외처리")
    void quantityRangeTest() {
        OrderRequset orderRequset = new OrderRequest(
                "3333-11-1234567",
                "TSLA",
                "BUY",
                100,
                0);

        assertThatThrownBy(() ->
                orderValidator(orderRequset)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("수량이 0이나 음수, 한도 10,000 초과시 예외처리")
    void quantityRangeTest() {
        OrderRequset orderRequset = new OrderRequest(
                "3333-11-1234567",
                "TSLA",
                "BUY",
                100,
                -100);

        assertThatThrownBy(() ->
                orderValidator(orderRequset)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("수량이 0이나 음수, 한도 10,000 초과시 예외처리")
    void quantityRangeTest() {
        OrderRequset orderRequset = new OrderRequest(
                "3333-11-1234567",
                "TSLA",
                "BUY",
                100,
                11_000);

        assertThatThrownBy(() ->
                orderValidator(orderRequset)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    // 가격 X 수량이 10,000,000 초과 시 예외 처리
    @Test
    @DisplayName("가격 X 수량이 3,000,000 초과 시 예외 처리")
    void totalPriceRangeTest() {
        OrderRequset orderRequset = new OrderRequest(
                "3333-11-1234567",
                "TSLA",
                "BUY",
                1_000,
                3_500);

        assertThatThrownBy(() ->
                orderValidator(orderRequset)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    // 매수 주문 시 계좌 현금이 부족할 경우 예외 처리
    @Test
    @DisplayName("매수 주문 시 계좌 현금이 부족할 경우 예외 처리")
    void accountFundUnderAtButOrderTest() {
        OrderRequset orderRequset = new OrderRequest(
                "3333-11-1234567",
                "TSLA",
                "BUY",
                1_000,
                5_000);

        assertThatThrownBy(() ->
                orderValidator(orderRequset)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    // 매도 주문 시 계좌의 보유 수량이 부족할 경우 예외 처리
    @Test
    @DisplayName("매도 주문 시 계좌의 보유 수량이 부족할 경우 예외 처리")
    void symbolQuantityUnderAtSellOrderTest() {
        OrderRequset orderRequset = new OrderRequest(
                "3333-11-1234567",
                "TSLA",
                "SELL",
                100,
                200);

        assertThatThrownBy(() ->
                orderValidator(orderRequset)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
