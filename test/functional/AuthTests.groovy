import functionaltestplugin.FunctionalTestCase

class AuthTests extends BaseTests {
//class AuthTests extends FunctionalTestCase {

    void testLoginLogout() {

        //assert logout is not displayed i.e. we're not logged in
        //TODO:
        !assertContentContains('Logout')
        login('smithscott')

        //if logout is there to click, that means we were successfully logged in
        assertContentContains('Logout')
        click('Logout')
    }

    void testGetCurrentUser() {
        login('smithscott')
        assertContentContains('Welcome smithscott')
        click('Logout')

        login('geli')
        assertContentContains('Welcome geli')
        click('Logout')
    }

}
