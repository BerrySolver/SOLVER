<template>
  <div>
    <div class="question-header font-break">
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
        <div class="question-category font-break">
          <div><span class="info1">TODAY</span></div>
          <div style="display: flex; justify-content: space-between;">
            <span class="info2">질문: </span><span><span id="todayQuestions"></span>개</span>
          </div>
          <div style="display: flex; justify-content: space-between;">
            <span class="info2">답변: </span><span><span id="todayAnswers"></span>개</span>
          </div>
          <div class="question-category-items">
            <vs-collapse open-hover>
              <vs-collapse-item class="list-group-category" :not-arrow="true">
                <div slot="header" @click="getAllQuestionList">
                  <span>전체</span>
                </div>
              </vs-collapse-item>
              <vs-collapse-item
                class="list-group-category"
                :not-arrow="true"
                v-for="(mainCategory, idx) in categories"
                :key="idx"
              >
                <div
                  slot="header"
                  @click="setMainCategory(mainCategory.codeName, mainCategory.code)"
                >
                  {{ mainCategory.codeName }}
                </div>
                <ul class="list-group list-group-flush">
                  <li
                    class="list-group-item"
                    v-for="(subCategory, idx2) in mainCategory.category"
                    :key="idx2"
                    @click="
                      setSubCategory(
                        mainCategory.codeName,
                        subCategory.subCategoryName,
                        subCategory.subCategoryCode
                      )
                    "
                  >
                    {{ subCategory.subCategoryName }}
                  </li>
                </ul>
              </vs-collapse-item>
            </vs-collapse>
          </div>
        </div>

        <div class="question-main">
          <div class="question-query">
            <div class="question-query-head">
              <span style="font-size: 20px; font-weight: 700;">{{ request.curCategory }}</span>
              <div class="question-type font-break">
                <span
                  class="question-type-item"
                  :style="{ color: typeColor[0] }"
                  @click="setType(0)"
                  >전체</span
                >
                <span style="margin: 0 30px 0 30px;">|</span>
                <span
                  class="question-type-item"
                  :style="{ color: typeColor[1] }"
                  @click="setType(1)"
                  >해결</span
                >
                <span style="margin: 0 30px 0 30px;">|</span>
                <span
                  class="question-type-item"
                  :style="{ color: typeColor[2] }"
                  @click="setType(2)"
                  >미해결</span
                >
              </div>
            </div>
            <div class="form-group">
              <input
                class="form-field"
                type="text"
                v-model="request.query"
                placeholder="궁금한 모든 것을 검색해보세요!"
                @keypress.enter="setQuery"
              />
              <span style="cursor: pointer;" @click="setQuery">검&nbsp;&nbsp;색</span>
            </div>
            <div class="selectDifficultyBox font-break">
              <vs-select
                color="#0F4C81"
                class="selectDifficulty"
                v-model="request.difficulty"
                icon-pack=false
                width="150px"
                @change="setDifficulty"
              >
                <vs-select-item
                  v-for="(item, index) in difficultyOptions"
                  :key="index"
                  :value="item.value"
                  :text="item.text"
                  style="font-size: 15px;"
                />
              </vs-select>
            </div>
            <div class="question-mode font-break">
              <span class="question-mode-item" :style="{ color: modeColor[0] }" @click="setMode(0)"
                >최신순</span
              >
              <span style="margin: 0 30px 0 30px;">|</span>
              <span class="question-mode-item" :style="{ color: modeColor[1] }" @click="setMode(1)"
                >조회순</span
              >
              <span style="margin: 0 30px 0 30px;">|</span>
              <span class="question-mode-item" :style="{ color: modeColor[2] }" @click="setMode(2)"
                >좋아요순</span
              >
            </div>
            <div class="question-create-btn" @click="goQuestionCreate">
              글쓰기
            </div>
          </div>
          <div class="question-list">
            <div
              class="question-list-item"
              v-for="(question, idx) in questionList"
              :key="idx"
              @click="goQuestionDetail(question.questionId)"
            >
              <div class="d-flex" style="margin-bottom: 10px;">
                <div
                  style="background-color: #B5C7D3; border-radius: 6px; height: 27px; margin-top: 1px; width: 27px; padding-top:2px;"
                >
                  <span v-if="question.difficulty == 1" style="color: white; font-size: 17px; font-weight: 700;">하</span>
                  <span v-else-if="question.difficulty == 2" style="color: white; font-size: 17px; font-weight: 700;">중</span>
                  <span v-else style="color: white; font-size: 17px; font-weight: 700;">상</span>
                </div>
                <div style="display: flex;">
                  <span
                    class="question-list-item-title"
                    style="font-size: 20px; font-weight: 700; margin-left: 10px;"
                    >{{ question.title }}
                  </span>
                  <img 
                    v-if="question.isImage" 
                    style="width:15px; margin-left:5px;" 
                    src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iaXNvLTg4NTktMSI/Pg0KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDE5LjAuMCwgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPg0KPHN2ZyB2ZXJzaW9uPSIxLjEiIGlkPSJMYXllcl8xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB4PSIwcHgiIHk9IjBweCINCgkgdmlld0JveD0iMCAwIDUxMiA1MTIiIHN0eWxlPSJlbmFibGUtYmFja2dyb3VuZDpuZXcgMCAwIDUxMiA1MTI7IiB4bWw6c3BhY2U9InByZXNlcnZlIj4NCjxnPg0KCTxnPg0KCQk8cGF0aCBkPSJNNDk1LjMwNCw2MS4yMTdIMTYuNjk2QzcuNDc1LDYxLjIxNywwLDY4LjY5MywwLDc3LjkxM3YzNTYuMTc0YzAsOS4yMiw3LjQ3NSwxNi42OTYsMTYuNjk2LDE2LjY5Nmg0NzguNjA5DQoJCQljOS4yMiwwLDE2LjY5Ni03LjQ3NSwxNi42OTYtMTYuNjk2Vjc3LjkxM0M1MTIsNjguNjkzLDUwNC41MjUsNjEuMjE3LDQ5NS4zMDQsNjEuMjE3eiBNNDc4LjYwOSw0MTcuMzkxSDMzLjM5MVYzNzAuNDMNCgkJCWw5My4yNjItOTguODY5bDc3Ljk1Niw4Mi42NDZjNi41OSw2Ljk4NSwxNy43MDYsNi45NzksMjQuMjksMEwzODUuNDM2LDE4OC4yNmw5My4xNzMsOTguNzY5VjQxNy4zOTF6IE00NzguNjA5LDIzOC4zNjgNCgkJCWwtODEuMDI5LTg1Ljg5NmMtNi41OS02Ljk4NS0xNy43MDUtNi45NzktMjQuMjksMEwyMTYuNzU1LDMxOC40MThsLTc3Ljk1Ni04Mi42NDZjLTYuNTktNi45ODUtMTcuNzA2LTYuOTc5LTI0LjI5LDANCgkJCWwtODEuMTE3LDg1Ljk5NFY5NC42MDloNDQ1LjIxN1YyMzguMzY4eiIvPg0KCTwvZz4NCjwvZz4NCjxnPg0KCTxnPg0KCQk8cGF0aCBkPSJNMjE2Ljc1NCwxMzMuODEyYy0yNy43MjEsMC01MC4yNzUsMjIuNTU0LTUwLjI3NSw1MC4yNzRjMCwyNy43MjEsMjIuNTU0LDUwLjI3NSw1MC4yNzUsNTAuMjc1DQoJCQljMjcuNzIxLDAsNTAuMjc0LTIyLjU1NCw1MC4yNzQtNTAuMjc1QzI2Ny4wMjgsMTU2LjM2NiwyNDQuNDc0LDEzMy44MTIsMjE2Ljc1NCwxMzMuODEyeiBNMjE2Ljc1NCwyMDAuOTcNCgkJCWMtOS4zMSwwLTE2Ljg4NC03LjU3NC0xNi44ODQtMTYuODg0YzAtOS4zMDksNy41NzQtMTYuODgzLDE2Ljg4NC0xNi44ODNzMTYuODgzLDcuNTc0LDE2Ljg4MywxNi44ODMNCgkJCUMyMzMuNjM3LDE5My4zOTYsMjI2LjA2MiwyMDAuOTcsMjE2Ljc1NCwyMDAuOTd6Ii8+DQoJPC9nPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPC9zdmc+DQo=" 
                  />
                  <img 
                    v-if="question.isVideo" 
                    style="width:15px; margin-left:5px;" 
                    src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iaXNvLTg4NTktMSI/Pg0KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDE5LjAuMCwgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPg0KPHN2ZyB2ZXJzaW9uPSIxLjEiIGlkPSJMYXllcl8xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB4PSIwcHgiIHk9IjBweCINCgkgdmlld0JveD0iMCAwIDQ2MS41MDEgNDYxLjUwMSIgc3R5bGU9ImVuYWJsZS1iYWNrZ3JvdW5kOm5ldyAwIDAgNDYxLjUwMSA0NjEuNTAxOyIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSI+DQo8Zz4NCgk8Zz4NCgkJPGc+DQoJCQk8cGF0aCBkPSJNNDI4LjYzNiw0Mi4yNUgzMi44NjVDMTQuNzQzLDQyLjI1LDAsNTYuOTkzLDAsNzUuMTE1djMxMS4yNzFjMCwxOC4xMjIsMTQuNzQzLDMyLjg2NSwzMi44NjUsMzIuODY1aDM5NS43NzENCgkJCQljMTguMTIyLDAsMzIuODY1LTE0Ljc0MywzMi44NjUtMzIuODY1Vjc1LjExNUM0NjEuNSw1Ni45OTMsNDQ2Ljc1Nyw0Mi4yNSw0MjguNjM2LDQyLjI1eiBNNDMxLjUwMSwzODYuMzg1TDQzMS41MDEsMzg2LjM4NQ0KCQkJCWMtMC4wMDEsMS41OC0xLjI4NiwyLjg2NS0yLjg2NiwyLjg2NUgzMi44NjVjLTEuNTgsMC0yLjg2NS0xLjI4NS0yLjg2NS0yLjg2NVY3NS4xMTVjMC0xLjU4LDEuMjg1LTIuODY1LDIuODY1LTIuODY1aDM5NS43NzENCgkJCQljMS41OCwwLDIuODY1LDEuMjg1LDIuODY1LDIuODY1VjM4Ni4zODV6Ii8+DQoJCQk8cGF0aCBkPSJNMjk2Ljc2NywyMTguNDY5bC0xMTQuODA5LTgwLjUxMWMtNC41ODEtMy4yMTMtMTAuNTctMy42MDktMTUuNTM0LTEuMDI2Yy00Ljk2NCwyLjU4Mi04LjA3OSw3LjcxMi04LjA3OSwxMy4zMDh2MTYxLjAyMw0KCQkJCWMwLDUuNTk1LDMuMTE0LDEwLjcyNiw4LjA3OSwxMy4zMDhjNC45OTEsMi41OTYsMTAuOTc3LDIuMTY5LDE1LjUzNC0xLjAyNmwxMTQuODA5LTgwLjUxMmM0LjAwNC0yLjgwOCw2LjM4OC03LjM5MSw2LjM4OC0xMi4yODENCgkJCQlTMzAwLjc3MSwyMjEuMjc3LDI5Ni43NjcsMjE4LjQ2OXogTTE4OC4zNDYsMjgyLjQyMlYxNzkuMDc5bDczLjY4NCw1MS42NzFMMTg4LjM0NiwyODIuNDIyeiIvPg0KCQk8L2c+DQoJPC9nPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPC9zdmc+DQo=" 
                  />
                </div>
              </div>
              <div class="d-flex">
                <span
                  id="question-content"
                  class="question-list-item-content"
                  style="font-size: 17px; margin-bottom: 30px;"
                  v-html="question.content"
                ></span>
              </div>
              <div class="d-flex justify-content-between">
                <div class="d-flex font-break">
                  <div style="padding-top: 2px;">{{ question.nickname }}</div>
                  <div
                    style=" border-radius: 6px;  margin-left: 25px; padding: 7px 5px 0 5px; font-size: 12px;"
                  >
                    {{ humanize(now, question.regDt) }}
                  </div>
                </div>
                <div>
                  <span class="material-icons"
                    ><img
                      style="width:15px;"
                      src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iaXNvLTg4NTktMSI/Pg0KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDE5LjAuMCwgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPg0KPHN2ZyB2ZXJzaW9uPSIxLjEiIGlkPSJDYXBhXzEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4Ig0KCSB2aWV3Qm94PSIwIDAgNTExLjk5OSA1MTEuOTk5IiBzdHlsZT0iZW5hYmxlLWJhY2tncm91bmQ6bmV3IDAgMCA1MTEuOTk5IDUxMS45OTk7IiB4bWw6c3BhY2U9InByZXNlcnZlIj4NCjxnPg0KCTxnPg0KCQk8cGF0aCBkPSJNNTA4Ljc0NSwyNDYuMDQxYy00LjU3NC02LjI1Ny0xMTMuNTU3LTE1My4yMDYtMjUyLjc0OC0xNTMuMjA2UzcuODE4LDIzOS43ODQsMy4yNDksMjQ2LjAzNQ0KCQkJYy00LjMzMiw1LjkzNi00LjMzMiwxMy45ODcsMCwxOS45MjNjNC41NjksNi4yNTcsMTEzLjU1NywxNTMuMjA2LDI1Mi43NDgsMTUzLjIwNnMyNDguMTc0LTE0Ni45NSwyNTIuNzQ4LTE1My4yMDENCgkJCUM1MTMuMDgzLDI2MC4wMjgsNTEzLjA4MywyNTEuOTcxLDUwOC43NDUsMjQ2LjA0MXogTTI1NS45OTcsMzg1LjQwNmMtMTAyLjUyOSwwLTE5MS4zMy05Ny41MzMtMjE3LjYxNy0xMjkuNDE4DQoJCQljMjYuMjUzLTMxLjkxMywxMTQuODY4LTEyOS4zOTUsMjE3LjYxNy0xMjkuMzk1YzEwMi41MjQsMCwxOTEuMzE5LDk3LjUxNiwyMTcuNjE3LDEyOS40MTgNCgkJCUM0NDcuMzYxLDI4Ny45MjMsMzU4Ljc0NiwzODUuNDA2LDI1NS45OTcsMzg1LjQwNnoiLz4NCgk8L2c+DQo8L2c+DQo8Zz4NCgk8Zz4NCgkJPHBhdGggZD0iTTI1NS45OTcsMTU0LjcyNWMtNTUuODQyLDAtMTAxLjI3NSw0NS40MzMtMTAxLjI3NSwxMDEuMjc1czQ1LjQzMywxMDEuMjc1LDEwMS4yNzUsMTAxLjI3NQ0KCQkJczEwMS4yNzUtNDUuNDMzLDEwMS4yNzUtMTAxLjI3NVMzMTEuODM5LDE1NC43MjUsMjU1Ljk5NywxNTQuNzI1eiBNMjU1Ljk5NywzMjMuNTE2Yy0zNy4yMywwLTY3LjUxNi0zMC4yODctNjcuNTE2LTY3LjUxNg0KCQkJczMwLjI4Ny02Ny41MTYsNjcuNTE2LTY3LjUxNnM2Ny41MTYsMzAuMjg3LDY3LjUxNiw2Ny41MTZTMjkzLjIyNywzMjMuNTE2LDI1NS45OTcsMzIzLjUxNnoiLz4NCgk8L2c+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8L3N2Zz4NCg==" />
                  </span>
                  <span class="count">{{ question.readCount }}</span>
                  <span class="material-icons"
                    ><img
                      style="width:15px;"
                      src="data:image/svg+xml;base64,PHN2ZyBpZD0iQ2FwYV8xIiBlbmFibGUtYmFja2dyb3VuZD0ibmV3IDAgMCA1MTEuMDcyIDUxMS4wNzIiIGhlaWdodD0iNTEyIiB2aWV3Qm94PSIwIDAgNTExLjA3MiA1MTEuMDcyIiB3aWR0aD0iNTEyIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPjxnIGlkPSJTcGVlY2hfQnViYmxlXzQ4XyI+PGc+PHBhdGggZD0ibTc0LjM5IDQ4MC41MzZoLTM2LjIxM2wyNS42MDctMjUuNjA3YzEzLjgwNy0xMy44MDcgMjIuNDI5LTMxLjc2NSAyNC43NDctNTEuMjQ2LTM2LjAyOS0yMy42NDQtNjIuMzc1LTU0Ljc1MS03Ni40NzgtOTAuNDI1LTE0LjA5My0zNS42NDctMTUuODY0LTc0Ljg4OC01LjEyMS0xMTMuNDgyIDEyLjg5LTQ2LjMwOSA0My4xMjMtODguNTE4IDg1LjEyOC0xMTguODUzIDQ1LjY0Ni0zMi45NjMgMTAyLjQ3LTUwLjM4NyAxNjQuMzMtNTAuMzg3IDc3LjkyNyAwIDE0My42MTEgMjIuMzg5IDE4OS45NDggNjQuNzQ1IDQxLjc0NCAzOC4xNTkgNjQuNzM0IDg5LjYzIDY0LjczNCAxNDQuOTMzIDAgMjYuODY4LTUuNDcxIDUzLjAxMS0xNi4yNiA3Ny43MDMtMTEuMTY1IDI1LjU1MS0yNy41MTQgNDguMzAyLTQ4LjU5MyA2Ny42MTktNDYuMzk5IDQyLjUyMy0xMTIuMDQyIDY1LTE4OS44MyA2NS0yOC44NzcgMC01OS4wMS0zLjg1NS04NS45MTMtMTAuOTI5LTI1LjQ2NSAyNi4xMjMtNTkuOTcyIDQwLjkyOS05Ni4wODYgNDAuOTI5em0xODItNDIwYy0xMjQuMDM5IDAtMjAwLjE1IDczLjk3My0yMjAuNTU3IDE0Ny4yODUtMTkuMjg0IDY5LjI4IDkuMTQzIDEzNC43NDMgNzYuMDQzIDE3NS4xMTVsNy40NzUgNC41MTEtLjIzIDguNzI3Yy0uNDU2IDE3LjI3NC00LjU3NCAzMy45MTItMTEuOTQ1IDQ4Ljk1MiAxNy45NDktNi4wNzMgMzQuMjM2LTE3LjA4MyA0Ni45OS0zMi4xNTFsNi4zNDItNy40OTMgOS40MDUgMi44MTNjMjYuMzkzIDcuODk0IDU3LjEwNCAxMi4yNDEgODYuNDc3IDEyLjI0MSAxNTQuMzcyIDAgMjI0LjY4Mi05My40NzMgMjI0LjY4Mi0xODAuMzIyIDAtNDYuNzc2LTE5LjUyNC05MC4zODQtNTQuOTc2LTEyMi43OS00MC43MTMtMzcuMjE2LTk5LjM5Ny01Ni44ODgtMTY5LjcwNi01Ni44ODh6Ii8+PC9nPjwvZz48L3N2Zz4="
                  /></span>
                  <span class="count">{{ question.answerCount }}</span>
                  <span class="material-icons"
                    ><img
                      style="width:15px;"
                      src="data:image/svg+xml;base64,PHN2ZyB2aWV3Qm94PSIwIC0yOCA1MTIuMDAxIDUxMiIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj48cGF0aCBkPSJtMjU2IDQ1NS41MTU2MjVjLTcuMjg5MDYyIDAtMTQuMzE2NDA2LTIuNjQwNjI1LTE5Ljc5Mjk2OS03LjQzNzUtMjAuNjgzNTkzLTE4LjA4NTkzNy00MC42MjUtMzUuMDgyMDMxLTU4LjIxODc1LTUwLjA3NDIxOWwtLjA4OTg0My0uMDc4MTI1Yy01MS41ODIwMzItNDMuOTU3MDMxLTk2LjEyNS04MS45MTc5NjktMTI3LjExNzE4OC0xMTkuMzEyNS0zNC42NDQ1MzEtNDEuODA0Njg3LTUwLjc4MTI1LTgxLjQ0MTQwNi01MC43ODEyNS0xMjQuNzQyMTg3IDAtNDIuMDcwMzEzIDE0LjQyNTc4MS04MC44ODI4MTMgNDAuNjE3MTg4LTEwOS4yOTI5NjkgMjYuNTAzOTA2LTI4Ljc0NjA5NCA2Mi44NzEwOTMtNDQuNTc4MTI1IDEwMi40MTQwNjItNDQuNTc4MTI1IDI5LjU1NDY4OCAwIDU2LjYyMTA5NCA5LjM0Mzc1IDgwLjQ0NTMxMiAyNy43Njk1MzEgMTIuMDIzNDM4IDkuMzAwNzgxIDIyLjkyMTg3NiAyMC42ODM1OTQgMzIuNTIzNDM4IDMzLjk2MDkzOCA5LjYwNTQ2OS0xMy4yNzczNDQgMjAuNS0yNC42NjAxNTcgMzIuNTI3MzQ0LTMzLjk2MDkzOCAyMy44MjQyMTgtMTguNDI1NzgxIDUwLjg5MDYyNS0yNy43Njk1MzEgODAuNDQ1MzEyLTI3Ljc2OTUzMSAzOS41MzkwNjMgMCA3NS45MTAxNTYgMTUuODMyMDMxIDEwMi40MTQwNjMgNDQuNTc4MTI1IDI2LjE5MTQwNiAyOC40MTAxNTYgNDAuNjEzMjgxIDY3LjIyMjY1NiA0MC42MTMyODEgMTA5LjI5Mjk2OSAwIDQzLjMwMDc4MS0xNi4xMzI4MTIgODIuOTM3NS01MC43NzczNDQgMTI0LjczODI4MS0zMC45OTIxODcgMzcuMzk4NDM3LTc1LjUzMTI1IDc1LjM1NTQ2OS0xMjcuMTA1NDY4IDExOS4zMDg1OTQtMTcuNjI1IDE1LjAxNTYyNS0zNy41OTc2NTcgMzIuMDM5MDYyLTU4LjMyODEyNiA1MC4xNjc5NjktNS40NzI2NTYgNC43ODkwNjItMTIuNTAzOTA2IDcuNDI5Njg3LTE5Ljc4OTA2MiA3LjQyOTY4N3ptLTExMi45Njg3NS00MjUuNTIzNDM3Yy0zMS4wNjY0MDYgMC01OS42MDU0NjkgMTIuMzk4NDM3LTgwLjM2NzE4OCAzNC45MTQwNjItMjEuMDcwMzEyIDIyLjg1NTQ2OS0zMi42NzU3ODEgNTQuNDQ5MjE5LTMyLjY3NTc4MSA4OC45NjQ4NDQgMCAzNi40MTc5NjggMTMuNTM1MTU3IDY4Ljk4ODI4MSA0My44ODI4MTMgMTA1LjYwNTQ2OCAyOS4zMzIwMzEgMzUuMzk0NTMyIDcyLjk2MDkzNyA3Mi41NzQyMTkgMTIzLjQ3NjU2MiAxMTUuNjI1bC4wOTM3NS4wNzgxMjZjMTcuNjYwMTU2IDE1LjA1MDc4MSAzNy42Nzk2ODggMzIuMTEzMjgxIDU4LjUxNTYyNSA1MC4zMzIwMzEgMjAuOTYwOTM4LTE4LjI1MzkwNyA0MS4wMTE3MTktMzUuMzQzNzUgNTguNzA3MDMxLTUwLjQxNzk2OSA1MC41MTE3MTktNDMuMDUwNzgxIDk0LjEzNjcxOS04MC4yMjI2NTYgMTIzLjQ2ODc1LTExNS42MTcxODggMzAuMzQzNzUtMzYuNjE3MTg3IDQzLjg3ODkwNy02OS4xODc1IDQzLjg3ODkwNy0xMDUuNjA1NDY4IDAtMzQuNTE1NjI1LTExLjYwNTQ2OS02Ni4xMDkzNzUtMzIuNjc1NzgxLTg4Ljk2NDg0NC0yMC43NTc4MTMtMjIuNTE1NjI1LTQ5LjMwMDc4Mi0zNC45MTQwNjItODAuMzYzMjgyLTM0LjkxNDA2Mi0yMi43NTc4MTIgMC00My42NTIzNDQgNy4yMzQzNzQtNjIuMTAxNTYyIDIxLjUtMTYuNDQxNDA2IDEyLjcxODc1LTI3Ljg5NDUzMiAyOC43OTY4NzQtMzQuNjA5Mzc1IDQwLjA0Njg3NC0zLjQ1MzEyNSA1Ljc4NTE1Ny05LjUzMTI1IDkuMjM4MjgyLTE2LjI2MTcxOSA5LjIzODI4MnMtMTIuODA4NTk0LTMuNDUzMTI1LTE2LjI2MTcxOS05LjIzODI4MmMtNi43MTA5MzctMTEuMjUtMTguMTY0MDYyLTI3LjMyODEyNC0zNC42MDkzNzUtNDAuMDQ2ODc0LTE4LjQ0OTIxOC0xNC4yNjU2MjYtMzkuMzQzNzUtMjEuNS02Mi4wOTc2NTYtMjEuNXptMCAwIi8+PC9zdmc+"
                  /></span>
                  <span class="count">{{ question.likeCount }}</span>
                  <span class="material-icons"
                    ><img
                      style="width:15px;"
                      src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iaXNvLTg4NTktMSI/Pg0KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDE5LjAuMCwgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPg0KPHN2ZyB2ZXJzaW9uPSIxLjEiIGlkPSJDYXBhXzEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4Ig0KCSB2aWV3Qm94PSIwIDAgNTEyIDUxMiIgc3R5bGU9ImVuYWJsZS1iYWNrZ3JvdW5kOm5ldyAwIDAgNTEyIDUxMjsiIHhtbDpzcGFjZT0icHJlc2VydmUiPg0KPGc+DQoJPGc+DQoJCTxwYXRoIGQ9Ik00MTYuNjY3LDBIOTUuMzMzYy04LjI4NCwwLTE1LDYuNzE2LTE1LDE1djQ4MmMwLDYuMDY3LDMuNjU1LDExLjUzNiw5LjI2LDEzLjg1OGMxLjg1NiwwLjc2OSwzLjgwNSwxLjE0Miw1LjczNywxLjE0Mg0KCQkJYzMuOTA0LDAsNy43NC0xLjUyMywxMC42MS00LjM5NGwxNTAuMDYzLTE1MC4wNjFMNDA2LjA2LDUwNy42MDZjNC4yOSw0LjI5LDEwLjc0Miw1LjU3MywxNi4zNDcsMy4yNTINCgkJCWM1LjYwNS0yLjMyMiw5LjI2LTcuNzkxLDkuMjYtMTMuODU4VjE1QzQzMS42NjcsNi43MTYsNDI0Ljk1MSwwLDQxNi42NjcsMHogTTI1Ni4wMDIsMzIxLjMzMmMtMy45NzgsMC03Ljc5MywxLjU4LTEwLjYwNiw0LjM5NA0KCQkJTDExMC4zMzMsNDYwLjc4N1YzMGgyOTEuMzMzdjQzMC43ODVMMjY2LjYwOSwzMjUuNzI2QzI2My43OTYsMzIyLjkxMiwyNTkuOTgxLDMyMS4zMzIsMjU2LjAwMiwzMjEuMzMyeiIvPg0KCTwvZz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjwvc3ZnPg0K"
                  /></span>
                  <span class="count">{{ question.bookMarkCount }}</span>
                </div>
              </div>
            </div>
            <div v-if="questionList.length === 0 && isLoaded" class="question-list-item">
              <span>저쪽 신사분께 첫 질문을 남겨보시겠어요?</span>
            </div>
          </div>
          <div>
            <nav aria-label="Page navigation" class="page-nav">
              <ul class="pagination justify-content-center">
                <li v-if="prev" class="page-item">
                  <a
                    class="page-link"
                    aria-label="Previous"
                    @click="paginationChanged(startPageIndex - 1)"
                  >
                    <span aria-hidden="true">«</span>
                  </a>
                </li>
                <li
                  v-for="index in endPageIndex - startPageIndex + 1"
                  :key="index"
                  v-bind:class="{
                    active: startPageIndex + index - 1 == pageIndex(),
                  }"
                  class="page-item"
                >
                  <a @click="paginationChanged(startPageIndex + index - 1)" class="page-link">{{
                    startPageIndex + index - 1
                  }}</a>
                  <!-- href 는 그대로, 커서 모양 유지-->
                </li>
                <li v-if="next" class="page-item">
                  <a
                    class="page-link"
                    aria-label="Next"
                    @click="paginationChanged(endPageIndex + 1)"
                  >
                    <span aria-hidden="true">»</span>
                  </a>
                </li>
              </ul>
            </nav>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import API from "@/API.js";
