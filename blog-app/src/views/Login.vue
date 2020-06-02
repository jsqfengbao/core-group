<template>
  <div id="login" v-title data-title="登录 - For Fun">
    <!--<video preload="auto" class="me-video-player" autoplay="autoplay" loop="loop">
          <source src="../../static/vedio/sea.mp4" type="video/mp4">
      </video>-->

    <div class="me-login-box me-login-box-radius">
      <h1>博客 登录</h1>

      <el-form ref="userForm" :model="userForm" :rules="rules">
        <el-form-item prop="username">
          <el-input placeholder="请输入用户名" v-model="userForm.username"></el-input>
        </el-form-item>

        <el-form-item prop="password">
          <el-input placeholder="密码" type="password" v-model="userForm.password"></el-input>
        </el-form-item>
        <el-form-item prop="inputCode">
          <el-col :span="16">
            <el-input type="text"  size="large" placeholder="请输入验证码" v-model="userForm.inputCode"></el-input>
          </el-col>
          <el-col :span="8" style="text-align: right">
            <img v-if="requestCodeSuccess" style="margin-top: 2px;" :src="randCodeImage" @click="handleChangeCheckCode"/>
            <img v-else style="margin-top: 2px;" src="../assets/checkcode.png" @click="handleChangeCheckCode"/>
          </el-col>
        </el-form-item>
        <el-form-item>
          <el-checkbox v-model="userForm.rememberMe">自动登录</el-checkbox>
          <a class="forge-password" style="float: right;" href="http://localhost:3000/user/alteration">
            忘记密码
          </a>
        </el-form-item>
        <el-form-item size="small" class="me-login-button">
          <el-button type="primary" @click.native.prevent="login('userForm')" :loading="loginBtn">登录</el-button>
        </el-form-item>
      </el-form>

      <div class="me-login-design">
        <p>Designed by
          <strong>
            <router-link to="/" class="me-login-design-color">D.K</router-link>
          </strong>
        </p>
      </div>

    </div>
  </div>
</template>

<script>
  import {getCaptcha} from "@/api/login";
  import {login} from "@/api/login";

  export default {
    name: 'Login',
    data() {
      return {
        userForm: {
          username: '',
          password: '',
          inputCode: '',
          rememberMe: ''
        },
        inputCodeContent: "",
        randCodeImage: "",
        requestCodeSuccess: false,
        loginBtn: false,
        currdatetime: '',
        rules: {
          username: [
            {required: true, message: '请输入用户名', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'}
          ],
          inputCode: [
            {required: true, message: '请输入验证码', trigger: 'blur'},
            {max: 4,message: '不能大于4个字符', trigger: 'blur'}
          ]
        }
      }
    },
    created () {
      this.currdatetime =new Date().getTime()
      this.handleChangeCheckCode()
    },
    methods: {
      login(formName) {
        let that = this;
        let loginParams = {};
        that.loginBtn = true;
        this.$refs[formName].validate((valid) => {
          if (valid) {
            loginParams.username =this.userForm.username
            loginParams.password = this.userForm.password
            loginParams.remember_me = this.userForm.rememberMe
            loginParams.captcha = this.userForm.inputCode
            loginParams.checkKey = that.currdatetime
            that.$store.dispatch('login',loginParams).then((data) => {
              that.loginBtn = true
              that.$router.go(-1)
            }).catch((error) =>{
              this.handleChangeCheckCode() //失败刷新验证码
              that.loginBtn = false
            })
          } else {
            return false;
          }
        });
      },
      handleChangeCheckCode(){
        let that = this
        getCaptcha(this.currdatetime).then(data => {
          that.randCodeImage = data.result
          that.requestCodeSuccess = true
        }).catch(error =>{
          if(error != 'error'){
            that.requestCodeSuccess = false
            that.$message({type:'error', message: '获取验证码失败', showClose: true})
          }
        })
      },
    }
  }
</script>

<style scoped>
  #login {
    min-width: 100%;
    min-height: 100%;
  }

  .me-video-player {
    background-color: transparent;
    width: 100%;
    height: 100%;
    object-fit: fill;
    display: block;
    position: absolute;
    left: 0;
    z-index: 0;
    top: 0;
  }

  .me-login-box {
    position: absolute;
    width: 300px;
    height: 330px;
    background-color: white;
    margin-top: 150px;
    margin-left: -180px;
    left: 50%;
    padding: 30px;
  }

  .me-login-box-radius {
    border-radius: 10px;
    box-shadow: 0px 0px 1px 1px rgba(161, 159, 159, 0.1);
  }

  .me-login-box h1 {
    text-align: center;
    font-size: 24px;
    margin-bottom: 20px;
    vertical-align: middle;
  }

  .me-login-design {
    text-align: center;
    font-family: 'Open Sans', sans-serif;
    font-size: 18px;
  }

  .me-login-design-color {
    color: #5FB878 !important;
  }

  .me-login-button {
    text-align: center;
  }

  .me-login-button button {
    width: 100%;
  }

  .getCaptcha {
    display: block;
    width: 100%;
    height: 40px;
  }

</style>
