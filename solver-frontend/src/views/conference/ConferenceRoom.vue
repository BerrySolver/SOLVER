<template>
  <div id="container">
    <div id="wrapper">
      <div id="room">
        <div style="dislay: flex; align-items: flex-start;">
          <div id="room-header" style="font-size: 28px;">{{ questionUserNickname }}님의 질문방</div>
        </div>
        <div class="screen-buttons">
          <div id="help" class="help-button" @click="clickHelp()">
            <img
              style="width:23px; margin: 10px 0 3px 0;"
              src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iaXNvLTg4NTktMSI/Pg0KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDE2LjAuMCwgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPg0KPCFET0NUWVBFIHN2ZyBQVUJMSUMgIi0vL1czQy8vRFREIFNWRyAxLjEvL0VOIiAiaHR0cDovL3d3dy53My5vcmcvR3JhcGhpY3MvU1ZHLzEuMS9EVEQvc3ZnMTEuZHRkIj4NCjxzdmcgdmVyc2lvbj0iMS4xIiBpZD0iQ2FwYV8xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB4PSIwcHgiIHk9IjBweCINCgkgd2lkdGg9Ijk3My4xcHgiIGhlaWdodD0iOTczLjFweCIgdmlld0JveD0iMCAwIDk3My4xIDk3My4xIiBzdHlsZT0iZW5hYmxlLWJhY2tncm91bmQ6bmV3IDAgMCA5NzMuMSA5NzMuMTsiIHhtbDpzcGFjZT0icHJlc2VydmUiDQoJPg0KPGc+DQoJPHBhdGggZD0iTTUwMi4yOSw3ODguMTk5aC00N2MtMzMuMSwwLTYwLDI2LjktNjAsNjB2NjQuOWMwLDMzLjEsMjYuOSw2MCw2MCw2MGg0N2MzMy4xMDEsMCw2MC0yNi45LDYwLTYwdi02NC45DQoJCUM1NjIuMjksODE1LDUzNS4zOTEsNzg4LjE5OSw1MDIuMjksNzg4LjE5OXoiLz4NCgk8cGF0aCBkPSJNMTcwLjg5LDI4NS44bDg2LjcsMTAuOGMyNy41LDMuNCw1My42LTEyLjQsNjMuNS0zOC4zYzEyLjUtMzIuNywyOS45LTU4LjUsNTIuMi03Ny4zYzMxLjYwMS0yNi42LDcwLjktNDAsMTE3LjktNDANCgkJYzQ4LjcsMCw4Ny41LDEyLjgsMTE2LjMsMzguM2MyOC44LDI1LjYsNDMuMSw1Ni4yLDQzLjEsOTIuMWMwLDI1LjgtOC4xLDQ5LjQtMjQuMyw3MC44Yy0xMC41LDEzLjYtNDIuOCw0Mi4yLTk2LjcsODUuOQ0KCQljLTU0LDQzLjctODkuODk5LDgzLjA5OS0xMDcuODk5LDExOC4wOTljLTE4LjQsMzUuODAxLTI0LjgsNzUuNS0yNi40LDExNS4zMDFjLTEuMzk5LDM0LjEsMjUuOCw2Mi41LDYwLDYyLjVoNDkNCgkJYzMxLjIsMCw1Ny0yMy45LDU5LjgtNTQuOWMyLTIyLjI5OSw1LjctMzkuMTk5LDExLjMwMS01MC42OTljOS4zOTktMTkuNzAxLDMzLjY5OS00NS43MDEsNzIuNjk5LTc4LjENCgkJQzcyMy41OSw0NzcuOCw3NzIuNzksNDI4LjQsNzk1Ljg5MSwzOTJjMjMtMzYuMywzNC42LTc0LjgsMzQuNi0xMTUuNWMwLTczLjUtMzEuMy0xMzgtOTQtMTkzLjRjLTYyLjYtNTUuNC0xNDctODMuMS0yNTMtODMuMQ0KCQljLTEwMC44LDAtMTgyLjEsMjcuMy0yNDQuMSw4MmMtNTIuOCw0Ni42LTg0LjksMTAxLjgtOTYuMiwxNjUuNUMxMzkuNjksMjY2LjEsMTUyLjM5LDI4My41LDE3MC44OSwyODUuOHoiLz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjwvc3ZnPg0K"
            />
          </div>
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
          <div
            v-else-if="isAnswerUser"
            @click="startAnswerRecord()"
            class="conference-record-start-btn"
          >
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
        <div id="videoList">
          <div id="participants"></div>
        </div>
        <div id="main-video-wrapper">
          <div id="mainVideo"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import kurentoUtils from "kurento-utils";