import { mapState, mapGetters, mapActions } from "vuex";
import LoginModal from "@/components/main/LoginModal"

function numberCounter(target_frame, target_number) {
  this.count = 0; this.diff = 0;
  this.target_count = parseInt(target_number);
  this.target_frame = document.getElementById(target_frame);
  this.timer = null;
  this.counter();
}

numberCounter.prototype.counter = function() {
  var self = this;
  this.diff = this.target_count - this.count;
  if(this.diff > 0) {
      self.count += Math.ceil(this.diff / 5);
  }
  
  this.target_frame.innerHTML = this.count.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
  if(this.count < this.target_count) {
      this.timer = setTimeout(function() { self.counter(); }, 40);
  } else {
      clearTimeout(this.timer);
  }
}

export default {
  name: "Questions",
  components: {},
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
        offset: 0,
        limit: 10,
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
      listRowCount: 10,
      pageLinkCount: 10,
      currentPageIndex: 1,
      totalListItemCount: 10,
      isLoaded: false,
      todayQuestions: "",
      todayAnswers: ""
    };
  },
  methods: {
    ...mapActions(["setStateQuery", "goQuestionDetail", "setCurrentPage"]),
    getAllQuestionList: function() {
      this.request.curCategory = "전체";
      this.request.mainCategory = null;
      this.request.subCategory = null;
      this.request.query = null;
      this.request.difficulty = null;
      (this.request.type = null), (this.request.mode = "releaseDesc");
      this.getQuestionList();
    },
    setMainCategory: function(mainCategoryName, code) {
      this.request.curCategory = `${mainCategoryName}`;
      this.request.mainCategory = code;
      this.request.subCategory = null;
      this.request.query = null;
      this.request.difficulty = null;
      (this.request.type = null), (this.request.mode = "releaseDesc");
      this.request.offset = 0;
      this.currentPageIndex = 1;
      this.getQuestionList();
    },
    setSubCategory: function(mainCategoryName, subCategoryName, code) {
      this.request.curCategory = `${mainCategoryName} / ${subCategoryName}`;
      this.request.mainCategory = null;
      this.request.subCategory = code;
      this.request.query = null;
      this.request.difficulty = null;
      (this.request.type = null), (this.request.mode = "releaseDesc");
      this.request.offset = 0;
      this.currentPageIndex = 1;
      this.getQuestionList();
    },
    setQuery: function() {
      this.request.difficulty = null;
      (this.request.type = null), (this.request.mode = "releaseDesc");
      this.request.offset = 0;
      this.currentPageIndex = 1;
      this.getQuestionList();
    },
    setDifficulty: function() {
      (this.request.type = null), (this.request.mode = "releaseDesc");
      this.request.offset = 0;
      this.currentPageIndex = 1;
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
      this.request.offset = 0;
      this.currentPageIndex = 1;
      this.getQuestionList();
    },
    setMode: function(modeNum) {
      if (modeNum === 1) {
        this.request.mode = "visitDesc";
      } else if (modeNum == 2) {
        this.request.mode = "likeDesc";
      } else {
        this.request.mode = "releaseDesc";
      }
      this.getQuestionList();
    },
    getQuestionList: function() {
      axios({
        url: API.URL + API.ROUTES.getQuestionList,
        method: "get",
        params: this.request,
      })
        .then((res) => {
          this.questionList = res.data.questionFormList;
          new numberCounter("todayQuestions", res.data.todayQuestions);
          new numberCounter("todayAnswers", res.data.todayAnswers);
          this.todayQuestions = res.data.todayQuestions;
          this.todayAnswers = res.data.todayAnswers;
          this.totalListItemCount = res.data.totalCount;
          this.questionList.forEach((e) => {
            var isImage = false;
            var isVideo = false;
            while (e.content.indexOf("<figure") != -1) {
              if (e.content.indexOf('<figure class="image">') != -1) {
                isImage = true;
              }
              if (e.content.indexOf('<figure class="media">') != -1) {
                isVideo = true;
              }
              e.content =
                e.content.slice(0, e.content.indexOf("<figure")) +
                e.content.slice(e.content.indexOf("</figure>") + 9);
            }
            
            e.content = e.content.replaceAll(`<img `, `<img style="display:none;" `);
            e.isImage = isImage;
            e.isVideo = isVideo;
          });
        })
        .catch((err) => {
          console.log(err.message);
        });
    },
    goQuestionCreate: function() {
      if (!this.isLoggedIn) {
        this.$modal.show(LoginModal,{
          modal : this.$modal },{
            name: 'dynamic-modal',
            width : '600px',
            height : '250px',
            draggable: false,
        });
        return;
      }

      this.$router.push({
        name: "QuestionsCreate",
      });
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
    paginationChanged(pageIndex) {
      this.request.offset = pageIndex - 1;
      this.currentPageIndex = pageIndex;
      this.getQuestionList();
    },
    pageIndex() {
      return this.currentPageIndex;
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
        this.categories = res.data;
        this.getQuestionList();
        this.setStateQuery(null);
        setTimeout(() => {
          this.isLoaded = true
        }, 1000)
      })
      .catch((err) => {
        console.log(err);
      });
  },
  computed: {
    ...mapState({
      query: (state) => state.questions.query,
      accessToken: state => state.auth.accessToken,
    }),
    ...mapGetters(['isLoggedIn']),
    typeWatch: function() {
      return this.request.type;
    },
    modeWatch: function() {
      return this.request.mode;
    },
    pageCount: function() {
      return Math.ceil(this.totalListItemCount / this.listRowCount);
    },
    startPageIndex: function() {
      if (this.currentPageIndex % this.pageLinkCount == 0) {
        //10, 20...맨마지막
        return (this.currentPageIndex / this.pageLinkCount - 1) * this.pageLinkCount + 1;
      } else {
        return Math.floor(this.currentPageIndex / this.pageLinkCount) * this.pageLinkCount + 1;
      }
    },
    endPageIndex: function() {
      let ret = 0;
      if (this.currentPageIndex % this.pageLinkCount == 0) {
        //10, 20...맨마지막
        ret =
          (this.currentPageIndex / this.pageLinkCount - 1) * this.pageLinkCount +
          this.pageLinkCount;
      } else {
        ret =
          Math.floor(this.currentPageIndex / this.pageLinkCount) * this.pageLinkCount +
          this.pageLinkCount;
      }
      return ret > this.pageCount ? this.pageCount : ret;
    },
    prev: function() {
      if (this.currentPageIndex <= this.pageLinkCount) {
        return false;
      } else {
        return true;
      }
    },
    next: function() {
      if (this.endPageIndex >= this.pageCount) {
        return false;
      } else {
        return true;
      }
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
      if (this.modeWatch == "visitDesc") {
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
  display: block;
  width: 100%;
  padding: 8px 16px;
  line-height: 25px;
  font-size: 14px;
  font-weight: 500;
  font-family: inherit;
  border-radius: 6px;
  -webkit-appearance: none;
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
  color: #99a3ba;
  background: #eef4ff;
  border: 1px solid #cdd9ed;
  transition: background 0.3s ease, border 0.3s ease, color 0.3s ease;
}

.form-group:focus-within > span {
  color: #fff;
  background: #678efe;
  border-color: #658dc6;
}

.info1 {
  color: #0f4c81;
  font-weight: 700;
  font-size: 21px;
}

.info2 {
  color: #0f4c81;
  font-weight: 700;
  font-size: 17px;
}

.list-group-category:hover {
  color: #0f4c81;
  animation: scale-up 0.1s forwards;
}

.list-group-item {
  color: #84898c;
}

.list-group-item:hover {
  color: #0f4c81;
  animation: scale-up 0.1s forwards;
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

.question-list {
  border-bottom: 1px solid #b5c7d3;
  margin-bottom: 20px;
}

.question-list-item {
  border-top: 1px solid #b5c7d3;
  cursor: pointer;
  padding: 15px 15px 15px 15px;
}

.question-list-item-content {
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  display: -webkit-box;
  text-align: left;
  overflow: hidden;
}

.question-list-item-title {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  display: inline-block;
  max-width: 800px;
  text-align: left;
}

.question-list-item .material-icons {
  font-size: 21px;
  margin: 0 6px 0 10px;
}

.question-list-item .count {
  font-size: 16px;
}

.question-list-item:hover {
  background-color: #f3f3f3;
  transition: color 0.2s;
}

.question-main {
  margin-left: auto;
  width: 920px;
}

.question-mode {
  color: #84898c;
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
  color: #84898c;
  font-weight: 600;
  padding-top: 5px;
}

.question-type-item {
  cursor: pointer;
}

.selectDifficulty {
  border-color: #cdd9ed;
  color: #84898c;
  font-size: 15px;
  padding-top: 2px;
}

.selectDifficultyBox {
  float: left;
  margin-left: 5px;
}

.page-nav {
  margin-bottom: 80px;
}

/* li a {
  border-style: solid;
  color: #7b27d8;
}
li a:hover {
  border-style: solid;
  background: #cba3f8;
  color: #fff;
} */
/* li {
  border-style: none;
} */
li.active.page-item a {
  background: #0f4c81 !important;
  /* background: linear-gradient(20deg, #5846f9 0%, #7b27d8 100%); */
  border-color: #0f4c81 !important;
  color: #fff;
  cursor: pointer;
}

li.page-item a {
  background: #fff !important;
  /* background: linear-gradient(20deg, #5846f9 0%, #7b27d8 100%); */
  border-color: #0f4c81 !important;
  color: #0f4c81;
  cursor: pointer;
}

li.page-item a:hover {
  background: #658dc6 !important;
  /* background: linear-gradient(20deg, #5846f9 0%, #7b27d8 100%); */
  border-color: #658dc6 !important;
  color: #fff;
}
</style>
