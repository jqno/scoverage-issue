package coverage

import org.scalatest._

class TestIntSpec extends FlatSpec with Matchers {
  it should "cover funTestMethod" in {
    Coverage.funTestMethod()
  }
}

