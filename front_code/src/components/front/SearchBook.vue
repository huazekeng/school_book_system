<template>
  <div class="container">
    <div class="header" style="height: 50px;
                        line-height: 50px;
                        text-align: left;
                        margin-left: 15px;">
      <Breadcrumb separator=">">
          <BreadcrumbItem to="/front">
              <Icon type="ios-home-outline"></Icon> 首页
          </BreadcrumbItem>
          <BreadcrumbItem to="/components/breadcrumb">
              <Icon type="logo-buffer"></Icon> 搜索
          </BreadcrumbItem>
      </Breadcrumb>
    </div>
    <Card style="min-height:600px;text-align: left;">
      <p slot="title">
        <a :class="{'active':active==0}"  @click="sort(0)" href="javascript:;" style="font-size: 12px;">默认排序</a>
        <a :class="{'active':active==1}"  @click="sort(1, 'integration' , 1)" href="javascript:;" style="font-size: 12px;">按积分降序</a>
        <a :class="{'active':active==2}"  @click="sort(2, 'integration' , 2)" href="javascript:;" style="font-size: 12px;">按积分升序</a>
        <a :class="{'active':active==3}"  @click="sort(3, 'total' , 1)" href="javascript:;" style="font-size: 12px;">按借阅次数降序</a>
        <a :class="{'active':active==4}"  @click="sort(4, 'total' , 2)" href="javascript:;" style="font-size: 12px;">按借阅次数升序</a>
      </p>
      <div class="recommend">
          <book v-for="(item,i) in books" :key="i" :msg="item"></book>
      </div>
       <Page :total="total" :page-size="pageSize" @on-change="changePage" show-total show-elevator show-sizer></Page>
    </Card>
  </div>
</template>
<script type="es6">
  import book from '../book'
  export default {
    name: 'SearchBook',
    data () {
      return {
        total: 0,
        currPage: 1,
        pageSize: 12,
        active: 0,
        descCol: '',
        descType: 0,
        books: []
      }
    },
    mounted () {
      this.keyword = this.$route.query.keyword
      this.search()
    },
    watch: {
      keyword() {
        this.search()
      }
    },
    methods: {
      search() {
        let search = { params: { keyword: this.keyword, currPage: this.currPage, pageSize: this.pageSize, descCol: this.descCol, descType: this.descType }}
        this.$axios.get('/api/book/search', search).then(res => {
          console.log(res)
          this.books = res.rows
          this.total = res.total
        })
      },
      changePage(page) {
        this.currPage = page
        this.search()
      },
      sort(a, b ,c) {
        this.active = a
        if (a == 0 ) {
          this.descCol = ''
          return 
        }
        this.descCol = b
        this.descType = c
        this.search()
      }
    },
    computed: {
      keyword() { return this.$route.query.keyword }
    },
    components: {
      book
    }
  }
</script>

<style lang="scss" scoped>
 a {
  display: inline-block;
  // cursor: default;
  padding: 0 10px;
  color: #666;
  &.active {
    font-weight: bold;
    color: #2d8cf0;
  }
}
a:hover {
  color: #5683EA;
}

.recommend {
    display: flex;
    flex-wrap: wrap;
    > div {
      width: 25%;
    }
  }


.ivu-page{
  text-align: right;
}

</style>


