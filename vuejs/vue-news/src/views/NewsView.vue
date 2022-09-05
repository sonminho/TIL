<template>
  <div>
    <div v-for="user in this.$store.state.news">{{user.title}}</div>
  </div>
</template>

<script>
export default {
    data() {
        return {
            users: []
        }
    },
    created() {
        this.$store.dispatch('FETCH_NEWS')

        // fetchNewsList()
        //     .then(function(response) {
        //         console.log(response)
        //         vm.users = response.data
        //     })
        //     .catch(function(error) {
        //         console.log(error)
        //     })
    }
}
</script>

<style>

</style>