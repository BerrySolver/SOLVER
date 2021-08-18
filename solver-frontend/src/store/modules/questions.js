// import axios from 'axios'
// import API from '@/API.js'
import router from "@/router";

const state = {
  query: null,
  questionChangeTrigger: false,
  answerChangeTrigger: false,
  commentCreateTrigger: false,
  commentDeleteTrigger: false,
  content: null,
  questionId: null,
  question: null,
};

const mutations = {
  SET_QUERY: (state, query) => {
    state.query = query;
  },
  SET_CONTENT: (state, content) => {
    state.content = content;
  },
  SET_QUESTION: (state, question) => {
    state.question = question;
  },
  SET_QUESTION_ID: (state, questionId) => {
    state.questionId = questionId;
  },
  SET_QUESTION_CHANGE_TRIGGER: (state) => {
    state.questionChangeTrigger = !state.questionChangeTrigger;
  },
  SET_ANSWER_CHANGE_TRIGGER: (state) => {
    state.answerChangeTrigger = !state.answerChangeTrigger;
  },
  SET_COMMENT_CREATE_TRIGGER: (state) => {
    state.commentCreateTrigger = !state.commentCreateTrigger;
  },
  SET_COMMENT_DELETE_TRIGGER: (state) => {
    state.commentDeleteTrigger = !state.commentDeleteTrigger;
  },
};

const actions = {
  setStateQuestionId({ commit }, questionId) {
    commit("SET_QUESTION_ID", questionId);
  },
  setStateQuestion({ commit }, question) {
    commit("SET_QUESTION", question);
  },
  setStateContent({ commit }, content) {
    commit("SET_CONTENT", content);
  },
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
  triggerQuestionReload({ commit }) {
    commit("SET_QUESTION_CHANGE_TRIGGER");
  },
  triggerAnswerReload({ commit }) {
    commit("SET_ANSWER_CHANGE_TRIGGER");
  },
  triggerCommentReload({ commit }) {
    commit("SET_COMMENT_CREATE_TRIGGER");
    commit("SET_COMMENT_DELETE_TRIGGER");
  },
};

const getters = {
  getQuestion(state) {
    return state.question;
  },
};

export default {
  state,
  getters,
  mutations,
  actions,
};
