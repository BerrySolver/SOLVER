<template>
  <div class="question-delete-modal solver-font">
    <h3 class="question-delete-title">답변 평가</h3>
    <hr />
    <p>답변 상대방의 점수를 평가해주세요</p>
    <div class="rating">
      <input
        v-model="rateValue"
        type="range"
        class="form-range"
        min="0"
        max="10"
        step="0.1"
        id="customRange3"
      />
      <label class="imageLabel" for="customRange3"></label>
      <label class="imageLabel" for="customRange3"></label>
      <label class="imageLabel" for="customRange3"></label>
      <label class="imageLabel" for="customRange3"></label>
      <label class="imageLabel" for="customRange3"></label>
    </div>

    <input readonly id="warnText" for="reasonInput" v-model="warningText" />
    <textarea
      id="reasonInput"
      class="comment-form-field"
      cols="30"
      rows="10"
      v-model="inputValue"
      placeholder="평가 내용을 입력해주세요 (선택)"
    >
    </textarea>
    <div class="question-delete-button-bar">
      <button type="button" class="btn btn-submit" @click="insertEvaluate()">등록</button>
      <button type="button" class="btn btn-outline-cancel" @click="$emit('close')">취소</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import API from "@/API.js";
import { mapState } from "vuex";

export default {
  data: function() {
    return {
      inputValue: "",
      rateValue: -1,
      warningText: "",
    };
  },
  props: ["questionId", "userName"],
  methods: {
    insertEvaluate() {
      if (this.rateValue < 0) {
        this.warningText = "점수를 선택해주세요!";
        return;
      }
      // this.$route.params
      axios({
        url: API.URL + `conferences/evaluation`,
        method: "post",
        headers: { Authorization: "Bearer " + this.accessToken },
        data: {
          score: this.rateValue,
          reason: this.inputValue,
          nickname: this.userName,
          // questionId: this.questionId,
          // questionId: this.questionId,
        },
      })
        .then(() => {
          location.href = "/";
          // router.push({ redirect: "/" });
        })
        .catch((err) => {
          console.log(err);
        });
      this.$emit("close");
    },
    setRate() {
      this.warningText = "";
      if (this.rateValue < 0.3) this.rateValue = 0;
      else if (this.rateValue >= 10) this.rateValue = 10;
      else this.rateValue = Math.ceil(this.rateValue);

      const $this = this;

      // this.rate = newRate;
      let items = document.querySelectorAll(".imageLabel");
      items.forEach(function(item, idx) {
        if (idx + 1 == $this.rateValue / 2 + 0.5) {
          item.className = "imageLabel halfImage";
        } else if ((idx + 1) * 2 <= $this.rateValue) {
          item.className = "imageLabel fullImage";
        } else {
          item.className = "imageLabel";
        }
      });
    },
    finishiConference() {
      this.$emit("close");
      location.href = "/";
      // router.push({ redirect: "/" });
    },
  },
  computed: {
    ...mapState({
      accessToken: (state) => state.auth.accessToken,
    }),
  },
  mounted() {
    const $this = this;
    document.querySelector("#customRange3").addEventListener("click", function(e) {
      $this.setRate();
    });
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
