package com.ehcache.simple;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
 import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
 
 public class SimpleTest {
     public static void main(String[] args) {
         // InputStream in = SimpleTest.class.getClassLoader().getResourceAsStream("ehcache.xml");
         // URL url = SimpleTest.class.getClassLoader().getResource("ehcache.xml");
         // URL url2 = SimpleTest.class.getResource("ehcache.xml");  
         String path = System.getProperty("ehcache.xml");  
         CacheManager manager = CacheManager.create(".src/resources/ehcache.xml"); 
         //创建Cache对象
         Cache cache = manager.getCache("lt.ecache");
         //cache缓存名称
         System.out.println("cache name: " + cache.getName());
         //将对象放入缓存    
         Element element = new Element("hello", "world");
         Element element2 = new Element("aaa", "111");
         Element element3 = new Element("bbb", "222");
         Element element4 = new Element("bbb", "222");
         cache.put(element);
         cache.put(element2);
         cache.put(element3);
         cache.put(element4);//key相同时会被覆盖
         
         //cache缓存对象个数
         System.out.println("size: " + cache.getSize());
         
         // 从cache中取回元素
         System.out.println("hello: " + cache.get("hello").getValue());
         
         List<String> keys = cache.getKeys();//所有缓存对象的key
         
         // 遍历所有缓存对象
         for(String key : keys ){
             System.out.println(key + " : " + cache.get(key));
         }
         
         // 从Cache中移除一个元素
         System.out.println(cache.remove("hello")); 
         System.out.println(cache.remove("hello2")); 
         
         //移除所有缓存对象
         cache.removeAll();
         
         System.out.println("size: " + cache.getSize());
         
         manager.shutdown();
     }
 
 }