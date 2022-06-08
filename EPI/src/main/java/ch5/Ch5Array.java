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

    /*
    5.4 배열에서 이동하기
    크기가 n 인 배열 A 에서 첫번째에서 마지막 인덱스로 이동할 수 있는지 판단
    배열의 인덱스 i 위치에서는 dist 만큼 이동할 수 있다.(dist <= A[i])
     */
    static boolean canReachEnd(List<Integer> A) {
        int nowIdx = 0;
        for(int i = 0; i <= nowIdx && nowIdx < A.size(); i++) {
            nowIdx = Math.max(nowIdx, i + A.get(i));
        }
        return nowIdx >= A.size() - 1;
    }

    /*
     5.5 정렬된 배열에서 중복 제거하기
     정렬된 배열이 입력으로 주어질 때, 중복된 원소를 모두 제거한 뒤, 비어있는
     공간이 생기지 않도록 유효한 원소들을 모두 왼쪽으로 시프트하라
     */
    static List<Integer> deleteDuplicates(List<Integer> A) {
        if(A.isEmpty()) return Collections.emptyList();

        int now = 1;

        for(int i = 1; i < A.size(); i++) {
            if(!A.get(now - 1).equals(A.get(i))) {
                A.set(now++, A.get(i));
            }
        }

        for(int i = now; i < A.size(); i++) {
            A.set(i, 0);
        }

        return A;
    }
}
