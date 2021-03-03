package com.nowcoder.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//这是一个配置类
public class CommunityApplication {
	//
	public static void main(String[] args) {
		//启动后，自动地创建了spring容器，自动扫描某些包
		// （配置类下的包以及子包，有controller（处理请求的组件）、component（在任何组件都能用）的注解，
		// 有service（业务组件）的注解，有Repository（数据库请求的组件）的注解）下的bean
		SpringApplication.run(CommunityApplication.class, args);
	}

}
