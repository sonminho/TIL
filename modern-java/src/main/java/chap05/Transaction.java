package chap05;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Data
@ToString
public class Transaction {
    private final Trader trader;
    private final int year;
    private final int value;
}
