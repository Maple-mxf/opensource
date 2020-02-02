package io.jopen.test.spark.sparksql.guide

import org.apache.spark.sql.SparkSession

/**
  * Spark SQL的Scala接口支持自动将包含案例类的RDD转换为DataFrame。案例类定义表的架构。
  * 案例类的参数名称使用反射读取，并成为列的名称。Case类也可以嵌套或包含Seqs或Arrays之类的复杂类型。
  * 可以将该RDD隐式转换为DataFrame，然后将其注册为表。可以在后续的SQL语句中使用表。
  *
  * @see [[Seq]]
  * @see [[java.util.List]]
  * @see [[List]]
  * @see [[java.util.LinkedList]]
  */
object SchemaUsingReflection {

    case class Person(id: String, age: Int, name: String, gender: String)

    def main(args: Array[String]): Unit = {


        val spark = SparkSession.builder().master("local").getOrCreate()
        // 基于反射推断
        import spark.implicits._

        // 基于反射推断DataFrame
        val personDF = spark.sparkContext.textFile("E:\\java-workplace\\opensource\\jopen-spark\\tmp\\perple.txt")
           .map(_.split(","))
           .map(meta => Person(meta(0), Integer.parseInt(meta(1)), meta(2), meta(3)))
           .toDF()

        // 创建DataFrame
        personDF.createOrReplaceTempView("person")

        /**
          * +---+---+----+------+
          * | id|age|name|gender|
          * +---+---+----+------+
          * |  1| 18|jack|  male|
          * +---+---+----+------+
          */
        spark.sql("select * from person").show()

        // 如果不加这行语句  会出现Any class not found 错误
        implicit val mapEncoder = org.apache.spark.sql.Encoders.kryo[Map[String, Any]]
        val maps = personDF.map(person => person.getValuesMap[Any](List("id", "age", "name", "gender"))).collect()
        println(maps)
        for (elem <- maps) {
            println(elem)
        }
    }
}
