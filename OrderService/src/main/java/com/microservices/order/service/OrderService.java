package com.microservices.order.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.order.dto.OrderDto;
import com.microservices.order.model.Order;
import com.microservices.order.repository.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
    private OrderRepository orderRepository;

    public List<OrderDto> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(order -> new OrderDto(order.getOrderId(), order.getUserId(), order.getProduct()))
                .collect(Collectors.toList());
    }

    public OrderDto saveOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setUserId(orderDto.getUserId());
        order.setProduct(orderDto.getProduct());
        return new OrderDto(orderRepository.save(order).getOrderId(), order.getUserId(), order.getProduct());
    }

    public OrderDto updateOrder(Long orderId, OrderDto orderDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setUserId(orderDto.getUserId());
        order.setProduct(orderDto.getProduct());
        return new OrderDto(orderRepository.save(order).getOrderId(), order.getUserId(), order.getProduct());
    }

    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }


}
