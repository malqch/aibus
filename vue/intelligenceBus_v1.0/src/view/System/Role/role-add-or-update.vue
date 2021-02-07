<template>
<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
        <el-form-item label="角色名称" prop="name">
            <el-input v-model="dataForm.name" placeholder="角色名称" maxlength="9"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
            <el-input v-model="dataForm.description" placeholder="描述" maxlength="128"></el-input>
        </el-form-item>
        <el-form-item size="mini" label="授权">
            <el-tree :data="rightIdList" :props="rightListTreeProps" node-key="rightId" ref="rightListTree" :default-expand-all="false" show-checkbox>
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
import {
    getRightList,
    getRoleByRoleId,
    saveOrUpdateRole
} from '@/api/system'
import {
    treeDataTranslate
} from '@/util/index'
export default {
    data() {
        return {
            //主要是为了解决当目录只有一个选中的时候，但是页面确实全部选择的状态
            checkHeader: [],
            visible: false,
            rightIdList: [],
            rightListTreeProps: {
                label: 'name',
                children: 'children'
            },
            dataForm: {
                id: 0,
                name: '',
                description: '',
                rightIdList: [],
                url: '',
                createUserId: 0,
                roleId: 0
            },
            dataRule: {
                name: [{
                    required: true,
                    message: '角色名称不能为空',
                    trigger: 'blur'
                }]

            },
            tempKey: -666666 
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
            this.dataForm.url = !this.dataForm.id ? 'save' : 'update'
            getRightList().then((resp) => {
              loading.close()
                for (var i = 0; i < resp.data.data.rightList.length; i++) {
                    if (resp.data.data.rightList[i].type == 1) {
                        this.checkHeader.push(resp.data.data.rightList[i].parentId)
                    }

                }
                this.rightIdList = treeDataTranslate(resp.data.data.rightList, 'rightId')
            }).then(() => {
                this.visible = true
                this.$nextTick(() => {
                  if(this.$refs['dataForm']!==undefined){
                    this.$refs['dataForm'].resetFields()
                    this.$refs.rightListTree.setCheckedKeys([])
                  }
                })
            }).then(() => {

                if (this.dataForm.id) {
                    getRoleByRoleId(id).then(({
                        data
                    }) => {
                        if (data && data.code === 0) {
                            this.dataForm.name = data.data.role.name
                            this.dataForm.description = data.data.role.description
                            this.dataForm.createUserId = data.data.role.createUserId
                            this.dataForm.roleId = data.data.role.roleId
                            for (var i = 0; i < this.checkHeader.length; i++) {
                                if (data.data.role.rightIdList.indexOf(this.checkHeader[i]) != -1) {

                                    var idx = data.data.role.rightIdList.indexOf(this.checkHeader[i])
                                    if (idx !== -1) {
                                        data.data.role.rightIdList.splice(idx, 1)
                                    }
                                }
                            }

                            this.$refs.rightListTree.setCheckedKeys(data.data.role.rightIdList)
                        }
                    })
                }
                loading.close();
            })
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
                    this.dataForm.rightIdList = [].concat(this.$refs.rightListTree.getCheckedKeys(),
                        this.$refs.rightListTree.getHalfCheckedKeys())
                    saveOrUpdateRole(this.dataForm).then((resp) => {
                        loading.close();
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
                                offset: 300
                            })
                        }
                    })
                }
            })
        }
    }
}
</script>
