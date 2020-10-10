package com.mikadifo.models.function_calls;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mikadifo.models.DB_Connection;

public class AllImagesByPlace implements FunctionDB {

    private DB_Connection dbConnection = new DB_Connection();
    private final String functionName = "all_images_by_place_id(?)";
    private ResultSet results;
    private List<AllImagesByPlace> resultList;

    private int placeId;

    private int imageId;
    private byte[] image;
    private String image_author;
    private String image_description;
    
    public AllImagesByPlace(int placeId) {
	this.placeId = placeId;
    }

    private AllImagesByPlace(int imageId,
			    byte[] image,
			    String image_author,
			    String image_description) {
	this.imageId = imageId;
	this.image = image;
	this.image_author = image_author;
	this.image_description = image_description;
    }

    @Override
    public List<AllImagesByPlace> selectAll() {
	resultList = new ArrayList<>();

	try {
	    dbConnection.buildAndPrepareSelect(functionName);
	    dbConnection.getStatement().setInt(1, placeId);
            results = dbConnection.executeQuery();
	    
	    while(results.next()) {
                resultList.add(getObjectFromResultSet());
            }    
	    
	    results.close();
            dbConnection.closeStatement();
        } catch (SQLException ex) {
	   return null;
	}

	return  resultList;
    }

    private AllImagesByPlace getObjectFromResultSet() 
	    throws SQLException {
	return new AllImagesByPlace(
		results.getInt("image_id"),
		results.getBytes("image"),
		results.getString("image_author"), 
		results.getString("image_description")
	);
    }

    public int getImageId() { return imageId; }

    public byte[] getImage() { return image; }

    public String getImage_author() { return image_author; }

    public String getImage_description() { return image_description; }

    @Override
    public String toString() {
    	return this.image[0] + "---" + this.image_author + "---" + this.image_description;
    }

}
