package chap05;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TransactionTest {

    Trader minho = new Trader("Minho", "Seoul");
    Trader minsu = new Trader("Minsu", "Busan");
    Trader minchul = new Trader("Minchul", "Daegu");
    Trader cheolsu = new Trader("Cheolsu", "Seoul");

    List<Transaction> transactions = Arrays.asList(
        new Transaction(minho, 2021, 300),
        new Transaction(minsu, 2022, 1000),
        new Transaction(minsu, 2021, 400),
        new Transaction(minchul, 2022, 710),
        new Transaction(minchul, 2022, 700),
        new Transaction(minho, 2022, 950),
        new Transaction(cheolsu, 2022, 150),
        new Transaction(minho, 2022, 1300)
    );

    @Test
    void valueAscending() {
        List<Transaction> ascendingList = transactions.stream()
                .filter(transaction -> transaction.getYear() == 2021)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());

        ascendingList.forEach(System.out::println);
    }

    @Test
    void cityDistinct() {
        List<String> cities = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());
        Assertions.assertEquals(cities.size(), 3);
    }

    @Test
    void traderCity() {
        List<Trader> seoulTraders = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Seoul"))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());

        System.out.println(seoulTraders);
    }

    @Test
    void fetchSeoulValues() {
        transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Seoul"))
                .map(Transaction::getValue)
                .forEach(System.out::println);
    }

    @Test
    void primitiveStream() {
        int sumGt200 = transactions.stream()
                .filter(t->t.getValue() > 200)
                .mapToInt(Transaction::getValue)
                .sum();
        System.out.println(sumGt200);
    }

    @Test
    void restoreObjectStream() {
        IntStream intStream = transactions.stream()
                .filter(t->t.getValue() > 200)
                .mapToInt(Transaction::getValue);
        Stream<Integer> stream = intStream.boxed();
    }

}
