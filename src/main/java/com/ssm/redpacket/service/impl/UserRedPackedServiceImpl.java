package com.ssm.redpacket.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ssm.redpacket.bean.RedPacked;
import com.ssm.redpacket.bean.UserRedPacked;
import com.ssm.redpacket.mapper.UserRedPackedDao;
import com.ssm.redpacket.service.RedPackedService;
import com.ssm.redpacket.service.RediSaveService;
import com.ssm.redpacket.service.UserRedPackedService;

import redis.clients.jedis.Jedis;

@Service
public class UserRedPackedServiceImpl implements UserRedPackedService {

	@Autowired
	private UserRedPackedDao userRedPackedDao;

	@Autowired
	private RedPackedService redPackedService;

	@Autowired
	private RediSaveService redisSaveService;

	@Autowired
	private RedisTemplate redisTemplate;

	String script = "local listkey='user_list_'..KEYS[1]\r\n" + "local redpackedkey='redpacked_'..KEYS[1]\r\n"
			+ "local stock=tonumber(redis.call('hget',redpackedkey,'stock'))\r\n" + "if stock<=0\r\n"
			+ "then return 0\r\n" + "end\r\n" + "stock=stock-1\r\n"
			+ "redis.call('hset',redpackedkey,'stock',tostring(stock))\r\n" + "redis.call('rpush',listkey,ARGV[1])\r\n"
			+ "if stock==0\r\n" + "then return 2 end\r\n" + "return 1\r\n";

	String luasha = null;

	public int insertRedPackedInfo(UserRedPacked userRedPacked) {
		return userRedPackedDao.insertRedPackedInfo(userRedPacked);
	}

	public Long grapRedPacketByRedis(Long redpackedId, Long userId) {
		Jedis jedis = (Jedis) redisTemplate.getConnectionFactory().getConnection().getNativeConnection();
		Long result = null;
		try {
			if (luasha == null) {
				luasha = jedis.scriptLoad(script);
			}
			Object evalsha = jedis.evalsha(luasha, 1, redpackedId + "", userId + "_" + System.currentTimeMillis());
			result = (Long) evalsha;
			if (result == 2) {

				String hget = jedis.hget("redpacked_" + redpackedId, "unit_amount");
				double amount = Double.parseDouble(hget);
				redisSaveService.saveUserRedpactedByRedis(redpackedId, amount);
			}
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;

	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Long grapRedpackedByVsersion(Long redpackedId, Long userId) {

		for (int i = 0; i < 5; i++) {
			RedPacked redPacked = redPackedService.getRedPackedById(redpackedId);

			if (redPacked.getStock() > 0) {
				int flag = redPackedService.decrementRedpackedForStock(redpackedId, redPacked.getVersion());
				if (flag == 0) {
					continue;
				}
				UserRedPacked userRedPacked = new UserRedPacked();
				userRedPacked.setUserId(userId);
				userRedPacked.setRedPacketId(redpackedId);
				userRedPacked.setAmount(redPacked.getUnitAmount());
				userRedPacked.setNote("抢到" + redPacked.getUnitAmount() + "元");
				long result = userRedPackedDao.insertRedPackedInfo(userRedPacked);
				return result;
			}

			return -1L;
		}
		return -1L;
	}

	public Long grapRedpackedBySayLock(Long redpackedId, Long userId) {
		RedPacked redPacked = redPackedService.getRedPackedByIdToSayLocking(redpackedId);

		if (redPacked.getStock() > 0) {
			redPackedService.decrementRedpacked(redpackedId);
			UserRedPacked userRedPacked = new UserRedPacked();
			userRedPacked.setUserId(userId);
			userRedPacked.setRedPacketId(redpackedId);
			userRedPacked.setAmount(redPacked.getUnitAmount());
			userRedPacked.setNote("抢到" + redPacked.getUnitAmount() + "元");
			long result = userRedPackedDao.insertRedPackedInfo(userRedPacked);
			return result;
		}

		return -1L;
	}

}
