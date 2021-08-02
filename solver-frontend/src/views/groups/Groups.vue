<template>
  <div>
    <h1>모임</h1>
      <GroupVideo/>
      <GroupQuestion/>
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