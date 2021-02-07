<template>
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" style="color:#fff">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForms" @keyup.enter.native="dataFormSubmit()" label-width="120px">

            <el-form-item label="公交VIN" size="mini" prop="busId">
                <el-select
                        style="margin-bottom: 4px;"
                        v-model="dataForm.busId"
                        :disabled="disabled"
                        filterable
                        default-first-option
                        placeholder="请选择公交VIN">
                    <el-option
                            v-for="item in busIdOptions"
                            :key="item.busId"
                            :label="item.vinCode"
                            :value="item.busId">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="设备类型" size="mini" prop="deviceTypeId">
                <el-select
                        style="margin-bottom: 4px;"
                        v-model="dataForm.deviceTypeId"
                        placeholder="请选择设备类型">
                    <el-option
                            v-for="item in deviceTypeIdOptions"
                            :key="item.deviceTypeId"
                            :label="item.deviceTypeName"
                            :value="item.deviceTypeId">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="设备名称" prop="deviceName">
                <el-input v-model="dataForm.deviceName" placeholder="设备名称" maxlength="30"></el-input>
            </el-form-item>
            <el-form-item label="设备编号" prop="deviceCode">
                <el-input v-model="dataForm.deviceCode" placeholder="设备编号" maxlength="30"></el-input>
            </el-form-item>
            <el-form-item label="设备描述编码" prop="deviceDescCode">
                <el-input v-model="dataForm.deviceDescCode" :readonly="readonly" placeholder="设备描述编码" maxlength="30"></el-input>
            </el-form-item>
            <el-form-item label="设备状态" size="mini" prop="deviceStatus">
                <el-select
                        style="margin-bottom: 4px;"
                        v-model="dataForm.deviceStatus"
                        default-first-option
                        placeholder="请选择设备状态">
                    <el-option
                            v-for="item in deviceStatusOptions"
                            :key="item.deviceStatus"
                            :label="item.deviceStatusDesc"
                            :value="item.deviceStatus">
                    </el-option>
                </el-select>
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
    import {saveInfoBusDevice, editInfoBusDevice, getInfoBusDeviceDetail} from "@/api/parameter";
    import {getDeviceTypeS,getBusS} from "@/api/selectionApi";

    export default {
        name: 'Dialog',
        data() {
            return {
                readonly: false,
                title: '',
                visible: false,
                value: false,
                disabled:true,
                busId:'',
                deviceTypeIdOptions: [],
                busIdOptions: [],
                deviceStatusOptions: [{deviceStatus:0,deviceStatusDesc:'离线'},{deviceStatus:1,deviceStatusDesc:'在线'}],
                dataForm: {
                    busDeviceId: '',
                    deviceTypeId: '',
                    busId: '',
                    deviceName: '',
                    deviceCode: '',
                    deviceDescCode: '',
                    deviceStatus: '',
                    isDeleted: '',
                    isEnabled: 0,
                    createdBy: '',
                    createdDate: '',
                    modifiedBy: '',
                    modifiedDate: ''
                },
                dataRule: {
                    deviceTypeId: [
                        {required: true, message: '请选择设备类型', trigger: 'change'}
                    ],
                    busId: [
                        {required: true, message: '请选择所属车辆', trigger: 'change'}
                    ],
                    deviceName:  [
                        {required: true, message: '请输入设备名称', trigger: 'blur'},
                        {max: 32, message: '长度不能超过32个字符', trigger: 'blur'}
                    ],
                    deviceCode: [
                        {required: true, message: '请输入设备编码', trigger: 'blur'},
                        {max: 64, message: '长度不能超过64个字符', trigger: 'blur'}
                    ],
                    deviceDescCode: [
                        {required: true, message: '请输入设备描述编码', trigger: 'blur'},
                        {max: 64, message: '长度不能超过64个字符', trigger: 'blur'}
                    ],
                    deviceStatus: [
                        {required: true, message: '请选择设备状态', trigger: 'change'}
                    ],
                }
            }
        },
        methods: {
            //编辑或新增进入弹窗
            initData: function (busId,id) {
                this.busId = busId;
                this.visible = true;
                this.value = false;
                this.readonly = false;
                this.dataForm.busId = '';
                this.dataForm.deviceTypeId = '';
                this.dataForm.deviceStatus = 1;
                this.$nextTick(() => {
                    if (this.$refs['dataForms'] !== undefined) {
                        this.$refs['dataForms'].resetFields()
                    }
                })
                if (id) {
                    this.readonly = true;
                    const loading = this.$loading({
                        lock: true,
                        text: '加载中.......',
                        spinner: 'el-icon-loading',
                        background: 'rgba(0, 0, 0, 0.7)'
                    });
                    this.title = '修改';
                    console.log("打开编辑弹窗,ID:"+id);
                    getInfoBusDeviceDetail(id).then((res) => {
                        console.log("单条详细:"+JSON.stringify(res.data.data.data));
                        if (res.data && res.data.code === 0) {

                            this.dataForm.busDeviceId = res.data.data.data.busDeviceId;
                            this.dataForm.deviceTypeId = res.data.data.data.deviceTypeId;
                            this.dataForm.busId = res.data.data.data.busId;
                            this.dataForm.deviceName = res.data.data.data.deviceName;
                            this.dataForm.deviceCode = res.data.data.data.deviceCode;
                            this.dataForm.deviceDescCode = res.data.data.data.deviceDescCode;
                            this.dataForm.deviceStatus = res.data.data.data.deviceStatus;
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
                    this.dataForm.busId = this.busId;
                    this.value = true;
                }

                getDeviceTypeS().then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.deviceTypeIdOptions = res.data.data.selection
                        return;
                    }
                });

                getBusS().then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.busIdOptions = res.data.data.selection
                        return;
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
                            editInfoBusDevice(this.dataForm).then((res) => {
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
                            saveInfoBusDevice(this.dataForm).then((res) => {
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
