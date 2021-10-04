
## DataSet
We need appropriate datasets to show Spark features
data sets with 10K, 100K rows, 1M rows

films.csv: https://perso.telecom-paristech.fr/eagan/class/igr204/datasets
https://www.kaggle.com/rounakbanik/the-movies-dataset

## Slicing

## Aggregation

## Pivoting

## Spark SQL


## Hadoop Installation

so many holes: https://www.codeleading.com/article/24496035638/
So we are back to JDK8 then.
https://www.oracle.com/java/technologies/downloads/#java8


Use 3.2.2 because on Windows we need the binaries from
https://github.com/cdarlint/winutils

- Download Hadoop binaries from https://hadoop.apache.org/releases.html
- Download https://github.com/cdarlint/winutils to override <hadoop>/bin
- set HADOOP_HOME and add %HADOOP_HOME%/bin and %HADOOP_HOME%/sbin to PATH
- verify with ```hadoop version``` at the command line
```
Hadoop 3.2.2
Source code repository Unknown -r 7a3bc90b05f257c8ace2f76d74264906f0f7a932
Compiled by hexiaoqiao on 2021-01-03T09:26Z
Compiled with protoc 2.5.0
From source with checksum 5a8f564f46624254b27f6a33126ff4
This command was run using /D:/0dev/tools/hadoop-3.2.2/share/hadoop/common/hadoop-common-3.2.2.jar
```

https://towardsdatascience.com/installing-hadoop-3-2-1-single-node-cluster-on-windows-10-ac258dd48aef

https://kontext.tech/column/hadoop/377/latest-hadoop-321-installation-on-windows-10-step-by-step-guide
