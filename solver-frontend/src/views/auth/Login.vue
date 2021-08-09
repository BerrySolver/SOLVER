<template>
  <div class="background">
    <div class="nav-for-login">
      <div class="row pt-3">
        <div class="col-2">
          <RouterLink :to="{ name: 'Main' }" style="text-decoration:none; color:#fff"
            >← 돌아가기</RouterLink
          >
        </div>
        <div class="col-5"></div>
        <!-- <div class="col-5">솔버가 아니신가요?
          <button class='ghost-button'><RouterLink :to="{ name: 'Signup1' }" style="text-decoration:none; color:#fff">SIGN UP</RouterLink></button>
        </div> -->
      </div>
    </div>

    <div class="logo"><img src="@/assets/logo.png" alt="logo" height="100px" /></div>

    <div class="window">
      <div class="content">
        <div class="welcome">SOLVER</div>
        <div class="for-margin"></div>
        <div>
          <a
            href="https://kauth.kakao.com/oauth/authorize?client_id=4d0b843e88238ebf6614549fce8bff85&redirect_uri=http://localhost:8080/api/v1/auth/login&response_type=code"
          >
            <img src="@/assets/kakao_login_medium_wide.png" />
          </a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";

export default {
  name: "Login",
  data() {
    return {
      // credentials: {
      //   loginId: '',
      //   password: '',
      // },
    };
  },
  methods: {
    ...mapActions(["login", "tokenLogin", "profileSetting"]),
  },
  computed: {
    ...mapState({
      userNickname: (state) => state.auth.userNickname,
    }),
  },
  mounted() {
    // var too = document.location.href.
    var search = location.search;
    var params = new URLSearchParams(search);
    var token = params.get("accessToken");
    if (token != null) {
      this.tokenLogin(token);
    }
  },
  destroyed() {
    this.profileSetting(this.userNickname);
  },
};
</script>

<style>
.background {
  background: linear-gradient(135deg, #658dc6, #b5c7d3);
  height: 100vh;
  width: 100vw;
}

button:focus {
  outline: none;
}

.content {
  margin-left: 10%;
  margin-right: 10%;
  text-align: center;
  padding-left: 30%;
  padding-right: 30%;
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  -webkit-flex-flow: column;
  -ms-flex-flow: column;
  flex-flow: column;
  z-index: 5;
}

.for-margin {
  height: 5vh;
}

.full-width {
  width: 100%;
}

.ghost-button {
  cursor: pointer;
  background: none;
  border: 1px solid rgba(255, 255, 255, 0.65);
  color: rgba(255, 255, 255, 0.65);
  -webkit-align-self: flex-end;
  -ms-flex-item-align: end;
  align-self: flex-end;
  margin-left: 10px;
  padding-left: 10px;
  padding-right: 10px;
  -webkit-transition: all 0.2s ease;
  transition: all 0.2s ease;
}

.ghost-button:hover {
  background: rgba(255, 255, 255, 0.15);
  color: #fff;
  -webkit-transition: all 0.2s ease;
  transition: all 0.2s ease;
}

.ghost-round {
  cursor: pointer;
  background: none;
  border: 1px solid rgba(255, 255, 255, 0.65);
  border-radius: 25px;
  color: rgba(255, 255, 255, 0.65);
  -webkit-align-self: flex-end;
  -ms-flex-item-align: end;
  align-self: flex-end;
  font-size: 19px;
  font-size: 1.2rem;
  font-weight: 300;
  line-height: 2.5em;
  margin-top: auto;
  margin-bottom: 25px;
  -webkit-transition: all 0.2s ease;
  transition: all 0.2s ease;
}

.ghost-round:hover {
  background: rgba(255, 255, 255, 0.15);
  color: #fff;
  -webkit-transition: all 0.2s ease;
  transition: all 0.2s ease;
}

.highlight {
  color: rgba(255, 255, 255, 0.8);
  font-weight: 400;
  cursor: pointer;
  transition: color 0.2s ease;
}

.highlight:hover {
  color: #fff;
  transition: color 0.2s ease;
}

input {
  border: none;
}

.input-fields {
  margin-top: 25px;
}

.input-line {
  background: none;
  margin-bottom: 10px;
  line-height: 2.4em;
  color: #fff;
  font-family: roboto;
  font-weight: 300;
  letter-spacing: 0px;
  letter-spacing: 0.02rem;
  font-size: 19px;
  font-size: 1.2rem;
  border-bottom: 1px solid rgba(255, 255, 255, 0.65);
  -webkit-transition: all 0.2s ease;
  transition: all 0.2s ease;
}

.input-line:focus {
  outline: none;
  border-color: #fff;
  -webkit-transition: all 0.2s ease;
  transition: all 0.2s ease;
}

.nav-for-login {
  height: 20vh;
  color: #fff;
}

::-webkit-input-placeholder {
  color: rgba(255, 255, 255, 0.65);
}

::-webkit-input-placeholder .input-line:focus + ::input-placeholder {
  color: #fff;
}

.welcome {
  font-weight: 100;
  text-align: center;
  font-size: 30px;
  font-size: 2.5rem;
  letter-spacing: 0px;
  letter-spacing: 0.05rem;
  color: #fff;
}

.window {
  width: 30%;
  text-align: center;
  margin-left: 35%;
  margin-right: 35%;
}
</style>
