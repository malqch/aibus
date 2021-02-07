<!--
 @Author: songwenxin
 @Filename: index.vues
 @ProjectName: gfm-manage
 @Mail: songwenxin666@sina.com
 @Date: 2019-09-24 11:56
 @file-description:集群监控组件页面
-->
<template>
    <div style="color:#fff;" class="login-container" >
        <keep-alive>
            <canvas id="canvas" class="canvas" v-if="canvasFlag"></canvas>
        </keep-alive>
        <div style="" class="login_box">
            <div style="background-color:rgb(41,77,148, 0.9);" class="login_item">
                <div>
                    <el-row>
                        <el-col :span="24">
                            <div>
                                <img :src="busImg" alt="" style="width:150px;height: 150px;float:left;margin-top:80px;margin-left:100px;">
                            </div>
                        </el-col>
                        <el-col :span="24">
                            <div style="font-size:24px;color:#fff;width:100%;text-align: center;margin-top: 40px;">
                                智能公交后台管理系统V1.0
                            </div>
                        </el-col>
                    </el-row>
                </div>
            </div>
            <div style="background-color:rgba(0,0,0, 0.8)" class="login_item">
                <el-form class="" :model="dataForm" ref="dataForm" :rules="dataRule" label-width="0px" @keyup.enter.native="dataFormSubmit()"  v-if="true" style="margin-top: 80px;">
                    <el-form-item prop="loginName">
                        <input type="text" v-model="dataForm.loginName" placeholder="帐号" maxlength="20" style=" width:80%;height:40px !important;margin-left: 10%;background-color:rgba(0,0,0,0.3) !important;color:#fff !important;box-sizing: border-box;padding-left:40px;border:1px solid #fff !important;"/>
                        <i class="fa fa-user-circle-o" style="position: absolute;left:47px;margin-top: 12px;color:#fff;font-size:18px;"></i>
                        <span style="width:1px; height:16px;background-color:#fff;position: absolute;left: 70px;top: 12px;"></span>
                    </el-form-item>
                    <el-form-item prop="password">
                        <input  v-model="dataForm.password" type="password" placeholder="密码" maxlength="32" style="width:80%;height:40px !important;margin-left: 10%;background-color:rgba(0,0,0,0.3) !important;color:#fff !important;box-sizing: border-box;padding-left:40px;border:1px solid #fff !important;"/>
                        <i class="fa fa-lock" style="position: absolute;left:50px;margin-top: 11px;color:#fff;font-size:18px;"></i>
                        <span style="width:1px; height:16px;background-color:#fff;position: absolute;left: 70px;top: 12px;"></span>
                    </el-form-item>
                    <el-form-item prop="captcha">
                        <input type="text" v-model="dataForm.captcha" placeholder="验证码" style="width:80%;height:40px !important;margin-left: 10%;background-color:rgba(0,0,0,0.3) !important;color:#fff !important;box-sizing: border-box;padding-left:40px;border:1px solid #fff !important;"/>

                        <i class="fa fa-image" style="position: absolute;left:46px;margin-top: 12px;color:#fff;font-size:17px;"></i>
                        <span style="width:1px; height:16px;background-color:#fff;position: absolute;left: 70px;top: 12px;"></span>
                        <img :src="captchaPath" @click="getCaptcha()" alt style="margin-top:2px;height:26px;position: absolute;right:43px;top:5px;border:1px solid #000;border-radius:2px;"/>
                    </el-form-item>
                    <el-button :loading="loading" type="primary" style="width:80%;margin-bottom:30px;background: linear-gradient(#27CADF, #023C45) !important; border:1px solid #000;box-shadow:2px 2px 4px #000;margin-left:10%;" @click="dataFormSubmit()">登录</el-button>
                </el-form>
            </div>
        </div>
    </div>
</template>

