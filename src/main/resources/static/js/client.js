// import Vue from 'https://cdn.jsdelivr.net/npm/vue@latest/dist/vue.esm.browser.min.js'
Object.defineProperties(Array.prototype, {
    count: {
        value: function (value) {
            return this.filter(x => x == value).length;
        }
    }
});

import {
    Navbar
} from '../components/navbar.js'

import {
    MainTemplate
} from './templates/main-template.js'

import {About} from '../components/about.js'
import {Login} from '../components/login.js'
import {User} from '../components/user.js'
import {Users} from '../components/users.js'
import {Shapes} from '../components/shapes.js'
import {Shape} from '../components/shape.js'
import {Categories} from '../components/categories.js'
import {Category} from '../components/category.js'
import {AuthService} from './auth-service.js';
import { publish } from './eventBus.js'

function guard(to, from, next) {
    if(to.path == '/login' || to.path == '/register'){
        next()
        return
    }else if (!AuthService.isAuthenticated()) next('/login')
    else next()
}

Vue.use(VueRouter)

const router = new VueRouter({
    routes: [
        {
            path: '/',
            component: Shapes,
            name: "Shapes Page"
        },
        {
            path: '/shape/:id',
            component: Shape,
            name: "Shape Page"
        },
        {
            path: '/category/:id',
            component: Category,
            name: "Category Page"
        },
        {
            path: '/about',
            component: About,
            name: "About Us Page"
        },
        {
            path: '/login',
            component: Login,
            name: "Login Page"
        },
        {
            path: '/user/:username',
            component: User,
            name: "User Page"
        },
        {
            path: '/register/',
            component: User,
            name: "Register Page"
        },
        {
            path: '/categories',
            component: Categories,
            name: "Categories Page"
        },
        {
            path: '/users',
            component: Users,
            name: "Users Page"
        }
    ]
})


Vue.config.devtools = true

router.beforeEach(guard)


new Vue({
    el: '#app', // This should be the same as your <div id=""> from earlier.
    data: {
        authenticated: false,
    },

    computed: {
        isAuthenticated() {
            return this.authenticated
        }
    },
    methods: {

    },
    components: {
        'navbar': Navbar
    },
    router,
    template: MainTemplate,
    mounted() {
        if(AuthService.isAuthenticated()){
            publish("user_login", "ok")
        }
    }
})
