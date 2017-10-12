package ems_statefull_example
import java.util.HashMap
 
import org.apache.spark.rdd._
import org.apache.spark._
import org.apache.spark.rdd.RDD.rddToPairRDDFunctions
import org.apache.spark.streaming.{ Seconds, StreamingContext }
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.dstream._
import org.apache.spark.streaming._
import org.apache.spark.storage.StorageLevel
import org.codehaus.jackson.map.ObjectMapper;
import java.util.Date
import spark.kafkaSpark

import org.apache.kafka.clients.producer.{ ProducerConfig, KafkaProducer, ProducerRecord }

import scala.reflect._
import java.time._

object EMS {

        class EMSData extends java.io.Serializable {
                @BeanProperty var kind: String = ""
                @BeanProperty var state: String = ""
                @BeanProperty var info: String = ""
                @BeanProperty var hypid: String = ""
                @BeanProperty var name: String = ""
                @BeanProperty var timestamp: Long = 0
        }

        def createStream(ssc: StreamingContext, zookeeperHost: String, consumerGroup: String, topic: String, windowLenght: Int, windowSlide: Int): DStream[String] = {
                return KafkaUtils.createStream(ssc, zookeeperHost, consumerGroup, Map(topic -> 1), StorageLevel.MEMORY_AND_DISK).map { _._2 } // Get data stream from Kafka
                        .window(Seconds(windowLenght), Seconds(windowSlide)) // Set the sliding window       
        }

        /**
         * key : id de l'objet pour lequel on souhaite garder un état
         * value : objet dont on souhaite garder un état
         * state : état
         */
        //kind / state  / info / timestamp
        def stateSpecFuntion(key: String, value: Option[(String, String, String, Long)], state: State[Long]): String = {
                var duration: Long = 0
                var kind = value.get._1
                var step = value.get._2
                var timestamp = value.get._4
                var info: String = null

                if (state.exists()) {
                        duration = timestamp - state.get()
                }
                state.update(timestamp)

                if (step == "CLOSED") {
                        info = value.get._3
                        state.remove()
                }

                return "{\"src\":\"EMS\",\"context\":null,\"timestamp\":\"" + timestamp + "\",\"name\":\"EMS_EC3_" + kind + "_EC3_" + step + "_EC3_" + info + "\",\"value\":\"" + duration + "\"}";
        }


        def initProducer(kafkaBroker: String): KafkaProducer[String, String] = {
                val kafkaProducerProps = new HashMap[String, Object]()
                kafkaProducerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBroker)
                kafkaProducerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
                kafkaProducerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
                new KafkaProducer[String, String](kafkaProducerProps)
        }

        def computeStream2(ssc: StreamingContext, stream: DStream[String], brokerList: String, topic: String) {
                println("compute")
                // Specification of how the mapWithState should work
                val stateSpec: StateSpec[String, (String, String, String, Long), Long, String] = StateSpec.function(stateSpecFuntion _)

                  val state = stream.map { parseJsonToEMSData(_) }
                        .map { data => (data.hypid, (data.kind, data.state, data.info, data.timestamp)) }
                        .mapWithState(stateSpec)
                        .foreachRDD { rdd =>
                                rdd.foreachPartition {
                                        iter =>
                                                val producer = initProducer(brokerList)
                                                sendKpi(iter, producer, topic)
                                }
                        }
        }

        def sendKpi(iter: Iterator[String], producer: KafkaProducer[String, String], topic: String) {
                iter.foreach { json =>
                        producer.send(new ProducerRecord(topic, json))
                }
        }


        def parseJsonToEMSData(json: String): EMSData = {
                println("data : " + json)
                // val mapper = new ObjectMapper()
                // mapper.readValue(json, classOf[EMSData])
                val tmp = new EMSData()
                tmp.hypid = ""
                tmp.info = "";
                tmp.kind = ""
                tmp.name = ""
                tmp.state = ""
                tmp.timestamp = 0
                tmp
        }

        def createStreamFromFile(ssc: StreamingContext): DStream[String] = {
                val stream = ssc.textFileStream("Ressources/EMS_Tmp/")
                return stream
        }
        
}