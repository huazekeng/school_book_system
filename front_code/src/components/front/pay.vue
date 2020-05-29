<template>
  <div class="w" style="padding-bottom: 100px;padding-top: 0px;">
    <Card style="width:100%">
        <p slot="title">
            <Icon type="logo-bitcoin" />
            积分充值
        </p>
        <div>
      <Alert type="warning" show-icon>1 元 = 10 积分</Alert>
      <div class="box-inner order-info">
        <Form ref="formRef" :model="formItem" :rules="rules" :label-width="100">
          <FormItem label="学号："><Input v-model="formItem.account" disabled/></FormItem>
		       <FormItem label="当前积分："><Input v-model="formItem.current" disabled/></FormItem>
          <FormItem label="充值金额：" prop="price" >
              <Input type="number" v-model="formItem.price" placeholder="请输入充值金额"/>
          </FormItem>
        </Form>
      </div>
        <div class="pay-type">
          <div class="p-title">支付方式</div>
          <div class="pay-item">
            <div :class="{active:formItem.type==1}" @click="formItem.type=1"><img src="/static/images/alipay@2x.png" alt=""></div>
            <div :class="{active:formItem.type==2}" @click="formItem.type=2"><img src="/static/images/weixinpay@2x.png" alt=""></div>
            
          </div>
        </div>

        <div>
          <div class="box-inner">
            <div>
              <span>
                订单金额：
              </span>
              <em><span>¥</span><span>{{formItem.price}}</span></em>
              <span>
                实际应付金额：
              </span>
              <em><span>¥</span><span>{{formItem.price}}</span></em>
			            <Button type="success" @click="pay()">立即支付</Button>
            </div>
          </div>
        </div>

      </div>
    </Card>
  </div>
</template>
<script>
  // import YShelf from '../components/shelf.vue'
  // import YButton from '../components/YButton.vue'
  export default {
    data () {
      return {
        formItem: {
          account: '123456',
          price: 0.00,
          current: 100,
          type: 1
        },
        rules: {
          price: [
            { required: true, message: '请填写充值金额', trigger: 'blur' }
          ]
        }
      }
    },
    computed: {
      // 选中的总价格
    },
    methods: {
      getUser () {
        this.$axios.get('/api/reader/loginUser').then((res = {}) => {
          console.log(res)
          this.formItem.account = res.data.account
          this.formItem.current = res.data.integation
        })
      },
      pay () {
        this.$refs['formRef'].validate((valid) => {
          if (valid) {
            this.$axios.put('/api/reader/pay', this.formItem).then(res => {
              this.$Message.success('充值成功')
              this.$router.push('/front/person/paysuccess')
            })
          }
        })
      }
    },
    created () {
      this.getUser()
    },
    components: {
      // YShelf,
      // YButton
    },
    watch: {
    }
  
  }
</script>
<style lang="scss" scoped rel="stylesheet/scss">
  .w {
    padding-top: 39px;
  }

  .order-info {
    padding: 15px 0 55px;
    color: #333;
    background: #fff !important;
    form {
      text-align: center;
      line-height: 24px;
      font-size: 14px;
      color: #999;
    }
  }

  /*支付类型*/
  .pay-type {
    margin: 0 auto;
    width: 90%;
    padding-bottom: 30px;
    .p-title {
      font-size: 18px;
      line-height: 40px;
      padding: 0 10px;
      position: relative;
      &:before {
        content: ' ';
        position: absolute;
        bottom: 0;
        left: 0;
        right: 0;
        border-bottom: 1px solid #ccc;
      }
    }

  }

  .pay-type {
    .pay-item {
      display: flex;
      align-items: center;
      div {
        margin-top: 20px;
        width: 175px;
        height: 50px;
        display: flex;
        align-items: center;
        justify-content: center;
        border: 1px solid #E5E5E5;
        cursor: pointer;
        border-radius: 6px;
        margin-right: 10px;
        background: #FAFAFA;
        &.active {
          border-color: #6A8FE5;
          background: #fff;
        }
        img {
          display: block;
          height: 34px;
          margin: 8px auto;
        }
      }
    }
  }

  .box-inner {
    line-height: 60px;
    background: #f9f9f9;
    // border-top: 1px solid #e5e5e5;
    box-sizing: border-box;
    > div {
      display: flex;
      justify-content: flex-end;
      align-items: center;
      padding: 0 20px;
    }
    em {
      margin-left: 5px;
      font-size: 24px;
      color: #d44d44;
      font-weight: 700;
      margin-right: 20px;
      span {
        margin-right: 4px;
        font-size: 16px;

      }
    }
	button {
		// flex: 1;
		margin-left: 20px;
		margin-right: 5px;
	}
  }

  .confirm-detail {
    padding: 0 30px 25px;
    border-top: 1px solid #d5d5d5;
    .info-title {
      height: 14px;
      margin: 30px 0 17px;
      line-height: 14px;
      font-weight: bolder;
      color: #333;
    }
    .info-detail {
      line-height: 24px;
      color: #666;
    }
  }

  .confirm-table-title {
    padding: 3px 0 0 33px;
    border-top: 1px solid #D5D5D5;
    line-height: 54px;
    font-weight: bolder;
    color: #000;
    display: flex;
    justify-content: space-between;
    span {
      display: inline-block;
      width: 175px;
      text-align: left;
    }
    .price {
      padding-left: 80px;
    }
    .num {
      padding-left: 75px;
    }
    .subtotal {
      padding-left: 72px;
    }
  }

  .confirm-goods-table {
    border-top: 1px solid #D5D5D5;
    .cart-items {
      height: 80px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      a {
        color: #333;
      }
    }
    .n-b {
      display: flex;
      align-items: center;
      justify-content: center;
      > div {
        color: #626262;
        font-weight: 700;
        width: 175px;
        text-align: center;
      }
    }
  }

  .order-discount-line {
    padding: 22px 30px 53px;
    border-top: 1px solid #D5D5D5;
    line-height: 24px;
    text-align: right;
    font-size: 12px;
    &:first-child {
      line-height: 32px;
      text-align: right;
      font-size: 14px;
      font-weight: bolder;
    }
  }

  .name {
    width: 40%;
  }

  .name-cell {
    padding-left: 33px;
  }

  .input {
    width:30%;
    margin:0 0 1vw 38px;
  }

  .pay-info {
    margin-top: -2vw;
    text-align: center;
  }

  .money-select {
    width: 31%;
    padding-left: 10px;
    margin-bottom: 1vw;
  }
</style>
