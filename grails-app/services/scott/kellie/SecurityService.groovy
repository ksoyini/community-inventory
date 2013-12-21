package scott.kellie

import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.UsernamePasswordToken

class SecurityService {

    /**
     * The currently authenticated user.
     *
     * @return current User
     */
    User getCurrentUser() {
        User currentUser = null
        if (SecurityUtils.subject.isAuthenticated()) {
            currentUser = User.findByUsername(SecurityUtils.subject.principal as String)
        }
        return currentUser
    }

    boolean apiLogin(String username, String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(username: username, password: password)
        try {
            log.debug("api authentication for username: $username")
            SecurityUtils.subject.login(token)
            return true
        } catch (AuthenticationException ae){
            log.debug("api authentication failed: $ae.message")
            return false
        }
    }
}
