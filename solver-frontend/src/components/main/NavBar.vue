<template>
  <nav v-if="needHide" class="navbar navbar-expand navbar-light">
    <div class="container-fluid navbar-body">
      <RouterLink :to="{ name: 'Main' }" class="nav-logo">
        <img src="@/assets/logo.png" alt="logo" height="40px" /><span
          style="font-size: 17px; vertical-align: middle;"
          >SOLVER</span
        >
      </RouterLink>
      <button
        class="navbar-toggler"
        type="button"
        aria-controls="navbarNav"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNav">
        <div class="navbar-nav" style="width: 100%;">
          <div class="d-flex justify-content-between" style="width: 100%;">
            <div class="d-flex justify-content-center">
              <li class="nav-item">
                <RouterLink :to="{ name: 'Questions' }" class="nav-router">질문/답변</RouterLink>
              </li>
              <!-- <li class="nav-item">
                <RouterLink :to="{ name: 'Groups' }" class="nav-router">모임</RouterLink>
              </li> -->
              <li class="nav-item">
                <RouterLink :to="{ name: 'Solvers' }" class="nav-router">솔버</RouterLink>
              </li>
            </div>
            <div class="d-flex justify-content-center">
              <li class="nav-item" v-if="!isLoggedIn">
                <RouterLink :to="{ name: 'Login' }" class="nav-router">로그인</RouterLink>
              </li>
              <li class="nav-item" v-if="isLoggedIn">
                <RouterLink :to="`/my-profile/${userNickname}`" class="nav-router"
                  >마이프로필</RouterLink
                >
              </li>
              <li class="nav-item" v-if="isLoggedIn">
                <a @click="clickLogout()" class="nav-logout">로그아웃</a>
              </li>

              <!-- notification for 기본 알람 -->
              <li class="nav-item dropdown" v-if="isLoggedIn" @click="getNotifications">
                <!-- notification [NAVBAR 삽입 로고] -->
                <span
                  class="dropdown-toggle"
                  type="button"
                  id="dropdownMenuClickableInside"
                  data-bs-toggle="dropdown"
                  data-bs-auto-close="outside"
                  aria-expanded="false"
                >
                  <img class="notification-img" src="@/assets/notification-alarm.png" />
                </span>

                <!-- notification [dropdown TAB] -->
                <ul class="dropdown-menu dropdown-menu-end">
                  <li class="dropdown-item" aria-labelledby="dropdownMenuClickableInside">
                    <div class="dropdown-tabs">
                      <div><img src="@/assets/logo-white.png" width="20px" /></div>
                      <div class="dropdown-tab">알림</div>
                    </div>
                  </li>

                  <!-- notificatiion [구분선] -->
                  <li><hr class="line" /></li>

                  <!-- notificatiion [dropdown 알림 Items] -->
                  <li v-if="!notificationList.length == 0" class="notification-scroll">
                    <div
                      v-for="(notification, index) in notificationList"
                      :key="'n' + index"
                      @click="fromNotiToQuestion(notification.questionId)"
                      class="notification-item-set"
                    >
                      <div class="notification-title" v-if="notification.code != 66">
                        '{{ notification.title }}'
                      </div>
                      <div class="notification-title" v-if="notification.code == 66">
                        '팔로우가 추가되었습니다!'
                      </div>

                      <div class="notification-explanation">
                        <span v-if="notification.code == 60"
                          >라는 글에 새로운 <span class="notification-highlight">답변</span>이
                          달렸습니다.</span
                        >
                        <span v-if="notification.code == 61"
                          >라는 글에 새로운 <span class="notification-highlight">댓글</span>이
                          달렸습니다.</span
                        >
                        <span v-if="notification.code == 62"
                          >라는 글에 <span class="notification-highlight">좋아요</span>가
                          달렸습니다.</span
                        >
                        <span v-if="notification.code == 63"
                          >에 대한 답변에 <span class="notification-highlight">좋아요</span>가
                          달렸습니다.</span
                        >
                        <span v-if="notification.code == 64"
                          >라는 글이
                          <span class="notification-highlight">북마크</span>되었습니다.</span
                        >
                        <span v-if="notification.code == 65"
                          >라는 글이
                          <span class="notification-highlight">채택</span>되었습니다.</span
                        >
                        <span v-if="notification.code == 66"
                          >새로운 솔버가
                          <span class="notification-highlight">팔로우</span>했습니다.</span
                        >
                        <span class="notification-dateTime">{{
                          humanize(now, notification.regDt)
                        }}</span>
                      </div>

                      <hr class="notification-line" />
                    </div>
                  </li>

                  <li v-if="notificationList.length == 0">
                    <img src="@/assets/no-notification.png" class="no-notification-img" />
                  </li>
                </ul>
              </li>

              <!-- notification-video -->
              <li
                class="nav-item dropdown interval"
                v-if="isLoggedIn"
                @click="getVideoNotifications"
              >
                <!-- notification video [NAVBAR 삽입 로고] -->
                <span
                  class="dropdown-toggle"
                  type="button"
                  id="dropdownMenuClickableInside"
                  data-bs-toggle="dropdown"
                  data-bs-auto-close="outside"
                  aria-expanded="false"
                >
                  <img class="notification-img" src="@/assets/notification-video.png" />
                </span>

                <!-- notification [dropdown TAB] -->
                <ul class="dropdown-menu dropdown-menu-end">
                  <li class="dropdown-item" aria-labelledby="dropdownMenuClickableInside">
                    <div class="video-dropdown-tabs">
                      <div><img src="@/assets/logo-white.png" width="20px" /></div>
                      <div class="dropdown-tab">화상알림</div>
                    </div>
                  </li>

                  <!-- notificatiion [구분선] -->
                  <li><hr class="line" /></li>

                <!-- notificatiion [dropdown 알림 Items] -->
                <li v-if="!notificationVideoMsgList.length == 0" class="notification-scroll">
                  <div
                    v-for="(message, index) in notificationVideoMsgList"
                    :key="'m' + index"
                    
                    class="video-notification-item-set">
                    
                    <!-- TYPE 072 : 화상회의를 요청받음 -->
                    <div v-if="message.type == '072'" @click="fromNotiToQuestion( message.questionId )">
                      <div class="video-notification-title">
                        <span class="video-notification-datetime">{{ message.content }}</span>
                        <span class="one"> 에</span>
                      </div>

                      <div class="notification-explanation">
                        <span>
                          <span class="video-notification-highlight">{{ message.sendNickName }}</span>
                          <span>님으로부터 화상 회의가 요청되었습니다.</span>
                        </span> 
                      </div>

                      <div v-if="message.checked == 0" class="video-button">
                        <button class="video-yes-button" @click="OKButton(message)">수락</button>
                        <button class="video-no-button interval" @click="NOButton(message)">거절</button>
                      </div>

                      <hr class="notification-line">
                    </div>

                    <!-- TYPE 073 : 화상회의가 승인되었음을 알림 -->
                    <div v-if="message.type == '073'" @click="fromNotiToQuestion( message.questionId )">
                      <div class="video-notification-title">
                        <span class="video-notification-datetime">{{ message.sendNickName }}</span>
                        <span class="one">님이 화상 회의 요청을 </span>
                        <span class="video-notification-highlight">승인</span>
                        <span class="one">하셨습니다.</span>
                      </div>

                      <div class="reason">"{{ message.content }}"</div>

                      <hr class="notification-line">
                    </div>

                    <!-- TYPE 074 : 화상회의가 거절되었음을 알림 -->
                    <div v-if="message.type == '074'" @click="fromNotiToQuestion( message.questionId )">
                      <div class="video-notification-title">
                        <span class="video-notification-datetime">{{ message.sendNickName }}</span>
                        <span class="one">님이 화상 회의 요청을 </span>
                        <span class="video-notification-highlight">거절</span>
                        <span class="one">하셨습니다.</span>
                      </div>

                      <div class="reason">"{{ message.content }}"</div>

                      <hr class="notification-line">
                    </div>

                    <!-- TYPE 075 : 화상회의가 곧 진행될 것을 알림 -->
                    <div v-if="message.type == '075'">
                      <div class="video-notification-title" @click="fromNotiToQuestion( message.questionId )">
                        <span class="video-notification-datetime">{{ message.sendNickName }}</span>
                        <span class="one">님과의 화상회의가 곧 </span>
                        <span class="video-notification-highlight">시작</span>
                        <span class="one">됩니다!</span>
                      </div> 

                      <div class="reason">약속시간 : {{ message.content }}</div>                     

                      <div class="video-enter">
                        <button @click="pushToConference(message.questionId)" class="video-enter-button" >입장하기</button>
                      </div>
                      <hr class="notification-line">
                    </div>
                  </div>
                </li>

                <!-- 화상 알림이 0개일 때 -->
                <li v-if="notificationVideoMsgList.length == 0">
                  <img src="@/assets/no-notification.png" class="no-notification-img">
                </li>
              </ul>
            </li>
            
            </div>
          </div>
        </div>
      </div>
    </div>
  </nav>
