package scott.kellie

class User {
    String username
    String passwordHash
    //TODO: salt
    
    static hasMany = [ roles: Role, permissions: String ]

    static constraints = {
        username(nullable: false, blank: false, unique: true)
    }

    static mapping = {
        table name: 'users'
    }
}
