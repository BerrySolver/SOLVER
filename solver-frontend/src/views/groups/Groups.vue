<template>
  <div>
    <h1>모임</h1>
    <!-- 추후 작업할 땐 이미지 삭제 -->
    <img src="https://user-images.githubusercontent.com/45550607/128437612-2e4b97aa-ebaa-4e49-8dd0-7a6c72954403.png">
<!--     
      <GroupVideo/>
      <GroupQuestion/> -->
  </div>
</template>

<script>
import GroupVideo from "@/components/groups/GroupVideo"
import GroupQuestion from "@/components/groups/GroupQuestion"

export default {
    name: 'Groups',
    components: {
        GroupVideo,
        GroupQuestion,
    },
}
</script>


<style>

</style>