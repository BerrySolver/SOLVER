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
          <!-- <li class="nav-item m-2">
            <RouterLink :to="{ name: 'Profile' }">프로필</RouterLink>
          </li> -->
          <li class="nav-item" v-if="!isLoggedIn">
            <RouterLink :to="{ name: 'Login' }" class="nav-router">로그인</RouterLink>
          </li>
          <li class="nav-item" v-if="!isLoggedIn">
            <RouterLink :to="{ name: 'Signup1' }" class="nav-router">회원가입</RouterLink>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
import {mapGetters} from 'vuex'

export default {
  name: "Navbar",
  computed: {
    ...mapGetters([
      'isLoggedIn',
    ]),
    needHide() {
      return !(this.$route.name === 'Signup1' || this.$route.name === 'Signup2' || this.$route.name === 'Login');
    },
  },
};
</script>

<style>
.navbar {
  align-items: center;
  font-family: 'NanumSquare', sans-serif;
  font-size: 15px;
  position: fixed;
  width: 100%;
  z-index: 100;
}

.nav-logo {
  color: #658DC6;
  font-weight: 600;
  margin-right: 20px;
  text-decoration: none;
}

.nav-router {
  color: #84898C;
  margin-right: 20px;
  text-decoration: none;
}
</style>
