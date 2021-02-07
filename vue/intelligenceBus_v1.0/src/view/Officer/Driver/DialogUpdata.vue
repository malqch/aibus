<!--
 * @Author : SongWenXin
 * @Mail : songwenxin666@sina.com
 * @ProjectName : intelligenceBus_v1.0
 * @FileName : Dialog
 * @Date :  2020-11-08 17:54
-->
<template>

    <el-dialog class="dialog_default" title="上传广告素材" :close-on-click-modal="false" @close="closeDialog" :visible.sync="visible" style="color:#fff" append-to-body >
        <div style="max-height:67vh;overflow-y:auto;width:100% !important;">
            <div >
                <div class="dialog_box">
                    <el-form ref="form" :model="form" label-width="140px">
                        <el-row>
                            <el-col>
                                <div style="width:100%;background-color:#F3F8FE;box-sizing:border-box;padding:15px 15px;margin-bottom: 20px;border-radius:5px;border:1px solid rgba(224,225,266,0.4);float: left;">
                                    <el-row>
                                        <el-col :span="10">
                                            <div class="updata_box_default">
                                                <el-upload ref="fileEle"
                                                           name="file"
                                                           :action="upUrl()"
                                                           :before-upload="beforeUpVideo"
                                                           :on-change="changeFile"
                                                           :auto-upload="false"
                                                           :before-remove = "fn"
                                                           :on-exceed="exceed"
                                                           accept=".jpg,.jpeg,.png"
                                                           file-list="只能上传.jpg类型的图片素材，分辨率为"
                                                           :limit="5" :with-credentials="false">
                                                    <el-button slot="trigger" size="small" type="success">选取本地广告</el-button>
                                                </el-upload>
                                            </div>
                                        </el-col>
                                    </el-row>
                                </div>
                            </el-col>
                        </el-row>
                    </el-form>

                </div>
                <div class="clear"></div>
            </div>

        </div>
        <span slot="footer" class="dialog-footer">
            <el-button  @click="closeDialog">取消</el-button>
            <el-button type="primary"  @click="submit()">确定</el-button>
        </span>
    </el-dialog>
</template>
<script>
    import {
        setInterruptSave,
        setInterruptUpdate,
        getInterruptInfo
    } from '@/api/Advertisement';
    export default {
        name: 'DialogUpData',
        data() {
            return {
                pickerOptions: {
                    disabledDate(time) {
                        // return time.getTime() < Date.now() + 7*24*60*60*1000;
                    }
                },
                visible : false,


                uploadForm: new FormData(),
                form : {
                    advertiseDeliveryId : '',
                    isInterrupt : 1,
                    deliveryBegin : '',
                    deliveryEnd : '',
                    interruptNotice : '',

                },
            }
        },
        methods : {
            fn : function(file, fileList){
                console.log('ok lalalllllll')
                this.videoUrl = '';
            },
            changeFile(file, fileList){
                if(fileList && fileList.length){
                    this.fileLeng = fileList.length;
                }
            },
            submit : function(){
                this.uploadForm.append('data', JSON.stringify(this.form));
                this.$refs.fileEle.submit();
                setInterruptSave(this.uploadForm).then((res) =>{
                    if(res.data && res.data.code == 0){
                        this.$message({
                            message: '上传广告素材成功！',
                            type: 'success',
                            offset: 300,
                            duration: 1500,
                        })
                    }else{
                        this.$message({
                            message: res.data.message,
                            type: 'error',
                            offset: 300
                        })
                    }
                })
            },
            beforeUpVideo(file) {
                this.uploadForm.append('file', file)
                return false
            },
            upUrl() {
                return "xxx";
            },
            exceed(files, fileList) {
                this.$message({
                    message: "超出上传文件数量限制，请先删除已有文件后再上传",
                    type: 'error',
                    duration: 3000
                })
            },
            closeDialog(){
                this.visible = false;
                this.clearInit();
                this.isShow = 0;
            }

        },
    }
</script>
<style scoped>
    /deep/ .el-dialog {
        width:1000px;
        margin-top: 2vh !important;
    }
    .dialog_default_box{
        width:100%;
        box-sizing: border-box;
        padding:20px;
        background: linear-gradient(-45deg, #0498DC, #03D4CC);
        border-radius:5px;
        margin-bottom: 10px;
        height:250px;
        background:url("../../..//assets/updata_bg.jpg") center;
    }
    .dialog_default_box .text{
        margin-bottom: 6px;
        color:#fff;
        font-size:13px;
    }
    .dialog_default_box .text span{
        font-weight: bold;
    }
    /deep/.dialog_default .el-dialog__body{
        padding: 20px 20px !important;
    }
    .dialog_default_box .card_icon{
        width:110px;
        height:110px;
        display: flex;
        border-radius: 100%;
        justify-content: center;
        align-items: center;
        background: radial-gradient(#03D4CC， #0498DC);
        border:5px solid rgba(255,255,255,1);
        float: left;
        margin-top: 0px;
        margin-left: 0px;
        box-shadow: 0px 0px 10px rgba(255,255,255,0.9);
    }
    .dialog_default_box .card_icon .card_dashed{
        width:95px;
        height:95px;
        border: 1px dashed rgba(255,255,255,0.9);
        border-radius:100%;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .dialog_default_box .card_icon .card_dashed img{
        width:60px;
        height:65px;
        opacity: 1;
    }
    .dialog_box{
        width:100%;
        box-sizing: border-box;
        padding:20px 20px 0px 20px;
        border-radius:5px;
        margin-bottom: 10px;
        border:1px dashed rgba(151,151,151,0.5);
    }
    .el-form-item{
        margin-bottom: 15px;
    }
    .dialog_box .targ_item{
        padding:3px 5px;
        border:1px solid #007BD9;
        display: block;
        min-width:60px;
        float: left;
        margin-right:0px;
        text-align: center;
        font-size:14px;
        cursor : pointer;
    }
    .targ_activeoff{
        background-color:#fff;
        color:#007BD9;
    }
    .targ_activeon{
        background-color:#007BD9;
        color:#fff;
    }
    .dialog_box .stationTarg{
        float: left;
        margin-right:10px;
        margin-left : 4px;
        cursor: pointer;
        font-size:13px;
    }
    .stationTargOn{
        color:#0D96FF;
    }
    .stationTargOff{
        color:#000;
    }
    .not-select{
        -moz-user-select:none; /*火狐*/
        -webkit-user-select:none; /*webkit浏览器*/
        -ms-user-select:none; /*IE10*/
        -khtml-user-select:none; /*早期浏览器*/
        user-select:none;
    }
    .updata_box_default{
        width:95%;
        height:250px;
        box-sizing:border-box;
        padding:20px;
        background-color:#fff;
        border:1px dashed #F89436;
        margin-left:5%;
        border-radius:5px;
    }
</style>

