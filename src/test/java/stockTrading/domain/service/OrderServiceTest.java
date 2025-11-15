package stockTrading.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stockTrading.domain.model.*;
import stockTrading.domain.repository.AccountRepository;
import stockTrading.domain.repository.SymbolRegistry;
import stockTrading.global.util.OrderParser;
import stockTrading.infra.InMemoryAccountRepository;
import stockTrading.infra.InMemoryOrderRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class OrderServiceTest {

    private OrderService orderService;
    private SymbolRegistry symbolRegistry;
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        // given
        accountRepository = new InMemoryAccountRepository();
        Symbols symbols = new Symbols();
        symbols.add(new Symbol("APPL"));
        symbols.add(new Symbol("GOOG"));
        symbolRegistry = new SymbolRegistry(symbols);

        OrderValidator orderValidator = new OrderValidator();

        orderService = new OrderService(
                accountRepository,
                symbolRegistry,
                new InMemoryOrderRepository(),
                orderValidator,
                new OrderParser()
        );

        Account accountA = new Account("3333-11-1234567", 4_000_000);
        Account accountB = new Account("3333-22-1234567", 4_000_000);
        accountRepository.add(accountA);
        accountRepository.add(accountB);
    }

    @Test
    @DisplayName("존재하지 않는 계좌 입력 예외")
    void notExistAccountTest() {
        String orderInput = "3333-12-1234567 APPL BUY 150 50";
        assertThatThrownBy(() ->
                orderService.createOrder(orderInput)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("존재하지 않는 종목일 경우 예외 처리")
    void notExistSymbolTest() {
        String orderInput = "3333-12-1234567 TSLA BUY 150 50";
        assertThatThrownBy(() ->
                orderService.createOrder(orderInput)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
