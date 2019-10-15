package core.function.util;

import io.jopen.core.function.StreamCheckedFunction;

import java.util.Optional;
import java.util.function.Function;

/**
 * 使用 stream 时，如果发生异常，我们可能不希望停止处理。如果你的 stream 包含大量需要处理的项目，
 * 我们不希望在处理集合的过程中因为发生异常而导致Stream停止处理集合。
 * 那我们可以换一种方式来思考，我们可以把 “异常情况” 下产生的结果，想象成一种特殊性的成功的结果。
 * 那我们可以把他们都看成是一种数据，不管成功还是失败，都继续处理流，然后决定如何处理它。我们可以
 * 这样做，这就是我们需要引入的一种新类型 - Either类型。
 * Either 类型是函数式语言中的常见类型，而不是 Java 的一部分。与 Java 中的 Optional 类型类似，
 * 一个 Either 是具有两种可能性的通用包装器。它既可以是左派也可以是右派，但绝不是两者兼而有之。
 * 左右两种都可以是任何类型。
 * <p>
 * 例如，如果我们有一个 Either 值，那么这个值可以包含 String 类型或 Integer 类型：Either。
 * 如果我们将此原则用于异常处理，我们可以说我们的 Either 类型包含一个 Exception 或一个成功的值。
 * 为了方便处理，通常左边是 Exception，右边是成功值。
 *
 * @author maxuefeng
 */
public class Either<L, R> {

    // 异常信息
    private final L left;

    // 正常信息
    private final R right;

    private Either(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public static <L, R> Either<L, R> Left(L left) {
        return new Either<>(left, null);
    }


    public static <L, R> Either<L, R> Right(R right) {
        return new Either<>(null, right);
    }

    public Optional<L> left() {
        return Optional.ofNullable(left);
    }

    public Optional<R> right() {
        return Optional.ofNullable(right);
    }

    public boolean isRight() {
        return right != null;
    }

    public boolean isLeft() {
        return left != null;
    }

    /**
     * 匹配左边并且处理数据
     *
     * @param mapper
     * @param <T>
     * @return
     */
    public <T> Optional<T> mapLeft(Function<? super L, T> mapper) {

        if (isLeft()) {
            return Optional.of(mapper.apply(left));
        }

        return Optional.empty();
    }

    /**
     * 匹配右边并且处理数据
     *
     * @param mapper
     * @param <T>
     * @return
     */
    public <T> Optional<T> mapRight(Function<? super R, T> mapper) {

        if (isRight()) {
            return Optional.of(mapper.apply(right));
        }
        return Optional.empty();
    }


    /**
     * @param function
     * @param <T>
     * @param <R>
     * @return
     * @see Either#liftWithValue(StreamCheckedFunction)
     */
    public static <T, R> Function<T, Either> lift(StreamCheckedFunction<T, R> function) {
        return t -> {
            try {
                // 正常数据在右侧
                return Either.Right(function.apply(t));
            } catch (Exception e) {
                // 异常保存在左侧
                return Either.Left(e);
            }
        };
    }


    /**
     * 上一个方法的加强
     *
     * @param function
     * @param <T>
     * @param <R>
     * @return
     * @see Either#lift(StreamCheckedFunction)
     */
    public static <T, R> Function<T, Either> liftWithValue(StreamCheckedFunction<T, R> function) {
        return t -> {
            try {
                return Either.Right(function.apply(t));
            } catch (Exception e) {
                return Either.Left(Pair.of(e, t));
            }
        };
    }

    @Override
    public String toString() {
        if (isRight()) {
            return right.toString();
        }
        return left.toString();
    }


}
