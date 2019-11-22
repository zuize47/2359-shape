const ShapeTemplate = `
    <div class="container">
        <h2 class="text-center">Shape</h2>
        <form action="#" class="form-horizontal " ref="form">
            <div class="form-group">
                <label for="category" class="control-label col-sm-2">Category:</label>
                <div class="col-sm-4">
                    <select v-model="categoryId" @change="onCategoryChange($event)" required="required" class="form-control" id="category">
                    <option value="" selected disabled>Choose a Category</option>
                    <option  v-for="cat in categories" :value="cat.id">{{ cat.name }}</option>
                </select>
                </div>
                    
            </div>
            <div v-for="(value, name) in attributes" class="form-group" :key="name">
                <label v-bind:for="name" class="control-label col-sm-2">{{ name }}:</label>
                <div class="col-sm-3">
                    <input type="text" required class="form-control" v-model.number="attributes[name]" v-bind:id="name"  @keypress="onlyNumber" />
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2">
                    <button type="button" @click="shapeSave" class="btn btn-primary">Save</button>    
                </div>
                 <div class="col-sm-2">
                    <button type="button" @click="shapeCalcArea" class="btn btn-success">Calc Area</button>    
                </div>
                <div class="col-sm-2">
                    <router-link :to="{ path: '/'}"> Cancel </router-link>
                </div>
            </div>
        </form>
            
    </div>`

export { ShapeTemplate }