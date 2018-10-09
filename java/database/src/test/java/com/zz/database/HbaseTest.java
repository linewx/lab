package com.zz.database;

import com.zz.database.hbase.Hbase;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class HbaseTest {
    @Test
    public void testTableList() throws IOException{
        Hbase hbase = new Hbase();
        Assert.assertTrue(hbase.listTables().contains("t1"));
    }
}
