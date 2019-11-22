import { CategoryTemplate } from "../templates/category-template.js";
import { ShapeService } from "../js/shape-service.js"

function createCategory() {
    return {
        id: 0,
        name: '',
        areaFormula: '',
        attributes: [""]
    }
}

const Category = {
    template: CategoryTemplate,
    data(){
        return {
            category: createCategory(),
            attributesData: [""],
        }
    },
    methods:{
        addNewRow: function(){
            this.attributesData.push("")
        },
        deleteRow: function(index){
            this.attributesData.splice(index, 1);
        },
        categorySave: function(){
            if(this.$refs['form'].reportValidity()){
                this.category.attributes = this.attributesData
                ShapeService.saveCategory(this.category).then(response=>{
                    // move to this page with id return
                    if(this.category.id != response.data){
                        this.category.id = response.data
                        this.$router.push({ path: "/category/" + response.data})
                        return
                    }

                }).catch( () =>{
                    alert("error when save")
                });
            }
        },
        onlyNumber: function ($event) {
            let keyCode = ($event.keyCode ? $event.keyCode : $event.which);
            if ((keyCode < 48 || keyCode > 57) && keyCode !== 46) { // 46 is dot
                $event.preventDefault();
            }
        }

    },
    computed: {
        categories(){
            return this.categoriesData
        },
        attributes(){
            return this.attributesData;
        }
    },
    mounted(){
        if(this.$route.params.id && this.$route.params.id != '0'){
            ShapeService.getCategory(this.$route.params.id).then(response =>{
                this.category = response.data;
                this.attributesData = this.category.attributes
            }).catch(() => {
                this.$router.push({ path: "/category/0"})
            })

        }


    }
}

export { Category }