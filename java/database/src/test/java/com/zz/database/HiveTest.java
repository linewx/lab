package com.zz.database;

import org.junit.Assert;
import org.junit.Test;

public class HiveTest {
    @Test
    public void testHive() {
        Assert.assertTrue(Database.testConnection("jdbc:hive2://localhost:10000", "hive", ""));
    }
}
