package io.jopen.core.common.proxology.handlers;

import io.jopen.core.common.proxology.EqualisableByState;
import io.jopen.core.common.proxology.handlers.early.PropertyMappingClassInterpreter;

import java.util.Map;
import java.util.Objects;

import static io.jopen.core.common.proxology.handlers.MethodInterpreters.binding;
import static io.jopen.core.common.proxology.handlers.MethodInterpreters.handlingDefaultMethods;


/**
 * @author maxuefeng
 * @see java.lang.reflect.Method
 */
public class PropertyValueStore implements EqualisableByState {

    private final Class<?> iface;
    private final Map<String, Object> propertyValues;

    public PropertyValueStore(Class<?> iFace, Map<String, Object> propertyValues) {
        this.iface = iFace;
        this.propertyValues = propertyValues;
    }

    @Override
    public int hashCode() {
        return propertyValues.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s %s", iface, propertyValues);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PropertyValueStore)) {
            return false;
        }

        PropertyValueStore other = (PropertyValueStore) o;
        return Objects.equals(iface, other.iface)
                && Objects.equals(propertyValues, other.propertyValues);
    }

    public MethodInterpreter createMethodInterpreter() {
        return binding(this,
                handlingDefaultMethods(
                        PropertyMappingClassInterpreter.interpret(iface).bind(propertyValues)));
    }

    @Override
    public Object getState() {
        return this;
    }
}
