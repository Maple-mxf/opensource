package io.jopen.springboot.plugin.mongo.template.builder;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {@link Aggregation}
 *
 * @author maxuefeng
 * @since 2019-11-13
 */
public class AggregationBuilder<T> extends Builder<T> {

    private List<AggregationOperation> aggregations = new ArrayList<>();

    private AggregationBuilder(Class<T> targetClass) {
        this(targetClass, null);
    }

    private AggregationBuilder(Class<T> targetClass, MongoTemplate mongoTemplate) {
        this.clazz = targetClass;
        this.mongoTemplate = mongoTemplate;
    }


    public static <V> AggregationBuilder<V> builderFor(@NonNull Class<V> classType) {
        return new AggregationBuilder<>(classType);
    }

    public AggregationBuilder<T> match(SFunction<T, ?> matchFs, Object object) {
        String colName = this.produceValName.apply(matchFs);
        MatchOperation matchOperation = Aggregation.match(Criteria.where(colName).is(object));
        aggregations.add(matchOperation);
        return this;
    }

    public AggregationBuilder<T> like(@NonNull SFunction<T, ?> matchFs, @NonNull Object object) {
        String colName = this.produceValName.apply(matchFs);
        MatchOperation matchOperation = Aggregation.match(Criteria.where(colName).regex(object.toString()));
        aggregations.add(matchOperation);
        return this;
    }

    /**
     * 随机查询
     *
     * @param limit 数据条数
     * @return this
     */
    public AggregationBuilder<T> sample(int limit) {
        aggregations.add(Aggregation.sample(limit));
        return this;
    }

    /**
     * db.c_consumption_credential.aggregate([
     * { $match : { uid : "662533264996569088" } },
     * { $group: { _id: "$uid", "totalResult":{"$sum":"$totalAmount"} } }
     * ])
     *
     * @param sumCol
     * @param asName
     * @param groupCols
     * @return
     */
    public final AggregationBuilder<T> groupByAndSingleFieldSum(SFunction<T, ?> sumCol, String asName, List<SFunction<T, ?>> groupCols) {
        String[] groupColNames = new String[groupCols.size()];
        for (int i = 0; i < groupColNames.length; i++) {
            groupColNames[i] = this.produceValName.apply(groupCols.get(i));
        }
        String sumColName = this.produceValName.apply(sumCol);
        GroupOperation groupOperation = Aggregation.group(groupColNames).sum(sumColName).as(asName);
        aggregations.add(groupOperation);
        return this;
    }


    /**
     * 对于多个字段进行求和操作
     * <p>
     * db.c_consumption_credential.aggregate([
     * { "$match" : { "uid" : "662533264996569088" } },
     * { "$match" : { "adopt" : 1 } },
     * { "$group" : { "_id" : "$uid", "totalResult" : { "$sum" : "$totalAmount" } , "total" : { "$sum" : "$commission" }} }
     * ])
     *
     * @param sumColAndAlias
     * @param groupCols
     * @return
     */
    public final AggregationBuilder<T> groupByAndSumMultiFieldSum(List<SFunction<T, ?>> groupCols, Map<SFunction<T, ?>, String> sumColAndAlias) {

        String[] groupColNames = new String[groupCols.size()];
        for (int i = 0; i < groupColNames.length; i++) {
            groupColNames[i] = this.produceValName.apply(groupCols.get(i));
        }

        GroupOperation groupOperation = Aggregation.group(groupColNames);

        for (Map.Entry<SFunction<T, ?>, String> entry : sumColAndAlias.entrySet()) {
            groupOperation = groupOperation.sum(this.produceValName.apply(entry.getKey())).as(entry.getValue());
        }

        this.aggregations.add(groupOperation);
        return this;
    }


    /**
     * <code>GroupOperation.group()参数不可为空  虽然是可变参数  SpringBoot留下的问题</code>
     * <code>error demo GroupOperation groupOperation = Aggregation.group().sum(this.produceValName.apply(sumCol)).as(asName);</code>
     * <code>right demo CountOperation countOperation = new CountOperation(this.produceValName.apply(sumCol));</code>
     *
     * @param asName 别名
     * @return
     * @see CountOperation#CountOperation(String)
     */
    public final AggregationBuilder<T> count(String asName) {
        aggregations.add(Aggregation.count().as(asName));
        return this;
    }


    public Aggregation build() {
        return Aggregation.newAggregation(aggregations);
    }

    public AggregationBuilder<T> gte(SFunction<T, ?> col, Object val) {
        MatchOperation gteOperation = Aggregation.match(Criteria.where(this.produceValName.apply(col)).gte(val));
        aggregations.add(gteOperation);
        return this;
    }

    public AggregationBuilder<T> lte(SFunction<T, ?> col, Object val) {
        MatchOperation gteOperation = Aggregation.match(Criteria.where(this.produceValName.apply(col)).lte(val));
        aggregations.add(gteOperation);
        return this;
    }
}
