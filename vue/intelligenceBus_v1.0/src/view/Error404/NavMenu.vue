<!--
 @Author: songwenxin
 @Filename: NavMenu.vue
 @ProjectName: geode
 @Mail: songwenxin666@sina.com
 @Date: 2019-10-15 12:43
 @file-description:
-->


<template>
    <div>
        <!-- 根据实际情况做修改 -->
        <el-submenu  v-for="(item,index) in navlist" :key="index"   v-if="item.adminMenu.menuType=='list'" :index="item.adminMenu.url">
            <template slot="title">
                <i  class="el-icon-location"></i>
                <span>{{item.adminMenu.menuName}}</span>
            </template>
            <NavMenu  :navlist="item.children"></NavMenu>
        </el-submenu>

        <el-menu-item  v-for="(item,index) in navlist" :key="index" v-if="item.adminMenu.menuType=='page'"  :index="item.adminMenu.url">
            <i  class="el-icon-location"></i>
            <span>{{item.adminMenu.menuName}}</span>
        </el-menu-item>
    </div>
</template>

<script>
    export default {
        name: "NavMenu",
        props: ["navlist"],
        data() {
            return {}
        },
        methods: {}
    }


</script>

<style scoped>

</style>
