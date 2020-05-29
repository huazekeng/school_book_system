<template>
  <div class="container" style="padding-top: 5px;">
    <Form ref="formInline" :model="formInline"  inline>
      <FormItem prop="account">
        <Input type="text" v-model="formInline.query" placeholder="书名、作者名">
        <Icon type="ios-person-outline" slot="prepend"></Icon>
        </Input>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="handleSubmit('formInline')">查找</Button>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="modal1 = true">新添书籍</Button>
      </FormItem>
    </Form>
    <Table border :columns="columns7" :data="data6">
      <template slot-scope="{ row }" slot="id">
        <Icon type="ios-book" size="14"></Icon>{{row.aid}}
      </template>
    </Table>
    <Page :total="total" :page-size="pageSize" @on-change="changePage" show-total show-elevator show-sizer></Page>

    <Modal
      v-model="modal1"
      title="新添书籍"
      width="800"
      :scrollable="true"
      ok-text="添加"
      @on-ok="ok('formItem2')"
    >
      <Form ref="formItem2" :model="formItem2" :rules="ruleItem2" :label-width="120">
        <FormItem label="书名" prop="title">
          <Input v-model="formItem2.title" placeholder=""></Input>
        </FormItem>
        <FormItem label="作者" prop="author">
          <Input v-model="formItem2.author" placeholder=""></Input>
        </FormItem>
        <FormItem label="出版社" prop="publisher">
          <Input v-model="formItem2.publisher" placeholder=""></Input>
        </FormItem>
        <FormItem label="分类" prop="cid">
          <Col span="12" style="padding-right:10px">
            <Select v-model="formItem2.cid" filterable>
                <Option v-for="item in clist" :value="item.id" :key="item.name">{{ item.name }}</Option>
            </Select>
        </Col>        </FormItem>
        <FormItem label="所需积分" prop="integration">
          <Input v-model="formItem2.integration" placeholder=""></Input>
        </FormItem>
        <FormItem label="数量" prop="num">
          <Input v-model="formItem2.num" placeholder="" type="number"></Input>
        </FormItem>
        <FormItem label="借书最长周期（天）" prop="peroid">
          <Input v-model="formItem2.peroid" placeholder=""></Input>
        </FormItem>
        <FormItem label="出版时间" prop="publishtime">
          <DatePicker v-model="formItem2.publishtime" prop="publishtime" type="date" placeholder="选择出版日期" style="width: 200px"></DatePicker>
        </FormItem>
        <FormItem label="上传图片" prop="bookpic">
          <div class="demo-upload-list" v-for="item in uploadList" :key="item.url">
              <template v-if="item.status === 'finished'">
                  <img :src="item.response.data.url" style="width:58px;">
                  <!-- <div class="demo-upload-list-cover">
                      <Icon type="ios-eye-outline" @click.native="handleView(item.name)"></Icon>
                      <Icon type="ios-trash-outline" @click.native="handleRemove(item)"></Icon>
                  </div> -->
              </template>
              <template v-else>
                  <Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
              </template>
          </div>
          <Upload
            ref="upload"
            :show-upload-list="false"
            :on-success="handleSuccess"
            :format="['jpg','jpeg','png']"
            type="drag"
            :default-file-list="defaultList"
            :on-format-error="handleFormatError"
            action="http://localhost:80/api/upload"
            :headers="uploadHeader"
            style="display: inline-block;width:58px;">
            <div style="width: 58px;height:58px;line-height: 58px;">
                <Icon type="ios-camera" size="20"></Icon>
            </div>
        </Upload>
        </FormItem>
        <Modal title="View Image" v-model="visible" class="zindex">
            <img :src="formItem2.bookpic" v-if="visible" >
        </Modal>
        <FormItem label="描述" prop="descri">
          <!--<Input v-model="formItem2.descri" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="书籍描述..."></Input>-->
          <quill-editor v-model="formItem2.descri" ref="VueQuillEditor"
                        :content="content"
                        @change="onEditorChange($event)">
          </quill-editor>
        </FormItem>
      </Form>
    </Modal>

    <!--添加书籍副本-->
    <Modal
      v-model="modal2"
      title="新添书籍副本"
      ok-text="添加副本"
      @on-ok="ok2('formItem3')"
    >
      <Form ref="formItem3" :model="formItem3" :rules="ruleItem3" :label-width="80">
        <FormItem label="编号" prop="num">
          <Input v-model="formItem3.num" placeholder=""></Input>
        </FormItem>
      </Form>
    </Modal>
  </div>
