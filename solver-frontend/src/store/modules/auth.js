import axios from "axios";
import router from "@/router";
import API from "@/API.js";

const state = {
  categoryList: [],
  possibleDay: [],
  possibleTime: [],
  accessToken: null,
  isFirst: false,
  mainChangeTrigger: false,
  userNickname: "",
  loginTime: null,
};

const getters = {
  isLoggedIn: (state) => (state.accessToken == null ? false : true),
  isFirst: (state) => state.isFirst,
  getAccessToken: (state) => state.accessToken,
  getUserNickname: (state) => state.userNickname,
  getLoginTime: (state) => state.loginTime,
};

const mutations = {
  SET_CATEGORY: (state, category) => {
    state.categoryList = category;
  },
  SET_POSSIBLE_DAY: (state, day) => {
    state.possibleDay = day;
  },
  SET_POSSIBLE_TIME: (state, time) => {
    state.possibleTime = time;
  },
  SET_ACCESS_TOKEN: (state, accessToken) => {
    state.accessToken = accessToken;
  },
  SET_IS_FIRST: (state, isFirst) => {
    state.isFirst = isFirst;
  },
  SET_USER_NICKNAME: (state, userNickname) => {
    state.userNickname = userNickname;
  },
  SET_MAIN_CHANGE_TRIGGER: (state) => {
    state.mainChangeTrigger = !state.mainChangeTrigger;
  },
  SET_LOGIN_TIME: (state) => {
    state.loginTime = new Date().getTime();
  },
};

const actions = {
  triggerMainReload({ commit }) {
    commit("SET_MAIN_CHANGE_TRIGGER");
  },
  signup(context, credentials) {
    axios({
      url: API.URL + API.ROUTES.signup,
      method: "post",
      data: credentials,
      headers: { Authorization: "Bearer " + this.getters.isLoggedIn },
    })
      .then((res) => {
        console.log(res);
        commit("SET_USER_NICKNAME", credentials.nickname);
        router.push({ path: "/" });
      })
      .catch(() => {
        console.log(credentials);
      });
  },
  tokenLogin({ commit }, token) {
    commit("SET_ACCESS_TOKEN", token);
    // this.state.accessToken = token;
    //토큰으로 닉네임을 찾아오기
    axios({
      url: API.URL + API.ROUTES.getNickname,
      method: "get",
      headers: { Authorization: "Bearer " + token },
    })
      .then((res) => {
        //닉네임을 찾고서 값이 없으면 회원가입으로, 있으면 isFirst 수정
        if (res.data == "") {
          router.push({ path: "/auth/signup1" });
        } else {
          // const info = {
          //   solverToken: token,
          //   nickname: res.data,
          // };
          // localStorage.setItem("userInfo", JSON.stringify(info));
          commit("SET_USER_NICKNAME", res.data);
          commit("SET_LOGIN_TIME");
          state.loginTime = new Date().getTime();
          // commit("SET_ACCESS_TOKEN", token);

          // const info2 = JSON.parse(localStorage.getItem("userInfo"));
          // router.go(-2)
          router.push({ path: "/" });
        }
      })
      .catch((e) => {
        console.log(e);
      });
  },
  login({ commit }) {
    axios({
      url: API.URL + API.ROUTES.login,
      method: "get",
      headers: { Authorization: "Bearer" },
    })
      .then((res) => {
        commit("SET_ACCESS_TOKEN", res.data.accessToken);
        router.push({ path: "/" });
      })
      .catch(() => {
        console.log();
      });
  },
  logout({ state, commit }) {
    axios({
      url: API.URL + API.ROUTES.logout,
      method: "get",
      headers: { Authorization: "Bearer " + state.accessToken },
    })
      .then(() => {
        router.push({ path: "/#" });
      })
      .catch(() => {
        router.push({ path: "/#" });
      });
    commit("SET_ACCESS_TOKEN", null);
    commit("SET_USER_NICKNAME", "");
    commit("SET_MAIN_CHANGE_TRIGGER");
  },
  // 카테고리 받기
  fetchSignupData({ commit }) {
    // back이랑 api 주소 확인
    axios
      .get(API.URL + API.ROUTES.signupData)
      .then((res) => {
        // back에서 data(res) 어떻게 담겨 오는지 확인
        console.log(res);
        commit("SET_CATEGORY", res.data.category);
        commit("SET_POSSIBLE_DAY", res.data.day);
        commit("SET_POSSIBLE_TIME", res.data.time);
      })
      .catch((err) => console.log(err));
  },
  // checkLoginTime() {
  //   const ttt = getters.getUserNickname();
  //   console.log(ttt);
  //   if (state.loginTime == 1) return;

  //   alert("0-0----");

  //   const time = state.loginTime.getTime();
  //   const currentTime = new Date().getTime();

  //   if (currentTime - time < 1000 * 60 * 60 * 2) {
  //     commit("SET_ACCESS_TOKEN", null);
  //     commit("SET_USER_NICKNAME", "");
  //     commit("SET_MAIN_CHANGE_TRIGGER");
  //   }
  // },
};

export default {
  state,
  getters,
  mutations,
  actions,
};
