package com.example.ApiTestingTool.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ApiTestingTool.Models.ApiBody;
import com.example.ApiTestingTool.Repository.BodyDataRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UpdateHistory {


    @Autowired
    private BodyDataRepository bodyDataRepository;

    public void UpdateData(int id, ApiBody data)
    {
           ApiBody idData = bodyDataRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Not Found"));
          
           if( !data.getMethod().equals(idData.getMethod()) || !data.getHeaders().equals(idData.getHeaders())
           || !data.getUrl().equals(idData.getUrl()) || !data.getBody().equals(idData.getBody()) 
           || !data.getTestScript().equals(idData.getTestScript()) || !data.getValidation().equals(idData.getValidation())){
            System.out.println("creating new row");
             bodyDataRepository.save(data);
           }
           else{
            data.setId(id);
            bodyDataRepository.save(data);
           }
           
    }
    
}
