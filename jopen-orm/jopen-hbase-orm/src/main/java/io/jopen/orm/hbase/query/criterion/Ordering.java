package io.jopen.orm.hbase.query.criterion;


import jdk.nashorn.internal.objects.annotations.Setter;

/**
 * Ordering criterion
 * This defines the order that results will be returned in.
 */
public class Ordering extends LambdaBuilder implements Criterion, WithProperty {

    public enum Order {
        ASCENDING, DESCENDING
    }

    /**
     * When property name is <tt>null</tt> should those values be put first or last
     */
    public enum NullOrdering {
        FIRST, LAST
    }

    private final String propertyName;
    private final Order order;
    private final NullOrdering nullOrdering;

    /**
     * Create an ordering to order results by the given property in ascending order with nulls first
     *
     * @param propertyName the name of the property to order by
     * @return the ordering
     */
    public static Ordering asc(String propertyName) {
        return new Ordering(propertyName, Order.ASCENDING, NullOrdering.FIRST);
    }

    /**
     * @param sFunction
     * @return
     * @see Ordering#asc(String)
     */
    public static Ordering asc(SFunction<?, ?> sFunction) {
        String propertyName = produceValName.apply(sFunction);
        return new Ordering(propertyName, Order.ASCENDING, NullOrdering.FIRST);
    }

    /**
     * Create an ordering to order results by the given property in descending order with nulls first
     *
     * @param propertyName the name of the property to order by
     * @return the ordering
     */
    public static Ordering desc(String propertyName) {
        return new Ordering(propertyName, Order.DESCENDING, NullOrdering.FIRST);
    }

    public static Ordering desc(SFunction<?, ?> sFunction) {
        String propertyName = produceValName.apply(sFunction);
        return new Ordering(propertyName, Order.DESCENDING, NullOrdering.FIRST);
    }

    /**
     * Create an ordering to order results by the given property in ascending order with nulls first
     *
     * @param propertyName the name of the property to order by
     * @param nullOrdering should nulls be first or last
     * @return the ordering
     */
    public static Ordering asc(String propertyName, NullOrdering nullOrdering) {
        return new Ordering(propertyName, Order.ASCENDING, nullOrdering);
    }

    public static Ordering asc(SFunction<?, ?> sFunction, NullOrdering nullOrdering) {
        String propertyName = produceValName.apply(sFunction);
        return new Ordering(propertyName, Order.ASCENDING, nullOrdering);
    }

    /**
     * Create an ordering to order results by the given property in descending order with nulls first
     *
     * @param propertyName the name of the property to order by
     * @param nullOrdering should nulls be first or last
     * @return the ordering
     */
    public static Ordering desc(String propertyName, NullOrdering nullOrdering) {
        return new Ordering(propertyName, Order.DESCENDING, nullOrdering);
    }

    public static Ordering desc(SFunction<?, ?> sFunction, NullOrdering nullOrdering) {
        String propertyName = produceValName.apply(sFunction);
        return new Ordering(propertyName, Order.DESCENDING, nullOrdering);
    }

    /**
     * Create an ordering
     *
     * @param propertyName the property to order by
     * @param order        the order that should be applied (ascending or descending)
     * @param nullOrdering whether nulls should be first or last
     */
    private Ordering(String propertyName, Order order, NullOrdering nullOrdering) {
        this.propertyName = propertyName;
        this.order = order;
        this.nullOrdering = nullOrdering;
    }

    private Ordering(SFunction<?, ?> sFunction, Order order, NullOrdering nullOrdering) {
        this.propertyName = produceValName.apply(sFunction);
        this.order = order;
        this.nullOrdering = nullOrdering;
    }



    @Override
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * Get the order to return the sorted items in
     *
     * @return ascending or descending
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Get the ordering of nulls
     *
     * @return whether nulls should be first or last
     */
    public NullOrdering getNullOrdering() {

        return nullOrdering;

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((order == null) ? 0 : order.hashCode());
        result = prime * result + ((propertyName == null) ? 0 : propertyName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ordering other = (Ordering) obj;
        if (order != other.order)
            return false;
        if (propertyName == null) {
            if (other.propertyName != null)
                return false;
        } else if (!propertyName.equals(other.propertyName))
            return false;
        return true;
    }

}
