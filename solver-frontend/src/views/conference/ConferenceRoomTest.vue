<template>
  <div id="container">
    <div id="wrapper">
      <div id="join" class="animate join">
        <h1>Join a Room</h1>
        <form accept-charset="UTF-8">
          <p>
            <input type="text" name="name" value="" id="name" placeholder="Username" required />
          </p>
          <p>
            <input type="text" name="room" value="" id="roomName" placeholder="Room" required />
          </p>
          <p class="submit">
            <input @click="clickRegister()" type="submit" name="commit" value="Join!" />
          </p>
        </form>
      </div>
      <div id="room" style="display: none;">
        <h2 id="room-header"></h2>
        <div id="videoList">
          <div id="participants">
            <!-- <div id="video1"></div>
          <div id="video2"></div> -->
          </div>
          <!-- <div id="screens"></div> -->
          <div id="mainVideo"></div>
        </div>
      </div>
      <div class="screen-buttons">
        <div id="start" v-if="checkScreen()" class="screen-start-button" @click="clickSt()">
          <img
            style="width:23px; margin: 10px 0 3px 0;"
            src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iaXNvLTg4NTktMSI/Pg0KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDE5LjAuMCwgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPg0KPHN2ZyB2ZXJzaW9uPSIxLjEiIGlkPSJDYXBhXzEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4Ig0KCSB2aWV3Qm94PSIwIDAgNDgwIDQ4MCIgc3R5bGU9ImVuYWJsZS1iYWNrZ3JvdW5kOm5ldyAwIDAgNDgwIDQ4MDsiIHhtbDpzcGFjZT0icHJlc2VydmUiPg0KPGc+DQoJPGc+DQoJCTxwYXRoIGQ9Ik00ODAsMzY4VjY0SDB2MzA0aDE4NHYzMmgtNTZ2MTZoMjQwdi0xNmgtNzJ2LTMySDQ4MHogTTI4MCw0MDBoLTgwdi0zMmg4MFY0MDB6IE0xNiwzNTJWODBoNDQ4djI3MkgxNnoiLz4NCgk8L2c+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8L3N2Zz4NCg=="
          />
        </div>
        <div id="stop" v-if="isMyScreen" class="screen-stop-button" @click="clickSp()">
          <img
            style="width:23px; margin: 10px 0 3px 0;"
            src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iaXNvLTg4NTktMSI/Pg0KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDE5LjAuMCwgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPg0KPHN2ZyB2ZXJzaW9uPSIxLjEiIGlkPSJDYXBhXzEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4Ig0KCSB2aWV3Qm94PSIwIDAgNDgwIDQ4MCIgc3R5bGU9ImVuYWJsZS1iYWNrZ3JvdW5kOm5ldyAwIDAgNDgwIDQ4MDsiIHhtbDpzcGFjZT0icHJlc2VydmUiPg0KPGc+DQoJPGc+DQoJCTxwYXRoIGQ9Ik00ODAsMzY4VjY0SDB2MzA0aDE4NHYzMmgtNTZ2MTZoMjQwdi0xNmgtNzJ2LTMySDQ4MHogTTI4MCw0MDBoLTgwdi0zMmg4MFY0MDB6IE0xNiwzNTJWODBoNDQ4djI3MkgxNnoiLz4NCgk8L2c+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8L3N2Zz4NCg=="
          />
        </div>
        <div v-if="isRecording" @click="stopAnswerRecord()" class="conference-record-stop-btn">
          <img
            style="width:23px; "
            src="data:image/svg+xml;base64,PHN2ZyBpZD0iQ2FwYV8xIiBlbmFibGUtYmFja2dyb3VuZD0ibmV3IDAgMCA1MTIgNTEyIiBoZWlnaHQ9IjUxMiIgdmlld0JveD0iMCAwIDUxMiA1MTIiIHdpZHRoPSI1MTIiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+PGc+PHBhdGggZD0ibTI2My41IDI0My4wMS05MC01MS45NjFjLTkuOTc3LTUuNzYxLTIyLjUgMS40NDctMjIuNSAxMi45OXYxMDMuOTIzYzAgMTEuNTAzIDEyLjQ4MyAxOC43NzQgMjIuNSAxMi45OWw5MC01MS45NjFjOS45NjQtNS43NTIgMTAuMDE0LTIwLjIgMC0yNS45ODF6bS04Mi41IDM4Ljk3di01MS45Nmw0NSAyNS45OHoiLz48cGF0aCBkPSJtNDkwLjMzOCAxMzcuNTYxLTk5LjMzOCA0OS4yMzh2LTY1Ljc5OWMwLTguMjg0LTYuNzE2LTE1LTE1LTE1aC0zNjFjLTguMjg0IDAtMTUgNi43MTYtMTUgMTV2MjcwYzAgOC4yODQgNi43MTYgMTUgMTUgMTVoMzYxYzguMjg0IDAgMTUtNi43MTYgMTUtMTV2LTY0Ljk3OGw5OS40MjggNDguNDYyYzkuOTQ5IDQuODQ4IDIxLjU3Mi0yLjQwMyAyMS41NzItMTMuNDg0di0yMTBjMC0xMS4wOTMtMTEuNjc5LTE4LjM4Ny0yMS42NjItMTMuNDM5em0tNDYwLjMzOCAyMzguNDM5di0yNDBoMzMxdjI0MHptNDUyLTM4Ljk5OC05MS00NC4zNTR2LTcyLjM2N2w5MS00NS4xMDV6Ii8+PC9nPjwvc3ZnPg=="
          />
        </div>
        <div v-else @click="startAnswerRecord()" class="conference-record-start-btn">
          <img
            style="width:23px; "
            src="data:image/svg+xml;base64,PHN2ZyBpZD0iQ2FwYV8xIiBlbmFibGUtYmFja2dyb3VuZD0ibmV3IDAgMCA1MTIgNTEyIiBoZWlnaHQ9IjUxMiIgdmlld0JveD0iMCAwIDUxMiA1MTIiIHdpZHRoPSI1MTIiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+PGc+PHBhdGggZD0ibTI2My41IDI0My4wMS05MC01MS45NjFjLTkuOTc3LTUuNzYxLTIyLjUgMS40NDctMjIuNSAxMi45OXYxMDMuOTIzYzAgMTEuNTAzIDEyLjQ4MyAxOC43NzQgMjIuNSAxMi45OWw5MC01MS45NjFjOS45NjQtNS43NTIgMTAuMDE0LTIwLjIgMC0yNS45ODF6bS04Mi41IDM4Ljk3di01MS45Nmw0NSAyNS45OHoiLz48cGF0aCBkPSJtNDkwLjMzOCAxMzcuNTYxLTk5LjMzOCA0OS4yMzh2LTY1Ljc5OWMwLTguMjg0LTYuNzE2LTE1LTE1LTE1aC0zNjFjLTguMjg0IDAtMTUgNi43MTYtMTUgMTV2MjcwYzAgOC4yODQgNi43MTYgMTUgMTUgMTVoMzYxYzguMjg0IDAgMTUtNi43MTYgMTUtMTV2LTY0Ljk3OGw5OS40MjggNDguNDYyYzkuOTQ5IDQuODQ4IDIxLjU3Mi0yLjQwMyAyMS41NzItMTMuNDg0di0yMTBjMC0xMS4wOTMtMTEuNjc5LTE4LjM4Ny0yMS42NjItMTMuNDM5em0tNDYwLjMzOCAyMzguNDM5di0yNDBoMzMxdjI0MHptNDUyLTM4Ljk5OC05MS00NC4zNTR2LTcyLjM2N2w5MS00NS4xMDV6Ii8+PC9nPjwvc3ZnPg=="
          />
        </div>
        <div class="conference-finish-btn" @click="clickLeaveRoom()">
          <img
            style="width: 12px;"
            src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iaXNvLTg4NTktMSI/Pg0KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDE5LjAuMCwgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPg0KPHN2ZyB2ZXJzaW9uPSIxLjEiIGlkPSJDYXBhXzEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4Ig0KCSB2aWV3Qm94PSIwIDAgNTEyLjAwMSA1MTIuMDAxIiBzdHlsZT0iZW5hYmxlLWJhY2tncm91bmQ6bmV3IDAgMCA1MTIuMDAxIDUxMi4wMDE7IiB4bWw6c3BhY2U9InByZXNlcnZlIj4NCjxnPg0KCTxnPg0KCQk8cGF0aCBkPSJNMjg0LjI4NiwyNTYuMDAyTDUwNi4xNDMsMzQuMTQ0YzcuODExLTcuODExLDcuODExLTIwLjQ3NSwwLTI4LjI4NWMtNy44MTEtNy44MS0yMC40NzUtNy44MTEtMjguMjg1LDBMMjU2LDIyNy43MTcNCgkJCUwzNC4xNDMsNS44NTljLTcuODExLTcuODExLTIwLjQ3NS03LjgxMS0yOC4yODUsMGMtNy44MSw3LjgxMS03LjgxMSwyMC40NzUsMCwyOC4yODVsMjIxLjg1NywyMjEuODU3TDUuODU4LDQ3Ny44NTkNCgkJCWMtNy44MTEsNy44MTEtNy44MTEsMjAuNDc1LDAsMjguMjg1YzMuOTA1LDMuOTA1LDkuMDI0LDUuODU3LDE0LjE0Myw1Ljg1N2M1LjExOSwwLDEwLjIzNy0xLjk1MiwxNC4xNDMtNS44NTdMMjU2LDI4NC4yODcNCgkJCWwyMjEuODU3LDIyMS44NTdjMy45MDUsMy45MDUsOS4wMjQsNS44NTcsMTQuMTQzLDUuODU3czEwLjIzNy0xLjk1MiwxNC4xNDMtNS44NTdjNy44MTEtNy44MTEsNy44MTEtMjAuNDc1LDAtMjguMjg1DQoJCQlMMjg0LjI4NiwyNTYuMDAyeiIvPg0KCTwvZz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjwvc3ZnPg0K"
          />
        </div>
      </div>
      <div>
        <button @click="pushToConference()" value="Leave room">라우터 테스트</button>
      </div>
      <div>
        <button @click="getReservationList()" value="Leave room">화상 예역 목록 테스트</button>
      </div>
    </div>
    <button id="stopBtn" @click="clickStop()">Stop</button>
  </div>
