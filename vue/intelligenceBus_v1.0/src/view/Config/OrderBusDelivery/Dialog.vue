<template>
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" style="color:#fff">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForms" @keyup.enter.native="dataFormSubmit()" label-width="120px">

            <el-form-item label="订单" size="mini" prop="orderId" >
                <el-select
                        style="margin-bottom: 4px;"
                        v-model="dataForm.orderId"
                        v-bind:disabled="dis"
                        filterable
                        default-first-option
                        @change="orderIdOptionsChange"
                        placeholder="请选择">
                    <el-option
                            v-for="item in orderIdOptions"
                            :key="item.orderId"
                            :label="item.orderCode"
                            :value="item.orderId">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="订单交付" size="mini" prop="companyDeliveryId">
                <el-select
                        :disabled = "companyDeliveryDisabled"
                        style="margin-bottom: 4px;"
                        v-bind:disabled="dis"
                        v-model="dataForm.companyDeliveryId"
                        filterable
                        default-first-option
                        placeholder="请选择">
                    <el-option
                            v-for="item in companyDeliveryIdOptions"
                            :key="item.companyDeliveryId"
                            :label="item.orderDesc"
                            :value="item.companyDeliveryId"
                            :disabled="item.isCompleted == 1">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="车辆VIN码" size="mini" prop="busId">
                <el-select
                        style="margin-bottom: 4px;"
                        v-model="dataForm.busId"
                        filterable
                        default-first-option
                        placeholder="请选择公交VIN">
                    <el-option
                            v-for="item in busIdOptions"
                            :key="item.busId"
                            :label="item.vinCode"
                            :value="item.busId">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="车牌号" prop="plateCode">
                <el-input v-model="dataForm.plateCode" placeholder="车牌号" maxlength="16"></el-input>
            </el-form-item>
            <el-form-item label="车辆编号" prop="busCode">
                <el-input v-model="dataForm.busCode" placeholder="车辆编号"></el-input>
            </el-form-item>
            <el-form-item label="交付日期" prop="orderDeliveryDate">
                <el-date-picker
                      v-model="dataForm.orderDeliveryDate"
                      type="date"
                      placeholder="选择日期"
                      format="yyyy-MM-dd"
                      value-format="yyyy-MM-dd HH:mm:ss">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="交付说明" prop="orderDeliveryDesc">
                <el-input v-model="dataForm.orderDeliveryDesc" type="textarea" placeholder="交付说明" maxlength="300"></el-input>
            </el-form-item>
            <el-form-item label="出保日期" prop="orderOutDate">
                <el-date-picker
                      v-model="dataForm.orderOutDate"
                      type="date"
                      placeholder="选择日期"
                      format="yyyy-MM-dd"
                      value-format="yyyy-MM-dd HH:mm:ss">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="出保说明" prop="orderOutDesc">
                <el-input v-model="dataForm.orderOutDesc" type="textarea" placeholder="出保说明" maxlength="30"></el-input>
            </el-form-item>

        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button @click="visible = false">取消</el-button>
            <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
        </span>
    </el-dialog>
