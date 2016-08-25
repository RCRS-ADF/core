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

	private Map<Integer, Map<EntityID, StandardEntity>> rollbackAddInfo;
	private Map<Integer, Map<EntityID, StandardEntity>> rollbackChangeInfo;
	private Map<EntityID, Map<String, Object>> rollbackCache;
	private boolean runRollback;

    public WorldInfo(StandardWorldModel world) {
		this.setWorld(world);
        this.time = -1;
		this.rollbackChangeInfo = new HashMap<>();
		this.rollbackAddInfo = new HashMap<>();
        this.runRollback = Boolean.FALSE;
        this.rollbackCache = new HashMap<>();
	}

	// agent init ///////////////////////////////////////////////////////////////////////////////////////////////

	public void indexClass(StandardEntityURN... urns) {
		this.world.indexClass(urns);
	}

	public void index() {
		this.world.index();
	}

    public WorldInfo requestRollback() {
        this.runRollback = true;
        return this;
    }

    // get Flag ////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean needRollback() {
        return this.runRollback;
    }

	// getEntity ///////////////////////////////////////////////////////////////////////////////////////////////////////

	public StandardEntity getEntity(EntityID id) {
		return this.world.getEntity(id);
	}

	public StandardEntity getEntity(int targetTime, StandardEntity entity) {
		return this.getEntity(targetTime, entity.getID());
	}

	public StandardEntity getEntity(int targetTime, EntityID entityID) {
		StandardEntity result = this.getEntity(entityID);
		if(targetTime <= 0) {
			targetTime = this.time + targetTime;
		}

		for(int i = this.time - 1; i >= targetTime; i--) {
			Map<EntityID, StandardEntity> info = this.rollbackAddInfo.get(i);
			if(info != null) {
				StandardEntity entity = info.get(entityID);
				if (entity != null) {
					if(i > targetTime) return null;
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
			info = this.rollbackChangeInfo.get(i);
			if(info != null) {
				StandardEntity entity = info.get(entityID);
				if(entity != null) {
					result = entity;
				}
			}
		}
		return result;
	}

	// getEntityOfType /////////////////////////////////////////////////////////////////////////////////////////////////

	public Collection<StandardEntity> getEntitiesOfType(StandardEntityURN urn) {
		return this.world.getEntitiesOfType(urn);
	}

	public Collection<StandardEntity> getEntitiesOfType(StandardEntityURN... urns) {
		return this.world.getEntitiesOfType(urns);
	}

	public Collection<StandardEntity> getEntitiesOfType(int targetTime, StandardEntityURN urn) {
		return this.world.getEntitiesOfType(urn)
                .stream()
                .map(entity -> this.getEntity(targetTime, entity))
                .collect(Collectors.toCollection(HashSet::new));
	}

	public Collection<StandardEntity> getEntitiesOfType(int targetTime, StandardEntityURN... urns) {
        return this.world.getEntitiesOfType(urns)
                .stream()
                .map(entity -> this.getEntity(targetTime, entity))
                .collect(Collectors.toCollection(HashSet::new));
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

	// getObjectsInRange ///////////////////////////////////////////////////////////////////////////////////////////////

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

	public Collection<StandardEntity> getObjectsInRange(int targetTime, EntityID entity, int range, boolean ignoreHuman) {
		return this.getObjectsInRange(targetTime, this.getEntity(entity), range, ignoreHuman);
	}

	public Collection<StandardEntity> getObjectsInRange(int targetTime, StandardEntity entity, int range) {
		return this.getObjectsInRange(targetTime, entity, range, false);
	}

	public Collection<StandardEntity> getObjectsInRange(int targetTime, StandardEntity entity, int range, boolean ignoreHuman) {
		if (entity == null) return new HashSet<>();

		Pair<Integer, Integer> location = this.getLocation(entity);
		if (location == null) return new HashSet<>();

		return this.getObjectsInRange(targetTime, location.first(), location.second(), range, ignoreHuman);
	}

	public Collection<StandardEntity> getObjectsInRange(int targetTime, int x, int y, int range) {
		return this.getObjectsInRange(targetTime, x, y, range, false);
	}

	public Collection<StandardEntity> getObjectsInRange(int targetTime, int x, int y, int range, boolean ignoreHuman) {
		return this.getObjectsInRectangle(targetTime, x - range, y - range, x + range, y + range, ignoreHuman);
	}

	public Collection<StandardEntity> getObjectsInRectangle(int targetTime, int x1, int y1, int x2, int y2) {
		return this.getObjectsInRectangle(targetTime, x1, y1, x2, y2, false);
	}

	public Collection<StandardEntity> getObjectsInRectangle(int targetTime, int x1, int y1, int x2, int y2, boolean ignoreHuman) {
		Collection<StandardEntity> result = new HashSet<>();
        if (ignoreHuman) {
            result.addAll(
                    this.world.getObjectsInRectangle(x1, y1, x2, y2).stream()
                            .map(target -> this.getEntity(targetTime, target))
                            .collect(Collectors.toList())
            );
        } else {
            result.addAll(
                    this.world.getObjectsInRectangle(x1, y1, x2, y2).stream()
                            .filter(target -> target instanceof Area)
                            .map(target -> this.getEntity(targetTime, target))
                            .collect(Collectors.toList())
            );
            Map<EntityID, StandardEntity> info = this.rollbackChangeInfo.get(targetTime);
            info.values().stream()
                    .filter(target -> target instanceof Human && result.contains(this.getPosition((Human) target)))
                    .forEach(result::add);
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

	public Collection<EntityID> getObjectIDsInRange(int targetTime, EntityID entity, int range, boolean ignoreHuman) {
		return this.convertToID(this.getObjectsInRange(targetTime, entity, range, ignoreHuman));
	}

	public Collection<EntityID> getObjectIDsInRange(int targetTime, StandardEntity entity, int range) {
		return this.convertToID(this.getObjectsInRange(targetTime, entity, range));
	}

	public Collection<EntityID> getObjectIDsInRange(int targetTime, StandardEntity entity, int range, boolean ignoreHuman) {
		return this.convertToID(this.getObjectsInRange(targetTime, entity, range, ignoreHuman));
	}

	public Collection<EntityID> getObjectIDsInRange(int targetTime, int x, int y, int range) {
		return this.convertToID(this.getObjectsInRange(targetTime, x,y,range));
	}

	public Collection<EntityID> getObjectIDsInRange(int targetTime, int x, int y, int range, boolean ignoreHuman) {
		return this.convertToID(this.getObjectsInRange(targetTime, x,y,range, ignoreHuman));
	}

	public Collection<EntityID> getObjectIDsInRectangle(int targetTime, int x1, int y1, int x2, int y2) {
		return this.convertToID(this.getObjectsInRectangle(targetTime, x1, y1, x2, y2));
	}

	public Collection<EntityID> getObjectIDsInRectangle(int targetTime, int x1, int y1, int x2, int y2, boolean ignoreHuman) {
		return this.convertToID(this.getObjectsInRectangle(targetTime, x1, y1, x2, y2, ignoreHuman));
	}

	// getAllEntities //////////////////////////////////////////////////////////////////////////////////////////////////

	public Collection<StandardEntity> getAllEntities() {
		return this.world.getAllEntities();
	}

	public Collection<StandardEntity> getAllEntities(int targetTime) {
		return this.world.getAllEntities()
                .stream()
                .map(entity -> this.getEntity(targetTime, entity))
                .collect(Collectors.toCollection(HashSet::new));
	}

	// getChangeInfo ///////////////////////////////////////////////////////////////////////////////////////////////////

    public ChangeSet getChanged() {
        return this.changed;
    }

	public Map<EntityID, StandardEntity> getChangeEntities(int time) {
		return this.rollbackChangeInfo.get(time);
	}

	public Map<EntityID, StandardEntity> getAddEntities(int time) {
		return this.rollbackAddInfo.get(time);
	}

	public Map<EntityID, StandardEntity> getRemoveEntities(int time) {
		return this.rollbackChangeInfo.get(time);
	}

	// other //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Collection<Building> getFireBuildings() {
		Set<Building> fireBuildings = new HashSet<>();
		for(StandardEntity entity : this.getEntitiesOfType(BUILDING, GAS_STATION, AMBULANCE_CENTRE, FIRE_STATION, POLICE_OFFICE)) {
			Building building = (Building)entity;
			if(building.isOnFire()) fireBuildings.add(building);
		}
		return fireBuildings;
	}

	public Collection<EntityID> getFireBuildingIDs() {
		return this.getFireBuildings().stream().map(Building::getID).collect(Collectors.toList());
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

	public Collection<Human> getBuriedHumans(Building building) {
		return this.getBuriedHumans(building.getID());
	}

	public Collection<Human> getBuriedHumans(EntityID entityID) {
		Collection<Human> result = new HashSet<>();
		for(StandardEntity entity : this.getEntitiesOfType(CIVILIAN, AMBULANCE_TEAM, FIRE_BRIGADE, POLICE_FORCE)) {
			Human human = (Human)entity;
			if(this.getPosition(human).getID().getValue() == entityID.getValue()) {
				if(human.isBuriednessDefined() && human.getBuriedness() > 0) result.add(human);
			}
		}
		return result;
	}

	public Collection<Blockade> getBlockades(EntityID entityID) {
	    StandardEntity entity = this.getEntity(entityID);
        if(entity instanceof Road) {
            return this.getBlockades((Road)entity);
        }
        return null;
    }

	public Collection<Blockade> getBlockades(Road road) {
	    return road.getBlockades().stream().map(entityID -> (Blockade)this.getEntity(entityID)).collect(Collectors.toSet());
    }

	// getPosition /////////////////////////////////////////////////////////////////////////////////////////////////////

    public StandardEntity getPosition(Human human) {
        return human.getPosition(this.world);
    }

    public StandardEntity getPosition(EntityID entityID) {
        StandardEntity entity = this.getEntity(entityID);
        if(entity instanceof Human) return this.getPosition((Human)entity);
        return entity.getStandardURN() == BLOCKADE ? this.getEntity(((Blockade)entity).getPosition()) : null;
    }

    public StandardEntity getPosition(int targetTime, Human entity) {
        return this.getEntity(targetTime, entity.getPosition(this.world));
    }

    public StandardEntity getPosition(int targetTime, EntityID entityID) {
        StandardEntity entity = this.getEntity(entityID);
        return (entity instanceof Human) ? this.getEntity(targetTime, this.getPosition((Human)entity)) : null;
    }

    // getLocation /////////////////////////////////////////////////////////////////////////////////////////////////////

    public Pair<Integer, Integer> getLocation(StandardEntity entity) {
        return entity.getLocation(this.world);
    }

    public Pair<Integer, Integer> getLocation(EntityID entityID) {
        return this.getLocation(this.getEntity(entityID));
    }

	public Pair<Integer, Integer> getLocation(int targetTime, StandardEntity entity) {
		StandardEntity target = this.getEntity(targetTime, entity);
		return target.getLocation(this.world);
	}

	public Pair<Integer, Integer> getLocation(int targetTime, EntityID entityID) {
		StandardEntity target = this.getEntity(targetTime, entityID);
		return target.getLocation(this.world);
	}

	// getDistance /////////////////////////////////////////////////////////////////////////////////////////////////////

    public int getDistance(EntityID first, EntityID second) {
        return this.world.getDistance(first, second);
    }

    public int getDistance(StandardEntity first, StandardEntity second) {
        return this.world.getDistance(first, second);
    }

    public int getDistance(int targetTime, EntityID first, EntityID second) {
		return this.world.getDistance(this.getEntity(targetTime, first), this.getEntity(targetTime, second));
	}

	public int getDistance(int targetTime, StandardEntity first, StandardEntity second) {
		return this.world.getDistance(this.getEntity(targetTime, first), this.getEntity(targetTime, second));
	}

	// getWorldBounds //////////////////////////////////////////////////////////////////////////////////////////////////

	public Rectangle2D getBounds() {
		return this.world.getBounds();
	}

	public Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> getWorldBounds() {
		return this.world.getWorldBounds();
	}

	// addEntity //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void addEntity(Entity entity) {
        entity.addEntityListener(new ChangeListener());
        this.world.addEntity(entity);
    }

    public void addEntity(Entity entity,
            Class<? extends EntityListener> listener,
            Class<? extends EntityListener>... otherListeners) {
		try {
			entity.addEntityListener(listener.newInstance());
			for(Class<? extends EntityListener> other : otherListeners) {
				entity.addEntityListener(other.newInstance());
			}
		} catch (InstantiationException | IllegalAccessException exception) {
			exception.printStackTrace();
		}
		this.addEntity(entity);
	}

    public void addEntities(Collection<? extends Entity> entities) {
		entities.forEach(this::addEntity);
    }

    public void addEntities(Collection<? extends Entity> entities,
            Class<? extends EntityListener> listener,
            Class<? extends EntityListener>... otherListeners) {
        entities.forEach(entity -> {
            this.addEntity(entity, listener, otherListeners);
        });
    }

    // registerListener ////////////////////////////////////////////////////////////////////////////////////////////////

    public void registerEntityListener(Class<? extends EntityListener> listener) {
        for (StandardEntity entity : this.getAllEntities()) {
            try {
                entity.addEntityListener(listener.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void registerWorldListener(Class<? extends WorldModelListener<StandardEntity>> listener) {
        try {
            this.world.addWorldModelListener(listener.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // removeEntity // marge ///////////////////////////////////////////////////////////////////////////////////////////

    public void removeEntity(StandardEntity e) {
        this.world.removeEntity(e.getID());
    }

    public void removeEntity(EntityID id) {
        this.world.removeEntity(id);
    }

    public void removeAllEntities() {
        this.world.removeAllEntities();
    }

	public void merge(ChangeSet changeSet) {
		this.world.merge(changeSet);
	}

	// system //////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Iterator<StandardEntity> iterator() {
        return this.world.iterator();
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

    private Collection<EntityID> convertToID(Collection<StandardEntity> entities) {
        return entities.stream().map(StandardEntity::getID).collect(Collectors.toList());
    }

    // rollback ////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void registerListener() {
        if(this.needRollback()) {
            this.world.addWorldModelListener(new RollbackListener());
            for (StandardEntity entity : this.getAllEntities()) {
                entity.addEntityListener(new ChangeListener());
            }
        }
    }

	public void setRollbackBase(int time) {
		this.setTime(time);
		this.rollbackAddInfo.put(time, new HashMap<>());
		this.rollbackChangeInfo.put(time, new HashMap<>());
        this.rollbackCache.clear();
	}

	public void createRollbackInfo(){
        Map<EntityID, StandardEntity> info = this.rollbackChangeInfo.get(this.time);
	    EntityListener listener = new ChangeListener();
	    for(EntityID id : this.rollbackCache.keySet()) {
	        if(info.get(id) != null) continue;

	        StandardEntity entity = this.getEntity(id);
	        Map<String, Object> cache = this.rollbackCache.get(id);
            StandardEntityURN urn = entity.getStandardURN();
            if(BUILDING == urn) info.put(id, this.createRollbackBuilding(entity, cache, listener));
            else if(ROAD == urn) info.put(id, this.createRollbackRoad(entity, cache, listener));
            else if(BLOCKADE == urn) info.put(id, this.createRollbackBlockade(entity, cache, listener));
            else if(CIVILIAN == urn) info.put(id, this.createRollbackHuman(entity, cache, listener));
            else if(AMBULANCE_TEAM == urn) info.put(id, this.createRollbackHuman(entity, cache, listener));
            else if(POLICE_FORCE == urn) info.put(id, this.createRollbackHuman(entity, cache, listener));
            else if(FIRE_BRIGADE == urn) info.put(id, this.createRollbackFireBrigade(entity, cache, listener));
            else if(HYDRANT == urn) info.put(id, this.createRollbackRoad(entity, cache, listener));
            else if(REFUGE == urn) info.put(id, this.createRollbackBuilding(entity, cache, listener));
            else if(GAS_STATION == urn) info.put(id, this.createRollbackBuilding(entity, cache, listener));
            else if(AMBULANCE_CENTRE == urn) info.put(id, this.createRollbackBuilding(entity, cache, listener));
            else if(FIRE_STATION == urn) info.put(id, this.createRollbackBuilding(entity, cache, listener));
            else if(POLICE_OFFICE == urn) info.put(id, this.createRollbackBuilding(entity, cache, listener));
            else if(WORLD == urn) info.put(id, this.createRollbackWorld(entity, cache, listener));
        }
        this.rollbackChangeInfo.put(this.time, info);
    }

    private Blockade createRollbackBlockade(StandardEntity entity, Map<String, Object> cache, EntityListener listener) {
        Blockade copy = (Blockade)entity.copy();
        copy.removeEntityListener(listener);
        for(String urn : cache.keySet()) {
            Object value = cache.get(urn);
            StandardPropertyURN type = StandardPropertyURN.fromString(urn);
            boolean isDefined = value != null;
            switch (type) {
                case X:
                    if(isDefined) copy.setX((Integer)value);
                    else copy.undefineX();
                    break;
                case Y:
                    if(isDefined) copy.setY((Integer)value);
                    else copy.undefineY();
                    break;
                case POSITION:
                    if(isDefined) copy.setPosition((EntityID) value);
                    else copy.undefinePosition();
                    break;
                case APEXES:
                    if(isDefined) copy.setApexes((int[])value);
                    else copy.undefineApexes();
                    break;
                case REPAIR_COST:
                    if(isDefined) copy.setRepairCost((Integer)value);
                    else copy.undefineRepairCost();
                    break;
                default:
            }
        }
        return copy;
    }

    @SuppressWarnings("unchecked")
    private Building createRollbackBuilding(StandardEntity entity, Map<String, Object> cache, EntityListener listener) {
        Building copy = (Building)entity.copy();
        copy.removeEntityListener(listener);
        for(String urn : cache.keySet()) {
            Object value = cache.get(urn);
            StandardPropertyURN type = StandardPropertyURN.fromString(urn);
            boolean isDefined = value != null;
            switch (type) {
                case X:
                    if(isDefined) copy.setX((Integer)value);
                    else copy.undefineX();
                    break;
                case Y:
                    if(isDefined) copy.setY((Integer)value);
                    else copy.undefineY();
                    break;
                case EDGES:
                    if(isDefined) copy.setEdges((List<Edge>)value);
                    else copy.undefineEdges();
                case BLOCKADES:
                    if(isDefined) copy.setBlockades((List<EntityID>)value);
                    else copy.undefineBlockades();
                case FLOORS:
                    if(isDefined) copy.setFloors((Integer)value);
                    else copy.undefineFloors();
                case IGNITION:
                    if(isDefined) copy.setIgnition((Boolean)value);
                    else copy.undefineIgnition();
                case FIERYNESS:
                    if(isDefined) copy.setFieryness((Integer)value);
                    else copy.undefineFieryness();
                case BROKENNESS:
                    if(isDefined) copy.setBrokenness((Integer)value);
                    else copy.undefineBrokenness();
                case BUILDING_CODE:
                    if(isDefined) copy.setBuildingCode((Integer)value);
                    else copy.undefineBuildingCode();
                case BUILDING_ATTRIBUTES:
                    if(isDefined) copy.setBuildingAttributes((Integer)value);
                    else copy.undefineBuildingAttributes();
                case BUILDING_AREA_GROUND:
                    if(isDefined) copy.setGroundArea((Integer)value);
                    else copy.undefineGroundArea();
                case BUILDING_AREA_TOTAL:
                    if(isDefined) copy.setTotalArea((Integer)value);
                    else copy.undefineTotalArea();
                case TEMPERATURE:
                    if(isDefined) copy.setTemperature((Integer)value);
                    else copy.undefineTemperature();
                case IMPORTANCE:
                    if(isDefined) copy.setImportance((Integer)value);
                    else copy.undefineImportance();
                default:
            }
        }
        return copy;
    }

    @SuppressWarnings("unchecked")
    private Road createRollbackRoad(StandardEntity entity, Map<String, Object> cache, EntityListener listener) {
        Road copy = (Road)entity.copy();
        copy.removeEntityListener(listener);
        for(String urn : cache.keySet()) {
            Object value = cache.get(urn);
            StandardPropertyURN type = StandardPropertyURN.fromString(urn);
            boolean isDefined = value != null;
            switch (type) {
                case X:
                    if(isDefined) copy.setX((Integer)value);
                    else copy.undefineX();
                    break;
                case Y:
                    if(isDefined) copy.setY((Integer)value);
                    else copy.undefineY();
                    break;
                case EDGES:
                    if(isDefined) copy.setEdges((List<Edge>)value);
                    else copy.undefineEdges();
                case BLOCKADES:
                    if(isDefined) copy.setBlockades((List<EntityID>)value);
                    else copy.undefineBlockades();
                default:
            }
        }
        return copy;
    }

    private World createRollbackWorld(StandardEntity entity, Map<String, Object> cache, EntityListener listener) {
        World copy = (World) entity.copy();
        copy.removeEntityListener(listener);
        for(String urn : cache.keySet()) {
            Object value = cache.get(urn);
            StandardPropertyURN type = StandardPropertyURN.fromString(urn);
            boolean isDefined = value != null;
            switch (type) {
                case START_TIME:
                    if(isDefined) copy.setStartTime((Integer)value);
                    else copy.undefineStartTime();
                    break;
                case LONGITUDE:
                    if(isDefined) copy.setLongitude((Integer)value);
                    else copy.undefineLongitude();
                    break;
                case LATITUDE:
                    if(isDefined) copy.setLatitude((Integer)value);
                    else copy.undefineLatitude();
                    break;
                case WIND_FORCE:
                    if(isDefined) copy.setWindForce((Integer)value);
                    else copy.undefineWindForce();
                    break;
                case WIND_DIRECTION:
                    if(isDefined) copy.setWindDirection((Integer)value);
                    else copy.undefineWindDirection();
                    break;
                default:
            }
        }
        return copy;
    }

    private Human createRollbackHuman(StandardEntity entity, Map<String, Object> cache, EntityListener listener) {
        Human copy = (Human)entity.copy();
        copy.removeEntityListener(listener);
        for(String urn : cache.keySet()) {
            Object value = cache.get(urn);
            StandardPropertyURN type = StandardPropertyURN.fromString(urn);
            boolean isDefined = value != null;
            switch (type) {
                case X:
                    if(isDefined) copy.setX((Integer)value);
                    else copy.undefineX();
                    break;
                case Y:
                    if(isDefined) copy.setY((Integer)value);
                    else copy.undefineY();
                    break;
                case POSITION:
                    if(isDefined) copy.setPosition((EntityID) value);
                    else copy.undefinePosition();
                    break;
                case POSITION_HISTORY:
                    if(isDefined) copy.setPositionHistory((int[])value);
                    else copy.undefinePositionHistory();
                    break;
                case DIRECTION:
                    if(isDefined) copy.setDirection((Integer)value);
                    else copy.undefineDirection();
                    break;
                case STAMINA:
                    if(isDefined) copy.setStamina((Integer)value);
                    else copy.undefineStamina();
                    break;
                case HP:
                    if(isDefined) copy.setHP((Integer)value);
                    else copy.undefineHP();
                    break;
                case DAMAGE:
                    if(isDefined) copy.setDamage((Integer)value);
                    else copy.undefineDamage();
                    break;
                case BURIEDNESS:
                    if(isDefined) copy.setBuriedness((Integer)value);
                    else copy.undefineBuriedness();
                    break;
                case TRAVEL_DISTANCE:
                    if(isDefined) copy.setTravelDistance((Integer)value);
                    else copy.undefineTravelDistance();
                    break;
                default:
            }
        }
        return copy;
    }

    private FireBrigade createRollbackFireBrigade(StandardEntity entity, Map<String, Object> cache, EntityListener listener) {
        FireBrigade copy = (FireBrigade) entity.copy();
        copy.removeEntityListener(listener);
        for(String urn : cache.keySet()) {
            Object value = cache.get(urn);
            StandardPropertyURN type = StandardPropertyURN.fromString(urn);
            boolean isDefined = value != null;
            switch (type) {
                case X:
                    if(isDefined) copy.setX((Integer)value);
                    else copy.undefineX();
                    break;
                case Y:
                    if(isDefined) copy.setY((Integer)value);
                    else copy.undefineY();
                    break;
                case POSITION:
                    if(isDefined) copy.setPosition((EntityID) value);
                    else copy.undefinePosition();
                    break;
                case POSITION_HISTORY:
                    if(isDefined) copy.setPositionHistory((int[])value);
                    else copy.undefinePositionHistory();
                    break;
                case DIRECTION:
                    if(isDefined) copy.setDirection((Integer)value);
                    else copy.undefineDirection();
                    break;
                case STAMINA:
                    if(isDefined) copy.setStamina((Integer)value);
                    else copy.undefineStamina();
                    break;
                case HP:
                    if(isDefined) copy.setHP((Integer)value);
                    else copy.undefineHP();
                    break;
                case DAMAGE:
                    if(isDefined) copy.setDamage((Integer)value);
                    else copy.undefineDamage();
                    break;
                case BURIEDNESS:
                    if(isDefined) copy.setBuriedness((Integer)value);
                    else copy.undefineBuriedness();
                    break;
                case TRAVEL_DISTANCE:
                    if(isDefined) copy.setTravelDistance((Integer)value);
                    else copy.undefineTravelDistance();
                    break;
                case WATER_QUANTITY:
                    if(isDefined) copy.setWater((Integer)value);
                    else copy.undefineWater();
                    break;
                default:
            }
        }
        return copy;
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
            Map<EntityID, StandardEntity> removeInfo = rollbackChangeInfo.get(time);
            removeInfo.put(e.getID(), (StandardEntity)e.copy());
            rollbackChangeInfo.put(time, removeInfo);
        }
    }

    private class ChangeListener implements EntityListener {
        @Override
        public void propertyChanged(Entity entity, Property property, Object oldValue, Object newValue) {
            EntityID entityID = entity.getID();
            Map<String, Object> cache = rollbackCache.get(entityID);
            if(cache == null) cache = new HashMap<>();
            cache.put(property.getURN(), oldValue);
            rollbackCache.put(entityID, cache);
        }
    }
}
