<template>
  <div>
    {{question.mainCategory}}
    {{question.subCategory}}
    {{question.title}}
    {{question.content}}
    {{question.difficulty}}
    {{question.nickname}}
    <Answer/>
    <AnswerCreate/>
  </div>
</template>

<script>
import axios from 'axios'
import API from '@/API.js'
import Answer from "@/components/questions/Answer"
import AnswerCreate from "@/components/questions/AnswerCreate"


export default {
    name: 'QuestionsDetail',
    components: {
        Answer,
        AnswerCreate,
    },
    data() {  
      return {
        question: {},
      }
    },
    methods: {

    },
    created() {
      axios({
        url: API.URL + `questions/${this.$route.params.questionId}/info`,
        method: "get"
      })
      .then((res) => {
        console.log(res.data);
        this.question = res.data
      })
      .catch((err) => {
        console.log(err);
      });
    }
}
</script>

<style>

</style>
