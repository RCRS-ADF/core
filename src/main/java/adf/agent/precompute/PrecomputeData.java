package adf.agent.precompute;

import adf.agent.info.WorldInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import rescuecore2.standard.entities.StandardEntityURN;
import rescuecore2.worldmodel.EntityID;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class PrecomputeData
{

	public static final String DEFAULT_FILE_NAME = "data.bin";
	public static final File PRECOMP_DATA_DIR = new File("precomp_data");

	private String fileName;

	private PreData data;

	public PrecomputeData()
	{
		this(DEFAULT_FILE_NAME);
	}

	public PrecomputeData(String name)
	{
		this.fileName = name;
		this.init();
	}

	private PrecomputeData(String name, PreData precomputeDatas)
	{
		this.fileName = name;
		this.data = precomputeDatas;
	}

	public static void removeData(String name)
	{
		if (!PRECOMP_DATA_DIR.exists())
		{
			return;
		}
		File file = new File(PRECOMP_DATA_DIR, name);
		if (!file.exists())
		{
			return;
		}
		file.delete();
	}

	public static void removeData()
	{
		removeData(DEFAULT_FILE_NAME);
	}

	public PrecomputeData copy()
	{
		return new PrecomputeData(this.fileName, this.data.copy());
	}

	private void init()
	{
		this.data = this.read(this.fileName);
		if (this.data == null)
		{
			this.data = new PreData();
		}
	}

	private PreData read(String name)
	{
		try
		{
			if (!PRECOMP_DATA_DIR.exists())
			{
				if (!PRECOMP_DATA_DIR.mkdir()) return null;
			}
			File readFile = new File(PRECOMP_DATA_DIR, name);
			if (!readFile.exists())
			{
				return null;
			}
			FileInputStream fis = new FileInputStream(readFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			byte[] binary = new byte[1024];
			while (true)
			{
				int len = bis.read(binary);
				if (len < 0)
				{
					break;
				}
				bout.write(binary, 0, len);
			}
			binary = bout.toByteArray();
			ObjectMapper om = new ObjectMapper(new MessagePackFactory());
			PreData ds = om.readValue(binary, PreData.class);
			bis.close();
			fis.close();
			return ds;
		}
		catch (IOException e)
		{
			return null;
		}
	}

	public boolean write()
	{
		try
		{
			if (!PRECOMP_DATA_DIR.exists())
			{
				if (!PRECOMP_DATA_DIR.mkdir())
				{ return false; }
			}
			ObjectMapper om = new ObjectMapper(new MessagePackFactory());
			byte[] binary = om.writeValueAsBytes(this.data);
			FileOutputStream fos = new FileOutputStream(new File(PRECOMP_DATA_DIR, this.fileName));
			fos.write(binary);
			fos.close();
			return true;
		}
		catch (IOException e)
		{
            e.printStackTrace();;
			return false;
		}
	}

    /*public String getFileName() {
				return this.fileName;
    }*/

	public Integer setInteger(String name, int value)
	{
		return this.data.intValues.put(name, value);
	}

	public Double setDouble(String name, double value)
	{
		return this.data.doubleValues.put(name, value);
	}

	public Boolean setBoolean(String name, boolean value) {
		return this.data.boolValues.put(name, value);
	}

	public String setString(String name, String value)
	{
		return this.data.stringValues.put(name, value);
	}

	public EntityID setEntityID(String name, EntityID value) {
		Integer id = this.data.idValues.put(name, value.getValue());
		return id == null ? null : new EntityID(id);
	}

	public List<Integer> setIntegerList(String name, List<Integer> list)
	{
		return this.data.intLists.put(name, list);
	}

	public List<Double> setDoubleList(String name, List<Double> list)
	{
		return this.data.doubleLists.put(name, list);
	}

	public List<String> setStringList(String name, List<String> list)
	{
		return this.data.stringLists.put(name, list);
	}

	public List<EntityID> setEntityIDList(String name, List<EntityID> list)
	{
		List<Integer> cvtList = new ArrayList<>();
		for (EntityID id : list)
		{
			cvtList.add(id.getValue());
		}
		cvtList = this.data.idLists.put(name, cvtList);
		return cvtList == null ? null : cvtList.stream().map(EntityID::new).collect(Collectors.toList());
	}

	public List<Boolean> setBooleanList(String name, List<Boolean> list)
	{
		return this.data.boolLists.put(name, list);
	}

	public boolean setReady(boolean isReady, WorldInfo worldInfo)
	{
		this.data.isReady = isReady;
		this.data.readyID = makeReadyID(worldInfo);
		return (this.data.isReady && this.data.readyID.equals(this.makeReadyID(worldInfo)));
	}

	public Integer getInteger(String name)
	{
		return this.data.intValues.get(name);
	}

	public Double getDouble(String name)
	{
		return this.data.doubleValues.get(name);
	}

	public Boolean getBoolean(String name) {
		return this.data.boolValues.get(name);
	}

	public String getString(String name)
	{
		return this.data.stringValues.get(name);
	}

	public EntityID getEntityID(String name)
	{
		Integer id = this.data.idValues.get(name);
		return id == null ? null : new EntityID(id);
	}

	public List<Integer> getIntegerList(String name)
	{
		return this.data.intLists.get(name);
	}

	public List<Double> getDoubleList(String name)
	{
		return this.data.doubleLists.get(name);
	}

	public List<String> getStringList(String name)
	{
		return this.data.stringLists.get(name);
	}

	public List<EntityID> getEntityIDList(String name)
	{
		List<Integer> cvtList = this.data.idLists.get(name);
		return cvtList == null ? null : cvtList.stream().map(EntityID::new).collect(Collectors.toList());
	}

	public List<Boolean> getBooleanList(String name)
	{
		return this.data.boolLists.get(name);
	}

	public boolean isReady(WorldInfo worldInfo)
	{
		return (this.data.isReady && this.data.readyID.equals(this.makeReadyID(worldInfo)));
	}

	private String makeReadyID(WorldInfo worldInfo)
	{
		return "" + worldInfo.getBounds().getX() + "" + worldInfo.getBounds().getY();
	}
}
