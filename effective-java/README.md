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


# _Item26_
### 로 타입은 사용하지 말라

Raw Type 이란?
제네릭 타입에서 타입 매개변수를 전혀 사용하지 않을 때
 
```java
// Stamp 인스턴스만 취급
private final Collection stamps = ...;

// 실수로 동전을 넣는다.
stmpas.add(new Coin(...));

for( Iterator i = stmpas.iterator(); i.hasNext(); ) {
    Stamp stamp = (Stamp) i.next(); // ClassCastException 발생
    stamp.cancel();
}
```
위 코드와 같이 로타입을 사용하면 제네릭이 주는 안정성과 표현력을 잃게 됨

```java
private Collection<Stamp> stamp = ...;

stamps.add(new Coin(...)); // 제네릭을 사용하면 add 호출시 컴파일 에러 발생
```

#### ```List``` 같은 로타입과 ```List<Object>``` 이 다른점
- 매개변수로 ```List```를 받는 메서드에 ```List<String>``` 을 넘길 수 있지만 ```List<Object>``` 를 받는 메서드에는 넘길 수 없다.
- 즉, ```List<String>``` 은 로타입의 하위타입이지만 ```List<Object>``` 의 하위타입이 아니다.
```java
static void unsafeAdd(List list, Object o) {
    list.add(o);
}

@Test
void unsafeAddTest() {
    List<String> strList = new ArrayList<>();
    unsafeAdd(strList, Integer.valueOf(42)); // 정상적으로 원소가 추가된다.
    String str = strList.get(0); // 컴파일러가 자동으로 형변환 실행
}
```
위 코드에서 ```strList.get(0)``` 을 ```str``` 변수에 할당 시 _ClassCastException_ 이 발생한다. 로타입을 사용하면 런타임 시점에 타입관련 예외가 발생한다.
아래와 같이 수정하여 컴파일 단계에서 오류를 미리 잡을 수 있도록 하자.
```java
static void unsafeAdd(List<Object> list, Object o) {
    list.add(o);
}
```

#### 로타입 사용 예외
1. class 리터럴에 매개변수화 타입을 사용하지 못하게함 ```List.class```, ```Integer.class``` 등 ...
2. 런타임에는 제네릭 타입 정보가 지워지므로 instanceof 연산자는 비한정적 와일드카드 타입<?> 이외의 매개변수화 타입에는 적용할 수 없다.
```if (o instanceof Set<Stamp>)``` 코드는 컴파일에러!

---
# _Item27_
### 비검사 경고를 제거하라
제네렉을 사용하다보면 수많은 컴파일 경고를 보게된다. 이러한 경고들은 최대한 없애는 것이 좋다. 그렇지 않으면 런타임 시점에서 _ClassCastException_ 이 발생할
확률이 높다.

#### 경고를 제거할 수 없지만 타입 안전하다고 확신할 수 있으면 ```@SuppressWarnings("unchecked")``` 애너테이션을 달아 경고를 숨기기
- 코드는 경고 없이 컴파일 되지만 런타임시에 여전히 _ClassCastException_ 이 발생할 수 있다.
- ```@SuppressWarnings("unchecked")``` 을 사용할 때면 그 경고를 무시해도 안전한 이유를 주석으로 남기는 것이 좋다.
- 다른 개발자가 코드를 이해하는데 도움이 되고, 잘못 수정하여 Type 안정성을 잃는 상황을 줄여준다.

> 비검사 경고는 중요하니 무시하지 말자. 모든 비검사 경고는 런타임 시점에 _ClassCastException_ 을 발생시킬 수 있으니 제거하는 것이 좋다.

---

# _Item28_
### 배열보다는 리스트를 사용하라

배열과 제네릭 타입의 차이가 있음

#### 배열(공변, covariant)
Sub 타입이 Super 타입의 하위타입이라면 Sub[]은 Super[] 의 하위 타입이 된다.

```java
/* 런타임시 실패 */
Object[] objArr = new Long[1];
objArr[0] = "공변환 테스트";  // ArrayStoreException 발생
```

#### 제네릭(불공변, invariant)
서로 다른 타입 Type1, Type2 가 있을 때, List<Type1>은 List<Type2>의 하위 타입도 상위 타입도 아님
```java
/* 컴파일시 실패 */
List<Object> ol = new ArrayList<Long>(); // 컴파일 에러
ol.add("공변환 테스트"); // 호환되지 않는 타입을 추가할 수 없음
```
> 배열은 공변이고 실체화되는 반면, 제네릭은 불공변이고 타입 정보가 소거가 된다. → 제네릭 코드가 지원되기 전의 레거시 코드와 제네릭
> 타입을 함께 사용하기 위해..

배열은 런타임에는 타입 안전하지만 컴파일타임에는 그렇지 않다. 제네릭은 이와 반대이다. 이러한 이유로 배열과 제네릭을 함께 쓰기란 쉽지 않다.

---

# _Item29_
### 이왕이면 제네릭 타입으로 만들라

#### 일반 클래스를 제네릭 클래스로 만드는 방법

