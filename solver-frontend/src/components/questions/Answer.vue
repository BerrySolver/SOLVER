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
            <div
              @click="goUserProfile(answer.nickname)"
              class="answer-user-profile-image"
              style="float: left;"
            >
              <img class="answer-item-profileImg" :src="answer.profileUrl" alt="" />
            </div>
            <div class="answer-info font-break">
              <div
                @click="goUserProfile(answer.nickname)"
                class="answer-user-nickname"
                style="font-size: 18px;width: 300px;"
              >
                {{ answer.nickname }}
              </div>
              <div style="color: #84898C; font-size: 13px;">{{ humanize(now, answer.regDt) }}</div>
            </div>
            <div class="answer-buttons">
              <div
                v-if="questionNickname == nickname && questionState != '해결'"
                class="answer-button"
                @click="selectAnswer(answer.answerId)"
              >
                <img
                  style="width:20px; margin: 3px 0 0 0;"
                  src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4KPHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIGlkPSJJY29ucyIgdmlld0JveD0iMCAwIDc0IDc0IiB3aWR0aD0iNTEyIiBoZWlnaHQ9IjUxMiI+PHBhdGggZD0iTTI3LjEsNzJoLS4wMTRhMSwxLDAsMCwxLS45MTItLjYyMmwtMy4yMjEtNy44OS03Ljc0MiwzLjUzM2ExLDEsMCwwLDEtMS4zMy0xLjMxNEwyMi4zMSw0Ni42MzZhMSwxLDAsMCwxLDEuODMuODA5TDE2Ljc3Niw2NC4xMDlsNi4zLTIuODczYTEsMSwwLDAsMSwxLjM0MS41MzJsMi43MjUsNi42NzMsNy45LTE3LjkxNWExLDEsMCwwLDEsMS44My44MDdMMjguMDE4LDcxLjRBMSwxLDAsMCwxLDI3LjEsNzJaIi8+PHBhdGggZD0iTTMxLjEsNTMuNDM2YTMuNjU0LDMuNjU0LDAsMCwxLS45NTctLjEyM2MtMS40OTMtLjQtMi40OTEtMS43LTMuMzcyLTIuODVhNy4zODEsNy4zODEsMCwwLDAtMS44LTEuOSw3LjUxMSw3LjUxMSwwLDAsMC0yLjYtLjYzNGMtMS40OS0uMi0zLjAzMS0uNDA2LTQuMS0xLjQ3N3MtMS4yNzktMi42MTQtMS40NzgtNC4xMDVhNy41NTYsNy41NTYsMCwwLDAtLjYzNC0yLjU5NSw3LjQzMSw3LjQzMSwwLDAsMC0xLjktMS44Yy0xLjE1LS44ODEtMi40NTItMS44NzktMi44NTEtMy4zNzEtLjM4My0xLjQzMy4yLTIuODU1Ljc2OC00LjIyOWE3LjY1Nyw3LjY1NywwLDAsMCwuNzY0LTIuNjI5LDcuNjU4LDcuNjU4LDAsMCwwLS43NjQtMi42MjhjLS41NjYtMS4zNzQtMS4xNTEtMi44LS43NjgtNC4yMjkuNC0xLjQ5MiwxLjctMi40OSwyLjg1MS0zLjM3MWE3LjQxNiw3LjQxNiwwLDAsMCwxLjktMS44MDYsNy41MjIsNy41MjIsMCwwLDAsLjYzMy0yLjU5NGMuMi0xLjQ5MS40LTMuMDMyLDEuNDc4LTQuMXMyLjYxMy0xLjI3OCw0LjEtMS40NzhhNy41MTEsNy41MTEsMCwwLDAsMi42LS42MzQsNy40MDgsNy40MDgsMCwwLDAsMS44LTEuOWMuODgtMS4xNSwxLjg3OS0yLjQ1MiwzLjM3MS0yLjg1MSwxLjQzMy0uMzg3LDIuODU1LjIsNC4yMjkuNzY4QTcuNjU5LDcuNjU5LDAsMCwwLDM3LDMuNjUzYTcuNjU5LDcuNjU5LDAsMCwwLDIuNjI5LS43NjNjMS4zNzQtLjU2NiwyLjgtMS4xNTIsNC4yMjgtLjc2OSwxLjQ5My40LDIuNDkxLDEuNywzLjM3MiwyLjg1MWE3LjQsNy40LDAsMCwwLDEuOCwxLjksNy40ODksNy40ODksMCwwLDAsMi41OTUuNjMzYzEuNDkuMiwzLjAzMS40MDYsNC4xLDEuNDc4czEuMjc5LDIuNjE0LDEuNDc4LDQuMWE3LjU1LDcuNTUsMCwwLDAsLjYzNCwyLjYsNy40LDcuNCwwLDAsMCwxLjksMS44YzEuMTUuODgxLDIuNDUyLDEuODgsMi44NTEsMy4zNzEuMzgzLDEuNDM0LS4yLDIuODU1LS43NjgsNC4yM2E3LjY1OCw3LjY1OCwwLDAsMC0uNzY0LDIuNjI4LDcuNjQ2LDcuNjQ2LDAsMCwwLC43NjQsMi42MjhjLjU2NiwxLjM3NSwxLjE1MSwyLjguNzY4LDQuMjMtLjQsMS40OTEtMS43LDIuNDktMi44NTEsMy4zNzFhNy40LDcuNCwwLDAsMC0xLjksMS44LDcuNTM0LDcuNTM0LDAsMCwwLS42MzMsMi41OTRjLS4yLDEuNDkxLS40MDUsMy4wMzMtMS40NzgsNC4xMDVzLTIuNjEzLDEuMjc4LTQuMSwxLjQ3N2E3LjUwOSw3LjUwOSwwLDAsMC0yLjU5NS42MzUsNy40MDgsNy40MDgsMCwwLDAtMS44LDEuOWMtLjg4LDEuMTQ5LTEuODc5LDIuNDUyLTMuMzcxLDIuODUxLTEuNDM1LjM4NS0yLjg1NS0uMi00LjIyOS0uNzY4QTcuNjM3LDcuNjM3LDAsMCwwLDM3LDUxLjc4MmE3LjYzNyw3LjYzNywwLDAsMC0yLjYyOS43NjNBOC44OCw4Ljg4LDAsMCwxLDMxLjEsNTMuNDM2Wk0zMS4xLDRhMS43MDgsMS43MDgsMCwwLDAtLjQ0MS4wNTRjLS44MzUuMjIzLTEuNTQ3LDEuMTUyLTIuMywyLjEzNUE4Ljg1LDguODUsMCwwLDEsMjUuOTY4LDguNmE4Ljk2OSw4Ljk2OSwwLDAsMS0zLjMzMi44ODUsNS40MTMsNS40MTMsMCwwLDAtMi45NTUuOTEsNS40LDUuNCwwLDAsMC0uOTEsMi45NTUsOC45NzcsOC45NzcsMCwwLDEtLjg4NCwzLjMzMSw4Ljg1OCw4Ljg1OCwwLDAsMS0yLjQxNiwyLjM5MWMtLjk4My43NTQtMS45MTEsMS40NjYtMi4xMzUsMi4zYTUuMzY2LDUuMzY2LDAsMCwwLC42ODUsMi45NTEsOS4xMTEsOS4xMTEsMCwwLDEsLjkxNSwzLjM5LDkuMSw5LjEsMCwwLDEtLjkxNSwzLjM5LDUuMzYyLDUuMzYyLDAsMCwwLS42ODUsMi45NTJjLjIyNC44MzUsMS4xNTIsMS41NDYsMi4xMzUsMi4zYTguODU4LDguODU4LDAsMCwxLDIuNDE2LDIuMzkxLDguOTU4LDguOTU4LDAsMCwxLC44ODQsMy4zMzEsNS40MTksNS40MTksMCwwLDAsLjkxMSwyLjk1Niw1LjQxLDUuNDEsMCwwLDAsMi45NTUuOTA5LDguOTY0LDguOTY0LDAsMCwxLDMuMzMxLjg4NSw4LjgxMiw4LjgxMiwwLDAsMSwyLjM5LDIuNDE2Yy43NTQuOTgyLDEuNDY2LDEuOTExLDIuMywyLjEzNGE1LjM4Niw1LjM4NiwwLDAsMCwyLjk1LS42ODVBOS4xMTUsOS4xMTUsMCwwLDEsMzcsNDkuNzgyYTkuMTE1LDkuMTE1LDAsMCwxLDMuMzkxLjkxNCw1LjM0NSw1LjM0NSwwLDAsMCwyLjk1MS42ODVjLjgzNS0uMjIzLDEuNTQ3LTEuMTUyLDIuMy0yLjEzNWE4LjgzNiw4LjgzNiwwLDAsMSwyLjM5LTIuNDE1LDguOTcsOC45NywwLDAsMSwzLjMzMi0uODg2LDUuNCw1LjQsMCwwLDAsMi45NTUtLjkwOSw1LjQwNyw1LjQwNywwLDAsMCwuOTEtMi45NTUsOC45NzIsOC45NzIsMCwwLDEsLjg4NC0zLjMzMSw4Ljg0Nyw4Ljg0NywwLDAsMSwyLjQxNi0yLjM5MmMuOTgzLS43NTQsMS45MTEtMS40NjUsMi4xMzUtMi4zYTUuMzY2LDUuMzY2LDAsMCwwLS42ODUtMi45NTEsOS4xLDkuMSwwLDAsMS0uOTE1LTMuMzksOS4xMTEsOS4xMTEsMCwwLDEsLjkxNS0zLjM5LDUuMzU3LDUuMzU3LDAsMCwwLC42ODUtMi45NTFjLS4yMjQtLjgzNS0xLjE1Mi0xLjU0Ni0yLjEzNS0yLjNhOC44NTgsOC44NTgsMCwwLDEtMi40MTYtMi4zOTEsOC45NjMsOC45NjMsMCwwLDEtLjg4NC0zLjMzMSw1LjQxNiw1LjQxNiwwLDAsMC0uOTExLTIuOTU2LDUuNCw1LjQsMCwwLDAtMi45NTUtLjkwOUE4Ljk4Myw4Ljk4MywwLDAsMSw0OC4wMzIsOC42YTguODQsOC44NCwwLDAsMS0yLjM5LTIuNDE2Yy0uNzU0LS45ODMtMS40NjYtMS45MTEtMi4zLTIuMTM1YTUuMzY5LDUuMzY5LDAsMCwwLTIuOTUuNjg2QTkuMTM0LDkuMTM0LDAsMCwxLDM3LDUuNjUzYTkuMTM0LDkuMTM0LDAsMCwxLTMuMzkxLS45MTRBNy40NzEsNy40NzEsMCwwLDAsMzEuMSw0WiIvPjxwYXRoIGQ9Ik0zNyw0NC43ODRBMTcuMDY3LDE3LjA2NywwLDEsMSw1NC4wNjYsMjcuNzE3LDE3LjA4NiwxNy4wODYsMCwwLDEsMzcsNDQuNzg0Wm0wLTMyLjEzM0ExNS4wNjcsMTUuMDY3LDAsMSwwLDUyLjA2NiwyNy43MTcsMTUuMDgzLDE1LjA4MywwLDAsMCwzNywxMi42NTFaIi8+PHBhdGggZD0iTTQ2LjksNzJhMSwxLDAsMCwxLS45MTUtLjZMMzcuMTI5LDUxLjMzM2ExLDEsMCwwLDEsMS44My0uODA3bDcuOSwxNy45MTUsMi43MjUtNi42NzNhMSwxLDAsMCwxLDEuMzQxLS41MzJsNi4zLDIuODczTDQ5Ljg2LDQ3LjQ0NWExLDEsMCwwLDEsMS44My0uODA5bDguNDI2LDE5LjA3MWExLDEsMCwwLDEtMS4zMywxLjMxNGwtNy43NDItMy41MzMtMy4yMjEsNy44OWExLDEsMCwwLDEtLjkxMi42MjJaIi8+PHBhdGggZD0iTTQyLjQ3LDM2LjYyNWEuOTk0Ljk5NCwwLDAsMS0uNDY1LS4xMTVMMzcsMzMuODc5LDMyLDM2LjUxYTEsMSwwLDAsMS0xLjQ1LTEuMDU0bC45NTYtNS41NzItNC4wNDktMy45NDdhMSwxLDAsMCwxLC41NTUtMS43MDZsNS41OTUtLjgxMiwyLjUtNS4wN2ExLjA0MSwxLjA0MSwwLDAsMSwxLjc5MiwwbDIuNSw1LjA3LDUuNTk1LjgxMmExLDEsMCwwLDEsLjU1NSwxLjcwNkw0Mi41LDI5Ljg4NGwuOTU2LDUuNTcyYTEsMSwwLDAsMS0uOTg1LDEuMTY5Wk0zNywzMS43NWExLDEsMCwwLDEsLjQ2NS4xMTRMNDEuMTQyLDMzLjhsLS43LTQuMDkzYTEsMSwwLDAsMSwuMjg4LS44ODVsMi45NzMtMi45LTQuMTA5LS42YTEsMSwwLDAsMS0uNzUzLS41NDhMMzcsMjEuMDUxbC0xLjgzOCwzLjcyNGExLDEsMCwwLDEtLjc1My41NDhsLTQuMTA5LjYsMi45NzMsMi45YTEsMSwwLDAsMSwuMjg4Ljg4NWwtLjcsNC4wOTMsMy42NzctMS45MzNBMSwxLDAsMCwxLDM3LDMxLjc1WiIvPjwvc3ZnPgo="
                />
                <div style="font-size: 13px;">채택</div>
              </div>
              <div v-if="answer.type == '일반채택'" class="answer-button">
                <img
                  style="width:25px; margin: 9px 0 0 0;"
                  src="data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMSIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHhtbG5zOnN2Z2pzPSJodHRwOi8vc3ZnanMuY29tL3N2Z2pzIiB3aWR0aD0iNTEyIiBoZWlnaHQ9IjUxMiIgeD0iMCIgeT0iMCIgdmlld0JveD0iMCAwIDUxMS43NiA1MTEuNzYiIHN0eWxlPSJlbmFibGUtYmFja2dyb3VuZDpuZXcgMCAwIDUxMiA1MTIiIHhtbDpzcGFjZT0icHJlc2VydmUiPjxnPgo8cG9seWdvbiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHN0eWxlPSIiIHBvaW50cz0iMTUxLjM1MiwyMjEuMjQ4IDM5LjU5Miw0MzEuODA4IDE0MS4wOCw0MjIuNDMyIDE5MC4yLDUxMS43NDQgMzAxLjk2LDMwMS4xODQgIiBmaWxsPSIjZTk1NDU0IiBkYXRhLW9yaWdpbmFsPSIjZTk1NDU0Ij48L3BvbHlnb24+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+Cgk8cGF0aCBzdHlsZT0iIiBkPSJNMTY0LjE1Miw0NTAuNDMyYzMuMTM2LTYuNzM2LTAuMTQ0LTE0Ljc4NC02LjcwNC0xOC4yNzJsLTYuNDMyLTMuNDI0ICAgYy02LjQzMi0zLjY2NC04Ljg4LTExLjgwOC01LjM3Ni0xOC40YzMuNTUyLTYuNzA0LDExLjkwNC05LjI2NCwxOC42MDgtNS43MTJsMTguMTc2LDkuNjQ4YzUuNjE2LDIuOTc2LDEyLjY1NiwwLjgxNiwxNS41NTItNC44MzIgICBsMC40NDgtMC44NjRjMy4zMjgtNS4zMjgsMS4zNi0xMi4zNjgtNC4xNzYtMTUuMjk2bC01NC4zNjgtMjguODQ4Yy02LjYyNC0zLjUyLTkuODcyLTExLjUzNi02Ljc4NC0xOC4yODggICBjMy40MDgtNy4xODQsMTIuMDE2LTEwLjAxNiwxOC45NDQtNi4zMzZsNTAuNDMyLDI2Ljc2OGM1LjYxNiwyLjk3NiwxMi42NTYsMC44MTYsMTUuNjMyLTQuOGwwLjI4OC0wLjczNmwwLjA4LTAuMTYgICBjMy4yMTYtNS44ODgsMC45Ni0xMy4yOC00Ljk3Ni0xNi40MzJsLTI4LjI4OC0xNS4wMDhjLTYuNjI0LTMuNTItOS44NzItMTEuNTM2LTYuNzM2LTE4LjM2OCAgIGMzLjM3Ni03LjEwNCwxMi4wMTYtMTAuMDE2LDE4Ljk0NC02LjMzNmwzMS4yNDgsMTYuNTkyYzUuODQsMy4xMDQsMTIuODE2LDAuNTEyLDE1Ljg0LTUuMzc2YzAuMDQ4LTAuMDgsMC4wNDgtMC4wOCwwLjA4LTAuMTYgICBjMC4wNDgtMC4wOCwwLjA0OC0wLjA4LDAuMDgtMC4xNmMzLjE4NC01LjgwOCwxLjQyNC0xMy4wNC00LjQzMi0xNi4xMjhsLTMzLjcxMi0xNy44ODhjLTYuNTQ0LTMuNDcyLTkuNzkyLTExLjQ4OC02LjcwNC0xOC4yNCAgIGMzLjQwOC03LjE4NCwxMi4wMTYtMTAuMDE2LDE4Ljk0NC02LjMzNmw4My4xNjgsNDQuMTQ0bC0xMTEuNzYsMjEwLjU2bC0zMC44LTU1Ljk4NCAgIEMxNjEuMzUyLDQ1NC40MTYsMTYzLjA4LDQ1Mi43NTIsMTY0LjE1Miw0NTAuNDMyeiIgZmlsbD0iI2VkNjM2MiIgZGF0YS1vcmlnaW5hbD0iI2VkNjM2MiI+PC9wYXRoPgoJPHBvbHlnb24gc3R5bGU9IiIgcG9pbnRzPSIzNjAuNDA4LDIyMS4yNDggNDcyLjE2OCw0MzEuODA4IDM3MC42OCw0MjIuNDMyIDMyMS41Niw1MTEuNzQ0IDIwOS44LDMwMS4xODQgICIgZmlsbD0iI2VkNjM2MiIgZGF0YS1vcmlnaW5hbD0iI2VkNjM2MiI+PC9wb2x5Z29uPgo8L2c+CjxwYXRoIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgc3R5bGU9IiIgZD0iTTM0Ny42MDgsNDUwLjQzMmMtMy4xMzYtNi43MzYsMC4xNDQtMTQuNzg0LDYuNzA0LTE4LjI3Mmw2LjQzMi0zLjQyNCAgYzYuNDMyLTMuNjY0LDguODgtMTEuODA4LDUuMzc2LTE4LjRjLTMuNTUyLTYuNzA0LTExLjkwNC05LjI2NC0xOC42MDgtNS43MTJsLTE4LjE3Niw5LjY0OGMtNS42MTYsMi45NzYtMTIuNjU2LDAuODE2LTE1LjU1Mi00LjgzMiAgbC0wLjQ0OC0wLjg2NGMtMy4zMjgtNS4zMjgtMS4zNi0xMi4zNjgsNC4xNzYtMTUuMjk2bDU0LjM4NC0yOC44NjRjNi42MjQtMy41Miw5Ljg3Mi0xMS41MzYsNi43ODQtMTguMjg4ICBjLTMuNDA4LTcuMTg0LTEyLjAxNi0xMC4wMTYtMTguOTQ0LTYuMzM2bC01MC40MzIsMjYuNzY4Yy01LjYxNiwyLjk3Ni0xMi42NTYsMC44MTYtMTUuNjMyLTQuOGwtMC4yODgtMC43MzZsLTAuMDgtMC4xNiAgYy0zLjIxNi01Ljg4OC0wLjk2LTEzLjI4LDQuOTc2LTE2LjQzMmwyOC4yODgtMTUuMDA4YzYuNjI0LTMuNTIsOS44NzItMTEuNTM2LDYuNzM2LTE4LjM2OCAgYy0zLjM3Ni03LjEwNC0xMi4wMTYtMTAuMDE2LTE4Ljk0NC02LjMzNmwtMzEuMjgsMTYuNjI0Yy01Ljg0LDMuMTA0LTEyLjgxNiwwLjUxMi0xNS44NC01LjM3NmMtMC4wNDgtMC4wOC0wLjA0OC0wLjA4LTAuMDgtMC4xNiAgYy0wLjA0OC0wLjA4LTAuMDQ4LTAuMDgtMC4wOC0wLjE2Yy0zLjE4NC01LjgwOC0xLjQyNC0xMy4wNCw0LjQzMi0xNi4xMjhsMzMuNzEyLTE3Ljg4OGM2LjU0NC0zLjQ3Miw5Ljc5Mi0xMS40ODgsNi43MDQtMTguMjQgIGMtMy40MDgtNy4xODQtMTIuMDE2LTEwLjAxNi0xOC45NDQtNi4zMzZMMjA5LjgxNiwzMDEuMmwxMTEuNzYsMjEwLjU2bDMwLjgtNTUuOTg0QzM1MC40MDgsNDU0LjQxNiwzNDguNjgsNDUyLjc1MiwzNDcuNjA4LDQ1MC40MzIgIHoiIGZpbGw9IiNlOTU0NTQiIGRhdGEtb3JpZ2luYWw9IiNlOTU0NTQiPjwvcGF0aD4KPGNpcmNsZSB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHN0eWxlPSIiIGN4PSIyNTUuODgiIGN5PSIxODYuNzM2IiByPSIxODYuNzM2IiBmaWxsPSIjZmZjYzViIiBkYXRhLW9yaWdpbmFsPSIjZmZjYzViIj48L2NpcmNsZT4KPHBhdGggeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiBzdHlsZT0iIiBkPSJNMjgxLjIwOCwxNjUuMzEySDE2Mi44NzJjLTkuMiwwLTE2Ljc1Mi03LjU1Mi0xNi43NTItMTYuNzUybDAuMTI4LTEuMTUydi0wLjI1NiAgYy0wLjEyOC05LjcxMiw3Ljc5Mi0xNy42NDgsMTcuNTItMTcuNjQ4aDgyLjAxNmMxMC44NjQsMCwyMC40NjQtOC4wNDgsMjEuMTA0LTE4LjkyOEMyNjcuNCw5OS4yLDI1OC4zMjgsODkuNiwyNDYuOTM2LDg5LjZoLTg2Ljg4ICBjLTkuNTg0LDAtMTYuNzUyLTguMDQ4LTE2LjYyNC0xNy42NDhjMC0wLjEyOCwwLTAuMTI4LDAtMC4yNTZzMC0wLjEyOCwwLTAuMjU2Yy0wLjEyOC05LjU4NCw3LjAyNC0xNy42NDgsMTYuNjI0LTE3LjY0OGg5MC45MTIgIGMxMC43MzYsMCwyMC4zMzYtOC4wNDgsMjAuOTc2LTE4LjhjMC41MTItMTEuNTA0LTguNTYtMjAuOTc2LTE5Ljk1Mi0yMC45NzZoLTY2LjU3NkMxMTcuNTI4LDQyLDY5LjY4OCwxMDguNzUyLDY5LjY4OCwxODYuNzM2ICBzNDcuODQsMTQ0LjcyLDExNS43NDQsMTcyLjY3Mmg4NC41MjhjMTAuNzM2LDAsMjAuMzM2LTguMDQ4LDIwLjk3Ni0xOC44YzAuNTEyLTExLjUwNC04LjU2LTIwLjk2LTE5Ljk1Mi0yMC45NmwtOTYuMTYtMC4wMTYgIGMtMTEuMzQ0LDAtMjAuNDgtOS40ODgtMTkuODcyLTIwLjk3NmMwLjU2LTEwLjc1MiwxMC4yMjQtMTguODMyLDIwLjk5Mi0xOC44MzJoMzcuMjhjMTAuNzItMC4zMDQsMTkuMzc2LTkuMDg4LDE5LjM3Ni0xOS44ODggIGMwLTEwLjk5Mi04Ljk0NC0xOS45NTItMTkuOTUyLTE5Ljk1MmgtNTYuNTEyYy05LjIsMC0xNi43NTItNy41MzYtMTYuNjI0LTE2Ljc1MnYtMS40MDhjLTAuNjQtOS4wNzIsNi42NTYtMTYuNzUyLDE1LjcyOC0xNi43NTIgIGgxMjQuODE2YzEwLjg2NCwwLDIwLjQ2NC04LjA0OCwyMS4xMDQtMTguOEMzMDEuNjcyLDE3NC43ODQsMjkyLjU4NCwxNjUuMzEyLDI4MS4yMDgsMTY1LjMxMnoiIGZpbGw9IiNmZGJjNGIiIGRhdGEtb3JpZ2luYWw9IiNmZGJjNGIiPjwvcGF0aD4KPHBhdGggeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiBzdHlsZT0iIiBkPSJNMjU1Ljg4LDMyMC4wMTZjLTczLjUwNCwwLTEzMy4yOTYtNTkuODA4LTEzMy4yOTYtMTMzLjI5NlMxODIuMzc2LDUzLjQ0LDI1NS44OCw1My40NCAgczEzMy4yOTYsNTkuODA4LDEzMy4yOTYsMTMzLjI5NlMzMjkuMzg0LDMyMC4wMTYsMjU1Ljg4LDMyMC4wMTZ6IiBmaWxsPSIjZmZkYjcwIiBkYXRhLW9yaWdpbmFsPSIjZmZkYjcwIj48L3BhdGg+CjxwYXRoIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgc3R5bGU9Im9wYWNpdHk6MC4yO2ZpbGw6I0ZGRkZGRjtlbmFibGUtYmFja2dyb3VuZDpuZXcgICAgOyIgZD0iTTM4OS4xNzYsMTg2LjczNmMwLTcwLjkxMi01NS43MTItMTI4LjkxMi0xMjUuNjQ4LTEzMi45MTIgIGgtMTIuNDE2Yy04LjE5MiwwLTE1LjUwNCw2LjE0NC0xNiwxNC4zMzZjLTAuMzg0LDguNzY4LDYuNTI4LDE2LDE1LjIxNiwxNmw3My4zNDQsMC4wMTZjOC42NTYsMCwxNS42MTYsNy4yMzIsMTUuMTUyLDE2ICBjLTAuNDMyLDguMjA4LTcuNzkyLDE0LjM2OC0xNi4wMTYsMTQuMzY4aC0yOC40MzJjLTguMTc2LDAuMjQtMTQuNzg0LDYuOTI4LTE0Ljc4NCwxNS4xNjhjMCw4LjM4NCw2LjgzMiwxNS4yMTYsMTUuMjE2LDE1LjIxNiAgaDQzLjEwNGM3LjAyNCwwLDEyLjc2OCw1Ljc2LDEyLjY3MiwxMi43Njh2MS4wNTZjMC40OCw2LjkyOC01LjA3MiwxMi43NjgtMTIsMTIuNzY4SDI0My40Yy04LjI4OCwwLTE1LjYsNi4xNDQtMTYuMDk2LDE0LjMzNiAgYy0wLjM4NCw4Ljc2OCw2LjUyOCwxNiwxNS4yMTYsMTZoOTAuMjRjNy4wMjQsMCwxMi43NjgsNS43NiwxMi43NjgsMTIuNzY4bC0wLjA5NiwwLjg4djAuMTkyICBjMC4wOTYsNy40MDgtNS45NTIsMTMuNDU2LTEzLjM2LDEzLjQ1NmgtNjIuNTZjLTguMjg4LDAtMTUuNiw2LjE0NC0xNi4wOTYsMTQuNDMyYy0wLjM4NCw4LjY3Miw2LjUyOCwxNiwxNS4yMTYsMTZoNjYuMjU2ICBjNy4zMTIsMCwxMi43NjgsNi4xNDQsMTIuNjcyLDEzLjQ1NmMwLDAuMDk2LDAsMC4wOTYsMCwwLjE5MmMwLDAuMDk2LDAsMC4wOTYsMCwwLjE5MmMwLjA5Niw3LjMxMi01LjM2LDEzLjQ1Ni0xMi42NzIsMTMuNDU2ICBIMjY1LjU2Yy04LjE5MiwwLTE1LjUwNCw2LjE0NC0xNiwxNC4zMzZjLTAuMzg0LDguNzg0LDYuNTI4LDE2LDE1LjIxNiwxNmgxOC4yODhDMzQzLjU5MiwzMDQuNjA4LDM4OS4xNzYsMjUwLjg5NiwzODkuMTc2LDE4Ni43MzZ6ICAiIGZpbGw9IiI+PC9wYXRoPgo8cGF0aCB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHN0eWxlPSIiIGQ9Ik0zNDguOTM2LDE1Ny4yMTZoLTY0LjgzMmwtMjAuMDQ4LTYxLjY2NGMtMi41NzYtNy45Mi0xMy43OTItNy45Mi0xNi4zNjgsMGwtMjAuMDMyLDYxLjY2NGgtNjQuODMyICBjLTguMzM2LDAtMTEuODA4LDEwLjY3Mi01LjA1NiwxNS41NjhsNTIuNDQ4LDM4LjExMmwtMjAuMDMyLDYxLjY2NGMtMi41NzYsNy45Miw2LjQ5NiwxNC41MTIsMTMuMjQ4LDkuNjE2bDUyLjQ0OC0zOC4xMTIgIGw1Mi40NDgsMzguMTEyYzYuNzUyLDQuODk2LDE1LjgyNC0xLjY5NiwxMy4yNDgtOS42MTZsLTIwLjAzMi02MS42NjRsNTIuNDQ4LTM4LjExMiAgQzM2MC43MjgsMTY3Ljg4OCwzNTcuMjcyLDE1Ny4yMTYsMzQ4LjkzNiwxNTcuMjE2eiIgZmlsbD0iI2VjOTkyMiIgZGF0YS1vcmlnaW5hbD0iI2VjOTkyMiI+PC9wYXRoPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8L2c+PC9zdmc+"
                />
              </div>
              <div
                v-if="questionNickname == nickname && answer.nickname != nickname"
                class="answer-button"
                @click="openRequestConference(answer)"
              >
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
                <div style="font-size: 13px; margin-left: 2px;">{{ answer.likeCount }}</div>
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
              <img src="@/assets/edit-button.png" width="20px" />
            </div>
          </div>
          <div class="answer-comment-area">
            <div class="answer-comment-tab font-break">
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
            <CommentsCreate
              v-if="isLoggedIn"
              v-show="commentListOpen[idx]"
              :answerId="answer.answerId"
            />
            <div
              v-else
              v-show="commentListOpen[idx]"
              class="nonlogin-comment"
              @click="$router.push({ name: 'Login' })"
            >
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
import RequesetConference from "./modal/RequesetConference.vue";
import AnswerDelete from "./modal/AnswerDelete.vue";
import AnswerSelect from "./modal/AnswerSelect.vue";
import LoginModal from "@/components/main/LoginModal";

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
      CKEditor: "",
    };
  },
  props: {
    questionNickname: String,
    questionState: String,
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
          nickname: this.userNickname,
        },
        headers: { Authorization: "Bearer " + this.accessToken },
      })
        .then((res) => {
          this.answerList = res.data.answerList.sort(function(a, b) {
            return a.type != "일반채택" && b.type == "일반채택"
              ? 1
              : a.type == "일반채택" && b.type != "일반채택"
              ? -1
              : 0;
          });
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
        this.$modal.show(
          LoginModal,
          {
            modal: this.$modal,
          },
          {
            name: "dynamic-modal",
            width: "600px",
            height: "250px",
            draggable: false,
          }
        );
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
        this.$modal.show(
          LoginModal,
          {
            modal: this.$modal,
          },
          {
            name: "dynamic-modal",
            width: "600px",
            height: "250px",
            draggable: false,
          }
        );
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
          Authorization: "Bearer " + this.accessToken,
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
          Authorization: "Bearer " + this.accessToken,
        },
      })
        .then(() => {
          this.getAnswerList();
        })
        .catch((err) => {
          console.log(err);
        });
    },
    deleteAnswerCheck(answer) {
      this.$modal.show(
        AnswerDelete,
        {
          answer: answer,
          modal: this.$modal,
        },
        {
          name: "dynamic-modal",
          width: "600px",
          height: "250px",
          draggable: false,
        }
      );
    },
    openRequestConference(answer) {
      this.$modal.show(
        RequesetConference,
        {
          answer: answer,
          modal: this.$modal,
        },
        {
          name: "dynamic-modal",
          width: "600px",
          height: "650px",
          draggable: false,
        }
      );
    },
    selectAnswer(answerId) {
      console.log(answerId);
      this.$modal.show(
        AnswerSelect,
        {
          data: {
            answerId: answerId,
            questionId: this.$route.params.questionId,
          },
          modal: this.$modal,
        },
        {
          name: "dynamic-modal",
          width: "600px",
          height: "250px",
          draggable: false,
        }
      );
    },
    goUserProfile(nickname) {
      if (nickname !== this.userNickname) {
        this.$router.push({
          name: "Profile",
          params: {
            nickname: nickname,
          },
        });
      } else {
        this.$router.push({
          path: `/my-profile/${this.userNickname}`,
        });
      }
    },
  },
  mounted() {
    this.nickname = this.userNickname;
  },
  created() {
    this.getAnswerList();
  },
  computed: {
    ...mapState({
      commentDeleteTrigger: (state) => state.questions.commentDeleteTrigger,
      answerChangeTrigger: (state) => state.questions.answerChangeTrigger,
      accessToken: (state) => state.auth.accessToken,
      userNickname: (state) => state.auth.userNickname,
    }),
    ...mapGetters(["isLoggedIn"]),
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
    },
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

.answer-user-profile-image {
  cursor: pointer;
}

.answer-user-nickname {
  cursor: pointer;
}
</style>
