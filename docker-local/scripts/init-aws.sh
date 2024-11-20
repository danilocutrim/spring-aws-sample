#!/bin/bash

awslocal s3 mb s3://my-bucket
awslocal sqs create-queue --queue-name my-queue
awslocal dynamodb create-table --cli-input-json file:///tables/repo_table.json
awslocal dynamodb create-table --cli-input-json file:///tables/user_table.json


