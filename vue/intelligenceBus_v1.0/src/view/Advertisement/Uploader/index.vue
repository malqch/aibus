<!--
 * @Author : SongWenXin
 * @Mail : songwenxin666@sina.com
 * @ProjectName : intelligenceBus_v1.0
 * @FileName : index
 * @Date :  2020-11-07 09:24
-->
<template>
    <div class="table_div" style="padding-top: 0px; padding-bottom: 15px;">
        <div class="controlertab_list">
            <ul class="tab">
                <li  v-for="item in tablists" :key="item.itemId" @click="tabClick(item.itemId)">
                    <div :class="['table_item', item.code == 1 ? 'actinveon' : 'actinveoff']" >{{item.itemName}}</div>
                </li>
            </ul>
            <el-button  @click="showDialogUpData" type="primary" style="position: absolute;right:0;top:14px;"> <i class="fa fa-globe" style="margin-right:5px;"></i>投放广告</el-button>
        </div>
        <div style="width:100%;float: left;" >
            <div   :class="['ad_card', setBgColor(item.checkStatus)]" v-for="item in list" :key="item.advertiseDeliveryId" @click="showDialog(item)">
                <el-row >
                    <el-col :span="24">
                        <div class="card_icon">
                            <div class="card_dashed">
                                <img :src="adIcon" alt="" >
                            </div>
                        </div>
                        <ul class="card_text" style="color:#fff;">
                            <li><span>广告编号</span> ：{{item.advertiseNo}}</li>
                            <li><span>投放设备</span> ：{{item.deviceName}}</li>
                            <li><span>创建时间</span> ：{{item.createdDate}}</li>
                            <li><span>广告状态</span> ：{{setStatusText(item.checkStatus)}}</li>
                        </ul>

                    </el-col>
                </el-row>
                <el-row v-if="item.checkStatus == 3 || item.checkStatus == 9">
                    <el-col :span="24" style="box-sizing: border-box;padding: 0px 10px 5px 10px;">
                        <div style="color:#fff;font-size:12px;" class="show_time">投放剩余时间 : {{item.remainingTime}}</div>
                        <el-progress :text-inside="false" :stroke-width="10" :percentage="parseInt((item.useTime/item.totalTime)*100)" :color="setBarColor(item.checkStatus)"></el-progress>
                    </el-col>
                </el-row>
                <el-row v-if="item.checkStatus == 4">
                    <el-col :span="24" style="box-sizing: border-box;padding: 0px 10px 0px 10px;">
                        <ul class="wg_item">
                            <li class="wg_title">{{setListTitle(item.checkItemList)}}</li>
                            <li class="wg_text">
                                <el-popover
                                        placement="top-start"
                                        title="违规描述"
                                        width="200"
                                        trigger="click"
                                        :content="item.checkSuggest">
                                    <span slot="reference">违规描述 : {{item.checkSuggest}}</span>
                                </el-popover>
                            </li>
                        </ul>
                    </el-col>
                </el-row>
            </div>
            <div style="width:100%;height:240px;background-color:#f1f1f1;float: left;" v-if="list.length == 0">
                <div style="width:100%;display: flex;justify-content: center;"><img :src="adIcon1" alt="" style="width: 60px;height:75px;margin-top: 80px;"></div>
                <div style="width:100%;display:flex;justify-content:center;color:#;font-size:18px;">无广告卡片信息</div>
            </div>
        </div>
        <DialogDefault v-show="DialogDefaultFlag" ref="DialogDefaultEle"></DialogDefault>
        <Dialog v-show="DialogFlag" ref="DialogEle"></Dialog>
        <DialogUpdata v-show="DialogUpdataFlag" ref="DialogUpdataEle" @regetData="getTabFn()"></DialogUpdata>
        <Dialogs v-show="DialogFlags"  ref="DialogEles" @regetData="getCardListFn()"></Dialogs>

    </div>
</template>

