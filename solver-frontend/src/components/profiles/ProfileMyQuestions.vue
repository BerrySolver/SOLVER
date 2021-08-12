<template>
  <div>
    <!-- 질문 목록 소제목 -->
    <div style="display:flex; align-items:center; ">
      <div class="small-box"></div>
      <span class="subheading interval">질문 목록</span>
    </div>
    <!-- 질문한 질문 제목들 -->
    <div
      v-for="question in myQuestions.myQuestionList"
      :key="question.id"
      class="my-question-title">
      <div @click="clickQuestion(question.id)">- {{ question.title }} [ {{ question.answerCount }} ]</div>
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
          myQuestions: {},
    }
  },
  methods: {
    ...mapActions(['goQuestionDetail']),
    clickQuestion(questionId) {
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
      this.myQuestions = res.data
    })
    .catch((err) => {
      console.log(err)
    })
  }
}
</script>

<style>
.my-question-title {
  text-align: left;
  margin: 10px;
}

.my-question-title div{
  padding: 3px;
  max-width: 800px;
}

.my-question-title div:hover {
  background-color: #658DC6;
  color: white;
  cursor: pointer;
}
</style>