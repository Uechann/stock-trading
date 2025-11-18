package stockTrading.domain.model;

import static stockTrading.global.Exception.ErrorMessage.ACCOUNT_SYMBOL_QUANTITY_NOT_IN_RANGE;

public class Position {
    private Symbol symbol;
    private int quantity;
    private int totalPrice; // 총 매입 원가 금액 (매입가 * 수량)의 합산

    private Position(Symbol symbol, int quantity, int totalPrice) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public static Position create(Symbol symbol, int price, int quantity) {
        validateAccountSymbolQuantity(quantity);
        return new Position(symbol, quantity, price * quantity);
    }

    // TODO: 테스트 코드 작성
    public void applyBuy(Trade trade) {
        quantity += trade.getQuantity();
        totalPrice += trade.getPrice() * trade.getQuantity();
    }

    public void applySell(Trade trade) {
        quantity -= trade.getQuantity();
        // 기존 총 매입가 * 남은 수량 / 기존 수량
        totalPrice = (int) ((double) totalPrice * (double) (quantity - trade.getQuantity()) / quantity);
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void decreaseQuantity(int quantity) {
        this.quantity -= quantity;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public int getQuantity() {
        return quantity;
    }

    // =============== private metohd =====================

    private static void validateAccountSymbolQuantity(int quantity) {
        if (quantity <= 0 || quantity > 10000) {
            throw new IllegalArgumentException(ACCOUNT_SYMBOL_QUANTITY_NOT_IN_RANGE.getMessage());
        }
    }
}

