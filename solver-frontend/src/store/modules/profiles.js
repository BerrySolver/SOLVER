import axios from "axios";
import API from "@/API.js";
import auth from "./auth.js"

const state = {
  myAnswers: [],
  myQuestions: [],
  userProfileInfo: [],
  userStatistics: [],
};

const getters = {

};

const mutations = {
  SET_MY_ANSWERS: (state, myAnswers) => {
    state.myAnswers = myAnswers
  },  
  SET_MY_QUESTIONS: (state, myQuestions) => {
    state.myQuestions = myQuestions
  },
  SET_USER_PROFILE: (state, userProfileInfo) => {
    state.userProfileInfo = userProfileInfo
  },
  SET_USER_STATISTICS: (state, userStatistics) => {
    state.userStatistics = userStatistics
  },
};

const actions = {
  // 답변목록 TAB(1)
  myAnswersSetting({commit}, myAnswersInfo) {
    axios({
      url: API.URL + `profiles/${myAnswersInfo.userNickname}/tab`,
      method: "get",
      headers: { Authorization: "Bearer " + auth.accessToken},
      params: {
        tabNum: myAnswersInfo.tabnum,
      }
    })
    .then((res) => {
      console.log('///', res.data)
      commit('SET_MY_ANSWERS', res.data)
    })
    .catch((err) => console.log(err))
  },  

  // 질문목록 TAB(2)
  myQuestionsSetting({commit}, myQuestionsInfo) {
    axios({
      url: API.URL + `profiles/${myQuestionsInfo.userNickname}/tab`,
      method: "get",
      headers: { Authorization: "Bearer " + auth.accessToken},
      params: {
        tabNum: myQuestionsInfo.tabnum,
      }
    })
    .then((res) => {
      commit('SET_MY_QUESTIONS', res.data)
    })
    .catch((err) => console.log(err))
  },

  // SOLVE 기록 TAB(0)
  statisticSetting({commit}, statisticInfo ) {
    axios({
      url: API.URL + `profiles/${statisticInfo.userNickname}/tab`,
      method: "get",
      headers: { Authorization: "Bearer " + auth.accessToken},
      params: {
        tabNum: statisticInfo.tabnum,
      }
    })
    .then((res) => {
      commit('SET_USER_STATISTICS', res.data)
    })
  },

  // 프로필 기본정보 SETTING
  profileSetting({commit}, nickname) {
    axios.get(API.URL + `profiles/${nickname}/info`)
    .then((res) => {
      console.log(res.data)
      commit('SET_USER_PROFILE', res.data)
    })
    .catch((err) => console.log(err))
  },
};

export default {
  state,
  getters,
  mutations,
  actions,
};
