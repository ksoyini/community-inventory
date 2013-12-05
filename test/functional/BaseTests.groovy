import functionaltestplugin.FunctionalTestCase

//abstract class BaseTests extends com.grailsrocks.functionaltest.BrowserTestCase {
abstract class BaseTests extends FunctionalTestCase {

    protected void login(String user) {
        get('/auth/index')
        form('signIn') {
            username = user
            password = user
            click('Sign in')
        }

    }
}
