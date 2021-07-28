<template>
  <div id="app">
    <NavBar/>
    <!-- 라우터 보여지는 부분 -->
    <RouterView/>
    <Footer/>
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
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
