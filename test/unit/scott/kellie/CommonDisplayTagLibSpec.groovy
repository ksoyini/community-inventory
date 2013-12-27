package scott.kellie

import grails.test.mixin.TestFor
import scott.kellie.CommonDisplayTagLib
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.GroovyPageUnitTestMixin} for usage instructions
 */
@TestFor(CommonDisplayTagLib)
class CommonDisplayTagLibSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test current User Display"() {
        assert applyTemplate('<pantry:currentUserDisplay/>').startsWith('Welcome')
    }
}