

A RDD has Partitions spanning across nodes. Tasks should be close to data

DAG

manual optimization, no Catalyst optimizer or Tungsten execution engine.

Create RDD
- read external sources
- parallelize collections

RDD operations
- transformations: lazy
    - narrow: all needed data is in one node, like map, filter
    - wide: needed data is across nodes, like groupByKey, reduceByKey
- actions: 

Shared variables:
- Broadcast variables
- accumulators


- https://www.xenonstack.com/blog/rdd-in-spark/
