package com.cloudrain.derecho.ds.zk.framework;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.transaction.CuratorTransaction;
import org.apache.curator.framework.api.transaction.CuratorTransactionFinal;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;

import java.util.Collection;

/**
 * Created by lugan on 12/26/2016.
 */
public class TransactionExample {
    public static Collection<CuratorTransactionResult> transaction(CuratorFramework client) throws Exception {
        Collection<CuratorTransactionResult> results = client.inTransaction()
                .create().forPath("/a/path", "some data".getBytes())
                .and()
                .setData().forPath("/another/path", "other data".getBytes())
                .and()
                .delete().forPath("/yet/another/path")
                .and()
                .commit();

        for (CuratorTransactionResult result : results) {
            System.out.println(result.getForPath() + "-" + result.getType());
        }
        return results;
    }

    public static CuratorTransaction startTransaction(CuratorFramework client) {
        // start the transaction builder
        return client.inTransaction();
    }

    public static CuratorTransactionFinal addCreateToTransaction(CuratorTransaction transaction) throws Exception {
        // add a create operation
        return transaction.create().forPath("/a/path", "some data".getBytes()).and();
    }

    public static CuratorTransactionFinal addDeleteToTransaction(CuratorTransaction transaction) throws Exception {
        // add a delete operation
        return transaction.delete().forPath("/another/path").and();
    }

    public static void commitTransaction(CuratorTransactionFinal transaction) throws Exception {
        // commit the transaction
        transaction.commit();
    }


}
