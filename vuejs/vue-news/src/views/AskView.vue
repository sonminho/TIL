<template>
  <div>
    <ListItem></ListItem>
    <!-- <ListItem></ListItem>
    <ul class="news-list">
      <li v-for="item in askItems" class="post">s
        <div class="points">
          {{ item.points }}
        </div>

        <div>
            <p class="news-title">
                <router-link :to="`item/${item.id}`">{{ item.title }}</router-link>
            </p>
            <small class="link-text">
                {{ item.time_ago }} by 
                <router-link :to="`/user/${item.user}`" class="link-text">{{ item.user }}</router-link>
            </small>
        </div>
      </li>
    </ul> -->
  </div>
</template>

<script>
import ListItem from '../components/ListItem.vue'

export default {
    components: { ListItem },
}
</script>

<style scoped>
</style>