</template>

//
<script>
// import kurentoUtils from "kurento-utils";
// import axios from "axios";
// import API from "@/API.js";
// import { mapState, mapGetters } from "vuex";
// import ConferenceEvaluateModal from "./ConferenceEvaluateModal.vue";

// function clStart() {
//   console.log(isMySharing);
//   startSharing();
// }
// function clStop(e) {
//   console.log(e);
//   stopSharing();
// }

// async function startSharing() {
//   if (!name.startsWith("scree&")) name = "scree&" + name;

//   try {
//     screenName = name;
//     var message = {
//       id: "startShare",
//       name: name,
//       room: room,
//     };
//     sendMessage(message);
//   } catch (error) {
//     console.log(error);
//   }
// }

// function stopSharing() {
//   sendMessage({
//     id: "stopShare",
//     name: myName,
//   });

//   name = myName;
// }

// // var ws = new WebSocket("wss://localhost:8443/groupcall");
// // var ws = new WebSocket("wss://i5a507.p.ssafy.io:8443/groupcall");
// var participants = {};
// var name;
// var room;
// var screenName;
// var myName;
// var isSharing = false;
// var isMySharing = false;
// var participantsCount = 1;

// ws.onmessage = function(message) {
//   var parsedMessage = JSON.parse(message.data);
//   console.info("Received message: " + message.data);

