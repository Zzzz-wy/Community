package com.nowcoder.community.controller;

import com.nowcoder.community.service.AlphaService;
import com.nowcoder.community.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sound.midi.Soundbank;
import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/alpha")//类的访问名，通过这个名字访问类
public class AlphaController {

    @Autowired
    private AlphaService alphaService;

    @RequestMapping("/hello")
    @ResponseBody
    //@responseBody注解的作用是将controller的方法返回的对象通过适当的转换器转换为指定的格式之后，
    //写入到response对象的body区，通常用来返回JSON数据或者是XML
    public String sayHello() {
        return "Hello boot";
    }

    @RequestMapping("/data")
    @ResponseBody
    public String getData() {
        return alphaService.find();
    }

    @RequestMapping("/http")
    //HttpServletRequest和HttpServletResponse都是请求和响应常用的接口
    public void http(HttpServletRequest request, HttpServletResponse response) {
        //获取请求数据
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        Enumeration<String> enumeration = request.getHeaderNames();//请求行是key-value结构
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + "：" + value);
        }
        System.out.println(request.getParameter("code"));

        //返回响应数据
        response.setContentType("text/html;charset=utf-8");
        try {
            PrintWriter writer = response.getWriter();//获取到一个输出流，是一个PrintWriter类型
            writer.write("<h1> zzzwy </h1>");//通过这个向浏览器打印一个网页
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //GET请求

    // /students?current=1&limit=20(传递参数）
    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            //没有请求参数的话，可以像下面一样加个注解
            @RequestParam(name = "current", required = false, defaultValue = "1") int current,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        System.out.println(current);
        System.out.println(limit);
        return"some students";
    }

    // /students/123（参数成为路径的一部分）
    @RequestMapping(path = "/students/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(@PathVariable("id") int id){
        System.out.println(id);
        return "students";
    }

    //POST请求
    @RequestMapping(path = "/student", method = RequestMethod.POST)
    @ResponseBody
    //参数的名字与表单中的名字对应就可以自动地传过来
    public String saveStudent(String name, int age){
        System.out.println(name);
        System.out.println(age);
        return "success";
    }

    //响应HTMl数据
    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("name", "张三");
        mav.addObject("age", "30");
        mav.setViewName("/demo/view");//固定是.html,所以没写后缀，写上模版的路径及名字，在template目录中，下级目录要写上
        return mav;
    }

    @RequestMapping(path = "/school", method = RequestMethod.GET)
    //DispatchServlet自动实例化model对象，不是我们自己创建的，是个bean，DispatchServlet持有这个对象的引用
    //在方法内往这个对象存数据，它也能得到
    public String getSchool(Model model){
        model.addAttribute("name", "北京大学");
        model.addAttribute("age", "80");
        return "demo/view";
    }

    //响应JSON数据（异步请求）
    //Java对象返回给浏览器，浏览器用JS解析，用JSON字符串当中转就能将两种语言串起来
    //Java对象 -> JSON字符串 -> JS对象
    //JSON字符串是很常见的中转方式，任何语言都有字符串类型
    @RequestMapping(path = "/emp", method = RequestMethod.GET)
    @ResponseBody//加了这个注解才返回JSON字符串，否则认为返回HTML
    public Map<String, Object> getEmp(){
        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "张三");
        emp.put("age", 11);
        return emp;
    }

    @RequestMapping(path = "/emps", method = RequestMethod.GET)
    @ResponseBody//加了这个注解才返回JSON字符串
    public List<Map<String, Object>> getEmps(){
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "张三");
        emp.put("age", 11);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "张1");
        emp.put("age", 12);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "张3");
        emp.put("age", 13);
        list.add(emp);

        return list;
    }

    //Cookie示例
    @RequestMapping(path = "/cookie/set", method = RequestMethod.GET)
    @ResponseBody
    public String setCookie(HttpServletResponse response) {
        //创建cookie,cookie只能存字符串，并且只能是小量数据
        //因为cookie来回传，大了会影响性能，其他Java类型客户端可能不能识别，字符串可以识别
        Cookie cookie = new Cookie("code", CommunityUtil.generateUUID());
        //设置cookie生效的范围
        cookie.setPath("/community/alpha");
        //设置cookie的生存时间
        cookie.setMaxAge(60 * 10);
        //发送cookie
        response.addCookie(cookie);

        return "set cookie";
    }

    @RequestMapping(path = "/cookie/get", method = RequestMethod.GET)
    @ResponseBody
    //HttpServletRequest request.getCookies();可以得到cookie数组
    //@CookieValue("xxx")注解就可以得到从cookie取到key为code的值赋给参数
    public String getCookie(@CookieValue("code") String code) {

        System.out.println(code);
        return "get cookie";
    }

    //Session示例
    @RequestMapping(path = "/session/set", method = RequestMethod.GET)
    @ResponseBody
    //SpringMVC可以自动地帮我们创建session，并自动注入进来
    public String setSession(HttpSession session) {
        //session一直在服务端保存着，因此它存什么都行，cookie只能存字符串
        session.setAttribute("id",1);
        session.setAttribute("name", "Test");
        return "set session";
    }


    @RequestMapping(path = "/session/get", method = RequestMethod.GET)
    @ResponseBody
    public String getSession(HttpSession session) {
        System.out.println(session.getAttribute("id"));
        System.out.println(session.getAttribute("name"));
        return "get session";
    }



}
