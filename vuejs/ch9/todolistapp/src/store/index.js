import Vue from 'vue';
import Vuex from 'vuex';
import Constant from '../Constant';

// 애플리케이션 내부의 모든 컴포넌트가 저장소의 상태, 변이 객체에 접근할 수 있음
// 상태는 반드시 변이를 통해서만 변경되어야 함
Vue.use(Vuex);

const store = new Vuex.Store({
    state: {
        todolist : [
            { id: 1, todo: "토비의 스프링", done: false },
            { id: 2, todo: "모던자바인액션", done: false },
            { id: 3, todo: "JPA프로그래밍", done: false }
        ]
    },
    mutations: {
        [Constant.ADD_TODO] : (state, payload) => {
            if (payload.todo !== "") {
                state.todolist.push(
                    { id:new Date().getTime(), todo: payload.todo, done:false });
            }
        },
        [Constant.DONE_TOGGLE] : (state, payload) => {
            var index = state.todolist.findIndex((item)=>item.id === payload.id);
            state.todolist[index].done = !state.todolist[index].done;
        },
        [Constant.DELETE_TODO] : (state, payload) => {
            var index = state.todolist.findIndex((item)=>item.id === payload.id);
            state.todolist.splice(index,1);
        }
    }
});

export default store;