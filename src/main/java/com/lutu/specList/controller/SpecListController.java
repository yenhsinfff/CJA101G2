package com.lutu.specList.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lutu.specList.model.SpecListDTO;
import com.lutu.specList.model.SpecListService;

@RestController
@RequestMapping("/api/specs")
@CrossOrigin(origins = "*") //允許所有網域跨域存取
public class SpecListController {

    @Autowired
    private SpecListService service;

    @GetMapping("")
    public List<SpecListDTO> getAllSpecs() {
        return service.getAllSpecs();
    }

    @GetMapping("/{id}")
    public SpecListDTO getSpecById(@PathVariable Integer id) {
        return service.getSpecById(id);
    }

    @PostMapping("")
    public SpecListDTO addSpec(@RequestBody SpecListDTO dto) {
        return service.saveOrUpdate(dto);
    }

    @PutMapping("")
    public SpecListDTO updateSpec(@RequestBody SpecListDTO dto) {
        return service.saveOrUpdate(dto);
    }

//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable Integer id) {
//        service.delete(id);
//    }
}
