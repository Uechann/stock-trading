package stockTrading.global.util;

import java.util.Arrays;
import java.util.List;

public class SymbolQuantityParser implements Parser<String> {

    private final String symbolDelimiter = ",";
    private final String quantityDelimiter = "=";

    @Override
    public List<String> parse(String input) {
        return Arrays.stream(input.split(symbolDelimiter))
                .toList();
    }

    public List<String> parseSymbolAndQuantity(String input) {
        return Arrays.stream(input.split(quantityDelimiter))
                .toList();
    }
}
