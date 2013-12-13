package scott.kellie

import grails.converters.JSON
import grails.converters.XML
import org.springframework.dao.DataIntegrityViolationException

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
                render pantry as JSON
            }
            xml {
                render pantry as XML
            }
        }
    }


    def create() {
        [pantryInventoryInstance: new PantryInventory(params)]
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
