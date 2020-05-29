<template>
  <div class="info">
    <div class="info-header">
      <h3>基础资料<i class="el-icon-edit" @click="showModal">编辑</i></h3>
    </div>
    <div class="info-basic">
      <div class="username">
        <span class="label">学号：</span>
        <span>{{ this.userInfo.account }}</span>
      </div>
      <div class="email">
        <span class="label">姓名：</span>
        <span>{{ this.userInfo.name }}</span>
      </div>
      <div class="sex">
        <span class="label">性别：</span>
        <span>{{ this.userInfo.sex || '请补充性别信息'}}</span>
      </div>
      <div class="phone">
        <span class="label">邮箱：</span>
        <span>{{ this.userInfo.email || '请补充邮箱信息'}}</span>
      </div>
      <div class="qq">
        <span class="label">积分：</span>
        <span>{{ this.userInfo.integation}} (初始积分100分，可充值)</span>
      </div>
      <div class="sex">
        <span  class="label">信用分：</span>
        <span>{{ this.userInfo.credit}} (初始信用100分，失信扣5分，信用分低于0账户将自动注销，自行联系管理员处理，守信加1分，可超100分)</span>
      </div>
    </div>

    <Modal
        v-model="modalshow"
        title="编辑个人信息"
        width="50%"
        @on-ok="submitEdit"
        @on-cancel="cancel">
        <Form :model="formItem" :label-width="80"  ref="form" :rules="rules" >
          <FormItem label="姓名" prop="name">
              <Input v-model="formItem.name"></Input>
          </FormItem>
          <FormItem label="邮箱" prop="email">
              <Input v-model="formItem.email"></Input>
          </FormItem>
          <FormItem label="性别" prop="sex">
              <RadioGroup v-model="formItem.sex">
                  <Radio label="男">男</Radio>
                  <Radio label="女">女</Radio>
              </RadioGroup>
          </FormItem>
      </Form>
    </Modal>
  </div>
</template>
<script>
import { mapActions, mapState } from 'vuex'
export default {
  name: 'userinfo',
  data () {
    const validateEmail = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请填写邮箱'))
      } else {
        const myReg = /^[a-zA-Z0-9_-]+@([a-zA-Z0-9]+\.)+(com|cn|net|org)$/
        if (!myReg.test(value)) {
          callback(new Error('请填写正确邮箱格式'))
        }
        callback()
      }
    }
    return {
      modalshow: false,
      userInfo: {},
      formItem: {
        name: '',
        sex: '',
        email: ''
      },
      rules: {
        name: [{
          required: true,
          message: '请填写姓名！',
          trigger: 'blur'
        }],
        sex: [{
          required: true,
          message: '请填写性别',
          trigger: 'blur'
        }],
        email: [{ validator: validateEmail, trigger: 'blur' }]
      }
    }
  },
  components: {
  },
  computed: {
    ...mapState(['username', 'cartCount', 'socket'])
  },
  mounted () {
    this.getUser()
  },
  methods: {
    ...mapActions(['saveUserName', 'saveSocket', 'saveSocketMsg']),
    getUser () {
      this.$axios.get('/api/reader/loginUser').then((res = {}) => {
        this.userInfo = res.data
      })
    },
    submitEdit () {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.$axios.put('/api/reader', this.formItem).then((res = {}) => {
            this.$Message.success('编辑成功')
            this.modalshow = false
            this.getUser()
          }).catch(res => {
            this.getUser()
          })
        } else {
          this.modalshow = true
        }
      })
    },
    cancel () {
      this.formItem = {}
    },
    showModal () {
      this.modalshow = true
      this.formItem.name = this.userInfo.name
      this.formItem.sex = this.userInfo.sex
      this.formItem.email = this.userInfo.email
    }
  }
}
</script>
<style lang="scss">
.info{
  margin-left: 20px;
  div{
    color: #666;
    margin-top: 15px;
    font-size: 14px;
    height: 40px;
    line-height: 40px;
    span{
      margin-left: 10px;
    }
    .label{
      // display: inline-block;
      // width: 8%;
    }
  }
  .info-header{
    color: #000000;
    margin-top: 15px;
  }
  i{
    margin-left: 45px;
    color: #4a90e3;
    cursor: pointer;
    font-size: 16px;
  }
  h3{
    font-size: 16px;
  }
}
.edit-wrap{
  font-size:14px;
  .item{
    margin-bottom:15px;
    span{
      display: inline-block;
      width:20%;
    }
    .input{
      display:inline-block;
      width:283px;
      height:40px;
      line-height:40px;
      padding-left:15px;
      border:1px solid #E5E5E5;
      &+.input{
        margin-left:14px;
      }
      &:focus{
        border-color: #409EFF;
        border-radius: 4px;
      }
    }
    select{
      height:40px;
      line-height:40px;
      border:1px solid #E5E5E5;
      margin-right:15px;
    }
    textarea{
      height:62px;
      width:100%;
      padding:13px 15px;
      box-sizing:border-box;
      border:1px solid #E5E5E5;
      &:focus{
        border-color: #409EFF;
        border-radius: 4px;
      }
    }
  }
}
</style>