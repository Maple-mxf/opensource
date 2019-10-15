package core.common.proxology.beans;

import io.jopen.core.common.proxology.EqualisableByState;
import io.jopen.core.common.proxology.handlers.MethodInterpreter;

import java.util.Arrays;
import java.util.Objects;

import static io.jopen.core.common.proxology.handlers.MethodInterpreters.binding;

/**
 * @author maxuefeng
 */
public final class BeanProxyStorage implements EqualisableByState {

    private final BeanProxySchema schema;

    private final Object[] values;

    BeanProxyStorage(BeanProxySchema schema, Object[] values) {
        this.schema = schema;
        this.values = values;
    }

    public Object get(int index) {
        return values[index];
    }

    public Object set(int index, Object value) {
        values[index] = value;
        return null;
    }

    @Override
    public String toString() {
        return schema.formatValues(values);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BeanProxyStorage)) {
            return false;
        }

        BeanProxyStorage other = (BeanProxyStorage) o;
        return Objects.equals(schema, other.schema)
                && Arrays.deepEquals(values, other.values);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(values);
    }

    public MethodInterpreter getMethodInterpreter() {
        return binding(this, schema.getMethodInterpreter(this));
    }

    @Override
    public Object getState() {
        return this;
    }
}
