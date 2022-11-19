<template>
    <div class="contents">
        <h1 class="page-header">Edit Post</h1>
        <div class="form-wrapper">
            <form class="form" @submit.prevent="submitForm">
                <div>
                    <label for="title">Title: </label>
                    <input id="title" type="text" v-model="title">
                </div>
                <div>
                    <label for="contents">Contents: </label>
                    <textarea id="contents" type="text" v-model="contents"/>
                </div>
                <button type="submit" class="btn">Edit</button>
            </form>
        </div>
    </div>
</template>

<script>
import { fetchPost, editPost } from '@/api/posts';

export default {
    data() {
        return {
            title: '',
            contents: '',
            logMessage: '',
        }
    },
    async created() {
        const id = this.$route.params.id;
        const { data } = await fetchPost(id);
        console.log(data);
        this.title = data.title;
        this.contents = data.contents;
    },
    methods: {
        async submitForm() {
            try {
                await editPost(this.$route.params.id, {
                    "title": this.title,
                    "contents": this.contents
                });
                this.$router.push('/main');
            } catch(error) {
                console.log(error);
            }
        },
    }
}
</script>

<style scoped>
.form-wrapper .form {
    width: 100%;
}
.btn {
    color: white;
}
</style>