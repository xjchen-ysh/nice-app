package com.zyr.nice.util

import com.zyr.nice.core.config.Config

object ResourceUtil {

    /**
     * 将相对资源转为绝对路径
     *
     * @param uri 服务器中的资源路径
     */
    fun r(uri: String): String {
        return if (uri.startsWith("http"))
            uri
        else
            String.format(Config.RESOURCE_ENDPOINT, uri)
    }

}