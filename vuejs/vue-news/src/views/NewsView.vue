<template>
  <div>
    <ListItem></ListItem>
  </div>
</template>

<script>
import ListItem from '../components/ListItem.vue'
import ListMixIn from '../mixins/ListMixin.js'

export default {
  name: 'NewsView',
  components: { 
    ListItem 
  },
  //mixins: [ListMixIn],
  created() {
  }
}
</script>

<style scoped>
</style>