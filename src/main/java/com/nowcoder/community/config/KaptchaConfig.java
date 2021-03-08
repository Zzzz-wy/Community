package com.nowcoder.community.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KaptchaConfig {

    @Bean
    //Producer有一个默认的实现类DefaultKaptcha
    public Producer kaptchaProducer() {
        //这个key不好写，就不在配置文件里配了，直接实例化
        Properties properties = new Properties();
        properties.setProperty("kaptcha.image.width", "100");
        properties.setProperty("kaptcha.image.height", "40");
        properties.setProperty("kaptcha.textproducer.font.size", "32");
        properties.setProperty("kaptcha.textproducer.font.color", "0,0,0");
        //生成的字符范围
        properties.setProperty("kaptcha.textproducer.char.string", "0123456789ASBCDEFGHIJKLMNOPQRSTUVWXYZ");
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        //所选择的噪声类，默认就有一点变形，不加也可以
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        //要求传入Properties对象，所有配置项都是通过config去配的
        Config config = new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha;
    }

}
