package kr.dataportal.calendar

/**
 * @author Heli
 * Created on 2022. 11. 26
 */
fun <T : Any> T?.notNull(): T = requireNotNull(this)
inline fun <T : Any> T?.notNull(lazyMessage: () -> Any): T = requireNotNull(this, lazyMessage)
