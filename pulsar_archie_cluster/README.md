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

```



## Contant me

Please feel free to contact me:

- qunlin1108@163.com
- WeChart: Yilami1108
