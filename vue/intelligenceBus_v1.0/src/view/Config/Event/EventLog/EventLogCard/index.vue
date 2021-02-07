<!--
 @Author: songwenxin
 @Filename: index.vue
 @ProjectName: gfm-manage-view
 @Mail: songwenxin666@sina.com
 @Date: 2019-12-04 10:21
 @file-description: BasicIFM  => 基础信息-事件日志
 http://qyxy.baic.gov.cn/wap/wap/creditWapAction!qr.dhtml?id=ff8080815b575d42015b5bcbb16a55d3
-->
<template>
    <div>
        <div class="show_table" style="width:100%;">
            <div class="show_table_controller">
                <svg t="1575621249210" class="icons" viewBox="0 0 1024 1024" version="1.1"
                     xmlns="http://www.w3.org/2000/svg" p-id="2551" width="200" height="200">
                    <path
                        d="M729.014 64.868H224.382v250.69H98.244v361.723h126.138v279.284h703.613V256.39L729.014 64.868zM293.116 888.035v-83.946h293.606V408.1h-68.734v327.458H293.116v-58.276h167.921V315.559H293.116V133.4h379.101v187.055h187.045v567.58H293.116z"
                        fill="#2C91E0" p-id="2552"></path>
                    <path
                        d="M660.899 417.683h129.305v66.818H660.899zM660.899 577.049h129.305v66.818H660.899zM660.899 736.414h129.305v66.818H660.899z"
                        fill="#2C91E0" p-id="2553"></path>
                </svg>
                <p class="title">事件日志卡片</p>
                <div class="btn_list">
                    <el-button type="warning" @click="handleReload" size="small"
                               style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="fa fa-refresh"></i>刷新
                    </el-button>
                    <el-button type="primary" @click="goBack" size="small" plain
                               style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class=" fa fa-mail-reply-all"></i>返回
                    </el-button>
                </div>
            </div>
            <div class="table_div card-text">
                <el-row class="card-title-box">
                    <el-col :span="5" class="">
                        <div>
                            <el-row style=" margin-top: 20px;">
                                <el-col :span="24"  class="title-box-svg" >
                                    <div  class="title-bg-box-svg">
                                        <img style="width: 100%;height: 100%;" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNjAwODM5NzMyOTI3IiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjQ4NzciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNNTEyIDM2NGgtMTM5LjJhMzIgMzIgMCAwIDEgMC02My4wNEg1MTJhMzIgMzIgMCAwIDEgMCA2My4wNHpNNzEyLjY0IDc1OS41MmgtMzUyYTMyIDMyIDAgMCAxIDAtNjMuMDRoMzUyYTMyIDMyIDAgMCAxIDAgNjMuMDR6TTcwOS4yOCA1NjEuNzZIMzYwLjE2YTMyIDMyIDAgMCAxIDAtNjMuMDRoMzQ5LjEyYTMyIDMyIDAgMCAxIDAgNjMuMDR6IiBwLWlkPSI0ODc4IiBmaWxsPSIjNDA5RUZGIj48L3BhdGg+PHBhdGggZD0iTTgwNC42NCA5NjBIMjg2Ljg4YTEwOC45NiAxMDguOTYgMCAwIDEtMTA3LjM2LTExMC40VjUyMi40YTMwLjcyIDMwLjcyIDAgMSAxIDYxLjI4IDBWODQ5LjZhNDYuNzIgNDYuNzIgMCAwIDAgNDYuMDggNDhoNTE3Ljc2YTQ2LjcyIDQ2LjcyIDAgMCAwIDQ2LjA4LTQ4VjM1Ny4yOEw2MTIuOTYgOTUuMDRIMjg2Ljg4YTQ2LjcyIDQ2LjcyIDAgMCAwLTQ2LjA4IDQ4djI1MC4yNGEzMC43MiAzMC43MiAwIDEgMS02MS4yOCAwVjE0Mi40QTEwOC45NiAxMDguOTYgMCAwIDEgMjg2Ljg4IDMySDY0MGwyNzIgMzAwLjQ4Vjg0OS42YTEwOC45NiAxMDguOTYgMCAwIDEtMTA3LjM2IDExMC40eiIgcC1pZD0iNDg3OSIgZmlsbD0iIzQwOUVGRiI+PC9wYXRoPjxwYXRoIGQ9Ik04ODEuMjggMzc4LjA4SDY4NC44YTk4LjcyIDk4LjcyIDAgMCAxLTk3LjEyLTk5Ljg0VjY0YTMxLjA0IDMxLjA0IDAgMCAxIDMwLjcyLTMyaDcuODRhMzAuMjQgMzAuMjQgMCAwIDEgMjIuNCAxMC4wOGwyNTYgMjgxLjI4YTMyIDMyIDAgMCAxIDcuMzYgMjEuNnYxLjZhMzEuMDQgMzEuMDQgMCAwIDEtMzAuNzIgMzEuNTJ6TTY0OC45NiAxMzQuODh2MTQ0YTM2LjQ4IDM2LjQ4IDAgMCAwIDM1Ljg0IDM2LjhoMTI4eiIgcC1pZD0iNDg4MCIgZmlsbD0iIzQwOUVGRiI+PC9wYXRoPjwvc3ZnPg==">
                                    </div>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="24"  class="title-text-box-svg">
                                    <div class="title-text-content-svg">{{logEventDetail.collectEvent}}</div>
                                </el-col>
                            </el-row>
                        </div>
                    </el-col>
                    <el-col :span="10" class="">
                        <div class=" card-right-content">
                            <el-row>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">事件采集名称 ：{{ logEventDetail.collectEvent }} </span>
                                    </div>
                                </el-col>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">事件级别 ：{{ logEventDetail.eventLevelName }} </span>
                                    </div>
                                </el-col>

                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">事件标签 ：{{ logEventDetail.eventTargetName }}</span>
                                    </div>
                                </el-col>