//   switch (parsedMessage.id) {
//     case "existingParticipants":
//       onExistingParticipants(parsedMessage);
//       break;
//     case "newParticipantArrived":
//       onNewParticipant(parsedMessage);
//       break;
//     case "participantLeft":
//       onParticipantLeft(parsedMessage);
//       break;
//     case "receiveVideoAnswer":
//       receiveVideoResponse(parsedMessage);
//       break;
//     case "receiveScreenVideoAnswer":
//       receiveScreenVideoResponse(parsedMessage);
//       break;
//     case "iceCandidate":
//       participants[parsedMessage.name].rtcPeer.addIceCandidate(parsedMessage.candidate, function(
//         error
//       ) {
//         if (error) {
//           console.error("Error adding candidate: " + error);
//           return;
//         }
//       });
//       break;
//     case "myScreenSharingStart":
//       onExistingParticipants(parsedMessage);
//       break;
//     case "screenSharingStart":
//       onNewParticipant(parsedMessage);
//       break;
//     default:
//       console.error("Unrecognized message", parsedMessage);
//   }
// };

// function register() {
//   name = document.getElementById("name").value;
//   room = document.getElementById("roomName").value;

//   console.log(name + " " + room);

//   //나중에 지울 부분
//   myName = name;

//   document.getElementById("room-header").innerText = "ROOM " + room;
//   document.getElementById("join").style.display = "none";
//   document.getElementById("room").style.display = "block";

