const ACCESS_TOKEN ='accessToken'
const AuthService = {
    username: '',
    password: '',
    authenticated: false,
    token: null,
    bearerToken(){
        return {
            headers: {'Authorization': "Bearer " + sessionStorage.getItem("accessToken")}
        };
    }

}

AuthService.login = function(username, password){
    return axios.post('/api/auth/login',{
        username: username,
        password: password
    }).then(response => {
        AuthService.username = username
        AuthService.password = password
        AuthService.token = response.data
        AuthService.authenticated = true
        sessionStorage.setItem(ACCESS_TOKEN, AuthService.token.accessToken)
    })
}
AuthService.isAuthenticated = function () {
    return sessionStorage.getItem("accessToken") != null

}
AuthService.logout = function () {
    sessionStorage.removeItem(ACCESS_TOKEN)
}

AuthService.userInfo = function() {
    if(sessionStorage.getItem(ACCESS_TOKEN)){
        let base64Url = sessionStorage.getItem("accessToken").split('.')[1];
        let base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        let jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));
        let userInfo = JSON.parse(jsonPayload);
        userInfo.auth = userInfo.auth ? userInfo.auth.split(',') : []
        return userInfo;
    }
    return {
        auth: []
    }

};
AuthService.isAdmin = function(){
    return this.userInfo().auth.includes("ROLE_ADMIN")
}

export { AuthService }
