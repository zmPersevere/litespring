package org.litespring.core.io.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;
import org.litespring.util.Assert;
import org.litespring.util.ClassUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 20:25 2019-10-14
 */
public class PackageResourceLoader {

    private static final Log logger = LogFactory.getLog(PackageResourceLoader.class);

    private final ClassLoader classLoader;

    public PackageResourceLoader(){
        this.classLoader = ClassUtils.getDefaultClassLoader();
    }

    /**
     * 可以指定加载的classLoader
     * @param classLoader
     */
    public PackageResourceLoader(ClassLoader classLoader){
        this.classLoader = classLoader;
    }

    /**
     * 获取当前classLoader
     * @return
     */
    public ClassLoader getClassLoader() {
        return this.classLoader;
    }

    /**
     * 获取Resource[]
     * @param basePackage 需要用到的包
     * @return
     * @throws IOException
     */
    public Resource[] getResources(String basePackage)throws IOException{
        Assert.notNull(basePackage , "basePackage must not be null ");
        //对传入的目录进行字符串转换
        String location = ClassUtils.convertClassNameToResourcePath(basePackage);
        //获取当前的classLoader
        ClassLoader cl = getClassLoader();
        //使用当前classLoader加载该目录
        URL url = cl.getResource(location);
        //根据url，生成文件
        File rootDir = new File(url.getFile());
        //对文件进行遍历，可能传入的是多层文件夹，一层一层取出全部文件，存入到LinkedHashSet中
        Set<File> matchingFiles = retrieveMatchingFiles(rootDir);
        //创建matchingFiles大笑的Resource[]
        Resource[] result = new Resource[matchingFiles.size()];
        int i = 0 ;
        //根据文件生成FileSystemResource
        for (File file : matchingFiles){
            result[i++] = new FileSystemResource(file);
        }
        return result;
    }

    /**
     * 对文件进行遍历，可能传入的是多层文件夹，一层一层取出全部文件，存入到LinkedHashSet中
     * @param rootDir 文件地址
     * @return 返回全部文件Set
     * @throws IOException
     */
    protected Set<File> retrieveMatchingFiles(File rootDir)throws IOException{
        if (!rootDir.exists()){
            if (logger.isDebugEnabled()){
                logger.debug("Skipping [" + rootDir.getAbsolutePath() + "] because it does not exist");
            }
            return Collections.emptySet();
        }
        if (!rootDir.isDirectory()){
            if (logger.isWarnEnabled()){
                logger.warn("Skipping [" + rootDir.getAbsolutePath() + "] because it does not denote a directory");
            }
            return Collections.emptySet();
        }
        if (!rootDir.canRead()){
            if (logger.isWarnEnabled()){
                logger.warn("Cannot search for matching files underneath directory [" + rootDir.getAbsolutePath()
                        + "] because the application is not allowed to read the directory" );
            }
            return Collections.emptySet();
        }
        Set<File> result = new LinkedHashSet<File>(8);
        //对该文件进行真正的取出
        doRetrieveMatchingFiles(rootDir,result);
        return result;

    }

    /**
     * 取出全部文件
     * @param dir
     * @param result
     * @throws IOException
     */
    protected void doRetrieveMatchingFiles(File dir ,Set<File> result)throws IOException{
        File[] dirContents = dir.listFiles();//得到该目录下全部文件
        if (dirContents == null){//如果是空，则警告！！！
            if (logger.isWarnEnabled()){
                logger.warn("Could not retrieve contents of directory [" + dir.getAbsolutePath() + "]" );
            }
            return;
        }
        for (File content : dirContents){//进行遍历
            if (content.isDirectory()){//如果是文件夹递归调用
                if (!content.canRead()){
                    if (logger.isDebugEnabled()){
                        logger.debug("Skipping subdirectory [" + dir.getAbsolutePath() +
                                "] because the application is not allowed to read the directory");
                    }
                }else {
                    doRetrieveMatchingFiles(content,result);
                }
            }else {//是文件则加入到result中
                result.add(content);
            }
        }
    }

}

