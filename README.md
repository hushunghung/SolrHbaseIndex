SolrHbaseIndex
==============
Function:
This is a basic HBase common API for HFile to solr.(Hbase table import into solr)

Used:
Hadoop-1.1.2
Hbase-0.94.1
Solr-4.2.1

Step

Stored CSV to HDFS file. (Default csv put it into $HADOOP_HOME, and create a new directory in HDFS file system)

=> bin/hadoop fs -copyFromLocal import_data.csv /user/$user_name/new/import_data.csv

Importtsv (in $HBASE_HOME)

=> bin/hbase org.apache.hadoop.hbase.mapreduce.ImportTsv '-Dimporttsv.separator=,' -Dimporttsv.columns=HBASE_ROW_KEY,
column_family:category,column_family:name,column_family:tel,column_family:province,column_family:address HBASE_TABLE 
hdfs://localhost:9000/user/$user_name/new/import_data.csv

Use java file

