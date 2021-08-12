<template>
  <div>
    <div class="question-detail">
      <div class="question-detail-contents">
        <div class="question-detail-texts">
          <div class="question-detail-category">
            <div>{{ question.mainCategory }} > {{ question.subCategory }}</div>
            <div v-if="checkNickname()" class="question-buttons">
              <div class="question-modify-btn" @click="modifyQuestion()">
                수정하기
              </div>
              <div class="question-delete-btn" @click="deleteQuestionCheck()">
                삭제하기
              </div>
            </div>
          </div>
          <div class="question-detail-title">
            {{ question.title }}
          </div>
          <div class="question-detail-user">
            <img
              class="question-detail-profileImg"
              src="@/assets/profile-ex.jpg"
              alt="profile-image"
            />
            <span class="question-detail-nickname">{{ question.nickname }}</span>
          </div>
          <div class="question-detail-content" v-html="modifyUrl(question.content)">
            <!-- {{question.content}} -->
          </div>
          <div class="question-detail-info">
            {{ humanize(now, question.regDt) + "\u00A0\u00A0\u00A0" }}조회수{{
              "\u00A0" + question.readCount
            }}
          </div>
        </div>
        <div class="question-detail-counts">
          <div class="question-detail-count">
            <img
              style="width:20px; margin: 13px 0 3px 0;"
              src="data:image/svg+xml;base64,PHN2ZyBpZD0iQ2FwYV8xIiBlbmFibGUtYmFja2dyb3VuZD0ibmV3IDAgMCA1MTEuMDcyIDUxMS4wNzIiIGhlaWdodD0iNTEyIiB2aWV3Qm94PSIwIDAgNTExLjA3MiA1MTEuMDcyIiB3aWR0aD0iNTEyIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPjxnIGlkPSJTcGVlY2hfQnViYmxlXzQ4XyI+PGc+PHBhdGggZD0ibTc0LjM5IDQ4MC41MzZoLTM2LjIxM2wyNS42MDctMjUuNjA3YzEzLjgwNy0xMy44MDcgMjIuNDI5LTMxLjc2NSAyNC43NDctNTEuMjQ2LTM2LjAyOS0yMy42NDQtNjIuMzc1LTU0Ljc1MS03Ni40NzgtOTAuNDI1LTE0LjA5My0zNS42NDctMTUuODY0LTc0Ljg4OC01LjEyMS0xMTMuNDgyIDEyLjg5LTQ2LjMwOSA0My4xMjMtODguNTE4IDg1LjEyOC0xMTguODUzIDQ1LjY0Ni0zMi45NjMgMTAyLjQ3LTUwLjM4NyAxNjQuMzMtNTAuMzg3IDc3LjkyNyAwIDE0My42MTEgMjIuMzg5IDE4OS45NDggNjQuNzQ1IDQxLjc0NCAzOC4xNTkgNjQuNzM0IDg5LjYzIDY0LjczNCAxNDQuOTMzIDAgMjYuODY4LTUuNDcxIDUzLjAxMS0xNi4yNiA3Ny43MDMtMTEuMTY1IDI1LjU1MS0yNy41MTQgNDguMzAyLTQ4LjU5MyA2Ny42MTktNDYuMzk5IDQyLjUyMy0xMTIuMDQyIDY1LTE4OS44MyA2NS0yOC44NzcgMC01OS4wMS0zLjg1NS04NS45MTMtMTAuOTI5LTI1LjQ2NSAyNi4xMjMtNTkuOTcyIDQwLjkyOS05Ni4wODYgNDAuOTI5em0xODItNDIwYy0xMjQuMDM5IDAtMjAwLjE1IDczLjk3My0yMjAuNTU3IDE0Ny4yODUtMTkuMjg0IDY5LjI4IDkuMTQzIDEzNC43NDMgNzYuMDQzIDE3NS4xMTVsNy40NzUgNC41MTEtLjIzIDguNzI3Yy0uNDU2IDE3LjI3NC00LjU3NCAzMy45MTItMTEuOTQ1IDQ4Ljk1MiAxNy45NDktNi4wNzMgMzQuMjM2LTE3LjA4MyA0Ni45OS0zMi4xNTFsNi4zNDItNy40OTMgOS40MDUgMi44MTNjMjYuMzkzIDcuODk0IDU3LjEwNCAxMi4yNDEgODYuNDc3IDEyLjI0MSAxNTQuMzcyIDAgMjI0LjY4Mi05My40NzMgMjI0LjY4Mi0xODAuMzIyIDAtNDYuNzc2LTE5LjUyNC05MC4zODQtNTQuOTc2LTEyMi43OS00MC43MTMtMzcuMjE2LTk5LjM5Ny01Ni44ODgtMTY5LjcwNi01Ni44ODh6Ii8+PC9nPjwvZz48L3N2Zz4="
            />
            <div>{{ answerCount }}</div>
          </div>
          <div class="question-detail-count question-count-button" @click="changeLike()">
            <img
              v-if="isLiked"
              style="width:20px; margin: 13px 0 3px 0;"
              src="data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMSIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHhtbG5zOnN2Z2pzPSJodHRwOi8vc3ZnanMuY29tL3N2Z2pzIiB3aWR0aD0iNTEyIiBoZWlnaHQ9IjUxMiIgeD0iMCIgeT0iMCIgdmlld0JveD0iMCAwIDM5MS44MzcgMzkxLjgzNyIgc3R5bGU9ImVuYWJsZS1iYWNrZ3JvdW5kOm5ldyAwIDAgNTEyIDUxMiIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSI+PGc+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+Cgk8cGF0aCBzdHlsZT0iIiBkPSJNMjg1LjI1NywzNS41MjhjNTguNzQzLDAuMjg2LDEwNi4yOTQsNDcuODM2LDEwNi41OCwxMDYuNTggICBjMCwxMDcuNjI0LTE5NS45MTgsMjE0LjIwNC0xOTUuOTE4LDIxNC4yMDRTMCwyNDguMTY1LDAsMTQyLjEwOGMwLTU4Ljg2Miw0Ny43MTctMTA2LjU4LDEwNi41OC0xMDYuNThsMCwwICAgYzM2LjAzMi0wLjI4MSw2OS43MTgsMTcuODQyLDg5LjMzOSw0OC4wNjVDMjE1LjY3NCw1My41MTcsMjQ5LjI3MywzNS40NDEsMjg1LjI1NywzNS41Mjh6IiBmaWxsPSIjZjViODk1IiBkYXRhLW9yaWdpbmFsPSIjZDc0NDNlIj48L3BhdGg+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPC9nPjwvc3ZnPg=="
            />
            <img
              v-else
              style="width:20px; margin: 13px 0 3px 0;"
              src="data:image/svg+xml;base64,PHN2ZyB2aWV3Qm94PSIwIC0yOCA1MTIuMDAxIDUxMiIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj48cGF0aCBkPSJtMjU2IDQ1NS41MTU2MjVjLTcuMjg5MDYyIDAtMTQuMzE2NDA2LTIuNjQwNjI1LTE5Ljc5Mjk2OS03LjQzNzUtMjAuNjgzNTkzLTE4LjA4NTkzNy00MC42MjUtMzUuMDgyMDMxLTU4LjIxODc1LTUwLjA3NDIxOWwtLjA4OTg0My0uMDc4MTI1Yy01MS41ODIwMzItNDMuOTU3MDMxLTk2LjEyNS04MS45MTc5NjktMTI3LjExNzE4OC0xMTkuMzEyNS0zNC42NDQ1MzEtNDEuODA0Njg3LTUwLjc4MTI1LTgxLjQ0MTQwNi01MC43ODEyNS0xMjQuNzQyMTg3IDAtNDIuMDcwMzEzIDE0LjQyNTc4MS04MC44ODI4MTMgNDAuNjE3MTg4LTEwOS4yOTI5NjkgMjYuNTAzOTA2LTI4Ljc0NjA5NCA2Mi44NzEwOTMtNDQuNTc4MTI1IDEwMi40MTQwNjItNDQuNTc4MTI1IDI5LjU1NDY4OCAwIDU2LjYyMTA5NCA5LjM0Mzc1IDgwLjQ0NTMxMiAyNy43Njk1MzEgMTIuMDIzNDM4IDkuMzAwNzgxIDIyLjkyMTg3NiAyMC42ODM1OTQgMzIuNTIzNDM4IDMzLjk2MDkzOCA5LjYwNTQ2OS0xMy4yNzczNDQgMjAuNS0yNC42NjAxNTcgMzIuNTI3MzQ0LTMzLjk2MDkzOCAyMy44MjQyMTgtMTguNDI1NzgxIDUwLjg5MDYyNS0yNy43Njk1MzEgODAuNDQ1MzEyLTI3Ljc2OTUzMSAzOS41MzkwNjMgMCA3NS45MTAxNTYgMTUuODMyMDMxIDEwMi40MTQwNjMgNDQuNTc4MTI1IDI2LjE5MTQwNiAyOC40MTAxNTYgNDAuNjEzMjgxIDY3LjIyMjY1NiA0MC42MTMyODEgMTA5LjI5Mjk2OSAwIDQzLjMwMDc4MS0xNi4xMzI4MTIgODIuOTM3NS01MC43NzczNDQgMTI0LjczODI4MS0zMC45OTIxODcgMzcuMzk4NDM3LTc1LjUzMTI1IDc1LjM1NTQ2OS0xMjcuMTA1NDY4IDExOS4zMDg1OTQtMTcuNjI1IDE1LjAxNTYyNS0zNy41OTc2NTcgMzIuMDM5MDYyLTU4LjMyODEyNiA1MC4xNjc5NjktNS40NzI2NTYgNC43ODkwNjItMTIuNTAzOTA2IDcuNDI5Njg3LTE5Ljc4OTA2MiA3LjQyOTY4N3ptLTExMi45Njg3NS00MjUuNTIzNDM3Yy0zMS4wNjY0MDYgMC01OS42MDU0NjkgMTIuMzk4NDM3LTgwLjM2NzE4OCAzNC45MTQwNjItMjEuMDcwMzEyIDIyLjg1NTQ2OS0zMi42NzU3ODEgNTQuNDQ5MjE5LTMyLjY3NTc4MSA4OC45NjQ4NDQgMCAzNi40MTc5NjggMTMuNTM1MTU3IDY4Ljk4ODI4MSA0My44ODI4MTMgMTA1LjYwNTQ2OCAyOS4zMzIwMzEgMzUuMzk0NTMyIDcyLjk2MDkzNyA3Mi41NzQyMTkgMTIzLjQ3NjU2MiAxMTUuNjI1bC4wOTM3NS4wNzgxMjZjMTcuNjYwMTU2IDE1LjA1MDc4MSAzNy42Nzk2ODggMzIuMTEzMjgxIDU4LjUxNTYyNSA1MC4zMzIwMzEgMjAuOTYwOTM4LTE4LjI1MzkwNyA0MS4wMTE3MTktMzUuMzQzNzUgNTguNzA3MDMxLTUwLjQxNzk2OSA1MC41MTE3MTktNDMuMDUwNzgxIDk0LjEzNjcxOS04MC4yMjI2NTYgMTIzLjQ2ODc1LTExNS42MTcxODggMzAuMzQzNzUtMzYuNjE3MTg3IDQzLjg3ODkwNy02OS4xODc1IDQzLjg3ODkwNy0xMDUuNjA1NDY4IDAtMzQuNTE1NjI1LTExLjYwNTQ2OS02Ni4xMDkzNzUtMzIuNjc1NzgxLTg4Ljk2NDg0NC0yMC43NTc4MTMtMjIuNTE1NjI1LTQ5LjMwMDc4Mi0zNC45MTQwNjItODAuMzYzMjgyLTM0LjkxNDA2Mi0yMi43NTc4MTIgMC00My42NTIzNDQgNy4yMzQzNzQtNjIuMTAxNTYyIDIxLjUtMTYuNDQxNDA2IDEyLjcxODc1LTI3Ljg5NDUzMiAyOC43OTY4NzQtMzQuNjA5Mzc1IDQwLjA0Njg3NC0zLjQ1MzEyNSA1Ljc4NTE1Ny05LjUzMTI1IDkuMjM4MjgyLTE2LjI2MTcxOSA5LjIzODI4MnMtMTIuODA4NTk0LTMuNDUzMTI1LTE2LjI2MTcxOS05LjIzODI4MmMtNi43MTA5MzctMTEuMjUtMTguMTY0MDYyLTI3LjMyODEyNC0zNC42MDkzNzUtNDAuMDQ2ODc0LTE4LjQ0OTIxOC0xNC4yNjU2MjYtMzkuMzQzNzUtMjEuNS02Mi4wOTc2NTYtMjEuNXptMCAwIi8+PC9zdmc+"
            />
            <div>{{ likeCount }}</div>
          </div>
          <div class="question-detail-count question-count-button" @click="changeBookmark()">
            <img
              v-if="isBookmarked"
              style="width:20px; margin: 13px 0 3px 0;"
              src="data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMSIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHhtbG5zOnN2Z2pzPSJodHRwOi8vc3ZnanMuY29tL3N2Z2pzIiB3aWR0aD0iNTEyIiBoZWlnaHQ9IjUxMiIgeD0iMCIgeT0iMCIgdmlld0JveD0iMCAwIDUxMiA1MTIiIHN0eWxlPSJlbmFibGUtYmFja2dyb3VuZDpuZXcgMCAwIDUxMiA1MTIiIHhtbDpzcGFjZT0icHJlc2VydmUiIGNsYXNzPSIiPjxnPgo8cG9seWdvbiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHN0eWxlPSIiIHBvaW50cz0iNDE0LjA0LDAgNDE0LjA0LDUwOS4zNjggMjU2LDM3Ni40ODggOTcuOTYsNTEyIDk3Ljk2LDAgIiBmaWxsPSIjZjJkNmFlIiBkYXRhLW9yaWdpbmFsPSIjZTIxYjFiIj48L3BvbHlnb24+Cjxwb2x5Z29uIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgc3R5bGU9IiIgcG9pbnRzPSI5Ny45NiwwIDk3Ljk2LDUxMiAyNTYsMzc2LjQ4OCAyNTYsMCAiIGZpbGw9IiNmMmQ2YWUiIGRhdGEtb3JpZ2luYWw9IiNmOTFlMWUiIGNsYXNzPSIiPjwvcG9seWdvbj4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPC9nPjwvc3ZnPg=="
            />
            <img
              v-else
              style="width:20px; margin: 13px 0 3px 0;"
              src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iaXNvLTg4NTktMSI/Pg0KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDE5LjAuMCwgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPg0KPHN2ZyB2ZXJzaW9uPSIxLjEiIGlkPSJDYXBhXzEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4Ig0KCSB2aWV3Qm94PSIwIDAgNTEyIDUxMiIgc3R5bGU9ImVuYWJsZS1iYWNrZ3JvdW5kOm5ldyAwIDAgNTEyIDUxMjsiIHhtbDpzcGFjZT0icHJlc2VydmUiPg0KPGc+DQoJPGc+DQoJCTxwYXRoIGQ9Ik00MTYuNjY3LDBIOTUuMzMzYy04LjI4NCwwLTE1LDYuNzE2LTE1LDE1djQ4MmMwLDYuMDY3LDMuNjU1LDExLjUzNiw5LjI2LDEzLjg1OGMxLjg1NiwwLjc2OSwzLjgwNSwxLjE0Miw1LjczNywxLjE0Mg0KCQkJYzMuOTA0LDAsNy43NC0xLjUyMywxMC42MS00LjM5NGwxNTAuMDYzLTE1MC4wNjFMNDA2LjA2LDUwNy42MDZjNC4yOSw0LjI5LDEwLjc0Miw1LjU3MywxNi4zNDcsMy4yNTINCgkJCWM1LjYwNS0yLjMyMiw5LjI2LTcuNzkxLDkuMjYtMTMuODU4VjE1QzQzMS42NjcsNi43MTYsNDI0Ljk1MSwwLDQxNi42NjcsMHogTTI1Ni4wMDIsMzIxLjMzMmMtMy45NzgsMC03Ljc5MywxLjU4LTEwLjYwNiw0LjM5NA0KCQkJTDExMC4zMzMsNDYwLjc4N1YzMGgyOTEuMzMzdjQzMC43ODVMMjY2LjYwOSwzMjUuNzI2QzI2My43OTYsMzIyLjkxMiwyNTkuOTgxLDMyMS4zMzIsMjU2LjAwMiwzMjEuMzMyeiIvPg0KCTwvZz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjwvc3ZnPg0K"
            />
            <div>{{ bookmarkCount }}</div>
          </div>
        </div>
      </div>
    </div>
    <hr style="color: #e0e0e0; opacity: 0.8;" />
    <Answer />
    <AnswerCreate :questionId="$route.params.questionId" />
  </div>
