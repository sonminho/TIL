<template>
    <form @submit.prevent="submitForm">
        <div>
            <label for="username">ID: </label>
            <input id="username" type="text" v-model="username"/>
        </div>
        <div>
            <label for="password">PW: </label>
            <input id="password" type="text" v-model="password"/>
        </div>
        <button v-bind:disabled="!isUsernameValid && !passsword" type="submit">로그인</button>
        <p>{{ logMessage }}</p>
    </form>
</template>

<script>
import { loginUser } from '@/api/index';
import { validateEmail } from '@/utils/validation';

export default {
    data() {
        return {
            username: '',
            password: '',

            logMessage: '',
        }
    },
    computed: {
        isUsernameValid() {
            return validateEmail(this.username);
        }
    },
    methods: {
        async submitForm() {
            try {
                const userData = {
                    username: this.username,
                    password: this.password,
                };

                const { data } = await loginUser(userData);
                console.log(data);
                this.logMessage = `${userData.username}님 환영합니다.`;
                this.initForm();
            } catch(error) {
                // 에러 핸들링할 코드
                console.log(error.response.data);
                this.logMessage = error.response.data;
            }
        },
        initForm() {
            this.username = '';
            this.password = '';
        }
    }
};
</script>

<style></style>
