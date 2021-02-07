<template>
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" style="color:#fff" @close="closeDialog()">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForms" @keyup.enter.native="dataFormSubmit()" label-width="120px">

            <el-form-item label="设备类型" size="mini" prop="deviceTypeId">
                <el-select
                        disabled
                        style="margin-bottom: 4px;"
                        v-model="dataForm.deviceTypeId"
                        filterable
                        default-first-option
                        placeholder="请选择设备类型">
                    <el-option
                            v-for="item in deviceTypeIdOptions"
                            :key="item.deviceTypeId"
                            :label="item.deviceTypeName"
                            :value="item.deviceTypeId">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="电池型号" prop="batteryTypeName">
                <el-input v-model="dataForm.batteryTypeName" placeholder="电池型号" maxlength="32"></el-input>
            </el-form-item>
            <el-form-item label="电池电压(V)" prop="batteryTypeVoltage">
                <el-input v-model.number="dataForm.batteryTypeVoltage" placeholder="电池电压" maxlength="30"></el-input>
            </el-form-item>

            <el-form-item label="电池电流(A)" prop="batteryTypeCurrent">
                <el-input v-model.number="dataForm.batteryTypeCurrent" placeholder="电池电流" maxlength="30"></el-input>
            </el-form-item>
            <el-form-item label="能量(kW.h)" prop="batteryTypeEnergy">
                <el-input v-model.number="dataForm.batteryTypeEnergy" placeholder="电池能量" maxlength="30"></el-input>
            </el-form-item>

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
    import {saveInfoBatteryType, editInfoBatteryType, getInfoBatteryTypeDetail} from "@/api/parameter";
    import {getDeviceTypeS} from "@/api/selectionApi";

    export default {
        name: 'Dialog',
        data() {
            return {
                title: '',
                visible: false,
                value: false,
                deviceTypeIdOptions: [],
                dataForm: {
                    batteryTypeId: '',
                    deviceTypeId: '',
                    batteryTypeName: '',
                    batteryTypeVoltage: '',
                    batteryTypeEnergy: '',
                    batteryTypeCurrent: '',
                    isDeleted: '',
                    createdBy: '',
                    createdDate: '',
                    modifiedBy: '',
                    modifiedDate: '',
                    isEnabled: 0
                },
                dataRule: {
                    deviceTypeId: [
                        {required: true, message: '请选择设备类型', trigger: 'change'}
                    ],
                    batteryTypeName: [
                        {required: true, message: '请输入电池型号', trigger: 'blur'},
                        {max: 32, message: '长度不能超过32个字符', trigger: 'blur'}
                    ],
                    batteryTypeVoltage: [
                        {required: true, message: '请输入电池电压', trigger: 'blur'},
                        { type: 'number', message: '电池电压必须为数字值'}
                    ],
                    batteryTypeEnergy: [
                        {required: true, message: '请输入电池能量', trigger: 'blur'},
                        { type: 'number', message: '电池能量必须为数字值'}
                    ],
                    batteryTypeCurrent: [
                        {required: true, message: '请输入电池电流', trigger: 'blur'},
                        { type: 'number', message: '电池电流必须为数字值'}
                    ]
                }
            }
        },
        methods: {
            //编辑或新增进入弹窗
            initData: function (id) {

                const loading = this.$loading({
                    lock: true,
                    text: '加载中.......',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });

                getDeviceTypeS().then(({data}) => {
                    loading.close();
                    if(data && data.code == 0){
                        this.deviceTypeIdOptions = data.data.selection;
                        var idx = (data.data.selection || []).findIndex((item) => item.deviceTypeCode == 'battery');
                        if(idx != -1){
                            this.dataForm.deviceTypeId = data.data.selection[idx].deviceTypeId;
                            this.dataForm.deviceTypeName = data.data.selection[idx].deviceTypeName;
                        }
                    }
                }).then(() => {
                    this.visible = true
                    this.$nextTick(() => {
                        if(this.$refs['dataForms']!==undefined){
                            this.$refs['dataForms'].resetFields()
                        }
                    })
                }).then(() => {
                if (id) {
                    const loading = this.$loading({
                        lock: true,
                        text: '加载中.......',
                        spinner: 'el-icon-loading',
                        background: 'rgba(0, 0, 0, 0.7)'
                    });
                    this.title = '修改';
                    console.log("打开编辑弹窗,ID:"+id);
                    getInfoBatteryTypeDetail(id).then((res) => {
                        console.log("单条详细:"+JSON.stringify(res.data.data.data));
                        if (res.data && res.data.code === 0) {

                            this.dataForm.batteryTypeId = res.data.data.data.batteryTypeId;
                            //this.dataForm.deviceTypeId = res.data.data.data.deviceTypeId;
                            this.dataForm.batteryTypeName = res.data.data.data.batteryTypeName;
                            this.dataForm.batteryTypeVoltage = res.data.data.data.batteryTypeVoltage;
                            this.dataForm.batteryTypeEnergy = res.data.data.data.batteryTypeEnergy;
                            this.dataForm.batteryTypeCurrent = res.data.data.data.batteryTypeCurrent;
                            this.dataForm.isDeleted = res.data.data.data.isDeleted;
                            this.dataForm.isEnabled = res.data.data.data.isEnabled;
                            this.dataForm.createdBy = res.data.data.data.createdBy;
                            this.dataForm.createdDate = res.data.data.data.createdDate;
                            this.dataForm.modifiedBy = res.data.data.data.modifiedBy;
                            this.dataForm.modifiedDate = res.data.data.data.modifiedDate;

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
                    this.value = true;
                }

                });
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
                            console.log("修改提交:"+JSON.stringify(this.dataForm));
                            editInfoBatteryType(this.dataForm).then((res) => {
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
                            console.log("新增提交:"+JSON.stringify(this.dataForm));
                            saveInfoBatteryType(this.dataForm).then((res) => {
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
            },
            closeDialog(){
                console.log("关闭弹窗!");
                if(this.$refs['dataForms']!==undefined){
                    this.$refs['dataForms'].resetFields()
                }
            }
        }
    }

</script>
<style>
</style>
