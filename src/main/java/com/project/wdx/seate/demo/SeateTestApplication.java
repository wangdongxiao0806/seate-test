package com.project.wdx.seate.demo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.project.wdx.facade.OrderService;
import com.project.wdx.facade.StorageService;
import com.project.wdx.model.Order;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@SpringBootApplication
@EnableDubbo
@RestController
public class SeateTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeateTestApplication.class, args);
	}

	@Reference
	private OrderService orderService;

	@Reference
	private StorageService storageService;

	@RequestMapping("/seata")
	@GlobalTransactional(timeoutMills = 300000, name = "seata-test")
	public void seata(){

		Order order = new Order();
		order.setUserId("wdx");
		order.setCommodityCode("commodityCode");
		order.setCount(2);
		order.setMoney(new BigDecimal("100"));
		orderService.create(order);
		storageService.deduct("commodityCode",1);
	}
}
