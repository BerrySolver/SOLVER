<template>
  <div class="solver-bg">
    <div class="solver-header">
      <div class="solver-banner">
        <img src="@/assets/logo-white-2.png" alt="white-logo">
        <div class="solver-banner-ment">
          <h6 class="ment">여러분이 좋아하는 답변자는 다른 사람의 기준과는 다를거에요!</h6>
          <h6 class="ment">많은 <span>능력자</span> 중 여러분의 마음에 드는 분을 찾아보세요!</h6>
        </div>
      </div>
    </div>
    <div class="solver-body">
      <!-- 검색창 -->
      <div class="solver-body-contents">
        <div class="solver-form-group search-col">
            <input class="solver-form-field" type="text" placeholder="닉네임을 검색해보세요!" @keypress.enter="setQuery" v-model="query">
            <span @click="setQuery">검&nbsp;&nbsp;색</span>
        </div>
        <div class="search-col">
          <select class="form-select" @change="selectSort($event)">
            <option :value="''">검색기준</option>
            <option v-for="(item, index) in sortOptions" :key="index" :value="item.value" >{{item.text}}</option>
          </select>
        </div>
        <div class="search-col">
          <select class="form-select" @change="setSubCategory($event)">
            <option :value="''">대분류</option>
            <option v-for="(item, index) in Category" :key="index" :value="item.code">{{item.codeName}}</option>
          </select>
        </div>
        <div class="search-col">
          <select id="sub-select" class="form-select" @change="setResultCategory($event)">
            <option :value="''">소분류</option>
            <option v-for="(item, index) in subCategory" :key="index" :value="item.subCategoryCode">{{item.subCategoryName}}</option>
          </select>
        </div>
      </div>
    </div>
    <div style="min-height:500px;">
      <!-- 실제 리스트 -->
      <div class="solver-body" v-for="(solvers, idx1) in solverList" :key="idx1">
        <div class="solver-body-contents">
          <div class="solver-list">
            <div class="solver-user-card" v-for="(solverData, idx2) in solvers" :key="idx2" @click="goUserProfile(solverData.nickname)">
              <img class="user-level-badge" src="@/assets/berry-1.png" v-if="parseInt(solverData.point) < 100" alt="">
              <img class="user-level-badge" src="@/assets/berry-2.png" v-if="parseInt(solverData.point) >= 100 && parseInt(solverData.point) < 200" alt="">
              <img class="user-level-badge" src="@/assets/berry-3.png" v-if="parseInt(solverData.point) >= 200 && parseInt(solverData.point) < 300" alt="">
              <img class="user-level-badge" src="@/assets/berry-4.png" v-if="parseInt(solverData.point) >= 300 && parseInt(solverData.point) < 400" alt="">
              <img class="user-level-badge" src="@/assets/berry-5.png" v-if="parseInt(solverData.point) >= 400 " alt="">
              <div class="solver-user-col1">
                <img class="user-image" src="@/assets/logo.png"/>
              </div>   
              <div class="detail-nickname">
                <div>{{solverData.nickname}}</div>
              </div>     
              <div class="detail-text box-for-intro">
                <div class="box-for-intro">
                 {{solverData.introduction}}
                </div>
              </div>
              <div class="box-for-category">
                <span class="detail-category" v-for="(field, idx3) in solverData.favoriteFieldNameList" :key="idx3">{{field}}</span>  
              </div>      
              <div class="solver-user-row">
                <img src="@/assets/icon-follow.png" />
                {{solverData.follower}} &nbsp;&nbsp;
                <img src="@/assets/icon-blueberry.png" />
                {{solverData.point}} &nbsp;&nbsp; 
                <img src="@/assets/icon-evaluation.png" />
                {{isNaN(solverData.evaluationScore) ? 0 : parseFloat(solverData.evaluationScore).toFixed(2)}} <span>/10</span>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="solver-body" v-if="solverCnt == 0 && isLoaded">
        <img style="width:600px;" src="@/assets/no-solver.png">
      </div>
    </div>
  </div>
</template>

<script>
import API from "@/API.js";
import axios from "axios";
import { mapState } from 'vuex';

export default {
  name: 'Solvers',
  data(){
    return {
      sortOptions: [
        {text:'팔로워 순', value:"follwerDesc"},
        {text:'베리 평점 순', value:"pointDesc"},
        {text:'베리 포인트 순', value:"evaluationDesc"},
      ],
      Category: [],
      subCategory: [],
      // 검색 Params
      sortValue: "",
      query: "",
      mainCategoryCode: "",
      subCategoryCode: "",
      // 실질 List
      solverList: [],
      solverCnt: 0,
      isLoaded: false,
    }
  }, methods:{
    setQuery(){
      this.setSolverList();
    }, selectSort(event){
      this.sortValue = event.target.value;
      this.setSolverList();
    }, setSubCategory(event) { 
      this.subCategoryCode = "";
      this.mainCategoryCode = event.target.value;
      document.getElementById("sub-select").value="";
      this.Category.forEach(element => {
        if(element.code == event.target.value){
          this.subCategory = element.category;
        }
      });
      this.setSolverList();
    }, setResultCategory(event){
      this.subCategoryCode = event.target.value;
      console.log("Sub : " + this.subCategoryCode);
      this.setSolverList();
    }, goUserProfile(nickname){
        if (nickname !== this.userNickname) {
          this.$router.push({
            name: 'Profile',
            params: {
              nickname: nickname
            }
          });
        } else {
          this.$router.push({
            path: `/my-profile/${this.userNickname}`
          })
        }
    }, setSolverList(){
      axios({
        url: API.URL + API.ROUTES.getSolverList,
        method: "get",
        params: {
          mainCategory: this.mainCategoryCode,
          subCategory: this.subCategoryCode,
          query: this.query,
          mode: this.sortValue
        }
      })
      .then((res)=>{
        this.solverList = [];
        var tempList = [];
        var n = 4; // 행에 몇개를 보일 것인지 확인
        var len = 0;
        for (var i in res.data.list){
          if (i % n == 0)
            tempList = [];
          tempList.push(res.data.list[i]);
          if (i % n == n-1){
            this.solverList.push(tempList);
            len += tempList.length;
          }
        }

        if(tempList.length < n){
          this.solverList.push(tempList);
          len += tempList.length;
        }

        this.solverCnt = len;
      })
      .catch(()=>{
        console.log();
      });
    }
  },
  created() {
    axios({
      url: API.URL + API.ROUTES.getCategory,
      method: "get",
    })
    .then((res) => {
      this.Category = res.data;
      setTimeout(() => {
        this.isLoaded = true
      }, 1000)
    })
    .catch(() => {
      console.log();
    });
    this.setSolverList();
  },
  computed: {
    ...mapState({
      userNickname: state => state.auth.userNickname,
    }),
  }
}
</script>

