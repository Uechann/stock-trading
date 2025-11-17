package stockTrading.domain.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Stream;

import static stockTrading.global.Exception.ErrorMessage.ORDER_NOT_FOUND;

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

    public void removeOrder(Long orderId) {
        buyOrders.removeIf(order -> order.getOrderId().equals(orderId));
        sellOrders.removeIf(order -> order.getOrderId().equals(orderId));
    }

    public Order findOrderById(Long orderId) {
        // 매수 큐 매도 큐에서 순회하여 조회
        // 있으면 Order 반환 없으면 예외 처리
        // Stream.concat으로 두개의 큐 stream을 연결해서 순회 후 없으면 예외 처리 -> 반환
        return Stream.concat(buyOrders.stream(), sellOrders.stream())
                .filter(order -> order.getOrderId().equals(orderId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ORDER_NOT_FOUND.getMessage()));
    }

    public Symbol getSymbol() {
        return symbol;
    }
}
