## 컨피그서버
---
다수의 마이크로서비스 애플리케이션의 구성정보를 개별로 관리한다면 변경사항이 있을 때마다 코드 저장소에서 파일을 검색, 재기동 해야하며 이를 해결하는 방법은 아래와 같다.

- 배포되는 실제 코드와 구성 정보를 완전히 분리
- 여러 환경에서도 절대 변경되지 않는 불변 `immutable` 애플리케이션 이미지를 빌드
- 서버가 기동될 때 마이크로서비스가 읽어 오늘 환경변수 또는 중앙 저장소를 통해 모든 애플리케이션 구성 정보를 주입

마이크로서비스 배포 작업이 수기로 진행된다면 애플리케이션 구성정보 불일치, 예기치 못한 장애가 발생할 수 있음  

1. 분리 _`segregate`_ : 서비스의 물리적 배포 단계에서 구성정보를 완전히 독립시킴, 서비스가 시작할 때 필요한 프로퍼티를 환경번수로 전달 받거나 중앙 저장소에서 읽어들임

2. 추상화 _`abstract`_ : 서비스 인터페이스 뒷단에 있는 구성 데이터의 접근 방식을 추상화(REST 기반 JSON 등)

3. 중앙 집중화 _`centralize`_ : 가능한 적은 수의 저장소로 애플리케이션 구성 정보를 집중시킴 

4. 견고화 _`harden`_ : 애플리케이션 구성 정보는 배포되는 서비스와 완전히 분리되고 중앙 집중화 되므로 사용하고 구현할 술루션은 가용성이 높고 이중화가 필요
