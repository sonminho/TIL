<template>
    <div class="main">{{msg}}</div>
</template>

<script>
export default {
    name: 'child2',
    data: function() {
        return {
            msg: 'child2'
        }
    }
}
</script>

<style scoped>
.main { border: solid 1px black; background-color: aqua; }
</style>