</template>
<script>
    import {saveOrderBusDelivery, editOrderBusDelivery, getOrderBusDeliveryDetail} from "@/api/parameter";
    import {getBusSNoDelivery,getBusCompanyOrderS,getDeliveryOrderS} from "@/api/selectionApi";

    export default {
        name: 'Dialog',
        data() {
            return {
                dis:false,
                title: '',
                visible: false,
                value: false,
                companyDeliveryDisabled: true,
                busIdOptions: [],
                orderIdOptions: [],
                companyDeliveryIdOptions: [],
                orderIdOptionsQuery:{orderId:"",isCompleted:""},
                dataForm: {
                    busDeliveryId: '',
                    busId: '',
                    orderId: '',
                    plateCode: '',
                    busCode:'',
                    companyDeliveryId: '',
                    orderDeliveryDate: '',
                    orderDeliveryDesc: '',
                    orderOutDate: '',
                    orderOutDesc: '',
                    isDeleted: '',
                    createdDate: '',
                    modifiedDate: '',

                    isEnabled: 0
                },
                dataRule: {
                    busId: [
                        {required: true, message: '请选择公交车', trigger: 'change'}
                    ],
                    plateCode: [
                        {required: true, message: '请输入车牌号', trigger: 'blur'},
                        {max: 16, message: '长度不能超过16个字符', trigger: 'blur'}
                    ],
                    orderId: [
                        {required: true, message: '请选择所属订单', trigger: 'change'}
                    ],
                    companyDeliveryId: [
                        {required: true, message: '请选择所属订单交付', trigger: 'change'}
                    ],
                    orderDeliveryDate: [
                        {required: true, message: '请选择交付日期', trigger: 'change'}
                    ],
                    orderDeliveryDesc: [
                        {max: 200, message: '长度不能超过200个字符', trigger: 'blur'}
                    ],
                   /* orderOutDate: [
                        {type: 'date', required: true, message: '请选择出保', trigger: 'change'}
                    ],*/
                    orderOutDesc: [
                        {max: 200, message: '长度不能超过200个字符', trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            //编辑或新增进入弹窗
            initData: function (id) {
                this.visible = true;
                this.value = false;
                this.$nextTick(() => {
                    if (this.$refs['dataForms'] !== undefined) {
                        this.$refs['dataForms'].resetFields()
                    }
                });
                this.dataForm.busId = '';
                this.dataForm.orderId = '';
                this.dataForm.companyDeliveryId = '';
                this.dataForm.orderDeliveryDate = '';
                this.dataForm.orderOutDate = '';
                getBusSNoDelivery().then((res) => {
                if (res.data && res.data.code === 0) {
                    this.busIdOptions = res.data.data.selection
                }
                if (id) {
                    const loading = this.$loading({
                        lock: true,
                        text: '加载中.......',
                        spinner: 'el-icon-loading',
                        background: 'rgba(0, 0, 0, 0.7)'
                    });
                    this.title = '修改';
                    console.log("打开编辑弹窗,ID:"+id);

                    getOrderBusDeliveryDetail(id).then((res) => {
                        console.log("单条详细:"+JSON.stringify(res.data.data.data));
                        if (res.data && res.data.code === 0) {
                            this.dis = true;
                            this.dataForm.orderId = res.data.data.data.orderId;
                            this.dataForm.plateCode = res.data.data.data.plateCode;
                            this.dataForm.busCode = res.data.data.data.busCode;
                            this.dataForm.busDeliveryId = res.data.data.data.busDeliveryId;
                            this.dataForm.busId = res.data.data.data.busId;
                            this.dataForm.companyDeliveryId = res.data.data.data.companyDeliveryId;
                            this.dataForm.orderDeliveryDate = res.data.data.data.orderDeliveryDate;
                            this.dataForm.orderDeliveryDesc = res.data.data.data.orderDeliveryDesc;
                            this.dataForm.orderOutDate = res.data.data.data.orderOutDate;
                            this.dataForm.orderOutDesc = res.data.data.data.orderOutDesc;
                            this.dataForm.isDeleted = res.data.data.data.isDeleted;
                            this.dataForm.createdDate = res.data.data.data.createdDate;
                            this.dataForm.modifiedDate = res.data.data.data.modifiedDate;
                            this.busIdOptions.push({"busId":res.data.data.data.busId,
                                "vinCode":res.data.data.data.vinCode});
                            this.companyDeliveryDisabled = false;
                            this.orderIdOptionsQuery.orderId = this.dataForm.orderId;
                            this.orderIdOptionsQuery.isCompleted = 0;
                            this.companyDeliveryIdOptions = [];
                            this.companyDeliveryIdOptions.push({"companyDeliveryId":res.data.data.data.companyDeliveryId,
                                "orderDesc":res.data.data.data.busTypeName+"_"+res.data.data.data.orderDeliveryNum});
                            console.log(this.companyDeliveryIdOptions)
                            this.orderIdOptions = [];
                            this.orderIdOptions.push({"orderId":res.data.data.data.orderId,"orderCode":res.data.data.data.orderCode})
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
                    this.dis = false;
                    this.value = true;
                    this.orderIdOptions = [];
                    getBusCompanyOrderS({isCompleted:0}).then((res) => {
                        if (res.data && res.data.code === 0) {
                            this.orderIdOptions = res.data.data.selection
                            return;
                        }
                    });
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
                            editOrderBusDelivery(this.dataForm).then((res) => {
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
                            saveOrderBusDelivery(this.dataForm).then((res) => {
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
                this.dataForm.companyDeliveryId = '';
                this.companyDeliveryDisabled = false;

                this.orderIdOptionsQuery.orderId = value;
                this.orderIdOptionsQuery.isCompleted = 0;
                getDeliveryOrderS(this.orderIdOptionsQuery).then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.companyDeliveryIdOptions = res.data.data.selection
                        return;
                    }
                });
            }

        }
    }

</script>
<style>
</style>
