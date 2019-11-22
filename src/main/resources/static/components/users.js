import { UsersTemplate } from "../templates/users-template.js";
import { UserService } from "../js/user-service.js"

const Users = {
    template: UsersTemplate,
    data(){
        return{
            usersData: []
        }
    },
    computed: {
        users(){
            return this.usersData
        }
    },
    methods: {
        deleteUser: function (username) {
            UserService.deleteUser(username).then(response =>{
                this.$parent.$router.go()
            })
        }
    },
    mounted() {
        return UserService.allUser().then(response =>{
            this.usersData = response.data
        })
    }
}

export { Users }