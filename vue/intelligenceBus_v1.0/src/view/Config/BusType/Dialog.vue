<template>
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" style="color:#fff">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForms" @keyup.enter.native="dataFormSubmit()" label-width="120px">

            <el-form-item label="车型名称" prop="busTypeName">
                <el-input v-model="dataForm.busTypeName" placeholder="校车车型名称" maxlength="20"></el-input>
            </el-form-item>
            <el-form-item label="车型编码" prop="busTypeCode">
                <el-input v-model="dataForm.busTypeCode" placeholder="校车车型编码" maxlength="20"></el-input>
            </el-form-item>

<!--            <el-form-item label="电机类型" size="mini" prop="motorTypeId">-->
<!--                <el-select-->
<!--                        style="margin-bottom: 4px;"-->
<!--                        v-model="dataForm.motorTypeId"-->
<!--                        filterable-->
<!--                        default-first-option-->
<!--                        placeholder="请选择电机类型">-->
<!--                    <el-option-->
<!--                            v-for="item in motorTypeIdOptions"-->
<!--                            :key="item.motorTypeId"-->
<!--                            :label="item.motorTypeName"-->
<!--                            :value="item.motorTypeId">-->
<!--                    </el-option>-->
<!--                </el-select>-->
<!--            </el-form-item>-->
<!--            <el-form-item label="电池类型" size="mini" prop="batteryTypeId">-->
<!--                <el-select-->
<!--                        style="margin-bottom: 4px;"-->
<!--                        v-model="dataForm.batteryTypeId"-->
<!--                        filterable-->
<!--                        default-first-option-->
<!--                        placeholder="请选择电池类型">-->
<!--                    <el-option-->
<!--                            v-for="item in batteryTypeIdOptions"-->
<!--                            :key="item.batteryTypeId"-->
<!--                            :label="item.batteryTypeName"-->
<!--                            :value="item.batteryTypeId">-->
<!--                    </el-option>-->
<!--                </el-select>-->
<!--            </el-form-item>-->

