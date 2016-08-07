package adf.agent.communication.standard.bundle;

import adf.agent.communication.standard.bundle.information.*;
import adf.agent.info.WorldInfo;
import rescuecore2.standard.entities.*;

public class MessageUtil {

    public static Building reflectMessage(WorldInfo worldInfo, MessageBuilding message) {
        Building building = (Building)worldInfo.getEntity(message.getBuildingID());
        worldInfo.setRollbackEntity(-2, WorldInfo.RollbackType.ROLLBACK_CHANGE, building);
        if(message.isFierynessDefined()) building.setFieryness(message.getFieryness());
        if(message.isBrokennessDefined()) building.setBrokenness(message.getBrokenness());
        if(message.isTemperatureDefined()) building.setTemperature(message.getTemperature());
        worldInfo.setRollbackEntity(-1, WorldInfo.RollbackType.ROLLBACK_CHANGE, building);
        return building;
    }

    public static AmbulanceTeam reflectMessage(WorldInfo worldInfo, MessageAmbulanceTeam message) {
        AmbulanceTeam ambulanceteam = (AmbulanceTeam) worldInfo.getEntity(message.getAgentID());
        if (ambulanceteam != null) {
            worldInfo.setRollbackEntity(-2, WorldInfo.RollbackType.ROLLBACK_CHANGE, ambulanceteam);
            if(message.isHPDefined()) ambulanceteam.setHP(message.getHP());
            if(message.isBuriednessDefined()) ambulanceteam.setBuriedness(message.getBuriedness());
            if(message.isDamageDefined()) ambulanceteam.setDamage(message.getDamage());
            if(message.isPositionDefined()) ambulanceteam.setPosition(message.getPosition());
            worldInfo.setRollbackEntity(-1, WorldInfo.RollbackType.ROLLBACK_CHANGE, ambulanceteam);
        } else {
            worldInfo.addEntity(new AmbulanceTeam(message.getAgentID()));
            ambulanceteam = (AmbulanceTeam) worldInfo.getEntity(message.getAgentID());
            if(message.isHPDefined()) ambulanceteam.setHP(message.getHP());
            if(message.isBuriednessDefined()) ambulanceteam.setBuriedness(message.getBuriedness());
            if(message.isDamageDefined()) ambulanceteam.setDamage(message.getDamage());
            if(message.isPositionDefined()) ambulanceteam.setPosition(message.getPosition());
            worldInfo.setRollbackEntity(-1, WorldInfo.RollbackType.ROLLBACK_ADD, ambulanceteam);
        }
        return ambulanceteam;
    }

    public static Civilian reflectMessage(WorldInfo worldInfo, MessageCivilian message) {
        Civilian civilian = (Civilian)worldInfo.getEntity(message.getAgentID());
        if (civilian != null) {
            worldInfo.setRollbackEntity(-2, WorldInfo.RollbackType.ROLLBACK_CHANGE, civilian);
            if(message.isHPDefined()) civilian.setHP(message.getHP());
            if(message.isBuriednessDefined()) civilian.setBuriedness(message.getBuriedness());
            if(message.isDamageDefined()) civilian.setDamage(message.getDamage());
            if(message.isPositionDefined()) civilian.setPosition(message.getPosition());
            worldInfo.setRollbackEntity(-1, WorldInfo.RollbackType.ROLLBACK_CHANGE, civilian);
        } else {
            worldInfo.addEntity(new Civilian(message.getAgentID()));
            civilian = (Civilian) worldInfo.getEntity(message.getAgentID());
            if(message.isHPDefined()) civilian.setHP(message.getHP());
            if(message.isBuriednessDefined()) civilian.setBuriedness(message.getBuriedness());
            if(message.isDamageDefined()) civilian.setDamage(message.getDamage());
            if(message.isPositionDefined()) civilian.setPosition(message.getPosition());
            worldInfo.setRollbackEntity(-1, WorldInfo.RollbackType.ROLLBACK_ADD, civilian);
        }
        return civilian;
    }

