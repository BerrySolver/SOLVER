<template>
  <div>
    <div class="groups-header">
        <div class="groups-banner">
          <img src="@/assets/logo-white-2.png" alt="white-logo">
          <div class="solver-banner-ment">
            <h6 class="ment">같은 관심사를 가진 사람들과 소통하며 지식을 <span>공유</span>하고 싶나요?</h6>
            <h6 class="ment">여러분에게 맞는 <span>모임</span>을 찾아보세요!</h6>
          </div>
        </div>
    </div>
    <div class="groups">
      <div class="groups-container">
        <div class="group-form-group">
            <input class="group-form-field" type="text" placeholder="닉네임을 검색해보세요!">
            <span>검&nbsp;&nbsp;색</span>
        </div>
        <div class="group-list">
          <div
            class="group-card" 
            v-for="(group, idx) in groups" 
            :key="idx"
            @click="goGroupDetail()"
          >
            <img class="group-img" :src="group.img_url" alt="">

          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "Groups",
  data() {
    return {
      groups: [
        {
          group_name: 'SSAFY 5기',
          introduce: '오기로극뽁!',
          img_url: 'https://www.ssafy.com/swp/images/sns_img.png'
        },
        {
          group_name: '파이썬 병아리들',
          introduce: '우리 같이 Python을 배워봐요!',
          img_url: 'https://s3.ap-northeast-2.amazonaws.com/grepp-cloudfront/programmers_imgs/learn/thumb-course-phthon-basic.jpg'
        },
        {
          group_name: 'Java를 잡아라',
          introduce: 'Java에 입문하는 사람들의 모임',
          img_url: 'https://cdn.inflearn.com/wp-content/uploads/java_do_it4.jpg'
        },
        {
          group_name: '싸피고 3학년 5반',
          introduce: '담임쌤과 함께하는 방과후',
          img_url: 'https://img.seoul.co.kr/img/upload/2014/11/03/SSI_20141103172839.jpg'
        },
        {
          group_name: '오픽으로 취뽀',
          introduce: 'AL을 목표로 하는 모임입니다.',
          img_url: 'https://yt3.ggpht.com/ytc/AKedOLRQglLoe9sB4ewAgNbsYHFFncMeeCi04my7qu9pRg=s900-c-k-c0x00ffffff-no-rj'
        },
        {
          group_name: 'Guitar 독학 모임',
          introduce: '우리에게 유일하게 허락된 마약, 음악',
          img_url: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT0gkJPD6_s8IceLr8PK_iKuj-U8oUDiXrMPw&usqp=CAU'
        },
        {
          group_name: '하루 세 번 스트레칭!',
          introduce: '화상으로 함께 스트레칭해봐요~',
          img_url: 'https://www.k-health.com/news/photo/202104/53348_51821_4922.jpg'
        },
        
      ]
    }
  },
  methods: {
    goGroupDetail: function () {
      this.$router.push({name: 'GroupDetail'})
    }
  }
};
</script>

<style>
  .group-card {
    border: 3px solid #658DC6;
    border-width: 5px 0px 1px 0px ;
    cursor: pointer;
    width: 270px;
    height: 300px;
    margin: 15px 10px 15px 10px;
    background-color: white;
    border-radius: 2px;
  }
  
  .group-card:hover {
    background-color: #ecf4ff;
    transition: color 0.2s;
  }

  .group-form-field {
    display: block;
    width: 100%;
    height: 38px;
    padding: 8px 16px;
    line-height: 25px;
    font-size: 14px;
    font-weight: 500;
    font-family: inherit;
    border-radius: 6px;
    -webkit-appearance: none;
    color: #84898C;
    border: 1px solid #CDD9ED;
    background: #fff;
    transition: border 0.3s ease;
  }

  .group-form-field::-moz-placeholder {
    color: #CBD1DC;
  }

  .group-form-field:-ms-input-placeholder {
    color: #CBD1DC;
  }

  .group-form-field::placeholder {
    color: #CBD1DC;
  }

  .group-form-field:focus {
    outline: none;
    border-color: #658DC6;
  }

  .group-form-group {
    display: flex;
    margin: 15px 0 0 10px;
    width: 500px;
  }

  .group-form-group > span,
  .group-form-group .group-form-field {
    white-space: nowrap;
    display: block;
  }

  .group-form-group > span:not(:first-child):not(:last-child),
  .group-form-group .group-form-field:not(:first-child):not(:last-child) {
    border-radius: 0;
  }

  .group-form-group > span:first-child,
  .group-form-group .group-form-field:first-child {
    border-radius: 6px 0 0 6px;
  }

  .group-form-group > span:last-child,
  .group-form-group .group-form-field:last-child {
    border-radius: 0 6px 6px 0;
  }

  .group-form-group > span:not(:first-child),
  .group-form-group .group-form-field:not(:first-child) {
    margin-left: -1px;
  }

  .group-form-group .group-form-field {
    position: relative;
    z-index: 1;
    flex: 1 1 auto;
    width: 1%;
    margin-top: 0;
    margin-bottom: 0;
  }

  .group-form-group > span {
    text-align: center;
    padding: 5px 12px;
    font-size: 14px;
    line-height: 25px;
    width: 80px;
    height: 38px;
    color: #99A3BA;
    background: #EEF4FF;
    border: 1px solid #CDD9ED;
    transition: background 0.3s ease, border 0.3s ease, color 0.3s ease;
  }

  .group-form-group:focus-within > span {
    color: #fff;
    background: #678EFE;
    border-color: #658DC6;
  }

  .group-img {
    width: 270px;
    height: 170px;
    object-fit: cover;
  }

  .group-list {
    display: flex;
    flex-flow: row wrap;
    justify-content: start;
    margin: 15px 0px 0px 0px;
  }

  .groups {
    background-color: #f7f7f7;
    display: flex;
    min-height: 800px; 
    justify-content: center;
  }

  .groups-banner {
    align-items: center;
    display: flex;
  }

  .groups-banner img {
    height: 100px;
  }

  .groups-banner-ment {
    height: 40px;
    margin-left: 40px;
    text-align: left;
  }

  .groups-container {
    width: 1190px;
  }
  
  .groups-header {
    background-color: #658DC6;
    display: flex;
    height: 180px;
    justify-content: center;
  }
</style>
