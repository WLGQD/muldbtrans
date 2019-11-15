package com.muldbtrans;

import cn.hutool.core.collection.CollectionUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@Slf4j
public class UserDao {

    @Getter
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(final List<User> datas){
        String sql = "insert into t_user(id, name, gender) values (?, ?, ?)";
       int[] result = this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                User d = datas.get(i);
                ps.setString(1, d.getId());
                ps.setString(2, d.getName());
                ps.setString(3, d.getGender());
            }

            @Override
            public int getBatchSize() {
                System.out.println("数量：" + datas.size());
                return datas.size();
            }
        });

        System.out.println(result.toString());
    }
    public void insert(final User user){
        this.insert(CollectionUtil.newArrayList(user));
    }

}
