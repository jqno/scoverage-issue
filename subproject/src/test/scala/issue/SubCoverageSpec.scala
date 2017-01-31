package issue

import org.scalatest.FlatSpec

class SubCoverageSpec extends FlatSpec {
  it should "cover regular" in {
    SubCoverage.regularTest()
  }
}
