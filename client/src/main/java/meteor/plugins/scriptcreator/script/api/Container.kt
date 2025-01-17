package meteor.plugins.scriptcreator.script.api

import meteor.api.items.Items
import net.runelite.api.InventoryID
import net.runelite.api.Item
import kotlin.random.Random


/**
 *
 * @param id requires a value of type T, which can be either a String or Int
 * @param container specifies which inventory to search in, with options being InventoryID.INVENTORY or InventoryID.BANK.
 *        The default value is InventoryID.INVENTORY.
 * @return the first instance of an Item object that matches the given id in the specified inventory
 * @see InventoryID for the available container options
 *
 * This function searches the specified inventory for an Item object with a matching id. If the id is of type Int,
 * it will match based on the Item's id field. If the id is of type String, it will match based on the Item's
 * name field (case-insensitive). If no match is found, it will throw an IllegalArgumentException.
 *
 */
inline fun <reified T> getFirst(id: T, container: InventoryID = InventoryID.INVENTORY): Item {
    return when (T::class) {
        Int::class -> {
            when (container) {
                InventoryID.BANK -> Items.getAll(InventoryID.BANK)!!.first { it.id == id }
                InventoryID.INVENTORY -> Items.getAll(InventoryID.INVENTORY)!!.first { it.id == id }
                else -> throw IllegalArgumentException("Wrong container type")
            }
        }
        String::class -> {
            when (container) {
                InventoryID.BANK -> Items.getAll(InventoryID.BANK)!!
                    .first { it.name?.lowercase() == id.toString().lowercase() }
                InventoryID.INVENTORY -> Items.getAll(InventoryID.INVENTORY)!!.first {
                    it.name?.lowercase() == id.toString().lowercase()
                }
                else -> throw IllegalArgumentException("Wrong container type")
            }
        }
        else -> throw IllegalArgumentException("Use the right type getFirst() requires String || Int")
    }
}

/**
 *
 * @param inventoryID specifies which inventory to check, with options being InventoryID.INVENTORY or InventoryID.BANK.
 *        The default value is InventoryID.INVENTORY.
 * @return a Boolean value indicating whether the specified inventory is full (contains 28 items)
 *
 * This function checks the specified inventory to determine if it is full. If the inventory contains 28 items,
 * it returns true, otherwise it returns false.
 *
 */
fun isFull(inventoryID: InventoryID = InventoryID.INVENTORY): Boolean {
    return when (inventoryID) {
        InventoryID.INVENTORY -> Items.getAll(InventoryID.INVENTORY)?.size == 28
        InventoryID.BANK -> Items.getAll(InventoryID.BANK)?.size == 28
        else -> false

    }
}


/**
 *
 * @param inventoryID specifies which inventory to check, with options being InventoryID.INVENTORY or InventoryID.BANK.
 * @return a Boolean value indicating whether the specified inventory is empty
 *
 * This function checks the specified inventory to see if it is empty. If the inventory is empty, the function
 * returns true, otherwise it returns false.
 *
 */
fun isEmpty(inventoryID: InventoryID = InventoryID.INVENTORY): Boolean {
    return when (inventoryID) {
        InventoryID.INVENTORY -> Items.getAll(InventoryID.INVENTORY)?.isEmpty() == true
        InventoryID.BANK -> Items.getAll(InventoryID.BANK)?.isEmpty() == true
        else -> false
    }
}

/**
 *
 * @param ids an Iterable of values of type T, which can be either a list of Strings or Ints
 * @return a MutableList of Item objects that match any of the given ids
 *
 * This function searches all available inventories for Item objects with ids that match any of the given ids.
 * If the ids are of type Int, it will match based on the Item's id field. If the ids are of type String, it will
 * match based on the Item's name field (case-insensitive). The function returns a MutableList of all matching
 * Item objects.
 *
 */
inline fun <reified T> getAll(ids: Iterable<T>): MutableList<Item> {
    return when (T::class) {
        Int::class -> Items.getAll()!!.filter { obj -> ids.any { it == obj.id } }.toMutableList()
        String::class -> Items.getAll()!!
            .filter { obj -> ids.any { it.toString().lowercase() == obj.name?.lowercase() } }.toMutableList()
        else -> throw IllegalArgumentException("Use the right type getAll() requires  String || Int")
    }
}

/**
 *
 * @param ids a MutableList of values of type T, which can be either a list of Strings or Ints
 * @param container specifies which inventory to search in, with options being InventoryID.INVENTORY or InventoryID.BANK.
 * @return a Boolean value indicating whether the specified inventory contains all of the given ids
 *
 * This function searches the specified inventory for Item objects with ids that match all of the given ids.
 * If the ids are of type Int, it will match based on the Item's id field. If the ids are of type String, it will
 * match based on the Item's name field (case-insensitive). If all of the given ids are found in the inventory,
 * the function returns true, otherwise it returns false.
 *
 */
inline fun <reified T> containsAll(ids: MutableList<T>, container: InventoryID): Boolean {
    ids.forEach { if (has(it, container) == true) return true }
    return false
}

/**
 *
 * @param id a value of type T, which can be either a String or Int
 * @param container specifies which inventory to search in, with options being InventoryID.INVENTORY or InventoryID.BANK.
 * @return a Boolean value indicating whether the specified inventory contains an Item object with a matching id
 *
 * This function searches the specified inventory for an Item object with an id that matches the given id.
 * If the id is of type Int, it will match based on the Item's id field. If the id is of type String, it will match
 * based on the Item's name field (case-insensitive). If a match is found, the function returns true,
 * otherwise it returns false.
 *
 */
inline fun <reified T> has(id: T, container: InventoryID): Boolean? {
    return when (T::class) {
        Int::class -> Items.getAll(container)?.any { it.id == id }
        String::class -> Items.getAll(container)?.any { it.name == id }
        else -> throw IllegalArgumentException("Wrong type use Int || String")
    }
}

/**
 *
 * @param id a value of type T, which can be either a String or Int
 * @param container specifies which inventory to search in, with options being InventoryID.INVENTORY or InventoryID.BANK.
 * @return a Boolean value indicating whether the player is carrying an Item object with a matching id in either their
 *         inventory or bank
 *
 * This function searches the player's inventory and bank for an Item object with an id that matches the given id.
 * If the id is of type Int, it will match based on the Item's id field. If the id is of type String, it will match
 * based on the Item's name field (case-insensitive). If a match is found in either location, the function returns
 * true, otherwise it returns false.
 *
 */
inline fun <reified T> isCarrying(id: T, container: InventoryID): Boolean {
    return has(id, container) == true || has(id, container) == true
}

/**
 *

@param block a block of code to be executed if a random number between 0 and 99 is less than the given Int value
This infix operator allows for a block of code to be executed based on a random probability. The given Int value
represents the probability as a percentage (e.g. 20 means there is a 20% chance of the code block being executed).
The operator generates a random number between 0 and 99, and if it is less than the given Int value, the code block
is executed.
 */
val r = Random(233)
inline infix operator fun Int.rem(block: () -> Unit) {
    if (r.nextInt(100) < this) block()
}