package track.container;

import track.container.config.Bean;
import track.container.config.Property;
import track.container.config.ValueType;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Основной класс контейнера
 * У него определено 2 публичных метода, можете дописывать свои методы и конструкторы
 */
public class Container {

    private Map<String, Bean> map = new HashMap<>();
    private Map<String, Object> objById = new HashMap<>();

    // Реализуйте этот конструктор, используется в тестах!
    public Container(List<Bean> beans) throws Exception {
        for (Bean bean : beans) {
            map.put(bean.getId(), bean);
        }
    }

    public static void main(String[] args) throws Exception {

    }

    private Object createObject(Bean bean) throws Exception {
        String className = bean.getClassName();
        String id = bean.getId();
        Map<String, Property> properties = bean.getProperties();
        Class cl = Class.forName(className);

        Object obj = cl.newInstance();

        for (Map.Entry<String, Property> entry : properties.entrySet()) {
            Property property = entry.getValue();
            Field field = cl.getDeclaredField(property.getName());
            field.setAccessible(true);

            if (property.getType() == ValueType.VAL) {
                setFieldValue(obj, field, property);
            } else {
                field.set(obj, getById(property.getValue()));
            }
        }
        objById.put(id, obj);
        return obj;
    }

    private void setFieldValue(Object object, Field field, Property property) throws IllegalAccessException {
        boolean fieldIsSet = false;

        if (field.getType() == Integer.TYPE) {
            field.setInt(object, Integer.parseInt(property.getValue()));
            fieldIsSet = true;
        }
        if (field.getType() == Double.TYPE) {
            field.setDouble(object, Double.parseDouble(property.getValue()));
            fieldIsSet = true;
        }
        if (field.getType() == Short.TYPE) {
            field.setShort(object, Short.parseShort(property.getValue()));
            fieldIsSet = true;
        }
        if (field.getType() == Long.TYPE) {
            field.setLong(object, Long.parseLong(property.getValue()));
            fieldIsSet = true;
        }
        if (field.getType() == Boolean.TYPE) {
            field.setBoolean(object, Boolean.parseBoolean(property.getValue()));
            fieldIsSet = true;
        }
        if (field.getType() == Float.TYPE) {
            field.setFloat(object, Float.parseFloat(property.getValue()));
            fieldIsSet = true;
        }
        if (field.getType() == Byte.TYPE) {
            field.setByte(object, Byte.parseByte(property.getValue()));
            fieldIsSet = true;
        }
        if (!fieldIsSet) {
            field.set(object, property.getValue());
        }
    }

    /**
     *  Вернуть объект по имени бина из конфига
     *  Например, Car car = (Car) container.getById("carBean")
     */
    public Object getById(String id) throws Exception {

        if (objById.containsKey(id)) {
            return objById.get(id);

        } else if (map.containsKey(id)) {
            return createObject(map.get(id));
        }
        throw new NoSuchElementException();
    }

    /**
     * Вернуть объект по имени класса
     * Например, Car car = (Car) container.getByClass("track.container.beans.Car")
     */
    public Object getByClass(String className) throws Exception {
        String id = null;
        Iterator<Bean> beanIterator = map.values().iterator();

        while (beanIterator.hasNext()) {
            Bean current = beanIterator.next();
            if (current.getClassName().equals(className)) {
                id = current.getId();
                break;
            }
        }
        if (id != null) {
            return getById(id);
        }
        throw new NoSuchElementException();
    }

    public void printMap() {
        Iterator<String> iterator = objById.keySet().iterator();
        while (iterator.hasNext()) {
            String currentId = iterator.next();
            System.out.println(currentId + " : " + objById.get(currentId));
        }
    }
}
