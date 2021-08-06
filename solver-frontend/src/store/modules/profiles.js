import axios from "axios";
// import router from "@/router";
import API from "@/API.js";

const state = {
  userProfileInfo: [],
};

const getters = {

};

const mutations = {
  SET_USER_PROFILE: (state, userProfileInfo) => {
    state.userProfileInfo = userProfileInfo
  },
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
  }
};

export default {
  state,
  getters,
  mutations,
  actions,
};
