<template>
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" style="color:#fff">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForms" @keyup.enter.native="dataFormSubmit()"
                 label-width="120px">

            <el-form-item label="校车公司" size="mini" prop="companyId">
                <el-select
                    style="margin-bottom: 4px;"
                    v-model="dataForm.companyId"
                    filterable
                    default-first-option
                    placeholder="请选择校车车厂">
                    <el-option
                        v-for="item in companyIdOptions"
                        :key="item.companyId"
                        :label="item.companyName"
                        :value="item.companyId">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="校车车型" size="mini" prop="busTypeId">
                <el-select
                    style="margin-bottom: 4px;"
                    v-model="dataForm.busTypeId"
                    filterable
                    default-first-option
                    placeholder="请选择校车车型">
                    <el-option
                        v-for="item in busTypeIdOptions"
                        :key="item.busTypeId"
                        :label="item.busTypeName"
                        :value="item.busTypeId">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="校车编号" prop="busCode">
                <el-input v-model="dataForm.busCode" placeholder="校车编号" maxlength="30"></el-input>
            </el-form-item>
            <el-form-item label="VIN编号" prop="vinCode">
                <el-input v-model="dataForm.vinCode" placeholder="VIN编号" maxlength="30"></el-input>
            </el-form-item>
            <el-form-item label="车牌号" prop="plateCode">
                <el-input v-model="dataForm.plateCode" placeholder="车牌号" maxlength="30"></el-input>
            </el-form-item>
            <el-form-item label="登记日期" prop="registrationDateStr">
                <el-date-picker v-model="dataForm.registrationDateStr" type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd" placeholder="登记日期"></el-date-picker>
            </el-form-item>
<!--            <el-form-item label="车辆状态" size="mini" prop="busStatus">-->
<!--                <el-select-->
<!--                    style="margin-bottom: 4px;"-->
<!--                    v-model="dataForm.busStatus"-->
<!--                    filterable-->
<!--                    disabled="disabled"-->
<!--                    default-first-option-->
<!--                    placeholder="请选择车辆状态">-->
<!--                    <el-option-->
<!--                        v-for="item in busStatusOptions"-->
<!--                        :key="item.configParamId"-->
<!--                        :label="item.paramName"-->
<!--                        :value="item.configParamId">-->
<!--                    </el-option>-->
<!--                </el-select>-->
<!--            </el-form-item>-->
          <!--  <el-form-item label="运行状态" size="mini" prop="runStatus">
                <el-select
                    style="margin-bottom: 4px;"
                    v-model="dataForm.runStatus"
                    filterable
                    default-first-option
                    placeholder="请选择运行状态">
                    <el-option
                        v-for="item in runStatusOptions"
                        :key="item.paramValue"
                        :label="item.paramName"
                        :value="item.paramValue">
                    </el-option>
                </el-select>
            </el-form-item>-->

            <el-form-item label="是否启用">
                <el-switch v-model="value" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
            </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button @click="visible = false">取消</el-button>
            <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
        </span>
    </el-dialog>
</template>
<script>
import {saveInfoBus, editInfoBus, getInfoBusDetail} from "@/api/parameter";
import {getBusCompanyS, getBusTypeS, getBusStatusS, getRunStatusS} from "@/api/selectionApi";

