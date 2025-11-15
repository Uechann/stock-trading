package stockTrading.domain.model;

public class Order {

    private Long orderId;
    private String accountId;
    private Symbol symbol;
    private String side;
    private int price;
    private int quantity;

    private Order(String accountId, Symbol symbol, String side, int price, int quantity) {
        this.accountId = accountId;
        this.symbol = symbol;
        this.side = side;
        this.price = price;
        this.quantity = quantity;
    }

    public static Order of(String accountId, Symbol symbol, String side, int price, int quantity) {
        return new Order(accountId, symbol, side, price, quantity);
    }

    public String getAccount() {
        return accountId;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public String getSide() {
        return side;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
