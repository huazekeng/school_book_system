<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'app'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  /* margin-top: 60px; */
}
 html, body, #app, .layout, .ivu-row-flex {
  height: 100%;
  overflow: auto;
}
.ivu-page{
  margin-top: 15px;
  margin-bottom: 20px;
}
</style>
