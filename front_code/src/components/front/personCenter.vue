<style scoped>
.layout{
    border: 1px solid #d7dde4;
    background: #f5f7f9;
    position: relative;
    border-radius: 4px;
    overflow: hidden;
    height: 100%;
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
}
.layout-nav{
    width: 420px;
    margin: 0 auto;
    margin-right: 20px;
}
.content{
    text-align: left;
}
</style>
<template>
    <div class="layout" style="z-index:1">
        <Layout>
            <Layout>
                <Sider hide-trigger :style="{background: '#fff'}">
                    <Menu :active-name="active" theme="light" width="auto" :open-names="['4']">
                        <MenuItem name="4-1" to="/front/person/info"><Icon type="ios-information-circle" />个人信息</MenuItem>
                        <MenuItem name="4-2" to="/front/person/books"><Icon type="ios-add-circle" />发布书籍</MenuItem>
                        <MenuItem name="4-3" to="/front/person/brecord"><Icon type="md-document" />借阅记录</MenuItem>
                        <MenuItem name="4-7" to="/front/person/beborrow"><Icon type="md-document" />被借书籍</MenuItem>
                        <MenuItem name="4-6" to="/front/person/pay"><Icon type="logo-bitcoin" />积分充值</MenuItem>
                        <MenuItem name="4-8" to="/front/person/notice"><Icon type="ios-notifications" />我的通告</MenuItem>
                        <MenuItem name="4-9" to="/front/person/order"><Icon type="logo-bitcoin" />我的订单</MenuItem>
                    </Menu>
                </Sider>
                <Layout :style="{padding: '0 24px 24px'}" class="content">
                    <Breadcrumb :style="{margin: '24px 0'}">
                        <BreadcrumbItem to="/front"><Icon type="ios-home-outline"></Icon>首页</BreadcrumbItem>
                        <BreadcrumbItem><Icon type="logo-buffer"></Icon> 个人中心</BreadcrumbItem>
                        <BreadcrumbItem><Icon type="ios-information-circle"></Icon> {{title}}</BreadcrumbItem>
                    </Breadcrumb>
                    <Content :style="{ minHeight: '650px', background: '#fff'}">
                        <router-view/>
                    </Content>
                </Layout>
            </Layout>
        </Layout>
    </div>
</template>
<script>
    export default {
      data () {
        return {
          active: '4-1',
          title: '个人信息'
        }
      },
      created () {
        let path = this.$route.path.split('/')[3]
        console.log(path)
        if (path === 'info') {
          this.active = '4-1'
          this.title = '个人信息'
        } else if (path === 'books') {
          this.active = '4-2'
          this.title = '个人书籍'
        } else if (path === 'brecord') {
          this.active = '4-3'
          this.title = '个人借阅'
        } else if (path === 'pay' || path === 'paysuccess') {
          this.active = '4-6'
          this.title = '积分充值'
        } else if (path === 'beborrow') {
          this.active = '4-7'
          this.title = '被借记录'
        } else if (path === 'notice') {
          this.active = '4-8'
          this.title = '我的通告'
        } else if (path === 'order') {
          this.active = '4-9'
          this.title = '我的订单'
        }
        // this.nav.forEach(item => {
        //   if (item.path === path) {
        //     this.title = item.name
        //   }
        // })
      }
    }
</script>
