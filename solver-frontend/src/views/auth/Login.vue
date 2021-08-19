<template>
  <div style="overflow-x: hidden; margin-top: -56px;">
    <div class="background">
      <div class="login-back">
        <RouterLink :to="{ name: 'Main' }" style="text-decoration:none; color:#fff"
          >← 돌아가기</RouterLink
        >
      </div>

      <div class="logo"><img src="@/assets/logo.png" alt="logo" height="100px" /></div>

      <div class="content">
        <div class="welcome">SOLVER</div>
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
    return {};
  },
  methods: {
    ...mapActions(["login", "tokenLogin"]),
  },
  computed: {
    ...mapState({
      userNickname: (state) => state.auth.userNickname,
    }),
  },
  mounted() {
    var search = location.search;
    var params = new URLSearchParams(search);
    var token = params.get("accessToken");
    if (token != null) {
      this.tokenLogin(token);
    }
  },
};
</script>

<style>
.background {
  background: linear-gradient(135deg, #658dc6, #b5c7d3);
  height: 1080px;
}

.content {
  text-align: center;
  display: flex;
  z-index: 5;
}

.login-back {
  margin-bottom: 200px;
  padding-top: 100px;
  width: 300px;
}

.welcome {
  font-weight: 100;
  text-align: center;
  font-size: 30px;
  color: #fff;
  margin-bottom: 40px;
}
</style>
