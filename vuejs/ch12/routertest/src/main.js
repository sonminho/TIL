import Vue from 'vue'
import 'bootstrap/dist/css/bootstrap.css'
import App from './App.vue'
import Home from './components/Home.vue';
import About from './components/About.vue';
import Contacts from './components/Contacts.vue';
import ContactByNo from './components/ContactByNo.vue';
import VueRouter from 'vue-router';

Vue.config.productionTip = false
Vue.use(VueRouter);

const router = new VueRouter({
  routes : [
    { path:'/', component: Home },
    { path:'/home', component: Home },
    { path:'/about', component: About },
    { 
      path:'/contacts',
      name:'contacts',
      component: Contacts,
      children: [
        { path:':no', name: 'contactbyno', component: ContactByNo }
      ]
    },
  ]
})

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')