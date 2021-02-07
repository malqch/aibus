<template>
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" style="color:#fff">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
                 label-width="130px">
            <el-form-item label="用户姓名" prop="userName">
                <el-input v-model="dataForm.userName" placeholder="用户姓名" maxlength="128"></el-input>
            </el-form-item>
            <el-form-item label="登录名" prop="loginName">
                <el-input v-model="dataForm.loginName" placeholder="登录名" maxlength="128" :disabled="userFlag"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password" :class="{ 'is-required': !dataForm.id }">
                <el-input v-model="dataForm.password" type="password" placeholder="密码" maxlength="32"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="comfirmPassword" :class="{ 'is-required': !dataForm.id }">
                <el-input v-model="dataForm.comfirmPassword" type="password" placeholder="确认密码"
                          maxlength="32"></el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
                <el-input v-model="dataForm.email" placeholder="邮箱" maxlength="64"></el-input>
            </el-form-item>
            <el-form-item label="手机号" prop="mobile">
                <el-input v-model="dataForm.mobile" placeholder="手机号" maxlength="11"></el-input>
            </el-form-item>
            <el-form-item label="角色" size="mini" >
                <el-checkbox-group v-model="dataForm.roleIdList" >
                    <el-checkbox :label="item.roleId" name="type" v-for="(item, index) in roleList" :key="item.roleId">{{item.roleName}}</el-checkbox>
                </el-checkbox-group>
            </el-form-item>
            <el-form-item label="岗位" size="mini" >
                <el-radio-group v-model="positionId" @change="change" v-if="false">
                    <el-radio :label="item.positionId"  v-for="(item, index) in positionsList" :key="item.positionId">{{item.positionName}}</el-radio>
                </el-radio-group>
                <el-select
                        style="margin-bottom: 4px;"
                        @change="change"
                        v-model="positionId"
                        filterable
                        allow-create
                        default-first-option
                        placeholder="请选择岗位">
                    <el-option
                            v-for="item in positionsList"
                            :key="item.positionId"
                            :label="item.positionName"
                            :value="item.positionId">
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
    import { addUserUpdate, addUserSave, getUserDetail } from "@/api/user";
    import {isEmail, isMobile} from '@/util/validate'


    export default {
        data() {
            var validatePassword = (rule, value, callback) => {
                //编辑用户时不校验密码
                if(this.editOrAdd == 'edit' && this.dataForm.password === '' && this.dataForm.comfirmPassword ==='') callback();

                var reg=/^(?=.*\d)(?=.*[a-zA-Z])(?=.*[~!@#$.%^&*])[\da-zA-Z~!@#$%.^&*]{8,}$/
                if (!this.dataForm.id &&!reg.test(value)){
                    callback(new Error('密码至少8位且必须包含数字、大小英文字母、特殊符号'))
                }else{
                    callback()
                }
                if (!this.dataForm.id && !/\S/.test(value)) {
                    callback(new Error('密码不能为空'))
                }
                callback()
            }
            var validateComfirmPassword = (rule, value, callback) => {
                //编辑用户时不校验密码
                if(this.editOrAdd == 'edit'  && this.dataForm.password === '' && this.dataForm.comfirmPassword ==='') callback();

                if (!this.dataForm.id && !/\S/.test(value)) {
                    callback(new Error('确认密码不能为空'))
                } else if (this.dataForm.password != value) {
                    callback(new Error('确认密码与密码输入不一致'))
                } else {
                    callback()
                }
            }
            var validateEmail = (rule, value, callback) => {
                if (!isEmail(value)) {
                    callback(new Error('邮箱格式错误'))
                } else {
                    callback()
                }
            }
            var validateMobile = (rule, value, callback) => {
                if (!isMobile(value)) {
                    callback(new Error('手机号格式错误'))
                } else {
                    callback()
                }
            }
            return {
                optionsa: [{
                    value: 'HTML',
                    label: 'HTML'
                }, {
                    value: 'CSS',
                    label: 'CSS'
                }, {
                    value: 'JavaScript',
                    label: 'JavaScript'
                }],
                valuea: [],
                type : [],
                visible: false,
                title: '新增',
                editOrAdd: 'edit',
                userFlag : false,
                dataForm: {
                    userName: '',
                    loginName: '',
                    password: '',
                    comfirmPassword: '',
                    email: '',
                    mobile: '',
                    roleIdList: [],
                    positionIdList: [],
                    userId: ''
                },
                roleList : [],
                positionsList : [],
                positionsObjectList : [],
                positionId : '',


                dataRule: {
                    userName: [
                        {required: true, message: '用户姓名不能为空', trigger: 'blur'}
                    ],
                    loginName: [
                        {required: true, message: '登录名不能为空', trigger: 'blur'}
                    ],
                    password: [
                        {validator: validatePassword, trigger: 'blur'}
                    ],
                    comfirmPassword: [
                        {validator: validateComfirmPassword, trigger: 'blur'}
                    ],
                    email: [
                        {required: true, message: '邮箱不能为空', trigger: 'blur'},
                        {validator: validateEmail, trigger: 'blur'}
                    ],
                    mobile: [
                        {required: true, message: '手机号不能为空', trigger: 'blur'},
                        {validator: validateMobile, trigger: 'blur'}
                    ],
                },
                options: [],
                value: [
                    {
                        icabId : "1212932358861225985",
                        label : "诸葛胡1",
                        positionAuthId : "1266545195608965122",
                        positionId : "1266544481147027458",
                        selected : 0,
                        value : "1253167328049233921"
                    }
                ],
                icabIds:{},
                selectFlag : false,
                radioList : [
                    {
                        isSelfonly : 1,
                        busiObjectId : '',
                        label : '济南工商所2',
                        positionAuthId : "1267283659501600770",

                        options : [
                            {
                                label : "乌昂旺",
                                selected : 0,
                                value : "1267285912736235521",
                                isSelfonly : 1,
                            }
                        ]
                    },
                    {
                        isSelfonly : 0,
                        busiObjectId : '',
                        label : '济南工商所1',
                        positionAuthId : "1267283659560321026",
                        options : [
                            {
                                label : "付误服",
                                selected : 0,
                                value : "1235465958445809666",
                                isSelfonly : 0,
                            },
                            {
                                label : "木村本一",
                                selected : 0,
                                value : "1265201210713964545",
                                isSelfonly : 0,
                            },
                            {
                                label : "齐昂抢",
                                selected : 0,
                                value : "1267368327773159425",
                                isSelfonly : 0,
                            }
                        ]
                    }
                ],

            }
        },
        methods: {
            filterPositionIdList : function( data){
                console.log(data)
                var arr = [];
                var posList = [];
                var positionid = '';
                for(let index in data){
                    if(data[index].selected == 1){
                        console.log('ok')
                        arr = data[index].busiObjectId;
                        positionid = data[index].positionId;
                        posList.push({
                            positionId : positionid,
                            positionAuthId : '',
                            busiObjectId : ''
                        })
                    }
                }
                if(arr.length == 0){
                    posList.push({
                        positionId : positionid,
                        positionAuthId : '',
                        busiObjectId : ''
                    })
                };
                console.log()
                if(arr.length > 0){
                    for(var i=0;i<arr.length;i++){
                        for(var j=0;j<arr[i].options.length;j++){
                            if(arr[i].options[j].selected == 1){
                                console.log('ok')
                                posList.push({
                                    positionId : arr[i].options[j].positionId,
                                    positionAuthId : arr[i].options[j].positionAuthId,
                                    busiObjectId : arr[i].options[j].value
                                })
                                console.log(posList)
                            }
                        }
                    }
                }
                if(posList.length > 1){
                    posList.splice(0,1)

                }
                return posList;
            },
            getPositionIsSelfonly : function(id, data){
                console.log(id)
                console.log(data)
                for(let index in data){
                    if(data[index].positionId == id){
                        return data[index].isSelfonly;
                    }
                }
            },
            filterRoleActive : function(data){
                var arr = [];
                for(let index in data){
                    if(data[index].hasRole == 1){
                        arr.push(data[index].roleId)
                    }
                };
                return arr;
            },
            clearForm  : function(){
                this.dataForm.userName = '';
                this.dataForm.loginName = '';
                this.dataForm.password = '';
                this.dataForm.comfirmPassword = '';
                this.dataForm.email = '';
                this.dataForm.mobile = '';
                this.dataForm.roleIdList = [];
                this.dataForm.positionIdList = [];
                this.roleList = [];
                this.positionsList = [];
                this.options = [];
                this.value = [];
                this.positionId = '';

            },
            test(e){
                var iMap= new Map();
                for(var i=0;i<e.length;i++){
                    var ev=e[i];
                    var id=ev.icabId;
                    var val= this.icabIds.get(id);
                    if(val==undefined){
                        this.icabIds.set(id,ev);
                    }else {
                        if(val.icabId == id){
                            if(val.value!=ev.value){
                                e.splice(iMap.get(id),1);
                                this.icabIds.set(id,ev);
                            }else{
                                iMap.set(id,i)
                            }
                        }
                    }
                }
                if(e.length<this.icabIds.size){
                    this.icabIds.clear();
                    for(var i=0;i<e.length;i++){
                        this.icabIds.set(e[i].icabId,e[i]);
                    }
                }
                this.filterValue(this.value)
            },
            filterValue : function(data){
                this.dataForm.positionIdList.splice(0,1);
                for(let index in data){
                    this.dataForm.positionIdList.push({
                        positionId : data[index].positionId,
                        positionAuthId : data[index].positionAuthId,
                        busiObjectId : data[index].value

                    })
                };
                console.log(this.dataForm.positionIdList)
            },
            change : function(v){
                var arr = [];
                arr.push({positionId:v, positionAuthId : '', busiObjectId : ''})
                this.dataForm.positionIdList = arr;
                this.options = this.filterPositionList(this.positionId, this.positionsList);
                this.value = this.filterPositionListActive(this.options)
                if(this.getPositionIsSelfonly(v, this.positionsList) == 0){
                    this.selectFlag = true;
                }else{
                    this.selectFlag = false;
                }
            },
            filterPositionId : function(data){
                var positionId = '';
                for(let index in data){
                    if(data[index].selected == 1){
                        positionId = data[index].positionId;
                    }
                }
                return positionId;
            },
            filterPositionList : function(positionId, data){
                var arr = [];
                for(let index in data){
                    if(data[index].positionId == positionId){
                        arr = data[index].busiObjectId
                    }
                }
                return arr;
            },
            filterPositionListActive : function(data){
                var arr = [];
                for(var i=0;i<data.length;i++){
                    for(var j=0;j<data[i].options.length;j++){
                        if(data[i].options[j].selected == 1){
                            arr.push(data[i].options[j]);
                        }
                    }
                }
                return arr;
            },
            init(id) {
                this.clearForm();
                const loading = this.$loading({
                    lock: true,
                    text: '加载中.......',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });
                this.visible = true;
                if (id) {
                    this.dataForm.userId = id
                    this.title = '修改'
                    this.editOrAdd = 'edit'
                    this.userFlag = true;
                    this.$nextTick(() => {
                        if (this.$refs['dataForm'] !== undefined) {
                            this.$refs['dataForm'].resetFields()
                        }

                    })
                    getUserDetail({
                        userId : id
                    }).then(res => {
                        if (res.data && res.data.code == 0) {
                            loading.close();
                            console.log(res.data.data);
                            this.dataForm.userName = res.data.data.user.userName;
                            this.dataForm.loginName = res.data.data.user.loginName;
                            this.dataForm.email = res.data.data.user.email;
                            this.dataForm.mobile = res.data.data.user.mobile;
                            this.roleList = res.data.data.user.roles;
                            this.dataForm.roleIdList = this.filterRoleActive(this.roleList);
                            this.positionsList = res.data.data.user.positions;
                            this.positionId = this.filterPositionId(this.positionsList);

                            this.options = this.filterPositionList(this.positionId, this.positionsList);
                            this.value = this.filterPositionListActive(this.options)

                            this.dataForm.positionIdList = this.filterPositionIdList(this.positionsList)
                            console.log(this.dataForm.positionIdList);

                        }
                    })

                } else {
                    this.dataForm.id = ''
                    this.title = '新增';
                    this.editOrAdd = 'add'
                    this.userFlag = false;
                    this.$nextTick(() => {
                        if (this.$refs['dataForm'] !== undefined) {
                            this.$refs['dataForm'].resetFields()
                        }
                    })
                    getUserDetail().then(res => {
                        if (res.data && res.data.code == 0) {
                            loading.close();
                            console.log(res.data.data);
                            this.roleList = res.data.data.user.roles;
                            this.positionsList = res.data.data.user.positions;
                            this.positionId = this.filterPositionId(this.positionsList);
                            this.options = this.filterPositionList(this.positionId, this.positionsList);
                            this.value = this.filterPositionListActive(this.options)

                        }
                    })
                    loading.close();
                }
                //获取角色列表
            },
            // 表单提交
            dataFormSubmit() {
                console.log(this.dataForm);
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        const loading = this.$loading({
                            lock: true,
                            text: '加载中.......',
                            spinner: 'el-icon-loading',
                            background: 'rgba(0, 0, 0, 0.7)'
                        });
                        if (this.title == '修改') {
                            console.log(this.selectFlag);
                            console.log(this.dataForm.positionIdList)
                            var isSelfonly = this.getPositionIsSelfonly(this.positionId, this.positionsList)
                            if(this.dataForm.positionIdList.length == 1 && this.dataForm.positionIdList[0].busiObjectId == '' &&  isSelfonly ==1){
                                this.$message({
                                    message: '请选择工商员工下拉数据',
                                    type: 'error',
                                    offset: 300
                                })
                                loading.close();
                                return;
                            }
                            addUserUpdate(this.dataForm).then((res) => {
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
                            addUserSave(this.dataForm).then((res) => {
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
        },
        mounted : function(){
            this.icabIds=new Map();
        }
    }

</script>
<style>
</style>
