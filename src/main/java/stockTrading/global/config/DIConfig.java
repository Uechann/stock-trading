package stockTrading.global.config;

import stockTrading.controller.StockTradingController;
import stockTrading.domain.model.Symbols;
import stockTrading.domain.repository.AccountRepository;
import stockTrading.domain.repository.SymbolRegistry;
import stockTrading.domain.service.InitialService;
import stockTrading.global.util.Parser;
import stockTrading.global.util.SymbolParser;
import stockTrading.global.util.SymbolQuantityParser;
import stockTrading.infra.InMemoryAccountRepository;
import stockTrading.view.InputView;
import stockTrading.view.OutputView;

public final class DIConfig {

    private final Symbols symbols = new Symbols();
    private final SymbolRegistry symbolRegistry = new SymbolRegistry(symbols);
    private final AccountRepository accountRepository = new InMemoryAccountRepository();

    public Parser<String> symbolParser() {
        return new SymbolParser();
    }

    public Parser<String> symbolQuantityParser() {
        return new SymbolQuantityParser();
    }

    public Symbols symbols() {
        return symbols;
    }

    public SymbolRegistry symbolRegistry() {
        return symbolRegistry;
    }

    public AccountRepository accountRepository() {
        return accountRepository;
    }

    public InitialService stockTradingService() {
        return new InitialService(
                symbolRegistry(),
                accountRepository(),
                symbolParser(),
                symbolQuantityParser());
    }

    public InputView inputView() {
        return new InputView();
    }

    public OutputView outputView() {
        return new OutputView();
    }

    public StockTradingController stockTradingController() {
        return new StockTradingController(
                stockTradingService(),
                inputView(),
                outputView());
    }
}
