package com.MotorParts.TN.Service;

import com.MotorParts.TN.Repository.PartRepository;
import com.MotorParts.TN.Model.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class PartService {
    @Autowired
    private PartRepository partRepo;

    public Part updateStock(long part_id,int quantity){
        Part part= partRepo.findById(part_id).orElseThrow(()-> new RuntimeException("No Part found "));
        int PartQuantity= part.getStockQuantity();
        int total =PartQuantity-quantity;
        if(total<0){
            throw new RuntimeException("Insufficient Stock");
        }
        part.setStockQuantity(total);
        return partRepo.save(part);
    }

    public List<Part> getLowStock(){
        return partRepo.findByStockQuantity();
    }


    public Integer predictRestockDate(Long partId){
        Part part=partRepo.findById(partId).orElseThrow(()->new RuntimeException("No Parts found"));
        int BufferStock =10;
        int currentStock=part.getStockQuantity();
        if(currentStock <0){
            return 0;
        }
        Double AverageDailyConsumption =5.0;
        int total=0;
        if(currentStock > BufferStock){
            total=(int) Math.round((currentStock-BufferStock)/AverageDailyConsumption);
        }
        return  total;
    }

    public String addParts(Part part){
        partRepo.save(part);
        return "The Part is added Successfully";
    }
}

