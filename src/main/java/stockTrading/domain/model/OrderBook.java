package stockTrading.domain.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class OrderBook {
    private final Symbol symbol;
    private final PriorityQueue<Order> buyOrders;
    private final PriorityQueue<Order> sellOrders;

    private static final Comparator<Order> BUY_ORDER_COMPARATOR =
            Comparator.comparing(Order::getPrice, Comparator.reverseOrder())
                    .thenComparing(Order::getOrderDate);
    private static final Comparator<Order> SELL_ORDER_COMPARATOR =
            Comparator.comparing(Order::getPrice)
                    .thenComparing(Order::getOrderDate);

    private OrderBook(Symbol symbol) {
        this.symbol = symbol;
        this.buyOrders = new PriorityQueue<>(BUY_ORDER_COMPARATOR);
        this.sellOrders = new PriorityQueue<>(SELL_ORDER_COMPARATOR);
    }

    public static OrderBook create(Symbol symbol) {
        return new OrderBook(symbol);
    }

    public void add(Order order) {
        if (order.getSide().equals("BUY")) {
            buyOrders.add(order);
        }

        if (order.getSide().equals("SELL")) {
            sellOrders.add(order);
        }
    }

    public List<Trade> match() {
        List<Trade> trades = new ArrayList<>();
        while(!buyOrders.isEmpty() && !sellOrders.isEmpty()) {
            Order buyOrder = buyOrders.peek();
            Order sellOrder = sellOrders.peek();

            // 교차 가격이 맞지 않을 경우 종료
            if (buyOrder.getPrice() < sellOrder.getPrice()) {
                break;
            }

            int priceResult = sellOrder.getPrice();
            int quantityResult = Math.min(buyOrder.getQuantity(), sellOrder.getQuantity());

            trades.add(Trade.create(
                    buyOrder.getAccount(),
                    sellOrder.getAccount(),
                    symbol,
                    priceResult,
                    quantityResult));

            buyOrder.decrementRemainingQuantity(quantityResult);
            sellOrder.decrementRemainingQuantity(quantityResult);
            if (buyOrder.isCompleted()) {
                buyOrders.poll();
            }
            if (sellOrder.isCompleted()) {
                sellOrders.poll();
            }
        }
        return trades;
    }

    public Symbol getSymbol() {
        return symbol;
    }
}
