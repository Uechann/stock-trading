package stockTrading.domain.model;

public interface SymbolPriceProvider {

    // 종목 시세 저장
    void save(Symbol symbol, int price);

    // 현재 종목 시세 조회
    int getPrice(Symbol symbol);
}
