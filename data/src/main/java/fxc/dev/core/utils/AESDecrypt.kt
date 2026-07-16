package fxc.dev.core.utils

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class AESDecrypt(
    private val initializationVector: String,
    private val secretKey: String
) {
    fun decrypt(
        textToDecrypt: String
    ): ByteArray {
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")

        val ivSpec = IvParameterSpec(initializationVector.toByteArray())
        val secretKeySpec = SecretKeySpec(secretKey.toByteArray(), "AES")

        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec)
        val cipherText = cipher.doFinal(Base64.decode(textToDecrypt, Base64.DEFAULT))

        val sb = StringBuilder()
        for (b in cipherText) {
            sb.append(b.toInt().toChar())
        }
        return cipherText
    }
}