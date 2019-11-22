const NavbarTemplate = `<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#/">Shape Demo</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li>
                    <a href="#/categories">Categories</a>
                </li>
                <li>
                    <a href="#/users" >Users</a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li v-if="isAuthenticated">
                    <a href="#" @click="clickLogout" >
                        <span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>&nbsp;Logout
                    </a>
                </li>
                <li v-else>
                    <a href="#/login" >
                        <span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>&nbsp;Login
                    </a>
                </li>
            </ul>
        </div>
        <!--/.nav-collapse -->
    </div>
</nav>`

export { NavbarTemplate }