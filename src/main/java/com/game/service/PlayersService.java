package com.game.service;


import com.game.entity.Player;
import com.game.exceptions.NotFoundException;
import com.game.repository.PlayersRepository;
import com.game.util.NewPlayerValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PlayersService extends NewPlayerValidations {
    private PlayersRepository playersRepository;

    @Autowired
    public PlayersService(PlayersRepository playersRepository) {
        this.playersRepository = playersRepository;
    }

    public Page<Player> findAllPlayers(Specification<Player> specification, Pageable pageable) {
        return playersRepository.findAll(specification, pageable);
    }

    public Long getCountPlayers(Specification<Player> specification) {
        return playersRepository.count(specification);
    }
    public Player getPlayer(Long id){
        checkId(id);
        return playersRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Error 404! Player not found!"));
    }

    @Transactional
    public Player createPlayer(Player player){
        checkName(player.getName());
        checkTitle(player.getTitle());
        checkRace(player.getRace());
        checkProfession(player.getProfession());
        checkExperience(player.getExperience());
        checkBirthday(player.getBirthday());
        if (player.getBanned() == null){
            player.setBanned(false);
        }
        // Перерасчитываем level и UntilNextLevel
        newLevelAndUntilNextLevelExperience(player);

        return playersRepository.save(player);
    }

    @Transactional
    public Player updatePlayer(Long id, Player updatedPlayer){
        Player playerToBeUpdated = getPlayer(id);

        if (playerToBeUpdated == null){
            return null;
        }
        updatedPlayer.setId(id);

        if (updatedPlayer.getName() !=null){
            checkName(updatedPlayer.getName());
            playerToBeUpdated.setName(updatedPlayer.getName());
        }
        if (updatedPlayer.getTitle() != null) {
            checkTitle(updatedPlayer.getTitle());
            playerToBeUpdated.setTitle(updatedPlayer.getTitle());
        }
        if (updatedPlayer.getRace() != null) {
            checkRace(updatedPlayer.getRace());
            playerToBeUpdated.setRace(updatedPlayer.getRace());
        }
        if (updatedPlayer.getProfession() != null) {
            checkProfession(updatedPlayer.getProfession());
            playerToBeUpdated.setProfession(updatedPlayer.getProfession());
        }
        if (updatedPlayer.getExperience() != null) {
            checkExperience(updatedPlayer.getExperience());
            playerToBeUpdated.setExperience(updatedPlayer.getExperience());
        }
        if (updatedPlayer.getBirthday() != null) {
            checkBirthday(updatedPlayer.getBirthday());
            playerToBeUpdated.setBirthday(updatedPlayer.getBirthday());
        }
        if (updatedPlayer.getBanned() != null) {
            playerToBeUpdated.setBanned(updatedPlayer.getBanned());
        }

        // Перерасчитываем level и UntilNextLevel
        newLevelAndUntilNextLevelExperience(playerToBeUpdated);
        return   playersRepository.save(playerToBeUpdated);
    }

    @Transactional
    public void deletePlayer(Long id){
         Player player = getPlayer(id);
         playersRepository.delete(player);
    }

}
