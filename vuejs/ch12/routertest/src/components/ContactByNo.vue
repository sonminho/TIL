<template>
    <div>
        <hr class="divder">
        <table class="detail table table-bordered">
            <tbody>
                <tr class="active"><td>일련번호</td><td>{{contact.no}}</td></tr>
                <tr class="active"><td>이름</td><td>{{contact.name}}</td></tr>
                <tr class="active"><td>전화</td><td>{{contact.tel}}</td></tr>
                <tr class="active"><td>주소</td><td>{{contact.address}}</td></tr>
            </tbody>
        </table>
    </div>
</template>

<script>
import contactlist from '../ContactList'

export default {
    name: "contactByNo",
    data: function () {
        return {
            no: 0,
            contacts: contactlist.contacts,
        }
    },
    created: function() {
        this.no = this.$route.params.no;
    },
    computed: {
        contact: function() {
            var no = this.no;
            var arr = this.contacts.filter(function(item) {
                return item.no == no;
            });

            if(arr.length == 1) return arr[0];
            else return { };
        }
    },
    watch: {
        '$route': function(to) { // 라우트 속성 관찰
            this.no = to.params.no;
        }
    }
}
</script>

<style>
.divider {
    height:3px;
    margin-left: auto;
    margin-right: auto;
    background-color: #FF0066;
    color: #FF0066;
    border: 0 none;
}
</style>