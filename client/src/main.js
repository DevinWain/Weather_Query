import Vue from 'vue'
import App from './App'

import { Cascader } from 'vant'
import { Field } from 'vant'
import { Popup } from 'vant'
import {Icon} from 'vant'
import { Dialog } from 'vant'

Vue.config.productionTip = false

Vue.use(Cascader)
Vue.use(Field)
Vue.use(Popup)
Vue.use(Icon)
Vue.use(Dialog)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  render: h => h(App)
})

// require('./assets/css/base.css');
