<template>
  <div>
    <div class="question-header">
      <div class="question-banner">
        <img src="@/assets/logo-white-2.png" alt="white-logo">
        <div class="question-banner-ment">
          <h6 class="ment">얼마든 물어보고 해답을 찾아가세요!</h6>
          <h6 class="ment">여러분의 공부하다 막힌 부분을 <span>사이다</span>처럼 해결해줄 능력자들이 기다리는 중이에요!</h6>
        </div>
      </div>
    </div>
    <div class="question-body">
      <div class="question-body-contents">
        <div class="question-category">
          <div><span class="info1">TODAY</span></div>
          <div style="display: flex; justify-content: space-between;">
            <span class="info2">질문: </span> 000개
          </div>
          <div style="display: flex; justify-content: space-between;">
            <span class="info2">답변: </span> 000개
          </div>
          <div class="question-category-items">
            <vs-collapse open-hover >
              <vs-collapse-item class="list-group-category" :not-arrow="true">
                <div slot="header" @click="getAllQuestionList">
                  <span>전체</span>
                </div>
              </vs-collapse-item>
              <vs-collapse-item class="list-group-category" :not-arrow="true"
                v-for="(mainCategory, idx) in categories" 
                :key="idx" 
                >
                <div slot="header" @click="setMainCategory(mainCategory.codeName, mainCategory.code)">
                  {{mainCategory.codeName}}
                </div>
                  <ul class="list-group list-group-flush">
                    <li class="list-group-item" 
                      v-for="(subCategory, idx2) in mainCategory.category" 
                      :key="idx2" 
                      @click="setSubCategory(mainCategory.codeName, subCategory.subCategoryName, subCategory.subCategoryCode)">
                      {{subCategory.subCategoryName}}
                    </li>
                  </ul>
              </vs-collapse-item>
            </vs-collapse>
          </div>
        </div>

        <div class="question-main">
          <div class="question-query">
            <div class="question-query-head">
              <span style="font-size: 20px; font-weight: 700;">{{request.curCategory}}</span>
              <div class="question-type">
                <span class="question-type-item" :style="{color: typeColor[0]}" @click="setType(0)">전체</span>
                <span style="margin: 0 30px 0 30px;">|</span>
                <span class="question-type-item" :style="{color: typeColor[1]}" @click="setType(1)">해결</span>
                <span style="margin: 0 30px 0 30px;">|</span>
                <span class="question-type-item" :style="{color: typeColor[2]}" @click="setType(2)">미해결</span>
              </div>
            </div>
            <div class="form-group">
                <input class="form-field" type="text" v-model="request.query" placeholder="궁금한 모든 것을 검색해보세요!" @keypress.enter="setQuery">
                <span @click="setQuery">검&nbsp;&nbsp;색</span>
            </div>
            <div class="selectDifficultyBox">
              <vs-select
                color='#0F4C81'
                class="selectDifficulty"
                v-model="request.difficulty"
                width=150px
                @change="setDifficulty"
                >
                <vs-select-item 
                  v-for="(item,index) in difficultyOptions"
                  :key="index" 
                  :value="item.value" 
                  :text="item.text"
                  style="font-size: 15px;"
                  />
              </vs-select>
            </div>
            <div class="question-mode">
              <span class="question-mode-item" :style="{color: modeColor[0]}" @click="setMode(0)">최신순</span>
              <span style="margin: 0 30px 0 30px;">|</span>
              <span class="question-mode-item" :style="{color: modeColor[1]}" @click="setMode(1)">답변순</span>
              <span style="margin: 0 30px 0 30px;">|</span>
              <span class="question-mode-item" :style="{color: modeColor[2]}" @click="setMode(2)">좋아요순</span>
            </div>
          </div>
          <div class="question-list">
            <div 
              class="question-list-item"
              v-for="(question, idx) in questionList" 
              :key="idx"
              >
              <div class="d-flex" style="margin-bottom: 10px;">
                <div style="background-color: #B5C7D3; border-radius: 6px; height: 27px; margin-top: 1px; width: 27px;">
                  <span style="color: white; font-size: 20px; font-weight: 700;">{{question.difficulty}}</span>
                </div>
                <div>
                  <span style="font-size: 20px; font-weight: 700; margin-left: 10px;">{{question.title}}</span>
                </div>
              </div>
              <div class="d-flex">
                <span style="font-size: 17px; margin-bottom: 30px;">{{question.content}}</span>
              </div>
              <div class="d-flex justify-content-between">
                <div class="d-flex">
                  <div style="padding-top: 2px;">{{question.nickname}}</div>
                  <div style=" border-radius: 6px;  margin-left: 25px; padding: 7px 5px 0 5px; font-size: 12px;">{{humanize(now, question.regDt)}}</div>
                </div>
                <div>
                  <span class="material-icons">comment</span>
                  <span class="count">{{question.answerCount}}</span>
                  <span class="material-icons">favorite_border</span>
                  <span class="count">{{question.likeCount}}</span>
                  <span class="material-icons">bookmark_border</span>
                  <span class="count">{{question.bookMarkCount}}</span>
                </div>
              </div>
            </div>
            <div 
              v-if="questionList.length === 0"
              class="question-list-item"
              >
              <span>저쪽 신사분께 첫 질문을 남겨보시겠어요?</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import API from '@/API.js'

