#!/bin/sh

awslocal sqs create-queue --queue-name ${QUEUE_NAME} --attributes FifoQueue=false
awslocal sns create-topic --name ${TOPIC_NAME}

echo "Initialized."