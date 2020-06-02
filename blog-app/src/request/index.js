import axios from 'axios'
import {Message} from 'element-ui'
import store from '@/store'
import {getToken} from '@/request/token'

const service = axios.create({
  baseURL: process.env.BASE_API,
  timeout: 10000
})

//request拦截器
service.interceptors.request.use(config => {

  if (store.state.token) {
    config.headers['X-Access-Token'] = getToken()
  }
  return config
}, error => {

  Promise.reject(error)
})
const err = (error) => {
  if (error.response) {
    let data = error.response.data
    const token = getToken()
    console.log("------异常响应------",token)
    console.log("------异常响应------",error.response.status)
    switch (error.response.status) {
      case 403:
        notification.error({ message: '系统提示', description: '拒绝访问',duration: 4})
        break
      case 500:
        //notification.error({ message: '系统提示', description:'Token失效，请重新登录!',duration: 4})
        if(token && data.message == "验证码错误"){
          console.log(data.message)
        }
        if(token && data.message=="Token失效，请重新登录"){
          // update-begin- --- author:scott ------ date:20190225 ---- for:Token失效采用弹框模式，不直接跳转----
          // store.dispatch('Logout').then(() => {
          //     window.location.reload()
          // })
          Modal.error({
            title: '登录已过期',
            content: '很抱歉，登录已过期，请重新登录',
            okText: '重新登录',
            mask: false,
            onOk: () => {
              store.dispatch('Logout').then(() => {
                Vue.ls.remove(ACCESS_TOKEN)
                window.location.reload()
              })
            }
          })
          // update-end- --- author:scott ------ date:20190225 ---- for:Token失效采用弹框模式，不直接跳转----
        }
        break
      case 404:
        notification.error({ message: '系统提示', description:'很抱歉，资源未找到!',duration: 4})
        break
      case 504:
        notification.error({ message: '系统提示', description: '网络超时'})
        break
      case 401:
        notification.error({ message: '系统提示', description:'未授权，请重新登录',duration: 4})
        if (token) {
          store.dispatch('Logout').then(() => {
            setTimeout(() => {
              window.location.reload()
            }, 1500)
          })
        }
        break
      default:
        notification.error({
          message: '系统提示',
          description: data.message,
          duration: 4
        })
        break
    }
  }
  return Promise.reject(error)
};
// respone拦截器
service.interceptors.response.use((response) => {
  return response.data
}, err)

// service.interceptors.response.use(
//   response => {
//
//     //全局统一处理 Session超时
//     if (response.headers['session_time_out'] == 'timeout') {
//       store.dispatch('fedLogOut')
//     }
//
//     const res = response.data;
//     //0 为成功状态
//     if (!res.success) {
//
//       //90001 Session超时
//       if (res.code === 90001) {
//         return Promise.reject('error');
//       }
//
//       //20001 用户未登录
//       if (res.code === 20001) {
//         console.info("用户未登录")
//
//         Message({
//           type: 'warning',
//           showClose: true,
//           message: '未登录或登录超时，请重新登录哦'
//         })
//
//         return Promise.reject('error');
//       }
//
//       //70001 权限认证错误
//       if (res.code === 70001) {
//         console.info("权限认证错误")
//         Message({
//           type: 'warning',
//           showClose: true,
//           message: '你没有权限访问哦'
//         })
//         return Promise.reject('error');
//       }
//
//       return Promise.reject(res.msg);
//     } else {
//       return response.data;
//     }
//   },
//   error => {
//     Message({
//       type: 'warning',
//       showClose: true,
//       message: '连接超时'
//     })
//     return Promise.reject('error')
//   })

export default service
