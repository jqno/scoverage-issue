package coverage

import org.scalatest._

class TestSpec extends FlatSpec with Matchers {
  it should "cover testMethod" in {
    Coverage.testMethod()
  }
}