<!--                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">设备名称 ：{{ logEventDetail.busDeviceName }}</span>
                                    </div>
                                </el-col>-->
                                <el-col :span="24">
                                    <div class="mtop">
                                    <span
                                        class="text-public">是否启用 ：{{ logEventDetail.isEnabled == 1 ? '已启用' : '未启用' }}</span>
                                    </div>
                                </el-col>

                            </el-row>
                        </div>
                    </el-col>
                    <el-col :span="9">
                        <div class="">
                            <el-col :span="24">
                                <div class="mtop">
                                    <span class="text-public">车辆VIN码 ：{{ logEventDetail.vinCode }}</span>
                                </div>
                            </el-col>
                            <el-col :span="24">
                                <div class="mtop">
                                    <span class="text-public">事件类别 ：{{ logEventDetail.eventTypeName }} </span>
                                </div>
                            </el-col>

                            <el-col :span="24">
                                <div class="mtop">
                                    <span class="text-public">设备类型 ：{{ logEventDetail.deviceTypeName }}</span>
                                </div>
                            </el-col>
                            <el-col :span="24">
                                <div class="mtop">
                                    <span class="text-public">创建时间：{{ logEventDetail.createdDate }} </span>
                                </div>
                            </el-col>

                        </div>
                    </el-col>
                </el-row>
            </div>

            <div class="show_table_controller">
                <svg t="1575621249210" class="icons" viewBox="0 0 1024 1024" version="1.1"
                     xmlns="http://www.w3.org/2000/svg" p-id="2551" width="200" height="200">
                    <path
                        d="M729.014 64.868H224.382v250.69H98.244v361.723h126.138v279.284h703.613V256.39L729.014 64.868zM293.116 888.035v-83.946h293.606V408.1h-68.734v327.458H293.116v-58.276h167.921V315.559H293.116V133.4h379.101v187.055h187.045v567.58H293.116z"
                        fill="#2C91E0" p-id="2552"></path>
                    <path
                        d="M660.899 417.683h129.305v66.818H660.899zM660.899 577.049h129.305v66.818H660.899zM660.899 736.414h129.305v66.818H660.899z"
                        fill="#2C91E0" p-id="2553"></path>
                </svg>
                <p class="title">日志附件列表</p>
            </div>

            <div class="table_div">
                <div style="position: relative">
                    <el-table :data="eventAttachList" style="width: 100%" max-height="300">
                        <el-table-column fixed type="index" align="center" label="序号" width="50px"></el-table-column>
                        <el-table-column prop="eventTargetCode" label="子标签编码" min-width="100"></el-table-column>
                        <el-table-column prop="eventTargetGrope" label="子标签类型" min-width="100"></el-table-column>
                        <el-table-column prop="eventTargetName" label="子标签名称" min-width="100"></el-table-column>
                        <el-table-column prop="collectAttachValue" label="数值" min-width="120"></el-table-column>
                        <el-table-column prop="collectAttachLink" label="外链" min-width="170"></el-table-column>
                        <el-table-column prop="collectAttachLinkName" label="外链实际值" min-width="160"></el-table-column>

                        <el-table-column prop="collectAttachChar" label="字符值" min-width="140">
                            <template slot-scope="scope">
                                <span v-if="scope.row.eventTargetGrope=='image'">
                                    <el-image
                                        style="width: 30px;height: 20px;margin: 3px 2px 0 2px;"
                                        :src="scope.row.collectAttachChar"
                                        :preview-src-list="[scope.row.collectAttachChar]">
                                    </el-image>
                                </span>
                                <span v-else>{{ scope.row.collectAttachChar }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="创建时间" width="150">
                            <template slot-scope="scope">
                                <span>{{ scope.row.createdDate }}</span>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>

            </div>

        </div>
    </div>
</template>

<script>
import {getEventLogDetailByEventDetailId} from "@/api/event";

export default {
    name: "EventLogCard",
    data() {
        return {
            id:'',
            logEventDetail:{},
            eventAttachList:[]
        };
    },
    components: {
    },
    mounted: function () {
        this.id = this.$route.query.eventDetailId;
        this.getData(this.id);
    },
    methods: {
        goBack: function () {
            this.$router.push({
                path: './EventLog'
            })
        },
        handleReload: function () {
            this.getData(this.id);
        },
        //获取基础列表数据函数
        getData: function (id) {
            console.log(id)
            const loading = this.$loading({
                lock: true,
                text: "加载中.......",
                spinner: "el-icon-loading",
                background: "rgba(0, 0, 0, 0.7)"
            });
            getEventLogDetailByEventDetailId(id).then((res) => {
                loading.close();
                if (res.data && res.data.code === 0) {
                    this.logEventDetail = res.data.data.data
                    this.eventAttachList = res.data.data.data.eventAttachList
                }
            })
        },
    },

};
</script>
<style scoped>
.table_div {
    margin-bottom: 20px;
}
.card-right-content{
    padding-bottom:15px
}
</style>
