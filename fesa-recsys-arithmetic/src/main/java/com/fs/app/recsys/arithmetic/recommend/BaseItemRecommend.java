package com.fs.app.recsys.arithmetic.recommend;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.ToolRunner;
import org.apache.mahout.cf.taste.hadoop.item.RecommenderJob;
import com.fs.app.recsys.arithmetic.utils.HDFSUtils;

public class BaseItemRecommend extends IRecommend{

	public void Recommend() throws Exception{
		Configuration conf = new Configuration();
		conf.setBoolean("mapreduce.app-submission.cross-platform", true);// 配置使用跨平台提交任务 
		conf.set("fs.defaultFS", HDFSUtils.hdfs_input);
		conf.set("dfs.nameservices",HDFSUtils.hdfs_nameservices);
		conf.set("dfs.ha.namenodes.nameservice1", "nn1,nn2");
		conf.set("dfs.namenode.rpc-address.nameservice1.nn1", HDFSUtils.hdfs_nn1);
		conf.set("dfs.namenode.rpc-address.nameservice1.nn2", HDFSUtils.hdfs_nn1);
		conf.set("dfs.client.failover.proxy.provider."+HDFSUtils.hdfs_nameservices+"","org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
		Path p = new Path(HDFSUtils.hdfs_output);
		Path ptmp = new Path(HDFSUtils.hdfs_tmp);
		FileSystem fs = p.getFileSystem(conf);
		if(fs.exists(ptmp)){
			fs.delete(ptmp,true);
			System.out.println("临时路径存在，已删除！");
		}
		if (fs.exists(p)) {
			fs.delete(p, true);
			System.out.println("输出路径存在，已删除！");
		}
		RecommenderJob job = new RecommenderJob();
		job.setConf(conf);
		StringBuilder sb = new StringBuilder();
		sb.append("--input ").append(HDFSUtils.hdfs_input);
		sb.append(" --output ").append(HDFSUtils.hdfs_output);
		sb.append(" --booleanData true");
		sb.append(" --similarityClassname org.apache.mahout.math.hadoop.similarity.cooccurrence.measures.TanimotoCoefficientSimilarity");
		sb.append(" --tempDir ").append(HDFSUtils.hdfs_tmp);
		String[] args = sb.toString().split(" ");
		int result1 = job.run(args);
		System.out.println("==result1:"+result1+"==");
//		int result = ToolRunner.run(new RecommendConvertJob(), new String[] { "datas.txt" });
//		System.out.println("==执行结果:"+result+"==");
	}
}
