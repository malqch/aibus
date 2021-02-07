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
<!--            <el-form-item label="校车车站编码" prop="busStationCode">-->
<!--                <el-input v-model="dataForm.busStationCode" placeholder="校车车站编码" maxlength="20"></el-input>-->
<!--            </el-form-item>-->
            <el-form-item label="校车车站名称" prop="busStationName">
                <el-input v-model="dataForm.busStationName" placeholder="校车车站名称" maxlength="20"></el-input>
            </el-form-item>

            <el-form-item label="经度" prop="busStationLongitude">
                <el-input v-model="dataForm.busStationLongitude" placeholder="经度" readonly @click.native="showMap"></el-input>
            </el-form-item>
            <el-form-item label="纬度" prop="busStationLatitude">
                <el-input v-model="dataForm.busStationLatitude" placeholder="纬度" readonly @click.native="showMap"></el-input>
            </el-form-item>
            <el-form-item label="偏差(米)" prop="busStationDeviation">
                <el-input v-model.number="dataForm.busStationDeviation" placeholder="偏差(米)" maxlength="30"></el-input>
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
    import {saveInfoBusStation, editInfoBusStation, getInfoBusStationDetail} from "@/api/parameter";
    import Map from '@/components/map/index.vue'
    import {treeDataTranslate1} from '@/util/index';
    import {getRegionalS} from '@/api/selectionApi';

    export default {
        name: 'Dialog',
        components: {Map},
        data() {
            return {
                title: '',
                treeData: [],
                treeShowFlag: false,
                treeProps:{
                    label: 'areaName',
                    children: 'children'
                },
                visible: false,
                value: false,
                mapVisible: false,
                dataForm: {
                    areaId: '',
                    areaName:'',
                    busStationId: '',
                    busStationName: '',
                    // busStationCode: '',
                    busStationLongitude: '',
                    busStationLatitude: '',
                    busStationDeviation: 50,
                    isDeleted: '',
                    createdBy: '',
                    createdDate: '',
                    modifiedBy: '',
                    modifiedDate: '',
                    isEnabled: 0
                },
                dataRule: {
                    areaName: [
                        {required: true, message: '请选择区域', trigger: 'change'}
                    ],
                    busStationName: [
                        {required: true, message: '请输入校车车站名称', trigger: 'blur'},
                        {max: 20, message: '长度不能超过20个字符', trigger: 'blur'}
                    ],
                    // busStationCode: [
                    //     {required: true, message: '请输入校车车站编码', trigger: 'blur'},
                    //     {max: 20, message: '长度不能超过20个字符', trigger: 'blur'}
                    // ],
                    busStationLongitude: [
                        {required: true, message: '请选择车厂经度', trigger: 'change'}
                    ],
                    busStationLatitude: [
                        {required: true, message: '请选择车厂纬度', trigger: 'change'}
                    ],
                    busStationDeviation: [
                        {required: true, message: '请输入偏差（米）', trigger: 'blur'},
                        {type:'number', message: '偏差（米）必须为数字值'}
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
                    getInfoBusStationDetail(id).then((res) => {
                        console.log("单条详细:"+JSON.stringify(res.data.data.data));
                        if (res.data && res.data.code === 0) {
                            this.dataForm.areaId = res.data.data.data.areaId;
                            this.dataForm.busStationId = res.data.data.data.busStationId;
                            this.dataForm.busStationName = res.data.data.data.busStationName;
                            // this.dataForm.busStationCode = res.data.data.data.busStationCode;
                            this.dataForm.busStationLongitude = res.data.data.data.busStationLongitude;
                            this.dataForm.busStationLatitude = res.data.data.data.busStationLatitude;
                            this.dataForm.busStationDeviation = res.data.data.data.busStationDeviation;
                            this.dataForm.isDeleted = res.data.data.data.isDeleted;
                            this.dataForm.isEnabled = res.data.data.data.isEnabled;
                            this.dataForm.createdBy = res.data.data.data.createdBy;
                            this.dataForm.createdDate = res.data.data.data.createdDate;
                            this.dataForm.modifiedBy = res.data.data.data.modifiedBy;
                            this.dataForm.modifiedDate = res.data.data.data.modifiedDate;
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
                    console.log("打开新增弹窗");
                    this.title = '新增'
                    this.dataForm.isEnabled = 0;
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
                        if (this.title == '修改') {
                            console.log("修改提交:"+JSON.stringify(this.dataForm));
                            editInfoBusStation(this.dataForm).then((res) => {
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
                            saveInfoBusStation(this.dataForm).then((res) => {
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
            showMap() {
                this.mapVisible = true
                this.$nextTick(() => {
                    this.$refs.Map.init(this.dataForm.busStationLongitude, this.dataForm.busStationLatitude)
                });
            },
            getLocation(data) {
                this.dataForm.busStationLongitude = data.longitude
                this.dataForm.busStationLatitude = data.latitude
            }
        }
    }

</script>
<style>
</style>
