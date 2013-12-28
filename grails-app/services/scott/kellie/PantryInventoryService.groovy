package scott.kellie

import org.springframework.transaction.annotation.Transactional

class PantryInventoryService {

    SecurityService securityService
    def messageSource

    @Transactional(readOnly = true)
    List<PantryInventory> listPantryInventoryForMyFamily() {
        Family currentUserFamily = getMyFamily()
        return PantryInventory.createCriteria().list {
            eq 'family', currentUserFamily
        }

    }

    @Transactional(readOnly = true)
    List<PantryInventory> listPantryInventoryForMyCommunity() {
        Community currentUserCommunity = getMyFamily().community
        List<Family> familiesInMyCommunity = Family.createCriteria().list {
            eq 'community', currentUserCommunity
        }
        return PantryInventory.createCriteria().list {
            inList 'family', familiesInMyCommunity
        }
    }


    @Transactional
    PantryInventory createPantryInventoryForMyFamily(PantryItem pantryItem, Long quantity) {
        Family currentUserFamily = getMyFamily()
        PantryInventory pantryInventory = new PantryInventory(
                pantryItem: pantryItem,
                quantity: quantity,
                family: currentUserFamily
        )
        pantryInventory.save()
        return pantryInventory
    }

    @Transactional(readOnly = true)
    PantryInventory getPantryInventoryForMyFamily(Long pantryInventoryId) {
        Family currentUserFamily = getMyFamily()
        //returns pantryInventory with valid id in my family
        return PantryInventory.createCriteria().get{
            eq 'family', currentUserFamily
            eq 'id', pantryInventoryId
        }
    }

    @Transactional
    PantryInventory updatePantryInventoryForMyFamily(Long pantryInventoryId, Long version, Long quantity) {
        PantryInventory pantryInventory = getPantryInventoryForMyFamily(pantryInventoryId)
        if(pantryInventory) {
            if (version != null) {
                if (pantryInventory.version > version) {
                    pantryInventory.errors.rejectValue("version", "default.optimistic.locking.failure",
                           ['PantryInventory'] as Object[],
                            "Another user has updated this PantryInventory while you were editing")

                    return pantryInventory
                }
            }
            pantryInventory.quantity = quantity
            pantryInventory.save()
        }
        return pantryInventory
    }


    /**
     *
     * @param pantryInventoryId
     * @return false, if can't find pantryInventory to delete, true otherwise
     */
    @Transactional
    boolean deletePantryInventoryForMyFamily(Long pantryInventoryId) {
        PantryInventory pantryInventory = getPantryInventoryForMyFamily(pantryInventoryId)
        if(pantryInventory) {
            pantryInventory.delete()//TODO: flush:true
            return true
        }
        return false
    }


    Family getMyFamily() {
        User currentUser = securityService.currentUser
        log.debug('currentUser: ' + currentUser)
        return FamilyMember.createCriteria().get {
            eq 'user', currentUser
            projections {
                property 'family'
            }
        }
    }

}
