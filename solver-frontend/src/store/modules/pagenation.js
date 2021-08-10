const state = {
  listRowCount: 10,
  pageLinkCount: 10,
  currentPageIndex: 1,

  totalListItemCount: 0,
};

const mutations = {
  SET_TOTAL_LIST_ITEM_COUNT: (state, totalListItemCount) => {
    state.totalListItemCount = totalListItemCount;
  },
  SET_MOVE_PAGE(state, pageIndex) {
    state.currentPageIndex = pageIndex;
  },
};

const actions = {
  setTotalListItemCount({ commit }, totalListItemCount) {
    commit("SET_TOTAL_LIST_ITEM_COUNT", totalListItemCount);
  },
  setCurrectPage({ commit }, pageIndex) {
    commit("SET_MOVE_PAGE", pageIndex);
  },
};

const getters = {
  getCurrentPageIndex: (state) => state.currentPageIndex,
};

export default {
  state,
  getters,
  mutations,
  actions,
};
