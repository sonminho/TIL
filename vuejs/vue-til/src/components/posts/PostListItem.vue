<template>
    <li>
        <div class="post-title">
            {{ postItem.title }}
        </div>
        <div class="post-contents">
            {{ postItem.contents }}
        </div>
        <div class="post-time">
            {{ postItem.createdAt | formatDate}}
            <i class="icon ion-md-create" @click="routeEditPage">수정</i>
            <i class="icon ion-md-trash" @click="deleteItem">삭제</i>
        </div> 
    </li>
</template>

<script>
import { deletePosts } from '@/api/posts'
export default {
    props: {
        postItem: {
            type: Object,
            required: true,
        }
    },
    methods: {
        async deleteItem() {
            if(!confirm('정말 삭제하시겠습니까?')) return;

            await deletePosts(this.postItem._id);
            this.$emit('refreshPostList');
            console.log('deleted')
        },
        routeEditPage() {
            this.$router.push(`/post/${this.postItem._id}`)
        },
    }
}
</script>

<style>

</style>