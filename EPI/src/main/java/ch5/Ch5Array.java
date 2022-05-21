package ch5;

import java.util.Collections;
import java.util.List;

public class Ch5Array {

    /*
    5.2 임의의 정수값 증가시키기
    십진수 D를 나타낸 배열 A가 주어졌을 떄, D+1의 결과를 다시 배열 A에 갱신
     */
    static List<Integer> plusOne(List<Integer> A) {
        int n = A.size() - 1;
        A.set(n, A.get(n) + 1);

        for(int i = n; i > 0 && A.get(i) == 10; --i) {
            A.set(i, 0);
            A.set(i-1, A.get(i -1) + 1);
        }

        if(A.get(0) == 10) {
            A.set(0, 1);
            A.add(0);
        }
        return A;
    }
}
