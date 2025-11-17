package stockTrading.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stockTrading.dto.OrderRequest;

import java.util.Arrays;
import java.util.List;

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

        Order order = Order.create(orderRequest);
        assertThat(order).isNotNull();
        assertThat(order.getAccount()).isEqualTo(orderRequest.accountId());
        assertThat(order.getSymbol()).isEqualTo(symbol);
        assertThat(order.getSide()).isEqualTo("BUY");
        assertThat(order.getPrice()).isEqualTo(150);
        assertThat(order.getQuantity()).isEqualTo(50);
        assertThat(order.getRemainingQuantity()).isEqualTo(50);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PENDING);
    }

    // 주문 취소 시 상태 변경이 되는지 검증하는 테스트
    @Test
    @DisplayName("주문 취소시 상태 변경 되는지 검증하는 테스트")
    void orderCancelTest() {
        // given
        List<String> orderInput = Arrays.stream("ORDER 3333-11-1234567 APPL BUY 1000 10".split(" "))
                .toList();
        OrderRequest orderRequest = OrderRequest.of(orderInput);
        Order order = Order.create(orderRequest);

        // when
        order.cancel();

        // then
        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCELLED);
    }
}
