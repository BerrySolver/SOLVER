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
      - {{ question.title }} [ {{ question.answerCount }} ]
    </div>
  </div>
</template>

<script>
import {mapActions, mapState} from 'vuex'

export default {
  name: 'ProfileMyQuestions',
  props: ['myQuestionsTab'],
  methods: {
    ...mapActions(['myQuestionsSetting'])
  },
  computed: {
  ...mapState({
    userNickname: state => state.auth.userNickname,
    myQuestions: state => state.profiles.myQuestions,
    }),
  },
  created() {
    const myQuestionsTabInfo = {
      userNickname: this.userNickname,
      tabnum: this.myQuestionsTab,
    }
    this.myQuestionsSetting(myQuestionsTabInfo)
  }
}
</script>

<style>
.my-question-title {
  text-align: left;
  margin: 10px;
}

.my-question-title:hover {
  color: #658DC6
}
</style>