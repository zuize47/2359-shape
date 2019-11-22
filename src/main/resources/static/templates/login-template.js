const LoginTemplate = `
    <div class="login-form">
        
            <h2 class="text-center">Log in</h2>
            <div class="form-group">
                <input type="text" name="username" class="form-control" placeholder="Username" required="required" v-model="username">
            </div>
            <div class="form-group">
                <input type="password" name='password' class="form-control" placeholder="Password" required="required" v-model="password">
            </div>
            <div class="form-group">
                <button type="submit" @click="clickLogin" class="btn btn-primary btn-block">Log in</button>
            </div>
            <div class="clearfix">
                <label class="pull-left checkbox-inline"><input type="checkbox"> Remember me</label>
                <a href="#/register" class="pull-right">Register?</a>
            </div>
    </div>`

export { LoginTemplate }