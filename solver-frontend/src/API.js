export default {
  URL: 'http://localhost:8080/api/v1/',
  ROUTES: {
    // 회원가입, 로그인
    signup: 'user/signup',
    login: 'auth/login',
    logout: 'auth/logout',

    // 회원가입2 관련
    signupdata: 'user/signup/data',

    // 질문/답변 페이지
    getCategory: 'auth/category',
    getQuestionList: 'questions/list'
  }
}