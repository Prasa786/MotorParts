package com.MotorParts.TN.Controller;

import com.MotorParts.TN.Model.Orders;
import com.MotorParts.TN.Model.Part;
import com.MotorParts.TN.Service.OrderService;
import com.MotorParts.TN.Service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PartController {

    @Autowired
    private PartService partService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/parts/low-stock")
    public List<Part> getLowStockParts() {
        return partService.getLowStock();
    }

    @GetMapping("/parts/{id}/predict-restock")
    public int predictRestock(@PathVariable Long id) {
        return partService.predictRestockDate(id);
    }

//    @PostMapping("/orders")
//    public Orders createOrder(@RequestParam Long partId, @RequestParam int quantity) {
//        return orderService.CreateOrder(partId, quantity);
//    }

    @PostMapping
    public Part addParts(@RequestBody Part part){
        return partService.addParts(part);
    }
}

