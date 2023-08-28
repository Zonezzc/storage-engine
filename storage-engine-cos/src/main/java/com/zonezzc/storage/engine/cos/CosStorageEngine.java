package com.zonezzc.storage.engine.cos;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.COSObject;
import com.zonezzc.storage.engine.cos.config.CosStorageEngineConfig;
import com.zonezzc.storage.engine.core.AbstractStorageEngine;
import com.zonezzc.storage.engine.core.context.DeleteFileContext;
import com.zonezzc.storage.engine.core.context.DownloadUrlContext;
import com.zonezzc.storage.engine.core.context.ReadFileContext;
import com.zonezzc.storage.engine.core.context.StoreFileContext;
import com.zonezzc.storage.engine.core.utils.FileUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Cos存储引擎实现类
 *
 * @author Zonezzc
 * @date 2023/8/22
 * @since
 */
@Component
public class CosStorageEngine extends AbstractStorageEngine {

	@Resource
	private CosStorageEngineConfig config;

	@Resource
	private COSClient cosClient;

	/**
	 * 执行保存物理文件的动作
	 * 下沉到具体的子类去实现
	 *
	 * @param context 上下文
	 */
	@Override
	protected void doStore(StoreFileContext context) throws IOException {
		String realPath = FileUtils.generateStoreFileRealPath(context.getFilePath(), context.getFilename());
		cosClient.putObject(config.getBucketName(), realPath, context.getInputStream(), null);
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
		realFilePathList.forEach(realPath -> cosClient.deleteObject(config.getBucketName(), realPath));
	}

	/**
	 * 读取文件内容并写入到输出流中
	 * 下沉到子类去实现
	 *
	 * @param context 上下文
	 */
	@Override
	protected void doReadFile(ReadFileContext context) throws IOException {
		COSObject cosObject = cosClient.getObject(config.getBucketName(), context.getRealPath());
		if (Objects.isNull(cosObject)) {
			throw new RuntimeException("文件读取失败，文件的名称为：" + context.getRealPath());
		}
		FileUtils.writeStream2StreamNormal(cosObject.getObjectContent(), context.getOutputStream());
	}

	/**
	 * 确实下载url
	 *
	 * @param context 上下文
	 * @throws IOException IOException
	 */
	@Override
	protected void doDownloadUrl(DownloadUrlContext context) throws IOException {
		// 设置签名URL过期时间，单位为毫秒。本示例以设置过期时间为1小时为例。
		Date expiration = new Date(new Date().getTime() + context.getExpireTime() * 1000L);
		// 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
		URL url = cosClient.generatePresignedUrl(config.getBucketName(), context.getRealPath(), expiration);
		context.setDownloadUrl(url.toString());
	}
}
