package communityinventory

import org.apache.shiro.SecurityUtils
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
            currentUser = User.findByUsername(SecurityUtils.subject.principal)
        }
        return currentUser
    }
}
