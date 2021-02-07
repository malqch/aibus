<template>
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" style="color:#fff">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForms" @keyup.enter.native="dataFormSubmit()"
                 label-width="120px">
            <label>基本信息</label>
            <el-form-item label="身份证号" prop="idNo">
                <el-input v-model="dataForm.idNo" placeholder="身份证号" v-bind:disabled="disabled"></el-input>
            </el-form-item>
            <el-form-item label="姓名" prop="fullName">
                <el-input v-model="dataForm.fullName" placeholder="姓名"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="sex" size="mini">
                <el-select style="margin-bottom: 4px;" v-model="dataForm.sex" filterable default-first-option
                           v-bind:disabled="disabled" placeholder="选择">
                    <el-option v-for="item in sexOptions" :key="item.idx" :label="item.vle"
                               :value="item.idx"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="年龄" prop="age">
                <el-input v-model="dataForm.age" placeholder="年龄" v-bind:disabled="disabled"></el-input>
            </el-form-item>


            <el-form-item label="身份验证照片">
                <el-upload ref="fileEleIDF"
                           name="filename"
                           action=""
                           :before-upload="beforeUp"
                           :on-exceed="exceed"
                           :http-request="uploadIdF"
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
            <label>驾驶员信息</label>
            <el-form-item label="驾驶证件号" prop="drivingLicenseNo">
                <el-input v-model="dataForm.drivingLicenseNo" placeholder="驾驶证件号" v-bind:disabled="disabled"></el-input>
            </el-form-item>

            <el-form-item label="驾驶主岗备岗">
                <template>
                    <div style="margin-top: 8px">
                        <el-radio v-model="dataForm.isPrimary" label="1" border size="medium">主岗</el-radio>
                        <el-radio v-model="dataForm.isPrimary" label="2" border size="medium">备岗</el-radio>
                    </div>
                </template>
            </el-form-item>

            <el-form-item label="初次领证时间" prop="firstIssueDate">
                <!--                <el-input v-model="dataForm.firstIssueDate" placeholder="初次领证时间"></el-input>-->
                <el-row>
                    <el-date-picker style="width:185px"
                                    arrow-control
                                    format="yyyy 年 MM 月 dd 日"
                                    value-format="yyyy-MM-dd HH:mm"
                                    v-model="dataForm.firstIssueDate"
                                    placeholder="初次领证时间" v-bind:disabled="disabled">
                    </el-date-picker>
                </el-row>
            </el-form-item>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="驾驶证有效期限" prop="validPeriodStart">
                        <el-date-picker style="width:185px"
                                        arrow-control
                                        format="yyyy 年 MM 月 dd 日"
                                        value-format="yyyy-MM-dd HH:mm"
                                        v-model="dataForm.validPeriodStart"
                                        placeholder="起始时间">
                        </el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="结束时间" prop="summerEndTime">
                        <el-date-picker style="width:185px"
                                        arrow-control
                                        format="yyyy 年 MM 月 dd 日"
                                        value-format="yyyy-MM-dd HH:mm"
                                        v-model="dataForm.validPeriodEnd"
                                        placeholder="结束时间">
                        </el-date-picker>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-form-item label="驾驶证类型" prop="drivingLicenseClass">
                <el-input v-model="dataForm.drivingLicenseClass" placeholder="驾驶证类型"></el-input>
            </el-form-item>
            <el-form-item label="手机号" prop="mobileNumber">
                <el-input v-model="dataForm.mobileNumber" placeholder="手机号" v-bind:disabled="disabled"></el-input>
            </el-form-item>
            <el-form-item label="驾驶证照片" prop="driversLicensePhoto">
                <!--                <el-input v-model="dataForm.driversLicensePhoto" placeholder="驾驶证照片"></el-input>-->
                <el-upload ref="fileEleDLP"
                           name="filename"
                           action=""
                           :before-upload="beforeUp"
                           :on-exceed="exceed"
                           :http-request="uploadDLP"
                           :show-file-list="false"
                           accept=".jpg,.jpeg,.png,.gif"
                           :limit="1" :with-credentials="false">
                    <img v-if="dataForm.driversLicensePhoto" :src="dataForm.driversLicensePhoto"
                         style="height: 38px;width: 38px; margin-right: 20px;float:left" class="avatar">
                    <el-button size="small" type="primary">选择要上传的图片</el-button>
                </el-upload>
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
                <el-input v-model="dataForm.loginUserId" placeholder="系统登录用户id" v-bind:disabled="disabled"></el-input>
            </el-form-item>

        </el-form>

        <span slot="footer" class="dialog-footer">
            <el-button @click="visible = false">取消</el-button>
            <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
        </span>
    </el-dialog>
</template>
<script>
import {saveInfoDriver, editInfoDriver, getInfoDriver, uploadOfficerFile} from "@/api/parameter";
import {API_SERVER_URL} from "@/api";

