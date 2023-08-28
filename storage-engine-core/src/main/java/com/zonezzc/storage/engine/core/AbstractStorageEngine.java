package com.zonezzc.storage.engine.core;

import cn.hutool.core.lang.Assert;
import com.zonezzc.storage.engine.core.context.DeleteFileContext;
import com.zonezzc.storage.engine.core.context.DownloadUrlContext;
import com.zonezzc.storage.engine.core.context.ReadFileContext;
import com.zonezzc.storage.engine.core.context.StoreFileContext;

import java.io.IOException;

/**
 * 顶级文件存储引擎的公用父类
 *
 * @author Zonezzc
 * @date 2023/7/28
 * @since
 */
public abstract class AbstractStorageEngine implements StorageEngine {

	/**
	 * 存储物理文件
	 * <p>
	 * 1、参数校验
	 * 2、执行动作
	 *
	 * @param context 上下文
	 */
	@Override
	public void store(StoreFileContext context) throws IOException {
		checkStoreFileContext(context);
		doStore(context);
	}

	/**
	 * 执行保存物理文件的动作
	 * 下沉到具体的子类去实现
	 *
	 * @param context 上下文
	 */
	protected abstract void doStore(StoreFileContext context) throws IOException;

	/**
	 * 校验上传物理文件的上下文信息
	 *
	 * @param context 上下文
	 */
	private void checkStoreFileContext(StoreFileContext context) {
		Assert.notBlank(context.getFilename(), "文件名称不能为空");
		Assert.notBlank(context.getFilePath(), "文件的存储路径不能为空");
		Assert.notNull(context.getTotalSize(), "文件的总大小不能为空");
		Assert.notNull(context.getInputStream(), "文件不能为空");
	}

	/**
	 * 删除物理文件
	 * <p>
	 * 1、参数校验
	 * 2、执行动作
	 *
	 * @param context 上下文
	 */
	@Override
	public void delete(DeleteFileContext context) throws IOException {
		checkDeleteFileContext(context);
		doDelete(context);
	}

	/**
	 * 执行删除物理文件的动作
	 * 下沉到子类去实现
	 *
	 * @param context 上下文
	 */
	protected abstract void doDelete(DeleteFileContext context) throws IOException;

	/**
	 * 校验删除物理文件的上下文信息
	 *
	 * @param context 上下文
	 */
	private void checkDeleteFileContext(DeleteFileContext context) {
		Assert.notEmpty(context.getRealFilePathList(), "要删除的文件路径列表不能为空");
	}

	/**
	 * 读取文件内容写入到输出流中
	 * <p>
	 * 1、参数校验
	 * 2、执行动作
	 *
	 * @param context 上下文
	 */
	@Override
	public void realFile(ReadFileContext context) throws IOException {
		checkReadFileContext(context);
		doReadFile(context);
	}

	/**
	 * 读取文件内容并写入到输出流中
	 * 下沉到子类去实现
	 *
	 * @param context 上下文
	 */
	protected abstract void doReadFile(ReadFileContext context) throws IOException;

	/**
	 * 文件读取参数校验
	 *
	 * @param context 上下文
	 */
	private void checkReadFileContext(ReadFileContext context) {
		Assert.notBlank(context.getRealPath(), "文件真实存储路径不能为空");
		Assert.notNull(context.getOutputStream(), "文件的输出流不能为空");
	}

	/**
	 * 下载url
	 *
	 * @param context 上下文
	 * @throws IOException IOException
	 */
	@Override
	public void downloadUrl(DownloadUrlContext context) throws IOException {
		checkDownloadUrlContext(context);
		doDownloadUrl(context);
	}

	private void checkDownloadUrlContext(DownloadUrlContext context) {
		Assert.notBlank(context.getRealPath(), "文件真实存储路径不能为空");
		Assert.notNull(context.getExpireTime(), "文件的过期时间不能为空");
	}

	/**
	 * 确实下载url
	 *
	 * @param context 上下文
	 * @throws IOException IOException
	 */
	protected abstract void doDownloadUrl(DownloadUrlContext context) throws IOException;
}
