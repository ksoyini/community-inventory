package scott.kellie

import scott.kellie.Family
import scott.kellie.FamilyMember
import scott.kellie.PantryInventory
import scott.kellie.User

class PantryInventoryService {

    SecurityService securityService

    List<PantryInventory> listPantryInventoryForMyFamily() {
        User currentUser = securityService.currentUser
        log.debug('currentUser: ' + currentUser)
        Family currentUserFamily = FamilyMember.createCriteria().get {
            eq 'user', currentUser
            projections {
                property 'family'
            }
        }
        return PantryInventory.createCriteria().list {
            eq 'family', currentUserFamily
        }

    }
}
