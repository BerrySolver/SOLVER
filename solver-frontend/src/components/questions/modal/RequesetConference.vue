<template>
  <div class="request-modal solver-font">
    <div v-if="isRequest">
      <h3 class="request-title">화상 요청</h3>
      <hr>
      <b>{{this.answer.nickname}}</b>님에게 화상 요청하시겠어요?<br>
      <br>
      <div class="request-day-select-button">
        <button class="day-button" :class="{'first-button':isFisrt}" @click="[checkWeekday(), changeFirst()]">평일</button>
        <button class="day-button" :class="{'first-button':!isFisrt}" @click="[checkWeekend(), changeFirst()]">주말</button>
      </div>
      <div class="days-content-table">
        <div v-if="isWeekday">
          <div class="days-margin">
            <div class="days-radio-button" v-for="(weekday, index) in weekdayDay" :key="index">
              <input :value="weekday.value" :name="weekday" type="radio" v-bind:id="'weekday'+weekday.value" v-model="chooseDays">
              <label v-bind:for="'weekday'+weekday.value">{{weekday.days}}</label>
            </div>
          </div>
          <span v-for="(time,index) in timeTable" v-bind:key="time">
          <button @click="chooseWdt(time)" v-bind:class="{'selected-button': isInWdt(time), 'final-button': finalWdt(time)}" :disabled="isInWdt(time) == false" class="none-selected-button">
            {{ time }}
          </button>
          <div v-if="(index+1)%6 == 0"></div>
          </span>
        </div>
        <div v-if="!isWeekday">
          <div class="days-margin">
            <div class="days-radio-button" v-for="(weekend, index) in weekendDay" :key="index">
              <input :value="weekend.value" :name="weekend" type="radio" v-bind:id="'weekend'+weekend.value" v-model="chooseDays">
              <label v-bind:for="'weekend'+weekend.value">{{weekend.days}}</label>
            </div>
          </div>
          <span v-for="(time,index) in timeTable" v-bind:key="time">
            <button @click="chooseWkt(time)" v-bind:class="{'selected-button': isInWkt(time), 'final-button': finalWkt(time)}" :disabled="isInWkt(time) == false"  class="none-selected-button">
              {{ time }}
            </button>
            <div v-if="(index+1)%6 == 0"></div>
          </span>
        </div>      
      </div>
      <div class="request-check-box">
        <span v-if="chooseTime==''"><br>요일과 시간을 모두 선택하세요!</span>
        <span v-else>오늘부터 가장 가까운 날로 요청드려요!<br>{{ getRequestDays }}({{ chooseDaysText }}) {{chooseTime}} 맞으신가요?</span>
      </div>
      <div class="request-button-bar">
        <button type="button" class="btn btn-submit" @click="requestCallBtn" :disabled="isSelectAll">신청하기</button>
        <button type="button" class="btn btn-outline-cancel" @click="$emit('close')">취소하기</button>
      </div>
    </div>
    <div v-if="!isRequest && isResultSuccess">
      <h3 class="request-title">화상 신청 성공</h3>
      <hr>
      <div style="text-align:center;">
        <img src="@/assets/berry-success.png" alt="" style="width:350px;">
        <br>
        <p>화상요청에 성공했어요!<br><b><span style="request-title">{{answer.nickname}}</span></b>님이 확인 후 승인/거절하실꺼에요!</p>
        <br>
        <button @click="$emit('close')" class="btn request-check-button">닫기</button>
      </div>
    </div>
    <div v-if="!isRequest && isResultFail">
      <h3 class="request-title">신청 실패</h3>
      <hr>
      <div style="text-align:center;">
        <img src="@/assets/berry-fail.png" alt="" style="width:350px;">
        <br>
        <p>화상요청에 실패했어요!<br>잠시 후 다시 신청해주시거나 고객센터에 리포트 보내주세요!</p>
        <br>
        <button @click="$emit('close')" class="btn request-check-button">닫기</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import API from "@/API.js";
