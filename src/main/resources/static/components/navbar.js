import { NavbarTemplate } from '../templates/navbar-template.js'
import { AuthService } from "../js/auth-service.js";

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
    this.$on("user_login", function (data) {
      this.isAuthenticated = true
      this.isAdmin = AuthService.isAdmin()
    })
  }
}

export { Navbar }