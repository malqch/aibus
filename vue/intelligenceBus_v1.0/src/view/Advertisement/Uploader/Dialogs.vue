<!--
 * @Author : SongWenXin
 * @Mail : songwenxin666@sina.com
 * @ProjectName : intelligenceBus_v1.0
 * @FileName : Dialog
 * @Date :  2020-11-08 17:54
-->
<template>

    <el-dialog class="dialog_default" title="广告投放信息详细展示" :close-on-click-modal="false" @close="closeDialog" :visible.sync="visible" style="color:#fff" append-to-body >
        <div style="max-height:60vh;overflow-y:auto;width:100% !important;">
            <div >
                <div class="dialog_default_box" v-if="showData.isInterrupt == 0">
                    <el-row>
                        <el-col :span="4">
                            <div class="card_icon">
                                <div class="card_dashed">
                                    <img :src="adIcon" alt="" >
                                </div>
                            </div>
                        </el-col>
                        <el-col :span="20">
                            <el-row  class="text">
                                <el-col :span="12">
                                    <div class="text"><span>投放方式</span> ： {{showData.advertiseDeliveryTypeName}}</div>
                                </el-col>
                                <el-col :span="12">
                                    <div class="text"><span>投放时间段</span> ： {{showData.deliveryDate}}</div>
                                </el-col>
                            </el-row>

                            <el-row  class="text">
                                <el-col :span="12">
                                    <div class="text"><span>投放受众人群类型</span> ： {{showData.deliveryPeopleAge}}</div>
                                </el-col>
                                <el-col :span="12">
                                    <div class="text"><span>投放受众人群性别</span> ：{{showData.deliveryPeopleGender}}</div>
                                </el-col>
                            </el-row>
                            <el-row  class="text">
                                <el-col :span="24">
                                    <div class="text"><span>投放线路</span> ：{{showData.deliveryLine}}</div>
                                </el-col>
                            </el-row>
                            <el-row  class="text">
                                <el-col :span="24">
                                    <div class="text"><span>投放站点</span> ：{{showData.deliveryStation}}</div>
                                </el-col>
                            </el-row>
                            <el-row  class="text">
                                <el-col :span="24">
                                    <div class="text"><span>投放显示屏描述</span> ： {{showData.positionDesc}}</div>
                                </el-col>
                            </el-row>
                        </el-col>
                    </el-row>
                </div>
                <div class="dialog_default_box" v-if="showData.isInterrupt == 1">
                    <el-row>
                        <el-col :span="24">
                            <el-row  class="text">
                                <el-col :span="24">
                                    <div class="text"><span>投放时间段</span> ： {{showData.deliveryDate}}</div>
                                </el-col>
                                <el-col :span="24">
                                    <div class="text"><span>投放显示屏描述</span> ： {{showData.positionDesc}}</div>
                                </el-col>
                                <el-col :span="24" v-if="showData.interruptNotice !== ''">
                                    <div class="text"><span>通告内容</span> ： {{showData.interruptNotice}}</div>
                                </el-col>
                            </el-row>
                        </el-col>
                    </el-row>
                </div>
                <div class="dialog_default_box1" v-if="showData.interruptNotice == '' && showData.attachList.rectangleScreen.fileUrlList.length > 0">
                    <el-row>
                        <el-col :span="24">
                            <div style="color:#fff;font-size:15px;">长条显示屏</div>
                        </el-col>
                        <el-col :span="24">
                            <div style="border:10px solid rgba(0,0,0,1)">
                                <el-carousel height="240px">
                                    <el-carousel-item v-for="item in showData.attachList.rectangleScreen.fileUrlList" :key="item">
                                        <img :src="item" alt="" style="width:100%;height:240px">
                                    </el-carousel-item>
                                </el-carousel>
                            </div>
                        </el-col>
                    </el-row>
                </div>
                <div class="dialog_default_box1" v-if="showData.interruptNotice == '' && showData.attachList.squareScreen.fileUrlList.length > 0">
                    <el-row>
                        <el-col :span="24">
                            <div style="color:#fff;font-size:15px;">方形显示屏</div>
                        </el-col>
                        <el-col :span="24">
                            <div style="border:10px solid rgba(0,0,0,1)">
                                <el-carousel height="390px" v-if="showData.attachList.squareScreen.advertiseType == 0">
                                    <el-carousel-item v-for="item in showData.attachList.squareScreen.fileUrlList" :key="item">
                                        <img :src="item" alt="" style="width:100%;height:390px">
                                    </el-carousel-item>
                                </el-carousel>
                                <el-carousel height="390px" v-if="showData.attachList.squareScreen.advertiseType == 1">
                                    <el-carousel-item v-for="item in showData.attachList.squareScreen.fileUrlList" :key="item">
                                        <video v-if="videoFlag" controls="controls"   autoplay="autoplay" loop="loop" muted="muted" :src="item" alt="" width="100%" style="height:100%;display: block;border:none;object-fit:fill;" type="video/mp4"></video>
                                    </el-carousel-item>
                                </el-carousel>
                            </div>
                        </el-col>
                    </el-row>
                </div>
                <div class="dialog_default_box">
                    <el-form ref="form" :model="form" label-width="80px">
                        <el-row>
                            <el-col :span="24">
                                <div style="width:100%;background-color:rgba(255,255,255,0.6);border-radius:5px;margin-bottom: 10px;">
                                    <el-form-item label="投放选项">
                                        <el-radio-group v-model="form.radios" >
                                            <el-radio label="1">确定投放</el-radio>
                                            <el-radio label="0">放弃投放</el-radio>
                                        </el-radio-group>
                                    </el-form-item>
                                </div>
                            </el-col>
                        </el-row>
                    </el-form>
                </div>
                <div class="clear"></div>
            </div>

        </div>
        <span slot="footer" class="dialog-footer">
            <el-button @click="closeDialog()">取消</el-button>
            <el-button type="primary"  @click="submit()">确定</el-button>
        </span>
    </el-dialog>
