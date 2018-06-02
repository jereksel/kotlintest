package io.kotlintest.specs

import io.kotlintest.AbstractSpec
import io.kotlintest.TestContext
import io.kotlintest.TestType

@Suppress("FunctionName")
abstract class AbstractBehaviorSpec(body: AbstractBehaviorSpec.() -> Unit = {}) : AbstractSpec() {

  init {
    body()
  }

  @KotlinTestDsl
  inner class GivenContext(val context: TestContext) {
    fun And(name: String, test: suspend WhenContext.() -> Unit) = and(name, test)
    fun and(name: String, test: suspend WhenContext.() -> Unit) = add("Add: $name", test)
    fun When(name: String, test: suspend WhenContext.() -> Unit) = `when`(name, test)
    fun `when`(name: String, test: suspend WhenContext.() -> Unit) = add("When: $name", test)
    private fun add(name: String, test: suspend WhenContext.() -> Unit) =
        context.registerTestCase(name, this@AbstractBehaviorSpec, { this@AbstractBehaviorSpec.WhenContext(this).test() }, this@AbstractBehaviorSpec.defaultTestCaseConfig, TestType.Container)
  }

  @KotlinTestDsl
  inner class WhenContext(val context: TestContext) {
    fun Then(name: String, test: suspend TestContext.() -> Unit) = then(name, test)
    fun then(name: String, test: suspend TestContext.() -> Unit) =
        context.registerTestCase("Then: $name", this@AbstractBehaviorSpec, test, this@AbstractBehaviorSpec.defaultTestCaseConfig, TestType.Test)
  }

  fun Given(name: String, test: suspend GivenContext.() -> Unit) = given(name, test)
  fun given(name: String, test: suspend GivenContext.() -> Unit) =
      addTestCase("Given: $name", { this@AbstractBehaviorSpec.GivenContext(this).test() }, defaultTestCaseConfig, TestType.Container)
}