</template>

<script>
import axios from "axios";
import API from "@/API.js";
import Answer from "@/components/questions/Answer";
import AnswerCreate from "@/components/questions/AnswerCreate";
import router from "@/router";
import { mapState, mapActions } from "vuex";

export default {
  name: "QuestionsDetail",
  components: {
    Answer,
    AnswerCreate,
  },
  data() {
    return {
      question: {},
      answerCount: 0,
      likeCount: 0,
      bookmarkCount: 0,
      isLiked: false,
      isBookmarked: false,
      now: new Date(),
    };
  },
  methods: {
    ...mapActions(["setStateQuestionId", "setStateQuestion", "setStateContent"]),
    changeLike: function() {
      if (localStorage.getItem("solverToken") != null) {
        if (this.isLiked) {
          axios({
            url: API.URL + `questions/${this.$route.params.questionId}/recommend`,
            method: "delete",
            headers: { Authorization: "Bearer " + localStorage.getItem("solverToken") },
          })
            .then(() => {
              this.isLiked = !this.isLiked;
              this.likeCount -= 1;
            })
            .catch((err) => {
              console.log(err);
            });
        } else {
          axios({
            url: API.URL + `questions/${this.$route.params.questionId}/recommend`,
            method: "post",
            headers: { Authorization: "Bearer " + localStorage.getItem("solverToken") },
          })
            .then(() => {
              this.isLiked = !this.isLiked;
              this.likeCount += 1;
            })
            .catch((err) => {
              console.log(err);
            });
        }
      }
    },
    changeBookmark: function() {
      if (localStorage.getItem("solverToken") != null) {
        if (this.isBookmarked) {
          axios({
            url: API.URL + `questions/${this.$route.params.questionId}/bookmark`,
            method: "delete",
            headers: { Authorization: "Bearer " + localStorage.getItem("solverToken") },
          })
            .then(() => {
              this.isBookmarked = !this.isBookmarked;
              this.bookmarkCount -= 1;
            })
            .catch((err) => {
              console.log(err);
            });
        } else {
          axios({
            url: API.URL + `questions/${this.$route.params.questionId}/bookmark`,
            method: "post",
            headers: { Authorization: "Bearer " + localStorage.getItem("solverToken") },
          })
            .then(() => {
              this.isBookmarked = !this.isBookmarked;
              this.bookmarkCount += 1;
            })
            .catch((err) => {
              console.log(err);
            });
        }
      }
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
    modifyUrl(url) {
      if (url == null) {
        console.log("null");
        return "";
      }

      let endpoint = url;
      endpoint = endpoint.replaceAll("<oembed", "<iframe allowfullscreen");
      endpoint = endpoint.replaceAll("url", "src");
      endpoint = endpoint.replaceAll("watch?v=", "embed/");
      endpoint = endpoint.replaceAll("</oembed", "</iframe");
      endpoint = endpoint.replaceAll(`youtu.be/`, `www.youtube.com/embed/`);

      return endpoint;
    },
    deleteQuestion() {
      axios({
        url: API.URL + `questions/${this.$route.params.questionId}`,
        method: "delete",
        headers: { Authorization: "Bearer " + localStorage.getItem("solverToken") },
      })
        .then((res) => {
          router.push({ path: "/questions" });
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    deleteQuestionCheck(){
      const $this =this;
      alertify.confirm("질문삭제", "정말 삭제하시겠습니까?",
      function(){
        $this.deleteQuestion();
      }, function(){
        
      });
    },
    modifyQuestion() {
      this.setStateQuestion(this.question);
      this.setStateQuestionId(this.$route.params.questionId);
      this.$router.push({
        name: "QuestionsModify",
      });
    },
    checkNickname() {
      if (this.question.nickname == localStorage.getItem("solverNickname")) return true;

      return false;
    },
  },
  created() {
    axios({
      url: API.URL + `questions/${this.$route.params.questionId}/info`,
      method: "get",
      headers: { Authorization: "Bearer " + localStorage.getItem("solverToken") },
    })
      .then((res) => {
        this.question = res.data;
        this.answerCount = res.data.answerCount;
        this.likeCount = res.data.likeCount;
        this.bookmarkCount = res.data.bookmarkCount;
        this.isLiked = res.data.liked;
        this.isBookmarked = res.data.bookmarked;
        console.log(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  },
};
</script>

<style>
iframe {
  width: 800px;
  height: 450px;
}

.question-count-button {
  cursor: pointer;
}

.question-count-button:hover {
  filter: brightness(107%);
}

.question-detail {
  display: flex;
  min-height: 300px;
  margin-top: 60px;
  justify-content: center;
}

.question-detail-category {
  display: flex;
  color: #84898c;
  font-size: 17px;
  margin-bottom: 10px;
  text-align: left;
  justify-content: space-between;
}

.question-detail-content {
  font-size: 17px;
  padding: 30px 0 0 20px;
  text-align: left;
  width: 850px;
}

.question-detail-contents {
  max-width: 900px;
  min-width: 900px;
}

.question-detail-count {
  border: 1px solid #e0e0e0;
  border-radius: 70%;
  height: 70px;
  margin-bottom: 20px;
  width: 70px;
}

.question-detail-counts {
  align-items: flex-end;
  display: flex;
  flex-direction: column;
  margin-top: 40px;
  justify-content: flex-end;
}

.question-detail-info {
  color: #84898c;
  font-size: 14px;
  margin-top: 100px;
  padding-left: 20px;
  text-align: left;
}

.question-detail-nickname {
  color: #84898c;
  margin-left: 10px;
  font-size: 16px;
}

.question-detail-profileImg {
  border-radius: 70%;
  height: 30px;
  object-fit: cover;
  width: 30px;
}

.question-detail-texts {
  float: left;
  min-height: 300px;
  width: 1000px;
}

.question-detail-user {
  margin: 15px 0 0 20px;
  text-align: left;
}

.question-detail-title {
  border-left: 3px solid #0f4c81;
  font-size: 30px;
  padding-left: 15px;
  text-align: left;
  width: 850px;
}

.question-buttons {
  display: flex;
}

.question-buttons div {
  display: flex;

  width: 70px;
}

.question-modify-btn {
  background-color: #658dc6;
  border-radius: 6px;
  color: white;
  cursor: pointer;
  float: left;
  height: 30px;
  font-size: 15px;
  width: 70px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.question-delete-btn {
  background-color: #b5c7d3;
  border-radius: 6px;
  color: white;
  cursor: pointer;
  float: left;
  height: 30px;
  font-size: 15px;
  width: 70px;
  margin-left: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.question-modify-btn:hover {
  color: white;
  background: #0f4c81;
  transition: 0.4s;
}

.question-delete-btn:hover {
  color: white;
  background: #84898c;
  transition: 0.4s;
}
</style>
