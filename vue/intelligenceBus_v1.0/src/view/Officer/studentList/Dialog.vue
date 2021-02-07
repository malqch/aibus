<template>
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" style="color:#fff">
        <el-form :model="dataForm" :rules="dataRule"
                 ref="dataForms" @keyup.enter.native="dataFormSubmit()"
                 label-width="120px" >
            <el-form-item label="学生姓名" prop="studentName">
                <el-input v-model="dataForm.studentName" placeholder="学生姓名" disabled></el-input>
            </el-form-item>

            <el-form-item label="性别" prop="studentSex" size="mini" disabled>
                <el-select style="margin-bottom: 4px;" v-model="dataForm.studentSex" filterable disabled default-first-option
                           placeholder="选择">
                    <el-option v-for="item in sexOptions" :key="item.idx" :label="item.vle"
                               :value="item.idx"></el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="年龄" prop="studentAge" >
                <el-input v-model="dataForm.studentAge" placeholder="年龄" disabled></el-input>
            </el-form-item>

            <el-form-item label="校车路线" prop="upCompanyLineName">
                <el-input v-model="dataForm.upCompanyLineName" placeholder="校车路线" disabled></el-input>
            </el-form-item>

            <el-form-item label="座位编号" prop="seatNo">
                <el-input v-model="dataForm.seatNo" placeholder="座位编号"></el-input>
            </el-form-item>
            <el-form-item label="上车站点" prop="upStationId">
                <template>
                    <el-select v-model="dataForm.upStationId"  placeholder="请选择">
                        <el-option
                            v-for="item in busStationIdOptions"
                            :key="item.busStationId"
                            :label="item.busStationName"
                            :value="item.busStationId">
                        </el-option>
                    </el-select>
                </template>
            </el-form-item>

            <el-form-item label="下车站点" prop="offStationId">
                <template>
                    <el-select v-model="dataForm.offStationId"  placeholder="请选择">
                        <el-option
                            v-for="item in busStationIdOptions"
                            :key="item.busStationId"
                            :label="item.busStationName"
                            :value="item.busStationId">
                        </el-option>
                    </el-select>
                </template>
            </el-form-item>

            <el-form-item label="与学生关系" prop="relationStudent">
                <el-input v-model="dataForm.relationStudent" placeholder="与学生关系" disabled></el-input>
            </el-form-item>

            <el-form-item label="监护人手机" prop="mobileNumber">
                <el-input v-model="dataForm.mobileNumber" placeholder="监护人手机" disabled></el-input>
            </el-form-item>

        </el-form>
<!--        <el-form :model="dataForm" :rules="dataRule"-->
<!--                 ref="dataForms" @keyup.enter.native="dataFormSubmit()"-->
<!--                 label-width="120px" v-else>-->
<!--            <el-form-item label="识别照片" prop="className">-->
<!--                <img src="" alt="">-->
<!--            </el-form-item>-->

<!--        </el-form>-->
        <span slot="footer" class="dialog-footer">
            <el-button @click="visible = false">取消</el-button>
            <el-button type="primary" @click="dataFormSubmit()" v-if="dialogType === 0">确定</el-button>
        </span>
    </el-dialog>
