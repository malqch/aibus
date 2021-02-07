<!--
 @Author: songwenxin
 @Filename: index.vue
 @ProjectName: gfm-manage
 @Mail: songwenxin666@sina.com
 @Date: 2019-09-24 13:23
 @file-description:公共卫生组件页面
-->
<template>
    <div>
        <div class="box">
            <el-select class="selectArea" v-for="(itemList,index) in areaList"  :key="'itemList'+index" v-model="areaIdList[index]"
                placeholder="请选择" @change="changeAreaList($event,index)" :popper-append-to-body="false">
                <el-option v-for="item in itemList" :key="item.areaId" :label="item.areaName" :value="item.areaId">
                </el-option>
            </el-select>
            <el-select class="" v-model="companyName" placeholder="请选择" @change="changeCompany" :popper-append-to-body="false">
                <el-option
                  v-for="item in companyList"
                  :key="item.companyId"
                  :label="item.companyName"
                  :value="item.companyId">
                </el-option>
            </el-select>
            <el-select class="selectArea" v-model="companyLineName" placeholder="请选择" @change="changeLine" :popper-append-to-body="false">
                <el-option
                  v-for="item in companyLineList"
                  :key="item.companyLineId"
                  :label="item.companyLineName"
                  :value="item.companyLineId">
                </el-option>
            </el-select>
        </div>
        <div class="show_table" style="width:100%;">
            <div class="table_div">
                <div style="float: right;">
                    <el-input v-model="form.busStationName" placeholder="请输入车站名称" style="float: left;width:190px;height:42px;margin-right:10px"></el-input>
                    <el-button  type="primary" @click="handleList(1)" size="small">
                        <i class="fa fa-search" style="margin-right: 4px;font-size:14px;"></i>搜索
                    </el-button>
                </div>
                <div>
                    <el-table border :data="tableData"  style="width: 100%">
                        <el-table-column type="index" :index="table_index" label="序号" prop="busId"></el-table-column>
                        <el-table-column label="车辆VIN码" prop="vinCode"></el-table-column>
                        <el-table-column label="所属路线" prop="companyLineName"></el-table-column>
                        <el-table-column label="上车站名" prop="busStationName"></el-table-column>
                        <el-table-column label="经度" prop="longitude"></el-table-column>
                        <el-table-column label="纬度" prop="latitude"></el-table-column>
                        <el-table-column label="体温超标人数" prop="overPersonCount"></el-table-column>
                        <el-table-column label="证据照片查看">
                            <template slot-scope="scope">
                                <el-button  size="mini" type="primary"  @click="showPhoto(scope.row.eventDetailId)">查看</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <el-pagination
                            @size-change="handleSizeChange"
                            @current-change="handleCurrentChange"
                            :current-page="table.currPage"
                            :page-sizes="[10, 20, 50, 100]"
                            :page-size="table.pageSize"
                            layout="total, sizes, prev, pager, next, jumper" :total="table.totalCount" background style="width:100%;text-align:right;margin-top:10px;"></el-pagination>
                </div>

            </div>
        </div>
        <show-photo v-show="showPhotoVisible" ref='showPhoto'></show-photo>
    </div>
</template>

