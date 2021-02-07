<template>
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
                 label-width="80px">
            <el-form-item label="区域名称" prop="areaName">
                <el-input v-model="dataForm.areaName" placeholder="区域名称" maxlength="20"></el-input>
            </el-form-item>
            <el-form-item label="区域编码" prop="areaCode">
                <el-input v-model.number="dataForm.areaCode" placeholder="区域编码"></el-input>
            </el-form-item>
            <el-form-item label="区域简称" prop="areaAlias">
                <el-input v-model="dataForm.areaAlias" placeholder="区域简称" maxlength="10"></el-input>
            </el-form-item>
            <el-form-item label="上级区域" style="position: relative">
                <el-popover
                    ref="menuListPopover"
                    placement="bottom-start"
                    v-model="showFlag"
                    trigger="click">
                    <el-scrollbar style="height: 240px">
                        <el-tree
                            :data="rightList"
                            :props="rightListTreeProps"
                            node-key="areaId"
                            ref="rightListTree"
                            @current-change="rightListTreeCurrentChangeHandle"
                            :default-expand-all="false"
                            :highlight-current="true"
                            :expand-on-click-node="false">
                        </el-tree>
                    </el-scrollbar>
                </el-popover>
                <el-input
                    v-model="inputName"
                    v-popover:menuListPopover
                    :readonly="true"
                    placeholder="点击选择上级区域"
                    clearable
                    class="menu-list__input"></el-input>
                <span style="position: absolute;right:8px;top: 0px;z-index: 9999;cursor: pointer;" @click="clearInput">清除</span>
            </el-form-item>
            <el-form-item label="级别" prop="areaLevel">
                <el-input v-model="dataForm.areaLevel" placeholder="级别" maxlength="8 " disabled></el-input>
            </el-form-item>
            <el-form-item label="排序">
                <el-input-number v-model="dataForm.areaSort" :max="32767" :min="1"
                                 :step-strictly="true"></el-input-number>
            </el-form-item>
            <el-form-item label="是否启用">
                <el-switch v-model="value" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
            </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" :disabled="nextStepDisable" @click="dataFormSubmit()">确定</el-button>
    </span>
    </el-dialog>
