import functionaltestplugin.FunctionalTestCase

//class AuthTests extends BaseTests {
class AuthTests extends FunctionalTestCase {

    void testLoginLogout() {

        //login('smithscott')
        get('/auth/index')
        form('signIn') {
            username = 'smithscott'
            password = 'smithscott'
            click('Sign in')
        }

        //if logout is there to click, that means we were successfully logged in
        click('Logout')


    }

//    void testGetCurrentUser() {
//
//    }

}
