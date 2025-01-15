import com.google.cloud.Timestamp;
import com.psre.dataflow.options.SpannerChangeStreamsToPubSubOptions;
import com.psre.dataflow.transforms.ChangeEventToPubSubMessageFn;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.io.gcp.spanner.SpannerIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.ParDo;

public class SpannerChangeStreamsToPubSub {
    public static void main(String[] args) {
        SpannerChangeStreamsToPubSubOptions options =
            PipelineOptionsFactory.fromArgs(args).withValidation().as(SpannerChangeStreamsToPubSubOptions.class);

        Pipeline pipeline = Pipeline.create(options);

        // Parse the start and end timestamps
        Timestamp startTimestamp = Timestamp.parseTimestamp(options.getStartTimestamp());
        Timestamp endTimestamp = Timestamp.parseTimestamp(options.getEndTimestamp());

        pipeline
            .apply("Read from Spanner Change Streams",
                SpannerIO.readChangeStream()
                    .withInstanceId(options.getSpannerInstanceId())
                    .withDatabaseId(options.getSpannerDatabaseId())
                    .withChangeStreamName(options.getChangeStreamName())
                    .withMetadataInstance(options.getSpannerMetadataInstanceId())
                    .withMetadataDatabase(options.getSpannerMetadataDatabaseId())
                    .withInclusiveStartAt(startTimestamp)
                    .withInclusiveEndAt(endTimestamp))
            .apply("Transform to Pub/Sub Message", ParDo.of(new ChangeEventToPubSubMessageFn()))
            .apply("Write to Pub/Sub", PubsubIO.writeStrings().to(options.getPubSubTopic()));

        pipeline.run().waitUntilFinish();
    }
}
