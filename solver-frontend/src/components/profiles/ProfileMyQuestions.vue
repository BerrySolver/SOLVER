<template>
  <div class="my-question-tab">
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

.my-question-tab {
  height: 400px;
  overflow-y: scroll;
  overflow-x: hidden;
}

.my-question-tab::-webkit-scrollbar {
  width: 10px; /*스크롤바의 너비*/
}

.my-question-tab::-webkit-scrollbar-thumb {
  background-color: #B5C7D3; /*스크롤바의 색상*/
  border: 4px solid transparent;
  border-radius: 50px;
}

.my-question-tab::-webkit-scrollbar-track {
  background-color: rgb(245, 245, 245); 
}

</style>