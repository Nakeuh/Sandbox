package tests

import org.apache.spark._
import org.apache.spark.rdd._
import org.apache.spark.streaming.StreamingContext

import bus_tools.Bus
import graphx_tools.GraphX
import ems_statefull_example.EMS
import spark.SparkUtils



object Tests {

        def main(args: Array[String]) {

                testEMS()

        }

        def testBus() {
                val sc: SparkContext = SparkUtils.initSpark()
                val time = System.currentTimeMillis()
                val nbBus = 3000
                val nbLine = 10
                val nbTime = 50

                var tmpTime = System.currentTimeMillis()
                val buses = Bus.initRandomBusRDD(sc, time, nbBus, nbLine, nbTime)
                val delayInit = System.currentTimeMillis() - tmpTime

                println("Données Random Bus générées : " + buses.count() + " lignes.")

                val totalDelay = Bus.getAvgDelayTotal(buses, time + 500)
                println("Retard moyen total : " + totalDelay)

                for (i <- 1 to 20)
                        println("Retard moyen Ligne " + (i - 1) + " : " + Bus.getAvgDelayLine(buses, time + 500, (i - 1)))
                val delay1AllL = System.currentTimeMillis() - tmpTime

                tmpTime = System.currentTimeMillis()
                Bus.getAvgDelayByLine(buses, time + 500).foreach(x => println("Retard moyen Ligne " + x._1 + " : " + x._2))
                val delay2AllL = System.currentTimeMillis() - tmpTime

                tmpTime = System.currentTimeMillis()
                println("Retard moyen Ligne " + 2 + " : " + Bus.getAvgDelayLine(buses, time + 500, 2))
                val delay11L = System.currentTimeMillis() - tmpTime

                tmpTime = System.currentTimeMillis()
                Bus.getAvgDelayByLine(buses, time + 500).filter(f => f._1 == 2).foreach(x => println("Retard moyen Ligne " + x._1 + " : " + x._2))
                val delay21L = System.currentTimeMillis() - tmpTime

                println("Temps de génération des données : " + delayInit / 1000 + "secondes")
                println("Calcul pour une ligne : ")
                println("    Solution 1 : " + delay11L + "ms")
                println("    Solution 2 : " + delay21L + "ms")
                println("Calcul pour toute les lignes : ")
                println("     Solution 1 : " + delay1AllL + "ms")
                println("     Solution 2 : " + delay2AllL + "ms")

                SparkUtils.closeSpark(sc)
        }

        def testGraph() {
                val sc: SparkContext = SparkUtils.initSpark()
                GraphX.initRandomGraph(sc)
                SparkUtils.closeSpark(sc)
        }

        def testEMS() {

                val sc: SparkContext = SparkUtils.initSpark()
                val ssc: StreamingContext = SparkUtils.initSparkStreaming(sc)

                val streamK = EMS.createStream(ssc, "dev-kafka-1:2181", "ems-kpi", "ems_data", 10, 10)

                // val stream = EMS.createStreamFromFile(ssc)
                //    EMS.computeStream(ssc, stream,"dev-kafka-0:9092,dev-kafka-1:9092,dev-kafka-3:9092","kpi")
                //   EMS.computeStream(ssc, streamK,"dev-kafka-0:9092","kpi")
                EMS.computeStream2(ssc, streamK, "dev-kafka-0:9092", "kpi")

                ssc.checkpoint("checkpoint/ems")

                SparkUtils.startStreaming(ssc)
        }

}