package com.psre.dataflow;

import com.psre.dataflow.options.PipelineOptions;
import org.apache.beam.sdk.testing.TestPipeline;
import org.junit.Rule;
import org.junit.Test;

public class SpannerChangeStreamsToPubSubTest {

    @Rule
    public final TestPipeline pipeline = TestPipeline.create();

    @Test
    public void testPipelineBuild() {
        PipelineOptions options = TestPipeline.testingPipelineOptions().as(PipelineOptions.class);
        options.setSpannerInstanceId("test-instance");
        options.setSpannerDatabaseId("test-database");
        options.setPubsubTopic("projects/test-project/topics/test-topic");
        options.setChangeStreamName("test-change-stream");

        SpannerChangeStreamsToPubSub.main(new String[]{});
    }
}
