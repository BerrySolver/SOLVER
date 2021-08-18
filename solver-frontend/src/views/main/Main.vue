<template>
  <!-- 전체 메인 페이지 div -->
  <div class="main">
    <!-- 1번째 메인 페이지(SOLVER 설명) -->
    <div class="main-container">
      <div class="main-container-contents">
        <div class="main-info">
          <div class="title">솔버(SOLVER)</div>
          <div class="introduction">
            무언가를 꾸준히 해서 얻는 성과는 달콤한 법이죠.
            <br />
            공부를 하고 얻은 지식은 자산이 되고,
            <br />
            지식을 공유한 분들은 원하는 목표를 향한 희망이 될 것입니다.
          </div>

          <div class="form__main-group">
            <input
              type="input"
              class="form__field"
              v-model="query"
              placeholder="Name"
              name="name"
              id="name"
              required
              @keypress.enter="setStateQueryMain(query)"
            />
            <label for="name" class="form__label">
              <span>질문을 검색해보세요!</span>
            </label>
          </div>
        </div>

        <img src="@/assets/main-logo.png" alt="main-logo" class="main-logo" />
      </div>
    </div>

    <!-- 2번째 메인 페이지(현재 인기 질문) -->
    <div class="popular-question">
      <div class="popular-question-contents">
        <div class="semi-title">
          <span>인기 질문</span>
        </div>
        <!-- 인기질문 -->
        <div class="card-grid">
          <div
            class="card-item"
            v-for="(question, index) in mainQuestion"
            :key="index"
            @click="goQuestionDetail(question.questionId)"
          >
            <div class="card-title">
              {{ question.title }}
            </div>
            <div class="card-question-body">
              <div class="card-text card-content" v-html="question.content"></div>
              <p class="card-text text-right">
                <img
                  style="width:15px;"
                  src="data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMSIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHhtbG5zOnN2Z2pzPSJodHRwOi8vc3ZnanMuY29tL3N2Z2pzIiB3aWR0aD0iNTEyIiBoZWlnaHQ9IjUxMiIgeD0iMCIgeT0iMCIgdmlld0JveD0iMCAwIDM5MS44MzcgMzkxLjgzNyIgc3R5bGU9ImVuYWJsZS1iYWNrZ3JvdW5kOm5ldyAwIDAgNTEyIDUxMiIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSIgY2xhc3M9IiI+PGc+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+Cgk8cGF0aCBzdHlsZT0iIiBkPSJNMjg1LjI1NywzNS41MjhjNTguNzQzLDAuMjg2LDEwNi4yOTQsNDcuODM2LDEwNi41OCwxMDYuNTggICBjMCwxMDcuNjI0LTE5NS45MTgsMjE0LjIwNC0xOTUuOTE4LDIxNC4yMDRTMCwyNDguMTY1LDAsMTQyLjEwOGMwLTU4Ljg2Miw0Ny43MTctMTA2LjU4LDEwNi41OC0xMDYuNThsMCwwICAgYzM2LjAzMi0wLjI4MSw2OS43MTgsMTcuODQyLDg5LjMzOSw0OC4wNjVDMjE1LjY3NCw1My41MTcsMjQ5LjI3MywzNS40NDEsMjg1LjI1NywzNS41Mjh6IiBmaWxsPSIjZjhiODk1IiBkYXRhLW9yaWdpbmFsPSIjZDc0NDNlIiBjbGFzcz0iIj48L3BhdGg+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPC9nPjwvc3ZnPg=="
                />&nbsp;&nbsp;{{ question.likeCount }} &nbsp;&nbsp;
                <img
                  style="width:15px;"
                  src="data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMSIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHhtbG5zOnN2Z2pzPSJodHRwOi8vc3ZnanMuY29tL3N2Z2pzIiB3aWR0aD0iNTEyIiBoZWlnaHQ9IjUxMiIgeD0iMCIgeT0iMCIgdmlld0JveD0iMCAwIDUxMiA1MTIiIHN0eWxlPSJlbmFibGUtYmFja2dyb3VuZDpuZXcgMCAwIDUxMiA1MTIiIHhtbDpzcGFjZT0icHJlc2VydmUiPjxnPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgoJPGc+CgkJPHBhdGggZD0iTTQxNi42NjcsMEg5NS4zMzRjLTguMjg0LDAtMTUsNi43MTYtMTUsMTV2NDgyYzAsNi4wNjcsMy42NTUsMTEuNTM2LDkuMjYsMTMuODU4YzEuODU2LDAuNzY5LDMuODA1LDEuMTQyLDUuNzM3LDEuMTQyICAgIGMzLjkwMywwLDcuNzQtMS41MjMsMTAuNjA5LTQuMzk0bDE1MC4wNjMtMTUwLjA2Mkw0MDYuMDYsNTA3LjYwNmM0LjI5LDQuMjkxLDEwLjc0MSw1LjU3MywxNi4zNDcsMy4yNTIgICAgYzUuNjA1LTIuMzIyLDkuMjYtNy43OTEsOS4yNi0xMy44NThWMTVDNDMxLjY2Nyw2LjcxNiw0MjQuOTUyLDAsNDE2LjY2NywweiIgZmlsbD0iI2YyZDZhZSIgZGF0YS1vcmlnaW5hbD0iIzAwMDAwMCIgc3R5bGU9IiI+PC9wYXRoPgoJPC9nPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjwvZz48L3N2Zz4="
                />
                &nbsp;&nbsp;{{ question.bookMarkCount }} &nbsp;&nbsp;
                <img
                  style="width:15px;"
                  src="data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMSIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHhtbG5zOnN2Z2pzPSJodHRwOi8vc3ZnanMuY29tL3N2Z2pzIiB3aWR0aD0iNTEyIiBoZWlnaHQ9IjUxMiIgeD0iMCIgeT0iMCIgdmlld0JveD0iMCAwIDUxMS45OTkgNTExLjk5OSIgc3R5bGU9ImVuYWJsZS1iYWNrZ3JvdW5kOm5ldyAwIDAgNTEyIDUxMiIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSIgY2xhc3M9IiI+PGc+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+Cgk8Zz4KCQk8cGF0aCBkPSJNNTA4Ljc0NSwyNDYuMDQxYy00LjU3NC02LjI1Ny0xMTMuNTU3LTE1My4yMDYtMjUyLjc0OC0xNTMuMjA2UzcuODE4LDIzOS43ODQsMy4yNDksMjQ2LjAzNSAgICBjLTQuMzMyLDUuOTM2LTQuMzMyLDEzLjk4NywwLDE5LjkyM2M0LjU2OSw2LjI1NywxMTMuNTU3LDE1My4yMDYsMjUyLjc0OCwxNTMuMjA2czI0OC4xNzQtMTQ2Ljk1LDI1Mi43NDgtMTUzLjIwMSAgICBDNTEzLjA4MywyNjAuMDI4LDUxMy4wODMsMjUxLjk3MSw1MDguNzQ1LDI0Ni4wNDF6IE0yNTUuOTk3LDM4NS40MDZjLTEwMi41MjksMC0xOTEuMzMtOTcuNTMzLTIxNy42MTctMTI5LjQxOCAgICBjMjYuMjUzLTMxLjkxMywxMTQuODY4LTEyOS4zOTUsMjE3LjYxNy0xMjkuMzk1YzEwMi41MjQsMCwxOTEuMzE5LDk3LjUxNiwyMTcuNjE3LDEyOS40MTggICAgQzQ0Ny4zNjEsMjg3LjkyMywzNTguNzQ2LDM4NS40MDYsMjU1Ljk5NywzODUuNDA2eiIgZmlsbD0iIzg0ODk4YyIgZGF0YS1vcmlnaW5hbD0iIzAwMDAwMCIgc3R5bGU9IiI+PC9wYXRoPgoJPC9nPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+Cgk8Zz4KCQk8cGF0aCBkPSJNMjU1Ljk5NywxNTQuNzI1Yy01NS44NDIsMC0xMDEuMjc1LDQ1LjQzMy0xMDEuMjc1LDEwMS4yNzVzNDUuNDMzLDEwMS4yNzUsMTAxLjI3NSwxMDEuMjc1ICAgIHMxMDEuMjc1LTQ1LjQzMywxMDEuMjc1LTEwMS4yNzVTMzExLjgzOSwxNTQuNzI1LDI1NS45OTcsMTU0LjcyNXogTTI1NS45OTcsMzIzLjUxNmMtMzcuMjMsMC02Ny41MTYtMzAuMjg3LTY3LjUxNi02Ny41MTYgICAgczMwLjI4Ny02Ny41MTYsNjcuNTE2LTY3LjUxNnM2Ny41MTYsMzAuMjg3LDY3LjUxNiw2Ny41MTZTMjkzLjIyNywzMjMuNTE2LDI1NS45OTcsMzIzLjUxNnoiIGZpbGw9IiM4NDg5OGMiIGRhdGEtb3JpZ2luYWw9IiMwMDAwMDAiIHN0eWxlPSIiPjwvcGF0aD4KCTwvZz4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8L2c+PC9zdmc+"
                />
                &nbsp;&nbsp;{{ question.readCount }} &nbsp;&nbsp;
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="month-solver">
      <br />
      <div class="month-solver-text">
        <img src="@/assets/logo-white-2.png" class="small-logo" />
        <p class="solver-title">오늘의 SOLVER</p>
        <p v-if="this.isLoggedIn" style="margin-top:20px;color:white;"><span style="color:#F2D6AE"><b>{{getUserNickname}}</b></span>님의 관심분야에 맞는 솔버입니다!</p>
        <p v-else style="margin-top:20px;color:white">능력있는 솔버들을 만나보세요!</p>
      </div>

      <div v-if="isZeroSolver" class="empty_card">
        <div class="empty_card_detail">
          유능한 솔버를 곧 추천해드릴게요!<br><span style="font-size:20px; color:#0F4C81;">잠시만 기다려주세요!</span><br>
          <img style="width:150px;" src="@/assets/berry-fail.png" alt="">
        </div>
      </div>
      <div class="solver-line-1" :class="{'solver-one-row':isEmptySolver}">
        <div class="solver-row">
          <div style="float: left;" v-for="(mul, idx) in solverLine1" :key="idx" :class="{'solver-margin-remove':isEmptySolver}">
            <div class="solver-card-content">
              <img :src="mul.profileUrl" class="solver-card-img" alt="solver-card-img" @click="goUserProfile(mul.nickname)"/>
              <img class="solver-level-badge" src="@/assets/berry-1.png" v-if="parseInt(mul.point) < 100" alt="">
              <img class="solver-level-badge" src="@/assets/berry-2.png" v-if="parseInt(mul.point) >= 100 && parseInt(mul.point) < 200" alt="">
              <img class="solver-level-badge" src="@/assets/berry-3.png" v-if="parseInt(mul.point) >= 200 && parseInt(mul.point) < 300" alt="">
              <img class="solver-level-badge" src="@/assets/berry-4.png" v-if="parseInt(mul.point) >= 300 && parseInt(mul.point) < 400" alt="">
              <img class="solver-level-badge" src="@/assets/berry-5.png" v-if="parseInt(mul.point) >= 400 " alt="">
              <p class="solver-intro">{{mul.nickname}}</p>
            </div>
          </div>
        </div>
      </div>
      <div class="solver-line-2">        
        <div class="solver-row">
          <div style="float: left;" v-for="(mul, idx) in solverLine2" :key="idx">
            <div class="solver-card-content">
              <img :src="mul.profileUrl" class="solver-card-img" alt="solver-card-img"  @click="goUserProfile(mul.nickname)"/>
              <img class="solver-level-badge" src="@/assets/berry-1.png" v-if="parseInt(mul.point) < 100" alt="">
              <img class="solver-level-badge" src="@/assets/berry-2.png" v-if="parseInt(mul.point) >= 100 && parseInt(mul.point) < 200" alt="">
              <img class="solver-level-badge" src="@/assets/berry-3.png" v-if="parseInt(mul.point) >= 200 && parseInt(mul.point) < 300" alt="">
              <img class="solver-level-badge" src="@/assets/berry-4.png" v-if="parseInt(mul.point) >= 300 && parseInt(mul.point) < 400" alt="">
              <img class="solver-level-badge" src="@/assets/berry-5.png" v-if="parseInt(mul.point) >= 400 " alt="">
              <p class="solver-intro">{{mul.nickname}}</p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 4번째 메인 페이지(모임) -->
    <div class="main-group-container">
      <div class="main-group-container-contents">
        <div class="main-group-title">
          <div class="main-group-card">
            <section class="page-contain">
              <a class="data-card" href="https://danghyeona.notion.site/5999b6935e544b5fa9557e26f567b04a" target='_blank'>
                <div class="data-card-height">
                  <h3 class="notion-h3">공지사항</h3>
                  <div class="notion-div">안내사항</div>
                  <div class="notion-div">개발기록</div>
                  <div class="notion-div">개발자 후기</div>
                  <div class="notion-div">업데이트 예정</div>
                  <p>꼼꼼히 보면 재미있는 정보!</p>
                </div>
                <span class="link-text">
                  상세보기
                  <svg
                    width="25"
                    height="16"
                    viewBox="0 0 25 16"
                    fill="none"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path
                      fill-rule="evenodd"
                      clip-rule="evenodd"
                      d="M17.8631 0.929124L24.2271 7.29308C24.6176 7.68361 24.6176 8.31677 24.2271 8.7073L17.8631 15.0713C17.4726 15.4618 16.8394 15.4618 16.4489 15.0713C16.0584 14.6807 16.0584 14.0476 16.4489 13.657L21.1058 9.00019H0.47998V7.00019H21.1058L16.4489 2.34334C16.0584 1.95281 16.0584 1.31965 16.4489 0.929124C16.8394 0.538599 17.4726 0.538599 17.8631 0.929124Z"
                      fill="#0F4C81"
                    />
                  </svg>
                </span>
              </a>
              <a class="data-card" href="https://danghyeona.notion.site/SOLVER-e5336b26f14147d69f9f124cd8574e9b" target='_blank'>
                <div class="data-card-height">
                  <h3 class="notion-h3">솔버소개</h3>
                  <div class="notion-div">솔버란?</div>
                  <div class="notion-div">솔버의 강점</div>
                  <div class="notion-div">솔버의 모든것</div>
                  <p>이렇게까지 디테일하다고?!</p>
                </div>
                <span class="link-text">
                  상세보기
                  <svg
                    width="25"
                    height="16"
                    viewBox="0 0 25 16"
                    fill="none"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path
                      fill-rule="evenodd"
                      clip-rule="evenodd"
                      d="M17.8631 0.929124L24.2271 7.29308C24.6176 7.68361 24.6176 8.31677 24.2271 8.7073L17.8631 15.0713C17.4726 15.4618 16.8394 15.4618 16.4489 15.0713C16.0584 14.6807 16.0584 14.0476 16.4489 13.657L21.1058 9.00019H0.47998V7.00019H21.1058L16.4489 2.34334C16.0584 1.95281 16.0584 1.31965 16.4489 0.929124C16.8394 0.538599 17.4726 0.538599 17.8631 0.929124Z"
                      fill="#0F4C81"
                    />
                  </svg>
                </span>
              </a>
              <a class="data-card" href="https://danghyeona.notion.site/1c352b72fa604393adda86b681a0c3b9" target='_blank'>                
                <div class="data-card-height">
                  <h3 class="notion-h3">베리소개</h3>
                  <div class="notion-div">베리란?</div>
                  <div class="notion-div">베리의 역할</div>
                  <div class="notion-div">다양한 베리</div>
                  <p>동글동글 귀여운 얘는,<br>도대체 누구야?!</p>
                </div>
                <span class="link-text">
                  상세보기
                  <svg
                    width="25"
                    height="16"
                    viewBox="0 0 25 16"
                    fill="none"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path
                      fill-rule="evenodd"
                      clip-rule="evenodd"
                      d="M17.8631 0.929124L24.2271 7.29308C24.6176 7.68361 24.6176 8.31677 24.2271 8.7073L17.8631 15.0713C17.4726 15.4618 16.8394 15.4618 16.4489 15.0713C16.0584 14.6807 16.0584 14.0476 16.4489 13.657L21.1058 9.00019H0.47998V7.00019H21.1058L16.4489 2.34334C16.0584 1.95281 16.0584 1.31965 16.4489 0.929124C16.8394 0.538599 17.4726 0.538599 17.8631 0.929124Z"
                      fill="#0F4C81"
                    />
                  </svg>
                </span>
              </a>
              <a class="data-card" href="https://danghyeona.notion.site/edce4119fb7c49aea92846efa4253ae4" target='_blank'>
                <div class="data-card-height">
                  <h3 class="notion-h3">고객센터</h3>
                  <div class="notion-div">1:1 문의</div>
                  <div class="notion-div">오류 신고</div>
                  <div class="notion-div">서비스 평가</div>
                  <div class="notion-div">자주 묻는 질문</div>
                  <p>여러분과 함께 만들어갑니다.</p>
                </div>
                <span class="link-text">
                  상세보기
                  <svg
                    width="25"
                    height="16"
                    viewBox="0 0 25 16"
                    fill="none"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path
                      fill-rule="evenodd"
                      clip-rule="evenodd"
                      d="M17.8631 0.929124L24.2271 7.29308C24.6176 7.68361 24.6176 8.31677 24.2271 8.7073L17.8631 15.0713C17.4726 15.4618 16.8394 15.4618 16.4489 15.0713C16.0584 14.6807 16.0584 14.0476 16.4489 13.657L21.1058 9.00019H0.47998V7.00019H21.1058L16.4489 2.34334C16.0584 1.95281 16.0584 1.31965 16.4489 0.929124C16.8394 0.538599 17.4726 0.538599 17.8631 0.929124Z"
                      fill="#0F4C81"
                    />
                  </svg>
                </span>
              </a>
            </section>
          </div>
        </div>
        <div class="col-2"></div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import API from "@/API.js";
