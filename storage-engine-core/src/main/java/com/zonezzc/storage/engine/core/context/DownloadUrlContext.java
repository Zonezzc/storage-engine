package com.zonezzc.storage.engine.core.context;

import lombok.Builder;
import lombok.Data;

/**
 * 文件下载路径的上下文实体信息
 *
 * @author Zhuzhicheng
 * @date 2023/8/22
 * @since
 */
@Data
@Builder
public class DownloadUrlContext {

	/**
	 * 文件的真实存储路径
	 */
	String realPath;

	/**
	 * 文件的过期时间，单位：秒
	 */
	Long expireTime;

	/**
	 * 文件的下载路径
	 */
	String downloadUrl;
}
