package stockTrading.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stockTrading.domain.repository.AccountRepository;
import stockTrading.domain.repository.SymbolRegistry;
import stockTrading.domain.service.OrderValidator;
import stockTrading.dto.OrderRequest;
import stockTrading.infra.InMemoryAccountRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OrderTest {

    private AccountRepository accountRepository;
    private SymbolRegistry symbolRegistry;
    private OrderValidator orderValidator;

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

        orderValidator = new OrderValidator();
    }

    @Test
    @DisplayName("주문이 해당 입력 대로 생성되는지 검증하는 성공 테스트")
    void orderSuccessTest() {
        OrderRequest orderRequest = new OrderRequest(
                "3333-12-1234567",
                "APPL",
                "BUY",
                150,
                50);

        Symbol symbol = new Symbol("APPL");

        Order order = Order.create(orderRequest.accountId(), symbol, orderRequest.side(), orderRequest.price(), orderRequest.quantity());
        assertThat(order).isNotNull();
        assertThat(order.getAccount()).isEqualTo(orderRequest.accountId());
        assertThat(order.getSymbol()).isEqualTo(symbol);
        assertThat(order.getSide()).isEqualTo("BUY");
        assertThat(order.getPrice()).isEqualTo(150);
        assertThat(order.getQuantity()).isEqualTo(50);
        assertThat(order.getRemainingQuantity()).isEqualTo(50);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PENDING);
    }
}