</template>
<script>
    import {
        getOperationAdvertiseDetail,
        getOperationAdvertiseConfirm,
        getOperationAdvertiseDelete
    } from '@/api/Advertisement';
    export default {
        name: 'Dialog',
        data() {
            return {
                adIcon : 'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNjA0NzE1MzI4NjEzIiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjQ0OTAiIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNNzA3LjI5NiAyNzQuMjcyYzAtNDQuNTEyLTcuNDU2LTc5LjItMjcuMjk2LTk4Ljk3Ni0xOS43NDQtMjIuMjA4LTUxLjkzNi0zNy4wODgtOTQuMDE2LTM3LjA4OGgtNzkuMTM2djI2OS43MjhoNzQuMjA4YzQyLjA0OCAwIDc2LjczNi0xMi4zODQgOTYuNTQ0LTM3LjA4OCAxOS43NzYtMTkuODQgMjkuNjk2LTU0LjQ5NiAyOS42OTYtOTYuNTc2eiIgcC1pZD0iNDQ5MSIgZmlsbD0iI2ZmZmZmZiI+PC9wYXRoPjxwYXRoIGQ9Ik01ODguNTEyIDk1Mi4yODhIMTMwLjY4OGMtNDIuMDQ4IDAtNzkuMi0zMi4xNi03OS4yLTc0LjIwOFY1MTYuOGg3NDIuMzY4YzI0LjcwNCAwIDQ5LjQ3MiAyLjQzMiA0OS40NzIgNC45NlYxMzUuNzQ0YzAtNjkuMzQ0LTUxLjkzNi0xMjMuNzQ0LTExOC43ODQtMTIzLjc0NEgxMzAuNjg4QzYxLjQ0IDEyIDIuMDE2IDY4Ljg5NiAyLjAxNiAxMzUuNzQ0djc0Mi4zMDRjMCA2OS4zMTIgNTkuNDI0IDEyMy43MTIgMTI4LjY3MiAxMjMuNzEyaDUxMi4yNTZjLTE5Ljg0LTEyLjMyLTM3LjE1Mi0yOS42MzItNTQuNDMyLTQ5LjQ3MnogbS0xMjEuMjgtODQ4LjcwNGgxMjMuNzQ0YzUxLjk2OCAwIDk0LjA0OCAxNy4yOCAxMjEuMTg0IDQ5LjUwNCAyMi4zMzYgMjkuNjY0IDM3LjE1MiA2OS4yMTYgMzcuMTUyIDEyMS4yMTYgMCA1MS45NjgtMTQuODE2IDk0LjA0OC00Mi4wMTYgMTIzLjc3Ni0yOS43MjggMzIuMTI4LTY5LjI4IDQ5LjQ3Mi0xMjMuNzEyIDQ5LjQ3MmgtMTE4LjgxNlYxMDMuNTg0aDIuNDY0eiBtLTIzNS4xMDQgMGgzOS41ODRsMTM2LjA5NiAzNDEuNDcySDM2NS43NmwtMzcuMTUyLTk5LjAwOEgxNzUuMjMybC0zNy4xMiA5OS4wMDhIOTYuMDMyTDIzMi4xMjggMTAzLjU4NHoiIHAtaWQ9IjQ0OTIiIGZpbGw9IiNmZmZmZmYiPjwvcGF0aD48cGF0aCBkPSJNMjU0LjQzMiAxNTUuNTJMMTkwLjA0OCAzMTMuODg4aDEyMy43NzZMMjU0LjQzMiAxNTUuNTJ6IG0yNTkuODA4IDQ5Mi40MTZIMTE4LjMwNGEyNC43NjggMjQuNzY4IDAgMCAwIDAgNDkuNTA0SDUxNC4yNGMxMi4zNTIgMCAyNC43NjgtMTIuMzUyIDI0Ljc2OC0yNC43MzYgMC0xMi40MTYtMTIuNDE2LTI0Ljc2OC0yNC43NjgtMjQuNzY4eiBtMCAxNDMuNTJIMTE4LjMwNGEyNC44IDI0LjggMCAwIDAtMjQuNzM2IDI0LjczNmMwIDE0Ljg4IDkuODg4IDI0LjczNiAyNC43MzYgMjQuNzM2SDUxNC4yNGEyNC44MzIgMjQuODMyIDAgMCAwIDI0Ljc2OC0yNC43MzZjMC0xMi4zMi0xMi40MTYtMjQuNzM2LTI0Ljc2OC0yNC43MzZ6IG01MDcuMjk2LTM3LjEybC0xMi40NDgtNjEuODU2Yy00LjkyOC0xNy4zNzYtMjIuMjQtMzIuMjI0LTM5LjUyLTM0LjYyNGwtMjQuNzM2LTIuNTI4Yy0yLjQ2NCAwLTIuNDY0IDAtNC44OTYtMi40MzJ2LTkuOTJsMi40MzItMjQuNzY4YzIuNDY0LTE5Ljc3Ni03LjM5Mi0zOS41NTItMjQuNzY4LTQ3LjAwOEw4NjAuNzA0IDU0NGgtMi40MzJjLTQuOTYtMi40NjQtOS45Mi0yLjQ2NC0xNC44OC0yLjQ2NC0xMi4zNTIgMC0yNC43MzYgNC45MjgtMzIuMTYgMTIuMzUybC0xNy4zMTIgMTcuMzEyLTQuOTI4IDQuOTI4Yy0yLjQ5NiAwLTIuNDk2LTIuNC00Ljk2LTQuOTI4bC0xNy4zNzYtMTcuMzEyYy03LjM5Mi03LjM5Mi0xOS43NDQtMTIuMzUyLTMyLjA5Ni0xMi4zNTItOS45NTIgMC0xNy4zNzYgMi40NjQtMTkuODA4IDQuOTI4bC01Ni44OTYgMjcuMjY0Yy0xNC44NDggOS44ODgtMjcuMjY0IDI3LjItMjQuNzM2IDQ0LjQ4bDIuNDMyIDI3LjI2NHY0Ljk2cy00LjkyOCAyLjQ2NC03LjM5MiAyLjQ2NGwtMjIuMjcyIDIuNDMyYy0xNy4yOCAyLjUyOC0zNC42NTYgMTQuODgtMzkuNTg0IDM0LjY4OGwtMTIuMzUyIDYxLjkyYy00Ljk2IDE3LjI0OCAyLjQ2NCAzNy4wMjQgMTkuNzc2IDQ5LjQ0bDE5LjgwOCAxMi40MTYgNy40MjQgNy4zOTJjMCAyLjQ2NCAwIDIuNDY0LTIuNTI4IDQuOTI4bC0xNC43ODQgMjIuMjcyYy05Ljk1MiAxNC43ODQtOS45NTIgMzcuMDg4IDIuNCA1MS45MzZsMzkuNjQ4IDUxLjkzNmM5Ljg1NiA5LjkyIDIyLjI3MiAxNy4zNzYgMzQuNjI0IDE3LjM3NiA0LjkyOCAwIDkuODg4IDAgMTQuODQ4LTIuNDk2bDI0LjcwNC03LjQyNGg3LjQ1NmMwIDIuNDY0IDIuNDMyIDIuNDY0IDIuNDMyIDQuOTZsNy4zOTIgMjIuMjRjNC45MjggMTkuODA4IDI0LjczNiAyOS43MjggNDIuMDQ4IDI5LjcyOGg2MS45MmMxNy4zNDQgMCAzNC41OTItMTIuNDE2IDM5LjU1Mi0yOS43MjhsNy40MjQtMjIuMjRjMC0yLjQ5NiAyLjQ2NC0yLjQ5NiAyLjQ2NC00Ljk2aDcuNDI0bDE5LjgwOCA3LjQyNGM0Ljg5NiAyLjQ5NiAxMi4zNTIgMi40OTYgMTQuODQ4IDIuNDk2IDE0Ljg0OCAwIDI5LjY5Ni03LjQ1NiAzNy4wNTYtMTcuMzc2bDM5LjYxNi00OS40MDhjOS44ODgtMTIuNDE2IDEyLjM4NC0zNC43NTIgMi40NjQtNTEuOTY4bC0xMi4zODQtMTkuODA4YzAtMi40NjQtMi40MzItNC45Ni0yLjQzMi03LjQyNGw0Ljk2LTQuOTYgMjIuMTc2LTEyLjQxNmMxMi40NDgtMTQuODE2IDE5LjkwNC0zNy4wODggMTcuNDQtNTEuOTM2eiBtLTQyLjExMiA3LjQ4OGwtMjIuMjQgMTQuODE2Yy00LjkyOCA0Ljk2LTE5LjgwOCAxNy4zMTItMjIuMjQgMjkuNjY0LTIuNDk2IDEyLjQxNiAyLjQzMiAyOS43MjggOS44ODggMzcuMTUybDE0LjgxNiAyMi4yNzItMzcuMDI0IDUyaC0yLjU2bC0yNC43MDQtNy41MmMtMTcuMzEyLTQuODY0LTM0LjY1NiAwLTM5LjYxNiAyLjU2LTkuODg4IDQuOTYtMTkuNzc2IDE5Ljc3Ni0yNC42NzIgMjkuNjY0bC03LjQyNCAyMi4yNzItMi40NjQgMi40NjRoLTY0LjM1MmwtOS45Mi0xOS43NzZjLTIuNDMyLTEyLjM1Mi0xMi4zNTItMjcuMjY0LTIyLjI3Mi0zMi4xNi03LjQyNC01LjAyNC0xNy4yOC01LjAyNC0xOS44MDgtNS4wMjQtNy4zNiAwLTE0LjgxNiAyLjU2LTI0LjczNiA1LjAyNGwtMjIuMjA4IDcuNDU2aC00Ljk5MmwtMzcuMDg4LTQ5LjZ2LTQuOTZsMTIuMzg0LTE5LjcxMmM3LjM5Mi05Ljk1MiAxMi4zODQtMjQuNzM2IDkuOTUyLTM5LjY0OC0yLjUyOC0xNC44MTYtMTcuMzQ0LTI3LjE2OC0yNC43NjgtMzIuMTI4bC0yMi4yNC0xMi40MTZzLTIuNDY0LTIuNCAwLTIuNGwxNC44MTYtNjQuMzUyIDIyLjI3Mi0yLjQ5NmMxMi4zODQgMCAyOS43MjgtNy40MjQgMzcuMTItMTcuMzEyIDcuMzkyLTkuODU2IDEyLjM4NC0yNC43MzYgOS45Mi0zNy4xNTJsLTIuNDk2LTI0LjY3MmMwLTIuNTI4IDIuNDk2LTQuOTYgMi40OTYtNC45Nmw1NC40MzItMjQuNzM2aDQuOTZsMTcuMzEyIDE3LjMxMmM5LjkyIDcuNDI0IDIyLjI0IDE0LjgxNiAzNC42MjQgMTQuODE2IDEyLjQxNiAwIDI5LjY5Ni03LjM2IDM0LjY1Ni0xNy4yOGwxNy4zNDQtMTcuMzEyaDIuNDMybDU2LjkyOCAyNC43MzYgMi40MzIgMi40NjQtMi40MzIgMjQuNjcyYzAgNC45NiAwIDI0LjczNiA5Ljg4OCAzNy4xODQgNC45NiA3LjQyNCAxNy4zNDQgMTQuODE2IDM0LjY1NiAxNy4zMTJsMjIuMjQgMi40NjQgMi40OTYgMi40NjQgMTIuMzUyIDU5LjM2djcuNDg4aC0wLjE2eiIgcC1pZD0iNDQ5MyIgZmlsbD0iI2ZmZmZmZiI+PC9wYXRoPjxwYXRoIGQ9Ik03ODguOTI4IDY2NS4yNDhjLTYxLjg4OCAwLTExMy44NTYgNTEuOTM2LTExMy44NTYgMTEzLjg4OCAwIDYxLjg1NiA1MS45NjggMTEzLjg1NiAxMTMuODU2IDExMy44NTYgNTkuMzYgMCAxMTEuMzI4LTUyIDExMy43OTItMTEzLjg1NiAwLTYxLjk1Mi01MS45MzYtMTEzLjg4OC0xMTMuNzkyLTExMy44ODh6IG0wIDE4My4xNjhjLTM5LjYxNiAwLTcxLjgwOC0zMi4xOTItNzEuODA4LTcxLjc3NnMzMi4xOTItNzEuNzEyIDcxLjgwOC03MS43MTJjMzcuMDI0IDAgNjkuMjggMzIuMTI4IDcxLjcxMiA3MS43MTItMi40MzIgNDIuMDQ4LTM0LjY1NiA3MS43NzYtNzEuNzEyIDcxLjc3NnoiIHAtaWQ9IjQ0OTQiIGZpbGw9IiNmZmZmZmYiPjwvcGF0aD48L3N2Zz4=',
                tableData: [],
                visible : false,
                videoFlag : false,
                showData : {
                    advertiseId : '',
                    checkStatus : 9,
                    isInterrupt : 0,
                    interruptNotice : '',
                    advertiseDeliveryTypeName : '',
                    deliveryDate : '',
                    deliveryLine : '',
                    deliveryStation : '',
                    deliveryPeopleAge : '',
                    deliveryPeopleGender : '',
                    attachList : {
                        rectangleScreen : {
                            fileUrlList : [],
                            advertiseType : 0,
                        },
                        squareScreen : {
                            fileUrlList : [],
                            advertiseType : 1,
                        }
                    },
                    positionDesc : ''
                } ,
                form : {
                    radios : '1',
                    advertiseId : '',
                }
            }
        },
        methods : {
            submit : function(){
                if(this.form.radios == 1){
                    getOperationAdvertiseConfirm({
                        advertiseId : this.form.advertiseId
                    }).then((res) => {
                        if(res.data && res.data.code == 0){
                            this.$message({
                                message: '广告投放成功！',
                                type: 'success',
                                offset: 300,
                                duration: 1500,
                                onClose: () => {
                                    this.visible = false;
                                    this.$emit('regetData');
                                }
                            })
                        }
                    })
                };
                if(this.form.radios == 0){
                    getOperationAdvertiseDelete({
                        advertiseId : this.form.advertiseId
                    }).then((res) => {
                        if(res.data && res.data.code == 0){
                            this.$message({
                                type: 'error',
                                message: '放弃广告投放成功，此广告已删除',
                                onClose: () => {
                                    this.visible = false;
                                    this.$emit('regetData');
                                }
                            });
                        }
                    })
                }
            },
            closeDialog : function(){
                this.visible = false;
                this.videoFlag = false;
            },
            init : function(id){
                this.visible = true;
                getOperationAdvertiseDetail({
                    advertiseId : id
                }).then((res) => {
                    if(res.data && res.data.code == 0){
                        this.showData = res.data.data.data;
                        this.form.advertiseId = this.showData.advertiseId;
                        console.log('打印showdata')
                        console.log(this.showData)
                        this.videoFlag = true;
                        this.form.radios = '1';
                    }
                })
            }

        }
    }
</script>
<style scoped>
    /deep/ .el-dialog {
        width:900px;
        margin-top: 10vh !important;
    }
    .dialog_default_box{
        width:100%;
        box-sizing: border-box;
        padding:20px;
        background: linear-gradient(-45deg, #89121F, #A80000, #FF3F00);
        border-radius:5px;
        margin-bottom: 10px;
        /*background:url("../../..//assets/updata_bg.jpg") center;*/

        /*border:1px solid rgba(0,0,0,0.3);*/
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
    .dialog_default_box1{
        width:100%;
        box-sizing: border-box;
        padding:20px;
        background: linear-gradient(-45deg, #0498DC, #03D4CC);
        border-radius:5px;
        margin-bottom: 10px;
        background:url("../../..//assets/updata_bg.jpg") center;

        /*border:1px solid rgba(0,0,0,0.3);*/
    }
</style>

