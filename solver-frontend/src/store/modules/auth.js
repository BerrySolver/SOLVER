import axios from 'axios'
// import router from '@/router'
import API from '@/API.js'

const state = {
  categoryList: [],
  possibleDay: [],
  possibleTime: [],
}

const getters = {

}

const mutations = {
  SET_CATEGORY: (state, category) => {
    state.categoryList = category
  },

  SET_POSSIBLE_DAY: (state, day) => {
    state.possibleDay = day
  },

  SET_POSSIBLE_TIME: (state, time) => {
    state.possibleTime = time
  },
}

const actions = {

  // 카테고리 받기
  fetchSignupData({commit}) {
    // back이랑 api 주소 확인
    axios.get(API.URL + API.ROUTES.signupData)
      .then(res => {
        // back에서 data(res) 어떻게 담겨 오는지 확인
        console.log(res)
        commit('SET_CATEGORY', res.data.category)
        commit('SET_POSSIBLE_DAY', res.data.day)
        commit('SET_POSSIBLE_TIME', res.data.time)
      })
      .catch(err => console.log(err))
  }
}

export default {
  state, getters, mutations, actions
}