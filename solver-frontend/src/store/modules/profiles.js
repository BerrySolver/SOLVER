import axios from "axios";
// import router from "@/router";
import API from "@/API.js";

const state = {
  userProfileInfo: [],
  userStatistics: [],
};

const getters = {

};

const mutations = {
  SET_USER_PROFILE: (state, userProfileInfo) => {
    state.userProfileInfo = userProfileInfo
  },
  SET_USER_STATISTICS: (state, userStatistics) => {
    state.userStatistics = userStatistics
  }
};

const actions = {
  profileSetting({commit}, nickname) {
    console.log(API.URL + `profiles/${nickname}/info`)
    axios.get(API.URL + `profiles/${nickname}/info`)
    .then((res) => {
      console.log(res)
      console.log(res.data)
      commit('SET_USER_PROFILE', res.data)
    })
    .catch((err) => console.log(err))
  },
  statisticSetting({commit}, nickname) {
    axios.get(API.URL + `profiles/${nickname}/1`)
    .then((res) => {
      console.log('res', res)
      console.log('res.data', res.data)
      commit('SET_USER_STATISTICS', res.data)
    })
  }
};

export default {
  state,
  getters,
  mutations,
  actions,
};
