// This file was automatically generated from Either.kt by Knit tool. Do not edit.
package arrow.core.examples.exampleEither09

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

import arrow.core.Either
import arrow.core.flatMap

fun parse(s: String): Either<NumberFormatException, Int> =
  if (s.matches(Regex("-?[0-9]+"))) Either.Right(s.toInt())
  else Either.Left(NumberFormatException("$s is not a valid integer."))

fun reciprocal(i: Int): Either<IllegalArgumentException, Double> =
  if (i == 0) Either.Left(IllegalArgumentException("Cannot take reciprocal of 0."))
  else Either.Right(1.0 / i)

fun stringify(d: Double): String = d.toString()

fun magic(s: String): Either<Exception, String> =
  parse(s).flatMap{ reciprocal(it) }.map{ stringify(it) }

val magic0 = magic("0")
val magic1 = magic("1")
val magicNotANumber = magic("Not a number")
fun main() {
 println("magic0 = $magic0")
 println("magic1 = $magic1")
 println("magicNotANumber = $magicNotANumber")
}
