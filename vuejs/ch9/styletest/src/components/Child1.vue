<template>
    <div class="main">{{msg}}</div>
</template>

<script>
export default {
    name: 'child1',
    data: function() {
        return {
            msg: 'child1'
        }
    }
}
</script>

<style scoped>
.main { border: solid 1px black; background-color: yellow; }
</style>