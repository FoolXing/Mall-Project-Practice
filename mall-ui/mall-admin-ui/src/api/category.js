import service from '@/utils/request.js'
// 将所有的针对category的请求，都封装到一个对象中
const categoryApi = {
    list(categoryQuery) {
        return service.get('/category/list', {params: categoryQuery})
    },
    deleteById(id) {
        return service.delete( `/category/deleteById/${id}`)
    },
    deleteAll(ids) {
        return service.delete( `/category/deleteAll/${ids}`)
    },
    add(category) {
        //category以JSON形式传递到后台，后端使用@RequestBody接收
        return service.post('/category/add', category)
    },
    selectById(id) {
        return service.get(`/category/selectById/${id}`)
    },
    update(category) {
        return service.put('/category/update', category)
    },
    selectTopCategoryList() {
        return service.get('/category/selectTopCategoryList')
    },
    selectSecondCategoryListByParentId(parentId) {
        return service.get(`/category/selectSecondCategoryListByParentId/${parentId}`)
    }
}

export default categoryApi