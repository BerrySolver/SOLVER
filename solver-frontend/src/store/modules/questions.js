// import axios from 'axios'
// import API from '@/API.js'
import router from "@/router";

const state = {
  query: null,
}

const mutations = {
  SET_QUERY: (state, query) => {
    state.query = query;
  },
}

const actions = {
  setStateQuery({commit}, query) {
    commit('SET_QUERY', query)
    router.push({ path: "/questions" });
  },
  goQuestionDetail(context, questionId) {
    router.push({
      name: 'QuestionsDetail',
      params: {
        questionId: questionId
      }
    })
  }
}

const getters = {
}

export default {
  state, getters, mutations, actions
}