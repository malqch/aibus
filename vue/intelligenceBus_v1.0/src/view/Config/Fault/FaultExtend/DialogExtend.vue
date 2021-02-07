<template>
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" style="color:#fff">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForms" @keyup.enter.native="dataFormSubmit()"
                 label-width="120px">
            <el-form-item label="故障标签" prop="faultTargetId">
                <el-select v-model="dataForm.faultTargetId" placeholder="请选择故障标签">
                    <el-option :label="item.faultTargetName" :value="item.faultTargetId" :key="item.faultTargetId"
                               v-for="(item, index) in faultTargetList">{{item.faultTargetName}}
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
    import {
        getInfoFaultExtendDetail,
        saveInfoFaultExtend,
        editInfoFaultExtend,
        getInfoFaultTargetListAllSub,
    } from "@/api/fault";

    export default {
        name: 'DialogExtend',
        data() {
            return {
                title: '',
                visible: false,
                value: false,
                faultTargetList: [],
                dataForm: {
                    faultExtendId: '',
                    collectFaultId: '',
                    faultTargetId: '',
                    isEnabled: 0,
                },
                dataRule: {
                    faultTargetId: [
                        {required: true, message: '请选择故障标签', trigger: 'blur'},
                        {required: true, message: '请选择故障标签', trigger: 'change'}
                    ]
                }
            }
        },
        methods: {
            initData: function (collectFaultId,id, mark) {
                this.visible = true;
                this.value = false;
                this.$nextTick(() => {
                    if (this.$refs['dataForms'] !== undefined) {
                        this.$refs['dataForms'].resetFields()
                    }
                })
                getInfoFaultTargetListAllSub().then((res) => {
                    if (res.data && res.data.code == 0) {
                        this.faultTargetList = res.data.data.list
                    }
                })
                this.dataForm.collectFaultId = collectFaultId;
                if (mark == '修改') {
                    const loading = this.$loading({
                        lock: true,
                        text: '加载中.......',
                        spinner: 'el-icon-loading',
                        background: 'rgba(0, 0, 0, 0.7)'
                    });
                    this.title = '修改';
                    console.log(id);
                    getInfoFaultExtendDetail(id).then((res) => {
                        if (res.data && res.data.code === 0) {
                            console.log(res.data.data);
                            this.dataForm.faultExtendId = res.data.data.data.faultExtendId;
                            this.dataForm.collectFaultId = res.data.data.data.collectFaultId;
                            this.dataForm.faultTargetId = res.data.data.data.faultTargetId;
                            this.dataForm.isEnabled = res.data.data.data.isEnabled;
                            if (this.dataForm.isEnabled == 1) {
                                this.value = true
                            } else {
                                this.value = false
                            }
                        }
                    })
                    loading.close();
                }
                if (mark == '新增') {
                    const loading = this.$loading({
                        lock: true,
                        text: '加载中.......',
                        spinner: 'el-icon-loading',
                        background: 'rgba(0, 0, 0, 0.7)'
                    });

                    this.title = '新增';
                    this.value = true;
                    loading.close();
                }
            },
            // 表单提交
            dataFormSubmit: function () {
                console.log(this.dataForm);
                if (this.value == true) {
                    this.dataForm.isEnabled = 1;
                } else {
                    this.dataForm.isEnabled = 0;
                }
                this.$refs['dataForms'].validate((valid) => {
                    if (valid) {
                        console.log(valid);
                        console.log(this.title);
                        const loading = this.$loading({
                            lock: true,
                            text: '加载中.......',
                            spinner: 'el-icon-loading',
                            background: 'rgba(0, 0, 0, 0.7)'
                        });
                        if (this.title == '修改') {
                            console.log(this.dataForm);
                            editInfoFaultExtend(this.dataForm).then((res) => {
                                loading.close();
                                if (res.data && res.data.code == 0) {
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
                            saveInfoFaultExtend(this.dataForm).then((res) => {
                                loading.close();
                                if (res.data && res.data.code == 0) {
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
