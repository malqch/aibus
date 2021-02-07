<!--
 @Author: songwenxin
 @Filename: index.vue
 @ProjectName: gfm-manage
 @Mail: songwenxin666@sina.com
 @Date: 2019-09-24 11:51
 @file-description:
-->
<template>
<div>
    <div class="cotroller_box">
        <el-form :inline="true" class="demo-form-inline" label-width="120px">
            <el-form-item label="关键字">
                <el-input v-model="form.name" placeholder="日志操作或者是操作人" clearable></el-input>
            </el-form-item>
            <el-form-item label="筛选时间">
                <el-date-picker v-model="form.queryDt" type="datetimerange" value-format="yyyy-MM-dd HH:mm:ss" range-separator="至" start-placeholder="开始时间" end-placeholder="结束时间" :default-time="['00:00:00', '23:59:59']">
                </el-date-picker>
            </el-form-item>
            <el-form-item style="margin-top: -1px;">
                <el-button type="primary" @click="handleList(1)" size="small"><i class="fa fa-search" style="margin-right: 4px;font-size:14px;"></i>查询</el-button>
            </el-form-item>
        </el-form>
    </div>
    <div class="show_table" style="width:100%; ">
        <div class="show_table_controller">
            <!--<span class="table_title_span"></span>-->
            <svg t="1575621249210" class="icons" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2551" width="200" height="200"><path d="M729.014 64.868H224.382v250.69H98.244v361.723h126.138v279.284h703.613V256.39L729.014 64.868zM293.116 888.035v-83.946h293.606V408.1h-68.734v327.458H293.116v-58.276h167.921V315.559H293.116V133.4h379.101v187.055h187.045v567.58H293.116z" fill="#2C91E0" p-id="2552"></path><path d="M660.899 417.683h129.305v66.818H660.899zM660.899 577.049h129.305v66.818H660.899zM660.899 736.414h129.305v66.818H660.899z" fill="#2C91E0" p-id="2553"></path></svg>

            <p class="title">日志列表</p>
        </div>
        <div class="table_div">
            <el-table :data="tableData" style="width: 100%" max-height="400">
                <el-table-column type="index" :index="table_index" label="序号" align="center" width="80">
                </el-table-column>
                <el-table-column prop="username" label="用户名称" min-width="120">
                </el-table-column>
                <el-table-column prop="operation" label="日志操作" min-width="180">
                </el-table-column>
                <el-table-column prop="requestAddress" label="请求地址" width="140">
                </el-table-column>
                <el-table-column label="执行时间" width="150">
                    <template slot-scope="scope">
                        {{scope.row.executedTime+'ms'}}
                    </template>
                </el-table-column>
                <el-table-column prop="createDt" label="操作时间" width="150">
                </el-table-column>
            </el-table>
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="table.currPage" :page-sizes="[10, 20, 50, 100]" :page-size="table.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="table.totalCount" background style="width:100%;text-align:right;margin-top:10px;">
            </el-pagination>
        </div>
    </div>
</div>
</template>

<script>
import {
    getOperationList
} from '@/api/system'

export default {
    name: 'GfmAdminOperationLog',
    data() {

        return {
            form: {
                name: "",
                queryDt: "",
                page: 1,
                limit: 10
            },
            tableData: [],
            table: {}
        };
    },
    methods: {
        table_index(index) {
            return (this.form.page - 1) * this.form.limit + index + 1
        },
        handleList(page) {
            const loading = this.$loading({
                lock: true,
                text: '加载中.......',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            });
          if(page){this.form.page=1;}
            getOperationList(this.form).then((resp) => {


                this.tableData = resp.data.data.page.list;

                this.table = resp.data.data.page
                loading.close();

            })
        },
        handleSizeChange(val) {
            this.form.limit = val;
            this.handleList(1);
        },
        handleCurrentChange(val) {
            this.form.page = val;
            this.handleList();
        }
    },
    mounted() {
        this.handleList();
    }
}
</script>

<style>
</style>
