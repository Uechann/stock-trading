package stockTrading.infra;

import stockTrading.domain.model.Order;
import stockTrading.domain.model.Orders;
import stockTrading.domain.repository.OrderRepository;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static stockTrading.global.Exception.ErrorMessage.ID_ALREADY_SET;
import static stockTrading.global.Exception.ErrorMessage.ID_SET_FAIL;

public class InMemoryOrderRepository implements OrderRepository {

    private final Orders orders = new Orders();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public void add(Order order) {
        setIdByReflection(order, sequence.incrementAndGet());
        orders.add(order);
    }

    @Override
    public Optional<Order> findById(long id) {
        return Optional.ofNullable(orders.findById(id));
    }

    @Override
    public List<Order> findAll() {
        return orders.findAll();
    }

    // =============== private method ==================

    /**
     * id 값 자동 설정 리플렉션 내부 method
     * */
    private void setIdByReflection(Order order, Long id) {
        try {
            Field idField = Order.class.getDeclaredField("orderId");
            idField.setAccessible(true);
            Object current = idField.get(order);

            if (current != null) {
                throw new IllegalStateException(ID_ALREADY_SET.getMessage());
            }
            idField.set(order, id);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(ID_SET_FAIL.getMessage());
        }
    }
}
