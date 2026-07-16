package fxc.dev.core.utils.interceptor

import fxc.dev.core.utils.AESDecrypt
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.nio.charset.Charset
import javax.inject.Inject

open class DecryptInterceptor @Inject constructor(
    private val aesDecrypt: AESDecrypt
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain
        .run { proceed(request()) }
        .let { response ->
            return@let if (response.isSuccessful && response.body != null) {
                val body = response.body!!

                val contentType = body.contentType()
                val charset = contentType?.charset() ?: Charset.defaultCharset()
                val buffer = body.source().apply { request(Long.MAX_VALUE) }.buffer
                val bodyContent = buffer.clone().readString(charset)

                response.newBuilder()
                    .body(bodyContent.let(::decryptBody).toResponseBody(contentType))
                    .build()
            } else response
        }

    private fun decryptBody(content: String): String {
        return aesDecrypt.decrypt(content).decodeToString()
    }
}