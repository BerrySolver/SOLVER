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
          <button @click="testClick()">test</button>
        </form>
      </div>
      <div id="room" style="display: none;">
        <h2 id="room-header"></h2>
        <div id="participants">
          <div id="video1"></div>
          <div id="video2"></div>
        </div>
        <div id="screens"></div>
        <input type="button" id="button-leave" @click="clickLeaveRoom()" value="Leave room" />
      </div>
      <p>
        <button id="start" @click="clickSt()">Start Sharing</button>
        <button id="stop" @click="clickSp()">Stop Sharing</button>
      </p>
      <video id="video2" width="800" height="680" autoplay></video>
    </div>
  </div>
</template>

<script>
import kurentoUtils from "kurento-utils";

function clStart() {
  console.log(isMySharing);
  startSharing();
}
function clStop(e) {
  console.log(e);
  stopSharing();
}

// window.onbeforeunload = function() {
//   ws.close();
// };

async function startSharing() {
  // sendMessage({
  //   id: "leaveRoom",
  // });

  // for (var key in participants) {
  //   if (participants[key] != null) participants[key].dispose();

  //   participants[key] = null;
  // }
  // participants[name].dispose();
  if (!name.startsWith("scree&")) name = "scree&" + name;
  console.log("--------------------------");
  try {
    screenName = name;
    var message = {
      id: "startShare",
      // id: "joinRoom",
      name: name,
      // name: name,
      room: room,
    };
    sendMessage(message);
    // });
  } catch (error) {
    console.log(error);
  }
}

function stopSharing() {
  sendMessage({
    id: "stopShare",
    name: myName,
  });

  name = myName;

  participants["scree&" + myName].dispose();
  // isScreenSharingState
  // document.getElementById("join").style.display = "block";
  // document.getElementById("room").style.display = "none";
  // const videoPlayer = document.getElementById("video2");
  // let tracks = videoPlayer.srcObject.getTracks();
  // tracks.forEach((track) => track.stop());
  // videoPlayer.srcObject = null;
}

console.log("startShgare");

var ws = new WebSocket("wss://localhost:8443/groupcall");
var participants = {};
var name;
var room;
var screenName;
var myName;
var isSharing = false;
var isMySharing = false;
var participantsCount = 1;

ws.onmessage = function(message) {
  var parsedMessage = JSON.parse(message.data);
  console.info("Received message: " + message.data);

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
      // onNewScreenShare(parsedMessage);
      onNewParticipant(parsedMessage);
      break;
    default:
      console.error("Unrecognized message", parsedMessage);
    // alert("!");
  }
};

function register() {
  name = document.getElementById("name").value;
  room = document.getElementById("roomName").value;

  myName = name;

  document.getElementById("room-header").innerText = "ROOM " + room;
  document.getElementById("join").style.display = "none";
  document.getElementById("room").style.display = "block";

  var message = {
    id: "joinRoom",
    name: name,
    room: room,
  };
  sendMessage(message);
}

function onNewParticipant(request) {
  receiveVideo(request.name);
}

function onNewScreenShare(request) {
  screenName = request.name;
  receiveScreenVideo(request);
}

function receiveVideoResponse(result) {
  console.log(result.name);
  participants[result.name].rtcPeer.processAnswer(result.sdpAnswer, function(error) {
    console.log(result);
    if (error) {
      return console.error(error);
    }
  });
}

function receiveScreenVideoResponse(result) {
  console.log(participants[room]);
  participants[room].rtcPeer.processAnswer(result.sdpAnswer, function(error) {
    console.log(result);
    if (error) {
      return console.error(error);
    }
  });
}

// function callResponse(message) {
//   if (message.response != "accepted") {
//     console.info("Call not accepted by peer. Closing call");
//     stop();
//   } else {
//     webRtcPeer.processAnswer(message.sdpAnswer, function(error) {
//       if (error) return console.error(error);
//     });
//   }
// }

