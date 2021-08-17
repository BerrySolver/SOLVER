<template>
  <div>
    <div style="display: flex; justify-content: center;">
      <div style="color: black; font-size: 18px; text-align: left; width: 880px;">
        <span style="color: #0F4C81; font-size: 26px;">{{ answerList.length }}</span
        >&nbsp;명의 솔버
      </div>
    </div>
    <div class="answer">
      <div class="answer-list">
        <div class="answer-item" v-for="(answer, idx) in answerList" :key="idx">
          <div class="answer-item-header">
            <div style="float: left;">
              <img class="answer-item-profileImg" src="@/assets/profile-ex.jpg" alt="" />
            </div>
            <div class="answer-info">
              <div style="font-size: 18px;">{{ answer.nickname }}</div>
              <div style="color: #84898C; font-size: 13px;">{{ humanize(now, answer.regDt) }}</div>
            </div>
            <div class="answer-buttons">
              <div v-if="questionNickname == nickname" class="answer-button" @click="openRequestConference(answer)">
                <img
                  style="width:23px; margin: 10px 0 3px 0;"
                  src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iaXNvLTg4NTktMSI/Pg0KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDE5LjAuMCwgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPg0KPHN2ZyB2ZXJzaW9uPSIxLjEiIGlkPSJDYXBhXzEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4Ig0KCSB2aWV3Qm94PSIwIDAgNDgwIDQ4MCIgc3R5bGU9ImVuYWJsZS1iYWNrZ3JvdW5kOm5ldyAwIDAgNDgwIDQ4MDsiIHhtbDpzcGFjZT0icHJlc2VydmUiPg0KPGc+DQoJPGc+DQoJCTxwYXRoIGQ9Ik00ODAsMzY4VjY0SDB2MzA0aDE4NHYzMmgtNTZ2MTZoMjQwdi0xNmgtNzJ2LTMySDQ4MHogTTI4MCw0MDBoLTgwdi0zMmg4MFY0MDB6IE0xNiwzNTJWODBoNDQ4djI3MkgxNnoiLz4NCgk8L2c+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8L3N2Zz4NCg=="
                />
              </div>
              <div class="answer-button" @click="clickFavorite(answer, idx)">
                <img
                  v-if="!answer.myFavoriteAnswer"
                  style="width:15px; margin: 6px 0 3px 0;"
                  src="data:image/svg+xml;base64,PHN2ZyB2aWV3Qm94PSIwIC0yOCA1MTIuMDAxIDUxMiIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj48cGF0aCBkPSJtMjU2IDQ1NS41MTU2MjVjLTcuMjg5MDYyIDAtMTQuMzE2NDA2LTIuNjQwNjI1LTE5Ljc5Mjk2OS03LjQzNzUtMjAuNjgzNTkzLTE4LjA4NTkzNy00MC42MjUtMzUuMDgyMDMxLTU4LjIxODc1LTUwLjA3NDIxOWwtLjA4OTg0My0uMDc4MTI1Yy01MS41ODIwMzItNDMuOTU3MDMxLTk2LjEyNS04MS45MTc5NjktMTI3LjExNzE4OC0xMTkuMzEyNS0zNC42NDQ1MzEtNDEuODA0Njg3LTUwLjc4MTI1LTgxLjQ0MTQwNi01MC43ODEyNS0xMjQuNzQyMTg3IDAtNDIuMDcwMzEzIDE0LjQyNTc4MS04MC44ODI4MTMgNDAuNjE3MTg4LTEwOS4yOTI5NjkgMjYuNTAzOTA2LTI4Ljc0NjA5NCA2Mi44NzEwOTMtNDQuNTc4MTI1IDEwMi40MTQwNjItNDQuNTc4MTI1IDI5LjU1NDY4OCAwIDU2LjYyMTA5NCA5LjM0Mzc1IDgwLjQ0NTMxMiAyNy43Njk1MzEgMTIuMDIzNDM4IDkuMzAwNzgxIDIyLjkyMTg3NiAyMC42ODM1OTQgMzIuNTIzNDM4IDMzLjk2MDkzOCA5LjYwNTQ2OS0xMy4yNzczNDQgMjAuNS0yNC42NjAxNTcgMzIuNTI3MzQ0LTMzLjk2MDkzOCAyMy44MjQyMTgtMTguNDI1NzgxIDUwLjg5MDYyNS0yNy43Njk1MzEgODAuNDQ1MzEyLTI3Ljc2OTUzMSAzOS41MzkwNjMgMCA3NS45MTAxNTYgMTUuODMyMDMxIDEwMi40MTQwNjMgNDQuNTc4MTI1IDI2LjE5MTQwNiAyOC40MTAxNTYgNDAuNjEzMjgxIDY3LjIyMjY1NiA0MC42MTMyODEgMTA5LjI5Mjk2OSAwIDQzLjMwMDc4MS0xNi4xMzI4MTIgODIuOTM3NS01MC43NzczNDQgMTI0LjczODI4MS0zMC45OTIxODcgMzcuMzk4NDM3LTc1LjUzMTI1IDc1LjM1NTQ2OS0xMjcuMTA1NDY4IDExOS4zMDg1OTQtMTcuNjI1IDE1LjAxNTYyNS0zNy41OTc2NTcgMzIuMDM5MDYyLTU4LjMyODEyNiA1MC4xNjc5NjktNS40NzI2NTYgNC43ODkwNjItMTIuNTAzOTA2IDcuNDI5Njg3LTE5Ljc4OTA2MiA3LjQyOTY4N3ptLTExMi45Njg3NS00MjUuNTIzNDM3Yy0zMS4wNjY0MDYgMC01OS42MDU0NjkgMTIuMzk4NDM3LTgwLjM2NzE4OCAzNC45MTQwNjItMjEuMDcwMzEyIDIyLjg1NTQ2OS0zMi42NzU3ODEgNTQuNDQ5MjE5LTMyLjY3NTc4MSA4OC45NjQ4NDQgMCAzNi40MTc5NjggMTMuNTM1MTU3IDY4Ljk4ODI4MSA0My44ODI4MTMgMTA1LjYwNTQ2OCAyOS4zMzIwMzEgMzUuMzk0NTMyIDcyLjk2MDkzNyA3Mi41NzQyMTkgMTIzLjQ3NjU2MiAxMTUuNjI1bC4wOTM3NS4wNzgxMjZjMTcuNjYwMTU2IDE1LjA1MDc4MSAzNy42Nzk2ODggMzIuMTEzMjgxIDU4LjUxNTYyNSA1MC4zMzIwMzEgMjAuOTYwOTM4LTE4LjI1MzkwNyA0MS4wMTE3MTktMzUuMzQzNzUgNTguNzA3MDMxLTUwLjQxNzk2OSA1MC41MTE3MTktNDMuMDUwNzgxIDk0LjEzNjcxOS04MC4yMjI2NTYgMTIzLjQ2ODc1LTExNS42MTcxODggMzAuMzQzNzUtMzYuNjE3MTg3IDQzLjg3ODkwNy02OS4xODc1IDQzLjg3ODkwNy0xMDUuNjA1NDY4IDAtMzQuNTE1NjI1LTExLjYwNTQ2OS02Ni4xMDkzNzUtMzIuNjc1NzgxLTg4Ljk2NDg0NC0yMC43NTc4MTMtMjIuNTE1NjI1LTQ5LjMwMDc4Mi0zNC45MTQwNjItODAuMzYzMjgyLTM0LjkxNDA2Mi0yMi43NTc4MTIgMC00My42NTIzNDQgNy4yMzQzNzQtNjIuMTAxNTYyIDIxLjUtMTYuNDQxNDA2IDEyLjcxODc1LTI3Ljg5NDUzMiAyOC43OTY4NzQtMzQuNjA5Mzc1IDQwLjA0Njg3NC0zLjQ1MzEyNSA1Ljc4NTE1Ny05LjUzMTI1IDkuMjM4MjgyLTE2LjI2MTcxOSA5LjIzODI4MnMtMTIuODA4NTk0LTMuNDUzMTI1LTE2LjI2MTcxOS05LjIzODI4MmMtNi43MTA5MzctMTEuMjUtMTguMTY0MDYyLTI3LjMyODEyNC0zNC42MDkzNzUtNDAuMDQ2ODc0LTE4LjQ0OTIxOC0xNC4yNjU2MjYtMzkuMzQzNzUtMjEuNS02Mi4wOTc2NTYtMjEuNXptMCAwIi8+PC9zdmc+"
                />
                <img
                  v-else
                  style="width:15px; margin: 6px 0 3px 0;"
                  src="data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMSIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHhtbG5zOnN2Z2pzPSJodHRwOi8vc3ZnanMuY29tL3N2Z2pzIiB3aWR0aD0iNTEyIiBoZWlnaHQ9IjUxMiIgeD0iMCIgeT0iMCIgdmlld0JveD0iMCAwIDM5MS44MzcgMzkxLjgzNyIgc3R5bGU9ImVuYWJsZS1iYWNrZ3JvdW5kOm5ldyAwIDAgNTEyIDUxMiIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSI+PGc+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+Cgk8cGF0aCBzdHlsZT0iIiBkPSJNMjg1LjI1NywzNS41MjhjNTguNzQzLDAuMjg2LDEwNi4yOTQsNDcuODM2LDEwNi41OCwxMDYuNTggICBjMCwxMDcuNjI0LTE5NS45MTgsMjE0LjIwNC0xOTUuOTE4LDIxNC4yMDRTMCwyNDguMTY1LDAsMTQyLjEwOGMwLTU4Ljg2Miw0Ny43MTctMTA2LjU4LDEwNi41OC0xMDYuNThsMCwwICAgYzM2LjAzMi0wLjI4MSw2OS43MTgsMTcuODQyLDg5LjMzOSw0OC4wNjVDMjE1LjY3NCw1My41MTcsMjQ5LjI3MywzNS40NDEsMjg1LjI1NywzNS41Mjh6IiBmaWxsPSIjZjViODk1IiBkYXRhLW9yaWdpbmFsPSIjZDc0NDNlIj48L3BhdGg+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPC9nPjwvc3ZnPg=="
                />
                <div style="font-size: 13px;">{{ answer.likeCount }}</div>
              </div>
              <div
                v-if="answer.nickname == nickname"
                class="answer-delete"
                @click="deleteAnswerCheck(answer)"
              >
                <img
                  style="width: 8px;"
                  src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iaXNvLTg4NTktMSI/Pg0KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDE5LjAuMCwgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPg0KPHN2ZyB2ZXJzaW9uPSIxLjEiIGlkPSJDYXBhXzEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4Ig0KCSB2aWV3Qm94PSIwIDAgNTEyLjAwMSA1MTIuMDAxIiBzdHlsZT0iZW5hYmxlLWJhY2tncm91bmQ6bmV3IDAgMCA1MTIuMDAxIDUxMi4wMDE7IiB4bWw6c3BhY2U9InByZXNlcnZlIj4NCjxnPg0KCTxnPg0KCQk8cGF0aCBkPSJNMjg0LjI4NiwyNTYuMDAyTDUwNi4xNDMsMzQuMTQ0YzcuODExLTcuODExLDcuODExLTIwLjQ3NSwwLTI4LjI4NWMtNy44MTEtNy44MS0yMC40NzUtNy44MTEtMjguMjg1LDBMMjU2LDIyNy43MTcNCgkJCUwzNC4xNDMsNS44NTljLTcuODExLTcuODExLTIwLjQ3NS03LjgxMS0yOC4yODUsMGMtNy44MSw3LjgxMS03LjgxMSwyMC40NzUsMCwyOC4yODVsMjIxLjg1NywyMjEuODU3TDUuODU4LDQ3Ny44NTkNCgkJCWMtNy44MTEsNy44MTEtNy44MTEsMjAuNDc1LDAsMjguMjg1YzMuOTA1LDMuOTA1LDkuMDI0LDUuODU3LDE0LjE0Myw1Ljg1N2M1LjExOSwwLDEwLjIzNy0xLjk1MiwxNC4xNDMtNS44NTdMMjU2LDI4NC4yODcNCgkJCWwyMjEuODU3LDIyMS44NTdjMy45MDUsMy45MDUsOS4wMjQsNS44NTcsMTQuMTQzLDUuODU3czEwLjIzNy0xLjk1MiwxNC4xNDMtNS44NTdjNy44MTEtNy44MTEsNy44MTEtMjAuNDc1LDAtMjguMjg1DQoJCQlMMjg0LjI4NiwyNTYuMDAyeiIvPg0KCTwvZz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjwvc3ZnPg0K"
                />
              </div>
            </div>
          </div>
          <div class="answer-item-body" v-html="answer.content"></div>
          <div class="answer-modify-btn-wrapper">
            <div
              v-if="nickname == answer.nickname"
              class="answer-modify-btn"
              @click="clickModifyBtn(answer, idx)"
            >
              <img src="@/assets/edit-button.png" width="20px">
            </div>
          </div>
          <div class="answer-comment-area">
            <div class="answer-comment-tab">
              <div class="answer-comment-button" @click="openComment(idx)">
                <img
                  style="width:23px;"
                  src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iaXNvLTg4NTktMSI/Pg0KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDE4LjEuMSwgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPg0KPHN2ZyB2ZXJzaW9uPSIxLjEiIGlkPSJDYXBhXzEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4Ig0KCSB2aWV3Qm94PSIwIDAgMzAuMzMzIDMwLjMzMyIgc3R5bGU9ImVuYWJsZS1iYWNrZ3JvdW5kOm5ldyAwIDAgMzAuMzMzIDMwLjMzMzsiIHhtbDpzcGFjZT0icHJlc2VydmUiPg0KPGc+DQoJPHBhdGggc3R5bGU9ImZpbGw6IzAxMDAwMjsiIGQ9Ik0wLDI2Ljc1VjExLjkwOGMwLTQuNTksMy43MzUtOC4zMjUsOC4zMjUtOC4zMjVoMTMuNjgxYzQuNTkxLDAsOC4zMjcsMy43MzUsOC4zMjcsOC4zMjV2Mi41Ng0KCQljMCw0LjU5LTMuNzM1LDguMzI1LTguMzI1LDguMzI1SDguNTE4TDAsMjYuNzV6IE04LjMyNSw1LjQzOWMtMy41NjcsMC02LjQ2OSwyLjkwMi02LjQ2OSw2LjQ2OXYxMS45MzNsNi4yNTMtMi45MDVoMTMuODk5DQoJCWMzLjU2NywwLDYuNDY5LTIuOTAyLDYuNDY5LTYuNDY5di0yLjU2YzAtMy41NjctMi45MDMtNi40NjktNi40NzEtNi40NjlIOC4zMjVWNS40Mzl6IE04LjkzNywxMS43NjcNCgkJYzAuOTc1LDAsMS43NjUsMC43OSwxLjc2NSwxLjc2NXMtMC43OSwxLjc2NS0xLjc2NSwxLjc2NXMtMS43NjUtMC43OS0xLjc2NS0xLjc2NVM3Ljk2MiwxMS43NjcsOC45MzcsMTEuNzY3eiBNMjEuMzk1LDExLjc2Nw0KCQljMC45NzUsMCwxLjc2NSwwLjc5LDEuNzY1LDEuNzY1cy0wLjc5LDEuNzY1LTEuNzY1LDEuNzY1cy0xLjc2NS0wLjc5LTEuNzY1LTEuNzY1UzIwLjQyLDExLjc2NywyMS4zOTUsMTEuNzY3eiBNMTUuMTY1LDExLjc2Nw0KCQljMC45NzUsMCwxLjc2NSwwLjc5LDEuNzY1LDEuNzY1cy0wLjc5LDEuNzY1LTEuNzY1LDEuNzY1cy0xLjc2NS0wLjc5LTEuNzY1LTEuNzY1UzE0LjE5LDExLjc2NywxNS4xNjUsMTEuNzY3eiIvPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPC9zdmc+DQo="
                />
                <span style="font-size: 15px; font-weight: 700; margin-left: 5px;">{{
                  answer.commentCount
                }}</span>
              </div>
            </div>
            <span v-show="isTrue"></span>
            <Comments v-show="commentListOpen[idx]" :answerId="answer.answerId" />
            <CommentsCreate v-if="isLoggedIn" v-show="commentListOpen[idx]" :answerId="answer.answerId" />
            <div v-else v-show="commentListOpen[idx]" class="nonlogin-comment" @click="$router.push({name: 'Login'})">
              <div class="nonlogin-comment-content">
                <span>로그인하고 댓글을 남겨보세요!</span>
              </div>
            </div>
          </div>
          <div :id="`answerModifyEditorInsert` + idx"></div>
          <div>
            <div
              v-if="openedIdx == idx"
              class="answer-modify-complete-btn"
              @click="clickModifyCompleteBtn(answer)"
            >
              수정완료
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
import Comments from "@/components/questions/Comments";
import CommentsCreate from "@/components/questions/CommentsCreate";
import CKEditor from "@ckeditor/ckeditor5-vue2";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import { mapState, mapGetters } from "vuex";
import RequesetConference from './modal/RequesetConference.vue';
import AnswerDelete from './modal/AnswerDelete.vue';
import LoginModal from "@/components/main/LoginModal"

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
  name: "Answer",
  components: {
    Comments,
    CommentsCreate,
  },
  data() {
    return {
      nickname: "",
      isOpened: false,
      openedIdx: -1,
      answerList: [],
      commentListOpen: [],
      isTrue: true,
      now: new Date(),
      CKEditor: ""
    };
  },
  props: {
    questionNickname: String,
  },
  methods: {
    openComment: function(idx) {
      this.commentListOpen[idx] = !this.commentListOpen[idx];
      this.isTrue = !this.isTrue;
    },
    getAnswerList: function() {
      axios({
        url: API.URL + `answers/list/${this.$route.params.questionId}`,
        method: "get",
        params: {
          nickname: this.userNickname
        },
        headers: { Authorization: "Bearer " + this.accessToken },
      })
        .then((res) => {
          this.answerList = res.data.answerList;
          this.commentListOpen = new Array(res.data.answerList.length);
          for (let i = 0; i < res.data.answerList.length; i++) {
            this.commentListOpen[i] = false;
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    humanize: function(now, date) {
      const moment = require("moment");
      const dateData = new Date(date);
      let r = now - dateData;
      if (parseInt(r) > 43200000) {
        r = moment(dateData).format("YYYY.MM.DD");
      } else if (parseInt(r) >= 3600000) {
        r = parseInt(parseInt(r) / 3600000).toString() + "시간 전";
      } else if (parseInt(r) >= 60000) {
        r = parseInt(parseInt(r) / 60000).toString() + "분 전";
      } else {
        r = "방금 전";
      }
      return r;
    },
    checkNickname(nickname) {
      if (this.userNickname == nickname) return true;

      return false;
    },
    clickFavorite(answer, idx) {
      if (answer.myFavoriteAnswer) {
        this.removeFavoriteAnswer(answer, idx);
      } else {
        this.addFavoriteAnswer(answer, idx);
      }
    },
    addFavoriteAnswer(answer, idx) {
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

      const answerId = answer.answerId;

      axios({
        url: API.URL + "answers/" + answerId + "/recommend",
        method: "post",
        headers: { Authorization: "Bearer " + this.accessToken },
      })
        .then(() => {
          this.answerList[idx].myFavoriteAnswer = !this.answerList[idx].myFavoriteAnswer;
          this.answerList[idx].likeCount++;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    removeFavoriteAnswer(answer, idx) {
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

      const answerId = answer.answerId;

      axios({
        url: API.URL + "answers/" + answerId + "/recommend",
        method: "delete",
        headers: { Authorization: "Bearer " + this.accessToken },
      })
        .then(() => {
          this.answerList[idx].myFavoriteAnswer = !this.answerList[idx].myFavoriteAnswer;
          this.answerList[idx].likeCount--;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    clickModifyBtn(answer, idx) {
      const str = "#answerModifyEditorInsert" + idx;

      if (this.openedIdx == idx) {
        this.CKEditor.setData("");
        this.openedIdx = -1;
        this.isOpened = false;
        this.CKEditor.destroy();
        return;
      }

      if (this.isOpened == true) {
        this.CKEditor.setData("");
        this.CKEditor.destroy();
      }

      this.openedIdx = idx;

      ClassicEditor.create(document.querySelector(str), {
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
          this.CKEditor.setData(answer.content);
        })
        .catch((err) => {
          console.error(err.stack);
        });

      this.isOpened = true;
      let $this = this;
      this.$el.addEventListener("show.bs.modal", function() {
        $this.initUI();
      });
    },
    clickModifyCompleteBtn(answer) {
      const content = this.CKEditor.getData();
      axios({
        url: API.URL + `answers/${answer.answerId}/modify`,
        method: "post",
        data: {
          content: content,
        },
        headers: {
          "Access-Control-Allow-Methods": "GET,PUT,POST,PATCH,DELETE",
          Authorization: "Bearer " + this.accessToken
        },
      })
        .then(() => {
          this.CKEditor.setData("");
          this.openedIdx = -1;
          this.isOpened = false;
          this.CKEditor.destroy();
          this.getAnswerList();
        })
        .catch((err) => {
          console.log(err);
        });
    },
    deleteAnswer(answer) {
      axios({
        url: API.URL + `answers/${answer.answerId}`,
        method: "delete",
        headers: {
          Authorization: "Bearer " + this.accessToken
        },
      })
        .then(() => {
          this.getAnswerList();
        })
        .catch((err) => {
          console.log(err);
        });
    },
    deleteAnswerCheck(answer){
      this.$modal.show(AnswerDelete,{
        answer : answer,
        modal : this.$modal },{
          name: 'dynamic-modal',
          width : '600px',
          height : '250px',
          draggable: false,
      });
    },
    openRequestConference(answer){
      this.$modal.show(RequesetConference,{
        answer : answer,
        modal : this.$modal },{
          name: 'dynamic-modal',
          width : '600px',
          height : '650px',
          draggable: false,
        }
      );
    },
  },
  mounted() {
    this.nickname = this.userNickname
  },
  created() {
    this.getAnswerList();
  },
  computed: {
    ...mapState({
      commentDeleteTrigger: (state) => state.questions.commentDeleteTrigger,
      answerChangeTrigger: (state) => state.questions.answerChangeTrigger,
      accessToken: state => state.auth.accessToken,
      userNickname: state => state.auth.userNickname,
    }),
    ...mapGetters(['isLoggedIn'])
  },
  watch: {
    commentDeleteTrigger: function() {
      axios({
        url: API.URL + `answers/list/${this.$route.params.questionId}`,
        method: "get",
      })
        .then((res) => {
          this.answerList = res.data.answerList;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    answerChangeTrigger: function() {
      this.getAnswerList();
    }
  },
};
</script>

<style>
.answer {
  display: flex;
  margin-top: 10px;
  justify-content: center;
}

.answer-button {
  border: 1px solid #e0e0e0;
  border-radius: 70%;
  float: left;
  height: 45px;
  margin-right: 7px;
  width: 45px;
}

.answer-buttons {
  display: flex;
  margin-left: auto;
  align-items: center;
}

.answer-button:hover {
  cursor: pointer;
  filter: brightness(107%);
}

.answer-comment-area {
  background-color: #fcfcfc;
  border-top: 1px solid #e0e0e0;
  bottom: 0;
  width: 100%;
}

.answer-comment-button {
  cursor: pointer;
}

.answer-comment-tab {
  align-items: center;
  display: flex;
  height: 48px;
  margin-left: 40px;
  justify-content: space-between;
}

.answer-list {
  min-width: 900px;
  max-width: 900px;
}

.answer-info {
  float: left;
  margin-left: 10px;
  text-align: left;
}
.answer-info div {
  width: 60px;
}

.answer-item {
  border: 1px solid #e0e0e0;
  border-radius: 6px;
  margin-bottom: 20px;
  min-height: 250px;
  overflow: hidden;
}

.answer-item-body {
  font-size: 17px;
  margin: 15px 40px 80px 40px;
  text-align: left;
}

.answer-item-header {
  align-items: center;
  border-bottom: 1px solid #e0e0e0;
  display: flex;
  height: 50px;
  margin: 30px 40px 0 40px;
  padding-bottom: 30px;
}

.answer-item-profileImg {
  border-radius: 70%;
  height: 40px;
  object-fit: cover;
  width: 40px;
}

.answer-modify-btn {
  border-radius: 6px;
  color: white;
  cursor: pointer;
  float: left;
  height: 25px;
  margin-right: 10px;
  font-size: 15px;
  width: 25px;
  margin-left: 835px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.answer-modify-btn-wrapper {
  display: flex;
  justify-content: right;
  height: 40px;
  width: 100%;
  margin: 0px;
}

.answer-modify-btn:hover {
  filter: brightness(40%);
}

.answer-list .ck.ck-editor {
  width: 900px;
}

.answer-list .ck.ck-editor__editable {
  height: 400px;
  margin-bottom: 30px;
}

.answer-modify-complete-btn {
  background-color: #658dc6;
  border-radius: 6px;
  color: white;
  cursor: pointer;
  font-size: 17px;
  margin: 0px;
  width: 900px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.answer-delete {
  align-items: center;
  cursor: pointer;
  display: flex;
  height: 10px;
  margin-left: 10px;
  top: 17px;
}

.nonlogin-comment {
  display: flex;
  justify-content: center;
  margin: 0px 0 50px 0;
}

.nonlogin-comment-content {
  align-items: center;
  border: 1px solid #658dc6;
  border-radius: 20px;
  color: #658dc6;
  cursor: pointer;
  display: flex;
  font-size: 17px;
  font-weight: 700;
  height: 100px;
  justify-content: center;
  transition: 0.2s;
  width: 800px;
}

.nonlogin-comment-content:hover {
  background-color: #658dc6;
  color: white;
  transform: scale(1.02);
}
</style>
