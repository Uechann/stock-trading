package stockTrading;

import stockTrading.controller.StockTradingController;
import stockTrading.global.config.DIConfig;

public class Main {
    public static void main(String[] args) {
        DIConfig diConfig = new DIConfig();
        StockTradingController controller = diConfig.stockTradingController();
        controller.run();
    }
}