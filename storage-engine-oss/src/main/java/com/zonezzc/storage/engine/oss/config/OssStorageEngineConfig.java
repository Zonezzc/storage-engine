package com.zonezzc.storage.engine.oss.config;

import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * OSS文件存储引擎配置类
 *
 * @author Zhuzhicheng
 * @date 2023/7/28
 * @since
 */
@Data
@Component
@ConfigurationProperties(prefix = "com.zonezzc.storage.engine.oss")
public class OssStorageEngineConfig {

	/** 端点 */
	private String endpoint;

	/** 访问密钥id */
	private String accessKeyId;

	/** 访问密钥秘密 */
	private String accessKeySecret;

	/** bucket名称 */
	private String bucketName;

	/** 自动创建bucket */
	private Boolean autoCreateBucket = Boolean.TRUE;

	/**
	 * oss客户端
	 *
	 * @return {@code OSSClient}
	 */
	@Bean(destroyMethod = "shutdown")
	public OSS ossClient() {
		if (StrUtil.isBlankIfStr(endpoint) || StrUtil.isBlankIfStr(accessKeyId) || StrUtil.isBlankIfStr(accessKeySecret)) {
			throw new IllegalArgumentException("endpoint/accessKeyId/accessKeySecret must not be null");
		}
		CredentialsProvider credentialsProvider = new DefaultCredentialProvider(accessKeyId, accessKeySecret);
		// ClientConfiguration clientConfiguration = new ClientConfiguration();
		return new OSSClientBuilder().build(endpoint, credentialsProvider);
	}

	// /**
	//  * oss客户端
	//  *
	//  * @return {@code OSSClient}
	//  */
	// @Bean(destroyMethod = "shutdown")
	// public OSSClient ossClient() {
	// 	if (StrUtil.isBlankIfStr(endpoint) || StrUtil.isBlankIfStr(accessKeyId) || StrUtil.isBlankIfStr(accessKeySecret)) {
	// 		throw new IllegalArgumentException("endpoint/accessKeyId/accessKeySecret must not be null");
	// 	}
	// 	return new OSSClient(endpoint, accessKeyId, accessKeySecret);
	// }
}
