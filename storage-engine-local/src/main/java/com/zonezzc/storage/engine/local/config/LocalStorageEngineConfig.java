package com.zonezzc.storage.engine.local.config;

import com.zonezzc.storage.engine.core.utils.FileUtils;
import com.zonezzc.storage.engine.local.LocalStorageEngine;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * 本地存储引擎配置
 *
 * @author Zonezzc
 */
@Component
@ConfigurationProperties(prefix = "com.zonezzc.storage.engine.local")
@Data
public class LocalStorageEngineConfig implements InitializingBean {

	/**
	 * 实际存放路径的前缀
	 */
	private String rootFilePath = FileUtils.generateDefaultStoreFileRealPath();

	// /**
	//  * 实际存放文件分片的路径的前缀
	//  */
	// private String rootFileChunkPath = FileUtils.generateDefaultStoreFileChunkRealPath();

	@Configuration
	@Import(LocalStorageEngine.class)
	public static class MapperScannerRegistrarNotFoundConfiguration implements InitializingBean {
		@Override
		public void afterPropertiesSet() {
		}
	}

	@Override
	public void afterPropertiesSet() {
	}
}
