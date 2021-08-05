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
            <span class="info2">질문: </span> 1,326개
          </div>
          <div style="display: flex; justify-content: space-between;">
            <span class="info2">답변: </span> 2,415개
          </div>
          <div>
            <vs-collapse open-hover >
              <vs-collapse-item not-arrow=true>
                <div slot="header">
                  Collapse item
                </div>
                초중고
              </vs-collapse-item>
              <vs-collapse-item not-arrow=true>
                <div slot="header">
                  Collapse item 2
                </div>
                프로그래밍
              </vs-collapse-item>
              <vs-collapse-item not-arrow=true>
                <div slot="header">
                  Collapse item 3
                </div>
                ㅇㅇㅇ
              </vs-collapse-item>
              <vs-collapse-item not-arrow=true>
                <div slot="header">
                  Collapse item 4
                </div>
                ㅇㅇㅇㅇ
              </vs-collapse-item>
              </vs-collapse>
          </div>
        </div>

        <div class="question-main">
          <div class="question-query">

          </div>
          <div class="question-list">

          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
    name: 'Questions',
    data: function() {
      return {

        mainCategories: [{"초중고": "090", "인문": "091"}],
        subCategories: [[{"국어": "910", "수학": "911"}], [{"문학": "920"}]]
      }
    },
    methods: {

    },
    created: function () {

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
