package stockTrading.domain.service;

import stockTrading.domain.model.*;
import stockTrading.domain.repository.AccountRepository;
import stockTrading.domain.repository.OrderRepository;
import stockTrading.domain.repository.SymbolRegistry;
import stockTrading.domain.repository.TradeRepository;
import stockTrading.dto.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static stockTrading.global.Exception.ErrorMessage.ACCOUNT_IS_EMPTY;

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
        // 계좌 번호
        List<Account> accounts = findAllAccounts();
        List<AccountSummary> accountSummaries = new ArrayList<>();
        for (Account account : accounts) {

            // 계좌 번호, 가격
            String accountId = account.getId();
            int funds = account.getFunds();

            // positions
            List<PositionSummary> positionSummaries = new ArrayList<>();
            List<Symbol> symbols = symbolRegistry.findAll();
            for (Symbol symbol : symbols) {
                // 종목
                String symbolName = symbol.name();
                // 수량
                int quantity = account.getQuantity(symbol);
                // 평균 매입가
                int avgCost = account.getAvgCost(symbol);
                // 최종 체결가 (현재가)
                int lastPrice = symbolPriceProvider.getPrice(symbol);
                // 평가 손익
                // 평가 손익 : (마지막 채결가 - 평균 매입가) * 보유 수량
                // (8000 - 9000) * 120 = -120,000원
                int profitAndLoss = (lastPrice - avgCost) * quantity;
                // 수익률
                // 수익률 : 평가 손익 / (평균매입가 * 보유 수량) * 100
                // -120,000 / (9000 * 120) * 100
                // -120,000 / 10,80,000 = -11,11%
                double profitRate = ((double) profitAndLoss / (double) (avgCost * quantity)) * 100;
                profitRate = BigDecimal.valueOf(profitRate).setScale(2, RoundingMode.HALF_UP).doubleValue();
                positionSummaries.add(PositionSummary.create(symbolName, quantity, avgCost, lastPrice, profitAndLoss, profitRate));
            }
            accountSummaries.add(AccountSummary.create(accountId, funds, positionSummaries));
        }
        return accountSummaries;
    }

    public List<SymbolSummary> summarizeSymbols() {
        Map<Symbol, List<Trade>> symbolTrades = tradeRepository.findAll().stream()
                .collect(Collectors.groupingBy(Trade::getSymbol));

        List<SymbolSummary> symbolSummaries = new ArrayList<>();
        // 각 종목 별 이름, 체결 건수, 평균 체결가, 최종 체결가 계산
        symbolTrades.forEach((symbol, trades) -> {
            Long count = (long) trades.size();

            // 최종 체결가
            Long lastPrice = (long) trades.stream()
                    .max(Comparator.comparing(Trade::getCreatedAt))
                    .map(Trade::getPrice)
                    .orElse(0);

            double averagePrice = trades.stream()
                    .mapToInt(Trade::getPrice)
                    .average()
                    .orElse(0);

            symbolSummaries.add(SymbolSummary.create(symbol.name(), count, averagePrice, lastPrice));
        });

        return symbolSummaries;
    }

    public OrderSummary summarizeOrders() {
        List<Order> orders = orderRepository.findAll();

        // 총 주문 수, 체결 수, 부분 체결 수, 대기 수, 취소 수
        int totalCount = orders.size();

        int completeCount = (int) orders.stream()
                .filter(Order::isCompleted)
                .count();

        int partialCount = (int) orders.stream()
                .filter(Order::isPartialCompleted)
                .count();

        int pendingCount = (int) orders.stream()
                .filter(Order::isPending)
                .count();

        int cancelCount = (int) orders.stream()
                .filter(Order::isCancelled)
                .count();

        return OrderSummary.create(totalCount, completeCount, partialCount, pendingCount, cancelCount);
    }

    // ================= private method =========================

    private List<Account> findAllAccounts() {
        return accountRepository.findAll()
                .orElseThrow(() -> new IllegalArgumentException(ACCOUNT_IS_EMPTY.getMessage()));
    }
}