export default {
    name: 'Dialog',
    data() {
        return {
            readonly: false,
            title: '',
            visible: false,
            value: false,
            disabled: false,
            sexOptions: [{
                "idx": '0',
                "vle": "男"
            }, {
                "idx": '1',
                "vle": "女"
            }],
            dataForm: {
                id: '',
                idNo: '',
                basicId: '',
                fullName: '',
                sex: '',
                age: '',
                takePhoto: '',
                residentialAddress: '',
                drivingLicenseNo: '',
                firstIssueDate: '',
                drivingLicenseClass: '',
                mobileNumber: '',
                driversLicensePhoto: '',
                validPeriodStart: '',
                validPeriodEnd: '',
                noCriminalRecordPhoto: '',
                loginUserId: '',
                createUserId: '',
                createDt: '',
                modifyUserId: '',
                modifyDt: '',
                isPrimary: ''
            },
            dataRule: {
                idNo: [
                    {required: true, message: '身份证号不能为空', trigger: 'blur'}
                ],
                fullName: [
                    {required: true, message: '姓名不能为空', trigger: 'blur'}
                ],
                sex: [
                    {required: true, message: '性别不能为空', trigger: 'blur'}
                ],
                age: [
                    {required: true, message: '年龄不能为空', trigger: 'blur'}
                ],
                takePhoto: [
                    {required: true, message: '身份验证照片不能为空', trigger: 'blur'}
                ],
                residentialAddress: [
                    {required: true, message: '居住地址不能为空', trigger: 'blur'}
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
            this.$refs.fileEleIDF.clearFiles();
        },
        uploadDLP: function (options) {
            console.log("------> " + options)
            this.uploadForm.append("type","dlp")
            uploadOfficerFile(this.uploadForm).then((res) => {
                debugger
                console.log("上传文件后的返回值==>" + JSON.stringify(res));
                this.dataForm.driversLicensePhoto = API_SERVER_URL + '/officer/file' + res.data.data.url;
                this.uploadForm.delete("filename")
                this.uploadForm.delete("type")
            })

            this.$refs.fileEleDLP.clearFiles();
        },
        uploadNoC: function (options) {
            this.uploadForm.append("type","noc")
            console.log("------> " + options)
            uploadOfficerFile(this.uploadForm).then((res) => {
                debugger
                console.log("上传文件后的返回值==>" + JSON.stringify(res));
                this.dataForm.noCriminalRecordPhoto = API_SERVER_URL + '/officer/file' + res.data.data.url;
                this.uploadForm.delete("filename")
                this.uploadForm.delete("type")
            })
            this.$refs.fileEleNOC.clearFiles();
        },
        //编辑或新增进入弹窗
        initData: function (id, basicId) {

            this.id = id;
            this.visible = true;
            this.value = false;
            this.readonly = false;
            this.dataForm.basicId = basicId;
            this.uploadForm = new FormData();
            this.$nextTick(() => {
                if (this.$refs['dataForms'] !== undefined) {
                    this.$refs['dataForms'].resetFields()
                }
            })
            if (id) {
                this.readonly = true;
                const loading = this.$loading({
                    lock: true,
                    text: '加载中.......',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });
                this.title = '修改';
                this.disabled = true;
                console.log("打开编辑弹窗,ID:" + id);
                getInfoDriver(id).then((res) => {
                    console.log("单条详细:" + JSON.stringify(res.data.data));
                    if (res.data && res.data.code === 0) {

                        this.dataForm.id = res.data.data.data.id;
                        this.dataForm.basicId = res.data.data.data.basicId;

                        this.dataForm.idNo = res.data.data.data.idNo;
                        this.dataForm.fullName = res.data.data.data.fullName;
                        this.dataForm.sex = res.data.data.data.sex;
                        this.dataForm.age = res.data.data.data.age;
                        this.dataForm.takePhoto = res.data.data.data.takePhoto;
                        this.dataForm.residentialAddress = res.data.data.data.residentialAddress;
                        this.dataForm.isPrimary = res.data.data.data.isPrimary;

                        this.dataForm.drivingLicenseNo = res.data.data.data.drivingLicenseNo;
                        this.dataForm.firstIssueDate = res.data.data.data.firstIssueDate;
                        this.dataForm.drivingLicenseClass = res.data.data.data.drivingLicenseClass;
                        this.dataForm.mobileNumber = res.data.data.data.mobileNumber;
                        this.dataForm.driversLicensePhoto = res.data.data.data.driversLicensePhoto;
                        this.dataForm.validPeriodStart = res.data.data.data.validPeriodStart;
                        this.dataForm.validPeriodEnd = res.data.data.data.validPeriodEnd;
                        this.dataForm.noCriminalRecordPhoto = res.data.data.data.noCriminalRecordPhoto;
                        this.dataForm.loginUserId = res.data.data.data.loginUserId;
                        this.dataForm.createUserId = res.data.data.data.createUserId;
                        this.dataForm.createUserName = res.data.data.data.createUserName;
                        this.dataForm.createDt = res.data.data.data.createDt;
                        this.dataForm.modifyUserId = res.data.data.data.modifyUserId;
                        this.dataForm.modifyDt = res.data.data.data.modifyDt;

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
                this.disabled = false;
                this.dataForm.busId = this.busId;
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
                        console.log("修改提交:" + JSON.stringify(this.dataForm));
                        editInfoDriver(this.dataForm).then((res) => {
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
                        console.log("新增提交:" + JSON.stringify(this.dataForm));

                        saveInfoDriver(this.dataForm).then((res) => {
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
            return "xxx";
        },
        beforeUp(file) {
            // this.dataForm.takePhoto = URL.createObjectURL(file);
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
