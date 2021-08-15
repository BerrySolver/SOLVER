<template>
  <div class="footer">
    <div class="footer-list">
      <!-- 왼쪽 여백 col -->
      <!-- <div class="col-3"></div> -->

      <!-- 중앙 footer 부분 -->
      <div class="footer-center col-6 row">
        <div class="col-3">
          <div class="footer-title">
            <RouterLink :to="{ name: 'Main' }" class="footer-router">솔버(SOLVER)</RouterLink>
          </div>
          <div class="footer-text">
            <div>공지사항</div>
            <div>솔버 소개</div>
            <div>캐릭터 소개</div>
          </div>
        </div>

        <div class="col-3">
          <div class="footer-title">
            <RouterLink :to="{ name: 'Questions' }" class="footer-router">질문/답변</RouterLink>
          </div>
          <div class="footer-text">
            <div>최신 질문</div>
            <div>인기 질문</div>
            <div>최신 답변</div>
          </div>
        </div>

        <div class="col-3">
          <div class="footer-title">
            <RouterLink :to="{ name: 'Groups' }" class="footer-router">모임</RouterLink>
          </div>
          <div class="footer-text">
            <div>모임 목록</div>
            <div>신규 목록</div>
          </div>
        </div>

        <div class="col-3">
          <div class="footer-title">
            <RouterLink :to="{ name: 'Solvers' }" class="footer-router">솔버</RouterLink>
          </div>
          <div class="footer-text">
            <div>신규 솔버</div>
            <div>인기 솔버</div>
            <div>급상승 솔버</div>
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
