package com.aoodax.jvm.common.test.toolkit

import org.apache.commons.lang3.RandomStringUtils
import org.apache.commons.lang3.RandomUtils
import org.apache.commons.lang3.StringUtils
import java.net.URL
import java.util.*
import kotlin.math.abs

abstract class Randomizer {

    companion object {
        private const val RANDOM_STRING_LENGTH = 255

        fun generateRandomUid() = UUID.randomUUID().toString()

        fun generateRandomFloat(): Float {
            return RandomUtils.nextFloat()
        }

        fun generateRandomDouble(): Double {
            return RandomUtils.nextDouble()
        }

        fun generateRandomDouble(startInclusive: Int, endExclusive: Int): Double {
            return RandomUtils.nextDouble(startInclusive.toDouble(), endExclusive.toDouble())
        }

        fun generateRandomString(): String {
            return generateRandomString(RANDOM_STRING_LENGTH)
        }

        fun generateRandomString(length: Int): String {
            return RandomStringUtils.randomAscii(length)
        }

        fun generateRandomNonAlphaNumeric(length: Int): String {
            return RandomStringUtils.random(length, false, false)
        }

        fun generateRandomAlphaNumericString(length: Int): String? {
            return RandomStringUtils.randomAlphanumeric(length)
        }

        fun generateRandomNumericString(length: Int): String {
            return RandomStringUtils.randomNumeric(length)
        }

        fun generateRandomInteger(): Int {
            return RandomUtils.nextInt()
        }

        fun generateRandomPositiveInteger(): Int {
            return abs(RandomUtils.nextInt())
        }

        fun generateRandomNegativeInteger(): Int {
            return -1 * abs(RandomUtils.nextInt())
        }

        fun generateRandomInteger(startInclusive: Int, endExclusive: Int): Int {
            return RandomUtils.nextInt(startInclusive, endExclusive)
        }

        fun generateRandomAlphabeticString(length: Int): String {
            return RandomStringUtils.randomAlphabetic(length)
        }

        fun generateRandomNegativeLong() = (Long.MIN_VALUE..0).random()

        fun generateRandomPositiveLong() = (1..Long.MAX_VALUE).random()

        fun generateRandomLong(): Long {
            return RandomUtils.nextLong()
        }

        fun generateRandomBoolean(): Boolean {
            return RandomUtils.nextBoolean()
        }

        fun generateRandomLong(startInclusive: Long, endExclusive: Long): Long {
            return RandomUtils.nextLong(startInclusive, endExclusive)
        }

        fun generateRandomEmail(): String {
            return String.format(
                "%s@%s.com",
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(5)
            )
        }

        fun generateRandomStringURL(): String {
            return String.format("http://%s", RandomStringUtils.randomAlphabetic(10))
        }

        fun generateRandomURL(): URL {
            return URL(generateRandomStringURL())
        }

        fun generateEmptyString(): String {
            return StringUtils.EMPTY
        }
    }
}