import { ShapesTemplate } from "../templates/shapes-template.js";
import { ShapeService } from "../js/shape-service.js"
import { AuthService } from '../js/auth-service.js'
const Shapes = {
    template: ShapesTemplate,
    data(){
        return{
            shapesData: []
        }
    },
    computed: {
        shapes(){
            return this.shapesData
        }
    },
    methods: {
        shapeDelete: function (id) {
            ShapeService.deleteShape(id).then(response =>{
                this.$parent.$router.go()
            })
        }
    },
    mounted() {
        if(AuthService.isAuthenticated()){
            return ShapeService.allShape().then(response =>{
                this.shapesData = response.data
            })
        }else {
            this.$router.push({path: '/login'})
        }

    }
}

export { Shapes }