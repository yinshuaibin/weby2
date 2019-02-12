package com.ferret.utils;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tree {
	private String id ="id";
	private String pid="pid";
	private String children ="children";
	private String title = "name";
	private String number="number";
	// private String url = "url";
	public Tree(){}
	public Tree(String number, String pid){
		this.number = number;
		this.pid = pid;
	}
	public Tree(String id,String number, String pid, String children, String title){

		this.id = id;
		this.pid = pid;
		this.children = children;
		this.title=title;
		this.number=number;
		//	this.url = url;
	}
	public String getNumber(){return number;}
	public void setNumber(String number){this.number=number;}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getChildren() {
		return children;
	}

	public void setChildren(String children) {
		this.children = children;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Map<String, Object>> getFormatTree() {
		return formatTree;
	}

	public void setFormatTree(List<Map<String, Object>> formatTree) {
		this.formatTree = formatTree;
	}

	/**
	 +----------------------------------------------------------
	 * 把返回的数据集转换成Tree
	 +----------------------------------------------------------
	 * @access public
	+----------------------------------------------------------
	 * @param  list 要转换的数据集
	+----------------------------------------------------------
	 * @return array
	+----------------------------------------------------------
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> toTree(List<Map<String,Object>> list){

		List<Map<String,Object>> tree = new ArrayList<Map<String,Object>>();
		if (null == list || list.size()<=0){
			return tree;
		}

		Map<Object,Object> refer = new HashMap<Object, Object>();
		for (Map<String, Object> map : list) {

			Object _key = map.get(number);
			refer.put(_key, map);
		}
		for (Map<String, Object> map : list) {
			Object parentId = map.get(pid);
			if (refer.containsKey(parentId)){

				Map<String,Object> parent = (Map<String, Object>) refer.get(parentId);

				List<Map<String,Object>> _child = (List<Map<String, Object>>) parent.get(children);

				if (null == _child || _child.size()<=0){

					_child = new ArrayList<Map<String,Object>>();
				}
				_child.add(map);
				parent.put(children, _child);
			}else{
				tree.add(map);
			}
		}
		return tree;
	}

	/**
	 * 将格式数组转换为树
	 *
	 * @param array $list
	 * @param integer $level 进行递归时传递用的参数
	 */
	private List<Map<String,Object>> formatTree = new ArrayList<Map<String,Object>>(); //用于树型数组完成递归格式的全局变量

	@SuppressWarnings("unchecked")
	private List<Map<String,Object>> _toFormatTree(List<Map<String,Object>> list,int level){

		for (Map<String, Object> map : list) {

			StringBuffer tmp_str = new StringBuffer(StringUtils.repeat("&nbsp;&nbsp;", level *2));

			tmp_str.append("|--");
			map.put("level", level);
			map.put("title_show", tmp_str.append(map.get(title)));
			if (!map.containsKey(children)){
				this.formatTree.add(map);
			}else{
				List<Map<String,Object>> tmp_arr = (List<Map<String, Object>>) map.get(children);
				map.remove(children);
				formatTree.add(map);
				this._toFormatTree(tmp_arr, level+1);
			}
		}
		return formatTree;
	}
	/**
	 * 角色赋权的时候调用, 获得菜单树形列表
	 * [{
	 "text": "Same but with checkboxes",
	 "children": [{
	 "text": "initially selected",
	 "state": {
	 "selected": true
	 }
	 }, {
	 "text": "custom icon",
	 "icon": "fa fa-warning icon-state-danger"
	 }, {
	 "text": "initially open",
	 "icon" : "fa fa-folder icon-state-default",
	 "state": {
	 "opened": true
	 },
	 "children": ["Another node"]
	 }, {
	 "text": "custom icon",
	 "icon": "fa fa-warning icon-state-warning"
	 }, {
	 "text": "disabled node",
	 "icon": "fa fa-check icon-state-success",
	 "state": {
	 "disabled": true
	 }
	 }]
	 },
	 "And wholerow selection"
	 ]*/
	@SuppressWarnings("unchecked")
	private List<Map<String,Object>> _toJsTree(List<Map<String,Object>> list,int level){
		if (null == list || list.size() <=0){
			return null;
		}

		List<Map<String,Object>> _list = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> map : list) {
			Map<String,Object> _map = new HashMap<String, Object>();
			_map.put("text", map.get(title));
//			Map<String,Object> attr = new HashMap<String, Object>();
//			attr.put("id", map.get(id));
			_map.put("number", map.get(number));
//			_map.put("id",map.get(id));
			Boolean check = (Boolean) map.get("check");

			if (map.containsKey(children)){
				List<Map<String,Object>> tmp_arr = (List<Map<String, Object>>) map.get(children);
				map.remove(children);
				List<Map<String,Object>> children = this._toJsTree(tmp_arr, level+1);
				if (null != children && children.size() >0){
					_map.put("children", children);
				}else{
					if (null != check && check){
						Map<String,Object> _state = new HashMap<String, Object>();
						_state.put("selected", true);
						_map.put("state", _state);
					}
					_map.put("icon", "fa fa-file icon-state-success icon-lg");
				}
			}else{
				if (null != check && check){
					Map<String,Object> _state = new HashMap<String, Object>();
					_state.put("selected", true);
					_map.put("state", _state);
				}
				_map.put("icon", "fa fa-file icon-state-success icon-lg");
			}
			_list.add(_map);
		}
		return _list;
	}


	@SuppressWarnings("unchecked")
	private List<Map<String,Object>> _toIviewTree(List<Map<String,Object>>list,int level,boolean expand){
		if (null == list || list.size()<=0){
			return null;
		}
		List<Map<String,Object>> _list = new ArrayList<Map<String,Object>>();

		for (Map<String, Object> map : list) {

			Map<String,Object> _map = new HashMap<String, Object>();

			_map.put("title", map.get(title));
			_map.put("number",map.get(number));
			_map.put("id", map.get(id));
			_map.put("expand", expand);

            /** 判断是否含有孩子*/
			if (map.containsKey(children)){
				/** 将孩子数组转换成树*/
				List<Map<String,Object>> tmp_arr = (List<Map<String, Object>>) map.get(children);

				List<Map<String,Object>> _children = this._toIviewTree(tmp_arr, level+1, expand);

					_map.put(children, _children);
			}else{
				map.remove(children);
			}
			_list.add(_map);
		}
		return _list;
	}

	public List<Map<String,Object>> toFormatTree(List<Map<String,Object>> list){
		list = this.toTree(list);
		this._toFormatTree(list, 0);
		return this.formatTree;
	}

	public List<Map<String,Object>> toJsTree(List<Map<String,Object>> list){
		list = this.toTree(list);
		return this._toJsTree(list, 0);
	}

	public List<Map<String,Object>> toIviewTree(List<Map<String,Object>> list,boolean expand){
		list = this.toTree(list);
		return this._toIviewTree(list, 0, expand);
	}

	public static void main(String[] args) {
		
		/*String jsonStr = "[{\"id\":1,\"pid\":0,\"name\":\"实时分析\"},{\"id\":2,\"pid\":1,\"name\":\"此时此刻\"},{\"id\":3,\"pid\":1,\"name\":\"经营时报\"},{\"id\":9,\"pid\":0,\"name\":\"销售分析\"},{\"id\":11,\"pid\":9,\"name\":\"销售概览\"},{\"id\":12,\"pid\":9,\"name\":\"订单分析\"},{\"id\":4,\"pid\":0,\"name\":\"权限管理\"},{\"id\":5,\"pid\":4,\"name\":\"角色管理\"},{\"id\":6,\"pid\":4,\"name\":\"用户管理\"},{\"id\":7,\"pid\":4,\"name\":\"菜单管理\"},{\"id\":13,\"pid\":0,\"name\":\"供应链分析\"},{\"id\":14,\"pid\":13,\"name\":\"库存分析\"},{\"id\":15,\"pid\":14,\"name\":\"整体\"},{\"id\":16,\"pid\":14,\"name\":\"仓库\"},{\"id\":17,\"pid\":14,\"name\":\"BAND分级\"},{\"id\":18,\"pid\":14,\"name\":\"组织\"},{\"id\":19,\"pid\":14,\"name\":\"组织BAND分级\"},{\"id\":20,\"pid\":14,\"name\":\"品牌\"},{\"id\":21,\"pid\":14,\"name\":\"分类\"},{\"id\":22,\"pid\":14,\"name\":\"sku明细\"}]";
		List list = (List) JSON.parse(jsonStr);
		System.out.println(list);
		Tree t = new Tree();
		list = t.toJsTree(list);
		System.out.println(JSON.toJSON(list));*/

	}
}