    public static FireBrigade reflectMessage(WorldInfo worldInfo, MessageFireBrigade message) {
        FireBrigade firebrigade = (FireBrigade) worldInfo.getEntity(message.getAgentID());
        if (firebrigade != null) {
            worldInfo.setRollbackEntity(-2, WorldInfo.RollbackType.ROLLBACK_CHANGE, firebrigade);
            if(message.isHPDefined()) firebrigade.setHP(message.getHP());
            if(message.isBuriednessDefined()) firebrigade.setBuriedness(message.getBuriedness());
            if(message.isDamageDefined()) firebrigade.setDamage(message.getDamage());
            if(message.isPositionDefined()) firebrigade.setPosition(message.getPosition());
            if(message.isWaterDefined()) firebrigade.setWater(message.getWater());
            worldInfo.setRollbackEntity(-1, WorldInfo.RollbackType.ROLLBACK_CHANGE, firebrigade);
        } else {
            worldInfo.addEntity(new FireBrigade(message.getTargetID()));
            firebrigade = (FireBrigade) worldInfo.getEntity(message.getTargetID());
            if(message.isHPDefined()) firebrigade.setHP(message.getHP());
            if(message.isBuriednessDefined()) firebrigade.setBuriedness(message.getBuriedness());
            if(message.isDamageDefined()) firebrigade.setDamage(message.getDamage());
            if(message.isPositionDefined()) firebrigade.setPosition(message.getPosition());
            if(message.isWaterDefined()) firebrigade.setWater(message.getWater());
            worldInfo.setRollbackEntity(-1, WorldInfo.RollbackType.ROLLBACK_ADD, firebrigade);
        }
        return firebrigade;
    }

    public static PoliceForce reflectMessage(WorldInfo worldInfo, MessagePoliceForce message) {
        PoliceForce policeforce = (PoliceForce) worldInfo.getEntity(message.getAgentID());
        if (policeforce != null) {
            worldInfo.setRollbackEntity(-2, WorldInfo.RollbackType.ROLLBACK_CHANGE, policeforce);
            if(message.isHPDefined()) policeforce.setHP(message.getHP());
            if(message.isBuriednessDefined()) policeforce.setBuriedness(message.getBuriedness());
            if(message.isDamageDefined()) policeforce.setDamage(message.getDamage());
            if(message.isPositionDefined()) policeforce.setPosition(message.getPosition());
            worldInfo.setRollbackEntity(-1, WorldInfo.RollbackType.ROLLBACK_CHANGE, policeforce);
        } else {
            worldInfo.addEntity(new PoliceForce(message.getTargetID()));
            policeforce = (PoliceForce) worldInfo.getEntity(message.getTargetID());
            if(message.isHPDefined()) policeforce.setHP(message.getHP());
            if(message.isBuriednessDefined()) policeforce.setBuriedness(message.getBuriedness());
            if(message.isDamageDefined()) policeforce.setDamage(message.getDamage());
            if(message.isPositionDefined()) policeforce.setPosition(message.getPosition());
            worldInfo.setRollbackEntity(-1, WorldInfo.RollbackType.ROLLBACK_ADD, policeforce);
        }
        return policeforce;
    }

    public static Blockade reflectMessage(WorldInfo worldInfo, MessageRoad message) {
        if(message.getBlockadeID() == null) return null;

        Blockade blockade = (Blockade) worldInfo.getEntity(message.getBlockadeID());
        if (blockade != null) {
            worldInfo.setRollbackEntity(-2, WorldInfo.RollbackType.ROLLBACK_CHANGE, blockade);
            blockade.setPosition(message.getRoadID());
            if(message.isRepairCostDefined()) blockade.setRepairCost(message.getRepairCost());
            if(message.isXDefined()) blockade.setX(message.getBlockadeX());
            if(message.isYDefined()) blockade.setY(message.getBlockadeY());
            worldInfo.setRollbackEntity(-1, WorldInfo.RollbackType.ROLLBACK_CHANGE, blockade);
        } else {
            worldInfo.addEntity(new Blockade(message.getBlockadeID()));
            blockade = (Blockade) worldInfo.getEntity(message.getBlockadeID());
            blockade.setPosition(message.getRoadID());
            if(message.isRepairCostDefined()) blockade.setRepairCost(message.getRepairCost());
            if(message.isXDefined()) blockade.setX(message.getBlockadeX());
            if(message.isYDefined()) blockade.setY(message.getBlockadeY());
            worldInfo.setRollbackEntity(-1, WorldInfo.RollbackType.ROLLBACK_ADD, blockade);
        }
        return blockade;
    }
}
