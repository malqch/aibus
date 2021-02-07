<template>
<el-dialog title="文件导出" :visible.sync="Visible">
    <div class="dialogbox">
        <div class="datebox">
            发生时间范围：
            <el-date-picker
              v-model="dateRange"
              type="datetimerange"
              :picker-options="pickerOptions"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              @change="changeDate">
            </el-date-picker>
        </div>
        <el-button class="btn" size="small" type="primary" @click="exportData()">确定导出</el-button>
    </div>
</el-dialog>
</template>

<script>
import areaData from './areaData.json'
import {getTrafficExportData,formatDate} from "@/api/sale"
export default {
    data() {
        return {
            busicon:'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTk4MjM4MzQ4MTg0IiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjIyODg3IiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayIgd2lkdGg9IjIwMCIgaGVpZ2h0PSIyMDAiPjxkZWZzPjxzdHlsZSB0eXBlPSJ0ZXh0L2NzcyI+PC9zdHlsZT48L2RlZnM+PHBhdGggZD0iTTIxMi43IDU5Mi44Yy0zMC42IDAtNTUuMyAyNC44LTU1LjMgNTUuNCAwIDMwLjYgMjQuOCA1NS4zIDU1LjMgNTUuMyAzMC42IDAgNTUuNC0yNC44IDU1LjQtNTUuM0MyNjggNjE3LjYgMjQzLjMgNTkyLjggMjEyLjcgNTkyLjh6TTIxMi43IDY4NGMtMTkuOCAwLTM1LjktMTYtMzUuOS0zNS44IDAtMTkuOCAxNi4xLTM1LjkgMzUuOS0zNS45IDE5LjggMCAzNS45IDE2IDM1LjkgMzUuOUMyNDguNSA2NjggMjMyLjUgNjg0IDIxMi43IDY4NHoiIHAtaWQ9IjIyODg4IiBmaWxsPSIjZmZmZmZmIj48L3BhdGg+PHBhdGggZD0iTTM4MSA1OTIuOGMtMzAuNiAwLTU1LjMgMjQuOC01NS4zIDU1LjQgMCAzMC42IDI0LjggNTUuMyA1NS4zIDU1LjMgMzAuNiAwIDU1LjQtMjQuOCA1NS40LTU1LjNDNDM2LjQgNjE3LjYgNDExLjYgNTkyLjggMzgxIDU5Mi44ek0zODEgNjg0Yy0xOS44IDAtMzUuOS0xNi0zNS45LTM1LjggMC0xOS44IDE2LjEtMzUuOSAzNS45LTM1LjkgMTkuOCAwIDM1LjkgMTYgMzUuOSAzNS45QzQxNi45IDY2OCA0MDAuOSA2ODQgMzgxIDY4NHoiIHAtaWQ9IjIyODg5IiBmaWxsPSIjZmZmZmZmIj48L3BhdGg+PHBhdGggZD0iTTczNi41IDU5Mi44Yy0zMC42IDAtNTUuMyAyNC44LTU1LjMgNTUuNCAwIDMwLjYgMjQuOCA1NS4zIDU1LjMgNTUuMyAzMC42IDAgNTUuNC0yNC44IDU1LjQtNTUuM0M3OTEuOSA2MTcuNiA3NjcuMSA1OTIuOCA3MzYuNSA1OTIuOHpNNzM2LjUgNjg0Yy0xOS44IDAtMzUuOS0xNi0zNS45LTM1LjggMC0xOS44IDE2LjEtMzUuOSAzNS45LTM1LjkgMTkuOCAwIDM1LjkgMTYgMzUuOSAzNS45Qzc3Mi40IDY2OCA3NTYuMyA2ODQgNzM2LjUgNjg0eiIgcC1pZD0iMjI4OTAiIGZpbGw9IiNmZmZmZmYiPjwvcGF0aD48cGF0aCBkPSJNOTMyLjkgMzk1LjZjLTE3LjctMjEuOS0zNS45LTM3LjgtNzQuOC0zNy40LTguNSAwLjEtMjguMSA5LjQtMjguMSA5LjRzLTUwLjktNDQuNy03NC44LTQ2LjhMMTM3LjkgMzIwLjhjMCAwLTEzLjQtMi44LTE4LjcgOS40TDgxLjcgNDMzbDAgMTk2LjRjMCAwLTEuMyA5LjUgOS40IDkuNCAwLjQgMCAxNy45IDAgNDcuNCAwLjEgNC42LTM2LjkgMzYtNjUuNSA3NC4yLTY1LjUgMzguMiAwIDY5LjYgMjguNiA3NC4yIDY1LjYgNi42IDAgMTMuMiAwIDIwIDAgNC42LTM2LjkgMzYtNjUuNiA3NC4yLTY1LjYgMzguMiAwIDY5LjYgMjguNiA3NC4yIDY1LjYgNjkuOSAwIDE0MS42IDAgMjA3IDAgNC42LTM2LjkgMzYtNjUuNSA3NC4yLTY1LjUgMzguMiAwIDY5LjYgMjguNiA3NC4yIDY1LjUgNjMgMCA5NC4yIDAgOTQuMiAwczE5LjUtMjcgMTguNy05My41Yy0wLjYtNDkuNi00Mi42LTEyMy43LTc0LjgtMTU5IDAgMCAxNy42LTEzIDI4LjEtOS40IDEwLjUgMy43IDUwLjUgNjIuOSA2NS41IDU2LjFDOTQyLjMgNDMzIDk0My41IDQwOC43IDkzMi45IDM5NS42ek02MzMuNiA0NzAuNCAxMTkuMSA0NzAuNGwyOC4xLTExMi4yIDQ4Ni40IDBMNjMzLjYgNDcwLjR6TTg3Ni44IDQ5OC41bDAgNTYuMWMwIDAtNDkuNi0zNy4zLTkzLjUtNTYuMS02Mi0yNi42LTEzMS0yOC4xLTEzMS0yOC4xTDY1Mi4zIDM1OC4ybDExMi4yIDBjMCAwIDMyLjggMTguMSA2NS41IDU2LjFDODU2LjggNDQ1LjUgODc2LjMgNDk4LjggODc2LjggNDk4LjV6IiBwLWlkPSIyMjg5MSIgZmlsbD0iI2ZmZmZmZiI+PC9wYXRoPjwvc3ZnPg==',
            hexagonicon:'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTk4MzI2ODk1NDUwIiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjE4MTgiIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNNTEyLjAyNDQ3NSAwbDQ0NS40MTMyNzUgMjU1Ljk4NDY0MVY3NjguMDE1MzU5bC00NDUuNDEzMjc1IDI1NS45ODQ2NDFMNjYuNTA4ODA2IDc2OC4wMTUzNTlWMjU2LjA0NjA3N3oiIHAtaWQ9IjE4MTkiIGZpbGw9IiNmZGExMTUiPjwvcGF0aD48L3N2Zz4=',
            tipicon:'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTk4MzI2NzM0NjcwIiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjgwNjciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNNTEyLjAyMzY1NyAwLjE2NjUzMUMzMzYuOTk4NDc0IDAuMTY2NTMxIDE5Mi40ODc1NDkgMTUxLjM5NDQzMSAxOTIuNDg3NTQ5IDMzNy4zNTg2ODhjMCAxMTMuNDIwOTI1IDQyLjczMjc1NyAyMDQuNzA3ODE3IDEyNy44Nzg0MTUgMjY1LjY3MjM2NHYxMDkuMDA2OTEzYzAgMTIuNDEwNDExIDcuMDM2ODMxIDI0LjMwOTA1MyAxNS42NzI5NDIgMzIuNjg5Mjc5YTQzLjY5MjMyNSA0My42OTIzMjUgMCAwIDAgMjkuNzQ2NjA1IDExLjc3MDdjMS4xNTE0ODEgMCAyLjc1MDc2MSAwIDMuOTAyMjQyLTAuMTI3OTQzbDI5Mi45MjQwOTItMjQuNjI4OTA5YTQ1LjQ4MzUxOCA0NS40ODM1MTggMCAwIDAgNDEuMDA1NTM1LTQ0LjMzMjAzN1Y2MDMuNzk4NzA2Yzc0LjUyNjQ0LTU2LjQ4NjU2MyAxMjcuNjg2NTAxLTE1NC4wNDI2MzIgMTI3LjY4NjUwMS0yNjYuNDQwMDE4IDAtMTg1Ljk2NDI1Ny0xNDQuMjU1MDQtMzM3LjE5MjE1Ny0zMTkuMjgwMjI0LTMzNy4xOTIxNTd6IG0xMjcuMzAyNjc0IDUzOS4wODUyNDJjLTEzLjgxNzc3OCA3Ljc0MDUxNC0yMC44NTQ2MDkgMjIuODM3NzE2LTIwLjg1NDYwOSAzOS4yNzgzMTJ2NjcuODA5NDY1bC0yMTIuOTYwMTAxIDE3LjQ2NDEzNVY1NzguNTMwMDg1YzAtMTUuODAwODg1LTYuMTQxMjM1LTMwLjQ1MDI4OC0xOS4xOTEzNTgtMzguMzgyNzE1LTczLjMxMDk4Ny00NC45NzE3NDktMTA4LjA0NzM0NS0xMTEuMzA5ODc1LTEwOC4wNDczNDQtMjAyLjc4ODY4MiAwLTEzNi44OTgzNTMgMTA0LjkxMjc1Ni0yNDguMjcyMTk5IDIzMy4zMDI5NC0yNDguMjcyMTk5IDEyOC4zOTAxODQgMCAyMzMuODE0NzEgMTExLjM3Mzg0NyAyMzMuODE0NzEgMjQ4LjI3MjE5OSAwIDg4Ljg1NTk4Ny0zOS4wMjI0MjggMTY0LjM0MTk5NC0xMDYuMDY0MjM4IDIwMS44OTMwODV6IG02MC40NTI3NzcgMjg3LjEwMjcxM2MxLjcyNzIyMiAyNC41NjQ5MzgtMTUuODAwODg1IDQ1Ljg2NzM0NS0zOS4wODYzOTkgNDcuNjU4NTM5bC0yOTAuODEzMDQyIDIyLjEzNDAzM2MtMS4wMjM1MzkgMC4xMjc5NDItMi4xMTEwNDkgMC4xMjc5NDItMy4xMzQ1ODkgMC4xMjc5NDItMjEuOTQyMTE5IDAtNDAuNDkzNzY1LTE3Ljg0Nzk2My00Mi4xNTcwMTYtNDEuMjYxNDE5LTEuNjYzMjUxLTI0LjUwMDk2NyAxNS44NjQ4NTYtNDUuODAzMzc0IDM5LjE1MDM3LTQ3LjU5NDU2OGwyOTAuODEzMDQzLTIyLjE5ODAwNGMyMy4yMjE1NDMtMS43OTExOTMgNDMuNTY0MzgyIDE2LjYzMjUxIDQ1LjIyNzYzMyA0MS4xMzM0Nzd6TTYwNy4xNDg4MjEgOTY2Ljg5OTE5N2MxLjc5MTE5MyAyNC41MDA5NjctMTUuNjA4OTcxIDQ1Ljg2NzM0NS0zOC45NTg0NTYgNDcuNzIyNTFsLTEwNS40ODg0OTcgOC41NzIxMzktMy4zMjY1MDIgMC4xMjc5NDNjLTIxLjg3ODE0OCAwLTQwLjQyOTc5NC0xNy43MjAwMi00Mi4xNTcwMTYtNDEuMDY5NTA2LTEuNzkxMTkzLTI0LjUwMDk2NyAxNS42NzI5NDItNDUuODY3MzQ1IDM4Ljk1ODQ1Ni00Ny43ODY0ODFsMTA1LjU1MjQ2OC04LjUwODE2OWMyMy4zNDk0ODUtMS43OTExOTMgNDMuNjI4MzUzIDE2LjQ0MDU5NyA0NS40MTk1NDcgNDAuOTQxNTY0eiBtNzQuNTI2NDQtNjMwLjMwODE2M2EzNy43NDMwMDQgMzcuNzQzMDA0IDAgMCAxLTQwLjQyOTc5NCAzNC45MjgyNzFjLTIwLjM0MjgzOS0yLjExMTA0OS0zNS4yNDgxMjctMjEuMTEwNDk0LTMzLjI2NTAyMS00Mi41NDA4NDMgNS44ODUzNS02My42NTEzMzctNDcuMjc0NzExLTg2LjIzMzE2OC01My4zNTE5NzQtODguNjAwMTAyYTM5LjY2MjE0IDM5LjY2MjE0IDAgMCAxLTIyLjE5ODAwNC00OS43Njk1ODggMzYuNDYzNTggMzYuNDYzNTggMCAwIDEgNDYuODkwODg0LTIzLjYwNTM3YzM5LjUzNDE5NyAxNC40NTc0OSAxMTEuNzU3Njc0IDY4LjU3NzExOSAxMDIuMzUzOTA5IDE2OS41ODc2MzJ6IiBmaWxsPSIjZmZmZmZmIiBwLWlkPSI4MDY4Ij48L3BhdGg+PC9zdmc+',
            Visible: false,
            lastExportTime:'2020-08-26',
            radio:'0',
            showDate:true,
            dateRange:[],
            pickerMinDate: '',
            pickerOptions: {
              onPick: ({ maxDate, minDate }) => {
                this.pickerMinDate = minDate.getTime()
                if (maxDate) {
                  this.pickerMinDate = ''
                }
              },
              disabledDate: (time) => {
                if (this.pickerMinDate !== '') {
                  const day180 = (180-1) * 24 * 3600 * 1000
                  let maxTime = this.pickerMinDate + day30
                  let minTime = this.pickerMinDate - day30
                  if (maxTime > new Date()) {
                    maxTime = new Date()
                  }
                  return time.getTime() > maxTime ||time.getTime() < minTime
                }
                return time.getTime() > Date.now()
              }
            },
            dateRange:[],
            startTime:'',
            endTime:'',
            companyId:'',
            pickerMinDate: '',
            pickerOptions: {
              onPick: ({ maxDate, minDate }) => {
                this.pickerMinDate = minDate.getTime()
                if (maxDate) {
                  this.pickerMinDate = ''
                }
              },
              disabledDate: (time) => {
                if (this.pickerMinDate !== '') {
                  const day180 = (180-1) * 24 * 3600 * 1000
                  let maxTime = this.pickerMinDate + day180
                  let minTime = this.pickerMinDate - day180
                  if (maxTime > new Date()) {
                    maxTime = new Date()
                  }
                  return time.getTime() > maxTime ||time.getTime() < minTime
                }
                return time.getTime() > Date.now()
              }
            },
        }
    },
    methods: {
        init : function(res){
            this.Visible = true;
            this.companyId=res;
        },
        changeDate(val){
            //console.log(val)
            if(val!=null){
                this.startTime=formatDate(new Date(val[0]).getTime());
                this.endTime=formatDate(new Date(val[1]).getTime());
                this.dateRange=[this.startTime,this.endTime];
            }else{
                this.dateRange=[]
            }

            //console.log(this.busesDate)
        },
        exportData(){
            console.log(this.dateRange)
            if(this.dateRange!=null){
                if(this.dateRange.length==0){
                    this.$message({
                        message: "请选择导出数据的违章发生时间范围",
                        showClose: true,
                        type: 'error',
                        offset: 300
                    })
                    return
                }
            }else{
                this.$message({
                    message: "请选择日期",
                    showClose: true,
                    type: 'error',
                    offset: 300
                })
                return
            }
            getTrafficExportData(this.companyId,this.startTime,this.endTime).then(resp => {
                const blob = new Blob([resp.data]);
                 const fileName = this.startTime.substring(0,10)+'~'+this.endTime.substring(0,10)+'.xls';
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
                 this.$message({
                    message: '导出完成，请确认',
                    type: 'success',
                    offset: 300,
                    duration: 1500,
                    onClose: () => {
                        this.Visible = false
                    }
                 })
            })
        },
    }
}
</script>
<style scoped="scoped">
    .dialogbox{
        overflow: hidden;
    }
    .faultTip{
        border: 1px dashed #fc8310;
        color: #fc8310;
        padding: 10px;
        border-radius: 3px;
        line-height: 24px;
        font-size: 12px;
    }
    .faultTipimg{
        width: 24px;
        height: 24px;
        background-size:24px 24px;
        background-position:center center;
        background-repeat: no-repeat;
        float: left;
        margin-right: 10px;
    }
    .faultTipimg img{
        width: 12px;
        height: 12px;
        margin: 6px 0 0 6px;
    }
    .radiobox{
        margin: 10px 0;
        font-size: 12px;
    }
    .btn{
        margin-top: 30px;
        float: right;
    }
    .el-input__icon {
        line-height: 35px;
    }
</style>
