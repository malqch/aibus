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
        props:['barObj','height','color','boxid'],
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
                var optionBar = {
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    legend: {
                        data: data.legendData,
                        x:'center',
                        y:'bottom'
                    },
                    grid: {
                        left: '4%',
                        right: '15%',
                        bottom: '7%',
                        top:'4%',
                        containLabel: true
                    },
                    xAxis: {
                        type: 'value',
                        name:data.xAxisName,
                        splitArea:{
                            show:true
                        },
                        splitLine: {
                            show: false
                        },
                        axisTick:{
                            show:false
                        },
                        max: function(value) {
                            return Math.ceil(value.max * 1.1);
                        }
                    },
                    yAxis: {
                        type: 'category',
                        axisTick:{
                            show:false
                        },
                        // axisLine:{       //y轴
                        //     show:false
                        // },
                        data: data.textList
                    },
                    series: [
                        {
                            name: data.xAxisName,
                            type: 'bar',
                            barWidth : 30,
                            data: data.valueList,
                            label: {
                                show: true,
                                position: 'insideRight',
                                color: '#fff'
                            },
                            itemStyle: {
                                normal: {
                                    color: new this.$echarts.graphic.LinearGradient(0, 0, 1, 0, [{
                                        offset: 0,
                                        color: this.color[0]
                                    }, {
                                        offset: 1,
                                        color: this.color[1]
                                    }]),
                                }
                            },
                        }
                    ]
                };
                myChartBar.setOption(optionBar);
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
