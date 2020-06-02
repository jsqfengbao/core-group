<template>
  <div id="register" v-title data-title="注册">

    <div class="me-login-box me-login-box-radius">
      <h1>博客注册</h1>

      <el-form ref="userForm" :model="userForm" :rules="rules">
        <el-form-item prop="account">
          <el-input placeholder="用户名" v-model="userForm.username"></el-input>
        </el-form-item>

        <el-form-item prop="password">
          <el-input placeholder="请输入密码" v-model="userForm.password" type="password" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item prop="confirmPassword">
          <el-input placeholder="请输入确认密码" v-model="userForm.confirmPassword" type="password"  autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item prop="telephone" ref="telephone">
          <el-input placeholder="请输入11位手机号" v-model="userForm.telephone"></el-input>
        </el-form-item>

        <el-row :gutter="20">
          <el-col :span="16">
            <el-form-item prop="smscode">
              <el-input placeholder="短信验证码" v-model="userForm.smscode"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item>
              <el-button type="primary" :disabled="state.smsSendBtn"
                         @click="getCaptcha()" v-text="!state.smsSendBtn && '验证码'||(state.time+' s')"></el-button>
            </el-form-item>
          </el-col>
        </el-row>


        <el-form-item size="small" class="me-login-button">
          <el-button type="primary" @click.native.prevent="register('userForm')">注册</el-button>
        </el-form-item>
      </el-form>

      <div class="me-login-design">
        <p>Designed by
          <strong>
            <router-link to="/" class="me-login-design-color">ForFun</router-link>
          </strong>
        </p>
      </div>

    </div>
  </div>
</template>

<script>
  import {register,getSmsCode} from '@/api/login'

  export default {
    name: 'Register',
    data() {
      var validatePass = (rule,value,callback) => {
        if(value === '') {
          callback(new Error('请输入密码'));
        }else {
          if(this.userForm.password !== '') {
            this.$refs.userForm.validateField('password');
          }
          callback();
        }
      };
      var validatePass2 = (rule,value,callback) => {
        if(value === '') {
          callback(new Error('请再次输入密码'));
        } else if(value !== this.userForm.password) {
          callback(new Error('两次输入密码不一致'));
        } else {
          callback();
        }
      };
      return {
        userForm: {
          username: '',
          password: '',
          confirmPassword: '',
          telephone: '',
          smscode: ''
        },
        state: {
          time: 60,
          smsSendBtn: false,
          passwordLevel: 0,
          passwordLevelChecked: false,
          percent: 10,
          progressColor: '#FF0000'
        },
        rules: {
          username: [
            {required: true, message: '请输入用户名', trigger: 'blur'},
            {max: 10, message: '不能大于10个字符', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'},
            {max: 10, message: '不能大于10个字符', trigger: 'blur'}
          ],
          confirmPassword: [
            {required: true, message: '请输入确认密码', trigger: 'blur'},
            {validator: validatePass2, trigger: 'blur'}
          ],
          telephone: [
            {required: true, message: '请输入手机号', trigger: 'blur'},
            {required: true, pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号',trigger: 'blur'}
          ],
          smscode: [
            {required: true,pattern: /^\d{6}$/, message: '请输入六位数字验证码',trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      validatePhone(){
        if(!this.userForm.telephone){
          this.$message({message: '手机号不能为空！', type: 'error',showClose: true});
          return false
        }else if(!/^1[3456789]\d{9}$/.test(this.userForm.telephone)){
          this.$message({message: '请输入正确的手机号！', type: 'error',showClose: true});
          return false
        } else {
          return true
        }
      },
      getCaptcha(){
        // e.preventDefault()
        let that = this
          if (this.validatePhone()) {
            this.state.smsSendBtn = true;
            let interval = window.setInterval(() => {
              if (that.state.time-- <= 0) {
                that.state.time = 60;
                that.state.smsSendBtn = false;
                window.clearInterval(interval);
              }
            }, 1000);
            const hide = this.$message({message: '验证码发送中..', type: 'success', showClose: true});
            const params = {
              mobile: this.userForm.telephone,
              smsmode: "1"
            };
            getSmsCode(this.userForm.telephone,"1").then((res) => {
              if (!res.success) {
                this.registerFailed(res.message);
                setTimeout(hide, 0);
              }
              setTimeout(hide, 500);
            }).catch(err => {
              setTimeout(hide, 1);
              clearInterval(interval);
              that.state.time = 60;
              that.state.smsSendBtn = false;
              this.requestFailed(err);
            });
          }
      },
      register(formName) {
        let that = this
        this.$refs[formName].validate((valid) => {
          if (valid) {
            console.log('userForm',that.userForm)
            that.$store.dispatch('register', that.userForm).then(() => {
              that.$message({message: '注册成功 快写文章吧', type: 'success', showClose: true});
              that.$router.push({path: '/'})
            }).catch((error) => {
              if (error !== 'error') {
                that.$message({message: error, type: 'error', showClose: true});
              }
            })

          } else {
            return false;
          }
        });

      }

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
    height: 390px;
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

</style>
