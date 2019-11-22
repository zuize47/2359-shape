const UserTemplate = `
    <div class="login-form">
            <h2 class="text-center">{{ user.username ? 'Edit User' : 'Register'  }}</h2>
            <div class="form-group">
                <input type="text" name="username" class="form-control" placeholder="Username" required="required" v-model="user.username">
            </div>
            <div class="form-group">
                <input type="password" name='password' class="form-control" placeholder="Password" required="required" v-model="user.password">
            </div>
            <div class="form-group">
                <input type="text" name='firstName' class="form-control" placeholder="First Name" required="required" v-model="user.firstName">
            </div>
            <div class="form-group">
                <input type="text" name='lastName' class="form-control" placeholder="Last Name" required="required" v-model="user.lastName">
            </div>
            <div class="form-group">
                <input type="email" name='email' class="form-control" placeholder="Email" required="required" v-model="user.email">
            </div>
            <div class="form-group"  v-if="roles.length > 0">
                <select v-if="roles" v-model="user.authorities" multiple required="required" class="form-control">
                    <option  v-for="item in roles" v-bind:value="item" :selected="user.authorities.includes(item)" >{{ item }}</option>
                </select>
            </div>
            
            <div class="form-group">
                <button type="submit" @click="clickRegister" class="btn btn-primary btn-block">{{ user.username ? 'Update' : 'Register'  }}</button>
            </div>
    </div>`

export { UserTemplate }