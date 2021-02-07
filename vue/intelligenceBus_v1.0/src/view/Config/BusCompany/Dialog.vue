<template>
<div>
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" style="color:#fff">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForms" @keyup.enter.native="dataFormSubmit()" label-width="120px">

            <el-form-item label="区域" style="position: relative" prop="areaName">
                <el-popover
                    ref="areaPopover"
                    @show="showPopoverHandle"
                    placement="bottom-start"
                    v-model="treeShowFlag"
                    trigger="click">
                    <el-scrollbar style="height: 240px">
                        <el-tree
                            :data="treeData"
                            :props="treeProps"
                            accordion
                            node-key="areaId"
                            ref="treeRef"
                            @node-click="nodeClickHandle"
                            :default-expand-all="false"
                            :highlight-current="true"
                            :expand-on-click-node="false">
                        </el-tree>
                    </el-scrollbar>
                </el-popover>
                <el-input
                    v-model="dataForm.areaName"
                    v-popover:areaPopover
                    :readonly="true"
                    placeholder="选择区域"
                    clearable
                    class="menu-list__input"></el-input>
                <span style="position: absolute;right:8px;top: 0px;z-index: 9999;cursor: pointer;" @click="clearInput">清除</span>
            </el-form-item>
            <el-form-item label="校车公司名称" prop="companyName">
                <el-input v-model="dataForm.companyName" placeholder="校车公司名称" maxlength="30"></el-input>
            </el-form-item>
<!--            <el-form-item label="校车公司图标">-->
<!--                <el-upload ref="upload"-->
<!--                           name="companySnapshotFile"-->
<!--                           :action="upUrl()"-->
<!--                           :before-upload="beforeUp"-->
<!--                           :on-exceed="exceed"-->
<!--                           accept=".jpg,.jpeg,.png,.gif"-->
<!--                           :limit="1" :with-credentials="false">-->
<!--                    <img v-if="dataForm.companySnapshot" :src="dataForm.companySnapshot"-->
<!--                         style="height: 38px;width: 38px; margin-right: 20px;float:left" class="avatar">-->
<!--                    <el-button size="small" type="primary">选择要上传的图片</el-button>-->
<!--                </el-upload>-->
<!--            </el-form-item>-->
            <el-form-item label="统一信用代码" prop="companyCode">
                <el-input v-model="dataForm.companyCode" placeholder="统一信用代码" maxlength="30"></el-input>
            </el-form-item>
            <el-form-item label="经营范围" prop="companyScope">
                <el-input type="textarea" v-model="dataForm.companyScope" placeholder="经营范围"></el-input>
            </el-form-item>
            <el-form-item label="公司地址" prop="companyAddress">
                <el-input type="textarea" v-model="dataForm.companyAddress" placeholder="公司地址"></el-input>
            </el-form-item>
            <el-form-item label="联系人" prop="companyPerson">
                <el-input v-model="dataForm.companyPerson" placeholder="联系人" maxlength="30"></el-input>
            </el-form-item>
            <el-form-item label="电话" prop="companyPhone">
                <el-input v-model="dataForm.companyPhone" placeholder="电话" maxlength="30"></el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="companyEmail">
                <el-input v-model="dataForm.companyEmail" placeholder="邮箱" maxlength="30"></el-input>
            </el-form-item>
            <el-form-item label="经度" prop="companyLongitude">
                <el-input v-model="dataForm.companyLongitude" placeholder="经度" readonly @click.native="showMap"></el-input>
            </el-form-item>
            <el-form-item label="纬度" prop="companytLatitude">
                <el-input v-model="dataForm.companytLatitude" placeholder="纬度" readonly @click.native="showMap"></el-input>
            </el-form-item>

            <el-form-item label="是否启用">
                <el-switch v-model="value" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
            </el-form-item>

            <el-form-item label="是否集团">
                <el-switch v-model="clique" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
            </el-form-item>
            <el-form-item label="所属集团" size="mini" prop="parentCompanyId" v-if="!clique">
                <el-select
                    style="margin-bottom: 4px;"
                    v-model="dataForm.parentCompanyId"
                    filterable
                    clearable
                    default-first-option
                    placeholder="仅在有所属集团时选择">
                    <el-option
                        v-for="item in companyList"
                        :key="item.companyId"
                        :label="item.companyName"
                        :value="item.companyId">
                    </el-option>
                </el-select>
            </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button @click="visible = false">取消</el-button>
            <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
        </span>
    </el-dialog>
    <Map v-show="mapVisible" ref="Map" @callback="getLocation"></Map>