import axios from "axios";
import API from "@/API.js";
import { mapState, mapGetters } from "vuex";
import ConferenceEvaluateModal from "./ConferenceEvaluateModal.vue";
import ConferenceExitModal from "./ConferenceExitModal.vue";
import ConferenceHelpModal from "./ConferenceHelpModal.vue";

async function startSharing() {
  if (!name.startsWith("scree&")) name = "scree&" + name;

  try {
    screenName = name;
    var message = {
      id: "startShare",
      name: name,
      room: room,
    };
    sendMessage(message);
  } catch (error) {
    console.log(error);
  }
}

function stopSharing() {
  sendMessage({
    id: "stopShare",
    name: screenName,
    userName: myName,
  });

  name = myName;
}

var ws = new WebSocket("wss://localhost:8443/groupcall");
// var ws = new WebSocket("wss://i5a507.p.ssafy.io:8443/groupcall");
var participants = {};
var name;
var room;
var screenName;
var opponentName;
var myName;
var isSharing = false;
var isMySharing = false;

ws.onmessage = function(message) {
  var parsedMessage = JSON.parse(message.data);
  // console.info("Received message: " + message.data);

  switch (parsedMessage.id) {
    case "existingParticipants":
      onExistingParticipants(parsedMessage);
      break;
    case "newParticipantArrived":
      onNewParticipant(parsedMessage);
      break;
    case "participantLeft":
      onParticipantLeft(parsedMessage);
      break;
    case "receiveVideoAnswer":
      receiveVideoResponse(parsedMessage);
      break;
    case "receiveScreenVideoAnswer":
      receiveScreenVideoResponse(parsedMessage);
      break;
    case "iceCandidate":
      participants[parsedMessage.name].rtcPeer.addIceCandidate(parsedMessage.candidate, function(
        error
      ) {
        if (error) {
          console.error("Error adding candidate: " + error);
          return;
        }
      });
      break;
    case "myScreenSharingStart":
      onExistingParticipants(parsedMessage);
      break;
    case "screenSharingStart":
      onNewParticipant(parsedMessage);
      break;
    default:
      console.error("Unrecognized message", parsedMessage);
  }
};

function register() {
  // myName = name;

  var message = {
    id: "joinRoom",
    name: myName,
    room: room,
  };
  sendMessage(message);
}

function onNewParticipant(request) {
  receiveVideo(request.name);
}

function receiveVideoResponse(result) {
  participants[result.name].rtcPeer.processAnswer(result.sdpAnswer, function(error) {
    if (error) {
      return console.error(error);
    }
  });
}

function receiveScreenVideoResponse(result) {
  participants[room].rtcPeer.processAnswer(result.sdpAnswer, function(error) {
    if (error) {
      return console.error(error);
    }
  });
}

function onExistingParticipants(msg) {
  var constraints = {
    audio: true,
    video: {
      mandatory: {
        maxWidth: 1920,
        maxHeight: 1080,
        maxFrameRate: 60,
      },
    },
  };

  if (participants[name] != null && msg.id != "myScreenSharingStart") {
    return;
  }

  var options = {};

  if (name.startsWith("scree&")) {
    isSharing = true;
    var participant = new Participant(name);
    participants[name] = participant;
    var video = participant.getVideoElement();
    var audio = "";
    navigator.mediaDevices.getUserMedia({ audio: true }).then((stream) => {
      audio = new MediaRecorder(stream);
    });

    options = {
      localVideo: video,
      mediaConstraints: constraints,
      audioStreams: audio,
      onicecandidate: participant.onIceCandidate.bind(participant),
      sendSource: "screen",
    };
  } else {
    var participant = new Participant(name);

    participants[name] = participant;
    var video = participant.getVideoElement();
    options = {
      localVideo: video,
      mediaConstraints: {
        audio: true,
        video: {
          mandatory: {
            maxWidth: 1920,
            maxHeight: 1080,
            maxFrameRate: 60,
          },
        },
      },
      onicecandidate: participant.onIceCandidate.bind(participant),
    };
  }
  participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerSendonly(options, function(error) {
    if (error) {
      return console.error(error);
    }
    this.generateOffer(participant.offerToReceiveVideo.bind(participant));
  });

  msg.data.forEach(receiveVideo);
}