클래스 선언에 타입 매개변수를 추가

```java
import java.util.EmptyStackException;

class Stack<E> {
    private E[] elements;
    private int size = 0;
    private static final int DEFAULT_INIT_SIZE = 16;

    public Stack(E e) {
        elements = new E[DEFAULT_INIT_SIZE];
    }

    public void push(E e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public E pop() {
        if (size == 0) throw new EmptyStackException();
        E result = elements[--size];
        elements[size] = null;
        return result;
    }
    
    ...
}
```
생성자 내부 ```elemetns = new E[DEFAULT_INIT_SIZE];``` 코드에서 컴파일 에러가 발생한다. E와 같이 실체화 불가한 타입으로는 배열을
만들 수 없다.

해결법
1. 제네릭 배열 생성을 금지하는 제약을 우회하는 방법. Object 배열을 생성 후 제네릭 배열로 형변환 ```elements = (E[]) new Object[DEFAULT_INIT_SIZE];```
과 같이 변환하면 컴파일 오류 대신 경고가 발생한다.
2. elements 필드의 타입을 E[]에서 Object[]로 바꿈
위와 같이 변환하면 ```E result = elements[--size];``` 에서 컴파일 오류발생한다. 
```E result = (E) elements[--size];``` 배열이 반환한 원소를 E로 형변환 하면 오류 대신 경고가 발생한다.

첫 번째 방식이 가독성이 더 좋고 배열 타입을 E[]로 선언하여 오직 E타입 인스턴스만 받음을 어느정도 보장이 된다.
두 번째 방식에서는 배열에서 원소를 읽을 때마다 형변환을 해줘야한다.

현업에서는 첫 번째 방식을 선호하여 자주 사용하지만 배열의 런타임 타입과 컴파일타임 타입과 달라 _힙 오염(heap pollution)_ 이 발생하기도 한다.
합 오염이 걱정이 되는 경우에는 두 번째 방식을 고수하기도 한다.

> 클라이언트에서 직접 형변환해야 하는 타입보다 제네릭 타입이 더 안전하고 쓰기 편하다. 그러니 새로운 타입을 설계할 때는 형변환 없이도
> 사용할 수 있도록 하라. 그렇게 하려면 제네릭 타입으로 만들어야 할 경우가 많다. 기존 타입 중 제네릭이었어야 하는 게 있다면 제네릭 타입으로 변경하자.
> 기존 클라이언트에는 아무 영향을 주지 않으면서, 새로운 사용자를 훨씬 편하게 해주는 길이다.

---

# _Item31_
### 한정적 와일드 카드를 사용해 API 유연성을 높이라

```List<String>``` 같은 매개변수화 타입은 불공변(invariant) 이다.

즉, ```List<String>``` 은 ```List<Object>``` 의 하위 타입이 아니다.

```java
public class Stack<E> {
    public void push(E e);
    
    public void pushAll(Iterable<E> src) {
        for(E e : src) push(e);
    }
}
```

```pushAll(Iterable<E> src)```는 Iterable 을 구현한 자료형이면 잘 동작하지만 결함이 있다.

```java
Stack<Number> numStack = new Stack<>();
Iterable<Integer> integers = ...;
numStack.pushAll(integers);
```
코드에서 정상적으로 ```pushAll``` 이 호출될 것 같지만 컴파일 에러가 발생한다.

매개변수화 타입은 불공변이기 때문에 ```Iterable<Integer>``` 는 ```Stack<Number>```와 다른 타입이다 

#### ```pushAll(Iterable<E> src)``` 메서드가 유연하게 동작하려면 어떻게 해야할까?

#### _한정적 와일드카드타입_
```java
public void pushAll(Iterable<? extends E> src) {
    for(E e : src) push(e);
}
```
코드로 변경하면 E 타입을 상속받는 클래스를 받을 수 있기 때문에 컴파일 에러를 피할 수 있다. 

_그렇다면 아래 코드를 확장하기 위해 어떤 작업이 필요할까?_
```java
public void popAll(Collection<E> dst) {
    while(!dst.isEmpty())
        dst.add(pop());
}
```
```popAll``` 메서드는 ```Collection<E>``` 타입의 원소를 받을 수 있다.
```java
Stack<Number> numStack = new Stack<>();
Collection<Object> objects = ...;
numStack.popAll(objects);
```
마찬가지로 위 코드에서 매개변수 타입은 불공변 성질을 갖기 때문에 컴파일 에러가 발생하는 것을 알 수 있다.


```popAll``` 메서드는 ```Collection<E>``` 은 E 타입을 소비한다. 

유연하게 변수를 받기 위해 E 타입의 상위 타입도 받을 수 있도록 수정해보자.

```java
public void popAll(Collection<? super E> dst) {
    while(!dst.isEmpty())
        dst.add(pop());
}
```

```Collection<? super E>``` 와일드 카드로 상위 타입을 받을 수 있도록 변경하여 컴파일 에러를 제거 할 수 있다.

