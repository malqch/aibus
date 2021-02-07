<!--
 @Author: songwenxin
 @Filename: TipBox.vue
 @ProjectName: fs-manage-view
 @Mail: songwenxin666@sina.com
 @Date: 2019-12-13 19:28
 @file-description:
-->


<template>
    <el-dialog title="上传SN码文件" :visible.sync="formAddVisible" :before-close="handleClose" :close-on-click-modal="false">
        <el-form :model="formAdd" :rules="formAddRules" ref="formAdd" label-width="140px">

            <el-form-item label="更新类型">
                <el-select v-model="formAdd.updateType" placeholder="请选择更新类型"  @change="changeSelect" >
                    <el-option v-for="item in updataTypeList" :key="item.updateTypeId" :label="item.updateTypeName" :value="item.updateTypeId" />
                </el-select>
            </el-form-item>
            <el-form-item label="更新描述" prop="restName">
                <el-input v-model="formAdd.restName" maxlength="16"></el-input>
            </el-form-item>
            <el-form-item label="是否发布" >
                <el-switch v-model="value" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
            </el-form-item>
            <el-form-item label="SNExcel文件">
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
                    accept="*">
                    <el-button size="small" type="primary">选择Sn码Excel文件</el-button>
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
    import { API_SERVER_URL } from '@/api'
    export default {
        name: 'TipBox',
        data() {
            return {
                updataTypeList : [
                    {
                        updateTypeId : '0',
                        updateTypeName : '物联代理更新'
                    },
                    {
                        updateTypeId : '1',
                        updateTypeName : '物联网关更新'
                    },
                    {
                        updateTypeId : '2',
                        updateTypeName : '缓存数据更新'
                    },
                    {
                        updateTypeId : '3',
                        updateTypeName : '视频监控更新'
                    },
                    {
                        updateTypeId : '4',
                        updateTypeName : 'IoT网关更新'
                    }
                ],
                value : '',
                formAdd: {
                    enterDeviceId: '',
                    updateType : '',
                    restName : '',
                    isAudited : 0,
                    isEnabled : 0
                },

                //约定上传的阀值
                upFile: 0,
                nextStepDisabled: false,
                formAddRules: {},
                formAddVisible: false
            };
        },
        methods: {
            changeSelect : function(){
                console.log(this.formAdd.updateType);
            },
            initData : function(){
                this.formAddVisible = true;
            },
            handleClose(done) {
                this.resetForm();
                done();
            },
            //初始化上传表单
            resetForm(){
                this.$refs['formAdd'].resetFields();
                this.$refs.upload.clearFiles();
                this.formAddVisible = false;
                this.nextStepDisabled = false;
                this.upFile = 0;
            },
            //上传的url
            upUrl() {
                return API_SERVER_URL + "/sn/auditEnterDevice/save";
            },

            //约定上传文件的个数
            changeFile(file, fileList){
                if(fileList && fileList.length > 0){
                    this.upFile = 1;
                }
            },

            removeFile(file, fileList){
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
                        if(this.upFile == 1){
                            this.$refs.upload.submit();
                        }else {
                            this.$message.error({
                                dangerouslyUseHTMLString: true,
                                message: '请选择要上传的文件'
                            })
                        }
                    }
                });
            },
            // 成功上传文件
            upSuccess(res, file) {
                this.upFile = 0;
                //this.nextStepDisabled = false;
                if (res.code == 0) {
                    //this.resetForm();
                    //this.$refs.upload.clearFiles();
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
            upError(err, file, fileList){
                this.upFile = 0;
                this.nextStepDisabled = false;
            }

        },
        computed: {
            // 上传要绑定的表单的数据
            upData: function() {
                return this.formAdd;
            },
            //携带token上传
            upHeader: function() {
                return {
                    "token": window.sessionStorage.getItem('token')
                }
            }
        },
        mounted() {}

    }
</script>

<style scoped>

</style>


