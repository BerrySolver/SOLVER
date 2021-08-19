<template>
  <div class="question-delete-modal solver-font">
    <h3 class="question-delete-title">질문 삭제</h3>
    <hr>
    <p class="font-break">정말 질문을 삭제하시겠습니까?</p>
    <div class="question-delete-button-bar">
      <button type="button" class="btn btn-submit" @click="deleteQuestion()">삭제</button>
      <button type="button" class="btn btn-outline-cancel" @click="$emit('close')">취소</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import API from "@/API.js";
import router from "@/router";
import {mapState} from 'vuex'

export default {
  data:function(){
    return {
    }
  }, props : [
    'question',
  ], methods : {
    deleteQuestion: function(){
      axios({
        url: API.URL + `questions/${this.question}`,
        method: "delete",
        headers: { Authorization: "Bearer " + this.accessToken },
      })
      .then(() => {
        router.push({ path: "/questions" });
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
  .question-delete-modal{
     padding: 40px 50px;
  }

  .question-delete-title{
    color: #0F4C81;
  }  

  .question-delete-button-bar{
    text-align: center;
    margin-top: 30px;
  }

  .question-delete-button-bar > button {
    margin: 0px 10px;
  }
</style>