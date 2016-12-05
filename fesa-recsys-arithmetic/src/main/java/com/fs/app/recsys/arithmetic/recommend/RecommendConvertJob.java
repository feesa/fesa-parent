package com.fs.app.recsys.arithmetic.recommend;

import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import com.fs.app.recsys.arithmetic.utils.HDFSUtils;

public class RecommendConvertJob implements Tool{

	public static class InnerMapper extends Mapper<LongWritable, Text, NullWritable, Text> {

		@Override
		protected void map(LongWritable key, Text value, Context context)throws IOException, InterruptedException {
			String line = value.toString();
			StringTokenizer tokenizer = new StringTokenizer(line);
			int cursor = 1;
			String cursorStr = "";
			
			String userid ="";
			String itemid="";
			while (tokenizer.hasMoreTokens()) {
				cursorStr = tokenizer.nextToken();
				switch (cursor) {
				case 1:
					userid = cursorStr;
					break;
				case 2:
					itemid = cursorStr;
					context.write(NullWritable.get(),new Text(userid + "," + itemid));
					break;
				default:
					break;
				}
				cursor++;
			}
		}
	}
	
	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://"+HDFSUtils.hdfs_nameservices+"");
		conf.set("dfs.nameservices", HDFSUtils.hdfs_nameservices);
		conf.set("dfs.ha.namenodes.nameservice1", "nn1,nn2");
		conf.set("dfs.namenode.rpc-address.nameservice1.nn1", HDFSUtils.hdfs_nn1);
		conf.set("dfs.namenode.rpc-address.nameservice1.nn2", HDFSUtils.hdfs_nn1);
		conf.set("dfs.client.failover.proxy.provider."+HDFSUtils.hdfs_nameservices+"",
				"org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
		Job job = new Job(conf, this.getClass().getSimpleName());
		String data_name = args[0];
		TextInputFormat.setInputPaths(job, "/" + data_name);

		job.setInputFormatClass(TextInputFormat.class);
		job.setMapperClass(InnerMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		job.setNumReduceTasks(0);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		Path p = new Path(HDFSUtils.hdfs_output);
		FileSystem fs = p.getFileSystem(conf);
		if (fs.exists(p)) {
			fs.delete(p, true);
			System.out.println("输出路径存在，已删除！");
		}
		FileOutputFormat.setOutputPath(job, p);
		return job.waitForCompletion(true) ? 0 : -1;
	}
	
	@Override
	public Configuration getConf() {
		return null;
	}

	@Override
	public void setConf(Configuration arg0) {
		
	}
}
