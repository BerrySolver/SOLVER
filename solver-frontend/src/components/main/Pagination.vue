<template>
  <nav aria-label="Page navigation">
    121221
    <ul class="pagination justify-content-center">
      <li v-if="prev" class="page-item">
        <a
          class="page-link"
          href="#"
          aria-label="Previous"
          @click="paginationChanged(startPageIndex - 1)"
        >
          <span aria-hidden="true">«</span>
        </a>
      </li>
      <li
        v-for="index in endPageIndex - startPageIndex + 1"
        :key="index"
        v-bind:class="{
          active: startPageIndex + index - 1 == pageIndex(),
        }"
        class="page-item"
      >
        <a @click="paginationChanged(startPageIndex + index - 1)" class="page-link" href="#">{{
          startPageIndex + index - 1
        }}</a>
        <!-- href 는 그대로, 커서 모양 유지-->
      </li>
      <li v-if="next" class="page-item">
        <a
          class="page-link"
          href="#"
          aria-label="Next"
          @click="paginationChanged(endPageIndex + 1)"
        >
          <span aria-hidden="true">»</span>
        </a>
      </li>
    </ul>
  </nav>
</template>

<script>
import pageNationStore from "@/store/modules/pagenation.js";

export default {
  name: "Pagination",
  // props 사용 X
  // props: ['listRowCount', 'pageLinkCount', 'currentPageIndex', 'totalListItemCount'],
  data() {
    return {
      index: 1,
    };
  },
  computed: {
    pageCount: function() {
      return Math.ceil(
        pageNationStore.state.totalListItemCount / pageNationStore.state.listRowCount
      );
    },
    startPageIndex: function() {
      console.log("!!!!@!@!@!@");
      if (pageNationStore.state.currentPageIndex % pageNationStore.state.pageLinkCount == 0) {
        //10, 20...맨마지막
        return (
          (pageNationStore.state.currentPageIndex / pageNationStore.state.pageLinkCount - 1) *
            pageNationStore.state.pageLinkCount +
          1
        );
      } else {
        return (
          Math.floor(pageNationStore.state.currentPageIndex / pageNationStore.state.pageLinkCount) *
            pageNationStore.state.pageLinkCount +
          1
        );
      }
    },
    endPageIndex: function() {
      // 아래 코드는 Error in render: "RangeError: Maximum call stack size exceeded" 발생, this.endPageIndex 계산 할 때, 자기 자신 값을 단순 비교만하더라도 에러 발생
      // if( this.endPageIndex < this.pageCount ) return this.pageCount;

      let ret = 0;
      if (pageNationStore.state.currentPageIndex % pageNationStore.state.pageLinkCount == 0) {
        //10, 20...맨마지막
        ret =
          (pageNationStore.state.currentPageIndex / pageNationStore.state.pageLinkCount - 1) *
            pageNationStore.state.pageLinkCount +
          pageNationStore.state.pageLinkCount;
      } else {
        ret =
          Math.floor(pageNationStore.state.currentPageIndex / pageNationStore.state.pageLinkCount) *
            pageNationStore.state.pageLinkCount +
          pageNationStore.state.pageLinkCount;
      }

      // alert(
      //   "currentPageIndx: " +
      //     this.$store.state.notice.currentPageIndex +
      //     "\n pageLinkCount: " +
      //     this.$store.state.notice.pageLinkCount +
      //     "\n pageCount: " +
      //     this.pageCount +
      //     "\n totalListItemCount: " +
      //     this.$store.state.notice.totalListItemCount +
      //     "\n listRowCount: " +
      //     this.$store.state.notice.listRowCount
      // );
      // 위 오류나는 코드를 아래와 같이 비교해서 처리
      return ret > this.pageCount ? this.pageCount : ret;
    },
    prev: function() {
      if (pageNationStore.state.currentPageIndex <= pageNationStore.state.pageLinkCount) {
        return false;
      } else {
        return true;
      }
    },
    next: function() {
      if (this.endPageIndex >= this.pageCount) {
        return false;
      } else {
        return true;
      }
    },
  },
  methods: {
    // 부모에게 event 전달
    paginationChanged(pageIndex) {
      console.log("paginationVue : paginationChanged : pageIndex : " + pageIndex);
      this.$emit("call-parent", pageIndex);
    },
    pageIndex() {
      return pageNationStore.state.currentPageIndex;
    },
  },
};
</script>

<style scoped>
li a {
  border-style: solid;
  color: #7b27d8;
}
li a:hover {
  border-style: solid;
  background: #cba3f8;
  color: #fff;
}
li {
  border-style: none;
}
li.active a {
  background: linear-gradient(20deg, #5846f9 0%, #7b27d8 100%);
  border-color: 5846f9;
  color: #fff;
}
</style>
