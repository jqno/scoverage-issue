package issue

object SubCoverage {
  def regularTest(): Unit =
    println("in the regular sub test")

  def funTest(): Unit = {
    println("have some sub fun")
    println("in the sub fun test")
  }
}
