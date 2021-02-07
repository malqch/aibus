<!--
 @Author: songwenxin
 @Filename: index.vue
 @ProjectName: gfm-manage
 @Mail: songwenxin666@sina.com
 @Date: 2019-09-24 13:23
 @file-description:组件页面
-->
<template>
    <div :id="boxid" :style="{height:height}"></div>
</template>

<script>
    export default {
        data() {
            return {

            };
        },
        props:['barObj','height','boxid'],
        components: {
        },
        created(){
        },
        mounted(){
            this.myEcharts(this.barObj);
        },
        watch:{
            "barObj":{
               handler:function(newVal, oldVal){
                   this.myEcharts(newVal);
               } ,
               "deep":true
            }
        },
        methods: {
            myEcharts(data){
                var myChartBar = this.$echarts.init(document.getElementById(this.boxid));
                var seriesData=[];
                var color=[['#efbf2d','#cf8b16'],['#ff3f6a','#c20020'],['#fbb162','#eb5602'],['#de70dd','#242bb8']];
                if(data.verValueList){
                    for(var i=0;i<data.verValueList.length;i++){
                        var obj=new Object();
                        obj.name=data.verticalShowList[i];
                        obj.type=data.verValueList[i].type;
                        obj.barWidth=40;
                        obj.itemStyle={
                                    normal: {
                                        color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                            offset: 0,
                                            color: color[i%4][0]
                                        }, {
                                            offset: 1,
                                            color: color[i%4][1]
                                        }]),
                                    }
                                };
                        obj.data=data.verValueList[i].data;
                        seriesData.push(obj);
                    }
                }

                var optionBar = {
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    legend: {
                        data: data.verticalShowList,
                        x:'right',
                        y:'top'
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        top:'15%',
                        containLabel: true
                    },
                    xAxis: {
                        type: 'category',
                        data: data.horizontalShowList,
                        axisTick:{
                            show:false
                        },
                    },
                    yAxis: {
                        type: 'value',
                        axisTick:{
                            show:false
                        },
                    },
                    series:seriesData
                };
                myChartBar.setOption(optionBar,true);
                window.addEventListener('resize', () => {
                    myChartBar.resize();
                })
                setTimeout(() => {
                   myChartBar.resize();
                }, 1000)

            }
        },

    };
</script>

<style>

</style>
