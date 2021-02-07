<template>
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" style="color:#fff">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForms" @keyup.enter.native="dataFormSubmit()" label-width="120px">

            <el-form-item label="故障标签名称" prop="faultTargetName">
                <el-input v-model="dataForm.faultTargetName" placeholder="故障标签名称" maxlength="20"></el-input>
            </el-form-item>
            <el-form-item label="故障标签编码" prop="faultTargetCode">
                <el-input v-model="dataForm.faultTargetCode" placeholder="故障标签编码" maxlength="20"></el-input>
            </el-form-item>
            <el-form-item label="标签分类编码" size="mini" prop="faultTargetGrope">
                <el-select
                    style="margin-bottom: 4px;"
                    v-model="dataForm.faultTargetGrope"
                    filterable
                    default-first-option
                    placeholder="请选择标签分类">
                    <el-option
                        v-for="item in eventTagGroupOptions"
                        :key="item.paramCode"
                        :label="item.paramCode"
                        :value="item.paramCode">
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
    import {saveInfoFaultTarget, editInfoFaultTarget, getInfoFaultTargetDetail,getInfoFaultTargetGroupS} from "@/api/fault";

    export default {
        name: 'Dialog',
        data() {
            return {
                title: '',
                visible: false,
                value: false,
                eventTagGroupOptions:[],
                dataForm: {
                    faultTargetId: '',
                    faultTargetName: '',
                    faultTargetCode: '',
                    faultTargetGrope:'',
                    isEnabled: ''
                },
                dataRule: {
                    faultTargetName: [
                        {required: true, message: '请输入故障标签名称', trigger: 'blur'},
                        {max: 20, message: '长度不能超过20个字符', trigger: 'blur'}
                    ],faultTargetGrope: [
                        {required: true, message: '请选择标签分类编码', trigger: 'change'}
                    ],faultTargetCode: [
                        {max: 20, message: '长度不能超过20个字符', trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            //编辑或新增进入弹窗
            initData: function (id) {
                this.visible = true;
                this.$nextTick(() => {
                    if (this.$refs['dataForms'] !== undefined) {
                        this.$refs['dataForms'].resetFields()
                    }
                });

                this.uploadForm = new FormData();
                if (id) {
                    const loading = this.$loading({
                        lock: true,
                        text: '加载中.......',
                        spinner: 'el-icon-loading',
                        background: 'rgba(0, 0, 0, 0.7)'
                    });
                    this.title = '修改';
                    console.log("打开编辑弹窗,ID:"+id);
                    getInfoFaultTargetDetail(id).then((res) => {
                        console.log("单条详细:"+JSON.stringify(res.data.data.data));
                        if (res.data && res.data.code === 0) {

                            this.dataForm.faultTargetId = res.data.data.data.faultTargetId;
                            this.dataForm.faultTargetName = res.data.data.data.faultTargetName;
                            this.dataForm.faultTargetCode = res.data.data.data.faultTargetCode;
                            this.dataForm.faultTargetGrope = res.data.data.data.faultTargetGrope;
                            this.dataForm.isDeleted = res.data.data.data.isDeleted;
                            this.dataForm.isEnabled = res.data.data.data.isEnabled;

                            this.value = res.data.data.data.isEnabled === 1;
                        }
                    });
                    loading.close();
                } else {
                    this.dataForm.faultTargetId = '';
                    this.dataForm.faultTargetGrope = '';
                    console.log("打开新增弹窗");
                    this.title = '新增'
                    this.dataForm.isEnabled = 0;
                    this.value = true;
                }

                getInfoFaultTargetGroupS().then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.eventTagGroupOptions = res.data.data.selection
                    }
                });


            },
            // 表单提交
            dataFormSubmit: function () {
                if (this.value) {
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

                        if (this.title === '修改') {
                            console.log("修改提交:"+JSON.stringify(this.dataForm));
                            editInfoFaultTarget(this.dataForm).then((res) => {
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
                        if (this.title === '新增') {
                            console.log("新增提交:"+JSON.stringify(this.dataForm));
                            saveInfoFaultTarget(this.dataForm).then((res) => {
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
