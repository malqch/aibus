<template>
    <div>
        <el-dialog :title="dialogTitle" :close-on-click-modal="false" :visible.sync="dialogVisible" style="color:#fff">
            <el-form :model="dataForm" :rules="dataFormRule" ref="dataForm" label-width="140px">
                <el-form-item label="事件标签" prop="eventTargetId">
                    <el-select v-model="dataForm.eventTargetId" placeholder="请选择事件标签" clearable>
                        <el-option :label="item.eventTargetName" :value="item.eventTargetId" :key="item.eventTargetId"
                                   v-for="(item, index) in conditionQuery.eventTargetNameList">
                        </el-option>
                    </el-select>
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
import {getInfoEventExtendDetail,saveInfoEventExtend,editInfoEventExtend, getSubInfoEventTargetList} from "@/api/event";
export default {
    name: 'DialogEventExtend',
    data() {
        return {
            dialogTitle: '',
            isAdd: true,
            dialogVisible: false,
            nextStepDisabled: false,
            conditionQuery: {
                eventTargetNameList: []
            },
            dataForm: {
                eventExtendId:'',
                collectEventId: '',
                eventTargetId: '',
                isEnabled: 1
            },
            loading: null,
            dataFormRule: {
                eventTargetId:  [
                    {required: true, message: '请选择事件标签', trigger: 'change'},
                ]
            }
        }
    },
    methods: {

        initData: function (collectEventId,eventExtendId) {
            this.dialogVisible = true;
            this.$nextTick(() => {
                if (this.$refs['dataForm'] !== undefined) {
                    this.$refs['dataForm'].resetFields()
                    this.conditionQuery.eventTargetNameList = []
                }
            })
            for (let key in this.dataForm) {
                this.dataForm[key] = ''
            }
            this.dataForm.collectEventId = collectEventId

            //修改
            if (eventExtendId) {
                this.dialogTitle = '修改'
                this.isAdd = false

                //回显表单
                getInfoEventExtendDetail(eventExtendId).then((res) => {
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

            getSubInfoEventTargetList('').then((res) => {
                if (res.data && res.data.code === 0) {
                    this.conditionQuery.eventTargetNameList = res.data.data.list
                }
            })
        },
        resetForm() {
            this.$refs['dataForm'].resetFields();
            this.nextStepDisabled = false;
            this.dialogVisible = false
            this.conditionQuery = {
                eventTargetNameList: []
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
                        saveInfoEventExtend(this.dataForm).then((res) => {
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
                        editInfoEventExtend(this.dataForm).then((res) => {
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
