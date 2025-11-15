package stockTrading.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    public InputView() {}

    public String inputSymbols() {
        System.out.println("거래할 주식 종목을 입력하세요. (예: APPL,GOOG,TSLA)");
        return Console.readLine();
    }

    public String inputAccounts() {
        System.out.println("계좌 번호를 입력하세요. (예: 3333-12-1234567)");
        return Console.readLine();
    }

    public String inputAccountFunds() {
        System.out.println("계좌 초기 자금을 입력하세요. (예: 1000000)");
        return Console.readLine();
    }

    public String inputAccountSymbolQuantity() {
        System.out.println("계좌 초기 종목 보유량을 입력하세요");
        return Console.readLine();
    }

    public String inputOrder() {
        System.out.println("주문을 입력하세요. (예: ORDER 3333-12-1234567 APPL BUY 120 50)");
        return Console.readLine();
    }
}