import {mapState} from 'vuex'

export default {
  data:function(){
    return {
      isRequest: true,
      isResultSuccess: false,
      isResultFail: false,
      isWeekday: true,
      isFisrt: true,
      weekdayTime: [],
      weekendTime: [],
      chooseTime: '',
      chooseDays: 0,
      chooseDaysText:'',
      timeTable:[
        "00:00","00:30","01:00","01:30","02:00","02:30","03:00","03:30",
        "04:00","04:30","05:00","05:30","06:00","06:30","07:00","07:30",
        "08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30",
        "12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30",
        "16:00","16:30","17:00","17:30","18:00","18:30","19:00","19:30",
        "20:00","20:30","21:00","21:30","22:00","22:30","23:00","23:30"
      ], 
      weekdayDay: [
        {days: "월", value: 0},
        {days: "화", value: 1},
        {days: "수", value: 2},
        {days: "목", value: 3},
        {days: "금", value: 4},
      ],
      weekendDay: [
        {days: "토", value: 5},
        {days: "일", value: 6}
      ],
      today: 0,
      requestDay: "",
    }
  }, props : [
      'answer',
  ], methods : {
    changeFirst() {
      this.isFisrt = !this.isFisrt
    },
    // 주중, 주말 CLICK해 SWITCH
    checkWeekday () {
      this.chooseTime = '';
      this.isWeekday = true
    },
    checkWeekend () {
      this.chooseTime = '';
      this.isWeekday = false
    },
    // 주중, 주말에서 선택한 시간인 경우 (CSS 변동)
    chooseWdt(time) {
      this.chooseTime = time;
      this.getDays();
    },
    chooseWkt(time){
      this.chooseTime = time;
      this.getDays();
    },
    finalWdt(time){
      if (this.chooseTime==time) {
        return true
      } else
      return false
    },
    finalWkt(time){
      if (this.chooseTime==time) {
        return true
      } else
      return false
    },
    // 수정X용 : 기존에 PROP으로 받아온 TIME데이터에 속하는 TIME인지 CHECK
    isInWdt(time) {
      if (this.weekdayTime.includes(time)) {
        return true
      } else
      return false
    },
    isInWkt(time) {
      if (this.weekendTime.includes(time)) {
        return true
      } else
      return false
    },      
    getDays(){
      if(this.isWeekday){
        this.weekdayDay.forEach(w => {
          if(w.value==this.chooseDays){
            this.chooseDaysText = w.days;
          }
        });
      }else{
        this.weekendDay.forEach(w => {
          if(w.value==this.chooseDays)
            this.chooseDaysText = w.days;
        });
      }
    },
    resetData(){
      this.chooseTime ='';
    },
    requestCallBtn(){
      axios({
        url: API.URL + `messages/regist`,
        method: 'POST',
        data: {
          answerId: this.answer.answerId,
          regDt: this.requestDay + " " + this.chooseTime,
          answerUserId: this.answer.userId,
          type: "072"
        },
        headers: { Authorization: "Bearer " + this.getAccessToken },
      }).then((res)=>{
        console.log(res);
        this.isRequest = false;
        this.isResultSuccess = true;
      }).catch((res)=>{
        console.log(res);
        this.isRequest = false;
        this.isResultFail = true;
      });
    }
  }, 
  created: function(){
    axios({
        url: API.URL + `profiles/${this.answer.nickname}/info`,
        method: "get",
        headers: { Authorization: "Bearer " + this.accessToken}
      })
    .then((res) => {
      this.weekdayTime = res.data.weekdayTime.split('|');
      this.weekendTime = res.data.weekendTime.split('|');
    })
    .catch((err) => console.log(err))

    var d = new Date();
    // Date 상으로는 일월화수목금토 순으로 0~6으로 배치, 서비스에 맞게 변동
    this.today = d.getDay() - 1 < 0 ? 6 : d.getDay() - 1;
  }, computed: {
    ...mapState({
      accessToken: state => state.auth.accessToken,
    }),
    getRequestDays : function(){
      var d = new Date();
      this.requestDay = "";
      
      if (this.today <= this.chooseDays){
        d.setDate(d.getDate() + (this.chooseDays - this.today));
        this.requestDay = d.toLocaleDateString();
      }else{
        d.setDate(d.getDate() + ((6 - this.today)+(this.chooseDays+1)));
        this.requestDay = d.toLocaleDateString();
      }

      // regDt에 맞게 형태 변화, 요일 업데이트
      this.requestDay = this.requestDay.replaceAll(" ", "").replaceAll(".", "-").replace(/-$/, '');
      this.getDays();

      // 화면 출력용 형태
      var printDay = this.requestDay;
      return printDay.replace("-", "년 ").replace("-", "월 ")+"일";
    },
    isSelectAll : function(){
      if(this.chooseTime=="" && this.chooseDays == ""){
        return true;
      }
      return false;
    }
  }
}
</script>

