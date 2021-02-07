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
                            <el-col :span="9">
                                <div>
                                    <el-form-item label="投放方式">
                                        <el-select v-model="form.advertiseDeliveryType" placeholder="选择投放方式" style="width:180px" @change="changeType">
                                            <el-option  :label="item.advertiseDeliveryTypeName" :value="item.advertiseDeliveryType" v-for="(item, index) in lineList" :key="item.advertiseDeliveryTypeName"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </div>
                            </el-col>
                            <el-col :span="15">
                                <div>
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
                                </div>
                            </el-col>
                        </el-row>
                        <el-row v-if="deliveryTypeCode == 'line'">
                            <el-col :span="24">
                                <div style="width:100%;box-sizing:border-box;background-color:rgba(243,248,254,1);border:1px solid rgba(224,225,266,0.4);border-radius:0px;margin-bottom:5px;border-radius:5px;">
                                    <el-form-item label="选择线路" style="margin-bottom: 0;">
                                        <el-checkbox-group v-model="form.deliveryLineList" >
                                            <el-checkbox :label="item.companyLineCode" name="type" v-for="(item, index) in lineCheck" :key="item.companyLineCode">{{item.companyLineCode}}</el-checkbox>
                                        </el-checkbox-group>
                                    </el-form-item>
                                </div>
                            </el-col>
                        </el-row>
                        <el-row v-if="deliveryTypeCode == 'station'">
                            <el-col :span="24">
                                <div style="width:100%;box-sizing:border-box;background-color:rgba(243,248,254,1);border:1px solid rgba(224,225,266,0.4);border-radius:0px;margin-bottom:5px;padding:10px;border-radius:5px;">
                                    <el-row style="width:100%;">
                                        <el-col :span="24">
                                            <span style=""  @click="targClick(item.companyLineCode)"  :class="['targ_item', item.code == 1 ? 'targ_activeon' : 'targ_activeoff']" v-for="item in targLists">{{item.companyLineCode}}</span>
                                        </el-col>
                                        <el-col :span="24">
                                            <div style="width:100%;background-color:#fff;margin-top: 5px;border:1px dashed #F89436;box-sizing:border-box;padding:12px 10px;">

                                                <el-row>
                                                    <el-col :span="24">
                                                        <div>
                                                            <span   :class="['stationTarg', item.code ? 'stationTargOn' : 'stationTargOff']"  @click="staticonClick(item.lineStationId)"  v-for="item in stationTargs" :key="item.lineStationId">{{item.busStationCode}}</span>
                                                        </div>
                                                    </el-col>
                                                </el-row>
                                            </div>
                                        </el-col>
                                        <el-col :span="24">
                                            <div style="float: right;margin-top: -1px;background-color:#fff;padding:6px 8px;border:1px dashed rgba(248,148,54,1);color:rgba(248,148,54,1);font-size:12px;cursor: pointer;" @click="changeLine"> {{changeLineText}} </div>
                                        </el-col>
                                    </el-row>
                                </div>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="14">
                                <div>
                                    <el-form-item label="投放受众人群类型">
                                        <el-checkbox-group v-model="form.peopleAgeList"  >
                                            <el-checkbox :label="item.advertiseTargetId" name="type" v-for="(item, index) in crowdTypeList" :key="item.advertiseTargetId">{{item.advertiseTargetName}}</el-checkbox>
                                        </el-checkbox-group>
                                    </el-form-item>
                                </div>
                            </el-col>
                            <el-col :span="10">
                                <div>
                                    <el-form-item label="投放受众人群性别">
                                        <el-checkbox-group v-model="form.peopleGenderList">
                                            <el-checkbox :label="item.advertiseTargetId" name="type" v-for="(item, index) in sexList" :key="item.advertiseTargetId">{{item.advertiseTargetName}}</el-checkbox>
                                        </el-checkbox-group>
                                    </el-form-item>
                                </div>
                            </el-col>
                        </el-row>
                        <el-row>
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
                        <el-row>
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
                                            <div class="dialog_default_box">
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
                                                           :before-remove = "fn"
                                                           :auto-upload="false"
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
        getAdvertiseDeliveryType,
        getgetCompanyLineByUser,
        getListByUser,
        getAdvertiseTagAge,
        getAdvertiseTagGender,
        setOperationAdvertiseSave,
        getOperationAdvertiseInfo,
        setOperationAdvertiseUpdate
    } from '@/api/Advertisement';
    export default {
        name: 'DialogUpData',
        data() {
            return {
                adIcon : 'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNjA0NzE1MzI4NjEzIiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjQ0OTAiIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNNzA3LjI5NiAyNzQuMjcyYzAtNDQuNTEyLTcuNDU2LTc5LjItMjcuMjk2LTk4Ljk3Ni0xOS43NDQtMjIuMjA4LTUxLjkzNi0zNy4wODgtOTQuMDE2LTM3LjA4OGgtNzkuMTM2djI2OS43MjhoNzQuMjA4YzQyLjA0OCAwIDc2LjczNi0xMi4zODQgOTYuNTQ0LTM3LjA4OCAxOS43NzYtMTkuODQgMjkuNjk2LTU0LjQ5NiAyOS42OTYtOTYuNTc2eiIgcC1pZD0iNDQ5MSIgZmlsbD0iI2ZmZmZmZiI+PC9wYXRoPjxwYXRoIGQ9Ik01ODguNTEyIDk1Mi4yODhIMTMwLjY4OGMtNDIuMDQ4IDAtNzkuMi0zMi4xNi03OS4yLTc0LjIwOFY1MTYuOGg3NDIuMzY4YzI0LjcwNCAwIDQ5LjQ3MiAyLjQzMiA0OS40NzIgNC45NlYxMzUuNzQ0YzAtNjkuMzQ0LTUxLjkzNi0xMjMuNzQ0LTExOC43ODQtMTIzLjc0NEgxMzAuNjg4QzYxLjQ0IDEyIDIuMDE2IDY4Ljg5NiAyLjAxNiAxMzUuNzQ0djc0Mi4zMDRjMCA2OS4zMTIgNTkuNDI0IDEyMy43MTIgMTI4LjY3MiAxMjMuNzEyaDUxMi4yNTZjLTE5Ljg0LTEyLjMyLTM3LjE1Mi0yOS42MzItNTQuNDMyLTQ5LjQ3MnogbS0xMjEuMjgtODQ4LjcwNGgxMjMuNzQ0YzUxLjk2OCAwIDk0LjA0OCAxNy4yOCAxMjEuMTg0IDQ5LjUwNCAyMi4zMzYgMjkuNjY0IDM3LjE1MiA2OS4yMTYgMzcuMTUyIDEyMS4yMTYgMCA1MS45NjgtMTQuODE2IDk0LjA0OC00Mi4wMTYgMTIzLjc3Ni0yOS43MjggMzIuMTI4LTY5LjI4IDQ5LjQ3Mi0xMjMuNzEyIDQ5LjQ3MmgtMTE4LjgxNlYxMDMuNTg0aDIuNDY0eiBtLTIzNS4xMDQgMGgzOS41ODRsMTM2LjA5NiAzNDEuNDcySDM2NS43NmwtMzcuMTUyLTk5LjAwOEgxNzUuMjMybC0zNy4xMiA5OS4wMDhIOTYuMDMyTDIzMi4xMjggMTAzLjU4NHoiIHAtaWQ9IjQ0OTIiIGZpbGw9IiNmZmZmZmYiPjwvcGF0aD48cGF0aCBkPSJNMjU0LjQzMiAxNTUuNTJMMTkwLjA0OCAzMTMuODg4aDEyMy43NzZMMjU0LjQzMiAxNTUuNTJ6IG0yNTkuODA4IDQ5Mi40MTZIMTE4LjMwNGEyNC43NjggMjQuNzY4IDAgMCAwIDAgNDkuNTA0SDUxNC4yNGMxMi4zNTIgMCAyNC43NjgtMTIuMzUyIDI0Ljc2OC0yNC43MzYgMC0xMi40MTYtMTIuNDE2LTI0Ljc2OC0yNC43NjgtMjQuNzY4eiBtMCAxNDMuNTJIMTE4LjMwNGEyNC44IDI0LjggMCAwIDAtMjQuNzM2IDI0LjczNmMwIDE0Ljg4IDkuODg4IDI0LjczNiAyNC43MzYgMjQuNzM2SDUxNC4yNGEyNC44MzIgMjQuODMyIDAgMCAwIDI0Ljc2OC0yNC43MzZjMC0xMi4zMi0xMi40MTYtMjQuNzM2LTI0Ljc2OC0yNC43MzZ6IG01MDcuMjk2LTM3LjEybC0xMi40NDgtNjEuODU2Yy00LjkyOC0xNy4zNzYtMjIuMjQtMzIuMjI0LTM5LjUyLTM0LjYyNGwtMjQuNzM2LTIuNTI4Yy0yLjQ2NCAwLTIuNDY0IDAtNC44OTYtMi40MzJ2LTkuOTJsMi40MzItMjQuNzY4YzIuNDY0LTE5Ljc3Ni03LjM5Mi0zOS41NTItMjQuNzY4LTQ3LjAwOEw4NjAuNzA0IDU0NGgtMi40MzJjLTQuOTYtMi40NjQtOS45Mi0yLjQ2NC0xNC44OC0yLjQ2NC0xMi4zNTIgMC0yNC43MzYgNC45MjgtMzIuMTYgMTIuMzUybC0xNy4zMTIgMTcuMzEyLTQuOTI4IDQuOTI4Yy0yLjQ5NiAwLTIuNDk2LTIuNC00Ljk2LTQuOTI4bC0xNy4zNzYtMTcuMzEyYy03LjM5Mi03LjM5Mi0xOS43NDQtMTIuMzUyLTMyLjA5Ni0xMi4zNTItOS45NTIgMC0xNy4zNzYgMi40NjQtMTkuODA4IDQuOTI4bC01Ni44OTYgMjcuMjY0Yy0xNC44NDggOS44ODgtMjcuMjY0IDI3LjItMjQuNzM2IDQ0LjQ4bDIuNDMyIDI3LjI2NHY0Ljk2cy00LjkyOCAyLjQ2NC03LjM5MiAyLjQ2NGwtMjIuMjcyIDIuNDMyYy0xNy4yOCAyLjUyOC0zNC42NTYgMTQuODgtMzkuNTg0IDM0LjY4OGwtMTIuMzUyIDYxLjkyYy00Ljk2IDE3LjI0OCAyLjQ2NCAzNy4wMjQgMTkuNzc2IDQ5LjQ0bDE5LjgwOCAxMi40MTYgNy40MjQgNy4zOTJjMCAyLjQ2NCAwIDIuNDY0LTIuNTI4IDQuOTI4bC0xNC43ODQgMjIuMjcyYy05Ljk1MiAxNC43ODQtOS45NTIgMzcuMDg4IDIuNCA1MS45MzZsMzkuNjQ4IDUxLjkzNmM5Ljg1NiA5LjkyIDIyLjI3MiAxNy4zNzYgMzQuNjI0IDE3LjM3NiA0LjkyOCAwIDkuODg4IDAgMTQuODQ4LTIuNDk2bDI0LjcwNC03LjQyNGg3LjQ1NmMwIDIuNDY0IDIuNDMyIDIuNDY0IDIuNDMyIDQuOTZsNy4zOTIgMjIuMjRjNC45MjggMTkuODA4IDI0LjczNiAyOS43MjggNDIuMDQ4IDI5LjcyOGg2MS45MmMxNy4zNDQgMCAzNC41OTItMTIuNDE2IDM5LjU1Mi0yOS43MjhsNy40MjQtMjIuMjRjMC0yLjQ5NiAyLjQ2NC0yLjQ5NiAyLjQ2NC00Ljk2aDcuNDI0bDE5LjgwOCA3LjQyNGM0Ljg5NiAyLjQ5NiAxMi4zNTIgMi40OTYgMTQuODQ4IDIuNDk2IDE0Ljg0OCAwIDI5LjY5Ni03LjQ1NiAzNy4wNTYtMTcuMzc2bDM5LjYxNi00OS40MDhjOS44ODgtMTIuNDE2IDEyLjM4NC0zNC43NTIgMi40NjQtNTEuOTY4bC0xMi4zODQtMTkuODA4YzAtMi40NjQtMi40MzItNC45Ni0yLjQzMi03LjQyNGw0Ljk2LTQuOTYgMjIuMTc2LTEyLjQxNmMxMi40NDgtMTQuODE2IDE5LjkwNC0zNy4wODggMTcuNDQtNTEuOTM2eiBtLTQyLjExMiA3LjQ4OGwtMjIuMjQgMTQuODE2Yy00LjkyOCA0Ljk2LTE5LjgwOCAxNy4zMTItMjIuMjQgMjkuNjY0LTIuNDk2IDEyLjQxNiAyLjQzMiAyOS43MjggOS44ODggMzcuMTUybDE0LjgxNiAyMi4yNzItMzcuMDI0IDUyaC0yLjU2bC0yNC43MDQtNy41MmMtMTcuMzEyLTQuODY0LTM0LjY1NiAwLTM5LjYxNiAyLjU2LTkuODg4IDQuOTYtMTkuNzc2IDE5Ljc3Ni0yNC42NzIgMjkuNjY0bC03LjQyNCAyMi4yNzItMi40NjQgMi40NjRoLTY0LjM1MmwtOS45Mi0xOS43NzZjLTIuNDMyLTEyLjM1Mi0xMi4zNTItMjcuMjY0LTIyLjI3Mi0zMi4xNi03LjQyNC01LjAyNC0xNy4yOC01LjAyNC0xOS44MDgtNS4wMjQtNy4zNiAwLTE0LjgxNiAyLjU2LTI0LjczNiA1LjAyNGwtMjIuMjA4IDcuNDU2aC00Ljk5MmwtMzcuMDg4LTQ5LjZ2LTQuOTZsMTIuMzg0LTE5LjcxMmM3LjM5Mi05Ljk1MiAxMi4zODQtMjQuNzM2IDkuOTUyLTM5LjY0OC0yLjUyOC0xNC44MTYtMTcuMzQ0LTI3LjE2OC0yNC43NjgtMzIuMTI4bC0yMi4yNC0xMi40MTZzLTIuNDY0LTIuNCAwLTIuNGwxNC44MTYtNjQuMzUyIDIyLjI3Mi0yLjQ5NmMxMi4zODQgMCAyOS43MjgtNy40MjQgMzcuMTItMTcuMzEyIDcuMzkyLTkuODU2IDEyLjM4NC0yNC43MzYgOS45Mi0zNy4xNTJsLTIuNDk2LTI0LjY3MmMwLTIuNTI4IDIuNDk2LTQuOTYgMi40OTYtNC45Nmw1NC40MzItMjQuNzM2aDQuOTZsMTcuMzEyIDE3LjMxMmM5LjkyIDcuNDI0IDIyLjI0IDE0LjgxNiAzNC42MjQgMTQuODE2IDEyLjQxNiAwIDI5LjY5Ni03LjM2IDM0LjY1Ni0xNy4yOGwxNy4zNDQtMTcuMzEyaDIuNDMybDU2LjkyOCAyNC43MzYgMi40MzIgMi40NjQtMi40MzIgMjQuNjcyYzAgNC45NiAwIDI0LjczNiA5Ljg4OCAzNy4xODQgNC45NiA3LjQyNCAxNy4zNDQgMTQuODE2IDM0LjY1NiAxNy4zMTJsMjIuMjQgMi40NjQgMi40OTYgMi40NjQgMTIuMzUyIDU5LjM2djcuNDg4aC0wLjE2eiIgcC1pZD0iNDQ5MyIgZmlsbD0iI2ZmZmZmZiI+PC9wYXRoPjxwYXRoIGQ9Ik03ODguOTI4IDY2NS4yNDhjLTYxLjg4OCAwLTExMy44NTYgNTEuOTM2LTExMy44NTYgMTEzLjg4OCAwIDYxLjg1NiA1MS45NjggMTEzLjg1NiAxMTMuODU2IDExMy44NTYgNTkuMzYgMCAxMTEuMzI4LTUyIDExMy43OTItMTEzLjg1NiAwLTYxLjk1Mi01MS45MzYtMTEzLjg4OC0xMTMuNzkyLTExMy44ODh6IG0wIDE4My4xNjhjLTM5LjYxNiAwLTcxLjgwOC0zMi4xOTItNzEuODA4LTcxLjc3NnMzMi4xOTItNzEuNzEyIDcxLjgwOC03MS43MTJjMzcuMDI0IDAgNjkuMjggMzIuMTI4IDcxLjcxMiA3MS43MTItMi40MzIgNDIuMDQ4LTM0LjY1NiA3MS43NzYtNzEuNzEyIDcxLjc3NnoiIHAtaWQ9IjQ0OTQiIGZpbGw9IiNmZmZmZmYiPjwvcGF0aD48L3N2Zz4=',
                visible : false,
                line : [],
                valueTime : '',

                pickerOptions: {
                    disabledDate(time) {
                        // return time.getTime() < Date.now() + 7*24*60*60*1000;
                    }
                },
                deliveryTypeCode : '',
                lineList : [],
                lineCheck : [],
                crowdTypeList : [],
                sexList : [],
                stationList : [],
                targList : [],
                targLists : [],
                publicCode : '',
                stationTarg : [],
                stationTargs : [],
                stationListCode : [],
                obj : {},
                changeLineText : '',
                testlist : [
                    {
                        companyLineId : 'A-4',
                        lineStationId : 'd-3',
                    }
                ],
                uploadForm: new FormData(),
                form : {
                    advertiseDeliveryId : '',
                    advertiseDeliveryType : '',
                    deliveryBegin : '',
                    deliveryEnd : '',
                    deliveryLineList :[],
                    deliveryStationList:[],
                    peopleGenderList : [],
                    peopleAgeList : [],

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
            changeFile(file, fileList){
                console.log(file)
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
            clearInit : function(){
                this.valueTime = '';
                this.form.advertiseDeliveryId = '';
                this.form.advertiseDeliveryType = '';
                this.form.deliveryBegin  = '';
                this.form.deliveryEnd = '';
                this.form.deliveryLineList = [];
                this.form.deliveryStationList = [];
                this.form.peopleGenderList = [];
                this.form.peopleAgeList = [];
                this.deliveryTypeCode = '';
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
                if(this.form.advertiseDeliveryType == ''){
                    this.$message({
                        message: '投放方式不能为空',
                        type: 'error',
                        offset: 300
                    });
                    validate = false;
                    return;
                }else{
                    validate = true;
                }
                if(this.form.deliveryBegin == ''){
                    this.$message({
                        message: '请设置投放开始时间',
                        type: 'error',
                        offset: 300
                    });
                    validate = false;
                    return;
                }else{
                    validate = true;
                }
                if(this.form.deliveryEnd == ''){
                    this.$message({
                        message: '请设置投放结束时间',
                        type: 'error',
                        offset: 300
                    });
                    validate = false;
                    return;
                }else{
                    validate = true;
                }
                if(this.form.deliveryLineList.length == 0 && this.form.deliveryStationList.length == 0){
                    this.$message({
                        message: '请选择投放线路或站点',
                        type: 'error',
                        offset: 300
                    });
                    validate = false;
                    return;
                }else{
                    validate = true;
                };
                // if(this.form.peopleGenderList.length == 0 ){
                //     this.$message({
                //         message: '投放受众人群类型不能为空',
                //         type: 'error',
                //         offset: 300
                //     });
                //     validate = false;
                //     return;
                // }else{
                //     validate = true;
                // }
                // if(this.form.peopleAgeList.length == 0 ){
                //     this.$message({
                //         message: '投放受众人群性别不能为空',
                //         type: 'error',
                //         offset: 300
                //     });
                //     validate = false;
                //     return;
                // }else{
                //     validate = true;
                // }
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
                if(validate){
                    if(this.form.advertiseDeliveryId == ''){
                        console.log(this.form);
                        this.uploadForm = new FormData()
                        this.uploadForm.append('data', JSON.stringify(this.form));
                        this.$refs.rectangleFilesEle.submit()  // 提交时触发了before-upload函数
                        this.$refs.squareFilesEle.submit()
                        setOperationAdvertiseSave(this.uploadForm).then((res) =>{
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
                        console.log(this.form);
                        this.uploadForm = new FormData()
                        this.uploadForm.append('data', JSON.stringify(this.form));
                        this.$refs.rectangleFilesEle.submit()  // 提交时触发了before-upload函数
                        this.$refs.squareFilesEle.submit()
                        setOperationAdvertiseUpdate(this.uploadForm).then((res) =>{
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
            filerCode : function(data){
                var arr = [];
                for(var i=0;i<data.length;i++){
                    for(var j=0;j<data[i].list.length;j++){
                        for(var k=0;k<data[i].list[j].length;k++){
                            if(data[i].list[j][k].code){
                                var obj = {
                                    companyLineId : '',
                                    lineStationId : '',
                                }
                                obj.companyLineId = data[i].list[j][k].companyLineId;
                                obj.lineStationId = data[i].list[j][k].lineStationId;
                                arr.push(obj)
                            }
                        }
                    }
                };
                return arr;
            },
            beforeUpImg(file) {
                this.uploadForm.append('rectangleFiles', file)
                return false
            },
            beforeUpVideo(file) {
                console.log('ok lalala')
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
            getAdvertiseTagAgeFn : function(){
                this.crowdTypeList = [];
                getAdvertiseTagAge().then((res) => {
                    if(res.data && res.data.code == 0){
                        console.log('受众人群');
                        console.log(res.data.data);
                        if(res.data.data.list.length > 0){
                            this.crowdTypeList = res.data.data.list;
                        }else{
                            this.crowdTypeList = [];
                        }
                    }
                })
            },
            getAdvertiseTagGenderFn : function(){
                this.sexList = [];
                getAdvertiseTagGender().then((res) => {
                    if(res.data && res.data.code == 0){
                        this.sexList = res.data.data.list;
                    }
                })
            },
            getStationFn : function(){
                this.stationList = [];
                getListByUser().then((res) => {
                    if(res.data && res.data.code == 0){
                        if(res.data.data.stationList.length > 0){
                            console.log('打印station')
                            console.log(res.data.data.stationList)
                            this.stationList = res.data.data.stationList;
                            this.targLists = this.targList = this.getTagList(this.stationList)
                            this.stationListCode = this.setCodeData(this.stationList);
                            this.initTargActive();
                        }else{
                            this.stationList = [];
                            this.targLists = this.targList = this.getTagList(this.stationList)
                            this.stationListCode = this.setCodeData(this.stationList);
                            this.initTargActive();
                        }
                    }
                })
            },
            getgetCompanyLineByUserFn : function(){
                this.lineCheck = [];
                getgetCompanyLineByUser().then((res) => {
                    if(res.data && res.data.code == 0){
                        if(res.data.data.list.length > 0){
                            this.lineCheck = res.data.data.list;
                        }
                    }
                })
            },
            //获取投放方式列表
            getAdvertiseDeliveryTypeFn : function(){
                this.lineList = [];
                getAdvertiseDeliveryType().then((res) => {
                    if(res.data && res.data.code == 0){
                        console.log(res.data.data)
                        if(res.data.data.list.length > 0){
                            this.lineList = res.data.data.list;
                        }
                    }
                })
            },
            changeType : function(){
                console.log(this.form.advertiseDeliveryType)
                for(let index in this.lineList){
                    if(this.lineList[index].advertiseDeliveryType == this.form.advertiseDeliveryType){
                        this.deliveryTypeCode = this.lineList[index].advertiseDeliveryTypeCode;
                    }
                }
            },
            showTime : function(){
                this.form.deliveryBegin  = '';
                this.form.deliveryEnd = '';
                if(this.valueTime){
                    this.form.deliveryBegin = this.valueTime[0];
                    this.form.deliveryEnd = this.valueTime[1];
                }
            },
            staticonClick : function(value){
                this.stationTargs = [];
                for(let index in this.stationTarg){
                    if(this.stationTarg[index].lineStationId == value){
                        this.stationTarg[index].code = !this.stationTarg[index].code;
                    }
                };
                this.stationTargs = this.stationTarg;
                this.form.deliveryStationList = this.filerCode(this.stationListCode);
            },
            changeLine : function(){
                this.obj.list.reverse();
                this.stationTargs =  this.stationTarg = this.obj.list[0];
                this.changeLineText = this.obj.list[0][0].busStationCode + ' → ' +  this.obj.list[0][this.obj.list[0].length - 1].busStationCode;
            },
            filterStationList : function(value){
                for(let index in this.stationListCode){
                    if(this.stationListCode[index].companyLineCode == value){
                        return this.stationListCode[index];
                    }
                }
            },
            setCodeData : function(data){
                console.log(data)
                for(var i=0;i<data.length;i++){
                    for(var j=0;j<data[i].list.length;j++){
                        for(var k=0;k<data[i].list[j].length;k++){
                            data[i].list[j][k].code = false
                        }
                    }
                };
                return data;
            },
            targClick : function(value){
                for(let index in this.targList){
                    this.targList[index].code = 0;
                }
                for(let index in this.targList){
                    if(this.targList[index].companyLineCode == value){
                        this.targList[index].code =  1;
                    }
                }
                this.targLists = [];
                this.targLists = this.targList;

                this.obj = this.filterStationList(value);
                this.stationTargs =  this.stationTarg = this.obj.list[0];
                this.changeLineText = this.obj.list[0][0].busStationCode + ' → ' +  this.obj.list[0][this.obj.list[0].length - 1].busStationCode;

            },
            getTagList : function(data){
                var arr = [];
                for(let index in data){
                    var obj = {
                        companyLineCode : '',
                        code : 0
                    }
                    obj.companyLineCode = data[index].companyLineCode;
                    arr.push(obj)
                };
                return arr;
            },
            initTargActive : function(){
                this.targClick(this.targList[0].companyLineCode)
            },
            initRetCode : function(data){
                for(var n=0;n<data.length;n++){
                    for(var i=0;i<this.stationListCode.length;i++){
                        for(var j=0;j<this.stationListCode[i].list.length;j++){
                            for(var k=0;k<this.stationListCode[i].list[j].length;k++){
                                if(this.stationListCode[i].list[j][k].lineStationId == data[n].lineStationId){
                                    this.stationListCode[i].list[j][k].code = true;
                                };
                            }
                        }
                    }
                }
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
                this.visible = true;
                this.getAdvertiseDeliveryTypeFn();
                this.getgetCompanyLineByUserFn();
                this.getStationFn();
                this.getAdvertiseTagAgeFn();
                this.getAdvertiseTagGenderFn();
                if(id !== ''){
                    this.clearInit();
                    getOperationAdvertiseInfo({
                        advertiseId : id
                    }).then((res) => {
                        if(res.data && res.data.code == 0){
                            console.log(res.data.data);
                            this.valueTime = [];
                            this.form.advertiseDeliveryId = res.data.data.data.advertiseDeliveryId;
                            this.form.advertiseDeliveryType = res.data.data.data.advertiseDeliveryType;
                            this.form.deliveryBegin  = res.data.data.data.deliveryBegin;
                            this.form.deliveryEnd = res.data.data.data.deliveryEnd;
                            this.form.deliveryLineList = res.data.data.data.outDeliveryLineList;
                            this.form.deliveryStationList = res.data.data.data.deliveryStationList;
                            this.form.peopleGenderList = res.data.data.data.peopleGenderList;
                            this.form.peopleAgeList = res.data.data.data.peopleAgeList;
                            this.valueTime.push(this.form.deliveryBegin);
                            this.valueTime.push(this.form.deliveryEnd);
                            this.initRetCode(this.form.deliveryStationList);
                            console.log(this.stationListCode)
                            if(this.form.deliveryLineList.length > 0){
                                this.deliveryTypeCode = 'line';
                            };
                            if(this.form.deliveryStationList.length > 0){
                                this.deliveryTypeCode = 'station';
                            }
                        }
                    })
                    console.log('修改广告')
                    console.log(id)
                }else{
                    this.clearInit();

                    console.log('上传广告')
                    console.log(id)
                }

            },
            closeDialog(){
                this.visible = false;
                this.clearInit();
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

