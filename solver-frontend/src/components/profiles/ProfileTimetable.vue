<template>
  <div>
    <div class="calendar-title">
      화상시간 캘린더
    </div>
    <div class="m-top-3">
      <button class="day-button" @click="onClickWeekday()">평일</button>
      <button class="day-button" @click="onClickWeekend()">주말</button>
    </div>
    <br>
    <div v-if="isWeekday">
      {{ weekdayTime }}
    </div>
    <div v-else>
      {{ weekendTime }}
    </div>    
  </div>
</template>

<script>
export default {
  name: 'ProfileTimetable',
  data() {
    return {
      isWeekday: true
    }
  },
  props: ['userProfileInfo'],
  methods: {
    onClickWeekday() {
      this.isWeekday = true
    },
    onClickWeekend() {
      this.isWeekday = false
    }
  },
  computed: {
    weekdayTime() {
      const weekdaytime_str = this.userProfileInfo.weekdayTime
      const split_wdt = weekdaytime_str.split('|')
      return split_wdt
    },
    weekendTime() {
      const weekendtime_str = this.userProfileInfo.weekendTime
      const split_wkt = weekendtime_str.split('|')
      return split_wkt
    }
  }
}
</script>

<style>
.calendar-title {
  color: #658DC6;
}

.day-button {
  background-color: transparent;
  border: solid 1px #658DC6;
  transition: background-color .5s;
  width: 120px;
}

.day-button:hover {
  background-color: #658DC6;
  border-color: #658DC6;
  color: #F1F2F2;
}
.day-button:active {
  background-color: #658DC6;
  border-color: #658DC6;
  color: #F1F2F2;
  box-shadow: none;
}
.day-button:focus, .day-button:active:focus {
  background-color: #658DC6;
  border-color: #658DC6;
  color: #F1F2F2;
  outline: 5px auto #658DC6;
}
</style>