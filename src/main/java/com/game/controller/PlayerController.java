package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayersService;
import com.game.specifications.PlayerSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/players")
public class PlayerController {

    private PlayersService playersService;

    @Autowired
    public PlayerController(PlayersService playersService) {
        this.playersService = playersService;
    }

    @GetMapping
    public List<Player> getPlayers(

            @RequestParam(required = false) String name,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "3") Integer pageSize,
            @RequestParam(required = false) Race race,
            @RequestParam(required = false) Profession profession,
            @RequestParam(required = false) Long after,
            @RequestParam(required = false) Long before,
            @RequestParam(required = false) Boolean banned,
            @RequestParam(required = false) Integer minExperience,
            @RequestParam(required = false) Integer maxExperience,
            @RequestParam(required = false) Integer minLevel,
            @RequestParam(required = false) Integer maxLevel,
            @RequestParam(required = false, defaultValue = "ID") PlayerOrder order

    ){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));


        return playersService.findAllPlayers(
                Specification.where(PlayerSpecification.filterByName(name))
                        .and(PlayerSpecification.filterByTitle(title))
                        .and(PlayerSpecification.filterByRace(race))
                        .and(PlayerSpecification.filterByProfession(profession))
                        .and(PlayerSpecification.filterByBirthday(after, before))
                        .and(PlayerSpecification.filterByBanned(banned))
                        .and(PlayerSpecification.filterByExperience(minExperience, maxExperience))
                        .and(PlayerSpecification.filterByLevel(minLevel, maxLevel)),
                        pageable).getContent();
    }

    @GetMapping("/count")
    public Long  getPlayersCount(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Race race,
            @RequestParam(required = false) Profession profession,
            @RequestParam(required = false) Long after,
            @RequestParam(required = false) Long before,
            @RequestParam(required = false) Boolean banned,
            @RequestParam(required = false) Integer minExperience,
            @RequestParam(required = false) Integer maxExperience,
            @RequestParam(required = false) Integer minLevel,
            @RequestParam(required = false) Integer maxLevel){

        return playersService.getCountPlayers(
                Specification.where(PlayerSpecification.filterByName(name))
                        .and(PlayerSpecification.filterByTitle(title))
                        .and(PlayerSpecification.filterByRace(race))
                        .and(PlayerSpecification.filterByProfession(profession))
                        .and(PlayerSpecification.filterByBirthday(after, before))
                        .and(PlayerSpecification.filterByBanned(banned))
                        .and(PlayerSpecification.filterByExperience(minExperience, maxExperience))
                        .and(PlayerSpecification.filterByLevel(minLevel, maxLevel))

        );

    }

    @GetMapping ("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable("id") Long id){
        return ResponseEntity.ok(playersService.getPlayer(id));
    }

    @PostMapping()
    public ResponseEntity<Player> createPlayer(@RequestBody Player player){
        return ResponseEntity.ok(playersService.createPlayer(player));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable("id") Long id, @RequestBody Player player){
        return ResponseEntity.ok(playersService.updatePlayer(id, player));
    }

    @DeleteMapping ("/{id}")
    public void deletePlayer(@PathVariable("id") Long id){
         playersService.deletePlayer(id);
    }

}
