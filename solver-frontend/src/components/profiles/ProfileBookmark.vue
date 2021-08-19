<template>
  <div class="my-bookmark-tab">
    <div style="display:flex; align-items:center; ">
      <div class="small-box"></div>
      <span class="subheading interval">북마크 목록</span>
    </div>
    <div
      v-for="question in myBookmarkQuestions.bookmarkQuestionList"
      :key="question.id"
      class="my-bookmark-title">
      <div @click="clickQuestion(question.id)">- {{ question.title }} [ {{ question.answerCount }} ]</div>
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

.my-bookmark-title div{
  padding: 3px;
  max-width: 800px;
}

.my-bookmark-title div:hover {
  background-color: #658DC6;
  color: white;
  cursor: pointer;
}

.my-bookmark-tab {
  height: 400px;
  overflow-y: scroll;
  overflow-x: hidden;
}

.my-bookmark-tab::-webkit-scrollbar {
  width: 10px; /*스크롤바의 너비*/
}

.my-bookmark-tab::-webkit-scrollbar-thumb {
  background-color: #B5C7D3; /*스크롤바의 색상*/
  border: 4px solid transparent;
  border-radius: 50px;
}

.my-bookmark-tab::-webkit-scrollbar-track {
  background-color: rgb(245, 245, 245); 
}

</style>