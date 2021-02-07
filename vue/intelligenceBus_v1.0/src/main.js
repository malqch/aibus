// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
// import App from './App'
// import router from './router'


//引入element-ui 组将框架
import ElementUI from 'element-ui';
Vue.use(ElementUI)
//引入element-ui 组件样式文件
import 'element-ui/lib/theme-chalk/index.css';


import BaiduMap from 'vue-baidu-map'
import 'font-awesome/css/font-awesome.min.css';
import NProgress from 'nprogress';
import 'nprogress/nprogress.css';

import echarts from 'echarts';
Vue.prototype.$echarts = echarts;

Vue.config.productionTip = false

import App from './App'
import router from './router'
Vue.use(BaiduMap, {
    // ak 是在百度地图开发者平台申请的密钥 详见 http://lbsyun.baidu.com/apiconsole/key */
    ak: 'yOIDo1iL7W7ONYjg7NwKzXssIqKDp9UR'
})

NProgress.inc(0.2)
NProgress.configure({
    easing: 'ease',
    speed: 500,
    showSpinner: false
})

router.beforeEach((to, from, next) => {
    NProgress.start()
    next()
})
router.afterEach(() => {
    NProgress.done()
})

/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    components: {
        App
    },
    template: '<App/>'
})
