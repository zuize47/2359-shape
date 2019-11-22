const ShapesTemplate = `
    
    
    <div class="table-responsive">
    <a href="#/shape/0">Add new Shape</a>
    <br/>
    <table class="table">
        <thead>
            <tr>
                <th>Name</th>
                <th>Data</th>
                <th>Delete</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="shape in shapes">
                <td><router-link :to="{ path: '/shape/' + shape.id }"> {{ shape.name }} </router-link></td>
                <td>
                    <ul>
                        <li v-for="(v, n) in shape.attributes" >{{ n }} = {{ v }} </li>
                    </ul>
                </td>
                <td>
                     <button v-on:click="shapeDelete(shape.id)">Delete</button>
                </td>
            </tr>
        </tbody>
        
    </table>
</div>`

export { ShapesTemplate }