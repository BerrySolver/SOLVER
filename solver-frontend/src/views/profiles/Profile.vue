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
      </div>
      <div style="color:#84898C; display:flex; align-items: center; justify-content:space-between;">
        <div>{{ userProfileInfo.introduction }}</div>
        <div class="social-url">
          {{ userProfileInfo.profileUrl }}
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

      <!-- TAB_BAR 전 여백 -->
      <br>
      <br>
    </div>


    <!-- calendar section -->
    <div>
      <ProfileTimetable
      v-bind:userProfileInfo="userProfileInfo"/>
    </div>

    <!-- tab-component section -->
    <div>
      <!-- TAB_BAR 선택 -->
      <ul class="tab-ul">
        <li v-for="tab in tabs" v-bind:key="tab.id" v-on:click="onClickTab(tab)" class="tab-li multi-button">
          <button class="tab-btn">{{ tab }}</button>
        </li>
      </ul>
      <hr class="line">

      <!-- SOLVE 기록 TAB -->
      <div v-if="'SOLVE 기록' == selectedTab">
        <ProfileStatistics class="m-top-3"/>
      </div>

      <!-- 답변 목록 TAB -->
      <div v-if="'답변 목록' == selectedTab">
        <ProfileHistory class="m-top-3"/>
      </div>

      <div v-if="'질문 목록' == selectedTab">
        <ProfileMyQuestions class="m-top-3"/>
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
      selectedTab: '',
      tabs: ['SOLVE 기록', '답변 목록', '질문 목록']
    }
  },
  methods: {
    ...mapActions(['profileSetting']),
    onClickTab(tab) {
      this.selectedTab = tab
      console.log(this.selectedTab)
    },
  },
  computed: {
    ...mapState({
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
    this.selectedTab = this.tabs[0],
    this.profileSetting(this.userNickname)
  },
}
</script>

<style>
 @import './Profile.css';
 @import '../../common.css'
</style>