//   var message = {
//     id: "joinRoom",
//     name: name,
//     room: room,
//   };
//   sendMessage(message);
// }

// function onNewParticipant(request) {
//   receiveVideo(request.name);
// }

// function receiveVideoResponse(result) {
//   console.log(result.name);
//   participants[result.name].rtcPeer.processAnswer(result.sdpAnswer, function(error) {
//     console.log(result);
//     if (error) {
//       return console.error(error);
//     }
//   });
// }

// function receiveScreenVideoResponse(result) {
//   console.log(participants[room]);
//   participants[room].rtcPeer.processAnswer(result.sdpAnswer, function(error) {
//     console.log(result);
//     if (error) {
//       return console.error(error);
//     }
//   });
// }

// function onExistingParticipants(msg) {
//   var constraints = {
//     audio: true,
//     video: {
//       mandatory: {
//         maxWidth: 1920,
//         maxHeight: 1080,
//         maxFrameRate: 60,
//       },
//     },
//   };
//   console.log(msg);
//   console.log(name);
//   if (participants[name] != null && msg.id != "myScreenSharingStart") {
//     console.log(participants[name]);
//     return;
//   }

//   console.log(participants[name]);
//   console.log(name + " registered in room " + room);

//   var options = {};

//   if (name.startsWith("scree&")) {
//     isSharing = true;
//     var participant = new Participant(name);
//     console.log(participant);
//     participants[name] = participant;
//     var video = participant.getVideoElement();
//     var audio = "";
//     navigator.mediaDevices.getUserMedia({ audio: true }).then((stream) => {
//       audio = new MediaRecorder(stream);
//     });
//     console.log(audio);
//     options = {
//       localVideo: video,
//       mediaConstraints: constraints,
//       audioStreams: audio,
//       onicecandidate: participant.onIceCandidate.bind(participant),
//       sendSource: "screen",
//     };
//   } else {
//     var participant = new Participant(name);
//     console.log(participant);
//     participants[name] = participant;
//     var video = participant.getVideoElement();
//     options = {
//       localVideo: video,
//       mediaConstraints: {
//         audio: true,
//         video: {
//           mandatory: {
//             maxWidth: 1920,
//             maxHeight: 1080,
//             maxFrameRate: 60,
//           },
//         },
//       },
//       onicecandidate: participant.onIceCandidate.bind(participant),
//       // sendSource: "screen",
//     };
//   }
//   console.log("sendOnly");
//   stop(100000000);
//   participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerSendonly(options, function(error) {
//     if (error) {
//       console.log(error);
//       return console.error(error);
//     }
//     this.generateOffer(participant.offerToReceiveVideo.bind(participant));
//   });

//   console.log(msg.data);

//   msg.data.forEach(receiveVideo);
// }

// // function leaveRoom() {
// //   console.log("leaveRoom");
// //   sendMessage({
// //     id: "leaveRoom",
// //   });

// //   for (var key in participants) {
// //     participants[key].dispose();
// //   }

// //   document.getElementById("join").style.display = "block";
// //   document.getElementById("room").style.display = "none";
// // }

// function receiveVideo(sender) {
//   console.log("sender: " + sender);
//   var participant = new Participant(sender);
//   console.log(participant);
//   participants[sender] = participant;
//   var video = participant.getVideoElement();

//   console.log(sender.includes(myName));

