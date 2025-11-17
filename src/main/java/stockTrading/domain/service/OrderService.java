package stockTrading.domain.service;

import stockTrading.domain.model.Account;
import stockTrading.domain.model.Order;
import stockTrading.domain.model.Symbol;
import stockTrading.domain.model.Trade;
import stockTrading.domain.repository.AccountRepository;
import stockTrading.domain.repository.OrderRepository;
import stockTrading.domain.repository.SymbolRegistry;
import stockTrading.dto.OrderRequest;
import stockTrading.global.util.Parser;

import java.util.List;

import static stockTrading.global.Exception.ErrorMessage.*;

public class OrderService {

    private final AccountRepository accountRepository;
    private final SymbolRegistry symbolRegistry;
    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
    private final MatchingService matchingService;
    private final SettlementService settlementService;
    private final Parser<String> parser;

    public OrderService(AccountRepository accountRepository,
                        SymbolRegistry symbolRegistry,
                        OrderRepository orderRepository,
                        OrderValidator orderValidator,
                        MatchingService matchingService,
                        SettlementService settlementService,
                        Parser<String> parser) {
        this.accountRepository = accountRepository;
        this.symbolRegistry = symbolRegistry;
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
        this.matchingService = matchingService;
        this.settlementService = settlementService;
        this.parser = parser;
    }

    public void startOrder(String orderInput) {
        // 주문 취소 기능
        if (orderInput.startsWith("CANCEL")) {
            String orderId = orderInput.split(" ")[1];

            matchingService.cancelOrder(Long.parseLong(orderId));
            return;
        }

        OrderRequest orderRequest = OrderRequest.of(parser.parse(orderInput));

        Account account = findByAccountId(orderRequest);
        Symbol symbol = new Symbol(orderRequest.symbol());
        validateSymbol(symbol);

        orderValidator.validate(
                account,
                symbol,
                orderRequest.side(),
                orderRequest.price(),
                orderRequest.quantity());

        Order order = Order.create(orderRequest.accountId(), symbol, orderRequest.side(), orderRequest.price(), orderRequest.quantity());
        orderRepository.add(order);
        List<Trade> matchResult = matchingService.match(order);
        settlementService.settle(matchResult);
    }

    // ================ private method ==================

    private void validateSymbol(Symbol symbol) {
        if (!symbolRegistry.isContains(symbol)) {
            throw new IllegalArgumentException(SYMBOL_NOT_FOUND.getMessage());
        }
    }

    private Account findByAccountId(OrderRequest orderRequest) {
        return accountRepository.fingById(orderRequest.accountId())
                .orElseThrow(() -> new IllegalArgumentException(ACCOUNT_NOT_FOUND.getMessage()));
    }
}
