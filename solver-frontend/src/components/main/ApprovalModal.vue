<template>
  <div class="conference-rejcet-modal solver-font">
    <div v-if="!isSuccess">
      <h3 class="conference-rejcet-title">요청 승인</h3>
      <br>
      <input type="email" class="form-control" placeholder="수업 준비사항이 있다면 써주세요!" v-model="content">
      <div class="conference-rejcet-button-bar">
        <button type="button" class="btn btn-submit" @click="OKButton()">승인</button>
        <button type="button" class="btn btn-outline-cancel" @click="$emit('close')">취소</button>
      </div>
    </div>
    <div v-else>
      <h4>정상적으로 승인되었습니다.</h4>
      <br>
      멋진 경력과 평가를 위해 수업 준비를 해주세요!
      <div class="reject-result-button-bar">
        <button @click="$emit('close')" class="btn request-check-button">닫기</button>
      </div>
    </div>
  </div>
</template>

<script>
import API from "@/API.js"
import axios from 'axios';
import { mapState } from "vuex";

export default {
  data:function(){
    return {
      content: '',
      isSuccess: false,
    }
  }, props : [
    'type',
    'messageId'
  ], created: function(){
    
  }, methods : {
    OKButton(){
      axios({
        url: API.URL + `messages/result`,
        method: 'POST',
        data: {
          messageId: this.messageId,
          type: "073",
          content: this.content,
        },
        headers: { Authorization: "Bearer " + this.getAccessToken },
      }).then((res)=>{
        
      this.isSuccess = true;

      }).catch((err)=>{

      });
    },
  },
  computed: {
    ...mapState({
      accessToken: (state) => state.auth.accessToken,
    }),
  }

}
</script>

<style>
  .conference-rejcet-modal{
     padding: 40px 50px;
  }

  .conference-rejcet-title{
    color: #0F4C81;
  }  

  .conference-rejcet-button-bar{
    text-align: center;
    margin-top: 30px;
  }

  .conference-rejcet-button-bar > button {
    margin: 0px 10px;
  }

  .btn-outline-cancel-emit{
    width: 200px;
  }

  .reject-result-button-bar{
    text-align: center;
    margin-top: 40px;
  }
</style>