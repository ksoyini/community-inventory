package scott.kellie

import org.apache.shiro.UnavailableSecurityManagerException
import org.apache.shiro.config.IniSecurityManagerFactory
import org.apache.shiro.subject.Subject
import org.apache.shiro.subject.support.SubjectThreadState
import org.apache.shiro.util.LifecycleUtils
import org.apache.shiro.util.ThreadState
import org.apache.shiro.web.subject.WebSubject
import org.codehaus.groovy.grails.plugins.testing.GrailsMockHttpServletResponse
import org.junit.After
import org.junit.AfterClass
import org.junit.BeforeClass
import org.springframework.mock.web.MockHttpServletRequest
import scott.kellie.SecurityService
import grails.plugin.spock.IntegrationSpec
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.UsernamePasswordToken
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.junit.Before
import scott.kellie.User

import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

class SecurityServiceIntegrationSpecification extends AbstractShiroIntegrationSpecification {

//    GrailsApplication grailsApplication
//    def shiroSecurityManager
    SecurityService securityService


    @Before
    void setup() {
        setUpShiro()
//        boolean hasSecurityManager
//        try {
//            hasSecurityManager = SecurityUtils.securityManager
//        }catch (UnavailableSecurityManagerException usme) {
//            hasSecurityManager = false
//        }
//        if(!hasSecurityManager) {
////            SecurityUtils.securityManager = grailsApplication.mainContext.getBean('shiroSecurityManager')
//            SecurityUtils.setSecurityManager(shiroSecurityManager)
//            ServletRequest request = new MockHttpServletRequest() as ServletRequest
//            ServletResponse response = new GrailsMockHttpServletResponse() as ServletResponse
//            Subject subject = WebSubject.Builder(request, response).buildWebSubject();
//            SubjectThreadState sts = new SubjectThreadState(subject)
//            sts.bind()
//
//        }
    }
    @After
    void tearDown() {
        tearDownShiro()
    }

//
//    void "test get current user"() {
//        when:
//        def authToken = new UsernamePasswordToken(username, password)
//
//        SecurityUtils.subject.login(authToken)
//        User u = securityService.currentUser
//
//        then:
//        u.username == username
//
//        where:
//        username     | password
//        'smithscott' | 'smithscott'
//        'geli'       | 'geli'
//        'simo'       | 'simo'
//        'tar'        | 'tar'
//        'ang'        | 'ang'
//    }

    void "test api login"() {
        when:
        boolean result = securityService.apiLogin(username, password)

        then:
         loggedIn == result

        where:
        username     | password     | loggedIn
        'smithscott' | 'smithscott' | true
//        'geli'       | 'geli'       | true
        'simo'       | 'simo'       | true
//        'tar'        | 'tar'        | true
//        'ang'        | 'bad_pwd' | false
//        'smithscott' | 'bad_pwd' | false
//        'geli'       | 'bad_pwd' | false
//        'simo'       | 'bad_pwd' | false
//        'tar'        | 'bad_pwd' | false
//        'ang'        | 'bad_pwd' | false

    }
}
