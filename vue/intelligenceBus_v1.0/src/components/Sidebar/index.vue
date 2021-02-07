<!--
 @Author: songwenxin
 @Filename: index.vue
 @ProjectName: geode_09_23
 @Mail: songwenxin666@sina.com
 @Date: 2019-09-24 11:44
 @file-description: 404页面组件
-->

<template>
    <div class="sidebar">
        <el-menu :default-active="defaultUrl" class="el-menu-vertical-demo" text-color="#666" router unique-opened active-text-color="#FFF"
                 :collapse-transition="this.collapse_transition" :collapse="isCollapse">
            <el-menu-item  :key="item.name" v-for="(item, index) in rightList" :class="item.index===0?'test':'test1'" v-if="item.list ==null||item.list.length===0" :index="item.endpointPath==null?'#':item.endpointPath">
                <i :class="'fa fa-'+item.iconCss"></i>
                <span slot="title">{{item.name}}</span>
            </el-menu-item>
            <el-submenu :key="item.name " v-for="(item, index) in rightList" v-if="item.list !=null && item.list.length!=0" :index="index+''">
                <template slot="title">
                    <i :class="'fa fa-'+item.iconCss"></i>
                    <span slot="title">{{item.name}}</span>
                </template>
                <el-menu-item-group>
                    <el-menu-item :key="val.name" :index="val.endpointPath==null?'#':val.endpointPath" v-if="item.list !=null && item.list.length!=0" v-for="(val, i) in item.list">
                        <i :class="'fa fa-'+val.iconCss"></i>
                        <span slot="title">{{val.name}}</span>
                    </el-menu-item>
                </el-menu-item-group>
            </el-submenu>
        </el-menu>
    </div>
</template>

<script>
    import "./index.css";
    import {
        getRightNav
    } from "@/api/system";

    export default {
        name: "Sidebar",
        created() {
            this.handlerNav();
            this.getUrl();
            this.$root.$on('eventName', value => {
                this.isCollapse=value;
            })
        },
        mounted(){

        },
        data() {
            return {
                defaultUrl: '/Monitor',
                navPath:true,
                rightList: [],
                userNav: [],
                isCollapse: false,
                collapse_transition: true,
                dataarr: {},
                uniqueopened: true,
                isCollapse:false
            };
        },

        methods: {
            getUrl() {
                let self = this;
                let currentUrl = window.location.href;
                this.defaultUrl = "/" + this.$route.path.split("/")[1]
            },
            handlerNav() {
                const loading = this.$loading({
                    lock: true,
                    text: "加载中.......",
                    spinner: "el-icon-loading",
                    background: "rgba(0, 0, 0, 0.7)"
                });
                getRightNav().then(({data}) => {
                    loading.close();
                    if (data && data.code === 0) {
                        this.rightList = data.data.rightList;
                        for (var i = 0; i < data.data.rightList.length; i++) {
                            if(data.data.rightList[i].endpointPath=='/Monitor'){
                                this.navPath=false
                            }
                        }
                        for (var i = 0; i < this.rightList.length; i++) {
                            this.saveNaV(this.rightList[i])
                        }
                    } else {
                        this.$router.push("/")
                    }

                });
            },
            saveNaV(value) {
                if (value.list != null && value.list.length > 0) {
                    for (var j = 0; j < value.list.length; j++) {
                        this.saveNaV(value.list[j])
                    }

                } else {
                    this.userNav.push(value.endpointPath)
                }
            },

            handleOpen(key, keyPath){

            },
            handleClose(key, keyPath){

            }
        },
        watch: {
            $route(to, from) {
                return;
                if (to.path.split("/") != null && to.path.split("/").length > 0) {
                    var path = "/" + to.path.split("/")[1]
                    console.log(path);
                    this.userNav.push("/Monitor")
                    console.log(this.userNav);
                    if (this.userNav.indexOf(path) === -1) {
                        this.$message({
                            message: "权限不够，请联系管理员",
                            type: 'error',
                            offset: 300
                        })
                        this.$router.push(from.path)
                    } else {

                    }
                } else {
                    this.$router.push("/")
                }

            }
        }
    }
</script>

<style scoped>
    /deep/.sidebar {
        width: 100%;
        height: calc(100vh - 44px);
    }

    /deep/.l_checked {
        color: #2b7dbc;
    }

    /deep/ .sidebar .el-menu-vertical-demo:not(.el-menu--collapse) {
        width: 200px;
        min-height: 400px;
    }

    /deep/.sidebar .el-menu-item.is-active {
        background-color: #2f2f4d !important;
        color: #0f94fd !important;
    }
    /deep/.sidebar .el-menu-item.is-active i{
        color:#0f94fd !important;
    }
    /deep/.sidebar .el-menu-item-group__title {
         box-sizing: border-box;
        padding:0 !important;
    }
</style>
