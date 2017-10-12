package utils

import scala.util.Random

object StringUtils {

        def randomString(length: Int): String = {
                var word: String = ""
                for (i <- 1 to length) {
                        word += Random.alphanumeric.toString()
                }
                return word
        }

}