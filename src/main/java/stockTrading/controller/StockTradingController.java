package stockTrading.controller;

import stockTrading.domain.model.Account;
import stockTrading.domain.service.InitialService;
import stockTrading.domain.service.OrderService;
import stockTrading.domain.service.SummaryService;
import stockTrading.dto.Summary;
import stockTrading.global.util.InputValidator;
import stockTrading.view.InputView;
import stockTrading.view.OutputView;

import static stockTrading.global.util.Retry.retry;

public class StockTradingController {
    private final InitialService initialService;
    private final OrderService orderService;
    private final SummaryService summaryService;
    private final InputView inputView;
    private final OutputView outputView;

    public StockTradingController(InitialService initialService, OrderService orderService,
                                  SummaryService summaryService, InputView inputView, OutputView outputView) {
        this.initialService = initialService;
        this.orderService = orderService;
        this.summaryService = summaryService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        initialize();
        inputAndStartOrder();
        outputSummary();
    }

// ===================== private method ========================

    private void outputSummary() {
        Summary summary = summaryService.summarize();
        outputView.printSummary(summary);
    }

    private void inputAndStartOrder() {
        while (true) {
            String input = retry(() -> {
                String inputOrder = inputView.inputOrder();
                if (inputOrder.equals("END")) {
                    return "END";
                }
                InputValidator.validateOrder(inputOrder);
                orderService.startOrder(inputOrder);
                return "ORDER";
            });

            if (input.equals("END")) {
                break;
            }
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
            Account account = retry(() -> {
                String inputAccounts = inputView.inputAccounts();
                if (inputAccounts.equals("NEXT")) {
                    return null;
                }

                String inputFunds = inputView.inputAccountFunds();
                InputValidator.validateGlobalEmptyOrBlank(inputAccounts);
                InputValidator.validateFunds(inputFunds);
                return initialService.createAccountWithFunds(inputAccounts, inputFunds);
            });

            if (account == null) {
                break;
            }

            retry(() -> {
                String accountSymbols = inputView.inputAccountSymbolQuantity();
                InputValidator.validateSymbolQuantity(accountSymbols);
                initialService.initializeSymbolQuantity(account, accountSymbols);
                return true;
            });
        }
    }
}
