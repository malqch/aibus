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
                            <el-col :span="24">
                                <div>
                                    <el-form-item label="选择插播方式">
                                        <el-radio-group v-model="isShow" @change="changeRidios">
                                            <el-radio :label="0">插播通告</el-radio>
                                            <el-radio :label="1">插播广告</el-radio>
                                        </el-radio-group>
                                    </el-form-item>

                                </div>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="设置投放起止日期">
                                    <el-date-picker
                                            @change="showTime"
                                            v-model="valueTime"
                                            :picker-options="pickerOptions"
                                            type="datetimerange"
                                            value-format="yyyy-MM-dd HH:mm:ss"
                                            range-separator="至"
                                            start-placeholder="开始日期"
                                            end-placeholder="结束日期">
                                    </el-date-picker>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row v-if="isShow == 0">
                            <el-col :span="24">
                                <div>
                                    <el-form-item label="填写插播通知">
                                        <el-input type="textarea" v-model="form.interruptNotice"></el-input>
                                    </el-form-item>
                                </div>
                            </el-col>
                        </el-row>
                        <el-row v-if="isShow == 1">
                            <el-col>
                                <div style="width:100%;background-color:#F3F8FE;box-sizing:border-box;padding:15px 15px;margin-bottom: 20px;border-radius:5px;border:1px solid rgba(224,225,266,0.4);float: left;">
                                    <el-row>
                                        <el-col :span="24">
                                            <div style="font-size:14px;color:#000;">长条显示屏 - 素材添加</div>
                                        </el-col>
                                        <el-col :span="14">
                                            <div class="dialog_default_box" >
                                                <div style="position: absolute;top: 60px;color:#fff;left:60px;">93cm</div>
                                                <div style="position: absolute;top: 100px; left: 17px;color:#fff;transform:rotate(-90deg);">26cm</div>
                                                <div style=" width:95%; height:60%;box-sizing:border-box;border:4px solid #000;background-color:rgba(0,0,0,0.8);margin-top: 40px;margin-left: 5%;" >
                                                    <img :src="imgUrlL" alt="" v-if="imgUrlL !== ''" style="width:100%;height:100%;">
                                                </div>
                                            </div>
                                        </el-col>
                                        <el-col :span="10">
                                            <div class="updata_box_default">
                                                <el-upload ref="rectangleFilesEle"
                                                           name="rectangleFiles"
                                                           :action="upUrl()"
                                                           :before-upload="beforeUpImg"
                                                           :on-preview="handlePreviewImg"
                                                           :auto-upload="false"
                                                           :on-exceed="exceed"
                                                           :on-change="changeFiles"
                                                           :file-list="filelists"
                                                           accept=".jpg,.jpeg,.png"
                                                           :limit="5" :with-credentials="false">
                                                    <el-button slot="trigger" size="small" type="primary">选取本地广告图片素材</el-button>
                                                    <div slot="tip" class="el-upload__tip" style="color:red;">只能上传.jpg文件，且不超过1024kb,图片宽度约1920px*高度约540px</div>
                                                </el-upload>
                                            </div>
                                        </el-col>
                                    </el-row>
                                </div>
                            </el-col>

                        </el-row>
                        <el-row v-if="isShow == 1">
                            <el-col>
                                <div style="width:100%;background-color:#F3F8FE;box-sizing:border-box;padding:15px 15px;margin-bottom: 20px;border-radius:5px;border:1px solid rgba(224,225,266,0.4);float: left;">
                                    <el-row>
                                        <el-col :span="24">
                                            <el-row>
                                                <el-col :span="6">
                                                    <div style="font-size:14px;color:#000;">方形显示屏 - 素材添加</div>
                                                </el-col>
                                                <el-col :span="18">
                                                    <el-radio-group v-model="radio" @change="changeRidio">
                                                        <el-radio :label="0">图片</el-radio>
                                                        <el-radio :label="1">视频</el-radio>
                                                    </el-radio-group>
                                                </el-col>
                                            </el-row>

                                        </el-col>
                                        <el-col :span="14">
                                            <div class="dialog_default_box" >
                                                <div style="position: absolute;top:20px;color:#fff;left:70px;">70cm</div>
                                                <div style="position: absolute;top: 75px;left:35px;color:#fff;transform:rotate(-90deg);">40cm</div>
                                                <div style=" width:83%; height:105%;box-sizing:border-box;border:4px solid #000;background-color:rgba(0,0,0,0.8);margin-top: 0px;margin-left: 10%;" >
                                                    <video style="width:100%;height:100%;" :src="videoUrl" id="videoEle" controls="controls" v-if="radio == 1 && videoUrl !== ''" autoplay="autoplay" loop="loop"></video>
                                                    <img :src="imgUrlF" alt="" v-if="radio == 0 && imgUrlF !== ''" style="width:100%;height:100%;" alt="无图片预览">
                                                </div>
                                            </div>
                                        </el-col>
                                        <el-col :span="10">
                                            <div class="updata_box_default">
                                                <el-upload ref="squareFilesEle"
                                                           name="squareFiles"
                                                           :action="upUrl()"
                                                           :before-upload="beforeUpVideo"
                                                           :on-preview="handlePreviewVideo"
                                                           :on-change="changeFile"
                                                           :auto-upload="false"
                                                           :before-remove = "fn"
                                                           :on-exceed="exceed"
                                                           :accept="fileType"
                                                           :file-list="filelist"
                                                           :limit="5" :with-credentials="false">
                                                    <el-button slot="trigger" size="small" type="success">选取本地广告{{title}}</el-button>
                                                    <div slot="tip" class="el-upload__tip" style="color:red;" v-if="radio == 0">只能上传.jpg文件，且不超过1024kb,图片宽度约1920px*高度约1080px</div>
                                                    <div slot="tip" class="el-upload__tip" style="color:red;" v-if="radio == 1">只能上传.mp4文件，且不超过10M,视频比例16：9，分辨率1280px* 720px最佳</div>
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
                forms : {},
                pickerOptions: {
                    disabledDate(time) {
                        // return time.getTime() < Date.now() + 7*24*60*60*1000;
                    }
                },
                visible : false,
                isShow : 0,
                valueTime : [],


                uploadForm: new FormData(),
                form : {
                    advertiseDeliveryId : '',
                    isInterrupt : 1,
                    deliveryBegin : '',
                    deliveryEnd : '',
                    interruptNotice : '',

                },
                radio : 0,
                fileType : '.jpg,.jpeg,.png',
                textList : [
                    '只能上传.jpg类型的图片素材，分辨率为'
                ],
                title : '',
                imgUrlL : '',
                imgUrlF : '',
                videoUrl : '',
                filelist : [],
                filelists : [],
                fileLeng : '',
                fileLengs : '',
            }
        },
        methods : {
            fn : function(file, fileList){
                console.log('ok lalalllllll')
                this.videoUrl = '';
            },
            showTime : function(){
                this.form.deliveryBegin  = '';
                this.form.deliveryEnd = '';
                if(this.valueTime){
                    this.form.deliveryBegin = this.valueTime[0];
                    this.form.deliveryEnd = this.valueTime[1];
                }

            },
            changeFile(file, fileList){
                if(fileList && fileList.length){
                    this.fileLeng = fileList.length;
                }
            },
            changeFiles(file, fileList){
                if(fileList && fileList.length){
                    this.fileLengs = fileList.length;
                }
            },
            changeRidio : function(){
                this.imgUrlF  =  '';
                this.videoUrl = '';
                this.filelist = [];
                if(this.radio == 0){
                    this.fileType = '.jpg';
                    this.title = '图片素材';
                };
                if(this.radio == 1){
                    this.fileType = '.mp4';
                    this.title = '视频素材';
                }
            },
            changeRidios : function(){
                this.clearInit();
            },
            clearInit : function(){
                this.form.isInterrupt = 1;
                this.form.interruptNotice = '';
                this.form.deliveryBegin = '';
                this.form.deliveryEnd = '';
                this.valueTime = [];

                this.radio = 1;
                this.fileType = '.mp4';
                this.title = '视频素材';
                this.imgUrlL = '';
                this.imgUrlF = '';
                this.videoUrl = '';
                this.filelist = [];
                this.filelists = [];

            },
            submit : function(){
                var validate = false;
                if(this.isShow == 0){

                    if(this.form.deliveryBegin =='' || this.form.deliveryEnd ==''){
                        validate = false;
                        this.$message({
                            message: '请选择插播起止时间段',
                            type: 'error',
                            offset: 300
                        });
                        return;
                    }else{
                        validate = true;
                    }
                    if(this.form.interruptNotice ==''){
                        validate = false;
                        this.$message({
                            message: '插播通告不能为空！',
                            type: 'error',
                            offset: 300
                        });
                        return;
                    }else{
                        validate = true;
                    };
                }
                if(this.isShow ==  1){
                    if(this.form.deliveryBegin =='' || this.form.deliveryEnd ==''){
                        validate = false;
                        this.$message({
                            message: '请选择插播起止时间段',
                            type: 'error',
                            offset: 300
                        });
                        return;
                    }else{
                        validate = true;
                    }
                    if(this.fileLengs == 0 && this.fileLeng == 0){
                        this.$message({
                            message: '投放素材不能为空',
                            type: 'error',
                            offset: 300
                        });
                        validate = false;
                        return;
                    }else{
                        validate = true;
                    }
                }
                if(validate){
                    if(this.form.advertiseDeliveryId == ''){
                        console.log(this.form.interruptNotice == '')
                        this.uploadForm = new FormData()
                        this.uploadForm.append('data', JSON.stringify(this.form));
                        if(this.form.interruptNotice == ''){
                            this.$refs.rectangleFilesEle.submit()  // 提交时触发了before-upload函数
                            this.$refs.squareFilesEle.submit();
                        }
                        setInterruptSave(this.uploadForm).then((res) =>{
                            if(res.data && res.data.code == 0){
                                this.$message({
                                    message: '上传广告素材成功！',
                                    type: 'success',
                                    offset: 300,
                                    duration: 1500,
                                    onClose: () => {
                                        this.visible = false;
                                        this.$emit('regetData');
                                    }
                                })
                            }else{
                                this.$message({
                                    message: res.data.message,
                                    type: 'error',
                                    offset: 300
                                })
                            }
                        })
                    }else{
                        this.uploadForm = new FormData()
                        this.uploadForm.append('data', JSON.stringify(this.form));
                        if(this.form.interruptNotice == ''){
                            this.$refs.rectangleFilesEle.submit()  // 提交时触发了before-upload函数
                            this.$refs.squareFilesEle.submit();
                        }
                        setInterruptUpdate(this.uploadForm).then((res) =>{
                            if(res.data && res.data.code == 0){
                                this.$message({
                                    message: '修改广告素材成功！',
                                    type: 'success',
                                    offset: 300,
                                    duration: 1500,
                                    onClose: () => {
                                        this.visible = false;
                                        this.$emit('regetData');
                                    }
                                })
                            }else{
                                this.$message({
                                    message: res.data.message,
                                    type: 'error',
                                    offset: 300
                                })
                            }
                        })
                    }
                }
            },

            beforeUpImg(file) {
                this.uploadForm.append('rectangleFiles', file)
                return false
            },
            beforeUpVideo(file) {
                this.uploadForm.append('squareFiles', file)
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
            handlePreviewVideo :  function(file){
                this.imgUrlF  =  '';
                this.videoUrl = '';
                if(this.radio == 1){
                    this.videoUrl = window.URL.createObjectURL(file.raw);
                };
                if(this.radio == 0){
                    this.imgUrlF = window.URL.createObjectURL(file.raw);
                };

            },
            handlePreviewImg :  function(file){
                this.imgUrlL = window.URL.createObjectURL(file.raw);
            },
            init : function(id){
                this.form.advertiseDeliveryId = '';
                this.visible = true;
                if(id !== ''){
                    this.clearInit();
                    this.isShow = 0;
                    getInterruptInfo({
                        advertiseId : id
                    }).then((res) => {
                        if(res.data && res.data.code == 0){
                            this.form.advertiseDeliveryId = res.data.data.data.advertiseDeliveryId;
                            this.form.deliveryBegin = res.data.data.data.deliveryBegin;
                            this.form.deliveryEnd = res.data.data.data.deliveryEnd;
                            this.form.isInterrupt = res.data.data.data.isInterrupt;
                            this.form.interruptNotice = res.data.data.data.interruptNotice;
                            this.valueTime.push(this.form.deliveryBegin);
                            this.valueTime.push(this.form.deliveryEnd);
                            if(this.form.interruptNotice == ''){
                                this.isShow = 1;
                            }else{
                                this.isShow = 0;
                            }
                        }
                    })
                    console.log('修改广告')
                    console.log(id)
                }else{
                    this.clearInit();
                    this.isShow = 0;
                    console.log('上传广告')
                    console.log(id)
                }

            },
            closeDialog(){
                this.visible = false;
                this.clearInit();
                this.isShow = 0;
            }

        },
        mounted : function(){

        }
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

