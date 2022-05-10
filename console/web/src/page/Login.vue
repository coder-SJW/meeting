<template>
  <div class="login-wrap">
    <div class="ms-login">
      <div class="ms-title" style="color: black">会议室预定系统</div>
      <el-form v-show="showLogin" id="loginForm" ref="login" :model="param" :rules="rules" class="ms-content"
               label-width="0px">
        <el-form-item prop="passport">
          <el-input v-model="param.passport" placeholder="账号">
            <el-button slot="prepend" icon="el-icon-lx-people"/>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
              v-model="param.password"
              placeholder="密码"
              type="password"
              @keyup.enter.native="submitForm()"
          >
            <el-button slot="prepend" icon="el-icon-lx-lock"/>
          </el-input>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="submitForm()">登录</el-button>
        </div>
        <div id="registerClick">
          <span style="font-size: 14px">还没有账号？</span>
          <el-button type="primary" @click="showRegisterForm()">立即注册</el-button>
        </div>
      </el-form>

      <el-form v-show="!showLogin" id="registerForm" ref="register" :model="registerParam" :rules="registerRules"
               class="ms-content"
               label-width="60px">
        <el-form-item
            label="账号"
            prop="passport">
          <el-input
              v-model="registerParam.passport"
              placeholder="账号"/>
        </el-form-item>
        <el-form-item
            label="密码"
            prop="passport">
          <el-input
              v-model="registerParam.password"
              placeholder="密码"
              type="password"/>
        </el-form-item>
        <el-form-item
            label="名称"
            prop="name">
          <el-input v-model="registerParam.name" placeholder="名称"/>
        </el-form-item>
        <el-form-item
            label="邮箱"
            prop="email">
          <el-input v-model="registerParam.email" placeholder="邮箱"/>
        </el-form-item>
        <el-form-item
            label="手机"
            prop="phone">
          <el-input v-model="registerParam.phone" placeholder="手机"/>
        </el-form-item>
        <div class="login-btn" style="margin-left: 60px">
          <el-button type="primary" @click="submitRegisterForm()">注册</el-button>
        </div>
        <div id="loginClick">
          <span style="font-size: 14px">已有账号？</span>
          <el-button type="primary" @click="showLoginForm()">立即登陆</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import account from '@/api/Account';

export default {
  data: function () {
    return {
      showLogin: true,
      param: {
        passport: '',
        password: '',
        rememberMe: false
      },
      registerParam: {
        passport: '',
        password: '',
        name: '',
        email: '',
        phone: ''
      },
      rules: {
        passport: [{required: true, message: '请输入用户名', trigger: 'blur'}],
        password: [{required: true, message: '请输入密码', trigger: 'blur'}]
      },
      registerRules: {
        passport: [
          {
            required: true,
            message: '请输入账号',
            trigger: 'blur'
          },
          {
            type: 'string',
            max: 16,
            trigger: 'blur',
            message: '账号长度不能超过16'
          }
        ],
        name: [
          {
            required: true,
            message: '请输入名称',
            trigger: 'blur'
          },
          {
            type: 'string',
            max: 6,
            trigger: 'blur',
            message: '名称长度不能超过6'
          }
        ],
        password: [
          {
            required: true,
            message: '请输入密码',
            trigger: 'blur'
          },
          {
            type: 'string',
            max: 64,
            trigger: 'blur',
            message: '密码长度不能超过64'
          }
        ],
        email: [
          {
            required: true,
            message: '请输入邮箱',
            trigger: 'blur'
          },
          {
            type: 'string',
            max: 32,
            trigger: 'blur',
            message: '邮箱长度不能超过32'
          }
        ],
        phone: [
          {
            required: true,
            message: '请输入手机号',
            trigger: 'blur'
          },
          {
            type: 'string',
            max: 16,
            trigger: 'blur',
            message: '手机号长度不能超过16'
          }
        ]
      }
    };
  },
  methods: {
    submitForm() {
      this.$refs.login.validate(async valid => {
        if (valid) {
          try {
            const data = await account.login(this.param);
            this.$store.commit('UPDATE_USER_INFO', data.data);
            this.$message.success('登录成功');
            sessionStorage.setItem('passport', this.param.passport);
            this.$router.push('/');
          } catch (e) {
            this.$message.error(e.message);
          }

        } else {
          this.$message.error('请输入账号和密码');
          return false;
        }
      });
    },
    submitRegisterForm() {
      this.$refs.register.validate(async valid => {
        if (valid) {
          try {
            await account.register(this.registerParam);
            this.$message.success('注册成功');
            this.showLogin = true;
          } catch (e) {
            this.$message.error(e.message);
          }
        } else {
          this.$message.error('请完善注册信息');
          return false;
        }
      });
    },
    showRegisterForm() {
      this.showLogin = false;
    },
    showLoginForm() {
      this.showLogin = true;
    }
  }
};
</script>

<style scoped>
.login-wrap {
  position: relative;
  width: 100%;
  height: 100%;
  background-image: url(../assets/img/bg.jpg);
  background-size: 100%;
}

.ms-title {
  width: 100%;
  line-height: 50px;
  text-align: center;
  font-size: 20px;
  color: #fff;
  border-bottom: 1px solid #ddd;
}

.ms-login {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 350px;
  margin: -190px 0 0 -175px;
  border-radius: 5px;
  background: rgba(255, 255, 255, 0.3);
  overflow: hidden;
}

.ms-content {
  padding: 30px 30px;
}

.login-btn {
  text-align: center;
}

.login-btn button {
  width: 100%;
  height: 36px;
  margin-bottom: 10px;
}

.login-tips {
  font-size: 12px;
  line-height: 30px;
  color: #fff;
}
</style>
