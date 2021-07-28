import Vue from 'vue'
import VueRouter from 'vue-router'

import Main from '@/views/main/Main.vue'

import Login from '@/views/auth/Login.vue'
import Logout from '@/views/auth/Logout.vue'
import Signup1 from '@/views/auth/Signup1.vue'
import Signup2 from '@/views/auth/Signup2.vue'

import Groups from '@/views/groups/Groups.vue'
import GroupCreate from '@/views/groups/GroupCreate.vue'
import GroupDetail from '@/views/groups/GroupDetail.vue'

import Questions from '@/views/questions/Questions.vue'
import QuestionsDetail from '@/views/questions/QuestionsDetail.vue'
import QuestionsCreate from '@/views/questions/QuestionsCreate.vue'

import Solvers from '@/views/solvers/Solvers.vue'

import Profile from '@/views/profiles/Profile.vue'

import Notifications from '@/views/notifications/Notifications.vue'
import NotificationsVideo from '@/views/notifications/NotificationsVideo.vue'
import NotificationsDetail from '@/views/notifications/NotificationsDetail.vue'
import MessageForwarding from '@/views/notifications/MessageForwarding.vue'
import MessageReceptions from '@/views/notifications/MessageReceptions.vue'

import Reports from '@/views/reports/Reports.vue'

Vue.use(VueRouter)

const routes = [
  // main
  { path: '/',  name: 'Main', component: Main },

  // auth
  { path: '/auth/login',  name: 'Login', component: Login },
  { path: '/auth/logout',  name: 'Logout', component: Logout }, // logout api?
  { path: '/auth/signup1',  name: 'Signup1', component: Signup1 },
  { path: '/auth/signup2',  name: 'Signup2', component: Signup2 }, // signup 경로 2개

  // groups
  { path: '/groups',  name: 'Group', component: Groups },
  { path: '/groups/create',  name: 'GroupCreate', component: GroupCreate },
  { path: '/groups/detail',  name: 'GroupDetail', component: GroupDetail },

  // questions
  { path: '/questions',  name: 'Question', component: Questions }, // 질문 목록
  { path: '/questions/create',  name: 'QuestionsCreate', component: QuestionsCreate }, // 질문 작성
  // question 목록이랑 create랑 같은 경로일 수가 있나?
  { path: '/:question_id',  name: 'QuestionsDetail', component: QuestionsDetail },

  // notifications
  { path: '/notifications',  name: 'Notifications', component: Notifications },
  { path: '/video-notifications',  name: 'NotificationsVideo', component: NotificationsVideo },
  { path: '/notifications/forwardings',  name: 'MessageForwarding', component: MessageForwarding },
  { path: '/notifications/receptions',  name: 'MessageReceptions', component: MessageReceptions },
  // api에 이 경로 없음
  { path: '/notifications/detail',  name: 'NotificationsDetail', component: NotificationsDetail },


  // solver
  { path: '/solvers',  name: 'Solvers', component: Solvers },

  // profile
  { path: '/profiles/:nickname',  name: 'Profile', component: Profile },

  // reports
  { path: '/reports',  name: 'Reports', component: Reports },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
