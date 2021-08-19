<template>
  <div id="app">
    <NavBar id="navbar"/>
    <!-- 라우터 보여지는 부분 -->
    <RouterView id="router" :key="$route.fullPath"/>
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
}

#router {
  padding-top: 56px;
  min-width: 1190px;
  /* transform: skew(-0.03deg); */
}

#footer {
  bottom: 0;
  transform: skew(-0.1deg);
  min-width: 1190px;
}

#navbar {
  min-width: 1190px;
  transform: skew(-0.1deg);
}
</style>