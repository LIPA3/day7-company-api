package org.example;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("componies")
public class ComponyController {
    private List<Compony> componies = new ArrayList<>();
    private int id = 0;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Compony create(@RequestBody Compony compony) {
        int id = ++this.id;
        Compony newCompony = new Compony(id, compony.name());
        componies.add(newCompony);
        return newCompony;
    }
    @GetMapping("{id}")
    public  Compony get(@PathVariable int id){
        for(Compony compony : componies){
            if(compony.id().equals(id)){
                return compony;
            }
        }
        return null;
    }

}
