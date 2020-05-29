import Vue from 'vue'
import Router from 'vue-router'
import VueResource from 'vue-resource'

import 'iView/dist/styles/iview.css'
import VueQuillEditor from 'vue-quill-editor'
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'
import AdminIndex from '@/components/admin/Index'
import FrontIndex from '@/components/front/Index'
import FrontHome from '@/components/front/Home'
import Reader from '@/components/front/Reader'
import AdminLogin from '@/components/admin/Login'
import FrontLogin from '@/components/front/Login'
import FrontRegister from '@/components/front/Register'
import PersonCenter from '@/components/front/personCenter'
import PersonInfo from '@/components/front/personInfo'
import PersonBooks from '@/components/front/BookManage'
import PersonBRecord from '@/components/front/RecordManage'
import Pay from '@/components/front/pay'
import BeBorrow from '@/components/front/BeBorrow'
import paySuccess from '@/components/front/paySuccess'
import Detail from '@/components/front/bookDetail'
import SearchBook from '@/components/front/SearchBook'
import Notice from '@/components/front/notice'
import Order from '@/components/front/order'
import AdminOrder from '@/components/admin/order'

Vue.use(Router)
Vue.use(VueResource)
// Vue.use(VueQuillEditor,{default global options})
Vue.use(VueQuillEditor)

export default new Router({
  routes: [
    {
      path: '/admin',
      name: 'Home',
      component: AdminLogin
    },
    {
      path: '/admin/login',
      name: 'AdminLogin',
      component: AdminLogin
    }, {
      path: '/admin/index',
      name: 'AdminIndex',
      component: AdminIndex
    }, {
      path: '/admin/order',
      name: 'AdminOrder',
      component: AdminOrder
    },
    {
      path: '/front',
      name: 'FrontHome',
      component: FrontHome,
      redirect: '/front/index',
      children: [
        {
          path: 'index',
          name: 'FrontIndex',
          component: FrontIndex
        }, {
          path: 'person',
          name: 'PersonCenter',
          component: PersonCenter,
          children: [{
            path: 'info',
            name: 'PersonInfo',
            component: PersonInfo
          }, {
            path: 'books',
            name: 'PersonBooks',
            component: PersonBooks
          }, {
            path: 'brecord',
            name: 'PersonBRecord',
            component: PersonBRecord
          }, {
            path: 'pay',
            name: 'Pay',
            component: Pay
          }, {
            path: 'beborrow',
            name: 'BeBorrow',
            component: BeBorrow
          }, {
            path: 'paysuccess',
            name: 'paySuccess',
            component: paySuccess
          }, {
            path: 'notice',
            name: 'Notice',
            component: Notice
          }, {
            path: 'order',
            name: 'Order',
            component: Order
          }]
        }, {
          path: 'detail/:id',
          name: 'Detail',
          component: Detail
        }, {
          path: 'search',
          name: 'SearchBook',
          component: SearchBook
        }

      ]
    },
    {
      path: '/front/login',
      name: 'FrontLogin',
      component: FrontLogin
    },
    {
      path: '/front/register',
      name: 'FrontRegister',
      component: FrontRegister
    },
    {
      path: '/front/reader',
      name: 'Reader',
      component: Reader
    }
  ]
})
