<template>
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" style="color:#fff">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForms" @keyup.enter.native="dataFormSubmit()"
                 label-width="120px" v-if="dialogType === 0">
            <el-form-item label="班级名称" prop="className">
                <el-input v-model="dataForm.className" placeholder="班级名称"></el-input>
            </el-form-item>
            <el-form-item label="班主任" prop="teacherName">
                <el-input v-model="dataForm.teacherName" placeholder="班主任"></el-input>
            </el-form-item>
            <el-form-item label="联系方式（手机）" prop="teacherMobileNumber">
                <el-input v-model="dataForm.teacherMobileNumber" placeholder="联系方式（手机）"></el-input>
            </el-form-item>
        </el-form>
        <el-form label-width="120px" v-else>
            <el-form-item label="导入学生名单" prop="className">
                <el-button>下载模板</el-button>
                <el-upload ref="uploadExcel"
                           name="filename"
                           action=""
                           :before-upload="beforeUp"
                           :on-exceed="exceed"
                           :http-request="uploadXLS"
                           accept=".xls,.xlsx"
                           :limit="1" :with-credentials="false">
                    <!--                    <img v-if="dataForm.takePhoto" :src="dataForm.takePhoto"-->
                    <!--                         style="height: 38px;width: 38px; margin-right: 20px;float:left" class="avatar">-->
                    <el-button size="small" type="primary">导入表格</el-button>
                </el-upload>
            </el-form-item>


            <el-form-item label="导入识别照片" prop="teacherName">
                <el-button>下载模板</el-button>
                <el-upload ref="uploadZIP"
                           name="filename"
                           action=""
                           :before-upload="beforeUp"
                           :on-exceed="exceed"
                           :http-request="uploadZip"
                           accept=".zip,.rar"
                           :limit="1" :with-credentials="false">
                    <!--                    <img v-if="dataForm.takePhoto" :src="dataForm.takePhoto"-->
                    <!--                         style="height: 38px;width: 38px; margin-right: 20px;float:left" class="avatar">-->
                    <el-button size="small" type="primary">导入文件包</el-button>
                </el-upload>
            </el-form-item>

        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button @click="visible = false">取消</el-button>
            <el-button type="primary" @click="dataFormSubmit()" v-if="dialogType === 0">确定</el-button>
        </span>
    </el-dialog>
</template>
<script>
import {getClassesDetail, saveClassesDetail, updateClassesDetail, uploadStudentInfo} from "@/api/parameter";
// import {getDeviceTypeS,getBusS} from "@/api/selectionApi";

export default {
    name: 'Dialog',
    data() {
        return {
            dialogType: 0,
            readonly: false,
            title: '',
            visible: false,
            value: false,
            disabled: false,
            dataForm: {
                id: '',
                className: '',
                teacherName: '',
                teacherMobileNumber: '',
            },
            dataRule: {
                className: [
                    {required: true, message: '班级名称不能为空', trigger: 'blur'}
                ],
                teacherName: [
                    {required: true, message: '姓名不能为空', trigger: 'blur'}
                ],
                teacherMobileNumber: [
                    {required: true, message: '联系方式不能为空', trigger: 'blur'}
                ]
            }
        }
    },
    methods: {
        uploadXLS: function (options) {
            console.log("------> " + options)
            this.uploadForm.append("type", "excel")
            console.log("======>id  " + this.dataForm.id)
            this.uploadForm.append("classId", this.dataForm.id)
            uploadStudentInfo(this.uploadForm).then((res) => {
                console.log("上传文件后的返回值==>" + JSON.stringify(res));
                // this.dataForm.takePhoto = API_SERVER_URL + '/officer/file' + res.data.data.url;
                this.$alert('上传成功数量:' + res.data.data.success + '<hr>' + '失败数量:' + res.data.data.fail, '上传结果:', {
                    confirmButtonText: '确定',
                    callback: action => {
                        this.$message({
                            type: 'info',
                            message: `action: ${action}`
                        });
                    }
                });
                this.uploadForm.delete("attach")
                this.uploadForm.delete("type")
            })
            this.$refs.uploadExcel.clearFiles();
        },

        uploadZip: function (options) {
            console.log("------> " + options)
            this.uploadForm.append("type", "zip")
            this.uploadForm.append("classId", this.dataForm.id)
            uploadStudentInfo(this.uploadForm).then((res) => {
                console.log("上传文件后的返回值==>" + JSON.stringify(res));
                // this.dataForm.takePhoto = API_SERVER_URL + '/officer/file' + res.data.data.url;
                this.$alert('上传成功数量:' + res.data.data.success + '<hr>' + '失败数量:' + res.data.data.fail, '上传结果:', {
                    confirmButtonText: '确定',
                    callback: action => {
                        this.$message({
                            type: 'info',
                            message: `action: ${action}`
                        });
                    }
                });
                this.uploadForm.delete("attach")
                this.uploadForm.delete("type")
            })
            this.$refs.uploadZIP.clearFiles();
        },



        //编辑或新增进入弹窗
        initData: function (id, type) {
            this.dialogType = id ? 1 : 0;
            console.log(this.dialogType, type)
            this.id = id;
            this.visible = true;
            this.value = false;
            this.readonly = false;
            // this.dataForm.basicId = basicId;
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
                this.title = type === 0 ? '修改' : '导入';
                if (type === 0) {
                    this.dialogType = 0;
                }
                console.log("打开编辑弹窗,ID:" + id);
                getClassesDetail(id).then((res) => {
                    console.log("单条详细:" + JSON.stringify(res.data.data));
                    if (res.data && res.data.code === 0) {
                        debugger
                        this.dataForm.id = res.data.data.data.id
                        this.dataForm.className = res.data.data.data.className
                        this.dataForm.teacherName = res.data.data.data.teacherName
                        this.dataForm.teacherMobileNumber = res.data.data.data.teacherMobileNumber

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
                this.id = ''
                this.dataForm.isEnabled = 0;
                // this.dataForm.busId = this.busId;
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
                debugger
                if (valid) {
                    const loading = this.$loading({
                        lock: true,
                        text: '加载中.......',
                        spinner: 'el-icon-loading',
                        background: 'rgba(0, 0, 0, 0.7)'
                    });
                    if (this.title == '修改') {
                        console.log("修改提交:" + JSON.stringify(this.dataForm));
                        debugger
                        updateClassesDetail(this.dataForm).then((res) => {
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
                        debugger
                        console.log("新增提交:" + JSON.stringify(this.dataForm));
                        debugger
                        saveClassesDetail(this.dataForm).then((res) => {
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
            this.dataForm.takePhoto = URL.createObjectURL(file);
            this.uploadForm.append('attach', file)
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
