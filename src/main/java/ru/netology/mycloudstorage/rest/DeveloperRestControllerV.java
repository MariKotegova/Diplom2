package ru.netology.mycloudstorage.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.netology.mycloudstorage.modele.Developer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@RestController
//@RequestMapping("/")
public class DeveloperRestControllerV {
//    private List<Developer> DEVEL = Stream.of(
//            new Developer(1, "test", "testp"),
//            new Developer(2, "test1", "testp1")
//    ).collect(Collectors.toList());
////вернет весь список
//    @GetMapping
//    public List<Developer> getAll(){
//       return DEVEL;
//    }
////вернет по id
//    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('developers:read')") // дает разрешение на чтение
//    public Developer getById(@PathVariable Long id){
//        return DEVEL.stream().filter(developer -> developer.getId().equals(id))
//                .findFirst()
//                .orElse(null);
//    }
//
//    // добавить человека
//    @PostMapping
//    @PreAuthorize("hasAuthority('developers:write')") // дает разрешение на запись
//    public Developer create(@RequestBody Developer developer){
//        this.DEVEL.add(developer);
//        return developer;
//    }
//
//    //удаляет
//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('developers:write')") // дает разрешение на удаление
//    public void deleteById(@PathVariable Long id){
//        this. DEVEL.removeIf(developer -> developer.getId().equals(id));
//
//    }
}

