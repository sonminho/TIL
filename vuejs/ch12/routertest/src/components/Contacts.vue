<template>
    <div>
        <h1>연락처</h1>
        <div class="wrapper">
            <div class="box" v-for="c in contacts" :key="c.no">
                <!-- <router-link v-bind:to="'/contacts/' + c.no">{{c.name}}</router-link> -->
                <span @click="navigate(c.no)" style='cursor:pointer'>
                    [ {{c.name}} ]
                </span>
            </div>
        </div>
        <router-view></router-view>
    </div>
</template>

<script>
import contactlist from '../ContactList';

export default {
    name: "Contacts",
    data: function() {
        return {
            contacts : contactlist.contacts
        }
    },
    methods: {
        navigate(no) {
            if(confirm('상세 정보를 열람하시겠습니까? ' + no)) {
                this.$router.push( {name:'contactbyno', params: {no: no}}, function() { // 명명된 라우트
                    console.log('/contacts/' + no + '로 라우팅 완료')
                });
            }
        }
    }
}
</script>

<style>
.wrapper { background-color:#fff; clear:both; display:table; }
.box { float:left; background-color:silver; border-radius:5px;
    padding: 10px; margin:3px; text-align:center; font-size:120%;
    width:100px; font-weight:bold; }
a:link, a:visited { text-align:center; text-decoration:none;
    display:inline-block; }
</style>
