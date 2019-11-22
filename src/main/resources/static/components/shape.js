import { ShapeTemplate } from "../templates/shape-template.js";
import { ShapeService } from "../js/shape-service.js"
// this component used to add and edit a shape

function convert(category) {
    let data = {}
    let attrs = category.attributes;
    for (let i = 0; i < attrs.length; i++){
        data[attrs[i]] = ''
    }
    return data
}
function createShape() {
    return {
        id: 0,
        catId: 0,
        attributes: {}
    }
}

const Shape = {
    template: ShapeTemplate,
    data(){
        return {
            shape: createShape(),
            categoriesData: [],
            categoryId: undefined,
            attributesData: undefined,
        }
    },
    methods:{
        shapeCalcArea: function(){
            if(this.$refs['form'].reportValidity()){
                console.log(this.shape)
                this.shape.catId = this.categoryId;
                this.shape.attributes = this.attributesData
                ShapeService.calcArea(this.shape).then(response=>{
                    // move to this page with id return
                    alert("Area:" + response.data)
                }).catch(() =>{
                    alert("error")
                });
            }
        },
        shapeSave: function () {
            if(this.$refs['form'].reportValidity()){
                console.log(this.shape)
                this.shape.catId = this.categoryId;
                this.shape.attributes = this.attributesData
                ShapeService.saveShape(this.shape).then(response=>{
                    // move to this page with id return

                    if(this.shape.id != response.data){
                        this.$router.push({ path: "/shape/" + response.data})
                        return
                    }

                });
            }

        },
        onCategoryChange: function(event){
            if(this.categoriesData){
                let categoryData = this.categoriesData.filter(e => e.id ==event.target.value)[0];
                this.attributesData = convert(categoryData)

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
        // load category
        ShapeService.allCategories().then(response => {
            this.categoriesData = response.data
        });
        if(this.$route.params.id && this.$route.params.id != '0'){
            ShapeService.getShape(this.$route.params.id).then(response =>{
                this.shape = response.data;
                this.categoryId = this.shape.catId
                this.attributesData = this.shape.attributes
            })

        }


    }
}

export { Shape }