</div>
</template>
<script>
    import {saveInfoBusCompany, editInfoBusCompany, getInfoBusCompanyDetail,getInfoBusCompanyCliqueListAll} from "@/api/parameter";
    import {treeDataTranslate1} from '@/util/index';
    import {getRegionalS} from '@/api/selectionApi';
    import Map from '@/components/map/index.vue';

    export default {
        name: 'Dialog',
        components: {Map},
        data() {
            return {
                title: '',
                visible: false,
                value: true,
                clique:false,
                treeData: [],
                companyList:[],
                treeShowFlag: false,
                mapVisible: false,
                treeProps:{
                    label: 'areaName',
                    children: 'children'
                },
                dataForm: {
                    companyId: '',
                    areaId: '',
                    areaName:'',
                    companyName: '',
                    companySnapshot: '',
                    companyCode: '',
                    companyScope: '',
                    companyAddress: '',
                    companyPerson: '',
                    companyPhone: '',
                    companyEmail: '',
                    companyLongitude: '',
                    companytLatitude: '',
                    isEnabled: 0,
                    isClique:0,
                    parentCompanyId:'',
                    isDeleted: '',
                    createdBy: '',
                    createdDate: '',
                    modifiedBy: '',
                    modifiedDate: ''
                },
                dataRule: {
                    areaName: [
                        {required: true, message: '请选择所属地区', trigger: 'change'}
                    ],
                    companyName: [
                        {required: true, message: '请输入公司名称', trigger: 'blur'},
                        {max: 20, message: '长度不能超过20个字符', trigger: 'blur'}
                    ],
                    companyCode: [
                        {required: true, message: '请输入统一信用代码', trigger: 'blur'},
                        {max: 20, message: '长度不能超过20个字符', trigger: 'blur'}
                    ],
                    companyScope: [
                        {required: true, message: '请输入经营范围', trigger: 'blur'},
                        {max: 512, message: '长度不能超过512个字符', trigger: 'blur'}
                    ],
                    companyAddress: [
                        {required: true, message: '请输入公司地址', trigger: 'blur'},
                        {max: 128, message: '长度不能超过128个字符', trigger: 'blur'}
                    ],
                    companyPerson: [
                        {required: true, message: '请输入联系人', trigger: 'blur'},
                        {max: 64, message: '长度不能超过64个字符', trigger: 'blur'}
                    ],
                    companyPhone: [
                        {required: true, message: '请输入联系电话', trigger: 'blur'},
                        {pattern:/^(([1][0-9]{10})|((\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}))$/, message: '电话格式不正确' }
                    ],
                    companyEmail: [
                        {required: true, message: '请输入邮箱', trigger: 'blur'},
                        { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
                    ],
                    companyLongitude: [
                        {required: true, message: '请输入经度', trigger: 'change'}
                    ],
                    companytLatitude: [
                        {required: true, message: '请输入纬度', trigger: 'change'}
                    ]
                }
            }
        },
        methods: {
            //编辑或新增进入弹窗
            initData: function (id) {

                getInfoBusCompanyCliqueListAll().then((res) => {
                    if (res.data && res.data.code == 0) {
                        this.companyList = res.data.data.list
                    }
                });
                this.visible = true;
                this.value = true;
                this.clique = false;
                this.dataForm.areaName = '';
                this.dataForm.companySnapshot = '';
                this.$nextTick(() => {
                    if (this.$refs['dataForms'] !== undefined) {
                        this.$refs['dataForms'].resetFields()
                    }
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
                    getInfoBusCompanyDetail(id).then((res) => {
                        console.log("单条详细:"+JSON.stringify(res.data.data.data));
                        if (res.data && res.data.code === 0) {

                            this.dataForm.companyId = res.data.data.data.companyId;
                            this.dataForm.areaId = res.data.data.data.areaId;
                            this.dataForm.companyName = res.data.data.data.companyName;
                            this.dataForm.companySnapshot = res.data.data.data.companySnapshot;
                            this.dataForm.companyCode = res.data.data.data.companyCode;
                            this.dataForm.companyScope = res.data.data.data.companyScope;
                            this.dataForm.companyAddress = res.data.data.data.companyAddress;
                            this.dataForm.companyPerson = res.data.data.data.companyPerson;
                            this.dataForm.companyPhone = res.data.data.data.companyPhone;
                            this.dataForm.companyEmail = res.data.data.data.companyEmail;
                            this.dataForm.companyLongitude = res.data.data.data.companyLongitude;
                            this.dataForm.companytLatitude = res.data.data.data.companytLatitude;
                            this.dataForm.isEnabled = res.data.data.data.isEnabled;
                            this.dataForm.isClique = res.data.data.data.isClique;
                            this.dataForm.parentCompanyId = res.data.data.data.parentCompanyId;
                            getRegionalS().then((res) => {
                                //console.log(JSON.stringify(res.data.data.selection));
                                var areaId = this.dataForm.areaId;
                                var index = res.data.data.selection.findIndex(function(p){
                                    return p.areaId == areaId;
                                })
                                if (res.data && res.data.code == 0) {
                                    this.treeData = treeDataTranslate1(res.data.data.selection);
                                    if(index != -1){
                                        this.dataForm.areaName = res.data.data.selection[index].areaName;
                                        console.log(this.dataForm.areaName);
                                    }
                                }
                            })

                            if (res.data.data.data.isEnabled == 1) {
                                this.value = true
                            } else {
                                this.value = false
                            }

                            if (res.data.data.data.isClique == 1) {
                                this.clique = true;
                            } else {
                                this.clique = false
                            }
                        }
                    });
                    loading.close();
                } else {
                    console.log("打开新增弹窗");
                    this.title = '新增'
                    this.dataForm.isEnabled = 0;
                    this.dataForm.isClique = 0;
                    this.value = true;
                    this.clique = false;
                    console.log(this.value);
                    getRegionalS().then((res) => {
                    //loading.close();
                        if (res.data && res.data.code == 0) {
                            this.treeData = treeDataTranslate1(res.data.data.selection)
                        }
                    });
                }
            },
            // 表单提交
            dataFormSubmit: function () {
                if (this.value == true) {
                    this.dataForm.isEnabled = 1;
                } else {
                    this.dataForm.isEnabled = 0;
                }

                if (this.clique == true) {
                    this.dataForm.isClique = 1;
                    this.dataForm.parentCompanyId = null;
                } else {
                    this.dataForm.isClique = 0;
                }
                this.$refs['dataForms'].validate((valid) => {
                    if (valid) {
                        const loading = this.$loading({
                            lock: true,
                            text: '加载中.......',
                            spinner: 'el-icon-loading',
                            background: 'rgba(0, 0, 0, 0.7)'
                        });
                        let newForm = this.uploadForm;
                        if(this.uploadForm.get("companySnapshotFile") != null){
                            newForm.set("companySnapshotFile",this.uploadForm.get("companySnapshotFile"));
                        }
                        for (var key in this.dataForm) {
                            if(this.dataForm[key] != null && this.dataForm[key].toString() != ''){
                            console.log("-------------------key:"+key+" value:"+this.dataForm[key]);
                                newForm.set(key, this.dataForm[key])
                            }
                        }
                        newForm.append("createdDate", "2020-01-01 12:12:12");
                        // this.$refs.upload.submit();
                        //return;

                        if (this.title == '修改') {
                            console.log("修改提交:"+JSON.stringify(this.dataForm));
                            editInfoBusCompany(newForm).then((res) => {
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
                            saveInfoBusCompany(newForm).then((res) => {
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
            showPopoverHandle: function () {
                console.log(this.dataForm.areaId);
                this.$refs.treeRef.setCurrentKey(this.dataForm.areaId);
            },
            // 选中当前节点的数据
            nodeClickHandle(data) {
                console.log("选择节点:"+JSON.stringify(data));
                this.treeShowFlag = false;
                this.dataForm.areaName = data.areaName;
                this.dataForm.areaId = data.areaId;
            },
            clearInput: function () {
                this.dataForm.areaName = '';
                this.dataForm.areaId = '';
            },
            upUrl() {
                return "xxx";
            },
            beforeUp(file) {
                this.dataForm.companySnapshot=URL.createObjectURL(file);
                this.uploadForm.append('companySnapshotFile', file);
                this.nextStepDisabled = true;
                return false;
            },
            exceed(files, fileList) {
                this.$message({
                    message: "超出上传文件数量限制，请先删除已有文件后再上传",
                    type: 'error',
                    duration: 3000
                })
            },
            showMap() {
                this.mapVisible = true
                this.$nextTick(() => {
                    this.$refs.Map.init(this.dataForm.companyLongitude, this.dataForm.companytLatitude)
                });
            },
            getLocation(data) {
                this.dataForm.companyLongitude = data.longitude
                this.dataForm.companytLatitude = data.latitude
            }
        }
    }

</script>
<style>
</style>
