package org.litespring.core.type.classreading;

import org.litespring.core.type.ClassMetadata;
import org.litespring.util.ClassUtils;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.Opcodes;
import org.springframework.asm.SpringAsmInfo;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 21:00 2019-10-14
 */
public class ClassMetadataReadingVisitor extends ClassVisitor implements ClassMetadata {

    private String className;

    private boolean isInterface;

    private boolean isAbstract;

    private boolean isFinal;

    private String superClassName;

    private String[] interfaces;



    public ClassMetadataReadingVisitor() {
        super( SpringAsmInfo.ASM_VERSION);
    }


    /**
     * classReader回掉的方法
     * @param version
     * @param access
     * @param name
     * @param signature
     * @param supername
     * @param interfaces
     */
    public void visit(int version, int access, String name, String signature, String supername, String[] interfaces) {
        this.className = ClassUtils.convertResourcePathToClassName(name);//获取className
        this.isInterface = ((access & Opcodes.ACC_INTERFACE) != 0);//获取接口
        this.isAbstract = ((access & Opcodes.ACC_ABSTRACT) != 0);//是否是抽象的
        this.isFinal = ((access & Opcodes.ACC_FINAL) != 0);//是否是Final
        if (supername != null) {//父类不为空
            this.superClassName = ClassUtils.convertResourcePathToClassName(supername);
        }
        this.interfaces = new String[interfaces.length];
        for (int i = 0; i < interfaces.length; i++) {
            this.interfaces[i] = ClassUtils.convertResourcePathToClassName(interfaces[i]);
        }
    }


    public String getClassName() {
        return this.className;
    }

    public boolean isInterface() {
        return this.isInterface;
    }

    public boolean isAbstract() {
        return this.isAbstract;
    }

    public boolean isConcrete() {
        return !(this.isInterface || this.isAbstract);
    }

    public boolean isFinal() {
        return this.isFinal;
    }


    public boolean hasSuperClass() {
        return (this.superClassName != null);
    }

    public String getSuperClassName() {
        return this.superClassName;
    }

    public String[] getInterfaceNames() {
        return this.interfaces;
    }

}
