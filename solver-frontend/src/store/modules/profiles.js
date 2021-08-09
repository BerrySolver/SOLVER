import axios from "axios";
import API from "@/API.js";
import auth from "./auth.js"

const state = {
  myQuestions: [],
  userProfileInfo: [],
  userStatistics: [],
};

const getters = {

};

const mutations = {
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
  profileSetting({commit}, nickname) {
    axios.get(API.URL + `profiles/${nickname}/info`)
    .then((res) => {
      console.log(res.data)
      commit('SET_USER_PROFILE', res.data)
    })
    .catch((err) => console.log(err))
  },
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
};

export default {
  state,
  getters,
  mutations,
  actions,
};
