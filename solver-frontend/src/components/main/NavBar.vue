<template>
  <nav v-if="needHide" class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
      <!-- <a href="/">SOLVER</a> -->
      <RouterLink :to="{ name: 'Main' }" class="nav-logo">
        <img src="@/assets/logo.png" alt="logo" height="40px" />SOLVER
      </RouterLink>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarNav"
        aria-controls="navbarNav"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav me-auto mb-lg-0">
          <li class="nav-item">
            <RouterLink :to="{ name: 'Questions' }" class="nav-router">질문/답변</RouterLink>
          </li>
          <li class="nav-item">
            <RouterLink :to="{ name: 'Groups' }" class="nav-router">모임</RouterLink>
          </li>
          <li class="nav-item">
            <RouterLink :to="{ name: 'Solvers' }" class="nav-router">솔버</RouterLink>
          </li>
          <li class="nav-item" v-if="!checkLogin()">
            <RouterLink :to="{ name: 'Login' }" class="nav-router">로그인</RouterLink>
          </li>
          <li class="nav-item" v-if="checkLogin()" @click="profileClick">
            <RouterLink :to="`/profiles/${userNickname}`" class="nav-router">마이프로필</RouterLink>
          </li>
          <li class="nav-item" v-if="checkLogin()">
            <a @click="clickLogout()" class="nav-logout">로그아웃</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
import { mapActions, mapGetters, mapState } from "vuex";
import router from "@/router";

export default {
  name: "Navbar",
  computed: {
    ...mapState({
      userNickname: (state) => state.auth.userNickname,
    }),
    ...mapGetters(["isLoggedIn"]),
    needHide() {
      return !(
        this.$route.name === "Signup1" ||
        this.$route.name === "Signup2" ||
        this.$route.name === "Login"
      );
    },
  },
  methods: {
    ...mapActions(['logout', 'profileSetting']),
    profileClick() {
      console.log('유저 닉네임', this.userNickname)
      this.profileSetting(this.userNickname);
    },
    checkLogin() {
      return localStorage.getItem("solverToken") != null;
    },
    clickLogout() {
      this.logout();
    },
  },
};
</script>

<style>
.navbar {
  align-items: center;
  font-family: "NanumSquare", sans-serif;
  font-size: 15px;
  height: 6vh;
  position: fixed;
  width: 100%;
  z-index: 100;
}

.nav-logo {
  color: #658dc6;
  font-weight: 600;
  margin-right: 20px;
  text-decoration: none;
}

.nav-router {
  color: #84898c;
  margin-right: 20px;
  text-decoration: none;
}

.nav-logout {
  color: #84898c;
  margin-right: 20px;
  text-decoration: none;
  cursor: pointer;
}
</style>