</template>
<script>
import {
    saveInfoDriver,
    getStudentDetail,
    getPlanBusServiceDetail,
    getLineStation,
    updateStudentInfo, saveStudentInfo
} from "@/api/parameter";
import {getAllBusStationS} from "@/api/selectionApi";
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
            sexOptions: [{
                "idx": '0',
                "vle": "男"
            }, {
                "idx": '1',
                "vle": "女"
            }],
            busStationIdOptions: [{
                busStationId: "1352788483441164290",
                busStationName: "白鹭岛西大门超市",
                companyLineId: "1352790748369518594",
                stationOrder: 1
            }],
            busNameList: [],
            dataForm: {
                "id": '',
                "basicId": '',
                "studentName": '',
                "studentTakePhoto": '',
                "studentSex": '',
                "studentAge": '',
                "upCompanyLineId": '',
                "upCompanyLineName": '',
                "offCompanyLineId": '',
                "offCompanyLineName": '',
                "seatNo": '',
                "upStationId": '',
                "upBusStationName": '',
                "offStationId": '',
                "offBusStationName": '',
                "guardianName": '',
                "relationStudent": '',
                "mobileNumber": '',
                "guardianId": '',
                "classesId": '',
                "schoolId": '',
                "createDt": '',
                "createUserId": '',
                "modifyUserId": '',
                "modifyDt": ''
            },
            dataRule: {
                className: [
                    {required: true, message: '班级名称不能为空', trigger: 'blur'}
                ],
                fullName: [
                    {required: true, message: '姓名不能为空', trigger: 'blur'}
                ],
                mobileNumber: [
                    {required: true, message: '联系方式不能为空', trigger: 'blur'}
                ]
            }
        }
    },
    methods: {
        //编辑或新增进入弹窗
        initData: function (id, type,lineId) {
            // debugger

            this.dialogType = id ? 0 : 1;
            console.log(this.dialogType)
            this.id = id;
            this.visible = true;
            this.value = false;
            this.readonly = false;
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
                this.title =  '修改学生信息';

                if (type === 0) {
                    this.dialogType = 0;
                }
                console.log("打开编辑弹窗ID:" + id);
                console.log("---------------输出上行线路id:---" + lineId);
                getLineStation(lineId).then((res) => {
                    this.busStationIdOptions = res.data.data.data;
                })
                getStudentDetail(id).then((res) => {
                    // console.log("单条详细:" + JSON.stringify(res.data.data));
                    if (res.data && res.data.code === 0) {
                        // debugger
                        this.dataForm.id = res.data.data.data.id,//"1351910567522074625",
                            this.dataForm.basicId = res.data.data.data.basicId,//"1351903655967588353",
                            this.dataForm.studentName = res.data.data.data.studentName,//"木一行",
                            this.dataForm.studentTakePhoto = res.data.data.data.studentTakePhoto,//"/take_photo/20210121101438/二二班/图片/木一行070935.jpg",
                            this.dataForm.studentSex = res.data.data.data.studentSex,//"0",
                            this.dataForm.studentAge = res.data.data.data.studentAge,//10,
                            this.dataForm.upCompanyLineId = res.data.data.data.upCompanyLineId,//"1351831594381344769",
                            this.dataForm.upCompanyLineName = res.data.data.data.upCompanyLineName,//"1号线路",
                            this.dataForm.offCompanyLineId = res.data.data.data.offCompanyLineId,//"1351884063866093570",
                            this.dataForm.offCompanyLineName = res.data.data.data.offCompanyLineName,//"1号线路",
                            this.dataForm.seatNo = res.data.data.data.seatNo,//"1",
                            this.dataForm.upStationId = res.data.data.data.upStationId,//"1351759402419355649",
                            this.dataForm.upBusStationName = res.data.data.data.upBusStationName,//"富力新城配套学校",
                            this.dataForm.offStationId = res.data.data.data.offStationId,//"1351759101893279746",
                            this.dataForm.offBusStationName = res.data.data.data.offBusStationName,//"珠光御景",
                            this.dataForm.guardianName = res.data.data.data.guardianName,//"左转",
                            this.dataForm.relationStudent = res.data.data.data.relationStudent,//"亲属",
                            this.dataForm.mobileNumber = res.data.data.data.mobileNumber,//"13533514366",
                            this.dataForm.guardianId = res.data.data.data.guardianId,//"1351910469790597121",
                            this.dataForm.classesId = res.data.data.data.classesId,//"1351418893544980481",
                            this.dataForm.schoolId = res.data.data.data.schoolId,//"1234565432123463212",
                            this.dataForm.createDt = res.data.data.data.createDt,//"2021-01-21 00:00:00",
                            this.dataForm.createUserId = res.data.data.data.createUserId,//"1351438597974130689",
                            this.dataForm.modifyUserId = res.data.data.data.modifyUserId,//null,
                            this.dataForm.modifyDt = res.data.data.data.modifyDt

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
            console.log("提交表单111111111111")
            this.$refs['dataForms'].validate((valid) => {
                if (valid) {
                    const loading = this.$loading({
                        lock: true,
                        text: '加载中.......',
                        spinner: 'el-icon-loading',
                        background: 'rgba(0, 0, 0, 0.7)'
                    });
                    if (this.title == '修改学生信息') {
                        console.log("修改提交:" + JSON.stringify(this.dataForm));
                        updateStudentInfo(this.dataForm).then((res) => {
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
                        saveStudentInfo(this.dataForm).then((res) => {
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
        formatSex: function (row, column) {
            return row.studentSex == '0' ? "男" : row.studentSex == '1' ? "女" : "NoN";
        },
        formatTime: function (row, column, cellValue, index) {
            if (cellValue) {
                console.log(cellValue)
                return cellValue.substr(0, 4) + '年' + cellValue.substr(5, 2) + '月' + cellValue.substr(8, 2) + '日';
            }
            return cellValue
        },
        formatTime1: function (row, column, cellValue, index) {
            return cellValue.substr(5, 2) + '月' + cellValue.substr(8, 2) + '日';
        },
        formatTime2: function (row, column, cellValue, index) {
            return cellValue.substr(11, 2) + '点' + cellValue.substr(14, 2) + '分';
        },
        beforeUp(file) {
            this.dataForm.takePhoto = URL.createObjectURL(file);
            this.uploadForm.append('takePhotoFile', file)
            this.nextStepDisabled = true;
            return false;
        },

        beforeUpNoC(file) {
            this.dataForm.noCriminalRecordPhoto = URL.createObjectURL(file);
            this.uploadForm.append('noCriminalRecordPhoto', file)
            this.nextStepDisabled = true;
            return false;
        },
        beforeUpDLP(file) {
            this.dataForm.driversLicensePhoto = URL.createObjectURL(file);
            this.uploadForm.append('driversLicensePhoto', file)
            this.nextStepDisabled = true;
            return false;
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
