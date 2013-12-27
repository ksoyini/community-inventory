package scott.kellie

class CommonDisplayTagLib {
    static namespace = "pantry"

    SecurityService securityService

    def currentUserDisplay  = {attrs ->
        out << "Welcome $securityService.currentUser.username !"
    }
}
