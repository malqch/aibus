<template>
    <div>
    <el-dialog :title="dialogTitle" :close-on-click-modal="false" :visible.sync="dialogVisible" style="color:#fff">
        <el-form :model="dataForm" :rules="dataFormRule" ref="dataForm" label-width="140px">
            <el-form-item label="事件采集名称" prop="collectEvent">
                <el-input v-model="dataForm.collectEvent" placeholder="事件采集名称"></el-input>
            </el-form-item>
            <el-form-item label="事件采集编码" prop="collectCode">
                <el-input v-model="dataForm.collectCode" placeholder="事件采集编码"></el-input>
            </el-form-item>
            <el-form-item label="事件级别" prop="eventLevelId">
                <el-select v-model="dataForm.eventLevelId" placeholder="请选择事件级别" clearable>
                    <el-option :label="item.eventLevelName" :value="item.eventLevelId" :key="item.eventLevelId"
                               v-for="(item, index) in conditionQuery.eventLevelNameList"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="事件类型" prop="eventTypeId">
                <el-select v-model="dataForm.eventTypeId" placeholder="请选择事件类型" clearable>
                    <el-option :label="item.eventTypeName" :value="item.eventTypeId" :key="item.eventTypeId"
                               v-for="(item, index) in conditionQuery.eventTypeNameList">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="事件标签" prop="eventTargetId">
                <el-select v-model="dataForm.eventTargetId" placeholder="请选择事件标签" clearable>
                    <el-option :label="item.eventTargetName" :value="item.eventTargetId" :key="item.eventTargetId"
                               v-for="(item, index) in conditionQuery.eventTargetNameList">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="设备类型" prop="deviceTypeId">
                <el-select v-model="dataForm.deviceTypeId" placeholder="请选择设备类型" clearable>
                    <el-option :label="item.deviceTypeName" :value="item.deviceTypeId" :key="item.deviceTypeId"
                               v-for="(item, index) in conditionQuery.deviceTypeNameList">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="事件大类" prop="eventType">
                <el-input v-model.number="dataForm.eventType" placeholder="事件大类"></el-input>
            </el-form-item>
            <el-form-item label="事件小类" prop="eventDetail">
                <el-input v-model.number="dataForm.eventDetail" placeholder="事件小类"></el-input>
            </el-form-item>
            <el-form-item label="事件描述" prop="descriptionContent">
                <el-input type="textarea" v-model="dataForm.descriptionContent" placeholder="事件描述" maxlength="200"></el-input>
            </el-form-item>
            <el-form-item label="是否启用">
                <el-switch v-model="dataForm.isEnabled" :active-value="1" :inactive-value="0" active-color="#13ce66"
                           inactive-color="#ff4949"></el-switch>
            </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button @click="resetForm">取消</el-button>
            <el-button type="primary" :disabled="nextStepDisabled" @click="dataFormSubmit">确定</el-button>
        </span>
    </el-dialog>

    </div>
</template>
<script>
import {saveInfoCollectEvent,editInfoCollectEvent,getInfoCollectEventDetail,
    getInfoEventLevelList,getInfoEventTypeList,getInfoEventTargetList} from "@/api/event";
