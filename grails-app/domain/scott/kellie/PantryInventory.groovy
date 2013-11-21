package scott.kellie

class PantryInventory {

    Family family
    PantryItem pantryItem
    Long quantity



    static constraints = {
        pantryItem unique: 'family'
        quantity min: 0L
    }
}