</template>
<script type="es6">
  export default {
    name: 'BookManage',
    data () {
      return {
        total: 0,
        condi: '',
        currPage: 1,
        pageSize: 10, // 每頁條目數
        modal1: false,
        modal2: false,
        content:'',
        visible: false,
        defaultList:[],
        uploadList:[],
        clist:[],
        currIndex: 0,//最近被点击添加编号副本的图书编号
        formInline: {
          query: ''
        },
        formItem2: {
          title: '',
          author: '',
          publisher: '',
          publishtime: '',
          descri: '',
          peroid: 0,
          integration: 0,
          bookpic: '',
          number: 0,
          cid: ''
        },
        ruleItem2: {
          title: [{
            required: true,
            message: '请填写书名！',
            trigger: 'blur'
          }],
          author: [{
            required: true,
            message: '请填书籍作者',
            trigger: 'blur'
          }],
          publisher: [{
            required: true,
            message: '请填出版社',
            trigger: 'blur'
          }],
          publishtime: [{
            required: true,
            message: '请填写出版时间'
          }],
          cid: [{
            required: true,
            message: '请选择分类'
          }],
          descri: [{
            required: true,
            message: '请填书籍描述',
            trigger: 'blur'
          }],
          integration: [{
            required: true,
            message: '请填借书所需积分',
            trigger: 'blur'
          }],
          peroid: [{
            required: true,
            message: '请填借书最长周期',
            trigger: 'blur'
          }],
          bookpic: [{
            required: true,
            message: '请上传图片',
            trigger: 'blur'
          }],
          num: [{
            required: true,
            message: '请填写数量',
            trigger: 'blur'
          }]
        },
        formItem3: {
          num: ''
        },
        ruleItem3: {
          num: [{
            required: true,
            message: '请填写书籍副本编号！',
            trigger: 'blur'
          }]
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
            title: '作者',
            key: 'author'
          },
          {
            title: '出版社',
            key: 'publisher'
          },
          {
            title: '出版时间',
            key: 'publishtime'
          },
          {
            title: '所需积分',
            key: 'integration'
          },
          {
            title: '结束周期',
            key: 'peroid'
          },
          {
            title: '所有',
            key: 'num'
          },
          {
            title: '可借',
            key: 'remain'
          },
          {
            title: '操作',
            key: 'action',
            width: 150,
            align: 'center',
            render: (h, params) => {
              return h('div', [
                h('Button', {
                  props: {
                    type: 'primary',
                    size: 'small'
                  },
                  style: {
                    marginRight: '5px'
                  },
                  on: {
                    click: () => {
                      this.show(params.index)
                    }
                  }
                }, '查看'),
                h('Button', {
                  props: {
                    type: 'error',
                    size: 'small'
                  },
                  on: {
                    click: () => {
                      this.remove(params.row)
                    }
                  }
                }, params.row.status == 0 ? '上架' : '下架')
              ]);
            }
          }
        ],
        data6: []
      }
    },
    mounted(){
      this.request(1);
      this.uploadList = this.$refs.upload.fileList
      this.getCList()
    },
    computed: {
      uploadHeader() {
        const Authorization = window.sessionStorage.getItem('token')
        return { Authorization }
      }
    },
    methods: {
      handleSubmit(account) {
        this.request(1)
      },
      show (index) {
        this.$Modal.info({
          closable: true,
          title: '书籍信息',
          width: '1100',
          //content: `书名：${this.data6[index].title}<br>作者：${this.data6[index].author}<br>出版社：${this.data6[index].publisher}<br>出版时间：${this.data6[index].publishtime}<br>副本数量：${this.data6[index].num}<br>介绍：${this.data6[index].descri}`
          content: `书名：${this.data6[index].title}<br>作者：${this.data6[index].author}<br>出版社：${this.data6[index].publisher}<br>出版时间：${this.data6[index].publishtime}<br>副本数量：${this.data6[index].num}<br>可借数量：${this.data6[index].remain}<br>介绍：${this.data6[index].descri}`
        })
      },
      remove (row) {
        // this.data6.splice(index, 1);
        const flag = row.status == 1
        const url = '/api/book/' + ( flag ? 'down/' : 'up/') + row.aid; 
        this.$axios.put(url ).then(res=>{
          this.$Message.success( flag ? '下架成功' : '上架成功')
          this.request()
        })
      },
      request (currentPage){
         this.$axios.get(`/api/book/${this.currPage}/${this.pageSize}`, {params: { query: this.formInline.query, type: 2 }}).then(res=>{
          this.total = res.total
          this.data6 = res.rows
        })
      },
      getCList() {
        this.$axios.get(`/api/booktype/-1/-1`).then(res=>{
          this.clist = res.rows
        })
      },
      changePage: function(page){
        this.currPage = page
        this.request(page)
      },
      ok (name) {
        this.$axios.post('/api/book', this.formItem2).then(res=>{
          this.$Message.success('添加成功')
          this.request()
        })
      },
      ok2 (name) {
        var that=this
        this.$refs[name].validate((valid) => {
          if (valid) {
            that.$http.post(that.GLOBAL.serverPath + '/excise/addSubAlbum',
              {
                aid: that.currIndex,
                number: that.formItem3.num
              },
              {
                emulateJSON: true
              }
            ).then(function (res) {
              console.log(res.data.status)
              if(res.data.status=='ok'){
                that.$Message.success('新增成功')
                that.formItem3.num=''
                that.request(1)
              }else{
                that.$Message.error('新增失败！查看是否存在相同编号')
              }

            }).catch((e) => {
              that.$Message.fail('网络有误！')
            })
          }
        })
      },
      onEditorChange({editor,html,text}){
        // 富文本编辑器，文本改变时，设置字段值
        console.log(editor,html,text)
        this.content = html
      },
      handleView (name) {
        console.log(this.$refs.upload.fileList)
        this.imgName = name;
        this.visible = true;
      },
      handleRemove (file) {
          const fileList = this.$refs.upload.fileList;
          this.$refs.upload.fileList.splice(fileList.indexOf(file), 1);
      },
      handleSuccess (res, file) {
        this.defaultList.push( { name: file.name, url: res.data.url })
        this.formItem2.bookpic = res.data.url
        this.$Message.success('上传成功')
        console.log(this.uploadList)
        console.log(file)
        console.log(res.data.url)
        console.log(this.defaultList)
      },
      handleFormatError (file) {
          this.$Notice.warning({
              title: 'The file format is incorrect',
              desc: 'File format of ' + file.name + ' is incorrect, please select jpg or png.'
          });
      },
    }
  }
</script>
<style scoped>
.zindex{
  z-index: 1002;
}
.zindex>.ivu-modal-mask {
   z-index: 1002;
}
.zindex>.ivu-modal-wrap {
   z-index: 1002;
}
</style>


