import java.util.*

class Management_sys() {
    // Item class, Menu, Add, Update, Delete, Search

    class Item(var name:String, var num:Int) {
        // Property (data member)
        val product: String = name
        var stock: Int = num

        fun addStock(quantity: Int) {
            stock += quantity
        }

        fun removeStock(quantity: Int) {
            stock -= quantity
        }
    }

    fun menu(){
        do {
            println("===========================")
            println("INVENTORY MANAGEMENT SYSTEM")
            println("===========================")
            println("[1] - Add Product")
            println("[2] - Subtract Product")
            println("[3] - Delete Product")
            println("[4] - View All Product")
            println("[5] - Exit")
            println("===========================")
            print("Select Operation : ")
            val choice: String? = readLine()

            when (choice) {
                "1" -> addItem()
                "2" -> subtractItem()
                "3" -> removeItem()
                "4" -> getAllItems()
                "5" -> System.exit(0);
                else -> println("ERROR: Incorrect Operation Input!")
            }

            print("Do you want to exit the system (Y/N) : ")
            val exit: String? = readLine()?.lowercase()
        }

        while (exit.equals("n"))
        println("ERROR: Incorrect Input! System exit!")
    }


    //add, update, delete, browse

    var inventory = arrayListOf<Item>()

    private fun findItem(name: String): Int{

        return inventory.indexOfFirst { it.name == name }

    }
    fun removeItem() {
        print("Input item name: ")
        val name = readln()
        val index = findItem(name.lowercase())
        if (index>=0) {
            inventory.remove(inventory[index])
            println("Item removed to inventory!")
        }else{
            println("Operation failed!")
        }
    }
    fun getAllItems() {
        if(inventory.size <= 0){
            println("The inventory is empty.")
        }
        for (item in inventory) {
            println("${item.product} : ${item.stock}")
        }
    }

    fun getItem(): Unit{
        print("Input item name: ")
        val name = readln()
        var index = inventory.indexOfFirst { it.name == name }
        if( index >= 0){
            var item = inventory[index]
            println("${item.name} : ${item.stock}")
        } else {
            println("$name not found.")
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
        val newItem = Item(name.lowercase(),num.toInt())
        val index = findItem(name.lowercase())
        if (index >= 0)  {
            inventory[index].addStock(num.toInt())
            var item = inventory[index]
            println("$name updated! \n${item.name} : ${item.stock} ")
            println("Index is $index")
        }else{
            println("Index is $index")
            inventory.add(newItem)
            println("$name added to inventory!")
        }
    }

    fun subtractItem(){
        print("Input item name: ")
        val name = readln()
        print("Input item stock: ")
        val num = readln()
        val newItem = Item(name.lowercase(),num.toInt())
        val index = findItem(name.lowercase())
        if (index >= 0)  {
            inventory[index].removeStock(num.toInt())
            var item = inventory[index]
            println("$name updated! \n${item.name} : ${item.stock} ")
        }else{
            inventory.add(newItem)
            println("$name added to inventory!")
        }
    }
}