# Spark Walk Through

## RDD (Resilient Distributed Dataset)

https://spark.apache.org/docs/latest/rdd-programming-guide.html

RDD -> Transformations(lazy), Actions
Action -> Stage -> Jobs
https://www.tutorialspoint.com/apache_spark/index.htm

Shared data is propagated through broadcasting.

We need appropriate datasets to show Spark features
data sets with 10K, 100K rows, 1M rows

films.csv: https://perso.telecom-paristech.fr/eagan/class/igr204/datasets
https://www.kaggle.com/rounakbanik/the-movies-dataset

### Slicing

### Aggregation

### Pivoting

### Spark SQL

Similar tools:
https://medium.com/@thijser/doing-cool-data-science-in-java-how-3-dataframe-libraries-stack-up-5e6ccb7b437

## Optimization

- https://medium.com/datakaresolutions/key-factors-to-consider-when-optimizing-spark-jobs-72b1a0dc22bf
- https://enigma.com/blog/post/things-i-wish-id-known-about-spark
- https://blog.stratio.com/optimizing-spark-streaming-applications-apache-kafka/
- https://michalsenkyr.github.io/2018/01/spark-performance
- https://michalsenkyr.github.io/2018/01/spark-performance

## Installation
The installation is for starting a standalone cluster. Otherwise, sbt works fine.
Windows installation is painful, mac/linux are better, see
https://www.codeleading.com/article/24496035638/ (Chinese)

### Spark 
Download Spark 3.1.2 from [Apache Spark](https://spark.apache.org/downloads.html).
It works with Scala 2.12.

Set up SPARK_HOME to the installation folder and add $SPARK_HOME/bin and 
$SPARK_HOME/sbin to the PATH.


### Hadoop
Download Hadoop 3.3.1 from [Apache Hadoop](https://hadoop.apache.org/releases.html).

According to [Hadoop Wiki](https://cwiki.apache.org/confluence/display/HADOOP/Hadoop+Java+Versions),
Hadoop 3.3.1 supports JDK 8 & 11.

Set up HADOOP_HOME to the installation folder and add $HADOOP_HOME/bin and
$HADOOP_HOME/sbin to the PATH.

On Windows, we need extra from [winutils](https://github.com/cdarlint/winutils), 
copy winutils.* with the right version from there to $HADOOP_HOME/bin.
Currently, the latest version is for Hadoop 3.2.2, which is only JDK 8 compatible.

### Scala

Download Scala from [Scala Download](https://www.scala-lang.org/download/all.html).
Then go to "Other ways to install" to find the link.

We are using 2.12.<latest> because of Spark. It is compatible with JDK 8 & 11
according to [Scala Compatibility](https://docs.scala-lang.org/overviews/jdk-compatibility/overview.html).

### JDK 
Now the only choice on Windows is JDK 8.
If JDK > 8 is used, the following error will be triggered from Spark shell
```
Caused by: java.lang.reflect.InaccessibleObjectException: Unable to make 
private java.nio.DirectByteBuffer(long,int) accessible: module java.base does 
not "opens java.nio" to unnamed module
```
I also tried to set the following or alike unsuccessfully on JVM starts.
```
--illegal-access=permit --add-opens java.base/java.lang=ALL-UNNAMED
```

Hadoop has the same issue. So it's time to start multi-jdk practice, like
Python/Conda. May take a look at jenv/jenv-for-windows.

Download JDK 8 from [Oracle Java](https://www.oracle.com/java/technologies/downloads/archive/).

Set up JAVA_HOME as well.

### Verification

- Test JDK: ```java -version``` and ```where java```.
- Test Hadoop: ```hadoop version```
- Test Spark: ```spark-shell --version```

Create /tmp/spark-events first.
Start Spark with ```spark-shell```, then ```:help``` for help.
The job console is http://localhost:4040/jobs/

### References:

- https://spark.apache.org/docs/latest/
- https://spark.apache.org/docs/latest/spark-standalone.html


- https://www.tutorialspoint.com/apache_spark/apache_spark_quick_guide.htm
- https://data-flair.training/blogs/spark-tutorial/
- https://github.com/spark-examples/spark-scala-examples
- https://sparkbyexamples.com/
- https://phoenixnap.com/kb/install-spark-on-windows-10
- https://towardsdatascience.com/installing-hadoop-3-2-1-single-node-cluster-on-windows-10-ac258dd48aef
- https://medium.com/analytics-vidhya/creating-apache-spark-standalone-cluster-with-on-windows-95e66e00a2d8

- https://kontext.tech/column/hadoop/377/latest-hadoop-321-installation-on-windows-10-step-by-step-guide

- https://onlineguwahati.com/installation-of-apache-hadoop-3-2-0/
