package stockTrading.infra;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stockTrading.domain.model.Order;
import stockTrading.domain.model.Symbol;
import stockTrading.domain.repository.OrderRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class InMemoryOrderRepositoryTest {

    private final OrderRepository orderRepository = new InMemoryOrderRepository();

    @Test
    @DisplayName("Order 객체 생성후 id 값 자동으로 저장 되는지 검증하는 테스트 코드")
    void orderSaveTest() {
        // given
        Order order1 = Order.create(
                "3333-12-1234567",
                new Symbol("APPL"),
                "BUY",
                1000,
                10
        );

        Order order2 = Order.create(
                "3333-12-1234567",
                new Symbol("APPL"),
                "BUY",
                1000,
                10
        );

        // when
        orderRepository.add(order1);
        orderRepository.add(order2);

        // then
        assertThat(order1.getOrderId()).isEqualTo(1);
        assertThat(order2.getOrderId()).isEqualTo(2);
    }
}
