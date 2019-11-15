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
@ContextConfiguration(locations = {"classpath:spring_conf2.xml"})
public class Test2 {
    @Autowired
    private UserDao userDao;

    @Autowired
    private DataSource atomDataSource1;
    @Autowired
    private DataSource atomDataSource2;

    static final User user = new User("11", "zs", "M");

    static final User user2 = new User("12", "zs2", "F");


    @Autowired
    PlatformTransactionManager myTxManager;

    @org.junit.Test
    //  @Transactional
    public void addUserManualTransaction() {
        final TransactionTemplate template = new TransactionTemplate(this.myTxManager);
        final DataSource db2 = this.atomDataSource2;
        final JdbcTemplate jdbc = this.userDao.getJdbcTemplate();
        template.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                try {
                    DataSourceHolder.setDataSource("db1");
                    userDao.insert(user);
                    DataSourceHolder.setDataSource("db2", jdbc, db2);
                    userDao.insert(user);
                    throwException();
                } catch (Exception e) {
                    System.out.println("RollBack: " + e.getMessage());
                    transactionStatus.setRollbackOnly();
                    System.out.println("RollBack done! ");
                }
            }
        });
    }

    private void throwException() throws Exception {
        if (true){
            throw new Exception("eee");
        }
    }
}
