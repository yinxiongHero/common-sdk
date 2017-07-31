package com.hero.commons;

/**
 * 版本信息
 *
 * @author yin.xiong
 */
public class Version {

    private Version() {
    }

    public static String getImplementationVersion() {
        return Version.class.getPackage().getImplementationVersion();
    }

    public static String getSpecificationVersion() {
        return Version.class.getPackage().getSpecificationVersion();
    }

    public static String getSpecificationTitle() {
        return Version.class.getPackage().getSpecificationTitle();
    }

    public static String getSpecificationVendor() {
        return Version.class.getPackage().getSpecificationVendor();
    }

    public static String getImplementationTitle() {
        return Version.class.getPackage().getImplementationTitle();
    }

    public static String getImplementationVendor() {
        return Version.class.getPackage().getImplementationVendor();
    }

}
