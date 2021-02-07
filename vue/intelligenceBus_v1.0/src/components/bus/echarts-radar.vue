<!--
 @Author: songwenxin
 @Filename: index.vue
 @ProjectName: gfm-manage
 @Mail: songwenxin666@sina.com
 @Date: 2019-09-24 13:23
 @file-description:组件页面
-->
<template>
    <div id="radar" :style="{height:height}"></div>
</template>

<script>
    export default {
        data() {
            return {

            };
        },
        props: ['radarObj','height'],
        components: {
        },
        created(){

        },
        mounted(){
            this.myEcharts(this.radarObj);

        },
        watch:{
            "radarObj":{
                handler:function(newVal, oldVal){
                    this.myEcharts(newVal);
                } ,
                "deep":true
            }

        },
        methods: {
            myEcharts(data){

                var myChartRadar = this.$echarts.init(document.getElementById('radar'));

                var optionRadar = {
                    tooltip: {
                        trigger: 'axis'
                    },
                    radar: [
                        {
                            indicator: data.indicatorList,
                            center: ['50%', '50%'],
                            radius: 80,
                            nameGap:5,
                            splitArea:false,
                            name:{
                                textStyle: {
                                 color:'#333333'
                                }
                            }

                        }
                    ],
                    series: [
                        {
                            type: 'radar',
                            tooltip: {
                                trigger: 'item'
                            },
                            areaStyle: {
                                opacity:1,
                                color:new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                        offset: 0,
                                        color: "#f4707d"
                                    }, {
                                        offset: 1,
                                        color: "#b73e50"
                                    }])
                            },
                            symbolSize:0,
                            data: [
                                {
                                    value: data.data,
                                    name: data.name
                                }
                            ]
                        }
                    ]
                };
                myChartRadar.setOption(optionRadar);
                window.addEventListener('resize', () => {
                    myChartRadar.resize();
                })
                setTimeout(() => {
                   myChartRadar.resize();
                }, 1000)

            }
        },

    };
</script>

<style>

</style>
