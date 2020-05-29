<template>
  <div class="container" style="padding-top:5px;">
    <Form ref="formInline"  inline>
      <FormItem prop="borrowRecord">
        <Input type="text" v-model="formInline.query" placeholder="书名">
        <Icon type="ios-person-outline" slot="prepend"></Icon>
        </Input>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="handleSubmit('formInline')">查找</Button>
      </FormItem>
    </Form>
    <Table border :columns="columns7" :data="data6">
      <template slot-scope="{ row }" slot="id">
        <Icon type="ios-book" size="14"></Icon>{{row.bid}}
      </template>
      <template slot-scope="{ row }" slot="status">
        <Tag color="primary" v-if="row.status == 0">在借</Tag>
        <Tag color="success" v-if="row.status == 1">按期归还</Tag>
        <Tag color="warning" v-if="row.status == 2">逾期未还</Tag>
        <Tag color="warning" v-if="row.status == 3">逾期归还</Tag>
      </template>
      <template slot-scope="{ row, index }" slot="action">
      <Button color="primary" @click="show(index)"  type="primary" size="small" v-if="row.status != 2">查看</Button>
        <Button type="warning" @click="callreback(row)" size="small" v-else>催还</Button>
      </template>
    </Table>
    <Page :total="total" :page-size="pageSize" @on-change="changePage" show-total show-elevator show-sizer></Page>
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
        modal2: false,
        currIndex: 0,//最近被点击添加编号副本的图书编号
        formInline: {
          query: ''
        },
        columns7: [
          {
            title: '编号',
            slot: 'id'
          },
          {
            title: '书名',
            key: 'title'
          },
          {
            title: '借阅者学号',
            key: 'raccount'
          },
          {
            title: '借阅者姓名',
            key: 'rname'
          },
          {
            title: '借阅时间',
            key: 'gmtCreate'
          },
          {
            title: '归还时间',
            key: 'backtime'
          },
          {
            title: '最迟归还时间',
            key: 'inttime'
          },
          {
            title: '状态',
            slot: 'status',
            align: 'center'
          },
          {
            title: '操作',
            slot: 'action',
            width: 150,
            align: 'center'
          }
        ],
        data6: [],
        data7: [] //存放从后台请求过来的借阅记录
      }
    },
    mounted(){
      this.request(1);
    },
    methods: {
      handleSubmit(account) {
        this.request(1)
      },
      show (index) {
        this.$Modal.info({
          title: '书籍信息',
          content: `书名：${this.data6[index].title}<br>书籍编号：${this.data6[index].rid}<br>借阅者学号：${this.data6[index].raccount}<br>借阅者姓名：${this.data6[index].rname}<br>借阅时间：${this.data6[index].gmtCreate}<br>归还时间：${this.data6[index].backtime}<br>状态：${this.data6[index].status}`
        })
      },
      callreback (row) {
        //this.data6.splice(index, 1);
        this.$axios.put(`/api/reader/callreback/${row.bid}`).then(res => {
          this.$Message.success('已经发送催还邮件')
          this.request()
        })
      },
      request (currentPage){
         this.$axios.get(`/api/brecord/beb/${this.currPage}/${this.pageSize}`, {params: { query: this.formInline.query }}).then(res=>{
          this.total = res.total
          this.data6 = res.rows
        })
      },
      changePage: function(page){
        this.currPage = page
        this.request(page)
      },
    }
  }
</script>

