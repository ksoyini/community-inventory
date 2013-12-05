package scott.kellie

class FamilyMember {

    Family family
    User user

    static constraints = {
        user unique: true
    }
}
