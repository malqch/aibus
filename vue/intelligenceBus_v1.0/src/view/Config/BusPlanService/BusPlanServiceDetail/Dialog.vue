<template>
    <div>
        <el-dialog :title="dialogTitle" :close-on-click-modal="false" :visible.sync="dialogVisible" style="color:#fff">
            <el-form :model="dataForm" :rules="dataRule" ref="dataForm" label-width="80px">
                <el-form-item label="营运时间" prop="queryDt">
                    <el-date-picker style="width: 95%" v-model="dataForm.queryDt" type="datetimerange"
                         range-separator="至"
                                    value-format="yyyy-MM-dd HH:mm:ss"
                                    :default-time="['00:00:00', '23:59:59']"
                        start-placeholder="开始时间" end-placeholder="截至时间"
                         @change="getDate">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="公交车" prop="busId">
                    <el-select style="width: 95%" v-model="dataForm.busId" placeholder="请先选择车辆营运起止时间" clearable :disabled="disabled" @change="getBusId">
                        <el-option :label="item.busCode" :value="item.busId" :key="item.busId"
                                   v-for="(item, index) in busNameList">
                        </el-option>
                    </el-select>
                </el-form-item>

                <el-row>
                    <el-col :span="12">
                        <el-form-item label="司机">
                            <el-select
                                style="width:165px;"
                                v-model="dataForm.driverId"
                                clearable
                                default-first-option
                                placeholder="请选择司机">
                                <el-option
                                    v-for="item in driverList"
                                    :key="item.id"
                                    :label="item.driverName"
                                    :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="安全员">
                            <el-select
                                style="width:165px;"
                                v-model="dataForm.safetyOfficerId"
                                clearable
                                default-first-option
                                placeholder="请选择安全员">
                                <el-option
                                    v-for="item in safetyOfficerList"
                                    :key="item.id"
                                    :label="item.id"
                                    :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-form-item label="是否启用">
                    <el-switch v-model="value" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
            <el-button @click="resetForm">取消</el-button>
            <el-button type="primary" :disabled="nextStepDisabled" @click="dataFormSubmit">确定</el-button>
        </span>
        </el-dialog>

    </div>
</template>
<script>
    import {
        getPlanBusServiceDetail,
        savePlanBusService,
        editPlanBusService,
        getNoPlanBusList,
        getInfoDriverListAll, getInfoSafetyOfficerListAll
    } from "@/api/parameter";
