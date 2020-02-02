package io.jopen.test.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.*;
import org.testng.annotations.Test;

/**
 * @author maxuefeng
 * @since 2020/2/1
 */
public class BaseDataFrameApi {

    /**
     * 基本API测试
     */
    @Test
    public void testDataFrameSQL() {
        // 在本地测试注意master的值
        // The master URL to connect to,
        // such as "local" to run locally with one thread,
        // "local[4]" to run locally with 4 cores,
        // or "spark://master:7077" to run on a Spark standalone cluster.
        SparkConf conf = new SparkConf().setAppName("spark-test-dataframe-api").setMaster("local[2]");
        SparkSession sc = SparkSession.builder().config(conf).getOrCreate();

        Dataset<String> dataset = sc.read()
                .textFile("E:\\java-workplace\\opensource\\jopen-spark\\tmp\\perple.txt");

        // dataset-> dataframe
        // registry the dataframe
        dataset.createOrReplaceTempView("user");

        /**
         * print result
         * +------+
         * | value|
         * +------+
         * |1,male|
         * |2,jack|
         * +------+
         */
        sc.sql("select * from user").show();

        sc.close();
    }


    /**
     * 测试spark dataframe schema  （类似表格用法，需要注册表头信息）
     *
     * @see org.apache.spark.sql.types.StructType
     * @see org.apache.spark.sql.types.StructField#StructField(String, DataType, boolean, Metadata)
     */
    @Test
    public void testDataFrameSchema() {

        SparkConf conf = new SparkConf().setAppName("spark-test-dataframe-api").setMaster("local[2]");
        SparkSession sc = SparkSession.builder().config(conf).getOrCreate();

        // 1 Create an RDD of Rows from the original RDD;
        JavaRDD<Row> rdd = sc.read().textFile("E:\\java-workplace\\opensource\\jopen-spark\\tmp\\perple.txt")
                .javaRDD()
                .map(string -> {
                    String[] arr = string.split(",");
                    return RowFactory.create(Integer.parseInt(arr[0]), arr[1]);
                });

        // 2 Create the schema represented by a StructType matching the structure of Rows in the RDD created in Step 1.
        StructField[] fields = {
                new StructField("id", DataTypes.IntegerType, false, Metadata.empty()),
                new StructField("name", DataTypes.StringType, false, Metadata.empty())
        };
        StructType structType = new StructType(fields);

        // 3 Apply the schema to the RDD of Rows via createDataFrame method provided by SparkSession.
        Dataset<Row> dataFrame = sc.createDataFrame(rdd, structType);

        // 4 registry the define schema
        dataFrame.createOrReplaceTempView("user");
        sc.sql("select * from user where id = 1").show();

        // 5 close spark session
        sc.close();

        // print result
        // +---+----+
        //| id|name|
        //+---+----+
        //|  1|male|
        //+---+----+

    }

    /**
     * SparkSQL内部自定义了诸多的SQL关键字  （类似于SQL语法 不过比传统的SQL更加灵活易用）
     */
    @Test
    public void testDataframeSQL() {
        SparkConf conf = new SparkConf().setAppName("spark-test-dataframe-api").setMaster("local[2]");
        SparkSession sc = SparkSession.builder().config(conf).getOrCreate();

        JavaRDD<Row> rdd = sc.read().textFile("E:\\java-workplace\\opensource\\jopen-spark\\tmp\\user_1")
                .javaRDD()
                .map(string -> {
                    // 使用空格进行切割字符串
                    String[] arr = string.split(",");
                    return RowFactory.create(Integer.parseInt(arr[0]), arr[1], Integer.parseInt(arr[2]), arr[3]);
                });

        StructField[] fields = {
                new StructField("id", DataTypes.IntegerType, false, Metadata.empty()),
                new StructField("name", DataTypes.StringType, false, Metadata.empty()),
                new StructField("age", DataTypes.IntegerType, false, Metadata.empty()),
                new StructField("job", DataTypes.StringType, false, Metadata.empty()),
        };
        StructType structType = new StructType(fields);

        Dataset<Row> dataFrame = sc.createDataFrame(rdd, structType);

        // 4 registry the define schema
        dataFrame.createOrReplaceTempView("user");

        // Spark SQL
        // print result
        // +---------+
        //|(age % 2)|
        //+---------+
        //|        0|
        //|        1|
        //+---------+
        // 测试取模运算
        sc.sql("select age%2 from user").show();

        // 测试数学函数运算
        // +--------+
        //|avg(age)|
        //+--------+
        //|    23.5|
        //+--------+
        sc.sql("select avg(age) from user").show();

        // 测试聚合操作
        // print result
        // +----+
        //|name|
        //+----+
        //| Tom|
        //|null|
        //|Jack|
        //|null|
        //|null|
        //| Tom|
        //|Jack|
        //+----+
        sc.sql("select name from user group by cube(name,age)").show();


        // 5 close spark session
        sc.close();
    }

