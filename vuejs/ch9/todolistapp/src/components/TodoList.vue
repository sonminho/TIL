<style scoped>
    * {  box-sizing: border-box;  }
    .header {
        background-color: rgb(69, 205, 240); padding: 30px 30px;
        color: yellow; text-align: center;
    }
    .header:after {
        content: ""; display: table; clear: both;
    }
</style>

<template>
    <div id="todolistapp">
        <div id="header" class="header">
            <h2>Todo List App</h2>
            <input-todo></input-todo>
        </div>
        <list></list>
    </div>
</template>

<script type="text/javascript">
import InputTodo from './InputTodo.vue';
import List from './List.vue';
import store from '../store';

export default {
    name: 'todo-list',
    components: { InputTodo, List }
}
</script>