<template>
  <div class="comment-list">
    <div class="comment-item" v-for="(comment, idx) in commentList" :key="idx">
      {{comment.content}}
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import API from '@/API.js'


export default {
  name: 'Comment',
  props: {
    answerId: String,
  },
  data() {
    return {
      commentList: [],
    }
  },
  methods: {

  },
  created() {
    axios({
      url: API.URL + `comments/list/${this.answerId}`,
      method: "get"
    })
    .then((res) => {
      this.commentList = res.data.commentList
    })
    .catch((err) => {
      console.log(err);
    });
  }
}
</script>

<style>
  .comment-list{
    padding: 30px;
  }

  .comment-item {
    border-top: 1px solid #e0e0e0;
  }
</style>
