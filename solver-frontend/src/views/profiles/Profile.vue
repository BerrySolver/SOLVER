<template>
  <div class="for-center">
    <div class="profile solver-font">
      <!-- profile-image section -->
      <div class="profile-img-section">
        <img src="@/assets/profile-ex.jpg" class="profile-img">
      </div>


      <!-- profile-text_info section -->
      <div class="profile-info-section">
        <div class="nickname">
          <div>{{ userProfileInfo.nickname }}</div>
          <div
          class="edit-img"
          @click="editRequest">
            <span>수정</span>
            <img src="@/assets/edit-button.png" width="20px">
          </div>
        </div>
        <div style="color:#84898C; display:flex; align-items: center; justify-content:space-between;">
          
          <!-- 한줄 소개 -->
          <div>
            <div v-if="!isEdit">
              <span>한줄 소개</span>
              <span class="content-text-b m-left-1">{{ userProfileInfo.introduction }}</span>
            </div>
            <div v-if="isEdit" @keyup.enter="[editRequest(), editSelfIntro(editInfo.selfIntro)]">
              한줄 소개 
              <span><input v-model="editInfo.selfIntro" class="intro-input m-left-1" type="text"></span>
              <button
              @click="[editRequest(), editSelfIntro(editInfo.selfIntro)]"
              class="edit-end-button m-left-1">수정</button>
            </div>
          </div>
          
          <!-- 개인 URL -->
          <div class="social-url">
            <div v-if="!isEdit">
              <span>URL</span>
              <span class="content-text-b m-left-1">{{ userProfileInfo.linkText }}</span>
            </div>
            <div v-if="isEdit" @keyup.enter="[editRequest(), editSelfUrl(editInfo.selfUrl)]">
              URL
              <span><input v-model="editInfo.selfUrl" class="url-input m-left-1" type="text"></span>
              <button
              @click="[editRequest(), editSelfUrl(editInfo.selfUrl)]"
              class="edit-end-button m-left-1">수정</button>
            </div>
          </div>
        </div>

        <!-- 구분선 -->
        <hr class="line">

        <div class="profile-info m-top-3">
          <!-- profile-info의 first child(left) -->
          <div>
            <div style="display:flex; align-items:center;">
              <span class="subheading">주 활동분야</span>
              <span
                v-for="field in fields"
                :key="field.id"
                class="interval">
                {{ field }}
              </span>
              <img src="@/assets/edit-button.png" width="20px" @click="requestEditCategory" class="edit-img">
            </div>

            <br>

            <div class="category-edit-box" v-if="isCategoryEdit">
              대분류
              <span v-for="(mainCtg, index) in mainCtgs" :key="index">
                <button @click="findMainCtgIdx(index)" class="offMainCtg"> {{ mainCtg.codeName }} </button>
              </span>

              <div><hr class="line"></div>

              소분류
              <span class='m-top-3'>
                <button
                v-for="subCtg in subCtgsList"
                :key="subCtg.subCategoryCode"
                :class="{onSubCtg:isInCtg(subCtg.subCategoryCode)}"
                class="offSubCtg"
                @click="selectCategory(subCtg.subCategoryCode)">
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
            <div class="berry-point">
              <!-- ingroup의 first child -->
              <div class="subheading">베리 포인트</div>
              <!-- ingroup의 last child -->
              <div class="d-inline-block interval">
                <span>잔여</span>
                <span class="interval point-color-1">{{ userProfileInfo.point }}P</span><br>
                <span>누적</span>
                <span class="interval point-color-1">{{ userProfileInfo.remainingPoint }}P</span>                     
              </div>
            </div>
            <br>
            <div style="display:flex; align-items:center;">
              <span class="subheading">베리 점수</span>
              <span class="interval point-color-1">{{ userProfileInfo.evaluationScore.toFixed(1) }} 점</span>
              <span>/&nbsp;&nbsp;&nbsp;10 점</span>
            </div>

            <!-- 베리 점수에 따른 berry 개수 -->
            <div>
              <div v-if="userProfileInfo.remainingPoint < 100">
                <span v-for="berryImg01 in parseInt(this.userProfileInfo.evaluationScore / 2)" :key="berryImg01">
                  <img src="@/assets/berry-2.png" width="42px">
                </span>
                <span v-for="berryImg01 in 5 - parseInt(this.userProfileInfo.evaluationScore / 2)" :key="berryImg01">
                  <img src="@/assets/berry-2-b.png" width="42px" class="berry-opacity">
                </span>
              </div>
              <div v-if="100 <= userProfileInfo.remainingPoint && userProfileInfo.remainingPoint < 200">
                <span v-for="berryImg02 in parseInt(this.userProfileInfo.evaluationScore / 2)" :key="berryImg02">
                  <img src="@/assets/berry-2.png" width="42px">
                </span>
                <span v-for="berryImg02 in 5 - parseInt(this.userProfileInfo.evaluationScore / 2)" :key="berryImg02">
                  <img src="@/assets/berry-2-b.png" width="42px" class="berry-opacity">
                </span>                
              </div>    
              <div v-if="200 <= userProfileInfo.remainingPoint && userProfileInfo.remainingPoint < 300">
                <span v-for="berryImg03 in parseInt(this.userProfileInfo.evaluationScore / 2)" :key="berryImg03">
                  <img src="@/assets/berry-3.png" width="42px">
                </span>
                <span v-for="berryImg03 in 5 - parseInt(this.userProfileInfo.evaluationScore / 2)" :key="berryImg03">
                  <img src="@/assets/berry-3-b.png" width="42px" class="berry-opacity">
                </span>  
              </div>
              <div v-if="300 <= userProfileInfo.remainingPoint && userProfileInfo.remainingPoint < 400">
                <span v-for="berryImg04 in parseInt(this.userProfileInfo.evaluationScore / 2)" :key="berryImg04">
                  <img src="@/assets/berry-4.png" width="42px">
                </span>
                <span v-for="berryImg04 in 5 - parseInt(this.userProfileInfo.evaluationScore / 2)" :key="berryImg04">
                  <img src="@/assets/berry-4-b.png" width="42px" class="berry-opacity">
                </span> 
              </div>         
              <div v-if="400 <= userProfileInfo.remainingPoint">
                <span v-for="berryImg05 in parseInt(this.userProfileInfo.evaluationScore / 2)" :key="berryImg05">
                  <img src="@/assets/berry-5.png" width="42px">
                </span>
                <span v-for="berryImg05 in 5 - parseInt(this.userProfileInfo.evaluationScore / 2)" :key="berryImg05">
                  <img src="@/assets/berry-5-b.png" width="42px" class="berry-opacity">
                </span> 
              </div>
            </div>

          </div>
        </div>
      </div>


      <!-- calendar section -->
      <div>
        <ProfileTimetable 
        :weekdayTime="userProfileInfo.weekdayTime"
        :weekendTime="userProfileInfo.weekendTime"/>
      </div>

      <!-- tab-component section -->
      <div>
        <!-- TAB_BAR 선택 -->
        <ul class="tab-ul">
          <li v-for="(tab, index) in tabs" v-bind:key="tab.index" v-on:click="onClickTab(index)" class="tab-li multi-button">
            <button class="tab-btn" :class="{ 'tab-btn-selected': index == selectedTab}">{{ tab.tabName }}</button>
          </li>
        </ul>
        <hr class="line">

        <!-- SOLVE 기록 TAB -->
        <div v-if="0 == selectedTab">
          <ProfileStatistics class="m-top-3"/>
        </div>

        <!-- 답변 목록 TAB -->
        <div v-if="1 == selectedTab">
          <ProfileHistory :myAnswersTab="selectedTab" class="m-top-3"/>
        </div>

        <!-- 질문 목록 TAB -->
        <div v-if="2 == selectedTab">
          <ProfileMyQuestions :myQuestionsTab="selectedTab" class="m-top-3"/>
        </div>      
      </div>     
    </div>    
  </div>
