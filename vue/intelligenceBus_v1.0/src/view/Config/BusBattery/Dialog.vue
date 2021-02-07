<template>
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" style="color:#fff">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForms" @keyup.enter.native="dataFormSubmit()" label-width="120px">

            <el-form-item label="公交VIN" size="mini" prop="busId">
                <el-select
                        style="margin-bottom: 4px;"
                        :disabled="disabled"
                        v-model="dataForm.busId"
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
            <el-form-item label="电池类型" size="mini" prop="batteryTypeId">
                <el-select
                        style="margin-bottom: 4px;"
                        v-model="dataForm.batteryTypeId"
                        filterable
                        default-first-option
                        placeholder="请选择电池类型">
                    <el-option
                            v-for="item in batteryTypeIdOptions"
                            :key="item.batteryTypeId"
                            :label="item.batteryTypeName"
                            :value="item.batteryTypeId">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="电池编号" prop="busBatteryCode">
                <el-input v-model="dataForm.busBatteryCode" placeholder="电池编号" maxlength="30"></el-input>
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
    import {saveInfoBusBattery, editInfoBusBattery, getInfoBusBatteryDetail} from "@/api/parameter";
    import {getBatteryTypeS,getBusS} from "@/api/selectionApi";

    export default {
        name: 'Dialog',
        data() {
            return {
                title: '',
                visible: false,
                value: false,
                busId:'',
                disabled:true,
                busIdOptions: [],
                batteryTypeIdOptions: [],
                dataForm: {
                    busBatteryId: '',
                    busId: '',
                    batteryTypeId: '',
                    busBatteryCode: '',
                    isDeleted: '',
                    isEnabled: 0,
                    createdBy: '',
                    createdDate: '',
                    modifiedBy: '',
                    modifiedDate: ''
                },
                dataRule: {
                    busId: [
                        {required: true, message: '请选择所属车辆', trigger: 'change'}
                    ],
                    batteryTypeId: [
                        {required: true, message: '请选择电池类型', trigger: 'change'}
                    ],
                    busBatteryCode: [
                        {required: true, message: '请输入电池编号', trigger: 'blur'},
                        {max: 64, message: '长度不能超过64个字符', trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            //编辑或新增进入弹窗
            initData: function (busId,id) {
                this.busId = busId;
                this.visible = true;
                this.value = false;
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
                    console.log("打开编辑弹窗,ID:"+id);
                    getInfoBusBatteryDetail(id).then((res) => {
                        console.log("单条详细:"+JSON.stringify(res.data.data.data));
                        if (res.data && res.data.code === 0) {

                            this.dataForm.busBatteryId = res.data.data.data.busBatteryId;
                            this.dataForm.busId = res.data.data.data.busId;
                            this.dataForm.batteryTypeId = res.data.data.data.batteryTypeId;
                            this.dataForm.busBatteryCode = res.data.data.data.busBatteryCode;
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

                getBusS().then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.busIdOptions = res.data.data.selection
                        return;
                    }
                });

                getBatteryTypeS().then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.batteryTypeIdOptions = res.data.data.selection
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
                            editInfoBusBattery(this.dataForm).then((res) => {
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
                            saveInfoBusBattery(this.dataForm).then((res) => {
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
