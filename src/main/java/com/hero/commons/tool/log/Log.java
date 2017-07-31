package com.hero.commons.tool.log;

/**
 * 封装日志接口
 * @author yin.xiong
 * 2017年7月17日
 */
public interface Log {

    boolean enableTrace();

    boolean enableDebug();

    boolean enableInfo();

    boolean enableWarn();

    boolean enableError();

    boolean enableFatal();

    boolean enableALL();

    public void debug(String message);

    public void debug(Object obj, String message);

    public void debug(Class objClass, String message);

    public void debug(Throwable e);

    public void debug(String message, Throwable e);

    public void info(String message);

    public void info(Object obj, String message);

    public void info(Class objClass, String message);

    public void info(Throwable e);

    public void info(String message, Throwable e);

    public void warn(String message);

    public void warn(Object obj, String message);

    public void warn(Class objClass, String message);

    public void warn(Throwable e);

    public void warn(String message, Throwable e);

    public void error(String message);

    public void error(Object obj, String message);

    public void error(Class objClass, String message);

    public void error(Object obj, Throwable e);

    public void error(Class objClass, Throwable e);

    public void error(Throwable e);

    public void error(String message, Throwable e);

    public void fatal(String message);

    public void fatal(Object obj, String message);

    public void fatal(Class objClass, String message);

    public void fatal(Throwable e);

    public void fatal(String message, Throwable e);

    public void trace(String message);

    public void trace(Object obj, String message);

    public void trace(Class objClass, String message);

    public void trace(Throwable e);

    public void trace(String message, Throwable e);

}