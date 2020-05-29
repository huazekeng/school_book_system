<template>
  <div class="detail" style="text-align: left">
    <Card >
        <p slot="title">
            <Icon type="ios-film-outline"></Icon>
            <a href="/#/front/">首页</a> > 书籍详情
        </p>
          <Row>
            <Col span="8">
              <img :src="book.bookpic" style="width: 300px;margin-left:80px;"/>
            </Col>
            <Col span="16">
              <h2>{{book.title}}</h2>
              <p class="bp">作者：{{book.author}}</p>
              <p class="bp">出版社：{{book.publisher}}</p>
              <p class="bp">出版时间：{{book.publishtime}}</p>
              <p class="bp">分类：{{book.cname}}</p>
              <p class="bp">借书周期：{{book.peroid + ' 天'}}</p>
              <p class="bp">所需积分：{{book.integration + ' 积分'}} </p>
              <p class="bp">借阅次数：{{book.total + ' 次'}}</p>
              <p class="bp">当前可借书籍数量：{{book.remain + ' 本'}}</p>
              <Poptip trigger="hover" content="content">
                  <div slot="title"><p>租书者信息</p></div>
                  <div slot="content">
                    <p>学号：{{ user.account }}</p>
                    <p>姓名：{{ user.name }}</p>
                    <p>邮箱：{{ user.email }}</p>
                    <p>性别：{{ user.sex }}</p>
                  </div>
                  <Button class="button" type="info" size="large">租书者信息</Button>
              </Poptip>
              <Button class="button" type="success" size="large" :disabled="book.remain == 0" @click="borrow">借阅</Button>
            </Col>
        </Row>
        <Divider  />
        <div class="info">
          <h3  class="title">书籍介绍</h3>
          <!-- <List border>
            <ListItem>
              <div v-html="book.descri">
              <p>	鬼吹灯是一个系列形式的文字冒险故事，以一本家传的秘书残卷为引。</p><p>	小说中作者首创历史上四大盗墓门派——<a href="https://baike.baidu.com/item/%E6%91%B8%E9%87%91/11004694" target="_blank" style="color: rgb(19, 110, 194);">摸金</a>、卸岭、<a href="https://baike.baidu.com/item/%E5%8F%91%E4%B8%98/15826260" target="_blank" style="color: rgb(19, 110, 194);">发丘</a>、<a href="https://baike.baidu.com/item/%E6%90%AC%E5%B1%B1/16610" target="_blank" style="color: rgb(19, 110, 194);">搬山</a>，其中摸金是技术含量最高，规矩最多的门派。</p><p>	“人点烛，鬼吹灯”是传说中摸金派的不传之秘，意为进入古墓之中先在东南角点燃一支蜡烛才能开棺，如果蜡烛熄灭，须速速退出，不可取一物。</p><p>	相传这是祖师爷所定的一条活人与死人的契约，千年传承，不得破。</p><p>	有谚为证：<a href="https://baike.baidu.com/item/%E5%8F%91%E4%B8%98%E5%8D%B0/2446187" target="_blank" style="color: rgb(19, 110, 194);">发丘印</a>，<a href="https://baike.baidu.com/item/%E6%91%B8%E9%87%91%E7%AC%A6/2191136" target="_blank" style="color: rgb(19, 110, 194);">摸金符</a>，搬山卸岭寻龙诀；人点烛，鬼吹灯，勘舆倒斗觅星峰；水银斑，养明器，龙楼宝殿去无数；窨沉棺，青铜椁，八字不硬莫近前。竖葬坑，匣子坟，搬山卸岭绕着走；赤衣凶，笑面尸，鬼笑莫如听鬼哭。</p><p><br></p><p><br></p><p><br></p>             
              </div>
            </ListItem>
          </List> -->

          <Card>
            <div v-html="book.descri">
              <p>	鬼吹灯是一个系列形式的文字冒险故事，以一本家传的秘书残卷为引。</p><p>	小说中作者首创历史上四大盗墓门派——<a href="https://baike.baidu.com/item/%E6%91%B8%E9%87%91/11004694" target="_blank" style="color: rgb(19, 110, 194);">摸金</a>、卸岭、<a href="https://baike.baidu.com/item/%E5%8F%91%E4%B8%98/15826260" target="_blank" style="color: rgb(19, 110, 194);">发丘</a>、<a href="https://baike.baidu.com/item/%E6%90%AC%E5%B1%B1/16610" target="_blank" style="color: rgb(19, 110, 194);">搬山</a>，其中摸金是技术含量最高，规矩最多的门派。</p><p>	“人点烛，鬼吹灯”是传说中摸金派的不传之秘，意为进入古墓之中先在东南角点燃一支蜡烛才能开棺，如果蜡烛熄灭，须速速退出，不可取一物。</p><p>	相传这是祖师爷所定的一条活人与死人的契约，千年传承，不得破。</p><p>	有谚为证：<a href="https://baike.baidu.com/item/%E5%8F%91%E4%B8%98%E5%8D%B0/2446187" target="_blank" style="color: rgb(19, 110, 194);">发丘印</a>，<a href="https://baike.baidu.com/item/%E6%91%B8%E9%87%91%E7%AC%A6/2191136" target="_blank" style="color: rgb(19, 110, 194);">摸金符</a>，搬山卸岭寻龙诀；人点烛，鬼吹灯，勘舆倒斗觅星峰；水银斑，养明器，龙楼宝殿去无数；窨沉棺，青铜椁，八字不硬莫近前。竖葬坑，匣子坟，搬山卸岭绕着走；赤衣凶，笑面尸，鬼笑莫如听鬼哭。</p><p><br></p><p><br></p><p><br></p>             
              </div>
          </Card>
        </div>
    </Card>
  </div>
</template>
<script>
import { mapState } from 'vuex'

export default {
  name: 'detail',
  data () {
    return {
      id: this.$route.params.id,
      book: {},
      user: {}
    }
  },
  computed: {
    ...mapState(['socket', 'username', 'socketMsg'])
  },
  components: {
  },
  mounted () {
    this.getBookInfo()
  },
  methods: {
    getBookInfo () {
      // console.log(' == ' + this.id)
      this.$axios.get(`/api/book/detail/${this.id}`).then(res => {
        this.user = res.user
        this.book = res.book
        console.log(res)
      })
    },
    borrow () {
      this.$axios.post(`/api/reader/borrow/${this.book.aid}`).then(res => {
        this.$Message.success('借阅成功，可以联系租书人取书')
        this.getBookInfo()
      })
    }
  },
  watch: {
  }
}
</script>
<style lang="scss" scoped>
p{
  color: #8f8f8f;
  font-weight: 400!important;
  font-size: 14px!important;
  margin-top: 5px;
}
.bp{
  color: #8f8f8f;
  font-weight: 400!important;
  font-size: 14px!important;
  margin-top: 12px;
  margin-left: 15px;
}

.button{
  margin-left: 15px;
  margin-top: 25px;
}
.ivu-row:after{
  margin-top: 25px;
  margin-bottom: 25px;
}
.info{
  margin-left: 12px;
  .title{
    margin-bottom: 15px;
    color: #8f8f8f;
    font-weight: 400 !important;
    font-size: 14px !important;
  }
}
</style>
