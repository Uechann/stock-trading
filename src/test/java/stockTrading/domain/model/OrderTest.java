package stockTrading.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stockTrading.dto.OrderRequest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OrderTest {

    @Test
    @DisplayName("주문이 해당 입력 대로 생성되는지 검증하는 성공 테스트")
    void orderSuccessTest() {
        OrderRequest orderRequest = new OrderRequest(
                "3333-12-1234567",
                "APPL",
                "BUY",
                150,
                50);

        Symbol symbol = new Symbol("APPL");

        Order order = Order.create(orderRequest.accountId(), symbol, orderRequest.side(), orderRequest.price(), orderRequest.quantity());
        assertThat(order).isNotNull();
        assertThat(order.getAccount()).isEqualTo(orderRequest.accountId());
        assertThat(order.getSymbol()).isEqualTo(symbol);
        assertThat(order.getSide()).isEqualTo("BUY");
        assertThat(order.getPrice()).isEqualTo(150);
        assertThat(order.getQuantity()).isEqualTo(50);
        assertThat(order.getRemainingQuantity()).isEqualTo(50);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PENDING);
    }
}