import {getDeviceTypeS} from '@/api/selectionApi';
    export default {
        name: 'Dialog',
        data() {
            return {
                dialogTitle: '',
                isAdd: true,
                dialogVisible: false,
                nextStepDisabled: false,
                conditionQuery: {
                    eventLevelNameList: [],
                    eventTypeNameList: [],
                    eventTargetNameList: [],
                    deviceTypeNameList: []
                },
                dataForm: {
                    collectEvent: '',
                    collectCode: '',
                    eventLevelId: '',
                    eventTypeId: '',
                    eventTargetId: '',
                    deviceTypeId: '',
                    eventType: '',
                    eventDetail: '',
                    eventDescriptionId:'',
                    descriptionContent:'',
                    isEnabled: 1
                },
                loading: null,
                dataFormRule: {
                    collectEvent:  [
                        {required: true, message: '请输入采集事件名称', trigger: 'blur'},
                        {max: 20, message: '长度不能超过20个字符', trigger: 'blur'}
                    ],
                    collectCode: [
                        {max: 20, message: '长度不能超过20个字符', trigger: 'blur'}
                    ],
                    eventLevelId: [
                        {required: true, message: '请选择事件级别', trigger: 'change'}
                    ],
                    eventTypeId:  [
                        {required: true, message: '请选择事件类型', trigger: 'change'}
                    ],
                    eventTargetId:  [
                        {required: true, message: '请选择事件标签', trigger: 'change'}
                    ],
                    deviceTypeId:  [
                        {required: true, message: '请选择设备类型', trigger: 'change'}
                    ],
                    eventType:  [
                        {required: true, message: '请输入事件大类', trigger: 'blur'},
                        { type: 'number', message: '事件大类必须为数字值'}
                    ],
                    eventDetail: [
                        {required: true, message: '请输入事件小类', trigger: 'blur'},
                        { type: 'number', message: '事件小类必须为数字值'}
                    ],
                    descriptionContent: [
                        {max: 200, message: '长度不能超过200个字符', trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {

            initData: function (id) {
                this.dialogVisible = true;
                this.$nextTick(() => {
                    if (this.$refs['dataForm'] !== undefined) {
                        this.$refs['dataForm'].resetFields()
                    }
                })

                //修改
                if (id) {
                    this.dialogTitle = '修改'
                    this.isAdd = false

                    //回显表单
                    getInfoCollectEventDetail(id).then((res) => {
                        if (res.data && res.data.code == 0) {
                            this.dataForm = res.data.data.data
                        }
                    });
                }
                //新增
                else {
                    this.dialogTitle = '新增'
                    this.isAdd = true
                    this.dataForm.isEnabled = 1;
                }

                getInfoEventLevelList().then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.conditionQuery.eventLevelNameList = res.data.data.list
                    }
                })
                getInfoEventTypeList().then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.conditionQuery.eventTypeNameList = res.data.data.list
                    }
                })
                getInfoEventTargetList('master').then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.conditionQuery.eventTargetNameList = res.data.data.list
                    }
                })

                getDeviceTypeS().then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.conditionQuery.deviceTypeNameList = res.data.data.selection
                    }
                })
            },
            resetForm() {
                this.$refs['dataForm'].resetFields();
                this.nextStepDisabled = false;
                this.dialogVisible = false
                this.conditionQuery = {
                    eventLevelNameList: [],
                    eventTypeNameList: [],
                    eventTargetNameList: [],
                    deviceTypeNameList: []
                }
            },
            dataFormSubmit() {
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        this.loading = this.$loading({
                            lock: true,
                            text: '加载中...',
                            spinner: 'el-icon-loading',
                            background: 'rgba(0, 0, 0, 0.7)'
                        });

                        if (this.isAdd) {
                            saveInfoCollectEvent(this.dataForm).then((res) => {
                                this.loading.close();
                                if (res.data && res.data.code === 0) {
                                    this.$message.success({
                                        dangerouslyUseHTMLString: true,
                                        message: '操作成功',
                                        type: 'success',
                                        onClose: () => {
                                            this.dialogVisible = false
                                            this.$emit('refreshDataList')
                                        }
                                    })
                                } else {
                                    this.$message({
                                        dangerouslyUseHTMLString: true,
                                        message: res.data.message,
                                        type: 'error',
                                        offset: 300
                                    })
                                }
                                this.nextStepDisabled = false;
                                this.resetForm()
                            })
                        } else {
                            editInfoCollectEvent(this.dataForm).then((res) => {
                                this.loading.close();
                                if (res.data && res.data.code === 0) {
                                    this.$message.success({
                                        dangerouslyUseHTMLString: true,
                                        message: '操作成功',
                                        type: 'success',
                                        onClose: () => {
                                            this.dialogVisible = false
                                            this.$emit('refreshDataList')
                                        }
                                    })
                                } else {
                                    this.$message({
                                        dangerouslyUseHTMLString: true,
                                        message: res.data.message,
                                        type: 'error',
                                        offset: 300
                                    })
                                }
                                this.nextStepDisabled = false;
                                this.resetForm()
                            })
                        }
                    }
                })
            },
        }
    }
</script>
<style scoped>
    .el-rate {
        padding-top: 10px;
    }
</style>
