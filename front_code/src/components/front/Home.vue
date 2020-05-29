<style scoped>
    .layout{
        border: 1px solid #d7dde4;
        background: #f5f7f9;
        position: relative;
        border-radius: 4px;
        overflow: scroll;
    }
    .layout-logo{
        width: 100px;
        height: 30px;
        background: #5b6270;
        border-radius: 3px;
        float: left;
        position: relative;
        top: 15px;
        left: 20px;
        line-height: 30px;
        color: white;
    }
    .layout-search{
        width: 250px;
        height: 30px;
        background: #5b6270;
        border-radius: 3px;
        float: left;
        position: relative;
        top: 15px;
        left: 850px;
        line-height: 30px;
        color: white;
    }
    .layout-nav{
        width: 170px;
        margin: 0 auto;
        margin-right: 20px;
    }
    .ivu-layout-header{
        z-index: 9;
        right: 15px;
    }
    .layout-footer-center{
        text-align: center;
    }
</style>
<template>
    <div class="layout">
        <Layout>
            <Header :style="{position: 'fixed', width: '100%'}">
                <Menu mode="horizontal" theme="dark" active-name="4" @on-select="selectMenu">
                    <a href="/#/front"><div class="layout-logo"><Icon type="ios-book" />校园书城</div></a>
                    <div class="layout-search"><Input search placeholder="Enter something..." @on-search="search" v-model="keyword"/></div>
                    <div class="layout-nav">
                        <Submenu name="4">
                            <template slot="title">
                                <Icon type="ios-person" />
                                {{ user.name ? user.name : '个人中心' }}
                            </template>
                            <MenuItem name="4-1" to="/front/person/info"><Icon type="ios-information-circle" />个人信息</MenuItem>
                            <MenuItem name="4-2" to="/front/person/books"><Icon type="ios-add-circle" />发布书籍</MenuItem>
                            <MenuItem name="4-3" to="/front/person/brecord"><Icon type="md-document" />借阅记录</MenuItem>
                            <MenuItem name="4-7" to="/front/person/beborrow"><Icon type="md-document" />被借书籍</MenuItem>
                            <MenuItem name="4-6" to="/front/person/pay"><Icon type="logo-bitcoin" />积分充值</MenuItem>
                            <MenuItem name="4-8" to="/front/person/notice"><Icon type="ios-notifications" />我的通告</MenuItem>
                            <MenuItem name="4-9" to="/front/person/order"><Icon type="logo-bitcoin" />我的订单</MenuItem>
                            <MenuItem name="4-4" to="/front/login"><Icon type="ios-log-in" />登录</MenuItem>
                            <MenuItem name="4-5"><Icon type="ios-log-in" />退出</MenuItem>
                        </Submenu>
                    </div>
                </Menu>
            </Header>
            <Content :style="{margin: '88px 20px 0', background: '#fff', minHeight: '500px'}">
                <router-view/>
            </Content>
            <Footer class="layout-footer-center">2011-2016 &copy; TalkingData</Footer>
        </Layout>
    </div>
</template>
<script>
    import { mapState } from 'vuex'
    export default {
  data () {
        return {
          user: {},
          keyword: ''
        }
  },

  methods: {
        search () {
          window.location.href = `/#/front/search?keyword=${this.keyword}`
        },
        selectMenu (name) {
          if (name === '4-5') {
            this.logout()
          }
        },
        logout () {
          console.log('退出')
          window.sessionStorage.clear()
          this.$store.dispatch('saveUserName', {})
          this.user = {}
        },
        getLogin () {
          if (window.sessionStorage.getItem('token')) {
            this.$axios.get('/api/reader/loginUser').then(res => {
              this.user = res.data
            })
          }
        }
  },
      computed: {
        ...mapState(['username', 'cartCount', 'socket'])
      },
      mounted () {
        this.getLogin()
      }
    }
</script>



