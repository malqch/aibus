<template>
<el-dialog :title="titletext" :visible.sync="detailVisible">
    <div class="dialogbox">
        <div class="statusbox" :style="styleObject">
            <div class="statusboximg" :style="'background-image:url('+ busicon+');'"></div>
            <div>状态：{{status}}</div>
        </div>
        <div class="detailbox">
            <ul>
                <li v-for="(item,index) in maintainInfo">
                    <span>{{item.displayName}}：</span>
                    <span>{{item.value}}</span>
                </li>
            </ul>
        </div>
    </div>
</el-dialog>
</template>

<script>
import {getMaintainInfoByBusIdData} from '@/api/sale'
export default {
    data() {
        return {
            busicon:'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTk4MjM4MzQ4MTg0IiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjIyODg3IiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayIgd2lkdGg9IjIwMCIgaGVpZ2h0PSIyMDAiPjxkZWZzPjxzdHlsZSB0eXBlPSJ0ZXh0L2NzcyI+PC9zdHlsZT48L2RlZnM+PHBhdGggZD0iTTIxMi43IDU5Mi44Yy0zMC42IDAtNTUuMyAyNC44LTU1LjMgNTUuNCAwIDMwLjYgMjQuOCA1NS4zIDU1LjMgNTUuMyAzMC42IDAgNTUuNC0yNC44IDU1LjQtNTUuM0MyNjggNjE3LjYgMjQzLjMgNTkyLjggMjEyLjcgNTkyLjh6TTIxMi43IDY4NGMtMTkuOCAwLTM1LjktMTYtMzUuOS0zNS44IDAtMTkuOCAxNi4xLTM1LjkgMzUuOS0zNS45IDE5LjggMCAzNS45IDE2IDM1LjkgMzUuOUMyNDguNSA2NjggMjMyLjUgNjg0IDIxMi43IDY4NHoiIHAtaWQ9IjIyODg4IiBmaWxsPSIjZmZmZmZmIj48L3BhdGg+PHBhdGggZD0iTTM4MSA1OTIuOGMtMzAuNiAwLTU1LjMgMjQuOC01NS4zIDU1LjQgMCAzMC42IDI0LjggNTUuMyA1NS4zIDU1LjMgMzAuNiAwIDU1LjQtMjQuOCA1NS40LTU1LjNDNDM2LjQgNjE3LjYgNDExLjYgNTkyLjggMzgxIDU5Mi44ek0zODEgNjg0Yy0xOS44IDAtMzUuOS0xNi0zNS45LTM1LjggMC0xOS44IDE2LjEtMzUuOSAzNS45LTM1LjkgMTkuOCAwIDM1LjkgMTYgMzUuOSAzNS45QzQxNi45IDY2OCA0MDAuOSA2ODQgMzgxIDY4NHoiIHAtaWQ9IjIyODg5IiBmaWxsPSIjZmZmZmZmIj48L3BhdGg+PHBhdGggZD0iTTczNi41IDU5Mi44Yy0zMC42IDAtNTUuMyAyNC44LTU1LjMgNTUuNCAwIDMwLjYgMjQuOCA1NS4zIDU1LjMgNTUuMyAzMC42IDAgNTUuNC0yNC44IDU1LjQtNTUuM0M3OTEuOSA2MTcuNiA3NjcuMSA1OTIuOCA3MzYuNSA1OTIuOHpNNzM2LjUgNjg0Yy0xOS44IDAtMzUuOS0xNi0zNS45LTM1LjggMC0xOS44IDE2LjEtMzUuOSAzNS45LTM1LjkgMTkuOCAwIDM1LjkgMTYgMzUuOSAzNS45Qzc3Mi40IDY2OCA3NTYuMyA2ODQgNzM2LjUgNjg0eiIgcC1pZD0iMjI4OTAiIGZpbGw9IiNmZmZmZmYiPjwvcGF0aD48cGF0aCBkPSJNOTMyLjkgMzk1LjZjLTE3LjctMjEuOS0zNS45LTM3LjgtNzQuOC0zNy40LTguNSAwLjEtMjguMSA5LjQtMjguMSA5LjRzLTUwLjktNDQuNy03NC44LTQ2LjhMMTM3LjkgMzIwLjhjMCAwLTEzLjQtMi44LTE4LjcgOS40TDgxLjcgNDMzbDAgMTk2LjRjMCAwLTEuMyA5LjUgOS40IDkuNCAwLjQgMCAxNy45IDAgNDcuNCAwLjEgNC42LTM2LjkgMzYtNjUuNSA3NC4yLTY1LjUgMzguMiAwIDY5LjYgMjguNiA3NC4yIDY1LjYgNi42IDAgMTMuMiAwIDIwIDAgNC42LTM2LjkgMzYtNjUuNiA3NC4yLTY1LjYgMzguMiAwIDY5LjYgMjguNiA3NC4yIDY1LjYgNjkuOSAwIDE0MS42IDAgMjA3IDAgNC42LTM2LjkgMzYtNjUuNSA3NC4yLTY1LjUgMzguMiAwIDY5LjYgMjguNiA3NC4yIDY1LjUgNjMgMCA5NC4yIDAgOTQuMiAwczE5LjUtMjcgMTguNy05My41Yy0wLjYtNDkuNi00Mi42LTEyMy43LTc0LjgtMTU5IDAgMCAxNy42LTEzIDI4LjEtOS40IDEwLjUgMy43IDUwLjUgNjIuOSA2NS41IDU2LjFDOTQyLjMgNDMzIDk0My41IDQwOC43IDkzMi45IDM5NS42ek02MzMuNiA0NzAuNCAxMTkuMSA0NzAuNGwyOC4xLTExMi4yIDQ4Ni40IDBMNjMzLjYgNDcwLjR6TTg3Ni44IDQ5OC41bDAgNTYuMWMwIDAtNDkuNi0zNy4zLTkzLjUtNTYuMS02Mi0yNi42LTEzMS0yOC4xLTEzMS0yOC4xTDY1Mi4zIDM1OC4ybDExMi4yIDBjMCAwIDMyLjggMTguMSA2NS41IDU2LjFDODU2LjggNDQ1LjUgODc2LjMgNDk4LjggODc2LjggNDk4LjV6IiBwLWlkPSIyMjg5MSIgZmlsbD0iI2ZmZmZmZiI+PC9wYXRoPjwvc3ZnPg==',
            detailVisible: false,
            titletext:'',
            bgColor: "bgColor1",
            styleObject:{
            },
            status:'',
            maintainInfo:{

            }
        }
    },
    methods: {
        init : function(res){
            this.busId=res.busId;
            this.detailVisible = true;
            this.titletext=res.vinCode+'-车辆维修信息查看'
            /*if(res.busStatusCode!='maintenance'){
                this.status='维修完';
                this.styleObject={
                    'background-color': '#c96fe3',
                    'background': '-webkit-linear-gradient(315deg,#3017c0,#c96fe3)'

                }
            }else{*/
                this.status='维修中';
                this.styleObject={
                    'background-color': '#fb7a00',
                    'background': '-webkit-linear-gradient(315deg,#fde14e,#fb7a00)'
                }

            getMaintainInfoByBusIdData(this.busId).then(resp =>{
                if(resp.data.code==0){
                    this.maintainInfo=resp.data.data.list;
                }
            })

            //console.log(this.styleObject)
        }
    }
}
</script>
<style scoped="scoped">
    .dialogbox{
        overflow: hidden;
    }
    .statusbox{
        width: 120px;
        height: 120px;
        float: left;
        text-align: center;
        color: #fff;
        font-size: 14px;
    }
    .detailbox{
        width: calc(100% - 152px);
        border: 1px solid #e5e8ed;
        background-color: #f2f9fe;
        padding: 10px;
        margin-left: 10px;
        float: left;

    }
    .detailbox ul{
        padding: 0;
        margin: 0;
    }
    .detailbox ul li{
        float: left;
        width: 50%;
        font-size: 10px;
        line-height: 20px;
    }
    .bgcolor1{
        background-color: #3a35ff;
        background: -webkit-linear-gradient(0deg,#3a35ff,#00ebfc)
    }
    .statusboximg{
        width: 68px;
        height: 68px;
        border: 1px solid #fff;
        border-radius: 50%;
        background: rgba(255,255,255,0.2);
        background-size:60px 60px;
        background-position:center center;
        float: left;
        background-repeat: no-repeat;
        margin: 10px 26px;
    }
</style>
