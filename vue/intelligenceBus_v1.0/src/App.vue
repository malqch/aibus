<template>
  <div id="app">
      <router-view></router-view>
  </div>
</template>

<script>
    export default {
        name: 'App'
    }
</script>

<style>
    body,html{
        background-color:#f1f1f1;
        /*-moz-user-select:none;*/
        /*-ms-user-select:none;*/
        /*-webkit-user-select:none;*/
        /*user-select:none;*/
    }
    body,html,#app{margin: 0;padding: 0;width: 100%;height: 100%;}
    *{
        -webkit-overflow-scrolling: touch;
        overflow-scrolling: touch;
        -webkit-tap-highlight-color: rgba(0,0,0,0);
        -webkit-tap-highlight-color: transparent; /* For some Androids */
    }
    .el-container{width: 100%;height: 100%;}

    .l_setBtn{width: 110px;height: 34px;color: #fff;background: #ffa500;border:none;border-radius:4px;cursor: pointer;}
</style>



