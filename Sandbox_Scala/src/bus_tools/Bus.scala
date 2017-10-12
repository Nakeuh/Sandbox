package bus_tools

import org.apache.spark.rdd._
import org.apache.spark._
import org.codehaus.jackson.annotate._
import scala.util.Random
import scala.reflect._
import org.apache.spark.rdd.RDD.rddToPairRDDFunctions

class BusData extends java.io.Serializable {
        @BeanProperty var id: Int = 0
        @BeanProperty var lineId: Int = 0
        @BeanProperty var timestamp: Long = 0
        @BeanProperty var delay: Int = 0
        @BeanProperty var longitude: Double = 0
        @BeanProperty var latitude: Double = 0
}

object Bus {

        def initRandomBus(id: Int, time: Long,nbLine:Int): BusData = {
                val bus: BusData = new BusData()
                bus.id = id
                bus.lineId = id % nbLine
                bus.timestamp = time
                bus.delay = Random.nextInt(1000)
                bus.longitude = Random.nextDouble() / 4 + 25
                bus.latitude = Random.nextDouble() / 2 + 55

                return bus
        }

        def displayBus(bus: BusData) {
                println("bus n°" + bus.id)
                println("    Ligne : " + bus.lineId)
                println("    Date : " + bus.timestamp)
                println("    Retard :" + bus.delay)
                println("    Position : " + bus.longitude + " , " + bus.latitude)
        }

        def initRandomBusRDD(sc: SparkContext, time: Long,nbBus:Int,nbTime:Int, nbLine:Int): RDD[BusData] = {
                var array: Array[BusData] = Array()

                for (i <- 1 to nbBus) { 
                        for (j <- 1 to nbTime) {
                                val bus: BusData = initRandomBus(i, time + ((j - 1) * 100),nbLine)
                                array = array :+ bus
                        }
                        println("Données du bus " + i + " générées. ("+nbTime+" données)")
                }
                return sc.parallelize(array)
        }

        /**
         * Calcul average delay on BusData RDD
         */
        def getAvgDelay(rdd: RDD[BusData]): Float = {
                val pair = rdd.map(b => (b.delay, 1))
                        .reduce((a, b) => (a._1 + b._1, a._2 + b._2))
                return pair._1 / pair._2;
        }

        /**
         *  Calcul average delay for each Line on BusData RDD at Time t
         */
        def getAvgDelayByLine(rdd: RDD[BusData], t: Long): RDD[(Int, Float)] = {
                return rdd.filter { b => b.timestamp == t }
                        .map(b => (b.lineId, (b.delay, 1)))
                        .reduceByKey((a, b) => (a._1 + b._1, a._2 + b._2))
                        .map(b => (b._1, (b._2._1 / b._2._2)))
        }

        /**
         * Calcul average delay on BusData RDD at Time t
         */
        def getAvgDelayTotal(rdd: RDD[BusData], t: Long): Float = {
                return getAvgDelay(rdd.filter { b => b.timestamp == t })
        }

        /**
         * Calcul average delay on BusData RDD at Time t for line l
         */
        def getAvgDelayLine(rdd: RDD[BusData], t: Long, l: Int): Float = {
                return getAvgDelay(rdd.filter(b => (b.lineId.equals(l) && b.timestamp == t)))
        }
}