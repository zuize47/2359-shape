const CategoryTemplate = `
    <div class="container">
        <h2 class="text-center">Shape</h2>
        <form action="#" class="form-horizontal " ref="form">
            <div class="form-group">
                <label for="category" class="control-label col-sm-2">Name</label>
                <div class="col-sm-4">
                    <input type="text" required class="form-control" v-model.number="category.name" id="name"/>
                </div>
                    
            </div>
            <div class="form-group">
                <label for="areaFormula" class="control-label col-sm-2">Area Formula</label>
                <div class="col-sm-8">
                    <input type="text" required class="form-control" v-model.number="category.areaFormula" id="areaFormula"/>
                </div>
                    
            </div>
            <div v-for="(value, index) in attributes" class="form-group" :key="index">
                <label v-bind:for="index" class="control-label col-sm-2">{{ index + 1 }}:</label>
                <div class="col-sm-3">
                    <input type="text" required class="form-control" v-model.number="attributes[index]" v-bind:id="index"  />
                </div>
                <div class="col-sm-2">
                    <button type="button" @click="deleteRow(index)" class="btn">Delete</button>    
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2">
                    <button type="button" @click="categorySave" class="btn btn-primary">Save</button>    
                </div>
                <div class="col-sm-2">
                    <button type="button" @click="addNewRow" class="btn">Add Attribute</button>    
                </div>
                <div class="col-sm-2">
                    <router-link :to="{ path: '/categories'}" class="btn"> Cancel </router-link>
                </div>
            </div>
        </form>
            
    </div>`

export { CategoryTemplate }