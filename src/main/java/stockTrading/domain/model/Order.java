package stockTrading.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class Order {

    private final UUID orderId;
    private final String accountId;
    private final Symbol symbol;
    private final String side;
    private final int price;
    private final int quantity;
    private int remainingQuantity;
    private OrderStatus status;
    private final LocalDateTime orderDate;

    private Order(UUID orderId, String accountId, Symbol symbol, String side, int price, int quantity, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.accountId = accountId;
        this.symbol = symbol;
        this.side = side;
        this.price = price;
        this.quantity = quantity;
        this.remainingQuantity = quantity;
        this.status = OrderStatus.PENDING;
        this.orderDate = orderDate;
    }

    public int getRemainingQuantity() {
        return remainingQuantity;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public static Order create(String accountId, Symbol symbol, String side, int price, int quantity) {
        UUID orderId = UUID.randomUUID();
        LocalDateTime orderDate = LocalDateTime.now();
        return new Order(orderId, accountId, symbol, side, price, quantity, orderDate);
    }

    public UUID getOrderId() {
        return orderId;
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
