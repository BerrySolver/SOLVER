export default {
  URL: "http://localhost:8080/api/v1/",
  ROUTES: {
    // 회원가입, 로그인
    signup: "user/signup",
    logout: "auth/logout",
    getNickname: "user/nickname",
    getCategory: "auth/category",
    checkNickname: "user/signup/check-nickname",

    // 회원가입2 관련
    signupdata: 'user/signup/data',

    // 질문/답변 페이지
    getQuestionList: 'questions/list',

    // 프로필
    editProfile: 'profiles'
  }
}
