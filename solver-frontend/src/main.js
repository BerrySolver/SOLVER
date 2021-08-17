import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Vuesax from 'vuesax'
import 'vuesax/dist/vuesax.css'
import 'material-icons/iconfont/material-icons.css';
import VModal from 'vue-js-modal';
import VueRandomColor from 'vue-randomcolor'

Vue.config.productionTip = false
Vue.use(Vuesax)
Vue.use(VModal, { dynamic: true })
Vue.use(VueRandomColor)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
