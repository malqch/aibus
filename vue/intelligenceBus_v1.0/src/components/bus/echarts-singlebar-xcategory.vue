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
        // props: {
        //     barObj: {
        //         type: Object,
        //         default: {}
        //     },
        //     height:String

        // },
        props: ['barObj','height','color','boxid'],
        components: {
        },
        created(){
        },
        mounted(){

            this.myEcharts();
        },
        watch:{
            barObj(){
                this.myEcharts();
            }
        },
        methods: {
            myEcharts(){
                var myChartBar = this.$echarts.init(document.getElementById(this.boxid));
                var optionBar = {
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    grid: {
                        left: '6%',
                        right: '14%',
                        bottom: '5%',
                        top:'18%',
                        containLabel: true
                    },
                    xAxis: {
                        type: 'category',
                        name: this.barObj.xAxisName,
                        data: this.barObj.textList,
                        axisTick:{
                            show:false
                        },
                        axisLabel: {
                            interval:0,
                            rotate:20
                        }
                    },
                    yAxis: {
                        type: 'value',
                        name: this.barObj.yAxisName,
                        axisTick:{
                            show:false
                        },
                    },
                    series: [
                        {
                            type: 'bar',
                            barWidth : 30,
                            itemStyle: {
                                normal: {
                                    color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                        offset: 0,
                                        color: this.color[0]
                                    }, {
                                        offset: 1,
                                        color: this.color[1]
                                    }]),
                                }
                            },
                            data: this.barObj.valueList
                        }
                    ]
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
