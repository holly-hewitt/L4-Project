import java.util.Set;

import org.antlr.v4.runtime.tree.ParseTree;

public class jsonObject extends jsonComplexElement {

	jsonParser.ObjContext ctx;

	// private ArrayList<jsonObject> childObjs;
	// private ArrayList<jsonArray> childArrs;

	public jsonObject(int fieldNo, jsonParser.ObjContext ctx, int depth) {
		super(fieldNo, depth);
		this.ctx = ctx;
		setTypeName("object");
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name
	 * @param ctx
	 * @param fieldNo
	 * @param childObjs
	 * @param childArrs
	 */
	public jsonObject(String name, int fieldNo, jsonParser.ObjContext ctx, int depth) {
		super(name, fieldNo, depth);
		if (name.equals("[10]")) {
			this.name = "This json file";
		} else {
			this.name = name;
		}
		this.ctx = ctx;
		setTypeName("object");
	}

	@Override
	public String listAllChildren() {

		String description = "";

		Set<String> types = children.keySet();
		for (String type : types) {
			int numOfType = children.get(type).size();
			if (numOfType > 0) {
				if (numOfType==fieldNo && description.length()>3) {
					description = description.substring(0,-2);
				}
				if (description.length() > 3) {
					if (description.charAt(description.length() - 2) == ',' || description.charAt(description.length()-1) == '.') {
						description = description.substring(0, description.length() - 2);
					}
				}
				description += listFields(numOfType, type);


				String names = "";
				for (jsonElement child : children.get(type)) {
					if (!child.getName().equals("")) {
						names += child.getName() + ", ";

					}
				}
				if (!names.equals("")) {
					description += ", named: " + names;
					description = description.substring(0, description.length() - 2);
				}

				description += ". ";
			}

		}

		return description;
	}

	@Override
	public String fullListAllChildren() {

		String description = "";

		Set<String> types = children.keySet();
		for (String type : types) {
			int numOfType = children.get(type).size();
			if (numOfType > 0) {
				if (numOfType==fieldNo && description.length()>3) {
					description = description.substring(0,-2);
				}
				description += listFields(numOfType, type);

				description += " named: ";

				for (jsonElement child : children.get(type)) {
					if(!(type.equals("object") || type.equals("array"))) {
						if (!child.getName().equals("")) {
							description += child.getName();
							// if (!child.getValue().equals("")) {
							description += " with value " + child.getValue();
							// }
							description += ", ";
						}
					}else {
						if (!child.getName().equals("")){
							description += child.getName();
							description += ", ";
						}
					}
				}
				description = description.substring(0, description.length() - 2);
				//description += "#### hello from line 103 ####";



			}

		}
		char char1 = description.charAt(description.length()-1);
		char char2 = description.charAt(description.length()-2);
		if (char1 != '.' && char2 != '.') {
			description += ". ";
		}

		return description;
	}

	/**
	 * Method to check whether two objects are of the same structure Checks for 2
	 * things 1. Do objects have same field number 2. Do objects have same field
	 * names
	 *
	 * @param obj1: the object we are comparing this objectOrArr to
	 * @return true if objects are same structure else false
	 */
	boolean sameFields(jsonObject obj1) {
		if (fieldNo != obj1.getFieldNo()) {
			return false;
		}
		int n = 0;
		for (ParseTree i : ctx.children) {
			ParseTree j = obj1.getCtx().getChild(n);
			if (i.getChildCount() != j.getChildCount()) {
				return false;
			}
			if (i.getChildCount() > 0) {
				if (!i.getChild(0).toString().equals(j.getChild(0).toString())) {
					return false;
				}
			}
			n++;
		}
		return true;
	}

	/**
	 * @return the ctx
	 */
	public jsonParser.ObjContext getCtx() {
		return ctx;
	}

}
