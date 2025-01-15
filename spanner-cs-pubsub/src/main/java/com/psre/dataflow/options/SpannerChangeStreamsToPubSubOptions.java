package com.psre.dataflow.options;

import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.Validation;

public interface SpannerChangeStreamsToPubSubOptions extends DataflowPipelineOptions {

    @Description("Spanner Instance ID for the data stream")
    @Validation.Required
    String getSpannerInstanceId();
    void setSpannerInstanceId(String spannerInstanceId);

    @Description("Spanner Database ID for the data stream")
    @Validation.Required
    String getSpannerDatabaseId();
    void setSpannerDatabaseId(String spannerDatabaseId);

    @Description("Change Stream name in Spanner")
    @Validation.Required
    String getChangeStreamName();
    void setChangeStreamName(String changeStreamName);

    @Description("Metadata Instance ID for Spanner")
    @Validation.Required
    String getSpannerMetadataInstanceId();
    void setSpannerMetadataInstanceId(String spannerMetadataInstanceId);

    @Description("Metadata Database ID for Spanner")
    @Validation.Required
    String getSpannerMetadataDatabaseId();
    void setSpannerMetadataDatabaseId(String spannerMetadataDatabaseId);

    @Description("Start timestamp for change stream (RFC3339 format)")
    String getStartTimestamp();
    void setStartTimestamp(String startTimestamp);

    @Description("End timestamp for change stream (RFC3339 format)")
    String getEndTimestamp();
    void setEndTimestamp(String endTimestamp);

    @Description("Pub/Sub topic to publish messages to")
    @Validation.Required
    String getPubSubTopic();
    void setPubSubTopic(String pubSubTopic);
}
