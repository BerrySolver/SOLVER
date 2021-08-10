<template>
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
        style="color:#658DC6; font-size: 14px"
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
          URL {{ userProfileInfo.profileUrl }}
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
          </div>
          <br>
          <div class="in-group">
            <!-- ingroup의 first child -->
            <div class="subheading">소속한 모임</div>
            <!-- ingroup의 last child -->
            <div class="d-inline-block interval">
              <div
              v-for="group in groups"
              :key="group.id">
              {{ group }}  
              </div>            
            </div>
          </div>          
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
            <span class="interval point-color-1">{{ userProfileInfo.evaluationScore }}</span>
            <span>/&nbsp;&nbsp;&nbsp;10</span>
          </div>
          <div>
            <img src="@/assets/berry-1.png" width="42px">
            <img src="@/assets/berry-1.png" width="42px">
            <img src="@/assets/berry-1.png" width="42px">
            <img src="@/assets/berry-1.png" width="42px">
            <img src="@/assets/berry-1.png" width="42px">
          </div>
        </div>
      </div>
    </div>


    <!-- calendar section -->
    <div>
      <ProfileTimetable 
      v-bind:weekdayTime="userProfileInfo.weekdayTime"
      :weekendTime="userProfileInfo.weekendTime"/>
    </div>

    <!-- tab-component section -->
    <div>
      <!-- TAB_BAR 선택 -->
      <ul class="tab-ul">
        <li v-for="(tab, index) in tabs" v-bind:key="tab.index" v-on:click="onClickTab(index)" class="tab-li multi-button">
          <button class="tab-btn">{{ tab.tabName }}</button>
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
      isEdit: false,
      editInfo: {
        selfIntro: '',
        selfUrl: '',
      },
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
      console.log('자기소개 수정')
      axios({
        url: API.URL + API.ROUTES.editProfile,
        method: "patch",
        headers: { Authorization: "Bearer " + this.accessToken},
        data: {
          nickname: this.userNickname,
          profileUrl: this.userProfileInfo.profileUrl,
          introduction: selfIntro,
          link_text: this.userProfileInfo.link_text,
          category: this.userProfileInfo.favoriteFieldNameList
        }
      })
      .then((res) => {
        // 실시간 업데이트를 위해
        this.profileSetting(this.userNickname)
        })
      .catch((err) => console.log(err))
    },

    // 프로필 URL 수정
  },
  computed: {
    ...mapState({
      accessToken: state => state.auth.accessToken,
      userNickname: state => state.auth.userNickname,
      userProfileInfo: state => state.profiles.userProfileInfo,
    }),
    fields() {
      return this.userProfileInfo.favoriteFieldNameList
    },
    groups() {
      return this.userProfileInfo.groupNameList
    },
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
 @import '../../common.css'
</style>