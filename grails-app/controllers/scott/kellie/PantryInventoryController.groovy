package scott.kellie

import grails.converters.JSON
import grails.converters.XML
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


    def create() {
//        [pantryInventoryInstance: new PantryInventory(params)]
        [pantryInventoryInstance: new PantryInventory()]
    }

    def save() {
        def pantryInventoryInstance = new PantryInventory(params)
        if (!pantryInventoryInstance.save(flush: true)) {
            render(view: "create", model: [pantryInventoryInstance: pantryInventoryInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'pantryInventory.label', default: 'PantryInventory'), pantryInventoryInstance.id])
        redirect(action: "show", id: pantryInventoryInstance.id)
    }

    def show(Long id) {
        def pantryInventoryInstance = PantryInventory.get(id)
        if (!pantryInventoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pantryInventory.label', default: 'PantryInventory'), id])
            redirect(action: "list")
            return
        }

        [pantryInventoryInstance: pantryInventoryInstance]
    }

    def edit(Long id) {
        def pantryInventoryInstance = PantryInventory.get(id)
        if (!pantryInventoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pantryInventory.label', default: 'PantryInventory'), id])
            redirect(action: "list")
            return
        }

        [pantryInventoryInstance: pantryInventoryInstance]
    }

    def update(Long id, Long version) {
        def pantryInventoryInstance = PantryInventory.get(id)
        if (!pantryInventoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pantryInventory.label', default: 'PantryInventory'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (pantryInventoryInstance.version > version) {
                pantryInventoryInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'pantryInventory.label', default: 'PantryInventory')] as Object[],
                          "Another user has updated this PantryInventory while you were editing")
                render(view: "edit", model: [pantryInventoryInstance: pantryInventoryInstance])
                return
            }
        }

        pantryInventoryInstance.properties = params

        if (!pantryInventoryInstance.save(flush: true)) {
            render(view: "edit", model: [pantryInventoryInstance: pantryInventoryInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'pantryInventory.label', default: 'PantryInventory'), pantryInventoryInstance.id])
        redirect(action: "show", id: pantryInventoryInstance.id)
    }

    def delete(Long id) {
        def pantryInventoryInstance = PantryInventory.get(id)
        if (!pantryInventoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pantryInventory.label', default: 'PantryInventory'), id])
            redirect(action: "list")
            return
        }

        try {
            pantryInventoryInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'pantryInventory.label', default: 'PantryInventory'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'pantryInventory.label', default: 'PantryInventory'), id])
            redirect(action: "show", id: id)
        }
    }
}