<script>
    //import areaData from './areaData.json'
    import showPhoto from './showPhoto'
    import {getAreaList,getCustomerCompanyListData,getLineListByCompanyIdData,
    getHealthStatByCompanyAndLineData,getInitDataByEventType} from "@/api/sale"
    export default {
        name: "GfmAdminStudent",
        data() {
            return {
                parentAreaId:0,
                initSearchFlag:true,
                initData:{},
                companyList:[],
                companyName:'',
                companyId:'',
                companyLineList:[],
                companyLineName:'',
                companyLineId:0,
                form: {
                    companyId: "",
                    companyLineId:0,
                    busStationName:'',
                    page: 1,
                    limit: 10
                },
                tableData:[],
                table: {
                    currPage:1,
                    pageSize:5,
                    totalCount:50
                },
                showPhotoVisible: false,
                areaList: [],
                areaIdList: [],
            };
        },
        components: {
            showPhoto
        },
        activated() {
        },
        mounted(){
            this.getInitData();
            // this.getProvinceData();
        },
        methods: {
            getInitData(){
                getInitDataByEventType('healthEvent').then(resp => {
                    if(resp.data.code==0 && resp.data.data.info != null){
                        this.initData=resp.data.data.info;
                    }
                    this.initAreaList();
                })
            },
            initAreaList() {
                getAreaList(0).then(resp => {
                    if (resp.data.code == 0) {
                        this.areaList.push(resp.data.data.list);
                        let initAreaId = resp.data.data.list[0].areaId
                        this.areaIdList[0] = initAreaId
                        this.getSubAreaList(initAreaId,0)
                        this.getCompanyData(initAreaId)
                    }

                })
            },
            getSubAreaList(parentAreaId,index) {

                getAreaList(parentAreaId).then(resp => {
                    if (resp.data.code == 0) {
                        let list = resp.data.data.list;
                        list.unshift({
                            areaId:parentAreaId,
                            areaName:'全部'
                            //没有hasChild属性，不会产生下级下拉框
                        })
                        this.areaList.push(list);
                        this.areaIdList[index + 1] = parentAreaId
                    }

                })
            },
            changeAreaList(data, index) {
                console.log(data + '~~~~' + index)
                this.areaList = this.areaList.slice(0, index + 1);
                this.areaIdList = this.areaIdList.slice(0,index + 1)
                console.log(this.areaList)
                for (var i = 0; i < this.areaList[index].length; i++) {
                    if (this.areaList[index][i].areaId == data) {
                        this.areaIdList[index] = this.areaList[index][i].areaId
                        if(this.areaList[index][i].hasChild){
                            this.getSubAreaList(data,index);
                        }
                    }
                }
                this.getCompanyData(data)

            },
            getCompanyData(id){
                getCustomerCompanyListData(id).then(resp => {
                    if(resp.data.code==0){
                        this.companyList=resp.data.data.list;
                        // 第一次
                        if(this.initSearchFlag && this.initData.companyId != null){
                            this.companyName=this.initData.companyName;
                            this.companyId=this.initData.companyId;
                        }else{
                            this.companyName=this.companyList[0].companyName;
                            this.companyId=this.companyList[0].companyId;
                        }
                        this.initSearchFlag = false;

                        this.getLineList()

                    }else{

                        this.companyName='';
                        this.companyId='';
                        this.companyList=[];
                        this.companyLineList=[];
                        this.companyLineId=0;
                        this.companyLineName='';
                        this.tableData=[];
                        this.table={};
                        this.form.busStationName='';
                    }

                })

            },
            changeCompany(data){
                this.companyId=data;
                this.companyLineList=[];
                this.tableData=[];
                this.table={};
                this.form.busStationName='';
                this.getLineList()
            },
            getLineList(){
                this.tableData=[];
                this.table={};
                this.companyLineList=[
                    {companyLineId:0,companyLineName:'全部'}
                ]

                getLineListByCompanyIdData(this.companyId).then(resp => {
                    if(resp.data.code==0){
                        for(var i=0;i<resp.data.data.list.length;i++){
                            this.companyLineList.push(resp.data.data.list[i])
                        }
                        this.companyLineId=this.companyLineList[0].companyLineId;
                        this.companyLineName=this.companyLineList[0].companyLineName;
                        this.handleList(1);
                    }
                })

            },
            changeLine(val){
                this.companyLineId=val;
                this.tableData=[];
                this.table={};
                this.form.busStationName='';
                this.handleList(1);
            },
            table_index(index) {
                return (this.form.page - 1) * this.form.limit + index + 1
            },
            handleList(page) {
                // const loading = this.$loading({
                //     lock: true,
                //     text: "加载中.......",
                //     spinner: "el-icon-loading",
                //     background: "rgba(0, 0, 0, 0.7)"
                // });
                if(page){this.form.page=1;}
                this.form.companyId=this.companyId;
                this.form.companyLineId=this.companyLineId;
                getHealthStatByCompanyAndLineData(this.form).then(resp => {
                    if(resp.data.code==0){
                        this.tableData=resp.data.data.list.list;
                        this.table=resp.data.data.list
                    }

                })

            },
            handleSizeChange(val) {
                this.form.limit = val;
                this.handleList(1);
            },
            handleCurrentChange(val) {
                this.form.page = val;
                this.handleList();
            },
            showPhoto(id){
                this.showPhotoVisible=true;
                this.$nextTick(()=>{
                    this.$refs.showPhoto.init(id);

                })
            }

        }
    };
</script>

<style scoped>
.box{
    padding:15px 10px;
    border: 1px solid #e5e8ed;
    border-radius: 3px;
    background-color: #fff;
    font-size: 14px;
    margin-bottom: 10px;
    /* overflow: hidden; */
    box-shadow: 2px 2px 5px rgba(0,0,0,0.1);
}
.selectArea{
    width: 150px;
    margin-right: 10px;
}
.table_div{
    box-shadow: 2px 2px 5px rgba(0,0,0,0.1);
}
/deep/ .el-scrollbar .el-scrollbar__wrap{
    margin-bottom: 0!important;
}
</style>
