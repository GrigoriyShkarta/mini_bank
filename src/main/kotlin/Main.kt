import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.random.Random

data class Person(val id: Long, val name: String)

data class Transfer(val sender: Person, val recipient: Person, val amount: Double)

data class WalletMoves(val person: Person, val correspondent: Person, val amount: Double)

fun main() {

    val persons = listOf(
        Person(1L, "Ben"),
        Person(2L, "Michael"),
        Person(1L, "Maks"),
        Person(1L, "Anna"),
    )

    val transfers = List(5) {
        val person1 = persons.random()
        val person2 = persons.filter { it != person1 }.random()
        Transfer(
            person1,
            person2,
            BigDecimal(Random.nextDouble(1.0, 100.0))
                .setScale(2, RoundingMode.HALF_UP)
                .toDouble()
        )
    }

    val wallets = mutableMapOf<Person, Double>()
    for (person in persons) {
        wallets[person] = 0.0
    }

    val walletMoves = mutableListOf<WalletMoves>()
    for (transfer in transfers) {
        val sender = transfer.sender
        val recipient = transfer.recipient
        val amount = transfer.amount

        wallets[sender] = wallets[sender]!! - amount
        wallets[recipient] = wallets[recipient]!! + amount

        walletMoves.add(WalletMoves(sender, recipient, -amount))
        walletMoves.add(WalletMoves(recipient, sender, amount))
    }

    println("----------TRANSFERS----------")
    println()

    for (transfer in transfers) {
        println(transfer)
    }

    println()
    println("----------MOVES----------")
    println()

    for (moves in walletMoves) {
        println(moves)
    }

    println()
    println("----------BALANCES----------")
    println()

    for ((person, balance) in wallets) {
        println("$person, $balance")
    }
}