<script>
    import {
        getDeliveryTab,
        getAdvertiseList,
        getOperationAdvertiseConfirm,
        getOperationAdvertiseDelete
    } from '@/api/Advertisement';
    import DialogDefault from './DialogDefault';
    import Dialogs from './Dialogs';
    import Dialog from './Dialog';
    import DialogUpdata from './DialogUpdata';
    export default {
        name: "index",
        components : {
            DialogDefault,
            Dialog,
            DialogUpdata,
            Dialogs
        },
        data(){
            return{
                adIcon : 'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNjA0NzE1MzI4NjEzIiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjQ0OTAiIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNNzA3LjI5NiAyNzQuMjcyYzAtNDQuNTEyLTcuNDU2LTc5LjItMjcuMjk2LTk4Ljk3Ni0xOS43NDQtMjIuMjA4LTUxLjkzNi0zNy4wODgtOTQuMDE2LTM3LjA4OGgtNzkuMTM2djI2OS43MjhoNzQuMjA4YzQyLjA0OCAwIDc2LjczNi0xMi4zODQgOTYuNTQ0LTM3LjA4OCAxOS43NzYtMTkuODQgMjkuNjk2LTU0LjQ5NiAyOS42OTYtOTYuNTc2eiIgcC1pZD0iNDQ5MSIgZmlsbD0iI2ZmZmZmZiI+PC9wYXRoPjxwYXRoIGQ9Ik01ODguNTEyIDk1Mi4yODhIMTMwLjY4OGMtNDIuMDQ4IDAtNzkuMi0zMi4xNi03OS4yLTc0LjIwOFY1MTYuOGg3NDIuMzY4YzI0LjcwNCAwIDQ5LjQ3MiAyLjQzMiA0OS40NzIgNC45NlYxMzUuNzQ0YzAtNjkuMzQ0LTUxLjkzNi0xMjMuNzQ0LTExOC43ODQtMTIzLjc0NEgxMzAuNjg4QzYxLjQ0IDEyIDIuMDE2IDY4Ljg5NiAyLjAxNiAxMzUuNzQ0djc0Mi4zMDRjMCA2OS4zMTIgNTkuNDI0IDEyMy43MTIgMTI4LjY3MiAxMjMuNzEyaDUxMi4yNTZjLTE5Ljg0LTEyLjMyLTM3LjE1Mi0yOS42MzItNTQuNDMyLTQ5LjQ3MnogbS0xMjEuMjgtODQ4LjcwNGgxMjMuNzQ0YzUxLjk2OCAwIDk0LjA0OCAxNy4yOCAxMjEuMTg0IDQ5LjUwNCAyMi4zMzYgMjkuNjY0IDM3LjE1MiA2OS4yMTYgMzcuMTUyIDEyMS4yMTYgMCA1MS45NjgtMTQuODE2IDk0LjA0OC00Mi4wMTYgMTIzLjc3Ni0yOS43MjggMzIuMTI4LTY5LjI4IDQ5LjQ3Mi0xMjMuNzEyIDQ5LjQ3MmgtMTE4LjgxNlYxMDMuNTg0aDIuNDY0eiBtLTIzNS4xMDQgMGgzOS41ODRsMTM2LjA5NiAzNDEuNDcySDM2NS43NmwtMzcuMTUyLTk5LjAwOEgxNzUuMjMybC0zNy4xMiA5OS4wMDhIOTYuMDMyTDIzMi4xMjggMTAzLjU4NHoiIHAtaWQ9IjQ0OTIiIGZpbGw9IiNmZmZmZmYiPjwvcGF0aD48cGF0aCBkPSJNMjU0LjQzMiAxNTUuNTJMMTkwLjA0OCAzMTMuODg4aDEyMy43NzZMMjU0LjQzMiAxNTUuNTJ6IG0yNTkuODA4IDQ5Mi40MTZIMTE4LjMwNGEyNC43NjggMjQuNzY4IDAgMCAwIDAgNDkuNTA0SDUxNC4yNGMxMi4zNTIgMCAyNC43NjgtMTIuMzUyIDI0Ljc2OC0yNC43MzYgMC0xMi40MTYtMTIuNDE2LTI0Ljc2OC0yNC43NjgtMjQuNzY4eiBtMCAxNDMuNTJIMTE4LjMwNGEyNC44IDI0LjggMCAwIDAtMjQuNzM2IDI0LjczNmMwIDE0Ljg4IDkuODg4IDI0LjczNiAyNC43MzYgMjQuNzM2SDUxNC4yNGEyNC44MzIgMjQuODMyIDAgMCAwIDI0Ljc2OC0yNC43MzZjMC0xMi4zMi0xMi40MTYtMjQuNzM2LTI0Ljc2OC0yNC43MzZ6IG01MDcuMjk2LTM3LjEybC0xMi40NDgtNjEuODU2Yy00LjkyOC0xNy4zNzYtMjIuMjQtMzIuMjI0LTM5LjUyLTM0LjYyNGwtMjQuNzM2LTIuNTI4Yy0yLjQ2NCAwLTIuNDY0IDAtNC44OTYtMi40MzJ2LTkuOTJsMi40MzItMjQuNzY4YzIuNDY0LTE5Ljc3Ni03LjM5Mi0zOS41NTItMjQuNzY4LTQ3LjAwOEw4NjAuNzA0IDU0NGgtMi40MzJjLTQuOTYtMi40NjQtOS45Mi0yLjQ2NC0xNC44OC0yLjQ2NC0xMi4zNTIgMC0yNC43MzYgNC45MjgtMzIuMTYgMTIuMzUybC0xNy4zMTIgMTcuMzEyLTQuOTI4IDQuOTI4Yy0yLjQ5NiAwLTIuNDk2LTIuNC00Ljk2LTQuOTI4bC0xNy4zNzYtMTcuMzEyYy03LjM5Mi03LjM5Mi0xOS43NDQtMTIuMzUyLTMyLjA5Ni0xMi4zNTItOS45NTIgMC0xNy4zNzYgMi40NjQtMTkuODA4IDQuOTI4bC01Ni44OTYgMjcuMjY0Yy0xNC44NDggOS44ODgtMjcuMjY0IDI3LjItMjQuNzM2IDQ0LjQ4bDIuNDMyIDI3LjI2NHY0Ljk2cy00LjkyOCAyLjQ2NC03LjM5MiAyLjQ2NGwtMjIuMjcyIDIuNDMyYy0xNy4yOCAyLjUyOC0zNC42NTYgMTQuODgtMzkuNTg0IDM0LjY4OGwtMTIuMzUyIDYxLjkyYy00Ljk2IDE3LjI0OCAyLjQ2NCAzNy4wMjQgMTkuNzc2IDQ5LjQ0bDE5LjgwOCAxMi40MTYgNy40MjQgNy4zOTJjMCAyLjQ2NCAwIDIuNDY0LTIuNTI4IDQuOTI4bC0xNC43ODQgMjIuMjcyYy05Ljk1MiAxNC43ODQtOS45NTIgMzcuMDg4IDIuNCA1MS45MzZsMzkuNjQ4IDUxLjkzNmM5Ljg1NiA5LjkyIDIyLjI3MiAxNy4zNzYgMzQuNjI0IDE3LjM3NiA0LjkyOCAwIDkuODg4IDAgMTQuODQ4LTIuNDk2bDI0LjcwNC03LjQyNGg3LjQ1NmMwIDIuNDY0IDIuNDMyIDIuNDY0IDIuNDMyIDQuOTZsNy4zOTIgMjIuMjRjNC45MjggMTkuODA4IDI0LjczNiAyOS43MjggNDIuMDQ4IDI5LjcyOGg2MS45MmMxNy4zNDQgMCAzNC41OTItMTIuNDE2IDM5LjU1Mi0yOS43MjhsNy40MjQtMjIuMjRjMC0yLjQ5NiAyLjQ2NC0yLjQ5NiAyLjQ2NC00Ljk2aDcuNDI0bDE5LjgwOCA3LjQyNGM0Ljg5NiAyLjQ5NiAxMi4zNTIgMi40OTYgMTQuODQ4IDIuNDk2IDE0Ljg0OCAwIDI5LjY5Ni03LjQ1NiAzNy4wNTYtMTcuMzc2bDM5LjYxNi00OS40MDhjOS44ODgtMTIuNDE2IDEyLjM4NC0zNC43NTIgMi40NjQtNTEuOTY4bC0xMi4zODQtMTkuODA4YzAtMi40NjQtMi40MzItNC45Ni0yLjQzMi03LjQyNGw0Ljk2LTQuOTYgMjIuMTc2LTEyLjQxNmMxMi40NDgtMTQuODE2IDE5LjkwNC0zNy4wODggMTcuNDQtNTEuOTM2eiBtLTQyLjExMiA3LjQ4OGwtMjIuMjQgMTQuODE2Yy00LjkyOCA0Ljk2LTE5LjgwOCAxNy4zMTItMjIuMjQgMjkuNjY0LTIuNDk2IDEyLjQxNiAyLjQzMiAyOS43MjggOS44ODggMzcuMTUybDE0LjgxNiAyMi4yNzItMzcuMDI0IDUyaC0yLjU2bC0yNC43MDQtNy41MmMtMTcuMzEyLTQuODY0LTM0LjY1NiAwLTM5LjYxNiAyLjU2LTkuODg4IDQuOTYtMTkuNzc2IDE5Ljc3Ni0yNC42NzIgMjkuNjY0bC03LjQyNCAyMi4yNzItMi40NjQgMi40NjRoLTY0LjM1MmwtOS45Mi0xOS43NzZjLTIuNDMyLTEyLjM1Mi0xMi4zNTItMjcuMjY0LTIyLjI3Mi0zMi4xNi03LjQyNC01LjAyNC0xNy4yOC01LjAyNC0xOS44MDgtNS4wMjQtNy4zNiAwLTE0LjgxNiAyLjU2LTI0LjczNiA1LjAyNGwtMjIuMjA4IDcuNDU2aC00Ljk5MmwtMzcuMDg4LTQ5LjZ2LTQuOTZsMTIuMzg0LTE5LjcxMmM3LjM5Mi05Ljk1MiAxMi4zODQtMjQuNzM2IDkuOTUyLTM5LjY0OC0yLjUyOC0xNC44MTYtMTcuMzQ0LTI3LjE2OC0yNC43NjgtMzIuMTI4bC0yMi4yNC0xMi40MTZzLTIuNDY0LTIuNCAwLTIuNGwxNC44MTYtNjQuMzUyIDIyLjI3Mi0yLjQ5NmMxMi4zODQgMCAyOS43MjgtNy40MjQgMzcuMTItMTcuMzEyIDcuMzkyLTkuODU2IDEyLjM4NC0yNC43MzYgOS45Mi0zNy4xNTJsLTIuNDk2LTI0LjY3MmMwLTIuNTI4IDIuNDk2LTQuOTYgMi40OTYtNC45Nmw1NC40MzItMjQuNzM2aDQuOTZsMTcuMzEyIDE3LjMxMmM5LjkyIDcuNDI0IDIyLjI0IDE0LjgxNiAzNC42MjQgMTQuODE2IDEyLjQxNiAwIDI5LjY5Ni03LjM2IDM0LjY1Ni0xNy4yOGwxNy4zNDQtMTcuMzEyaDIuNDMybDU2LjkyOCAyNC43MzYgMi40MzIgMi40NjQtMi40MzIgMjQuNjcyYzAgNC45NiAwIDI0LjczNiA5Ljg4OCAzNy4xODQgNC45NiA3LjQyNCAxNy4zNDQgMTQuODE2IDM0LjY1NiAxNy4zMTJsMjIuMjQgMi40NjQgMi40OTYgMi40NjQgMTIuMzUyIDU5LjM2djcuNDg4aC0wLjE2eiIgcC1pZD0iNDQ5MyIgZmlsbD0iI2ZmZmZmZiI+PC9wYXRoPjxwYXRoIGQ9Ik03ODguOTI4IDY2NS4yNDhjLTYxLjg4OCAwLTExMy44NTYgNTEuOTM2LTExMy44NTYgMTEzLjg4OCAwIDYxLjg1NiA1MS45NjggMTEzLjg1NiAxMTMuODU2IDExMy44NTYgNTkuMzYgMCAxMTEuMzI4LTUyIDExMy43OTItMTEzLjg1NiAwLTYxLjk1Mi01MS45MzYtMTEzLjg4OC0xMTMuNzkyLTExMy44ODh6IG0wIDE4My4xNjhjLTM5LjYxNiAwLTcxLjgwOC0zMi4xOTItNzEuODA4LTcxLjc3NnMzMi4xOTItNzEuNzEyIDcxLjgwOC03MS43MTJjMzcuMDI0IDAgNjkuMjggMzIuMTI4IDcxLjcxMiA3MS43MTItMi40MzIgNDIuMDQ4LTM0LjY1NiA3MS43NzYtNzEuNzEyIDcxLjc3NnoiIHAtaWQ9IjQ0OTQiIGZpbGw9IiNmZmZmZmYiPjwvcGF0aD48L3N2Zz4=',
                adIcon1 : 'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNjA0NzQ1ODUzNjQ2IiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjQzNTciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNNzA3LjI5NiAyNzQuMjcyYzAtNDQuNTEyLTcuNDU2LTc5LjItMjcuMjk2LTk4Ljk3Ni0xOS43NDQtMjIuMjA4LTUxLjkzNi0zNy4wODgtOTQuMDE2LTM3LjA4OGgtNzkuMTM2djI2OS43MjhoNzQuMjA4YzQyLjA0OCAwIDc2LjczNi0xMi4zODQgOTYuNTQ0LTM3LjA4OCAxOS43NzYtMTkuODQgMjkuNjk2LTU0LjQ5NiAyOS42OTYtOTYuNTc2eiIgcC1pZD0iNDM1OCI+PC9wYXRoPjxwYXRoIGQ9Ik01ODguNTEyIDk1Mi4yODhIMTMwLjY4OGMtNDIuMDQ4IDAtNzkuMi0zMi4xNi03OS4yLTc0LjIwOFY1MTYuOGg3NDIuMzY4YzI0LjcwNCAwIDQ5LjQ3MiAyLjQzMiA0OS40NzIgNC45NlYxMzUuNzQ0YzAtNjkuMzQ0LTUxLjkzNi0xMjMuNzQ0LTExOC43ODQtMTIzLjc0NEgxMzAuNjg4QzYxLjQ0IDEyIDIuMDE2IDY4Ljg5NiAyLjAxNiAxMzUuNzQ0djc0Mi4zMDRjMCA2OS4zMTIgNTkuNDI0IDEyMy43MTIgMTI4LjY3MiAxMjMuNzEyaDUxMi4yNTZjLTE5Ljg0LTEyLjMyLTM3LjE1Mi0yOS42MzItNTQuNDMyLTQ5LjQ3MnogbS0xMjEuMjgtODQ4LjcwNGgxMjMuNzQ0YzUxLjk2OCAwIDk0LjA0OCAxNy4yOCAxMjEuMTg0IDQ5LjUwNCAyMi4zMzYgMjkuNjY0IDM3LjE1MiA2OS4yMTYgMzcuMTUyIDEyMS4yMTYgMCA1MS45NjgtMTQuODE2IDk0LjA0OC00Mi4wMTYgMTIzLjc3Ni0yOS43MjggMzIuMTI4LTY5LjI4IDQ5LjQ3Mi0xMjMuNzEyIDQ5LjQ3MmgtMTE4LjgxNlYxMDMuNTg0aDIuNDY0eiBtLTIzNS4xMDQgMGgzOS41ODRsMTM2LjA5NiAzNDEuNDcySDM2NS43NmwtMzcuMTUyLTk5LjAwOEgxNzUuMjMybC0zNy4xMiA5OS4wMDhIOTYuMDMyTDIzMi4xMjggMTAzLjU4NHoiIHAtaWQ9IjQzNTkiPjwvcGF0aD48cGF0aCBkPSJNMjU0LjQzMiAxNTUuNTJMMTkwLjA0OCAzMTMuODg4aDEyMy43NzZMMjU0LjQzMiAxNTUuNTJ6IG0yNTkuODA4IDQ5Mi40MTZIMTE4LjMwNGEyNC43NjggMjQuNzY4IDAgMCAwIDAgNDkuNTA0SDUxNC4yNGMxMi4zNTIgMCAyNC43NjgtMTIuMzUyIDI0Ljc2OC0yNC43MzYgMC0xMi40MTYtMTIuNDE2LTI0Ljc2OC0yNC43NjgtMjQuNzY4eiBtMCAxNDMuNTJIMTE4LjMwNGEyNC44IDI0LjggMCAwIDAtMjQuNzM2IDI0LjczNmMwIDE0Ljg4IDkuODg4IDI0LjczNiAyNC43MzYgMjQuNzM2SDUxNC4yNGEyNC44MzIgMjQuODMyIDAgMCAwIDI0Ljc2OC0yNC43MzZjMC0xMi4zMi0xMi40MTYtMjQuNzM2LTI0Ljc2OC0yNC43MzZ6IG01MDcuMjk2LTM3LjEybC0xMi40NDgtNjEuODU2Yy00LjkyOC0xNy4zNzYtMjIuMjQtMzIuMjI0LTM5LjUyLTM0LjYyNGwtMjQuNzM2LTIuNTI4Yy0yLjQ2NCAwLTIuNDY0IDAtNC44OTYtMi40MzJ2LTkuOTJsMi40MzItMjQuNzY4YzIuNDY0LTE5Ljc3Ni03LjM5Mi0zOS41NTItMjQuNzY4LTQ3LjAwOEw4NjAuNzA0IDU0NGgtMi40MzJjLTQuOTYtMi40NjQtOS45Mi0yLjQ2NC0xNC44OC0yLjQ2NC0xMi4zNTIgMC0yNC43MzYgNC45MjgtMzIuMTYgMTIuMzUybC0xNy4zMTIgMTcuMzEyLTQuOTI4IDQuOTI4Yy0yLjQ5NiAwLTIuNDk2LTIuNC00Ljk2LTQuOTI4bC0xNy4zNzYtMTcuMzEyYy03LjM5Mi03LjM5Mi0xOS43NDQtMTIuMzUyLTMyLjA5Ni0xMi4zNTItOS45NTIgMC0xNy4zNzYgMi40NjQtMTkuODA4IDQuOTI4bC01Ni44OTYgMjcuMjY0Yy0xNC44NDggOS44ODgtMjcuMjY0IDI3LjItMjQuNzM2IDQ0LjQ4bDIuNDMyIDI3LjI2NHY0Ljk2cy00LjkyOCAyLjQ2NC03LjM5MiAyLjQ2NGwtMjIuMjcyIDIuNDMyYy0xNy4yOCAyLjUyOC0zNC42NTYgMTQuODgtMzkuNTg0IDM0LjY4OGwtMTIuMzUyIDYxLjkyYy00Ljk2IDE3LjI0OCAyLjQ2NCAzNy4wMjQgMTkuNzc2IDQ5LjQ0bDE5LjgwOCAxMi40MTYgNy40MjQgNy4zOTJjMCAyLjQ2NCAwIDIuNDY0LTIuNTI4IDQuOTI4bC0xNC43ODQgMjIuMjcyYy05Ljk1MiAxNC43ODQtOS45NTIgMzcuMDg4IDIuNCA1MS45MzZsMzkuNjQ4IDUxLjkzNmM5Ljg1NiA5LjkyIDIyLjI3MiAxNy4zNzYgMzQuNjI0IDE3LjM3NiA0LjkyOCAwIDkuODg4IDAgMTQuODQ4LTIuNDk2bDI0LjcwNC03LjQyNGg3LjQ1NmMwIDIuNDY0IDIuNDMyIDIuNDY0IDIuNDMyIDQuOTZsNy4zOTIgMjIuMjRjNC45MjggMTkuODA4IDI0LjczNiAyOS43MjggNDIuMDQ4IDI5LjcyOGg2MS45MmMxNy4zNDQgMCAzNC41OTItMTIuNDE2IDM5LjU1Mi0yOS43MjhsNy40MjQtMjIuMjRjMC0yLjQ5NiAyLjQ2NC0yLjQ5NiAyLjQ2NC00Ljk2aDcuNDI0bDE5LjgwOCA3LjQyNGM0Ljg5NiAyLjQ5NiAxMi4zNTIgMi40OTYgMTQuODQ4IDIuNDk2IDE0Ljg0OCAwIDI5LjY5Ni03LjQ1NiAzNy4wNTYtMTcuMzc2bDM5LjYxNi00OS40MDhjOS44ODgtMTIuNDE2IDEyLjM4NC0zNC43NTIgMi40NjQtNTEuOTY4bC0xMi4zODQtMTkuODA4YzAtMi40NjQtMi40MzItNC45Ni0yLjQzMi03LjQyNGw0Ljk2LTQuOTYgMjIuMTc2LTEyLjQxNmMxMi40NDgtMTQuODE2IDE5LjkwNC0zNy4wODggMTcuNDQtNTEuOTM2eiBtLTQyLjExMiA3LjQ4OGwtMjIuMjQgMTQuODE2Yy00LjkyOCA0Ljk2LTE5LjgwOCAxNy4zMTItMjIuMjQgMjkuNjY0LTIuNDk2IDEyLjQxNiAyLjQzMiAyOS43MjggOS44ODggMzcuMTUybDE0LjgxNiAyMi4yNzItMzcuMDI0IDUyaC0yLjU2bC0yNC43MDQtNy41MmMtMTcuMzEyLTQuODY0LTM0LjY1NiAwLTM5LjYxNiAyLjU2LTkuODg4IDQuOTYtMTkuNzc2IDE5Ljc3Ni0yNC42NzIgMjkuNjY0bC03LjQyNCAyMi4yNzItMi40NjQgMi40NjRoLTY0LjM1MmwtOS45Mi0xOS43NzZjLTIuNDMyLTEyLjM1Mi0xMi4zNTItMjcuMjY0LTIyLjI3Mi0zMi4xNi03LjQyNC01LjAyNC0xNy4yOC01LjAyNC0xOS44MDgtNS4wMjQtNy4zNiAwLTE0LjgxNiAyLjU2LTI0LjczNiA1LjAyNGwtMjIuMjA4IDcuNDU2aC00Ljk5MmwtMzcuMDg4LTQ5LjZ2LTQuOTZsMTIuMzg0LTE5LjcxMmM3LjM5Mi05Ljk1MiAxMi4zODQtMjQuNzM2IDkuOTUyLTM5LjY0OC0yLjUyOC0xNC44MTYtMTcuMzQ0LTI3LjE2OC0yNC43NjgtMzIuMTI4bC0yMi4yNC0xMi40MTZzLTIuNDY0LTIuNCAwLTIuNGwxNC44MTYtNjQuMzUyIDIyLjI3Mi0yLjQ5NmMxMi4zODQgMCAyOS43MjgtNy40MjQgMzcuMTItMTcuMzEyIDcuMzkyLTkuODU2IDEyLjM4NC0yNC43MzYgOS45Mi0zNy4xNTJsLTIuNDk2LTI0LjY3MmMwLTIuNTI4IDIuNDk2LTQuOTYgMi40OTYtNC45Nmw1NC40MzItMjQuNzM2aDQuOTZsMTcuMzEyIDE3LjMxMmM5LjkyIDcuNDI0IDIyLjI0IDE0LjgxNiAzNC42MjQgMTQuODE2IDEyLjQxNiAwIDI5LjY5Ni03LjM2IDM0LjY1Ni0xNy4yOGwxNy4zNDQtMTcuMzEyaDIuNDMybDU2LjkyOCAyNC43MzYgMi40MzIgMi40NjQtMi40MzIgMjQuNjcyYzAgNC45NiAwIDI0LjczNiA5Ljg4OCAzNy4xODQgNC45NiA3LjQyNCAxNy4zNDQgMTQuODE2IDM0LjY1NiAxNy4zMTJsMjIuMjQgMi40NjQgMi40OTYgMi40NjQgMTIuMzUyIDU5LjM2djcuNDg4aC0wLjE2eiIgcC1pZD0iNDM2MCI+PC9wYXRoPjxwYXRoIGQ9Ik03ODguOTI4IDY2NS4yNDhjLTYxLjg4OCAwLTExMy44NTYgNTEuOTM2LTExMy44NTYgMTEzLjg4OCAwIDYxLjg1NiA1MS45NjggMTEzLjg1NiAxMTMuODU2IDExMy44NTYgNTkuMzYgMCAxMTEuMzI4LTUyIDExMy43OTItMTEzLjg1NiAwLTYxLjk1Mi01MS45MzYtMTEzLjg4OC0xMTMuNzkyLTExMy44ODh6IG0wIDE4My4xNjhjLTM5LjYxNiAwLTcxLjgwOC0zMi4xOTItNzEuODA4LTcxLjc3NnMzMi4xOTItNzEuNzEyIDcxLjgwOC03MS43MTJjMzcuMDI0IDAgNjkuMjggMzIuMTI4IDcxLjcxMiA3MS43MTItMi40MzIgNDIuMDQ4LTM0LjY1NiA3MS43NzYtNzEuNzEyIDcxLjc3NnoiIHAtaWQ9IjQzNjEiPjwvcGF0aD48L3N2Zz4=',
                tablist : [],
                tablists : [],
                list : [

                ],
                cardForm : {
                    advertiseNo : '',
                    code : '',
                    group : ''
                },
                DialogDefaultFlag : false,
                DialogFlag : false,
                DialogUpdataFlag : false,
                DialogFlags : false
            }
        },
        methods : {
            showDialogUpData : function(){
                this.$nextTick(() => {
                    var id = '';
                    this.DialogUpdataFlag = true;
                    this.$refs.DialogUpdataEle.init(id)
                });
            },
            showDialog: function(item){
                if(item.checkStatus == 4){
                    this.$nextTick(() => {
                        var id = item.advertiseDeliveryId;
                        this.DialogUpdataFlag = true;
                        this.$refs.DialogUpdataEle.init(id);

                    });
                };
                if(item.checkStatus == 9){
                    this.$nextTick(() => {
                        var id = item.advertiseDeliveryId;
                        this.DialogDefaultFlag = true;
                        this.$refs.DialogDefaultEle.init(id);

                    });
                };
                if(item.checkStatus == 3 || item.checkStatus == 1){
                    this.$nextTick(() => {
                        var id = item.advertiseDeliveryId;
                        this.DialogFlag = true;
                        this.$refs.DialogEle.init(id)
                    });
                };
                if(item.checkStatus == 2){
                    this.$nextTick(() => {
                        var id = item.advertiseDeliveryId;
                        this.DialogFlags = true;
                        this.$refs.DialogEles.init(id)
                    });
                    return;

                    this.$nextTick(() => {
                        var id = item.advertiseDeliveryId;
                        this.$confirm('是否确定投放广告！', '提示', {
                            confirmButtonText: '确定投放',
                            cancelButtonText: '取消投放',
                            type: 'warning'
                        }).then(() => {
                            getOperationAdvertiseConfirm({
                                advertiseId : id
                            }).then((res) => {
                                if(res.data && res.data.code == 0){
                                    this.$message({
                                        type: 'success',
                                        message: '确定投放中',
                                        onClose: () => {
                                            this.getCardListFn();
                                        }
                                    });
                                }
                            })

                        }).catch(() => {
                            getOperationAdvertiseDelete({
                                advertiseId : id
                            }).then((res) => {
                                if(res.data && res.data.code == 0){
                                    this.$message({
                                        type: 'info',
                                        message: '已删除此广告',
                                        onClose: () => {
                                            this.getCardListFn();
                                        }
                                    });
                                }
                            })

                        });
                    });
                }

            },
            setStatusText : function(status){
                console.log('ok')
                //投放中
                if(status == 3){
                    return '投放中';
                };
                //投放结束
                if(status == 9){
                    return '投放结束';
                };
                //审核未通过
                if(status == 4){
                    return '审核未通过';
                };
                //待审核
                if(status == 1){
                    return '待审核';
                };
                //待确认
                if(status == 2){
                    return '待确认';
                };
            },
            setListTitle : function(data){
                var str = '';
                for(let index in data){
                    str += data[index].itemName + '、'
                };
                return str.slice(0, -1);
            },
            setBarColor : function(status){
                //投放中
                if(status == 3){
                    return '#85FF00';
                };
                //投放结束
                if(status == 9){
                    return '#E30000';
                };
            },
            setBgColor : function(status){
                //投放中
                if(status == 3){
                    return 'card_bg1';
                };
                //投放结束
                if(status == 9){
                    return 'card_bg2';
                };
                //审核未通过
                if(status == 4){
                    return 'card_bg3';
                };
                //待审核
                if(status == 1){
                    return 'card_bg4';
                };
                //待确认
                if(status == 2){
                    return 'card_bg5';
                };
            },
            tabClick : function(id){
                this.tablists = [];
                this.tablist = this.resetTabCodeList(this.tablist);
                for(let index in this.tablist){
                    if(this.tablist[index].itemId == id){
                        this.tablist[index].code = 1;
                        this.cardForm.code = this.tablist[index].itemCode;
                        this.cardForm.group = this.tablist[index].itemGroup;
                        this.getCardListFn();
                    }
                };
                this.tablists = this.tablist;
            },
            getTabFn : function(){
                getDeliveryTab({}).then((res) => {
                    if(res.data && res.data.code == 0){
                        if(res.data.data.list.length > 0){
                            this.tablist = res.data.data.list;
                            this.tablists = [];
                            this.tablist = this.resetTabCodeList(this.tablist);
                            this.tablist[0].code = 1;
                            this.tablists = this.tablist;
                            this.cardForm.code = this.tablist[0].itemCode;
                            this.cardForm.group = this.tablist[0].itemGroup;
                            this.getCardListFn();
                        }
                    }
                })
            },
            getCardListFn : function(){
                this.list = [];
                getAdvertiseList(this.cardForm).then((res) => {
                    if(res.data && res.data.code == 0){
                        console.log('打印卡片list')
                        console.log(res.data.data)
                        if(res.data.data.list.length > 0){
                            this.list = res.data.data.list;
                        }
                    }
                })
            },
            resetTabCodeList : function(data){
                for(let index in data){
                    data[index].code = 0
                }
                return data;
            }
        },
        mounted : function(){
            this.getTabFn();
        }
    }
