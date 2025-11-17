package stockTrading.dto;

import java.util.List;

import static stockTrading.global.Exception.ErrorMessage.ORDER_REQUEST_SIZE_NOT_MATCH;

public record OrderRequest(
        String accountId,
        String symbol,
        String side,
        int price,
        int quantity
) {

    public static OrderRequest create(List<String> orderInput) {
        validateSize(orderInput);
        return new OrderRequest(
                orderInput.get(1),
                orderInput.get(2),
                orderInput.get(3),
                Integer.parseInt(orderInput.get(4)),
                Integer.parseInt(orderInput.get(5))
        );
    }

    private static void validateSize(List<String> orderInput) {
        if (orderInput.size() != 6) {
            throw new IllegalArgumentException(ORDER_REQUEST_SIZE_NOT_MATCH.getMessage());
        }
    }

    @Override
    public String accountId() {
        return accountId;
    }

    @Override
    public String symbol() {
        return symbol;
    }

    @Override
    public String side() {
        return side;
    }

    @Override
    public int price() {
        return price;
    }

    @Override
    public int quantity() {
        return quantity;
    }
}
