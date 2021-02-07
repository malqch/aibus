<template>
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" style="color:#fff">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForms" label-width="120px">

            <el-form-item label="公交车型" size="mini" >
                <el-select
                        style="margin-bottom: 4px;"
                        v-model="dataForm.busTypeId"
                        filterable
                        default-first-option
                        placeholder="请选择公交车型">
                    <el-option
                            v-for="item in busTypeIdOptions"
                            :key="item.busTypeId"
                            :label="item.busTypeName"
                            :value="item.busTypeId">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="订单数量" prop="orderDetailNum">
                <el-input v-model="dataForm.orderDetailNum" placeholder="订单数量" maxlength="30"></el-input>
            </el-form-item>
            <el-form-item label="说明" prop="orderDetailDesc">
                <el-input type="textarea" v-model="dataForm.orderDetailDesc" placeholder="说明" maxlength="30"></el-input>
            </el-form-item>

        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button @click="visible = false">取消</el-button>
            <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
        </span>
    </el-dialog>
</template>
<script>
    import {saveOrderBusCompanyDetail, editOrderBusCompanyDetail, getOrderBusCompanyDetailSingle} from "@/api/parameter";
    import {getBusTypeS} from "@/api/selectionApi";

    export default {
        name: 'Dialog',
        data() {
            return {
                title: '',
                visible: false,
                value: false,
                busTypeIdOptions: [],
                dataForm: {
                    orderDetailId: '',
                    orderId: '',
                    busTypeId: '',
                    orderDetailNum: '',
                    orderDetailDesc: '',
                    isDeleted: '',
                    createdDate: '',
                    modifiedDate: '',

                    isEnabled: 0
                },
                dataRule: {
                    paramName: [
                        {required: true, message: '请输入参数名称', trigger: 'blur'},
                        {max: 20, message: '长度不能超过30个字符', trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            //编辑或新增进入弹窗
            initData: function (id,orderId) {
                this.visible = true;
                this.value = false;
                this.dataForm.busTypeId = '';
                this.$nextTick(() => {
                    if (this.$refs['dataForms'] !== undefined) {
                        this.$refs['dataForms'].resetFields()
                    }
                })
                this.dataForm.orderId = orderId;
                if (id) {
                    const loading = this.$loading({
                        lock: true,
                        text: '加载中.......',
                        spinner: 'el-icon-loading',
                        background: 'rgba(0, 0, 0, 0.7)'
                    });
                    this.title = '修改';
                    console.log("打开编辑弹窗,ID:"+id+"orderId:"+orderId);
                    getOrderBusCompanyDetailSingle(id).then((res) => {
                        console.log("单条详细:"+JSON.stringify(res.data.data.data));
                        if (res.data && res.data.code === 0) {

                            this.dataForm.orderDetailId = res.data.data.data.orderDetailId;
                            this.dataForm.busTypeId = res.data.data.data.busTypeId;
                            this.dataForm.orderDetailNum = res.data.data.data.orderDetailNum;
                            this.dataForm.orderDetailDesc = res.data.data.data.orderDetailDesc;
                            this.dataForm.isDeleted = res.data.data.data.isDeleted;
                            this.dataForm.createdDate = res.data.data.data.createdDate;
                            this.dataForm.modifiedDate = res.data.data.data.modifiedDate;

                            if (res.data.data.data.isEnabled == 1) {
                                this.value = true
                            } else {
                                this.value = false
                            }
                        }
                    });
                    loading.close();
                } else {
                    console.log("打开新增弹窗,ID:"+id+"orderId:"+orderId);
                    this.title = '新增'
                    this.dataForm.isEnabled = 0;
                    this.value = true;
                }

                getBusTypeS().then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.busTypeIdOptions = res.data.data.list
                        return;
                    }
                });

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
                            editOrderBusCompanyDetail(this.dataForm).then((res) => {
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
                            saveOrderBusCompanyDetail(this.dataForm).then((res) => {
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
