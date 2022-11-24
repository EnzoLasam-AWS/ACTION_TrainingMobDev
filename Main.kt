fun main(args: Array<String>) {

    //Get Input
    print("Enter Palindrome: ")
    val input = readLine()!!

    checkPalindrome(input)
    occurCounter(input)

    val myInventoryManagement=Management_sys()
    val items = arrayOf(
        Management_sys.Item("Choco", 12),
        Management_sys.Item("Oranges",12),
        Management_sys.Item("Apples",12),)

    myInventoryManagement.addItems(items)

    myInventoryManagement.printInventory()
    myInventoryManagement.removeItem("Choco")
    myInventoryManagement.printInventory()
    myInventoryManagement.findItem("Choco")
}

fun checkPalindrome(input: String): Unit{

    //removes whitespaces
    val pal = input.filterNot { it.isWhitespace() }
    //Checks if the sentence is a palindrome

    if (pal.equals(pal.reversed(), ignoreCase = true)) {
        println("$input is a Palindrome")
    } else{
        println("$input is NOT a Palindrome")
    }
}

fun occurCounter(input: String): Unit {

    //filters unnecessary elements
    var filteredString = input.filter { !it.isWhitespace() }.lowercase().toList().sorted()

    //gets set of characters without duplication
    val dist = filteredString.toSet().toList()

    for(x in dist){
        var counter = filteredString.lastIndexOf(x).plus(1).minus(filteredString.indexOf(x))
        println("There are $counter $x")
    }


}


