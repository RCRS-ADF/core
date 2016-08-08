package adf.agent.info;

import rescuecore2.misc.Pair;
import rescuecore2.standard.entities.*;
import rescuecore2.worldmodel.*;

import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.stream.Collectors;

import static rescuecore2.standard.entities.StandardEntityURN.*;

public class WorldInfo implements Iterable<StandardEntity> {
	private StandardWorldModel world;
	private ChangeSet changed;
    private int time;

	private Map<Integer, Map<EntityID, StandardEntity>> rollbackChangeInfo;
	private Map<Integer, Map<EntityID, StandardEntity>> rollbackAddInfo;
	private Map<Integer, Map<EntityID, StandardEntity>> rollbackRemoveInfo;

	public enum RollbackType {
		ROLLBACK_CHANGE,
        ROLLBACK_REMOVE,
		ROLLBACK_ADD
	}

    public WorldInfo(StandardWorldModel world) {
		this.setWorld(world);
        this.time = -1;
		this.rollbackChangeInfo = new HashMap<>();
		this.rollbackAddInfo = new HashMap<>();
		this.rollbackRemoveInfo = new HashMap<>();
		this.world.addWorldModelListener(new RollbackListener());
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void indexClass(StandardEntityURN... urns) {
		this.world.indexClass(urns);
	}

	public void index() {
		this.world.index();
	}

	public ChangeSet getChanged() {
		return this.changed;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public StandardEntity getEntity(EntityID id) {
		return this.world.getEntity(id);
	}

	public StandardEntity getEntity(int targetTime, StandardEntity entity) {
		return this.getEntity(targetTime, entity.getID(), false);
	}

	public StandardEntity getEntity(int targetTime, StandardEntity entity, boolean nullable) {
		return this.getEntity(targetTime, entity.getID(), nullable);
	}

	public StandardEntity getEntity(int targetTime, EntityID entityID) {
		return this.getEntity(targetTime, entityID, false);
	}

	public StandardEntity getEntity(int targetTime, EntityID entityID, boolean nullable) {
		StandardEntity result = this.getEntity(entityID);
		if(targetTime < 0) {
			targetTime = this.time + targetTime;
		} else if(targetTime == 0){
			targetTime = 1;
		}

		for(int i = this.time - 1; i >= targetTime; i--) {
			Map<EntityID, StandardEntity> info = this.rollbackAddInfo.get(i);
			if(info != null) {
				StandardEntity entity = info.get(entityID);
				if (entity != null) {
					if(i > targetTime && nullable) return null;
					result = entity;
				}
			}
			info = this.rollbackChangeInfo.get(i);
			if(info != null) {
				StandardEntity entity = info.get(entityID);
				if(entity != null) {
					result = entity;
					continue;
				}
			}
			info = this.rollbackRemoveInfo.get(i);
			if(info != null) {
				StandardEntity entity = info.get(entityID);
				if(entity != null) {
					result = entity;
				}
			}
		}
		return result;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Collection<StandardEntity> getEntitiesOfType(StandardEntityURN urn) {
		return this.world.getEntitiesOfType(urn);
	}

	public Collection<StandardEntity> getEntitiesOfType(StandardEntityURN... urns) {
		return this.world.getEntitiesOfType(urns);
	}

	public Collection<StandardEntity> getEntitiesOfType(int targetTime, StandardEntityURN urn) {
		Collection<StandardEntity> result = new HashSet<>();
		for(StandardEntity entity : this.world.getEntitiesOfType(urn)) {
			result.add(this.getEntity(targetTime, entity));
		}
		return result;
	}

	public Collection<StandardEntity> getEntitiesOfType(int targetTime, StandardEntityURN... urns) {
		Collection<StandardEntity> result = new HashSet<>();
		for(StandardEntity entity : this.world.getEntitiesOfType(urns)) {
			result.add(this.getEntity(targetTime, entity));
		}
		return result;
	}

	public Collection<EntityID> getEntityIDsOfType(StandardEntityURN urn) {
		return this.convertToID(this.world.getEntitiesOfType(urn));
	}

	public Collection<EntityID> getEntityIDsOfType(StandardEntityURN... urns) {
		return this.convertToID(this.world.getEntitiesOfType(urns));
	}

	public Collection<EntityID> getEntityIDsOfType(int targetTime, StandardEntityURN urn) {
		return this.convertToID(this.getEntitiesOfType(targetTime, urn));
	}

	public Collection<EntityID> getEntityIDsOfType(int targetTime, StandardEntityURN... urns) {
		return this.convertToID(this.getEntitiesOfType(targetTime, urns));
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Collection<StandardEntity> getObjectsInRange(EntityID entity, int range) {
		return this.world.getObjectsInRange(entity, range);
	}

	public Collection<StandardEntity> getObjectsInRange(StandardEntity entity, int range) {
		return this.world.getObjectsInRange(entity, range);
	}

	public Collection<StandardEntity> getObjectsInRange(int x, int y, int range) {
		return this.world.getObjectsInRange(x, y, range);
	}

	public Collection<StandardEntity> getObjectsInRectangle(int x1, int y1, int x2, int y2) {
		return this.world.getObjectsInRectangle(x1, y1, x2, y2);
	}

	public Collection<StandardEntity> getObjectsInRange(int targetTime, EntityID entity, int range) {
		return this.getObjectsInRange(targetTime, entity, range, false);
	}

	public Collection<StandardEntity> getObjectsInRange(int targetTime, EntityID entity, int range, boolean collectHumanData) {
		return this.getObjectsInRange(targetTime, this.getEntity(entity), range, collectHumanData);
	}

	public Collection<StandardEntity> getObjectsInRange(int targetTime, StandardEntity entity, int range) {
		return this.getObjectsInRange(targetTime, entity, range, false);
	}

	public Collection<StandardEntity> getObjectsInRange(int targetTime, StandardEntity entity, int range, boolean collectHumanData) {
		if (entity == null) return new HashSet<>();

		Pair<Integer, Integer> location = this.getLocation(entity);
		if (location == null) return new HashSet<>();

		return this.getObjectsInRange(targetTime, location.first(), location.second(), range, collectHumanData);
	}

	public Collection<StandardEntity> getObjectsInRange(int targetTime, int x, int y, int range) {
		return this.getObjectsInRange(targetTime, x, y, range, false);
	}

	public Collection<StandardEntity> getObjectsInRange(int targetTime, int x, int y, int range, boolean collectHumanData) {
		return this.getObjectsInRectangle(targetTime, x - range, y - range, x + range, y + range, collectHumanData);
	}

	public Collection<StandardEntity> getObjectsInRectangle(int targetTime, int x1, int y1, int x2, int y2) {
		return this.getObjectsInRectangle(targetTime, x1, y1, x2, y2, false);
	}

	public Collection<StandardEntity> getObjectsInRectangle(int targetTime, int x1, int y1, int x2, int y2, boolean collectHumanData) {
		Collection<StandardEntity> result = new HashSet<>();
		if(collectHumanData) {
			for (StandardEntity target : this.world.getObjectsInRectangle(x1, y1, x2, y2)) {
				if (target instanceof Area) {
					result.add(this.getEntity(targetTime, target));
				}
			}
			Map<EntityID, StandardEntity> info = this.rollbackChangeInfo.get(targetTime);
			for(StandardEntity target : info.values()) {
				if(target instanceof Human && result.contains(this.getPosition((Human)target))) {
					result.add(target);
				}
			}
		} else {
			for(StandardEntity target : this.world.getObjectsInRectangle(x1, y1, x2, y2)) {
				result.add(this.getEntity(targetTime, target));
			}
		}
		return result;
	}

	public Collection<EntityID> getObjectIDsInRange(EntityID entity, int range) {
		return this.convertToID(this.world.getObjectsInRange(entity, range));
	}

	public Collection<EntityID> getObjectIDsInRange(StandardEntity entity, int range) {
		return this.convertToID(this.world.getObjectsInRange(entity, range));
	}

	public Collection<EntityID> getObjectIDsInRange(int x, int y, int range) {
		return this.convertToID(this.world.getObjectsInRange(x,y,range));
	}

	public Collection<EntityID> getObjectIDsInRectangle(int x1, int y1, int x2, int y2) {
		return this.convertToID(this.world.getObjectsInRectangle(x1, y1, x2, y2));
	}

	public Collection<EntityID> getObjectIDsInRange(int targetTime, EntityID entity, int range) {
		return this.convertToID(this.getObjectsInRange(targetTime, entity, range));
	}

	public Collection<EntityID> getObjectIDsInRange(int targetTime, EntityID entity, int range, boolean collectHumanData) {
		return this.convertToID(this.getObjectsInRange(targetTime, entity, range, collectHumanData));
	}

	public Collection<EntityID> getObjectIDsInRange(int targetTime, StandardEntity entity, int range) {
		return this.convertToID(this.getObjectsInRange(targetTime, entity, range));
	}

	public Collection<EntityID> getObjectIDsInRange(int targetTime, StandardEntity entity, int range, boolean collectHumanData) {
		return this.convertToID(this.getObjectsInRange(targetTime, entity, range, collectHumanData));
	}

	public Collection<EntityID> getObjectIDsInRange(int targetTime, int x, int y, int range) {
		return this.convertToID(this.getObjectsInRange(targetTime, x,y,range));
	}

	public Collection<EntityID> getObjectIDsInRange(int targetTime, int x, int y, int range, boolean collectHumanData) {
		return this.convertToID(this.getObjectsInRange(targetTime, x,y,range, collectHumanData));
	}

	public Collection<EntityID> getObjectIDsInRectangle(int targetTime, int x1, int y1, int x2, int y2) {
		return this.convertToID(this.getObjectsInRectangle(targetTime, x1, y1, x2, y2));
	}

	public Collection<EntityID> getObjectIDsInRectangle(int targetTime, int x1, int y1, int x2, int y2, boolean collectHumanData) {
		return this.convertToID(this.getObjectsInRectangle(targetTime, x1, y1, x2, y2, collectHumanData));
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Collection<StandardEntity> getAllEntities() {
		return this.world.getAllEntities();
	}

	public Collection<StandardEntity> getAllEntities(int targetTime) {
		Collection<StandardEntity> result = new HashSet<>();
		for(StandardEntity entity : this.world.getAllEntities()) {
			result.add(this.getEntity(targetTime, entity));
		}
		return result;
	}

	public Map<EntityID, StandardEntity> getChangeEntities(int time) {
		return this.rollbackChangeInfo.get(time);
	}

	public Map<EntityID, StandardEntity> getAddEntities(int time) {
		return this.rollbackAddInfo.get(time);
	}

	public Map<EntityID, StandardEntity> getRemoveEntities(int time) {
		return this.rollbackRemoveInfo.get(time);
	}

	public Collection<Building> getFireBuildingSet() {
		Set<Building> fireBuildings = new HashSet<>();
		for(StandardEntity entity : this.getEntitiesOfType(BUILDING, GAS_STATION, AMBULANCE_CENTRE, FIRE_STATION, POLICE_OFFICE)) {
			Building building = (Building)entity;
			if(building.isOnFire()) fireBuildings.add(building);
		}
		return fireBuildings;
	}

	public Collection<EntityID> getFireBuildingIDs() {
		return this.getFireBuildingSet().stream().map(Building::getID).collect(Collectors.toList());
	}

	public int getNumberOfBuried(Building entity) {
		return this.getNumberOfBuried(entity.getID());
	}

	public int getNumberOfBuried(EntityID entityID) {
		int value = 0;
		for(StandardEntity entity : this.getEntitiesOfType(CIVILIAN, AMBULANCE_TEAM, FIRE_BRIGADE, POLICE_FORCE)) {
			Human human = (Human)entity;
			if(this.getPosition(human).getID().getValue() == entityID.getValue()) {
				if(human.isBuriednessDefined() && human.getBuriedness() > 0) value++;
			}
		}
		return value;
	}

	public Collection<Human> getBuriedEntities(Building building) {
		return this.getBuriedEntities(building.getID());
	}

	public Collection<Human> getBuriedEntities(EntityID entityID) {
		Collection<Human> result = new HashSet<>();
		for(StandardEntity entity : this.getEntitiesOfType(CIVILIAN, AMBULANCE_TEAM, FIRE_BRIGADE, POLICE_FORCE)) {
			Human human = (Human)entity;
			if(this.getPosition(human).getID().getValue() == entityID.getValue()) {
				if(human.isBuriednessDefined() && human.getBuriedness() > 0) result.add(human);
			}
		}
		return result;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public StandardEntity getPosition(Human entity) {
        return entity.getPosition(this.world);
    }

    public StandardEntity getPosition(EntityID entityID) {
        StandardEntity entity = this.getEntity(entityID);
        return (entity instanceof Human) ? this.getPosition((Human)entity) : null;
    }

    public Pair<Integer, Integer> getLocation(StandardEntity entity) {
        return entity.getLocation(this.world);
    }

    public Pair<Integer, Integer> getLocation(EntityID entityID) {
        return this.getLocation(this.getEntity(entityID));
    }

	public int getDistance(EntityID first, EntityID second) {
		return this.world.getDistance(first, second);
	}

	public int getDistance(StandardEntity first, StandardEntity second) {
		return this.world.getDistance(first, second);
	}

	public StandardEntity getPosition(int targetTime, Human entity) {
		return this.getEntity(targetTime, entity.getPosition(this.world));
	}

	public StandardEntity getPosition(int targetTime, EntityID entityID) {
		StandardEntity entity = this.getEntity(entityID);
		return (entity instanceof Human) ? this.getEntity(targetTime, this.getPosition((Human)entity)) : null;
	}

	public Pair<Integer, Integer> getLocation(int targetTime, StandardEntity entity) {
		StandardEntity target = this.getEntity(targetTime, entity);
		return target.getLocation(this.world);
	}

	public Pair<Integer, Integer> getLocation(int targetTime, EntityID entityID) {
		StandardEntity target = this.getEntity(targetTime, entityID);
		return target.getLocation(this.world);
	}

	public int getDistance(int targetTime, EntityID first, EntityID second) {
		return this.world.getDistance(this.getEntity(targetTime, first), this.getEntity(targetTime, second));
	}

	public int getDistance(int targetTime, StandardEntity first, StandardEntity second) {
		return this.world.getDistance(this.getEntity(targetTime, first), this.getEntity(targetTime, second));
	}

	public Rectangle2D getBounds() {
		return this.world.getBounds();
	}

	public Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> getWorldBounds() {
		return this.world.getWorldBounds();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Iterator<StandardEntity> iterator() {
		return this.world.iterator();
	}

	public void setRollbackEntity(int targetTime, RollbackType type, StandardEntity entity) {
		if(targetTime <= 0) targetTime = this.time + targetTime;

		if(type == RollbackType.ROLLBACK_CHANGE) {
			Map<EntityID, StandardEntity> changeInfo = this.rollbackChangeInfo.get(targetTime);
			StandardEntity target = changeInfo.get(entity.getID());
			if(target == null) {
				changeInfo.put(entity.getID(), (StandardEntity) entity.copy());
			}
			this.rollbackChangeInfo.put(targetTime, changeInfo);
		} else if(type == RollbackType.ROLLBACK_ADD) {
			Map<EntityID, StandardEntity> addInfo = this.rollbackAddInfo.get(targetTime);
			StandardEntity target = addInfo.get(entity.getID());
			if(target == null) {
				addInfo.put(entity.getID(), entity);
			}
			this.rollbackAddInfo.put(targetTime, addInfo);
		} else if(type == RollbackType.ROLLBACK_REMOVE) {
			Map<EntityID, StandardEntity> removeInfo = this.rollbackRemoveInfo.get(targetTime);
			StandardEntity target = removeInfo.get(entity.getID());
			if (target == null) {
				removeInfo.put(entity.getID(), entity);
			}
			this.rollbackRemoveInfo.put(targetTime, removeInfo);
		}
	}

	public void setWorld(StandardWorldModel world)
	{
		this.world = world;
	}

	public void setChanged(ChangeSet changed) {
		this.changed = changed;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public void createRollback(int time, ChangeSet changed) {
		this.setTime(time);
		Map<EntityID, StandardEntity> changeInfo = new HashMap<>();
		for(EntityID entityID : changed.getChangedEntities()) {
			StandardEntity entity = this.getEntity(entityID);
			if(entity != null) changeInfo.put(entityID, (StandardEntity) entity.copy());
		}

		this.rollbackChangeInfo.put(time - 1, changeInfo);
		this.rollbackAddInfo.put(time, new HashMap<>());
		this.rollbackRemoveInfo.put(time, new HashMap<>());
	}

	private class RollbackListener implements WorldModelListener<StandardEntity> {
		@Override
		public void entityAdded(WorldModel<? extends StandardEntity> model, StandardEntity e) {
			Map<EntityID, StandardEntity> addInfo = rollbackAddInfo.get(time);
			addInfo.put(e.getID(), (StandardEntity)  e.copy());
			rollbackAddInfo.put(time, addInfo);
		}

		@Override
		public void entityRemoved(WorldModel<? extends StandardEntity> model, StandardEntity e) {
			Map<EntityID, StandardEntity> removeInfo = rollbackRemoveInfo.get(time);
			removeInfo.put(e.getID(), (StandardEntity)e.copy());
			rollbackRemoveInfo.put(time, removeInfo);
		}
	}

    public void addEntity(Entity e) {
        this.world.addEntity(e);
    }

    public void addEntities(Collection<? extends Entity> e) {
        this.world.addEntities(e);
    }

    public void removeEntity(StandardEntity e) {
        this.world.removeEntity(e.getID());
    }

    public void removeEntity(EntityID id) {
        this.world.removeEntity(id);
    }

    public void removeAllEntities() {
        this.world.removeAllEntities();
    }

	private Collection<EntityID> convertToID(Collection<StandardEntity> entities) {
		return entities.stream().map(StandardEntity::getID).collect(Collectors.toList());
	}

	public void merge(ChangeSet changeSet) {
		this.world.merge(changeSet);
	}
}
