package com.zfz.myspringmvc.servlet;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.stream.StreamResult;

import com.zfz.myspringmvc.annotation.Controller;
import com.zfz.myspringmvc.annotation.Qualifier;
import com.zfz.myspringmvc.annotation.Repository;
import com.zfz.myspringmvc.annotation.RequestMapping;
import com.zfz.myspringmvc.annotation.Service;
import com.zfz.myspringmvc.controller.UserController;
@WebServlet(name="dispatcherServlet",urlPatterns="/*",loadOnStartup=1,
initParams={@WebInitParam(name="base-package",value="com.zfz.myspringmvc")})
public class DispatcherServlet extends HttpServlet{
	private String basePackage = "";
	private List<String> packageNames = new ArrayList<String>();
	private Map<String, Object> instanceMap = new HashMap<String, Object>();
	private Map<String, String> nameMap = new HashMap<String, String>();
	private Map<String, Method> urlMethodMap = new HashMap<String, Method>();
	private Map<Method, String> methodPackageMap = new HashMap<Method, String>();

	@Override
	public void init(ServletConfig config){
		try {
			basePackage = config.getInitParameter("base-package");
			scanBasePackage(basePackage);
			intance(packageNames);
			springIOC();
			handlerUrlMethodMap();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		doPost(req, resp);
	}
	@Override 
	protected void doPost(HttpServletRequest req,HttpServletResponse resp){
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String path = uri.replaceAll(contextPath, "");
		Method method = urlMethodMap.get(path);
		if(method != null){
			String packageName = methodPackageMap.get(method);
			String controllerName= nameMap.get(packageName);
			UserController userController = (UserController)instanceMap.get(controllerName);
			method.setAccessible(true);
			try {
				method.invoke(userController);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void handlerUrlMethodMap() throws Exception{
		if(packageNames.size()<1){return ;}
		for(String string : packageNames){
			Class c = Class.forName(string);
			if(c.isAnnotationPresent(Controller.class)){
				Method[] methods = c.getMethods();
				StringBuffer baseUrl = new StringBuffer();
				if(c.isAnnotationPresent(RequestMapping.class)){
					RequestMapping requestMapping =(RequestMapping)c.getAnnotation(RequestMapping.class);
					baseUrl.append(requestMapping.value());
				}
				for(Method method: methods){
					if(method.isAnnotationPresent(RequestMapping.class)){
						RequestMapping requestMapping =(RequestMapping)c.getAnnotation(RequestMapping.class);
						baseUrl.append(requestMapping.value());
						urlMethodMap.put(baseUrl.toString(), method);
						methodPackageMap.put(method, string);
					}
				}
			}
		}
	}

	private void springIOC() throws Exception{
		for(Map.Entry<String, Object> entry : instanceMap.entrySet()){
			Field[] fields = entry.getValue().getClass().getFields();
			for(Field field :fields){
				if(field.isAnnotationPresent(Qualifier.class))	{
					String name = field.getAnnotation(Qualifier.class).value();
					field.setAccessible(true);
					field.set(entry.getValue(), instanceMap.get(name));
				}
			}
		}
	}

	private void intance(List<String> packageNames) throws Exception {
		if(packageNames.size()<1){return ;}
		for(String string : packageNames){
			Class c = Class.forName(string);
			if(c.isAnnotationPresent(Controller.class)){
				Controller controller = (Controller)c.getAnnotation(Controller.class);
				String controllerName= controller.value();
				instanceMap.put(controllerName, c.newInstance());
				nameMap.put(string, controllerName);
				System.out.println("Controller: "+string +",value: "+controller.value());
			}else 	if(c.isAnnotationPresent(Service.class)){
				Service service = (Service)c.getAnnotation(Service.class);
				String serviceName= service.value();
				instanceMap.put(serviceName, c.newInstance());
				nameMap.put(string, serviceName);
				System.out.println("Service: "+string +",value: "+service.value());
			}else 	if(c.isAnnotationPresent(Repository.class)){
				Repository repository = (Repository)c.getAnnotation(Repository.class);
				String repositoryName= repository.value();
				instanceMap.put(repositoryName, c.newInstance());
				nameMap.put(string, repositoryName);
				System.out.println("Repository: "+string +",value: "+repository.value());
			}
		}
	}

	private void scanBasePackage(String basePackage) {
		URL url = this.getClass().getClassLoader().getResource(basePackage.replaceAll("\\.", "/"));
		File basePackageFile = new File(url.getPath());
		System.out.println("scan:" +basePackageFile);
		File[]	childFiles = basePackageFile.listFiles();
		for(File file : childFiles){
			if(file.isDirectory()){
				scanBasePackage(basePackage+"."+file.getName());
			}else if(file.isFile()){
				packageNames.add(basePackage+"."+file.getName().split("\\.")[0]);
			}
		}
	}
}
