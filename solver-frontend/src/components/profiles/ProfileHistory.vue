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
      @click="clickAnswer(answer.id)"
      class="my-answer-title">
      - {{ answer.title }} [ {{ answer.answerCount }} ]
    </div>
  </div>
</template>

<script>
import {mapActions, mapState} from 'vuex'

export default {
  name: 'ProfileMyQuestions',
  props: ['myAnswersTab'],
  methods: {
    ...mapActions(['myAnswersSetting', 'goQuestionDetail']),
    clickAnswer(questionId) {
      this.goQuestionDetail(questionId)
    }
  },
  computed: {
  ...mapState({
    userNickname: state => state.auth.userNickname,
    myAnswers: state => state.profiles.myAnswers,
    }),
  },
  created() {
    const myAnswersTabInfo = {
      userNickname: this.userNickname,
      tabnum: this.myAnswersTab,
    }
    this.myAnswersSetting(myAnswersTabInfo)
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

.my-answer-title:hover {
  background-color: #0F4C81;
  color: white;
  cursor: pointer;
} 
</style>