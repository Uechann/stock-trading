package stockTrading.domain.service;

import stockTrading.domain.model.*;
import stockTrading.global.util.Parser;

import java.util.List;

public class StockTradingService {

    private final Parser<String> symbolParser;
    private final Parser<String> quantityParser;

    public StockTradingService(Parser<String> symbolParser, Parser<String> quantityParser) {
        this.symbolParser = symbolParser;
        this.quantityParser = quantityParser;
    }

    // 종목 생성
    public Symbols createSymbols(String symbols) {
        List<String> symmbolList = symbolParser.parse(symbols);

        return Symbols.of(symmbolList.stream()
                .map(Symbol::new)
                .toList());
    }

    // 계좌 생성
    public AccountSymbols createAccount(Symbols symbols, String accountId, String accountFunds, String accountSymbols) {
        // 계좌 생성 및 자금 초기화
        Account account = new Account(accountId);
        account.initializeFunds(Integer.parseInt(accountFunds));

        // 종목 보유량 초기화
        AccountSymbols accountSymbols1 = new AccountSymbols();
        List<String> symbolAndQuantity = symbolParser.parse(accountSymbols);

        symbolAndQuantity.stream()
                .map(quantityParser::parse)
                .filter(symbolQuantity -> symbols.contains(symbolQuantity.getFirst()))
                .forEach(symbolQuantity -> {
                    accountSymbols1.add(parseAccountSymbol(symbolQuantity, account));
                });

        return accountSymbols1;
    }

    // 종목과 개수 파싱 후 계좌 종목 개수 클래수 생성 [APPL, 100]
    private AccountSymbol parseAccountSymbol(List<String> symbolquantity, Account account) {
        Symbol symbol = new Symbol(symbolquantity.getFirst());
        int quantity = Integer.parseInt(symbolquantity.get(1));
        return AccountSymbol.of(account, symbol, quantity);
    }
}
