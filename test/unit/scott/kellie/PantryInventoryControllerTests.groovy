package scott.kellie

import grails.web.JSONBuilder
import groovy.json.JsonBuilder
import groovy.xml.MarkupBuilder
import org.junit.*
import grails.test.mixin.*
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
        controller.save()

        assert model.pantryInventoryInstance != null
        assert view == '/pantryInventory/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/pantryInventory/show/1'
        assert controller.flash.message != null
        assert PantryInventory.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/pantryInventory/list'

        populateValidParams(params)
        def pantryInventory = new PantryInventory(params)

        assert pantryInventory.save() != null

        params.id = pantryInventory.id

        def model = controller.show()

        assert model.pantryInventoryInstance == pantryInventory
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/pantryInventory/list'

        populateValidParams(params)
        def pantryInventory = new PantryInventory(params)

        assert pantryInventory.save() != null

        params.id = pantryInventory.id

        def model = controller.edit()

        assert model.pantryInventoryInstance == pantryInventory
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/pantryInventory/list'

        response.reset()

        populateValidParams(params)
        def pantryInventory = new PantryInventory(params)

        assert pantryInventory.save() != null

        // test invalid parameters in update
        params.id = pantryInventory.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/pantryInventory/edit"
        assert model.pantryInventoryInstance != null

        pantryInventory.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/pantryInventory/show/$pantryInventory.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        pantryInventory.clearErrors()

        populateValidParams(params)
        params.id = pantryInventory.id
        params.version = -1
        controller.update()

        assert view == "/pantryInventory/edit"
        assert model.pantryInventoryInstance != null
        assert model.pantryInventoryInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/pantryInventory/list'

        response.reset()

        populateValidParams(params)
        def pantryInventory = new PantryInventory(params)

        assert pantryInventory.save() != null
        assert PantryInventory.count() == 1

        params.id = pantryInventory.id

        controller.delete()

        assert PantryInventory.count() == 0
        assert PantryInventory.get(pantryInventory.id) == null
        assert response.redirectedUrl == '/pantryInventory/list'
    }
}
