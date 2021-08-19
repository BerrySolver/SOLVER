<template>
  <div class="manual-delete-modal solver-font font-break">
    <h3 class="manual-delete-title">도움말</h3>
    <hr />
    <p>1. 왼쪽에 나열된 화면 중 보고싶은 화면을 선택하면 메인에서 확대가 됩니다.</p>
    <p>2. 화면공유<img
              style="width:23px; margin: 2px 0 3px 2px;"
              src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iaXNvLTg4NTktMSI/Pg0KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDE5LjAuMCwgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPg0KPHN2ZyB2ZXJzaW9uPSIxLjEiIGlkPSJDYXBhXzEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4Ig0KCSB2aWV3Qm94PSIwIDAgNDgwIDQ4MCIgc3R5bGU9ImVuYWJsZS1iYWNrZ3JvdW5kOm5ldyAwIDAgNDgwIDQ4MDsiIHhtbDpzcGFjZT0icHJlc2VydmUiPg0KPGc+DQoJPGc+DQoJCTxwYXRoIGQ9Ik00ODAsMzY4VjY0SDB2MzA0aDE4NHYzMmgtNTZ2MTZoMjQwdi0xNmgtNzJ2LTMySDQ4MHogTTI4MCw0MDBoLTgwdi0zMmg4MFY0MDB6IE0xNiwzNTJWODBoNDQ4djI3MkgxNnoiLz4NCgk8L2c+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8L3N2Zz4NCg=="
            />
      는 질문자와 답변자 중 한 명만 가능합니다.</p>
    <p>3. 화면 공유<img
              style="width:23px; margin: 2px 0 3px 2px;"
              src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iaXNvLTg4NTktMSI/Pg0KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDE5LjAuMCwgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPg0KPHN2ZyB2ZXJzaW9uPSIxLjEiIGlkPSJDYXBhXzEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4Ig0KCSB2aWV3Qm94PSIwIDAgNDgwIDQ4MCIgc3R5bGU9ImVuYWJsZS1iYWNrZ3JvdW5kOm5ldyAwIDAgNDgwIDQ4MDsiIHhtbDpzcGFjZT0icHJlc2VydmUiPg0KPGc+DQoJPGc+DQoJCTxwYXRoIGQ9Ik00ODAsMzY4VjY0SDB2MzA0aDE4NHYzMmgtNTZ2MTZoMjQwdi0xNmgtNzJ2LTMySDQ4MHogTTI4MCw0MDBoLTgwdi0zMmg4MFY0MDB6IE0xNiwzNTJWODBoNDQ4djI3MkgxNnoiLz4NCgk8L2c+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8L3N2Zz4NCg=="
            />
      시 프로그램의 소리가 같이 들리기 원하면, 화면 선택 창에서 "오디오 공유"를 선택해야 합니다.</p>
    <p>4. 녹화<img
              style="width:23px; margin: 0 0 0 2px;"
              src="data:image/svg+xml;base64,PHN2ZyBpZD0iQ2FwYV8xIiBlbmFibGUtYmFja2dyb3VuZD0ibmV3IDAgMCA1MTIgNTEyIiBoZWlnaHQ9IjUxMiIgdmlld0JveD0iMCAwIDUxMiA1MTIiIHdpZHRoPSI1MTIiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+PGc+PHBhdGggZD0ibTI2My41IDI0My4wMS05MC01MS45NjFjLTkuOTc3LTUuNzYxLTIyLjUgMS40NDctMjIuNSAxMi45OXYxMDMuOTIzYzAgMTEuNTAzIDEyLjQ4MyAxOC43NzQgMjIuNSAxMi45OWw5MC01MS45NjFjOS45NjQtNS43NTIgMTAuMDE0LTIwLjIgMC0yNS45ODF6bS04Mi41IDM4Ljk3di01MS45Nmw0NSAyNS45OHoiLz48cGF0aCBkPSJtNDkwLjMzOCAxMzcuNTYxLTk5LjMzOCA0OS4yMzh2LTY1Ljc5OWMwLTguMjg0LTYuNzE2LTE1LTE1LTE1aC0zNjFjLTguMjg0IDAtMTUgNi43MTYtMTUgMTV2MjcwYzAgOC4yODQgNi43MTYgMTUgMTUgMTVoMzYxYzguMjg0IDAgMTUtNi43MTYgMTUtMTV2LTY0Ljk3OGw5OS40MjggNDguNDYyYzkuOTQ5IDQuODQ4IDIxLjU3Mi0yLjQwMyAyMS41NzItMTMuNDg0di0yMTBjMC0xMS4wOTMtMTEuNjc5LTE4LjM4Ny0yMS42NjItMTMuNDM5em0tNDYwLjMzOCAyMzguNDM5di0yNDBoMzMxdjI0MHptNDUyLTM4Ljk5OC05MS00NC4zNTR2LTcyLjM2N2w5MS00NS4xMDV6Ii8+PC9nPjwvc3ZnPg=="
            />
      는 답변자만 가능하며, 원하는 화면을 녹화할 수 있습니다.</p>
    <p>5. 녹화<img
              style="width:23px; margin: 0 0 0 2px;"
              src="data:image/svg+xml;base64,PHN2ZyBpZD0iQ2FwYV8xIiBlbmFibGUtYmFja2dyb3VuZD0ibmV3IDAgMCA1MTIgNTEyIiBoZWlnaHQ9IjUxMiIgdmlld0JveD0iMCAwIDUxMiA1MTIiIHdpZHRoPSI1MTIiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+PGc+PHBhdGggZD0ibTI2My41IDI0My4wMS05MC01MS45NjFjLTkuOTc3LTUuNzYxLTIyLjUgMS40NDctMjIuNSAxMi45OXYxMDMuOTIzYzAgMTEuNTAzIDEyLjQ4MyAxOC43NzQgMjIuNSAxMi45OWw5MC01MS45NjFjOS45NjQtNS43NTIgMTAuMDE0LTIwLjIgMC0yNS45ODF6bS04Mi41IDM4Ljk3di01MS45Nmw0NSAyNS45OHoiLz48cGF0aCBkPSJtNDkwLjMzOCAxMzcuNTYxLTk5LjMzOCA0OS4yMzh2LTY1Ljc5OWMwLTguMjg0LTYuNzE2LTE1LTE1LTE1aC0zNjFjLTguMjg0IDAtMTUgNi43MTYtMTUgMTV2MjcwYzAgOC4yODQgNi43MTYgMTUgMTUgMTVoMzYxYzguMjg0IDAgMTUtNi43MTYgMTUtMTV2LTY0Ljk3OGw5OS40MjggNDguNDYyYzkuOTQ5IDQuODQ4IDIxLjU3Mi0yLjQwMyAyMS41NzItMTMuNDg0di0yMTBjMC0xMS4wOTMtMTEuNjc5LTE4LjM4Ny0yMS42NjItMTMuNDM5em0tNDYwLjMzOCAyMzguNDM5di0yNDBoMzMxdjI0MHptNDUyLTM4Ljk5OC05MS00NC4zNTR2LTcyLjM2N2w5MS00NS4xMDV6Ii8+PC9nPjwvc3ZnPg=="
            />
      가 중지되는 순간 신청한 질문의 답변으로 자동 등록됩니다.
    </p>
    <div class="manual-delete-button-bar">
      <button type="button" class="btn btn-submit" @click="$emit('close')">확인</button>
    </div>
  </div>
</template>

<script>
export default {
};
</script>

<style>
.manual-delete-modal {
  font-size: 17px;
  padding: 40px 50px;
}

.manual-delete-title {
  color: #0f4c81;
}

.manual-delete-button-bar {
  text-align: center;
  margin-top: 70px;
}

.manual-delete-button-bar > button {
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
