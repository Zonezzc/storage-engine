package com.yarzzc.storage.engine.kodo;

import com.qiniu.storage.*;
import com.qiniu.util.Auth;
import com.yarzzc.storage.engine.kodo.config.KodoStorageEngineConfig;
import com.zonezzc.storage.engine.core.AbstractStorageEngine;
import com.zonezzc.storage.engine.core.context.DeleteFileContext;
import com.zonezzc.storage.engine.core.context.DownloadUrlContext;
import com.zonezzc.storage.engine.core.context.ReadFileContext;
import com.zonezzc.storage.engine.core.context.StoreFileContext;
import com.zonezzc.storage.engine.core.utils.FileUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * Kodo存储引擎实现类
 *
 * @author Zhuzhicheng
 * @date 2023/8/22
 * @since
 */
@Component
public class KodoStorageEngine extends AbstractStorageEngine {
	@Resource
	private KodoStorageEngineConfig config;

	@Resource
	private Auth kodoAuth;

	/**
	 * 执行保存物理文件的动作
	 * 下沉到具体的子类去实现
	 *
	 * @param context 上下文
	 */
	@Override
	protected void doStore(StoreFileContext context) throws IOException {
		String realPath = FileUtils.generateStoreFileRealPath(context.getFilePath(), context.getFilename());

		Configuration cfg = new Configuration(Region.regionCnEast2());
		cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
		//...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);
		String upToken = kodoAuth.uploadToken(config.getBucketName());
		uploadManager.put(context.getInputStream(), realPath, upToken, null, null);

		context.setRealPath(realPath);
	}

	/**
	 * 执行删除物理文件的动作
	 * 下沉到子类去实现
	 *
	 * @param context 上下文
	 */
	@Override
	protected void doDelete(DeleteFileContext context) throws IOException {
		List<String> realFilePathList = context.getRealFilePathList();
		BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
		batchOperations.addDeleteOp(config.getBucketName(), realFilePathList.toArray(new String[0]));
	}

	/**
	 * 读取文件内容并写入到输出流中
	 * 下沉到子类去实现
	 *
	 * @param context 上下文
	 */
	@Override
	protected void doReadFile(ReadFileContext context) throws IOException {
		throw new RuntimeException("七牛云暂不支持该功能");
	}

	/**
	 * 确实下载url
	 *
	 * @param context 上下文
	 * @throws IOException IOException
	 */
	@Override
	protected void doDownloadUrl(DownloadUrlContext context) throws IOException {
		DownloadUrl url = new DownloadUrl("rzscf5l0a.bkt.clouddn.com", false, context.getRealPath());
		// 带有效期
		long expireInSeconds = context.getExpireTime();// 1小时，可以自定义链接过期时间
		long deadline = System.currentTimeMillis() / 1000 + expireInSeconds;
		String urlString = url.buildURL(kodoAuth, deadline);
		context.setDownloadUrl(urlString);
	}
}
