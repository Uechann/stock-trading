package stockTrading.global.util;

import java.util.Arrays;
import java.util.List;

public class OrderParser implements Parser<String> {

    private final String ORDER_DELIMITER = " ";

    @Override
    public List<String> parse(String input) {
        return Arrays.stream(input.split(ORDER_DELIMITER))
                .toList();
    }
}
