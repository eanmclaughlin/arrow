// This file was automatically generated from Iterable.kt by Knit tool. Do not edit.
package arrow.core.examples.exampleIterable14

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

import arrow.core.*

fun main(args: Array<String>) {
  //sampleStart
  val tags = List(10) { "#" }
  val result =
   tags.interleave(listOf("A", "B", "C"))
  //sampleEnd
  println(result)
}
