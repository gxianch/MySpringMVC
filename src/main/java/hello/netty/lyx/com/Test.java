package hello.netty.lyx.com;
import io.netty.util.internal.SystemPropertyUtil;

public class Test {
	public static void main(String[] args) {
	//	SystemPropertyUtil("io.netty.leakDetectionLevel");

			System.setProperty("io.netty.allocator.type", "pooled");
		//		System.setProperty("io.netty.leakDetection.acquireAndReleaseOnly", "true");
		//		System.setProperty("io.netty.noJavassist", "true");
		System.out.println(	SystemPropertyUtil.get("io.netty.allocator.type"));
	}
}