function receiveVideo(sender) {
  var participant = new Participant(sender);
  participants[sender] = participant;
  var video = participant.getVideoElement();

  if (sender.startsWith("scree&") && sender.includes(myName)) {
    var constraints = {
      audio: true,
      video: {
        mandatory: {
          maxWidth: 1920,
          maxHeight: 1080,
          maxFrameRate: 60,
        },
      },
    };

    var options = {
      localVideo: video,
      mediaConstraints: constraints,
      onicecandidate: participant.onIceCandidate.bind(participant),
      sendSource: "screen",
    };

    participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerSendonly(options, function(error) {
      if (error) {
        return console.error(error);
      }
      this.generateOffer(participant.offerToReceiveVideo.bind(participant));
    });

    return;
  }

  var options = {
    remoteVideo: video,
    onicecandidate: participant.onIceCandidate.bind(participant),
  };

  participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(options, function(error) {
    if (error) {
      return console.error(error);
    }
    this.generateOffer(participant.offerToReceiveVideo.bind(participant));
  });
}

function onParticipantLeft(request) {
  if (request.name.startsWith("scree&")) isSharing = false;
  var participant = participants[request.name];
  participant.dispose();
  delete participants[request.name];
}

function sendMessage(message) {
  var jsonMessage = JSON.stringify(message);
  // console.log("Sending message: " + jsonMessage);
  ws.send(jsonMessage);
}

const PARTICIPANT_MAIN_CLASS = "participant main";
const PARTICIPANT_CLASS = "participant";

function Participant(name) {
  if (!name.startsWith("scree&") && name != myName) opponentName = name;

  this.name = name;

  if (name.startsWith("scree&")) {
    isSharing = true;
    this.name = name;

    var container = document.createElement("div");
    container.className = PARTICIPANT_CLASS;
    container.id = name;

    var video = document.createElement("video");
    video.id = "video-" + name;
    video.style.width = "300px";
    video.style.borderRadius = "5%";
    video.style.height = "225px";

    container.appendChild(video);
    container.onclick = switchContainerClass;

    document.getElementById("participants").appendChild(container);

    video.autoplay = true;
    video.controls = false;
  } else {
    this.name = name;

    var container = document.createElement("div");
    container.className = PARTICIPANT_CLASS;
    container.id = name;

    var video = document.createElement("video");
    video.id = "video-" + name;
    video.style.width = "300px";
    video.style.borderRadius = "5%";
    video.style.height = "225px";

    container.appendChild(video);
    container.onclick = switchContainerClass;
    document.getElementById("participants").appendChild(container);

    video.autoplay = true;
    video.controls = false;
  }

  this.getElement = function() {
    return container;
  };

  this.getVideoElement = function() {
    return video;
  };

  function switchContainerClass() {
    if (container.className === PARTICIPANT_CLASS) {
      const mainDiv = document.getElementById("mainVideo");
      const childDiv = mainDiv.firstChild;
      if (childDiv != null) {
        const participantsDiv = document.getElementById("participants");
        childDiv.className = PARTICIPANT_CLASS;
        const childVideo = childDiv.firstChild;
        childVideo.style.width = "300px";
        childVideo.style.borderRadius = "5%";
        childVideo.style.height = "225px";
        participantsDiv.appendChild(childDiv);
      }
      container.className = PARTICIPANT_MAIN_CLASS;
      mainDiv.appendChild(container);
      video.style.width = "1800px";
      video.style.borderRadius = "5%";
      video.style.height = "1350px";
    } else {
      const participantsDiv = document.getElementById("participants");
      container.className = PARTICIPANT_CLASS;
      participantsDiv.appendChild(container);
      video.style.width = "300px";
      video.style.borderRadius = "5%";
      video.style.height = "225px";
    }
  }

  this.offerToReceiveVideo = function(error, offerSdp, wp) {
    console.log(wp);
    if (error) return console.error("sdp offer error");
    console.log("Invoking SDP offer callback function");
    var msg = { id: "receiveVideoFrom", sender: name, sdpOffer: offerSdp, room: room };
    sendMessage(msg);
  };

  this.onIceCandidate = function(candidate, wp) {
    console.log(wp);
    var message = {
      id: "onIceCandidate",
      candidate: candidate,
      name: name,
    };
    sendMessage(message);
  };

  Object.defineProperty(this, "rtcPeer", { writable: true });

  this.dispose = function() {
    console.log("Disposing participant " + this.name);
    this.rtcPeer.dispose();
    container.parentNode.removeChild(container);
  };
}

