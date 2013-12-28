import functionaltestplugin.FunctionalTestCase

class APITests extends FunctionalTestCase {

    void testPantryInventoryList() {
        get('/api/pantryInventory?username=smithscott&password=smithscott'){
            headers['Accept'] = 'application/json'

}
        assertStatus(200)
        String jsonResult ='{"pantryInventory":[{"item":"apples","quantity":5},{"item":"yogurt","quantity":4}]}'
        assertContentContains(jsonResult)
    }

}
