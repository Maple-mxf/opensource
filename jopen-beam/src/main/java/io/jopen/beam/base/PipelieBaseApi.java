
package io.jopen.beam.base;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.POutput;

/**
 * 测试{@link Pipeline}基础API
 *
 * @author maxuefeng
 * @since 2020/1/10
 */
public class PipelieBaseApi {

    public static void main(String[] args) {

        PipelineOptions options = PipelineOptionsFactory.create();
        options.setJobName("wordCount");
        Pipeline pipeline = Pipeline.create(options);

        PCollection<String> collection = pipeline.apply("readLine", TextIO.read().from("gs://some/inputData.txt"));
        collection.apply(new PTransform<PCollection<String>, POutput>() {
            @Override
            public POutput expand(PCollection<String> input) {
                return null;
            }
        });

    }
}
