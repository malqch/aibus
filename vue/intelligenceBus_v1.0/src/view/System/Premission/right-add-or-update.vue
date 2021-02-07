<template>
    <el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
                 label-width="80px">
            <el-form-item label="类型" prop="type">
                <el-radio-group v-model="dataForm.type">
                    <el-radio v-for="(type, index) in dataForm.typeList" :label="index" :key="index">{{ type }}</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item :label="dataForm.typeList[dataForm.type] + '名称'" prop="name">
                <el-input v-model="dataForm.name" :placeholder="dataForm.typeList[dataForm.type] + '名称'"
                          maxlength="8 "></el-input>
            </el-form-item>
            <el-form-item v-if="dataForm.type === 1" label="上级菜单" prop="parentName">
                <el-select v-model="dataForm.parentName" @change="selectChange" placeholder="请选择上级菜单">
                    <el-option v-for="item in selectList"
                               :key="item.rightId"
                               :label="item.name"
                               :value="item"></el-option>

                </el-select>
                <!--
                 　　多级目录

                 <el-popover ref="rightListPopover" placement="bottom-start" trigger="click"　 v-model="popverVisible">-->
                <!--          <el-tree :data="rightList" :props="rightListTreeProps" trigger="click" node-key="rightId" ref="rightListTree" @current-change="rightListTreeCurrentChangeHandle" :default-expand-all="true" :highlight-current="true" :expand-on-click-node="false">-->
                <!--          </el-tree>-->
                <!--        </el-popover>-->
                <!--        <el-input v-model="dataForm.parentName" v-popover:rightListPopover :readonly="true" placeholder="点击选择上级菜单"></el-input>-->
            </el-form-item>
            <el-form-item v-if="dataForm.type === 0" label="上级目录" prop="parentName">
                <el-input v-model="dataForm.parentName" 　 :disabled="true"></el-input>
            </el-form-item>
            <el-form-item prop="endpointPath" v-if="dataForm.type === 1" label="菜单路由">
                <el-input v-model="dataForm.endpointPath" placeholder="菜单路由,必须以/开头,如：/Host" maxlength="128"></el-input>
            </el-form-item>
            <el-form-item v-if="dataForm.type !== 2" label="排序号" prop="sort">
                <el-input-number v-model="dataForm.sort" controls-position="right" :min="0" label="排序号"
                                 maxlength="2"></el-input-number>
            </el-form-item>
            <el-form-item v-if="dataForm.type !== 2" label="菜单图标" prop="iconCss">
                <el-row>
                    <el-col :span="24">
                        <el-popover ref="iconListPopover" placement="bottom-start" trigger="click"
                                    popper-class="mod-right__icon-popover" v-model="bottomVisible" width="475">
                            <div>
                                <el-button v-for="(item, index) in dataForm.iconList" :key="index" @click="iconActiveHandle(item)"
                                           :class="{ 'is-active': item === dataForm.iconCss }" style="margin: 5px !important;">
                                    <i :class="'fa fa-'+item"></i>
                                </el-button>
                            </div>
                        </el-popover>
                        <el-input v-model="dataForm.iconCss" v-popover:iconListPopover :readonly="true" placeholder="菜单图标名称"
                                  class="icon-list__input"></el-input>
                    </el-col>
                </el-row>
            </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
    </el-dialog>
