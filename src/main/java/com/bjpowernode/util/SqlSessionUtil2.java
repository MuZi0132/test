package com.bjpowernode.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class SqlSessionUtil2 {

    private static SqlSessionFactory sqlSessionFactory;

    static {
        String config = "mybatis-config";
        InputStream stream = null;
        try {
            stream = Resources.getResourceAsStream(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(stream);
    }

    private static ThreadLocal<SqlSession> t = new ThreadLocal<>();

    public static SqlSession getSqlSession(){
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
