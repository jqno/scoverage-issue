package issue

import org.scalatest.FlatSpec

class CoverageSpec extends FlatSpec {
  it should "cover regular" in {
    Coverage.regularTest()
  }
}
