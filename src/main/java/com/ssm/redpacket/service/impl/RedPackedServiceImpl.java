package com.ssm.redpacket.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ssm.redpacket.bean.RedPacked;
import com.ssm.redpacket.mapper.RedPackedDao;
import com.ssm.redpacket.service.RedPackedService;

@Service
public class RedPackedServiceImpl implements RedPackedService {

	@Autowired
	private RedPackedDao redPackedDao;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public RedPacked getRedPackedById(long redPackedId) {
		return redPackedDao.getRedPackedById(redPackedId);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int decrementRedpackedForStock(long redPackedId, long version) {

		return redPackedDao.decrementRedpackedForStock(redPackedId, version);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public RedPacked getRedPackedByIdToSayLocking(long redPackedId) {
		return redPackedDao.getRedPackedByIdToSayLocking(redPackedId);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int decrementRedpacked(long redPackedId) {
		return redPackedDao.decrementRedpacked(redPackedId);
	}

}
