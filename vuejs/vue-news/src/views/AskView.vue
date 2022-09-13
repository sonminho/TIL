<template>
  <div>
    <ListItem></ListItem>
  </div>
</template>

<script>
import ListItem from '../components/ListItem.vue'
import ListMixIn from '../mixins/ListMixin.js'
import {bus} from '../utils/bus.js'

export default {
    name: 'AskView',
    components: { ListItem },
    //mixins: [ListMixIn],
    // mounted() {
    //   bus.$emit('end:spinner')
    // },
}
</script>

<style scoped>
</style>