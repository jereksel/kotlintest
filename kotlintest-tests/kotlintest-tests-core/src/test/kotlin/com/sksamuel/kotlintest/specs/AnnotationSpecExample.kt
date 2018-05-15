package com.sksamuel.kotlintest.specs

import io.kotlintest.shouldBe
import io.kotlintest.specs.AbstractAnnotationSpec
import io.kotlintest.specs.IntelliMarker
import io.kotlintest.specs.Test
import kotlinx.coroutines.experimental.delay

class AnnotationSpecExample : AbstractAnnotationSpec() {

  @Test
  fun test1() {
    1 shouldBe 1
  }

  @Test
  fun test2() {
    1 shouldBe 1
  }

  @Test
  suspend fun test3() {
    delay(10)
    1 shouldBe 1
  }

}