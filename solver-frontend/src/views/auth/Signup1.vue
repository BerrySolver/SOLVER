<template>
  <div>
    <!-- title -->
    <h3>SIGNUP1</h3>

    <!-- input -->
    <div>
      <div>
        <input type="text" v-model="signup1Data.userId" placeholder="userId">
      </div>
      <div>
        <input type="text" v-model="signup1Data.nickname" placeholder="nickname">
      </div>
      <div>
        <input type="password" v-model="signup1Data.password1" placeholder="password1">
      </div>
      <div>
        <input type="password" v-model="signup1Data.password2" placeholder="password2">
      </div>
    </div>

    <!-- move to signup 2 -->
    <button @click="onClick($event)">Go to signup2</button>
  </div>
</template>

<script>

export default {
    name: 'Signup1',
    data() {
      return {
        signup1Data: {
          userId: '',
          nickname: '',
          password1: '',
          password2: '',
        }
      }
    },

}

</script>

<style>

</style>