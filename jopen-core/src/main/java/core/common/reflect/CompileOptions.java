package core.common.reflect;

import javax.annotation.processing.Processor;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author maxuefeng
 * @see Compile#compile(String, String, CompileOptions)
 */
public class CompileOptions {

    //
    final List<? extends Processor> processors;

    //
    final List<String> options;

    //
    public CompileOptions() {
        this(
                Collections.emptyList(),
                Collections.emptyList()
        );
    }

    private CompileOptions(
            List<? extends Processor> processors,
            List<String> options
    ) {

        this.processors = processors;
        this.options = options;

    }

    /**
     * @param newProcessors
     * @return
     */
    public final CompileOptions processors(Processor... newProcessors) {
        return processors(Arrays.asList(newProcessors));
    }

    /**
     * @param newProcessors
     * @return
     */
    public final CompileOptions processors(List<? extends Processor> newProcessors) {
        return new CompileOptions(newProcessors, options);
    }

    /**
     * @param newOptions
     * @return
     */
    public final CompileOptions options(String... newOptions) {
        return options(Arrays.asList(newOptions));
    }

    /**
     * @param newOptions
     * @return
     */
    public final CompileOptions options(List<String> newOptions) {
        return new CompileOptions(processors, newOptions);
    }

}
