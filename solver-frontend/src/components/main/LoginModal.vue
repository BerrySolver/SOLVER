<template>
  <div class="login-modal solver-font">
    <h3 class="login-title">솔버가 아니신가요?</h3>
    <hr>
    <p>로그인이 필요한 기능입니다. 이동하시겠습니까?</p>
    <div class="login-button-bar">
      <button type="button" class="btn btn-submit" @click="goLogin">로그인</button>
      <button type="button" class="btn btn-outline-cancel" @click="$emit('close')">취소</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "LoginModal",
  methods : {
    goLogin : function(){
      this.$router.push({name: 'Login'})
      this.$emit('close')
    }
  }
}
</script>

<style>
  .login-modal{
     padding: 40px 50px;
  }

  .login-title{
    color: #0F4C81;
  }  

  .login-button-bar{
    text-align: center;
    margin-top: 30px;
  }

  .login-button-bar > button {
    margin: 0px 10px;
  }
</style>