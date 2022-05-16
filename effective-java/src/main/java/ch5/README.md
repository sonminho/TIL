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