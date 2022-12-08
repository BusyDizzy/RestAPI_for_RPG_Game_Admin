package com.game.util;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.exceptions.BadRequestException;
import com.game.exceptions.NotFoundException;

import java.util.Calendar;
import java.util.Date;

/**
 * Класс для проверки на валидность вводимых параметров,
 * а также соответствия бизнес требованиям (внедрения бизнес логики)
 */

public class NewPlayerValidations {

    private static final int MAX_LENGTH_NAME = 12;
    private static final int MAX_LENGTH_TITLE = 30;
    private static final int MAX_SIZE_EXPERIENCE = 10000000;
    private static final long MIN_BIRTHDAY = 2000L;
    private static final long MAX_BIRTHDAY = 3000L;


    protected void checkId(Long id){
        if ( id<=0 ) throw new BadRequestException("Id is invalid");
    }
    protected void checkName(String name){
        if (name == null || name.isEmpty() || name.length() > MAX_LENGTH_NAME)
            throw new BadRequestException("Name is invalid");
    }

    protected void checkTitle(String title){
        if (title == null || title.isEmpty() || title.length() > MAX_LENGTH_TITLE)
            throw new BadRequestException("Title is invalid");
    }

    protected void checkRace(Race race){
        if (race == null )
            throw new BadRequestException("Race is invalid");
    }

    protected void checkProfession(Profession profession){
        if (profession == null )
            throw new BadRequestException("Race is invalid");
    }

    protected void checkExperience(Integer experience){
        if (experience == null || experience <0 || experience > MAX_SIZE_EXPERIENCE)
            throw new BadRequestException("Experience is invalid");
    }

    protected void checkBirthday(Date birthday){
        if(birthday==null)
            throw new BadRequestException("Birthday is invalid");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(birthday.getTime());
        if (calendar.get(Calendar.YEAR) < MIN_BIRTHDAY || calendar.get(Calendar.YEAR) > MAX_BIRTHDAY)
            throw new BadRequestException("Birthday is out of bounds");
    }

    /**
     * Бизнес-логика игрового приложения. Метод для перерасчета параметров нового игрока:
     *  1. Текущего уровня персонажа
     *  2. Опыта необходимого для следующего уровня
     */

    protected Player newLevelAndUntilNextLevelExperience(Player player){

        try {
            Integer currentExperience = player.getExperience();

            Integer newLevel = (int) Math.round(Math.sqrt(2500 + 200 * currentExperience) - 50) / 100;
            player.setLevel(newLevel);

            Integer untilNextLevel = 50 * (newLevel + 1) * (newLevel + 2) - currentExperience;
            player.setUntilNextLevel(untilNextLevel);
        }
        catch (Exception e){

        }
        return player;
    }
}
