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
          <div class="col-1 semi-title">
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
        <div class="month-solver-text">
          <img src="@/assets/logo-white-2.png" class="small-logo">
          <h3 class="solver-title">이달의 SOLVER</h3>
          <p class="solver-intro">이달에 활약한 SOLVER들을 소개해드려요!</p>
        </div>

        <div class="row">
          <!-- 왼쪽 여백용 col-2 -->
          <div class="col-2"></div>

          <!-- 중앙 카드용 col-8 -->
          <div class="row solver-row">
              <div class="col-2"></div>
              <div class="col-2">          
                <div class="solver-card-rank">01</div>
                <div class="solver-card-content">
                  <img src="@/assets/예시.jpg" class="solver-card-img" alt="solver-card-img">
                  <p class="solver-intro">1등 닉네임</p>
                </div>
              </div>
              <div class="col-2">
                <div class="solver-card-rank">02</div>
                <div class="solver-card-content">
                  <img src="@/assets/예시.jpg" class="solver-card-img" alt="solver-card-img">
                  <p class="solver-intro">2등 닉네임</p>
                </div>
              </div>
              <div class="col-2">
                <div class="solver-card-rank">03</div>
                <div class="solver-card-content">
                  <img src="@/assets/예시.jpg" class="solver-card-img" alt="solver-card-img">
                  <p class="solver-intro">3등 닉네임</p>
                </div>
              </div>
              <div class="col-2">
                <div class="solver-card-rank">04</div>
                <div class="solver-card-content">
                  <img src="@/assets/예시.jpg" class="solver-card-img" alt="solver-card-img">
                  <p class="solver-intro">4등 닉네임</p>
                </div>
              </div>
              <div class="col-3"></div>                           
          </div>

          <div class="row solver-row">
            <div class="col-3"></div>
            <div class="col-2">
              <div class="solver-card-rank">05</div>
              <div class="solver-card-content">
                <img src="@/assets/예시.jpg" class="solver-card-img" alt="solver-card-img">
                <p class="solver-intro">5등 닉네임</p>
              </div>
            </div>
            <div class="col-2">
              <div class="solver-card-rank">06</div>
              <div class="solver-card-content">
                <img src="@/assets/예시.jpg" class="solver-card-img" alt="solver-card-img">
                <p class="solver-intro">6등 닉네임</p>
              </div>
            </div>
            <div class="col-2">
              <div class="solver-card-rank">07</div>
              <div class="solver-card-content">
                <img src="@/assets/예시.jpg" class="solver-card-img" alt="solver-card-img">
                <p class="solver-intro">7등 닉네임</p>
              </div>
            </div>
            <div class="col-2">
              <div class="solver-card-rank">08</div>
              <div class="solver-card-content">
                <img src="@/assets/예시.jpg" class="solver-card-img" alt="solver-card-img">
                <p class="solver-intro">8등 닉네임</p>
              </div>
            </div>
            <div class="col-1"></div>                           
          </div>

          <!-- 오른쪽 여백용 col-2 -->
          <div class="col-2"></div>
          <br>
        </div>

    
    
    </div>

    <!-- 4번째 메인 페이지(모임) -->
    <div class="container group">
      <div class="row">
        <div class="col-2"></div>
        <div class="col-8 group-title">
          <div class="semi-title">모임</div>
          <br>
          <div>열심히 공부중인 모임을 추천해드려요~</div>
          <br>
          <div class="group-card">
            <section class="page-contain">
              <a href="#" class="data-card">
                <h3>블루베리</h3>
                <h4>Python</h4>
                <h4>JAVA</h4>
                <h4>Vue.js</h4>
                <p>Aeneansed consectetur.</p>
                <span class="link-text">
                  모임 상세보기
                  <svg width="25" height="16" viewBox="0 0 25 16" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path fill-rule="evenodd" clip-rule="evenodd" d="M17.8631 0.929124L24.2271 7.29308C24.6176 7.68361 24.6176 8.31677 24.2271 8.7073L17.8631 15.0713C17.4726 15.4618 16.8394 15.4618 16.4489 15.0713C16.0584 14.6807 16.0584 14.0476 16.4489 13.657L21.1058 9.00019H0.47998V7.00019H21.1058L16.4489 2.34334C16.0584 1.95281 16.0584 1.31965 16.4489 0.929124C16.8394 0.538599 17.4726 0.538599 17.8631 0.929124Z" fill="#0F4C81"/>
                  </svg>
                </span>
              </a>
              <a href="#" class="data-card">
                <h3>SOLVER</h3>
                <h4>Python</h4>
                <p>Aenean lacinia bibendum nulla sed consectetur.</p>
                <span class="link-text">
                  모임 상세보기
                  <svg width="25" height="16" viewBox="0 0 25 16" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path fill-rule="evenodd" clip-rule="evenodd" d="M17.8631 0.929124L24.2271 7.29308C24.6176 7.68361 24.6176 8.31677 24.2271 8.7073L17.8631 15.0713C17.4726 15.4618 16.8394 15.4618 16.4489 15.0713C16.0584 14.6807 16.0584 14.0476 16.4489 13.657L21.1058 9.00019H0.47998V7.00019H21.1058L16.4489 2.34334C16.0584 1.95281 16.0584 1.31965 16.4489 0.929124C16.8394 0.538599 17.4726 0.538599 17.8631 0.929124Z" fill="#0F4C81"/>
                  </svg>
                </span>
              </a>
              <a href="#" class="data-card">
                <h3>나당연합군</h3>
                <h4>Python</h4>
                <h4>JAVA</h4>
                <p>Aenean lacinia bibendum nulla sed consectetur.</p>
                <span class="link-text">
                  모임 상세보기
                  <svg width="25" height="16" viewBox="0 0 25 16" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path fill-rule="evenodd" clip-rule="evenodd" d="M17.8631 0.929124L24.2271 7.29308C24.6176 7.68361 24.6176 8.31677 24.2271 8.7073L17.8631 15.0713C17.4726 15.4618 16.8394 15.4618 16.4489 15.0713C16.0584 14.6807 16.0584 14.0476 16.4489 13.657L21.1058 9.00019H0.47998V7.00019H21.1058L16.4489 2.34334C16.0584 1.95281 16.0584 1.31965 16.4489 0.929124C16.8394 0.538599 17.4726 0.538599 17.8631 0.929124Z" fill="#0F4C81"/>
                  </svg>
                </span>
              </a>
              <a href="#" class="data-card">
                <h3>솔버</h3>
                <h4>Python</h4>
                <h4>JAVA</h4>
                <h4>Vue.js</h4>
                <p>Aenean lacinia bibendum nulla sed consectetur.</p>
                <span class="link-text">
                  모임 상세보기
                  <svg width="25" height="16" viewBox="0 0 25 16" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path fill-rule="evenodd" clip-rule="evenodd" d="M17.8631 0.929124L24.2271 7.29308C24.6176 7.68361 24.6176 8.31677 24.2271 8.7073L17.8631 15.0713C17.4726 15.4618 16.8394 15.4618 16.4489 15.0713C16.0584 14.6807 16.0584 14.0476 16.4489 13.657L21.1058 9.00019H0.47998V7.00019H21.1058L16.4489 2.34334C16.0584 1.95281 16.0584 1.31965 16.4489 0.929124C16.8394 0.538599 17.4726 0.538599 17.8631 0.929124Z" fill="#0F4C81"/>
                  </svg>
                </span>
              </a>                            
            </section>
          </div>
        </div>
        <div class="col-2"></div>
      </div>
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
