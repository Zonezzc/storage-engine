package com.zonezzc.storage.engine.core;

import com.zonezzc.storage.engine.core.context.DeleteFileContext;
import com.zonezzc.storage.engine.core.context.DownloadUrlContext;
import com.zonezzc.storage.engine.core.context.ReadFileContext;
import com.zonezzc.storage.engine.core.context.StoreFileContext;

import java.io.IOException;

/**
 * 文件存储引擎模块的顶级接口
 * 该接口定义所有需要向外暴露给业务层面的相关文件操作的功能
 * 业务方只能调用该接口的方法，而不能直接使用具体的实现方案去做业务调用
 *
 * @author Zonezzc
 * @date 2023/7/28
 * @since
 */
public interface StorageEngine {
	/**
	 * 存储物理文件
	 *
	 * @param context 上下文
	 * @throws IOException IOException
	 */
	void store(StoreFileContext context) throws IOException;

	/**
	 * 删除物理文件
	 *
	 * @param context 上下文
	 * @throws IOException IOException
	 */
	void delete(DeleteFileContext context) throws IOException;

	/**
	 * 读取文件内容写入到输出流中
	 *
	 * @param context 上下文
	 * @throws IOException IOException
	 */
	void realFile(ReadFileContext context) throws IOException;

	/**
	 * 下载url
	 *
	 * @param context 上下文
	 * @throws IOException IOException
	 */
	void downloadUrl(DownloadUrlContext context) throws IOException;
}
