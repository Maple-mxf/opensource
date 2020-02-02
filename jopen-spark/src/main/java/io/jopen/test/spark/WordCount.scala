package io.jopen.test.spark

object WordCount {
  def main(args: Array[String]): Unit = {
    /*val conf = new SparkConf().setAppName("sparkWordCount").setMaster("")

    val context = new SparkContext(conf)

    val lines = context.textFile("E:\\java-workplace\\opensource\\jopen-spark\\src\\main\\java\\io\\jopen\\spark\\1.txt")

    lines.foreach(println)*/

    // getWordCount(List('Hello'), Map("age" -> 1))
    val a: List[String] = List("HelloWorld")
    val b: Map[String, Any] = Map("age" -> 1)
    val c: Function[String, String] = { P => P.toUpperCase }

    val d: Function[String, String] = p => p.toUpperCase
  }


  def getWordCount(list: List[Char], map: Map[Char, Int]): Unit = {

    println(list)
    println(map)
  }

}
