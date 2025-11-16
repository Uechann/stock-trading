package stockTrading.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderBookTest {

    @Test
    @DisplayName("종목 하나의 OrderBook 에서 주문에 대한 매칭이 정상적으로 이루어진다.")
    void matchTest() {
        // given
        // 주문 한개의 OrderBook 한개 생성 후 주문들을 추가
        Symbol apple = new Symbol("APPL");
        OrderBook orderBook = new OrderBook(apple);
        Order order1 = Order.create("3333-11-1234567", apple, "BUY", 10000, 10);
        Order order2 = Order.create("3333-22-1234567", apple, "SELL", 8000, 5);
        orderBook.add(order1);
        orderBook.add(order2);

        // when
        // orderBook.match() 메서드를 실행하여 Trad에 대한 객체 리스트를 반환하여 검증
        List<Trade> result = orderBook.match();

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getBuyerAccountId()).isEqualTo("3333-11-1234567");
        assertThat(result.getFirst().getSellerAccountId()).isEqualTo("3333-22-1234567");
        assertThat(result.getFirst().getSymbol()).isEqualTo(apple);
        assertThat(result.getFirst().getPrice()).isEqualTo(8000);
        assertThat(result.getFirst().getQuantity()).isEqualTo(5);
    }
}