    // source 读入测试（I）
    // sink 存储测试（O）
    @Test
    public void testSparkSQLDataSourceSink() {

        // 注意  此处设定的运行的环境切换到远程环境
        SparkConf conf = new SparkConf().setAppName("testSparkSQLDataSource").setMaster("spark://192.168.74.136:7077");


        SparkSession sc = SparkSession.builder()
                // 添加Hive支持
                .enableHiveSupport()
                .config(conf)
                .getOrCreate();

        // Text File （文本读入）

        // =========================================================================================================
        // JDBC datasource( 比如MySQL/Oracle)
        Dataset<Row> jdbcDataset = sc.read().format("jdbc")
                // 设定JDBC URL
                .option("url", "jdbc:mysql://192.168.74.136:3306/bigdata")
                .option("dbtable", "brand")
                .option("driver", "com.mysql.jdbc.Driver")
                .option("user", "root")
                .option("password", "121101mxf@@ForBigData")
                .load();

        // print result
        // +-------------------+--------+---------+
        //|                 id| cn_name|  en_name|
        //+-------------------+--------+---------+
        //|B608509717006077952|    华为|   HUAWEI|
        //|B608509717006077953|    小米|       MI|
        //|B608509717006077954|   Apple|     null|
        //|B608509717006077955|    魅族|    MEIZU|
        //|B608509717006077956|    三星|  SAMSUNG|
        //|B608509717006077957|  诺基亚|    NOKIA|
        //|B608509717014466560|    OPPO|     null|
        //|B608509717014466561|    vivo|     null|
        //|B608509717014466562|    黑鲨|     null|
        //|B608509717014466563|    联想|   Lenovo|
        //|B608509717014466564|    一加|     null|
        //|B608509717014466565|     360|     null|
        //|B608509717014466566|  飞利浦|  PHILIPS|
        //|B608509717014466567|    荣耀|     null|
        //|B608509717014466568|  努比亚|    nubia|
        //|B608509717014466569|    锤子|smartisan|
        //|B608509717014466571|    索尼|     SONY|
        //|B608509717014466572|    美图|    meitu|
        //|B608509717014466573|     HTC|     null|
        //|B608509717014466574|摩托罗拉| Motorola|
        //+-------------------+--------+---------+
        jdbcDataset.show();


        // =========================================================================================================
        // JSON datasource (比如JSON文件)
        Dataset<Row> dataset = sc.read().json("E:\\java-workplace\\opensource\\jopen-spark\\tmp\\source.json");
        dataset.createOrReplaceTempView("user_json");
        // print result
        // +---+---+--------+----+
        //|age| id|     job|name|
        //+---+---+--------+----+
        //| 18|  1|职业法师|Jack|
        //+---+---+--------+----+
        sc.sql("select * from user_json where age=18").show();


        // =========================================================================================================
        // Hive datasource (Hive数仓)
        // Hive跟一般数据源的使用有所差距   一般我们直接读取  无需设定option选项(Spark远程读取可能会出现序列化错误  这是因为网络底层编码造成的)
        // 切换数据库
        sc.sql("use spark;");
        // 选择指定表格  此处测试需要打jar包spark机器上运行（附带jar依赖  否则会出现一系列的依赖的）
        sc.table("consumptionrecords").show();


        sc.close();
    }


    @Test
    public void testSparkSQLOtherDataSource() {

        SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("testSparkSQLOtherDataSource");
        SparkSession sc = SparkSession.builder().config(conf).getOrCreate();

        // 读取MongoDB数据源(Spark官方并没有直接提供mongo的connector)
    }
}
