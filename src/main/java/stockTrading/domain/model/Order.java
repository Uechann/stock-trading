package stockTrading.domain.model;

import stockTrading.dto.OrderRequest;

import java.time.LocalDateTime;

public class Order {

    private Long orderId;
    private final String accountId;
    private final Symbol symbol;
    private final String side;
    private final int price;
    private final int quantity;
    private int remainingQuantity;
    private OrderStatus status;
    private final LocalDateTime orderDate;

    private Order(String accountId, Symbol symbol, String side, int price, int quantity, LocalDateTime orderDate) {
        this.accountId = accountId;
        this.symbol = symbol;
        this.side = side;
        this.price = price;
        this.quantity = quantity;
        this.remainingQuantity = quantity;
        this.status = OrderStatus.PENDING;
        this.orderDate = orderDate;
    }

    public static Order create(OrderRequest orderRequest) {
        LocalDateTime orderDate = LocalDateTime.now();
        return new Order(
                orderRequest.accountId(),
                new Symbol(orderRequest.symbol()),
                orderRequest.side(),
                orderRequest.price(),
                orderRequest.quantity(),
                orderDate);
    }

    public void decrementRemainingQuantity(int quantity) {
        this.remainingQuantity -= quantity;
        if (this.remainingQuantity == 0) {
            this.status = OrderStatus.COMPLETED;
        }
    }

    public boolean isCompleted() {
        return this.status == OrderStatus.COMPLETED;
    }

    public void cancel() {
        this.status = OrderStatus.CANCELLED;
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

    public Long getOrderId() {
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
