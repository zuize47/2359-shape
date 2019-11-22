import {AuthService} from "./auth-service.js";

const ShapeService = {

}

ShapeService.allShape = function () {
    return axios.get('/api/shape/all', AuthService.bearerToken())
}

ShapeService.saveShape = function (shape) {
    return axios.put('/api/shape/save', shape, AuthService.bearerToken())
}
ShapeService.getShape = function (id) {
    return axios.get('/api/shape/' + id, AuthService.bearerToken())
}
ShapeService.calcArea = function (shape) {
    return axios.post('/api/shape/calc', shape, AuthService.bearerToken())
}
ShapeService.deleteShape = function (id) {
    return axios.delete('/api/shape/' + id, AuthService.bearerToken())
}

ShapeService.allCategories = function () {
    return axios.get('/api/shape/categories', AuthService.bearerToken())
}
ShapeService.getCategory = function (id) {
    return axios.get('/api/shape/category/' + id, AuthService.bearerToken())
}
ShapeService.deleteCategory = function (id) {
    return axios.delete('/api/shape/category/' + id, AuthService.bearerToken())
}
ShapeService.saveCategory= function (category) {
    return axios.post('/api/shape/category/save', category, AuthService.bearerToken())
}


export { ShapeService }