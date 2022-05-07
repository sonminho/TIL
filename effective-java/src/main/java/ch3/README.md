# _Item10_
### equals 메서드는 일반 규약을 지켜 재정의하라

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
### equals 를 재정의하려거든 hashCode 도 재정의하라
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
### toString 을 항상 재정의하라
모든 클래스가 상속한 Object 에 정의된 toString 메서드는 *클래스_이름@16진수로_표시한_해시코드* 를 반환한다.

> toString 의 일반 규약에 따르면 '간결하면서 사람이 일기 쉬운 형태의 유익한 정보'를 반환해야 한다. 

toString 을 재정의할 때 반환 값의 포맷을 문서화할지 정해야 한다.
- 포맷을 명시하면 해당 객체는 표준적이고 명확하고 사람이 읽을 수 있음
- 포맷을 명시하기로 했으면 정적 팩터리 메서드나 생성자를 함께 제공해 주면 좋음
  - ex. BigInteger, BigDecimal 클래스
- 포맷을 한번 명시하면 그 클래스는 포맷에 얽매이게 됨, 해당 클래스를 사용하는 모든 프로그래머들이 포맷을 준수하여 
파싱하고 객체를 생성해야하며 포맷이 변경될 때 번거로움

---

# _Item13_
### clone 재정의는 주의해서 진행하라

> Cloneable 은 복제해도 되는 클래스임을 명시하는 mixin interface(item20)이지만 아쉽게도 의도한 목적을 제대로 이루지 못했다.

Cloneable 인터페이스의 역할
- Object 클래스의 protected 메서드인 clone 의 동작 방식을 결정
- 인터페이스를 구현한 클래스의 인스턴스 필드들을 하나하나 복사한 객체를 반환하며
그렇지 않은 클래스 인스턴스에서 호출하면 _CloneNotSupportedException_ 를 던짐

#### Object 클래스 스펙에 명시된 clone

> 이 객체의 복사본을 생성해 반환한다. '복사'의 의미는 그 객체를 구현한 클래스에 따라 다를 수 있다.

어떤 객체 x에 대해 다음 식은 참이다.
- x.clone() != x
- x.clone().getClass() == x.getClass()
  - 반드시 만족해야하는 것은 아님
- x.clone().equals(x)
  - 마찬가지로 일반적으로 참이지만, 필수는 아님

#### 관례상, 반환된 객체와 원본 객체는 독립적이여야 함

- 클래스 A를 상속한 클래스 B의 clone 은 'B' 타입 객체를 반환해야한다.
  - 만약 A 클래스가 clone 에서 'A' 타입을 반환했다면 'B'도 'A' 타입 객체를 반환하게 된다.
    - super.clone() 을 연쇄적으로 호출하도록 구현하면 clone 이 처음 호출된 상위 클래스의 객체가 만들어짐

_가변상태를 참조하지 않는 클래스용 clone 메서드_
```java
final class PhoneNumber implements Cloneable {
    private final int areaCode, preFix, lineNumber;
    
    ...
    
    @Override
    PhoneNumber clone() {
        try {
            /*
             재정의한 메서드의 반환타입은 상위 클래스의 메서드가 
             반환하는 타입의 하위 타입일 수 있다.
             */
            return (PhoneNumber) super.clone();
        } catch(CloneNotSupportedException e) {
            throw new AssertionError(); // 발생할 수 없음
        }
    }
} 
```
위 코드와 같이 모든 필드가 기본타입이거나 불변 객체를 참조하면 super.clone() 으로 반환되는
상위 객체 타입을 하위 객체 타입으로 캐스팅 하면 된다. 하지만 가변 객체는 상황이 달라진다.

_가변상태를 참조하는 클래스용 clone 메서드_
```java
class Stack implements Cloneable {
    private Object[] elements;
    private int size = 0;
    ...
    @Override
    public Stack clone() {
        try {
            Stack stack = (Stack) super.clone();
            // result.elements = elements.clone();
            return result;    
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
    
    @Test
    void cloneTest() {
        Stack stack1 = new Stack();
        stack1.push(1);
        Stack stack2 = stack1.clone();
        stack1.push(2);
        
        assertEqual(stack1.elements, stack2.elements);
    }
}
```
cloneTest()에서 스택의 배열원소 element 는 참조필드 이기 때문에 stack1과 stack2 인스턴스는 같은 필드를 참조한다.
위 케이스에서는 스택 사이즈는 stack1이 2, stack2가 1인데 element 는 [1, 2] 를 갖는다.
clone 메서드를 재정의할 때 _참조형 변수 및 가변상태를 재귀적으로 clone() 호출_ 해야한다. 
elements.clone() 의 결과를 Object[]로 형변환할 필요는 없음.
> 배열의 clone 은 런타임 타입과 컴파일타임 타입 모두가 원본 배열과 똑같은 배열을 반환한다.
> 따라서 배열을 복제할 때는 clone 메서드 사용이 권장됨.
---

# _Item14_
###Comparable 을 구현할지 고려하라

> Comparable 을 구현했다는 것은 그 클래스의 인스턴스들에는 자연적인 순서(natural order)가 있음을 의미한다.

compareTo 메서드의 일반 규약은 equals 의 규약과 비슷하다.
- 주어진 객체보다 작으면 음의 정수를, 같으면 0을, 크면 양의 정수를 반환
- 비교할수 없는 타입의 객체가 주어지면 ClassCastException 을 던짐
- Comparable 을 구현한 모든 클래스는 모든 x, y에 대해 sgn(x.compareTo(y)) == -sgn(y.compareTo(x))여야 한다.
- Comparable 을 구현한 클래스는 추이성을 보장해야 한다. x.compareTo(y) > 0 && y.compareTo(z) > 0 이면 x.compareTo(z) > 0 이다.
- Comparable 을 구현한 클래스는 모든 z에 대해 x.compareTo(y) == 0 이면 sgn(x.compareTo(z)) == sgn(y.compareTo(z))다.
- 필수는 아니지만 꼭 지키는게 좋다. _(x.compareTo(y) == 0) == x.equals(y)_ 
- ---