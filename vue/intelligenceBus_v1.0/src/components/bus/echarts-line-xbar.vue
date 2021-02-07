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
        props:['linebarObj','height','boxid'],
        components: {
        },
        created(){
        },
        mounted(){
            this.myEcharts(this.linebarObj);
        },
        watch:{
            "linebarObj":{
               handler:function(newVal, oldVal){
                   this.myEcharts(newVal);
               } ,
               "deep":true
            }
        },
        methods: {
            myEcharts(data){

                var myChartLineXBar = this.$echarts.init(document.getElementById(this.boxid));
                var seriesData=[];
                var color=[['#b765fe','#7c2bc8'],['#23e8be','#3b8f8c'],['#fbb162','#eb5602'],['#adef53','#0da31e']];
                if(data.verValueList){
                    for(var i=0;i<data.verValueList.length;i++){
                        var obj=new Object();
                        obj.name=data.verticalShowList[i];
                        obj.type=data.verValueList[i].type;
                        if(data.verValueList[i].type=='line'){
                            obj.yAxisIndex=1;
                            //obj.symbol='circle';
                            obj.symbolSize =10;
                        }else{
                            obj.barWidth=40;
                        }


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

                var optionLineXBar = {
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'cross',
                            crossStyle: {
                                color: '#999'
                            }
                        }
                    },
                    legend: {
                        data: data.verticalShowList,
                        x:'right',
                        y:'top'
                    },
                    grid: {
                        left: '4%',
                        right: '5%',
                        bottom: '5%',
                        top:'15%',
                        containLabel: true
                    },
                    xAxis: [
                        {
                            type: 'category',
                            data: data.horizontalShowList,
                            axisPointer: {
                                type: 'shadow'
                            },
                            axisTick:{
                                show:false
                            },
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value',
                            // min: 0,
                            // max: 250,
                            // interval: 50,
                            splitNumber:5,
                            //splitLine:{show: false},
                            axisTick:{
                                show:false
                            },
                            // axisLabel: {
                            //     formatter: '{value} ml'
                            // }
                        },
                        {
                            type: 'value',
                            // min: 0,
                            // max: 25,
                            // interval: 5,
                            splitLine:{show: false},
                            axisTick:{
                                show:false
                            },
                            splitNumber:5,
                            axisLabel: {
                                formatter: '{value} °C'
                            }
                        }
                    ],
                    series:seriesData
                    // series: [
                    //     {
                    //         name: this.linebarObj.verticalShowList[0],
                    //         type: 'bar',
                    //         barWidth : 20,
                    //         itemStyle: {
                    //             normal: {
                    //                 color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                    //                     offset: 0,
                    //                     color: '#adef53'
                    //                 }, {
                    //                     offset: 1,
                    //                     color: '#0da31e'
                    //                 }]),
                    //             }
                    //         },
                    //         symbol: 'circle',
                    //         symbolSize : 10,
                    //         data: this.linebarObj.verValueList[0].data
                    //     },
                    //     {
                    //         name: this.linebarObj.verticalShowList[1],
                    //         type: 'bar',
                    //         barWidth : 20,
                    //         itemStyle: {
                    //             normal: {
                    //                 color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                    //                     offset: 0,
                    //                     color: '#ff3f6a'
                    //                 }, {
                    //                     offset: 1,
                    //                     color: '#c20020'
                    //                 }]),
                    //             }
                    //         },
                    //         symbol: 'circle',
                    //         symbolSize : 10,
                    //         data: this.linebarObj.verValueList[1].data
                    //     },
                    //     {
                    //         name:this.linebarObj.verticalShowList[2],
                    //         type: 'line',
                    //         yAxisIndex: 1,
                    //         itemStyle: {
                    //             normal: {
                    //                 color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                    //                     offset: 0,
                    //                     color: '#fee49d'
                    //                 }, {
                    //                     offset: 1,
                    //                     color: '#ff7e2c'
                    //                 }]),
                    //             }
                    //         },
                    //         symbol: 'circle',
                    //         symbolSize : 10,
                    //         data: this.linebarObj.verValueList[2].data
                    //     }
                    // ]
                };
                myChartLineXBar.setOption(optionLineXBar,true);
                window.addEventListener('resize', () => {
                    myChartLineXBar.resize();
                })
                setTimeout(() => {
                   myChartLineXBar.resize();
                }, 1000)

            }
        },

    };
</script>

<style>

</style>
