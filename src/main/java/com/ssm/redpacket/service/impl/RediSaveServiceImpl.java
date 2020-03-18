package com.ssm.redpacket.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ssm.redpacket.bean.RedPacked;
import com.ssm.redpacket.bean.UserRedPacked;
import com.ssm.redpacket.service.RedPackedService;
import com.ssm.redpacket.service.RediSaveService;

@Service
public class RediSaveServiceImpl implements RediSaveService {

	private static int TIME_SIZE = 1000;
	private static String PREFIX = "user_list_";

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private DataSource dataSource;

	@Async
	public void saveUserRedpactedByRedis(long redpacktedId, Double amount) {
		System.out.println("开始保存数据");
		long start = System.currentTimeMillis();
		BoundListOperations<String, String> boundListOps = redisTemplate.boundListOps(PREFIX + redpacktedId);
		Long size = boundListOps.size();
		System.out.println(size);
		int count = 0;
		long time = size % TIME_SIZE == 0 ? size / TIME_SIZE : (size / TIME_SIZE) + 1;
		System.out.println("time：" + time);
		List<UserRedPacked> list = new ArrayList<UserRedPacked>(TIME_SIZE);

		try {
			for (int i = 0; i < time; i++) {
				System.out.println("i:" + i);
				List<String> userRepacked = null;

				if (i == 0) {
					userRepacked = boundListOps.range(0, TIME_SIZE - 1);
				} else {
					userRepacked = boundListOps.range(i * TIME_SIZE, ((i + 1) * TIME_SIZE) - 1);
				}
				System.out.println("userRedpacked" + userRepacked.size());
				list.clear();
				for (String ur : userRepacked) {
					System.out.println("ur" + ur.toString());
					String[] split = ur.split("_");
					Long userId = Long.parseLong(split[0]);
					Long timeStamp = Long.parseLong(split[1]);
					UserRedPacked temp = new UserRedPacked();
					temp.setUserId(userId);
					temp.setRedPacketId(redpacktedId);
					temp.setGrabTime(new Timestamp(timeStamp));
					temp.setAmount(amount);
					temp.setNote("抢到" + amount + "红包");

					list.add(temp);
				}
				System.out.println(list.size());
				count += executeBatch(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		redisTemplate.delete(PREFIX+redpacktedId);

		long end = System.currentTimeMillis();System.out.println("数据保存完毕，耗时"+(end-start)+"ms");
	}


	private int executeBatch(List<UserRedPacked> list) {
		Connection connection = null;
		Statement statement = null;
		int count[] = null;

		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			connection.setAutoCommit(false);
			for (UserRedPacked userRedPacked : list) {
				userRedPacked.toString();
				String sql1 = "UPDATE	redpacted rp SET rp.`stock`=rp.`stock`-1 WHERE rp.`red_packed_id`="
						+ userRedPacked.getRedPacketId();
				String sql2 = "INSERT user_redpacked(user_id,red_packed,amount,grab_time,noto) VALUES("
						+ userRedPacked.getUserId() + "," + userRedPacked.getRedPacketId() + ","
						+ userRedPacked.getAmount() + ",now(),'" + userRedPacked.getNote() + "')";
				statement.addBatch(sql1);
				statement.addBatch(sql2);
			}
			count = statement.executeBatch();
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return count.length / 2;
	}

}
