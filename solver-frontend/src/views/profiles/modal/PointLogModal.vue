<template>
  <div class="point-log-modal solver-font">
    <h3 class="point-log-title">포인트 내역</h3>
    <hr>
    <div class="point-log-box">
      <div v-for="(point, index) in pointList" :key="index" class="point-detail">
          <img class="point-img" v-if="point.value == 10 && point.code ==='080'" src="@/assets/plus-10.png" alt="">
          <img class="point-img" v-if="point.value == 30 && point.code ==='080'" src="@/assets/plus-30.png" alt="">
          <img class="point-img" v-if="point.value == 50 && point.code ==='080'" src="@/assets/plus-50.png" alt="">
          <img class="point-img" v-if="point.value == 100 && point.code ==='080'" src="@/assets/plus-100.png" alt="">
          <img class="point-img" v-if="point.value == 100 && point.code ==='081'" src="@/assets/minus-100.png" alt=""> &nbsp;
          <span class="point-text" v-if="point.pointCode === '000'"> 새로운 답변을 등록했어요! </span>
          <span class="point-text" v-if="point.pointCode === '001'"> 새로운 화상 답변을 등록했어요! </span>
          <span class="point-text" v-if="point.pointCode === '002'"> 귀중한 첫 답변을 등록했어요! </span>
          <span class="point-text" v-if="point.pointCode === '003'"> 곧 마감할 질문에 답변을 했어요! </span>
          <span class="point-text" v-if="point.pointCode === '004'"> 답변이 채택됬어요! </span>
          <span class="point-text" v-if="point.pointCode === '005'"> 화상답변이 채택됬어요! </span>
          <span class="point-text" v-if="point.pointCode === '006'"> 나의 질문이 추천받았어요! </span>
          <span class="point-text" v-if="point.pointCode === '007'"> 나의 답변이 추천받았어요! </span>
          <span class="point-text" v-if="point.pointCode === '008'"> 나의 질문이 북마크되었어요! </span>
          <span class="point-text" v-if="point.pointCode === '100'"> 질문이 신고 받았어요... </span>
          <span class="point-text" v-if="point.pointCode === '101'"> 답변이 신고 받았어요! </span>
          <span class="point-text" v-if="point.pointCode === '102'"> 댓글이 신고 받았어요! </span>
          <span class="point-time">&nbsp; {{point.redDt.slice(0,10)}} {{point.redDt.slice(11,16)}}</span>
      </div>
    </div>
    <button class="btn point-log-button" @click="$emit('close')">확인완료</button>
  </div>
</template>

<script>
import axios from "axios";
import API from "@/API.js";

export default {
  data:function(){
    return {
      nickname: '',
      pointList: [],
    }
  }, props : [
    
  ], methods : {

  }, created(){
    this.nickname = this.$store.getters.getUserNickname;
    axios({
      url: API.URL + `points/${this.nickname}`,
      method: "get",
      headers: { Authorization: "Bearer " + localStorage.getItem("solverToken")},
    })
    .then((res) => {
      this.pointList = res.data.list;
      console.log(this.pointList);
    })
    .catch((err) => {
      console.log(err);
    });
  }
}
</script>

<style>
  .point-detail{
    border-bottom: 1px solid #e0e0e0;
    padding: 20px 0px;
  }

  .point-detail-text{
    display: inline-block;
    width: 200px;
    border: 1px solid #0F4C81;
  }

  .point-log-box{
    /* background: #0F4C81; */
    height: 500px;
    overflow: auto;
  }
 
  .point-log-box::-webkit-scrollbar {
      width: 10px; /*스크롤바의 너비*/
  }

  .point-log-box::-webkit-scrollbar-thumb {
      background-color: #B5C7D3; /*스크롤바의 색상*/
      /* background-clip: padding-box; */
      border: 4px solid transparent;
      border-radius: 50px;
      /* border-top-right-radius: 50px;
      border-bottom-left-radius: 50px; */
  }

  .point-log-box::-webkit-scrollbar-track {
      background-color: rgb(245, 245, 245); 
      border-radius: 50px;
  }

  .point-log-button{
    margin-top: 25px;
    border: 1px solid #0F4C81;
    color: #0F4C81;
    width: 500px;
    padding: 10px 0px;
  }

  .point-log-button:hover{
    margin-top: 25px;
    background:  #658dc665;
    color: white;
    border: 1px solid white;
    width: 500px;
    padding: 10px 0px;
  }

  .point-log-modal{
     padding: 40px 50px;
  }

  .point-log-title{
    color: #0F4C81;
  } 

  .point-text{
    display: inline-block;
    width: 290px;
  }

  .point-time{
    font-size: 10px;
  }

  .point-img {
    width: 80px;
  }
</style>