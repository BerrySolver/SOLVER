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
        <div class="col-2">
          <RouterLink :to="{ name: 'Signup1' }" style="text-decoration:none; color:#fff"
            >← 돌아가기</RouterLink
          >
        </div>
        <div class="col-5"></div>
        <div class="col-5">
          이미 솔버이신가요?
          <button class="ghost-button">
            <RouterLink :to="{ name: 'Login' }" style="text-decoration:none; color:#fff"
              >LOGIN</RouterLink
            >
          </button>
        </div>
      </div>
    </div>

    <div class="logo"><img src="@/assets/logo.png" alt="logo" height="100px" /></div>

    <div class="signup2 container">
      <div class="content">SOLVER</div>
      <div class="layout"></div>
      <div class="row">
        <ul class="col" style="color:#fff; list-style:none;padding-left:0px;">
          <div>관심있는 분야를 골라주세요</div>
          <div>( {{ selectedCode.length }} / 3 )</div>
          <div class="mt-2 row">
            <div class="col-1"></div>
            <div class="col">
              <li v-for="(main, index) in mainCategory" v-bind:key="main.code" @click="changeIndex(index)">
                <button class=" mb-2 category-btn">{{ main.codeName }}</button>
              </li>
              
            </div>
            <div class="col">
              <li v-for="(sub) in subCategory" v-bind:key="sub.subCategoryCode" @click="selectCategory(sub.subCategoryCode)">
                <button v-bind:class="{selected:isSelectedCategory(sub.subCategoryCode)}" class="mb-2 category-btn">{{ sub.subCategoryName }}</button>
              </li>
            </div>
            <div class="col-1"></div>
          </div>
        </ul>
        <div class="col" style="color:#fff">
          <div>화상 응답이 가능한 요일과 시간을 골라주세요</div>
          <div class="m-4">
            <button v-bind:class="{selected: !isWeekend}" class="time-btn" @click="selectWeekday()">평일</button>
            <button v-bind:class="{selected: isWeekend}" class="time-btn" @click="selectWeekend()">주말</button>
          </div>
          <div class="col">
            <span v-for="(time, index) in timeList" v-bind:key="time">
              <button v-bind:class="{selected:isSelectedTime(time)}" class="mb-2 time-btn" @click="selectTime(time)">{{time}}</button>
              <div v-if="(index+1)%8 == 0"></div>
            </span>
          </div>
        </div>
      </div>
      <div>
        <button
          class="ghost-round m-4"
          @click="clickSignupBtn($route.params.nickname)"
        >
          SOLVER 이용하기
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions, mapGetters } from "vuex";
import API from "@/API.js";
import axios from "axios";

export default {
  name: "Signup2",
  data() {
    return {
      isWeekend: false,
      timeTable:[
        "00:00","00:30","01:00","01:30","02:00","02:30","03:00","03:30",
        "04:00","04:30","05:00","05:30","06:00","06:30","07:00","07:30",
        "08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30",
        "12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30",
        "16:00","16:30","17:00","17:30","18:00","18:30","19:00","19:30",
        "20:00","20:30","21:00","21:30","22:00","22:30","23:00","23:30"
      ],
      categoryIndex: -1,
      selectedCode: [],
      Category: [],
      weekday:[],
      weekend:[]
    };
  },
  methods: {
    ...mapActions(["signup"]),
    ...mapGetters(["isFirst"]),
    changeIndex(index){
      this.categoryIndex = index;
    },
    selectCategory(code){
      if(this.selectedCode.includes(code)){
        const idx = this.selectedCode.indexOf(code);
        this.selectedCode.splice(idx, 1);
        return;
      }

      if(this.selectedCode.length == 3){
        console.log("최대 3개 까지만 선택");
        return;
      }

      this.selectedCode.push(code);
    },
    selectWeekday(){
      this.isWeekend = false;
    },
    selectWeekend(){
      this.isWeekend = true;
    },
    selectTime(time){
      //주말 시간 선택
      if(this.isWeekend){
        if(this.weekend.includes(time)){
          const idx = this.weekend.indexOf(time);
          this.weekend.splice(idx, 1);
        }
        else{
          this.weekend.push(time);
        }
      }
      //평일 시간 선택
      else{
        if(this.weekday.includes(time)){
          const idx = this.weekday.indexOf(time);
          this.weekday.splice(idx, 1);
        }
        else{
          this.weekday.push(time);
        }
      }
    },
    clickSignupBtn(nickname){
      var weekdayTime = "";
      var weekendTime = "";

      this.weekday.forEach(element => {
        weekdayTime += element+"|";
      });

      this.weekend.forEach(element => {
        weekendTime += element+"|";
      });

      this.signup({
        "nickname": nickname,
        "selectedCode": this.selectedCode,
        "weekdayTime": weekdayTime,
        "weekendTime": weekendTime,
      });
    },
    isSelectedTime(time){
      if(this.isWeekend){
        if(this.weekend.includes(time)){
          return true;
        }
        else{
          return false;
        }
      }
      else{
        if(this.weekday.includes(time)){
          return true;
        }
        else{
          return false;
        }
      }

    },
    isSelectedCategory(code){
      if(this.selectedCode.includes(code)){
        return true;
      }

      return false;
    },
  },
  computed: {
    ...mapState({
      categoryList: (state) => state.auth.CategoryList,
    }),
    mainCategory(){
      return this.Category;
    },
    subCategory(){
      if(this.categoryIndex == -1)
        return null;

      return this.Category[this.categoryIndex].category;
    },
    timeList(){
      return this.timeTable;
    }
  },
  created() {
    axios({
      url: API.URL + API.ROUTES.getCategory,
      method: "get",
    })
    .then((res) => {
      this.Category = res.data;
    })
    .catch(() => {
      console.log();
    });
  },
  mounted() {
  },
};
</script>

<style>
.background {
  background: linear-gradient(135deg, #658dc6, #b5c7d3);
  height: 100vh;
  width: 100vw;
}

 .category-btn{
  cursor: pointer;
  width: 200px;
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

.time-btn{
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

.content {
  padding-left: 0;
  padding-right: 0;
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  -webkit-flex-flow: column;
  -ms-flex-flow: column;
  flex-flow: column;
  z-index: 5;
  font-weight: 100;
  text-align: center;
  font-size: 30px;
  letter-spacing: 0.05rem;
  color: #fff;
}

.nav-for-signup {
  height: 10vh;
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
  -webkit-transition: all 0.2s ease;
  transition: all 0.2s ease;
}

.selected{
  background: rgba(255, 255, 255, 0.5);
  color: #fff;
  -webkit-transition: all 0.2s ease;
  transition: all 0.2s ease;
}

.ghost-button:hover {
  background: rgba(255, 255, 255, 0.15);
  color: #fff;
  -webkit-transition: all 0.2s ease;
  transition: all 0.2s ease;
}

.ghost-round {
  cursor: pointer;
  background: none;
  width: 200px;
  border: 1px solid rgba(255, 255, 255, 0.65);
  border-radius: 25px;
  color: rgba(255, 255, 255, 0.65);
  -webkit-align-self: flex-end;
  -ms-flex-item-align: end;
  align-self: flex-end;
  font-size: 16px;
  font-size: 1.2rem;
  font-weight: 200;
  line-height: 2.5em;
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
  -webkit-transition: all 0.2s ease;
  transition: all 0.2s ease;
}

.full-width {
  width: 100%;
}

.input-fields {
  margin-top: 25px;
}

.for-margin {
  height: 5vh;
}
</style>
