package adf.agent.precompute;

import adf.agent.info.WorldInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import rescuecore2.standard.entities.StandardEntityURN;
import rescuecore2.worldmodel.EntityID;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

public final class PrecomputeData {

	public static final String DEFAULT_FILE_NAME = "data.bin";
	public static final File PRECOMP_DATA_DIR = new File("precomp_data");

	@Nonnull
	private String fileName;

	private PreData data;

	public PrecomputeData()
	{
		this(DEFAULT_FILE_NAME);
	}

	public PrecomputeData(@Nonnull String name)
	{
		this.fileName = name;
		this.init();
	}

	private PrecomputeData(@Nonnull String  name, @Nonnull PreData precomputeDatas)
	{
		this.fileName = name;
		this.data = precomputeDatas;
	}

	public static void removeData(@Nonnull String name)
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

	@Nonnull
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

	@CheckForNull
	private PreData read(@Nonnull String name)
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

	@Nullable
	public Integer setInteger(@Nonnull String name, int value)  {
	    StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if(stackTraceElements == null || stackTraceElements.length == 0) {
            return null;
        }
		String callClassName = stackTraceElements[2].getClassName();
		return this.data.intValues.put(callClassName + ":" + name, value);
	}

	@Nullable
	public Double setDouble(@Nonnull String name, double value) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if(stackTraceElements == null || stackTraceElements.length == 0) {
            return null;
        }
        String callClassName = stackTraceElements[2].getClassName();
		return this.data.doubleValues.put(callClassName + ":" + name, value);
	}

	@Nullable
	public Boolean setBoolean(@Nonnull String name, boolean value) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if(stackTraceElements == null || stackTraceElements.length == 0) {
            return null;
        }
        String callClassName = stackTraceElements[2].getClassName();
		return this.data.boolValues.put(callClassName + ":" + name, value);
	}

	@Nullable
	public String setString(@Nonnull String name, @Nonnull String value) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if(stackTraceElements == null || stackTraceElements.length == 0) {
            return null;
        }
        String callClassName = stackTraceElements[2].getClassName();
		return this.data.stringValues.put(callClassName + ":" + name, value);
	}

	@Nullable
	public EntityID setEntityID(@Nonnull String name, @Nonnull EntityID value) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if(stackTraceElements == null || stackTraceElements.length == 0) {
            return null;
        }
        String callClassName = stackTraceElements[2].getClassName();
		Integer id = this.data.idValues.put(callClassName + ":" + name, value.getValue());
		return id == null ? null : new EntityID(id);
	}

	@Nullable
	public List<Integer> setIntegerList(@Nonnull String name, @Nonnull List<Integer> list) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if(stackTraceElements == null || stackTraceElements.length == 0) {
            return null;
        }
        String callClassName = stackTraceElements[2].getClassName();
		return this.data.intLists.put(callClassName + ":" + name, list);
	}

	@Nullable
	public List<Double> setDoubleList(@Nonnull String name, @Nonnull List<Double> list) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if(stackTraceElements == null || stackTraceElements.length == 0) {
            return null;
        }
        String callClassName = stackTraceElements[2].getClassName();
		return this.data.doubleLists.put(callClassName + ":" + name, list);
	}

	@Nullable
	public List<String> setStringList(@Nonnull String name, @Nonnull List<String> list) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if(stackTraceElements == null || stackTraceElements.length == 0) {
            return null;
        }
        String callClassName = stackTraceElements[2].getClassName();
		return this.data.stringLists.put(callClassName + ":" + name, list);
	}

	@Nullable
	public List<EntityID> setEntityIDList(@Nonnull String name, @Nonnull List<EntityID> list) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if(stackTraceElements == null || stackTraceElements.length == 0) {
            return null;
        }
        String callClassName = stackTraceElements[2].getClassName();
		List<Integer> cvtList = new ArrayList<>();
		for (EntityID id : list)
		{
			cvtList.add(id.getValue());
		}
		cvtList = this.data.idLists.put(callClassName + ":" + name, cvtList);
		return cvtList == null ? null : cvtList.stream().map(EntityID::new).collect(Collectors.toList());
	}

	@Nullable
	public List<Boolean> setBooleanList(@Nonnull String name, @Nonnull List<Boolean> list) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if(stackTraceElements == null || stackTraceElements.length == 0) {
            return null;
        }
        String callClassName = stackTraceElements[2].getClassName();
		return this.data.boolLists.put(callClassName + ":" + name, list);
	}

	public boolean setReady(boolean isReady, @Nonnull WorldInfo worldInfo)
	{
		this.data.isReady = isReady;
		this.data.readyID = makeReadyID(worldInfo);
		return (this.data.isReady && this.data.readyID.equals(this.makeReadyID(worldInfo)));
	}

	@Nullable
	public Integer getInteger(@Nonnull String name) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if(stackTraceElements == null || stackTraceElements.length == 0) {
            return null;
        }
        String callClassName = stackTraceElements[2].getClassName();
		return this.data.intValues.get(callClassName + ":" + name);
	}

	@Nullable
	public Double getDouble(@Nonnull String name) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if(stackTraceElements == null || stackTraceElements.length == 0) {
            return null;
        }
        String callClassName = stackTraceElements[2].getClassName();
		return this.data.doubleValues.get(callClassName + ":" + name);
	}

	@Nullable
	public Boolean getBoolean(@Nonnull String name) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if(stackTraceElements == null || stackTraceElements.length == 0) {
            return null;
        }
        String callClassName = stackTraceElements[2].getClassName();
		return this.data.boolValues.get(callClassName + ":" + name);
	}

	@Nullable
	public String getString(@Nonnull String name) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if(stackTraceElements == null || stackTraceElements.length == 0) {
            return null;
        }
        String callClassName = stackTraceElements[2].getClassName();
		return this.data.stringValues.get(callClassName + ":" + name);
	}

	@Nullable
	public EntityID getEntityID(@Nonnull String name) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if(stackTraceElements == null || stackTraceElements.length == 0) {
            return null;
        }
        String callClassName = stackTraceElements[2].getClassName();
		Integer id = this.data.idValues.get(callClassName + ":" + name);
		return id == null ? null : new EntityID(id);
	}

	@Nullable
	public List<Integer> getIntegerList(@Nonnull String name) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if(stackTraceElements == null || stackTraceElements.length == 0) {
            return null;
        }
        String callClassName = stackTraceElements[2].getClassName();
		return this.data.intLists.get(callClassName + ":" + name);
	}

	@Nullable
	public List<Double> getDoubleList(@Nonnull String name) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if(stackTraceElements == null || stackTraceElements.length == 0) {
            return null;
        }
        String callClassName = stackTraceElements[2].getClassName();
		return this.data.doubleLists.get(callClassName + ":" + name);
	}

	@Nullable
	public List<String> getStringList(@Nonnull String name) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if(stackTraceElements == null || stackTraceElements.length == 0) {
            return null;
        }
        String callClassName = stackTraceElements[2].getClassName();
		return this.data.stringLists.get(callClassName + ":" + name);
	}

	@Nullable
	public List<EntityID> getEntityIDList(@Nonnull String name) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if(stackTraceElements == null || stackTraceElements.length == 0) {
            return null;
        }
        String callClassName = stackTraceElements[2].getClassName();
		List<Integer> cvtList = this.data.idLists.get(callClassName + ":" + name);
		return cvtList == null ? null : cvtList.stream().map(EntityID::new).collect(Collectors.toList());
	}

	@Nullable
	public List<Boolean> getBooleanList(@Nonnull String name) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if(stackTraceElements == null || stackTraceElements.length == 0) {
            return null;
        }
        String callClassName = stackTraceElements[2].getClassName();
		return this.data.boolLists.get(callClassName + ":" + name);
	}

	public boolean isReady(@Nonnull WorldInfo worldInfo)
	{
		return (this.data.isReady && this.data.readyID.equals(this.makeReadyID(worldInfo)));
	}

	@Nonnull
	private String makeReadyID(@Nonnull WorldInfo worldInfo)
	{
		return "" + worldInfo.getBounds().getX() + "" + worldInfo.getBounds().getY() + "" + worldInfo.getAllEntities().size();
	}
}
