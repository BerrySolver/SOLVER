<template>
  <div class="profile main-container">
    <!-- 프로필 기본 정보 -->
    <div class="row">
      <!-- 왼쪽 여백용 col-1 -->
      <div class="col-xl-1"></div>

      <!-- 프로필 사진용 col-2 -->
      <div class="col-xl-2">
        <figure>
          <img src="@/assets/profile-ex.jpg" class="profile-img">
        </figure>
      </div>

      <!-- 프로필 외 기본정보용 col-8 -->
      <div class="col-xl-8">
        <!-- 기본정보 상단 -->
        <div class="info">
          <div class="info-left">
            <div class="nickname">나약하지 않은 나약이</div>
            <div>@na_0_i</div>
            </div>
          <div class="social-info">
            <pre class="solver-font social-url">
              https://github.com/na0i
              https://www.instagram.com/na_0_i
            </pre>
          </div>          
        </div>
        
        <!-- 구분선 -->
        <hr class="line">

        <!-- 기본정보 하단 -->
        <div class="row">
          <div class="col-xl-7 row">
            <div class="col-xl-3 subheading">
              <div class="m-top-3">주 활동분야</div>
              <br>
              <div>소속한 모임</div>
            </div>
            <div class="col-xl-9 content-text-b">
              <div class="m-top-3">
                <span class="me-2">프로그래밍</span>
                <span class="interval">디자인</span>
                <span class="interval">영어</span>
                <span class="interval">일본어</span>
              </div>
              <br>
              <div>나당 연합군</div>
              <div>기억을 쌓자 SSATCHA!</div>
              <div>SSAFY 서울 5반</div>
            </div>
          </div>
          <div class="col-xl-5 row">
            <div class="col-xl-4 subheading">
              <div class="m-top-3">베리 포인트</div>
              <br>
              <div>베리 점수</div>
            </div>
            <div class="col-xl-8 content-text-b">
              <div class="m-top-3">
                <span>누적</span>
                <span class="point-color-1 interval">3200P</span>
              </div>
              <br>
              <div>
                <span class="point-color-1">6.2</span>
                <span class="interval">/</span>
                <span>10</span>
              </div>  
              <div>
                <img src="@/assets/berry-1.png" class="point-berry">
                <img src="@/assets/berry-1.png" class="point-berry">
                <img src="@/assets/berry-1.png" class="point-berry">
                <img src="@/assets/berry-1.png" class="point-berry-g">
                <img src="@/assets/berry-1.png" class="point-berry-g">
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 오른쪽 여백용 col-1 -->
      <div class="col-xl-1"></div>
    </div>

    <br>

    <!-- 컴포넌트 시작 -->
    <div class="row">
      <div class="col-3">
        <ProfileTimetable/>
      </div>
      <div class="col-9">
        <ul class="tab-ul">
          <li v-for="tab in tabs" v-bind:key="tab.id" v-on:click="onClickTab(tab)" class="tab-li multi-button">
            <button class="tab-btn">{{ tab }}</button>
          </li>
        </ul>
        <hr class="line">

        <!-- SOLVE 기록 TAB -->
        <div v-if="'SOLVE 기록' == selectedTab">
          <ProfileStatistics/>
        </div>

        <!-- 답변 목록 TAB -->
        <div v-else>
          <ProfileHistory/>
        </div>
      </div>
    </div>
    
    
    <ProfileMyQuestions/>
  </div>
</template>

<script>
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
      tabs: ['SOLVE 기록', '답변 목록']
    }
  },
  methods: {
    onClickTab(tab) {
      this.selectedTab = tab
      console.log(this.selectedTab)
    },
  },
}
</script>

<style>
 @import './Profile.css';
 @import '../../common.css'
</style>