<template>
  <div>
    <!-- detail에서 확인 불가능해서 여기에 일단 작성 -->
    <div class="question">
      <div class="category">
        카테고리 > 대분류 > 소분류
      </div>
      <div class="q-title">
        제목
      </div>
      <div class="q-profile">
        질문자 프로필
      </div>
      <div class="q-content">
        질문 내용입니다.
        질문 내용입니다.
        질문 내용입니다.
        질문 내용입니다.
      </div>
      <div class="q-hash">
        질문 해쉬태그
      </div>
    </div>
    <Answer/>
    <AnswerCreate/>
  </div>
</template>

<script>
import Answer from '../../components/questions/Answer.vue'
import AnswerCreate from '../../components/questions/AnswerCreate.vue'
export default {
  components: { Answer, AnswerCreate },
    name: 'Questions',
}
</script>

<style>
  .question {
    padding-top: 10vh;
  }
</style>