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