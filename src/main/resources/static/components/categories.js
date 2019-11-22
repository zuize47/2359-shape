import { CategoriesTemplate } from "../templates/categories-template.js";
import { ShapeService } from "../js/shape-service.js"

const Categories = {
    data(){
        return{
            categoriesData: []
        }

    },
    methods: {
        deleteCategory:function(id){
            ShapeService.deleteCategory(id).then(response =>{
                this.$router.go()
            })
        }
    },
    template: CategoriesTemplate,
    computed: {
        categories(){
            return this.categoriesData;
        }
    },
    mounted() {
        ShapeService.allCategories().then(response => {
            this.categoriesData = response.data
        });
    }
}

export { Categories }