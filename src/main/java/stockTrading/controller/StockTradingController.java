package stockTrading.controller;

import stockTrading.domain.model.Account;
import stockTrading.domain.service.InitialService;
import stockTrading.domain.service.MatchingService;
import stockTrading.domain.service.OrderService;
import stockTrading.global.util.InputValidator;
import stockTrading.view.InputView;
import stockTrading.view.OutputView;

import static stockTrading.global.util.Retry.retryUntilValid;

public class StockTradingController {
    private final InitialService initialService;
    private final OrderService orderService;
    private final InputView inputView;
    private final OutputView outputView;

    public StockTradingController(InitialService initialService, OrderService orderService, InputView inputView, OutputView outputView) {
        this.initialService = initialService;
        this.orderService = orderService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        initialize();
        inputAndStartOrder();
    }

    // ===================== private method ========================

    private void inputAndStartOrder() {
        while (true) {
            String orderInput = retryUntilValid(inputView::inputOrder, InputValidator::validateOrder);
            if (orderInput.equals("END")) {
                break;
            }
            
            orderService.startOrder(orderInput);
        }
    }

    private void initialize() {
        initialSymbols();
        initialAccount();
    }

    private void initialSymbols() {
        String symbolInput = retryUntilValid(inputView::inputSymbols, InputValidator::validateSymbol);
        initialService.createSymbols(symbolInput);
    }

    private void initialAccount() {
        while (true) {
            String accountId = retryUntilValid(inputView::inputAccounts, InputValidator::validateGlobalEmptyOrBlank);
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
