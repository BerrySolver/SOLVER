<template>
  <div class="row">
    <!-- 답변 통계 -->
    <div class="col-xl-1 statistics-title subheading">
      <div>
        <div class="box"></div>
        <span>답변 개수</span>
      </div>
      <br>
      <br>
      <div>
        <div class="box"></div>
        <span>화상 시간</span>
      </div>
      <br>
      <br>
      <div>
        <div class="box"></div>
        <span>답변 통계</span>
      </div>
    </div>

    <div class="col-xl-5 statistics-title">
      <div>
        <span>TEXT</span>
        <span class="interval point-color-1">35개</span>
      </div>
      <div>
        <span>VIDEO</span>
        <span class="interval point-color-1">05개</span>
      </div>

      <br>

      <span class="point-color-1">총</span>
    </div>

    <!-- 베리 등급 캐릭터 -->
    <div class="col-xl-5">
      <!-- <div class="box"></div> -->
      <div class="statistics-title subheading">솔버 등급</div>
      <figure>
        <img src="@/assets/berry-1.png" class="berry-character">
      </figure>
      <div class="point-color-1">LV.1 으쌰으쌰 단계</div>
    </div>

    <div class="col-xl-1"></div>
  </div>
</template>

<script>
export default {
    name: 'ProfileStatistics',
}
</script>

<style>
.berry-character {
  width: 75%;
}

.statistics-content {
  display: inline-block;
  font-size: 17px;
}

.statistics-title {
  margin-top: 10px;
  text-align: left;
  padding-right: 0;
}
</style>