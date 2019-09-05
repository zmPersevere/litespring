package org.litespring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 18:12 2019-09-01
 */
public interface Resource {

    public InputStream getInputStream() throws IOException;

    public String getDescription();
}