</template>

<script>
import API from "@/API.js";
import axios from "axios";
import { mapActions, mapGetters, mapState } from "vuex";

import RejectModal from "@/components/main/RejectModal.vue";
import ApprovalModal from "@/components/main/ApprovalModal.vue";

export default {
  name: "Navbar",
  data() {
    return {
      notificationList: [],
      notificationVideoMsgList: [],
      now: new Date(),
    };
  },
  computed: {
    ...mapState({
      accessToken: (state) => state.auth.accessToken,
      userNickname: (state) => state.auth.userNickname,
    }),
    ...mapGetters(["isLoggedIn"]),
    needHide() {
      return !(
        this.$route.name === "Signup1" ||
        this.$route.name === "Signup2" ||
        this.$route.name === "Login" ||
        this.$route.name === "Conference"
      );
    },
  },
  methods: {
    ...mapActions(["logout", "goQuestionDetail", "triggerMainReload"]),
    clickLogout() {
      this.logout();
      this.triggerMainReload();
    },

    // 시간 humanize
    humanize: function(now, date) {
      const moment = require("moment");
      const dateData = new Date(date);
      let r = now - dateData;
      if (parseInt(r) > 43200000) {
        r = moment(dateData).format("YY-MM-DD\u00A0\u00A0HH:MM");
      } else if (parseInt(r) >= 3600000) {
        r = parseInt(parseInt(r) / 3600000).toString() + "시간 전";
      } else if (parseInt(r) >= 60000) {
        r = parseInt(parseInt(r) / 60000).toString() + "분 전";
      } else {
        r = "방금 전";
      }
      return r;
    },

    // 기본알림 AXIOS GET
    getNotifications() {
      axios({
        url: API.URL + API.ROUTES.notificationsAlarm,
        method: "GET",
        headers: { Authorization: "Bearer " + this.accessToken },
      })
        .then((res) => {
          this.notificationList = res.data.notificationList;

          // 만약, 알림이 20개가 넘어가면 자르기
          if (this.notificationList.length > 20) {
            const notificationArr = this.notificationList.splice(20, this.notificationList.length);
            return notificationArr;
          }
        })
        .catch((err) => console.log(err));
    },

    // 화상알림 AXIOS GET
    getVideoNotifications() {
      axios({
        url: API.URL + API.ROUTES.notificationReceivedMessage,
        method: "GET",
        headers: { Authorization: "Bearer " + this.accessToken },
      })
        .then((res) => {
          this.notificationVideoMsgList = res.data.messageList;
        })
        .catch((err) => console.log(err));
    },

    // 알림 → 질문 상세로 이동
    fromNotiToQuestion(questionId) {
      if (questionId == null) {
        this.$router.push({
          path: `/my-profile/${this.userNickname}`,
        });
      } else {
        this.goQuestionDetail(questionId);
      }
    },

    // MODAL - 화상회의 요청 승낙시
    OKButton(message){
        this.$modal.show(ApprovalModal,{
          type: "073",
          messageId: message.messageId,
          modal: this.$modal,
        },
        {
          name: "dynamic-modal",
          width: "600px",
          height: "250px",
          draggable: false,
        }
      );
    },

    // MODAL - 화상회의 요청 거절시
    NOButton(message){
        this.$modal.show(RejectModal,{
          type: "074",
          messageId: message.messageId, 
          modal : this.$modal },{
            name: 'dynamic-modal',
            width : '600px',
            height : '250px',
            draggable: false,
        });
    },

    // 화상회의 사이트로 이동
    pushToConference(questionId) {
      axios({
        //conference id 값은 테스트용
        url: API.URL + `questions/${questionId}/info`,
        method: "get",
        headers: { Authorization: "Bearer " + this.accessToken },
      })
        .then((res) => {
          console.log(res);
          const questionUserNickname = res.data.nickname;
          this.$router.push({
            name: "Conference",
            params: { questionId: questionId, questionUserNickname: questionUserNickname },
          });
        })
        .catch((e) => {
          console.log(e);
        });
    },
  },
};
</script>

<style>
@import "./NavBar.css";
</style>
