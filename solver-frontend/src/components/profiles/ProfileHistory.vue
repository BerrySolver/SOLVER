<template>
  <div>
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
  text-align: left;
  margin: 10px;
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
</style>