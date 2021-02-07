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
            <el-form-item label="车厂名称" prop="factoryName">
                <el-input v-model="dataForm.factoryName" placeholder="公交车厂名称" maxlength="64"></el-input>
            </el-form-item>
            <el-form-item label="车厂图标" >
                <el-upload ref="upload"
                           name="factorySnapshotFile"
                           action=""
                           :before-upload="beforeUp"
                           :on-exceed="exceed"
                           accept=".jpg,.jpeg,.png,.gif"
                           :limit="1" :with-credentials="false">
                    <img v-if="dataForm.factorySnapshotUrl" :src="dataForm.factorySnapshotUrl"
                         style="height: 38px;width: 38px; margin-right: 20px;float:left" class="avatar">
                    <el-button size="small" type="primary">选择要上传的图片</el-button>
                </el-upload>
            </el-form-item>
            <el-form-item label="统一信用代码" prop="factoryCode">
                <el-input v-model="dataForm.factoryCode" placeholder="统一信用代码" maxlength="64"></el-input>
            </el-form-item>
            <el-form-item label="经营范围" prop="factoryBusinessScope">
                <el-input type="textarea" v-model="dataForm.factoryBusinessScope" placeholder="经营范围" maxlength="512"></el-input>
            </el-form-item>
            <el-form-item label="车厂地址" prop="factoryAddress">
                <el-input type="textarea" v-model="dataForm.factoryAddress" placeholder="车厂地址" maxlength="128"></el-input>
            </el-form-item>
            <el-form-item label="经度" prop="factoryLongitude">
                <el-input v-model="dataForm.factoryLongitude" placeholder="经度" readonly @click.native="showMap"></el-input>
            </el-form-item>
            <el-form-item label="纬度" prop="factoryLatitude">
                <el-input v-model="dataForm.factoryLatitude" placeholder="纬度" readonly @click.native="showMap"></el-input>
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

    <Map v-show="mapVisible" ref="Map" @callback="getLocation"></Map>
    </div>

</template>
<script>
    import {saveBusFactory, editBusFactory, getBusFactoryDetail} from "@/api/parameter";
    import {treeDataTranslate1} from '@/util/index'
    import {getRegionalS} from '@/api/selectionApi'
    import Map from '@/components/map/index.vue'

    export default {
        name: 'Dialog',
        components: {Map},
        data() {
            return {
                title: '',
                visible: false,
                value: false,
                treeShowFlag: false,
                mapVisible: false,
                treeData: [],
                treeProps:{
                    label: 'areaName',
                    children: 'children'
                },
                uploadForm: new FormData(),
                dataForm: {
                    factoryId: '',
                    areaId: '',
                    areaName: '',
                    factoryName: '',
                    factorySnapshot:'',
                    factorySnapshotUrl:'',
                    factoryCode: '',
                    factoryBusinessScope: '',
                    factoryAddress: '',
                    factoryLongitude: '',
                    factoryLatitude: '',
                    isEnabled: 0
                },
                dataRule: {
                    areaName: [
                        {required: true, message: '请选择车厂所在区域', trigger: 'change'}
                    ],
                    factoryName: [
                        {required: true, message: '请输入车厂名称', trigger: 'blur'},
                        {max: 64, message: '长度不能超过64个字符', trigger: 'blur'}
                    ],
                    factoryCode: [
                        {required: true, message: '请输入统一信用代码', trigger: 'blur'},
                        {max: 64, message: '长度不能超过64个字符', trigger: 'blur'}
                    ],
                    factoryBusinessScope: [
                        {required: true, message: '请输入经营范围', trigger: 'blur'},
                        {max: 512, message: '长度不能超过512个字符', trigger: 'blur'}
                    ],
                    factoryAddress: [
                        {required: true, message: '请输入车厂地址', trigger: 'blur'},
                        {max: 128, message: '长度不能超过128个字符', trigger: 'blur'}
                    ],
                    factoryLongitude: [
                        {required: true, message: '请选择车厂经度', trigger: 'change'}
                    ],
                    factoryLatitude: [
                        {required: true, message: '请选择车厂纬度', trigger: 'change'}
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
                    this.clearInput();
                    if (this.$refs['dataForms'] !== undefined) {
                        this.$refs['dataForms'].resetFields()
                    }
                    if (this.$refs.upload !== undefined) {
                        this.$refs.upload.clearFiles();
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
                    console.log("修改ID:"+id);
                    getBusFactoryDetail(id).then((res) => {
                        if (res.data && res.data.code === 0) {
                            this.dataForm.factoryId = res.data.data.data.factoryId;
                            this.dataForm.areaId = res.data.data.data.areaId;
                            this.dataForm.areaName = res.data.data.data.areaName;
                            this.dataForm.factoryName = res.data.data.data.factoryName;
                            this.dataForm.factoryCode = res.data.data.data.factoryCode;
                            this.dataForm.factorySnapshot = res.data.data.data.factorySnapshot;
                            this.dataForm.factorySnapshotUrl = res.data.data.data.factorySnapshotUrl;
                            this.dataForm.factoryBusinessScope = res.data.data.data.factoryBusinessScope;
                            this.dataForm.factoryAddress = res.data.data.data.factoryAddress;
                            this.dataForm.factoryLongitude = res.data.data.data.factoryLongitude;
                            this.dataForm.factoryLatitude = res.data.data.data.factoryLatitude;
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
                        }
                    });
                    loading.close();
                } else {
                    console.log("新增");
                    this.title = '新增'
                    this.dataForm.isEnabled = 0;
                    this.dataForm.factorySnapshotUrl = '';
                    this.value = true;
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
                this.$refs['dataForms'].validate((valid) => {
                    if (valid) {
                        const loading = this.$loading({
                            lock: true,
                            text: '加载中.......',
                            spinner: 'el-icon-loading',
                            background: 'rgba(0, 0, 0, 0.7)'
                        });
                        let newForm = this.uploadForm;
                        if(this.uploadForm.get("factorySnapshotFile") != null){
                            newForm.set("factorySnapshotFile",this.uploadForm.get("factorySnapshotFile"));
                        }
                        for (var key in this.dataForm) {
                            if(this.dataForm[key] != null && this.dataForm[key].toString() != ''){
                                newForm.set(key, this.dataForm[key])
                            }
                        }
                        newForm.append("createdDate", "2020-01-01 12:12:12");
                        this.$refs.upload.submit();

                        if (this.title == '修改') {
                            console.log("修改:"+JSON.stringify(this.dataForm));
                            editBusFactory(newForm).then((res) => {
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
                            console.log("新增:"+JSON.stringify(this.dataForm));
                            saveBusFactory(newForm).then((res) => {
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
            showPopoverHandle: function () {
                console.log(this.dataForm.areaId);
                this.$refs.treeRef.setCurrentKey(this.dataForm.areaId);
            },
            showMap() {
                this.mapVisible = true
                this.$nextTick(() => {
                    this.$refs.Map.init(this.dataForm.factoryLongitude, this.dataForm.factoryLatitude)
                });
            },
            getLocation(data) {
                this.dataForm.factoryLongitude = data.longitude
                this.dataForm.factoryLatitude = data.latitude
            },
            upUrl() {
                return "xxx";
            },
            beforeUp(file) {
                this.dataForm.factorySnapshotUrl=URL.createObjectURL(file);
                this.uploadForm.append('factorySnapshotFile', file)
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
