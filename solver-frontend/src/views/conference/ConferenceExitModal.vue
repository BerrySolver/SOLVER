<template>
  <div class="question-delete-modal solver-font">
    <h3 class="question-delete-title">회의 종료</h3>
    <hr />
    <p>화상 회의에서 나가시겠습니까?</p>
    <div class="question-delete-button-bar">
      <button type="button" class="btn btn-submit" @click="exitConferenceLog()">나가기</button>
      <button type="button" class="btn btn-outline-cancel" @click="$emit('close')">취소</button>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import axios from "axios";
import API from "@/API.js";

export default {
  data: function() {
    return {
      inputValue: "",
      rateValue: -1,
      warningText: "",
    };
  },
  props: ["questionId"],
  methods: {
    finishiConference() {
      this.$emit("close");
      location.href = "/";
    },
    exitConferenceLog() {
      axios({
        url: API.URL + API.ROUTES.conferenceLog,
        method: "post",
        data: {
          type: "031",
        },
        headers: { Authorization: "Bearer " + this.accessToken },
      })
        .then(() => {
          this.finishiConference();
        })
        .catch((e) => {
          console.log(e);
        });
    },
  },
  computed: {
    ...mapState({
      accessToken: (state) => state.auth.accessToken,
    }),
  },
  mounted() {},
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

<style scoped>
#customRange3 {
  position: absolute;
  display: inline-block;
  z-index: 20;
  opacity: 0.001;
  width: 220px;
  height: 60px;
  background: none;
  cursor: pointer;
  vertical-align: top;
}
.rating .rate_radio {
  position: relative;
  display: inline-block;
  z-index: 20;
  opacity: 0.001;
  width: 60px;
  height: 60px;
  background-color: #fff;
  cursor: pointer;
  vertical-align: top;
  display: none;
}
.rating .rate_radio + label.imageLabel {
  position: relative;
  display: inline-block;
  margin-left: -4px;
  z-index: 10;
  width: 60px;
  height: 60px;
  background-image: url("../../assets/berry-2-b.png");
  background-repeat: no-repeat;
  background-size: 60px 60px;
  cursor: pointer;
}

.rating label.imageLabel.fullImage {
  background-image: url("../../assets/berry-2.png");
}

.rating label.imageLabel.halfImage {
  background-image: url("../../assets/berry-2-half.png");
}

.rating label.imageLabel {
  position: relative;
  display: inline-block;
  margin-left: -10px;
  margin-right: -10px;
  z-index: 10;
  width: 60px;
  height: 60px;
  background-image: url("../../assets/berry-2-b.png");
  background-repeat: no-repeat;
  background-size: 60px 60px;
  cursor: pointer;
}
.rating .rate_radio:checked + label {
  background-image: url("../../assets/berry-2.png");
}

.rating {
  display: flex;
  justify-content: center;
  width: 500px;
  /* position: relative; */
}

input {
  border-style: solid;
}

#warnText {
  margin-top: 15px;
  margin-bottom: 10px;
  border-style: none;
  display: block;
  color: red;
  cursor: auto;
}

#reasonInput {
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
  height: 100px;
}

#reasonInput::-moz-placeholder {
  color: #cbd1dc;
}

#reasonInput:-ms-input-placeholder {
  color: #cbd1dc;
}

#reasonInput::placeholder {
  color: #cbd1dc;
}

#reasonInput:focus {
  outline: none;
  border-color: #658dc6;
}

.imageImportLabel {
  display: none;
}
</style>
