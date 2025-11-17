package stockTrading.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stockTrading.domain.model.*;
import stockTrading.domain.repository.AccountRepository;
import stockTrading.dto.OrderRequest;
import stockTrading.infra.InMemoryAccountRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class OrderValidatorTest {

    private AccountRepository accountRepository;
    private OrderValidator orderValidator;

    @BeforeEach
    void setUp() {
        // given
        accountRepository = new InMemoryAccountRepository();
        Account accountA = Account.create("3333-11-1234567", 4_000_000);
        Account accountB = Account.create("3333-22-1234567", 4_000_000);
        accountRepository.add(accountA);
        accountRepository.add(accountB);

        AccountSymbols accountSymbolsA = new AccountSymbols();
        AccountSymbol accountSymbolA1 = AccountSymbol.create(new Symbol("APPL"), 100);
        AccountSymbol accountSymbolA2 = AccountSymbol.create(new Symbol("GOOG"), 100);
        accountSymbolsA.add(accountSymbolA1);
        accountSymbolsA.add(accountSymbolA2);
        accountA.initializeSymbolQuantities(accountSymbolsA);

        AccountSymbols accountSymbolsB = new AccountSymbols();
        AccountSymbol accountSymbol1B1 = AccountSymbol.create(new Symbol("APPL"), 100);
        AccountSymbol accountSymbol1B2 = AccountSymbol.create(new Symbol("GOOG"), 100);
        accountSymbolsB.add(accountSymbol1B1);
        accountSymbolsB.add(accountSymbol1B2);
        accountB.initializeSymbolQuantities(accountSymbolsB);

        orderValidator = new OrderValidator();
    }

    // 가격이 범위에 맞지 않을 경우 예외 처리
    @Test
    @DisplayName("가격이 음수나 0일 경우, 한도 1,000,000 초과 시 예외처리")
    void PriceRangeZeroTest() {
        OrderRequest orderRequest = new OrderRequest(
                "3333-11-1234567",
                "TSLA",
                "BUY",
                0,
                50);

        Account account = accountRepository.fingById(orderRequest.accountId())
                .orElse(null);

        assertThatThrownBy(() ->
                orderValidator.validate(account, new Symbol("APPL"), orderRequest.side(), orderRequest.price(), orderRequest.quantity())
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("가격이 음수나 0일 경우, 한도 1,000,000 초과 시 예외처리")
    void rPriceRangNegativeTest() {
        OrderRequest orderRequest = new OrderRequest(
                "3333-11-1234567",
                "TSLA",
                "BUY",
                -100,
                50);

        Account account = accountRepository.fingById(orderRequest.accountId())
                .orElse(null);

        assertThatThrownBy(() ->
                orderValidator.validate(account, new Symbol("APPL"), orderRequest.side(), orderRequest.price(), orderRequest.quantity())
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("가격이 음수나 0일 경우, 한도 1,000,000 초과 시 예외처리")
    void PriceRangeOverTest() {
        OrderRequest orderRequest = new OrderRequest(
                "3333-11-1234567",
                "TSLA",
                "BUY",
                2_000_000,
                50);

        Account account = accountRepository.fingById(orderRequest.accountId())
                .orElse(null);

        assertThatThrownBy(() ->
                orderValidator.validate(account, new Symbol("APPL"), orderRequest.side(), orderRequest.price(), orderRequest.quantity())
        ).isInstanceOf(IllegalArgumentException.class);
    }

    // 수량이 0이나 음수일 경우 예외처리
    @Test
    @DisplayName("수량이 0이나 음수, 한도 10,000 초과시 예외처리")
    void quantityRangeZeroTest() {
        OrderRequest orderRequest = new OrderRequest(
                "3333-11-1234567",
                "TSLA",
                "BUY",
                100,
                0);

        Account account = accountRepository.fingById(orderRequest.accountId())
                .orElse(null);

        assertThatThrownBy(() ->
                orderValidator.validate(account, new Symbol("APPL"), orderRequest.side(), orderRequest.price(), orderRequest.quantity())
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("수량이 0이나 음수, 한도 10,000 초과시 예외처리")
    void quantityRangeNegativeTest() {
        OrderRequest orderRequest = new OrderRequest(
                "3333-11-1234567",
                "TSLA",
                "BUY",
                100,
                -100);

        Account account = accountRepository.fingById(orderRequest.accountId())
                .orElse(null);

        assertThatThrownBy(() ->
                orderValidator.validate(account, new Symbol("APPL"), orderRequest.side(), orderRequest.price(), orderRequest.quantity())
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("수량이 0이나 음수, 한도 10,000 초과시 예외처리")
    void quantityRangeOverTest() {
        OrderRequest orderRequest = new OrderRequest(
                "3333-11-1234567",
                "TSLA",
                "BUY",
                100,
                11_000);

        Account account = accountRepository.fingById(orderRequest.accountId())
                .orElse(null);

        assertThatThrownBy(() ->
                orderValidator.validate(account, new Symbol("APPL"), orderRequest.side(), orderRequest.price(), orderRequest.quantity())
        ).isInstanceOf(IllegalArgumentException.class);
    }

    // 가격 X 수량이 10,000,000 초과 시 예외 처리
    @Test
    @DisplayName("가격 X 수량이 10,000,000 초과 시 예외 처리")
    void totalPriceRangeTest() {
        OrderRequest orderRequest = new OrderRequest(
                "3333-11-1234567",
                "APPL",
                "BUY",
                10_000,
                3_500);

        Account account = accountRepository.fingById(orderRequest.accountId())
                .orElse(null);

        assertThatThrownBy(() ->
                orderValidator.validate(account, new Symbol("APPL"), orderRequest.side(), orderRequest.price(), orderRequest.quantity())
        ).isInstanceOf(IllegalArgumentException.class);
    }

    // 매수 주문 시 계좌 현금이 부족할 경우 예외 처리
    @Test
    @DisplayName("매수 주문 시 계좌 현금이 부족할 경우 예외 처리")
    void accountFundUnderAtButOrderTest() {
        OrderRequest orderRequest = new OrderRequest(
                "3333-11-1234567",
                "APPL",
                "BUY",
                1_000,
                5_000);

        Account account = accountRepository.fingById(orderRequest.accountId())
                .orElse(null);

        assertThatThrownBy(() ->
                orderValidator.validate(account, new Symbol("APPL"), orderRequest.side(), orderRequest.price(), orderRequest.quantity())
        ).isInstanceOf(IllegalArgumentException.class);
    }

    // 매도 주문 시 계좌의 보유 수량이 부족할 경우 예외 처리
    @Test
    @DisplayName("매도 주문 시 계좌의 보유 수량이 부족할 경우 예외 처리")
    void symbolQuantityUnderAtSellOrderTest() {
        OrderRequest orderRequest = new OrderRequest(
                "3333-11-1234567",
                "TSLA",
                "SELL",
                100,
                200);

        Account account = accountRepository.fingById(orderRequest.accountId())
                .orElse(null);

        assertThatThrownBy(() ->
                orderValidator.validate(account, new Symbol("APPL"), orderRequest.side(), orderRequest.price(), orderRequest.quantity())
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
