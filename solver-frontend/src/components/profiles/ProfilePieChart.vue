<script>
import { Doughnut } from 'vue-chartjs'

export default {
  extends: Doughnut,
  name: "ProfilePieChart",
  props: ["dataset", "options"],
  created() {
    console.log(this.dataset)
  },
  mounted() {
    this.renderChart(this.dataset, this.options)
  }

}
</script>