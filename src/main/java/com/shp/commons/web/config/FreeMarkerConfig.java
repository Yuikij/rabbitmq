/**
 * 2016年9月29日 下午3:04:49
 * @author NOE DUBOIS(LJF)
 *
 **/
package com.shp.commons.web.config;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.TemplateException;

@Configuration
public class FreeMarkerConfig {

	private String ticketageMessage = "123(${plateNo}),您已经成功取号。请耐心等待叫号,谢谢合作.";
	private String callMessage = "123(${plateNo}),我们正在呼叫您,请到 ${door} 接受服务.";
	private String processMessage = "222(${plateNo}),服务开始.";
	private String endMessage = "333(${plateNo}),服务已结束,谢谢您的合作.";
	private String passMessage = "111(${plateNo}),您的票号已过号。请耐心等待我们再次叫号，谢谢合作.";


	
	@Bean
	public freemarker.template.Configuration freemarkerConfiguration() throws IOException, TemplateException {
		FreeMarkerConfigurationFactory factory = new FreeMarkerConfigurationFactory();
		// factory.setTemplateLoaderPaths("classpath:templates",// "src/main/resource/templates");
		
		factory.setDefaultEncoding("UTF-8");
		freemarker.template.Configuration config = factory.createConfiguration();
		StringTemplateLoader stringLoader = new StringTemplateLoader();
	

		stringLoader.putTemplate("ticket",ticketageMessage);
		stringLoader.putTemplate("call",callMessage);
		stringLoader.putTemplate("process",processMessage);
		stringLoader.putTemplate("end",endMessage);
		stringLoader.putTemplate("pass",passMessage);
		
		config.setTemplateLoader(stringLoader);
		return config;
	}

}
