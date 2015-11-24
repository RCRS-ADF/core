package adf.agent.precompute;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PreDatas
{
	public Map<String, Integer> intValues;
	public Map<String, Double> doubleValues;
	public Map<String, String> stringValues;
	public Map<String, Integer> idValues;

	public Map<String, List<Integer>> intLists;
	public Map<String, List<Double>> doubleLists;
	public Map<String, List<String>> stringLists;
	public Map<String, List<Integer>> idLists;

	public boolean isReady;

	public PreDatas()
	{
		this.intValues = new HashMap<>();
		this.doubleValues = new HashMap<>();
		this.stringValues = new HashMap<>();
		this.idValues = new HashMap<>();
		this.intLists = new HashMap<>();
		this.doubleLists = new HashMap<>();
		this.stringLists = new HashMap<>();
		this.idLists = new HashMap<>();
		this.isReady = false;
	}

	public PreDatas copy()
	{
		PreDatas preDatas = new PreDatas();
		preDatas.intValues = new HashMap<>(this.intValues);
		preDatas.doubleValues = new HashMap<>(this.doubleValues);
		preDatas.stringValues = new HashMap<>(this.stringValues);
		preDatas.idValues = new HashMap<>(this.idValues);
		preDatas.intLists = new HashMap<>(this.intLists);
		preDatas.doubleLists = new HashMap<>(this.doubleLists);
		preDatas.stringLists = new HashMap<>(this.stringLists);
		preDatas.idLists = new HashMap<>(this.idLists);
		preDatas.isReady = this.isReady;
		return preDatas;
	}
}
