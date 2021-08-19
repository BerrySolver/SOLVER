<template>
  <div class="conference-rejcet-modal solver-font">
    <div v-if="!isSuccess">
      <h3 class="conference-rejcet-title">요청 거절</h3>
      <br>
      <input type="email" class="form-control font-break" placeholder="거절 사유를 기재해주세요." v-model="content">
      <div class="conference-rejcet-button-bar">
        <button type="button" class="btn btn-submit font-break" @click="NOButton()">거절</button>
        <button type="button" class="btn btn-outline-cancel font-break" @click="$emit('close')">취소</button>
      </div>
    </div>
    <div v-else>
      <h4 class="font-break">정상적으로 거절되었습니다.</h4>
      <br>
      <div class="font-break">
        혹시 답변할 수 없는 시간이라면, 마이프로필에서 화상 가능 시간을 수정하세요!
      </div>
      <div class="reject-result-button-bar">
        <button @click="$emit('close')" class="btn request-check-button font-break">닫기</button>
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
    NOButton(){
      axios({
        url: API.URL + `messages/result`,
        method: 'POST',
        data: {
          messageId: this.messageId,
          type: "074",
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