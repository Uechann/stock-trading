package stockTrading.domain.model;

import java.time.LocalDateTime;

public class Trade {

    private String buyerAccountId;
    private String sellerAccountId;
    private Symbol symbol;
    private int price;
    private int quantity;
    private LocalDateTime createdAt;

    private Trade(String buyerAccountId, String sellerAccountId, Symbol symbol, int price, int quantity) {
        this.buyerAccountId = buyerAccountId;
        this.sellerAccountId = sellerAccountId;
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
        this.createdAt = LocalDateTime.now();
    }

    public static Trade create(String buyerAccountId, String sellerAccountId, Symbol symbol, int price, int quantity) {
        return new Trade(buyerAccountId, sellerAccountId, symbol, price, quantity);
    }

    public String getBuyerAccountId() {
        return buyerAccountId;
    }

    public String getSellerAccountId() {
        return sellerAccountId;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
