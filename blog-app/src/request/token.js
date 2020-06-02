export function getToken() {
  return localStorage.token
}

export function setToken(token) {
  return localStorage.token = token
}

export function removeToken() {
  return localStorage.removeItem('token')
}

export function getUserInfo() {
  return localStorage.userInfo
}
export function setUserInfo(userInfo){
  return localStorage.userInfo = userInfo
}

export function removeUserInfo() {
  return localStorage.removeItem("userInfo")
}
