<template>
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" style="color:#fff">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForms" @keyup.enter.native="dataFormSubmit()" label-width="120px">

            <el-form-item label="标签名称" prop="advertiseTargetName">
                <el-input v-model="dataForm.advertiseTargetName" placeholder="标签名称" maxlength="20"></el-input>
            </el-form-item>
            <el-form-item label="标签编码" prop="advertiseTargetCode">
                <el-input v-model="dataForm.advertiseTargetCode" placeholder="标签编码" maxlength="32"></el-input>
            </el-form-item>
            <el-form-item label="标签分类" size="mini" prop="advertiseTargetGrope">
                <el-input v-model="dataForm.advertiseTargetGrope" placeholder="标签分类" maxlength="32"></el-input>
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
    import {saveInfoAdvertiseTarget, editInfoAdvertiseTarget, getInfoAdvertiseTargetDetail} from "@/api/advertise";

    export default {
        name: 'Dialog',
        data() {
            return {
                title: '',
                visible: false,
                value: true,
                advertiseTagGroupOptions:[],
                dataForm: {
                    advertiseTargetId: '',
                    advertiseTargetName: '',
                    advertiseTargetCode: '',
                    advertiseTargetGrope:'',
                    isEnabled: '',
                    isDeleted: ''
                },
                dataRule: {
                    advertiseTargetName: [
                        {required: true, message: '请输入标签名称', trigger: 'blur'},
                        {max: 20, message: '长度不能超过20个字符', trigger: 'blur'}
                    ],
                    advertiseTargetGrope: [
                        {required: true, message: '请输入标签分类', trigger: 'blur'},
                        {max: 32, message: '长度不能超过32个字符', trigger: 'blur'}
                    ],
                    advertiseTargetCode: [
                        {required: true, message: '请输入标签编码', trigger: 'blur'},
                        {max: 32, message: '长度不能超过32个字符', trigger: 'blur'}
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
                    getInfoAdvertiseTargetDetail(id).then((res) => {
                        console.log("单条详细:"+JSON.stringify(res.data.data.data));
                        if (res.data && res.data.code === 0) {

                            this.dataForm.advertiseTargetId = res.data.data.data.advertiseTargetId;
                            this.dataForm.advertiseTargetName = res.data.data.data.advertiseTargetName;
                            this.dataForm.advertiseTargetCode = res.data.data.data.advertiseTargetCode;
                            this.dataForm.advertiseTargetGrope = res.data.data.data.advertiseTargetGrope;
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
                            editInfoAdvertiseTarget(this.dataForm).then((res) => {
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
                            saveInfoAdvertiseTarget(this.dataForm).then((res) => {
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
