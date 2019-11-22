import { ShapesTemplate } from "../templates/shapes-template.js";
import { ShapeService } from "../js/shape-service.js"

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
        return ShapeService.allShape().then(response =>{
            this.shapesData = response.data
        })
    }
}

export { Shapes }