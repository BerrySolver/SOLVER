import axios from "axios";
// import router from "@/router";
import API from "@/API.js";

const state = {
  myQuestions: [],
  userProfileInfo: [],
  userStatistics: [],
};

const getters = {

};

const mutations = {
  // SET_MY_QUESTIONS: (state, myQuestions) => {
  //   state.myQuestions = myQuestions
  // },
  SET_USER_PROFILE: (state, userProfileInfo) => {
    state.userProfileInfo = userProfileInfo
  },
  // SET_USER_STATISTICS: (state, userStatistics) => {
  //   state.userStatistics = userStatistics
  // },
};

const actions = {
  // myQuestionsSetting({commit}, nickname) {
  //   axios({
  //     url: API.URL + `profiles/${nickname}/2`,
  //     method: "get",
  //     headers: { Authorization: "Bearer " + this.getters.getAccessToken},
  //   })
  //   .then((res) => {
  //     console.log(res)
  //     commit('SET_MY_QUESTIONS', res.data)
  //   })
  //   .catch((err) => console.log(err))
  // },
  profileSetting({commit}, nickname) {
    axios.get(API.URL + `profiles/${nickname}/info`)
    .then((res) => {
      console.log(res.data)
      commit('SET_USER_PROFILE', res.data)
    })
    .catch((err) => console.log(err))
  },
  // statisticSetting({commit}, nickname, tabNum ) {
  //   axios({
  //     url: API.URL + `profiles/${nickname}/tab`,
  //     method: "get",
  //     headers: { Authorization: "Bearer " + state.accessToken},
  //     params: {
  //       tabNum: tabNum,
  //     }
  //   })
  //   .then((res) => {
  //     console.log('res.data', res.data)
  //     commit('SET_USER_STATISTICS', res.data)
  //   })
  // },
};

export default {
  state,
  getters,
  mutations,
  actions,
};
