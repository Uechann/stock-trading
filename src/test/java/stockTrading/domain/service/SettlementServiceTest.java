package stockTrading.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stockTrading.domain.model.*;
import stockTrading.domain.repository.AccountRepository;
import stockTrading.infra.InMemoryAccountRepository;
import stockTrading.infra.InMemorySymbolPrice;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SettlementServiceTest {

    private final AccountRepository accountRepository = new InMemoryAccountRepository();
    private final SymbolPriceProvider symbolPriceProvider = new InMemorySymbolPrice();
    private final SettlementService settlementService = new SettlementService(accountRepository, symbolPriceProvider);

    @BeforeEach
    void setUp() {
        // given
        Account account1 = Account.create("3333-11-1234567", 10_000);
        Account account2 = Account.create("3333-22-1234567", 10_000);
        accountRepository.add(account1);
        accountRepository.add(account2);

        Symbol APPL = new Symbol("APPL");
        Symbol GOOG = new Symbol("GOOG");

        Positions positionsA = new Positions();
        Position positionA1 = Position.create(APPL, 10000, 10);
        Position positionA2 = Position.create(GOOG, 10000, 10);
        positionsA.add(positionA1);
        positionsA.add(positionA2);

        Positions positionsB = new Positions();
        Position positionB1 = Position.create(APPL, 10000, 10);
        Position positionB2 = Position.create(GOOG, 10000, 10);
        positionsB.add(positionB1);
        positionsB.add(positionB2);

        account1.initializeSymbolQuantities(positionsA);
        account2.initializeSymbolQuantities(positionsB);
    }

    // 매수자 매도자 현금 잔액과 종목 보유량이 적절하게 정산 되었는지 테스트
    @Test
    @DisplayName("매수자 매도자의 현금 잔액과 종목 보유량이 적절하게 정산 되었는데 성공 테스트 ")
    void settlementSuccessTest() {
        // given
        Trade trade = Trade.create(
                "3333-11-1234567",
                "3333-22-1234567",
                new Symbol("APPL"),
                1000,
                5
        );

        settlementService.settle(List.of(trade));

        Optional<Account> account1 = accountRepository.fingById("3333-11-1234567");
        Optional<Account> account2 = accountRepository.fingById("3333-22-1234567");

        assertThat(account1.get().getFunds()).isEqualTo(5_000);
        assertThat(account1.get().getQuantity(new Symbol("APPL"))).isEqualTo(15);

        assertThat(account2.get().getFunds()).isEqualTo(15_000);
        assertThat(account2.get().getQuantity(new Symbol("APPL"))).isEqualTo(5);
    }

    // 매수자의 현금 잔액이 음수가 될 경우 예외 처리
    @Test
    @DisplayName("매수자의 현금 잔액이 음수가 될 경우 예외 처리")
    void buyerAccountFundsIsNotEnough() {

        // given
        Trade trade = Trade.create(
                "3333-11-1234567",
                "3333-22-1234567",
                new Symbol("APPL"),
                10000,
                10
        );

        // then
        assertThatThrownBy(() ->
                settlementService.settle(List.of(trade))
        ).isInstanceOf(IllegalArgumentException.class);
    }


    // 매도자의 주식 보유 수량이 음수가 될 경우 예외 처리
    @Test
    @DisplayName("매도자의 주식 보유 수량이 음수가 될 경우 예외 처리")
    void sellerAccountSymbolQuantityIsNotEnough() {

        // given
        Trade trade = Trade.create(
                "3333-11-1234567",
                "3333-22-1234567",
                new Symbol("APPL"),
                10,
                20
        );

        // then
        assertThatThrownBy(() ->
                settlementService.settle(List.of(trade))
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
