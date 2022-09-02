package ch4;

/*
 * 기본자료형 연습
 */
public class PrimitiveType {

    /*
        값이 1인 Bit 수 세기
        최하위 bit 부터 shift 연산을 한 번씩 진행
        bit 수가 n개일 때, 시간 복잡도는 O(n)
     */
    public short countBits(int x) {
        short bitCnt = 0;

        while(x != 0) {
            bitCnt += (x & 1);
            x >>>= 1;
        }

        return bitCnt;
    }

    /*
        4.1 패리티 계산
        bit 의 1의 개수가 짝수면 0, 홀수면 1을 반환
        1로 셋팅된 bit 수가 k 개라면
        시간 복잡도는 O(k)
     */
    public short parity(long x) {
        short result = 0;

        // 1로된 최하위 비트를 제거
        while(x != 0) {
            result ^= 1; // XOR 0, 1 반복
            x &= (x-1);
        }

        return result;
    }

    /*
        4.2 비트스왑
        입력 크기에 상관없이 O(1)
     */
    public long swapBits(long x, int i, int j) {
        // i번째 비트와 j번째 비트가 다르면 스왑
        if( ((x >>> i) & 1) != ((x >>> j) & 1) ) {
            long bitMask = (1L << i) | (1L << j);
            x ^= bitMask; // XOR
        }

        return x;
    }

    /*
        4.3 비트 뒤집기
        8비트를 2비트의 x1, x2, x3, x4로 나누고
        룩업 테이블을 활용(캐시 전략)
        [00], [10], [01], [11] <- 미리 계산된 값
        시간 복잡도는 입력 비트 수 n을 캐시의 크기 L로 나눈 값 O(n/L)
     */
    public long reverseBits(long x) {
        int[] precomputedReverse = { 0, 2, 1, 3 }; // 리버스 룩업 테이블 [00], [10], [01], [11]
        final int WORD_SIZE = 2;
        final int BIT_MASK = 0x3; // [11]

        return precomputedReverse[ (int) (x & BIT_MASK) ] << (3 * WORD_SIZE)
            | precomputedReverse[ (int) ((x >>> WORD_SIZE) & BIT_MASK) ] << (2 * WORD_SIZE)
            | precomputedReverse[ (int) ((x >>> (2 * WORD_SIZE)) & BIT_MASK) ] << WORD_SIZE
            | precomputedReverse[ (int) ((x >>> (3 * WORD_SIZE)) & BIT_MASK) ];
    }
    
    /*
        4.4 같은 무게를 가진 가장 가까운 정수 찾기
        음이 아닌 정수 x가 주어졌을 때, x와 무게는 같지만, 즉 |y - x|가 최소가 되는 문제
        ex) x가 6일 때(110), 5(101)
        비트의 길이가 n일 때 시간 복잡도는 O(n)
     */
    public long closestIntSameBitCount(long x) throws IllegalArgumentException {
        final int UNSIGNED_BITS = 63;
        for(int i = 0; i < UNSIGNED_BITS - 1; i++) {
            if( ((x >>> i) & 1) != ((x >>> i+1) & 1) ) {
                x ^= (1L << i) | (1L << (i+1));
                return x;
            }
        }
        // x의 모든 비트가 0 또는 1이면 오류
        throw new IllegalArgumentException("All bits are 0 or 1");
    }
}
