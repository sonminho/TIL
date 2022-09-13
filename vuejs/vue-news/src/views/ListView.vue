<template>
    <div>
        <ListItem></ListItem>    
    </div>
</template>

<script>
import ListItem from '../components/ListItem.vue'

export default {
    name: 'ListView',
    components: { ListItem },
    computed: {
        listItems() {
            return this.$store.state.list
        }
    }
}
</script>

<style scoped>

</style>
