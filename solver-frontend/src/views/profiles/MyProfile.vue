<template>
  <div class="for-center">
    <div class="profile solver-font font-break">
      <!-- profile-image section -->
      <div class="profile-img-section">
        <img style="object-fit: cover;" :src="userProfileInfo.profileUrl" class="profile-img" />
        <label class="profile-img-label" for="profile-img-input" type="file"
          ><img src="@/assets/edit-button.png" width="20px"
        /></label>
        <form id="profileImgForm" enctype="multipart/form-data">
          <input @change="profileImgChange()" id="profile-img-input" type="file" />
        </form>
      </div>

      <!-- profile-text_info section -->
      <div class="profile-info-section">
        <div class="nickname">
          <div style="display:flex; align-items:center;">
            {{ userProfileInfo.nickname }}
            <button @click="openReservation" class="check-reservation interval">
              화상시간 예약 확인
            </button>
          </div>
          <div v-if="isLogin" class="edit-img" @click="editRequest">
            <span>수정</span>
            <img src="@/assets/edit-button.png" width="20px" />
          </div>
        </div>
        <div
          style="color:#84898C; display:flex; align-items: center; justify-content:space-between;"
        >
          <!-- 한줄 소개 -->
          <div>
            <div v-if="!isEdit">
              <span>한줄 소개</span>
              <span class="content-text-b m-left-1">{{ userProfileInfo.introduction }}</span>
            </div>
            <div v-if="isEdit" @keyup.enter="[editRequest(), editSelfIntro(editInfo.selfIntro)]">
              한줄 소개

              <span>
                <input
                  v-model="editInfo.selfIntro"
                  class="intro-input m-left-1"
                  type="text"
                  :maxlength="maxIntro"
                />
                <!-- @input="inputLengthCheck($event)"> -->
                <span>{{ editInfo.selfIntro.length }} / 30</span>
              </span>

              <button
                v-if="isLogin"
                @click="[editRequest(), editSelfIntro(editInfo.selfIntro)]"
                class="edit-end-button m-left-1"
              >
                수정
              </button>
            </div>
          </div>

          <!-- 개인 URL -->
          <div class="social-url">
            <div v-if="!isEdit">
              <span>
                <img
                  style="width: 20px"
                  src="data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMSIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHhtbG5zOnN2Z2pzPSJodHRwOi8vc3ZnanMuY29tL3N2Z2pzIiB3aWR0aD0iNTEyIiBoZWlnaHQ9IjUxMiIgeD0iMCIgeT0iMCIgdmlld0JveD0iMCAwIDUxMiA1MTIiIHN0eWxlPSJlbmFibGUtYmFja2dyb3VuZDpuZXcgMCAwIDUxMiA1MTIiIHhtbDpzcGFjZT0icHJlc2VydmUiIGNsYXNzPSIiPjxnPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgoJPGc+CgkJPHBhdGggZD0iTTQ3Mi4yMDgsMjAxLjcxMmM5LjI3MS05LjAzNywxMi41NDQtMjIuMyw4LjU0NC0zNC42MTNjLTQuMDAxLTEyLjMxMy0xNC40NDUtMjEuMTE4LTI3LjI1Ny0yMi45NzlsLTExMi4wMy0xNi4yNzkgICAgYy0yLjE5OS0wLjMxOS00LjEtMS43LTUuMDg0LTMuNjk0TDI4Ni4yOCwyMi42MzJjLTUuNzI5LTExLjYxLTE3LjMzMS0xOC44MjItMzAuMjc4LTE4LjgyMmMtMTIuOTQ3LDAtMjQuNTQ5LDcuMjEyLTMwLjI3OCwxOC44MjIgICAgbC01MC4xMDEsMTAxLjUxNmMtMC45ODUsMS45OTMtMi44ODUsMy4zNzQtNS4wODUsMy42OTRMNTguNTEsMTQ0LjEyYy0xMi44MTIsMS44NjEtMjMuMjU1LDEwLjY2Ni0yNy4yNTcsMjIuOTc5ICAgIGMtNC4wMDIsMTIuMzEzLTAuNzI4LDI1LjU3Niw4LjU0NCwzNC42MTNsODEuMDY1LDc5LjAxOWMxLjU5MSwxLjU1MiwyLjMxOCwzLjc4NywxLjk0Miw1Ljk3OGwtMTkuMTM3LDExMS41NzYgICAgYy0yLjE4OCwxMi43NjEsMi45NTgsMjUuNDE0LDEzLjQzMiwzMy4wMjRjMTAuNDc0LDcuNjEyLDI0LjEwMiw4LjU5NSwzNS41NiwyLjU3MmwxMDAuMjAxLTUyLjY3OSAgICBjMS45NjgtMS4wMzUsNC4zMTctMS4wMzUsNi4yODYsMGwxMDAuMjAyLDUyLjY3OWM0Ljk4NCwyLjYyLDEwLjM3NywzLjkxNSwxNS43NDQsMy45MTRjNi45NywwLDEzLjg5Ni0yLjE4NCwxOS44MTMtNi40ODcgICAgYzEwLjQ3NC03LjYxMSwxNS42MjEtMjAuMjY1LDEzLjQzMi0zMy4wMjRsLTE5LjEzNy0xMTEuNTc2Yy0wLjM3NS0yLjE5MSwwLjM1MS00LjQyNiwxLjk0Mi01Ljk3OEw0NzIuMjA4LDIwMS43MTJ6ICAgICBNMzYyLjU3OSwyOTEuMjc2bDE5LjEzNywxMTEuNTc4YzAuNjQsMy43MzQtMS42NjUsNS44NjMtMi42ODYsNi42MDRjLTEuMDIyLDAuNzQtMy43NiwyLjI3Ny03LjExMiwwLjUxM2wtMTAwLjIwMi01Mi42NzkgICAgYy00LjkxOS0yLjU4NS0xMC4zMTUtMy44NzktMTUuNzEyLTMuODc5Yy01LjM5NywwLTEwLjc5NCwxLjI5NC0xNS43MTIsMy44NzhsLTEwMC4yMDEsNTIuNjc4ICAgIGMtMy4zNTQsMS43NjMtNi4wOTEsMC4yMjgtNy4xMTItMC41MTNjLTEuMDIxLTAuNzQxLTMuMzI3LTIuODctMi42ODYtNi42MDRsMTkuMTM3LTExMS41NzYgICAgYzEuODc5LTEwLjk1NS0xLjc1LTIyLjEyNy05LjcxMS0yOS44ODZsLTgxLjA2NS03OS4wMTljLTIuNzEzLTIuNjQ2LTIuMDk5LTUuNzIzLTEuNzA4LTYuOTIzICAgIGMwLjM4OS0xLjIwMSwxLjcwMi00LjA1Miw1LjQ1MS00LjU5NmwxMTIuMDI3LTE2LjI3OWMxMC45OTktMS41OTgsMjAuNTA0LTguNTAyLDI1LjQyNC0xOC40NzFsNTAuMTAxLTEwMS41MTYgICAgYzEuNjc3LTMuMzk3LDQuNzkzLTMuNzY0LDYuMDU2LTMuNzY0YzEuMjYxLDAsNC4zNzcsMC4zNjYsNi4wNTUsMy43NjR2MC4wMDFsNTAuMTAxLDEwMS41MTYgICAgYzQuOTE5LDkuOTY5LDE0LjQyMywxNi44NzMsMjUuNDIyLDE4LjQ3MWwxMTIuMDI5LDE2LjI3OWMzLjc0OSwwLjU0NCw1LjA2MSwzLjM5NSw1LjQ1MSw0LjU5NiAgICBjMC4zOSwxLjIwMSwxLjAwNSw0LjI3OS0xLjcwOSw2LjkyM2wtODEuMDY1LDc5LjAxOUMzNjQuMzI5LDI2OS4xNDksMzYwLjcsMjgwLjMyMSwzNjIuNTc5LDI5MS4yNzZ6IiBmaWxsPSIjODQ4OThjIiBkYXRhLW9yaWdpbmFsPSIjMDAwMDAwIiBzdHlsZT0iIiBjbGFzcz0iIj48L3BhdGg+Cgk8L2c+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KCTxnPgoJCTxwYXRoIGQ9Ik00MTMuNzgzLDIyLjYyNWMtNi4wMzYtNC4zODQtMTQuNDgxLTMuMDQ2LTE4Ljg2NSwyLjk4OGwtMTQuMzM3LDE5LjczMmMtNC4zODQsNi4wMzQtMy4wNDcsMTQuNDgxLDIuOTg4LDE4Ljg2NSAgICBjMi4zOTksMS43NDEsNS4xNzYsMi41OCw3LjkyOCwyLjU4YzQuMTc3LDAsOC4yOTUtMS45MzEsMTAuOTM3LTUuNTY3bDE0LjMzNy0xOS43MzIgICAgQzQyMS4xNTUsMzUuNDU2LDQxOS44MTgsMjcuMDA5LDQxMy43ODMsMjIuNjI1eiIgZmlsbD0iIzg0ODk4YyIgZGF0YS1vcmlnaW5hbD0iIzAwMDAwMCIgc3R5bGU9IiIgY2xhc3M9IiI+PC9wYXRoPgoJPC9nPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+Cgk8Zz4KCQk8cGF0aCBkPSJNMTMxLjM2LDQ1LjI2NWwtMTQuMzM3LTE5LjczMmMtNC4zODMtNi4wMzItMTIuODI5LTcuMzctMTguODY1LTIuOTg4Yy02LjAzNCw0LjM4NC03LjM3MiwxMi44MzEtMi45ODgsMTguODY1ICAgIGwxNC4zMzcsMTkuNzMyYzIuNjQzLDMuNjM5LDYuNzYxLDUuNTY5LDEwLjkzOSw1LjU2OWMyLjc1MywwLDUuNTMxLTAuODM5LDcuOTI3LTIuNTgxQzEzNC40MDcsNTkuNzQ3LDEzNS43NDUsNTEuMywxMzEuMzYsNDUuMjY1ICAgIHoiIGZpbGw9IiM4NDg5OGMiIGRhdGEtb3JpZ2luYWw9IiMwMDAwMDAiIHN0eWxlPSIiIGNsYXNzPSIiPjwvcGF0aD4KCTwvZz4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgoJPGc+CgkJPHBhdGggZD0iTTQ5LjU1MiwzMDYuODI5Yy0yLjMwNS03LjA5My05LjkyNC0xMC45NzYtMTcuMDE5LTguNjcxbC0yMy4xOTcsNy41MzhjLTcuMDk1LDIuMzA1LTEwLjk3Niw5LjkyNi04LjY3MSwxNy4wMTkgICAgYzEuODU0LDUuNzA5LDcuMTQ5LDkuMzM3LDEyLjg0Miw5LjMzN2MxLjM4MywwLDIuNzktMC4yMTUsNC4xNzctMC42NjZsMjMuMTk3LTcuNTM4ICAgIEM0Ny45NzUsMzIxLjU0Myw1MS44NTcsMzEzLjkyNCw0OS41NTIsMzA2LjgyOXoiIGZpbGw9IiM4NDg5OGMiIGRhdGEtb3JpZ2luYWw9IiMwMDAwMDAiIHN0eWxlPSIiIGNsYXNzPSIiPjwvcGF0aD4KCTwvZz4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgoJPGc+CgkJPHBhdGggZD0iTTI1Ni4wMDUsNDU2Ljc4NmMtNy40NTksMC0xMy41MDYsNi4wNDctMTMuNTA2LDEzLjUwNnYyNC4zOTJjMCw3LjQ1OSw2LjA0NywxMy41MDYsMTMuNTA2LDEzLjUwNiAgICBjNy40NTksMCwxMy41MDYtNi4wNDcsMTMuNTA2LTEzLjUwNnYtMjQuMzkyQzI2OS41MTEsNDYyLjgzMiwyNjMuNDY1LDQ1Ni43ODYsMjU2LjAwNSw0NTYuNzg2eiIgZmlsbD0iIzg0ODk4YyIgZGF0YS1vcmlnaW5hbD0iIzAwMDAwMCIgc3R5bGU9IiIgY2xhc3M9IiI+PC9wYXRoPgoJPC9nPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+Cgk8Zz4KCQk8cGF0aCBkPSJNNTAyLjY2NCwzMDUuNzE1bC0yMy4xOTctNy41MzhjLTcuMDkyLTIuMzAzLTE0LjcxNCwxLjU3Ny0xNy4wMTksOC42NzJjLTIuMzA1LDcuMDk1LDEuNTc2LDE0LjcxNCw4LjY3MSwxNy4wMTkgICAgbDIzLjE5Nyw3LjUzOGMxLjM4NywwLjQ1LDIuNzkzLDAuNjY0LDQuMTc2LDAuNjY0YzUuNjk0LDAsMTAuOTg5LTMuNjI5LDEyLjg0My05LjMzNyAgICBDNTEzLjY0LDMxNS42MzksNTA5Ljc1OCwzMDguMDIsNTAyLjY2NCwzMDUuNzE1eiIgZmlsbD0iIzg0ODk4YyIgZGF0YS1vcmlnaW5hbD0iIzAwMDAwMCIgc3R5bGU9IiIgY2xhc3M9IiI+PC9wYXRoPgoJPC9nPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjwvZz48L3N2Zz4="
                />
                <a :href="userProfileInfo.linkText" class="profile-link" target="_blank"
                  >프로필 링크</a
                >
              </span>
            </div>
            <div v-if="isEdit" @keyup.enter="[editRequest(), editSelfUrl(editInfo.selfUrl)]">
              URL
              <span
                ><input v-model="editInfo.selfUrl" class="url-input m-left-1" type="text"
              /></span>
              <button
                v-if="isLogin"
                @click="[editRequest(), editSelfUrl(editInfo.selfUrl)]"
                class="edit-end-button m-left-1"
              >
                수정
              </button>
            </div>
          </div>
        </div>

        <!-- 구분선 -->
        <hr class="line" />

        <div class="profile-info m-top-3">
          <!-- profile-info의 first child(left) -->
          <!-- 카테고리 -->
          <div>
            <div style="display:flex; align-items:center;">
              <span class="subheading">주 활동분야</span>
              <span
                v-for="(field, index) in userProfileInfo.favoriteFieldNameList"
                :key="'F' + index"
                class="interval"
              >
                {{ field }}
              </span>
              <img
                src="@/assets/edit-button.png"
                width="20px"
                v-if="isLogin"
                @click="requestEditCategory"
                class="edit-img"
              />
            </div>

            <br />

            <!-- 카테고리 수정 -->
            <div class="category-edit-box" v-if="isCategoryEdit">
              대분류
              <span v-for="(mainCtg, index) in mainCtgs" :key="'M' + index">
                <button @click="findMainCtgIdx(index)" class="offMainCtg">
                  {{ mainCtg.codeName }}
                </button>
              </span>

              <div><hr class="line" /></div>

              소분류
              <span class="m-top-3">
                <button
                  v-for="subCtg in subCtgsList"
                  :key="subCtg.subCategoryCode"
                  :class="{ onSubCtg: isInCtg(subCtg.subCategoryCode) }"
                  class="offSubCtg"
                  @click="selectCategory(subCtg.subCategoryCode)"
                >
                  {{ subCtg.subCategoryName }}
                </button>
              </span>

              <div class="complete-edit">
                <button @click="postEditCategory" class="post-edit-button">수정 완료</button>
              </div>
            </div>

            <!-- <div class="in-group">
              ingroup의 first child
              <div class="subheading">소속한 모임</div>
              ingroup의 last child
              <div class="d-inline-block interval">
                <div
                v-for="group in groups"
                :key="group.id">
                {{ group }}  
                </div>            
              </div>
            </div>           -->
          </div>

          <!-- profile-info의 last child(right) -->
          <div>
            <div class="follow-info">
              <span class="follower-button" @click="getFollowList(1)">
                <span class="subheading">팔로워</span>
                <span class="interval point-color-1">{{ followers }}</span>
              </span>
              <span class="following-button" @click="getFollowList(0)">
                <span class="subheading">팔로잉</span>
                <span class="interval point-color-1">{{ userProfileInfo.followings }}</span>
              </span>
            </div>
            <br />
            <div class="berry-point">
              <!-- ingroup의 first child -->
              <div class="subheading">베리 포인트</div>
              <!-- ingroup의 last child -->
              <div class="d-inline-block interval">
                <span v-if="isLogin">누적</span>
                <span v-if="isLogin" class="interval point-color-1"
                  >{{ userProfileInfo.point }}P</span
                >
                <span>잔여</span>
                <span class="interval point-color-1">{{ userProfileInfo.remainingPoint }}P</span>
                <span v-if="isLogin"
                  ><button class="point-button" style="margin-right: 10px;" @click="openPointLog">
                    내역
                  </button></span
                >
                <span v-if="isLogin"
                  ><button class="point-button" @click="openPaySolver">사용</button></span
                >
              </div>
            </div>
            <br />
            <div style="display:flex; align-items:center;">
              <span class="subheading">베리 점수</span>
              <!-- 소수점 보여주고 싶은데 NaN 인 사람들에게 오류가 발생 -->
              <!-- <span class="interval point-color-1">{{ userProfileInfo.evaluationScore.toFixed(1) }} 점</span> -->
              <span v-if="isLoaded" class="interval point-color-1"
                >{{ userProfileInfo.evaluationScore.toFixed(1) }} 점</span
              >
              <span>/&nbsp;&nbsp;&nbsp;10 점</span>
            </div>

            <!-- 베리 점수에 따른 berry 개수 -->
            <div>
              <!-- null값 없으면 오류 발생 -->
              <div v-if="!isNaN">
                <span v-for="berryImg01 in 5" :key="'b0' + berryImg01">
                  <img src="@/assets/berry-1-b.png" width="42px" class="berry-opacity" />
                </span>
              </div>
              <div v-if="isNaN">
                <div v-if="userProfileInfo.point < 100">
                  <span
                    v-for="berryImg01 in parseInt(this.userProfileInfo.evaluationScore / 2)"
                    :key="'c1' + berryImg01"
                  >
                    <img src="@/assets/berry-1.png" width="42px" />
                  </span>
                  <span
                    v-for="berryImg01 in 5 - parseInt(this.userProfileInfo.evaluationScore / 2)"
                    :key="'b1' + berryImg01"
                  >
                    <img src="@/assets/berry-1-b.png" width="42px" class="berry-opacity" />
                  </span>
                </div>
                <div v-if="100 <= userProfileInfo.point && userProfileInfo.point < 200">
                  <span
                    v-for="berryImg02 in parseInt(this.userProfileInfo.evaluationScore / 2)"
                    :key="'c2' + berryImg02"
                  >
                    <img src="@/assets/berry-2.png" width="42px" />
                  </span>
                  <span
                    v-for="berryImg02 in 5 - parseInt(this.userProfileInfo.evaluationScore / 2)"
                    :key="'b2' + berryImg02"
                  >
                    <img src="@/assets/berry-2-b.png" width="42px" class="berry-opacity" />
                  </span>
                </div>
                <div v-if="200 <= userProfileInfo.point && userProfileInfo.point < 300">
                  <span
                    v-for="berryImg03 in parseInt(this.userProfileInfo.evaluationScore / 2)"
                    :key="'c3' + berryImg03"
                  >
                    <img src="@/assets/berry-3.png" width="42px" />
                  </span>
                  <span
                    v-for="berryImg03 in 5 - parseInt(this.userProfileInfo.evaluationScore / 2)"
                    :key="'b3' + berryImg03"
                  >
                    <img src="@/assets/berry-3-b.png" width="42px" class="berry-opacity" />
                  </span>
                </div>
                <div v-if="300 <= userProfileInfo.point && userProfileInfo.point < 400">
                  <span
                    v-for="berryImg04 in parseInt(this.userProfileInfo.evaluationScore / 2)"
                    :key="'c4' + berryImg04"
                  >
                    <img src="@/assets/berry-4.png" width="42px" />
                  </span>
                  <span
                    v-for="berryImg04 in 5 - parseInt(this.userProfileInfo.evaluationScore / 2)"
                    :key="'b4' + berryImg04"
                  >
                    <img src="@/assets/berry-4-b.png" width="42px" class="berry-opacity" />
                  </span>
                </div>
                <div v-if="400 <= userProfileInfo.point">
                  <span
                    v-for="berryImg05 in parseInt(this.userProfileInfo.evaluationScore / 2)"
                    :key="'c5' + berryImg05"
                  >
                    <img src="@/assets/berry-5.png" width="42px" />
                  </span>
                  <span
                    v-for="berryImg05 in 5 - parseInt(this.userProfileInfo.evaluationScore / 2)"
                    :key="'b5' + berryImg05"
                  >
                    <img src="@/assets/berry-5-b.png" width="42px" class="berry-opacity" />
                  </span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- calendar section -->
      <div>
        <ProfileTimetable
          v-if="isLoaded"
          :weekdayTime="userProfileInfo.weekdayTime"
          :weekendTime="userProfileInfo.weekendTime"
        />
      </div>

      <!-- tab-component section -->
      <div>
        <!-- TAB_BAR 선택 -->
        <ul class="tab-ul">
          <li
            v-for="(tab, index) in tabs"
            v-bind:key="tab.index"
            v-on:click="onClickTab(index)"
            class="tab-li multi-button"
          >
            <button class="tab-btn" :class="{ 'tab-btn-selected': index == selectedTab }">
              {{ tab.tabName }}
            </button>
          </li>
        </ul>
        <hr class="line" />

        <!-- SOLVE 기록 TAB -->
        <div v-if="0 == selectedTab">
          <ProfileStatistics
            :tabNum="selectedTab"
            :nickname="$route.params.nickname"
            :userProfileInfo="userProfileInfo"
            class="m-top-3"
          />
        </div>

        <!-- 답변 목록 TAB -->
        <div v-if="1 == selectedTab">
          <ProfileHistory
            :tabNum="selectedTab"
            :nickname="$route.params.nickname"
            class="m-top-3"
          />
        </div>

        <!-- 질문 목록 TAB -->
        <div v-if="2 == selectedTab">
          <ProfileMyQuestions
            :tabNum="selectedTab"
            :nickname="$route.params.nickname"
            class="m-top-3"
          />
        </div>

        <!-- 북마크 질문 목록 TAB -->
        <div v-if="3 == selectedTab">
          <ProfileBookmark
            :tabNum="selectedTab"
            :nickname="$route.params.nickname"
            class="m-top-3"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters, mapState } from "vuex";

