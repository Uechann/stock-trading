package stockTrading.domain.service;

import stockTrading.domain.model.*;
import stockTrading.domain.repository.AccountRepository;
import stockTrading.domain.repository.OrderBookRepository;
import stockTrading.domain.repository.SymbolRegistry;
import stockTrading.global.util.Parser;

import java.util.List;

import static stockTrading.global.Exception.ErrorMessage.SYMBOL_NOT_FOUND;

public class InitialService {

    private final SymbolRegistry symbolRegistry;
    private final AccountRepository accountRepository;
    private final OrderBookRepository orderBookRepository;
    private final Parser<String> symbolParser;
    private final Parser<String> quantityParser;


    public InitialService(SymbolRegistry symbolRegistry, AccountRepository accountRepository,
                          OrderBookRepository orderBookRepository,
                          Parser<String> symbolParser, Parser<String> quantityParser) {
        this.symbolRegistry = symbolRegistry;
        this.accountRepository = accountRepository;
        this.orderBookRepository = orderBookRepository;
        this.symbolParser = symbolParser;
        this.quantityParser = quantityParser;
    }

    // 종목 생성
    public void createSymbols(String symbolsInput) {
        List<String> symbolNames = symbolParser.parse(symbolsInput);
        symbolNames.stream()
                .map(Symbol::new)
                .forEach(symbol -> {
                    symbolRegistry.add(symbol);
                    orderBookRepository.add(OrderBook.create(symbol));
                });
    }

    // 계좌 생성
    public Account createAccountWithFunds(String accountId, String accountFunds) {
        // 계좌 생성 및 자금 초기화
        Account account = Account.create(accountId, Integer.parseInt(accountFunds));
        accountRepository.add(account);
        return account;
    }

    public void initializeSymbolQuantity(Account account, String accountSymbolInput) {
        // 종목 보유량 초기화
        AccountSymbols accountSymbols = new AccountSymbols();
        List<String> symbolAndQuantitys = symbolParser.parse(accountSymbolInput);

        for (String symbolAndQuantity : symbolAndQuantitys) {
            List<String> symbolQuantity = quantityParser.parse(symbolAndQuantity);
            String symbol = symbolQuantity.getFirst();
            String quantity = symbolQuantity.get(1);

            validateSymbolExist(symbolQuantity.getFirst());
            accountSymbols.add(AccountSymbol.create(new Symbol(symbol), Integer.parseInt(quantity)));
            account.initializeSymbolQuantities(accountSymbols);
        }
    }

    // =================== private method ===================

    private void validateSymbolExist(String symbol) {
        if (!symbolRegistry.isContains(new Symbol(symbol))) {
            throw new IllegalArgumentException(SYMBOL_NOT_FOUND.getMessage());
        }
    }
}
