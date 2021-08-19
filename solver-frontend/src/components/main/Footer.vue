<template>
  <div v-if="needHide" class="footer">
    <div class="footer-list">
      <!-- 왼쪽 여백 col -->
      <!-- <div class="col-3"></div> -->

      <!-- 중앙 footer 부분 -->
      <div class="footer-center col-6 row">
        <div class="col-3">
          <div class="footer-title">
            <div style="color:white;">SOLVER</div>
          </div>
          <div class="footer-text">
            <div>공지사항</div>
            <div>솔버 소개</div>
            <div>캐릭터 소개</div>
          </div>
        </div>

        <div class="col-3">
          <div class="footer-title">
            <div style="color:white;">질문/답변</div>
          </div>
          <div class="footer-text">
            <div>모든 질문</div>
            <div>인기 질문</div>
            <div>질문 검색</div>
            <div>화상 예약</div>
            <div>화상 답변</div>
          </div>
        </div>

        <div class="col-3">
          <div class="footer-title">
            <div style="color:white;">솔버</div>
          </div>
          <div class="footer-text">
            <div>모든 솔버</div>
            <div>인기 솔버</div>
            <div>솔버 검색</div>
          </div>
        </div>

        <div class="col-3">
          <div class="footer-title">
            <div style="color:white;">프로필</div>
          </div>
          <div class="footer-text">
            <div>모든 정보</div>
            <div>답변 통계</div>
            <div>화상 시간표</div>
            <div>프로필 링크</div>
          </div>
        </div>
      </div>
      <!-- 오른쪽 여백 col -->
      <!-- <div class="col-3"> -->
        <!-- <div class="team-info">
          <div class="team-info-title">삼성SW청년아카데미 5기</div>
          <div>서울 캠퍼스 5반 7팀 - 나승호 당현아 박나영 조현민</div>
        </div> -->
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Footer",
  computed: {
    needHide() {
      return !(
        this.$route.name === "Conference"
      );
    },
  }
};
</script>

<style>
.footer {
  background-color: #84898c;
  /* bottom: 0; */
  font-family: "NanumSquare", sans-serif;
  height: 15rem;
  /* position: absolute; */
  width: 100%;
  /* 세로로 줄일때 위치가 이동되는 현상 발생 */
}

.footer-center {
  margin-left: 75px;
}

.footer-router {
  color: white;
  text-decoration: none;
}

.footer-router:hover {
  color: white;
  text-decoration: none;
}

.footer-text {
  color: white;
  font-size: 12px;
  font-weight: 200;
  line-height: 23px;
  text-align: left;
}

.footer-title {
  font-weight: 600;
  font-size: 15px;
  margin-bottom: 20px;
  margin-top: 20px;
  text-align: left;
}

.footer-list{
  display: flex;
  justify-content: center;
}
/* .team-info {
  bottom: 0;
  color: white;
  font-size: 12px;
  right: 20px;
  position: relative;
  text-align: right;
}

.team-info-title {
  font-size: 17px;
  font-weight: 600;
} */
</style>
