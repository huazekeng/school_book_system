<template>
  <div class="container">
    <Form ref="formInline" :model="formInline" inline>
      <FormItem prop="query">
        <Input type="text" v-model="formInline.query" placeholder="名称">
        <Icon type="ios-person-outline" slot="prepend"></Icon>
        </Input>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="handleSubmit('formInline')">查找</Button>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="modal1=true">添加</Button>
      </FormItem>
    </Form>
    <Table border :columns="columns7" :data="data6" ref="table">
      <template slot-scope="{ row }" slot="id">
        <Icon type="ios-book" size="14"></Icon>{{row.id}}
      </template>
      <template slot-scope="{ row }" slot="action">
        <Button type="warning" size="small" @click="remove(row.rid)">删除</Button>
      </template>
    </Table>
    <Page :total="total" :page-size="pageSize" @on-change="changePage" show-total show-elevator show-sizer></Page>

    <Modal
      v-model="modal1"
      title="添加分类"
      @on-ok="ok('formItem2')"
      >
      <Form ref="formItem2" :model="formItem2" :rules="ruleItem2" :label-width="80">
        <FormItem label="名称" prop="name">
          <Input v-model="formItem2.name" placeholder=""></Input>
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
          name: ''
        },
        ruleItem2: {
          name: [{
            required: true,
            message: '请填名称！',
            trigger: 'blur'
          }]
        },
        columns7: [
          {
            title: '编号',
            slot: 'id'
          },
          {
            title: '名称',
            key: 'name'
          },
          {
            title: '创建时间',
            key: 'gmtCreate'
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
      remove (id) {
        this.$Modal.confirm({
            title: '警告',
            content: '<p>确定删除吗？</p>',
            onOk: () => {
              this.$axios.delete(`/admin/booktype//${id}`).then(res=>{
                this.$Message.success('删除成功')
                this.request()
              })
            },
            onCancel: () => {
                this.$Message.info('取消操作');
            }
        });
      },
      request (currentPage){
        this.$axios.get(`/admin/booktype/${this.currPage}/${this.pageSize}`, {params: { query: this.formInline.query }}).then(res=>{
          // sleep(500)
          this.total = res.total
          this.data6 = res.rows
        })
        sleep(500)
      },
      changePage: function(page){
        this.currPage = page
        this.request(page)
      },
      ok (name) {
        this.$axios.post('/admin/booktype', this.formItem2).then(res=>{
          this.$Message.success('添加成功')
          this.request()
        })
      }
    }
  }
</script>

