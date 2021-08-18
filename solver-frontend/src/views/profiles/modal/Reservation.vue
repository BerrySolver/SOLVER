<template>
  <div class="point-log-modal solver-font">
    <h3 class="point-log-title">화상시간 예약 확인</h3>
    <hr />
    <div class="point-log-box">
      <div v-for="(point, index) in reservationList" :key="index" class="point-detail">
        <div class="title-wrapper">
          <div class="reservation-title">
            <span class="reservation-question-title">"{{ titleSlice(point.questionTitle) }}</span
            >"
          </div>

          <span class="reservation-time-title">예약시간</span>
        </div>

        <div class="reservation-explanation">
          <span>질문에 대한 화상 회의가 예약되어 있습니다</span>
          <span class="reservation-dateTime">{{ point.startDt }}</span>
        </div>
      </div>
    </div>
    <button class="btn point-log-button" @click="$emit('close')">확인완료</button>
  </div>
</template>

<script>
import axios from "axios";
import API from "@/API.js";
import { mapState } from "vuex";

export default {
  data: function() {
    return {
      nickname: "",
      reservationList: [],
    };
  },
  props: [],
  methods: {
    titleSlice(str) {
      if (str.length > 25) return str.substring(0, 25) + "...";

      return str;
    },
  },
  computed: {
    ...mapState({
      accessToken: (state) => state.auth.accessToken,
    }),
  },
  created() {
    this.nickname = this.$store.getters.getUserNickname;
    axios({
      url: API.URL + `conferences/reservations`,
      method: "get",
      headers: { Authorization: "Bearer " + this.accessToken },
    })
      .then((res) => {
        this.reservationList = res.data.reservationList;

        this.reservationList.forEach((p) => {
          var date = new Date(p.startDt);

          let year = date.getFullYear();
          let month = date.getMonth() + 1;
          month = month >= 10 ? month : "0" + month;
          let day = date.getDate();
          day = day >= 10 ? day : "0" + day;
          let hour = date.getHours();
          hour = hour >= 10 ? hour : "0" + hour;
          let min = date.getMinutes();
          if (min == 0) min = "00";
          let result = year + "-" + month + "-" + day + " " + hour + ":" + min;

          p.startDt = result;
        });
      })
      .catch((err) => {
        console.log(err);
      });
  },
};
</script>

<style>
.point-detail {
  border-bottom: 1px solid #e0e0e0;
  padding: 20px 0px;
}

.point-detail-text {
  display: inline-block;
  width: 200px;
  border: 1px solid #0f4c81;
}

.point-log-box {
  /* background: #0F4C81; */
  height: 500px;
  overflow: auto;
}

.point-log-box::-webkit-scrollbar {
  width: 10px; /*스크롤바의 너비*/
}

.point-log-box::-webkit-scrollbar-thumb {
  background-color: #b5c7d3; /*스크롤바의 색상*/
  /* background-clip: padding-box; */
  border: 4px solid transparent;
  border-radius: 50px;
  /* border-top-right-radius: 50px;
      border-bottom-left-radius: 50px; */
}

.point-log-box::-webkit-scrollbar-track {
  background-color: rgb(245, 245, 245);
  border-radius: 50px;
}

.point-log-button {
  margin-top: 25px;
  border: 1px solid #0f4c81;
  color: #0f4c81;
  width: 500px;
  padding: 10px 0px;
}

.point-log-button:hover {
  margin-top: 25px;
  background: #658dc665;
  color: white;
  border: 1px solid white;
  width: 500px;
  padding: 10px 0px;
}

.point-log-modal {
  padding: 40px 50px;
}

.point-log-title {
  color: #0f4c81;
}

.point-text {
  display: inline-block;
  width: 290px;
}

.point-time {
  font-size: 10px;
}

.point-img {
  width: 80px;
}
</style>

<style scoped>
hr {
  margin: 0px !important;
}

.reservationContent div {
  display: inline;
}

.reservationContent {
  display: inline;
}

.reservationTimeDiv div {
  display: inline;
}

.reservationTimeDiv {
  display: inline-block;
  width: 200px;
  height: 42px;
}

.reservation-title {
  color: #0f4c81;
  font-size: 15px;
  font-weight: 600;
  /* padding: 15px 15px 0px 17px; */
  width: 300px;
  white-space: nowrap;
  align-items: center;
  display: inline-block;
}

.reservation-time-title {
  margin-left: 100px;
  color: #0f4c81;
  font-size: 15px;
  font-weight: 600;
}

.reservation-dateTime {
  color: black;
  font-size: 15px;
  padding-top: 3px;
}

.reservation-explanation {
  color: black;
  display: flex;
  font-size: 14px;
  justify-content: space-between;
  align-items: center;
  padding: 0px 5px 0px 17px;
  transform: skew(-0.1deg);
}

.title-wrapper {
  display: flex;
  align-items: center;
}
</style>
