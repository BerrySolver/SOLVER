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
                <div slot="header" @click="setMainCategory(mainCategory.code)">
                  {{mainCategory.codeName}}
                </div>
                  <ul class="list-group list-group-flush">
                    <li class="list-group-item" 
                      v-for="(subCategory, idx2) in mainCategory.category" 
                      :key="idx2" 
                      @click="setSubCategory(subCategory.subCategoryCode)">
                      {{subCategory.subCategoryName}}
                    </li>
                  </ul>
              </vs-collapse-item>
            </vs-collapse>
          </div>
        </div>

        <div class="question-main">
          <div class="question-query">

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
          mainCategory: null,
          subCategory: null,
          query: null,
          difficulty: null,
          type: "all",
          mode: "releaseDesc",
        },
        questionList: []
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
      setMainCategory: function (code) {
        this.request.mainCategory = code
        this.request.subCategory = null
        this.request.query = null
        this.request.difficulty = null
        this.request.type = "all"
        this.request.mode = "releaseDesc"
        this.getQuestionList()
      },
      setSubCategory: function (code) {
        this.request.mainCategory = null
        this.request.subCategory = code
        this.request.query = null
        this.request.difficulty = null
        this.request.type = "all"
        this.request.mode = "releaseDesc"
        this.getQuestionList()
      },
      getQuestionList: function() {
        console.log('실행')
        axios({
          url: API.URL + API.ROUTES.getQuestionList,
          method: 'get',
          params: this.request,
        })
        .then((res) => {
          console.log('가져옴')
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
    height: 200px;
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

</style>