<script>
    import {
        getUUID
    } from "@/util";
    import {
        API_SERVER_URL
    } from "@/api";
    import {
        loginSystem
    } from "@/api/system";
    export default {
        name: "Login",
        data() {
            return {
                busImg : 'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTk1OTIxNTAxMTUxIiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjQ4MTkiIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNNTA4LjgxOTg3NiAwQzIyOC45Njg5NDQgMCAwIDIyOC45Njg5NDQgMCA1MDguODE5ODc2czIyOC45Njg5NDQgNTA4LjgxOTg3NiA1MDguODE5ODc2IDUwOC44MTk4NzYgNTA4LjgxOTg3Ni0yMjguOTY4OTQ0IDUwOC44MTk4NzYtNTA4LjgxOTg3Ni0yMjguOTY4OTQ0LTUwOC44MTk4NzYtNTA4LjgxOTg3Ni01MDguODE5ODc2bTAgOTgyLjY1ODM4NUMyNDguMDQ5Njg5IDk4Mi42NTgzODUgMzQuOTgxMzY2IDc2OS41OTAwNjIgMzQuOTgxMzY2IDUwOC44MTk4NzYgMzQuOTgxMzY2IDI1MS4yMjk4MTQgMjQ4LjA0OTY4OSAzOC4xNjE0OTEgNTA4LjgxOTg3NiAzOC4xNjE0OTFzNDczLjgzODUwOSAyMTMuMDY4MzIzIDQ3My44Mzg1MDkgNDczLjgzODUwOWMwIDI1Ny41OTAwNjItMjEzLjA2ODMyMyA0NzAuNjU4Mzg1LTQ3My44Mzg1MDkgNDcwLjY1ODM4NW0wIDAiIGZpbGw9IiNmZmZmZmYiIHAtaWQ9IjQ4MjAiPjwvcGF0aD48cGF0aCBkPSJNNzA1Ljk4NzU3OCA3MzcuNzg4ODJ2MzguMTYxNDkxYzAgOS41NDAzNzMtOS41NDAzNzMgMTkuMDgwNzQ1LTE5LjA4MDc0NiAxOS4wODA3NDVoLTYwLjQyMjM2Yy05LjU0MDM3MyAwLTE5LjA4MDc0NS05LjU0MDM3My0xOS4wODA3NDUtMTkuMDgwNzQ1VjczNy43ODg4MmgtMTk3LjE2NzcwMnYzOC4xNjE0OTFjMCA5LjU0MDM3My05LjU0MDM3MyAxOS4wODA3NDUtMTkuMDgwNzQ1IDE5LjA4MDc0NWgtNjAuNDIyMzYxYy05LjU0MDM3MyAwLTE5LjA4MDc0NS05LjU0MDM3My0xOS4wODA3NDUtMTkuMDgwNzQ1VjczNy43ODg4MmMtMjIuMjYwODcgMC0zOC4xNjE0OTEtMTkuMDgwNzQ1LTM4LjE2MTQ5MS0zOC4xNjE0OTFWMzAyLjExMTgwMWMwLTIyLjI2MDg3IDM4LjE2MTQ5MS0zOC4xNjE0OTEgNjAuNDIyMzYtMzguMTYxNDkgMCAwIDc5LjUwMzEwNi0zOC4xNjE0OTEgMTc4LjA4Njk1Ny0zOC4xNjE0OTEgOTguNTgzODUxIDAgMTk3LjE2NzcwMiAzOC4xNjE0OTEgMTk3LjE2NzcwMiAzOC4xNjE0OTEgMjIuMjYwODcgMCAzOC4xNjE0OTEgMTkuMDgwNzQ1IDM4LjE2MTQ5MSAzOC4xNjE0OXYzOTcuNTE1NTI4Yy0zLjE4MDEyNCAxOS4wODA3NDUtMTkuMDgwNzQ1IDM4LjE2MTQ5MS00MS4zNDE2MTUgMzguMTYxNDkxbS0zNDYuNjMzNTQxLTYwLjQyMjM2YzI4LjYyMTExOCAwIDUwLjg4MTk4OC0yMi4yNjA4NyA1MC44ODE5ODgtNDcuNzAxODY0IDAtMjguNjIxMTE4LTIyLjI2MDg3LTQ3LjcwMTg2My01MC44ODE5ODgtNDcuNzAxODYzcy01MC44ODE5ODggMjIuMjYwODctNTAuODgxOTg3IDQ3LjcwMTg2M2MzLjE4MDEyNCAyOC42MjExMTggMjUuNDQwOTk0IDQ3LjcwMTg2MyA1MC44ODE5ODcgNDcuNzAxODY0bTIyOC45Njg5NDQtNDEzLjQxNjE0OWgtMTU5LjAwNjIxMWMtOS41NDAzNzMgMC0xOS4wODA3NDUgOS41NDAzNzMtMTkuMDgwNzQ1IDE5LjA4MDc0NXM5LjU0MDM3MyAxOS4wODA3NDUgMTkuMDgwNzQ1IDE5LjA4MDc0NWgxNTkuMDA2MjExYzkuNTQwMzczIDAgMTkuMDgwNzQ1LTkuNTQwMzczIDE5LjA4MDc0Ni0xOS4wODA3NDVzLTkuNTQwMzczLTE5LjA4MDc0NS0xOS4wODA3NDYtMTkuMDgwNzQ1bTExNy42NjQ1OTcgMTA4LjEyNDIyM2MwLTkuNTQwMzczLTkuNTQwMzczLTE5LjA4MDc0NS0xOS4wODA3NDYtMTkuMDgwNzQ1SDMzMC43MzI5MTljLTkuNTQwMzczIDAtMTkuMDgwNzQ1IDkuNTQwMzczLTE5LjA4MDc0NSAxOS4wODA3NDV2MTE3LjY2NDU5NmMwIDkuNTQwMzczIDkuNTQwMzczIDE5LjA4MDc0NSAxOS4wODA3NDUgMTkuMDgwNzQ2aDM1Ni4xNzM5MTNjOS41NDAzNzMgMCAxOS4wODA3NDUtOS41NDAzNzMgMTkuMDgwNzQ2LTE5LjA4MDc0NnYtMTE3LjY2NDU5Nm0tNDcuNzAxODY0IDIwOS44ODgxOTljLTI4LjYyMTExOCAwLTUwLjg4MTk4OCAyMi4yNjA4Ny01MC44ODE5ODcgNDcuNzAxODYzIDAgMjguNjIxMTE4IDIyLjI2MDg3IDQ3LjcwMTg2MyA1MC44ODE5ODcgNDcuNzAxODY0IDI4LjYyMTExOCAwIDUwLjg4MTk4OC0yMi4yNjA4NyA1MC44ODE5ODgtNDcuNzAxODY0LTMuMTgwMTI0LTI1LjQ0MDk5NC0yNS40NDA5OTQtNDcuNzAxODYzLTUwLjg4MTk4OC00Ny43MDE4NjNtMCAwIiBmaWxsPSIjZmZmZmZmIiBwLWlkPSI0ODIxIj48L3BhdGg+PC9zdmc+',
                fontstyle: {
                    // zm
                },
                dataForm: {
                    loginName: "",
                    password: "",
                    captcha: "",
                    uuid: ""
                },
                identifyCodes: "1234567890",
                identifyCode: "",
                dataRule: {
                    loginName: [{
                        required: true,
                        message: "帐号不能为空",
                        trigger: "blur"
                    }],
                    password: [{
                        required: true,
                        message: "密码不能为空",
                        trigger: "blur"
                    }],
                    captcha: [{
                        required: true,
                        message: "验证码不能为空",
                        trigger: "blur"
                    }]
                },
                passwordType: "password",
                loading: false,
                showDialog: false,
                redirect: undefined,
                picLyanzhengma: "",
                checkCode: "",
                captchaPath: "",
                canvasFlag : true
            };
        },
        created() {
            this.getCaptcha();
            // 验证码初始化
        },
        methods: {
            deactivated  : function(){
                if(this.canvasFlag){
                    const canvas = document.getElementById('canvas');
                    const ctx = canvas.getContext('2d');
                    var w, h, nodes = [], edges = [];
                    window.onresize = function (){
                        w = canvas.width = window.innerWidth - 20;
                        h = canvas.height = window.innerHeight - 20;
                        createParticles(w * h / 10000);
                    }
                    window.onresize();
                    function createParticles(n){
                        if(n !== nodes.length){
                            nodes = [];
                            edges = [];
                            for(var i=0; i<n; i++){
                                var node = {
                                    x: Math.random() * w,
                                    y: Math.random() * h,
                                    vx: (Math.random() - 0.5) * 1.2,
                                    vy: (Math.random() - 0.5) * 1.2,
                                    r: Math.random() > 0.9 ? Math.random() * 2 + 0.5 : Math.random() * 2 + 1,
                                    color: '#fff'
                                };
                                nodes.push(node);
                            }
                        }
                        for(var i=0, len=nodes.length; i<len; i++){
                            for(var j=i+1; j<len; j++){
                                edges.push({
                                    start: nodes[i],
                                    end: nodes[j]
                                })
                            }
                        }
                    }
                    function update(){
                        nodes.forEach(function(item, i) {
                            item.x += item.vx;
                            item.y += item.vy;
                            if(item.x - item.r <= 0 || item.x + item.r >= w){
                                item.vx *= -1;
                            }
                            if(item.y - item.r <= 0 || item.y + item.r >= h){
                                item.vy *= -1;
                            }
                        });
                    }
                    function render(){
                        ctx.clearRect(0, 0, w, h);
                        nodes.forEach(function(item, i) {
                            ctx.save();
                            ctx.fillStyle = item.color;
                            ctx.beginPath();
                            ctx.arc(item.x, item.y, item.r, 0, 2*Math.PI);
                            ctx.fill();
                            ctx.restore();
                        });
                        edges.forEach(function(item) {
                            var l = distance(item);
                            var s = w > h ? w/8 : h/8;
                            if(l > s) return;
                            ctx.save();
                            ctx.strokeStyle = '#fff';
                            ctx.globalAlpha = 1 - l/s;
                            ctx.lineWidth = (1 - l/s) * 0.5;
                            ctx.beginPath();
                            ctx.moveTo(item.start.x, item.start.y);
                            ctx.lineTo(item.end.x, item.end.y);
                            ctx.stroke();
                            ctx.restore();
                        });
                    }
                    function distance(node){
                        var x = Math.pow(node.start.x - node.end.x, 2);
                        var y = Math.pow(node.start.y - node.end.y, 2);
                        return Math.sqrt(x + y);
                    }
                    (function fn(){
                        window.requestAnimationFrame(fn);
                        update();
                        render();
                    })();
                }
            },
            toApp() {
                sessionStorage.token = "b668d07d879e8fc49fc18016eaa41e92";
                this.$router.push({
                    path: "/Main"
                });
            },
            clearCookie :function(){
                var keys = document.cookie.match(/[^ =;]+(?=\=)/g);
                if (keys) {
                    for (var i =  keys.length; i--;)
                        document.cookie=keys[i]+'=0;expires=' + new Date( 0).toUTCString()
                }
            },
            // 获取验证码
            getCaptcha() {
                this.dataForm.uuid = getUUID();
                this.captchaPath =
                    `${API_SERVER_URL}/captcha.jpg?uuid=` + this.dataForm.uuid;
            },
            dataFormSubmit() {
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        this.loading=true;
                        this.clearCookie();
                        loginSystem(this.dataForm).then(res => {
                            this.loading=false;
                            if (res.data && res.data.code === 0) {
                                //登录成功，写token到session中，并获取路由

                                sessionStorage.setItem("token", res.data.data.token);
                                var positionAuth = res.data.data.positionAuth;
                                if(positionAuth.length > 0){
                                    var systemAuth = '';
                                    var objectId = '';
                                    for(var i=0;i<positionAuth.length;i++){
                                        systemAuth = positionAuth[i].systemAuth;
                                        if(positionAuth[i].linkType == 3){
                                            objectId = positionAuth[i].areaOrgId;
                                        }
                                    }
                                    if(systemAuth == 'manage'){
                                        sessionStorage.setItem("objectId", objectId);
                                        this.$router.push({
                                            path: "/Home"
                                        });
                                    }else{
                                        this.$message({
                                            message: '此用户无权限登录商家后台管理系统',
                                            type: "error",
                                            offset: 30,
                                            duration: 1500
                                        });
                                    }
                                }else{
                                    this.$message({
                                        message: res.data.message,
                                        type: "error",
                                        offset: 30,
                                        duration: 1500
                                    });
                                }
                            } else {
                                this.getCaptcha();
                                this.dataForm.captcha=''
                                this.$message({
                                    message: res.data.message,
                                    type: "error",
                                    offset: 30,
                                    duration: 1500
                                });
                            }
                        });
                    }
                })

            }
        },
        mounted : function(){
            this.canvasFlag = true;
            this.deactivated();
        },
        destroyed : function(){
            this.canvasFlag = false;
            console.log(this.canvasFlag)
        }

    };
</script>

<style scoped>
    #canvas{
        padding: 0;
        margin:0;
        width: 100%;
        height: 90%;
        overflow: hidden;
    }



    .login-container {
        position: fixed;
        height: 100%;
        width: 100%;
        background: url("../../assets/image/login_bg1.jpg") no-repeat ;
        background-size: 100% 100%;
    }
    .login_box{
        width:720px;
        height:380px;
        position:absolute;
        top: 50%;
        left:50%;
        margin-left:-360px;
        margin-top: -190px;
        overflow: hidden;
    }
    .login_box .login_item{
        width:50%;
        height:400px;
        float:left;
    }

    .login-container .login-form {
        position: absolute;
        left: 50%;
        top: 50%;
        margin-left: -296px;
        margin-top: -210px;
        width: 520px;
        max-width: 100%;
        border-radius: 10px;
        padding: 15px 35px 15px;
        background-color:rgba(0,0,0, 0.4);
        border:1px solid rgba(255,255,255,0.4);
        box-shadow: 2px  2px 15px #000;
    }
    /deep/ .login_item .el-form-item__error{
        left:40px;
    }
</style>