import { mapActions, mapGetters, mapState } from "vuex";

export default {
  name: "Main",
  data() {
    return {
      mainQuestion: [],
      query: null,
      solverLine1: [],
      solverLine2: [],
      isEmptySolver: false,
      isZeroSolver: false,
    };
  },
  methods: {
    ...mapActions(["setStateQuery", "setStateQueryMain", "goQuestionDetail", "triggerMainReload"]),
    goUserProfile(nickname){
        if (nickname !== this.userNickname) {
          this.$router.push({
            name: 'Profile',
            params: {
              nickname: nickname
            }
          });
        } else {
          this.$router.push({
            path: `/my-profile/${this.userNickname}`
          })
        }
    },
    getPaySolverList: function(){
      axios({
        url: API.URL + API.ROUTES.getPaySolver,
        method: "get",
        headers: { Authorization: "Bearer " + this.accessToken },
      }).then((res)=>{
        this.isEmptySolver = false;
        this.isZeroSolver = false;
        if(res.data.list.length > 3){
          var n = res.data.list.length;

          if (n%2==0){
            this.solverLine1 = res.data.list.slice(0, n/2);
            this.solverLine2 = res.data.list.slice(n/2);
          }else{
            this.solverLine1 = res.data.list.slice(0, n/2);
            this.solverLine2 = res.data.list.slice(n/2);
          }
        }else{
          this.solverLine1 = res.data.list;
          this.isEmptySolver = true;
        }

        if(res.data.list.length == 0)
          this.isZeroSolver = true;

      }).catch((err)=>{

      });
    },
    getMainQuestionList : function(){
      axios({
        url: API.URL + API.ROUTES.getRecommendQuestion,
        method: "get",
        headers: { Authorization: "Bearer " + this.accessToken },
      })
      .then((res) => {
        this.mainQuestion = res.data.list;
        this.mainQuestion.forEach((e) => {
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
          // e.isImge = isImage;
          // e.isVideao = isVideo;
        });
      })
      .catch((err) => {
        console.log(err)
      });
    },
  },
  created() {
    this.getMainQuestionList();
    this.getPaySolverList();
  },
  computed : {    
    ...mapState({
      accessToken: state => state.auth.accessToken,
      userNickname: state => state.auth.userNickname,
      mainChangeTrigger: (state) => state.auth.mainChangeTrigger,
    }),
    ...mapGetters(['isLoggedIn', 'getUserNickname']),
  },
  watch: {
    mainChangeTrigger: function () {
      this.getMainQuestionList();
      this.getPaySolverList();
    },
  }
};
</script>

<style>
@import "./main.css";

.singleline-ellipsis {
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
}

  img.solver-level-badge{
    position: absolute;
    width: 70px;
    left: -10px;
    top: -30px;
    background: white;
    border-radius: 100%;
  }
</style>
