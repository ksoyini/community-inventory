package scott.kellie

import grails.plugin.spock.IntegrationSpec
import org.apache.shiro.SecurityUtils
import org.apache.shiro.UnavailableSecurityManagerException
import org.apache.shiro.mgt.SecurityManager
import org.apache.shiro.subject.Subject
import org.apache.shiro.subject.support.SubjectThreadState
import org.apache.shiro.util.LifecycleUtils
import org.apache.shiro.util.ThreadState
import org.apache.shiro.web.subject.WebSubject
import org.codehaus.groovy.grails.plugins.testing.GrailsMockHttpServletResponse
import org.springframework.mock.web.MockHttpServletRequest

import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

/**
 * Abstract test case enabling Shiro in test environments.
 * Taken from shiro testing documentation: http://shiro.apache.org/testing.html.
 */
public abstract class AbstractShiroIntegrationSpecification extends IntegrationSpec {

    static ThreadState subjectThreadState
    def shiroSecurityManager

    public AbstractShiroIntegrationSpecification() {
    }

    /**
     * Allows subclasses to set the currently executing {@link Subject} instance.
     *
     * @param subject the Subject instance
     */
    protected static void setSubject(Subject subject) {
        clearSubject()
        subjectThreadState = createThreadState(subject)
        subjectThreadState.bind()
    }

    protected static Subject getSubject() {
        return SecurityUtils.getSubject()
    }

    protected static ThreadState createThreadState(Subject subject) {
        return new SubjectThreadState(subject)
    }

    /**
     * Clears Shiro's thread state, ensuring the thread remains clean for future test execution.
     */
    protected static void clearSubject() {
        doClearSubject()
    }

    private static void doClearSubject() {
        if (subjectThreadState != null) {
            subjectThreadState.clear()
            subjectThreadState = null
        }
    }

    protected static void setSecurityManager(SecurityManager securityManager) {
        SecurityUtils.setSecurityManager(securityManager)
    }

    protected static SecurityManager getSecurityManager() {
        return SecurityUtils.getSecurityManager()
    }

    void setUpShiro() {
        //0.  Build and set the SecurityManager used to build Subject instances used in your tests
        //    This typically only needs to be done once per class if your shiro.ini doesn't change,
        //    otherwise, you'll need to do this logic in each test that is different
        setSecurityManager(shiroSecurityManager)
        //1.  Build the Subject instance for the test to run:
//        Subject subjectUnderTest = new Subject.Builder(getSecurityManager()).buildSubject();
        ServletRequest request = new MockHttpServletRequest() as ServletRequest
        ServletResponse response = new GrailsMockHttpServletResponse() as ServletResponse
        Subject subjectUnderTest = new WebSubject.Builder(request, response).buildSubject();

        //2. Bind the subject to the current thread:
        setSubject(subjectUnderTest);
    }

    void tearDownShiro() {
        doClearSubject()
        try {
            SecurityManager securityManager = getSecurityManager()
            LifecycleUtils.destroy(securityManager)
        } catch (UnavailableSecurityManagerException e) {
            //we don't care about this when cleaning up the test environment
            //(for example, maybe the subclass is a unit test and it didn't
            // need a SecurityManager instance because it was using only
            // mock Subject instances)
        }
        setSecurityManager(null)
    }
}
