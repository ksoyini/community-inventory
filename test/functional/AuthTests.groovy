
class AuthTests extends BaseTests {

    void testLoginLogout() {

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
