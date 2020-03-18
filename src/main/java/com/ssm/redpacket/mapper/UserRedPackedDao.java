package com.ssm.redpacket.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ssm.redpacket.bean.RedPacked;
import com.ssm.redpacket.bean.UserRedPacked;


@Repository
public interface UserRedPackedDao {

	public int insertRedPackedInfo(UserRedPacked userRedPacked);
}
