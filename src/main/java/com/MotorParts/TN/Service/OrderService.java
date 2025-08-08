package com.MotorParts.TN.Service;


import com.MotorParts.TN.Model.Orders;
import com.MotorParts.TN.Model.Part;
import com.MotorParts.TN.Repository.OrderRepository;
import com.MotorParts.TN.Repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private PartRepository PartRepo;
    public List<Orders> getData(){
        return orderRepo.findAll();
    }

    public Orders getSpecOrder(long id){
        return orderRepo.findById(id).orElseThrow(() -> new RuntimeException("No Order Found"));
    }

    public Orders CreateOrder(Long part_id,int quantity){
        Part part = PartRepo.findById(part_id).orElseThrow(()-> new RuntimeException("Part in the Id is not found"));
        Integer StockQuantity=part.getStockQuantity();

        Integer totalQuantity=StockQuantity-quantity;
        if(totalQuantity<0){
            throw new RuntimeException("Insufficient stock");
        }
        part.setStockQuantity(totalQuantity);
        PartRepo.save(part);
        Orders order =new Orders();
        order.setPartId(part);
        order.setQuantity(quantity);
        order.setOrder_Date(new Date());
        order.setStatus("IN PROGRESS");
        return orderRepo.save(order);
    }
    public List<Orders> processBulkOrders(List<Orders> orders) {
        List<Orders> SuccessfulOrder = new ArrayList<>();
        for (Orders order : orders) {
            try {
                Orders createdOrder = CreateOrder(order.getPartId().getPart_id(), order.getPartId().getStockQuantity());
                SuccessfulOrder.add(createdOrder);
            } catch (Exception e) {
                System.out.println("Failed to create order for part ID " + order.getPartId().getPart_id() + ": " + e.getMessage());

            }
        }
        return SuccessfulOrder;
    }

    public Orders  completeOrder(Long orderId){
        Orders order=orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("The Order Id is Invalid"));
        order.setStatus("COMPLETED");
        return orderRepo.save(order);
    }

}

