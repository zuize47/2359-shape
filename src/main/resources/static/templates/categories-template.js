const CategoriesTemplate = `
    <div class="table-responsive">
    <a href="#/category/0">Add new Category</a>
    <br/>
    <table class="table">
        <thead>
            <tr>
                <th>Shape Categories Name</th>
                <th>Formula</th>
                <th>Delete</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="cat in categories">
                <td><router-link :to="{ path: '/category/' + cat.id }"> {{ cat.name }} </router-link></td>
                <td>{{ cat.areaFormula }} </td>
                <td>
                     <button v-on:click="deleteCategory(cat.id)" class="btn btn-danger">Delete</button>
                </td>
            </tr>
        </tbody>
        
    </table>
</div>`

export { CategoriesTemplate }