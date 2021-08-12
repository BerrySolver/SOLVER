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
                <vs-select
                  color="#0F4C81"
                  class="selectMainCategory"
                  v-model="request.mainCategoryIndex"
                  width="285px"
                  @change="setMainCategory"
                >
                  <vs-select-item
                    v-for="(item, index) in categories"
                    :key="index"
                    :value="index"
                    :text="item.codeName"
                    style="font-size: 15px;"
                    >{{ item.codeName }}</vs-select-item
                  >
                </vs-select>
                <vs-select
                  color="#0F4C81"
                  class="selectSubCategory"
                  v-model="request.subCategoryIndex"
                  width="285px"
                  @change="setSubCategory"
                >
                  <vs-select-item
                    v-for="(item, index) in subCategories"
                    :key="index"
                    :text="item.subCategoryName"
                    :value="index"
                    style="font-size: 15px;"
                    >{{ item.subCategoryName }}</vs-select-item
                  >
                </vs-select>
                <vs-select
                  color="#0F4C81"
                  class="selectDifficulty"
                  v-model="request.difficulty"
                  width="285px"
                  @change="setDifficulty"
                >
                  <vs-select-item
                    v-for="(item, index) in difficultyOptions"
                    :key="index"
                    :value="item.value"
                    :text="item.text"
                    style="font-size: 15px;"
                    >{{ item.text }}</vs-select-item
                  >
                </vs-select>
              </div>
              <div class="title row mt-4">
                <!-- <div class="col-3 titleText">제목</div> -->
                <div class="col-12 titleInputDiv">
                  <input
                    class="form-field titleInput"
                    type="text"
                    v-model="request.title"
                    placeholder="제목을 입력해주세요"
                  />
                </div>
              </div>
            </div>
          </div>
          <div class="mb-3 editor">
            <div id="divEditorInsert"></div>
            <div class="row btn-group">
              <div class="question-create-btn1 col-5" @click="questionModify">
                수정하기
              </div>
              <div class="question-cancel-btn1 col-5" @click="clickCancle">
                취소
              </div>
            </div>
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
import { mapState, mapActions, mapGetters } from "vuex";
import CKEditor from "@ckeditor/ckeditor5-vue2";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import Question from "@/store/modules/questions.js";

Vue.use(CKEditor);

class UploadAdapter {
  constructor(loader) {
    this.loader = loader;
  }

  upload() {
    return this.loader.file.then(
      (file) =>
        new Promise((resolve, reject) => {
          this._initRequest();
          this._initListeners(resolve, reject, file);
          this._sendRequest(file);
        })
    );
  }

  _initRequest() {
    const xhr = (this.xhr = new XMLHttpRequest());
    xhr.open("POST", "http://localhost:8080/api/v1/test/image/drag", true);
    xhr.responseType = "json";
  }

  _initListeners(resolve, reject) {
    const xhr = this.xhr;
    const genericErrorText = "파일을 업로드 할 수 없습니다.";

    xhr.addEventListener("error", () => {
      reject(genericErrorText);
    });
    xhr.addEventListener("abort", () => reject());
    xhr.addEventListener("load", () => {
      const response = xhr.response;
      if (!response || response.error) {
        return reject(response && response.error ? response.error.message : genericErrorText);
      }

      resolve({
        default: response.url, //업로드된 파일 주소
      });
    });
  }

  _sendRequest(file) {
    const data = new FormData();
    data.append("upload", file);
    this.xhr.send(data);
  }
}

function MyCustomUploadAdapterPlugin(editor) {
  editor.plugins.get("FileRepository").createUploadAdapter = (loader) => {
    return new UploadAdapter(loader);
  };
}