</template>
<script>
    import {isPositiveInteger} from '@/util/validate'
    import {treeDataTranslate1} from '@/util/index'
    import {getAreaDetail, editArea, saveArea} from '@/api/parameter'
    import {getRegionalS} from '@/api/selectionApi'

    export default {

        data() {
            var validateNumber = (rule, value, callback) => {
                if (value) {
                    if (isPositiveInteger(value)) {
                        if (value > 2147483647) {
                            return callback(new Error('最大不能超过2147483647'));
                        }
                    } else {
                        return callback(new Error('请输入正整数'));
                    }
                }
                callback();
            }

            return {
                title: '',
                flag: true,
                visible: false,
                iconList: [],
                popverVisible: false,
                bottomVisible: false,
                selectList: [],
                showFlag: false,
                inputName: '',
                dataForm: {
                    areaId: '',
                    areaName: '',
                    parentAreaId: '0',
                    parentAreaName: '',
                    areaCode: '',
                    areaAlias: '',
                    areaSort: '',
                    areaLevel: 1,
                    isEnabled: 0,
                },
                value: false,
                dataRule: {
                    areaName: [
                        {required: true, message: '请输入区域名称', trigger: 'blur'},
                        {max: 20, message: '长度不能超过20个字符', trigger: 'blur'}
                    ],
                    areaCode: [
                        {validator: validateNumber, trigger: 'blur'}
                    ],
                    areaAlias: [
                        {max: 10, message: '长度不能超过10个字符', trigger: 'blur'}
                    ]
                },
                rightList: [],
                rightListTreeProps: {
                    label: 'areaName',
                    children: 'children'
                },
                nextStepDisable:false
            }
        },
        methods: {
            deleteFn: function (data, id) {
                for (var i = data.length - 1; i >= 0; i--) {
                    if (data[i].areaId == id && data[i].areaId) {
                        data.splice(i, 1);
                        break;
                    }
                    if (data[i] !== 'undefined') {
                        if (data[i].children) {
                            this.deleteFn(data[i].children, id)
                        }
                    }
                }
                return data;
            },
            clearInput: function () {
                this.dataForm.areaLevel = 1;
                this.dataForm.parentAreaId = '0';
                this.inputName = '';
            },
            init(id) {
                this.nextStepDisable = false
                this.clearInput();
                this.dataForm.areaSort = 1
                const loading = this.$loading({
                    lock: true,
                    text: '加载中.......',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });
                this.visible = true
                this.value = false;
                this.$nextTick(() => {
                    if (this.$refs['dataForm'] !== undefined) {
                        this.$refs['dataForm'].resetFields()
                    }
                })
                if (id) {
                    console.log("修改ID:"+id);
                    getAreaDetail(id).then((res) => {
                        console.log("单条详细:"+JSON.stringify(res.data.data.data));
                        loading.close();
                        this.dataForm.areaId = res.data.data.data.areaId;
                        this.dataForm.areaName = res.data.data.data.areaName;
                        this.dataForm.parentAreaName = res.data.data.data.parentAreaName;
                        this.dataForm.parentAreaId = res.data.data.data.parentAreaId;
                        this.dataForm.areaCode = res.data.data.data.areaCode;
                        this.dataForm.areaAlias = res.data.data.data.areaAlias;
                        this.dataForm.areaSort = res.data.data.data.areaSort;
                        this.dataForm.areaLevel = res.data.data.data.areaLevel;
                        this.inputName = this.dataForm.parentAreaName;
                        if (res.data.data.data.isEnabled == 1) {
                            this.value = true
                        } else {
                            this.value = false
                        }
                        this.rightListTreeSetCurrentNode()
                    }).then(() => {
                        getRegionalS().then((res) => {
                            loading.close();
                            if (res.data && res.data.code == 0) {
                                this.selectList = []
                                this.rightList = treeDataTranslate1(res.data.data.selection);
                                this.rightList = this.deleteFn(this.rightList, this.dataForm.areaId)
                                this.rightListTreeSetCurrentNode()
                            }
                        })
                    })
                    this.title = '修改'
                } else {
                    console.log("打开新增弹窗");
                    this.dataForm.parentAreaId = '0';
                    this.dataForm.areaLevel = 1;
                    getRegionalS().then((res) => {
                        loading.close();
                        if (res.data && res.data.code == 0) {
                            this.selectList = []
                            this.rightList = treeDataTranslate1(res.data.data.selection)
                        }
                    })
                    this.title = '新增'
                    this.value = true;
                }
            },
            //菜单选择器选择
            selectChange(data) {
                console.log(data);
                this.dataForm.parentAreaId = data.areaId;
                this.dataForm.parentAreaName = data.areaName;
            },
            // 菜单树选中
            rightListTreeCurrentChangeHandle(data, node) {
                console.log("选择节点:"+JSON.stringify(data));

                if (this.title == '新增') {
                    this.dataForm.areaId = '';
                    this.dataForm.parentAreaId = data.areaId;
                    this.dataForm.areaLevel = data.areaLevel + 1;
                    this.inputName = data.areaName;
                }
                if (this.title == '修改') {
                    this.dataForm.parentAreaId = data.areaId;
                    this.dataForm.areaLevel = data.areaLevel + 1;
                    this.inputName = data.areaName;
                }

                this.popverVisible = false;
                this.showFlag = false;
            },
            // 菜单树设置当前选中节点
            rightListTreeSetCurrentNode() {
                if (this.dataForm.parentAreaId !== '0') {
                    this.$refs.rightListTree.setCurrentKey(this.dataForm.parentAreaId)
                    this.dataForm.parentAreaName = (this.$refs.rightListTree.getCurrentNode() || {})['areaName']
                }

            },
            // 表单提交
            dataFormSubmit() {
                this.nextStepDisable = true
                if (this.value == true) {
                    this.dataForm.isEnabled = 1;
                } else {
                    this.dataForm.isEnabled = 0;
                }
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        const loading = this.$loading({
                            lock: true,
                            text: '加载中.......',
                            spinner: 'el-icon-loading',
                            background: 'rgba(0, 0, 0, 0.7)'
                        });
                        if (this.title == '修改') {
                            console.log("修改提交:"+JSON.stringify(this.dataForm));
                            editArea(this.dataForm).then((res) => {
                                loading.close();
                                if (res.data && res.data.code === 0) {
                                    this.$message({
                                        message: '操作成功',
                                        type: 'success',
                                        offset: 300,
                                        duration: 1500,
                                        onClose: () => {
                                            this.visible = false;
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
                            //return;
                            saveArea(this.dataForm).then((res) => {
                                loading.close();
                                if (res.data && res.data.code === 0) {
                                    this.$message({
                                        message: '操作成功',
                                        type: 'success',
                                        offset: 300,
                                        duration: 1500,
                                        onClose: () => {
                                            this.visible = false;
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
                    }else {
                        this.nextStepDisable = false
                    }
                })
            }
        }
    }

</script>
<style scoped>
    .mod-right__icon-popover {
        max-width: 613px;
    }

    .el-button {
        margin: 5px;
    }

    .el-button + .el-button {
        margin-left: 5px;
    }
</style>

