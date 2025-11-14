package stockTrading.global.config;

import stockTrading.controller.StockTradingController;
import stockTrading.domain.service.StockTradingService;
import stockTrading.global.util.Parser;
import stockTrading.global.util.SymbolParser;
import stockTrading.global.util.SymbolQuantityParser;
import stockTrading.view.InputView;
import stockTrading.view.OutputView;

public final class DIConfig {

    public Parser<String> symbolParser() {
        return new SymbolParser();
    }

    public Parser<String> symbolQuantityParser() {
        return new SymbolQuantityParser();
    }

    public StockTradingService stockTradingService() {
        return new StockTradingService(symbolParser(), symbolQuantityParser());
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
