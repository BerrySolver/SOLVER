<template>
<!-- 전체 메인 페이지 div -->
  <div class="main">

    <!-- 1번째 메인 페이지(SOLVER 설명) -->
    <div class="container main-container">
      <div class="row row-left align-items-center">
        
        <div class="col-1"></div>

        <div class="row col-5">
          <div class="title">솔버(SOLVER)</div>
          <div class="introduction">
            무언가를 꾸준히 해서 얻는 성과는 달콤한 법이죠.
            <br>
            공부를 하고 얻은 지식은 자산이 되고, 
            <br>
            지식을 공유한 분들은 원하는 목표를 향한 희망이 될 것입니다.
          </div>

          <div class="form__group">
            <input type="input" class="form__field" placeholder="Name" name="name" id='name' required />
            <label for="name" class="form__label">
              <span>질문을 검색해보세요!</span>
            </label>
            <!-- <img src="@/assets/main-search.png" alt="main-search-button" width="25px"> -->
          </div>
        </div>

        <div class="col-5">
          <img src="@/assets/logo.png" alt="main-logo" class="main-logo">
        </div>


      </div>

    </div>

    <!-- 2번째 메인 페이지(현재 인기 질문) -->
    <div class="popular-question">
      <div class="container">
        <div class="row">
          <!-- 왼쪽 여백용 col -->
          <div class="col-1"></div>
          <!-- 소제목 col -->
          <div class="col-1 popular-title">
            <div>현재</div>
            <span>인기 질문</span>
          </div>
          <!-- 인기질문 col -->
          <div class="col-8">

            <div class="card-group">
              <div class="card">
                <div class="card-header">
                  <small class="card-ctg">카테고리 이름</small>
                </div>
                <div class="card-body">
                  <p class="card-title">질문 제목입니다.</p>
                  <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content.</p>
                </div>
              </div>

              <div class="card">
                <div class="card-header">
                  <small class="card-ctg">카테고리 이름</small>
                </div>
                <div class="card-body">
                  <p class="card-title">질문 제목입니다. 긴 질문 제목입니다.</p>
                  <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content.</p>
                </div>
              </div>

              <div class="card">
                <div class="card-header">
                  <small class="card-ctg">카테고리 이름</small>
                </div>
                <div class="card-body">
                  <p class="card-title">질문 제목입니다.</p>
                  <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content.</p>
                </div>
              </div>

              <div class="card">
                <div class="card-header">
                  <small class="card-ctg">카테고리 이름</small>
                </div>
                <div class="card-body">
                  <p class="card-title">질문 제목입니다. 긴 질문 제목입니다.</p>
                  <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content.</p>
                </div>
              </div>
            </div>

            <br>

            <div class="card-group">
              <div class="card">
                <div class="card-header">
                  <small class="card-ctg">카테고리 이름</small>
                </div>
                <div class="card-body">
                  <p class="card-title">질문 제목입니다.</p>
                  <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content.</p>
                </div>
              </div>

              <div class="card">
                <div class="card-header">
                  <small class="card-ctg">카테고리 이름</small>
                </div>
                <div class="card-body">
                  <p class="card-title">질문 제목입니다.</p>
                  <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content.</p>
                </div>
              </div>

              <div class="card">
                <div class="card-header">
                  <small class="card-ctg">카테고리 이름</small>
                </div>
                <div class="card-body">
                  <p class="card-title">질문 제목입니다.</p>
                  <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content.</p>
                </div>
              </div>

              <div class="card">
                <div class="card-header">
                  <small class="card-ctg">카테고리 이름</small>
                </div>
                <div class="card-body">
                  <p class="card-title">질문 제목입니다.</p>
                  <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content.</p>
                </div>
              </div>
            </div>

          </div>

          <!-- 오른쪽 여백용 col -->
          <div class="col-1"></div>

        </div>
      </div>
    </div>

    <!-- 3번째 메인 페이지(이달의 솔버) -->
    <div class="month-solver">
      <br>
        <div>
          <h2 class="solver-title">이달의 SOLVER</h2>
          <p class="solver-intro">이달에 활약한 SOLVER들을 소개해드려요!</p>
        </div>

        <div>
          <div class="card-group">
            <div class="card">
              <img class="card-img-top" src="@/assets/logo.png" alt="Card image cap" width="5vw">
              <div class="card-body">
                <p class="card-title">질문 제목입니다.</p>
              </div>
            </div>

            <div class="card">
              <div class="card-header">
                <small class="card-ctg">카테고리 이름</small>
              </div>
              <div class="card-body">
                <p class="card-title">질문 제목입니다. 긴 질문 제목입니다.</p>
              </div>
            </div>

            <div class="card">
              <div class="card-header">
                <small class="card-ctg">카테고리 이름</small>
              </div>
              <div class="card-body">
                <p class="card-title">질문 제목입니다.</p>
              </div>
            </div>

            <div class="card">
              <div class="card-header">
                <small class="card-ctg">카테고리 이름</small>
              </div>
              <div class="card-body">
                <p class="card-title">질문 제목입니다. 긴 질문 제목입니다.</p>
              </div>
            </div>
          </div>
        </div>
    </div>

    <!-- 4번째 메인 페이지(모임) -->
    <div class="group">
      <h5>모임</h5>
    </div>  
  </div>
</template>

<script>
export default {
  name: 'Main',
  components: {
  },
}
</script>

<style>
 @import './main.css';
</style>