//   if (sender.startsWith("scree&") && sender.includes(myName)) {
//     var constraints = {
//       audio: true,
//       video: {
//         mandatory: {
//           maxWidth: 1920,
//           maxHeight: 1080,
//           maxFrameRate: 60,
//         },
//       },
//     };

//     var options = {
//       localVideo: video,
//       mediaConstraints: constraints,
//       onicecandidate: participant.onIceCandidate.bind(participant),
//       sendSource: "screen",
//     };

//     participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerSendonly(options, function(error) {
//       if (error) {
//         console.log(error);
//         return console.error(error);
//       }
//       this.generateOffer(participant.offerToReceiveVideo.bind(participant));
//     });

//     return;
//   }

//   var options = {
//     remoteVideo: video,
//     onicecandidate: participant.onIceCandidate.bind(participant),
//   };

//   participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(options, function(error) {
//     if (error) {
//       return console.error(error);
//     }
//     this.generateOffer(participant.offerToReceiveVideo.bind(participant));
//   });
// }

// function onParticipantLeft(request) {
//   console.log("Participant " + request.name + " left");
//   if (request.name.startsWith("scree&")) isSharing = false;
//   var participant = participants[request.name];
//   participant.dispose();
//   delete participants[request.name];
// }

// function sendMessage(message) {
//   var jsonMessage = JSON.stringify(message);
//   console.log("Sending message: " + jsonMessage);
//   ws.send(jsonMessage);
// }

// const PARTICIPANT_MAIN_CLASS = "participant main";
// const PARTICIPANT_CLASS = "participant";

// function Participant(name) {
//   this.name = name;
//   console.log("this.name : " + this.name);

//   if (name.startsWith("scree&")) {
//     isSharing = true;
//     this.name = name;

//     var container = document.createElement("div");
//     container.className = PARTICIPANT_CLASS;
//     container.id = name;

//     var video = document.createElement("video");
//     video.id = "video-" + name;
//     video.style.width = "300px";

//     container.appendChild(video);
//     container.onclick = switchContainerClass;

//     document.getElementById("participants").appendChild(container);

//     video.autoplay = true;
//     video.controls = false;
//   } else {
//     this.name = name;

//     var container = document.createElement("div");
//     container.className = PARTICIPANT_CLASS;
//     container.id = name;

//     var video = document.createElement("video");
//     video.id = "video-" + name;
//     video.style.width = "300px";

//     container.appendChild(video);
//     container.onclick = switchContainerClass;
//     document.getElementById("participants").appendChild(container);

//     participantsCount++;
//     video.autoplay = true;
//     video.controls = false;
//   }

//   this.getElement = function() {
//     return container;
//   };

//   this.getVideoElement = function() {
//     return video;
//   };

//   function switchContainerClass() {
//     if (container.className === PARTICIPANT_CLASS) {
//       const mainDiv = document.getElementById("mainVideo");
//       const childDiv = mainDiv.firstChild;
//       if (childDiv != null) {
//         const participantsDiv = document.getElementById("participants");
//         childDiv.className = PARTICIPANT_CLASS;
//         const childVideo = childDiv.firstChild;
//         childVideo.style.width = "300px";
//         participantsDiv.appendChild(childDiv);
//       }
//       container.className = PARTICIPANT_MAIN_CLASS;
//       mainDiv.appendChild(container);
//       video.style.width = "1600px";
//     }
//   }

//   this.offerToReceiveVideo = function(error, offerSdp, wp) {
//     console.log(wp);
//     if (error) return console.error("sdp offer error");
//     console.log("Invoking SDP offer callback function");
//     var msg = { id: "receiveVideoFrom", sender: name, sdpOffer: offerSdp, room: room };
//     sendMessage(msg);
//   };

//   this.onIceCandidate = function(candidate, wp) {
//     console.log(wp);
//     var message = {
//       id: "onIceCandidate",
//       candidate: candidate,
//       name: name,
//     };
//     sendMessage(message);
//   };

//   Object.defineProperty(this, "rtcPeer", { writable: true });

//   this.dispose = function() {
//     console.log("Disposing participant " + this.name);
//     this.rtcPeer.dispose();
//     container.parentNode.removeChild(container);
//   };
// }

// function test() {
//   console.log(data.isSharing);
// }

