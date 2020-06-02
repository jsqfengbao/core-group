import request from '@/request'
import {getToken} from "../request/token";

export function login(loginParams) {
  const data = loginParams
  return request({
    url: '/sys/login',
    method: 'post',
    data
  })
}

export function getCaptcha(currentTime) {
  return request({
    url: '/sys/randomImage/'+currentTime,
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/sys/logout',
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
      'X-Access-Token':  getToken()
    }
  })
}

export function getUserInfo() {
  return request({
    url: '/users/currentUser',
    method: 'get'
  })
}

export function getSmsCode(mobile,smsmode) {
  const data = {
    mobile,
    smsmode
  }
  return request({
    url: '/sys/sms',
    method: 'post',
    data
  })
}

export function register(username,password,telephone,smscode) {
  const data = {
    'username': username,
    'password': password,
    'phone': telephone,
    'smscode': smscode
  }
  console.log(data)
  return request({
    url: '/sys/user/register',
    method: 'post',
    data
  })
}