export default {
    name: 'Questions',
    data() {
      return {
        categories: [],
        request: {
          curCategory: "전체",
          mainCategory: null,
          subCategory: null,
          query: null,
          difficulty: null,
          type: null,
          mode: "releaseDesc",
        },
        questionList: [],
        difficultyOptions: [
          {text:'난이도 전체', value: null},
          {text:'난이도 상', value: 3},
          {text:'난이도 중', value: 2},
          {text:'난이도 하', value: 1},
        ],
        typeColor: ["#0F4C81", "#89848C", "#89848C"],
        modeColor: ["#0F4C81", "#89848C", "#89848C"],
        now: new Date()
      }
    },
    methods: {
      getAllQuestionList: function () {
        this.request.mainCategory = null
        this.request.subCategory = null
        this.request.query = null
        this.request.difficulty = null
        this.request.type = null,
        this.request.mode = "releaseDesc"
        this.getQuestionList()
      },
      setMainCategory: function (mainCategoryName, code) {
        this.request.curCategory = `${mainCategoryName}`
        this.request.mainCategory = code
        this.request.subCategory = null
        this.request.query = null
        this.request.difficulty = null
        this.request.type = null,
        this.request.mode = "releaseDesc"
        this.getQuestionList()
      },
      setSubCategory: function (mainCategoryName, subCategoryName, code) {
        this.request.curCategory = `${mainCategoryName} / ${subCategoryName}`
        this.request.mainCategory = null
        this.request.subCategory = code
        this.request.query = null
        this.request.difficulty = null
        this.request.type = null,
        this.request.mode = "releaseDesc"
        this.getQuestionList()
      },
      setQuery: function () {
        this.request.difficulty = null
        this.request.type = null,
        this.request.mode = "releaseDesc"
        this.getQuestionList()
      },
      setDifficulty: function () {
        this.request.type = null,
        this.request.mode = "releaseDesc"
        this.getQuestionList()
      },
      setType: function (typeNum) {
        if (typeNum === 1) {
          this.request.type = "040"
        } else if (typeNum == 2) {
          this.request.type = "041"
        } else {
          this.request.type = null
        }
        this.request.mode = "releaseDesc"
        this.getQuestionList()
      },
      setMode: function (modeNum) {
        if (modeNum === 1) {
          this.request.mode = "answerDesc"
        } else if (modeNum == 2) {
          this.request.mode = "likeDesc"
        } else {
          this.request.mode = "releaseDesc"
        }
        this.getQuestionList()
      },
      getQuestionList: function() {
        axios({
          url: API.URL + API.ROUTES.getQuestionList,
          method: 'get',
          params: this.request,
        })
        .then((res) => {
          this.questionList = res.data.questionFormList
          console.log(this.questionList)
        })
        .catch((err) => {
          console.log(err.message)
        })
      },
        humanize: function (now, date) {
        const moment = require('moment')
        const dateData = new Date(date)
        let r = now - dateData
        if (parseInt(r) > 43200000) {
          r = moment(dateData).format('YY-MM-DD\u00A0\u00A0HH:MM')
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
    created: function () {
      axios({
      url: API.URL + API.ROUTES.getCategory,
      method: "get",
      })
      .then((res) => {
        this.categories = res.data
        this.getQuestionList()
      })
      .catch((err) => {
        console.log(err);
      });
    },
    computed: {
      typeWatch: function () {
        return this.request.type
      },
      modeWatch: function () {
        return this.request.mode
      }
    },
    watch: {
      typeWatch: function () {
        if (this.typeWatch == "040") {
          this.typeColor[0] = "#89848C"
          this.typeColor[1] = "#0F4C81"
          this.typeColor[2] = "#89848C"
        } else if (this.typeWatch == "041") {
          this.typeColor[0] = "#89848C"
          this.typeColor[1] = "#89848C"
          this.typeColor[2] = "#0F4C81"
        } else {
          this.typeColor[0] = "#0F4C81"
          this.typeColor[1] = "#89848C"
          this.typeColor[2] = "#89848C"
        }
      },
      modeWatch: function () {
        if (this.modeWatch == "answerDesc") {
          this.modeColor[0] = "#89848C"
          this.modeColor[1] = "#0F4C81"
          this.modeColor[2] = "#89848C"
        } else if (this.modeWatch == "likeDesc") {
          this.modeColor[0] = "#89848C"
          this.modeColor[1] = "#89848C"
          this.modeColor[2] = "#0F4C81"
        } else {
          this.modeColor[0] = "#0F4C81"
          this.modeColor[1] = "#89848C"
          this.modeColor[2] = "#89848C"
        }
      }
    }
}
</script>

<style>
  .form-field {
  display: block;
  width: 100%;
  padding: 8px 16px;
  line-height: 25px;
  font-size: 14px;
  font-weight: 500;
  font-family: inherit;
  border-radius: 6px;
  -webkit-appearance: none;
  color: #84898C;
  border: 1px solid #CDD9ED;
  background: #fff;
  transition: border 0.3s ease;
  }

  .form-field::-moz-placeholder {
    color: #CBD1DC;
  }

  .form-field:-ms-input-placeholder {
    color: #CBD1DC;
  }

  .form-field::placeholder {
    color: #CBD1DC;
  }

  .form-field:focus {
    outline: none;
    border-color: #658DC6;
  }

  .form-group {
    display: flex;
    float: left;
    width: 761px;
  }

  .form-group > span,
  .form-group .form-field {
    white-space: nowrap;
    display: block;
  }

  .form-group > span:not(:first-child):not(:last-child),
  .form-group .form-field:not(:first-child):not(:last-child) {
    border-radius: 0;
  }

  .form-group > span:first-child,
  .form-group .form-field:first-child {
    border-radius: 6px 0 0 6px;
  }

  .form-group > span:last-child,
  .form-group .form-field:last-child {
    border-radius: 0 6px 6px 0;
  }

  .form-group > span:not(:first-child),
  .form-group .form-field:not(:first-child) {
    margin-left: -1px;
  }

  .form-group .form-field {
    position: relative;
    z-index: 1;
    flex: 1 1 auto;
    width: 1%;
    margin-top: 0;
    margin-bottom: 0;
  }

  .form-group > span {
    text-align: center;
    padding: 8px 12px;
    font-size: 14px;
    line-height: 25px;
    width: 100px;
    color: #99A3BA;
    background: #EEF4FF;
    border: 1px solid #CDD9ED;
    transition: background 0.3s ease, border 0.3s ease, color 0.3s ease;
  }

  .form-group:focus-within > span {
    color: #fff;
    background: #678EFE;
    border-color: #658DC6;
  }

  .info1 {
    color: #0F4C81;
    font-weight: 700;
    font-size: 19px;
  }

  .info2 {
    color: #0F4C81;
    font-weight: 700;
    font-size: 15px;
  }

  .list-group-category:hover {
    color: #0F4C81;
    animation: scale-up .1s forwards;
  }

  .list-group-item {
    color: #84898C;
  }

  .list-group-item:hover {
    color: #0F4C81;
    animation: scale-up .1s forwards;
  }

  @keyframes scale-up {
    0% {
      font-size: 100%;
    }
    100% {
      font-size: 120%;
    }
  }

  .ment {
    color: white;
    vertical-align:middle;
  }

  .ment span {
    color: #0F4C81;
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
    color: #84898C;
    margin-bottom: 100px;
    text-align: left;
    width: 180px;
  }

  .question-category-items {
    border: 1px solid #B5C7D3;
    margin-top: 20px;
  }

  .question-header {
    background-color: #658DC6;
    display: flex;
    height: 180px;
    justify-content: center;
  }

  .question-list {
    border-bottom: 1px solid #B5C7D3;
    margin-bottom: 100px;
  }

  .question-list-item {
    border-top: 1px solid #B5C7D3;
    padding: 15px 15px 15px 15px;
  }

  .question-list-item .material-icons {
    font-size: 21px;
    margin: 0 6px 0 10px;
  }

  .question-list-item .count {
    font-size: 16px;
  }

  .question-main{
    margin-left: auto;
    width: 920px;
  }

  .question-mode {
    color: #84898C;
    float: left;
    font-weight: 600;
    margin-top: 40px;
    padding-top: 5px;
  }

  .question-mode-item {
    cursor: pointer;
  }

  .question-query {
    height: 160px;
  }

  .question-query-head {
    display: flex;
    justify-content: space-between;
    margin-bottom: 10px;
  }

  .question-type {
    color: #84898C;
    font-weight: 600;
    padding-top: 5px;
  }

  .question-type-item {
    cursor: pointer;
  }

  .selectDifficulty {
    border-color: #CDD9ED;
    color: #84898C;
    font-size: 15px;
    padding-top: 2px;
  }

  .selectDifficultyBox {
    float: left;
    margin-left: 5px;
  }
</style>
