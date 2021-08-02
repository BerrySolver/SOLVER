<template>
  <div class="main">
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
          </div>
        </div>

        <div class="col-5">

          <img src="@/assets/logo.png" alt="main-logo">

          <!-- <div class="slogan align-items-center">
            <div class="d-inline-block">당신의</div>
            <div class="d-inline-block slogan-center">
              질문은<br>답변은
            </div>
            <div class="d-inline-block">특별하다</div>
          </div> -->

        </div>


      </div>
        <!-- <div class="col-1"></div>
        <div class="form__group col-5">
          <input type="input" class="form__field" placeholder="Name" name="name" id='name' required />
          <label for="name" class="form__label">
            <span>질문을 검색해보세요!</span>
          </label>
        </div> -->


    </div>

    <div class="popular-question">
      <h5>인기 질문</h5>
    </div>
    <div class="month-solver">
      <h5>이달의 솔버</h5>
    </div>

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
