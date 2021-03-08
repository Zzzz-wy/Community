package com.nowcoder.community.dao;

import com.nowcoder.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LoginTicketMapper {

    //用注解实现sql，可以写多个字符串，它就是把字符串拼成sql
    //@Insert(({"", "", ""}))
    @Insert({
            "insert into login_ticket(user_id,ticket,status,expired)",
            "values(#{userId}, #{ticket}, #{status}, #{expired})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")//自动生成主键，并注入id
    int insertLoginTicket(LoginTicket loginTicket);

    //查询ticket
    @Select({
            "select id,user_id, ticket, status, expired",
            "from login_ticket where ticket=#{ticket}"
    })
    LoginTicket selectByTicket(String ticket);

    //使ticket失效，动态sql演示
    @Update({
            "<script>",
            "update login_ticket set status=#{status} where ticket=#{ticket}",
            "<if test=\"ticket!=null\">",
            "and 1=1",
            "</if>",
            "</script>"
    })
    int updateStatus(String ticket, int status);
}
