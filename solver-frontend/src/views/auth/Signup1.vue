<template>
  <div class="background">

    <div class="nav-for-signup">
      <div class="row pt-3">
        <div class="col-2"><RouterLink :to="{ name: 'Main' }" style="text-decoration:none; color:#fff">← 돌아가기</RouterLink></div>
        <div class="col-5"></div>
        <div class="col-5">이미 솔버이신가요?
          <button class='ghost-button'><RouterLink :to="{ name: 'Login' }" style="text-decoration:none; color:#fff">LOGIN</RouterLink></button>
        </div>
      </div>
    </div>

    <div class="logo"> <img src="@/assets/logo.png" alt="logo" height="100px" /> </div>

    <div class='window'>
      <div class='content'>
        <div class='welcome'>SOLVER</div>
        <div class='input-fields'>
          <input type="text" v-model="signup1Data.userId" placeholder="아이디" class='input-line full-width'>
          <input type="text" v-model="signup1Data.nickname" placeholder="닉네임" class='input-line full-width'>
          <input type="password" v-model="signup1Data.password1" placeholder="비밀번호" class='input-line full-width'>
          <input type="password" v-model="signup1Data.password2" placeholder="비밀번호 확인" class='input-line full-width'>
        </div>
        <div class="for-margin"></div>
        <button class='ghost-round full-width'><RouterLink :to="{ name: 'Signup2' }" style="text-decoration:none; color:#fff">NEXT</RouterLink></button>
        <!-- <div><button @click="onClick($event)" class='ghost-round full-width'>NEXT</button></div>  //원래 기능--> 
      </div>
    </div>

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
.background {
  background: linear-gradient(135deg, #658DC6, #b5c7d3);
  height: 100vh;
  width: 100vw;
}

.nav-for-login {
  height: 20vh;
  color: #fff;
}

input {
  border: none;   
}

.ghost-button {
  cursor: pointer;
  background: none;
  border: 1px solid rgba(255, 255, 255, 0.65);
  color: rgba(255, 255, 255, 0.65);
  -webkit-align-self: flex-end;
  -ms-flex-item-align: end;
  align-self: flex-end;
  margin-left: 10px;
  padding-left: 10px;
  padding-right: 10px;
  -webkit-transition: all .2s ease;
  transition: all .2s ease;
}

.ghost-button:hover {
  background: rgba(255, 255, 255, 0.15);
  color: #fff;
  -webkit-transition: all .2s ease;
  transition: all .2s ease;
}

button:focus {
  outline: none;
}

::-webkit-input-placeholder {
  color: rgba(255, 255, 255, 0.65);
}

::-webkit-input-placeholder .input-line:focus +::input-placeholder {
  color: #fff;
}

.highlight {
  color: rgba(255, 255, 255, 0.8);
  font-weight: 400;
  cursor: pointer;
  transition: color .2s ease;
}

.highlight:hover {
  color: #fff;
  transition: color .2s ease;
}


.input-line:focus {
  outline: none;
  border-color: #fff;
  -webkit-transition: all .2s ease;
  transition: all .2s ease;
}

.ghost-round {
  cursor: pointer;
  background: none;
  border: 1px solid rgba(255, 255, 255, 0.65);
  border-radius: 25px;
  color: rgba(255, 255, 255, 0.65);
  -webkit-align-self: flex-end;
  -ms-flex-item-align: end;
  align-self: flex-end;
  font-size: 19px;
  font-size: 1.2rem;
  font-weight: 300;
  line-height: 2.5em;
  margin-top: auto;
  margin-bottom: 25px;
  -webkit-transition: all .2s ease;
  transition: all .2s ease;
}

.ghost-round:hover {
  background: rgba(255, 255, 255, 0.15);
  color: #fff;
  -webkit-transition: all .2s ease;
  transition: all .2s ease;
}

.input-line {
  background: none;
  margin-bottom: 10px;
  line-height: 2.4em;
  color: #fff;
  font-family: roboto;
  font-weight: 300;
  letter-spacing: 0px;
  letter-spacing: 0.02rem;
  font-size: 19px;
  font-size: 1.2rem;
  border-bottom: 1px solid rgba(255, 255, 255, 0.65);
  -webkit-transition: all .2s ease;
  transition: all .2s ease;
}

.full-width {
  width: 100%;
}

.input-fields {
  margin-top: 25px;
}

.content {
  margin-left: 10%;
  margin-right: 10%;
  padding-left: 30vw;
  padding-right: 30vw;
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  -webkit-flex-flow: column;
  -ms-flex-flow: column;
  flex-flow: column;
  z-index: 5;
}

.welcome {
  font-weight: 100;
  text-align: center;
  font-size: 30px;
  letter-spacing: 0px;
  letter-spacing: 0.05rem;
  color: #fff;
}

.window {
  width: 30%;
  text-align: center;
  margin-left: 35%;
  margin-right: 35%;
}  

.for-margin{
  height: 5vh;
}
</style>