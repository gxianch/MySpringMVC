package mytomcat;

import java.io.IOException;
import java.io.InputStream;

public class MyRequest {
	private String url;
	private String method;
	public MyRequest(InputStream inputStream) throws IOException {
		String httpRequest = "";
		byte[] httpRequestBytes = new byte[1024];
		int length = 0;
//		POST /world HTTP/1.1
//		Host: localhost:8080
//		Connection: keep-alive
//		Content-Length: 0
//		Cache-Control: no-cache
//		Origin: chrome-extension://fhbjgbiflinjbdggehcddcbncdddomop
//		User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.87 Safari/537.36
//		Postman-Token: 73f7a346-9d03-69d3-a827-d17a51d6fb0b
//		Accept: */*
//		Accept-Encoding: gzip, deflate, br
//		Accept-Language: zh-CN,zh;q=0.9,zh-TW;q=0.8,en;q=0.7
		if((length = inputStream.read(httpRequestBytes))>0) {
			httpRequest = new String(httpRequestBytes, 0, length);
		}
		String httpHead = httpRequest.split("\n")[0];
		url = httpHead.split("\\s")[1];
		method = httpHead.split("\\s")[0];
		System.out.println(this);
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	@Override
	public String toString() {
		return "MyRequest [url=" + url + ", method=" + method + "]";
	}
	
}
