<template>
  <div>
    <div class="calendar">
      <span class="calendar-title interval">
        화상시간 캘린더
      </span>
      <span v-if="!isCalendarEdit">
        <img src="@/assets/edit-button.png" @click="timeEditRequest" class="calendar-edit-button">
      </span>
      <span v-if="isCalendarEdit" class="goback" @click="timeEditRequest">
        ←
      </span>
    </div>
    <div class="m-top-3">
      <button class="first-setting day-button" @click="checkWeekday()">평일</button>
      <button class="day-button" @click="checkWeekend()">주말</button>
    </div>
    <br>

    <!-- 수정 X 용 캘린더 -->
    <div v-if="!isCalendarEdit">
      <div v-if="isWeekday">
        <span v-for="(time,index) in timeTable" v-bind:key="time">
          <button v-bind:class="{'selected-button': isInWdt(time)}" class="none-selected-button">
            {{ time }}
          </button>
          <div v-if="(index+1)%4 == 0"></div>
        </span>
      </div>
      <div v-if="!isWeekday">
        <span v-for="(time,index) in timeTable" v-bind:key="time">
          <button v-bind:class="{'selected-button': isInWkt(time)}" class="none-selected-button">
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
          <button v-bind:class="{'none-selected-button': !isEditedWDTime(time)}" class="edit-selected-button" @click="selectWdt(time)">
            {{ time }}
          </button>
          <div v-if="(index+1)%4 == 0"></div>
        </span>
      </div>

      <div v-if="!isWeekday">
        <span v-for="(time,index) in timeTable" v-bind:key="time">
          <button v-bind:class="{'none-selected-button': !isEditedWKTime(time)}" class="edit-selected-button" @click="selectWkt(time)">
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
  name: 'ProfileTimetabl',
  props: ['weekdayTime', 'weekendTime'],
  data() {
    return {
      isCalendarEdit: false,
      isWeekday: true,
      editedWeekday: [],
      editedWeekend: [],
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
    checkWeekday () {
      this.isWeekday = true
    },
    // 주중, 주말 CLICK해 SWITCH
    checkWeekend () {
      this.isWeekday = false
    },

    // 수정용: EDIT TIME을 전부 담은 후 AXIOS로 서버에 요청 보낼 때
    editComplete() {
      var str_weekday = "";
      var str_weekend = "";

      this.editedWeekday.forEach(element => {
        str_weekday += element + '|'
      });

      this.editedWeekend.forEach(element => {
        str_weekend += element + '|'
      })
      console.log(str_weekday)
      axios({
        url: API.URL + API.ROUTES.editProfileCalendar,
        method: "patch",
        headers: { Authorization: "Bearer " + this.accessToken},
        data: {
          'weekdayTime': str_weekday,
          'weekendTime': str_weekend,
        }       
      })
      .then((res) => {
        this.profileSetting(this.userNickname)
        this.isCalendarEdit = false
      })
      .catch((err) => console.log(err))
    },

    // 수정용 : 현재 수정한 TIME 데이터에 속하는 TIME인지 CHECK
    isEditedWDTime(time) {
      if (this.editedWeekday.includes(time)){
        return true
      } else {
        return false
      }
    },
    isEditedWKTime(time) {
      if (this.editedWeekend.includes(time)){
        return true
      } else {
        return false
      }      
    },

    // 수정X용 : 기존에 PROP으로 받아온 TIME데이터에 속하는 TIME인지 CHECK
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

    // 수정용: TIME을 클릭할 때마다 EDIT할 TIME이 담긴 LIST를 수정
    selectWdt(time) {
      if(this.editedWeekday.includes(time)) {
        const wdt_idx = this.editedWeekday.indexOf(time);
        this.editedWeekday.splice(wdt_idx, 1);
      } else {
        this.editedWeekday.push(time)
        console.log(this.editedWeekday)
      }
    },
    selectWkt(time) {
      if(this.editedWeekend.includes(time)) {
        const wdt_idx = this.editedWeekend.indexOf(time);
        this.editedWeekend.splice(wdt_idx, 1);
      } else {
        this.editedWeekend.push(time)
        console.log(this.editedWeekend)
      }
    },

    // 캘린더 수정여부 CLICK
    timeEditRequest() {
      this.isCalendarEdit = !this.isCalendarEdit
    },
  },
  computed: {
  ...mapState({
    userNickname: state => state.auth.userNickname,
    accessToken: state => state.auth.accessToken,
    }),

    // string으로 받아온 PROP 데이터를 LIST로 변환해 CHECK용으로 사용
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

<style scoped>
.calendar-edit-button {
  cursor: pointer;
  width: 20px;
}

.day-button {
  background-color: transparent;
  border: solid 1px #658DC6;
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
}
.day-button:focus, .day-button:active:focus {
  background-color: #658DC6;
  border-color: #658DC6;
  color: #F1F2F2;
}

.edit-selected-button {
  background-color: #0F4C81;
  border: transparent;
  color: white;
  font-size: 16px;
  margin-top: 1px;
  margin-bottom: 1px;
  width: 65px;
}

.goback {
  border: solid 1px #658DC6;
  padding: 0px 2px 0px 2px;
  cursor: pointer;
}

.goback:hover {
  background-color: #658DC6;
  color: white;
}

.none-selected-button {
  background-color: transparent;
  border: transparent;
  color: #84898C;
  font-size: 16px;
  margin-top: 1px;
  margin-bottom: 1px;
  width: 65px;
}

.selected-button {
  background-color: #658dc63d;
  border: transparent;
  color: #0F4C81;
  font-size: 16px;
  margin-top: 1px;
  margin-bottom: 1px;
  width: 65px;  
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