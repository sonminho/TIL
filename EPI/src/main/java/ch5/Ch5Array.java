package ch5;

import java.util.ArrayList;
import java.util.Arrays;
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

    /*
    5.3 임의의 두 정수값 곱하기
    배열을 정수로 바꾸어 계산하면 오버플로 문제 발생
    길이가 n과 m인 피연산자를 곱한 결괏값의 자릿수는 최대 n+m
     */
    static List<Integer> multiply(List<Integer> num1, List<Integer> num2) {
        final int sign = num1.get(0) < 0 ^ num2.get(0) < 0 ? -1 : 1; // 부호
        num1.set(0, Math.abs(num1.get(0)));
        num2.set(0, Math.abs(num2.get(0)));

        List<Integer> result = new ArrayList<>(Collections.nCopies(num1.size() + num2.size(), 0));

        for(int i = num1.size() - 1; i >= 0; i--) {
            for(int j = num2.size() - 1; j >= 0; j--) {
                result.set(i+j+1, result.get(i+j+1) + num1.get(i) * num2.get(j));
                result.set(i+j, result.get(i+j) + result.get(i+j+1) / 10);
                result.set(i+j+1, result.get(i+j+1) % 10);
            }
        }
        
        // 0 제거
        int zeroIdx = 0;
        while(zeroIdx < result.size() && result.get(zeroIdx) == 0) zeroIdx++;
        result = result.subList(zeroIdx, result.size());
        if(result.isEmpty()) return Collections.singletonList(0);
        result.set(0, result.get(0) * sign);

        return result;
    }
}
