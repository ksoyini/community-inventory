package scott.kellie



import org.junit.*
import grails.test.mixin.*

@TestFor(PantryInventoryController)
@Mock(PantryInventory)
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

    void testList() {

        def model = controller.list()

        assert model.pantryInventoryInstanceList.size() == 0
        assert model.pantryInventoryInstanceTotal == 0
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
