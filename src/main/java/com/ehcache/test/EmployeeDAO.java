package com.ehcache.test;


import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component("employeeDAO")
public class EmployeeDAO {
    
    // key 当前类，缓存对象为返回值list
    @Cacheable(value = {"lt.ecache"}, key = "#root.targetClass")
    public List<Employee> getEmployees() {
        System.out.println("*** getEmployees() 已经调用   ***");
        List<Employee> list = new ArrayList<Employee>(5);
        list.add(new Employee(1, "Ben", "Architect"));
        list.add(new Employee(2, "Harley", "Programmer"));
        list.add(new Employee(3, "Peter", "BusinessAnalyst"));
        list.add(new Employee(4, "Sasi", "Manager"));
        list.add(new Employee(5, "Abhi", "Designer"));
        return list;
    }

    // key id，缓存数据为返回值Employee对象
    @Cacheable(value = "lt.ecache", key = "#id")
    public Employee getEmployee(int id, List<Employee> employees) {
        System.out.println("***  getEmployee(): " + id + " ***");
        Employee emp = null;
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                emp = employee;
            }
        }
        return emp;
    }

    // @CachePut会去替换缓存中的Employee对象为当前id对应的对象
    @CachePut(value = "lt.ecache", key = "#id")
    public Employee updateEmployee(int id, String designation, List<Employee> employees) {
        System.out.println("*** updateEmployee()  " + id + " ***");
        Employee emp = null;
        int i = 0;
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                employee.setDesignation(designation);
                emp = employee;
            }
        }
        System.out.println(emp);
        return emp;
    }

    //key为参数中Employee对象的id，缓存指定id对应的Employee对象
    @Cacheable(value = "lt.ecache", key = "#employee.id")
    public Employee addEmployee(Employee employee, List<Employee> employees) {
        System.out.println("*** addEmployee() : " + employee.getId() + " ***");
        employees.add(employee);
        System.out.println(employee);
        return employee;
    }

    //key为参数中的id，移除缓存，移除指定id对应的Employee对象
    @CacheEvict(value = "lt.ecache", key = "#id")
    public Employee removeEmployee(int id, List<Employee> employees) {
        System.out.println("*** removeEmployee()  : " + id + " ***");
        Employee emp = null;
        int i = 0;
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                emp = employee;
            } else {
                i++;
            }
        }
        employees.remove(i);
        return emp;
    }

    //key为当前类，移除缓存，移除employees列表对象
    @CacheEvict(value = "lt.ecache", key = "#root.targetClass")
    public List<Employee> removeAllEmployee(List<Employee> employees) {
        System.out.println("*** removeAllEmployee()  :  ***");
        employees.clear();
        System.out.println(employees.size());
        return employees;
    }

}