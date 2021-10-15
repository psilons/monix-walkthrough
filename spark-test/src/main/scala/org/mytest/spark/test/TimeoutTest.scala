package org.mytest.spark.test

// Seems Spark does not have timeout at job level.
// At stage level, there is a listener:
// https://stackoverflow.com/questions/63389263/how-to-set-timeout-to-a-spark-task-or-map-operation-or-skip-long-running-task
// https://github.com/apache/spark/pull/16189
// https://stackoverflow.com/questions/39685744/apache-spark-how-to-cancel-job-in-code-and-kill-running-tasks
// https://stackoverflow.com/questions/66405320/how-to-let-pyspark-databricks-job-continue-running-and-ignore-bad-records-after?rq=1
// https://stackoverflow.com/questions/40474057/what-are-possible-reasons-for-receiving-timeoutexception-futures-timed-out-afte/48449467#48449467
// https://www.waitingforcode.com/apache-spark/apache-spark-2.4.0-features-barrier-execution-mode/read

// Our scenario is that we need to retrieve data from several external data sources.
// If any of them takes long time, we want to stop and treat it as a failure.

// Can we do it with futures manually?

class TimeoutTest
{

}