<!--            <el-form-item label="车型照片">-->
<!--                <el-upload ref="upload"-->
<!--                           name="busTypeImageFile"-->
<!--                           action=""-->
<!--                           :before-upload="beforeUp"-->
<!--                           :on-exceed="exceed"-->
<!--                           accept=".jpg,.jpeg,.png,.gif"-->
<!--                           :limit="1" :with-credentials="false">-->
<!--                    <img v-if="dataForm.busTypeImageUrl" :src="dataForm.busTypeImageUrl"-->
<!--                         style="height: 38px;width: 38px; margin-right: 20px;float:left" class="avatar">-->
<!--                    <el-button size="small" type="primary">选择要上传的图片</el-button>-->
<!--                </el-upload>-->
<!--            </el-form-item>-->

            <el-form-item label="车型说明" prop="busTypeDesc">
                <el-input type="textarea" v-model="dataForm.busTypeDesc" placeholder="车型说明" maxlength="128"></el-input>
            </el-form-item>
            <el-form-item label="质保里程(km)" prop="warrantyMileage">
                <el-input v-model.number="dataForm.warrantyMileage" placeholder="质保里程" maxlength="30"></el-input>
            </el-form-item>
            <el-form-item label="质保年数" prop="warrantyYears">
                <el-input v-model.number="dataForm.warrantyYears" placeholder="质保年数" maxlength="30"></el-input>
            </el-form-item>
            <el-form-item label="核载人数" prop="peopleNumber">
                <el-input v-model.number="dataForm.peopleNumber" placeholder="核载人数" maxlength="30"></el-input>
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
</template>
<script>
    import {saveInfoBusType, editInfoBusType, getInfoBusTypeDetail} from "@/api/parameter";
    import {getMotorTypeTypeS,getBatteryTypeS} from "@/api/selectionApi";

    export default {
        name: 'Dialog',
        data() {
            return {
                title: '',
                visible: false,
                value: false,
                // motorTypeIdOptions: [],
                // batteryTypeIdOptions: [],
                uploadForm: new FormData(),
                dataForm: {
                    busTypeId: '',
                    // motorTypeId: '',
                    // batteryTypeId: '',
                    busTypeName: '',
                    busTypeCode: '',
                    // busTypeImage: '',
                    // busTypeImageUrl: '',
                    busTypeDesc: '',
                    warrantyMileage: '',
                    warrantyYears: '',
                    peopleNumber: '',
                    isDeleted: '',
                    createdBy: '',
                    createdDate: '',
                    modifiedBy: '',
                    modifiedDate: "",
                    isEnabled: 0
                },
                dataRule: {
                    busTypeName: [
                        {required: true, message: '请输入校车车型名称', trigger: 'blur'},
                        {max: 20, message: '长度不能超过20个字符', trigger: 'blur'}
                    ],
                    busTypeCode: [
                        {required: true, message: '请输入校车车型编码', trigger: 'blur'},
                        {max: 20, message: '长度不能超过20个字符', trigger: 'blur'}
                    ],
                    motorTypeId: [
                        {required: true, message: '请选择电机类型', trigger: 'change'}
                    ],
                    batteryTypeId: [
                        {required: true, message: '请选择电池类型', trigger: 'change'}
                    ],
                    busTypeDesc: [
                        {max: 128, message: '长度不能超过128个字符', trigger: 'blur'}
                    ],
                    warrantyMileage: [
                        {message: '请输入质保里程', trigger: 'blur'}
                    ],
                    warrantyYears: [
                        {message: '请输入质保年数', trigger: 'blur'}
                    ],
                    peopleNumber: [
                        {required: true, message: '请输入核载人数', trigger: 'blur'},
                        { type: 'number', message: '核载人数必须为数字值'}
                    ]
                }
            }
        },
        methods: {
            //编辑或新增进入弹窗
            initData: function (id) {
                this.visible = true;
                this.$nextTick(() => {
                    if (this.$refs['dataForms'] !== undefined) {
                        this.$refs['dataForms'].resetFields()
                    }
                    // if (this.$refs.upload !== undefined) {
                    //     this.$refs.upload.clearFiles();
                    // }
                });
                this.uploadForm = new FormData();
                if (id) {
                    const loading = this.$loading({
                        lock: true,
                        text: '加载中.......',
                        spinner: 'el-icon-loading',
                        background: 'rgba(0, 0, 0, 0.7)'
                    });
                    this.title = '修改';
                    console.log("打开编辑弹窗,ID:"+id);
                    getInfoBusTypeDetail(id).then((res) => {
                        console.log("单条详细:"+JSON.stringify(res.data.data.data));
                        if (res.data && res.data.code === 0) {

                            this.dataForm.busTypeId = res.data.data.data.busTypeId;
                            // this.dataForm.motorTypeId = res.data.data.data.motorTypeId;
                            // this.dataForm.batteryTypeId = res.data.data.data.batteryTypeId;
                            this.dataForm.busTypeName = res.data.data.data.busTypeName;
                            this.dataForm.busTypeCode = res.data.data.data.busTypeCode;
                            // this.dataForm.busTypeImage = res.data.data.data.busTypeImage;
                            // this.dataForm.busTypeImageUrl = res.data.data.data.busTypeImageUrl;
                            this.dataForm.busTypeDesc = res.data.data.data.busTypeDesc;
                            this.dataForm.warrantyMileage = res.data.data.data.warrantyMileage;
                            this.dataForm.warrantyYears = res.data.data.data.warrantyYears;
                            this.dataForm.peopleNumber = res.data.data.data.peopleNumber;
                            this.dataForm.isDeleted = res.data.data.data.isDeleted;
                            this.dataForm.isEnabled = res.data.data.data.isEnabled;
                            this.dataForm.createdBy = res.data.data.data.createdBy;
                            this.dataForm.createdDate = res.data.data.data.createdDate;
                            this.dataForm.modifiedBy = res.data.data.data.modifiedBy;
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
                    this.dataForm.busTypeImageUrl = '';
                    this.value = true;
                }

                // getMotorTypeTypeS().then((res) => {
                //     if (res.data && res.data.code === 0) {
                //         this.motorTypeIdOptions = res.data.data.selection
                //         return;
                //     }
                // });
                //
                // getBatteryTypeS().then((res) => {
                //     if (res.data && res.data.code === 0) {
                //         this.batteryTypeIdOptions = res.data.data.selection
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
                        let newForm = new FormData();
                        // if(this.uploadForm.get("busTypeImageFile") != null){
                        //     newForm.set("busTypeImageFile",this.uploadForm.get("busTypeImageFile"));
                        // }
                        for (var key in this.dataForm) {
                            if(this.dataForm[key] != null && this.dataForm[key].toString() != ''){
                                console.log("aa"+key);
                                newForm.set(key, this.dataForm[key])
                            }
                        }
                        newForm.append("createdDate", "2020-01-01 12:12:12");
                        // this.$refs.upload.submit();

                        if (this.title == '修改') {
                            console.log("修改提交:"+JSON.stringify(this.dataForm));
                            editInfoBusType(newForm).then((res) => {
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
                            saveInfoBusType(newForm).then((res) => {
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
                this.dataForm.busTypeImageUrl=URL.createObjectURL(file);
                this.uploadForm.append('busTypeImageFile', file)
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
