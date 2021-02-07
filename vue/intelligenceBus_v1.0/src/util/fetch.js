import axios from 'axios'
import {Message} from 'element-ui'

// 创建axios实例
const service = axios.create({
    // baseURL: process.env.BASE_API || '', // api的base_url
    //timeout: 50000 // 请求超时时间,
    // withCredentials: true
})

// 转化GET请求，data参数为url地址参数
const formatGET = (config) => {
    const data = config.data
    if (['get'].indexOf(config.method.toLowerCase()) > -1 && data && config.url.indexOf('?') < 0) {
        config.url = config.url + '?' + Object.keys(data).map(
            (key) => {
                if (data[key] === null || data[key] === undefined) return null
                return `${encodeURIComponent(key)}=${encodeURIComponent(data[key])}`
            }
        ).filter(
            (item) => (item !== null && item !== undefined)
        ).join('&')
    }
    return config
}

// request拦截器
service.interceptors.request.use(config => {
    // Do something before request is sent
    config = formatGET(config)
    config.headers.token = sessionStorage.getItem('token')
    return config
}, error => {
    // Do something with request error
    console.log(error) // for debug
    Promise.reject(error)
})

// respone拦截器
service.interceptors.response.use(
    response => {
        // console.log(response)
        if (response.data.code == 401) {
            window.location.href = '/#/'
        }
        return response
    },
    error => {
        console.log(error)
        const resp = error.response
        console.log(resp) // for debug
        // console.log(error.response.status)// for debug
        if (resp && resp.status === 401) {
            if (!(resp.data.message === '用户名或密码不正确')) {
                window.location.href = '/#/'
                // this.$router.push({
                //   name: 'Login'
                // });
            }

            // window.location.href = 'http://www.baidu.com'
        }

        Message({
            message: resp ? resp.data.Message : error.message,
            type: 'error',
            offset: 30,
            duration: 1 * 1000
        })
        return Promise.reject(error)
    }
)

export default service
