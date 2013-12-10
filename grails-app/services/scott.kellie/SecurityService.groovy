package scott.kellie

import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.UsernamePasswordToken
import scott.kellie.User

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
            log.debug('api authentication for username' + username)
            SecurityUtils.subject.login(token)
            return true
        } catch (AuthenticationException){
            log.debug('api authentication failed')
            return false
        }
    }
}
