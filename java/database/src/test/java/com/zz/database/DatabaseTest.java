package com.zz.database;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by luganlin on 11/06/2018.
 */

public class DatabaseTest {

    @Test
    public void connectionTest() {
        String userName = "root";
        String password = "root";
        String connectionString = "jdbc:mysql://localhost:3306/datalogic_new?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true";

        Assert.assertTrue(Database.testConnection(connectionString, userName, password));
    }
}
