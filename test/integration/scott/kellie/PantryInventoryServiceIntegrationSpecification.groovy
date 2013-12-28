package scott.kellie

import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.UsernamePasswordToken
import org.junit.AfterClass
import org.junit.BeforeClass

class PantryInventoryServiceIntegrationSpecification extends AbstractShiroIntegrationSpecification {

    PantryInventoryService pantryInventoryService

    @BeforeClass
    void setup() {
        setUpShiro()
    }
    @AfterClass
    void tearDown() {
        tearDownShiro()
    }

    void "test ListPantryInventoryForMyFamily"() {
        when:
        String password = username
        def authToken = new UsernamePasswordToken(username, password)

        SecurityUtils.subject.login(authToken)

        then:
        SecurityUtils.subject.isAuthenticated()

        when:
        List pantry = pantryInventoryService.listPantryInventoryForMyFamily()

        then:
        pantry.size() == pantrySize

        where:
        username    | pantrySize
        'smithscott' | 2
        'robinson'   | 3
        'geli'       | 3
        'simo'       | 4
        'tar'        | 4
        'ang'        | 4
    }

    void "test GetPantryInventoryForMyFamily"() {
        when:
        String password = username
        def authToken = new UsernamePasswordToken(username, password)

        SecurityUtils.subject.login(authToken)

        then:
        SecurityUtils.subject.isAuthenticated()

        when:
        PantryInventory pantryInventory = pantryInventoryService.getPantryInventoryForMyFamily(pantryInventoryId)

        then:
        pantryInventory != null
        pantryInventory.family.id == family.id

        where:
        username    | pantryInventoryId | family
        'smithscott' | 1                | Family.findByFamilyName('scott')
        'robinson'   | 3                | Family.findByFamilyName('robinson')
        'geli'       | 4                | Family.findByFamilyName('robinson')
        'simo'       | 6                | Family.findByFamilyName('smith')
        'tar'        | 7                | Family.findByFamilyName('smith')
        'ang'        | 8                | Family.findByFamilyName('smith')

    }
}
