package stockTrading.domain.service;

import stockTrading.domain.model.*;
import stockTrading.domain.repository.AccountRepository;
import stockTrading.domain.repository.OrderRepository;
import stockTrading.domain.repository.SymbolRegistry;
import stockTrading.domain.repository.TradeRepository;
import stockTrading.dto.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SummaryService {

    private final AccountRepository accountRepository;
    private final SymbolRegistry symbolRegistry;
    private final SymbolPriceProvider symbolPriceProvider;
    private final OrderRepository orderRepository;
    private final TradeRepository tradeRepository;

    public SummaryService(AccountRepository accountRepository,
                          SymbolRegistry symbolRegistry,
                          SymbolPriceProvider symbolPriceProvider,
                          OrderRepository orderRepository,
                          TradeRepository tradeRepository) {
        this.accountRepository = accountRepository;
        this.symbolRegistry = symbolRegistry;
        this.symbolPriceProvider = symbolPriceProvider;
        this.orderRepository = orderRepository;
        this.tradeRepository = tradeRepository;
    }

    public Summary summarize() {
        List<TradeSummary> tradeSummaries = summarizeTrades();
        List<AccountSummary> accountSummaries = summarizeAccounts();
        List<SymbolSummary> symbolSummaries = summarizeSymbols();
        OrderSummary orderSummary = summarizeOrders();
        return Summary.create(tradeSummaries, accountSummaries, symbolSummaries, orderSummary);
    }

    public List<TradeSummary> summarizeTrades() {
        List<Trade> trades = tradeRepository.findAll();
        return trades.stream()
                .map(TradeSummary::of)
                .toList();
    }

    public List<AccountSummary> summarizeAccounts() {
        return null;
    }

    public List<SymbolSummary> summarizeSymbols() {
        Map<Symbol, List<Trade>> symbolTrades = tradeRepository.findAll().stream()
                .collect(Collectors.groupingBy(Trade::getSymbol));

        List<SymbolSummary> symbolSummaries = new ArrayList<>();
        // 각 종목 별 이름, 체결 건수, 평균 체결가, 최종 체결가 계산
        symbolTrades.forEach((symbol, trades) -> {
            Long count = (long) trades.size();

            // 최종 체결가
            Long lastPrice = (long)trades.stream()
                    .max(Comparator.comparing(Trade::getPrice))
                    .map(Trade::getPrice)
                    .orElse(0);

            double averagePrice = trades.stream()
                    .mapToInt(Trade::getPrice)
                    .average()
                    .orElse(0);

            symbolSummaries.add(SymbolSummary.create(symbol.getName(), count, averagePrice, lastPrice));
        });

        return symbolSummaries;
    }

    public OrderSummary summarizeOrders() {
        List<Order> orders = orderRepository.findAll();

        // 총 주문 수, 체결 수, 부분 체결 수, 대기 수, 취소 수
        int totalCount = orders.size();

        int completeCount = (int)orders.stream()
                .filter(Order::isCompleted)
                .count();

        int partialCount = (int)orders.stream()
                .filter(Order::isPartialCompleted)
                .count();

        int pendingCount = (int)orders.stream()
                .filter(Order::isPending)
                .count();

        int cancelCount = (int)orders.stream()
                .filter(Order::isCancelled)
                .count();

        return OrderSummary.create(totalCount, completeCount, partialCount, pendingCount, cancelCount);
    }
}
