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

          <div class="form__group">
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
          <div>현재</div>
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
              <div class="card-text card-content">{{ question.content }}</div>
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

    <!-- 3번째 메인 페이지(이달의 솔버) -->
    <div class="month-solver">
      <br />
      <div class="month-solver-text">
        <img src="@/assets/logo-white-2.png" class="small-logo" />
        <p class="solver-title">이달의 SOLVER</p>
      </div>

      <div class="row">
        <!-- 왼쪽 여백용 col-2 -->
        <div class="col-2"></div>

        <!-- 중앙 카드용 col-8 -->
        <div class="row solver-row">
          <div class="col-2"></div>
          <div class="col-2">
            <div class="solver-card-rank">01</div>
            <div class="solver-card-content">
              <img src="@/assets/예시.jpg" class="solver-card-img" alt="solver-card-img" />
              <p class="solver-intro">1등 닉네임</p>
            </div>
          </div>
          <div class="col-2">
            <div class="solver-card-rank">02</div>
            <div class="solver-card-content">
              <img src="@/assets/예시.jpg" class="solver-card-img" alt="solver-card-img" />
              <p class="solver-intro">2등 닉네임</p>
            </div>
          </div>
          <div class="col-2">
            <div class="solver-card-rank">03</div>
            <div class="solver-card-content">
              <img src="@/assets/예시.jpg" class="solver-card-img" alt="solver-card-img" />
              <p class="solver-intro">3등 닉네임</p>
            </div>
          </div>
          <div class="col-2">
            <div class="solver-card-rank">04</div>
            <div class="solver-card-content">
              <img src="@/assets/예시.jpg" class="solver-card-img" alt="solver-card-img" />
              <p class="solver-intro">4등 닉네임</p>
            </div>
          </div>
          <div class="col-3"></div>
        </div>

        <div class="row solver-row">
          <div class="col-3"></div>
          <div class="col-2">
            <div class="solver-card-rank">05</div>
            <div class="solver-card-content">
              <img src="@/assets/예시.jpg" class="solver-card-img" alt="solver-card-img" />
              <p class="solver-intro">5등 닉네임</p>
            </div>
          </div>
          <div class="col-2">
            <div class="solver-card-rank">06</div>
            <div class="solver-card-content">
              <img src="@/assets/예시.jpg" class="solver-card-img" alt="solver-card-img" />
              <p class="solver-intro">6등 닉네임</p>
            </div>
          </div>
          <div class="col-2">
            <div class="solver-card-rank">07</div>
            <div class="solver-card-content">
              <img src="@/assets/예시.jpg" class="solver-card-img" alt="solver-card-img" />
              <p class="solver-intro">7등 닉네임</p>
            </div>
          </div>
          <div class="col-2">
            <div class="solver-card-rank">08</div>
            <div class="solver-card-content">
              <img src="@/assets/예시.jpg" class="solver-card-img" alt="solver-card-img" />
              <p class="solver-intro">8등 닉네임</p>
            </div>
          </div>
          <div class="col-1"></div>
        </div>

        <!-- 오른쪽 여백용 col-2 -->
        <div class="col-2"></div>
        <br />
      </div>
    </div>

    <!-- 4번째 메인 페이지(모임) -->
    <div class="group-container">
      <div class="group-container-contents">
        <div class="group-title">
          <div class="semi-title">모임</div>
          <div class="group-card">
            <section class="page-contain">
              <a href="#" class="data-card">
                <h3>블루베리</h3>
                <h4>Python</h4>
                <h4>JAVA</h4>
                <h4>Vue.js</h4>
                <p>Aeneansed consectetur.</p>
                <span class="link-text">
                  모임 상세보기
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
              <a href="#" class="data-card">
                <h3>SOLVER</h3>
                <h4>Python</h4>
                <p>Aenean lacinia bibendum nulla sed consectetur.</p>
                <span class="link-text">
                  모임 상세보기
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
              <a href="#" class="data-card">
                <h3>나당연합군</h3>
                <h4>Python</h4>
                <h4>JAVA</h4>
                <p>Aenean lacinia bibendum nulla sed consectetur.</p>
                <span class="link-text">
                  모임 상세보기
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
              <a href="#" class="data-card">
                <h3>솔버</h3>
                <h4>Python</h4>
                <h4>JAVA</h4>
                <h4>Vue.js</h4>
                <p>Aenean lacinia bibendum nulla sed consectetur.</p>
                <span class="link-text">
                  모임 상세보기
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
import { mapActions } from "vuex";

export default {
  name: "Main",
  data() {
    return {
      mainQuestion: [],
      query: null,
    };
  },
  methods: {
    ...mapActions(["setStateQuery", "setStateQueryMain", "goQuestionDetail"]),
  },
  created() {
    axios({
      url: API.URL + API.ROUTES.getQuestionList,
      method: "get",
      params: {
        mode: "likeDesc",
        limit: 6,
        offest: 0,
      },
    })
      .then((res) => {
        console.log(res);
        for (var i = 0; i < 6; i++) {
          this.mainQuestion.push(res.data.questionFormList[i]);
        }
      })
      .catch(() => {
        console.log();
      });
  },
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
</style>