export default {
  data: function() {
    return {
      searchInputData: "",
      isMyScreen: false,
      recoder: null,
      blobs: [],
      blob: null,
      desktopStream: null,
      isRecording: false,
      isAnswerUser: false,
    };
  },
  computed: {
    ...mapState({
      accessToken: (state) => state.auth.accessToken,
      userNickname: (state) => state.auth.userNickname,
    }),
    ...mapGetters(["getUserNickname"]),
  },
  props: ["questionId", "questionUserNickname"],
  methods: {
    entranceConferenceLog() {
      axios({
        url: API.URL + API.ROUTES.conferenceLog,
        method: "post",
        data: {
          type: "030",
        },
        headers: { Authorization: "Bearer " + this.accessToken },
      })
        .then((res) => {
          console.log(res);
        })
        .catch((e) => {
          console.log(e);
        });
    },
    exitConferenceLog() {
      axios({
        url: API.URL + API.ROUTES.conferenceLog,
        method: "post",
        data: {
          type: "031",
        },
        headers: { Authorization: "Bearer " + this.accessToken },
      })
        .then((res) => {
          console.log(res);
        })
        .catch((e) => {
          console.log(e);
        });
    },
    clickRegister() {
      register();
    },
    clickLeaveRoom() {
      if (this.isRecording) this.stopAnswerRecord();

      if (this.isAnswerUser) this.conferenceExit();
      else this.conferenceEvaluate();
    },
    clickSt() {
      if (isSharing) return;

      this.isMyScreen = true;
      isMySharing = true;
      isSharing = true;
      startSharing();
    },
    clickSp() {
      if (!isMySharing) return;
      this.isMyScreen = false;
      isMySharing = false;
      isSharing = false;
      stopSharing();
    },
    checkScreen() {
      if (isSharing && !this.isMyScreen) return false;

      if (this.isMyScreen) return false;

      if (!isSharing) return true;
    },
    isSharing() {
      return isSharing;
    },
    isMySharing() {
      return isMySharing;
    },
    conferenceEvaluate() {
      this.$modal.show(
        ConferenceEvaluateModal,
        {
          userName: opponentName,
          questionId: this.questionId,
          modal: this.$modal,
        },
        {
          name: "dynamic-modal",
          width: "600px",
          height: "500px",
          draggable: false,
          clickToClose: false,
        }
      );
    },
    conferenceExit() {
      this.$modal.show(
        ConferenceExitModal,
        {
          modal: this.$modal,
        },
        {
          name: "dynamic-modal",
          width: "600px",
          height: "230px",
          draggable: false,
          clickToClose: false,
        }
      );
    },
    async startAnswerRecord() {
      this.isRecording = true;

      const voiceStream = await navigator.mediaDevices.getUserMedia({ video: false, audio: true }); // 오디오스트림 생성

      const desktopStream = await navigator.mediaDevices.getDisplayMedia({
        video: true,
        audio: true,
      });

      const tracks = [
        ...desktopStream.getVideoTracks(),
        ...this.mergeAudioStreams(desktopStream, voiceStream),
      ];

      const stream = new MediaStream(tracks);

      const recoder = new MediaRecorder(stream, {
        mimeType: "",
      });
      this.recoder = recoder; // mediathis.recoderorder객체 생성
      this.recoder.ondataavailable = (e) => this.blobs.push(e.data);
      this.recoder.onstop = async () => {
        this.blob = new Blob(this.blobs, { type: "video/mp4" });
        if (this.isAnswerUser) this.recordVideo();
      };
      this.recoder.start(); // 녹화 시작
    },
    stopAnswerRecord() {
      this.isRecording = false;
      this.recoder.stop();
    },
    recordVideo() {
      var reader = new window.FileReader();
      var base64data;
      const $this = this;
      reader.readAsDataURL(this.blob);
      reader.onloadend = function() {
        base64data = reader.result;
        base64data = base64data.split(",")[1];
        $this.axiosVideo(base64data);
      };
    },
    axiosVideo(base64data) {
      axios({
        url: API.URL + `conferences/record/${this.questionId}`,
        method: "post",
        headers: {
          Authorization: "Bearer " + this.accessToken,
        },
        data: {
          videoFile: base64data,
        },
      })
        .then(() => {})
        .catch((err) => {
          console.log(err);
        });
    },
    mergeAudioStreams(desktopStream, voiceStream) {
      // 비디오, 오디오스트림 연결
      const context = new AudioContext();
      const destination = context.createMediaStreamDestination();
      let hasDesktop = false;
      let hasVoice = false;
      if (desktopStream && desktopStream.getAudioTracks().length > 0) {
        const source1 = context.createMediaStreamSource(desktopStream);
        const desktopGain = context.createGain();
        desktopGain.gain.value = 0.7;
        source1.connect(desktopGain).connect(destination);
        hasDesktop = true;
      }

      if (voiceStream && voiceStream.getAudioTracks().length > 0) {
        const source2 = context.createMediaStreamSource(voiceStream);
        const voiceGain = context.createGain();
        voiceGain.gain.value = 0.7;
        source2.connect(voiceGain).connect(destination);
        hasVoice = true;
      }

      return hasDesktop || hasVoice ? destination.stream.getAudioTracks() : [];
    },
    clickHelp() {
      this.$modal.show(
        ConferenceHelpModal,
        {
          modal: this.$modal,
        },
        {
          name: "dynamic-modal",
          width: "600px",
          height: "500px",
          draggable: false,
          clickToClose: false,
        }
      );
    },
  },
  mounted() {
    myName = this.getUserNickname;
    name = this.getUserNickname;
    room = this.questionId;

    if (room != null && this.questionUserNickname != this.getUserNickname) {
      this.isAnswerUser = true;
      this.entranceConferenceLog();
    }

    if (room == null) location.href = "/";

    const $this = this;
    window.onpopstate = function() {
      window.history.go(0);
    };

    window.addEventListener("beforeunload", () => {
      if ($this.isAnswerUser) $this.exitConferenceLog();
      if ($this.isRecording) $this.stopAnswerRecord();
    });

    this.clickRegister();
  },
};
</script>

