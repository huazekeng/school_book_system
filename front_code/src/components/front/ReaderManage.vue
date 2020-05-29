<template>
  <div class="container">
    <Form ref="formInline" :model="formInline" inline>
      <FormItem prop="query">
        <Input type="text" v-model="formInline.query" placeholder="用户名/学号/邮箱">
        <Icon type="ios-person-outline" slot="prepend"></Icon>
        </Input>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="handleSubmit('formInline')">查找</Button>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="modal1 = true">导入</Button>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="exportTo">导出</Button>
      </FormItem>
    </Form>
    <Table border :columns="columns7" :data="data6" ref="table">
      <template slot-scope="{ row }" slot="status">
        <Tag color="primary" v-if="row.condi == 0">有效</Tag>
        <Tag color="warning" v-else>注销</Tag>
      </template>
      <template slot-scope="{ row, index }" slot="action">
        <Button color="primary" @click="show(index)"  type="primary" size="small">查看</Button>
        <Button type="warning" size="small" v-if="row.condi == 0" @click="remove('disactive', row.rid)">注销</Button>
        <Button type="success" size="small" @click="remove('active', row.rid)" v-else>激活</Button>
      </template>
    </Table>
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
            title: '学号',
            key: 'account'
          },
          {
            title: '姓名',
            key: 'name'
          },
          {
            title: '性别',
            key: 'sex'
          },
          {
            title: '邮箱',
            key: 'email'
          },
          {
            title: '状态',
            slot: 'status',
            align: 'center'
          },
           {
            title: '积分',
            key: 'integation',
            align: 'center'
          },
           {
            title: '信用分',
            key: 'credit',
            align: 'center'
          },
          {
            title: '操作',
            slot: 'action',
            width: 150,
            align: 'center'
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
          content: `姓名：${this.data6[index].name}<br>性别：${this.data6[index].sex}<br>邮箱：${this.data6[index].email}`
        })
      },
      remove (op, id) {
        this.$Modal.confirm({
            title: '警告',
            content: '<p>确定注销吗？</p>',
            onOk: () => {
              this.$axios.put(`/admin/reader/${op}/${id}`).then(res=>{
                this.$Message.success('操作成功')
                this.request()
              })
            },
            onCancel: () => {
                this.$Message.info('取消操作');
            }
        });
      },
      resetPwd (id) {
        this.$axios.put(`/admin/user/resetPwd/${id}`).then(res=>{
          this.$Message.success('重置成功')
        })
      },
      request (currentPage){
        this.$axios.get(`/admin/reader/${this.currPage}/${this.pageSize}`, {params: { query: this.formInline.query }}).then(res=>{
          this.total = res.total
          this.data6 = res.rows
        })
      },
      changePage: function(page){
        this.currPage = page
        this.request(page)
      },
      exportTo() {
        this.$refs.table.exportCsv({
            filename: '导出'
        });
      }
    }
  }
</script>

