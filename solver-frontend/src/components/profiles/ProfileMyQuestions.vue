<template>
  <div>
    <!-- 질문 목록 소제목 -->
    <div style="display:flex; align-items:center; ">
      <div class="small-box"></div>
      <span class="subheading interval">질문 목록</span>
    </div>
    <!-- 질문한 질문 제목들 -->
    <div>
      <!-- {{ myQuestions }} -->
      <div class="history-content">답변했던 질문 제목</div>
      <div class="history-content">답변했던 질문 제목 답변했던 질문 제목 답변했던 질문 제목</div>
      <div class="history-content">답변했던 질문 제목 답변했던 질문 제목</div>
      <div class="history-content">답변했던 질문 제목</div>
      <div class="history-content">답변했던 질문 제목 답변했던 질문 제목 답변했던 질문 제목</div>
      <div class="history-content">답변했던 질문 제목 답변했던 질문 제목</div>
      <div class="history-content">답변했던 질문 제목</div>
      <div class="history-content">답변했던 질문 제목 답변했던 질문 제목 답변했던 질문 제목</div>
      <div class="history-content">답변했던 질문 제목 답변했던 질문 제목</div>
      <div class="history-content">답변했던 질문 제목</div>
      <div class="history-content">답변했던 질문 제목 답변했던 질문 제목 답변했던 질문 제목</div>
      <div class="history-content">답변했던 질문 제목 답변했던 질문 제목</div>                
    </div>
  </div>
</template>

<script>
import {mapActions, mapState} from 'vuex'

export default {
  name: 'ProfileMyQuestions',
  // methods: {
  //   ...mapActions(['myQuestionsSetting'])
  // },
  // computed: {
  // ...mapState({
  //   myQuestions: state => state.profiles.myQuestions,
  //   userNickname: state => state.auth.userNickname,
  //   }),
  // },
  // created() {
  //   console.log(this.userNickname)
  //   this.myQuestionsSetting(this.userNickname)
  // }
}
</script>

<style>

</style>