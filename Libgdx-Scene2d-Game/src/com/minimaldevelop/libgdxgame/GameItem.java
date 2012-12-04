package com.minimaldevelop.libgdxgame;



public class GameItem{
	String itemType;
	float x;
	float y;
	float width;
	float height;
	
	public GameItem(String itemType, float x, float y, float width, float height) {
		this.itemType = itemType;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public GameItem() {
		
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

//	@Override
//	public void write(Json json) {
//		// TODO Auto-generated method stub
//		json.writeValue("itemType", itemType);
//		json.writeValue("x", x);
//		json.writeValue("y", y);
//	}
//
//	@Override
//	public void read(Json json, OrderedMap<String, Object> jsonData) {
//		// TODO Auto-generated method stub
////	    currentLevelId = json.readValue( "currentLevelId", Integer.class, jsonData );
////	    credits = json.readValue( "credits", Integer.class, jsonData );
////	 
////	    // libgdx handles the keys of JSON formatted HashMaps as Strings, but we
////	    // want it to be integers instead (because the levelIds are integers)
////	    Map<String,Integer> highScores = json.readValue( "highScores", HashMap.class,
////	        Integer.class, jsonData );
////	    for( String levelIdAsString : highScores.keySet() ) {
////	        int levelId = Integer.valueOf( levelIdAsString );
////	        Integer highScore = highScores.get( levelIdAsString);
////	        this.highScores.put( levelId, highScore );
////	    }
//	}
	
}
