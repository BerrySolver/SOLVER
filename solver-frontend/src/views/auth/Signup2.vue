<template>
  <!-- <div>
    <div>
      <ul>
        <h3>1. 관심있는 분야를 골라주세요.</h3>
        <li v-for="(Category, idx) in categoryList" :key="idx">
        </li>
      </ul>
    </div>
    <div>
      <ul>
        <h3>2. 가능하신 요일과 시간을 골라주세요.</h3> 
      </ul>
    </div>
  </div> -->

  <div class="background">

    <div class="nav-for-signup">
      <div class="row pt-3">
        <div class="col-2"><RouterLink :to="{ name: 'Signup1' }" style="text-decoration:none; color:#fff">← 돌아가기</RouterLink></div>
        <div class="col-5"></div>
        <div class="col-5">이미 솔버이신가요?
          <button class='ghost-button'><RouterLink :to="{ name: 'Login' }" style="text-decoration:none; color:#fff">LOGIN</RouterLink></button>
        </div>
      </div>
    </div>

    <div class="logo"> <img src="@/assets/logo.png" alt="logo" height="100px" /> </div>

    <div class='window container'>
      <div class='content'>
        <div class='welcome'>SOLVER</div>
        <div class="layout"></div>
      </div>
      <div class="row">
        <ul class="col" style="color:#fff; list-style:none;padding-left:0px;">1. 관심있는 분야를 골라주세요.
          <li>초중고</li>
          <li>인문</li>
          <li>사회과학</li>
          <li>자연과학</li>
          <li>기술과학</li>
          <li>예술/체육</li>
          <li>기타</li>
        </ul>
        <div class="col" style="color:#fff">2. 가능하신 요일과 시간을 골라주세요.</div>
      </div>
      <div>
        <button class='ghost-round'><RouterLink :to="{ name: 'Home' }" style="text-decoration:none; color:#fff">가입완료</RouterLink></button>
      </div>
      
    </div>

  </div>
</template>

<script>
import {mapState} from 'vuex'

export default {
    name: 'Signup2',
    data() {
      return {
        signupInfo: {
          Category: [],
          day: [],
          time: [],
        },
      }
    },
    computed: {
      ...mapState({
        categoryList: state => state.auth.CategoryList
      })
    }
}
</script>

<style>
.background {
  background: linear-gradient(135deg, #658DC6, #b5c7d3);
  height: 100vh;
  width: 100vw;
}

.nav-for-signup {
  height: 20vh;
  color: #fff;
}

input {
  border: none;
}

.layout {
  height: 5vh;
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

.for-margin{
  height: 5vh;
}

.window {
  text-align: center;
}
</style>