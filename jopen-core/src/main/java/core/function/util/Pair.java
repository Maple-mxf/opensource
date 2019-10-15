package core.function.util;

/**
 * @author maxuefeng
 */
public class Pair<F, S> {

    private final F fst;

    private final S snd;

    private Pair(F fst, S snd) {
        this.fst = fst;
        this.snd = snd;
    }

    public static <F, S> Pair<F, S> of(F fst, S snd) {
        return new Pair<>(fst, snd);
    }
}
