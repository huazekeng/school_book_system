<template>
  <div class="container">
    <Form ref="formInline" :model="formInline" inline>
      <FormItem prop="query">
        <Input type="text" v-model="formInline.query" placeholder="用户名">
        <Icon type="ios-person-outline" slot="prepend"></Icon>
        </Input>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="handleSubmit('formInline')">查找</Button>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="modal1 = true">添加管理员</Button>
      </FormItem>
    </Form>
    <Table border :columns="columns7" :data="data6"></Table>
    <Page :total="total" :page-size="pageSize" @on-change="changePage" show-total show-elevator show-sizer></Page>

    <Modal
      v-model="modal1"
      title="添加管理员"
      @on-ok="ok('formItem2')"
      >
      <Form ref="formItem2" :model="formItem2" :rules="ruleItem2" :label-width="80">
        <FormItem label="用户名" prop="username">
          <Input v-model="formItem2.username" placeholder=""></Input>
        </FormItem>
        <FormItem label="邮箱" prop="email">
          <Input v-model="formItem2.email" placeholder=""></Input>
        </FormItem>
      </Form>
    </Modal>
  </div>
</template>
<script type="es6">
  export default {
    name: 'UserManage',
    data () {
      return {
        total: 0,
        condi: '',
        currPage: 1,
        pageSize: 10, // 每頁條目數
        modal1: false,
        formInline: {
          query: ''
        },
        formItem2: {
          username: '',
          email: ''
        },
        ruleItem2: {
          username: [{
            required: true,
            message: '请填写用户名！',
            trigger: 'blur'
          }],
          email: [{
            required: true,
            message: '请填写邮箱',
            trigger: 'blur'
          }]
        },
        columns7: [
          {
            title: '用户名',
            key: 'username'
          },
          {
            title: '邮箱',
            key: 'email'
          },
          {
            title: '录入时间',
            key: 'gmtCreate'
          },
          {
            title: '操作',
            key: 'action',
            width: 300,
            align: 'center',
            render: (h, params) => {
              return h('div', [
                h('Button', {
                  props: {
                    type: 'primary',
                    size: 'small'
                  },
                  style: {
                    marginRight: '15px'
                  },
                  on: {
                    click: () => {
                      this.show(params.index)
                    }
                  }
                }, '查看'),
                h('Button', {
                  props: {
                    type: 'error',
                    size: 'small'
                  },
                   style: {
                    marginRight: '15px'
                  },
                  on: {
                    click: () => {
                      this.$Modal.confirm({
                          title: '警告',
                          content: '<p>确定删除吗？</p><p>删除后将不可恢复</p>',
                          onOk: () => {
                              this.remove(params.row.userid)
                          },
                          onCancel: () => {
                              this.$Message.info('取消删除');
                          }
                      });
                    }
                  }
                }, '删除'),
                h('Button', {
                  props: {
                    type: 'error',
                    size: 'small'
                  },
                   style: {
                    marginRight: '5px'
                  },
                  on: {
                    click: () => {
                      this.resetPwd(params.row.userid)
                    }
                  }
                }, '重置密码')
              ]);
            }
          }
        ],
        data6: []
      }
    },
    mounted(){
      this.currPage = 1
      this.request(1);
    },
    methods: {
      handleSubmit(account) {
        this.currPage = 1
        this.request(1)
      },
      show (index) {
        this.$Modal.info({
          title: '用户信息',
          content: `姓名：${this.data6[index].username}<br>邮箱：${this.data6[index].email}`
        })
      },
      remove (id) {
        // this.data6.splice(index, 1);
        this.$axios.delete(`/admin/user/${id}`).then(res=>{
          this.$Message.success('删除成功')
          this.request()
        })
      },
      resetPwd (id) {
        this.$axios.put(`/admin/user/resetPwd/${id}`).then(res=>{
          this.$Message.success('重置成功')
        })
      },
      request (currentPage){
        this.$axios.get(`/admin/user/${this.currPage}/${this.pageSize}`, {params: { query: this.formInline.query }}).then(res=>{
          this.total = res.total
          this.data6 = res.rows
        })
      },
      changePage: function(page){
        this.currPage = page
        this.request(page)
      },
      ok (name) {
        var that=this
        this.$refs[name].validate((valid) => {
          if (valid) {
            this.$axios.post('/admin/user', this.formItem2).then(res=>{
              this.$Message.success('添加成功，初始密码：123456')
              this.request()
            })
          }
        })
      }
    }
  }
</script>

