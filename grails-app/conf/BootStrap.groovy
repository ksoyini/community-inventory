import org.apache.shiro.crypto.hash.Sha256Hash
import scott.kellie.Community
import scott.kellie.Family
import scott.kellie.FamilyMember
import scott.kellie.ItemCategory
import scott.kellie.PantryInventory
import scott.kellie.PantryItem
import scott.kellie.Role
import scott.kellie.User

class BootStrap {

    def init = { servletContext ->
        Role head = Role.findOrCreateByName("HEAD_OF_HOUSEHOLD")
        Role member = Role.findOrCreateByName("MEMBER_OF_HOUSEHOLD")

        // add sample set of users
//        if(!User.count()) {
            //TODO: add salt
            User smithScott  = new User(username: 'smithscott', passwordHash: new Sha256Hash('smithscott').toHex(), roles: [head]).save()

            User robinson = new User(username: 'robinson', passwordHash: new Sha256Hash('robinson').toHex(), roles: [head]).save()
            User geli = new User(username: 'geli', passwordHash: new Sha256Hash('geli').toHex(), roles: [member]).save()

            User simo = new User(username: 'simo', passwordHash: new Sha256Hash('simo').toHex(), roles: [head]).save()
            User tar = new User(username: 'tar', passwordHash: new Sha256Hash('tar').toHex(), roles: [member]).save()
            User ang = new User(username: 'ang', passwordHash: new Sha256Hash('ang').toHex(), roles: [member]).save()

            //permissions all users have:
            User.list().each {User u->
                //can access their own pantry
                u.addToPermissions('pantryInventory:index')
                u.addToPermissions('pantryInventory:list')
                u.addToPermissions('pantryInventory:apiList')
                u.save()
            }

//        }

        //add sample set of pantry items
//        if(!PantryItem.count()) {
            PantryItem apples = new PantryItem(name: 'apples', itemCategory: ItemCategory.PRODUCE).save()
            PantryItem yogurt = new PantryItem(name: 'yogurt', itemCategory: ItemCategory.DAIRY).save()
            PantryItem bleach = new PantryItem(name: 'bleach', itemCategory: ItemCategory.CLEANING).save()
            PantryItem cashews = new PantryItem(name: 'cashews', itemCategory: ItemCategory.SNACKS).save()
            PantryItem gummies = new PantryItem(name: 'gummies', itemCategory: ItemCategory.SNACKS).save()
            PantryItem chippies = new PantryItem(name: 'chippies', itemCategory: ItemCategory.SNACKS).save()
//        }

       //add sample community
        Community midlo = new Community(name: 'midlo').save()
        Community sunrise = new Community(name: 'sunrise').save()

        //add sample familys
        Family scott = new Family(familyName: 'scott', address: '305 Main St', community: midlo).save()
        Family rob = new Family(familyName: 'robinson', address: '1482 Main St', community: midlo).save()
        Family smith = new Family(familyName: 'smith', address: '9608 Main St', community: sunrise).save()

        //add sample FamilyMembers
        FamilyMember smithscott = new FamilyMember(family: scott, user: smithScott).save()
        FamilyMember robi = new FamilyMember(family: rob, user: robinson).save()
        FamilyMember gelica = new FamilyMember(family: rob, user: geli).save()
        FamilyMember barry = new FamilyMember(family: smith, user: simo).save()
        FamilyMember quito = new FamilyMember(family: smith, user: tar).save()
        FamilyMember aguy = new FamilyMember(family: smith, user: ang).save()

        //add sample pantrys
        PantryInventory scottPantryApples = new PantryInventory(pantryItem: apples, quantity: 5, family: scott).save()
        PantryInventory scottPantryYogurt = new PantryInventory(pantryItem: yogurt, quantity: 4, family: scott).save()
        PantryInventory robinsonPantryYogurt = new PantryInventory(pantryItem: yogurt, quantity: 2, family: rob).save()
        PantryInventory robinsonPantryBleach = new PantryInventory(pantryItem: bleach, quantity: 1, family: rob).save()
        PantryInventory robinsonPantryGummies = new PantryInventory(pantryItem: gummies, quantity: 10, family: rob).save()
        PantryInventory smithPantryYogurt = new PantryInventory(pantryItem: yogurt, quantity: 3, family: smith).save()
        PantryInventory smithPantryCashews = new PantryInventory(pantryItem: cashews, quantity: 2, family: smith).save()
        PantryInventory smithPantryApples = new PantryInventory(pantryItem: apples, quantity: 5, family: smith).save()
        PantryInventory smithPantryChippies = new PantryInventory(pantryItem: chippies, quantity: 9, family: smith).save()
    }
    def destroy = {
    }
}
