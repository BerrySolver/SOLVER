<template>
  <div>
    <div class="solver-header">
      <div class="solver-banner">
        <img src="@/assets/logo-white-2.png" alt="white-logo">
        <div class="solver-banner-ment">
          <h6 class="ment">여러분이 좋아하는 답변자는 다른 사람의 기준과는 다를거에요!</h6>
          <h6 class="ment">많은 <span>능력자</span> 중 여러분의 마음에 드는 분을 찾아보세요!</h6>
        </div>
      </div>
    </div>
    <div class="solver-body">
      <!-- 검색창 -->
      <div class="solver-body-contents">
        <div class="solver-form-group search-col">
            <input class="solver-form-field" type="text" placeholder="닉네임을 검색해보세요!" @keypress.enter="setQuery" v-model="query">
            <span @click="setQuery">검&nbsp;&nbsp;색</span>
        </div>
        <div class="search-col">
          <select class="form-select" @change="selectSort($event)">
            <option :value="''">검색기준</option>
            <option v-for="(item, index) in sortOptions" :key="index" :value="item.value" >{{item.text}}</option>
          </select>
        </div>
        <div class="search-col">
          <select class="form-select" @change="setSubCategory($event)">
            <option :value="''">대분류</option>
            <option v-for="(item, index) in Category" :key="index" :value="item.code">{{item.codeName}}</option>
          </select>
        </div>
        <div class="search-col">
          <select id="sub-select" class="form-select" @change="setResultCategory($event)">
            <option :value="''">소분류</option>
            <option v-for="(item, index) in subCategory" :key="index" :value="item.subCategoryCode">{{item.subCategoryName}}</option>
          </select>
        </div>
      </div>
    </div>
    <div style="min-height:500px;">
      <!-- 실제 리스트 -->
      <div class="solver-body"  v-for="(solvers, idx1) in solverList" :key="idx1">
        <div class="solver-body-contents">
          <div class="solver-list">
            <div class="solver-user-card" v-for="(solverData, idx2) in solvers" :key="idx2">
              <img class="user-level-badge" src="@/assets/berry-1.png" alt="">
              <div class="solver-user-col1">
                <img class="user-image" src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png"/>
              </div>   
              <div class="solver-user-col2 detail-text">
                <h5>{{solverData.nickname}}</h5>
                <span class="detail-category" v-for="(field, idx3) in solverData.favoriteFieldNameList" :key="idx3">{{field}}</span>
              </div>     
              <div class="solver-user-row detail-text">
                {{solverData.introduction}}
              </div>      
              <div class="solver-user-row">
                <img src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iaXNvLTg4NTktMSI/Pg0KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDE5LjAuMCwgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPg0KPHN2ZyB2ZXJzaW9uPSIxLjEiIGlkPSJDYXBhXzEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4Ig0KCSB2aWV3Qm94PSIwIDAgNTEyIDUxMiIgc3R5bGU9ImVuYWJsZS1iYWNrZ3JvdW5kOm5ldyAwIDAgNTEyIDUxMjsiIHhtbDpzcGFjZT0icHJlc2VydmUiPg0KPGc+DQoJPGc+DQoJCTxnPg0KCQkJPHBhdGggZD0iTTQ5MiwxMjcuNWgtMTh2LTE4YzAtMTEuMDQ2LTguOTU0LTIwLTIwLTIwYy0xMS4wNDYsMC0yMCw4Ljk1NC0yMCwyMHYxOGgtMThjLTExLjA0NiwwLTIwLDguOTU0LTIwLDIwczguOTU0LDIwLDIwLDIwDQoJCQkJaDE4djE4YzAsMTEuMDQ2LDguOTU0LDIwLDIwLDIwYzExLjA0NiwwLDIwLTguOTU0LDIwLTIwdi0xOGgxOGMxMS4wNDYsMCwyMC04Ljk1NCwyMC0yMFM1MDMuMDQ2LDEyNy41LDQ5MiwxMjcuNXoiLz4NCgkJCTxwYXRoIGQ9Ik0zMTUuNDA5LDI0OS4yMzFDMzQ1Ljg1NCwyMjUuNzExLDM2NS41LDE4OC44NiwzNjUuNSwxNDcuNUMzNjUuNSw3Ni42NDUsMzA3Ljg1NSwxOSwyMzcsMTlTMTA4LjUsNzYuNjQ1LDEwOC41LDE0Ny41DQoJCQkJYzAsNDEuMzU5LDE5LjY0Niw3OC4yMTEsNTAuMDkxLDEwMS43MzFDNjguMjkzLDI4MC43OTMsMCwzNjcuNDI3LDAsNDczYzAsMTEuMDQ2LDguOTU0LDIwLDIwLDIwaDQzNGMxMS4wNDYsMCwyMC04Ljk1NCwyMC0yMA0KCQkJCUM0NzQsMzY3LjQwMSw0MDUuNjU2LDI4MC43NzUsMzE1LjQwOSwyNDkuMjMxeiBNMTQ4LjUsMTQ3LjVjMC00OC43OTksMzkuNzAxLTg4LjUsODguNS04OC41czg4LjUsMzkuNzAxLDg4LjUsODguNQ0KCQkJCVMyODUuNzk5LDIzNiwyMzcsMjM2UzE0OC41LDE5Ni4yOTksMTQ4LjUsMTQ3LjV6IE00MS4wMDgsNDUzQzUxLjA2MSwzNTMuNzMsMTM1LjEyMywyNzYsMjM3LDI3NnMxODUuOTM5LDc3LjczLDE5NS45OTIsMTc3DQoJCQkJSDQxLjAwOHoiLz4NCgkJPC9nPg0KCTwvZz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjwvc3ZnPg0K" />
                {{solverData.follower}} &nbsp;&nbsp;
                <img src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iaXNvLTg4NTktMSI/Pg0KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDE5LjAuMCwgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPg0KPHN2ZyB2ZXJzaW9uPSIxLjEiIGlkPSJDYXBhXzEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4Ig0KCSB2aWV3Qm94PSIwIDAgMzc1Ljk0IDM3NS45NCIgc3R5bGU9ImVuYWJsZS1iYWNrZ3JvdW5kOm5ldyAwIDAgMzc1Ljk0IDM3NS45NDsiIHhtbDpzcGFjZT0icHJlc2VydmUiPg0KPGc+DQoJPGc+DQoJCTxwYXRoIGQ9Ik0zMjAuNTM1LDE0Ljg2NWMtNDcuOTgzLTI3LjkxMy0xMDkuNDIzLTE1LjUyMi0xNDIuOTIzLDI4LjgyYy0yLjI3MywzLjAxMS0xLjY3OCw3LjI5MSwxLjMzLDkuNTY0DQoJCQljMy4wMTQsMi4yNyw3LjI4NywxLjY3MSw5LjU2NC0xLjMyOWMyOS4zNDYtMzguODQsODMuMTU3LTQ5LjY5NiwxMjUuMTY0LTI1LjI1NGM0Ni4xOTksMjYuODgzLDYyLjA4LDg2LjY0OCwzNS4zOTYsMTMzLjIyMQ0KCQkJYy03Ljk1LDEzLjg4LTE5LjI0NCwyNS42NTYtMzIuNjYsMzQuMDUzYy0zLjE5OCwyLTQuMTY4LDYuMjE0LTIuMTY3LDkuNDA3YzEuMjk3LDIuMDcsMy41MjEsMy4yMDcsNS43OTQsMy4yMDcNCgkJCWMxLjIzNywwLDIuNDktMC4zMzYsMy42MTMtMS4wNDNjMTUuMzIxLTkuNTg2LDI4LjIwNi0yMy4wMTgsMzcuMjY3LTM4Ljg0QzM5MS4zMTIsMTEzLjYxMSwzNzMuMTk3LDQ1LjUwOSwzMjAuNTM1LDE0Ljg2NXoiLz4NCgk8L2c+DQo8L2c+DQo8Zz4NCgk8Zz4NCgkJPHBhdGggZD0iTTE4NC4wNTQsNDMuNzM5Yy0yOC4zMTctMTYuMzk5LTYxLjMxLTIwLjc2My05Mi45MDQtMTIuM2MtMzEuNjUzLDguNDg0LTU4LjEwMywyOC44MjQtNzQuNDg0LDU3LjI3NA0KCQkJYy0zMy43NzMsNTguNjYzLTEzLjY1MywxMzMuOTQ2LDQ0Ljg0NiwxNjcuODJjMTguNzgsMTAuODc3LDM5LjY3NywxNi40OSw2MS4wNjYsMTYuNDljNC4wNSwwLDguMTItMC4yMDMsMTIuMTk5LTAuNjA2DQoJCQljMy43NS0wLjM3NCw2LjQ5LTMuNzE3LDYuMTE3LTcuNDdjLTAuMzc0LTMuNzUtMy43LTYuNTMzLTcuNDctNi4xMTdjLTIyLjcyOSwyLjI1Ni00NS4yMjMtMi42MjEtNjUuMDY3LTE0LjExMQ0KCQkJYy01Mi0zMC4xMTEtNjkuODgxLTk3LjAzOS0zOS44NTQtMTQ5LjE5M0M0My4wNiw3MC4yNCw2Ni41NjIsNTIuMTYzLDk0LjY4Niw0NC42M2MyOC4wNTktNy41MjUsNTcuMzczLTMuNjQ0LDgyLjUyOSwxMC45MjMNCgkJCWMzNi45MzIsMjEuMzg2LDU4LjEwOSw2Mi43MjcsNTMuOTQzLDEwNS4zMmMtMC4zNjcsMy43NSwyLjM3Nyw3LjA5LDYuMTMsNy40NTZjMy43MzksMC4zODYsNy4wOTMtMi4zODEsNy40NTYtNi4xMw0KCQkJQzI0OS40MzIsMTE0LjI5OSwyMjUuNjA4LDY3Ljc5OSwxODQuMDU0LDQzLjczOXoiLz4NCgk8L2c+DQo8L2c+DQo8Zz4NCgk8Zz4NCgkJPHBhdGggZD0iTTEyNS40NDcsMTU0LjI4NWwtMTkuMjgtMjEuNjQ0bDUuODc2LTI4LjM4N2MwLjYwMS0yLjg4My0wLjcyLTUuODIzLTMuMjctNy4yOTZjLTIuNTQ2LTEuNDY5LTUuNzU3LTEuMTQ5LTcuOTUzLDAuODE4DQoJCQlsLTIxLjY0NCwxOS4yODRsLTI4LjM4Ny01Ljg3NmMtMi45MDYtMC42MDQtNS44MjcsMC43MjQtNy4yOTYsMy4yN2MtMS40NjksMi41NS0xLjE0Miw1Ljc1NywwLjgxOCw3Ljk1M2wxOS4yOCwyMS42NDQNCgkJCWwtNS44NzYsMjguMzg0Yy0wLjU5NCwyLjg4MywwLjcyNCw1LjgyMywzLjI3Myw3LjI5NmMxLjA2MywwLjYxMywyLjIzOSwwLjkxMywzLjQxMywwLjkxM2MxLjYzNywwLDMuMjYtMC41ODcsNC41NC0xLjczMQ0KCQkJbDIxLjY0MS0xOS4yOGwyOC4zODcsNS44NzZjMi45LDAuNjAxLDUuODI3LTAuNzI3LDcuMjk2LTMuMjdDMTI3LjczNCwxNTkuNjg4LDEyNy40MDcsMTU2LjQ4MSwxMjUuNDQ3LDE1NC4yODV6IE04OS45OSwxNDUuNTY5DQoJCQljLTIuMTE1LTAuNDQtNC4zMTQsMC4xNTQtNS45MjQsMS41ODdsLTguNzk2LDcuODM0bDIuMzg2LTExLjUzNGMwLjQ0LTIuMTE1LTAuMTUtNC4zMTEtMS41ODctNS45MjRsLTcuODM3LTguNzk2bDExLjU0MSwyLjM4Ng0KCQkJYzIuMTI2LDAuNDU2LDQuMzE4LTAuMTUsNS45MjQtMS41ODdsOC43OTYtNy44MzdsLTIuMzg2LDExLjUzN2MtMC40NCwyLjExNSwwLjE1LDQuMzExLDEuNTg3LDUuOTI0bDcuODM3LDguNzk2TDg5Ljk5LDE0NS41Njl6Ig0KCQkJLz4NCgk8L2c+DQo8L2c+DQo8Zz4NCgk8Zz4NCgkJPHBhdGggZD0iTTMxOC4yMzYsMTE2Ljc3MmwtMTMuMDI3LTI1Ljg5N2wxMy4wMjctMjUuODk3YzEuMzIzLTIuNjMsMC44MTEtNS44MTMtMS4yNzMtNy44OTNjLTIuMDgtMi4wNzctNS4yNTctMi41OTktNy44OTMtMS4yNzMNCgkJCWwtMjUuODk3LDEzLjAyN2wtMjUuODk3LTEzLjAyN2MtMi42My0xLjMyNi01LjgxMy0wLjgwNC03Ljg5MywxLjI3M2MtMi4wODQsMi4wOC0yLjU5Miw1LjI2My0xLjI3Myw3Ljg5M2wxMy4wMjQsMjUuODk3DQoJCQlsLTEzLjAyNCwyNS44OTdjLTEuMzIxLDIuNjMtMC44MTEsNS44MTMsMS4yNzMsNy44OTNjMS4zMTEsMS4zMTEsMy4wNTcsMiw0LjgzLDJjMS4wMzksMCwyLjA4OS0wLjIzNywzLjA2My0wLjcyN2wyNS44OTctMTMuMDI3DQoJCQlsMjUuODk3LDEzLjAyN2MyLjYzNywxLjMyMyw1LjgxMywwLjgwNyw3Ljg5My0xLjI3M0MzMTkuMDQ2LDEyMi41ODUsMzE5LjU1OCwxMTkuNDAyLDMxOC4yMzYsMTE2Ljc3MnogTTI5MS40NjgsOTMuOTQyDQoJCQlsNS4yOTQsMTAuNTIzbC0xMC41MjMtNS4yOTRjLTEuOTI3LTAuOTY2LTQuMjA3LTAuOTY2LTYuMTM0LDBsLTEwLjUyNyw1LjI5N2w1LjI5NC0xMC41MjdjMC45NjktMS45MywwLjk2OS00LjIwMywwLTYuMTM0DQoJCQlsLTUuMjk0LTEwLjUyN2wxMC41MjcsNS4yOTdjMS45MjcsMC45NjYsNC4yMDcsMC45NjYsNi4xMzQsMGwxMC41MjMtNS4yOTRsLTUuMjk0LDEwLjUyMw0KCQkJQzI5MC40OTksODkuNzM5LDI5MC40OTksOTIuMDEyLDI5MS40NjgsOTMuOTQyeiIvPg0KCTwvZz4NCjwvZz4NCjxnPg0KCTxnPg0KCQk8cGF0aCBkPSJNMjM5Ljg2MiwxNTcuNDg2Yy01OS44NDgsMC0xMDguNTM0LDQ5LTEwOC41MzQsMTA5LjIyN1MxODAuMDE2LDM3NS45NCwyMzkuODYyLDM3NS45NA0KCQkJYzU5Ljg0NiwwLDEwOC41MzQtNDksMTA4LjUzNC0xMDkuMjI3UzI5OS43MSwxNTcuNDg2LDIzOS44NjIsMTU3LjQ4NnogTTIzOS44NjIsMzYyLjI4NmMtNTIuMzE4LDAtOTQuODgtNDIuODczLTk0Ljg4LTk1LjU3Mw0KCQkJYzAtNTIuNyw0Mi41NjQtOTUuNTczLDk0Ljg4LTk1LjU3M2M1Mi4zMTYsMCw5NC44OCw0Mi44NzMsOTQuODgsOTUuNTczQzMzNC43NDMsMzE5LjQxMywyOTIuMTgsMzYyLjI4NiwyMzkuODYyLDM2Mi4yODZ6Ii8+DQoJPC9nPg0KPC9nPg0KPGc+DQoJPGc+DQoJCTxwYXRoIGQ9Ik0yNTYuNzk2LDI2MC4xMzJsLTEzLjAyNy0yNS44OTdsMTMuMDI3LTI1Ljg5N2MxLjMyMy0yLjYzLDAuODExLTUuODEzLTEuMjczLTcuODkzYy0yLjA3Ny0yLjA3Ny01LjI2LTIuNTk2LTcuODkzLTEuMjczDQoJCQlsLTI1Ljg5NywxMy4wMjdsLTI1Ljg5Ny0xMy4wMjdjLTIuNjMzLTEuMzIzLTUuODE2LTAuODA0LTcuODkzLDEuMjczYy0yLjA4NCwyLjA4LTIuNTkyLDUuMjYzLTEuMjczLDcuODkzbDEzLjAyNCwyNS44OTcNCgkJCWwtMTMuMDI0LDI1Ljg5N2MtMS4zMjEsMi42My0wLjgxMSw1LjgxMywxLjI3Myw3Ljg5M2MxLjMxMSwxLjMxMSwzLjA1NywyLDQuODMsMmMxLjAzOSwwLDIuMDg5LTAuMjM3LDMuMDYzLTAuNzI3bDI1Ljg5Ny0xMy4wMjcNCgkJCWwyNS44OTcsMTMuMDI3YzIuNjMzLDEuMzI2LDUuODE2LDAuODA0LDcuODkzLTEuMjczQzI1Ny42MDYsMjY1Ljk0NSwyNTguMTE4LDI2Mi43NjIsMjU2Ljc5NiwyNjAuMTMyeiBNMjMwLjAyOCwyMzcuMzAyDQoJCQlsNS4yOTQsMTAuNTIzbC0xMC41MjMtNS4yOTRjLTEuOTI3LTAuOTY2LTQuMjA3LTAuOTY2LTYuMTM0LDBsLTEwLjUyNyw1LjI5N2w1LjI5NC0xMC41MjdjMC45NjktMS45MywwLjk2OS00LjIwMywwLTYuMTM0DQoJCQlsLTUuMjk0LTEwLjUyN2wxMC41MjcsNS4yOThjMS45MjcsMC45NjYsNC4yMDcsMC45NjYsNi4xMzQsMGwxMC41MjMtNS4yOTRsLTUuMjk0LDEwLjUyMw0KCQkJQzIyOS4wNTksMjMzLjA5OSwyMjkuMDU5LDIzNS4zNzIsMjMwLjAyOCwyMzcuMzAyeiIvPg0KCTwvZz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjwvc3ZnPg0K" />
                {{solverData.point}} &nbsp;&nbsp; 
                <img src="data:image/svg+xml;base64,PHN2ZyBpZD0iQ2FwYV8xIiBlbmFibGUtYmFja2dyb3VuZD0ibmV3IDAgMCA1MTIgNTEyIiBoZWlnaHQ9IjUxMiIgdmlld0JveD0iMCAwIDUxMiA1MTIiIHdpZHRoPSI1MTIiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+PGc+PHBhdGggZD0ibTQyMSAzMGMtMTguMDUzIDAtMTA3LjE3MyAwLTEyMi41NyAwLTYuMTkxLTE3LjQ2MS0yMi44NzMtMzAtNDIuNDMtMzAtMTkuNTU2IDAtMzYuMjM5IDEyLjUzOS00Mi40MyAzMC0xNS4zOTUgMC0xMDQuNTM2IDAtMTIyLjU3IDAtOC4yODQgMC0xNSA2LjcxNi0xNSAxNXY0NTJjMCA4LjI4NCA2LjcxNiAxNSAxNSAxNWgzMzBjOC4yODQgMCAxNS02LjcxNiAxNS0xNXYtNDUyYzAtOC4yODQtNi43MTYtMTUtMTUtMTV6bS0xOTUgMzBjOC4yODQgMCAxNS02LjcxNiAxNS0xNSAwLTguMjcxIDYuNzI5LTE1IDE1LTE1czE1IDYuNzI5IDE1IDE1YzAgOC4yODQgNi43MTYgMTUgMTUgMTVoMTV2MTVjMCA1LjI1OC45MTUgMTAuMzA1IDIuNTggMTVoLTk1LjE2YzEuNjY1LTQuNjk1IDIuNTgtOS43NDIgMi41OC0xNXYtMTV6bTE4MCA0MjJoLTMwMHYtNDIyaDc1djE1YzAgOC4yNzEtNi43MjkgMTUtMTUgMTUtOC4yODQgMC0xNSA2LjcxNi0xNSAxNXM2LjcxNiAxNSAxNSAxNWgxODBjOC4yODQgMCAxNS02LjcxNiAxNS0xNXMtNi43MTYtMTUtMTUtMTVjLTguMjcxIDAtMTUtNi43MjktMTUtMTV2LTE1aDc1eiIvPjxwYXRoIGQ9Im0yNzEgMjExYy04LjI4NCAwLTE1IDYuNzE2LTE1IDE1czYuNzE2IDE1IDE1IDE1aDkwYzguMjg0IDAgMTUtNi43MTYgMTUtMTVzLTYuNzE2LTE1LTE1LTE1eiIvPjxwYXRoIGQ9Im0yMTguMzk0IDE2Ny4zOTMtMzcuMzk0IDM3LjM5NC0xMy4zOTQtMTMuMzk0Yy01Ljg1Ny01Ljg1OC0xNS4zNTUtNS44NTgtMjEuMjEzIDBzLTUuODU4IDE1LjM1NSAwIDIxLjIxM2wyNCAyNGMyLjkyOSAyLjkzIDYuNzY4IDQuMzk0IDEwLjYwNyA0LjM5NHM3LjY3OC0xLjQ2NCAxMC42MDYtNC4zOTNsNDgtNDhjNS44NTgtNS44NTggNS44NTgtMTUuMzU1IDAtMjEuMjEzLTUuODU3LTUuODU5LTE1LjM1NS01Ljg1OS0yMS4yMTItLjAwMXoiLz48cGF0aCBkPSJtMzYxIDMwMWgtOTBjLTguMjg0IDAtMTUgNi43MTYtMTUgMTVzNi43MTYgMTUgMTUgMTVoOTBjOC4yODQgMCAxNS02LjcxNiAxNS0xNXMtNi43MTYtMTUtMTUtMTV6Ii8+PHBhdGggZD0ibTIxOC4zOTQgMjU3LjM5My0zNy4zOTQgMzcuMzk0LTEzLjM5NC0xMy4zOTRjLTUuODU3LTUuODU4LTE1LjM1NS01Ljg1OC0yMS4yMTMgMHMtNS44NTggMTUuMzU1IDAgMjEuMjEzbDI0IDI0YzIuOTI5IDIuOTMgNi43NjggNC4zOTQgMTAuNjA3IDQuMzk0czcuNjc4LTEuNDY0IDEwLjYwNi00LjM5M2w0OC00OGM1Ljg1OC01Ljg1OCA1Ljg1OC0xNS4zNTUgMC0yMS4yMTMtNS44NTctNS44NTktMTUuMzU1LTUuODU5LTIxLjIxMi0uMDAxeiIvPjxwYXRoIGQ9Im0zNjEgMzkxaC05MGMtOC4yODQgMC0xNSA2LjcxNi0xNSAxNXM2LjcxNiAxNSAxNSAxNWg5MGM4LjI4NCAwIDE1LTYuNzE2IDE1LTE1cy02LjcxNi0xNS0xNS0xNXoiLz48cGF0aCBkPSJtMjE4LjM5NCAzNDcuMzkzLTM3LjM5NCAzNy4zOTQtMTMuMzk0LTEzLjM5M2MtNS44NTctNS44NTgtMTUuMzU1LTUuODU4LTIxLjIxMyAwcy01Ljg1OCAxNS4zNTUgMCAyMS4yMTNsMjQgMjRjMi45MjkgMi45MjkgNi43NjggNC4zOTMgMTAuNjA3IDQuMzkzczcuNjc4LTEuNDY0IDEwLjYwNi00LjM5NGw0OC00OGM1Ljg1OC01Ljg1OCA1Ljg1OC0xNS4zNTUgMC0yMS4yMTMtNS44NTctNS44NTgtMTUuMzU1LTUuODU4LTIxLjIxMiAweiIvPjwvZz48L3N2Zz4=" />
                {{isNaN(solverData.evaluationScore) ? 0 : parseFloat(solverData.evaluationScore).toFixed(2)}} <span>/10</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import API from "@/API.js";
