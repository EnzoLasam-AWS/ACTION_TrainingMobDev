import java.util.ArrayList

class Management_sys {

    //add, modify, delete, browse

    private var inventory = arrayListOf<Item>()

    fun success(result: Boolean) = if( result ){
        println("Operation was successful.") }else{println("Operation failed. Please try again")}

    fun findItem(name: String): Boolean{
        val index = inventory.indexOfFirst { it.name == name }

        if( index >= 0){
            var item = inventory.get(index)
            println("${item.name} : ${item.num}")
            return true
        } else {
            println("$name not found.")
            return false
        }

    }
    fun addItems(items:Array<Item>){
        items.forEach { inventory.add(it) }
    }
    fun addItem(){
        print("Input item name: ")
        val name = readln()
        print("Input item stock: ")
        val num = readln()
        val newItem = Item(name,num.toInt())
        if (!findItem(name)) {
            inventory.add(newItem)
            println("Item added to inventory!")
        }else{
            println("Item already exists!")
        }
    }
    fun printInventory(){
        for (item in inventory){
            println("${item.name} : ${item.num}")
        }
    }
    fun removeItem(name: String) {
        if (findItem(name)){
            inventory.removeIf { it.name == name }
            println("Item removed to inventory!")
        }else{
            println("Operation failed!")
        }

    }

    class Item(val name:String, val num:Int) {

        // Property (data member)
        private val item: String = name
        var numItems: Int = num

        fun addStock(quantity: Int) {
            numItems += quantity
        }

        fun subtractStock(quantity: Int) {
            numItems -= quantity
        }
    }

}

