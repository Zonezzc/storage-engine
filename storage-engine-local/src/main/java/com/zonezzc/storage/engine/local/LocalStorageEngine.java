package com.zonezzc.storage.engine.local;

import com.zonezzc.storage.engine.core.AbstractStorageEngine;
import com.zonezzc.storage.engine.core.context.DeleteFileContext;
import com.zonezzc.storage.engine.core.context.DownloadUrlContext;
import com.zonezzc.storage.engine.core.context.ReadFileContext;
import com.zonezzc.storage.engine.core.context.StoreFileContext;
import com.zonezzc.storage.engine.core.utils.FileUtils;
import com.zonezzc.storage.engine.local.config.LocalStorageEngineConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 本地的文件存储引擎实现方案
 * @author zonezzc
 */
@Component
public class LocalStorageEngine extends AbstractStorageEngine {

    @Autowired
    private LocalStorageEngineConfig config;

    /**
     * 执行保存物理文件的动作
     * 下沉到具体的子类去实现
     *
     * @param context 上下文
     */
    @Override
    protected void doStore(StoreFileContext context) throws IOException {
        String basePath = config.getRootFilePath();
        String realFilePath = FileUtils.generateStoreFileRealPath(basePath, context.getFilename());
        FileUtils.writeStream2File(context.getInputStream(), new File(realFilePath), context.getTotalSize());
        context.setRealPath(realFilePath);
    }

    /**
     * 执行删除物理文件的动作
     * 下沉到子类去实现
     *
     * @param context 上下文
     * @throws IOException IOException
     */
    @Override
    protected void doDelete(DeleteFileContext context) throws IOException {
        FileUtils.deleteFiles(context.getRealFilePathList());
    }

    /**
     * 读取文件内容并写入到输出流中
     * 下沉到子类去实现
     *
     * @param context 上下文
     */
    @Override
    protected void doReadFile(ReadFileContext context) throws IOException {
        File file = new File(context.getRealPath());
        FileUtils.writeFile2OutputStream(new FileInputStream(file), context.getOutputStream(), file.length());
    }

	/**
	 * 确实下载url
	 *
	 * @param context 上下文
	 * @throws IOException IOException
	 */
	@Override
	protected void doDownloadUrl(DownloadUrlContext context) throws IOException {

	}
}
