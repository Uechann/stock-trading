package stockTrading.infra;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stockTrading.domain.model.Order;
import stockTrading.domain.model.Symbol;
import stockTrading.domain.repository.OrderRepository;
import stockTrading.dto.OrderRequest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class InMemoryOrderRepositoryTest {

    private final OrderRepository orderRepository = new InMemoryOrderRepository();

    @Test
    @DisplayName("Order 객체 생성후 id 값 자동으로 저장 되는지 검증하는 테스트 코드")
    void orderSaveTest() {
        // given

        List<String> orderInput = Arrays.stream("ORDER 3333-11-1234567 APPL BUY 1000 10".split(" "))
                .toList();
        OrderRequest orderRequest = OrderRequest.create(orderInput);
        Order order1 = Order.create(orderRequest);
        Order order2 = Order.create(orderRequest);

        // when
        orderRepository.add(order1);
        orderRepository.add(order2);

        // then
        assertThat(order1.getOrderId()).isEqualTo(1);
        assertThat(order2.getOrderId()).isEqualTo(2);
    }
}
