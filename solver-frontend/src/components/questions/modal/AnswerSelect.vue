<template>
  <div class="answer-select-modal solver-font">
    <h3 class="answer-select-title">답변 채택</h3>
    <hr>
    <p>답변을 채택하면 질문은 해결 상태가 되고 채택 취소는 불가능합니다.</p>
    <div class="answer-select-button-bar">
      <button type="button" class="btn btn-submit" @click="selectAnswer()">채택</button>
      <button type="button" class="btn btn-outline-cancel" @click="$emit('close')">취소</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import API from "@/API.js";
import { mapActions, mapState } from 'vuex'

export default {
  data:function(){
    return {
    }
  }, props : [
    'data',
  ], methods : {
    ...mapActions(["triggerQuestionReload", "triggerAnswerReload"]),
  selectAnswer : function(){
    console.log(this.data.answerId)
      axios({
        url: API.URL + `answers/select/${this.data.questionId}/${this.data.answerId}`,
        method: "put",
        headers: {
          Authorization: "Bearer " + this.accessToken,
        },
      })
      .then(() => {
        this.triggerQuestionReload();
        setTimeout(() => {
          this.triggerAnswerReload();
        }, 200);
      })
      .catch((err) => {
        console.log(err);
      });
      this.$emit('close');
    }
  },
  computed: {
    ...mapState({
      accessToken: state => state.auth.accessToken,
    }),
  }
}
</script>

<style>
  .answer-select-modal{
    padding: 40px 50px;
  }

  .answer-select-title{
    color: #0F4C81;
  }  

  .answer-select-button-bar{
    text-align: center;
    margin-top: 30px;
  }

  .answer-select-button-bar > button {
    margin: 0px 10px;
  }
</style>