function onExistingParticipants(msg) {
  var constraints = {
    audio: true,
    video: {
      mandatory: {
        maxWidth: 1900,
        maxHeight: 1080,
        maxFrameRate: 60,
      },
    },
  };
  console.log(msg);
  console.log(name);
  if (participants[name] != null && msg.id != "myScreenSharingStart") {
    console.log(participants[name]);
    return;
  }

  console.log(participants[name]);
  console.log(name + " registered in room " + room);

  // navigator.mediaDevices.getDisplayMedia({ video: true }).then((stream) => {
  //   console.log(stream);
  //   video.srcObject = stream;
  // });

  var options = {};

  if (name.startsWith("scree&")) {
    // alert("!@!@");
    // var participant = new ScreenParticipant(screenName);
    var participant = new Participant(name);
    console.log(participant);
    participants[name] = participant;
    var video = participant.getVideoElement();
    var audio = "";
    navigator.mediaDevices.getUserMedia({ audio: true }).then((stream) => {
      audio = new MediaRecorder(stream);
    });
    console.log(audio);
    options = {
      localVideo: video,
      mediaConstraints: constraints,
      audioStreams: audio,
      onicecandidate: participant.onIceCandidate.bind(participant),
      sendSource: "screen",
    };
  } else {
    var participant = new Participant(name);
    console.log(participant);
    participants[name] = participant;
    var video = participant.getVideoElement();
    options = {
      localVideo: video,
      mediaConstraints: {
        audio: true,
        video: {
          mandatory: {
            maxWidth: 1900,
            maxHeight: 1080,
            maxFrameRate: 60,
          },
        },
      },
      onicecandidate: participant.onIceCandidate.bind(participant),
      // sendSource: "screen",
    };
  }
  console.log("sendOnly");
  stop(100000000);
  participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerSendonly(options, function(error) {
    if (error) {
      console.log(error);
      return console.error(error);
    }
    this.generateOffer(participant.offerToReceiveVideo.bind(participant));
  });

  // var participant = new Participant(name + "1");
  // console.log(participant);
  // participants[name] = participant;
  // var video = participant.getVideoElement();
  // options = {
  //   localVideo: video,
  //   mediaConstraints: constraints,
  //   onicecandidate: participant.onIceCandidate.bind(participant),
  //   // sendSource: "screen",
  // };

  // participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerSendonly(options, function(error) {
  //   if (error) {
  //     console.log(error);
  //     return console.error(error);
  //   }
  //   this.generateOffer(participant.offerToReceiveVideo.bind(participant));
  // });

  console.log(msg.data);

  msg.data.forEach(receiveVideo);
}

function leaveRoom() {
  console.log("leaveRoom");
  sendMessage({
    id: "leaveRoom",
  });

  for (var key in participants) {
    participants[key].dispose();
  }

  document.getElementById("join").style.display = "block";
  document.getElementById("room").style.display = "none";

  // ws.close();
}

