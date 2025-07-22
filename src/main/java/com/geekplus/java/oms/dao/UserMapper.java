package com.geekplus.java.oms.dao;

import com.geekplus.java.oms.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    public void addUser(User user);

    public User selectByName(String username);

    public void insertUser(User user);
}
