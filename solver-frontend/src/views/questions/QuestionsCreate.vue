<template>
  <div>
    <div class="question-header">
      <div class="question-banner">
        <img src="@/assets/logo-white-2.png" alt="white-logo" />
        <div class="question-banner-ment">
          <h6 class="ment">얼마든 물어보고 해답을 찾아가세요!</h6>
          <h6 class="ment">
            여러분의 공부하다 막힌 부분을 <span>사이다</span>처럼 해결해줄 능력자들이 기다리는
            중이에요!
          </h6>
        </div>
      </div>
    </div>
    <div class="question-body">
      <div class="question-body-contents">
        <div class="question-main container">
          <div class="question-query">
            <div>
              <div class="selectBox row mb-2">
                <select
                  color="#0F4C81"
                  class="selectMainCategory"
                  v-model="request.mainCategoryIndex"
                  width="150px"
                  @change="setMainCategory"
                >
                  <option
                    v-for="(item, index) in categories"
                    :key="index"
                    :value="index"
                    style="font-size: 15px;"
                    >{{ item.codeName }}</option
                  >
                </select>
                <select
                  color="#0F4C81"
                  class="selectSubCategory"
                  v-model="request.subCategoryIndex"
                  width="150px"
                  @change="setSubCategory"
                >
                  <option
                    v-for="(item, index) in subCategories"
                    :key="index"
                    :value="index"
                    style="font-size: 15px;"
                    >{{ item.subCategoryName }}</option
                  >
                </select>
                <select
                  color="#0F4C81"
                  class="selectDifficulty"
                  v-model="request.difficulty"
                  width="150px"
                  @change="setDifficulty"
                >
                  <option
                    v-for="(item, index) in difficultyOptions"
                    :key="index"
                    :value="item.value"
                    :text="item.text"
                    style="font-size: 15px;"
                    >{{ item.text }}</option
                  >
                </select>
              </div>
              <div class="title row mt-4">
                <div class="col-3 titleText">제목</div>
                <div class="col-9 titleInputDiv">
                  <input
                    class="form-field titleInput"
                    type="text"
                    v-model="request.query"
                    placeholder="궁금한 모든 것을 검색해보세요!"
                    @keypress.enter="setQuery"
                  />
                </div>
              </div>
            </div>
          </div>
          <div class="mb-3 editor">
            <div id="divEditorInsert"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import axios from "axios";
import API from "@/API.js";
import { mapState, mapActions } from "vuex";
import CKEditor from "@ckeditor/ckeditor5-vue2";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";

Vue.use(CKEditor);

export default {
  name: "QuestionsCreate",
  data() {
    return {
      CKEditor: "",
      categories: [],
      subCategories: [],
      request: {
        curCategory: "전체",
        mainCategoryName: null,
        mainCategoryCode: null,
        mainCategoryIndex: 0,
        subCategoryIndex: 0,
        subCategory: null,
        query: null,
        difficulty: null,
        type: null,
        mode: "releaseDesc",
      },
      questionList: [],
      difficultyOptions: [
        { text: "난이도 전체", value: null },
        { text: "난이도 상", value: 3 },
        { text: "난이도 중", value: 2 },
        { text: "난이도 하", value: 1 },
      ],
      typeColor: ["#0F4C81", "#89848C", "#89848C"],
      modeColor: ["#0F4C81", "#89848C", "#89848C"],
      now: new Date(),
    };
  },
  methods: {
    ...mapActions(["setStateQuery"]),
    setMainCategory: function() {
      const idx = this.request.mainCategoryIndex;
      this.subCategories = this.categories[idx].category;
      this.request.mainCategoryCode = this.categories[idx].code;
      this.request.mainCategoryName = this.categories[idx].codeName;
      console.log(this.request.mainCategoryCode);
      console.log(this.request.mainCategoryName);
    },
    setSubCategory: function() {
      const idx = this.request.subCategoryIndex;
      this.subCategory = this.subCategories[idx].subCategoryCode;
      console.log(this.subCategory);
    },
    setDifficulty: function() {
      (this.request.type = null), (this.request.mode = "releaseDesc");
      this.getQuestionList();
    },
    setType: function(typeNum) {
      if (typeNum === 1) {
        this.request.type = "040";
      } else if (typeNum == 2) {
        this.request.type = "041";
      } else {
        this.request.type = null;
      }
      this.request.mode = "releaseDesc";
      this.getQuestionList();
    },
    setMode: function(modeNum) {
      if (modeNum === 1) {
        this.request.mode = "answerDesc";
      } else if (modeNum == 2) {
        this.request.mode = "likeDesc";
      } else {
        this.request.mode = "releaseDesc";
      }
      this.getQuestionList();
    },
    humanize: function(now, date) {
      const moment = require("moment");
      const dateData = new Date(date);
      let r = now - dateData;
      if (parseInt(r) > 43200000) {
        r = moment(dateData).format("YY-MM-DD\u00A0\u00A0HH:MM");
      } else if (parseInt(r) >= 3600000) {
        r = parseInt(parseInt(r) / 3600000).toString() + "시간 전";
      } else if (parseInt(r) >= 60000) {
        r = parseInt(parseInt(r) / 60000).toString() + "분 전";
      } else {
        r = "방금 전";
      }
      return r;
    },
  },
  created: function() {
    if (this.query != null) {
      this.request.query = this.query;
    }
    axios({
      url: API.URL + API.ROUTES.getCategory,
      method: "get",
    })
      .then((res) => {
        console.log(res);
        this.categories = res.data;
        this.subCategories = res.data[0].category;
      })
      .catch((err) => {
        console.log(err);
      });
  },
  mounted() {
    ClassicEditor.create(document.querySelector("#divEditorInsert"))
      .then((editor) => {
        this.CKEditor = editor;
      })
      .catch((err) => {
        console.error(err.stack);
      });

    // bootstrap modal show event hook
    // InsertModal 이 보일 때 초기화
    let $this = this;
    this.$el.addEventListener("show.bs.modal", function() {
      $this.initUI();
    });
  },
  computed: {
    ...mapState({
      query: (state) => state.questions.query,
    }),
    typeWatch: function() {
      return this.request.type;
    },
    modeWatch: function() {
      return this.request.mode;
    },
  },
  watch: {
    typeWatch: function() {
      if (this.typeWatch == "040") {
        this.typeColor[0] = "#89848C";
        this.typeColor[1] = "#0F4C81";
        this.typeColor[2] = "#89848C";
      } else if (this.typeWatch == "041") {
        this.typeColor[0] = "#89848C";
        this.typeColor[1] = "#89848C";
        this.typeColor[2] = "#0F4C81";
      } else {
        this.typeColor[0] = "#0F4C81";
        this.typeColor[1] = "#89848C";
        this.typeColor[2] = "#89848C";
      }
    },
    modeWatch: function() {
      if (this.modeWatch == "answerDesc") {
        this.modeColor[0] = "#89848C";
        this.modeColor[1] = "#0F4C81";
        this.modeColor[2] = "#89848C";
      } else if (this.modeWatch == "likeDesc") {
        this.modeColor[0] = "#89848C";
        this.modeColor[1] = "#89848C";
        this.modeColor[2] = "#0F4C81";
      } else {
        this.modeColor[0] = "#0F4C81";
        this.modeColor[1] = "#89848C";
        this.modeColor[2] = "#89848C";
      }
    },
  },
};
</script>

