<template>
  <div class="comment-list">
    <div class="comment-item" v-for="(comment, idx) in commentList" :key="idx">
      <div class="comment-header">
        <div style="float: left;">
          <img class="comment-item-profileImg" src="@/assets/profile-ex.jpg" alt="">
        </div>
        <div class="comment-info">
          <div style="font-size: 16px;">{{comment.nickname}}</div>
          <div style="color: #84898C; font-size: 12px;">{{humanize(now, comment.regDt)}}</div>
        </div>
      </div>
      <div class="comment-body">
        {{comment.content}}
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import API from '@/API.js'
import { mapState } from 'vuex'


export default {
  name: 'Comment',
  props: {
    answerId: String,
  },
  data() {
    return {
      commentList: [],
      now: new Date(),
    }
  },
  methods: {
    getCommentList: function () {
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
    },
    humanize: function (now, date) {
      const moment = require('moment')
      const dateData = new Date(date)
      let r = now - dateData
      if (parseInt(r) > 43200000) {
        r = moment(dateData).format('YYYY.MM.DD')
      } else if (parseInt(r) >= 3600000) {
        r = parseInt(parseInt(r) / 3600000).toString() + '시간 전'
      } else if (parseInt(r) >= 60000) {
        r = parseInt(parseInt(r) / 60000).toString() + '분 전'
      } else {
        r = '방금 전'
      }
      return r
    },
  },
  created() {
    this.getCommentList()
  },
  computed: {
    ...mapState({
      commentCreateTrigger: (state) => state.questions.commentCreateTrigger,
    })
  },
  watch: {
    commentCreateTrigger: function () {
      this.getCommentList()
    }
  }
}
</script>

<style>
  .comment-body {
    font-size: 15px;
    text-align: left;
    margin-left: 160px;
    width: 650px;
  }

  .comment-list{
    padding: 30px;
  }

  .comment-info {
    float: left; 
    margin-left: 10px;
    text-align: left;
  }

  .comment-item {
    border-top: 1px solid #e0e0e0;
    min-height: 70px;
    padding: 15px 20px 20px 20px;
  }

  .comment-item-header {
    align-items: center;
    border-bottom: 1px solid #e0e0e0;
    display: flex;
    height: 50px;
    margin: 30px 40px 0 40px;
    padding-bottom: 30px;
  }

  .comment-item-profileImg {
    border-radius: 70%;
    height: 30px;
    object-fit: cover;
    width: 30px;
  }
</style>
