package scott.kellie

class CommonDisplayTagLib {
    static namespace = "pantry"

    SecurityService securityService

    def currentUserDisplay  = {attrs ->
        out << "You are currently logged in as $securityService.currentUser.username"
    }
}
