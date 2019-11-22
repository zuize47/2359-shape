# Shape Application
## How to run
    
    ./gradlew bootRun
    
## Login information
    - admin/admin: ROLE_ADMIN, ROLE_USER
    - user/password: ROLE_USER
## Screen List
* Login screen: used to login to this system, on this screen, it  has a *register link* to register new user 
* User screen: used to create new user
* the shape list screen will show after login, show all shape of current user. You can add/edit/edit shape in this screen.
* Shape screen: when you chose a shape in list or create new shape this screen will be shown. This screen used to create 
and edit shape. Chose a category, it will show a list of attribute need to enter the value. Button save used to save, button Area Calc used to calculate area of this shape, *cancel* link will to back to shape list screen. 
* Categories screen: to show this screen, you click on Categories menu in navigator bar. This screen show all category of shape in this system. you can view/edit/delete category.
* Category screen: this screen used to create new or edit exist category. You enter category name, the 
formula to calculator area of this category and list of attribute will be used in formula<br/>
**Example**<br/>
    ```code
    a SQUARE has area formula: "a*a"
    you enter an attribute with name "a" on attribute list - Category Screen.
    on Shape screen, you will enter value for "a": 4
    The area of SQUARE is: 16
    ```



     