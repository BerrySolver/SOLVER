<template>
  <div></div>
</template>

<script>
import {mapActions} from 'vuex'

export default {
    name: 'Logout',
    methods: {
      ...mapActions([
        'logout',
      ]),
    },
    created() {
        console.log("out")
        this.logout();
    },
}
// export default {
//     name: 'Logout',
//     data() {
        
//     }
// }
</script>

<style>

</style>