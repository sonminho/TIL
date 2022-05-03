# _Item10_
## equals 메서드는 일반 규약을 지켜 재정의하라

### Object 클래스의 equals 메서드의 기본기능은 해당 클래스의 인스턴스가 같은지 비교하는 것

> equals 메서드를 재정의할 때는 반드시 일반 규약을 따라야 함, _*동치관계(equivalence relation)*_ 유지

- _반사성_
    -  null 이 아닌 모든 참조 값 x에 대해, x.equal(x)는 참이다.

- _대칭성_
    - null 이 아닌 모든 참조 값 x, y에 대해 x.equals(y)가 참이면 y.equals(x)도 참이다.

#### 대칭성 위배 Case
```java 
class CaseInsensitive {
    private String str;

    CaseInsensitive(String str) {
        this.str = str;
    }

    @Override
    boolean equals(Object obj){
        if(obj instanceof CaseInsensitve)
            return str.equalsIgnore(
                (CaseInsensitive) obj).str));
        if(obj instanceof String)
            return str.equalsIgnore((String) obj);
    }
}

CaseInsensitive cis = new CaseInsensitive("Java");
String str = "java";
```
 `
    CaseInsensitive 클래스가 재정의한 equals 메서드는 String 타입의 비교를 고려하였지만 String 클래스는 CaseInsensitive 클래스의 존재를 알 수 없다. 
    위 Case에서 cis.equals(str)은 True이지만 str.equals(cis)는 False를 
    반환한다.
`
    
- _추이성_
    - null 이 아닌 모든 참조 값 x, y, z에 대해 x.equals(y)와 y.equals(z)가 참이면 x.equals(z)도 참이다.
```java
class Point {
    private final int x, y;

    Point(int x, int y) {
        this.x = x; 
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Point)) return false;
        Point point = (Point) obj;
        return point.x == x && point.y == y;
    }
}

class ColorPoint extends Point {
    private final String color;

    ColorPoint(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Point)) return false;
        
        // 색상을 비교하지 않음(Point 클래스)
        if(!(obj instanceof ColorPoint)) {
            return obj.equals(this);
        }

        // 색상까지 비교(ColorPoint 클래스)
        return super.eqauls(obj) && ((ColorPoint) obj).color.equals(color);
    }
    
    @Test
    void 추이성위배() {
        ColorPoint2 cp = new ColorPoint2(1,2,"RED");
        Point p = new Point(1,2);
        ColorPoint2 cp2 = new ColorPoint2(1,2,"BLACK");
    }
}
```
`
cp.equals(p)도 true 이고 p.equals(cp2)도 true 이지만 좌표와 색상을 비교하는 cp.euqlas(cp2)는 false 를 반환한다
=> 추이성 위배
`


- _일관성_
    - null 이 아닌 모든 참조 값 x, y에 대해 x.equals(y)를 반복해서 호출하면 항상 true 혹은 항상 false 를 반환한다.

equals 메서드 재정의하는 단계
1. == 연산자를 사용해 입력이 자기 자신 참조인지 확인. 참조값이 같다면 바로 true 를 반환하여 성능 최적화
2. instanceof 연산자로 올바른 타입인지 확인
3. 입력 파라미터를 올바른 타입으로 형변환
4. 입력 객체와 대응되는 _핵심필드_ 가 모두 일치하는지 확인

```java
public class Clazz {
    private int p1;
    private String s1;
    
    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true; /* 1) 같은 참조 객체인지 확인 */
        if(!(obj instanceof clazz)) /* 2) 올바른 타입인지 확인 */
            return false;
        Clazz clazz = (Clazz) obj; /* 3) 입력 파라미터의 변환 */
        
        return clazz.p1 == p1 && clazz.s1.equals(s1); /* 4) 두 객체의 핵심필드가 동일한지 확인 */
    }
}
```
---
# _Item11_
## equals 를 재정의하려거든 hashCode 도 재정의하라
equals 메서드가 재정의 되었으면 hashCode 도 변경이 되어야한다.
그렇지 않으면 HashMap 이나 HashSet 같은 컬렉션에서 Key 를 해싱할 때 문제가 발생한다. 

### Object 스펙에 명시된 규약
- 어플리케이션이 실행되는 동안 equals 에 비교되는 값이 변경되지 않았으면 hashCode 도 일관된 값을 반환해야 한다.
- equals 로 객체가 같다고 판단되면, 두 객체의 hashCode 는 같은 값을 반환해야 한다.
- equals 로 두 객체가 다르다 판단되더라도, 두 객체의 hashCode 가 다른 값을 반환할 필요는 없음. 단, 다른 객체에 대해서는 다른 값을 반환해야 HashTable 사용할 때 성능이 좋다.

