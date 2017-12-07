# Lambda-demo

Please refer to config directory for all configurations for Kafka and Hadoop setup

## Start Kafka
##### Run Zookeeper
`cd kafka_2.11-1.0.0 && /bin/zookeeper-server-start.sh config/zookeeper.properties`

##### Run first kafka broker
`cd kafka_2.11-1.0.0 && /bin/kafka-server-start.sh config/server.properties`

##### Run second kafka broker
`cd kafka_2.11-1.0.0 && /bin/kafka-server-start.sh config/server2.properties`

##### Run Schema Registry
`cd confluent-4.0.0 && /bin/schema-registry-start etc/schema-registry/schema-registry.properties`

##### Run kafka avro consumer
`cd confluent-4.0.0 && /bin/kafka-avro-console-consumer --bootstrap-server localhost:9092 --topic craft_trial --from-beginning`

## Start Hadoop
Verify core-site.xml, hive-site.xml, mapred-site.xml, yarn-site.xml files in hadoop/etc/hadoop directory

##### Set below mentioned environment variables in your ~/.bash_profile file and execute source ~/.bash_profile command
`export HADOOP_HOME=/usr/local/Cellar/hadoop`
`export HADOOP_CONF_DIR=/usr/local/Cellar/hadoop/etc/hadoop`
`export PATH=$PATH:$HADOOP_HOME/bin`
`export HIVE_HOME=/usr/local/Cellar/hive–1.1.0`
`export PATH=$PATH:$HIVE_HOME/bin`
`export HADOOP_MAPRED_HOME=$HADOOP_HOME`
`export HADOOP_COMMON_HOME=$HADOOP_HOME`
`export HADOOP_HDFS_HOME=$HADOOP_HOME`
`export YARN_HOME=$HADOOP_HOME`
`export SPARK_HOME=/usr/local/Cellar/spark`
`export PATH=$PATH:$SPARK_HOME/bin`
`export CLASSPATH=$CLASSPATH:/usr/local/Cellar/hive–1.1.0/lib/mysql-connector-java-5.1.45-bin.jar`
`export PATH=$PATH:/usr/local/Cellar/scala/2.10.6_1/bin:/usr/local/mysql/bin`

##### Start Yarn
`cd hadoop && /sbin/start-yarn.sh`

##### Start Hadoop
`cd hadoop && /sbin/start-dfs.sh`

##### Start Hive
Create metastore database, hiveuser in mysql. Give all the permission to hiveuser on metastore database.
Verify hive-site.xml as shown in resources
Copy mysql-connector-java-5.1.45-bin.jar in hive/lib directory and run below mentioned command
`hive`

## Build lambda-demo Project
Verify configuration in craft_config.properties file and make necessary changes based on your setup

mvn clean package
##### Run Kafka event generation code
Run com.intuit.craft.producer.TrialEventProducer class to generate Trial events into Kafka

##### Run kafka connect to push data from kafka to hdfs/hive
cd confluent-4.0.0 && /bin/connect-standalone etc/schema-registry/connect-avro-standalone.properties etc/kafka-connect-hdfs/quickstart-hdfs.properties

##### Run Streaming job
Verify configuration in streaming-config.properties file and make necessary changes based on your setup
Run com.intuit.craft.streaming.TravelEventJob class to run Streaming job which reads events from Kafka and pushes those to Cassandra realtime