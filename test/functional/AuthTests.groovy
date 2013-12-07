import functionaltestplugin.FunctionalTestCase

class AuthTests extends BaseTests {
//class AuthTests extends FunctionalTestCase {

    void testLoginLogout() {

        //assert logout is not displayed i.e. we're not logged in
        //TODO:
        login('smithscott')

        //if logout is there to click, that means we were successfully logged in
        click('Logout')
    }

    void testGetCurrentUser() {
        login('smithscott')
        assertContentContains('You are currently logged in as smithscott')
        click('Logout')
    }

}
