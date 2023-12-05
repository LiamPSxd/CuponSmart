package mybatis;

import java.io.IOException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil{
    public static final String RESOURCE = "mybatis/mybatis-config.xml";
    public static final String ENVIRONMENT = "development";
    
    public static SqlSession getSession(){
        SqlSession session = null;
        
        try{
            session = new SqlSessionFactoryBuilder().build(
                Resources.getResourceAsReader(RESOURCE),
                ENVIRONMENT
            ).openSession();
        }catch(IOException e){
            e.printStackTrace();
        }
        
        return session;
    }
}