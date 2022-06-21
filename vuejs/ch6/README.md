# 컴포넌트조합

컴포넌트들은 ```속성(props)``` 을 통해 정보를 전달할 수 있음.

유지보수의 어려움과 복잡도를 고려하여 주로 부모에서 자식 컴포넌트로 데이터를 전달함

### 부모-자식 컴포넌트 통신 프로세스
1. 자식 컴포넌트에서 사용자 이벤트를 정의하고 이벤트를 발생
2. 부모 컴포넌트에서 이벤트 핸들러 메서드를 호출하도록 작성

### 컴포넌트 작성
> Vue.component(tagname, options)  
tagname: 컴포넌트를 사용할 태그명  
options: 컴포넌트에서 렌더링할 templet 등을 지정


### 컴포넌트에서의 data 옵션
data 옵션에 객체를 직접 지정하면 컴포넌트가 정상적으로 렌더링 되지 않고 오류가 발생함


```javascript
<template id='timeTemplate'>
    <div>
        <span>{{nowTS}}</span>
        <button v-on:click="timeClick">현재시간</button>
    </div>
</template>
Vue.component('time-component',{
    template: '#timeTemplate',
    data: {nowTS : 0}, // 객체를 직접 지정, 렌더링 오류
    methods: {
        timeClick: function() {
            this.nowTS = (new Date()).getTime();
        }
    },
} )
```
> 함수가 호출되어 리턴된 객체가 data 옵션에 할당되어야 한다.  
컴포넌트가 여러 번 사용되더라도 동일한 객체를 가리키는 것이 아니라 함수가 호출될 때마다  
만들어진 객체가 리턴되기 때문

---
#### props 를 이용한 정보 전달

- Vue 컴포넌트 정의할 때 props 옵션 추가, 배열로 props명을 배열로 나열

```javascript

<!-- 하위 컴포넌트-->
<template id="template">
    <h1>{{message}}</h1>
</template>
Vue.component('component', {
    template: '#template',
    props: ['message']
})

...
<!-- 상위 컴포넌트에서 하위 컴포넌트로 props 정보 전달-->
<div id="app"> 
    <component message="Hello World!"></component>
</div>

...

new Vue({
    el: '#app'
})

```
---
 
#### event 를 이용한 정보전달

- 자식 컴포넌트는 이벤트를 발신(emit)하고 부모 컴포넌트는 v-on 디렉티브를 이용해 이벤트를 수신


---

#### 이벤트 버스 객체를 이용한 통신

- 부모-자식 관계가 아닌 컴포넌트들은 이벤트 버스(Event Bus) 객체를 사용하여 통신
- 비어 있는 Vue 인스턴스를 생성하여 사용

```child1-component```
```javascript
Vue.component('child1-component', {
    methods: {
        clickEvent: function() {
            ...
            eventBus.$emit('click', t);
        }
    }
})
```

```child2-component```
```javascript
Vue.component('child2-component', {
    create: function() {
        eventBus.$on('click1', this.child1Click);
    },
    methods: {
        click1Click: function(time) {
            this.timelist.push(time);
        }
    }
})
```
- 이벤트를 수신하는 컴포넌트는 미리 이벤트 핸들러를 등록해두어야 함
    - Vue 인스턴스 생명주기의 created 이벤트 훅을 이용하여 Vue 인스턴스가 만들어질 때 $on 메서드를 사용해 이벤트 수신정보를 등록
    - 이벤트를 발신하는 컴포넌트에서는 $emit 메서드를 호출