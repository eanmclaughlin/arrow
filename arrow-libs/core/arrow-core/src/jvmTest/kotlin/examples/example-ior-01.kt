// This file was automatically generated from Ior.kt by Knit tool. Do not edit.
package arrow.core.examples.exampleIor01

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

import arrow.core.Ior

fun main() {
  Ior.Left("tulip").isRight           // Result: false
  Ior.Right("venus fly-trap").isRight // Result: true
  Ior.Both("venus", "fly-trap").isRight // Result: false
}
