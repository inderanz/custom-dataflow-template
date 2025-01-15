package com.psre.dataflow.transforms;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.beam.sdk.io.gcp.spanner.changestreams.model.DataChangeRecord;
import org.apache.beam.sdk.io.gcp.spanner.changestreams.model.Mod;
import org.apache.beam.sdk.transforms.DoFn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChangeEventToPubSubMessageFn extends DoFn<DataChangeRecord, String> {
    private static final Logger LOG = LoggerFactory.getLogger(ChangeEventToPubSubMessageFn.class);

    @ProcessElement
    public void processElement(@Element DataChangeRecord record, OutputReceiver<String> receiver) {
        try {
            JsonObject message = new JsonObject();
            message.addProperty("commitTimestamp", record.getCommitTimestamp().toString());
            message.addProperty("tableName", record.getTableName());
            message.addProperty("modType", record.getModType().name());

            JsonArray modsArray = new JsonArray();
            for (Mod mod : record.getMods()) {
                JsonObject modJson = new JsonObject();
                modJson.addProperty("keysJson", mod.getKeysJson());
                modJson.addProperty("newValuesJson", mod.getNewValuesJson());
                modsArray.add(modJson);
            }
            message.add("mods", modsArray);

            receiver.output(message.toString());
        } catch (Exception e) {
            LOG.error("Error processing DataChangeRecord: {}", record, e);
        }
    }
}
