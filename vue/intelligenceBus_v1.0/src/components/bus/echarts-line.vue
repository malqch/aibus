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
        props:['lineObj','height','color','boxid'],
        components: {
        },
        created(){
        },
        mounted(){
            this.myEcharts(this.lineObj);
        },
        watch:{
            "lineObj":{
                handler:function(newVal, oldVal){
                    this.myEcharts(newVal);
                } ,
                "deep":true
            }
        },
        methods: {
            myEcharts(data){
                var myChartLine = this.$echarts.init(document.getElementById(this.boxid));
                var optionLine = {
                    title: {
                            text: ''
                        },
                        tooltip: {
                            trigger: 'axis',
                            formatter: '{a0}:{c0}'+data.unit

                        },
                        legend: {
                            data: data.legendData,
                            x:'right',
                            y:'top'
                        },

                        grid: {
                            left: '10%',
                            right: '4%',
                            bottom: '7%',
                            top:'15%',
                            containLabel: true
                        },
                        dataZoom: [
                            {
                                show: true,
                                realtime: true,
                                start: 75,
                                end: 100,
                                height: 12,
                                bottom:0
                            },
                            {
                                type: 'inside',
                                realtime: true,
                                start: 75,
                                end: 100,
                                height: 12,
                                bottom:0
                            }
                        ],
                        xAxis: [
                            {
                                type: 'category',
                                boundaryGap: false,
                                data: data.xAxisData
                            }
                        ],
                        yAxis: [
                            {
                                type: 'value',
                                name: data.yAxisName,
                            }
                        ],
                        series: [
                            {
                                name: data.legendData,
                                type: 'line',
                                areaStyle: {
                                    normal: {
                                        color: this.color[1] //改变区域颜色
                                    }
                                },
                                itemStyle : {
                                    normal : {
                                        color:this.color[0], //改变折线点的颜色
                                        lineStyle:{
                                            width: 1,
                                            color:this.color[0] //改变折线颜色
                                        }
                                    }
                                },
                                symbolSize : 10,
                                data: data.data
                            }
                        ]
                };
                myChartLine.setOption(optionLine,true);
                window.addEventListener('resize', () => {
                    myChartLine.resize();
                })
                setTimeout(() => {
                   myChartLine.resize();
                }, 1000)

            }
        },

    };
</script>

<style>

</style>
