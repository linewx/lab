package com.zz.bigdata.hadoop;

import org.apache.hadoop.util.ToolRunner;
import org.junit.Test;

public class HadoopTest {
    @Test
    public void testWordCount() throws Exception{
        String []args = new String[]{"/Users/luganlin/git/lab/java/bigdata/hadoop/src/test/resources/input.txt",
        "/tmp/output"};
        int exitCode = ToolRunner.run(new WordCount(), args);
        System.out.println(exitCode);
        //System.exit(exitCode);
    }



}
