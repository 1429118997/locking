package com.ssm.redpacket.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ssm.redpacket.bean.RedPacked;

@Repository
public interface RedPackedDao {

	public RedPacked getRedPackedById(long redPackedId);

	public int decrementRedpackedForStock(@Param("redPackedId") long redPackedId, @Param("vsersion") long vsersion);

	public int decrementRedpacked(@Param("redPackedId") long redPackedId);

	public RedPacked getRedPackedByIdToSayLocking(long redPackedId);
}
