<template>
  <div>
    <p v-for="item in askItems">
      <a v-bind:href="item.url">
        {{ item.title }}
      </a>
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