export default {
  name: "QuestionsCreate",
  data() {
    return {
      CKEditor: "",
      categories: [],
      subCategories: [],
      request: {
        title: "",
        curCategory: "전체",
        mainCategoryName: null,
        mainCategoryCode: null,
        mainCategoryIndex: 0,
        subCategoryIndex: 0,
        subCategory: null,
        query: null,
        difficulty: 1,
        type: null,
        mode: "releaseDesc",
      },
      questionList: [],
      difficultyOptions: [
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
    ...mapActions(["setStateQuery", "goQuestionDetail"]),
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
    },
    setMode: function(modeNum) {
      if (modeNum === 1) {
        this.request.mode = "answerDesc";
      } else if (modeNum == 2) {
        this.request.mode = "likeDesc";
      } else {
        this.request.mode = "releaseDesc";
      }
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
    questionModify() {
      if (this.getAccessToken == null || this.getAccessToken == "") {
        console.log("로그인 안 된 상태");
        return;
      }

      axios({
        url: API.URL + `questions/${Question.state.questionId}`,
        method: "post",
        data: {
          title: this.request.title,
          content: this.CKEditor.getData(),
          mainCategory: this.categories[this.request.mainCategoryIndex].code,
          subCategory: this.subCategories[this.request.subCategoryIndex].subCategoryCode,
          difficulty: this.request.difficulty,
        },
        headers: { Authorization: "Bearer " + this.getAccessToken },
      })
        .then((res) => {
          console.log(res);
          this.goQuestionDetail(Question.state.questionId);
        })
        .catch((e) => {
          console.log(e);
        });

      // axios
      //   .post("/notices", formData, { headers: { "Content-Type": "multipart/form-data" } })
      //   .then(({ data }) => {
      //     console.log("InsertModalVue: data : ");
      //     console.log(data);
      //     if (data.result == "login") {
      //       this.$router.push("/login");
      //     } else {
      //       this.$router.push("/notice");
      //     }
      //   })
      //   .catch((error) => {
      //     console.log("InsertModalVue: error ");
      //     console.log(error);
      //   });
    },
    clickCancle() {
      this.$router.go(-1);
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
        // this.subCategories = res.data[0].category;

        for (var idx = 0; idx < this.categories.length; idx++) {
          if (this.categories[idx].codeName == Question.state.question.mainCategory) {
            this.request.mainCategoryIndex = idx;
            // this.subCategories = res.data[idx].category;
            break;
          }
        }

        this.setMainCategory();
        for (var idx = 0; idx < this.subCategories.length; idx++) {
          if (this.subCategories[idx].subCategoryName == Question.state.question.subCategory) {
            this.request.subCategoryIndex = idx;
            break;
          }
        }

        this.setSubCategory();
      })
      .catch((err) => {
        console.log(err);
      });
  },
  mounted() {
    console.log(this.getQuestion);
    this.request.difficulty = Question.state.question.difficulty;
    this.request.title = Question.state.question.title;

    ClassicEditor.create(document.querySelector("#divEditorInsert"), {
      extraPlugins: [MyCustomUploadAdapterPlugin],
      toolbar: {
        items: [
          "heading",
          "|",
          "bold",
          "italic",
          "|",
          "link",
          "|",
          "bulletedList",
          "numberedList",
          "|",
          "ckfinder",
          "|",
          "undo",
          "redo",
        ],
      },
    })
      .then((editor) => {
        this.CKEditor = editor;
        this.CKEditor.setData(Question.state.question.content);
      })
      .catch((err) => {
        console.error(err.stack);
      });

    let $this = this;
    this.$el.addEventListener("show.bs.modal", function() {
      $this.initUI();
    });
  },
  computed: {
    ...mapState({
      query: (state) => state.questions.query,
    }),
    ...mapGetters(["getQuestion", "getAccessToken"]),
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

.btn-group {
  width: 100%;
  padding: 0px;
  margin: 0px;
}

.question-create-btn1 {
  background-color: #658dc6;
  border-radius: 6px;
  color: white;
  cursor: pointer;
  margin: 0px;
  width: 430px;
  height: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.question-cancel-btn1 {
  background-color: white;
  border-radius: 6px;
  border-color: #658dc6;
  border-style: solid;
  color: #658dc6;
  cursor: pointer;
  border-width: 2px;
  margin: 0px;
  width: 430px;
  height: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.question-create-btn1:hover {
  color: white;
  background: #0f4c81;
  transition: 0.4s;
}

.question-cancel-btn1:hover {
  background: #b5c7d3;
  transition: 0.4s;
}

.question-header {
  background-color: #658dc6;
  display: flex;
  height: 180px;
  justify-content: center;
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
  padding: 0px;
  border-color: #cdd9ed;
  color: #84898c;
  font-size: 15px;
  padding-top: 2px;
}

.selectMainCategory {
  padding: 0px;
  border-color: #cdd9ed;
  color: #84898c;
  font-size: 15px;
  padding-top: 2px;
}

.selectSubCategory {
  padding: 0px;
  border-color: #cdd9ed;
  color: #84898c;
  font-size: 15px;
  padding-top: 2px;
}

.selectBox {
  width: 100%;
  margin: 0px;
  display: flex;
  justify-content: space-between;
}

.question-main .ck.ck-editor__editable {
  width: 896px;
  height: 600px;
  margin-bottom: 30px;
}

.titleText {
  color: #696c6e;
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

.btn-group {
  display: flex;
  justify-content: space-between;

  padding-bottom: 100px;
}
</style>
