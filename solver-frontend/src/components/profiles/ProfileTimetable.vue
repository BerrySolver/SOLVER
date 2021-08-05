<template>
  <div>
    <div class="calendar-title">
      화상시간 캘린더
    </div>
    <div>{{ weekdayTime }}</div>    
  </div>
</template>

<script>
export default {
  name: 'ProfileTimetable',
  props: ['userProfileInfo'],
  methods: {
  },
  computed: {
    weekdayTime() {
      const weekdaytime_str = this.userProfileInfo.weekdayTime
      const split_wdt = weekdaytime_str.split('|')
      return split_wdt
    }
  }
}
</script>

<style>
.calendar-title {
  color: #658DC6;
}
</style>