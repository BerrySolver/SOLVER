<template>
  <div class="main">
    <div class="container">
      <div class="row align-items-center">
        
        <div class="col-1"></div>

        <div class="col-5">
          <div class="title">솔버(SOLVER)</div>
          <div class="introduction">
            솔버 서비스에 대한 설명입니다. 솔버 서비스에 대한 설명입니다. 솔버 서비스에 대한 설명입니다. 솔버 서비스에 대한 설명입니다.
            솔버 서비스에 대한 설명입니다. 솔버 서비스에 대한 설명입니다. 솔버 서비스에 대한 설명입니다.
          </div>
        </div>

        <div class="col-5">
          <img src="@/assets/logo.png" alt="main-logo">
          <div class="slogan align-items-center">
            <div class="d-inline-block">당신의</div>
            <div class="d-inline-block slogan-center">
              질문은<br>답변은
            </div>
            <div class="d-inline-block">특별하다</div>
          </div>
        </div>
      </div>

      <div class="form__group field">
        <input type="input" class="form__field" placeholder="Name" name="name" id='name' required />
        <label for="name" class="form__label">Name</label>
      </div>

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
