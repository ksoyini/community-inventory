import functionaltestplugin.FunctionalTestCase

class APITests extends FunctionalTestCase {

    void testPantryInventoryList() {
        get('/api/pantryInventory?username=smithscott&password=smithscott'){
            headers['Accept'] = 'application/json'

}
        assertStatus(200)
        String jsonResult ='[]'
        assertContentContains(jsonResult)
    }

}
