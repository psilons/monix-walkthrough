# Scala 3rd Party Lib Walk Through

The common version of the libraries that we are currently interested in is 2.12.
So we have to live with this, feel kind of wasting time or can't wait to get to 
Scala 3.

We choose not to use parent-child build hierarchy here, because these are prototypes
and we want to keep independent from each other and copy-paste later is easier
with self contained content.


https://awesomeopensource.com/project/lauris/awesome-scala
Notably, Apache Spark and Flink


- https://www.innoq.com/en/blog/functional-service-in-scala/
- https://github.com/tofu-tf/tofu

## Dependency Injection

Don't have time to go on. May just use Spring at the boundary and use Scala inside the domain.
Use Spring components if Scala version is not mature.

None of the solutions reaches the maturity of Spring/Spring Boot, all toys.
Before we solve problems, we need to know what problems are. It's been stuck here for more than ten years.

Seems that anyone ignores Spring gets the wrong answer - Spring accumulated a lot of industrial experience
from the past. Ignoring those experience means we are repeating our own history. For example, we need a
generic/universal object factory. We struggled this between 1999 - 2001, then Spring came. Below posts
spread out >10 years to deal with the same problem.

How to manage dependencies and configurations in Scala with a functional approach. To name a few features
- Singletons/prototypes/proxies
- init/destroy methods (lifecycles)
- override/mock one/more bean in the dependency tree for testing, assuming giant tree.
- select any bean/subtree in the tree for testing without initializing the rest. This bean may be
  many beans away from overwrites.
and more in Spring.

Historic attempts:
- https://medium.com/rahasak/dependency-injection-with-reader-monad-in-scala-fe05b29e04dd
- https://gist.github.com/gvolpe/1454db0ed9476ed0189dcc016fd758aa
- https://stackoverflow.com/questions/49889832/scala-dependency-injection-simple-pattern
- https://medium.com/@takezoe/airframe-surface-another-generic-programming-library-for-scala-a3040c5efd59
- https://medium.com/@takezoe/revisit-dependency-injection-in-scala-78276c691bba
- http://di-in-scala.github.io/
- https://softwaremill.com/dependency-injection-scala-guide/
- http://www.michaelpollmeier.com/2014/06/29/simple-dependency-injection-scala
- http://allaboutscala.com/tutorials/chapter-5-traits/scala-traits-depedency-injection-avoid-cake-pattern/
- https://www.warski.org/blog/2014/02/using-scala-traits-as-modules-or-the-thin-cake-pattern/
- https://www.mindissoftware.com/post/2019/10/di/
- http://blog.rcard.in/programming/software-design/java/scala/di/2016/08/01/resolve-problems-dependency-injection.html
- http://blog.rcard.in/design/scala/2017/10/15/resolve-me-implicitly.html
- https://www.titanwolf.org/Network/q/3532de41-f82f-46de-8c80-4c03b3e9e274/y

One of the key points is using constructors/setters to manage dependencies so that business interfaces
are free of technology dependencies. Scala is a functional language, getting closer to Python in Scala 3.
In the object world, we should mimic Spring behaviors. In the functional world, implicit can help.


### JDBC

Doobie seems good, but I haven't check nested transactions and batch performance.
If Monix is used, can we hide it? We are supposed to write SQL and do OR mapping, no more no less.

- https://godatadriven.com/blog/typechecking-sql-queries-with-doobie/
- https://tpolecat.github.io/doobie/