```java
class PhoneNumber {
    private final int areaCode, preFix, lineNumber;
    
    PhoneNumber(int areaCode, int preFix, int lineNumber) {
        this.areaCode = areaCode;
        this.preFix = preFix;
        this.lineNumber = lineNumber;
    }
    
    @Override
    public boolean equals(Object o) {
        if( o == this ) return true;
        if( !(o instanceof PhoneNumber) ) return false;
        PhoneNumber phoneNumber = (PhoneNumber) o;
        return phoneNumber.lineNumber == lineNumber
                && phoneNumber.preFix == preFix
                && phoneNumber.areaCode == areaCode;
    }
}

class hashTest {
    @Test
    void hashMapTest() {
        Map<PhoneNumber, String> map = new HashMap<>();
        PhoneNumber phoneNumber = new PhoneNumber(111, 222, 333);
        map.put(phoneNumber, "MINO");
        String name = map.get(new PhoneNumber(111, 222, 333));
        
        assertNotNull(name); // map 에 사용된 Key 값은 다른 인스턴스 
    }
}
```
위 코드에서 name 이 MINO 를 반환할 것 같지만 null 을 반환한다. phoneNumber 인스턴스와 map.get() 에 Key 로 사용된 인스턴스가 다르기 때문이다. 
equals 메서드 재정의로 논리적으로 동치인듯 하지만 같은 Key 값으로 처리하지 않았다. 
> _HashMap 은 서로 다른 해쉬코드에 대해서는 동치성 비교조차 하지 않는다._

```java
class PhoneNumber {
    ...
    @Override
    public int hashCode() {return 42;}
}
```
hashCode 메서드를 위와 같이 변경하면 문제는 해결되나 모든 객체가 같은 bucket 에 적재되어 마치 연결리스트 형식으로 동작한다.
해시테이블의 원소가 많아질수록 해시성능은 저하된다. 입력 n에 대해 시간복잡도가 O(1)에서 O(n)으로 느려짐
좋은 해시함수는 서로 다른 인스턴스에 대해 다른 해시코드를 반환한다.

#### 올바른 hashCode 재정의 하기
1. int 형 result 변수를 선언 해당 객체의 핵심 필드의 값 c로 초기화
2. 나머지 핵심필드의 값 f에 대해 다음 작업을 수행
   1) 기본 타입 필드면, Type.hashCode(f)를 수행 (Type 은 기본타입의 Boxing 클래스)
   2) 기본 타입이 아닌 참조 타입 필드를 재귀적으로 equals 비교 시 hashCode 도 재귀적으로 호출한다.
     계산이 더 복잡해질 것 같으면 표준 값 null 이나 0을 사용한다.  
   3) 필드가 배열이면 핵심 원소 각각을 별도 필드처럼 다룬다. 배열에 핵심 원소가 하나도 없으면 0 사용.
      모든 원소가 핵심 원소면 Arrays.hashCode 를 사용한다.
   4) 2.ⅰ~ ⅲ 에서 계산한 값 해시코드 c로 result 를 갱신한다.
      1) result = 31 * result + c;
3. result 를 반환한다.

위 규약을 준수하여 PhoneNumber 클래스의 hashCode 메서드는 아래와 같이 재정의 할 수 있다.
```java
class PhoneNumber {
    ...
    private int hashCode = 0;
    
    @Override
    public int hashCode() {
        int result = hashCode;
        if( result == 0 ) { // Thread Safe 보장
            result = Short.hashCode(areaCode); // 2.1 Type.hashCode(f)
            result = 31 * result + Short(preFix);
            result = 31 * result + Short(lineNumber);
            hashCode = result;
        }
        return result;
    }
}
```

> hashCode 재정의 시 *성능을 높이기 위해 핵심 필드를 생략해서는 안된다.*
  속도는 빨라지겠지만 특정 영역에 인스턴스들이 몰려 해시테이블의 성능이 급격하게 나빠진다.
---
# _Item12_
## toString 을 항상 재정의하라
모든 클래스가 상속한 Object 에 정의된 toString 메서드는 *클래스_이름@16진수로_표시한_해시코드* 를 반환한다.

> toString 의 일반 규약에 따르면 '간결하면서 사람이 일기 쉬운 형태의 유익한 정보'를 반환해야 한다. 

toString 을 재정의할 때 반환 값의 포맷을 문서화할지 정해야 한다.
- 포맷을 명시하면 해당 객체는 표준적이고 명확하고 사람이 읽을 수 있음
- 포맷을 명시하기로 했으면 정적 팩터리 메서드나 생성자를 함께 제공해 주면 좋음
  - ex. BigInteger, BigDecimal 클래스
- 포맷을 한번 명시하면 그 클래스는 포맷에 얽매이게 됨, 해당 클래스를 사용하는 모든 프로그래머들이 포맷을 준수하여 
파싱하고 객체를 생성해야하며 포맷이 변경될 때 번거로움

---