<style>
  .box-for-intro {
    margin-top: 4px;
    height: 40px;
    padding: 0px 2px 0px 8px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    /* display: inline-block; */
  }

  .box-for-category {
    height: 20px;
    padding: 0px 1px 0px 5px;
  }

  .detail-text {
    color: #658DC6;
    font-size: 14px;
    text-align: center;
  }

  .detail-nickname {
    color: #0F4C81;
    margin-top: 10px;
    font-size: 22px;
    font-weight: 700;
  }

  .form-select{
    width: 280px;
    margin: 0px 5px 0px 0px;
  }

  img.user-image {
    width:100px;
    height:100px;
    opacity: 60%;
    border: 1px solid #658dc673;
    border-radius: 100%;
  }

  img.user-level-badge{
    position: absolute;
    width: 60px;
    left: 70px;
    top: 0px;
  }

  .search-col{
    width: 290px;
    padding: 0px 3px;
  }

  select{
    cursor: pointer;
  }

  .solver-form-field {
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

  .solver-form-field::-moz-placeholder {
    color: #CBD1DC;
  }

  .solver-form-field:-ms-input-placeholder {
    color: #CBD1DC;
  }

  .solver-form-field::placeholder {
    color: #CBD1DC;
  }

  .solver-form-field:focus {
    outline: none;
    border-color: #658DC6;
  }

  .solver-form-group {
    display: flex;
    float: left;
  }

  .solver-form-group > span,
  .solver-form-group .solver-form-field {
    white-space: nowrap;
    display: block;
  }

  .solver-form-group > span:not(:first-child):not(:last-child),
  .solver-form-group .solver-form-field:not(:first-child):not(:last-child) {
    border-radius: 0;
  }

  .solver-form-group > span:first-child,
  .solver-form-group .solver-form-field:first-child {
    border-radius: 6px 0 0 6px;
  }

  .solver-form-group > span:last-child,
  .solver-form-group .solver-form-field:last-child {
    border-radius: 0 6px 6px 0;
  }

  .solver-form-group > span:not(:first-child),
  .solver-form-group .solver-form-field:not(:first-child) {
    margin-left: -1px;
  }

  .solver-form-group .solver-form-field {
    position: relative;
    z-index: 1;
    flex: 1 1 auto;
    width: 1%;
    margin-top: 0;
    margin-bottom: 0;
  }

  .solver-form-group > span {
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

  .solver-form-group:focus-within > span {
    color: #fff;
    background: #678EFE;
    border-color: #658DC6;
  }

  .solver-banner {
    align-items: center;
    display: flex;
  }

  .solver-banner img {
    height: 100px;
  }

  .solver-banner-ment {
    height: 40px;
    margin-left: 40px;
    text-align: left;
  }

  .solver-body {
    display: flex;
    justify-content: center;
    margin: 15px 0px 0px 0px;
  }

  .solver-list {
    display: flex;
    justify-content: center;
    margin: 15px 0px 0px 0px;
  }

  .solver-body-contents {
    display: flex;
    width: 1190px;
  }

  .solver-bg {
    background-color: #f7f7f7;
  }

  .solver-header {
    background-color: #658DC6;
    display: flex;
    height: 180px;
    justify-content: center;
  }

  .solver-select{
    margin-left: 5px;
  }

  .solver-user-card {
    border: 3px solid #658DC6;
    border-width: 5px 0px 1px 0px ;
    width: 260px;
    height: 300px;
    margin: 0px 15px 0px 15px;
    background-color: white;
    border-radius: 2px;
    position: relative;
  }

  .solver-user-card:hover {
    /* border: 1px solid white; */
    background-color: #ecf4ff;
    transition: color 0.2s;
    cursor: pointer;
  }

  .solver-user-col1 {
    margin: auto;
    margin-top: 20px;
  }


  .solver-user-row{
    position: absolute;
    bottom: 10px;
    height: 20px;
    width: 100%;
    color: #B5C7D3;
  }

  .solver-user-row > img {
    width: 15px;
  }


  span.detail-category{
    border: 1px solid #658DC6;
    padding-left: 2px;
    padding-right: 2px;
    margin-bottom: 2px;
    margin-right: 3px;
    color: #0F4C81;
    /* background-color: #658DC6; */
    display: inline-block;
    font-size:14px;
  }
</style>
