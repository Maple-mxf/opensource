package io.jopen.spark.scala

import org.apache.spark.scheduler.SparkListenerInterface
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Spark 下载地址 https://spark.apache.org/downloads.html
  *
  * @author maxuefeng
  */
object Base {

  def main(args: Array[String]): Unit = {
    println("HelloWorld")
    val conf = new SparkConf().setMaster("").setAppName("")
    val context = new SparkContext(conf)
  }

}
