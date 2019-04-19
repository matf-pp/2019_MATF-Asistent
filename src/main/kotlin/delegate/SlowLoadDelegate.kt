package delegate

import tornadofx.Latch
import kotlin.reflect.KProperty

/**
 * Ovaj delegat omogućava da jedna nit čeka da neka druga nit inicijalno postavi vrednost neke promenljive,
 * olakšavajući komunikaciju između niti.
 *
 */
class SlowLoadDelegate<T: Any> {

    private lateinit var value: T

    private val initLatch = Latch()

    operator fun getValue(thisRef: Any, property: KProperty<*>): T {
        initLatch.await()
        return value
    }

    operator fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        this.value = value
        initLatch.countDown()
    }
}

fun <T: Any> slowLoad() = SlowLoadDelegate<T>()