</script>

<style scoped>
    .table_item{
        width:86px;
        height:38px;
        cursor:pointer;
        border:1px solid #03D4CC;
        font-size:12px;
        display: flex;
        justify-content: center;
        align-items: center;
        float:left;
        margin-right:2px;
    }
    .actinveon{
        background: linear-gradient(-45deg, #0498DC, #03D4CC);
        color:#fff;
    }
    .actinveoff{
        background-color:#fff;
        color:#03D4CC;
    }
    .controlertab_list{
        position: relative;
    }
    .controlertab_list .tab{
        width:100%;
        padding:0px 0px 2px 0px;
        border-bottom:2px solid #03D4CC;
        float:left;
    }
    .ad_card{
        width:32.2%;
        box-sizing:border-box;
        float: left;
        margin-right:10px;
        height:155px;
        margin-bottom: 10px;
        border-radius: 5px;
        cursor: pointer;

    }
    .ad_card:hover{
        box-shadow: 2px 2px 10px rgba(0,0,0,0.5);
    }
    .card_bg1{
        background: linear-gradient(-45deg, #0498DC, #03D4CC);
    }
    .card_bg2{
        background: linear-gradient(-45deg, #FF9F52, #FC555C);
    }
    .card_bg3{
        background: linear-gradient(-45deg, #F5515F, #9F041B);
    }
    .card_bg4{
        background: linear-gradient(-45deg, #0ECAE2, #219389);
    }
    .card_bg5{
        background: linear-gradient(-45deg, #53A0FD, #2D4B8B);
    }
    .ad_card .card_icon{
        width:80px;
        height:80px;
        display: flex;
        border-radius: 100%;
        justify-content: center;
        align-items: center;
        border:2px solid rgba(255,255,255,0.8);
        float: left;
        margin-top: 10px;
        margin-left: 10px;
    }
    .ad_card .card_icon .card_dashed{
        width:68px;
        height:68px;
        border: 1px dashed rgba(255,255,255,0.9);
        border-radius:100%;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .ad_card .card_icon .card_dashed img{
        width:40px;
        height:45px;
    }
    .ad_card .card_text{
        float: left;
        padding-left:10px;
    }
    .ad_card .card_text li{
        line-height: 20px;
        color:#fff;
        font-size:13px;
        width:100%;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }
    .ad_card .card_text li span{
        font-weight:bold;
        color:rgba(255,255,255,1);
    }
    /deep/.ad_card .el-progress__text{
        color:#fff !important;
    }
    .ad_card .wg_item{
        width:100%;
        float: left;
        box-sizing: border-box;
        padding-left:10px;
        margin-top: -10px;
        height:44px;
        border-radius:5px;
        background-color:rgba(255,255,255,1);
        box-shadow: inset 1px 1px 1px rgba(0,0,0,0.2);

    }
    .ad_card .wg_item li{
        line-height:23px;
        font-size:12px;
        padding:0px;
        color:#FF0000;
    }
    .ad_card .wg_item .wg_title{
        font-size:14px;
        font-weight:bold;
        color:#FF0000;
    }
    .ad_card .wg_item .wg_text{
        font-size:13px;
        color:#FF0000;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }
</style>
