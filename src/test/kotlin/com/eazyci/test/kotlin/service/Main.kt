package test.kotlin.com.eazyci.test.kotlin.service

import org.junit.Test

class Main {

    @Test
    fun `test main stuff`() {
        println("unit-test-1")
        assert(true)
    }

    @Test
    fun `test main stuff fail`() {
        println("unit-test-1")
        assert(true)
    }
}