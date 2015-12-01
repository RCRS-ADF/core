package adf.agent.info;

import adf.util.WorldUtil;
import rescuecore2.misc.Pair;
import rescuecore2.standard.entities.Human;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.standard.entities.StandardEntityURN;
import rescuecore2.standard.entities.StandardWorldModel;
import rescuecore2.worldmodel.ChangeSet;
import rescuecore2.worldmodel.Entity;
import rescuecore2.worldmodel.EntityID;
import rescuecore2.worldmodel.WorldModel;

import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Iterator;

public class WorldInfo implements Iterable<StandardEntity> {
	private StandardWorldModel world;
	private ChangeSet changed;

	public WorldInfo(StandardWorldModel world) {
		this.setWorld(world);
	}

	public void setWorld(StandardWorldModel world)
	{
		this.world = world;
	}

	public StandardWorldModel getRawWorld() {
		return this.world;
	}

	public void setChanged(ChangeSet changed) {
		this.changed = changed;
	}

	public ChangeSet getChanged() {
		return this.changed;
	}

	public Collection<EntityID> getObjectIDsInRange(EntityID entity, int range) {
		return WorldUtil.convertToID(this.world.getObjectsInRange(entity, range));
	}

	public Collection<EntityID> getObjectIDsInRange(StandardEntity entity, int range) {
		return WorldUtil.convertToID(this.world.getObjectsInRange(entity, range));
	}

	public Collection<EntityID> getObjectIDsInRange(int x, int y, int range) {
		return WorldUtil.convertToID(this.world.getObjectsInRange(x,y,range));
	}

	public Collection<EntityID> getObjectIDsInRectangle(int x1, int y1, int x2, int y2) {
		return WorldUtil.convertToID(this.world.getObjectsInRectangle(x1, y1, x2, y2));
	}

	public Collection<EntityID> getEntityIDsOfType(StandardEntityURN urn) {
		return WorldUtil.convertToID(this.world.getEntitiesOfType(urn));
	}

	public Collection<EntityID> getEntityIDsOfType(StandardEntityURN... urns) {
		return WorldUtil.convertToID(this.world.getEntitiesOfType(urns));
	}

    public StandardEntity getPosition(Human entity) {
        return entity.getPosition(this.world);
    }

    public Pair<Integer, Integer> getLocation(StandardEntity entity) {
        return entity.getLocation(this.world);
    }


    //org

	public void merge(ChangeSet changeSet) {
		this.world.merge(changeSet);
	}

	public void indexClass(StandardEntityURN... urns) {
		this.world.indexClass(urns);
	}

	public void index() {
		this.world.index();
	}

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

	public Collection<StandardEntity> getEntitiesOfType(StandardEntityURN urn) {
		return this.world.getEntitiesOfType(urn);
	}

	public Collection<StandardEntity> getEntitiesOfType(StandardEntityURN... urns) {
		return this.world.getEntitiesOfType(urns);
	}

	public int getDistance(EntityID first, EntityID second) {
		return this.world.getDistance(first, second);
	}

	public int getDistance(StandardEntity first, StandardEntity second) {
		return this.world.getDistance(first, second);
	}

	public Rectangle2D getBounds() {
		return this.world.getBounds();
	}

	public Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> getWorldBounds() {
		return this.world.getWorldBounds();
	}

	public static StandardWorldModel createStandardWorldModel(WorldModel<? extends Entity> existing) {
		return StandardWorldModel.createStandardWorldModel(existing);
	}

    public StandardEntity getEntity(EntityID id) {
        return this.world.getEntity(id);
    }

    public Collection<StandardEntity> getAllEntities() {
        return this.world.getAllEntities();
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

	@Override
    public Iterator<StandardEntity> iterator() {
        return this.world.iterator();
    }
}
