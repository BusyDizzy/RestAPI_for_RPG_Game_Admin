package com.game.specifications;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class PlayerSpecification {

    public static Specification<Player> filterByName(String name){
        return (root, query, criteriaBuilder) -> name == null?null:criteriaBuilder.like(root.get("name"),"%" + name + "%");
    }

    public static Specification<Player> filterByTitle(String title){
        return (root, query, criteriaBuilder) -> title == null?null:criteriaBuilder.like(root.get("title"),"%" + title + "%");
    }

    public static Specification<Player> filterByRace(Race race){
        return (root, query, criteriaBuilder) -> race == null?null:criteriaBuilder.equal(root.get("race"),race);
    }

    public static Specification<Player> filterByProfession(Profession profession){
        return (root, query, criteriaBuilder) -> profession == null?null:criteriaBuilder.equal(root.get("profession"),profession);
    }


    public static Specification<Player> filterByBirthday(Long after, Long before){
        return (root, query, criteriaBuilder) -> {
            if (after == null && before == null ) return null;
            if (after == null) return criteriaBuilder.lessThanOrEqualTo(root.get("birthday"), new Date(before));
            if (before == null) return criteriaBuilder.greaterThanOrEqualTo(root.get("birthday"), new Date(after));
            return criteriaBuilder.between(root.get("birthday"), new Date(after), new Date(before));
        };
    }

    public static Specification<Player> filterByExperience(Integer min, Integer max){
        return (root, query, criteriaBuilder) -> {
            if (min == null && max == null ) return null;
            if (min == null) return criteriaBuilder.lessThanOrEqualTo(root.get("experience"), max);
            if (max == null) return criteriaBuilder.greaterThanOrEqualTo(root.get("experience"), min);
            return criteriaBuilder.between(root.get("experience"), min, max);
        };
    }

    public static Specification<Player> filterByLevel(Integer min, Integer max) {
        return (root, query, criteriaBuilder) -> {
            if (min == null && max == null) return null;
            if (min == null) return criteriaBuilder.lessThanOrEqualTo(root.get("level"), max);
            if (max == null) return criteriaBuilder.greaterThanOrEqualTo(root.get("level"), min);
            return criteriaBuilder.between(root.get("level"), min, max);
        };
    }
    public static Specification<Player> filterByBanned(Boolean isBanned){
        return (root, query, criteriaBuilder) -> {
            if (isBanned == null ) return null;
            if (isBanned) return criteriaBuilder.isTrue(root.get("banned"));
             return criteriaBuilder.isFalse(root.get("banned"));
        };
    }

}
