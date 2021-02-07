<!--
 @Author: songwenxin
 @Filename: index.vue
 @ProjectName: gfm-manage
 @Mail: songwenxin666@sina.com
 @Date: 2019-09-24 13:23
 @file-description:组件页面
-->
<template>
    <div>
        <div class="carboxTitle">
            {{progressObj.firstName}}
            <span :style="'float: right; color:'+ bg.color">{{progressObj.firstCount}}</span>
        </div>
        <div class="progressBg">
            <div class="progressC" :style="'width: '+progressObj.percent+'%;background-color:'+bg.color+';background: -webkit-linear-gradient(0deg,'+bg.bg1+','+bg.bg2+');'">

            </div>
            <div class="text" :style="'width:'+(progressObj.percent<40?'':progressObj.percent+'%')" :class="[progressObj.percent<40?'colorblack':'']">
                <span>{{progressObj.secondName}}</span>
                <span style="float: right;margin-top: 3px;">{{progressObj.secondCount}}</span>
            </div>
        </div>
    </div>

</template>

<script>
    export default {
        data() {
            return {

            };
        },
        props: ['progressObj','bg'],
        components: {
        },
        created(){
        },
        mounted(){
        },
        methods: {
        },

    };
</script>

<style scoped="scoped">
.carboxTitle{
    margin-bottom: 6px;
    font-size: 13px;
}
.progressC{
    height: 24px;
    line-height: 23px;
    color: #fff;
}

.progressBg{
    width: calc(100% - 6px);
    height: 24px;
    line-height: 22px;
    background-color: #fff;
    padding: 3px;
    border: 1px solid #e5e8ed;
    margin: 2px 0;
    position: relative;
	box-shadow:inset 2px 2px 5px rgba(0,0,0,0.2);
}
.text{
    position: absolute;
    top: 3px;
    left: 0px;
    color: #fff;
}
.text span{
    padding: 0 10px;
    font-size: 12px;

}
.colorblack{
    color: #616161!important;
    width: 100%;
}
</style>
