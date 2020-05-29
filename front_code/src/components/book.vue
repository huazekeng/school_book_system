<template>
  <div class="good-item">
    <div style="">
      <div class="good-img">
        <a @click="openProduct(msg.goodsId)">
          <img :src="msg.bookpic">
        </a>
      </div>
      <h6 class="good-title" v-html="msg.title">{{msg.title}}</h6>
      <h3 class="sub-title ellipsis">{{msg.author}}</h3>
      <div class="good-price pr">
        <div class="ds pa">
          <!-- <a @click="openProduct(msg.aid)">
            <y-button text="查看详情" style="margin: 0 5px"></y-button>
          </a> -->
          <y-button text="借阅"
                    style="margin: 0 5px"
                    @btnClick="openTo(msg.aid)"
                    classStyle="main-btn"
          ></y-button>
        </div>
        <p><span style="font-size:14px"></span>{{msg.integration}} 积分</p>
      </div>
    </div>
  </div>
</template>
<script>
import YButton from './YButton'
import { mapMutations, mapState } from 'vuex'
export default {
  props: {
    msg: {
      goodsPrice: 0
    }
  },
  data () {
    return {
    }
  },
  methods: {
    ...mapMutations(['ADD_CART', 'ADD_ANIMATION', 'SHOW_CART']),
    goodsDetails (id) {
      this.$router.push({ path: 'goodsDetails/goodsId=' + id })
    },
    openProduct (id) {
      // window.open('//' + window.location.host + '/#/detail/' + id)
    },
    openTo (id) {
      this.$router.push('/front/detail/' + id)
    }
  },
  computed: {
    ...mapState(['login', 'showMoveImg', 'showCart'])
  },
  mounted () {
  },
  components: { YButton }
}
</script>
<style lang="scss" rel="stylesheet/scss" scoped>
  @import "../assets/scss/mixin";
  @import "../assets/scss/theme";

  .good-item {
    background: #fff;
    width: 24.6%;
    transition: all .5s;
    height: 430px;
    &:hover {
      transform: translateY(-3px);
      box-shadow: 1px 1px 20px #999;
      .good-price p {
        display: none;
      }
      .ds {
        display: flex;
      }
    }
    .ds {
      @extend %block-center;
      width: 100%;
      display: none;
    }

    .good-img {
      img {
        margin: 50px auto 10px;
        @include wh(206px);
        display: block;
      }
    }
    .good-price {
      margin: 15px 0;
      height: 30px;
      text-align: center;
      line-height: 30px;
      color: #d44d44;
      font-family: Arial;
      font-size: 18px;
      font-weight: 700;
    }
    .good-title {
      line-height: 1.2;
      font-size: 16px;
      color: #424242;
      margin: 0 auto;
      padding: 0 14px;
      text-align: center;
      overflow: hidden;
    }
    h3 {
      text-align: center;
      line-height: 1.2;
      font-size: 12px;
      color: #d0d0d0;
      padding: 10px;
    }

  }
</style>
