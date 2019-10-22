package io.jopen.spark

import org.apache.spark.{SparkConf, SparkContext}

object WordCount {
  def main(args: Array[String]): Unit = {
    val conf  = new SparkConf().setAppName("sparkWordCount").setMaster("")

    val context = new SparkContext(conf)

    val lines = context.textFile("E:\\java-workplace\\opensource\\jopen-spark\\src\\main\\java\\io\\jopen\\spark\\1.txt")

    lines.foreach(println)
  }

}
