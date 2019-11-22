import { NavbarTemplate } from '../templates/navbar-template.js'
import { AuthService } from "../js/auth-service.js";
import { subscribe } from '../js/eventBus.js'
var eventBus = {}
const Navbar = {
  data(){
    return {
      isAdmin: false,
      isAuthenticated: false
    }
  },
  template: NavbarTemplate
  ,
  methods: {
    clickLogout: function (event) {
      AuthService.logout()
      this.$parent.$router.go()
    }
  },
  computed: {
  },
  mounted(){
    eventBus = subscribe("user_login", data => {
      this.isAuthenticated = true
      this.isAdmin = AuthService.isAdmin()
    })
  },
  beforeDistroy(){
    console.log("beforeDistroy")
    eventBus.unsubscribe()
  }
}

export { Navbar }