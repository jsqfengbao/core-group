import Vuex from 'vuex'
import Vue from 'vue'
import {getToken, setToken, removeToken, getUserInfo, setUserInfo, removeUserInfo} from '@/request/token'
import {login, logout, register} from '@/api/login'

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    id: '',
    username: '',
    avatar: '',
    token: getToken(),
  },
  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token;
    },
    SET_USERNAME: (state,username) => {
      state.username = username
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ID: (state, id) => {
      state.id = id
    }
  },
  actions: {
    login({commit}, loginParams) {
      return new Promise((resolve, reject) => {
        login(loginParams).then(data => {
          if(data.code == '200'){
            commit('SET_TOKEN', data.result['token'])
            commit('SET_USERNAME',data.result['userInfo'].username)
            commit('SET_AVATAR',data.result['userInfo'].avatar)
            commit('SET_ID',data.result['id'])
            setToken(data.result['token'])
            setUserInfo(JSON.stringify(data.result['userInfo']))
            resolve()
          }else if(data.code == '500'){
            Vue.prototype.$message.error(data.message)
            reject(data)
          }
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 退出
    logout({commit, state}) {
      return new Promise((resolve, reject) => {
        logout().then(data => {
          commit('SET_TOKEN', '')
          commit('SET_USERNAME', '')
          commit('SET_AVATAR', '')
          commit('SET_ID', '')
          removeToken()
          removeUserInfo()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 前端 登出
    fedLogOut({commit}) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        commit('SET_ACCOUNT', '')
        commit('SET_NAME', '')
        commit('SET_AVATAR', '')
        commit('SET_ID', '')
        removeToken()
        removeUserInfo()
        resolve()
      }).catch(error => {
        reject(error)
      })
    },
    register({commit}, user) {
      return new Promise((resolve, reject) => {
        register(user.username, user.password, user.telephone, user.smscode).then((data) => {
          if(data.success){
            resolve()
          } else{
            // this.$message({message: data.message, type: 'error', showClose: true })
            reject(data.message)
          }
          // commit('SET_TOKEN', data.data['Oauth-Token'])
          // setToken(data.data['Oauth-Token'])
          resolve()
        }).catch((error) => {
          reject(error)
        })
      })
    }
  }
})
