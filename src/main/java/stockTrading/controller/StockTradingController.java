package stockTrading.controller;

import stockTrading.domain.model.AccountSymbols;
import stockTrading.domain.model.Symbols;
import stockTrading.domain.service.StockTradingService;
import stockTrading.global.util.InputValidator;
import stockTrading.view.InputView;
import stockTrading.view.OutputView;

import static stockTrading.global.util.Retry.retryUntilValid;
import static stockTrading.global.util.Retry.retryUntilValidWithNoValidator;

public class StockTradingController {
    private final StockTradingService stockTradingService;
    private final InputView inputView;
    private final OutputView outputView;

    public StockTradingController(
            StockTradingService stockTradingService,
            InputView inputView, OutputView outputView) {
        this.stockTradingService = stockTradingService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        // 주식 종목 초기화
        String symbolInput = retryUntilValid(inputView::inputSymbols, InputValidator::validateSymbol);
        Symbols symbols = stockTradingService.createSymbols(symbolInput);

        // 계좌 번호 초기화 -> 초기 자금 초기화 -> 계좌별 종목 보유량 초기화 (반복 -> until NEXT)
        while(true) {
            String accountId = retryUntilValidWithNoValidator(inputView::inputAccounts);
            if (accountId.equals("NEXT")) {
                break;
            }
            String accountFunds = retryUntilValid(inputView::inputAccountFunds, InputValidator::validateFunds);
            String accountSymbols = retryUntilValid(inputView::inputAccountSymbolQuantity, InputValidator::validateSymbolQuantity);
            AccountSymbols account = stockTradingService.createAccount(symbols, accountId, accountFunds, accountSymbols);
        }
    }

}