import axios from "axios";

export default {
  name: 'Solvers',
  data(){
    return {
      sortOptions: [
        {text:'팔로워 순', value:"follwerDesc"},
        {text:'베리 평점 순', value:"pointDesc"},
        {text:'베리 포인트 순', value:"evaluationDesc"},
      ],
      Category: [],
      subCategory: [],
      // 검색 Params
      sortValue: "",
      query: "",
      mainCategoryCode: "",
      subCategoryCode: "",
      // 실질 List
      solverList: []
    }
  }, methods:{ 
    setQuery(){
      this.setSolverList();
    }, selectSort(event){
      this.sortValue = event.target.value;
      this.setSolverList();
    }, setSubCategory(event) { 
      this.subCategoryCode = "";
      this.mainCategoryCode = event.target.value;
      document.getElementById("sub-select").value="";
      this.Category.forEach(element => {
        if(element.code == event.target.value){
          this.subCategory = element.category;
        }
      });
      this.setSolverList();
    }, setResultCategory(event){
      this.subCategoryCode = event.target.value;
      console.log("Sub : " + this.subCategoryCode);
      this.setSolverList();
    }, setSolverList(){
      axios({
        url: API.URL + API.ROUTES.getSolverList,
        method: "get",
        params: {
          mainCategory: this.mainCategoryCode,
          subCategory: this.subCategoryCode,
          query: this.query,
          mode: this.sortValue
        }
      })
      .then((res)=>{
        this.solverList = [];
        var tempList = [];
        var n = 4; // 행에 몇개를 보일 것인지 확인
        for (var i in res.data.list){
          if (i % n == 0)
            tempList = [];
          tempList.push(res.data.list[i]);
          if (i % n == n-1)
            this.solverList.push(tempList);
        }

        if(tempList.length < n)
          this.solverList.push(tempList);
      })
      .catch(()=>{
        console.log();
      });
    }
  },
  created() {
    axios({
      url: API.URL + API.ROUTES.getCategory,
      method: "get",
    })
    .then((res) => {
      this.Category = res.data;
    })
    .catch(() => {
      console.log();
    });
    this.setSolverList();
  },
}
</script>

