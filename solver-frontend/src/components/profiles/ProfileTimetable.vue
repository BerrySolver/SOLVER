<template>
  <div>
    <div class="calendar-title">
      화상시간 캘린더
    </div>
    <div class="m-top-3">
      <button class="day-button" @click="[onClickWeekday(), isInWdt()]">평일</button>
      <button class="day-button" @click="onClickWeekend()">주말</button>
    </div>
    <br>
    <div v-if="isWeekday">
      <span v-for="time in timeTable" v-bind:key="time">
        <button :class = "[{'on-time-button': isInWdt(time)}, {'off-time-button': !isInWdt(time)}]">
          {{ time }}
        </button>
      </span>
    </div>
    <div v-if="!isWeekday">
      <span v-for="time in timeTable" v-bind:key="time">
        <button :class = "[{'on-time-button': isInWkt(time)}, {'off-time-button': !isInWkt(time)}]">
          {{ time }}
        </button>
      </span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ProfileTimetable',
  props: ['weekdayTime', 'weekendTime'],
  data() {
    return {
      isWeekday: true,
      isWeekend: false,
      timeTable:[
        "00:00","00:30","01:00","01:30","02:00","02:30","03:00","03:30",
        "04:00","04:30","05:00","05:30","06:00","06:30","07:00","07:30",
        "08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30",
        "12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30",
        "16:00","16:30","17:00","17:30","18:00","18:30","19:00","19:30",
        "20:00","20:30","21:00","21:30","22:00","22:30","23:00","23:30"
      ],
    }
  },
  methods: {
    onClickWeekday() {
      this.isWeekday = true
    },
    onClickWeekend() {
      this.isWeekday = false
    },
    isInWdt(time) {
      if (this.splitWeekdayTime.includes(time)) {
        return true
      } else
      return false
    },
    isInWkt(time) {
      if (this.splitWeekendTime.includes(time)) {
        return true
      } else
      return false
    }
  },
  computed: {
    splitWeekdayTime(){
      const str_weekday = this.weekdayTime
      const split_weekday = str_weekday.split('|')
      return split_weekday
    },
    splitWeekendTime(){
      const str_weekend = this.weekendTime
      const split_weekend = str_weekend.split('|')
      return split_weekend      
    },
  },
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

.off-time-button {
  background-color: transparent;
  border: transparent;
  color: #B5C7D3;
  font-size: 15px;
  margin-top: 1px;
  margin-bottom: 1px;
  opacity: 50%;
  width: 65px;
}

.on-time-button {
  background-color: transparent;
  border: transparent;
  color: #0F4C81;
  font-size: 15px;
  margin-top: 1px;
  margin-bottom: 1px;
  text-decoration: overline underline;
  text-decoration-color: #B5C7D3;
  text-decoration-thickness: 3px;
  width: 65px;
}

</style>