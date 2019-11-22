import {AuthService} from "./auth-service.js";
import {ShapeService} from "./shape-service.js";

const UserService = {
}

UserService.getUser = function (username) {
    return axios.get('api/user/' + username, AuthService.bearerToken())
}
UserService.allUser = function () {
    return axios.get('api/user/all', AuthService.bearerToken())
}
UserService.register = function (user) {
    return axios.put('api/user/register', user, AuthService.bearerToken())
}
UserService.deleteUser = function (username) {
    return axios.delete('/api/user/' + username, AuthService.bearerToken())
}


export { UserService
}