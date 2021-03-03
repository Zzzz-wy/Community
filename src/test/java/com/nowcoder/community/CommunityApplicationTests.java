package com.nowcoder.community;

import com.nowcoder.community.dao.AlphaDao;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.AlphaService;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
class CommunityApplicationTests implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	//ApplicationContext applicationContext这个参数就是spring容器，是个接口
	//ApplicationContext是FactoryBean的子接口，扩展了更多方法，功能更强，一般用这个
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Test
	public void testApplicationContext(){
		System.out.println(applicationContext);
		AlphaDao alaphDao = applicationContext.getBean(AlphaDao.class);//可以根据名字，类名，接口名获取Bean
		//多个接口实现类的话也可以在实现类上加上@Primary注解，就会有更高的优先级
		System.out.println(alaphDao.select());
		alaphDao = applicationContext.getBean("alphaHibernate", AlphaDao.class);
		System.out.println(alaphDao.select());
	}
	@Test
	public void testBeanManagement(){
		AlphaService alphaService = applicationContext.getBean(AlphaService.class);
		System.out.println(alphaService);
		alphaService = applicationContext.getBean(AlphaService.class);
		System.out.println(alphaService);
	}
	@Test
	public void testBeanConfig(){
		SimpleDateFormat simpleDateFormat = applicationContext.getBean(SimpleDateFormat.class);
		System.out.println(simpleDateFormat.format(new Date()));
	}
	@Autowired
	@Qualifier("alphaHibernate")//写上想要注入的bean的名字
	private AlphaDao alphaDao;



	@Test
	public void testDI(){
		System.out.println(alphaDao);
		System.out.println();
	}

}
