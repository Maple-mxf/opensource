package io.jopen.test.spark;

import com.google.common.collect.ImmutableList;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.junit.Test;

/**
 * @author maxuefeng
 * @since 2020/2/1
 */
public class BaseSparkActionOperator {

    // Action operator
    @Test
    public void testBaseOperator() {

        SparkConf conf = new SparkConf().setMaster("local");
        SparkSession sc = SparkSession.builder().config(conf).getOrCreate();

        People p1 = new People();
        p1.setId(1);
        p1.setAge(19);
        p1.setJob("职业法师");
        p1.setName("Jack");

        People p2 = new People();
        p2.setId(2);
        p2.setAge(29);
        p2.setJob("职业ADC");
        p2.setName("Tom");

        // Dataset<Row> dataFrame = sc.createDataFrame(ImmutableList.of(p1, p2), People.class);

        Dataset<People> dataset = sc.createDataset(ImmutableList.of(p1, p2), Encoders.kryo(People.class));
        dataset.map((MapFunction<People, People>) value -> {
            value.setName(value.getName().toUpperCase());
            return value;
        },Encoders.kryo(People.class)).show();

    }
}
