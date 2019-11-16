package io.jopen.spark.sparksql.guide

import org.apache.spark.sql.types.{DataTypes, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}

/**
  * 指定Scheme的方式创建dataFrame
  */
object SpecifyingTheSchema {

    def main(args: Array[String]): Unit = {
        val spark = SparkSession.builder().master("local").getOrCreate()
        // 返回RDD
        val personRDD = spark.sparkContext.textFile("E:\\java-workplace\\opensource\\jopen-spark\\tmp\\perple.txt")

        val rowRDD = personRDD.map(line=>line.split(","))
              .map(meta=>Row(meta(0),meta(1),meta(2),meta(3)))


        val fields = "id,age,name,gender"
        val structFields = fields.split(",").map(fieldName=>StructField(fieldName,DataTypes.StringType,nullable = true))

        val df = spark.createDataFrame(rowRDD,new StructType(structFields))
        df.show()

        df.createOrReplaceTempView("person")
        spark.sql("select * from person").show()
    }

}
