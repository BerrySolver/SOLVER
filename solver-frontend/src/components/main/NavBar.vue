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

            <!-- notification for 기본 알람 -->
            <li class="nav-item dropdown" v-if="isLoggedIn">
              
              <!-- notification [NAVBAR 삽입 로고] -->
              <span class="dropdown-toggle" type="button" id="dropdownMenuClickableInside" data-bs-toggle="dropdown" data-bs-auto-close="outside" aria-expanded="false">
                <img class="notification-img" src="@/assets/notification-alarm.png">
              </span>

              <!-- notification [dropdown TAB] -->
              <ul class="dropdown-menu dropdown-menu-end">
                <li class="dropdown-item" aria-labelledby="dropdownMenuClickableInside">
                  <div class="dropdown-tabs">
                    <div><img src="@/assets/logo-white.png" width="20px"></div>
                    <div class="dropdown-tab">알림</div>         
                  </div>
                </li>
                
                <!-- notificatiion [구분선] -->
                <li><hr class="line"></li>

                <!-- notificatiion [dropdown 알림 Items] -->
                <li class="notification-scroll">
                  <div
                    v-for="(notification, index) in notificationList"
                    :key="'n' + index"
                    class="notification-item-set">

                    <hr class="line">

                    <div class="notification-title">'{{ notification.title }}'</div>
                    
                    <div class="notification-explanation">
                      <span>라는 글에 새로운 답변이 달렸습니다.</span> 
                      <button @click="fromNotiToQuestion( notification.questionId )" class="notification-togo-btn">이동</button>
                    </div>
                  </div>
                </li>

              </ul>
            </li>

            <!-- notification-video -->
            <li class="nav-item dropdown interval" data-bs-auto-close="outside" v-if="isLoggedIn">
              <span class="dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="outside" data-bs-auto-close="false">
                <img class="notification-img" src="@/assets/notification-video.png">
              </span>

              <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuClickableInside">
                <li class="dropdown-item">
                  <div class="dropdown-tabs">
                    <div><img src="@/assets/logo-white.png" width="20px"></div>
                    <div class="dropdown-tab">화상알림</div>         
                  </div>
                </li>

                <li><hr class="line"></li>

                <li>
                  <a class="dropdown-item" href="#">Another action</a>
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
import API from "@/API.js"
import axios from 'axios';
import { mapActions, mapGetters, mapState } from "vuex";

export default {
  name: "Navbar",
  data() {
    return {
      notificationList: [],
      notificationQuestionId: [],
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
    ...mapActions(['logout', 'goQuestionDetail']),
    clickLogout() {
      this.logout();
    },

    // 기본알림 AXIOS GET
    getNotifications() {
      axios({
        url: API.URL + API.ROUTES.notificationsAlarm,
        method: "GET",
        headers: { Authorization: "Bearer " + this.accessToken},
      })
      .then((res) => {
        this.notificationList = res.data.notificationList
        
        // 7개가 넘어가면 자르기
        if (this.notificationList.length > 7) {
          const notificationArr = this.notificationList.splice(7, this.notificationList.length)
          return notificationArr
        } 

      })
      .catch((err) => console.log(err))
    },

    getVideoNotifications() {
      axios({
        url: API.URL + API.ROUTES.notificationReceivedMessage,
        method: "GET",
        headers: { Authorization: "Bearer " + this.accessToken}
      })
      .then ((res) => {
        console.log(res.data)
        })
      .catch ((err) => console.log(err))
    }
  },
  created() {
    this.getNotifications()
    this.getVideoNotifications()
  }
};
</script>


<style>

.dropdown-toggle::after {
  display: none;
}

.dropdown-item:hover {
  background-color: transparent;
}

.dropdown-menu {
  padding: 0;
}

.dropdown-tab {
  color: #0F4C81;
  font-weight: 600;
}

.dropdown-tabs {
  color: #84898c;
  font-size: 16px;
  font-weight: 400;
  padding-top: 5px;
  text-align: center;
  width: 280px;
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

.notification-explanation {
  color: #84898c;
  display: flex;
  font-size: 14px;
  justify-content: space-between;
  padding: 0px 15px 10px 17px;
}

.notification-item-set:hover {
  background-color: #b5c7d333;
  /* border-left: 5px solid #658dc6; */
}

.notification-scroll {
  height: 500px;
  overflow-y: scroll;
  overflow-x: hidden;
}

.notification-title {
  color: #0F4C81;
  font-size: 15px;
  font-weight: 600;
  padding: 15px 15px 0px 17px;
}

.notification-togo-btn {
  background-color: transparent;
  border: transparent;
  color: #658dc6;
  font-size: 14px;
  padding: 1px;
  transform: skew(-0.001deg);
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
