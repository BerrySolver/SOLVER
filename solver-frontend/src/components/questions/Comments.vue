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
      <div v-if="comment.nickname === userNickname" class="comment-delete" @click="deleteCheck(comment.id)"> <!--@click="deleteComment(comment.id)"-->
        <img style="width: 8px;" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iaXNvLTg4NTktMSI/Pg0KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDE5LjAuMCwgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPg0KPHN2ZyB2ZXJzaW9uPSIxLjEiIGlkPSJDYXBhXzEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4Ig0KCSB2aWV3Qm94PSIwIDAgNTEyLjAwMSA1MTIuMDAxIiBzdHlsZT0iZW5hYmxlLWJhY2tncm91bmQ6bmV3IDAgMCA1MTIuMDAxIDUxMi4wMDE7IiB4bWw6c3BhY2U9InByZXNlcnZlIj4NCjxnPg0KCTxnPg0KCQk8cGF0aCBkPSJNMjg0LjI4NiwyNTYuMDAyTDUwNi4xNDMsMzQuMTQ0YzcuODExLTcuODExLDcuODExLTIwLjQ3NSwwLTI4LjI4NWMtNy44MTEtNy44MS0yMC40NzUtNy44MTEtMjguMjg1LDBMMjU2LDIyNy43MTcNCgkJCUwzNC4xNDMsNS44NTljLTcuODExLTcuODExLTIwLjQ3NS03LjgxMS0yOC4yODUsMGMtNy44MSw3LjgxMS03LjgxMSwyMC40NzUsMCwyOC4yODVsMjIxLjg1NywyMjEuODU3TDUuODU4LDQ3Ny44NTkNCgkJCWMtNy44MTEsNy44MTEtNy44MTEsMjAuNDc1LDAsMjguMjg1YzMuOTA1LDMuOTA1LDkuMDI0LDUuODU3LDE0LjE0Myw1Ljg1N2M1LjExOSwwLDEwLjIzNy0xLjk1MiwxNC4xNDMtNS44NTdMMjU2LDI4NC4yODcNCgkJCWwyMjEuODU3LDIyMS44NTdjMy45MDUsMy45MDUsOS4wMjQsNS44NTcsMTQuMTQzLDUuODU3czEwLjIzNy0xLjk1MiwxNC4xNDMtNS44NTdjNy44MTEtNy44MTEsNy44MTEtMjAuNDc1LDAtMjguMjg1DQoJCQlMMjg0LjI4NiwyNTYuMDAyeiIvPg0KCTwvZz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjwvc3ZnPg0K" />
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import API from '@/API.js'
import { mapState, mapActions } from 'vuex'


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
    ...mapActions([
      'triggerCommentReload'
    ]),
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
    deleteComment: function (commentId) {
      axios({
        url: API.URL + `comments/${commentId}`,
        method: "delete",
        headers: { Authorization: "Bearer " + this.accessToken},
      })
      .then(() => {
        this.getCommentList()
        this.triggerCommentReload();
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
    deleteCheck:function(commentId){
      const $this =this;
      alertify.confirm("댓글삭제", "정말 삭제하시겠습니까?",
      function(){
        $this.deleteComment(commentId);
      }, function(){
        
      });
    },
  },
  created() {
    this.getCommentList()
  },
  computed: {
    ...mapState({
      accessToken: state => state.auth.accessToken,
      commentCreateTrigger: (state) => state.questions.commentCreateTrigger,
      userNickname: (state) => state.auth.userNickname,
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
    width: 600px;
  }

  .comment-delete {
    align-items: center;
    cursor: pointer;
    display: flex;
    height: 10px;
    margin-left: 800px;
    position: absolute;
    top: 17px;
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
    position: relative;
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
