<template>
<el-dialog :title="!dataForm.positionId ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
        <el-form-item label="岗位名称" prop="name">
            <el-input v-model="dataForm.name" placeholder="岗位名称" maxlength="128"></el-input>
        </el-form-item>
        <el-form-item label="岗位描述" prop="description">
            <el-input v-model="dataForm.description" placeholder="岗位描述" maxlength="128"></el-input>
        </el-form-item>
        <el-form-item label="是否可用" >
            <el-switch
                    v-model="dataForm.isEnabled"
                    :active-value="1"
                    :inactive-value="0"
                    active-color="#13ce66"
                    inactive-color="#D3D3D3">
            </el-switch>
        </el-form-item>
        <el-form-item label="岗位权限" prop="systemAuth">
            <el-radio-group v-model="dataForm.systemAuth" @change="changeAuth">
                <el-radio label="manage">管理</el-radio>
                <el-radio label="branch">公司</el-radio>
<!--                <el-radio label="group">集团</el-radio>-->
<!--                <el-radio label="factory"></el-radio>-->
<!--                <el-radio label="public"></el-radio>-->
            </el-radio-group>
        </el-form-item>
        <el-form-item size="mini" label="授权" prop="positionIdList" v-if="dataForm.systemAuth !== 'factory' && dataForm.systemAuth !== 'public'">
            <el-tree
                    :check-strictly	 = true
                    @check-change = "changeTree"
                    :data="positionAuth"
                    show-checkbox
                    ref = "tree"
                    node-key="organizeId"
                   :default-checked-keys = "treeKeys"
                   :default-expanded-keys = "expandedKeys"
                    :expand-on-click-node="false">
            </el-tree>
        </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
        <el-button @click="visible = false">取消</el-button>
        <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
</el-dialog>
</template>

<script>
import { getStationDetail, getStationDetailCompanyByClique,addStationSave, addStationUpdate} from "@/api/station";
import {treeDataTranslate} from '@/util/index'


