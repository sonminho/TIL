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
