package chap05;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Data
@ToString
public class Trader {
    private final String name;
    private final String city;
}
