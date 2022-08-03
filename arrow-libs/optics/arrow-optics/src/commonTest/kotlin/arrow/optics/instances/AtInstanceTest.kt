package arrow.optics.instances

import arrow.optics.typeclasses.At
import io.kotest.core.spec.style.StringSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.boolean
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.map
import io.kotest.property.arbitrary.set
import io.kotest.property.arbitrary.string
import io.kotest.property.arrow.core.functionAToB
import io.kotest.property.arrow.core.option
import io.kotest.property.arrow.laws.testLaws
import io.kotest.property.arrow.optics.LensLaws

class AtInstanceTest : StringSpec() {
  init {
    testLaws(
      "At map - ",
      LensLaws.laws(
        lensGen = Arb.string().map { At.map<String, Int>().at(it) },
        aGen = Arb.map(Arb.string(), Arb.int()),
        bGen = Arb.option(Arb.int()),
        funcGen = Arb.functionAToB(Arb.option(Arb.int())),
      )
    )

    testLaws(
      "At set - ",
      LensLaws.laws(
        lensGen = Arb.string().map { At.set<String>().at(it) },
        aGen = Arb.set(Arb.string()),
        bGen = Arb.boolean(),
        funcGen = Arb.functionAToB(Arb.boolean()),
      )
    )
  }
}