위 예시와 같이 와일드카드 사용에 적용되는 일반화 공식이 있다.

#### 펙스(PECS): producer-extends, consumer-super
_매개변수 타입 T가 생산자라면 <? extends T> 를 사용하고 소비자라면 <? super T>를 사용하라_

---

# _Item32_

### 제네릭과 가변인수를 함께 쓸 때는 신중하라

매개변수화 타입의 변수가 타입이 다른 객체를 참조하면 힙 오염이 발생한다.

가변인수(varargs) 메서드 는 호출 시 가변인수를 담기 위한 배열이 자동생성된다.

```java
static void dangerous(List<String> ... stringLists) {
    List<Integer> intList = List.of(42);
    Object[] objects = stringLists;
    objects[0] = intList; // Heap Pollution 발생, 다른 객체를 참조하였음
    String s = stringLists[0].get(0); // ClassCastException
} 
``` 
위 메서드를 호출하면 형변환하는 곳이 없는데도 ```ClassCastException``` 이 발생한다. 즉, 타입 안전하지 않다.

제네릭 배열은 직접 생성하지 못하게 했으면서 제네릭 varargs 매개변수를 받는 메서드를 선언하게 할 수 있게 한 이유는 무엇일까?

이유는 실무에 사용될 때 매우 유용하기 때문!

```Arrays.asList(T... a)```, ```Collections.addAll(Collection<? super T> c, T... elements)``` 등 자바 라이브러리가 기본적으로
제공하는 메서드는 위 dangerous 와 달리 타입 안전하다.

자바 7 이후로 ```@SafeVarargs``` 애너테이션이 추가돼서 제네릭 가변인수 메서드 작성자가 클라이언트 측에서 발생하는 경고를 숨길 수 있게 되었는데,

에너테이션을 사용하기 위해 2가지 일반적인 규칙이 있다.

1. 배열에 아무것도 저장하지 않는다.(매개변수들을 덮어쓰지 않음)
2. 그 배열의 참조가 밖으로 노출되지 않는다.(신뢰할 수 없는 코드가 배열에 접근할 수 없도록 함)

_참조변수를 노출하는 메서드_
```java
static<T> T[] toArray(T... args) {
    return args;
}
```
> ```args``` 변수를 그대로 반환하면 메서드를 호출한 쪽의 콜스택으로까지 전이한느 결과를 낳을 수 있다.

```java
static <T> T[] pickTwo(T a, T b, T c) {
    switch(ThreadLocalRandom.current().nextInt(3)) {
        case 0: return toArray(a, b);
        case 1: return toArray(a, c);
        case 2: return toArray(b, c);
    }
    throw new AssertionError(); // 도달할 수 없음
}
```
```java
@Test
public pickTwoTest() {
    String[] attributtes = pickTwo("좋은", "빠른", "저렴한"); // ClassCastException 발생!
}
```
```toArray``` 는 Object 타입의 배열을 반환한다.

메서드 호출 할 때가 되어서야 힙 오염(Heap pollution) 이 발생한다.

_올바른 ```@SafeVargs``` 사용하기_
```java
@SafeVarargs
static <T> List<T> flattern(List<? extends T>... lists) {
    List<T> result = new ArrayList<>();
    for(List<? extends T> list : lists)
        result.addAll(list);
    return result;
}
```
위 메서드는 다음과 같은 2가지를 준수하였다.
1. varargs 매개변수 배열에 아무것도 저장하지 않았다.
2. 그 배열(혹은 복제본)을 신뢰할 수 없는 코드에 노출하지 않았다.

> 제네릭은 가변인수와 궁합이 좋지 않다. 가변인수 기능은 배열을 노출하여 추상하가 완벽하지 못하고
> 배열과 제네릭의 타입 규칙이 서로 다르기 때문이다.

---

#_Item49_ 
### 매개변수가 유효한지 검사하라

메서드 몸체가 실행되기 전에 매개변수를 검사하는 것이 좋다.

그렇지 않으면, 아래와 같은 문제가 발생한다.

- 메서드가 수행되는 중간에 모호한 예외를 던지며 실패할 수 있다.
- 메서드가 잘 수행되었지만 잘못된 결과를 리턴한다.
  - 어떤 객체를 이상한 상태로 만들어 미래에 알 수 없는 오류가 발생

```java
class Test {
    @Test
    void param_test(long[] a, int offset, int length) {
        assert a != null;
        assert offset >= 0 && offset <= a.length;
        /* TO DO ~~*/
    }
}
```
```assert``` 를 사용하면 매개변수 유효성을 검증할 수 있다.
1) 실패하면 AssertionError 를 던진다
2) 런타임에 아무런 효과도, 성능 저하도 없다.

메서드 몸체 실행 전 매개변수의 유효성 검사에 대한 예외 케이스
>```Collections.sort(list)``` 같은 리스트를 정렬하는 메서드는 리스트 안의 객체들은 전부
> 상호 비교가 가능해야한다. 하지만 모든 객체가 상호 비교될 수 있는지 검사해봐야 별다른 실익이 없음
