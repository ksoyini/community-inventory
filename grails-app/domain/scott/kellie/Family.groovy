package scott.kellie

class Family {

    String familyName
    String address
    Community community

    static constraints = {
        address unique: true
    }
}
