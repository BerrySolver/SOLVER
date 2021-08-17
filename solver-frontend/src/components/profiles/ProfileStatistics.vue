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
            <ProfilePieChart v-if="isLoaded && Object.keys(this.userStatistics.answerStatistics).length > 0" :dataset="chartDataset" :options="chartOptions"/>
          </div>
        </div>
      </div>

      <!-- statistics-info의 lastchild(right) -->
      <div>
        <!-- 솔버 등급 -->
        <div style="display:flex; align-items:center;">
          <div class="small-box"></div>
          <span class="subheading interval">솔버 등급</span>
        </div>
        <div v-if="userProfileInfo.remainingPoint < 100">
          <div class="content-center">
            <img src="@/assets/berry-1-gif.gif" width="300px">
            <div class="point-color-1">새싹베리 등급</div> 
          </div>          
        </div>
        <div v-if="100 <= userProfileInfo.remainingPoint && userProfileInfo.remainingPoint < 200">
          <div class="content-center">
            <img src="@/assets/berry-2-gif.gif" width="300px">
            <div class="point-color-1">베이비베리 등급</div> 
          </div>
        </div>    
        <div v-if="200 <= userProfileInfo.remainingPoint && userProfileInfo.remainingPoint < 300">
          <div class="content-center">
            <img src="@/assets/berry-3-gif.gif" width="300px">
            <div class="point-color-1">전교1등베리 등급</div> 
          </div>
        </div>
        <div v-if="300 <= userProfileInfo.remainingPoint && userProfileInfo.remainingPoint < 400">
          <div class="content-center">
            <img src="@/assets/berry-4-gif.gif" width="300px">
            <div class="point-color-1">척척박사베리 등급</div> 
          </div>
        </div>     
        <div v-if="400 <= userProfileInfo.remainingPoint">
          <div class="content-center">
            <img src="@/assets/berry-5-gif.gif" width="300px">
            <div class="point-color-1">베리킹 등급</div> 
          </div>    
        </div>             
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import ProfilePieChart from '@/components/profiles/ProfilePieChart'
import API from "@/API.js"

export default {
  name: 'ProfileStatistics',
  components: {
    ProfilePieChart,    
  },
  props: ['nickname', 'tabNum', 'userProfileInfo'],
  data() {
    return {
      userStatistics: {},
      chartDataset: {},
      chartOptions: {
        hoverBorderWidth: 20,
        legend: {
          display: true,
        },
        responsive: true,
        maintainAspectRatio: false,
      },
      isLoaded: false,
    }
  },
  created() {
    axios({
      url: API.URL + `profiles/${this.nickname}/tab`,
      method: "get",
      params: {
        tabNum: this.tabNum,
      }
    })
    .then((res) => {
      this.userStatistics = res.data
      console.log(this.userStatistics.answerStatistics)
      const backgroundColor = []
      for (let i = 0; i < Object.keys(this.userStatistics.answerStatistics).length; i++) {
        let color = this.$randomColor()
        while (color in backgroundColor) {
          color = this.$randomColor()
        }
        backgroundColor.push(color)
      }
      console.log(backgroundColor)
      this.chartDataset = {
        labels: Object.keys(this.userStatistics.answerStatistics),
        datasets: [
          {
            label: "카테고리",
            data: Object.values(this.userStatistics.answerStatistics),
            backgroundColor: backgroundColor
          }
        ]
      }
      setTimeout(() => {
        this.isLoaded = true
      }, 300)
    })
    .catch((err) => {
      console.log(err)
    })
  }
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
  display: flex;
  flex-direction: column;
  align-items: center;
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