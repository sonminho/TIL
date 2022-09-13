<template>
  <div>
    <ListItem/>
  </div>
</template>

<script>
import ListItem from '@/components/ListItem.vue'
import ListMixIn from '../mixins/ListMixin.js'

export default {
    name: 'JobsView',
    components: { ListItem },
    //mixins: [ListMixIn],
    created() { }
}
</script>

<style scoped>
</style>