// export default {
//   data: function() {
//     return {
//       searchInputData: "",
//       isMyScreen: false,
//       recoder: null,
//       blobs: [],
//       blob: null,
//       desktopStream: null,
//       isRecording: false,
//       isAnswerUser: false,
//     };
//   },
//   computed: {
//     ...mapState({
//       accessToken: (state) => state.auth.accessToken,
//       userNickname: (state) => state.auth.userNickname,
//     }),
//     ...mapGetters(["getUserNickname"]),
//   },
//   props: ["questionId"],
//   methods: {
//     // insertConferenceLog() {
//     //   axios({
//     //     //conference id 값은 테스트용
//     //     url: API.URL + API.ROUTES.conferenceLog + `/12345678`,
//     //     method: "post",
//     //     data: {
//     //       type: "030",
//     //     },
//     //     headers: { Authorization: "Bearer " + this.accessToken },
//     //   })
//     //     .then((res) => {
//     //       console.log(res);
//     //       this.goQuestionDetail(res.data.questionId);
//     //     })
//     //     .catch((e) => {
//     //       console.log(e);
//     //     });
//     // },
//     entranceConferenceLog() {
//       axios({
//         //conference id 값은 테스트용
//         url: API.URL + API.ROUTES.conferenceLog,
//         method: "post",
//         data: {
//           type: "030",
//         },
//         headers: { Authorization: "Bearer " + this.accessToken },
//       })
//         .then((res) => {
//           console.log(res);
//         })
//         .catch((e) => {
//           console.log(e);
//         });
//     },
//     exitConferenceLog() {
//       axios({
//         //conference id 값은 테스트용
//         url: API.URL + API.ROUTES.conferenceLog,
//         method: "post",
//         data: {
//           type: "031",
//         },
//         headers: { Authorization: "Bearer " + this.accessToken },
//       })
//         .then((res) => {
//           console.log(res);
//         })
//         .catch((e) => {
//           console.log(e);
//         });
//     },
//     clickRegister() {
//       register();
//     },
//     clickLeaveRoom() {
//       if (this.isRecording) this.stopAnswerRecord();
//       // leaveRoom();
//       this.conferenceEvaluate();
//       if (this.isAnswerUser) this.exitConferenceLog();
//     },
//     clickSt() {
//       if (isSharing) return;
//       // if (participantsCount < 2) return;
//       this.isMyScreen = true;
//       isMySharing = true;
//       isSharing = true;
//       clStart();
//     },
//     clickSp() {
//       if (!isMySharing) return;
//       this.isMyScreen = false;
//       isMySharing = false;
//       isSharing = false;
//       clStop();
//     },
//     checkScreen() {
//       if (isSharing && !this.isMyScreen) return false;

//       if (this.isMyScreen) return false;

//       if (!isSharing) return true;
//     },
//     isSharing() {
//       return isSharing;
//     },
//     isMySharing() {
//       return isMySharing;
//     },
//     conferenceEvaluate() {
//       this.$modal.show(
//         ConferenceEvaluateModal,
//         {
//           questionId: "test",
//           modal: this.$modal,
//         },
//         {
//           name: "dynamic-modal",
//           width: "600px",
//           height: "500px",
//           draggable: false,
//           clickToClose: false,
//         }
//       );
//     },
//     async startAnswerRecord() {
//       this.isRecording = true;

//       const voiceStream = await navigator.mediaDevices.getUserMedia({ video: false, audio: true }); // 오디오스트림 생성

//       const desktopStream = await navigator.mediaDevices.getDisplayMedia({
//         video: true,
//         audio: true,
//       });

//       const tracks = [
//         ...desktopStream.getVideoTracks(),
//         ...this.mergeAudioStreams(desktopStream, voiceStream),
//       ];

//       const stream = new MediaStream(tracks);

