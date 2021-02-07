<template>
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" style="color:#fff">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForms" @keyup.enter.native="dataFormSubmit()" label-width="120px">
            <el-form-item label="参数名称" prop="paramName">
                <el-input v-model="dataForm.paramName" placeholder="参数名称" maxlength="30"></el-input>
            </el-form-item>
            <el-form-item label="参数编码" prop="paramCode">
                <el-input v-model="dataForm.paramCode" placeholder="参数编码" maxlength="30"></el-input>
            </el-form-item>
            <el-form-item label="参数分组" prop="paramGroup">
                <el-input v-model="dataForm.paramGroup" placeholder="参数分组" maxlength="30"></el-input>
            </el-form-item>
            <el-form-item label="数值" prop="paramValue">
                <el-input v-model="dataForm.paramValue" placeholder="数值" maxlength="30"></el-input>
            </el-form-item>
            <el-form-item label="字符" prop="paramChar">
                <el-input v-model="dataForm.paramChar" placeholder="字符" maxlength="30"></el-input>
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
    import {saveParameter, editParameter, getParameterDetail} from "@/api/parameter";

    export default {
        name: 'Dialog',
        data() {
            return {
                title: '',
                visible: false,
                value: false,
                dataForm: {
                    configParamId: '',
                    paramName: '',
                    paramCode: '',
                    paramGroup: '',
                    paramValue: '',
                    paramChar: '',
                    isEnabled: 0
                },
                dataRule: {
                    paramName: [
                        {required: true, message: '请输入参数名称', trigger: 'blur'},
                        {max: 30, message: '长度不能超过30个字符', trigger: 'blur'}
                    ],
                    paramCode: [
                        {required: true, message: '请输入参数编码', trigger: 'blur'},
                        {max: 30, message: '长度不能超过30个字符', trigger: 'blur'}
                    ],
                    paramGroup: [
                        {required: true, message: '请输入参数分组', trigger: 'blur'},
                        {max: 30, message: '长度不能超过30个字符', trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            //编辑或新增进入弹窗
            initData: function (id) {
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
                    getParameterDetail(id).then((res) => {
                        console.log("单条详细:"+JSON.stringify(res.data.data.data));
                        if (res.data && res.data.code === 0) {
                            this.dataForm.configParamId = res.data.data.data.configParamId;
                            this.dataForm.paramName = res.data.data.data.paramName;
                            this.dataForm.paramCode = res.data.data.data.paramCode;
                            this.dataForm.paramGroup = res.data.data.data.paramGroup;
                            this.dataForm.paramValue = res.data.data.data.paramValue;
                            this.dataForm.paramChar = res.data.data.data.paramChar;
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
                            editParameter(this.dataForm).then((res) => {
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
                            saveParameter(this.dataForm).then((res) => {
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
