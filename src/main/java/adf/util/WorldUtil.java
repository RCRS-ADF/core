package adf.util;

import adf.agent.communication.standard.bundle.information.*;
import adf.agent.info.WorldInfo;
import rescuecore2.standard.entities.*;
import rescuecore2.worldmodel.EntityID;

import java.util.Collection;
import java.util.stream.Collectors;

public class WorldUtil {

    public static Collection<EntityID> convertToID(Collection<StandardEntity> entities) {
        return entities.stream().map(StandardEntity::getID).collect(Collectors.toList());
    }

    public static Collection<StandardEntity> convertToEntity(Collection<EntityID> entityIDs, WorldInfo world) {
        return entityIDs.stream().map(world::getEntity).collect(Collectors.toList());
    }

    public static Building reflectedMessage(WorldInfo worldInfo, MessageBuilding message) {
        Building building = (Building)worldInfo.getEntity(message.getBuildingID());
        building.setFieryness(message.getFieryness());
        building.setBrokenness(message.getBrokenness());
        return building;
    }

    public static AmbulanceTeam reflectedMessage(WorldInfo worldInfo, MessageAmbulanceTeam message) {
        AmbulanceTeam ambulanceteam = (AmbulanceTeam) worldInfo.getEntity(message.getSenderID());
        if (ambulanceteam == null) {
            worldInfo.addEntity(new AmbulanceTeam(message.getSenderID()));
            ambulanceteam = (AmbulanceTeam) worldInfo.getEntity(message.getSenderID());
        }
        ambulanceteam.setHP(message.getHP());
        ambulanceteam.setBuriedness(message.getBuriedness());
        ambulanceteam.setDamage(message.getDamage());
        ambulanceteam.setPosition(message.getPosition());
        return ambulanceteam;
    }

    public static Civilian reflectedMessage(WorldInfo worldInfo, MessageCivilian message) {
        Civilian civilian = (Civilian)worldInfo.getEntity(message.getSenderID());
        if (civilian == null) {
            worldInfo.addEntity(new Civilian(message.getSenderID()));
            civilian = (Civilian) worldInfo.getEntity(message.getSenderID());
        }
        civilian.setHP(message.getHP());
        civilian.setBuriedness(message.getBuriedness());
        civilian.setDamage(message.getDamage());
        civilian.setPosition(message.getPosition());
        return civilian;
    }

    public static FireBrigade reflectedMessage(WorldInfo worldInfo, MessageFireBrigade message) {
        FireBrigade firebrigade = (FireBrigade) worldInfo.getEntity(message.getSenderID());
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

    public static PoliceForce reflectedMessage(WorldInfo worldInfo, MessagePoliceForce message) {
        PoliceForce policeforce = (PoliceForce) worldInfo.getEntity(message.getSenderID());
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

    public static Blockade reflectedMessage(WorldInfo worldInfo, MessageRoad message) {
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
