<template>
  <div class="comment-delete-modal solver-font">
    <h3 class="comment-delete-title">댓글 삭제</h3>
    <hr>
    <p>정말 댓글을 삭제하시겠습니까?</p>
    <div class="comment-delete-button-bar">
      <button type="button" class="btn btn-submit" @click="deleteComment()">삭제</button>
      <button type="button" class="btn btn-outline-cancel" @click="$emit('close')">취소</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import API from "@/API.js";
import { mapActions, mapState } from 'vuex'

export default {
  data:function(){
    return {
    }
  }, props : [
    'commentId',
  ], methods : {
    ...mapActions([
      'triggerCommentReload'
    ]),
    deleteComment : function(){
      axios({
        url: API.URL + `comments/${this.commentId}`,
        method: "delete",
        headers: { Authorization: "Bearer " + this.accessToken},
      })
      .then(() => {
        this.triggerCommentReload();
      })
      .catch((err) => {
        console.log(err);
      });
      this.$emit('close');
    }
  },
  computed: {
    ...mapState({
      accessToken: state => state.auth.accessToken,
    }),
  }
}
</script>

<style>
  .comment-delete-modal{
     padding: 40px 50px;
  }

  .comment-delete-title{
    color: #0F4C81;
  }  

  .comment-delete-button-bar{
    text-align: center;
    margin-top: 30px;
  }

  .comment-delete-button-bar > button {
    margin: 0px 10px;
  }
</style>