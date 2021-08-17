<template>
  <div class="answer-delete-modal solver-font">
    <h3 class="answer-delete-title">답변 삭제</h3>
    <hr>
    <p>정말 답변을 삭제하시겠습니까?</p>
    <div class="answer-delete-button-bar">
      <button type="button" class="btn btn-submit" @click="deleteAnswer()">삭제</button>
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
    'answer',
  ], methods : {
    ...mapActions(["triggerAnswerReload"]),
    deleteAnswer : function(){
      axios({
        url: API.URL + `answers/${this.answer.answerId}`,
        method: "delete",
        headers: {
          Authorization: "Bearer " + this.accessToken,
        },
      })
      .then(() => {
        this.triggerAnswerReload();
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
  .answer-delete-modal{
     padding: 40px 50px;
  }

  .answer-delete-title{
    color: #0F4C81;
  }  

  .answer-delete-button-bar{
    text-align: center;
    margin-top: 30px;
  }

  .answer-delete-button-bar > button {
    margin: 0px 10px;
  }
</style>