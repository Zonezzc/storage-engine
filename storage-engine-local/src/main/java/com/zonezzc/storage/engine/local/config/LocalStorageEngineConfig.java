package com.zonezzc.storage.engine.local.config;

import com.zonezzc.storage.engine.core.utils.FileUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 本地存储引擎配置
 *
 * @author yarzzc
 */
@Component
@ConfigurationProperties(prefix = "com.zonezzc.storage.engine.local")
@Data
public class LocalStorageEngineConfig {

	/**
	 * 实际存放路径的前缀
	 */
	private String rootFilePath = FileUtils.generateDefaultStoreFileRealPath();

	// /**
	//  * 实际存放文件分片的路径的前缀
	//  */
	// private String rootFileChunkPath = FileUtils.generateDefaultStoreFileChunkRealPath();

}
