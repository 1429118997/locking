package com.ssm.redpacket.service;

import com.ssm.redpacket.bean.UserRedPacked;

public interface UserRedPackedService {

	public int insertRedPackedInfo(UserRedPacked userRedPacked);

	public Long grapRedPacketByRedis(Long RedpackedId, Long userId);

	public Long grapRedpackedByVsersion(Long redpackedId, Long userId);

	public Long grapRedpackedBySayLock(Long redpackedId, Long userId);

}
