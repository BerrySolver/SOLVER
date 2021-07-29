<template>
  <nav v-if="needHide" class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
      <!-- <a href="/">SOLVER</a> -->
      <RouterLink :to="{ name: 'Main' }" class="m-2">
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
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item m-2">
            <RouterLink :to="{ name: 'Questions' }">질문/답변</RouterLink>
          </li>
          <li class="nav-item m-2">
            <RouterLink :to="{ name: 'Groups' }">모임</RouterLink>
          </li>
          <li class="nav-item m-2">
            <RouterLink :to="{ name: 'Solvers' }">솔버</RouterLink>
          </li>
          <!-- <li class="nav-item m-2">
            <RouterLink :to="{ name: 'Profile' }">프로필</RouterLink>
          </li> -->
          <li class="nav-item m-2">
            <RouterLink :to="{ name: 'Login' }">로그인</RouterLink>
          </li>
          <li class="nav-item m-2">
            <RouterLink :to="{ name: 'Signup1' }">회원가입</RouterLink>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  name: "Navbar",
  computed: {
    needHide() {
      return !(this.$route.name === 'Signup1' || this.$route.name === 'Signup2' || this.$route.name === 'Login');
    },
  }
};
</script>

<style></style>
