<template>
  <div>
    <p v-for="item in askItems">
      <router-link :to="`item/${item.id}`">{{ item.title }}</router-link>
      <small>{{ item.time_ago }} by {{ item.user }}</small>
    </p>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
    computed: {
        // ...mapState({
        //     fetchedAsk: state => state.ask
        // })

        ...mapGetters({
            askItems: 'fetchedAsk'
        })
    },
    data() {
        return {
            ask: []
        }
    },
    created() {
        this.$store.dispatch('FETCH_ASK')
    },
}
</script>

<style>

</style>