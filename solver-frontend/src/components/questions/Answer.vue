<template>
  <div>
    <div class="answer">

    </div>
    <Comments/>
    <CommentsCreate/>
  </div>
</template>

<script>
import Comments from "@/components/questions/Comments"
import CommentsCreate from "@/components/questions/CommentsCreate"


export default {
  name: 'Answer',
  components: {
    Comments,
    CommentsCreate,
  },
  data() {
    return {
      
    }
  }
}
</script>

<style>
  .answer {
    height: 200px;
  }
</style>
