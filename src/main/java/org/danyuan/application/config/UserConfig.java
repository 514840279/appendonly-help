package org.danyuan.application.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "user")
@Data
public class UserConfig {

	// 报告文档生成路径
	private String path;
	private String file;
	private String delStartWith;
	private String delContextStartWith;
	private String expStartWith;
	private String expContextStartWith;
	private String encoding;
}
