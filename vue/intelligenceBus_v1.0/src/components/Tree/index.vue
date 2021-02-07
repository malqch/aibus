<!--
 @Author: songwenxin
 @Filename: index.vue
 @ProjectName: fs-manage-view
 @Mail: songwenxin666@sina.com
 @Date: 2019-12-18 23:18
 @file-description:
-->


<template>
    <el-form-item label="事件类型" prop="parentTypeName" style="position: relative">
        <el-popover
            ref="menuListPopover"
            placement="bottom-start"
            v-model = "showFlag"
            width="404"
            trigger="click">
            <el-tree
                :data="rightList"
                :props="rightListTreeProps"
                node-key="eventTypeId"
                ref="rightListTree"
                @current-change="rightListTreeCurrentChangeHandle"
                :default-expand-all="false"
                :highlight-current="true"
                :expand-on-click-node="false">
            </el-tree>
        </el-popover>
        <el-input
            v-model="dataForm.eventTypeName"
            v-popover:menuListPopover
            :readonly="true"
            placeholder="选择事件类型"
            clearable
            class="menu-list__input"></el-input>
    </el-form-item>
</template>

<script>
    export default {
        name: "index",
        props : ['param'],
        data(){
            return{
                showFlag : true,
                diagLoading:true,
                rightListTreeProps: {
                    label: 'eventTypeName',
                    children: 'children'
                },
            }
        },
        methods : {
            rightListTreeCurrentChangeHandle(data, node) {
                this.dataForm.eventTypeName = data.eventTypeName;
                this.dataForm.eventTypeId = data.eventTypeId;

                this.popverVisible = false;
                this.showFlag = false;
            },
            // 菜单树设置当前选中节点
            rightListTreeSetCurrentNode() {
                if(this.dataForm.parentTypeId !== '0'){
                    this.$refs.rightListTree.setCurrentKey(this.dataForm.parentTypeId)
                    this.dataForm.parentTypeName = (this.$refs.rightListTree.getCurrentNode() || {})['eventTypeName']
                }

            },
        }
    }
</script>

<style scoped>

</style>
