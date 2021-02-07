<template>
<el-dialog :title="titletext" :visible.sync="visible">
    <div class="dialogbox">
        <el-form ref="form"  label-width="80px">
            <el-form-item label="车辆状态">
                <el-radio-group v-model="updata.busStatusId" @change="getBusStatusId">
                    <el-radio v-for="item in busStatusTypeList"
                              :label="item.busStatusId" :key="item.busStatusId">
                        {{item.busStatusName}}
                    </el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="操作备注">
                <el-input type="textarea" v-model="updata.maintenanceDesc"></el-input>
            </el-form-item>
        </el-form>

        <br>
        <el-button type="primary" size="small"  @click="update" style="float: right;margin-top: 10px;">确定</el-button>

    </div>
</el-dialog>
</template>

<script>
import {maintainDesc,getBusStatusListData} from '@/api/sale'
export default {
    data() {
        return {
            visible: false,
            titletext:'',
            busId:'',
            updata:{
                maintenanceDesc:'',
                busStatusId:''
            },
            busStatusTypeList:[]
        }
    },
    methods: {
        maintainDescinit : function(res){
            console.log(res)
            this.updata.busStatusId=res.busStatusId;
            this.busId=res.busId;
            this.visible = true;
            this.titletext=res.vinCode+'-车辆状态修改';
            getBusStatusListData().then(resp => {
                if(resp.data.code==0){
                    this.busStatusTypeList=resp.data.data.list;
                    console.log(this.busStatusTypeList)
                }
            })
        },
        getBusStatusId(val){
            console.log(val)
        },
        update(){
            maintainDesc(this.busId,this.updata.busStatusId,this.updata).then(resp =>{
                if(resp.data.code==0){
                    this.visible = false
                    this.$emit('refreshDataList')
                }
            })
        }
    }
}
</script>
<style scoped="scoped">
    .dialogbox{
        overflow: hidden;
    }
    .statusbox{
        width: 120px;
        height: 120px;
        float: left;
        text-align: center;
        color: #fff;
        font-size: 14px;
    }
    .detailbox{
        width: calc(100% - 152px);
        border: 1px solid #e5e8ed;
        background-color: #f2f9fe;
        padding: 10px;
        margin-left: 10px;
        float: left;

    }
    .detailbox ul{
        padding: 0;
        margin: 0;
    }
    .detailbox ul li{
        float: left;
        width: 50%;
        font-size: 10px;
        line-height: 20px;
    }
    .detailbox ul li span{
        float: left;
    }
    .bgcolor1{
        background-color: #3a35ff;
        background: -webkit-linear-gradient(0deg,#3a35ff,#00ebfc)
    }
    .statusboximg{
        width: 68px;
        height: 68px;
        border: 1px solid #fff;
        border-radius: 50%;
        background: rgba(255,255,255,0.2);
        background-size:60px 60px;
        background-position:center center;
        float: left;
        background-repeat: no-repeat;
        margin: 10px 26px;
    }
    .editInput{
        border: 1px solid #e5e8ed;
        font-size: 10px;
        color:#606266;
        min-height: 50px;
        width: calc(100% - 6px);
    }
</style>