function receiveVideo(sender) {
  // if (request.id == "screenSharngStart") sender = "isScreenSharingState";
  console.log("sender: " + sender);
  //영상인 경우가 겹침
  // if (participants[sender] != null) return;
  var participant = new Participant(sender);
  console.log(participant);
  participants[sender] = participant;
  var video = participant.getVideoElement();

  console.log(sender.includes(myName));

  if (sender.startsWith("scree&") && sender.includes(myName)) {
    var constraints = {
      audio: true,
      video: {
        mandatory: {
          maxWidth: 1900,
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
        console.log(error);
        return console.error(error);
      }
      this.generateOffer(participant.offerToReceiveVideo.bind(participant));
    });

    return;
  }

  // if(sender.startsWith("scree&") ){
  //   data
  // }

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

function receiveScreenVideo(request) {
  // if (request.id == "screenSharngStart") sender = "isScreenSharingState";
  console.log("Screen sender: " + request.name);
  //영상인 경우가 겹침
  // if (participants[sender] != null) return;
  console.log(screenName);
  var participant = new ScreenParticipant(screenName);
  console.log(participant);
  participants[room] = participant;
  var video = participant.getVideoElement();

  var options = {
    remoteVideo: video,
    onicecandidate: participant.onIceCandidate.bind(participant),
  };

  console.log(options);

  participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(options, function(error) {
    if (error) {
      return console.error(error);
    }
    console.log("screen gener");
    this.generateOffer(participant.offerToReceiveVideo.bind(participant));
  });
}

function onParticipantLeft(request) {
  console.log("Participant " + request.name + " left");
  var participant = participants[request.name];
  participant.dispose();
  delete participants[request.name];
}

function sendMessage(message) {
  console.log(ws);
  // alert("send");
  // if (ws.readyState == 3) {
  //   ws.close();
  //   ws = new WebSocket("wss://localhost:8443/groupcall");
  //   while (ws.readyState != 1) {}
  //   console.log("!@!@!@!@!@@@#@$@$@$");
  //   ws.readyState == 1;
  // }
  // console.log(ws);
  var jsonMessage = JSON.stringify(message);
  console.log("Sending message: " + jsonMessage);
  ws.send(jsonMessage);
}

const PARTICIPANT_MAIN_CLASS = "participant main";
const PARTICIPANT_CLASS = "participant";

/**
 * Creates a video element for a new participant
 *
 * @param {String} name - the name of the new participant, to be used as tag
 *                        name of the video element.
 *                        The tag of the new element will be 'video<name>'
 * @return
 */
function ScreenParticipant(scName) {
  this.screenName = scName;
  console.log(screenName);
  var container = document.createElement("div");
  container.className = isPresentMainParticipant() ? PARTICIPANT_CLASS : PARTICIPANT_MAIN_CLASS;
  container.id = scName;
  // var span = document.createElement("span");
  var video = document.createElement("video");
  video.id = "video-screen";

  // var rtcPeer;

  container.appendChild(video);
  container.appendChild(span);
  container.onclick = switchContainerClass;
  document.getElementById("participants").appendChild(container);

  // span.appendChild(document.createTextNode("screen"));

  video.autoplay = true;
  video.controls = false;

  this.getElement = function() {
    return container;
  };

  this.getVideoElement = function() {
    return video;
  };

  function switchContainerClass() {
    if (container.className === PARTICIPANT_CLASS) {
      var elements = Array.prototype.slice.call(
        document.getElementsByClassName(PARTICIPANT_MAIN_CLASS)
      );
      elements.forEach(function(item) {
        item.className = PARTICIPANT_CLASS;
      });

      container.className = PARTICIPANT_MAIN_CLASS;
    } else {
      container.className = PARTICIPANT_CLASS;
    }
  }

  function isPresentMainParticipant() {
    return document.getElementsByClassName(PARTICIPANT_MAIN_CLASS).length != 0;
  }

  this.offerToReceiveVideo = function(error, offerSdp, wp) {
    console.log(wp);
    if (error) {
      console.error(error);
      return console.error("sdp offer error");
    }
    console.log("Invoking SDP offer callback function");
    var msg = {
      id: "receiveVideoFrom",
      sender: screenName,
      sdpOffer: offerSdp,
      room: room,
      screen: screenName,
    };
    sendMessage(msg);
  };

  this.onIceCandidate = function(candidate, wp) {
    console.log(wp);
    // console.log("Local candidate" + JSON.stringify(candidate));

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

function Participant(name) {
  this.name = name;
  console.log("this.name : " + this.name);
  // var container = document.createElement("div");
  // container.className = isPresentMainParticipant() ? PARTICIPANT_CLASS : PARTICIPANT_MAIN_CLASS;
  // container.id = name;
  // var span = document.createElement("span");
  // var video = document.createElement("video");
  // video.id = "video-" + name;

  // // var rtcPeer;

  // container.appendChild(video);
  // container.appendChild(span);
  // container.onclick = switchContainerClass;

  if (name.startsWith("scree&")) {
    this.name = name;
    console.log("this.name : " + this.name);
    var container = document.createElement("div");
    container.className = isPresentMainParticipant() ? PARTICIPANT_CLASS : PARTICIPANT_MAIN_CLASS;
    container.id = name;
    // var span = document.createElement("span");
    var video = document.createElement("video");
    // var video = document.getElementById("screen");
    video.id = "screen";

    // var rtcPeer;

    container.appendChild(video);
    // container.appendChild(span);
    container.onclick = switchContainerClass;
    document.getElementById("screens").appendChild(container);
    // span.appendChild(document.createTextNode(name));

    video.autoplay = true;
    video.controls = false;
  } else {
    this.name = name;
    console.log("this.name : " + this.name);
    var container = document.createElement("div");
    container.className = isPresentMainParticipant() ? PARTICIPANT_CLASS : PARTICIPANT_MAIN_CLASS;
    container.id = name;
    // var span = document.createElement("span");
    var video = document.createElement("video");
    video.id = "video-" + name;
    video.style.width = "800px";
    video.style.height = "600px";

    // var rtcPeer;

    container.appendChild(video);
    // container.appendChild(span);
    container.onclick = switchContainerClass;
    // document.getElementById("participants").appendChild(container);
    document.getElementById("video" + participantsCount).appendChild(container);
    // span.appendChild(document.createTextNode(name));
    participantsCount++;
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
      var elements = Array.prototype.slice.call(
        document.getElementsByClassName(PARTICIPANT_MAIN_CLASS)
      );
      elements.forEach(function(item) {
        item.className = PARTICIPANT_CLASS;
      });

      container.className = PARTICIPANT_MAIN_CLASS;
    } else {
      container.className = PARTICIPANT_CLASS;
    }
  }

  function isPresentMainParticipant() {
    return document.getElementsByClassName(PARTICIPANT_MAIN_CLASS).length != 0;
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
    // console.log("Local candidate" + JSON.stringify(candidate));

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

function test() {
  console.log(data.isSharing);
}

export default {
  data: function() {
    return {
      searchInputData: "",
    };
  },
  computed: {
    myShare() {
      return isMySharing == true;
    },
    screenShare() {
      return isSharing == true;
    },
  },
  methods: {
    clickRegister() {
      register();
    },
    clickLeaveRoom() {
      leaveRoom();
    },
    clickSt() {
      if (isSharing) return;
      isMySharing = true;
      isSharing = true;
      clStart();
    },
    clickSp() {
      if (!isMySharing) return;
      isMySharing = false;
      isSharing = false;
      clStop();
    },
    testClick() {
      test();
    },
    isSharing() {
      return isSharing;
    },
    isMySharing() {
      return isMySharing;
    },
  },
};
</script>

<style scoped>
body {
  font: 13px/20px "Lucida Grande", Tahoma, Verdana, sans-serif;
  color: #404040;
  background: #0ca3d2;
}

input[type="checkbox"],
input[type="radio"] {
  border: 1px solid #c0c0c0;
  margin: 0 0.1em 0 0;
  padding: 0;
  font-size: 16px;
  line-height: 1em;
  width: 1.25em;
  height: 1.25em;
  background: #fff;
  background: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#ededed), to(#fbfbfb));
  -webkit-appearance: none;
  -webkit-box-shadow: 1px 1px 1px #fff;
  -webkit-border-radius: 0.25em;
  vertical-align: text-top;
  display: inline-block;
}

input[type="radio"] {
  -webkit-border-radius: 2em; /* Make radios round */
}

input[type="checkbox"]:checked::after {
  content: "✔";
  display: block;
  text-align: center;
  font-size: 16px;
  height: 16px;
  line-height: 18px;
}

input[type="radio"]:checked::after {
  content: "●";
  display: block;
  height: 16px;
  line-height: 15px;
  font-size: 20px;
  text-align: center;
}

select {
  border: 1px solid #d0d0d0;
  background: url(http://www.quilor.com/i/select.png) no-repeat right center,
    -webkit-gradient(linear, 0% 0%, 0% 100%, from(#fbfbfb), to(#ededed));
  background: -moz-linear-gradient(19% 75% 90deg, #ededed, #fbfbfb);
  -webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
  -moz-box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
  -webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
  color: #444;
}

.container {
  margin: 50px auto;
  width: 640px;
}

.join {
  position: relative;
  margin: 0 auto;
  padding: 20px 20px 20px;
  width: 310px;
  background: white;
  border-radius: 3px;
  -webkit-box-shadow: 0 0 200px rgba(255, 255, 255, 0.5), 0 1px 2px rgba(0, 0, 0, 0.3);
  box-shadow: 0 0 200px rgba(255, 255, 255, 0.5), 0 1px 2px rgba(0, 0, 0, 0.3);
  /*Transition*/
  -webkit-transition: all 0.3s linear;
  -moz-transition: all 0.3s linear;
  -o-transition: all 0.3s linear;
  transition: all 0.3s linear;
}

.join:before {
  content: "";
  position: absolute;
  top: -8px;
  right: -8px;
  bottom: -8px;
  left: -8px;
  z-index: -1;
  background: rgba(0, 0, 0, 0.08);
  border-radius: 4px;
}

.join h1 {
  margin: -20px -20px 21px;
  line-height: 40px;
  font-size: 15px;
  font-weight: bold;
  color: #555;
  text-align: center;
  text-shadow: 0 1px white;
  background: #f3f3f3;
  border-bottom: 1px solid #cfcfcf;
  border-radius: 3px 3px 0 0;
  background-image: -webkit-linear-gradient(top, whiteffd, #eef2f5);
  background-image: -moz-linear-gradient(top, whiteffd, #eef2f5);
  background-image: -o-linear-gradient(top, whiteffd, #eef2f5);
  background-image: linear-gradient(to bottom, whiteffd, #eef2f5);
  -webkit-box-shadow: 0 1px whitesmoke;
  box-shadow: 0 1px whitesmoke;
}

.join p {
  margin: 20px 0 0;
}

.join p:first-child {
  margin-top: 0;
}

.join input[type="text"],
.join input[type="password"] {
  width: 278px;
}

.join p.submit {
  text-align: center;
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

input[type="button"]:active,
input[type="submit"]:active {
  background: #cde5ef;
  border-color: #9eb9c2 #b3c0c8 #b4ccce;
  -webkit-box-shadow: inset 0 0 3px rgba(0, 0, 0, 0.2);
  box-shadow: inset 0 0 3px rgba(0, 0, 0, 0.2);
}

.lt-ie9 input[type="text"],
.lt-ie9 input[type="password"] {
  line-height: 34px;
}

#room {
  width: 100%;
  text-align: center;
}

#button-leave {
  text-align: center;
  position: absolute;
  bottom: 10px;
}

.participant {
  border-radius: 4px;
  /* border: 2px groove; */
  margin-left: 5;
  margin-right: 5;
  width: 150;
  text-align: center;
  overflow: hide;
  float: left;
  padding: 5px;
  border-radius: 10px;
  -webkit-box-shadow: 0 0 200px rgba(255, 255, 255, 0.5), 0 1px 2px rgba(0, 0, 0, 0.3);
  box-shadow: 0 0 200px rgba(255, 255, 255, 0.5), 0 1px 2px rgba(0, 0, 0, 0.3);
  /*Transition*/
  -webkit-transition: all 0.3s linear;
  -moz-transition: all 0.3s linear;
  -o-transition: all 0.3s linear;
  transition: all 0.3s linear;
}

.participant:before {
  content: "";
  position: absolute;
  top: -8px;
  right: -8px;
  bottom: -8px;
  left: -8px;
  z-index: -1;
  background: rgba(0, 0, 0, 0.08);
  border-radius: 4px;
}

.participant:hover {
  opacity: 1;
  background-color: 0a33b6;
  -webkit-transition: all 0.5s linear;
  transition: all 0.5s linear;
}

.participant video,
.participant.main video {
  width: 100% !important;
  height: auto !important;
}

.participant span {
  color: PapayaWhip;
}

.participant.main {
  width: 20%;
  margin: 0 auto;
}

.participant.main video {
  height: auto;
}

.animate {
  -webkit-animation-duration: 0.5s;
  -webkit-animation-fill-mode: both;
  -moz-animation-duration: 0.5s;
  -moz-animation-fill-mode: both;
  -o-animation-duration: 0.5s;
  -o-animation-fill-mode: both;
  -ms-animation-duration: 0.5s;
  -ms-animation-fill-mode: both;
  animation-duration: 0.5s;
  animation-fill-mode: both;
}

.removed {
  -webkit-animation: disapear 1s;
  -webkit-animation-fill-mode: forwards;
  animation: disapear 1s;
  animation-fill-mode: forwards;
}

@-webkit-keyframes disapear {
  50% {
    -webkit-transform: translateX(-5%);
    transform: translateX(-5%);
  }
  100% {
    -webkit-transform: translateX(200%);
    transform: translateX(200%);
  }
}

@keyframes disapear {
  50% {
    -webkit-transform: translateX(-5%);
    transform: translateX(-5%);
  }

  100% {
    -webkit-transform: translateX(200%);
    transform: translateX(200%);
  }
}
a.hovertext {
  position: relative;
  width: 500px;
  text-decoration: none !important;
  text-align: center;
}

a.hovertext:after {
  content: attr(title);
  position: absolute;
  left: 0;
  bottom: 0;
  padding: 0.5em 20px;
  width: 460px;
  background: rgba(0, 0, 0, 0.8);
  text-decoration: none !important;
  color: #fff;
  opacity: 0;
  -webkit-transition: 0.5s;
  -moz-transition: 0.5s;
  -o-transition: 0.5s;
  -ms-transition: 0.5s;
}

a.hovertext:hover:after,
a.hovertext:focus:after {
  opacity: 1;
}

#participants {
  display: flex;
  justify-content: space-between;
}
</style>
