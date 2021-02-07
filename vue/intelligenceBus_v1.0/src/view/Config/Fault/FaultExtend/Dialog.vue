<template>
    <div>
        <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" style="color:#fff">
            <el-form :model="dataForm" :rules="dataRule" ref="dataForms" @keyup.enter.native="dataFormSubmit()"
                     label-width="120px">
                <el-form-item label="采集内容" prop="collectFault">
                    <el-input v-model="dataForm.collectFault" placeholder="采集内容" maxlength="20"></el-input>
                </el-form-item>
                <el-form-item label="采集编码" prop="collectCode">
                    <el-input v-model="dataForm.collectCode" placeholder="采集编码" maxlength="20"></el-input>
                </el-form-item>
                <el-form-item label="故障类型" prop="faultTypeId">
                    <el-select v-model="dataForm.faultTypeId" placeholder="请选择故障类型">
                        <el-option :label="item.faultTypeName" :value="item.faultTypeId" :key="item.faultTypeId"
                                   v-for="(item, index) in conditionQuery.faultTypeList">{{item.faultTypeName}}
                        </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="故障标签" prop="faultTargetId">
                    <el-select v-model="dataForm.faultTargetId" placeholder="请选择故障标签">
                        <el-option :label="item.faultTargetName" :value="item.faultTargetId" :key="item.faultTargetId"
                                   v-for="(item, index) in conditionQuery.faultTargetList">{{item.faultTargetName}}
                        </el-option>
                    </el-select>
                </el-form-item>

                <!--                <el-form-item label="故障方案" prop="suggestionContent">-->
                <!--                    <el-input v-model="dataForm.suggestionContent" placeholder="故障方案" maxlength="200"></el-input>-->
                <!--                </el-form-item>-->

                <el-form-item label="故障方案" prop="suggestionContent">
                    <el-input type="textarea" v-model="dataForm.suggestionContent" placeholder="故障方案" maxlength="200"></el-input>
                </el-form-item>

                <el-form-item label="故障级别" prop="faultLevelId">
                    <el-select v-model="dataForm.faultLevelId" placeholder="请选择故障标签">
                        <el-option :label="item.faultLevelName" :value="item.faultLevelId" :key="item.faultLevelId"
                                   v-for="(item, index) in conditionQuery.faultLevelList">{{item.faultLevelName}}
                        </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="故障ID" prop="faultType">
                    <el-input v-model="dataForm.faultType" placeholder="故障ID" maxlength="64"></el-input>
                </el-form-item>
                <el-form-item label="故障索引码" prop="faultDetail">
                    <el-input v-model="dataForm.faultDetail" placeholder="故障索引码" maxlength="64"></el-input>
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
    </div>
</template>
<script>
    import {editInfoFaultCollect,getInfoFaultCollectDetail, getInfoFaultTypeListAll, getInfoFaultLevelListAll,getInfoFaultTargetListAll} from "@/api/fault";
    import DialogExtend from "./DialogExtend";
    export default {
        name: 'Dialog',
        data() {
            return {
                conditionQuery: {
                    faultTypeList: [],
                    faultTargetList: [],
                    faultLevelList: []
                },
                title: '',
                value: false,
                visible: false,
                dataForm: {
                    collectFaultId: '',
                    collectFault: '',
                    collectCode: '',
                    faultTypeId: '',
                    faultTargetId: '',
                    faultSuggestionId: '',
                    suggestionContent: '',
                    faultLevelId: '',
                    faultType: '',
                    faultDetail: '',
                    isEnabled: 1
                },
                dataRule: {
                    collectFault: [
                        {required: true, message: '请输入故障采集内容', trigger: 'blur'},
                        {max: 20, message: '长度不能超过20个字符', trigger: 'blur'}
                    ],
                    collectCode: [
                        {max: 20, message: '长度不能超过20个字符', trigger: 'blur'}
                    ],
                    faultTypeId: [
                        {required: true, message: '请选择故障类型', trigger: 'change'}
                    ],
                    faultTargetId: [
                        {required: true, message: '请选择故障标签', trigger: 'change'}
                    ],
                    suggestionContent: [
                        {max: 200, message: '长度不能超过200个字符', trigger: 'blur'}
                    ],
                    faultLevelId: [
                        {required: true, message: '请选择故障级别', trigger: 'change'}
                    ],
                    faultType: [
                        {required: true, message: '请输入故障ID', trigger: 'blur'},
                        {max: 64, message: '长度不能超过64个字符', trigger: 'blur'}
                    ],
                    faultDetail: [
                        {required: true, message: '请输入故障索引码', trigger: 'blur'},
                        {max: 64, message: '长度不能超过64个字符', trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            initData: function (id) {
                this.visible = true
                this.value = false;
                this.$nextTick(() => {
                    if (this.$refs['dataForms'] !== undefined) {
                        this.$refs['dataForms'].resetFields()
                    }
                })
                getInfoFaultTypeListAll().then((res) => {
                    if (res.data && res.data.code == 0) {
                        this.conditionQuery.faultTypeList = res.data.data.list
                    }
                })
                getInfoFaultTargetListAll().then((res) => {
                    if (res.data && res.data.code == 0) {
                        this.conditionQuery.faultTargetList = res.data.data.list
                    }
                })
                getInfoFaultLevelListAll().then((res) => {
                    if (res.data && res.data.code == 0) {
                        this.conditionQuery.faultLevelList = res.data.data.list
                    }
                })

                getInfoFaultCollectDetail(id).then((res) => {
                    if (res.data && res.data.code == 0) {
                        this.dataForm.collectFaultId = res.data.data.data.collectFaultId;
                        this.dataForm.collectFault = res.data.data.data.collectFault;
                        this.dataForm.collectCode = res.data.data.data.collectCode
                        this.dataForm.faultTypeId = res.data.data.data.faultTypeId;
                        this.dataForm.faultTargetId = res.data.data.data.faultTargetId;
                        this.dataForm.faultSuggestionId = res.data.data.data.faultSuggestionId;
                        this.dataForm.suggestionContent = res.data.data.data.suggestionContent;
                        this.dataForm.faultLevelId = res.data.data.data.faultLevelId;
                        this.dataForm.faultType = res.data.data.data.faultType;
                        this.dataForm.faultDetail = res.data.data.data.faultDetail;
                        this.dataForm.isEnabled = res.data.data.data.isEnabled;
                        if (this.dataForm.isEnabled == 1) {
                            this.value = true;
                        } else {
                            this.value = false;
                        }
                    }
                })

                this.title = '修改';
                this.value = true;
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
                            editInfoFaultCollect(this.dataForm).then((res) => {
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
