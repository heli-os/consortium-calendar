package kr.dataportal.calendar

import java.security.MessageDigest

/**
 * @author Heli
 * Created on 2022. 11. 26
 */
fun hashSHA512(message: String) = run {
    MessageDigest.getInstance("SHA-512").let {
        it.update(message.toByteArray())
        it.digest().toHexString()
    }
}

private const val DIGITS = "0123456789ABCDEF"

private fun ByteArray.toHexString(): String {
    val hexChars = CharArray(this.size * 2)
    for (i in this.indices) {
        val v = this[i].toInt() and 0xff
        hexChars[i * 2] = DIGITS[v shr 4]
        hexChars[i * 2 + 1] = DIGITS[v and 0xf]
    }
    return String(hexChars)
}
