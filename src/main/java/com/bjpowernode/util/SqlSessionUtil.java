package com.bjpowernode.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class SqlSessionUtil {
    //构造方法私有化，防止其他类调用，需使用静态方法的调用方法。防止无用代码
    private SqlSessionUtil(){}

    private static SqlSessionFactory sqlSessionFactory;

    static {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    private static ThreadLocal<SqlSession> t = new ThreadLocal<>();

    public static SqlSession getSession(){
        SqlSession session = t.get();
        if (session == null) {
            session = sqlSessionFactory.openSession();

            t.set(session);
        }
        return session;
    }

    public static void MyClose(SqlSession session){
        if (session != null) {
            session.close();
            t.remove();
        }
    }

}
