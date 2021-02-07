<template>
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" style="color:#fff">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForms" @keyup.enter.native="dataFormSubmit()" label-width="120px">
            <el-form-item label="订单编码" prop="orderCode">
                <el-input v-model="dataForm.orderCode" placeholder="订单编码" maxlength="30"></el-input>
            </el-form-item>
            <el-form-item label="公交车厂" size="mini" prop="factoryId">
                <el-select
                    style="margin-bottom: 4px;"
                    v-model="dataForm.factoryId"
                    filterable
                    default-first-option
                    placeholder="请选择公交车厂">
                    <el-option
                        v-for="item in factoryIdOptions"
                        :key="item.factoryId"
                        :label="item.factoryName"
                        :value="item.factoryId">
                    </el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="公交公司" size="mini"  prop="companyId">
                <el-select
                        style="margin-bottom: 4px;"
                        v-model="dataForm.companyId"
                        filterable
                        default-first-option
                        @change="orderIdOptionsChange"
                        placeholder="请选择公交公司">
                    <el-option
                            v-for="item in companyIdOptions"
                            :key="item.companyId"
                            :label="item.companyName"
                            :value="item.companyId">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="签订时间" prop="orderDate">
                <el-date-picker
                      v-model="dataForm.orderDate"
                      type="date"
                      placeholder="选择日期时间"
                      value-format="yyyy-MM-dd">
                </el-date-picker>
            </el-form-item>
<!--            <el-form-item label="销售员Id" prop="sellerId">-->
<!--                <el-input v-model="dataForm.sellerId" placeholder="销售员Id" maxlength="30"></el-input>-->
<!--            </el-form-item>-->

            <el-form-item label="销售员" size="mini"  prop="sellerId">
                <el-select
                    style="margin-bottom: 4px;"
                    v-model="dataForm.sellerId"
                    filterable
                    default-first-option
                    placeholder="请选择销售员">
                    <el-option
                        v-for="item in sellIdOptions"
                        :key="item.userId"
                        :label="item.userName"
                        :value="item.userId">
                    </el-option>
                </el-select>
            </el-form-item>

        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button @click="visible = false">取消</el-button>
            <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
        </span>
    </el-dialog>
</template>
<script>
    import {saveOrderBusCompany, editOrderBusCompany, getOrderBusCompanyDetail} from "@/api/parameter";
    import {getBusFactoryS,getBusCompanyS,getSellOrderS} from "@/api/selectionApi";

    export default {
        name: 'Dialog',
        data() {
            return {
                title: '',
                visible: false,
                value: false,
                factoryIdOptions: [],
                companyIdOptions: [],
                sellIdOptions: [],
                sellIdOptionsQuery:{companyId:""},
                dataForm: {
                    orderId: '',
                    orderCode: '',
                    factoryId: '',
                    companyId: '',
                    orderDate: '',
                    sellerId: '',
                    isDeleted: '',
                    createdBy: '',
                    createdDate: '',
                    modifiedBy: '',
                    modifiedDate: '',

                    isEnabled: 0
                },
                dataRule: {
                    orderCode: [
                        {required: true, message: '请输入订单编码', trigger: 'blur'},
                        {max: 20, message: '长度不能超过20个字符', trigger: 'blur'}
                    ],
                    factoryId: [
                        {required: true, message: '请选择车厂', trigger: 'change'}
                    ],
                    companyId: [
                        {required: true, message: '请选择公司', trigger: 'change'}
                    ],
                    orderDate: [
                        {required: true, message: '请选择签订时间', trigger: 'change'}
                    ],
                    sellerId: [
                        {required: true, message: '请选择销售员', trigger: 'change'}
                    ],
                }
            }
        },
        methods: {
            //编辑或新增进入弹窗
            initData: function (id) {
                this.visible = true;
                this.value = false;
                this.dataForm.factoryId = '';
                this.dataForm.companyId = '';
                this.$nextTick(() => {
                    if (this.$refs['dataForms'] !== undefined) {
                        this.$refs['dataForms'].resetFields()
                    }
                })
                if (id) {
                    const loading = this.$loading({
                        lock: true,
                        text: '加载中.......',
                        spinner: 'el-icon-loading',
                        background: 'rgba(0, 0, 0, 0.7)'
                    });
                    this.title = '修改';
                    console.log("打开编辑弹窗,ID:"+id);
                    getOrderBusCompanyDetail(id).then((res) => {
                        console.log("单条详细:"+JSON.stringify(res.data.data.data));
                        if (res.data && res.data.code === 0) {

                            this.dataForm.orderId = res.data.data.data.orderId;
                            this.dataForm.orderCode = res.data.data.data.orderCode;
                            this.dataForm.factoryId = res.data.data.data.factoryId;
                            this.dataForm.companyId = res.data.data.data.companyId;
                            this.dataForm.orderDate = res.data.data.data.orderDate;
                            this.dataForm.sellerId = res.data.data.data.sellerId;
                            this.dataForm.isDeleted = res.data.data.data.isDeleted;
                            this.dataForm.createdBy = res.data.data.data.createdBy;
                            this.dataForm.createdDate = res.data.data.data.createdDate;
                            this.dataForm.modifiedBy = res.data.data.data.modifiedBy;
                            this.dataForm.modifiedDate = res.data.data.data.modifiedDate;

                            this.sellIdOptionsQuery.companyId = this.dataForm.companyId;
                            getSellOrderS(this.sellIdOptionsQuery).then((res) => {
                                if (res.data && res.data.code === 0) {
                                    this.sellIdOptions = res.data.data.selection
                                    return;
                                }
                            });

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

                getBusFactoryS().then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.factoryIdOptions = res.data.data.selection
                        return;
                    }
                });

                getBusCompanyS().then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.companyIdOptions = res.data.data.list
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
                            editOrderBusCompany(this.dataForm).then((res) => {
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
                            saveOrderBusCompany(this.dataForm).then((res) => {
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
            orderIdOptionsChange: function (value) {
                console.log(value);
                this.dataForm.sellerId = '';
                this.sellIdOptionsQuery.companyId = value;
                getSellOrderS(this.sellIdOptionsQuery).then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.sellIdOptions = res.data.data.selection
                        return;
                    }
                });
            }
        }
    }

</script>
<style>

</style>
