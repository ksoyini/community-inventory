package scott.kellie

import org.apache.shiro.authc.AccountException
import org.apache.shiro.authc.IncorrectCredentialsException
import org.apache.shiro.authc.SimpleAccount
import org.apache.shiro.authc.UnknownAccountException

class ApiRealm {
    static authTokenClass = ApiAccessToken

    def credentialMatcher
    def shiroPermissionResolver

    def authenticate(authToken) {
        log.info "Attempting to authenticate API realm..."
        def accessToken = authToken.accessToken

        // Null username is invalid
        if (accessToken == null) {
            throw new AccountException("Null accessToken are not allowed by this realm.")
        }

        // Get the user with the given accessToken. If the user is not
        // found, then they don't have an account and we throw an
        // exception.
        def user = User.findByAccessToken(accessToken) //TODO: add accessToken field to user
        if (!user) {
            throw new UnknownAccountException("No account found for user")
        }

        log.info "Found user '${user.username}' in API"

        // Now check the user's password against the hashed value stored
        // in the database.
        def account = new SimpleAccount(user.username, user.passwordHash, "ApiRealm")
//        if (!credentialMatcher.doCredentialsMatch(authToken, account)) {
//            log.info "Invalid password (DB realm)"
//            throw new IncorrectCredentialsException("Invalid password for user '${username}'")
//        }

        return account
    }
}
