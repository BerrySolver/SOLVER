// import axios from 'axios'
// import API from '@/API.js'
import router from "@/router";

const state = {
  query: null,
  answerChangeTrigger: false,
  commentCreateTrigger: false,
};

const mutations = {
  SET_QUERY: (state, query) => {
    state.query = query;
  },
  SET_ANSWER_CHANGE_TRIGGER: (state) => {
    state.answerChangeTrigger = !state.answerChangeTrigger
  },
  SET_COMMENT_CREATE_TRIGGER: (state) => {
    state.commentCreateTrigger = !state.commentCreateTrigger
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
  triggerAnswerReload({ commit }) {
    commit("SET_ANSWER_CHANGE_TRIGGER")
  },
  triggerCommentReload({ commit }) {
    commit("SET_COMMENT_CREATE_TRIGGER")
  }
};

const getters = {};

export default {
  state,
  getters,
  mutations,
  actions,
};
