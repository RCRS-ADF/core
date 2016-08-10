package adf.agent.communication.standard.bundle;

import adf.agent.communication.standard.bundle.information.*;
import adf.agent.info.WorldInfo;
import rescuecore2.standard.entities.*;

public class MessageUtil {

    public static Building reflectMessage(WorldInfo worldInfo, MessageBuilding message) {
        Building building = (Building)worldInfo.getEntity(message.getBuildingID());
        if(message.isFierynessDefined()) building.setFieryness(message.getFieryness());
        if(message.isBrokennessDefined()) building.setBrokenness(message.getBrokenness());
        if(message.isTemperatureDefined()) building.setTemperature(message.getTemperature());
        return building;
    }

    public static AmbulanceTeam reflectMessage(WorldInfo worldInfo, MessageAmbulanceTeam message) {
        AmbulanceTeam ambulanceteam = (AmbulanceTeam) worldInfo.getEntity(message.getAgentID());
        if (ambulanceteam != null) {
            if(message.isHPDefined()) ambulanceteam.setHP(message.getHP());
            if(message.isBuriednessDefined()) ambulanceteam.setBuriedness(message.getBuriedness());
            if(message.isDamageDefined()) ambulanceteam.setDamage(message.getDamage());
            if(message.isPositionDefined()) ambulanceteam.setPosition(message.getPosition());
        } else {
            ambulanceteam = new AmbulanceTeam(message.getAgentID());
            if(message.isHPDefined()) ambulanceteam.setHP(message.getHP());
            if(message.isBuriednessDefined()) ambulanceteam.setBuriedness(message.getBuriedness());
            if(message.isDamageDefined()) ambulanceteam.setDamage(message.getDamage());
            if(message.isPositionDefined()) ambulanceteam.setPosition(message.getPosition());
            worldInfo.addEntity(ambulanceteam);
        }
        return ambulanceteam;
    }

    public static Civilian reflectMessage(WorldInfo worldInfo, MessageCivilian message) {
        Civilian civilian = (Civilian)worldInfo.getEntity(message.getAgentID());
        if (civilian != null) {
            if(message.isHPDefined()) civilian.setHP(message.getHP());
            if(message.isBuriednessDefined()) civilian.setBuriedness(message.getBuriedness());
            if(message.isDamageDefined()) civilian.setDamage(message.getDamage());
            if(message.isPositionDefined()) civilian.setPosition(message.getPosition());
        } else {
            civilian = new Civilian(message.getAgentID());
            if(message.isHPDefined()) civilian.setHP(message.getHP());
            if(message.isBuriednessDefined()) civilian.setBuriedness(message.getBuriedness());
            if(message.isDamageDefined()) civilian.setDamage(message.getDamage());
            if(message.isPositionDefined()) civilian.setPosition(message.getPosition());
            worldInfo.addEntity(civilian);
        }
        return civilian;
    }

    public static FireBrigade reflectMessage(WorldInfo worldInfo, MessageFireBrigade message) {
        FireBrigade firebrigade = (FireBrigade) worldInfo.getEntity(message.getAgentID());
        if (firebrigade != null) {
            if(message.isHPDefined()) firebrigade.setHP(message.getHP());
            if(message.isBuriednessDefined()) firebrigade.setBuriedness(message.getBuriedness());
            if(message.isDamageDefined()) firebrigade.setDamage(message.getDamage());
            if(message.isPositionDefined()) firebrigade.setPosition(message.getPosition());
            if(message.isWaterDefined()) firebrigade.setWater(message.getWater());
        } else {
            firebrigade = new FireBrigade(message.getAgentID());
            if(message.isHPDefined()) firebrigade.setHP(message.getHP());
            if(message.isBuriednessDefined()) firebrigade.setBuriedness(message.getBuriedness());
            if(message.isDamageDefined()) firebrigade.setDamage(message.getDamage());
            if(message.isPositionDefined()) firebrigade.setPosition(message.getPosition());
            if(message.isWaterDefined()) firebrigade.setWater(message.getWater());
            worldInfo.addEntity(firebrigade);
        }
        return firebrigade;
    }

    public static PoliceForce reflectMessage(WorldInfo worldInfo, MessagePoliceForce message) {
        PoliceForce policeforce = (PoliceForce) worldInfo.getEntity(message.getAgentID());
        if (policeforce != null) {
            if(message.isHPDefined()) policeforce.setHP(message.getHP());
            if(message.isBuriednessDefined()) policeforce.setBuriedness(message.getBuriedness());
            if(message.isDamageDefined()) policeforce.setDamage(message.getDamage());
            if(message.isPositionDefined()) policeforce.setPosition(message.getPosition());
        } else {
            policeforce = new PoliceForce(message.getAgentID());
            if(message.isHPDefined()) policeforce.setHP(message.getHP());
            if(message.isBuriednessDefined()) policeforce.setBuriedness(message.getBuriedness());
            if(message.isDamageDefined()) policeforce.setDamage(message.getDamage());
            if(message.isPositionDefined()) policeforce.setPosition(message.getPosition());
            worldInfo.addEntity(policeforce);
        }
        return policeforce;
    }

    public static Blockade reflectMessage(WorldInfo worldInfo, MessageRoad message) {
        if(message.getBlockadeID() == null) return null;

        Blockade blockade = (Blockade) worldInfo.getEntity(message.getBlockadeID());
        if (blockade != null) {
            blockade.setPosition(message.getRoadID());
            if(message.isRepairCostDefined()) blockade.setRepairCost(message.getRepairCost());
            if(message.isXDefined()) blockade.setX(message.getBlockadeX());
            if(message.isYDefined()) blockade.setY(message.getBlockadeY());
        } else {
            blockade = new Blockade(message.getBlockadeID());
            blockade.setPosition(message.getRoadID());
            if(message.isRepairCostDefined()) blockade.setRepairCost(message.getRepairCost());
            if(message.isXDefined()) blockade.setX(message.getBlockadeX());
            if(message.isYDefined()) blockade.setY(message.getBlockadeY());
            worldInfo.addEntity(blockade);
        }
        return blockade;
    }
}