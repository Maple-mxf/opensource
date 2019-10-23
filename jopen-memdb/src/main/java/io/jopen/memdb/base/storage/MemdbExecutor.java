package io.jopen.memdb.base.storage;

import com.google.common.base.Preconditions;

/**
 * @author maxuefeng
 * @see IntermediateExpression
 * @since 2019/10/23
 */
final
class MemdbExecutor {

    private OperationType operationType;

    MemdbExecutor(OperationType operationType) {
        Preconditions.checkNotNull(operationType);
        this.operationType = operationType;
    }


    <T> void execute(IntermediateExpression<T> expression) {

        switch (this.operationType) {
            case SELECT: {
                expression
            }
            break;
            case DELETE: {

            }
            break;
            case INSERT: {

            }
            break;
            case UPDATE: {

            }
            break;
            default: {
                System.err.println(String.format("executor %s complete", this.operationType));
            }

        }

    }

}
