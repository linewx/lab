package com.zz.database.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

public class Hbase {
    public List<String> listTables() throws IOException {
        List<String> results = new ArrayList<>();
        Connection connection = ConnectionFactory.createConnection(HBaseConfiguration.create());
        Admin admin = connection.getAdmin();
        HTableDescriptor[] tableDescriptors = admin.listTables();
        for (HTableDescriptor tableDescriptor : tableDescriptors) {
            results.add(tableDescriptor.getNameAsString());
            //System.out.println("Table Name:"+ tableDescriptor.getNameAsString());
        }
        admin.close();
        connection.close();
        return results;
    }
}