</template>

<script>
import {mapActions, mapState} from 'vuex'

import ProfileTimetable from "@/components/profiles/ProfileTimetable"
import ProfileStatistics from "@/components/profiles/ProfileStatistics"
import ProfileHistory from "@/components/profiles/ProfileHistory"
import ProfileMyQuestions from "@/components/profiles/ProfileMyQuestions"

import axios from 'axios'
import API from "@/API.js"

export default {
  name: 'Profile',
  components: {
    ProfileTimetable,
    ProfileStatistics,
    ProfileHistory,
    ProfileMyQuestions,
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
        selfIntro: '',
        selfUrl: '',
      },
      editedCategory: [],
      isEdit: false,
      isCategoryEdit: false,
      mainCtgIndex: -1,
      mainCtgs: [],
      subCtgs: [],
      selectedTab: 0,
      tabs: [
        {tabNum: 0, tabName: 'SOLVE 기록'},
        {tabNum: 1, tabName: '답변 목록'},
        {tabNum: 2, tabName: '질문 목록'},
      ],
    }
  },
  methods: {
    ...mapActions(['profileSetting', 'statisticSetting', 'myQuestionsSetting', 'profileSetting']),
    onClickTab(tabIndex) {
      this.selectedTab = tabIndex
    },
    editRequest() {
      this.isEdit = !this.isEdit
    },
    // 프로필 자기소개 수정
    editSelfIntro(selfIntro) {
      console.log(this.userProfileInfo.favoriteFieldCodeList)
      axios({
        url: API.URL + API.ROUTES.editProfile,
        method: "patch",
        headers: { Authorization: "Bearer " + this.accessToken},
        data: {
          nickname: this.userNickname,
          profileUrl: this.userProfileInfo.profileUrl,
          introduction: selfIntro,
          linkText: this.userProfileInfo.linkText,
          categoryCodeList: this.userProfileInfo.favoriteFieldCodeList,
        }
      })
      .then((res) => {
        // 실시간 업데이트를 위해
        this.profileSetting(this.userNickname)
        })
      .catch((err) => console.log(err))
    },

    // main카테고리의 IDX 찾기 - for Sub카테고리
    findMainCtgIdx(mainCtgIdx){
      this.mainCtgIndex = mainCtgIdx
    },

    // 프로필 URL 수정 - AXIOS
    editSelfUrl(selfUrl) {
      console.log(this.userProfileInfo.favoriteFieldCodeList)
      axios({
        url: API.URL + API.ROUTES.editProfile,
        method: "patch",
        headers: { Authorization: "Bearer " + this.accessToken},
        data: {
          nickname: this.userNickname,
          profileUrl: this.userProfileInfo.profileUrl,
          introduction: this.userProfileInfo.introduction,
          linkText: selfUrl,
          categoryCodeList: this.userProfileInfo.favoriteFieldCodeList,
        }
      })
      .then((res) => {
        // 실시간 업데이트를 위해
        this.profileSetting(this.userNickname)
        })
      .catch((err) => console.log(err))
    },

    // 카테고리 수정 요청 CLICK
    requestEditCategory() {
      this.isCategoryEdit = !this.isCategoryEdit
      if (this.isCategoryEdit) {
        this.getCategoryList()
      }
    },

    // 카테고리 GET AXIOS
    getCategoryList() {
      axios({
        url: API.URL + API.ROUTES.getCategory,
        methods: 'get',
      })
      .then((res) => {
        this.mainCtgs = res.data;
        console.log(this.mainCtgs)
      })
      .catch((err) => console.log(err))
    },

    // 카테고리 수정한 것 담기
    selectCategory(subCtgCode) {
      if (this.editedCategory.includes(subCtgCode)) {
        const subCtg_idx = this.editedCategory.indexOf(subCtgCode)
        this.editedCategory.splice(subCtg_idx, 1)
        return ;
      } 

      if (this.editedCategory.length == 3) {
        return ;
      }

      this.editedCategory.push(subCtgCode)
    },

    // 카테고리 수정 - AXIOS
    postEditCategory() {
      axios({
        url: API.URL + API.ROUTES.editProfile,
        method: "patch",
        headers: { Authorization: "Bearer " + this.accessToken},
        data: {
          nickname: this.userNickname,
          profileUrl: this.userProfileInfo.profileUrl,
          introduction: this.userProfileInfo.introduction,
          linkText: this.userProfileInfo.linkText,
          categoryCodeList: this.editedCategory,
        }
      })
      .then((res) => {
        this.profileSetting(this.userNickname)
        this.isCategoryEdit = false
      })
      .catch((err) => console.log(err))
    },

    isInCtg(selectedCtgCode) {
      if (this.editedCategory.includes(selectedCtgCode)) {
        return true
      } else {
      return false }
    },
  },
  computed: {
    ...mapState({
      accessToken: state => state.auth.accessToken,
      categoryList: state => state.auth.categoryList,
      userNickname: state => state.auth.userNickname,
      userProfileInfo: state => state.profiles.userProfileInfo,
    }),
    fields() {
      return this.userProfileInfo.favoriteFieldNameList
    },
    groups() {
      return this.userProfileInfo.groupNameList
    },
    mainCtgsList() {
      return this.mainCtgs
    },
    subCtgsList() {
      // 이부분이 없으니까 category를 못찾네..?
      if (this.mainCtgIndex == -1)
        return null;

      return this.mainCtgs[this.mainCtgIndex].category
    }
  },
  created() {
    const solveTabInfo = {
      userNickname: this.userNickname,
      tabnum: this.selectedTab,
    }
    this.selectedTab = this.tabs[0].tabNum
    this.statisticSetting(solveTabInfo)
  },
}
</script>

<style>
 @import './Profile.css';
 @import '../../common.css';
</style>