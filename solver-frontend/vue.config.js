
// Vue3 관련 설정 파일
module.exports = {
  devServer: {
    https: true,
    port: 8081,
    open: true,
    proxy: {
      '/api/v1': {
        target: 'i5a507.p.ssafy.io:8080/'
      }
    },
    historyApiFallback: true,
    hot: true
  },
  css: {
    requireModuleExtension: false // import 시에 경로에 .module 포함 안해도 됨.
  },
  transpileDependencies: [
    'element-plus'
  ],
  lintOnSave: false,
}