<style>
.form-field {
  display: inline;
  width: 80%;
  padding: 8px 16px;
  line-height: 25px;
  font-size: 14px;
  font-weight: 500;
  font-family: inherit;
  border-radius: 6px;
  color: #84898c;
  border: 1px solid #cdd9ed;
  background: #fff;
  transition: border 0.3s ease;
}

.form-field::-moz-placeholder {
  color: #cbd1dc;
}

.form-field:-ms-input-placeholder {
  color: #cbd1dc;
}

.form-field::placeholder {
  color: #cbd1dc;
}

.form-field:focus {
  outline: none;
  border-color: #658dc6;
}

.ment {
  color: white;
  vertical-align: middle;
}

.ment span {
  color: #0f4c81;
  font-weight: 700;
}

.question-banner {
  align-items: center;
  display: flex;
}

.question-banner img {
  height: 100px;
}

.question-banner-ment {
  height: 40px;
  margin-left: 40px;
  text-align: left;
}

.question-body {
  display: flex;
  justify-content: center;
}

.question-body-contents {
  display: flex;
  margin-top: 20px;
  width: 1190px;
}

.question-category {
  color: #84898c;
  margin-bottom: 100px;
  text-align: left;
  width: 180px;
}

.question-category-items {
  border: 1px solid #b5c7d3;
  margin-top: 20px;
}

.question-create-btn {
  background-color: #0f4c81;
  border-radius: 6px;
  color: white;
  cursor: pointer;
  float: left;
  margin-left: 520px;
  margin-top: 45px;
  width: 100px;
}

.question-create-btn:hover {
  background-color: #658dc6;
  transition: 0.4s;
}

.question-header {
  background-color: #658dc6;
  display: flex;
  height: 180px;
  justify-content: center;
}

.question-main {
}

.question-query {
  margin: 0px;
  width: 100%;
  height: 160px;
}

.question-query-head {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.question-type {
  color: #84898c;
  font-weight: 600;
  padding-top: 5px;
}

.question-type-item {
  cursor: pointer;
}

.selectDifficulty {
  width: 30%;
  border-color: #cdd9ed;
  color: #84898c;
  font-size: 15px;
  padding-top: 2px;
}

.selectMainCategory {
  width: 30%;
  border-color: #cdd9ed;
  color: #84898c;
  font-size: 15px;
  padding-top: 2px;
}

.selectSubCategory {
  width: 30%;
  border-color: #cdd9ed;
  color: #84898c;
  font-size: 15px;
  padding-top: 2px;
}

.selectBox {
  width: 100%;
  display: flex;
  justify-content: space-between;
}

.ck.ck-editor__editable {
  height: 400px;
  margin-bottom: 100px;
}

.titleText {
  text-align: center;
  align-content: center;
}

.titleInput {
  width: 100%;
}

.titleInputDiv {
  display: flex;
  align-items: center;
}
</style>
