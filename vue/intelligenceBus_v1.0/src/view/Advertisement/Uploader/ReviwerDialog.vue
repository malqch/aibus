<!--
 * @Author : SongWenXin
 * @Mail : songwenxin666@sina.com
 * @ProjectName : intelligenceBus_v1.0
 * @FileName : Dialog
 * @Date :  2020-11-08 17:54
-->
<template>

    <el-dialog class="dialog_default" title="广告审核判定" :close-on-click-modal="false" @close="closeDialog" :visible.sync="visible" style="color:#fff" append-to-body >
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
                <div style="width:100%;height:34px;" v-if="true">

                    <svg  style="float: left;" width="24px" height="33px" viewBox="0 0 30 33" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                        <defs></defs>
                        <g id="Page-18" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                            <g id="评定标准" fill="#D81E06" fill-rule="nonzero">
                                <path d="M5.60254854,18.8838646 C5.60254854,18.4394989 5.77907211,18.0133333 6.09328607,17.6991194 C6.40750004,17.3849054 6.83366564,17.2083818 7.27803129,17.2083818 L15.6547336,17.2083818 L15.6547336,13.0196749 L7.27803129,13.0196749 C6.35268774,13.0196749 5.60254859,12.2695357 5.60254859,11.3441922 C5.60254859,10.4188486 6.35268774,9.66870944 7.27803129,9.66870941 L15.6547336,9.66870941 L15.6547336,5.22921369 L4.50192357,5.22921369 C4.05759062,5.22921368 3.63146159,5.40574861 3.3173041,5.71997281 C3.00314662,6.034197 2.82670219,6.46036351 2.82679655,6.90469645 L2.82679655,27.8471637 C2.82679654,28.291435 3.00328263,28.7175102 3.31742988,29.0316574 C3.63157714,29.3458047 4.05765225,29.5222908 4.50192357,29.5222908 L13.9796066,29.5222908 C14.4238779,29.5222908 14.849953,29.3458047 15.1641003,29.0316574 C15.4782475,28.7175102 15.6547336,28.291435 15.6547336,27.8471637 L15.6547336,20.561126 L7.27803129,20.561126 C6.8333573,20.5611262 6.40692193,20.3843585 6.09265678,20.0697598 C5.77839164,19.755161 5.60207648,19.3285383 5.60254854,18.8838646 Z" id="Shape"></path>
                                <path d="M26.6232762,3.06958402 C27.0857042,3.07017197 27.4604296,3.44489737 27.4610175,3.9073254 L27.4610175,17.1692517 L29.1365003,15.4667335 L29.1365003,3.90554675 C29.134932,2.51818152 28.0106414,1.39389092 26.6232762,1.39232261 L21.8241386,1.39232261 L21.8241386,3.06780537 L26.6232762,3.06958402 Z M2.53830047,30.374617 C2.07587244,30.3740291 1.70114705,29.9993037 1.70055909,29.5368756 L1.70055909,3.9073254 C1.70114705,3.44489737 2.07587244,3.07017197 2.53830047,3.06958402 L7.33743802,3.06958402 L7.33743802,1.39410126 L2.53830047,1.39410126 C1.15093524,1.39566957 0.0266446478,2.51996017 0.0250763368,3.9073254 L0.0250763368,29.5368756 C0.0260564964,30.9246576 1.15051905,32.0495976 2.53830047,32.0511669 L15.6810576,32.0511669 L13.8035208,30.3756842 L2.53830047,30.374617 Z M21.8241386,0.837741379 C21.8241386,0.375069603 21.449069,1.74405716e-08 20.9863972,0 L8.1751794,0 C7.71250761,-2.83304762e-17 7.33743802,0.375069591 7.33743802,0.837741379 L7.33743802,1.39410126 L21.8241386,1.39410126 L21.8241386,0.837741379 Z" id="Shape"></path>
                                <path d="M8.1751794,4.46368528 L20.9863972,4.46368528 C21.449069,4.46368527 21.8241386,4.08861568 21.8241386,3.62594391 L21.8241386,3.06958402 L7.33743802,3.06958402 L7.33743802,3.62594391 C7.33743802,4.08861569 7.71250761,4.46368528 8.1751794,4.46368528 Z M7.33743802,1.39410126 L21.8241386,1.39410126 L21.8241386,3.06958402 L7.33743802,3.06958402 L7.33743802,1.39410126 Z M18.3604071,32.198083 C18.4750806,32.3010018 18.6281102,32.3503 18.7812956,32.3336715 C18.9344811,32.3170431 19.0733682,32.2360569 19.163287,32.1109294 L19.2070417,32.0500998 L18.1942817,32.0500998 L18.3604071,32.198083 Z M29.9742417,17.1283428 L29.1365003,17.9796019 L29.1365003,18.289086 L29.9742417,17.1283428 Z M19.0868053,28.1939993 C18.8900028,28.3942142 18.5730062,28.4113366 18.3557826,28.2334852 L15.1257649,25.5904201 C15.0113604,25.4965984 14.8636351,25.4535035 14.7167289,25.4710946 C14.5698227,25.4886858 14.4364487,25.5654407 14.3474302,25.683621 L13.3293343,27.0432166 C13.1595801,27.2683832 13.1904946,27.5860303 13.4004801,27.7742393 L16.3174564,30.374617 L20.414741,30.374617 L27.4610175,20.6105723 L27.4610175,19.6824758 L19.0868053,28.1939993 Z" id="Shape"></path>
                                <path d="M27.4610175,20.6105723 L29.1365003,18.289086 L29.1365003,17.9796019 L27.4610175,19.6824758 L27.4610175,20.6105723 Z M16.3163892,30.374617 L18.1942817,32.0500998 L19.2056187,32.0500998 L20.414741,30.374617 L16.3163892,30.374617 Z M22.5391535,12.1659257 L7.45980872,12.1659257 C6.99713693,12.1659257 6.62206734,11.7908561 6.62206734,11.3281844 C6.62206734,10.8655126 6.99713693,10.490443 7.45980872,10.490443 L22.5391535,10.490443 C23.0018253,10.490443 23.3768949,10.8655126 23.3768949,11.3281844 C23.3768949,11.7908561 23.0018253,12.1659257 22.5391535,12.1659257 Z M22.5391535,19.7073768 L7.45980872,19.7073768 C6.99713693,19.7073768 6.62206734,19.3323072 6.62206734,18.8696354 C6.62206734,18.4069636 6.99713693,18.031894 7.45980872,18.031894 L22.5391535,18.031894 C23.0018253,18.031894 23.3768949,18.4069636 23.3768949,18.8696354 C23.3768949,19.3323072 23.0018253,19.7073768 22.5391535,19.7073768 Z" id="Shape"></path>
                            </g>
                        </g>
                    </svg>
                    <span style="color:#D81E06;font-size:18px;font-weight:bold;float:left;margin-top: 8px;margin-left:4px;">审核判定</span>
                </div>
                <div  class="dialog_default_box" style="padding:20px 10px 10px; 10px;">
                    <el-form ref="form" :model="form" label-width="80px">
                        <el-row>
                            <el-col :span="24">
                                <div style="width:100%;background-color:rgba(255,255,255,0.6);border-radius:5px;margin-bottom: 10px;">
                                    <el-form-item label="判定结果">
                                        <el-radio-group v-model="form.auditResult" >
                                            <el-radio label="1">审核通过</el-radio>
                                            <el-radio label="0">审核未通过</el-radio>
                                        </el-radio-group>
                                    </el-form-item>
                                </div>
                            </el-col>
                        </el-row>
                        <el-row v-if="form.auditResult == 0">
                            <el-col :span="24">
                                <div style="width:100%;background-color:rgba(255,255,255,0.6);border-radius:5px;margin-bottom: 10px;">
                                    <el-form-item label="违规选项">
                                        <el-checkbox-group v-model="form.checkItemList">
                                            <el-checkbox :label="item.itemId" name="type" v-for="(item, index) in list" :key="item.itemId">{{item.itemName}}</el-checkbox>
                                        </el-checkbox-group>
                                    </el-form-item>
                                </div>
                            </el-col>
                        </el-row>
                        <el-row v-if="form.auditResult == 0">
                            <el-col :span="24">
                                <div style="width:100%;background-color:rgba(255,255,255,0.6);border-radius:5px;margin-bottom: 10px;box-sizing:border-box;padding:5px 5px 5px 0px">
                                    <el-form-item label="违规描述">
                                        <el-input type="textarea" v-model="form.checkSuggest"></el-input>
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
        getAudit,
        getCheckItemList
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
                list : [],
                form : {
                    advertiseId : '',
                    auditResult : '0',
                    checkItemList : [],
                    checkSuggest : '',
                },
            }
        },
        methods : {
            initForm : function(){
                this.form.advertiseId = '';
                this.form.auditResult = '0';
                this.form.checkItemList = [];
                this.form.checkSuggest = '';
            },
            init : function(id){

                this.visible = true;
                getOperationAdvertiseDetail({
                    advertiseId : id
                }).then((res) => {
                    if(res.data && res.data.code == 0){
                        console.log(res.data.data);
                        this.initForm();
                        this.showData = res.data.data.data;
                        this.form.advertiseId = this.showData.advertiseId;
                    }
                }).then(() => {
                    getCheckItemList().then((res) => {
                        if(res.data && res.data.code == 0){
                            this.videoFlag = true;
                            console.log(res.data.data);
                            this.list = res.data.data.list;
                        }
                    })
                })
            },
            submit : function(){
                console.log(this.form);
                var validate = false;
                if(this.form.auditResult == '1'){
                    validate = true;
                };
                if(this.form.auditResult == '0'){
                    if(this.form.checkItemList.length == 0){
                        this.$message({
                            message: '违规选项不能为空',
                            type: 'error',
                            offset: 300
                        });
                        validate = false;
                        return;
                    }else{
                        validate = true;
                    };
                    if(this.form.checkSuggest == ''){
                        this.$message({
                            message: '违规描述不能为空',
                            type: 'error',
                            offset: 300
                        });
                        validate = false;
                        return;
                    }else{
                        validate = true;
                    };
                }
                if(validate){
                    getAudit(this.form).then((res) => {
                        console.log(res.data.data)
                        if(res.data && res.data.code == 0){
                            this.$message({
                                message: '提交审核结果成功！',
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
                                message: '提交审核结果失败！',
                                type: 'error',
                                offset: 300
                            })
                        }
                    })
                }
            },
            closeDialog(){
                this.visible = false;
                this.videoFlag = false;
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
    .el-form-item{
        margin-bottom: 0px;
    }
</style>

