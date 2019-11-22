import { RegisterTemplate } from "../templates/register-template.js";
import { AuthService } from '../js/auth-service.js'

const Register = {
    template: RegisterTemplate,
    data(){
        return{
            username: '',
            password: '',
            firstName: '',
            lastName: '',
            email: '',
            authorities: []
        }

    },
    methods: {
        clickRegister: function (event) {


            if(!this.authorities){
                this.authorities = ['ROLE_USER']
            }
            AuthService.register({
                username: this.username,
                password: this.password,
                firstName: this.firstName,
                lastName: this.lastName,
                email: this.email,
                authorities: this.authorities

            }).then( (e, t) => {
                //     this.$parent.$router.push({ path: '/' })
                // }).catch(e => {
                //     alert("Cant not login")
                // })

            })

        }
    },
    computed: {
        roles() {
            let r = []
            if(AuthService.isAuthenticated()){
                if(AuthService.userInfo().auth.includes("ROLE_ADMIN")){
                    r.push("ROLE_ADMIN")
                    r.push("ROLE_USER")
                }
            }
            return r
        }
    },
}

export { Register }