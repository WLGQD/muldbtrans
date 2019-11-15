package com.muldbtrans;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

//import java.beans.beancontext.BeanContext;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring_conf.xml"})
public class Test {
    @Autowired
    private UserDao userDao;

    @Autowired
    private DataSource db2;
    @Autowired
    private DataSource db1;


    @org.junit.Test
    @Transactional
    public void test1() {
        User user = new User();
        user.setId("11");
        user.setName("zs");
        user.setGender("M");
        //     DataSourceHolder.setDataSource("db1");
        userDao.insert(user);
        DataSourceHolder.setDataSource("db2");
        user = new User();
        user.setId("12");
        user.setName("111");
        user.setGender("F");
        userDao.insert(user);
    }

    static final User user = new User("11", "zs", "M");

    static final User user2 = new User("12", "zs2", "F");


}
