package org.litespring.core.io;


import org.litespring.util.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 18:17 2019-09-01
 */
public class FileSystemResource implements Resource {

    private final String path;

    private final File file;

    public FileSystemResource(String path){
        Assert.notNull( path , " Path must not be null ");
        this.file = new File( path );
        this.path = path;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        //把文件转换成流
        return new FileInputStream( this.path );
    }

    @Override
    public String getDescription() {
        return "file [" + this.file.getAbsolutePath() + "]";
    }

}