export default {
    name: 'Dialog',
    data() {
        return {
            title: '',
            visible: false,
            value: false,
            companyIdOptions: [],
            busTypeIdOptions: [],
            // busStatusOptions: [],
            // runStatusOptions: [],
            dataForm: {
                busId: '',
                companyId: '',
                busTypeId: '',
                vinCode: '',
                busCode: '',
                plateCode: '',
                registrationDateStr: '',
                isDeleted: '',
                isEnabled: 0,
                createdBy: '',
                createdDate: '',
                modifiedBy: '',
                modifiedDate: ''
                // busStatus: '',
                // runStatus: 0
            },
            dataRule: {
                companyId: [
                    {required: true, message: '请选择校车公司', trigger: 'change'}
                ],
                factoryId: [
                    {required: true, message: '请选择校车工厂', trigger: 'change'}
                ],
                busTypeId: [
                    {required: true, message: '请选择车辆类型', trigger: 'change'}
                ],
                vinCode: [
                    {required: true, message: '请输入VIN码', trigger: 'blur'},
                    {max: 64, message: '长度不能超过64个字符', trigger: 'blur'}
                ],
                busCode: [
                    {required: true, message: '请输入校车编号', trigger: 'blur'},
                    {max: 64, message: '长度不能超过16个字符', trigger: 'blur'}
                ],
                plateCode: [
                    {required: true, message: '请输入车牌号', trigger: 'blur'},
                    {max: 64, message: '长度不能超过10个字符', trigger: 'blur'}
                ],
                busStatus: [
                    {required: true, message: '请选择车辆状态', trigger: 'change'}
                ]
            }
        }
    },
    methods: {
        //编辑或新增进入弹窗
        initData: function (id) {
            this.visible = true;
            this.value = false;
            this.dataForm.companyId = '';
            this.dataForm.busTypeId = '';
            // this.dataForm.busStatus = '';
            // this.dataForm.runStatus = '';
            this.$nextTick(() => {
                if (this.$refs['dataForms'] !== undefined) {
                    this.$refs['dataForms'].resetFields()
                }
            })

            if (id) {
                const loading = this.$loading({
                    lock: true,
                    text: '加载中.......',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });
                this.title = '修改';
                console.log("打开编辑弹窗,ID:" + id);
                getInfoBusDetail(id).then((res) => {
                    console.log("单条详细:" + JSON.stringify(res.data.data.data));
                    if (res.data && res.data.code === 0) {

                        this.dataForm.busId = res.data.data.data.busId;
                        this.dataForm.companyId = res.data.data.data.companyId;
                        this.dataForm.busTypeId = res.data.data.data.busTypeId;
                        this.dataForm.busCode = res.data.data.data.busCode;
                        this.dataForm.vinCode = res.data.data.data.vinCode;
                        this.dataForm.plateCode = res.data.data.data.plateCode;
                        this.dataForm.registrationDateStr = res.data.data.data.registrationDate;
                        this.dataForm.isDeleted = res.data.data.data.isDeleted;
                        this.dataForm.isEnabled = res.data.data.data.isEnabled;
                        this.dataForm.createdBy = res.data.data.data.createdBy;
                        this.dataForm.createdDate = res.data.data.data.createdDate;
                        this.dataForm.modifiedBy = res.data.data.data.modifiedBy;
                        this.dataForm.modifiedDate = res.data.data.data.modifiedDate;
                        // this.dataForm.busStatus = res.data.data.data.busStatus;
                        // this.dataForm.runStatus = res.data.data.data.runStatus;

                        if (res.data.data.data.isEnabled == 1) {
                            this.value = true
                        } else {
                            this.value = false
                        }
                    }
                });
                loading.close();
            } else {
                console.log("打开新增弹窗");
                this.title = '新增'
                this.dataForm.isEnabled = 0;
                this.dataForm.runStatus = 0;
                this.value = true;
            }

            getBusCompanyS().then((res) => {
                if (res.data && res.data.code === 0) {
                    this.companyIdOptions = res.data.data.list
                    return;
                }
            });

            getBusTypeS().then((res) => {
                if (res.data && res.data.code === 0) {
                    this.busTypeIdOptions = res.data.data.list
                    return;
                }
            });

            // getBusStatusS().then((res) => {
            //     if (res.data && res.data.code === 0) {
            //         this.busStatusOptions = res.data.data.selection;
            //         if(!id){
            //             this.busStatusOptions.forEach(ele =>{
            //                 if(ele.paramName == '正常'){
            //                     this.dataForm.busStatus = ele.configParamId;
            //                 }
            //             })
            //         }
            //         return;
            //     }
            // });

            // getRunStatusS().then((res) => {
            //     if (res.data && res.data.code === 0) {
            //         this.runStatusOptions = res.data.data.selection;
            //         return;
            //     }
            // });

        },
        // 表单提交
        dataFormSubmit: function () {
            if (this.value == true) {
                this.dataForm.isEnabled = 1;
            } else {
                this.dataForm.isEnabled = 0;
            }
            this.$refs['dataForms'].validate((valid) => {
                if (valid) {
                    const loading = this.$loading({
                        lock: true,
                        text: '加载中.......',
                        spinner: 'el-icon-loading',
                        background: 'rgba(0, 0, 0, 0.7)'
                    });
                    if (this.title == '修改') {
                        console.log("修改提交:" + JSON.stringify(this.dataForm));
                        editInfoBus(this.dataForm).then((res) => {
                            loading.close();
                            if (res.data && res.data.code === 0) {
                                this.$message({
                                    message: '操作成功',
                                    type: 'success',
                                    offset: 300,
                                    duration: 1500,
                                    onClose: () => {
                                        this.visible = false
                                        this.$emit('refreshDataList')
                                    }
                                })
                            } else {
                                this.$message({
                                    message: res.data.message,
                                    type: 'error',
                                    offset: 300
                                })
                            }
                        })
                    }
                    if (this.title == '新增') {
                        console.log("新增提交:" + JSON.stringify(this.dataForm));
                        saveInfoBus(this.dataForm).then((res) => {
                            loading.close();
                            if (res.data && res.data.code === 0) {
                                this.$message({
                                    message: '操作成功',
                                    type: 'success',
                                    offset: 300,
                                    duration: 1500,
                                    onClose: () => {
                                        this.visible = false
                                        this.$emit('refreshDataList')
                                    }
                                })
                            } else {
                                this.$message({
                                    message: res.data.message,
                                    type: 'error',
                                    offset: 300
                                })
                            }
                        })
                    }

                }
            })
        }
    }
}

</script>
<style>
</style>
