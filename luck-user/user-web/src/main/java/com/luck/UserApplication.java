package com.luck;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication(scanBasePackages = "com.luck.*")
@MapperScan("com.luck.mapper")
@EnableFeignClients
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

//	@Bean
//	public PaginationInterceptor paginationInterceptor() {
//		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//		// 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
//		// paginationInterceptor.setOverflow(false);
//		// 设置最大单页限制数量，默认 500 条，-1 不受限制
////		paginationInterceptor.setLimit(-1);
//		// 开启 count 的 join 优化,只针对部分 left join
//		paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
//		return paginationInterceptor;
//	}


	/**
	 * Spring Boot 使用@Async 实现异步调用-自定义线程池
	 * 开启异步注解 @EnableAsync 方法上加 @Async
	 * 默认实现 SimpleAsyncTaskExecutor 不是真的线程池，这个类不重用线程，每次调用
	 * 都会创建一个新的线程
	 * @return
	 */
	@Bean("myTaskExecutor")
	public Executor myTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);//核心线程数量，线程池创建时候初始化的线程数
		executor.setMaxPoolSize(15);//最大线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
		executor.setQueueCapacity(200);//缓冲队列，用来缓冲执行任务的队列
		executor.setKeepAliveSeconds(60);//当超过了核心线程数之外的线程在空闲时间到达之后会被销毁
		executor.setThreadNamePrefix("myTask-");//设置好了之后可以方便我们定位处理任务所在的线程池
		executor.setWaitForTasksToCompleteOnShutdown(true);//用来设置线程池关闭的时候等待所有任务都完成再
//		继续销毁其他的Bean
		executor.setAwaitTerminationSeconds(60);//该方法用来设置线程池中任务的等待时间，如果超过这个时候还没
//		有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住。
		//线程池对拒绝任务的处理策略：这里采用了CallerRunsPolicy策略，当线程池没有处理能力的时候，该策略会直接在
//		execute 方法的调用线程中运行被拒绝的任务；如果执行程序已关闭，则会丢弃该任务
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		return executor;
	}



}
