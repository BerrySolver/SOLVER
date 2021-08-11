// import axios from 'axios'
// import API from '@/API.js'
import router from "@/router";

const state = {
  query: null,
  commentCreateTrigger: false,
  commentDeleteTrigger: false,
};

const mutations = {
  SET_QUERY: (state, query) => {
    state.query = query;
  },
  SET_COMMENT_CREATE_TRIGGER: (state) => {
    state.commentCreateTrigger = !state.commentCreateTrigger
  },
  SET_COMMENT_DELETE_TRIGGER: (state) => {
    state.commentDeleteTrigger = !state.commentDeleteTrigger
  }
};

const actions = {
  setStateQuery({ commit }, query) {
    commit("SET_QUERY", query);
  },
  setStateQueryMain({ commit }, query) {
    commit("SET_QUERY", query);
    router.push({ path: "/questions" });
  },
  goQuestionDetail(context, questionId) {
    router.push({
      name: "QuestionsDetail",
      params: {
        questionId: questionId,
      },
    });
  },
  triggerCommentReload({ commit }) {
    commit("SET_COMMENT_CREATE_TRIGGER")
    commit("SET_COMMENT_DELETE_TRIGGER")
  },
};

const getters = {};

export default {
  state,
  getters,
  mutations,
  actions,
};
