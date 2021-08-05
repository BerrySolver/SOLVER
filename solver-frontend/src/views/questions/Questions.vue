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
          <div>
            <vs-collapse open-hover >
              <vs-collapse-item class="list-group-category" :not-arrow="true">
                <div slot="header" @click="getAllQuestionList">
                  전체
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
            <div>
              <span>{{request.curCategory}}</span>
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
          </div>
          <div class="question-list">
            <div 
              v-for="(question, idx) in questionList" 
              :key="idx"
              >
              {{question.title}}
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
          type: "all",
          mode: "releaseDesc",
        },
        questionList: [],
        difficultyOptions: [
          {text:'난이도 전체', value: null},
          {text:'난이도 상', value: 3},
          {text:'난이도 중', value: 2},
          {text:'난이도 하', value: 1},
        ]
      }
    },
    methods: {
      getAllQuestionList: function () {
        this.request.mainCategory = null
        this.request.subCategory = null
        this.request.query = null
        this.request.difficulty = null
        this.request.type = "all"
        this.request.mode = "releaseDesc"
        this.getQuestionList()
      },
      setMainCategory: function (mainCategoryName, code) {
        this.request.curCategory = `${mainCategoryName}`
        this.request.mainCategory = code
        this.request.subCategory = null
        this.request.query = null
        this.request.difficulty = null
        this.request.type = "all"
        this.request.mode = "releaseDesc"
        this.getQuestionList()
      },
      setSubCategory: function (mainCategoryName, subCategoryName, code) {
        this.request.curCategory = `${mainCategoryName} / ${subCategoryName}`
        this.request.mainCategory = null
        this.request.subCategory = code
        this.request.query = null
        this.request.difficulty = null
        this.request.type = "all"
        this.request.mode = "releaseDesc"
        this.getQuestionList()
      },
      setQuery: function () {
        this.request.difficulty = null
        this.request.type = "all"
        this.request.mode = "releaseDesc"
        this.getQuestionList()
      },
      setDifficulty: function () {
        this.request.type = "all"
        this.request.mode = "releaseDesc"
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
      }
    },
    created: function () {
      axios({
      url: API.URL + API.ROUTES.getCategory,
      method: "get",
      })
      .then((res) => {
        this.categories = res.data
        console.log(res.data)
      })
      .catch((err) => {
        console.log(err);
      });
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
    border-color: #275EFE;
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
    border-color: #275EFE;
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
    border: 1px solid gold;
    display: flex;
    margin-top: 20px;
    width: 1190px;
  }

  .question-category {
    border: 1px solid black;
    color: #84898C;
    text-align: left;
    width: 180px;
  }

  .question-list {
    border: 1px solid black;
  }

  .question-main{
    border: 1px solid red;
    margin-left: auto;
    width: 920px;
  }

  .question-query {
    border: 1px solid black;
    height: 200px;
  }

  .question-header {
    background-color: #658DC6;
    display: flex;
    height: 180px;
    justify-content: center;
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
