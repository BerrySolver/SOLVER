<template>
  <div>
    <div class="calendar">
      <span class="calendar-title interval">
        화상시간 캘린더
      </span>
      <span>
        <img src="@/assets/edit-button.png" @click="timeEditRequest" class="calendar-edit-button">
      </span>
    </div>
    <div class="m-top-3">
      <button class="day-button" @click="onClickWeekday()">평일</button>
      <button class="day-button" @click="onClickWeekend()">주말</button>
    </div>
    <br>

    <!-- 수정 X 용 캘린더 -->
    <div v-if="!isCalendarEdit">
      <div v-if="isWeekday">
        <span v-for="(time,index) in timeTable" v-bind:key="time">
          <button :class = "[{'on-time-button': isInWdt(time)}, {'off-time-button': !isInWdt(time)}]">
            {{ time }}
          </button>
          <div v-if="(index+1)%4 == 0"></div>
        </span>
      </div>
      <div v-if="!isWeekday">
        <span v-for="(time,index) in timeTable" v-bind:key="time">
          <button :class = "[{'on-time-button': isInWkt(time)}, {'off-time-button': !isInWkt(time)}]">
            {{ time }}
          </button>
          <div v-if="(index+1)%4 == 0"></div>
        </span>
      </div>      
    </div>

    <!-- 수정용 캘린더 -->
    <div v-if="isCalendarEdit">

      <div v-if="isWeekday">
        <span v-for="(time, index) in timeTable" v-bind:key="time">
          <button
          @click="[editWeekday(time)]"
          :class="[{'selected-time': isSelectedTime(time)}]"
          class="none-selected-time">
            {{ time }}
          </button>
          <div v-if="(index+1)%4 == 0"></div>
        </span>
      </div>

      <div v-if="!isWeekday">
        <span v-for="(time,index) in timeTable" v-bind:key="time">
          <button
          @click="[editWeekday(time)]"
          :class="[{'on-time-button': isInWkt(time)}, {'off-time-button': !isInWkt(time)}, {'foreach-edit-button': selectTime}]">
            {{ time }}
          </button>
          <div v-if="(index+1)%4 == 0"></div>
        </span>
      </div>
      
      <br>
      <button @click="editComplete" class="time-edit-complete">수정 완료</button>      
    
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import API from "@/API.js"
import { mapActions, mapState } from 'vuex'

export default {
  name: 'ProfileTimetable_2',
  props: ['weekdayTime', 'weekendTime'],
  data() {
    return {
      isCalendarEdit: false,
      isWeekday: true,
      isWeekend: false,
      wdtList: [],
      wktList: [],
      sendingWdTime: '',
      sendingWkTime: '',
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
    ...mapActions(['profileSetting']),
    isSelectedTime(time) {
      if (this.splitWeekdayTime.includes(time)) {
        return true
      }
      else {
        return false
      }
    },
    editWeekday(time) {
      console.log(this.splitWeekdayTime)
      if (this.splitWeekdayTime.includes(time)) {
        const time_idx = this.splitWeekdayTime.indexOf(time);
        this.splitWeekdayTime.splice(time_idx, 1);
        this.splitWeekdayTime.sort()
      }
      else {
        this.splitWeekdayTime.sort()
        this.splitWeekdayTime.push(time);
      }
    },
    editWeekend(time) {
      if (this.splitWeekendTime.includes(time)) {
        const time_index = this.splitWeekendTime.indexOf(time);
        this.splitWeekendTime.splice(time_index, 1);
      }
      else {
        this.splitWeekendTime.push(time);
      }
      this.splitWeekendTime.sort()
      console.log(this.splitWeekendTime)
    },
    editComplete() {
      this.splitWeekdayTime.forEach(element => {
        this.sendingWdTime += element + '|'
      });

      this.splitWeekendTime.forEach(element => {
        this.sendingWkTime += element + '|'
      })

      console.log(this.sendingWdTime)
      console.log(this.sendingWkTime)
      axios({
        url: API.URL + API.ROUTES.editProfileCalendar,
        method: "patch",
        headers: { Authorization: "Bearer " + this.accessToken},
        data: {
          weekdayTime: this.sendingWdTime,
          weekendTime: this.sendingWkTime,
        }       
      })
      .then((res) => {
        // sendingTime 리셋 안해주면 예전것까지 다 담아가는 문제 발생
        // profileSetting 하기 전에 리셋 해주어야함
        this.sendingWdTime = ''
        this.sendingWkTime = ''
        this.profileSetting(this.userNickname)
        this.isCalendarEdit = false
      })
      .catch((err) => console.log(err))
    },
    onClickWeekday() {
      this.isWeekday = true
    },
    onClickWeekend() {
      this.isWeekday = false
    },
    timeEditRequest() {
      this.isCalendarEdit = !this.isCalendarEdit
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
    },
  },
  computed: {
    ...mapState({
      userNickname: state => state.auth.userNickname,
      accessToken: state => state.auth.accessToken,
      }),
    splitWeekdayTime(){
      const str_weekday = this.weekdayTime
      const split_weekday = str_weekday.split('|')
      split_weekday.pop()
      return split_weekday
    },
    splitWeekendTime(){
      const str_weekend = this.weekendTime
      const split_weekend = str_weekend.split('|')
      split_weekend.pop()
      return split_weekend      
    },
  },
}
</script>

<style>
.calendar-title {
  color: #658DC6;
}

.calendar{
  display: flex;
  justify-content: center;
  margin: 0px 30px 0px 30px;
}

.calendar-edit-button {
  cursor: pointer;
  width: 20px;
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

.foreach-edit-time {
  background-color: #0F4C81;
  border: transparent;
  color: white;
  font-size: 15px;
  margin-top: 1px;
  margin-bottom: 1px;
  /* opacity: 50%; */
  width: 65px;
}

.none-selected-time {
  background-color: black;
  color: white;
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

.selected-time {
  background-color: blue;
  color: white;
  -webkit-transition: all 0.2s ease;
  transition: all 0.2s ease;
}

.time-edit-complete {
  background-color: transparent;
  border: solid 1px #658DC6;
  width: 240px;
}

.time-edit-complete:hover {
  background-color: #0F4C81;
  color: white;
}
</style>