//       const recoder = new MediaRecorder(stream, {
//         mimeType: "",
//       });
//       this.recoder = recoder; // mediathis.recoderorder객체 생성
//       this.recoder.ondataavailable = (e) => this.blobs.push(e.data);
//       this.recoder.onstop = async () => {
//         this.blob = new Blob(this.blobs, { type: "video/mp4" });
//         console.log(this.blob);
//         this.recordVideo();
//       };
//       console.log(this.recoder);
//       this.recoder.start(); // 녹화 시작
//     },
//     stopAnswerRecord() {
//       this.isRecording = false;
//       this.recoder.stop();
//     },
//     recordVideo() {
//       var reader = new window.FileReader();
//       var base64data;
//       const $this = this;
//       console.log(this.blob);
//       reader.readAsDataURL(this.blob);
//       console.log(this.accessToken);
//       reader.onloadend = function() {
//         base64data = reader.result;
//         base64data = base64data.split(",")[1];
//         $this.axiosVideo(base64data);
//       };
//     },
//     axiosVideo(base64data) {
//       axios({
//         url: API.URL + `conferences/record/aOa1yOS9cGdSX`,
//         method: "post",
//         headers: {
//           Authorization: "Bearer " + this.accessToken,
//         },
//         data: {
//           videoFile: base64data,
//         },
//       })
//         .then(() => {})
//         .catch((err) => {
//           console.log(err);
//         });
//     },
//     mergeAudioStreams(desktopStream, voiceStream) {
//       // 비디오, 오디오스트림 연결
//       const context = new AudioContext();
//       const destination = context.createMediaStreamDestination();
//       let hasDesktop = false;
//       let hasVoice = false;
//       if (desktopStream && desktopStream.getAudioTracks().length > 0) {
//         const source1 = context.createMediaStreamSource(desktopStream);
//         const desktopGain = context.createGain();
//         desktopGain.gain.value = 0.7;
//         source1.connect(desktopGain).connect(destination);
//         hasDesktop = true;
//       }

//       if (voiceStream && voiceStream.getAudioTracks().length > 0) {
//         const source2 = context.createMediaStreamSource(voiceStream);
//         const voiceGain = context.createGain();
//         voiceGain.gain.value = 0.7;
//         source2.connect(voiceGain).connect(destination);
//         hasVoice = true;
//       }

//       return hasDesktop || hasVoice ? destination.stream.getAudioTracks() : [];
//     },
//     pushToConference() {
//       axios({
//         //conference id 값은 테스트용
//         url: API.URL + `questions/5deK0GDhDpdZG/info`,
//         method: "get",
//         headers: { Authorization: "Bearer " + this.accessToken },
//       })
//         .then((res) => {
//           console.log(res);
//           const questionUserNickname = res.data.nickname;
//           // console.log(questionUserId);
//           this.$router.push({
//             name: "Conference",
//             params: { questionId: "5deK0GDhDpdZG", questionUserNickname: questionUserNickname },
//           });
//         })
//         .catch((e) => {
//           console.log(e);
//         });
//     },
//     getReservationList() {
//       axios({
//         //conference id 값은 테스트용
//         url: API.URL + "conferences/reservations",
//         method: "get",
//         headers: { Authorization: "Bearer " + this.accessToken },
//       })
//         .then((res) => {
//           console.log(res);
//         })
//         .catch((e) => {
//           console.log(e);
//         });
//     },
//   },
//   mounted() {
//     myName = this.getUserNickname;
//     name = this.getUserNickname;
//     // room = this.questionId;
//     room = "testRoom";

//     // axios({
//     //   //conference id 값은 테스트용
//     //   url: API.URL + "questions/1CYdWWZb3VtcY/info",
//     //   method: "post",
//     //   data: {
//     //     type: "030",
//     //   },
//     //   headers: { Authorization: "Bearer " + this.accessToken },
//     // })
//     //   .then((res) => {
//     //     console.log(res);
//     //     const questionUserId = res.data.userId;
//     //     if (questionUserId != this.getUserNickname()) {
//     //       this.isAnswerUser = true;
//     //       this.entranceConferenceLog();
//     //     }
//     //   })
//     //   .catch((e) => {
//     //     console.log(e);
//     //   });
//   },
//   beforeDestroy() {
//     // if(this.isMySharing)
//   },
// };
//
</script>

<style scoped>
.container {
  margin: 50px auto;
  width: 640px;
}

:-moz-placeholder {
  color: #c9c9c9 !important;
  font-size: 13px;
}

