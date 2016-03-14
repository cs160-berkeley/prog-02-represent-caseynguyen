package com.sunlightlabs.entities;

import java.util.*;

import org.json.*;

import com.sunlightlabs.api.*;

/**
 * represents a Bill in congress or a legislature
 * com.sunlightlabs.entities.Legislator steve Jul 22, 2009
 */
public class Bill extends JSONEntity {
	public static Class<Bill> THIS_CLASS = Bill.class;
	public static Bill[] EMPTY_ARRAY = {};

	public static final String[] KNOWN_PROPERTIES = { "chamber", "id",
				"name", "subcommittees" };

	public static final String name = "value";


	public static String getPluralEntityName() {
		return "committees";
	}

	/**
	 * internal function to build Committees
	 * @param items non-null array of JSONObject
	 * @return non-null array of Committee
	 */
	protected static Bill[] buildBills(JSONObject[] items) {
		Bill[] ret = new Bill[items.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = new Bill(items[i]);
		}
		return ret;
	}

	/**
	 * 
	 * @param call non-null caller
	 * @param chamber
	 * @return non-null array of committees
	 */
	public static Bill[] allBills(ApiCall call, String sponsor_id) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("sponsor_id", sponsor_id);
		String apiCall = "bills";
		JSONObject[] items = JSONEntity.getJSONObjects2(call, params, apiCall, getPluralEntityName());
		return buildBills(items);
	}

	/**
	 * 
	 * 
	 * @param call non-null caller
	 * @param id
	 * @return
	 */
	public static Bill getBillById(ApiCall call, String id ) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		return getBillById( call,  params );
	}

	/**
	 * 
	 * @param call non-null caller
	 * @param params
	 * @return possiblu null Commottee
	 */
	public static Bill getBillById(ApiCall call, Map<String, String> params ) {
		String apiCall = "committees.get";
		JSONObject item = JSONEntity.getJSONObject(call, params, apiCall,
				getPluralEntityName());
		return new Bill(item);
	}

	/**
	 * 
	 * @param call non-null caller
	 * @param biocode
	 * @return non-null array of committees
		 */
	public static Bill[] getBillsForLegislator(ApiCall call,Legislator leg) {
		 return getBillsForLegislator( call,leg.getProperty("bioguide_id"));
	}
	/**
	 * 
	 * @param call non-null caller
	 * @param biocode
	 * @return non-null array of committees
		 */
	public static Bill[] getBillsForLegislator(ApiCall call,String biocode) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("bioguide_id", biocode);
		String apiCall = "committees.allForLegislator";
		JSONObject[] items = JSONEntity.getJSONObjects(call, params, apiCall,getPluralEntityName());
		return buildBills(items);
	}

	
	private Bill[] m_Subcommittees = EMPTY_ARRAY;
	
	/**
	 * constructor
	 * @param data non-null json object with properties
	 */
	public Bill(JSONObject data) {
		super(data);
	}

	/**
	 * constructor
	 * @param data non-null map with properties
	 */
	public Bill(Map data) {
		super(data);
	}
	
	protected void handleJSONArray( JSONArray value)
	{
		JSONObject[] subcommittees = getArrayItems(value);
		m_Subcommittees = buildBills(subcommittees);
	}

	
	public Bill[] getSubcommittees() {
		return m_Subcommittees;
	}

	public void setSubcommittees(Bill[] subcommittees) {
		m_Subcommittees = subcommittees;
	}

	/**
	 * JSON Tag for this
	 */
	public String getEntityName() {
		return "bill";
	}

	/**
	 * unofficial list of properties
	 */
	public String[] getKnownProperties() {
		return KNOWN_PROPERTIES;
	}
	
	public String getName()
	{
		return getProperty("name");
	}
	
	/**
	 * write all name value pairs to the appender
	 * @param out non-null appender
	 */
	@Override
	public  void showProperties(Appendable out) {
		super.showProperties( out);
		Bill[] values = getSubcommittees();
		for (int j = 0; j < values.length; j++) {
			values[j].showProperties( out);
		}
	}
}
