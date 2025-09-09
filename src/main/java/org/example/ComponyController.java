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
        return componies.stream().filter(compony -> compony.id().equals(id)).findFirst().orElse(null);
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
    public Compony update(@PathVariable int id, @RequestBody Compony compony) {
        return componies.stream().filter(c -> c.id().equals(id)).findFirst().map(c -> {
            Compony updatedCompony = new Compony(id, compony.name());
            componies.set(componies.indexOf(c), updatedCompony);
            return updatedCompony;
        }).orElse(null);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        componies.removeIf(compony -> compony.id().equals(id));
    }
}
