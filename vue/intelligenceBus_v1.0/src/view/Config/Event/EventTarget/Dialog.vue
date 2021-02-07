<template>
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" style="color:#fff">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForms" @keyup.enter.native="dataFormSubmit()" label-width="120px">

            <el-form-item label="事件标签名称" prop="eventTargetName">
                <el-input v-model="dataForm.eventTargetName" placeholder="事件标签名称" maxlength="30"></el-input>
            </el-form-item>
            <el-form-item label="事件标签编码" prop="eventTargetCode">
                <el-input v-model="dataForm.eventTargetCode" placeholder="事件标签编码" maxlength="30"></el-input>
            </el-form-item>
            <el-form-item label="标签分类编码" size="mini" prop="eventTargetGrope">
                <el-select
                    style="margin-bottom: 4px;"
                    v-model="dataForm.eventTargetGrope"
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
    import {saveInfoEventTarget, editInfoEventTarget, getInfoEventTargetDetail,getInfoEventTargetGroupS} from "@/api/event";

    export default {
        name: 'Dialog',
        data() {
            return {
                title: '',
                visible: false,
                value: true,
                eventTagGroupOptions:[],
                dataForm: {
                    eventTargetId: '',
                    eventTargetName: '',
                    eventTargetCode: '',
                    eventTargetGrope:'',
                    isEnabled: '',
                    isDeleted: ''
                },
                dataRule: {
                    eventTargetName: [
                        {required: true, message: '请输入事件标签名称', trigger: 'blur'},
                        {max: 20, message: '长度不能超过20个字符', trigger: 'blur'}
                    ],
                    eventTargetGrope: [
                        {required: true, message: '请选择标签分类', trigger: 'change'}
                    ],
                    eventTargetCode: [
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
                    getInfoEventTargetDetail(id).then((res) => {
                        console.log("单条详细:"+JSON.stringify(res.data.data.data));
                        if (res.data && res.data.code === 0) {

                            this.dataForm.eventTargetId = res.data.data.data.eventTargetId;
                            this.dataForm.eventTargetName = res.data.data.data.eventTargetName;
                            this.dataForm.eventTargetCode = res.data.data.data.eventTargetCode;
                            this.dataForm.eventTargetGrope = res.data.data.data.eventTargetGrope;
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

                getInfoEventTargetGroupS().then((res) => {
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
                            editInfoEventTarget(this.dataForm).then((res) => {
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
                            saveInfoEventTarget(this.dataForm).then((res) => {
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
