package com.ehcache.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {

    public static void main(String[] args) {
        
        ApplicationContext context = new ClassPathXmlApplicationContext("D:\\workspace\\MySpringMVC\\src\\main\\resources\\Spring-config-ehcache.xml");

        EmployeeDAO dao = (EmployeeDAO) context.getBean("employeeDAO"); 

        System.out.println("-----------------------第1次调用----------------------------");
        List<Employee> employees = dao.getEmployees(); 
        System.out.println(employees.toString()); 
        System.out.println("------------------------第2次调用---------------------------");
        employees = dao.getEmployees();
        System.out.println(employees.toString()); 
        System.out.println("------------------------第3次调用---------------------------");
        employees = dao.getEmployees();
        System.out.println(employees.toString()); 

        
        System.out.println("-------------------------第1次调用--------------------------");
        Employee employee = dao.getEmployee(1, employees);
        System.out.println(employee.toString());
        System.out.println("-------------------------第2次调用--------------------------");
        employee = dao.getEmployee(1, employees);
        System.out.println(employee.toString());
        
        
        System.out.println("------------------------- 对象更新--------------------------"); 
        dao.updateEmployee(1, "已经更新的对象", employees); 
        System.out.println("-------------------------第1次调用--------------------------");
        employee = dao.getEmployee(1, employees);
        System.out.println(employee.toString());
        System.out.println("-------------------------第2次调用--------------------------");
        employee = dao.getEmployee(1, employees);
        System.out.println(employee.toString());
        
        
        System.out.println("------------------------- 添加对象--------------------------");
        dao.addEmployee(new Employee(6, "555", "Designer5555"),employees);
        System.out.println("-------------------------第1次调用--------------------------");
        employee = dao.getEmployee(6, employees);
        System.out.println(employee);
        System.out.println("-------------------------第2次调用--------------------------");
        employee = dao.getEmployee(6, employees);
        System.out.println(employee.toString());
        
        
        System.out.println("------------------------- 清除一个对象--------------------------");
        System.out.println(employees.size());
        employee = dao.removeEmployee(6, employees);
        System.out.println("-------------------------第1次调用--------------------------");
        employees = dao.getEmployees();
        System.out.println(employees);
        System.out.println(employees.size());
        System.out.println("-------------------------第2次调用--------------------------");
        employees = dao.getEmployees();
        System.out.println(employees); 
        
        
        System.out.println("------------------------- 清除所有--------------------------");
        System.out.println(employees.size());
        employees = dao.removeAllEmployee(employees);
        System.out.println("-------------------------第1次调用--------------------------");
        employees = dao.getEmployees();
        System.out.println(employees); 
        System.out.println("-------------------------第2次调用--------------------------");
        employees = dao.getEmployees();
        System.out.println(employees); 
    }
}