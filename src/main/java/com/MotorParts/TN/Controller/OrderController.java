package com.MotorParts.TN.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.MotorParts.TN.Model.Orders;
import com.MotorParts.TN.Service.OrderService;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/orders")
@RestController
public class OrderController {
    @Autowired
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Orders>> getData(){
        List<Orders> orders= service.getData();
        if(orders.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(orders);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Orders> getSpecData(@PathVariable Long id)
    {
        Orders o=service.getSpecOrder(id);
        if(o==null){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
       return ResponseEntity.ok().body(o);
    }
    @PostMapping
    public ResponseEntity<?> CreateOrders(@RequestParam Long part_id, @RequestParam int quantity){
        if(part_id==null|| quantity<=0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parameter cannot be null");
        }
        try {
            Orders order = service.CreateOrder(part_id, quantity);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PostMapping("/bulk")
    public ResponseEntity<?> processBulkOrders(@RequestBody  List<Orders> orders){
       if(orders ==null||orders.isEmpty()){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
       }
       List<Orders> orders1=new ArrayList<>();
       service.processBulkOrders(orders);
       return ResponseEntity.status(HttpStatus.CREATED).body(orders1);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Orders> UpdateOrder(@PathVariable Long id){
        if(id==null || id<0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().body(service.completeOrder(id));
    }


}
