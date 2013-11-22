SolrHbaseIndex
==============
Function:
This is a basic HBase common API for HFile to solr.(Hbase table import into solr)

Used:
Hadoop-1.1.2
Hbase-0.94.1
Solr-4.2.1

Step

1.
Stored CSV to HDFS file. (Default csv put it into $HADOOP_HOME, and create a new directory in HDFS file system)

=> bin/hadoop fs -copyFromLocal import_data.csv /user/$user_name/new/import_data.csv
2.
Importtsv (in $HBASE_HOME)

=> bin/hbase org.apache.hadoop.hbase.mapreduce.ImportTsv '-Dimporttsv.separator=,' -Dimporttsv.columns=HBASE_ROW_KEY,
column_family:category,column_family:name,column_family:tel,column_family:province,column_family:address HBASE_TABLE 
hdfs://localhost:9000/user/$user_name/new/import_data.csv

3.
Assemble jar file

It needs many jar file (please put these jar into HBASE_HOME/lib and HADOOP_HOME/lib replace old version)

commons-cli-1.2.jar           commons-io-2.1.jar
guava-11.0.2.jar              hadoop-core-1.1.2.jar
hbase-0.94.1.jar              httpclient-4.2.3.jar
httpcore-4.2.2.jar            httpmime-4.2.3.jar
jcl-over-slf4j-1.6.4.jar      slf4j-api-1.6.4.jar
slf4j-jdk14-1.6.4.jar         slf4j-log4j12-1.6.4.jar
solr-core-4.2.1.jar           solr-solrj-4.2.1.jar
wstx-asl-3.2.7.jar            zookeeper-3.4.5.jar
protobuf-java-2.4.0a.jar

step1: Java to class  (Default directory is /opt/data/e/)

javac -classpath commons-cli-1.2.jar:commons-io-2.1.jar:guava-11.0.2.jar:hadoop-core-1.1.2.jar
:hbase-0.94.1.jar:httpclient-4.2.3.jar:httpcore-4.2.2.jar:httpmime-4.2.3.jar:
jcl-over-slf4j-1.6.4.jar:slf4j-api-1.6.4.jar:slf4j-jdk14-1.6.4.jar:slf4j-log4j12-1.6.4.jar:
solr-core-4.2.1.jar:solr-solrj-4.2.1.jar:wstx-asl-3.2.7.jar:zookeeper-3.4.5.jar:protobuf-java-2.4.0a.jar
-d /opt/data/e/ solrIndexer.java

step2: Jar generating

jar -cvf solrIndexer.jar -C /opt/data/e/ .

step3: Copy solrIndexer.jar into $HBASE_HOME and then

bin/hadoop jar solrIndexer.jar com.example.solr.solrIndexer

step4: OK




