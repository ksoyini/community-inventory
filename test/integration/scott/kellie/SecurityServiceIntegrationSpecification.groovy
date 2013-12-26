package scott.kellie

import org.junit.AfterClass
import org.junit.BeforeClass
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.UsernamePasswordToken

class SecurityServiceIntegrationSpecification extends AbstractShiroIntegrationSpecification {

    SecurityService securityService


    @BeforeClass
    void setup() {
        setUpShiro()
    }
    @AfterClass
    void tearDown() {
        tearDownShiro()
    }


    void "test get current user"() {
        when:
        def authToken = new UsernamePasswordToken(username, password)

        SecurityUtils.subject.login(authToken)
        User u = securityService.currentUser

        then:
        u.username == username

        where:
        username     | password
        'smithscott' | 'smithscott'
        'geli'       | 'geli'
        'simo'       | 'simo'
        'tar'        | 'tar'
        'ang'        | 'ang'
    }

    void "test api login"() {
        when:
        boolean result = securityService.apiLogin(username, password)

        then:
        loggedIn == result

        where:
        username     | password     | loggedIn
        'smithscott' | 'smithscott' | true
        'geli'       | 'geli'       | true
        'simo'       | 'simo'       | true
        'tar'        | 'tar'        | true
        'ang'        | 'bad_pwd'    | false
        'smithscott' | 'bad_pwd'    | false
        'geli'       | 'bad_pwd'    | false
        'simo'       | 'bad_pwd'    | false
        'tar'        | 'bad_pwd'    | false
        'ang'        | 'bad_pwd'    | false
    }
}
