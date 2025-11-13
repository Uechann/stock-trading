package stockTrading.global.util;

public class SymbolParser implements Parser {

    private final String symbolDelimiter = ",";

    @Override
    public String[] parse(String input) {
        return input.split(symbolDelimiter);
    }
}
