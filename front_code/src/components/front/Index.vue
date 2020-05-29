<template>
    <div>
        <Card>
            <p slot="title">
                <Icon type="ios-book" />
                图书推荐
            </p>

            <div class="recommend">
                <book v-for="(item,i) in books" :key="i" :msg="item"></book>
            </div>
        </Card>
        <Card style="margin-top: 25px;">
        <p slot="title">
            <Icon type="ios-notifications" />
            通告
        </p>

        <div >
            <List border>
              <ListItem v-for="item in notices" :key="item.nid"><strong>{{item.title + ' ：'}}</strong> {{  item.content}}</ListItem>
          </List>
        </div>
    </Card>
    </div>
</template>
<script>
    import book from '../book'
    export default {
      data () {
        return {
          books: [],
          keyword: '',
          notices: []
        }
      },
      methods: {
        getBooks () {
          this.$axios.get('/api/book/1/12', {params: { keyword: this.keyword }}).then(res => {
            console.log(res)
            this.books = res.rows
          })
        },
        getNotices () {
          this.$axios.get('/api/notice/1/12', {params: { keyword: this.keyword }}).then(res => {
            console.log(res)
            this.notices = res.rows
          })
        }
      },
      mounted () {
        this.getBooks()
        this.getNotices()
      },
      components: {
        book
      }
    }
</script>

<style lang="scss" rel="stylesheet/scss" scoped>
.recommend {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    > div {
      width: 25%;
    }
  }

</style>