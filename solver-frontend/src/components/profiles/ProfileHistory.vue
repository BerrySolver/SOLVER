<template>
  <div class="my-history-tab">
    <!-- 질문 목록 소제목 -->
    <div style="display:flex; align-items:center; ">
      <div class="small-box"></div>
      <span class="subheading interval">답변 목록</span>
    </div>
    <!-- 질문한 질문 제목들 -->
    <div
      v-for="answer in myAnswers.answerQuestionList"
      :key="answer.id"
      class="my-answer-title">
      <div @click="clickAnswer(answer.id)">- {{ answer.title }} [ {{ answer.answerCount }} ]</div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import API from "@/API.js"
import {mapActions} from 'vuex'

export default {
  name: 'ProfileMyQuestions',
  props: ['nickname', 'tabNum'],
  data() {
    return {
      myAnswers: {},
    }
  },
  methods: {
    ...mapActions(['goQuestionDetail']),
    clickAnswer(questionId) {
      this.goQuestionDetail(questionId)
    }
  },
  created() {
    axios({
      url: API.URL + `profiles/${this.nickname}/tab`,
      method: "get",
      params: {
        tabNum: this.tabNum,
      }
    })
    .then((res) => {
      this.myAnswers = res.data
    })
    .catch((err) => {
      console.log(err)
    })
  }
}
</script>

<style>
.history-content {
  margin-left: 15px;
  margin-top: 10px;
  text-align: left;
}

.my-answer-title {
  margin: 10px;
  text-align: left;
}

.my-answer-title div{
  padding: 3px;
  max-width: 800px;
}

.my-answer-title div:hover {
  background-color: #658DC6;
  color: white;
  cursor: pointer;
} 

.my-history-tab {
  height: 400px;
  overflow-y: scroll;
  overflow-x: hidden;
}

.my-history-tab::-webkit-scrollbar {
  width: 10px; /*스크롤바의 너비*/
}

.my-history-tab::-webkit-scrollbar-thumb {
  background-color: #B5C7D3; /*스크롤바의 색상*/
  border: 4px solid transparent;
  border-radius: 50px;
}

.my-history-tab::-webkit-scrollbar-track {
  background-color: rgb(245, 245, 245); 
}

</style>