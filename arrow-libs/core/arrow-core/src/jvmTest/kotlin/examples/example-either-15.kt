// This file was automatically generated from Either.kt by Knit tool. Do not edit.
package arrow.core.examples.exampleEither15

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

val r : Either<Int, Int> = Either.Right(7)
val rightMapLeft = r.mapLeft {it + 1}
val l: Either<Int, Int> = Either.Left(7)
val leftMapLeft = l.mapLeft {it + 1}
fun main() {
 println("rightMapLeft = $rightMapLeft")
 println("leftMapLeft = $leftMapLeft")
}
