package com.zonezzc.storage.engine.kodo.config;

import cn.hutool.core.util.StrUtil;
import com.qiniu.util.Auth;
import com.zonezzc.storage.engine.kodo.KodoStorageEngine;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * COS文件存储引擎配置类
 *
 * @author Zonezzc
 * @date 2023/7/28
 * @since
 */
@Data
@Component
@ConfigurationProperties(prefix = "com.zonezzc.storage.engine.kodo")
public class KodoStorageEngineConfig implements InitializingBean {

	/** 端点 */
	private String region;

	/** 访问密钥id */
	private String accessKey;

	/** 访问密钥秘密 */
	private String secretKey;

	/** bucket名称 */
	private String bucketName;

	/** 自动创建bucket */
	private Boolean autoCreateBucket = Boolean.TRUE;

	/**
	 * kodo客户端
	 *
	 * @return {@code OSSClient}
	 */
	@Bean
	public Auth kodoAuth() {
		if (StrUtil.isBlankIfStr(region) || StrUtil.isBlankIfStr(accessKey) || StrUtil.isBlankIfStr(secretKey)) {
			throw new IllegalArgumentException("endpoint/accessKeyId/accessKeySecret must not be null");
		}
		return Auth.create(accessKey, secretKey);
	}

	@Configuration
	@Import(KodoStorageEngine.class)
	public static class MapperScannerRegistrarNotFoundConfiguration implements InitializingBean {
		@Override
		public void afterPropertiesSet() {
		}
	}

	@Override
	public void afterPropertiesSet() {
	}
}
