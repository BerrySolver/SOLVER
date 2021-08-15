<template>
  <div class="pay-solver-modal solver-font">
    <h3 class="pay-solver-title">홍보하기</h3>
    <hr>
    <div v-if="!checkNotifi && !checkResult">
      <div  class="notifi-point">
        <img style="width:400px" src="@/assets/berry-pay.png" alt="">
      </div>
      <div>
        <div class="form-check">
          <input class="form-check-input" type="checkbox" value="" @click="checkOneBox" :disabled="checkOne" id="oneCheckBox">
          <label class="form-check-label" for="oneCheckBox">
            결제하고 나면 취소가 불가능한 것을 숙지했습니다.
          </label>
        </div>
        <div class="form-check">
          <input class="form-check-input" type="checkbox" value="" @click="checkTwoBox" :disabled="checkTwo" id="twoCheckBox">
          <label class="form-check-label" for="twoCheckBox">
            메인화면에 접속할 때마다 8명이 무작위로 배치, 노출됩니다.
          </label>
        </div>
        <div class="form-check">
          <input class="form-check-input" type="checkbox" value="" @click="checkThreeBox" :disabled="checkThree" id="threeCheckBox">
          <label class="form-check-label" for="threeCheckBox">
            지정한 날짜의 범위만큼, 메인화면에 정보가 노출되는 것을 동의합니다.
          </label>
        </div>
      </div>
      <div class="pay-solver-button-bar">
        <button type="button" class="btn btn-submit" :disabled="!checknext()" @click="changeNext">확인</button>
        <button type="button" class="btn btn-outline-cancel" @click="$emit('close')">취소</button>
      </div>
    </div>
    <div v-else-if="checkNotifi && !checkResult">
      <p>원하는 날짜의 범위를 입력해주시면, 메인 화면에 여러분이 소개됩니다!<br>결제는 여러분이 모은 포인트로 진행합니다.</p>
      <div class="date-box">
        <div class="date-box-col">
          <h5>시작</h5>
          <input type="date" v-model="startDate" :min="today">
        </div>
        <div class="date-box-col">
          <h5>마지막</h5>
          <input type="date" v-model="endDate" :min="startDate">
        </div>
      </div>
      <br>
      <div>
        <h5>결정 내역</h5>
        <table class="table pay-table pay-result" style="width: 300px;">
            <tr>
              <th style="width:150px;">시작일</th>
              <td>{{startDate}}</td>
            </tr>
            <tr>
              <th>마지막일</th>
              <td>{{endDate}}</td>
            </tr>
            <tr class="pay-result-under">
              <th>총 기간</th>
              <td>{{lenDate}}</td>
            </tr>
            <tr>
              <th>결제 총 포인트</th>
              <td>{{pointCalcul}}</td>
            </tr>
        </table>
        <div class="last-check">
          <br>
          <p v-if="checkMyPoint == 0"> </p>
          <p v-else-if="checkMyPoint == 1" style="color:red;">최대 7일까지 선택가능하십니다!</p>
          <p v-else-if="checkMyPoint == 2" style="color:green;">현재 갖고 계신 포인트로 결제 가능합니다!</p>
          <p v-else style="color:red;">현재 갖고 계신 포인트({{myPoint}}P)로는 결제가 불가능합니다.</p>
        </div>
      </div>
      <div class="pay-solver-button-bar">
        <button type="button" class="btn btn-submit" @click="postPaySolver" :disabled="!checkPay()">결제</button>
        <button type="button" class="btn btn-outline-cancel" @click="$emit('close')">취소</button>
      </div>
    </div>
    <div v-else style="text-align:center;">
      <img src="@/assets/berry-success.png" alt="" style="width:400px;">
      <br>
      <p>화상요청에 성공했어요!<br>원하시는 날부터 메인에서 확인해보세요!</p>
      <button class="btn pay-solver-button-log" @click="$emit('close')">확인</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import API from "@/API.js";

export default {
  data: function () {
    return {
      today: '',
      startDate: '',
      endDate: '',
      checkNotifi: false,
      checkResult: false,
      checkOne: false,
      checkTwo: false,
      checkThree: false,
      len: 0, 
    };
  },props : [
    'myPoint',
    'nickName',
  ], 
  components: {

  },
  methods: {
    checkOneBox: function(){
      this.checkOne = true;
    },
    checkTwoBox: function(){
      this.checkTwo = true;
    },
    checkThreeBox: function(){
      this.checkThree = true;
    },
    checknext: function(){
      return this.checkOne && this.checkTwo && this.checkThree;
    },
    changeNext : function(){
      this.checkNotifi = !this.checkNotifi;
    },
    checkPay : function(){
      return this.startDate != "" && this.endDate != "" && (this.myPoint >= (this.len * 100));
    },
    postPaySolver: function(){
      axios({
        url: API.URL + API.ROUTES.paySolver,
        method: "post",
        data:{
          userNick: this.nickName,
          startRegDt: this.startDate + " 00:00:00",
          endRegDt: this.endDate + " 23:59:59"
        },
        headers: { Authorization: "Bearer " + this.accessToken },
      }).then((res)=>{
        this.checkResult = true;
      }).catch((err)=>{

      });
    },
  },
  created() {
    var date = new Date();
    let year = date.getFullYear()
    let month = date.getMonth() + 1
    month = month >= 10 ? month : '0' + month
    let day = date.getDate()
    day = day >= 10 ? day : '0' + day  
    let result = year + '-' + month + '-' + day;
    this.today = result;
  },
  computed:{
    lenDate: function(){
      var stDate = new Date(this.startDate);
      var enDate = new Date(this.endDate);

      var btMs = enDate.getTime() - stDate.getTime();
      var btDay = btMs / (1000*60*60*24);

      this.len = btDay + 1;

      if (isNaN(btDay))
        return "";
      else
        return (btDay + 1) + "일";
    },
    pointCalcul : function(){      
      if (isNaN(this.len))
        return "";
      else
        return (this.len * 100)+"P";
    },
    checkMyPoint : function(){

      if(isNaN(this.len)){
        return 0
      } else if(this.len > 7){
        return 1
      } else if(this.myPoint >= (this.len * 100)){
        return 2
      } else{
        return 3
      }
    },
  }
};
</script>

<style>
  .berry-col{
    display: inline-block;
    width: 250px;
  }

  .date-box{
    margin-top: 25px;
    margin-bottom: 25px;
  }

  .date-box-col{
    display: inline-block;
    width: 200px;
    margin: 20px;
  }

  .pay-solver-modal {
    padding: 40px 50px;
  }

  .pay-solver-title {
    color: #0f4c81;
  }

  .pay-solver-button-bar{
    text-align: center;
    margin-top: 30px;
  }

  .pay-solver-button-bar > button {
    margin: 0px 5px;
    width: 240px;
  }

  .pay-solver-button-log{
    margin-top: 25px;
    border: 1px solid #0F4C81;
    color: #0F4C81;
    width: 500px;
    padding: 10px 0px;
  }

  .pay-solver-button-log:hover{
    margin-top: 25px;
    background:  #658dc665;
    color: white;
    border: 1px solid white;
    width: 500px;
    padding: 10px 0px;
  }

  .pay-result{
    margin: 0px 100px;
  }

  .pay-result > tr{
    height: 40px;
  }

  .pay-result-under{
    border-bottom: 1px solid #84898C;
  }

  .notifi-point{
    text-align: center;
    margin-bottom: 20px;
  }

  .last-check{
    text-align: center;
    height: 60px;
  }
</style>