<style>
  .btn-submit, .btn-outline-cancel{
      padding: 6px 10px;
      width: 100px;
  }

  .btn-submit {
      background: #658DC6;
      color:white;
  }

  .btn-submit:hover {
      background: #0F4C81;
      color:white;
  }

  .btn-outline-cancel{
      border: 1px solid #658DC6;
      color: #658DC6;
  }

  .btn-outline-cancel:hover {
      border: 1px solid #0F4C81;
      color: #0F4C81;
      background: #e9e9e9;
  }

  .day-button {
    background-color: transparent;
    border: solid 1px #658DC6;
    width: 120px;
  }

  .day-button:hover {
    background-color: #658DC6;
    border-color: #658DC6;
    color: #F1F2F2;
  }
  .day-button:active {
    background-color: #658DC6;
    border-color: #658DC6;
    color: #F1F2F2;
  }
  .day-button:focus, .day-button:active:focus {
    background-color: #658DC6;
    border-color: #658DC6;
    color: #F1F2F2;
  }

  .days-content-table{
    text-align: center;
    margin: 10px;
  }

  .days-radio-button{
    display: inline;
  }

  .days-radio-button > input{
    margin-left: 10px;
  }

  .days-radio-button > label{
    margin-right: 10px;
    margin-left: 5px ;
  }

  .days-margin {
    margin: 15px 0px;
  }

  .first-button {
    background-color: #658DC6;
    color: #F1F2F2;
  }

  .request-button-bar{
    text-align: center;
    margin-top: 60px;
  }

  .request-button-bar > button {
    margin: 0px 10px;
  }

  .request-check-button {
    text-align: center;
    margin: 0px 10px;
    width: 250px;
    background: #658DC6;
    color:white;
  }

  .request-check-button:hover {
    background: #0F4C81;
    color:white;
  }

  .request-check-box{
    margin-top:20px;
    height: 20px;
    text-align: center;
  }

  .request-day-select-button{
    text-align: center;
  }

  .request-day-select-button > button{
    margin: 0px;
  }

  .request-day-select-button > button:last-child{
    margin: -1px;
  }

  .request-modal{
     padding: 40px 50px;
  }

  .request-title{
    color: #0F4C81;
  }  

  .none-selected-button {
    background-color: transparent;
    border: transparent;
    color: #84898C;
    font-size: 16px;
    margin-top: 1px;
    margin-bottom: 1px;
    width: 65px;
  }

  .selected-button {
    background-color: #b9b9b93d;
    /* border: transparent; */
    color: #0F4C81;
    font-size: 16px;
    margin-top: 1px;
    margin-bottom: 1px;
    width: 65px;  
  }

  .final-button {
    background-color: #0F4C81;
    /* border: transparent; */
    color: white;
    font-size: 16px;
    margin-top: 1px;
    margin-bottom: 1px;
    width: 65px;  
  }

</style>