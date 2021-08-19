<template>
  <div>
    <div class="group-detail-header font-break">
        <div class="group-detail-banner">
          <img src="@/assets/logo-white-2.png" alt="white-logo">
          <div class="solver-banner-ment">
            <h6 class="ment">모임 기능은 준비중입니다!</h6>
            <h6 class="ment">빠른 시일 내에 다시 만나요 😘</h6>
          </div>
        </div>
    </div>
    <h2 style="margin-top: 50px;">블루베리가 익어가는 중...🔮</h2>
    <img style="width: 500px;" src="@/assets/berry-success.png" alt="">
  </div>
</template>

<script>

export default {
  name: "GroupDetail",
};
</script>

<style>
  .group-detail-banner {
    align-items: center;
    display: flex;
  }

  .group-detail-banner img {
    height: 100px;
  }

  .group-detail-banner-ment {
    height: 40px;
    margin-left: 40px;
    text-align: left;
  }
  
  .group-detail-header {
    background-color: #658DC6;
    display: flex;
    height: 180px;
    justify-content: center;
  }
</style>
