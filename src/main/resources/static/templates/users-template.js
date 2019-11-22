const UsersTemplate = `
    <div class="table-responsive">
    <a href="#/register">Add new User</a>
    <br/>
    <table class="table">
        <thead>
            <tr>
                <th>User Name</th>
                <th>Name</th>
                <th>Email</th>
                <th>Role</th>
                <th>Delete</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="user in users">
                <td><router-link :to="{ path: '/user/' + user.username }"> {{ user.username }} </router-link></td>
                <td>{{ user.firstName + ' ' +  user.lastName }} </td>
                <td>{{ user.email }} </td>
                <td>
                    <ul>
                        <li v-for="role in user.authorities" >{{ role }}</li>
                    </ul>
                </td>
                <td>
                     <button v-on:click="deleteUser(user.username)" class="btn btn-danger">Delete</button>
                </td>
            </tr>
        </tbody>
        
    </table>
</div>`

export { UsersTemplate }