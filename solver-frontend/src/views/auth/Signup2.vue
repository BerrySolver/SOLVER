<template>
  <div>
    <div>
      <ul>
        <h3>1. 관심있는 분야를 골라주세요.</h3>
        <li v-for="(Category, idx) in categoryList" :key="idx">
        </li>
      </ul>
    </div>
    <div>
      <ul>
        <h3>2. 가능하신 요일과 시간을 골라주세요.</h3> 
      </ul>
    </div>
  </div>
</template>

<script>
import {mapState} from 'vuex'

export default {
    name: 'Signup2',
    data() {
      return {
        signupInfo: {
          Category: [],
          day: [],
          time: [],
        },
      }
    },
    computed: {
      ...mapState({
        categoryList: state => state.auth.CategoryList
      })
    }
}
</script>

<style>

</style>