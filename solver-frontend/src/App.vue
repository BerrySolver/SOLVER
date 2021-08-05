<template>
  <div id="app">
    <NavBar/>
    <!-- 라우터 보여지는 부분 -->
    <RouterView class="router"/>
    <Footer class="footer-router2"/>
  </div>
</template>

<script>
import NavBar from "@/components/main/NavBar"
import Footer from "@/components/main/Footer"

export default {
  name: 'App',
  components: {
      NavBar,
      Footer,
  },
}
</script>


<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  overflow-x: hidden;
}

.router {
  padding-top: 6vh;
}

.footer-router2 {
  bottom: 0;
}
</style>
