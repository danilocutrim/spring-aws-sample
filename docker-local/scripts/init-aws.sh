#!/bin/bash

awslocal s3 mb s3://my-bucket
awslocal sqs create-queue --queue-name my-queue
awslocal dynamodb create-table --cli-input-json file:///tables/1D_candle_table.json

