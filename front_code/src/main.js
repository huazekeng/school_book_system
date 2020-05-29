// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import Global from './components/util/Global'
import Nprogress from 'nprogress'
import axios from 'axios'
import iView from 'iview'
import qs from 'qs'
import store from './store'

Vue.prototype.$qs = qs
Vue.use(iView)
Vue.prototype.GLOBAL = Global
Vue.config.productionTip = false
axios.defaults.baseURL = 'http://localhost:80'

axios.interceptors.request.use(config => {
  config.headers.Authorization = window.sessionStorage.getItem('token')
  Nprogress.start()
  return config
})

// 接口拦截
axios.interceptors.response.use(function (response) {
  const res = response.data
  const path = location.hash
  Nprogress.done()
  console.log(this)
  if (res.code === 20000) {
    return res.data
  } else if (res.code === 40002 || res.code === 40007) {
    console.log(response)
    const url = response.config.url
    if (path !== '#/front/index') {
      if (url.indexOf('api') > 0) {
        window.location.href = '/#/front/login'
      } else {
        window.location.href = '/#/admin/login'
      }
    }
    iView.Message.info(res.message)
    return Promise.reject(res)
  } else {
    iView.Message.info(res.message)
    return Promise.reject(res)
  }
}, (error) => {
  console.log(iView.Message)
  if (error && error.response) {
    switch (error.response.status) {
      case 400: error.message = '请求错误(400)'; break
      case 401: error.message = '未授权，请重新登录(401)'; break
      case 403: error.message = '拒绝访问(403)'; break
      case 404: error.message = '请求出错(404)'; break
      case 408: error.message = '请求超时(408)'; break
      case 500: error.message = '服务器错误(500)'; break
      case 501: error.message = '服务未实现(501)'; break
      case 502: error.message = '网络错误(502)'; break
      case 503: error.message = '服务不可用(503)'; break
      case 504: error.message = '网络超时(504)'; break
      case 505: error.message = 'HTTP版本不受支持(505)'; break
      default: error.message = `连接出错(${error.response.status})!`
    }
  } else {
    error.message = '连接服务器失败!'
  }
  iView.Message.info(error.message)
  return Promise.reject(error)
})

Vue.prototype.$axios = axios
/* eslint-disable no-new */
new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app')
