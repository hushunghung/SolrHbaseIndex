package com.example.solr;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

public class solrIndexer {

    public static void main(String[] args) throws IOException,SolrServerException {
    	
        final Configuration conf;
        HttpSolrServer solrServer = new HttpSolrServer("http://c1master:8983/solr"); 
        conf = HBaseConfiguration.create();
        
        //Define Hbase Table Name
        HTable table = new HTable(conf, "test_global_shop");
        Scan scan = new Scan();
        
        //Define Hbase Column Family
        scan.addFamily(Bytes.toBytes("shop"));
        scan.setCaching(1000);
        scan.setCacheBlocks(false);
        ResultScanner ss = table.getScanner(scan);

        System.out.println("start Storing...");
        int i = 0;
        try {
            for (Result r : ss) {
                SolrInputDocument solrDoc = new SolrInputDocument();
                solrDoc.addField("key", new String(r.getRow()));
                for (KeyValue kv : r.raw()) {
                    String fieldName = new String(kv.getQualifier());
                    String fieldValue = new String(kv.getValue());
                    if (fieldName.equalsIgnoreCase("address")
                            || fieldName.equalsIgnoreCase("category")
                            || fieldName.equalsIgnoreCase("name")
                            || fieldName.equalsIgnoreCase("province")
                            || fieldName.equalsIgnoreCase("tel")) {
                        solrDoc.addField(fieldName, fieldValue);
                    }
                }
                solrServer.add(solrDoc);
                solrServer.commit(true, true, true);
                i = i + 1;
                System.out.println("Already Succcess " + i + " number data");
            }
            ss.close();
            table.close();
            System.out.println("done !");
        } catch (IOException e) {
        } finally {
            ss.close();
            table.close();
            System.out.println("error !");
        }
    }

}
