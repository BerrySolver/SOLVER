import Vue from "vue";
import VueRouter from "vue-router";
import auth from "@/store/modules/auth.js";

import Main from "@/views/main/Main.vue";

import Login from "@/views/auth/Login.vue";
import Logout from "@/views/auth/Logout.vue";
import Signup1 from "@/views/auth/Signup1.vue";
import Signup2 from "@/views/auth/Signup2.vue";

import Groups from "@/views/groups/Groups.vue";
import GroupCreate from "@/views/groups/GroupCreate.vue";
import GroupDetail from "@/views/groups/GroupDetail.vue";

import Questions from "@/views/questions/Questions.vue";
import QuestionsDetail from "@/views/questions/QuestionsDetail.vue";
import QuestionsCreate from "@/views/questions/QuestionsCreate.vue";
import QuestionsModify from "@/views/questions/QuestionsModify.vue";

import Solvers from "@/views/solvers/Solvers.vue";

import MyProfile from "@/views/profiles/MyProfile.vue";
import Profile from "@/views/profiles/Profile.vue";

import Notifications from "@/views/notifications/Notifications.vue";
import NotificationsVideo from "@/views/notifications/NotificationsVideo.vue";
import NotificationsDetail from "@/views/notifications/NotificationsDetail.vue";
import MessageForwarding from "@/views/notifications/MessageForwarding.vue";
import MessageReceptions from "@/views/notifications/MessageReceptions.vue";

import Reports from "@/views/reports/Reports.vue";

import Conference from "@/views/conference/ConferenceRoom.vue";
import ConferenceTest from "@/views/conference/ConferenceRoomTest.vue";

Vue.use(VueRouter);

const routes = [
  // main
  { path: "/", name: "Main", component: Main },

  // auth
  { path: "/auth/login", name: "Login", component: Login },
  { path: "/auth/logout", name: "Logout", component: Logout }, // logout api?
  { path: "/auth/signup1", name: "Signup1", component: Signup1 },
  { path: "/auth/signup2", name: "Signup2", component: Signup2 }, // signup 경로 2개

  // groups
  { path: "/groups", name: "Groups", component: Groups },
  { path: "/groups/create", name: "GroupCreate", component: GroupCreate },
  { path: "/groups/detail", name: "GroupDetail", component: GroupDetail },

  // questions
  { path: "/questions", name: "Questions", component: Questions }, // 질문 목록
  { path: "/questions/create", name: "QuestionsCreate", component: QuestionsCreate }, // 질문 작성
  { path: "/questions/:questionId", name: "QuestionsDetail", component: QuestionsDetail },
  { path: "/questions/modify", name: "QuestionsModify", component: QuestionsModify },

  // notifications
  { path: "/notifications", name: "Notifications", component: Notifications },
  { path: "/video-notifications", name: "NotificationsVideo", component: NotificationsVideo },
  { path: "/notifications/forwardings", name: "MessageForwarding", component: MessageForwarding },
  { path: "/notifications/receptions", name: "MessageReceptions", component: MessageReceptions },
  { path: "/notifications/detail", name: "NotificationsDetail", component: NotificationsDetail },

  // solver
  { path: "/solvers", name: "Solvers", component: Solvers },

  // profile
  { path: "/my-profile/:nickname", name: "MyProfile", component: MyProfile },
  { path: "/profiles/:nickname", name: "Profile", component: Profile },

  // reports
  { path: "/reports", name: "Reports", component: Reports },

  // conference
  { path: "/conference", name: "Conference", component: Conference, props: true },
  { path: "/conferenceTest", name: "ConferenceTest", component: ConferenceTest },
  { path: "/conferenceFinish", name: "conferenceFinish", redirect: { name: "Main" } },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
  scrollBehavior() {
    return { x: 0, y: 0 };
  },
});

// router.beforeEach((to, from, next) => {
//   const local = localStorage.getItem("vuex");

//   if (local == null) {
//     next();
//     return;
//   } else {
//     const loginTime = JSON.parse(local).auth.loginTime;
//     if (loginTime != null) {
//       const currentTime = new Date().getTime();
//       if (currentTime - loginTime > 1000 * 60 * 60 * 2) {
//         localStorage.clear();
//         location.href = "/#";
//         return;
//       }
//     }
//   }

//   next();
// });

export default router;
