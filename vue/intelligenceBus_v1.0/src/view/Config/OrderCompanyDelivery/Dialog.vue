<template>
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" style="color:#fff">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForms" label-width="120px">

            <el-form-item label="公交车型" size="mini" prop="busTypeId">
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
            <el-form-item label="交付数量" prop="orderDeliveryNum">
                <el-input v-model.number="dataForm.orderDeliveryNum" placeholder="交付数量" maxlength="30"></el-input>
            </el-form-item>
            <el-form-item label="交付日期" prop="orderDeliveryDate">
                <el-date-picker
                      v-model="dataForm.orderDeliveryDate"
                      type="date"
                      placeholder="选择交付日期"
                      value-format="yyyy-MM-dd">
                </el-date-picker>
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
    import {saveOrderCompanyDelivery, editOrderCompanyDelivery, getOrderCompanyDeliveryDetail} from "@/api/parameter";
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
                    companyDeliveryId: '',
                    orderId: '',
                    busTypeId: '',
                    orderDeliveryNum: '',
                    orderDeliveryDate: '',
                    orderDetailDesc: '',
                    isDeleted: '',
                    createdDate: '',
                    modifiedDate: '',

                    isEnabled: 0
                },
                dataRule: {
                    busTypeId: [
                        {required: true, message: '请选择公交车型', trigger: 'change'},
                    ],
                    orderDeliveryNum: [
                        {required: true, message: '请输入交付数量', trigger: 'blur'},
                        { type: 'number', message: '交付数量必须为数字值'}
                    ],
                    orderDeliveryDate: [
                        {required: true, message: '请选择交付日期', trigger: 'change'}
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
                this.dataForm.orderDeliveryDate = '';
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
                    console.log("打开编辑弹窗,ID:"+id);
                    getOrderCompanyDeliveryDetail(id).then((res) => {
                        console.log("单条详细:"+JSON.stringify(res.data.data.data));
                        if (res.data && res.data.code === 0) {

                            this.dataForm.companyDeliveryId = res.data.data.data.companyDeliveryId;
                            this.dataForm.busTypeId = res.data.data.data.busTypeId;
                            this.dataForm.orderDeliveryNum = res.data.data.data.orderDeliveryNum;
                            this.dataForm.orderDeliveryDate = res.data.data.data.orderDeliveryDate;
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
                    console.log("打开新增弹窗");
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
                            editOrderCompanyDelivery(this.dataForm).then((res) => {
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
                            saveOrderCompanyDelivery(this.dataForm).then((res) => {
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
