<template>
    <el-dialog :title="title" :visible.sync="formAddVisible" :before-close="handleClose" :close-on-click-modal="false">
        <el-form :model="formAdd" :rules="formAddRules" ref="formAdd" label-width="140px">
            <el-form-item v-if="isAdd" label="更新类型" prop="updateType">
                <el-select v-model="formAdd.updateType" placeholder="请选择更新类型" @change="changeSelect">
                    <el-option v-for="item in updataTypeList" :key="item.updateType" :label="item.updateTypeName"
                               :value="item.updateType"/>
                </el-select>
            </el-form-item>
            <el-form-item label="更新描述" prop="updateDesc">
                <el-input v-model="formAdd.updateDesc" maxlength="128"></el-input>
            </el-form-item>
            <el-form-item label="是否发布">
                <el-switch v-model="formAdd.isPublished" :active-value="1" :inactive-value="0" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
            </el-form-item>
            <el-form-item v-if="isAdd" label="上传更新包">
                <el-upload
                    ref="upload"
                    :headers="upHeader"
                    :action="upUrl()"
                    :on-success="upSuccess"
                    :on-error="upError"
                    :before-upload="beforeUp"
                    :data="upData"
                    :on-change="changeFile"
                    :before-remove="removeFile"
                    :auto-upload="false"
                    :limit="1"
                    :with-credentials="false"
                    name="updateFile"
                    accept="*">
                    <el-button size="small" type="primary">选择更新包文件</el-button>
                </el-upload>
            </el-form-item>

        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="resetForm">取 消</el-button>
            <el-button type="primary" :disabled="nextStepDisabled" @click="upAdd">确 定</el-button>
        </div>
    </el-dialog>
</template>

<script>
    import {getUpDateServerDetail, setQueryUpdateTypeSelection, setUpDateServerEdir} from "@/api/UpDateServerApi";
    import {API_SERVER_URL} from '@/api'
    import {json2UrlParam} from '@/util/json'

    export default {
        name: 'Dialog',
        data() {
            return {
                updataTypeList: [],
                title: '新增',
                isAdd: true,
                formAdd: {
                    updateListId: '',
                    updateTypeName: '',
                    updateType: '',
                    updateDesc: '',
                    isPublished: 0,
                },

                //约定上传的阀值
                upFile: 0,
                nextStepDisabled: false,
                formAddRules: {
                    updateType: [
                        {required: true, message: '请选择更新类型', trigger: 'change'}
                    ],
                    updateDesc: [
                        {required: true, message: '请输入更新描述', trigger: 'blur'},
                        {max: 128, message: '长度不能超过128个字符', trigger: 'blur'}
                    ]
                },
                formAddVisible: false
            };
        },
        methods: {
            changeSelect: function () {
                console.log(this.formAdd.updateType);
            },
            initData: function (id) {
                this.formAddVisible = true;
//                console.log(id);
                setQueryUpdateTypeSelection().then((res) => {
                    if (res.data && res.data.code == 0) {
                        console.log(res.data.data);
                        this.updataTypeList = res.data.data.selection;
                    }
                })
                if (id) {
                    this.isAdd = false
//                    console.log('触发了');
                    this.title = '修改';
                    const loading = this.$loading({
                        lock: true,
                        text: "加载中.......",
                        spinner: "el-icon-loading",
                        background: "rgba(0, 0, 0, 0.7)"
                    });
                    getUpDateServerDetail(id).then((res) => {
                        loading.close();
                        console.log(res.data);
                        if (res.data && res.data.code === 0) {
                            console.log(res.data.data.data);
                            //this.formAdd.updateDesc = '';
                            this.formAdd.updateListId = res.data.data.data.updateListId;
                            this.formAdd.updateDesc = res.data.data.data.updateDesc;
                            this.formAdd.updateType = res.data.data.data.updateType;
                            this.formAdd.isPublished = res.data.data.data.isPublished;
                        }
                    });
                } else {
                    this.isAdd = true
                    this.title = '新增'
                    this.formAdd.updateListId = ''
                    this.formAdd.updateDesc = ''
                    this.formAdd.updateType = ''
                }
            },
            handleClose(done) {
                this.resetForm()
                done();
            },
            //初始化上传表单
            resetForm() {
                this.$refs['formAdd'].resetFields();
                if(this.$refs.upload){
                    this.$refs.upload.clearFiles();
                }
                this.formAddVisible = false;
                this.nextStepDisabled = false;
                this.upFile = 0;
            },
            //上传的url
            upUrl() {
                if (this.title == '新增') {
                    return API_SERVER_URL + "/update/package/save";
                }
                if (this.title == '修改') {
                    return API_SERVER_URL + "/update/package/update";
                }

            },

            //约定上传文件的个数
            changeFile(file, fileList) {
                if (fileList && fileList.length > 0) {
                    this.upFile = 1;
                }
            },

            removeFile(file, fileList) {
                this.upFile = 0;
            },
            //上传之前的文件校验
            beforeUp(file) {
                //校验通过 => 上传按钮是否可点
                this.nextStepDisabled = true;
                return true;
            },
            upAdd() {
                this.$refs['formAdd'].validate((valid) => {
                    if (valid) {
                        //新增
                        if (this.isAdd) {
                            if (this.upFile == 1) {
                                this.$refs.upload.submit();
                            } else {
                                this.$message.error({
                                    dangerouslyUseHTMLString: true,
                                    message: '请选择要上传的文件'
                                })
                            }
                        }
                        //修改
                        else {
                            setUpDateServerEdir(json2UrlParam(this.formAdd)).then((res) => {
                                if (res.data && res.data.code === 0) {
//                                    console.log(res);
                                    this.$message({
                                        message: "操作成功",
                                        type: "success",
                                        offset: 300,
                                        duration: 1500,
                                        onClose: () => {
                                            this.resetForm();
//                                            this.$refs.upload.clearFiles();
                                            this.$emit('refreshDataList')
                                        }
                                    });
                                } else {
                                    this.$message({
                                        message: res.data.message,
                                        type: "error",
                                        offset: 300,
                                        duration: 1500,
                                        onClose: () => {
                                            this.resetForm();
//                                            this.$refs.upload.clearFiles();
                                            this.$emit('refreshDataList')
                                        }
                                    });
                                }
                            });
                        }
                    }
                });
            },
            // 成功上传文件
            upSuccess(res, file) {
                this.upFile = 0;
                this.nextStepDisabled = false;
                if (res.code == 0) {
                    this.$message({
                        message: res.message,
                        type: "success",
                        offset: 300,
                        duration: 1500,
                        onClose: () => {
                            this.resetForm();
                            this.$refs.upload.clearFiles();
                            this.$emit('refreshDataList')
                        }
                    });
                } else {
                    this.$message.error({
                        dangerouslyUseHTMLString: true,
                        duration: 5000,
                        showClose: true,
                        message: res.message
                    })


                }
            },
            //上传错误函数
            upError(err, file, fileList) {
                this.upFile = 0;
                this.nextStepDisabled = false;
            }

        },
        computed: {
            // 上传要绑定的表单的数据
            upData: function () {
                return this.formAdd;
            },
            //携带token上传
            upHeader: function () {
                return {
                    "token": window.sessionStorage.getItem('token')
                }
            }
        },
        mounted() {
        }

    }
</script>

<style scoped>

</style>