<style>
  .detail-text {
    text-align: left;
  }

  .detail-text > h5 {
    margin-top:5px;
  }

  .form-select{
    width: 280px;
    margin: 0px 5px 0px 0px;
  }

  img.user-image {
    width:110px;
    height:110px;
  }

  img.user-level-badge{
    position: absolute;
    width: 50px;
    left:-5px;
    top:-5px;
  }

  .search-col{
    width: 290px;
    padding: 0px 3px;
  }

  select{
    cursor: pointer;
  }

  .solver-form-field {
    display: block;
    width: 100%;
    height: 38px;
    padding: 8px 16px;
    line-height: 25px;
    font-size: 14px;
    font-weight: 500;
    font-family: inherit;
    border-radius: 6px;
    -webkit-appearance: none;
    color: #84898C;
    border: 1px solid #CDD9ED;
    background: #fff;
    transition: border 0.3s ease;
  }

  .solver-form-field::-moz-placeholder {
    color: #CBD1DC;
  }

  .solver-form-field:-ms-input-placeholder {
    color: #CBD1DC;
  }

  .solver-form-field::placeholder {
    color: #CBD1DC;
  }

  .solver-form-field:focus {
    outline: none;
    border-color: #658DC6;
  }

  .solver-form-group {
    display: flex;
    float: left;
  }

  .solver-form-group > span,
  .solver-form-group .solver-form-field {
    white-space: nowrap;
    display: block;
  }

  .solver-form-group > span:not(:first-child):not(:last-child),
  .solver-form-group .solver-form-field:not(:first-child):not(:last-child) {
    border-radius: 0;
  }

  .solver-form-group > span:first-child,
  .solver-form-group .solver-form-field:first-child {
    border-radius: 6px 0 0 6px;
  }

  .solver-form-group > span:last-child,
  .solver-form-group .solver-form-field:last-child {
    border-radius: 0 6px 6px 0;
  }

  .solver-form-group > span:not(:first-child),
  .solver-form-group .solver-form-field:not(:first-child) {
    margin-left: -1px;
  }

  .solver-form-group .solver-form-field {
    position: relative;
    z-index: 1;
    flex: 1 1 auto;
    width: 1%;
    margin-top: 0;
    margin-bottom: 0;
  }

  .solver-form-group > span {
    text-align: center;
    padding: 5px 12px;
    font-size: 14px;
    line-height: 25px;
    width: 80px;
    height: 38px;
    color: #99A3BA;
    background: #EEF4FF;
    border: 1px solid #CDD9ED;
    transition: background 0.3s ease, border 0.3s ease, color 0.3s ease;
  }

  .solver-form-group:focus-within > span {
    color: #fff;
    background: #678EFE;
    border-color: #658DC6;
  }

  .solver-banner {
    align-items: center;
    display: flex;
  }

  .solver-banner img {
    height: 100px;
  }

  .solver-banner-ment {
    height: 40px;
    margin-left: 40px;
    text-align: left;
  }

  .solver-body {
    display: flex;
    justify-content: center;
    margin: 15px 0px 0px 0px;
  }

  .solver-list {
    display: flex;
    justify-content: center;
    margin: 15px 0px 0px 0px;
  }

  .solver-body-contents {
    display: flex;
    width: 1190px;
  }

  .solver-header {
    background-color: #658DC6;
    display: flex;
    height: 180px;
    justify-content: center;
  }

  .solver-select{
    margin-left: 5px;
  }

  .solver-user-card {
    width: 270px;
    height: 210px;
    margin: 0px 10px 0px 10px;
    /* background-color: #658DC6; */
    border: 1px solid #b5c7d3;
    position: relative;
  }

  .solver-user-card:hover {
    border: 1px solid white;
    background-color: #f3f3f3;
    transition: color 0.2s;
    cursor: pointer;
  }

  .solver-user-col1 {
    width: 110px;
    height: 110px;
    margin: 10px 0px 10px 12px;
    float: left;
    /* background-color: #84898C; */
  }

  .solver-user-col2 {
    width: 120px;
    height: 110px;
    margin: 10px 0px 10px 12px;
    float: left;
    /* background-color: #84898C; */
  }

  .solver-user-row{
    width: 240px;
    height: 20px;
    margin: 7px 12px;
    float: left;
    /* background-color: #84898C; */
  }

  .solver-user-row > img {
    width: 15px;
  }

  .solver-user-row > span {
    color: #cccccc;
  }

  span.detail-category{
    border-radius: 10px;
    padding-left: 2px;
    padding-right: 2px;
    margin-bottom: 2px;
    margin-right: 3px;
    background-color: #dfdfdf;
    display: inline-block;
    font-size:13px;
  }
</style>