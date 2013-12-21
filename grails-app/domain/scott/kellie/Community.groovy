package scott.kellie

class Community {

    String name

    static constraints = {
        name blank: false, unique: true
    }
}
