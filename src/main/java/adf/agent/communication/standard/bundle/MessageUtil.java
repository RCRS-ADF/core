package adf.agent.communication.standard.bundle;

import adf.agent.communication.standard.bundle.information.*;
import adf.agent.info.WorldInfo;
import rescuecore2.standard.entities.*;

public class MessageUtil {

    public static Building reflectMessage(WorldInfo worldInfo, MessageBuilding message) {
        Building building = (Building)worldInfo.getEntity(message.getBuildingID());
        building.setFieryness(message.getFieryness());
        building.setBrokenness(message.getBrokenness());
        return building;
    }

    public static AmbulanceTeam reflectMessage(WorldInfo worldInfo, MessageAmbulanceTeam message) {
        AmbulanceTeam ambulanceteam = (AmbulanceTeam) worldInfo.getEntity(message.getAgentID());
        if (ambulanceteam == null) {
            worldInfo.addEntity(new AmbulanceTeam(message.getAgentID()));
            ambulanceteam = (AmbulanceTeam) worldInfo.getEntity(message.getAgentID());
        }
        ambulanceteam.setHP(message.getHP());
        ambulanceteam.setBuriedness(message.getBuriedness());
        ambulanceteam.setDamage(message.getDamage());
        ambulanceteam.setPosition(message.getPosition());
        return ambulanceteam;
    }

    public static Civilian reflectMessage(WorldInfo worldInfo, MessageCivilian message) {
        Civilian civilian = (Civilian)worldInfo.getEntity(message.getAgentID());
        if (civilian == null) {
            worldInfo.addEntity(new Civilian(message.getAgentID()));
            civilian = (Civilian) worldInfo.getEntity(message.getAgentID());
        }
        civilian.setHP(message.getHP());
        civilian.setBuriedness(message.getBuriedness());
        civilian.setDamage(message.getDamage());
        civilian.setPosition(message.getPosition());
        return civilian;
    }

    public static FireBrigade reflectMessage(WorldInfo worldInfo, MessageFireBrigade message) {
        FireBrigade firebrigade = (FireBrigade) worldInfo.getEntity(message.getAgentID());
        if (firebrigade == null) {
            worldInfo.addEntity(new FireBrigade(message.getTargetID()));
            firebrigade = (FireBrigade) worldInfo.getEntity(message.getTargetID());
        }
        firebrigade.setHP(message.getHP());
        firebrigade.setBuriedness(message.getBuriedness());
        firebrigade.setDamage(message.getDamage());
        firebrigade.setPosition(message.getPosition());
        firebrigade.setWater(message.getWater());
        return firebrigade;
    }

    public static PoliceForce reflectMessage(WorldInfo worldInfo, MessagePoliceForce message) {
        PoliceForce policeforce = (PoliceForce) worldInfo.getEntity(message.getAgentID());
        if (policeforce == null) {
            worldInfo.addEntity(new PoliceForce(message.getTargetID()));
            policeforce = (PoliceForce) worldInfo.getEntity(message.getTargetID());
        }
        policeforce.setHP(message.getHP());
        policeforce.setBuriedness(message.getBuriedness());
        policeforce.setDamage(message.getDamage());
        policeforce.setPosition(message.getPosition());
        return policeforce;
    }

    public static Blockade reflectMessage(WorldInfo worldInfo, MessageRoad message) {
        Blockade blockade = (Blockade) worldInfo.getEntity(message.getBlockadeID());
        if (blockade == null) {
            worldInfo.addEntity(new Blockade(message.getBlockadeID()));
            blockade = (Blockade) worldInfo.getEntity(message.getBlockadeID());
        }
        blockade.setPosition(message.getRoadID());
        blockade.setRepairCost(message.getRepairCost());
        return blockade;
    }
}
