package stockTrading.global.config;

import stockTrading.controller.StockTradingController;
import stockTrading.domain.model.Symbols;
import stockTrading.domain.repository.*;
import stockTrading.domain.service.*;
import stockTrading.global.util.OrderParser;
import stockTrading.global.util.Parser;
import stockTrading.global.util.SymbolParser;
import stockTrading.global.util.SymbolQuantityParser;
import stockTrading.infra.InMemoryAccountRepository;
import stockTrading.infra.InMemoryOrderBookRepository;
import stockTrading.infra.InMemoryOrderRepository;
import stockTrading.infra.InMemoryTradeRepository;
import stockTrading.view.InputView;
import stockTrading.view.OutputView;

public final class DIConfig {

    private final AccountRepository accountRepository = new InMemoryAccountRepository();
    private final OrderRepository orderRepository = new InMemoryOrderRepository();
    private final OrderBookRepository orderBookRepository = new InMemoryOrderBookRepository();
    private final TradeRepository tradeRepository = new InMemoryTradeRepository();
    private final Symbols symbols = new Symbols();
    private final SymbolRegistry symbolRegistry = new SymbolRegistry(symbols);

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

    public OrderBookRepository orderBookRepository() {
        return orderBookRepository;
    }

    public TradeRepository tradeRepository() {
        return tradeRepository;
    }

    public OrderValidator orderValidator() {
        return new OrderValidator();
    }

    public InitialService initialService() {
        return new InitialService(
                symbolRegistry(),
                accountRepository(),
                orderBookRepository(),
                symbolParser(),
                symbolQuantityParser());
    }

    public TradeService matchingService() {
        return new TradeService(
                orderRepository(),
                orderBookRepository(),
                tradeRepository()
        );
    }

    public SettlementService settlementService() {
        return new SettlementService(accountRepository());
    }

    public OrderService orderService() {
        return new OrderService(
                accountRepository(),
                symbolRegistry(),
                orderRepository(),
                orderValidator(),
                matchingService(),
                settlementService(),
                OrderParser()
        );
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
                inputView(),
                outputView());
    }
}
