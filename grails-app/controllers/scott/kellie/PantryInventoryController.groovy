package scott.kellie

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus

class PantryInventoryController {

    PantryInventoryService pantryInventoryService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        List<PantryInventory> pantry = pantryInventoryService.listPantryInventoryForMyFamily()
        withFormat {
            html{
                [pantryInventoryInstanceList: pantry, pantryInventoryInstanceTotal: pantry.size()]
            }
            json {
                if(pantry) {
//                    render pantry as JSON
                    render(contentType: 'text/json') {
                        pantryInventory = array {
                            for(pi in pantry) {
                                pantryInventory item: pi.pantryItem.name, quantity:pi.quantity

                            }
                        }
                    }
                } else {
                    response.status = HttpStatus.NO_CONTENT.value()
                    render ''
                }
            }
            xml {
                if(pantry) {
//                    render pantry as XML
                    render(contentType: 'text/xml') {
                        pantryInventory {
                            for(pi in pantry) {
                                inventory(item: pi.pantryItem.name, quantity: pi.quantity)
                            }
                        }
                    }
                } else {
                    response.status = HttpStatus.NO_CONTENT.value()
                    render ''
                }
            }
        }
    }


    def listCommunity() {
        List<PantryInventory> pantry = pantryInventoryService.listPantryInventoryForMyCommunity()
        [pantryInventoryInstanceList: pantry, pantryInventoryInstanceTotal: pantry.size()]
    }


    def create() {
//        [pantryInventoryInstance: new PantryInventory(params)]
        [pantryInventoryInstance: new PantryInventory()]
    }

    def save(CreatePantryInventoryCommand command) {
        PantryInventory pantryInventoryInstance = pantryInventoryService.createPantryInventoryForMyFamily(command.pantryItem, command.quantity)
        if (pantryInventoryInstance.hasErrors()) {
            render(view: "create", model: [pantryInventoryInstance: pantryInventoryInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'pantryInventory.label', default: 'PantryInventory'), pantryInventoryInstance.id])
        redirect(action: "show", id: pantryInventoryInstance.id)
    }

    def show(Long id) {
        PantryInventory pantryInventoryInstance = pantryInventoryService.getPantryInventoryForMyFamily(id)
        if (!pantryInventoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pantryInventory.label', default: 'PantryInventory'), id])
            redirect(action: "list")
            return
        }

        [pantryInventoryInstance: pantryInventoryInstance]
    }

    def edit(Long id) {
        PantryInventory pantryInventoryInstance = pantryInventoryService.getPantryInventoryForMyFamily(id)
        if (!pantryInventoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pantryInventory.label', default: 'PantryInventory'), id])
            redirect(action: "list")
            return
        }

        [pantryInventoryInstance: pantryInventoryInstance]
    }

    def update(Long id, Long version, EditPantryInventoryCommand command) {
        PantryInventory pantryInventoryInstance = pantryInventoryService.updatePantryInventoryForMyFamily(id, version, command.quantity)
        if (!pantryInventoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pantryInventory.label', default: 'PantryInventory'), id])
            redirect(action: "list")
            return
        }

        if (pantryInventoryInstance.hasErrors()) {
            render(view: "edit", model: [pantryInventoryInstance: pantryInventoryInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'pantryInventory.label', default: 'PantryInventory'), pantryInventoryInstance.id])
        redirect(action: "show", id: pantryInventoryInstance.id)
    }

    def delete(Long id) {
        try {
            boolean foundAndDeleted = pantryInventoryService.deletePantryInventoryForMyFamily(id)
            if(foundAndDeleted) {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'pantryInventory.label', default: 'PantryInventory'), id])
                redirect(action: "list")
            } else {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'pantryInventory.label', default: 'PantryInventory'), id])
                redirect(action: "list")
            }
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'pantryInventory.label', default: 'PantryInventory'), id])
            redirect(action: "show", id: id)
        }
    }
}

class CreatePantryInventoryCommand {
    PantryItem pantryItem
    Long quantity

    static constraints = {
        quantity min: 1L
    }

}
class EditPantryInventoryCommand {
    Long quantity

    static constraints = {
        quantity min: 1L
    }

}
