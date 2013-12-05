import communityinventory.SecurityService
import grails.plugin.spock.IntegrationSpec
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.UsernamePasswordToken
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.junit.Before
import scott.kellie.User

class SecurityServiceIntegrationSpecification extends IntegrationSpec {

    GrailsApplication grailsApplication
    def shiroSecurityManager
    SecurityService securityService


    @Before
    void before() {
//        if(!SecurityUtils.securityManager) {
//            SecurityUtils.securityManager = grailsApplication.mainContext.getBean('shiroSecurityManager')
            SecurityUtils.securityManager = shiroSecurityManager
//        }
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
}
