<template>
  <div>
    <div class="statistics-info">
      <!-- statistics-info의 firstchild(left) -->
      <div>
        <!-- 답변 개수 -->
        <div style="display:flex; align-items:center; ">
          <div class="small-box"></div>
          <span class="subheading interval">답변 개수</span>
          <div style="display:inline-block">
            <span class="interval">TEXT</span> <span class="point-color-1">{{ userStatistics.textAnswerCount }}</span><span>개</span><br>
            <span class="interval">VIDEO</span> <span class="point-color-1">{{ userStatistics.videoAnswerCount }}</span><span>개</span>
          </div>
        </div>
        <br>

        <!-- 화상 시간 -->
        <div style="display:flex; align-items:center; ">
          <div class="small-box"></div>
          <span class="subheading interval">화상 시간</span>
          <span class="point-color-1 interval">총 {{ userStatistics.videoAnswerTime }}분</span>
        </div>
        <br>
        
        <!-- 답변 통계 -->
        <div class="answer-statistics">
          <!-- answer-statistics의 firstchild(left) -->
          <div style="display:flex; align-items:center;">
            <div class="small-box"></div>
            <span class="subheading interval">답변 통계</span>            
          </div>
          <!-- answer-statistics의 lastchild(right) -->
          <div class="text-left">
            <div v-for="cateName in userProfileInfo.favoriteFieldNameList" :key="cateName">{{ cateName }}</div>
          </div>
        </div>
      </div>

      <!-- statistics-info의 lastchild(right) -->
      <div>
        <!-- 솔버 등급 -->
        <div style="display:flex; align-items:center; ">
          <div class="small-box"></div>
          <span class="subheading interval">솔버 등급</span>
        </div>
        <div v-if="userProfileInfo.remainingPoint < 100">
          <img src="@/assets/berry-1.png" width="300px">
          <div class="point-color-1">새싹베리</div> 
        </div>
        <div v-if="100 <= userProfileInfo.remainingPoint && userProfileInfo.remainingPoint < 200">
          <div>
            <img src="@/assets/berry-2.png" width="300px">
            <div class="point-color-1">베이비베리</div> 
          </div>
        </div>    
        <div v-if="200 <= userProfileInfo.remainingPoint && userProfileInfo.remainingPoint < 300">
          <img src="@/assets/berry-3.png" width="300px">
          <div class="point-color-1 content-center">전교1등베리</div> 
        </div>
        <div v-if="300 <= userProfileInfo.remainingPoint && userProfileInfo.remainingPoint < 400">
          <img src="@/assets/berry-4.png" width="300px">
          <div class="point-color-1">척척박사베리</div> 
        </div>         
        <div v-if="400 <= userProfileInfo.remainingPoint">
          <img src="@/assets/berry-5.png" width="300px">
          <div class="point-color-1">베리킹</div> 
        </div>             
      </div>
    </div>
  </div>
</template>

<script>
import {mapActions, mapState} from 'vuex'

export default {
  name: 'ProfileStatistics',
  methods: {
    ...mapActions(['statisticSetting']),
  },
  computed: {
    ...mapState({
      userNickname: state => state.auth.userNickname,
      userStatistics: state => state.profiles.userStatistics,
      userProfileInfo: state => state.profiles.userProfileInfo,
    })
  },
}
</script>

<style>
.answer-statistics {
  display: flex;
  flex-direction: row;
  text-align: left;
}

.answer-statistics > div:first-child {
  flex: 1; /* 공간차지비율 */
  height: 100%;
}
.answer-statistics > div:last-child {
  flex: 5.5; /* 공간차지비율 */
  height: 100%;
}

.content-center {
  margin: 0 auto;
}

.statistics-info {
  display: flex;
  flex-direction: row;
  text-align: left;
}

.statistics-info > div:first-child {
  flex: 6; /* 공간차지비율 */
  height: 100%;
}
.statistics-info > div:last-child {
  flex: 4; /* 공간차지비율 */
  height: 100%;
}
</style>