<template>
  <div class="comment-create">
    <div class="comment-form-group">
      <textarea
        class="comment-form-field"
        cols="30"
        rows="10"
        v-model="inputValue"
        placeholder="댓글을 입력하세요!"
      >
      </textarea>
      <span style="cursor: pointer;" @click="createComment">등&nbsp;&nbsp;록</span>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import API from "@/API.js";
import {mapState, mapActions} from 'vuex';


export default {
  name: 'CommentsCreate',
  props: {
    answerId: String,
  },
  data() {
      return {
        inputValue: '',
      }
  },
  methods: {
    ...mapActions([
      'triggerCommentReload'
    ]),
    createComment: function () {
      if (this.inputValue !== "") {
        axios({
          url: API.URL + `comments/${this.answerId}`,
          method: "post",
          headers: { 
            Authorization: "Bearer " + this.accessToken
          },
          data: {
              content: this.inputValue
          },
        })
        .then(() => {
          this.triggerCommentReload()
          this.inputValue = ''
        })
        .catch((err) => {
          console.log(err);
        });
      }
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
  .comment-create {
    display: flex;
    justify-content: left;
  }

  .comment-form-field {
    width: 100%;
    padding: 8px 8px;
    line-height: 25px;
    font-size: 15px;
    font-weight: 500;
    font-family: inherit;
    border-radius: 6px;
    -webkit-appearance: none;
    color: black;
    border: 1px solid #cdd9ed;
    background: #fff;
    transition: border 0.3s ease;
  }

  .comment-form-field::-moz-placeholder {
    color: #cbd1dc;
  }

  .comment-form-field:-ms-input-placeholder {
    color: #cbd1dc;
  }

  .comment-form-field::placeholder {
    color: #cbd1dc;
  }

  .comment-form-field:focus {
    outline: none;
    border-color: #658dc6;
  }

  .comment-form-group {
    display: flex;
    float: left;
    height: 150px;
    margin: 0 0 30px 30px;
    width: 835px;
  }

  .comment-form-group > span,
  .comment-form-group .comment-form-field {
    display: block;
  }

  .comment-form-group > span:not(:first-child):not(:last-child),
  .comment-form-group .comment-form-field:not(:first-child):not(:last-child) {
    border-radius: 0;
  }

  .comment-form-group > span:first-child,
  .comment-form-group .comment-form-field:first-child {
    border-radius: 6px 0 0 6px;
  }

  .comment-form-group > span:last-child,
  .comment-form-group .comment-form-field:last-child {
    border-radius: 0 6px 6px 0;
  }

  .comment-form-group > span:not(:first-child),
  .comment-form-group .comment-form-field:not(:first-child) {
    margin-left: -1px;
  }

  .comment-form-group .comment-form-field {
    position: relative;
    z-index: 1;
    flex: 1 1 auto;
    width: 1%;
    margin-top: 0;
    margin-bottom: 0;
  }

  .comment-form-group > span {
    text-align: center;
    padding: 8px 12px;
    font-size: 14px;
    line-height: 130px;
    width: 100px;
    color: #99a3ba;
    background: #eef4ff;
    border: 1px solid #cdd9ed;
    transition: background 0.3s ease, border 0.3s ease, color 0.3s ease;
  }

  .comment-form-group:focus-within > span {
    color: #fff;
    background: #678efe;
    border-color: #658dc6;
  }
</style>
