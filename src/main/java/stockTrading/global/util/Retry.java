package stockTrading.global.util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class Retry {

    private Retry() {}

    public static <T> String retryUntilValid(Supplier<T> supplier, Consumer<String> validator) {
        while (true) {
            try {
                String input = supplier.get().toString();
                validator.accept(input);
                return input;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static <T> String retryUntilValidWithNoValidator(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get().toString();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