export default {
    data() {
        return {
            dataRule: {
                name: [{
                    required: true,
                    message: '岗位名称不能为空',
                    trigger: 'blur'
                }],
                description: [{
                    required: true,
                    message: '岗位描述不能为空',
                    trigger: 'blur'
                }],
                systemAuth:[{
                    validator: (rule, value, callback) => {
                        this.$refs.dataForm.validateField('positionIdList');
                        callback();
                    },
                    trigger: 'change'
                }],
                positionIdList: [{
                    validator:(rule, value, callback) => {
                        //分公司只能选一个
                        if((this.dataForm.systemAuth === 'branch' || this.dataForm.systemAuth === 'group') && this.dataForm.auth.length > 1){
                            callback(new Error('岗位为公司(branch)或集团(group)时只可指定一个授权！'));
                        }else {
                            callback();
                        }
                    },
                    message: '岗位为公司(branch)或集团(group)时只可指定一个授权!',
                    trigger: 'change'
                }]
            },
            treeKeys : [],
            expandedKeys : [],
            value : 1,
            visible: false,
            authList: [],
            dataForm: {
                auth : [],
                description : '',
                isEnabled : 1,
                name : '',
                systemAuth : 'manage',
                positionId : ''
            },
            positionAuth: [
                {
                    organizeId: "1212932193370767362",
                    label: "北京市",
                    linkType: 0,
                    pid: 0,
                    selected: 0,
                    children: [
                        {
                            organizeId: "1252515004414951426",
                            label: "北京市工商局",
                            linkType: 1,
                            pid: 1212932193370767400,
                            selected: 0,
                            children: [
                                {
                                    organizeId: "1212932358861225984",
                                    label: "东城区",
                                    linkType: 0,
                                    pid: 1252515004414951400,
                                    selected: 0,
                                    children: [
                                        {
                                            organizeId: "1252515136241926146",
                                            label: "东城区工商局",
                                            linkType: 1,
                                            pid: 1212932358861226000,
                                            selected: 1,
                                            children: [
                                                {
                                                    organizeId: "1253211968274169858",
                                                    label: "东直门工商所",
                                                    linkType: 2,
                                                    pid: 1252515136241926100,
                                                    selected: 0,
                                                    children: []
                                                },
                                                {
                                                    organizeId: "1253211968274169851",
                                                    label: "东直门工商所1",
                                                    linkType: 2,
                                                    pid: 1252515136241926100,
                                                    selected: 0,
                                                    children: []
                                                },
                                            ]
                                        }
                                    ]
                                }
                            ]
                        }
                    ]
                }
            ],
            filterMap : [],
        }
    },
    methods: {
        filterCheckKeys : function(data){
            var arr = [];
            for(let index in data){
                if(data[index].selected == 1){
                    arr.push(data[index].organizeId)
                }
            };
            return arr;
        },
        filterCheckeysList : function(data){
            var arr = [];
            for(let index in data){
                if(data[index].selected == 1){
                    arr.push({
                        "linkType" : data[index].linkType,
                        "organizeId" : data[index].organizeId
                    })
                }
            };
            return arr;
        },
        filterTree : function(data){
            var arr = [];
            for(let index in data){
                arr.push({
                    "linkType" : data[index].linkType,
                    "organizeId" : data[index].organizeId
                })
            };
            return arr;
        },
        changeTree : function(){
            var list = this.$refs.tree.getCheckedNodes();
            //console.log(this.$refs.tree.getCurrentNode())
            console.log("当前选中节点:")
            console.log(JSON.stringify(list));
            this.dataForm.auth = this.filterTree(list)
        },
        initForm : function(){
            this.dataForm.auth = [];
            this.dataForm.description = '';
            this.dataForm.isEnabled = 1;
            this.dataForm.name = '';
            this.dataForm.systemAuth = 'manage';
            this.treeKeys = [];
            this.positionAuth = [];
        },
        changeAuth(){
            this.getCompanyByClique();
        },
        getCompanyByClique(){
            let isClique;
            if(this.dataForm.systemAuth == "group"){
                isClique = 1;
            }else if(this.dataForm.systemAuth == "branch"){
                isClique = 0;
            }
            getStationDetailCompanyByClique({"isClique" : isClique}).then((res) => {
                if(res.data && res.data.code == 0){
                    let list = res.data.data.list;
                    for (let i = 0; i < list.length; i++) {
                        list[i].disabled = list[i].type === 0
                    }
                    this.positionAuth = treeDataTranslate(list, 'organizeId', 'pid');
                }
            });
        },
        init(id) {
            //this.filterTree(this.list)
            //console.log(this.filterMap)
            console.log(id)
            this.visible = true;
            this.initForm();
            const loading = this.$loading({
                lock: true,
                text: '加载中.......',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            });

            this.dataForm.positionId = id || '';
            if(this.dataForm.positionId){
                console.log('修改')
                getStationDetail({"positionId" : id}).then((res) => {
                    console.log(res.data)
                    if(res.data && res.data.code == 0){
                        loading.close();
                        let list = res.data.data.position.positionAuth;
                        for (let i = 0; i < list.length; i++) {
                            list[i].disabled = list[i].type === 0
                        }
                        this.dataForm.positionId = res.data.data.position.positionId;
                        this.dataForm.description = res.data.data.position.description;
                        this.dataForm.isEnabled = res.data.data.position.isEnabled;
                        this.dataForm.name = res.data.data.position.name;
                        this.dataForm.systemAuth = res.data.data.position.systemAuth;

                        this.dataForm.auth = this.filterCheckeysList(list);
                        console.log(this.dataForm.auth)
                        this.positionAuth = treeDataTranslate(list, 'organizeId', 'pid');
                        this.treeKeys = this.filterCheckKeys(list);
                        this.expandedKeys = this.treeKeys;
                        console.log(this.treeKeys)
                    }else{
                        loading.close();
                        this.$message({
                            message: res.data.message,
                            type: 'error',
                            offset: 300
                        })
                    }
                })
            }else{
                console.log('新增')
                let isClique;
                if(this.dataForm.systemAuth == "group"){
                    isClique = 1;
                }else if(this.dataForm.systemAuth == "branch"){
                    isClique = 0;
                }
                getStationDetailCompanyByClique({"isClique" : isClique}).then((res) => {
                    if(res.data && res.data.code == 0){
                        loading.close();
                        let list = res.data.data.list;
                        for (let i = 0; i < list.length; i++) {
                            list[i].disabled = list[i].type === 0
                        }
                        this.positionAuth = treeDataTranslate(list, 'organizeId', 'pid');
                    }
                });

                console.log(this.positionAuth);
            }
        },
        // 表单提交
        dataFormSubmit() {
            if(this.dataForm.systemAuth === 'factory' || this.dataForm.systemAuth === 'public'){
                this.dataForm.auth = []
            }
            this.$refs['dataForm'].validate((valid) => {
                if (valid) {
                    const loading = this.$loading({
                        lock: true,
                        text: '加载中.......',
                        spinner: 'el-icon-loading',
                        background: 'rgba(0, 0, 0, 0.7)'
                    });
                    console.log(JSON.stringify(this.dataForm));

                    if(this.dataForm.positionId){
                        loading.close();
                        console.log('修改')
                        addStationUpdate(this.dataForm).then((res) => {
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
                    }else{
                        loading.close();
                        console.log('新增');
                        //console.log(JSON.stringify(this.$refs.tree.getCheckedKeys()));
                        addStationSave(this.dataForm).then((res) => {
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

<style scoped>
    /deep/ .tree_width{
        width:100% !important;
    }
</style>
