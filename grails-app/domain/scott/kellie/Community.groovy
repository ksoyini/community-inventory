package scott.kellie

//JurisdictionCode
class Community {

    String name

    static constraints = {
        name nullable: false, blank: false, unique: true
    }
}
