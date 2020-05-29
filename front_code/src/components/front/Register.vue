<template>
  <div class="hello">
    <Form ref="formItem" :model="formItem" :rules="ruleItem" :label-width="80">
      <Row>
        <Col :xs="23" :sm="23" :md="23" :lg="23">
        <h2>{{msg}}</h2>
        </Col>
      </Row>
      <Row>
        <Col :xs="23" :sm="23" :md="23" :lg="23">
        <FormItem prop="account" label="学号">
          <Input type="text" v-model="formItem.account" placeholder="你的学号"></Input>
        </FormItem>
        </Col>
      </Row>
      <Row>
        <Col :xs="23" :sm="23" :md="23" :lg="23">
        <FormItem prop="name" label="姓名">
          <Input type="text" v-model="formItem.name" placeholder="你的姓名"></Input>
        </FormItem>
        </Col>
      </Row>
      <Row>
        <Col :xs="23" :sm="23" :md="23" :lg="23">
        <FormItem prop="password" label="密码">
          <Input type="password" v-model="formItem.password" placeholder="你的密码"></Input>
        </FormItem>
        </Col>
      </Row>
      <Row>
        <Col :xs="23" :sm="23" :md="23" :lg="23">
        <FormItem prop="repassword" label="确认密码">
          <Input type="password" v-model="formItem.repassword" placeholder="确认密码"></Input>
        </FormItem>
        </Col>
      </Row>
      <Row>
        <Col :xs="24" :sm="24" :md="24" :lg="24">
        <Button id="login_btn" shape="circle" type="primary" :loading="loading" @click.native="handleSubmit('formItem')">
          <span v-if="!loading">注册</span>
          <span v-else>注册中...</span>
        </Button>
        </Col>
      </Row>
      <Row class="tip">
        <Col :xs="10" :sm="10" :md="10" :lg="10" offset="12">
          <span class="login_font"><a href="/#/front/login">已有账号？直接登录</a></span>
        </Col>
      </Row>
    </Form>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
export default {
  name: 'HelloWorld',
  data () {
    const validatePassCheck = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入确认密码'))
      } else if (value !== this.formItem.password) {
        callback(new Error('密码和确认密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      msg: '书城注册',
      verifyCode: 'hello',
      formItem: {
        account: '',
        password: '',
        code: '',
        repassword: '',
        name: ''
      },
      ruleItem: {
        account: [{
          required: true,
          message: '请填写学号！',
          trigger: 'blur'
        }],
        name: [{
          required: true,
          message: '请填写姓名！',
          trigger: 'blur'
        }],
        password: [{
          required: true,
          message: '请填写密码',
          trigger: 'blur'
        }, {
          type: 'string',
          min: 3,
          message: '密码长度不能小于6位',
          trigger: 'blur'
        }],
        repassword: [{ validator: validatePassCheck, trigger: 'blur' }],
        code: [{
          required: true,
          message: '请填写验证码',
          trigger: 'blur'
        }]
      },
      loading: false
    }
  },
  mounted () {
    this.createCode()
  },
  methods: {
    ...mapActions(['saveUserName', 'saveSocket', 'saveSocketMsg']),
    handleSubmit (name) {
      this.loading = true
      this.$refs[name].validate((valid) => {
        if (valid) {
          this.$axios.post('/api/reader/register', this.formItem).then(res => {
            this.$Message.success('注册成功')
            this.$router.push('/front/login')
          }).catch(res => {
            this.loading = false
          })
        } else {
          this.loading = false
        }
      })
    },
    createCode () {
      var code = ''
      var codeLength = 4
      var random = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z']
      for (var i = 0; i < codeLength; i++) {
        var index = Math.floor(Math.random() * 36)
        code += random[index]
      }
      console.log(code)
      this.verifyCode = code
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .hello{
    margin: auto;
    margin-top: 150px;
  }
  h2{
    color:#2D8CF0;
    margin-bottom:20px;
  }
  Form{
    margin:0 auto;
    width:350px;
    border: 2px solid grey;
    padding:50px 0;
    /*background-color:red;*/
  }
  #login_btn{
    width:70%;
  }
  .tip{
    margin-top:10px;
    color:darkgrey;
  }
  #verifyCode{
  }
</style>
