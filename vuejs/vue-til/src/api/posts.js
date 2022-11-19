// 학습노트 조작과 관련된 CRUD API 함수 파일
import { posts } from './index';

// 학습 노트 데이터를 조회 API
function fetchPosts() {
    return posts.get('/');
}

// 학습 노트 데이터 생성 API
function createPost(postData) {
    return posts.post('/', postData);
}

// 학습 노트 데이터 삭제하는 API
function deletePosts(postId) {
    return posts.delete(postId);
}

// 학습 노트를 조회하는 API
function fetchPost(postId) {
    return posts.get(postId);
}

// 학습 노트 데이터를 수정하는 API
function editPost(postId, postData) {
    return posts.put(postId, postData);
}

export {
    fetchPosts,
    createPost,
    deletePosts,
    fetchPost,
    editPost
};