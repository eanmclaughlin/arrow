// This file was automatically generated from Control.kt by Knit tool. Do not edit.
package arrow.core.examples.exampleCont03

import arrow.*
import arrow.core.*
import arrow.core.coroutines.*
import kotlinx.coroutines.*
import io.kotest.matchers.collections.*
import io.kotest.assertions.*
import io.kotest.matchers.*
import io.kotest.matchers.types.*
import kotlin.coroutines.cancellation.CancellationException
import io.kotest.property.*
import io.kotest.property.arbitrary.*
import arrow.core.test.generators.*

suspend fun test() = checkAll(Arb.string()) { str ->
  control<String, Int> {
    shift(str)
  }.fold({ it shouldBe str }, { fail("Computation never finishes") })
}
