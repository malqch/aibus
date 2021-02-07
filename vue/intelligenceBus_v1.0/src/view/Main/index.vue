<!--
 @Author: songwenxin
 @Filename: Main.vue
 @ProjectName: geode
 @Mail: songwenxin666@sina.com
 @Date: 2019-10-15 00:10
 @file-description:
-->


<template>
    <el-container style="height:100%;">
        <el-header style="height:44px !important;">
            <Header></Header>
        </el-header>
        <el-container style="height:calc(100% - 44px)">
            <el-aside width="auto" height="100%" style="background-color:#12203E;">
                <Sidebar></Sidebar>
            </el-aside>
            <el-container style="height:100%;overflow-y: auto">
                <el-main>
                    <router-view></router-view>
                </el-main>
            </el-container>
        </el-container>
    </el-container>
</template>

<script>
    import {Sidebar, Header} from '@/components'
    export default {
        name: "Main",
        components : {
            Sidebar : Sidebar,
            Header : Header
        }
    }
</script>

<style scoped>

</style>
