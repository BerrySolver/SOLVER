<template>
  <div id="app">
    <NavBar id="navbar"/>
    <!-- 라우터 보여지는 부분 -->
    <RouterView id="router"/>
    <Footer id="footer"/>
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
  font-family: 'NanumSquare', sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  white-space:nowrap;
  overflow-x: scroll;
}

#router {
  padding-top: 6vh;
  min-width: 1190px;
}

#footer {
  bottom: 0;
  min-width: 1190px;
}

#navbar {
  min-width: 1190px;
}
</style>