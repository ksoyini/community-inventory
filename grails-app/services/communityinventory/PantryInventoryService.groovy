package communityinventory

import scott.kellie.Family
import scott.kellie.FamilyMember
import scott.kellie.PantryInventory
import scott.kellie.User

class PantryInventoryService {

    SecurityService securityService
    //TODO: test
    List<PantryInventory> listPantryInventoryForMyFamily() {
        User currentUser = securityService.currentUser
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
