<!--
 @Author: songwenxin
 @Filename: index.vue
 @ProjectName: gfm-manage
 @Mail: songwenxin666@sina.com
 @Date: 2019-09-24 13:23
 @file-description:组件页面
-->
<template>
    <div id="pie" :style="{height:height}"></div>
</template>

<script>
    export default {
        data() {
            return {
            };
        },
        props:['pieObj','height'],
        components: {
        },
        created(){
        },
        mounted(){
            this.myEcharts(this.pieObj);

        },
        watch:{
            "pieObj":{
               handler:function(newVal, oldVal){
                   this.myEcharts(newVal);
               } ,
               "deep":true
            }
        },
        methods: {
            myEcharts(data){

                var myChartPie = this.$echarts.init(document.getElementById('pie'));
                var optionPie = {
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b} : {c} ({d}%)'
                    },
                    color : [ '#ff0044', '#ff00f8', '#00aff5', '#ff7800', '#ffea00', '#7736c0', 'purple' ],
                    graphic:{
                        type:"text",
                        left:"center",
                        top:"50%",
                        style:{
                            text:data.name,
                            textAlign:"center",
                            fill:"#333",
                            fontSize:12
                        }
                    },
                    series: [
                        {
                            name: data.name,
                            type: 'pie',
                            radius: ['55%', '85%'],
                            center: ['50%', '50%'],
                            data: data.data,
                            emphasis: {
                                itemStyle: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.4)'
                                }
                            }
                        }
                    ]
                };
                myChartPie.setOption(optionPie,true);
                window.addEventListener('resize', () => {
                    myChartPie.resize();
                })
                setTimeout(() => {
                   myChartPie.resize();
                }, 1000)

            }
        },

    };
</script>

<style>

</style>