::-webkit-input-placeholder {
  color: #ccc;
  font-size: 13px;
}

input {
  font-family: "Lucida Grande", Tahoma, Verdana, sans-serif;
  font-size: 14px;
}

input[type="text"],
input[type="password"] {
  margin: 5px;
  padding: 0 10px;
  width: 200px;
  height: 34px;
  color: #404040;
  background: white;
  border: 1px solid;
  border-color: #c4c4c4 #d1d1d1 #d4d4d4;
  border-radius: 2px;
  outline: 5px solid #eff4f7;
  -moz-outline-radius: 3px;
  -webkit-box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.12);
  box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.12);
}

input[type="text"]:focus,
input[type="password"]:focus {
  border-color: #7dc9e2;
  outline-color: #dceefc;
  outline-offset: 0;
}

input[type="button"],
input[type="submit"] {
  padding: 0 18px;
  height: 29px;
  font-size: 12px;
  font-weight: bold;
  color: #527881;
  text-shadow: 0 1px #e3f1f1;
  background: #cde5ef;
  border: 1px solid;
  border-color: #b4ccce #b3c0c8 #9eb9c2;
  border-radius: 16px;
  outline: 0;
  -webkit-box-sizing: content-box;
  -moz-box-sizing: content-box;
  box-sizing: content-box;
  background-image: -webkit-linear-gradient(top, #edf5f8, #cde5ef);
  background-image: -moz-linear-gradient(top, #edf5f8, #cde5ef);
  background-image: -o-linear-gradient(top, #edf5f8, #cde5ef);
  background-image: linear-gradient(to bottom, #edf5f8, #cde5ef);
  -webkit-box-shadow: inset 0 1px white, 0 1px 2px rgba(0, 0, 0, 0.15);
  box-shadow: inset 0 1px white, 0 1px 2px rgba(0, 0, 0, 0.15);
}

#room {
  width: 100%;
  text-align: center;
}

#screens {
  margin-top: 20px;
}

#participants {
  min-width: 300px;
  margin-top: 50px;
  /* display: flex; */
  justify-content: space-between;
}

.screen-buttons {
  display: flex;
  justify-content: center;
  margin-top: 20px;
  padding-bottom: 20px;
  align-items: center;
}

.conference-finish-btn {
  background-color: white;
  border: 1px solid #e0e0e0;
  border-radius: 70%;
  float: left;
  height: 45px;
  margin-right: 7px;
  width: 45px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.conference-finish-btn:hover {
  background-color: #84898c;
  cursor: pointer;
  filter: brightness(115%);
}

.screen-start-button {
  background-color: white;
  border: 1px solid #e0e0e0;
  border-radius: 70%;
  float: left;
  height: 45px;
  margin-right: 7px;
  width: 45px;
}

.screen-stop-button {
  background-color: #658dc6;
  filter: brightness(105%);
  border: 1px solid #e0e0e0;
  border-radius: 70%;
  float: left;
  height: 45px;
  margin-right: 7px;
  width: 45px;
}

.conference-record-start-btn {
  background-color: white;
  border: 1px solid #e0e0e0;
  border-radius: 70%;
  float: left;
  height: 45px;
  margin-right: 7px;
  width: 45px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.conference-record-start-btn:hover {
  background-color: #658dc6;
  cursor: pointer;
  filter: brightness(115%);
}

.conference-record-stop-btn {
  background-color: #658dc6;
  filter: brightness(105%);
  border: 1px solid #e0e0e0;
  border-radius: 70%;
  float: left;
  height: 45px;
  margin-right: 7px;
  width: 45px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.conference-record-stop-btn:hover {
  background-color: #658dc6;
  cursor: pointer;
  filter: brightness(115%);
}

.screen-start-button:hover {
  background-color: #658dc6;
  cursor: pointer;
  filter: brightness(115%);
}

.screen-stop-button:hover {
  cursor: pointer;
  filter: brightness(115%);
}

#videoList {
  display: flex;
  align-items: flex-start;
}
</style>

<style scoped>
#participants {
  overflow: hide;
}

#router {
  background: linear-gradient(135deg, #658dc6, #b5c7d3);
}

#wrapper {
  min-height: 1900px;
}
</style>
