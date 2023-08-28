package com.zonezzc.storage.engine.cos.config;

import cn.hutool.core.util.StrUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * COS文件存储引擎配置类
 *
 * @author Zhuzhicheng
 * @date 2023/7/28
 * @since
 */
@Data
@Component
@ConfigurationProperties(prefix = "com.zonezzc.storage.engine.cos")
public class CosStorageEngineConfig {

	/** 端点 */
	private String region;

	/** 访问密钥id */
	private String secretId;

	/** 访问密钥秘密 */
	private String secretKey;

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
	public COSClient cosClient() {
		if (StrUtil.isBlankIfStr(region) || StrUtil.isBlankIfStr(secretId) || StrUtil.isBlankIfStr(secretKey)) {
			throw new IllegalArgumentException("endpoint/accessKeyId/accessKeySecret must not be null");
		}
		// 1 初始化用户身份信息（secretId, secretKey）。
		// SECRETID 和 SECRETKEY 请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
		COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
		// 2 设置 bucket 的地域, COS 地域的简称请参见 https://cloud.tencent.com/document/product/436/6224
		// clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
		ClientConfig clientConfig = new ClientConfig(new Region(region));
		// 这里建议设置使用 https 协议
		// 从 5.6.54 版本开始，默认使用了 https
		clientConfig.setHttpProtocol(HttpProtocol.https);
		// 3 生成 cos 客户端。
		return new COSClient(cred, clientConfig);
	}
}
