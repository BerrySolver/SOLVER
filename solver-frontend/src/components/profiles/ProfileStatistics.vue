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
            <span class="interval">TEXT</span> <span class="point-color-1">20</span><span>개</span><br>
            <span class="interval">VIDEO</span> <span class="point-color-1">05</span><span>개</span>
          </div>
        </div>
        <br>
        <!-- 화상 시간 -->
        <div style="display:flex; align-items:center; ">
          <div class="small-box"></div>
          <span class="subheading interval">화상 시간</span>
          <span class="point-color-1 interval">총 122분</span>
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
            <div>되나</div>
            <div>되냐고</div>
            <div>되냐고</div>
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
        <img src="@/assets/berry-1.png" width="250px">
        <div class="m-left-5 point-color-1">LV1. 으쌰으쌰 레벨</div>        
      </div>
    </div>
  </div>
</template>

<script>
export default {
    name: 'ProfileStatistics',
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