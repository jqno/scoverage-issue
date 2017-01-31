package issue

object Coverage {
  def regularTest(): Unit =
    println("in the regular test")

  def funTest(): Unit = {
    println("have some fun")
    println("in the fun test")
  }
}
