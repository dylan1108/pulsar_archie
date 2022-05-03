# pulsar-archie-cluster
One script to (re)deploy Pulsar in pulsar-archie-cluster mode.

## User case

1. Rapid deployment a cluster, which is more complex than standalone but simpler than production.
2. Quick install for take a experience with new releases and new features (perhaps your old environment is gone).
3. Require frequent testing (compatibility testing, etc.).

## Features

1. One click deployment (one script. `./deploy.sh` ).
2. Script can be executed repeatedly to deploy a new clusters.
3. Contains the simplest configuration and administration commands that might be useful for beginners.

## Test cross

| HOST \ Apache Pulsar      | 2.9.1 | 2.8.2 | 2.7.4 | 2.6.1 |
| ------------------------- | ----- | ----- | ----- | ----- |
| CentOS 7.5 + jdk 11       | √     | √     | √     | √     |
| CentOS 7.5 + jdk1.8       | √     | √     | √     | √     |
| MacOS 12.2.1 M1 + jdk 1.8 | √     | √     | √     | √     |

## How to use

```shell
# Clone this repo
$ git clone git@github.com:dylan1108/pulsar_archie.git
$ cd pulsar_archie/pulsar-archie-cluster/

# Put your apache-pulsar tarball in the `pulsar-archie-cluster` directory (keep in same directory)
# for example download from CDN (take version of 2.9.1)
$ wget https://dlcdn.apache.org/pulsar/pulsar-2.9.1/apache-pulsar-2.9.1-bin.tar.gz --no-check-certificate

# Just execute the deployment script
$ sh deploy.sh
```

Tips: It's recommend to stop all threads ( `./stop-all.sh` ) before you redeploy (double check).

 ## Full log example

