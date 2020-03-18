package com.ssm.redpacket.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.redpacket.service.UserRedPackedService;

@Controller
@RequestMapping("/redis")
public class RedisControler {
    
	
	/**
	 * ʹ��redis��20000��������Ҫ10s
	 * ʹ�ñ�������20000��������Ҫ1��30s
	 * ʹ���ֹ�����20000��������Ҫ59s
	 */
	
	
	@Autowired
	private UserRedPackedService userRedPackedService;
	
	@RequestMapping("/redis")
	@ResponseBody
	public Map<String,String> test(Long redpackedId,Long userId) {
		System.out.println(redpackedId);
		System.out.println(userId);
		HashMap<String, String> hashMap = new HashMap<String, String>();
		Long result = userRedPackedService.grapRedPacketByRedis(redpackedId, userId);
		hashMap.put("success", "�����");
		if(result>0) {
			hashMap.put("msg", "�ɹ�");
		}else {
			hashMap.put("msg", "ʧ��");
		}
		return hashMap;
	}
	
	@RequestMapping("/version")
	@ResponseBody
	public Map<String,String> version(Long redpackedId,Long userId) {
		System.out.println(redpackedId);
		System.out.println(userId);
		HashMap<String, String> hashMap = new HashMap<String, String>();
		Long result = userRedPackedService.grapRedpackedByVsersion(redpackedId, userId);
		hashMap.put("success", "�����");
		if(result>0) {
			hashMap.put("msg", "�ɹ�");
		}else {
			hashMap.put("msg", "ʧ��");
		}
		return hashMap;
	}
	
	@RequestMapping("/saylocking")
	@ResponseBody
	public Map<String,String> sayLocking(Long redpackedId,Long userId) {
		System.out.println(redpackedId);
		System.out.println(userId);
		HashMap<String, String> hashMap = new HashMap<String, String>();
		Long result = userRedPackedService.grapRedpackedBySayLock(redpackedId, userId);
		hashMap.put("success", "�����");
		if(result>0) {
			hashMap.put("msg", "�ɹ�");
		}else {
			hashMap.put("msg", "ʧ��");
		}
		return hashMap;
	}
	
	
}
