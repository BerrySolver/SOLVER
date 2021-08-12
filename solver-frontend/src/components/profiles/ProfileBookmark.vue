<template>
  <div>
    <div style="display:flex; align-items:center; ">
      <div class="small-box"></div>
      <span class="subheading interval">북마크 목록</span>
    </div>
    <div
      v-for="question in myBookmarkQuestions.bookmarkQuestionList"
      :key="question.id"
      class="my-bookmark-title">
      <span @click="clickQuestion(question.id)">- {{ question.title }} [ {{ question.answerCount }} ]</span>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import API from "@/API.js"
import {mapActions} from 'vuex'

export default {
  name: 'ProfileBookmark',
  props: ['nickname', 'tabNum'],
  data() {
    return {
          myBookmarkQuestions: {},
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
      console.log(res.data)
      this.myBookmarkQuestions = res.data
    })
    .catch((err) => {
      console.log(err)
    })
  }
}
</script>

<style>
.my-bookmark-title {
  text-align: left;
  margin: 10px;
}

.my-bookmark-title span{
  padding-bottom: 3px;
}

.my-bookmark-title span:hover {
  border-bottom: 2px solid #658DC6;
  cursor: pointer;
}
</style>