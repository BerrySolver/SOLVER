<template>
  <div>
    <Answer/>
    <AnswerCreate/>
  </div>
</template>

<script>
import Answer from "@/components/questions/Answer"
import AnswerCreate from "@/components/questions/AnswerCreate"


export default {
    name: 'QuestionsDetail',
    components: {
        Answer,
        AnswerCreate,
    },
    data() {  
    }
}
</script>

<style>

</style>
