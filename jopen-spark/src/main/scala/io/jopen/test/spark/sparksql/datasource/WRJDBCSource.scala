package io.jopen.test.spark.sparksql.datasource

import org.apache.spark.sql.{SaveMode, SparkSession}


object WRJDBCSource {

    class Star(id: String, star: Integer)

    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().master("local").getOrCreate()

        // 读MySQL的一个数据库表格
        val jdbcDF1 = spark.read
           .format("jdbc")
           .option("url", "jdbc:mysql://192.168.74.136:3306/test")
           .option("dbtable", "video")
           // table和query是冲突的  不能同时指定
           // .option("query","select * from sys_user")
           // https://spark.apache.org/docs/latest/sql-data-sources-jdbc.html
           .option("user", "root")
           .option("password", "121101mxf@@ForBigData")
           .load()

        jdbcDF1.show()
        
        // val df = jdbcDF1.toDF("id", "star").selectExpr("id", "star+1000 as star")
        // TODO   在写入mysql的时候列名只能写成 _1 _2 从_1开始
        val df = jdbcDF1.toDF().selectExpr("_1", "_2 + 1000 as _2")
        df.show()

        df.write
           .format("jdbc")
           .option("url", "jdbc:mysql://192.168.74.136:3306/test")
           .option("dbtable", "video")
           // table和query是冲突的  不能同时指定
           // .option("query","select * from sys_user")
           // https://spark.apache.org/docs/latest/sql-data-sources-jdbc.html
           .option("user", "root")
           .option("password", "121101mxf@@ForBigData")
           // 如果数据存在 则覆盖（可能会删除全表）
           .mode(saveMode = SaveMode.Append)
           .saveAsTable("video")

    }
}
