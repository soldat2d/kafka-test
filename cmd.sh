#netsh interface portproxy add v4tov4 listenport=9092 connectport=9092 connectaddress=172.18.249.202
tar -xzf kafka_2.13-3.2.1.tgz
cd kafka_2.13-3.2.1

bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties
bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --topic kafka-test --partitions 1 --replication-factor 1
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic kafka-test
bin/kafka-topics.sh --bootstrap-server localhost:9092 --topic kafka-test --delete
bin/kafka-topics.sh --bootstrap-server localhost:9092 --topic kafka-test --create --partitions 10 --replication-factor 2
