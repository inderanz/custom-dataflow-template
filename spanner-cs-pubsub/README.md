# Spanner Change Streams to BigQuery

## Overview
This pipeline reads change events from Spanner change streams via Pub/Sub, transforms them, and writes them to BigQuery.

## Steps to Deploy
1. Build and push the container using Cloud Build.
2. Publish the template JSON to your GCS bucket.
3. Run the template using `gcloud` CLI.

## Parameters
- `pubsubTopic`: The Pub/Sub topic to read change events from.
- `bigQueryTable`: The BigQuery table to write transformed rows to.
