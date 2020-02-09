package io.jopen.springboot.plugin.mongo.template.builder;

import org.apache.logging.log4j.util.Strings;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 为了加速MongoDB的注解获取速度  采用缓存方式来
 *
 * @author maxuefeng
 */
public class BaseModel {

    // private final static Logger LOGGER = LoggerFactory.getLogger(BaseModel.class);

    public static String getCollectionName(@NonNull Class<? extends BaseModel> type) {
        Document annotation = type.getDeclaredAnnotation(Document.class);
        if (annotation == null || Strings.isBlank(annotation.collection())) {
            return type.getSimpleName();
        }
        return annotation.collection();
    }


    /**
     * 用于缓存mongo的实体类的字段
     *
     * @see SFunction 可以序列化的Function
     */
    // private static final Table<Class<? extends BaseModel>, SFunction<? extends BaseModel, ?>, String> TABLE = HashBasedTable.create();

//    static {
//        // TODO  注意此处的入参（是基于当前包进行扫描的）
//        Reflections reflections = new Reflections(BaseModel.class.getPackage().getName());
//        Set<Class<? extends BaseModel>> typeSet = reflections.getSubTypesOf(BaseModel.class);
//        typeSet.stream().filter(type -> type.getDeclaredAnnotation(Document.class) != null)
//                .forEach(type -> {
//                    Field[] fields = type.getDeclaredFields();
//                    Optional.ofNullable(fields)
//                            .filter(fs -> fs.length > 0)
//                            .flatMap((Function<Field[], Optional<Collection<Field>>>) fields1 -> Optional.of(Arrays.asList(fields1)))
//                            .ifPresent(collection -> collection.forEach(field -> {
//                                // 获取对应的注解
//                                org.springframework.data.mongodb.core.mapping.Field fieldAnnotation =
//                                        field.getDeclaredAnnotation(org.springframework.data.mongodb.core.mapping.Field.class);
//
//                                // 获取映射的字段名称
//                                String fieldMappingName = Optional.ofNullable(fieldAnnotation)
//                                        .filter(Objects::nonNull)
//                                        .map(org.springframework.data.mongodb.core.mapping.Field::value)
//                                        .orElse(field.getName());
//
//                                //
//                                SFunction<? extends BaseModel, ?> sFunction = (SFunction<BaseModel, Object>) baseModel -> {
//                                    String substring = fieldMappingName.substring(0, 1);
//                                    String temp = fieldMappingName.replaceFirst(fieldMappingName, substring.toUpperCase());
//                                    try {
//                                        Method method = baseModel.getClass().getMethod("get" + temp);
//                                        return method.invoke(baseModel);
//                                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
//                                        e.printStackTrace();
//                                        return null;
//                                    }
//                                };
//
//                                if (sFunction != null)
//                                    // 进行缓存
//                                    TABLE.put(type, sFunction, fieldMappingName);
//                            }));
//                });
//
//        LOGGER.info("加载完毕  {} ", TABLE.toString());
//
//    }
}
