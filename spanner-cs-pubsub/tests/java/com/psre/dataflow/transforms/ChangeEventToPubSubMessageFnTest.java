package com.psre.dataflow.transforms;

import com.google.api.services.bigquery.model.TableRow;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.junit.Rule;
import org.junit.Test;

public class ChangeEventToPubSubMessageFnTest {

    @Rule
    public final TestPipeline pipeline = TestPipeline.create();

    @Test
    public void testChangeEventToPubSubMessageFn() {
        String sampleChangeEvent = """
                {
                  "mods": [{"keysJson": "{\"id\": \"123\"}", "newValuesJson": "{\"id\": \"123\", \"name\": \"test\"}"}],
                  "modType": "INSERT"
                }
                """;

        PCollection<String> output = pipeline
                .apply("Create Input", org.apache.beam.sdk.transforms.Create.of(sampleChangeEvent))
                .apply("Transform Event", ParDo.of(new ChangeEventToPubSubMessageFn()));

        PAssert.that(output).containsInAnyOrder("""
                {
                  "modType": "INSERT",
                  "mods": [{"keysJson": "{\"id\": \"123\"}", "newValuesJson": "{\"id\": \"123\", \"name\": \"test\"}"}]
                }
                """);

        pipeline.run().waitUntilFinish();
    }
}
