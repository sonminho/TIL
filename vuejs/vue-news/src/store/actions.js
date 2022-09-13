import { fetchNewsList, 
         fetchJobsList, 
         fetchAskList,
         fetchUserInfo,
         fetchCommentItem,
         fetchList } from '../api/index.js'

export default {
    // FETCH_NEWS(context) {
    //     return fetchNewsList()
    //         .then(response => {
    //             context.commit('SET_NEWS', response.data)
    //             return response
    //         })
    //         .catch(function(error) {
    //             console.log(error)
    //         })
    // },

    // async
    async FETCH_NEWS(context) {
        const response = await fetchNewsList()
        context.commit('SET_NEWS', response.data)
        return response
    },
    async FETCH_JOBS( context ) {
        const response = await fetchJobsList()
        context.commit('SET_JOBS', response.data)
        return response
    },
    async FETCH_ASK( context ) {
        try {
            const response = await fetchAskList()
            context.commit('SET_ASK', data)
            return response
        } catch(error) {
            console.log(error)
        }
    },
    FETCH_USER( {commit}, name ) {
        return fetchUserInfo(name)
            .then( ({data}) => {
                commit('SET_USER', data)
            })
            .catch(function(error) {
                console.log(error)
            })
    },
    FETCH_ITEM( {commit}, id ) {
        return fetchCommentItem(id)
            .then(({data}) => {
                commit("SET_ITEM", data)
            })
            .catch(error => {
                console.log(error)
            })
    },
    async FETCH_LIST( {commit}, pageName ) {
        const response = await fetchList()
        commit('SET_LIST', response.data)
        return response
    },
}