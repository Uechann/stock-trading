package stockTrading.controller;

import stockTrading.domain.model.Account;
import stockTrading.domain.service.InitialService;
import stockTrading.global.util.InputValidator;
import stockTrading.view.InputView;
import stockTrading.view.OutputView;

import static stockTrading.global.util.Retry.retryUntilValid;
import static stockTrading.global.util.Retry.retryUntilValidWithNoValidator;

public class StockTradingController {
    private final InitialService initialService;
    private final InputView inputView;
    private final OutputView outputView;

    public StockTradingController(InitialService initialService, InputView inputView, OutputView outputView) {
        this.initialService = initialService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        initialize();


    }


    // ===================== private method ========================


    private void initialize() {
        initialSymbols();
        initialAccount();
    }

    private void initialSymbols() {
        String symbolInput = retryUntilValid(inputView::inputSymbols, InputValidator::validateSymbol);
        initialService.createSymbols(symbolInput);
    }

    private void initialAccount() {
        while(true) {
            String accountId = retryUntilValidWithNoValidator(inputView::inputAccounts);
            if (accountId.equals("NEXT")) {
                break;
            }
            String accountFunds = retryUntilValid(inputView::inputAccountFunds, InputValidator::validateFunds);
            Account account = initialService.createAccountWithFunds(accountId, accountFunds);

            String accountSymbols = retryUntilValid(inputView::inputAccountSymbolQuantity, InputValidator::validateSymbolQuantity);
            initialService.initializeSymbolQuantity(account, accountSymbols);
        }
    }
}
