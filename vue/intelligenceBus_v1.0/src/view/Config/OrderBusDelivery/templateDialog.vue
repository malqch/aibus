<template>
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" style="color:#fff;" destroy-on-close>
        <el-form style="padding-bottom:100px;position:relative" :model="dataForm" :rules="dataRule" ref="dataForms" @keyup.enter.native="dataFormSubmit()" label-width="120px">
            <a style="position:absolute;top:15px; left:250px;cursor: pointer; text-decoration:underline;color: #409eff;"
               @click="getDownLoadTemplateExcel()">下载模板</a>
            <el-upload
              style="float:left;padding-left: 30px"
              ref="upload"
              class="upload-demo"
              :headers="upHeader"
              :action="upUrl()"
              name="file"
              :on-success="upSuccess"
              :on-change="handleChange"
              :on-remove="handleRemove"
              :auto-upload="false"
              :limit="1"
              accept=".xls,.xlsx"
              :file-list="fileList">
              <el-button size="small" type="primary">选择模板文件</el-button>
              <div slot="tip" style="margin-top: 20px;" class="el-upload__tip">请先点击”下载模板“，然后选择填写好的车辆交付Excel模板文件提交上传。</div>
            </el-upload>

        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button @click="visible = false">取消</el-button>
            <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
        </span>
    </el-dialog>
</template>
<script>
    import {getDownLoadTemplateExcel,getUpLoadTemplateExcel} from "@/api/parameter";
    import { API_SERVER_URL } from '@/api'
    export default {
        name: 'Dialog',
        data() {
            return {
                title: '模板',
                visible: false,
                dataForm: {
                    file:""
                },
                dataRule: {
                },
                fileList: [

                ]
            }
        },
        computed: {
            //携带token上传
            upHeader: function() {
                return {
                    "token": window.sessionStorage.getItem('token')
                }
            }
        },
        methods: {
            //编辑或新增进入弹窗
            initData: function () {
                this.visible = true;
            },
            getDownLoadTemplateExcel(){
                getDownLoadTemplateExcel().then((res) => {
                    const blob = new Blob([res.data]);
                     const fileName = "车辆批量交付导入模板.xls";
                     const elink = document.createElement('a');// 创建a标签
                     elink.download = fileName;// 为a标签添加download属性
                     // a.download = fileName; //命名下载名称
                     elink.style.display = 'none';
                     elink.href = URL.createObjectURL(blob);
                     document.body.appendChild(elink);
                     elink.click();// 点击下载
                     URL.revokeObjectURL(elink.href); // 释放URL 对象
                     document.body.removeChild(elink);// 释放标签
                     this.loadingExt = false;
                });
            },
            handleRemove(file, fileList) {
                console.log("remove");
                console.log(file)
                this.dataForm.file = "";
            },
            handleChange(file, fileList) {
                console.log("change");
                console.log(file)
                this.dataForm.file=file;
                //this.fileList = fileList.slice(-1);
            },
            upUrl() {
                return API_SERVER_URL + "/customer/orderbusdelivery/templateExcel/upLoad";
            },
            dataFormSubmit: function () {
                if(this.dataForm.file != ''){
                    console.log("submit");
                    this.$refs.upload.submit();
                }else {
                    this.$message.error({
                        dangerouslyUseHTMLString: true,
                        message: '请选择要上传的文件'
                    })
                }
            },
            upSuccess(res, file) {
                this.dataForm.file = 0;
                if (res.code == 0) {
                    this.$message({
                        message: res.message,
                        type: "success",
                        offset: 300,
                        duration: 1500,
                        onClose: () => {
                            //this.resetForm();
                            this.visible = false;
                            this.$refs.upload.clearFiles();
                            this.$emit('refreshDataList')
                        }
                    });
                } else {
                    this.$message.error({
                        dangerouslyUseHTMLString: true,
                        duration: 5000,
                        showClose: true,
                        message: res.message
                    })

                }
            },

        }
    }

</script>
<style>
</style>
