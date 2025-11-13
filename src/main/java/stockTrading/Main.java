package stockTrading;

import stockTrading.controller.StockTradingController;
import stockTrading.domain.service.StockTradingService;
import stockTrading.global.util.SymbolParser;
import stockTrading.global.util.SymbolQuantityParser;
import stockTrading.view.InputView;
import stockTrading.view.OutputView;

public class Main {
    public static void main(String[] args) {
        StockTradingController controller = new StockTradingController(
                new StockTradingService(new SymbolParser(), new SymbolQuantityParser()),
                new InputView(),
                new OutputView()
        );

        controller.run();
    }
}