import ProfileTimetable from "@/components/profiles/ProfileTimetable";
import ProfileStatistics from "@/components/profiles/ProfileStatistics";
import ProfileHistory from "@/components/profiles/ProfileHistory";
import ProfileMyQuestions from "@/components/profiles/ProfileMyQuestions";
import ProfileBookmark from "@/components/profiles/ProfileBookmark";
import PointLog from "./modal/PointLogModal.vue";
import PaySolver from "./modal/PaySolverModal.vue";
import Reservation from "./modal/Reservation.vue";
import FollowListModal from "./modal/FollowListModal.vue";
import LoginModal from "@/components/main/LoginModal";

import axios from "axios";
import API from "@/API.js";

export default {
  name: "MyProfile",
  components: {
    ProfileTimetable,
    ProfileStatistics,
    ProfileHistory,
    ProfileMyQuestions,
    ProfileBookmark,
  },
  data() {
    return {
      berryImg01: '"@/assets/berry-1.png',
      berryImg02: '"@/assets/berry-2.png',
      berryImg03: '"@/assets/berry-3.png',
      berryImg04: '"@/assets/berry-4.png',
      berryImg05: '"@/assets/berry-5.png',
      blackberryImg01: '"@/assets/berry-1-b.png',
      blackberryImg02: '"@/assets/berry-2-b.png',
      blackberryImg03: '"@/assets/berry-3-b.png',
      blackberryImg04: '"@/assets/berry-4-b.png',
      blackberryImg05: '"@/assets/berry-5-b.png',
      editInfo: {
        selfIntro: "",
        selfUrl: "",
      },
      editedCategory: [],
      followers: "",
      isEdit: false,
      isFirst: true,
      isCategoryEdit: false,
      isLogin: true,
      isNaN: false,
      mainCtgIndex: -1,
      mainCtgs: [],
      maxIntro: 30,
      subCtgs: [],
      selectedTab: 0,
      tabs: [
        { tabNum: 0, tabName: "SOLVE 기록" },
        { tabNum: 1, tabName: "답변 목록" },
        { tabNum: 2, tabName: "질문 목록" },
        { tabNum: 3, tabName: "북마크 목록" },
      ],
      userProfileInfo: {},
      isLoaded: false,
      profileImgFile: null,
    };
  },
  methods: {
    ...mapActions(["statisticSetting", "myQuestionsSetting", "myAnswersSetting"]),
    profileSetting() {
      axios({
        url: API.URL + `profiles/${this.$route.params.nickname}/info`,
        method: "get",
        headers: { Authorization: "Bearer " + this.accessToken },
      })
        .then((res) => {
          this.userProfileInfo = res.data;
          this.userProfileInfo.favoriteFieldNameList.sort();
        })
        .catch(() => this.$router.push({name: 'ErrorPage'}));
    },
    // 프로필 수정 요청 CLICK
    editRequest() {
      this.isEdit = !this.isEdit;
    },
    // 프로필 자기소개 수정
    editSelfIntro(selfIntro) {
      axios({
        url: API.URL + API.ROUTES.editProfile,
        method: "put",
        headers: { Authorization: "Bearer " + this.accessToken },
        data: {
          nickname: this.userNickname,
          profileUrl: this.userProfileInfo.profileUrl,
          introduction: selfIntro,
          linkText: this.userProfileInfo.linkText,
          categoryCodeList: this.userProfileInfo.favoriteFieldCodeList,
        },
      })
        .then(() => {
          // 실시간 업데이트를 위해
          this.profileSetting();
        })
        .catch((err) => console.log(err));
    },

    // 프로필 URL 수정 - AXIOS
    editSelfUrl(selfUrl) {
      axios({
        url: API.URL + API.ROUTES.editProfile,
        method: "put",
        headers: { Authorization: "Bearer " + this.accessToken },
        data: {
          nickname: this.userNickname,
          profileUrl: this.userProfileInfo.profileUrl,
          introduction: this.userProfileInfo.introduction,
          linkText: selfUrl,
          categoryCodeList: this.userProfileInfo.favoriteFieldCodeList,
        },
      })
        .then(() => {
          // 실시간 업데이트를 위해
          this.profileSetting();
        })
        .catch((err) => console.log(err));
    },

    // main카테고리의 IDX 찾기 - for Sub카테고리
    findMainCtgIdx(mainCtgIdx) {
      this.mainCtgIndex = mainCtgIdx;
    },

    // 탭 클릭
    onClickTab(tabIndex) {
      this.selectedTab = tabIndex;
    },

    getFollowList(mode) {
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
      } else {
        this.$modal.show(
          FollowListModal,
          {
            data: {
              mode: mode,
              nickname: this.$route.params.nickname,
            },
            modal: this.$modal,
          },
          {
            name: "dynamic-modal",
            width: "600px",
            height: "700px",
            draggable: false,
          }
        );
      }
    },
    // 카테고리 수정 요청 CLICK
    requestEditCategory() {
      this.isCategoryEdit = !this.isCategoryEdit;
      if (this.isCategoryEdit) {
        this.getCategoryList();
      }
    },

    // 카테고리 GET AXIOS
    getCategoryList() {
      axios({
        url: API.URL + API.ROUTES.getCategory,
        methods: "get",
      })
        .then((res) => {
          this.mainCtgs = res.data;
        })
        .catch((err) => console.log(err));
    },

    // 카테고리 수정한 것 담기
    selectCategory(subCtgCode) {
      if (this.editedCategory.includes(subCtgCode)) {
        const subCtg_idx = this.editedCategory.indexOf(subCtgCode);
        this.editedCategory.splice(subCtg_idx, 1);
        return;
      }

      if (this.editedCategory.length == 3) {
        return;
      }

      this.editedCategory.push(subCtgCode);
    },

    // 카테고리 수정 - AXIOS
    postEditCategory() {
      axios({
        url: API.URL + API.ROUTES.editProfile,
        method: "put",
        headers: { Authorization: "Bearer " + this.accessToken },
        data: {
          nickname: this.userNickname,
          profileUrl: this.userProfileInfo.profileUrl,
          introduction: this.userProfileInfo.introduction,
          linkText: this.userProfileInfo.linkText,
          categoryCodeList: this.editedCategory,
        },
      })
        .then(() => {
          this.profileSetting();
          this.isCategoryEdit = false;
        })
        .catch((err) => console.log(err));
    },

    // 카테고리 안에 있으면 button ON
    isInCtg(selectedCtgCode) {
      if (this.editedCategory.includes(selectedCtgCode)) {
        return true;
      } else {
        return false;
      }
    },

    // 자기소개 30글자까지만
    // inputLengthCheck(event) {
    //   if(event.target.value.length > 30) {
    //     alert('최대 30글자까지 입력가능합니다.')
    //   } else {
    //     this.currentIntroLength = event.target.value.length
    //   }
    // },

    // 로그인 유저인지 CHECK
    isLoginUser() {
      if (this.userNickname === this.$route.params.nickname) {
        this.isLogin = true;
      } else {
        this.isLogin = false;
      }
    },

    // 사용 내역 Modal
    openPointLog() {
      this.$modal.show(
        PointLog,
        {
          modal: this.$modal,
        },
        {
          name: "dynamic-modal",
          width: "600px",
          height: "700px",
          draggable: false,
        }
      );
    },

    // 결제 Modal
    openPaySolver() {
      this.$modal.show(
        PaySolver,
        {
          nickName: this.userProfileInfo.nickname,
          myPoint: this.userProfileInfo.point,
          modal: this.$modal,
        },
        {
          name: "dynamic-modal",
          width: "600px",
          height: "700px",
          draggable: false,
        }
      );
    },
    openReservation() {
      this.$modal.show(
        Reservation,
        {
          modal: this.$modal,
        },
        {
          name: "dynamic-modal",
          width: "600px",
          height: "700px",
          draggable: false,
        }
      );
    },
    profileImgChange() {
      const fileInput = document.getElementById("profile-img-input").files[0];
      const formData = new FormData();
      formData.append("imgFile", fileInput);
      axios
        .put(API.URL + `profiles/profileImg`, formData, {
          headers: {
            Authorization: "Bearer " + this.accessToken,
            "Content-Type": "multipart/form-data",
          },
        })
        .then(() => {
          this.profileSetting();
        })
        .catch(() => {});
    },
  },
  computed: {
    ...mapState({
      accessToken: (state) => state.auth.accessToken,
      categoryList: (state) => state.auth.categoryList,
      userNickname: (state) => state.auth.userNickname,
      pointUseTrigger: (state) => state.profiles.pointUseTrigger
    }),
    ...mapGetters(["isLoggedIn"]),
    groups() {
      return this.userProfileInfo.groupNameList;
    },
    mainCtgsList() {
      return this.mainCtgs;
    },
    subCtgsList() {
      // 이부분이 없으니까 category를 못찾네..?
      if (this.mainCtgIndex == -1) return null;

      return this.mainCtgs[this.mainCtgIndex].category;
    },
  },
  created() {
    if (this.$route.params.nickname != this.userNickname) {
      this.$router.push({
        path: `/profiles/${this.$route.params.nickname}`,
      })
    }
    this.profileSetting();
    const solveTabInfo = {
      userNickname: this.$route.params.nickname,
      tabnum: 0,
    };
    this.selectedTab = this.tabs[0].tabNum;
    this.statisticSetting(solveTabInfo);
    const myQuestionsTabInfo = {
      userNickname: this.$route.params.nickname,
      tabnum: 1,
    };
    this.myQuestionsSetting(myQuestionsTabInfo);
    const myAnswersTabInfo = {
      userNickname: this.$route.params.nickname,
      tabnum: 2,
    };
    this.myAnswersSetting(myAnswersTabInfo);
    this.isLoginUser();
    setTimeout(() => {
      this.isLoaded = true;
    }, 500);
  },
  watch: {
    userProfileInfo() {
      this.editInfo.selfIntro = this.userProfileInfo.introduction;
      this.editInfo.selfUrl = this.userProfileInfo.linkText;
      this.editedCategory = this.userProfileInfo.favoriteFieldCodeList;
      this.followers = this.userProfileInfo.followers;
    },
    pointUseTrigger() {
      this.profileSetting()
    }
  },
};
</script>

<style>
@import "./Profile.css";
@import "../../common.css";
</style>

<style scoped>
#profile-img-input {
  display: none;
}

.profile-img-section {
  position: relative;
}

.profile-img-section img {
  background-color: white;
}

.profile-img-label {
  position: absolute;
  width: 20px;
  height: 20px;
  left: 310px;
  top: 238px;
  cursor: pointer;
}
</style>
