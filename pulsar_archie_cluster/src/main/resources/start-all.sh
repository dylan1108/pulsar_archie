#!/usr/bin/env bash
# Script to start pulsar cluster server in pulsar-archie-cluster.
# More info
# Copyright (C) 2022 yilami (Please feel free to contact me : qunlin1108@163.com)
# Permission to copy and modify is granted under the Apache 2.0 license
# Last revised 23/4/2022

pulsar-1/bin/pulsar-daemon start zookeeper
pulsar-2/bin/pulsar-daemon start zookeeper
pulsar-3/bin/pulsar-daemon start zookeeper

pulsar-1/bin/pulsar-daemon start bookie
pulsar-2/bin/pulsar-daemon start bookie
pulsar-3/bin/pulsar-daemon start bookie

pulsar-1/bin/pulsar-daemon start broker
pulsar-2/bin/pulsar-daemon start broker
pulsar-3/bin/pulsar-daemon start broker
