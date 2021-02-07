
<template>
    <div class="bg_img">
        <div class="home_text">
            欢迎进入智能公交后台系统
        </div>
    </div>
</template>

<script>
    export default {
        name: "index"
    }
</script>

<style scoped>
    .bg_img{
        width:100%;
        height:100%;
        background-image: url('../../assets/image/login_bg1.jpg');
        background-repeat:no-repeat;
        -webkit-background-size: 100% 100%;
        background-size: 100%;
    }
    .bg_img .home_text{
        width:100%;
        color:#fff;
        font-size:20px;
        text-align: center;
        float: left;
        margin-top: 420px;
    }

</style>
