<template>
  <div class="answer row">
    <div class="col-2">
      개인 프로필
    </div>
    <div class="col-8 answer-box">
      답변입니다  답변입니다  답변입니다  답변입니다  답변입니다  답변입니다  답변입니다  답변입니다  답변입니다  답변입니다  답변입니다
        답변입니다  답변입니다  답변입니다  답변입니다  답변입니다  답변입니다  답변입니다  답변입니다  답변입니다  답변입니다  답변입니다
      <Comments/>
      <CommentsCreate/>
    </div>
    <div class="col-2"></div>
  </div>
</template>

<script>
import Comments from "@/components/questions/Comments"
import CommentsCreate from "@/components/questions/CommentsCreate"


export default {
    name: 'Answer',
    components: {
        Comments,
        CommentsCreate,
    },
    data() {
        
    }
}
</script>

<style>
	.answer {
		width: 100%;
    margin: 0;
	}

  .answer-box {
    border: 1px solid #658DC6;
    border-radius: 10px;
  }


</style>