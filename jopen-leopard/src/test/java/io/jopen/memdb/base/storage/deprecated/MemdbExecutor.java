package io.jopen.memdb.base.storage.deprecated;

import com.google.common.base.Preconditions;
import io.jopen.memdb.base.storage.client.IntermediateExpression;
import io.jopen.memdb.base.storage.client.OperationType;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author maxuefeng
 * @see IntermediateExpression
 * @since 2019/10/23
 */
@Deprecated
final
class MemdbExecutor {

    private JavaModelTable<Object> currentTable;
    private OperationType operationType;

    MemdbExecutor(OperationType operationType) {
        Preconditions.checkNotNull(operationType);
        this.operationType = operationType;
    }

    /**
     * @param expression
     * @param <T>
     * @return 使用Stream高阶变成
     * @see MemdbTemplateImpl 相当于上下文对象
     */
    <T> Landing<T> input(IntermediateExpression<T> expression) {

        // 获取expression的泛型
//        currentTable = MemdbTemplateImpl.getInstance()
//                .getCurrentDatabase().getTable(expression.getTargetClass());

        if (currentTable == null) {
            return new Landing<>(Stream.of());
        }

        // 表格中所有数据
        List<T> cells = (List<T>) currentTable.queryAll();

        if (OperationType.SELECT.equals(this.operationType) || OperationType.UPDATE.equals(this.operationType) || OperationType.DELETE.equals(this.operationType)) {
            // 数据按照条件过滤
            return new Landing<>(filter(expression, cells));
        } else {
            throw new UnsupportedOperationException(String.format("not support operation, OperationType [%s] ", this.operationType));
        }
    }


    private <T> Stream<T> filter(IntermediateExpression<T> expression, List<T> cells) {

        // 获取断言集合
        List<IntermediateExpression.Condition> conditions = expression.getConditions();

        // 数据按照条件过滤
        return cells.stream().filter(cell -> {
            for (IntermediateExpression.Condition condition : conditions) {
                boolean test = condition.test(cell);
                if (!test) {
                    return test;
                }
            }
            return true;
        });
    }


    final class Landing<T> {

        private Stream<T> stream;

        Landing(Stream<T> stream) {
            this.stream = stream;
        }


        public List<T> selectList() {
            if (!MemdbExecutor.this.operationType.equals(OperationType.SELECT)) {
                throw new RuntimeException(String.format("current operationType error ,expection optionType is [%s] ", OperationType.SELECT));
            }
            // 收集数据;进行返回
            return stream.collect(Collectors.toList());
        }

        public boolean delete() {
            if (!MemdbExecutor.this.operationType.equals(OperationType.DELETE)) {
                throw new RuntimeException(String.format("current operationType error ,expection optionType is [%s] ", OperationType.DELETE));
            }
            return MemdbExecutor.this.currentTable.queryAll().removeAll(stream.collect(Collectors.toList()));
        }

        public T selectOne() {
            if (!MemdbExecutor.this.operationType.equals(OperationType.SELECT)) {
                throw new RuntimeException(String.format("current operationType error ,expection optionType is [%s] ", OperationType.SELECT));
            }
            List<T> collectRes = stream.collect(Collectors.toList());

            if (collectRes.size() == 0) {
                return null;
            }

            if (collectRes.size() > 1) {
                System.err.println(String.format("warn  result  size  [%s] gt  1 ", collectRes.size()));
            }
            return collectRes.get(0);
        }

        public void updateBatch() {
            if (!MemdbExecutor.this.operationType.equals(OperationType.UPDATE)) {
                throw new RuntimeException(String.format("current operationType error ,expection optionType is [%s] ", OperationType.UPDATE));
            }

            // 数据收集
            List<T> collectRes = stream.collect(Collectors.toList());

            // 进行修改数据
        }

    }

}
