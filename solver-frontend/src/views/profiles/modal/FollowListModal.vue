<template>
  <div class="follow-list-modal solver-font">
    <h3 v-if="this.data.mode == 0" class="follow-list-title">팔로잉 목록</h3>
    <h3 v-else class="follow-list-title">팔로워 목록</h3>
    <div class="follow-list-box">
      <div v-for="(user, index) in userList" :key="index" class="follow-detail" @click="goUserProfile(user.nickname)">
          <img class="follow-img" :src="user.profileUrl" alt="">
          <span class="follow-nickname">{{user.nickname}}</span>
      </div>
    </div>
    <button class="btn follow-list-button" @click="$emit('close')">나가기</button>
  </div>
</template>

<script>
import axios from "axios";
import API from "@/API.js";
import {mapState} from 'vuex'

export default {
  data:function(){
    return {
      userList: [],
    }
  }, props : [
    'data'
  ], methods : {
    goUserProfile: function (nickname) {
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
      this.$emit('close');
    }
  }, 
  computed: {
    ...mapState({
      accessToken: state => state.auth.accessToken,
      userNickname: state => state.auth.userNickname
    }),
  }, created(){
    axios({
      url: API.URL + `profiles/${this.data.nickname}/follow-list/${this.data.mode}`,
      method: "get",
      headers: { Authorization: "Bearer " + this.accessToken},
    })
    .then((res) => {
      this.userList = res.data.followList
    })
    .catch((err) => {
      console.log(err);
    });
  }
}
</script>

<style>
  .follow-detail {
    border-bottom: 1px solid #e0e0e0;
    cursor: pointer;
    padding: 20px 0px;
    transition: 0.2s;
  }

  .follow-detail:hover {
    background-color: #ecf4ff;
  }

  .follow-img {
    border-radius: 70%;
    height: 50px;
    width: 50px;
    object-fit: cover;
  }

  .follow-list-box {
    border-top: 1px solid #e0e0e0;
    height: 500px;
    margin-top: 20px;
    overflow: auto;
  }
 
  .follow-list-box::-webkit-scrollbar {
      width: 10px; /*스크롤바의 너비*/
  }

  .follow-list-box::-webkit-scrollbar-thumb {
      background-color: #B5C7D3; /*스크롤바의 색상*/
      /* background-clip: padding-box; */
      border: 4px solid transparent;
      border-radius: 50px;
      /* border-top-right-radius: 50px;
      border-bottom-left-radius: 50px; */
  }

  .follow-list-box::-webkit-scrollbar-track {
      background-color: rgb(245, 245, 245); 
      border-radius: 50px;
  }

  .follow-list-button {
    margin-top: 25px;
    border: 1px solid #0F4C81;
    color: #0F4C81;
    width: 500px;
    padding: 10px 0px;
  }

  .follow-list-button:hover {
    margin-top: 25px;
    background:  #658dc665;
    color: white;
    border: 1px solid white;
    width: 500px;
    padding: 10px 0px;
  }

  .follow-list-modal {
     padding: 40px 50px;
  }

  .follow-nickname {
    color: #84898C;
    font-size: 17px;
    margin-left: 20px;
  }
</style>