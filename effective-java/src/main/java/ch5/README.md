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


