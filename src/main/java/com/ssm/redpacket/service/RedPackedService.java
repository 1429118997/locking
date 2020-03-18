package com.ssm.redpacket.service;

import com.ssm.redpacket.bean.RedPacked;

public interface RedPackedService {

	public RedPacked getRedPackedById(long redPackedId);

	public RedPacked getRedPackedByIdToSayLocking(long redPackedId);

	public int decrementRedpackedForStock(long redPackedId, long version);

	public int decrementRedpacked(long redPackedId);
}
