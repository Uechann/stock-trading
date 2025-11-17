package stockTrading.controller;

import stockTrading.domain.model.Account;
import stockTrading.domain.service.InitialService;
import stockTrading.domain.service.OrderService;
import stockTrading.global.util.InputValidator;
import stockTrading.view.InputView;
import stockTrading.view.OutputView;

import static stockTrading.global.util.Retry.retry;
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
            retry(() -> {
                orderService.startOrder(orderInput);
                return true;
            });
        }
    }

    private void initialize() {
        initialSymbols();
        initialAccount();
    }

    private void initialSymbols() {
        retry(() -> {
            String symbolInput = inputView.inputSymbols();
            InputValidator.validateSymbol(symbolInput);
            initialService.createSymbols(symbolInput);
            return true;
        });
    }

    private void initialAccount() {
        while (true) {
            String accountId = retry(() -> {
                String inputAccounts = inputView.inputAccounts();
                InputValidator.validateGlobalEmptyOrBlank(inputAccounts);
                return inputAccounts;
            });

            if (accountId.equals("NEXT")) {
                break;
            }

            Account account = retry(() -> {
                String inputFunds = inputView.inputAccountFunds();
                InputValidator.validateFunds(inputFunds);
                return initialService.createAccountWithFunds(accountId, inputFunds);
            });

            retry(() -> {
                String accountSymbols = inputView.inputAccountSymbolQuantity();
                InputValidator.validateSymbolQuantity(accountSymbols);
                initialService.initializeSymbolQuantity(account, accountSymbols);
                return true;
            });
        }
    }
}
