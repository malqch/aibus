<template>
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" style="color:#fff">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForms" @keyup.enter.native="dataFormSubmit()" label-width="120px">

            <el-form-item label="事件级别名称" prop="eventLevelName">
                <el-input v-model="dataForm.eventLevelName" placeholder="事件级别名称" maxlength="30"></el-input>
            </el-form-item>
            <el-form-item label="事件级别编码" prop="eventLevelCode">
                <el-input v-model="dataForm.eventLevelCode" placeholder="事件级别编码" maxlength="30"></el-input>
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
    import {saveInfoEventLevel, editInfoEventLevel, getInfoEventLevelDetail} from "@/api/event";

    export default {
        name: 'Dialog',
        data() {
            return {
                title: '',
                visible: false,
                value: true,
                dataForm: {
                    eventLevelId: '',
                    eventLevelName: '',
                    eventLevelCode: '',
                    isEnabled: '',
                    isDeleted: ''
                },
                dataRule: {
                    eventLevelName: [
                        {required: true, message: '请输入事件级别名称', trigger: 'blur'},
                        {max: 20, message: '长度不能超过20个字符', trigger: 'blur'}
                    ],
                    eventLevelCode: [
                        {required: true, message: '请输入事件级别编码', trigger: 'blur'},
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
                for (let key in this.dataForm) {
                    this.dataForm[key] = ''
                }

                if (id) {
                    const loading = this.$loading({
                        lock: true,
                        text: '加载中.......',
                        spinner: 'el-icon-loading',
                        background: 'rgba(0, 0, 0, 0.7)'
                    });
                    this.title = '修改';
                    console.log("打开编辑弹窗,ID:"+id);
                    getInfoEventLevelDetail(id).then((res) => {
                        console.log("单条详细:"+JSON.stringify(res.data.data.data));
                        if (res.data && res.data.code === 0) {

                            this.dataForm.eventLevelId = res.data.data.data.eventLevelId;
                            this.dataForm.eventLevelName = res.data.data.data.eventLevelName;
                            this.dataForm.eventLevelCode = res.data.data.data.eventLevelCode;
                            this.dataForm.isDeleted = res.data.data.data.isDeleted;
                            this.dataForm.isEnabled = res.data.data.data.isEnabled;

                            this.value = res.data.data.data.isEnabled === 1;
                        }
                    });
                    loading.close();
                } else {
                    console.log("打开新增弹窗");
                    this.title = '新增'

                    this.dataForm.isEnabled = 0;
                    this.dataForm.isDeleted = 0;
                    this.value = true;
                }

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
                            editInfoEventLevel(this.dataForm).then((res) => {
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
                            saveInfoEventLevel(this.dataForm).then((res) => {
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
