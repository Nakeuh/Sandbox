package graphx_tools

import org.apache.spark.graphx._
import org.apache.spark._
import org.apache.spark.rdd._
import scala.util.Random
import utils.StringUtils

object GraphX {

        def initRandomGraph(sc: SparkContext): Graph[String, String] = {
                val graph: Graph[String, String] = Graph(initRandomVertices(sc), initRandomEdges(sc))
                return graph
        }

        def initRandomVertices(sc: SparkContext): RDD[(VertexId, String)] = {

                var array: Array[(VertexId, String)] = Array()

                for (i <- 1 to 200) {
                        array = array :+ (i.toLong, StringUtils.randomString(10))
                }
                val vertex: RDD[(VertexId, String)] = sc.parallelize(array)

                return vertex
        }

        def initRandomEdges(sc: SparkContext): RDD[Edge[String]] = {
                var array: Array[Edge[String]] = Array()

                for (i <- 1 to 200) {
                        array = array :+ (Edge(i, Random.nextInt(200), "Property" + i))
                }

                val edge: RDD[Edge[String]] = sc.parallelize(array)

                return edge
        }

}