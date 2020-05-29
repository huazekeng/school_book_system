<template>
  <div class="layout">
    <Row type="flex">
      <Col span="5" class="layout-menu-left">
      <Menu active-name="1-1" theme="dark" width="auto" :open-names="['1']" :accordion="true">
        <div class="layout-logo-left">
          <h2 style="color:white;">后台管理</h2>
        </div>
        <Submenu name="1">
          <template slot="title">
            <Icon type="ios-navigate"></Icon>
            用户管理
          </template>
          <MenuItem name="1-1" @click.native="lookUser"><span>用户列表</span></MenuItem>
        </Submenu>
        <Submenu name="2">
          <template slot="title">
            <Icon type="ios-keypad"></Icon>
            书籍管理
          </template>
          <MenuItem name="2-1"><span @click="lookBook">书籍列表</span></MenuItem>
        </Submenu>
        <Submenu name="3">
          <template slot="title">
            <Icon type="ios-analytics"></Icon>
            借阅管理
          </template>
          <MenuItem name="3-1"><span @click="lookRecord">借阅记录</span></MenuItem>
        </Submenu>
        <Submenu name="4">
          <template slot="title">
            <Icon type="ios-contact" />
            学生管理
          </template>
          <MenuItem name="4-1"><span @click="lookReader">学生列表</span></MenuItem>
        </Submenu>
        <Submenu name="5">
          <template slot="title">
            <Icon type="ios-cube" />
            分类管理
          </template>
          <MenuItem name="4-1"><span @click="lookCategory">分类列表</span></MenuItem>
        </Submenu>
        <Submenu name="6">
          <template slot="title">
            <Icon type="ios-cube" />
            订单管理
          </template>
          <MenuItem name="4-1"><span @click="lookOrder">订单列表</span></MenuItem>
        </Submenu>
      </Menu>
      </Col>
      <Col span="19">
      <div class="layout-header" >
        <span style="margin-left:85%">{{this.user.username}} <a style="color:#fff; margin-left:10px;" href="javascript:;" @click="logout">退出</a></span>
      </div>
      <div class="layout-breadcrumb">
        <Breadcrumb>
          <BreadcrumbItem href="#">{{one_nav}}</BreadcrumbItem>
          <BreadcrumbItem href="#">{{two_nav}}</BreadcrumbItem>
          <BreadcrumbItem>{{three_nav}}</BreadcrumbItem>
        </Breadcrumb>
      </div>
      <div class="layout-content">
        <div class="layout-content-main">
          <template id="userManage"></template>
          <component :is="currentView"></component>
        </div>
      </div>
      <div class="layout-copy">
        2019-2020 &copy; 图书共享平台
      </div>
      </Col>
    </Row>
  </div>
</template>
<script>
  import userManage from '@/components/admin/UserManage.vue'
  import Button from 'iview/src/components/button/button'
  import BookManage from '@/components/admin/BookManage.vue'
  import RecordManage from '@/components/admin/RecordManage.vue'
  import ReaderManage from '@/components/admin/ReaderManage.vue'
  import CategoryManage from '@/components/admin/CategoryManage.vue'
  import Order from '@/components/admin/order.vue'
  export default {
    name: 'Index',
    data () {
      return {
        msg: 'haha',
        one_nav: '主页',
        two_nav: '后台管理',
        three_nav: '用户管理',
        currentView: 'userManage',
        user: {}
      }
    },
    methods: {
      lookUser () {
        this.currentView = 'userManage'
        this.one_nav = '主页'
        this.two_nav = '用户管理'
        this.three_nav = '用户列表'
        this.currentView = 'userManage'
      },
      lookBook () {
        this.currentView = 'bookManage'
        this.one_nav = '主页'
        this.two_nav = '书籍管理'
        this.three_nav = '书籍列表'
        this.currentView = 'bookManage'
      },
      lookRecord () {
        this.currentView = 'recordManage'
        this.one_nav = '主页'
        this.two_nav = '借阅管理'
        this.three_nav = '借阅记录'
        this.currentView = 'recordManage'
      },
      lookReader () {
        this.currentView = 'readerManage'
        this.one_nav = '主页'
        this.two_nav = '学生管理'
        this.three_nav = '学生列表'
        this.currentView = 'readerManage'
      },
      lookCategory () {
        this.currentView = 'categoryManage'
        this.one_nav = '主页'
        this.two_nav = '分类管理'
        this.three_nav = '分类列表'
        this.currentView = 'categoryManage'
      },
      lookOrder () {
        this.currentView = 'order'
        this.one_nav = '主页'
        this.two_nav = '订单管理'
        this.three_nav = '订单列表'
        this.currentView = 'order'
      },
      getUser () {
        this.$axios.get('/admin/user/loginUser').then(res => {
          this.user = res.user
        })
      },
      logout () {
        window.sessionStorage.clear()
        window.location.href = '/#/admin/login'
      }
    },
    mounted () {
      this.getUser()
    },
    components: {
      Button,
      userManage: userManage,
      bookManage: BookManage,
      recordManage: RecordManage,
      readerManage: ReaderManage,
      categoryManage: CategoryManage,
      order: Order
    }
  }
</script>
<style scoped>
  .layout{
    border: 1px solid #d7dde4;
    background: #f5f7f9;
    position: relative;
  }
  .layout-breadcrumb{
    padding: 10px 15px 0;
    text-align:left;
  }
  .layout-content{
    min-height: 200px;
    margin: 15px;
    overflow: hidden;
    background: #fff;
    border-radius: 4px;
  }
  .layout-content-main{
    padding: 10px;
  }
  .layout-copy{
    text-align: center;
    padding: 10px 0 20px;
    color: #9ea7b4;
    position: absolute;
    left: 45%;
    bottom: 0;
  }
  .layout-menu-left{
    background: #464c5b;
    margin-top: -13px;
  }
  .layout-header{
    height: 40px;
    background: #515a6e;
    box-shadow: 0 1px 1px rgba(0,0,0,.1);
    font-size: 14px;
    line-height: 40px;
    color: #fff;
  }
  .layout-logo-left{
    width: 90%;
    height: 40px;
    line-height: 40px;
    background: #5b6270;
    border-radius: 3px;
    margin: 15px auto;
    font-size: 14px;
  }
  Button{
    color:white;
  }
</style>
