package adf.agent.precompute;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;
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

	private PreDatas datas;

	public PrecomputeData()
	{
		this(DEFAULT_FILE_NAME);
	}

	public PrecomputeData(String name)
	{
		this.fileName = name;
		this.init();
	}

	private PrecomputeData(String name, PreDatas precomputeDatas)
	{
		this.fileName = name;
		this.datas = precomputeDatas;
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
		return new PrecomputeData(this.fileName, this.datas.copy());
	}

	private void init()
	{
		this.datas = this.read(this.fileName);
		if (this.datas == null)
		{
			this.datas = new PreDatas();
		}
	}

	private PreDatas read(String name)
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
			PreDatas ds = om.readValue(binary, PreDatas.class);
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
				if (!PRECOMP_DATA_DIR.mkdir()) return false;
			}
			ObjectMapper om = new ObjectMapper(new MessagePackFactory());
			byte[] binary = om.writeValueAsBytes(this.datas);
			FileOutputStream fos = new FileOutputStream(new File(PRECOMP_DATA_DIR, this.fileName));
			fos.write(binary);
			fos.close();
			return true;
		}
		catch (IOException e)
		{
			return false;
		}
	}

    /*public String getFileName() {
				return this.fileName;
    }*/

	public Integer setInteger(String name, int value)
	{
		return this.datas.intValues.put(name, value);
	}

	public Double setDouble(String name, double value)
	{
		return this.datas.doubleValues.put(name, value);
	}

	public Boolean setBoolean(String name, boolean value) {
		int f = this.datas.intValues.put(name, value ? 1 : 0);
		return f > 0 ? Boolean.TRUE : Boolean.FALSE;
	}

	public String setString(String name, String value)
	{
		return this.datas.stringValues.put(name, value);
	}

	public EntityID setEntityID(String name, EntityID value) {
		Integer id = this.datas.idValues.put(name, value.getValue());
		return id == null ? null : new EntityID(id);
	}

	public List<Integer> setIntegerList(String name, List<Integer> list)
	{
		return this.datas.intLists.put(name, list);
	}

	public List<Double> setDoubleList(String name, List<Double> list)
	{
		return this.datas.doubleLists.put(name, list);
	}

	public List<String> setStringList(String name, List<String> list)
	{
		return this.datas.stringLists.put(name, list);
	}

	public List<EntityID> setEntityIDList(String name, List<EntityID> list)
	{
		List<Integer> cvtList = new ArrayList<>();
		for (EntityID id : list)
		{
			cvtList.add(id.getValue());
		}
		cvtList = this.datas.idLists.put(name, cvtList);
		return cvtList == null ? null : cvtList.stream().map(EntityID::new).collect(Collectors.toList());
	}

	public boolean setReady(boolean isReady)
	{
		return (this.datas.isReady = isReady);
	}

	public Integer getInteger(String name)
	{
		return this.datas.intValues.get(name);
	}

	public Double getDouble(String name)
	{
		return this.datas.doubleValues.get(name);
	}

    public Boolean getBoolean(String name) {
        int f = this.datas.intValues.get(name);
        return f > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

	public String getString(String name)
	{
		return this.datas.stringValues.get(name);
	}

	public EntityID getEntityID(String name)
	{
		Integer id = this.datas.idValues.get(name);
		return id == null ? null : new EntityID(id);
	}

	public List<Integer> getIntegerList(String name)
	{
		return this.datas.intLists.get(name);
	}

	public List<Double> getDoubleList(String name)
	{
		return this.datas.doubleLists.get(name);
	}

	public List<String> getStringList(String name)
	{
		return this.datas.stringLists.get(name);
	}

	public List<EntityID> getEntityIDList(String name)
	{
		List<Integer> cvtList = this.datas.idLists.get(name);
		return cvtList == null ? null : cvtList.stream().map(EntityID::new).collect(Collectors.toList());
	}

	public boolean isReady()
	{
		return this.datas.isReady;
	}
}
