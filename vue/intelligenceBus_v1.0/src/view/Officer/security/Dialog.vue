<template>
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" style="color:#fff">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForms" @keyup.enter.native="dataFormSubmit()" label-width="120px">
   <label>基本信息</label>
            <el-form-item label="身份证号" prop="idNo">
                <el-input v-model="dataForm.idNo" placeholder="身份证号" v-bind:disabled="disabled"></el-input>
            </el-form-item>
            <el-form-item label="姓名" prop="fullName">
                <el-input v-model="dataForm.fullName" placeholder="姓名"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="sex" size="mini">
                    <el-select style="margin-bottom: 4px;" v-model="dataForm.sex" filterable default-first-option placeholder="选择">
                        <el-option v-for="item in sexOptions" :key="item.idx" :label="item.vle" :value="item.idx"></el-option>
                    </el-select>
            </el-form-item>
            <el-form-item label="年龄" prop="age">
                <el-input v-model="dataForm.age" placeholder="年龄"></el-input>
            </el-form-item>


            <el-form-item label="驾驶主岗备岗">
                <template>
                    <div style="margin-top: 8px">
                        <el-radio v-model="dataForm.isPrimary" label="1" border size="medium">主岗</el-radio>
                        <el-radio v-model="dataForm.isPrimary" label="2" border size="medium">备岗</el-radio>
                    </div>
                </template>
            </el-form-item>

            <el-form-item label="身份验证照片">
                <el-upload ref="uploadIDF"
                           name="filename"
                           action="upUrl"
                           :before-upload="beforeUp"
                           :on-exceed="exceed"
                           :http-request="uploadIdF"
                           :on-success="uploadSuccess"
                           accept=".jpg,.jpeg,.png,.gif"
                           :limit="1" :with-credentials="false">
                    <img v-if="dataForm.takePhoto" :src="dataForm.takePhoto"
                         style="height: 38px;width: 38px; margin-right: 20px;float:left" class="avatar">
                    <el-button size="small" type="primary">选择要上传的图片</el-button>
                </el-upload>
            </el-form-item>


            <el-form-item label="居住地址" prop="residentialAddress">
                <el-input v-model="dataForm.residentialAddress" placeholder="居住地址"></el-input>
            </el-form-item>

            <hr>
            <label>安全员信息</label>

            <el-form-item label="手机号" prop="mobileNumber">
                <el-input v-model="dataForm.mobileNumber" placeholder="手机号"></el-input>
            </el-form-item>

            <el-form-item label="无犯罪记录证明" prop="noCriminalRecordPhoto">
                <el-upload ref="fileEleNOC"
                           name="filename"
                           action=""
                           :before-upload="beforeUp"
                           :on-exceed="exceed"
                           :http-request="uploadNoC"
                           accept=".jpg,.jpeg,.png,.gif"
                           :limit="1" :with-credentials="false">
                    <img v-if="dataForm.noCriminalRecordPhoto" :src="dataForm.noCriminalRecordPhoto"
                         style="height: 38px;width: 38px; margin-right: 20px;float:left" class="avatar">
                    <el-button size="small" type="primary">选择要上传的图片</el-button>
                </el-upload>
            </el-form-item>
            <el-form-item label="系统登录用户id" prop="loginUserId">
                <el-input v-model="dataForm.loginUserId" placeholder="系统登录用户id" disabled ></el-input>
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
    getSavetyOfficerDetail,
    saveSafetyOfficerInfo,
    updateSafetyOfficerInfo,
    uploadOfficerFile
} from "@/api/parameter";
import {API_SERVER_URL} from "@/api";
    // import {getDeviceTypeS,getBusS} from "@/api/selectionApi";

    export default {
        name: 'Dialog',
        data() {
            return {
                readonly: false,
                title: '',
                visible: false,
                value: false,
                disabled:false,
                sexOptions:[{
                    "idx":'0',
                    "vle":"男"
                },{
                    "idx":'1',
                    "vle":"女"
                }],
                dataForm: {
                    residentialAddress: '',
                    modifyUserName: '',
                    createUserId: '',
                    modifyDt: '',
                    isPrimary: "1",
                    createUserName: '',
                    noCriminalRecordPhoto: '',
                    sex: '',
                    basicId: '',
                    loginName: '',
                    isDeleted: 0,
                    idNo: '',
                    fullName: '',
                    createDt: '',
                    loginUserId: '',
                    takePhoto: '',
                    id: '',
                    mobileNumber: '',
                    modifyUserId: '',
                    category: '',
                    age: 0
                },
                dataRule: {
                    idNo: [
                        { required: true, message: '身份证号不能为空', trigger: 'blur' }
                    ],
                    fullName: [
                        { required: true, message: '姓名不能为空', trigger: 'blur' }
                    ],
                    sex: [
                        { required: true, message: '性别不能为空', trigger: 'blur' }
                    ],
                    age: [
                        { required: true, message: '年龄不能为空', trigger: 'blur' }
                    ],
                    takePhoto: [
                        { required: true, message: '身份验证照片不能为空', trigger: 'blur' }
                    ],
                    residentialAddress: [
                        { required: true, message: '居住地址不能为空', trigger: 'blur' }
                    ]
                }
            }
        },
        methods: {
            uploadIdF: function (options) {
                console.log("------> " + options)
                this.uploadForm.append("type","take")
                uploadOfficerFile(this.uploadForm).then((res) => {

                    console.log("上传文件后的返回值==>" + JSON.stringify(res));
                    this.dataForm.takePhoto = API_SERVER_URL + '/officer/file' + res.data.data.url;
                    this.uploadForm.delete("filename")
                    this.uploadForm.delete("type")
                })
                this.$refs.uploadIDF.clearFiles();
            },
            uploadNoC: function (options) {
                console.log("------> " + options)
                this.uploadForm.append("type","noc")
                uploadOfficerFile(this.uploadForm).then((res) => {
                    console.log("上传文件后的返回值==>" + JSON.stringify(res));
                    this.dataForm.noCriminalRecordPhoto = API_SERVER_URL + '/officer/file' + res.data.data.url;
                    this.uploadForm.delete("filename")
                    this.uploadForm.delete("type")
                })
                this.$refs.fileEleNOC.clearFiles();
            },
            //编辑或新增进入弹窗
            initData: function (id,basicId) {

                this.id = id;
                this.visible = true;
                this.value = true;
                this.readonly = false;
                this.dataForm.basicId = basicId;
                this.uploadForm = new FormData();
                this.$nextTick(() => {
                    if (this.$refs['dataForms'] !== undefined) {
                        this.$refs['dataForms'].resetFields()
                    }
                })
                console.log("打开时的id:"+id)
                if (id) {
                    this.readonly = true;
                    const loading = this.$loading({
                        lock: true,
                        text: '加载中.......',
                        spinner: 'el-icon-loading',
                        background: 'rgba(0, 0, 0, 0.7)'
                    });
                    this.title = '修改';
                    this.disabled=true;
                    console.log("打开编辑弹窗,ID:"+id);
                    getSavetyOfficerDetail(id).then((res) => {
                        console.log("单条详细:"+JSON.stringify(res.data.data));
                        if (res.data && res.data.code === 0) {
                            this.dataForm.residentialAddress = res.data.data.data.residentialAddress,//"霜革",
                                this.dataForm.modifyUserName = res.data.data.data.modifyUserName,//"sw",
                                this.dataForm.createUserId = res.data.data.data.createUserId,//"1351438597974130689",
                                this.dataForm.modifyDt = res.data.data.data.modifyDt,//"2021-01-23 00:00:00",
                                this.dataForm.isPrimary = res.data.data.data.isPrimary,//"1",
                                this.dataForm.createUserName = res.data.data.data.createUserName,//"sw",
                                this.dataForm.noCriminalRecordPhoto = res.data.data.data.noCriminalRecordPhoto,//"/aa/bb/cc",
                                this.dataForm.sex = res.data.data.data.sex,//"1",
                                this.dataForm.basicId = res.data.data.data.basicId,//"1352503542065135617",
                                this.dataForm.loginName = res.data.data.data.loginName,//"li",
                                this.dataForm.isDeleted = res.data.data.data.isDeleted,//0,
                                this.dataForm.idNo = res.data.data.data.idNo,//"123456789098765432",
                                this.dataForm.fullName = res.data.data.data.fullName,//"李却龙",
                                this.dataForm.createDt = res.data.data.data.createDt,//"2021-01-23 00:00:00",
                                this.dataForm.loginUserId = res.data.data.data.loginUserId,//"1351421326841085953",
                                this.dataForm.takePhoto = res.data.data.data.takePhoto,//"/aa/",
                                this.dataForm.id = res.data.data.data.id,//"1352511858698878978",
                                this.dataForm.mobileNumber = res.data.data.data.mobileNumber,//"15950829416",
                                this.dataForm.modifyUserId = res.data.data.data.modifyUserId,//"1351438597974130689",
                                this.dataForm.category = res.data.data.data.category,//"安全员",
                                this.dataForm.age = res.data.data.data.age

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
                    this.disabled=false;
                    this.value = true;
                }

                // getDeviceTypeS().then((res) => {
                //     if (res.data && res.data.code === 0) {
                //         this.deviceTypeIdOptions = res.data.data.selection
                //         return;
                //     }
                // });

                // getBusS().then((res) => {
                //     if (res.data && res.data.code === 0) {
                //         this.busIdOptions = res.data.data.selection
                //         return;
                //     }
                // });
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
                            updateSafetyOfficerInfo(this.dataForm).then((res) => {
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
                            saveSafetyOfficerInfo(this.dataForm).then((res) => {
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
            },
            upUrl() {
                return `${API_SERVER_URL}/officer/upload`;
            },
            uploadSuccess(res,file){
                console.log(res);
            },
            beforeUp(file) {
                // this.dataForm.takePhoto=URL.createObjectURL(file);
                this.uploadForm.append('filename', file)
                this.nextStepDisabled = true;
                 return true;
            },
            exceed(files, fileList) {
                this.$message({
                    message: "超出上传文件数量限制，请先删除已有文件后再上传",
                    type: 'error',
                    duration: 3000
                })
            }
        }
    }

</script>
<style>
</style>
