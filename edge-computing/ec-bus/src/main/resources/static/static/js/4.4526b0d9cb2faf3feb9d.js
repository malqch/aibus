webpackJsonp([4],{q8bm:function(t,s,e){"use strict";Object.defineProperty(s,"__esModule",{value:!0});var a=e("f+26"),n=e("gyMJ");var o={name:"login",data:function(){return{version:"1.0.0",loading:null,username:"",password:""}},methods:{submit:function(){var t,s,e,o=this;this.username?this.password?(this.loading=this.$toast.loading(),(t=this.username,s=this.password,e={userName:t,password:s},Object(a.a)({url:n.a+"/auth/login",method:"POST",data:e})).then(function(t){(o.loading.hide(),1==t.data.code)?(sessionStorage.setItem("token",t.data.data.token),"app"==sessionStorage.getItem("platform")?o.$router.push("/sn-scan"):o.$router.push("/sn")):o.$toast.text(t.data.msg)}).catch(function(t){o.$toast.text(t.response.data.message)})):this.$toast.fail("请输入密码"):this.$toast.fail("请输入用户名")},getSysVersion:function(){}},mounted:function(){this.getSysVersion(),delete sessionStorage.token}},i={render:function(){var t=this.$createElement,s=this._self._c||t;return s("div",{attrs:{id:"wrap"}},[s("div",{staticClass:"version-info"},[this._v("v"+this._s(this.version))])])},staticRenderFns:[]};var r=e("VU/8")(o,i,!1,function(t){e("uSpL")},"data-v-57e884e4",null);s.default=r.exports},uSpL:function(t,s){}});