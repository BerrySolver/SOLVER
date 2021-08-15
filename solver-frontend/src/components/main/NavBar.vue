<template>
  <nav v-if="needHide" class="navbar navbar-expand navbar-light">
    <div class="container-fluid navbar-body">
      <RouterLink :to="{ name: 'Main' }" class="nav-logo">
        <img src="@/assets/logo.png" alt="logo" height="40px" /><span style="font-size: 17px; vertical-align: middle;">SOLVER</span>
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
            <li class="nav-item">
              <RouterLink :to="{ name: 'Groups' }" class="nav-router">모임</RouterLink>
            </li>
            <li class="nav-item">
              <RouterLink :to="{ name: 'Solvers' }" class="nav-router">솔버</RouterLink>
            </li>
            </div>
            <div class="d-flex justify-content-center">
            <li class="nav-item" v-if="!isLoggedIn">
              <RouterLink :to="{ name: 'Login' }" class="nav-router">로그인</RouterLink>
            </li>
            <li class="nav-item" v-if="isLoggedIn">
              <RouterLink :to="`/my-profile/${userNickname}`" class="nav-router">마이프로필</RouterLink>
            </li>
            <li class="nav-item" v-if="isLoggedIn">
              <a @click="clickLogout()" class="nav-logout">로그아웃</a>
            </li>

            <!-- notification-alarm -->
            <li class="nav-item dropdown" data-bs-auto-close="outside" v-if="checkLogin()">
              <span class="dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="outside" data-bs-auto-close="false">
                <img class="notification-img" src="@/assets/notification-alarm.png">
              </span>
              <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuClickableInside">
                <li class="dropdown-item">
                  <div class="dropdown-tabs">
                    <span @click="getBasicNotification" class="dropdown-tab interval m-right-3">알림</span>
                    <span @click="getSentMessage" class="dropdown-tab interval">보낸 메세지</span>
                    <span @click="getReceivedMessage" class="dropdown-tab interval m-left-3">받은 메세지</span>                      
                  </div>
                </li>

                <!-- notificatiion [기본 알림] -->
                <li><hr class="line"></li>
                <li v-if="isSelectedTab['tab0']">
                  <div v-for="(notification, index) in notificationList" :key="'n' + index">
                    <div class="notification-content">{{ notification.content }} </div>
                    <hr class="line">
                  </div>
                </li>

                <!-- notification [보낸 메세지] -->
                <li v-if="isSelectedTab['tab1']">
                  <div>{{ sentMessage }}</div>
                </li>

                <!-- notification [받은 메세지] -->
                 <li v-if="isSelectedTab['tab2']">
                  <div>{{ receivedMessage }}</div>
                </li>               
              </ul>
            </li>

            <!-- notification-video -->
            <li class="nav-item dropdown interval" v-if="checkLogin()">
              <span class="dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                <img class="notification-img" src="@/assets/notification-video.png">
              </span>
              <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuButton1">
                <li><span class="dropdown-item" href="#">Action</span></li>
                <li><hr class="line"></li>
                <li><a class="dropdown-item" href="#">Another action</a></li>
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
import API from "@/API.js"
import axios from 'axios';
import { mapActions, mapGetters, mapState } from "vuex";

export default {
  name: "Navbar",
  data() {
    return {
      isSelectedTab: {
        'tab0': false,
        'tab1': false,
        'tab2': false,
      },
      notificationList : [],
      sentMessage: [],
      receivedMessage: [],
    }
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
        this.$route.name === "Login"
      );
    },
  },
  methods: {
    ...mapActions(['logout']),
    clickLogout() {
      this.logout();
    },

    // 기본알림 AXIOS GET
    getBasicNotification() {
      console.log('바로 왔냐')
      // 탭 전환
      this.isSelectedTab['tab0'] = true
      this.isSelectedTab['tab1'] = false
      this.isSelectedTab['tab2'] = false

      axios({
        url: API.URL + API.ROUTES.notificationsAlarm,
        method: "GET",
        headers: { Authorization: "Bearer " + this.accessToken},
      })
      .then((res) => {
        this.notificationList = res.data.notificationList
    })
      .catch((err) => console.log(err))
    },

    // 보낸 메세지 AXIOS GET
    getSentMessage() {
      // 탭 전환
      this.isSelectedTab['tab0'] = false
      this.isSelectedTab['tab1'] = true
      this.isSelectedTab['tab2'] = false

      axios({
        url: API.URL + API.ROUTES.notificationSentMessage,
        method: "GET",
        headers: { Authorization: 'Bearer ' + this.accessToken },
      })
      .then ((res) => 
      this.sentMessage = res.data.messageList)
      .catch ((err) => console.log(err))
    },

    // 받은 메세지 AXIOS GET
    getReceivedMessage() {
      // 탭 전환
      this.isSelectedTab['tab0'] = false
      this.isSelectedTab['tab1'] = false
      this.isSelectedTab['tab2'] = true

      axios({
        url: API.URL + API.ROUTES.notificationReceivedMessage,
        method: "GET",
        headers: { Authorization: 'Bearer ' + this.accessToken },
      })
      .then ((res) =>
      this.receivedMessage = res.data.messageList)
      .catch ((err) => console.log(err))
    },
  },
  created() {
    this.getBasicNotification()
  },
};
</script>


<style>
.dropdown-item:hover {
  background-color: transparent;
}

.dropdown-tab:hover {
  color: #0F4C81;
  font-weight: 600;
}

.dropdown-tabs {
  color: #84898c;
  font-size: 15px;
  font-weight: 400;
}

.navbar {
  background-color: white;
  border-bottom: 1px solid #658dc671;
  align-items: center;
  font-family: "NanumSquare", sans-serif;
  font-size: 15px;
  height: 56px;
  position: fixed;
  width: 100%;
  z-index: 100;
}

.navbar-body {
  width: 1190px;
}

.nav-logo {
  color: #658dc6;
  font-weight: 600;
  margin-right: 20px;
  text-decoration: none;
}

.nav-router {
  color: #84898c;
  margin-right: 20px;
  text-decoration: none;
}

.nav-logout {
  color: #84898c;
  margin-right: 20px;
  text-decoration: none;
  cursor: pointer;
}

.notification-content {
  color: #84898c;
  font-size: 14px;
  padding: 3px 0px 3px 15px;
}

.notification-img {
  background-color: #b5c7d3;
  border: 1px solid #b5c7d3;
  border-radius: 20%;
  height: 23px;
  padding: 3px 3px 2px 2px;
  width: 23px;
}

.notification-img:hover {
  background-color: #658dc6;
}
</style>
