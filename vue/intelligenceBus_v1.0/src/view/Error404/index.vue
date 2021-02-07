<!--
 @Author: songwenxin
 @Filename: Main.vue
 @ProjectName: geode
 @Mail: songwenxin666@sina.com
 @Date: 2019-10-15 00:10
 @file-description:
-->

<template>
<el-container>
    <el-header>
        <Header></Header>
    </el-header>
    <el-container class="container">
        <div class="site-wrapper site-page--not-found">
            <div class="left-box">
                <img src="../../../static/404.png"/>
            </div>
            <div class="site-content">
                <h2 class="not-found-title">404</h2>
                <p class="not-found-desc">抱歉，您访问的页面不存在 ...</p>
                <el-button @click="$router.go(-1)">返回上一个页面</el-button>
                <el-button type="primary" class="not-found-btn-gohome" @click="$router.push({ path: '/Monitor' })">回到首页</el-button>
            </div>
            <div class="clear"></div>
        </div>
        <el-main>
            <router-view></router-view>
        </el-main>
    </el-container>
</el-container>
</template>

<script>
import {
    Sidebar,
    Header
} from '@/components'
export default {
    name: "Main",
    components: {
        // Sidebar : Sidebar,
        Header: Header
    }
}
</script>

<style scoped>
.container{
  display: block;
}

.site-wrapper{
  width: 1200px;
  margin: 10% auto;
}
.left-box{
  width: 600px;
  height: 400px;
  float: left;
}
.left-box img{
  width: 600px;
  height: 400px;
}
.site-content {
  float: left;
  width: 400px;
    padding: 30px;
    text-align: center;
}

.not-found-title {
    margin: 20px 0 15px;
    font-size: 10em;
    font-weight: 400;
    color: rgb(92, 167, 205);
}

.not-found-desc {
    margin: 0 0 30px;
    font-size: 26px;
    text-transform: uppercase;
    color: rgb(118, 131, 143);

}

.not-found-btn-gohome {
    margin-left: 30px;
}
.clear{clear: both;}
</style>
