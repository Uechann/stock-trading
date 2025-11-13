package stockTrading.domain.service;

import stockTrading.domain.model.*;
import stockTrading.global.util.Parser;

import java.util.List;

public class StockTradingService {

    private final Parser<String> parser;


    public StockTradingService(Parser<String> parser) {
        this.parser = parser;
    }

    // 종목 생성
    public Symbols createSymbols(String symbols) {
        List<String> symmbolList = parser.parse(symbols);

        return Symbols.of(symmbolList.stream()
                .map(Symbol::new)
                .toList());
    }

    // 계좌 생성
    public Account createAccount(String accountId, String accountFunds, String accountSymbols) {

        // 계좌 생성
        Account account = new Account(accountId);

        // 초기 자금 초기화
        account.initializeFunds(Integer.parseInt(accountFunds));

        // 종목 보유량 초기화



        return null;
    }


}
