<template>
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" style="color:#fff">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForms" @keyup.enter.native="dataFormSubmit()" label-width="120px">

            <el-form-item label="广告位分类" prop="positionGroup">
                <el-input v-model="dataForm.positionGroup" placeholder="广告位分类" maxlength="34"></el-input>
            </el-form-item>
            <el-form-item label="广告位编码" prop="positionCode">
                <el-input v-model="dataForm.positionCode" placeholder="广告位编码" maxlength="34"></el-input>
            </el-form-item>
            <el-form-item label="广告位描述" size="mini" prop="positionDesc">
                <el-input v-model="dataForm.positionDesc" type="textarea"  placeholder="广告位描述" maxlength="100"></el-input>
            </el-form-item>
            <el-form-item label="像素高(px)" size="mini" prop="pixelHeight">
                <el-input v-model.number="dataForm.pixelHeight" placeholder="像素高" maxlength="10"></el-input>
            </el-form-item>
            <el-form-item label="像素宽(px)" size="mini" prop="pixelWidth">
                <el-input v-model.number="dataForm.pixelWidth" placeholder="像素宽" maxlength="10"></el-input>
            </el-form-item>
            <el-form-item label="屏幕高(cm)" size="mini" prop="screenHeight">
                <el-input v-model.number="dataForm.screenHeight" placeholder="屏幕高" maxlength="10"></el-input>
            </el-form-item>
            <el-form-item label="屏幕宽(cm)" size="mini" prop="screenWidth">
                <el-input v-model.number="dataForm.screenWidth" placeholder="屏幕宽" maxlength="10"></el-input>
            </el-form-item>
            <el-form-item label="素材类型" size="mini" prop="advertiseType">
                <el-select
                    style="margin-bottom: 4px;"
                    v-model="dataForm.advertiseType"
                    filterable
                    default-first-option
                    placeholder="请选择素材类型">
                    <el-option
                        v-for="item in advertiseTypeOptions"
                        :key="item.value"
                        :label="item.name"
                        :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>

        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button @click="visible = false">取消</el-button>
            <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
        </span>
    </el-dialog>
</template>
<script>
    import {saveInfoAdvertisePosition, editInfoAdvertisePosition, getInfoAdvertisePositionDetail} from "@/api/advertise";

    export default {
        name: 'Dialog',
        data() {
            return {
                title: '',
                visible: false,
                value: true,
                advertiseTypeOptions:[
                    {"value":0,"name":"图片"},
                    {"value":1,"name":"视频"},
                    {"value":2,"name":"所有"}
                ],
                dataForm: {
                    advertisePositionId: '',
                    positionGroup: '',
                    positionCode: '',
                    positionDesc:'',
                    pixelHeight: '',
                    pixelWidth: '',
                    screenHeight: '',
                    screenWidth: '',
                    advertiseType:''
                },
                dataRule: {
                    positionGroup: [
                        {required: true, message: '请输入分类', trigger: 'blur'},
                        {max: 34, message: '长度不能超过34个字符', trigger: 'blur'}
                    ],
                    positionCode: [
                        {required: true, message: '请输入编码', trigger: 'blur'},
                        {max: 34, message: '长度不能超过34个字符', trigger: 'blur'}
                    ],
                    positionDesc: [
                        {required: true, message: '请输入描述', trigger: 'blur'},
                        {max: 100, message: '长度不能超过100个字符', trigger: 'blur'}
                    ],
                    pixelHeight: [
                        {required: true, message: '请输入像素高', trigger: 'blur'},
                        { type: 'number', message: '像素高必须为数字值'}
                    ],
                    pixelWidth: [
                        {required: true, message: '请输入像素宽', trigger: 'blur'},
                        { type: 'number', message: '像素宽必须为数字值'}
                    ],
                    screenHeight: [
                        {required: true, message: '请输入屏幕高', trigger: 'blur'},
                        { type: 'number', message: '屏幕高必须为数字值'}
                    ],
                    screenWidth: [
                        {required: true, message: '请输入屏幕高', trigger: 'blur'},
                        { type: 'number', message: '屏幕高必须为数字值'}
                    ],
                    advertiseType: [
                        {required: true, message: '请选择素材类型', trigger: 'change'}
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
                    getInfoAdvertisePositionDetail(id).then((res) => {
                        console.log("单条详细:"+JSON.stringify(res.data.data.infoAdvertisePosition));
                        if (res.data && res.data.code === 0) {
                            this.dataForm.advertisePositionId = res.data.data.infoAdvertisePosition.advertisePositionId;
                            this.dataForm.positionGroup = res.data.data.infoAdvertisePosition.positionGroup;
                            this.dataForm.positionCode = res.data.data.infoAdvertisePosition.positionCode;
                            this.dataForm.positionDesc = res.data.data.infoAdvertisePosition.positionDesc;
                            this.dataForm.pixelHeight = res.data.data.infoAdvertisePosition.pixelHeight;
                            this.dataForm.pixelWidth = res.data.data.infoAdvertisePosition.pixelWidth;
                            this.dataForm.screenWidth = res.data.data.infoAdvertisePosition.screenWidth;
                            this.dataForm.screenHeight = res.data.data.infoAdvertisePosition.screenHeight;
                            this.dataForm.advertiseType = res.data.data.infoAdvertisePosition.advertiseType;
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
                            editInfoAdvertisePosition(this.dataForm).then((res) => {
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
                            saveInfoAdvertisePosition(this.dataForm).then((res) => {
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
