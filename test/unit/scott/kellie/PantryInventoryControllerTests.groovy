package scott.kellie

import grails.validation.ValidationErrors
import grails.web.JSONBuilder
import groovy.json.JsonBuilder
import groovy.xml.MarkupBuilder
import org.junit.*
import grails.test.mixin.*
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus

@TestFor(PantryInventoryController)
@Mock([PantryInventory, User])
class PantryInventoryControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'

    }

    void testIndex() {
        controller.index()
        assert "/pantryInventory/list" == response.redirectedUrl
    }

    void testListHtml() {
        def control = mockFor(PantryInventoryService)
        control.demand.listPantryInventoryForMyFamily { -> [new PantryInventory()]}
        controller.pantryInventoryService = control.createMock()

        response.format = 'html'

        def model = controller.list()

        assert model.size() == 2
        assert model.pantryInventoryInstanceList.size() == 1
        assert model.pantryInventoryInstanceTotal == 1
    }

    void testListJson() {
        List mockResultList = [
                new PantryInventory(pantryItem: new PantryItem(name: 'yogurt'), quantity: 4),
                new PantryInventory(pantryItem: new PantryItem(name: 'pasta'), quantity: 1)
        ]
        def control = mockFor(PantryInventoryService)
        control.demand.listPantryInventoryForMyFamily { -> mockResultList}
        controller.pantryInventoryService = control.createMock()

        response.format = 'json'
        controller.list()
        def builder = new JSONBuilder()
        def json = builder.build {
            pantryInventory  = array {
                mockResultList.each { PantryInventory pantryInv ->
                    _ {
                         item =  pantryInv.pantryItem.name
                        quantity = pantryInv.quantity
                     }
                }
            }
        }
//        def json = new JsonBuilder()
//        def root = json {
//            "pantryInventory" (
//                mockResultList.each ({ PantryInventory pantryInv ->
//                    "item" pantryInv.pantryItem.name
//                    "quantity" pantryInv.quantity
//                })
//            )
//        }
        assert json.toString() == response.text
        assert response.status == HttpStatus.OK.value()
    }

    void testListEmptyJson() {
        def control = mockFor(PantryInventoryService)
        control.demand.listPantryInventoryForMyFamily { -> []}
        controller.pantryInventoryService = control.createMock()

        response.format = 'json'
        controller.list()
        assert response.text.isEmpty()
        assert response.status == HttpStatus.NO_CONTENT.value()
    }

    void testListXml() {
        List mockResultList = [
                new PantryInventory(pantryItem: new PantryItem(name: 'yogurt'), quantity: 4),
                new PantryInventory(pantryItem: new PantryItem(name: 'pasta'), quantity: 1)
        ]
        def control = mockFor(PantryInventoryService)
        control.demand.listPantryInventoryForMyFamily { -> mockResultList}
        controller.pantryInventoryService = control.createMock()

        response.format = 'xml'
        controller.list()
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)
        xml.pantryInventory() {
            mockResultList.each { PantryInventory pantryInv ->
                inventory(item: pantryInv.pantryItem.name, quantity: pantryInv.quantity)
            }
        }
        String expected = ''

        assert writer.toString().replaceAll('\n','').replaceAll(' ','') == response.text.replaceAll(' ','')
        assert response.status == HttpStatus.OK.value()
    }

    void testListEmptyXml() {
        def control = mockFor(PantryInventoryService)
        control.demand.listPantryInventoryForMyFamily { -> []}
        controller.pantryInventoryService = control.createMock()

        response.format = 'xml'
        controller.list()
        assert response.text.isEmpty()
        assert response.status == HttpStatus.NO_CONTENT.value()
    }

    void testCreate() {
        def model = controller.create()

        assert model.pantryInventoryInstance != null
    }

    void testSave() {
        CreatePantryInventoryCommand cmd = new CreatePantryInventoryCommand()
        PantryInventory pi = new PantryInventory(pantryItem: cmd.pantryItem, quantity: cmd.quantity)
        assert !pi.validate()
        def control = mockFor(PantryInventoryService)
        control.demand.createPantryInventoryForMyFamily {p,q  -> pi}
        controller.pantryInventoryService = control.createMock()

        controller.save(cmd)

        assert model.pantryInventoryInstance != null
        assert view == '/pantryInventory/create'

        response.reset()

        cmd = new CreatePantryInventoryCommand(pantryItem:  new PantryItem(), quantity: 1)
        pi = new PantryInventory(pantryItem: cmd.pantryItem, quantity: cmd.quantity, family: new Family()).save()

        control.demand.createPantryInventoryForMyFamily {p, q -> pi}
        controller.pantryInventoryService = control.createMock()
        controller.save(cmd)

        assert response.redirectedUrl == '/pantryInventory/show/1'
        assert controller.flash.message != null
        assert PantryInventory.count() == 1
    }

    void testShow() {
        def control = mockFor(PantryInventoryService)
        control.demand.getPantryInventoryForMyFamily {id  -> null}
        controller.pantryInventoryService = control.createMock()

        controller.show(1L)

        assert flash.message != null
        assert response.redirectedUrl == '/pantryInventory/list'

        PantryInventory pi = new PantryInventory()
        control.demand.getPantryInventoryForMyFamily {id  -> pi}
        controller.pantryInventoryService = control.createMock()

        def model = controller.show(2L)

        assert model.pantryInventoryInstance == pi
    }

    void testEdit() {
        def control = mockFor(PantryInventoryService)
        control.demand.getPantryInventoryForMyFamily {id  -> null}
        controller.pantryInventoryService = control.createMock()

        controller.edit(1L)

        assert flash.message != null
        assert response.redirectedUrl == '/pantryInventory/list'

        PantryInventory pi = new PantryInventory()
        control.demand.getPantryInventoryForMyFamily {id  -> pi}
        controller.pantryInventoryService = control.createMock()

        def model = controller.edit(2L)

        assert model.pantryInventoryInstance == pi
    }

    void testUpdate() {
        EditPantryInventoryCommand cmd = new EditPantryInventoryCommand()
        PantryInventory pi = new PantryInventory(pantryItem: new PantryItem(), quantity: cmd.quantity, family: new Family())
        assert !pi.validate()
        def control = mockFor(PantryInventoryService)
        control.demand.updatePantryInventoryForMyFamily {id,v,q  -> null}
        controller.pantryInventoryService = control.createMock()

        controller.update(1L, 0L, cmd)

        assert flash.message != null
        assert response.redirectedUrl == '/pantryInventory/list'

        response.reset()

        control.demand.updatePantryInventoryForMyFamily {id,v,q  -> pi}
        controller.pantryInventoryService = control.createMock()

        controller.update(2L, 0L, cmd)

        assert view == "/pantryInventory/edit"
        assert model.pantryInventoryInstance != null
        assert model.pantryInventoryInstance == pi

        cmd = new EditPantryInventoryCommand(quantity: 2L)
        pi = new PantryInventory(pantryItem: new PantryItem(), quantity: cmd.quantity, family: new Family()).save()
        control.demand.updatePantryInventoryForMyFamily {id,v,q  -> pi}
        controller.pantryInventoryService = control.createMock()

        controller.update(3L, 0L, cmd)

        assert response.redirectedUrl == "/pantryInventory/show/$pi.id"
        assert flash.message != null

        }

    void testDelete() {
        def control = mockFor(PantryInventoryService)
        control.demand.deletePantryInventoryForMyFamily {id  -> false}
        controller.pantryInventoryService = control.createMock()

        controller.delete(1L)
        assert flash.message != null
        assert response.redirectedUrl == '/pantryInventory/list'

        response.reset()

        control.demand.deletePantryInventoryForMyFamily {id  -> throw new DataIntegrityViolationException('ex')}
        controller.pantryInventoryService = control.createMock()
        controller.delete(2L)
        assert flash.message != null
        assert response.redirectedUrl == '/pantryInventory/show/2'

        response.reset()

        control.demand.deletePantryInventoryForMyFamily {id  -> true}
        controller.pantryInventoryService = control.createMock()

        controller.delete(3L)
        assert flash.message != null
        assert response.redirectedUrl == '/pantryInventory/list'
    }
}