```shell
[2022-05-03 09:28:57] ====> Start to (re)deploy the pular archie-cluster of pulsar <====
[2022-05-03 09:28:57] [1/9][√] Your OS is ready => GNU/Linux
[2022-05-03 09:28:57] [2/9][√] JDK is ready => 1.8.0_311. Please check the version conflicts with Pulsar manually(JDK11+ is recommend).
[2022-05-03 09:28:57] [3/9][√] Pulsar tarball is ready => apache-pulsar-2.9.1-bin.tar.gz
[2022-05-03 09:28:57] [4/9][√] Your Ports are ready => Port list: 12181|22181|32181|19990|29990|39990|18001|18002|18003|18004|18005|18006|12888|13888|22888|23888|32888|33888|18443|28443|38443|16650|26650|36650|13181|23181|33181|18080|28080|38080|16651|26651|36651
[2022-05-03 09:28:57] [5/9][√] Please check the disk's remaining capacity manually. It's should remaining more than 95%.
doing start zookeeper ...
starting zookeeper, logging to /opt/pulsar-archie-cluster/pulsar-1/logs/pulsar-zookeeper-yilami01.log
Note: Set immediateFlush to true in conf/log4j2.yaml will guarantee the logging event is flushing to disk immediately. The default behavior is switched off due to performance considerations.
doing start zookeeper ...
starting zookeeper, logging to /opt/pulsar-archie-cluster/pulsar-2/logs/pulsar-zookeeper-yilami01.log
Note: Set immediateFlush to true in conf/log4j2.yaml will guarantee the logging event is flushing to disk immediately. The default behavior is switched off due to performance considerations.
doing start zookeeper ...
starting zookeeper, logging to /opt/pulsar-archie-cluster/pulsar-3/logs/pulsar-zookeeper-yilami01.log
Note: Set immediateFlush to true in conf/log4j2.yaml will guarantee the logging event is flushing to disk immediately. The default behavior is switched off due to performance considerations.
[2022-05-03 09:28:57] [6/9][√] Your Zookeeper archie-cluster is all ready.
[2022-05-03 09:28:57] [7/9][√] Your cluster metadata initialized in Zookeeper is ready.
[2022-05-03 09:28:57] [8/9][√] Your cluster metadata test is ready. => {"serviceUrl":"http://192.168.0.10:18080","serviceUrlTls":"https://192.168.0.10:18443","brokerServiceUrl":"pulsar://192.168.0.10:16650","brokerServiceUrlTls":"pulsar+ssl://192.168.0.10:16651","brokerClientTlsEnabled":false,"tlsAllowInsecureConnection":false,"brokerClientTlsEnabledWithKeyStore":false,"brokerClientTlsTrustStoreType":"JKS"}
doing start bookie ...
starting bookie, logging to /opt/pulsar-archie-cluster/pulsar-1/logs/pulsar-bookie-yilami01.log
Note: Set immediateFlush to true in conf/log4j2.yaml will guarantee the logging event is flushing to disk immediately. The default behavior is switched off due to performance considerations.
doing start bookie ...
starting bookie, logging to /opt/pulsar-archie-cluster/pulsar-2/logs/pulsar-bookie-yilami01.log
Note: Set immediateFlush to true in conf/log4j2.yaml will guarantee the logging event is flushing to disk immediately. The default behavior is switched off due to performance considerations.
doing start bookie ...
starting bookie, logging to /opt/pulsar-archie-cluster/pulsar-3/logs/pulsar-bookie-yilami01.log
Note: Set immediateFlush to true in conf/log4j2.yaml will guarantee the logging event is flushing to disk immediately. The default behavior is switched off due to performance considerations.
[2022-05-03 09:28:57] [9/9][√] Your bookies is all ready.
doing start broker ...
starting broker, logging to /opt/pulsar-archie-cluster/pulsar-1/logs/pulsar-broker-yilami01.log
Note: Set immediateFlush to true in conf/log4j2.yaml will guarantee the logging event is flushing to disk immediately. The default behavior is switched off due to performance considerations.
doing start broker ...
starting broker, logging to /opt/pulsar-archie-cluster/pulsar-2/logs/pulsar-broker-yilami01.log
Note: Set immediateFlush to true in conf/log4j2.yaml will guarantee the logging event is flushing to disk immediately. The default behavior is switched off due to performance considerations.
doing start broker ...
starting broker, logging to /opt/pulsar-archie-cluster/pulsar-3/logs/pulsar-broker-yilami01.log
Note: Set immediateFlush to true in conf/log4j2.yaml will guarantee the logging event is flushing to disk immediately. The default behavior is switched off due to performance considerations.
[2022-05-03 09:28:57] [ list brokers ] -> pulsar-1/bin/pulsar-admin brokers list pulsar_archie_cluster
"192.168.0.10:18080"
"192.168.0.10:28080"
[2022-05-03 09:28:57] [ create local cluster ] -> pulsar-1/bin/pulsar-admin clusters create pulsar_archie_cluster --url http://192.168.0.10:18080/
2022-05-03T09:29:47,348-0700 [AsyncHttpClient-7-1] WARN  org.apache.pulsar.client.admin.internal.BaseResource - [http://192.168.0.10:18080/admin/v2/clusters/pulsar_archie_cluster] Failed to perform http put request: javax.ws.rs.ClientErrorException: HTTP 409 Conflict
[2022-05-03 09:28:57] [ create tenant ] -> pulsar-1/bin/pulsar-admin tenants create t1 -c pulsar_archie_cluster
[2022-05-03 09:28:57] [ create tenant/namespaces ] -> pulsar-1/bin/pulsar-admin namespaces create t1/ns1 -c pulsar_archie_cluster
[2022-05-03 09:28:57] [ create second cluster ] -> pulsar-1/bin/pulsar-admin clusters create cluster_2 --url http://192.168.0.10:18080/
[2022-05-03 09:28:57] [ create second tenant ] -> pulsar-1/bin/pulsar-admin tenants create t2 -c cluster_2
[2022-05-03 09:28:57] [ create second tenant/namespaces ] -> pulsar-1/bin/pulsar-admin namespaces create t2/ns2 -c cluster_2
[2022-05-03 09:28:57] [ list tenants ] -> pulsar-1/bin/pulsar-admin tenants list
"public"
"pulsar"
"t1"
"t2"
[2022-05-03 09:28:57] [ list tenant's namespaces ] -> pulsar-1/bin/pulsar-admin namespaces list t1
"t1/ns1"
[2022-05-03 09:28:57] [ get tenant's clusters ] -> pulsar-1/bin/pulsar-admin namespaces get-clusters t1/ns1
"pulsar_archie_cluster"
[2022-05-03 09:28:57] [pulsar-client produce][√] 10 messages successfully produced
[2022-05-03 09:28:57] [pulsar-client consume] Now it's your turn to test. Please execute consume command like:
pulsar-1/bin/pulsar-client consume persistent://t1/ns1/test -n 10 -s "consumer-test"  -t "Exclusive" -p "Earliest"
 _   _  _     ______        _                    _ 
| | | |(_)    | ___ \      | |                  | |
| |_| | _     | |_/ /_   _ | | ___   __ _  _ __ | |
|  _  || |    |  __/| | | || |/ __| / _` || '__|| |
| | | || | _  | |   | |_| || |\__ \| (_| || |   |_|
\_| |_/|_|( ) \_|    \__,_||_||___/ \__,_||_|   (_)
          |/                                      
```



## Contant me

Please feel free to contact me:

- qunlin1108@163.com
- WeChart: Yilami1108
