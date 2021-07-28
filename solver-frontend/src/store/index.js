import Vue from 'vue'
import Vuex from 'vuex'

import auth from '@/store/modules/auth'
import groups from '@/store/modules/groups'
import notifications from '@/store/modules/notifications'
import profiles from '@/store/modules/profiles'
import questions from '@/store/modules/questions'
import reports from '@/store/modules/reports'
import solvers from '@/store/modules/solvers'
import videos from '@/store/modules/videos'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: { auth, groups, notifications, profiles, questions, reports, solvers, videos}
})
