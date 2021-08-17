<template>
  <div class="question-delete-modal solver-font">
    <h3 class="question-delete-title">답변 평가</h3>
    <hr />
    <p>답변 상대방의 점수를 평가해주세요</p>

    <label for="reasonInput">평가 이유를 적어주세요</label>
    <input id="reasonInput" type="text" />
    <div class="question-delete-button-bar">
      <button type="button" class="btn btn-submit" @click="insertEvaluate()">등록</button>
      <button type="button" class="btn btn-outline-cancel" @click="$emit('close')">취소</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import API from "@/API.js";
import router from "@/router";
import { mapState } from "vuex";

export default {
  data: function() {
    return {};
  },
  props: ["answerId"],
  methods: {
    insertEvaluate: function() {
      // axios({
      //   url: API.URL + `questions/${this.question}`,
      //   method: "delete",
      //   headers: { Authorization: "Bearer " + this.accessToken },
      // })
      //   .then(() => {
      //     router.push({ path: "/questions" });
      //   })
      //   .catch((err) => {
      //     console.log(err);
      //   });
      // this.$emit("close");
    },
  },
  computed: {
    ...mapState({
      accessToken: (state) => state.auth.accessToken,
    }),
  },
};
</script>

<style>
.question-delete-modal {
  padding: 40px 50px;
}

.question-delete-title {
  color: #0f4c81;
}

.question-delete-button-bar {
  text-align: center;
  margin-top: 30px;
}

.question-delete-button-bar > button {
  margin: 0px 10px;
}
</style>
