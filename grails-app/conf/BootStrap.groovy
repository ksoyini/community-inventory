import org.apache.shiro.crypto.hash.Sha256Hash
import scott.kellie.Role
import scott.kellie.User

class BootStrap {

    def init = { servletContext ->
        Role head = Role.findOrCreateByName("HEAD_OF_HOUSEHOLD")
        Role member = Role.findOrCreateByName("MEMBER_OF_HOUSEHOLD")

        if(!User.count()) {
            //TODO: add salt
            User smithScott  = new User(username: 'smithscott', passwordHash: new Sha256Hash('smithscott').toHex(), roles: [head]).save(failOnError: true)

            User robinson = new User(username: 'robinson', passwordHash: new Sha256Hash('robinson').toHex(), roles: [head]).save(failOnError: true)
            User geli = new User(username: 'geli', passwordHash: new Sha256Hash('geli').toHex(), roles: [member]).save(failOnError: true)

            User simo = new User(username: 'simo', passwordHash: new Sha256Hash('simo').toHex(), roles: [head]).save(failOnError: true)
            User tar = new User(username: 'tar', passwordHash: new Sha256Hash('tar').toHex(), roles: [member]).save(failOnError: true)
            User ang = new User(username: 'ang', passwordHash: new Sha256Hash('ang').toHex(), roles: [member]).save(failOnError: true)
        }

    }
    def destroy = {
    }
}
