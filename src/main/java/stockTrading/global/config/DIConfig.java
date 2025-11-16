package stockTrading.global.config;

import stockTrading.controller.StockTradingController;
import stockTrading.domain.model.Symbols;
import stockTrading.domain.repository.AccountRepository;
import stockTrading.domain.repository.OrderRepository;
import stockTrading.domain.repository.SymbolRegistry;
import stockTrading.domain.service.InitialService;
import stockTrading.domain.service.MatchingService;
import stockTrading.domain.service.OrderService;
import stockTrading.domain.service.OrderValidator;
import stockTrading.global.util.OrderParser;
import stockTrading.global.util.Parser;
import stockTrading.global.util.SymbolParser;
import stockTrading.global.util.SymbolQuantityParser;
import stockTrading.infra.InMemoryAccountRepository;
import stockTrading.infra.InMemoryOrderRepository;
import stockTrading.view.InputView;
import stockTrading.view.OutputView;

public final class DIConfig {

    private final Symbols symbols = new Symbols();
    private final AccountRepository accountRepository = new InMemoryAccountRepository();
    private final SymbolRegistry symbolRegistry = new SymbolRegistry(symbols);
    private final OrderRepository orderRepository = new InMemoryOrderRepository();

    public Parser<String> symbolParser() {
        return new SymbolParser();
    }

    public Parser<String> symbolQuantityParser() {
        return new SymbolQuantityParser();
    }

    public Parser<String> OrderParser() {
        return new OrderParser();
    }

    public SymbolRegistry symbolRegistry() {
        return symbolRegistry;
    }

    public AccountRepository accountRepository() {
        return accountRepository;
    }

    public OrderRepository orderRepository() {
        return orderRepository;
    }

    public OrderValidator orderValidator() {
        return new OrderValidator();
    }

    public InitialService initialService() {
        return new InitialService(
                symbolRegistry(),
                accountRepository(),
                symbolParser(),
                symbolQuantityParser());
    }

    public OrderService orderService() {
        return new OrderService(
                accountRepository(),
                symbolRegistry(),
                orderRepository(),
                orderValidator(),
                OrderParser()
        );
    }

    public MatchingService matchingService() {
        return new MatchingService();
    }

    public InputView inputView() {
        return new InputView();
    }

    public OutputView outputView() {
        return new OutputView();
    }

    public StockTradingController stockTradingController() {
        return new StockTradingController(
                initialService(),
                orderService(),
                matchingService(),
                inputView(),
                outputView());
    }
}