</template>
<script>
    import {treeDataTranslate} from '@/util/index'
    import {getRightListByUser, getRightByRightId, saveOrUpdateRight, isPathExist} from '@/api/system'

    export default {

        data() {
            var validatePath = (rule, value, callback) => {
                isPathExist({
                    rightId: this.dataForm.id,
                    path: value
                }).then(({
                             data
                         }) => {
                    if (data && data.code === 0) {
                        this.flag = data.data.isMultiple
                    }
                }).then(() => {
                    console.log('tag', this.flag)
                    if (!this.flag) {
                        callback(new Error('该路径已经被使用'))
                    } else {
                        callback()
                    }
                });

            }
            var validateEndpointPath = (rule, value, callback) => {
                if (this.dataForm.type === 1) {
                    if (!/^\/.*/.test(value)) {
                        callback(new Error('菜单URL必须以/开头'))
                    } else {
                        callback()
                    }

                } else {
                    callback()
                }
            }
            var isSort = (rule, value, callback) => {
                if (value < 0 || value > 99) {
                    callback(new Error("请输入两位数以内正整数"))
                } else {
                    callback();
                }
            }
            return {
                flag: true,
                visible: false,
                iconList: [],
                popverVisible: false,
                bottomVisible: false,
                selectList: [],
                dataForm: {
                    id: 0,
                    rightId: 0,
                    type: 1,
                    typeList: ['目录', '菜单'],
                    name: '',
                    parentId: 0,
                    parentName: '',
                    endpointPath: '',
                    pathUrl: '',
                    perms: '',
                    sort: 0,
                    iconCss: '',
                    iconList: ['file-audio-o', 'file-code-o', 'cogs', 'address-card-o', 'address-card', 'bars', 'mortar-board', 'folder', 'file-word-o', 'sort-amount-asc', 'file-archive-o', 'tv', 'sitemap', 'cubes', 'newspaper-o', 'file-word-o', 'user-circle', 'server', 'support', 'user-circle', 'user-o', 'briefcase', 'bullseye', 'cubes', 'globe', 'home', 'laptop', 'telegram', 'diamond', 'cube', 'file-archive-o', 'futbol-o', 'inbox', 'institution', 'paper-plane', 'server', 'tachometer', 'user-secret', 'users', 'video-camera', 'diamond', 'map-marker', ''                                                                                                                                                       ]
                },
                dataRule: {
                    name: [
                        {required: true, message: '菜单或目录名称不能为空', trigger: 'blur'}
                    ],
                    parentName: [
                        {required: true, message: '上级菜单不能为空', trigger: 'change'}
                    ],
                    sort: [
                        {validator: isSort}
                    ],
                    endpointPath: [
                        {required: true, message: '菜单URL不能为空', trigger: 'blur'},
                        {validator: validateEndpointPath, trigger: 'blur'},
                        {
                            validator: validatePath, trigger: 'blur'
                        }
                    ]
                },
                rightList: [],
                rightListTreeProps: {
                    label: 'name',
                    children: 'children'
                }
            }
        },
        methods: {
            init(id) {
                const loading = this.$loading({
                    lock: true,
                    text: '加载中.......',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });
                this.dataForm.id = id || 0
                getRightListByUser().then(({data}) => {
                    loading.close();
                    if(data&&data.code==0){
                        this.selectList=[]
                        this.rightList = treeDataTranslate(data.data.rightList, 'rightId')
                        for (var i = 0; i < data.data.rightList.length; i++) {
                            if (data.data.rightList[i].type == 0) {
                                this.selectList.push(data.data.rightList[i])
                            }
                        }
                    }

                }).then(() => {
                    this.visible = true

                    this.$nextTick(() => {
                        if(this.$refs['dataForm']!==undefined){
                            this.$refs['dataForm'].resetFields()
                        }

                    })
                }).then(() => {
                    if (!this.dataForm.id) {
                        // 新增
                        // this.rightListTreeSetCurrentNode()
                        loading.close();
                    } else {
                        // 修改
                        getRightByRightId(id).then(({data}) => {
                            loading.close();
                            this.dataForm.rightId = data.data.right.rightId
                            this.dataForm.type = data.data.right.type
                            this.dataForm.name = data.data.right.name
                            this.dataForm.parentId = data.data.right.parentId
                            this.dataForm.endpointPath = data.data.right.endpointPath
                            this.dataForm.perms = data.data.right.perms
                            this.dataForm.sort = data.data.right.sort
                            this.dataForm.iconCss = data.data.right.iconCss
                            for (var i = 0; i < this.selectList.length; i++) {
                                if(this.selectList[i].rightId==data.data.right.parentId){
                                    this.dataForm.parentName=this.selectList[i].name
                                }
                            }

                        })

                    }
                })
            },
            //菜单选择器选择
            selectChange(data) {
                this.dataForm.parentId = data.rightId
                this.dataForm.parentName = data.name
            },
            // 菜单树选中
            rightListTreeCurrentChangeHandle(data, node) {
                this.dataForm.parentId = data.rightId
                this.dataForm.parentName = data.name
                this.popverVisible = false
            },
            // 菜单树设置当前选中节点
            rightListTreeSetCurrentNode() {
                this.$refs.rightListTree.setCurrentKey(this.dataForm.parentId)
                this.dataForm.parentName = (this.$refs.rightListTree.getCurrentNode() || {})['name']
            },
            // 图标选中
            iconActiveHandle(iconName) {
                this.dataForm.iconCss = iconName
                this.bottomVisible = false
            },
            // 表单提交
            dataFormSubmit() {

                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        const loading = this.$loading({
                            lock: true,
                            text: '加载中.......',
                            spinner: 'el-icon-loading',
                            background: 'rgba(0, 0, 0, 0.7)'
                        });
                        this.dataForm.pathUrl = !this.dataForm.id ? 'save' : 'update'
                        saveOrUpdateRight(this.dataForm).then((resp) => {
                            loading.close()
                            if (resp.data && resp.data.code === 0) {
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
                                    message: resp.data.message,
                                    type: 'error',
                                    offset: 300,
                                    duration: 1500
                                })
                            }
                        })
                    }
                })
            }
        },
        watch: {
            'dataForm.type': function (newVal, OldVal) {
                if (newVal == 0) {
                    this.dataForm.parentName = '一级目录'
                    this.dataForm.parentId = 0
                } else {
                    this.dataForm.parentName = this.dataForm.parentName
                }
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

