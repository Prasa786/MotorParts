package com.MotorParts.TN.Service;


import com.MotorParts.TN.Model.Orders;
import com.MotorParts.TN.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;
    public List<Orders> getData(){
        return orderRepo.findAll();
    }

    public Orders getSpecOrder(long id){
        return orderRepo.findById(id).orElseThrow(() -> new RuntimeException("No Order Found"));
    }

}