export default {
    name: 'Dialog',
    data() {
        var validDate = function(rule,value,callback){
            console.log('test');
            console.log(value);
            if(value == null){
                return callback(new Error("请选择营运起止时间"))
            }else{
                if(!value[0] || !value[1]){
                    return callback(new Error("请选择营运起止时间"))
                }else{
                    console.log('ok');
                    return callback();
                }
            }
        }
        return {
            dialogTitle: '',
            isAdd: true,
            dialogVisible: false,
            companyId:'',
            companyLineId:'',
            direction:'',
            busNameList:[],
            driverList:[],
            safetyOfficerList:[],
            value:true,
            dataForm: {
                queryDt:[],
                busId:'',
                companyLineId: '',
                direction:'',
                driverId:'',
                safetyOfficerId:'',
                beginDate: '',
                endDate: '',
                isEnabled:true
            },
            dataRule:{
                queryDt: [
                    {required:true, validator:validDate, trigger: 'change'}
                ],
                busId: [
                    {required: true, message: '请选择公交车辆', trigger: 'change'}
                ]
            },
            vinCode:'',
            disabled:false,
            nextStepDisabled:false,
            loading: null,
        }
    },
    methods: {
        getDate(){
            console.log('change');
            console.log(this.dataForm.queryDt);
            if(this.dataForm.queryDt != null){
                this.dataForm.beginDate=this.dataForm.queryDt[0];
                this.dataForm.endDate=this.dataForm.queryDt[1];
                let params={
                    companyId:this.companyId,
                    companyLineId:this.companyLineId,
                    direction:this.direction,
                    beginDate:this.dataForm.beginDate,
                    endDate:this.dataForm.endDate
                }
                if(this.isAdd){
                    this.dataForm.busId = '';
                    this.busNameList = [];
                    getNoPlanBusList(params).then(res => {
                        if (res.data && res.data.code == 0) {
                            this.busNameList = res.data.data.data
                        }
                    })
                }
            }else{
                if(this.isAdd){
                    this.dataForm.busId = '';
                    this.busNameList = [];
                }
            }
        },
        getBusId(val){
            this.dataForm.busId=val
        },
        initData: function (companyId, direction, companyLineId,planServiceId,vinCode) {

            getInfoDriverListAll().then((res) => {
                if (res.data && res.data.code == 0) {
                    this.driverList = res.data.data.list;
                }
            });

            getInfoSafetyOfficerListAll().then((res) => {
                if (res.data && res.data.code == 0) {
                    this.safetyOfficerList = res.data.data.list;
                }
            });

            this.companyId = companyId;
            this.companyLineId = companyLineId;
            this.direction = direction;
            this.dataForm.beginDate='';
            this.dataForm.endDate='';
            this.dataForm.queryDt=[];
            this.dialogVisible = true;
            this.vinCode=vinCode;
            this.$nextTick(() => {
                if (this.$refs['dataForm'] !== undefined) {
                    this.$refs['dataForm'].resetFields()
                }
            })
            for (let key in this.dataForm) {
                this.dataForm[key] = ''
            }
            this.dataForm.companyLineId = companyLineId

            //修改
            if (planServiceId) {
                this.dialogTitle = '修改'
                this.isAdd = false;
                this.disabled=true;

                //回显表单
                getPlanBusServiceDetail(planServiceId).then((res) => {
                    if (res.data && res.data.code == 0) {
                        this.busNameList=[{busId:res.data.data.data.busId,vinCode:res.data.data.data.vinCode,busCode:res.data.data.data.busCode}]
                        this.dataForm = res.data.data.data;
                        this.$set(this.dataForm, 'queryDt', [this.dataForm.beginDate, this.dataForm.endDate]);
                        // this.dataForm.queryDt=[this.dataForm.beginDate,this.dataForm.endDate];
                        this.value = res.data.data.data.isEnabled === 1;
                    }
                });
            }
            //新增
            else {
                this.dialogTitle = '新增'
                this.isAdd = true
                this.dataForm.isEnabled = 1;
                this.disabled=false;
                this.value = true;
            }

        },
        resetForm() {
            this.$refs['dataForm'].resetFields();
            this.nextStepDisabled = false;
            this.dialogVisible = false
        },
        dataFormSubmit() {
            this.$refs['dataForm'].validate((valid) => {
                if (valid) {
                    this.loading = this.$loading({
                        lock: true,
                        text: '加载中...',
                        spinner: 'el-icon-loading',
                        background: 'rgba(0, 0, 0, 0.7)'
                    });
                    if (this.value) {
                        this.dataForm.isEnabled = 1;
                    } else {
                        this.dataForm.isEnabled = 0;
                    }
                    this.dataForm.direction = this.direction;
                    if (this.isAdd) {
                        console.log(this.dataForm);
                        savePlanBusService(this.dataForm).then((res) => {
                            this.loading.close();
                            if (res.data && res.data.code === 0) {
                                this.$message.success({
                                    dangerouslyUseHTMLString: true,
                                    message: '操作成功',
                                    type: 'success',
                                    onClose: () => {
                                        this.dialogVisible = false
                                        this.$emit('refreshDataList')
                                    }
                                })
                            } else {
                                this.$message({
                                    dangerouslyUseHTMLString: true,
                                    message: res.data.message,
                                    type: 'error',
                                    offset: 300
                                })
                            }
                            this.nextStepDisabled = false;
                            this.resetForm()
                        })
                    } else {
                        editPlanBusService(this.dataForm).then((res) => {
                            this.loading.close();
                            if (res.data && res.data.code === 0) {
                                this.$message.success({
                                    dangerouslyUseHTMLString: true,
                                    message: '操作成功',
                                    type: 'success',
                                    onClose: () => {
                                        this.dialogVisible = false
                                        this.$emit('refreshDataList')
                                    }
                                })
                            } else {
                                this.$message({
                                    dangerouslyUseHTMLString: true,
                                    message: res.data.message,
                                    type: 'error',
                                    offset: 300
                                })
                            }
                            this.nextStepDisabled = false;
                            this.resetForm()
                        })
                    }
                }
            })
        },
    }
}
</script>
<style scoped>
.el-rate {
    padding-top: 10px;
}
</style>
