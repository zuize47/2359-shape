import { LoginTemplate } from "../templates/login-template.js";
import { AuthService } from '../js/auth-service.js'
import { publish } from '../js/eventBus.js'
const Login = {
    template: LoginTemplate,
    data(){
        return{
            username: '',
            password: ''
        }

    },
    methods: {
        clickLogin: function (event) {
            AuthService.login(this.username, this.password).then( (e, t) => {

                publish("user_login", "ok")
                this.$parent.$router.push({ path: '/' })
            }).catch(e => {
                alert("Cant not login")
            })

        }
    },
}

export { Login }