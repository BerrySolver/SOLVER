<template>
  <div class="background">

    <!-- 상단 bar -->
    <div class="nav-for-signup">
      <div>
        <RouterLink :to="{ name: 'Main' }" style="text-decoration:none; color:#fff">← 돌아가기</RouterLink>
      </div>
      <div>
        이미 솔버이신가요?
        <button class="ghost-button">
          <RouterLink :to="{ name: 'Login' }" style="text-decoration:none; color:#fff">LOGIN</RouterLink>
        </button>
      </div>
    </div>

    <!-- 로고 -->
    <div class="logo"><img src="@/assets/logo.png" alt="logo" height="100px" /></div>
    <div class="content">SOLVER</div>
    
    <div>
      <div class="content">
        <div>
          <div class="input-fields">
            <!-- <input type="text" v-model="signup1Data.loginId" placeholder="아이디" class='input-line full-width'> -->
            <input
              type="text"
              v-model="signup1Data.nickname"
              placeholder=" 닉네임"
              class="input-line full-width"
              @input="checkSameNickname()"
            />
            <div class="nickname-msg">{{ msg }} &nbsp;</div>
            <!-- <input type="password" v-model="signup1Data.password1" placeholder="비밀번호" class='input-line full-width'> -->
            <!-- <input type="password" v-model="signup1Data.password2" placeholder="비밀번호 확인" class='input-line full-width'> -->
          </div>
          <!-- <div class="col-4">
            <button class="nickname-button" @click="checkNickname()">닉네임 중복 체크</button>
          </div>
          <div class="nickname-message" >{{ msg }} &nbsp;</div> -->
        </div>
        <div class="for-margin"></div>
        <RouterLink
          :to="{
            name: 'Signup2',
            params: {
              loginId: signup1Data.loginId,
              nickname: signup1Data.nickname,
              password: signup1Data.password1,
            },
          }"
          style="text-decoration:none; color:#fff"
          >
          <button class="ghost-round" v-bind:disabled="isPossible == false">NEXT</button>
        
        </RouterLink>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import API from "@/API.js";
import { mapGetters } from "vuex";

export default {
  name: "Signup1",
  data() {
    return {
      signup1Data: {
        loginId: "",
        nickname: "",
        password1: "",
        password2: "",
      },
      msg: "닉네임을 입력해주세요.",
      isPossible: false,
    };
  },
  methods: {
    checkSameNickname(){
      axios({
        url: API.URL + API.ROUTES.checkNickname,
        method: "get",
        params:{
          "nickname": this.signup1Data.nickname
        }
      })
      .then((res) => {
        if(res.data.statusCode == 200){
          this.msg = res.data.message;
          this.isPossible = true;
        }
        else{
          this.msg = res.data.message;
          this.isPossible = false;
        }

        this.chekcValidation();
      })
      .catch((e) => {
        this.msg = "이미 사용중인 닉네임입니다.";
        this.isPossible = false;
        console.log(e);
      });
    },
    chekcValidation(){
      const validNickname = this.signup1Data.nickname;
      const regExp1 = /[`~!@#$%^&*|\\\'\";:=+,.\/?]/gi;
      const regExp2 = /[ㄱ-ㅎㅏ-ㅣ]/g;

      if(regExp1.test(validNickname)){
        this.msg = "특수문자를 사용할 수 없습니다.";
        this.isPossible = false;
      }
      else if(regExp2.test(validNickname)){
        this.msg = "자음/모음만 사용할 수 없습니다.";
        this.isPossible = false;
      }
      if(validNickname == ""){
        this.msg = "닉네임을 입력해주세요.";
        this.isPossible = false;
      }
      if(validNickname.length > 12){
        this.msg = "12자 이하만 가능합니다";
        this.isPossible = false;
      }
    },
    isPossibleNickname(){
      return this.isPossible;
    }
  },
  computed: {
    ...mapGetters(['isLoggedIn']),
  }
};
</script>

<style>
.background {
  background: linear-gradient(135deg, #658dc6, #b5c7d3);
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

.nickname-msg{
  font-size: 15px;
}

.nickname-button{
  cursor: pointer;
  font-size: 13px;
  background: none;
  border: 1px solid rgba(255, 255, 255, 0.65);
  color: rgba(255, 255, 255, 0.65);
  -webkit-align-self: flex-end;
  -ms-flex-item-align: end;
  align-self: flex-end;
  margin-left: 10px;
  padding-left: 10px;
  padding-right: 10px;
  -webkit-transition: all 0.2s ease;
  transition: all 0.2s ease;
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
  -webkit-transition: all 0.2s ease;
  transition: all 0.2s ease;
}

.ghost-button:hover {
  background: rgba(255, 255, 255, 0.15);
  color: #fff;
  -webkit-transition: all 0.2s ease;
  transition: all 0.2s ease;
}

button:focus {
  outline: none;
}

::-webkit-input-placeholder {
  color: rgba(255, 255, 255, 0.65);
}

::-webkit-input-placeholder .input-line:focus + ::input-placeholder {
  color: #fff;
}

.highlight {
  color: rgba(255, 255, 255, 0.8);
  font-weight: 400;
  cursor: pointer;
  transition: color 0.2s ease;
}

.highlight:hover {
  color: #fff;
  transition: color 0.2s ease;
}

.input-line:focus {
  outline: none;
  border-color: #fff;
  -webkit-transition: all 0.2s ease;
  transition: all 0.2s ease;
}

.ghost-round {
  cursor: pointer;
  width: 150px;
  background: none;
  border: 1px solid rgba(255, 255, 255, 0.65);
  border-radius: 25px;
  color: rgba(255, 255, 255, 0.65);
  -webkit-align-self: flex-end;
  -ms-flex-item-align: end;
  align-self: flex-end;
  font-size: 19px;
  font-size: 1.2rem;
  font-weight: 200;
  line-height: 2.5em;
  padding: 0px;
  margin-top: auto;
  margin-bottom: 25px;
  -webkit-transition: all 0.2s ease;
  transition: all 0.2s ease;
}

.ghost-round:hover {
  background: rgba(255, 255, 255, 0.15);
  color: #fff;
  -webkit-transition: all 0.2s ease;
  transition: all 0.2s ease;
}

.input-line {
  background: none;
  margin-bottom: 10px;
  line-height: 2.4em;
  color: #fff;
  /* font-family: roboto; */
  font-weight: 300;
  letter-spacing: 0px;
  letter-spacing: 0.02rem;
  font-size: 15px;
  border: 1px solid white;
  /* border-bottom: 10px solid rgba(255, 255, 255, 0.65); */
  -webkit-transition: all 0.2s ease;
  transition: all 0.2s ease;
}

.full-width {
  width: 100%;
}

.input-fields {
  margin-top: 100px;
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

.for-margin {
  height: 5vh;
}
</style>