<style scoped>
#help {
  right: 20px;
  top: 20px;
  position: absolute;
}

.container {
  margin: 50px auto;
  width: 640px;
}

#room {
  width: 100%;
  text-align: center;
}

#screens {
  margin-top: 20px;
}

#mainVideo {
  border: 10px;
}
#main-video-wrapper {
  display: inline-flex;
  justify-content: center;
}

#room-header {
  display: inline-flex;
  justify-content: center;
  max-width: 1611px;
}

.screen-buttons {
  display: inline-flex;
  justify-content: center;
  margin-top: 20px;
  padding-bottom: 20px;
  max-width: 1611px;
  align-items: center;
}

.conference-finish-btn {
  background-color: #ff6e6e;
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
  background-color: #fa4545;
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

.help-button {
  background-color: white;
  border: 1px solid #e0e0e0;
  border-radius: 70%;
  float: left;
  height: 45px;
  margin-right: 7px;
  width: 45px;
}

.help-button:hover {
  background-color: #658dc6;
  cursor: pointer;
  filter: brightness(115%);
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
  height: 245px;
  display: flex;
  justify-content: center;
  margin-top: 10px;
  margin-bottom: 15px;
}
</style>

<style scoped>
#participants {
  display: flex;
  min-width: 320px;
  min-height: 225px;
  justify-content: center;
  border: 10px;
  overflow: hide;
}

#wrapper {
  min-height: 1000px;
}

#router {
  background: linear-gradient(135deg, #658dc6, #b5c7d3);
  padding-bottom: 100px;
}
</style>
