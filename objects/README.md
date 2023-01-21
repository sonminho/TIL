## 오브젝트(Obecjt) 코드로 이해하는 객체지향 설계 - 조영호 지음

---

### 캡슐화(Capsulation)
- 변경 가능성이 높은 부분을 객체 내부로 숨기는 추상화 기법
- 캡슐화가 잘 구현되어 있는 시스템은 코드 변경에 대해서 자유로움

### 응집도(Cohesion)
- 모듈에 포함된 내부 요소들이 연관되어 있는 정도
- 관련 높은 책임들을 객체 또는 클래스에 할당

### 결합도(Coupling)
- 객체 간 의존성의 정도, 타 모듈의 정보를 자세히 알수록 결합도는 낮아진다
- 한 모듈이 변경되기 위해서 다른 모듈의 변경을 요구되는 정도

> 높은 응집도와 낮은 결합도를 갖게 될 수록 좋은 설계  
> __High Cohesion, Low Coupling__

데이터 중심의 설계는 **캡슐화를 위반**하기 때문에 책임 중심의 설계에 비해
낮은 응집도와 높은 결합도를 갖는다  

1. 캡슐화 위반  
데이터 중심으로 설계된 Movie 클래스
```java
class Movie {
    private Money fee;
    public Money getFee() { return fee; }
    public void setFee(Money fee) { this.fee = fee; }
}
```
fee 의 값을 읽거나 수정하기 위해 setFee, getFee 메서드가 존재한다
이는 fee 라는 Movie 타입의 인스턴스 변수가 존재함을 노출함

_추측에 의한 설계 전략(design-by-guessing strategy)_ 은 위와 같이 접근자와 수정에 과도하게
의존하는 설계 방식이다

>객체가 사용될 협력을 고려하지 않고 다양한 상황에서 사용될 수 있을 것이라는 막연한 추측으로
>프로그래머는 객체의 내부 상태를 표현하는 메서드를 최대한 많이 생성해야 하는 압박에 시달린다 

2. 높은 결합도

**클라이언트가 객체의 구현에 강하게 결합되어 있음**  
여러 데이터 객체를 사용하는 제어 로직이 특정 객체 안에 집중되어 있을 경우, 임의의 데이터 객체가
변경될 때마다 제어 로직도 함께 수정되어야 한다      
  
  
3. 낮은 응집도  
 
하나의 요구사항 변경을 만족시키기 위해 **여러 모듈을 수정**해야함  
응집도가 낮을 경우 다른 모듈에 위치해야할 책임의 일부가 엉뚱한 곳에 위치함
---
## 책임 할당을 위한 GRASP 패턴
#### (General Responsibility Assignment Software Pattern)
객체에게 책임을 할당할 때 지침으로 삼을 수 있는 원칙들의 집합

- Information expert 패턴
  - 책임을 수행하는 데 필요한 정보를 가지고 있는 객체에게 할당
  - 정보를 가지고 있는 객체들로 책임이 분산되기 때문에 응집력은 향상되고 결합도는 낮아진다
- Low coupling 패턴
  - 설계의 전반적인 결합도를 낮추고 변화 영향을 줄이고 재사용성을 증가시킨다
- High cohesion 패턴
  - 객체의 책임이 분산되면 요구사항 변화에 연관된 모든 객체들도 동시에 수정되어야 한다
- Creator 패턴
  - 인스턴스를 생성할 책임을 할당하는 기법
  - 이미 결합돼 있는 객체에게 생성 책임을 할당
- Polymorphism 패턴
  - 객체의 타입에 따라 변하는 행동에 대해 타입을 분리하고 변화하는 행동을 각 타입의 책임으로 할당

```java
public class Movie {
  ...
  public boolean checkPeriodConditions(Screening screening) {
    ...
  }
  public boolean checkSequenceConditions(Screening screening) {
    ...
  }
}
```

새로운 할인 조건이 추가될 때마다 Movie 클래스는 수정되어야 한다. Movie 클래스 입장에서는 Screening 타입을
매개변수로 받아 할인이 가능한지만 리턴한면 된다.
할인 조건을 인터페이스로 정의하여 각 할인 조건 클래스가 할인 가능여부를 판단하도록 책임을 할당한다. 
```java
public interface DiscountCondition {
    boolean isSatisfiedBy(Screening screening);
}

public class PeriodCondition implements DiscountCondition { ... }
public class SequenceCondition implements DiscountCondition { ... }

public class Movie {
    private List<DiscountCondition> discountConditions;
    
    public Movie(List<DiscountCondition> discountConditions) {
        this.discountConditions = discountConditions;
    }
    
    public Money calculateMovieFee(Screening screening) {
        if(isDiscountable(screening)) {
            return fee.minus(calculateDiscountAmount());
        }
        return fee;
    }
    
    public boolean isDiscountable(Screening screening) {
        return discountConditions.stream().anyMatch( condition -> 
            condition.isSatisfiedBy(Screening));
    }
}
```

---

### 메시지와 인터페이스

클라이언트-서버 모델
 - 협력관계에 있는 객체 안에서 메시지를 송수신하는 구조
 - 메시지를 전송하는 객체는 클라이언트, 수신하는 객체는 서버
 - 클라이언트가 서버로 서비스를 요청하는 단방향 상호작용 구조
  

메시지
 - 객체들이 협력하기 위해 사용할 수 있는 의사소통 수단

오퍼레이션
 - 객체가 다른 객체에게 제공하는 추상적인 서비스
 - 메시지를 수신하는 객체의 인터페이스를 강조

메서드
 - 메시지에 응답하기 위해 실행되며 오퍼레이션을 구현한 코드블록

시그니쳐
 - 오퍼레이션, 메서드의 명세를 나타내며 이름과 인자의 목록을 포함

### 디미터 법칙
협력하는 객체의 내부 구조에 대한 결합으로 인해 발생하는 설계 문제를 해결하기 위해 제안된 원칙

오직 인접한 이웃하고만 말하라(only talk to your immediate neighbors)  
_부끄럼타는 코드(shy code)_ - 메시지 수신자의 내부 구조가 전송자에게 노출되지 않으며, 메시지 전송자는 수신자
의 내부 구현에 결합되지 않는다.  

`screnning.getMovie().getDiscountConditions()`  
위 코드는 디미터 법칙을 위반한다.  
메시지 전송자가 수신자의 내부 구조 (`screening.getMovie()`) 에 대해 요소를 반환 받고 연쇄적으로 할인조건을 
요청하는 메시지를 전송한다. 이는 Movie 클래스가 할인조건 클래스와 결합되어 있다는 것을 안다는 것이다.
즉, 메시지 전송자는 수신자의 내부 구현에 강하게 결합되며 수신자의 요구사항 변경이 발생하면 전송자 또한 변경되어야 한다.

### 명령-쿼리 분리 법칙(Command-Query Separation)  
오퍼레이션은 부수효과를 발생시키는 명령이거나 부수효과를 발생시키지 않는 쿼리 중 하나여야 한다
- 객체의 상태를 변경하는 명령은 반환값을 가질 수 없다.
- 객체의 정보를 반환하는 쿼리는 상태를 변경할 수 없다.  

명령이 개입하지 않는 한 쿼리의 값은 변경되지 않기 때문에 쿼리의 결과를 예측하기 쉬워진다.
명명과 쿼리를 분리함으로 `참조 투명성(referential transparency)` 의 장점을 누릴 수 있다.
참조 투명성이라는 특성을 잘 활용하면 버그가 적고 디버깅이 유용하며 쿼리의 순서에 따라 실행 결과가 변하지 않는
코드를 작성할 수 있다.