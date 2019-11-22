import { UserTemplate } from "../templates/user-template.js";
import { AuthService } from '../js/auth-service.js'
import { UserService } from  '../js/user-service.js'

function createShape() {
    return {
        username: '',
        password: '',
        firstName: '',
        lastName: '',
        email: '',
        authorities: []
    }
}

const User = {
    template: UserTemplate,
    data(){
        return{
            userData: createShape(),
        }

    },
    methods: {
        clickRegister: function (event) {

            if(!this.user.authorities){
                this.authorities = ['ROLE_USER']
            }
            UserService.register(this.user).then( (e, t) => {
                if("/register" === this.$route.path){
                    this.$router.push({path: '/login'})
                    return
                }else if(this.$route.path.startsWith("/user/")){
                    alert("userSaveOk")
                    this.$router.go()
                }

            }).catch((er) =>{
                console.log(er)
                alert("save user error")
            })

        }
    },
    computed: {
        user(){
            return this.userData
        },
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
    mounted(){
        if(this.$route.params.username){
            UserService.getUser(this.$route.params.username).then(response =>{
                this.userData = response.data;

            }).catch(() =>{
                alert("User not found")
                this.$router.push({path: '/user'})
            })
        }
    }
}

export { User }