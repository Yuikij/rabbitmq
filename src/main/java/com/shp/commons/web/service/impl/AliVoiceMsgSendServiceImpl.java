package com.shp.commons.web.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.shp.commons.web.service.VoiceMsgSendService;
import com.shp.commons.web.util.JsonUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcTtsNumSinglecallRequest;
import com.taobao.api.response.AlibabaAliqinFcTtsNumSinglecallResponse;

@Service
public class AliVoiceMsgSendServiceImpl implements VoiceMsgSendService {
	
	private static final Logger logger = Logger.getLogger(AliVoiceMsgSendServiceImpl.class);

	@Value("${com.shp.ali.message.sms.url}")
	private String url;
	@Value("${com.shp.ali.message.sms.key}")
	private String key;
	@Value("${com.shp.ali.message.sms.secret}")
	private String secret;
	@Value("${com.shp.ali.message.sms.sig}")
	private String sig;
	@Value("#{${com.shp.ali.message.voice.template}}")  
	private Map<String,String> voiceTemplate;
	@Value("${com.shp.ali.message.voice.displayno}")  
	private String displayNo;
	
	@Override
	public void sendMessage(Object object) {
				
		/*for(Entry<String, String> entry:voiceTemplate.entrySet()){
			System.out.println(entry.getKey() + "-" + entry.getValue());
		}*/
		@SuppressWarnings("unchecked")
		Map<String, Object> obj = (Map<String, Object>) object;
		Map<String, String> model = new HashMap<String,String>();
		String action = (String) obj.get("action");
		String plateNo = (String) obj.get("plateNo");
		String number = (String) obj.get("mobile");		
		
		if(number != null && !number.isEmpty() && action != null && !action.isEmpty() && plateNo != null && !plateNo.isEmpty()){
			model.put("plateNo", plateNo);
			if(action.equals("call")){
				String door = (String) obj.get("door");
				if(door != null && !door.isEmpty()){
					model.put("door", door);
				}else{
					model.put("door", "");
				}
			}
			if(action.equals("ticket")){
				String progress = (String) obj.get("progress");
				if(progress != null && !progress.isEmpty()){
					model.put("progress", progress);
				}else{
					model.put("progress", "0");
				}
			}			
		}else{
			return;
		}
		try {
			String json = JsonUtil.object2JsonString(model);
			String ttsCode = voiceTemplate.get(action);
			TaobaoClient client = new DefaultTaobaoClient(url, key, secret);
			AlibabaAliqinFcTtsNumSinglecallRequest req = new AlibabaAliqinFcTtsNumSinglecallRequest();
			req.setExtend("12345");
			req.setTtsParamString(json);
			req.setCalledNum(number);
			req.setCalledShowNum(displayNo);
			req.setTtsCode(ttsCode);
			AlibabaAliqinFcTtsNumSinglecallResponse rsp = client.execute(req);
			JsonNode node = JsonUtil.String2JsonNode(rsp.getBody());
			if(node.has("error_response")){
				logger.error("voice call error: " + number + "-" + rsp.getBody());
			}
		} catch (ApiException | IOException e) {
			logger.error(e.getMessage());
		}
		
		
		
	}
	
	

}
