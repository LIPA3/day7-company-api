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
    public void clear() {
        componies.clear();
        id = 0;
    }
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
    @GetMapping
    public List<Compony> listCompony(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size){
        List<Compony> list = componies;
        if(page != null && size != null){
            int fromIndex = (page - 1) * size;
            int toIndex = Math.min(fromIndex + size, list.size());
            if(fromIndex <= list.size()){
                list = list.subList(fromIndex, toIndex);
            }else{
                list = new ArrayList<>();
            }
        }
        return list;
    }
    @PutMapping("{id}")
    public Compony update(@PathVariable int id, @RequestBody Compony employee) {
        for (int i = 0; i < componies.size(); i++) {
            if (componies.get(i).id().equals(id)) {
                Compony updatedEmployee = new Compony(id, employee.name());
                componies.set(i, updatedEmployee);
                return updatedEmployee;
            }
        }
        return employee;
    }
}
